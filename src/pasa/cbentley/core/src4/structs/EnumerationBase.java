package pasa.cbentley.core.src4.structs;

import java.util.Enumeration;

import pasa.cbentley.core.src4.ctx.ObjectU;
import pasa.cbentley.core.src4.ctx.UCtx;
import pasa.cbentley.core.src4.logging.Dctx;

public class EnumerationBase extends ObjectU implements Enumeration {

   private Object[] ar;

   private int      num;

   private int      offset;

   private int      currentIndex = 0;

   public EnumerationBase(UCtx uc) {
      super(uc);
   }

   public void setArray(Object[] ar, int size) {
      this.setArray(ar, 0, size);
   }

   public void setArray(Object[] ar, int offset, int size) {
      this.ar = ar;
      this.offset = offset;
      this.num = size;
   }

   public boolean hasMoreElements() {
      return currentIndex < num;
   }

   public Object nextElement() {
      return ar[offset + currentIndex++];
   }

   //#mdebug
   public void toString(Dctx dc) {
      dc.root(this, EnumerationBase.class, 37);
      toStringPrivate(dc);
      super.toString(dc.sup());

      dc.nlArrayRaw(ar, "array");
   }

   public void toString1Line(Dctx dc) {
      dc.root1Line(this, EnumerationBase.class, 37);
      toStringPrivate(dc);
      super.toString1Line(dc.sup1Line());
   }

   private void toStringPrivate(Dctx dc) {
      dc.appendVarWithSpace("offset", offset);
      dc.appendVarWithSpace("num", num);
      dc.appendVarWithSpace("currentIndex", currentIndex);
   }
   //#enddebug

}
