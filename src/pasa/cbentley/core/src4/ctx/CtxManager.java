package pasa.cbentley.core.src4.ctx;

import pasa.cbentley.core.src4.io.BADataIS;
import pasa.cbentley.core.src4.io.BADataOS;
import pasa.cbentley.core.src4.logging.Dctx;
import pasa.cbentley.core.src4.logging.IDLog;
import pasa.cbentley.core.src4.logging.IStringable;
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
 * @author Charles Bentley
 *
 */
public class CtxManager implements IStringable {

   private IntToObjects intos;

   private UCtx         uc;

   public CtxManager(UCtx uc) {
      this.uc = uc;
      intos = new IntToObjects(uc);
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
      intos.add(ctx);
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
   public void registerStaticID(ICtx ctx, int keyID) {
      // TODO Auto-generated method stub

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

   }

   /**
    * We keep the.
    * What if a module is already loaded? Can we call this method?
    * 
    * @param dis
    */
   public void stateRead(BADataIS dis) {
      int num = dis.readInt();
      for (int i = 0; i < num; i++) {
         int val = dis.readInt();
         if (val == 1) {
            int moduleID = dis.readInt();
            byte[] data = dis.readByteArray();
            //avoid corrupted data
            int index = intos.findInt(moduleID);
            if (index == -1) {
               intos.add(data, moduleID);
            } else {
               ICtx mod = (ICtx) intos.getObjectAtIndex(index);
               mod.setSettings(data);
            }
         }
      }
      //#debug
      uc.toDLog().pInit("Count=" + num, this, CtxManager.class, "settingsRead");
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
         int moduleID = intos.ints[i];
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
            dos.writeInt(moduleID);
            dos.writeByteArray(data);
            //#debug
            toDLog().pInit(data.length + " bytes of data for " + moduleID, null, CtxManager.class, "settingsWrite");
         } else {
            //#debug
            toDLog().pInit("No data for " + moduleID, null, CtxManager.class, "settingsWrite");
            dos.writeInt(0);
         }
      }
      //#debug
      toDLog().pInit("Count=" + size, this, CtxManager.class, "settingsWrite");
   }

   public IDLog toDLog() {
      return uc.toDLog();
   }

   //#mdebug
   public String toString() {
      return Dctx.toString(this);
   }

   public void toString(Dctx dc) {
      dc.root(this, "CtxManager");
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
