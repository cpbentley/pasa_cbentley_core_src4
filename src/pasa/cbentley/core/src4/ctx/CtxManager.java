/*
 * (c) 2018-2020 Charles-Philip Bentley
 * This code is licensed under MIT license (see LICENSE.txt for details)
 */
package pasa.cbentley.core.src4.ctx;

import java.util.Enumeration;

import pasa.cbentley.core.src4.logging.Dctx;
import pasa.cbentley.core.src4.logging.IStringable;
import pasa.cbentley.core.src4.logging.StringableWrapper;
import pasa.cbentley.core.src4.stator.IStatorOwner;
import pasa.cbentley.core.src4.stator.ITechStator;
import pasa.cbentley.core.src4.stator.Stator;
import pasa.cbentley.core.src4.stator.StatorOwnerContainer;
import pasa.cbentley.core.src4.stator.StatorReader;
import pasa.cbentley.core.src4.stator.StatorWriter;
import pasa.cbentley.core.src4.structs.IntBuffer;
import pasa.cbentley.core.src4.structs.IntToObjects;
import pasa.cbentley.core.src4.structs.ObjectToObjects;
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
public class CtxManager extends ObjectU implements IStringable, IStatorOwner {

   private static final int STATE_1_STATOR_OWNER_ID = 0xFEFEFEFE;

   private static final int STATE_2_OTHER_ID        = 0xFFFFFFFF;

   /**
    * Number of static known ids.
    * <br>
    * <br>
    * Afterwards
    */
   public static final int STATIC_DEFINED = 10;

   /**
    * Loading 
    */
   private IntToObjects    dataOffsets;


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


   public CtxManager(UCtx uc) {
      super(uc);
      intos = new IntToObjects(uc);
      ids = new IntBuffer(uc, 5);
      dataOffsets = new IntToObjects(uc);
   }

   /**
    * 
    */
   public void deleteStartData() {
      dataOffsets.clear();
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

   /**
    * 
    * @param ctx
    * @return
    */
   public Stator getStatorInitFor(ICtx ctx) {
      int ctxID = ctx.getCtxID();
      StatorOwnerContainer ar = (StatorOwnerContainer) dataOffsets.findIntObject(ctxID);
      if (ar != null) {
         Stator stator = ar.activateReader();
         return stator;
      } else {
         return null;
      }
   }

   private void rangeOverlap(int modID, int staticID, int first, int last, int firstV, int lastV) {
      ICtx ctx = getCtx(modID);
      Dctx dc = new Dctx(uc,"\n");
      dc.appendVar("Overlap staticid",staticID);
      dc.append(" Incoming [");
      dc.append(first);
      dc.append(",");
      dc.append(last);
      dc.append("] with [");
      dc.append(firstV);
      dc.append(",");
      dc.append(lastV);
      dc.append("] of moduleid=");
      dc.append(modID);
      dc.append(ctx.toString1Line());
      
      String msg = dc.toString();
      StringableWrapper stringable = new StringableWrapper(uc, toStringStaticIDRange(staticID));
      //#debug
      toDLog().pAlways(msg, stringable, CtxManager.class, "rangeOverlap@171");
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
      //#debug
      toDLog().pStator("Reading...", null, CtxManager.class, "stateOwnerRead@270", LVL_05_FINE, false);

      StatorReader statorReaderCtx = stator.getReader(ITechStator.TYPE_3_CTX);

      if (statorReaderCtx == null) {
         //#debug
         toDLog().pStator("Null StatorReader for Ctx. Data is corrupted", stator, CtxManager.class, "stateOwnerRead@130", LVL_04_FINER, true);
         return;
      }
      //we store ctx settings

      int numCtx = statorReaderCtx.dataReadInt();
      for (int i = 0; i < numCtx; i++) {
         int ctxID = statorReaderCtx.dataReadInt();
         int ctrlInt = statorReaderCtx.dataReadInt();
         if (ctrlInt == STATE_1_STATOR_OWNER_ID) {
            ICtx ctx = getCtx(ctxID);
            IStatorOwner statorOwner;
            if (ctx == null) {
               statorOwner = new StatorOwnerContainer(uc);
               //set it to the key ctxid so when the code ctx is created, it finds its data

               dataOffsets.add(ctxID, statorOwner);
            } else {
               statorOwner = (IStatorOwner) ctx;
            }
            statorReaderCtx.dataReadStatorOwnerMono(statorOwner);
         } else {
            //other nothing to do
         }
      }
   }

   public void stateOwnerWrite(Stator stator) {
      //#debug
      toDLog().pStator("Writing...", stator, CtxManager.class, "stateOwnerWrite@304", LVL_04_FINER, true);

      StatorWriter stateWriterCtx = stator.getWriter(ITechStator.TYPE_3_CTX);

      //write the number
      int size = intos.nextempty;
      stateWriterCtx.countBytesStart();
      stateWriterCtx.dataWriteInt(size);
      for (int i = 0; i < size; i++) {
         ICtx ctx = (ICtx) intos.objects[i];
         int ctxID = intos.ints[i];
         stateWriterCtx.dataWriteInt(ctxID);

         if (ctx instanceof IStatorOwner) {
            //#debug
            toDLog().pStator("IStatorOwner ctxID=" + ctxID, null, CtxManager.class, "stateOwnerWrite@304", LVL_03_FINEST, true);
            IStatorOwner statorOwner = (IStatorOwner) ctx;
            stateWriterCtx.dataWriteInt(STATE_1_STATOR_OWNER_ID);
            //we want to write as a independant entity
            //because when reading, we store the data waiting for the context to come online
            stateWriterCtx.dataWriteStatorOwnerMono(statorOwner);
         } else {
            //#debug
            toDLog().pStator("Not an IStatorOwner id=" + ctxID, null, CtxManager.class, "stateOwnerWrite@348", LVL_03_FINEST, true);

            stateWriterCtx.dataWriteInt(STATE_2_OTHER_ID);
         }
      }
      int bytesWritten = stateWriterCtx.countBytesEnd();
      //#debug
      toDLog().pStator("-->" + bytesWritten + " bytes were written to StatorWriter", null, CtxManager.class, "stateOwnerWrite@323");

   }

   //#mdebug
   public void toString(Dctx dc) {
      dc.root(this, CtxManager.class, 407);
      toStringPrivate(dc);
      super.toString(dc.sup());

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

   //#mdebug
   public boolean toString(Dctx dc, Object o) {
      Enumeration e = intos.getEnumeration();
      while (e.hasMoreElements()) {
         Object c = e.nextElement();
         if (c != null) {
            if (c instanceof ICtx) {
               ICtx ctx = (ICtx) c;
               boolean found = ctx.toString(dc, o);
               if (found) {
                  return true;
               }
            }
         }
      }
      return false;
   }

   public void toString1Line(Dctx dc) {
      dc.root1Line(this, CtxManager.class, 407);
      toStringPrivate(dc);
      super.toString1Line(dc.sup1Line());
   }

   public boolean toString1Line(Dctx dc, Object o) {
      Enumeration e = intos.getEnumeration();
      while (e.hasMoreElements()) {
         Object c = e.nextElement();
         if (c != null) {
            if (c instanceof ICtx) {
               ICtx ctx = (ICtx) c;
               boolean found = ctx.toString1Line(dc, o);
               if (found) {
                  return true;
               }
            }
         }
      }
      return false;
   }

   public String toStringStaticIDRange(int staticID) {
      Dctx dc = new Dctx(uc, "\n\t");
      dc.setLineNumbers(false);
      dc.append("Static ID Ranges for ");
      dc.appendBracketedWithSpace(toStringStaticID(staticID));
      dc.appendBracketedWithSpace(""+staticID);
      
      int[] r = ids.getIntsRef();
      int size = ids.getSize();
      //start at 1 because IntBuffer has first element as length
      for (int i = 1; i < size; i += 4) {
         int ctxID = r[i];
         int sID = r[i + 1];
         int sidFirst = r[i + 2];
         int sidLast = r[i + 3];
         if(staticID == sID) {
            dc.nl();
            dc.appendVar("CtxID", ctxID);
            dc.appendBracketedWithSpace(toStringCtxID(ctxID));
            dc.append(" [" + sidFirst + "," + sidLast + "]");
         }
      }
      return dc.toString();
   }
   public String toStringConfigs() {
      Dctx c = new Dctx(uc, "\n\t");
      toStringConfigs(c);
      return c.toString();
   }

   /**
    * Collects all configurations mapping to {@link ICtx}
    * @param dc
    */
   public void toStringConfigs(Dctx dc) {
      ObjectToObjects oto = new ObjectToObjects(uc, true);
      Enumeration enu = intos.getEnumeration();
      while (enu.hasMoreElements()) {
         Object o = enu.nextElement();
         if (o instanceof ICtx) {
            ICtx module = (ICtx) o;
            IConfig config = module.getConfig();
            oto.add(config, module);
         }
      }

      enu = oto.getEnumerationKeys();
      int count = 0;
      while (enu.hasMoreElements()) {
         IConfig config = (IConfig) enu.nextElement();
         Object[] values = oto.getValues(config);
         dc.appendVarWithNewLine("|--->Configuration #", ++count);
         dc.nlLvl(config);
         dc.nlLvlArray1Line(values, "Contexts configured with this config");
      }
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
               String str = strU.getNameObjectClass(module);
               return str;
            }
         }
      }
      return null;
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

   private void toStringPrivate(Dctx dc) {

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
   //#enddebug

}
