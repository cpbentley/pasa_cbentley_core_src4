/*
 * (c) 2018-2020 Charles-Philip Bentley
 * This code is licensed under MIT license (see LICENSE.txt for details)
 */
package pasa.cbentley.core.src4.ctx;

import pasa.cbentley.core.src4.io.BADataIS;
import pasa.cbentley.core.src4.io.BADataOS;
import pasa.cbentley.core.src4.logging.Dctx;
import pasa.cbentley.core.src4.logging.IDLog;
import pasa.cbentley.core.src4.logging.IStringable;
import pasa.cbentley.core.src4.logging.ITechLvl;
import pasa.cbentley.core.src4.stator.IStatorOwner;
import pasa.cbentley.core.src4.stator.ITechStator;
import pasa.cbentley.core.src4.stator.Stator;
import pasa.cbentley.core.src4.stator.StatorReader;
import pasa.cbentley.core.src4.stator.StatorWriter;
import pasa.cbentley.core.src4.structs.IntBuffer;
import pasa.cbentley.core.src4.structs.IntToInts;
import pasa.cbentley.core.src4.structs.IntToObjects;
import pasa.cbentley.core.src4.utils.StringUtils;

/**
 * Manages several code contexts {@link ICtx}.
 * <br>
 * A {@link UCtx} always have a non null {@link CtxManager}. 
 * <br>
 * But a {@link CtxManager} may control 1,2 or more {@link UCtx}.
 * <br> 
 * A {@link CtxManager} control the save/load of a code context preference data.
 * <li> {@link CtxManager}
 * 
 * Usually it will be alone, but you could imagine several CtxManager, one for each applications
 * running in the same JVM process.
 * 
 * Example:
 * <li>Run the same application twice, each with a user language (english, french, russian), {@link UCtx} and {@link CtxManager}
 * <br>
 * <br>
 * 
 * Its configuration tells wether to erase or delete
 * 
 * @author Charles Bentley
 *
 */
public class CtxManager implements IStringable, IStatorOwner {

   /**
    * Number of static known ids.
    * <br>
    * <br>
    * Afterwards
    */
   public static final int STATIC_DEFINED = 10;

   /**
    * 
    */
   private IntBuffer       ids;

   /**
    * {@link ICtx#getCtxID()} and their {@link ICtx} object. Never Nulls inside.
    * <br>
    * Index is order of registration.
    * 
    * Contains byte[] data loaded from disk if Ctx has not been created
    */
   private IntToObjects    intos;

   private UCtx            uc;

   public CtxManager(UCtx uc) {
      this.uc = uc;
      intos = new IntToObjects(uc);
      ids = new IntBuffer(uc, 5);
      dataOffsets = new IntToObjects(uc);
   }

   public ICtx getCtx(int ctxID) {
      int index = intos.findInt(ctxID);
      if (index == -1) {
         return null;
      } else {
         return (ICtx) intos.getObjectAtIndex(index);
      }
   }

   /**
    * 
    * @param registrationID 1 index based ID provided during registration
    * @return
    * @throws ArrayIndexOutOfBoundsException
    */
   public ICtx getCtxFromRegID(int registrationID) {
      return (ICtx) intos.objects[registrationID - 1];
   }

   public ICtx getCtxOwner() {
      return uc;
   }

   /**
    * Loading 
    */
   private IntToObjects dataOffsets;

   private byte[]       dataCtx;

   private int          dataCtxOffset;

   /**
    * The first stator
    */
   private Stator       firstStator;

   public Stator getStatorInitFor(ICtx ctx) {
      if (firstStator == null) {
         return null;
      } else {
         int ctxID = ctx.getCtxID();
         int[] ar = (int[]) dataOffsets.findIntObject(ctxID);
         if (ar != null) {
            firstStator.setActiveReaderWith(dataCtx, ar[0], ar[1]);
            return firstStator;
         } else {
            return null;
         }
      }

   }

   /**
    * Return an integer array with triplets
    * 
    */
   public int[] getStaticRanges(int staticID) {
      IntBuffer id = new IntBuffer(uc);
      int[] r = ids.getIntsRef();
      int size = ids.getSize();
      for (int i = 0; i < size; i += 3) {
         int sID = r[i + 1];
         if (sID == staticID) {
            int sIDOffset = r[i + 2];
            int sLen = r[i + 3];
            id.addInt(sIDOffset);
            id.addInt(sLen);
         }
      }
      return id.getIntsClonedTrimmed();
   }

   private void rangeOverlap(int modID, int staticID, int first, int last, int firstV, int lastV) {
      String msg = "Overlap staticid=" + staticID + " Incoming [" + first + "," + last + "] with [" + firstV + "," + lastV + "] of moduleid=" + modID;
      //#debug
      toDLog().pData(msg, this, CtxManager.class, "rangeOverlap");
      throw new RuntimeException(msg);
   }

   /**
    * Called by constructor of a {@link ICtx}
    * 
    * <p>
    * Calls {@link ICtx#setSettings(byte[])} if any byte array has been mapped to CTX id.
    * </p>
    * 
    * @param ctx
    * @return a value from 1 to [
    */
   public int registerCtx(ICtx ctx) {
      //#debug
      uc.toStrDebugNullCheck(ctx, uc);

      if (intos.hasObject(ctx)) {
         throw new IllegalArgumentException("Same context reference is already registered");
      }
      //called during CodeCtx construction. Therefore we do not push anything back down to class.
      //we are tempted to send back CodeCtx settings now.
      int ctxID = ctx.getCtxID();
      Object stored = intos.findIntObject(ctxID);
      //check validity of ctxid
      if (stored != null) {
         if (stored.getClass() == ctx.getClass()) {
            throw new IllegalArgumentException("Same context class " + ctx.getClass().getName() + " is being registered.");
         } else {
            throw new IllegalArgumentException(ctx.getClass().getName() + " collides with existing " + stored.getClass().getName() + " with the Context ID (" + ctxID + ").");
         }
      }
      intos.add(ctx, ctxID); //add the ctx
      return intos.nextempty;
   }

   public void registerRemoveCtx(ICtx ctx) {
      intos.removeRef(ctx);
   }

   /**
    * Registers a framework known StaticID slot
    * <br>
    * Static Id domains are defined at the module level.
    * 
    * Its the key that is used to register those objects of the given type
    * <br>
    * Examples, key for CMDs, key for DIDs, key for Strings
    * 
    * @param staticID
    */
   public void registerStaticID(ICtx ctx, int staticID) {
      int[] r = ids.getIntsRef();
      int size = ids.getSize();
      //
      for (int i = 0; i < size; i += 3) {
         int sID = r[i + 1];

         if (sID == staticID) {
            throw new RuntimeException("Static Already Registered");
         }
      }
   }

   /**
    * Checks for conflicts with established ranges within a staticID domain of values.
    * <br>
    * Throws a {@link RuntimeException} when a conflict is found. Dev must fix the static range manually.
    * <br>
    * <br>
    * Register a range of integers for a static class.
    * <br>
    * <br>
    * For example, String IDs. The module at the top created a static id class using {@link IStaticObjCtrl#createNewStaticID()}.
    * <br>
    * 
    * @param staticID static ID can be anything but should not collide with others
    * @param first
    * @param last
    * @throws new {@link RuntimeException} when conflict of range
    */
   public void registerStaticRange(ICtx ctx, int staticID, int first, int last) {
      int[] r = ids.getIntsRef(); //dangerously get the reference to the array
      int size = ids.getSize();
      //start at 1 because IntBuffer has first element as length
      for (int i = 1; i < size; i += 4) {
         int modID = r[i];
         int sID = r[i + 1];
         if (sID == staticID) {
            //already existing range. check if there is an overlap
            int sidFirst = r[i + 2];
            int sidLast = r[i + 3];
            //
            if (first >= sidFirst && first <= sidLast) {
               rangeOverlap(modID, staticID, first, last, sidFirst, sidLast);
            }
            if (last >= sidFirst && last <= sidLast) {
               rangeOverlap(modID, staticID, first, last, sidFirst, sidLast);
            }
         }
      }
      //we are good to go!
      ids.addInt(new int[] { ctx.getCtxID(), staticID, first, last });
   }

   public void stateOwnerRead(Stator stator) {
      if (firstStator == null) {
         firstStator = stator;
      }
      StatorReader statorReaderCtx = stator.getReader(ITechStator.TYPE_3_CTX);
      //check if we any data for us
      if (statorReaderCtx != null) {
         //read 
         dataCtx = statorReaderCtx.getReaderByte();
         dataCtxOffset = statorReaderCtx.getReaderByteOffset();

         BADataIS dis = statorReaderCtx.getReader();
         int numCtx = dis.readInt();
         for (int i = 0; i < numCtx; i++) {
            int ctxID = dis.readInt();
            int ctrlInt = dis.readInt();
            if (ctrlInt == 1) {
               int posCtx = statorReaderCtx.getReaderByteOffset();
               //reads into temporary st
               stator.setActiveReaderWith(dis);
               int len = dis.readInt();
               dataOffsets.add(ctxID, new int[] { posCtx, len });
               StatorReader active = stator.getActiveReader();
               ICtx ctx = getCtx(ctxID);
               if (ctx != null) {
                  ((IStatorOwner) ctx).stateOwnerRead(stator);

               } else {
                  //not loaded yet, will load data later
               }
            }
         }
      }
   }

   public void stateOwnerWrite(Stator stator) {
      StatorWriter stateWriterCtx = stator.getWriter(ITechStator.TYPE_3_CTX);
      BADataOS dos = stateWriterCtx.getWriter();

      //write the number
      int size = intos.nextempty;
      int writeCount = dos.size();
      dos.writeInt(size);
      for (int i = 0; i < size; i++) {
         ICtx ctx = (ICtx) intos.objects[i];
         int ctxID = intos.ints[i];
         dos.writeInt(ctxID);

         if (ctx instanceof IStatorOwner) {
            dos.writeInt(1);
            //temporarily write
            stator.setActiveWriteTemp();
            StatorWriter active = stator.getActiveWriter();
            //stator flag that we need to compartimalize here
            ((IStatorOwner) ctx).stateOwnerWrite(stator);
            int sizeBeforeWrite = dos.size();
            stator.getActiveWriter().serializeData(dos);
            int len1 = active.getBytesWritten();
            int sizeAfterWrite = dos.size();
            int len = sizeAfterWrite - sizeBeforeWrite;
            dos.writeInt(len);
         } else {
            dos.writeInt(0);
         }
      }
      int writeCountB = dos.size();
      int bytesWritten = writeCountB - writeCount;
      //#debug
      toDLog().pData("End of Method:" + bytesWritten + " bytes were written to BADataOS", null, CtxManager.class, "stateWrite");

   }

   /**
    * We keep the.
    * What if a module is already loaded? Can we call this method?
    * 
    * @param dis
    */
   public void stateRead(BADataIS dis) {
      try {
         int num = dis.readInt();
         for (int i = 0; i < num; i++) {
            int val = dis.readInt();
            if (val == 1) {
               int ctxID = dis.readInt();
               byte[] data = dis.readByteArray();
               //avoid corrupted data
               int ctxIndex = intos.findInt(ctxID);
               //#debug
               uc.toDLog().pData("ctxIndex=" + ctxIndex + " for ctxID=" + ctxID, this, CtxManager.class, "settingsRead", ITechLvl.LVL_04_FINER, true);
               if (ctxIndex == -1) {
                  intos.add(data, ctxID);
               } else {
                  ICtx mod = (ICtx) intos.getObjectAtIndex(ctxIndex);
                  mod.setSettings(data);
               }
            }
         }
         //#debug
         uc.toDLog().pData("Count=" + num, this, CtxManager.class, "settingsRead");
      } catch (IllegalStateException e) {
         //reset all data

         //#debug
         toDLog().pEx("Corruption when reading CtxManager state", this, CtxManager.class, "stateRead", e);
      }
   }

   public void stateWrite2(BADataOS dos) {
   }

   /**
    * Write state of {@link ICtx#getSettings()} data.
    * 
    * <p>
    * 
    * </p>
    * @param dos
    */
   public void stateWrite(BADataOS dos) {
      //write the number
      int size = intos.nextempty;
      int writeCount = dos.size();

      dos.writeInt(size);
      for (int i = 0; i < size; i++) {
         Object o = intos.objects[i];
         byte[] data = null;
         int ctxID = intos.ints[i];
         //
         if (o instanceof ICtx) {
            ICtx module = (ICtx) o;
            data = module.getSettings();
         } else if (o instanceof byte[]) {
            data = (byte[]) o;
         } else {
            //log dev warning
            throw new RuntimeException();
         }

         if (data != null) {
            dos.writeInt(1);
            dos.writeInt(ctxID);
            dos.writeByteArray(data);
            //#debug
            toDLog().pData(data.length + " bytes of data for ctxID " + ctxID + " " + toStringCtxID(ctxID), null, CtxManager.class, "settingsWrite");
         } else {
            //#debug
            toDLog().pData("No data for ctxID " + ctxID + " " + toStringCtxID(ctxID), null, CtxManager.class, "settingsWrite");
            dos.writeInt(0);
         }
      }
      int writeCountB = dos.size();
      int bytesWritten = writeCountB - writeCount;
      //#debug
      toDLog().pData("End of Method:" + bytesWritten + " bytes were written to BADataOS", null, CtxManager.class, "stateWrite");
   }

   //#mdebug
   public IDLog toDLog() {
      return uc.toDLog();
   }

   public String toString() {
      return Dctx.toString(this);
   }

   public void toString(Dctx dc) {
      dc.root(this, CtxManager.class, 309);
      dc.nlVar("StaticIDs counter", STATIC_DEFINED);

      //set flag to prevent make onelines at lvl2
      Dctx di = dc.newLevel();
      di.root(this, "IntToObjects");
      di.append(" [size=" + intos.nextempty + "]");
      String total = String.valueOf(intos.nextempty);
      int numChars = total.length();
      for (int i = 0; i < intos.nextempty; i++) {
         di.nl();
         int num = (i + 1);
         String strNum = uc.getStrU().prettyIntPaddStr(num, numChars, " ");
         di.append(strNum);
         di.append("=");
         di.append(uc.getStrU().prettyStringPad("" + intos.ints[i], 5, ' '));
         di.append('\t');
         Object o = intos.objects[i];
         if (o != null) {
            if (o instanceof IStringable) {
               ((IStringable) o).toString1Line(di);
            } else if (o instanceof byte[]) {
               byte[] ar = ((byte[]) o);
               di.append("byte[] len=" + ar.length);
            }
         } else {
            di.append("null");
         }
      }

      dc.nl();
      //debug 
      dc.tab();
      dc.append("Static ID and Ranges");
      int[] r = ids.getIntsRef();
      int size = ids.getSize();
      //start at 1 because IntBuffer has first element as length
      for (int i = 1; i < size; i += 4) {
         int ctxID = r[i];
         int sID = r[i + 1];
         int sidFirst = r[i + 2];
         int sidLast = r[i + 3];
         dc.nl();
         dc.appendVar("CtxID", ctxID);
         dc.appendBracketedWithSpace(toStringCtxID(ctxID));
         dc.appendVarWithSpace("StaticID", sID);
         dc.appendBracketedWithSpace(toStringStaticID(sID));
         dc.append(" [" + sidFirst + "," + sidLast + "]");
      }
      dc.tabRemove();

      //toString all ctx

      for (int i = 0; i < intos.nextempty; i++) {
         Object o = intos.objects[i];
         if (o != null) {
            if (o instanceof IStringable) {
               dc.nlLvl(((IStringable) o));
            }
         }
      }

   }

   public String toString1Line() {
      return Dctx.toString1Line(this);
   }

   public void toString1Line(Dctx dc) {
      dc.root1Line(this, "CtxManager");
   }

   public String toStringEventID(int producerID, int eventID) {
      int size = intos.nextempty;
      for (int i = 0; i < size; i++) {
         Object o = intos.objects[i];
         if (o instanceof ICtx) {
            ICtx module = (ICtx) o;
            String producer = module.toStringEventID(producerID, eventID);
            if (producer != null) {
               return producer;
            }
         }
      }
      return null;
   }
   //#enddebug

   public UCtx toStringGetUCtx() {
      return uc;
   }

   public String toStringCtxID(int ctxID) {
      int size = intos.nextempty;
      for (int i = 0; i < size; i++) {
         Object o = intos.objects[i];
         if (o instanceof ICtx) {
            ICtx module = (ICtx) o;
            if (module.getCtxID() == ctxID) {
               //StringUtils.
               StringUtils strU = uc.getStrU();
               String name = module.getClass().getName();
               String str = strU.getStringAfterLastIndex(name, '.');
               return str;
            }
         }
      }
      return null;
   }

   public String toStringProducerID(int producerID) {
      int size = intos.nextempty;
      for (int i = 0; i < size; i++) {
         Object o = intos.objects[i];
         if (o instanceof ICtx) {
            ICtx module = (ICtx) o;
            String producer = module.toStringProducerID(producerID);
            if (producer != null) {
               return producer;
            }
         }
      }
      return null;
   }

   public String toStringStaticID(int staticID) {
      int size = intos.nextempty;
      for (int i = 0; i < size; i++) {
         Object o = intos.objects[i];
         if (o instanceof ICtx) {
            ICtx module = (ICtx) o;
            String producer = module.toStringStaticID(staticID);
            if (producer != null) {
               return producer;
            }
         }
      }
      return null;
   }

   public void deleteStartData() {
      firstStator = null;
      dataCtx = null;
      dataOffsets.clear();
   }

}
