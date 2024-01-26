package pasa.cbentley.core.src4.stator;

import pasa.cbentley.core.src4.ctx.ICtx;
import pasa.cbentley.core.src4.ctx.ObjectU;
import pasa.cbentley.core.src4.ctx.UCtx;
import pasa.cbentley.core.src4.logging.Dctx;

public class StatorFactoryUC extends ObjectU implements IStatorFactory, ITechStatorableUC {

   public StatorFactoryUC(UCtx uc) {
      super(uc);
   }

   public Object[] createArray(int classID, int size) {
      switch (classID) {
         case CLASSID_001_CTX:
            break;

         default:
      }
      return null;
   }

   public Object createObject(StatorReader reader, int classID) {
      switch (classID) {
         case CLASSID_001_CTX:
            break;

         default:
      }
      return null;
   }

   public ICtx getCtx() {
      return uc;
   }

   public boolean isSupported(IStatorable statorable) {
      int id = statorable.getStatorableClassID();
      switch (id) {
         case CLASSID_001_CTX:
            break;

         default:
      }
      return false;
   }

   //#mdebug
   public void toString(Dctx dc) {
      dc.root(this, StatorFactoryUC.class, 14);
      toStringPrivate(dc);
      super.toString(dc.sup());
   }

   public void toString1Line(Dctx dc) {
      dc.root1Line(this, StatorFactoryUC.class);
      toStringPrivate(dc);
      super.toString1Line(dc.sup1Line());
   }

   private void toStringPrivate(Dctx dc) {

   }

   //#enddebug

}
