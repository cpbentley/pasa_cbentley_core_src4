package pasa.cbentley.core.src4.ctx;

import pasa.cbentley.core.src4.io.BADataIS;
import pasa.cbentley.core.src4.io.BADataOS;
import pasa.cbentley.core.src4.logging.Dctx;
import pasa.cbentley.core.src4.logging.IDLog;
import pasa.cbentley.core.src4.stator.IStatorable;
import pasa.cbentley.core.src4.stator.Stator;
import pasa.cbentley.core.src4.stator.StatorReader;
import pasa.cbentley.core.src4.stator.StatorWriter;
import pasa.cbentley.core.src4.structs.IntToObjects;

/**
 * 
 * @author Charles Bentley
 *
 */
public class CtxStator implements IStatorable {

   private IntToObjects intos;

   protected final UCtx uc;

   public CtxStator(UCtx uc) {
      this.uc = uc;
   }

   public void addCtx(ICtx ctx) {
      int ctxID = ctx.getCtxID();
      intos.add(ctx, ctxID);
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
      uc.toDLog().pInit("Count=" + num, this, CtxManager.class, "settingsRead");
   }

   public void stateReadFrom(Stator state) {
      //byte position 
      stateRead(state.getDataReader());
   }

   public void stateReadFrom(StatorReader state) {
      // TODO Auto-generated method stub

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
            toDLog().pInit(data.length + " bytes of data for ctxID" + ctxID, null, CtxManager.class, "settingsWrite");
         } else {
            //#debug
            toDLog().pInit("No data for ctxID " + ctxID, null, CtxManager.class, "settingsWrite");
            dos.writeInt(0);
         }
      }
      //#debug
      toDLog().pInit("Count=" + size, this, CtxManager.class, "settingsWrite");
   }

   public void stateWriteTo(Stator state) {
      stateWrite(state.getDataWriter());
   }

   public void stateWriteTo(StatorWriter state) {
      // TODO Auto-generated method stub

   }

   //#mdebug
   public IDLog toDLog() {
      return toStringGetUCtx().toDLog();
   }

   public String toString() {
      return Dctx.toString(this);
   }

   public void toString(Dctx dc) {
      dc.root(this, "CtxStator");
      toStringPrivate(dc);
   }

   public String toString1Line() {
      return Dctx.toString1Line(this);
   }

   public void toString1Line(Dctx dc) {
      dc.root1Line(this, "CtxStator");
      toStringPrivate(dc);
   }

   public UCtx toStringGetUCtx() {
      return uc;
   }

   private void toStringPrivate(Dctx dc) {

   }

   //#enddebug

}
