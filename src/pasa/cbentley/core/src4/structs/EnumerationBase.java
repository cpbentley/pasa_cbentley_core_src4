package pasa.cbentley.core.src4.structs;

import java.util.Enumeration;

import pasa.cbentley.core.src4.ctx.ObjectU;
import pasa.cbentley.core.src4.ctx.UCtx;
import pasa.cbentley.core.src4.logging.Dctx;

public class EnumerationBase extends ObjectU implements Enumeration {

   private Object[] ar;

   private int      num;

   private int      currentIndex = 0;

   public EnumerationBase(UCtx uc) {
      super(uc);
   }

   public void setArray(Object[] ar, int size) {
      this.ar = ar;
      this.num = size;
   }

   public boolean hasMoreElements() {
      return currentIndex < num;
   }

   public Object nextElement() {
      return ar[currentIndex++];
   }

   //#mdebug
   public void toString(Dctx dc) {
      dc.root(this, EnumerationBase.class, "@line5");
      toStringPrivate(dc);
      super.toString(dc.sup());
   }

   private void toStringPrivate(Dctx dc) {

   }

   public void toString1Line(Dctx dc) {
      dc.root1Line(this, EnumerationBase.class);
      toStringPrivate(dc);
      super.toString1Line(dc.sup1Line());
   }

   //#enddebug

}
