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
import pasa.cbentley.core.src4.stator.IStatorable;
import pasa.cbentley.core.src4.stator.StatorReader;
import pasa.cbentley.core.src4.stator.StatorWriter;
import pasa.cbentley.core.src4.structs.IntBuffer;
import pasa.cbentley.core.src4.structs.IntToObjects;

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
public class CtxManager implements IStringable, IStatorable {

   /**
    * Number of static known ids.
    * <br>
    * <br>
    * Afterwards
    */
   public static final int STATIC_DEFINED = 10;

   private IntBuffer       ids;

   private IntToObjects    intos;

   private UCtx            uc;

   public CtxManager(UCtx uc) {
      this.uc = uc;
      intos = new IntToObjects(uc);
      ids = new IntBuffer(uc, 5);
   }

   /**
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
    * @param ctx
    * @return
    */
   public int registerCtx(ICtx ctx) {
      //#debug
      uc.toStrDebugNullCheck(ctx, uc);

      if (intos.hasObject(ctx)) {
         throw new IllegalArgumentException("Same context reference is already registered");
      }
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
      intos.add(ctx, ctxID);
      return intos.nextempty;
   }

   /**
    * Registers a framework known StaticID slot
    * <br>
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
    * Checks for conflicts with established ranges.
    * <br>
    * Throws a {@link RuntimeException} when a conflict is found. Dev must 
    * fix the static range manually.
    * <br>
    * <br>
    * Register a range of integers for a static class.
    * <br>
    * <br>
    * For example, String IDs. The module at the top created a static id class
    * using {@link IStaticObjCtrl#createNewStaticID()}.
    * <br>
    * 
    * @param staticID the class id created with {@link IStaticObjCtrl#createNewStaticID()}
    * @param first
    * @param last
    * @throws new {@link RuntimeException} when conflict of range
    */
   public void registerStaticRange(ICtx ctx, int staticID, int first, int last) {
      int[] r = ids.getIntsRef();
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
               int index = intos.findInt(ctxID);
               if (index == -1) {
                  intos.add(data, ctxID);
               } else {
                  ICtx mod = (ICtx) intos.getObjectAtIndex(index);
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

   public void unRegisterCtx(ICtx ctx) {
      intos.removeRef(ctx);
   }

   public void stateReadFrom(StatorReader state) {
      if (state.hasData()) {
         stateRead(state.getDataReader());
      } else {
         //#debug
         toDLog().pData("No Ctx State Data", state, CtxManager.class, "stateReadFrom", LVL_05_FINE, true);
      }
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
    * Write state of Module Settings
    * @param dos
    */
   public void stateWrite(BADataOS dos) {
      //write the number
      int size = intos.nextempty;
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
            toDLog().pData(data.length + " bytes of data for ctxID" + ctxID, null, CtxManager.class, "settingsWrite");
         } else {
            //#debug
            toDLog().pData("No data for ctxID " + ctxID , null, CtxManager.class, "settingsWrite");
            dos.writeInt(0);
         }
      }
      //#debug
      toDLog().pData("Count=" + size, this, CtxManager.class, "settingsWrite");
   }

   public void stateWriteTo(StatorWriter state) {
      stateWrite(state.getDataWriter());
   }

   //#mdebug
   public IDLog toDLog() {
      return uc.toDLog();
   }

   public String toString() {
      return Dctx.toString(this);
   }

   public void toString(Dctx dc) {
      dc.root(this, "CtxManager");
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
         int modID = r[i];
         int sID = r[i + 1];
         int sidFirst = r[i + 2];
         int sidLast = r[i + 3];
         dc.nl();
         dc.appendVar("ModuleID", modID);
         dc.appendVarWithSpace("sid", sID);
         dc.append(" [" + sidFirst + "," + sidLast + "]");
      }
   }

   public String toString1Line() {
      return Dctx.toString1Line(this);
   }

   public void toString1Line(Dctx dc) {
      dc.root1Line(this, "CtxManager");
   }

   public UCtx toStringGetUCtx() {
      return uc;
   }
   //#enddebug

}
