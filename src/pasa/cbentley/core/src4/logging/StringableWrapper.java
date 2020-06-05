package pasa.cbentley.core.src4.logging;

import pasa.cbentley.core.src4.ctx.UCtx;

public class StringableWrapper implements IStringable {

   protected final UCtx   uc;

   protected final Object o;

   public StringableWrapper(UCtx uc, Object o) {
      this.uc = uc;
      if(o == null) {
         throw new NullPointerException();
      }
      this.o = o;
   }

   //#mdebug
   public IDLog toDLog() {
      return toStringGetUCtx().toDLog();
   }

   public String toString() {
      return Dctx.toString(this);
   }

   public void toString(Dctx dc) {
      if (o instanceof IStringable) {
         ((IStringable) o).toString(dc);
      } else {
         String str = o.toString();
         dc.append(str);
      }
   }

   public String toString1Line() {
      return Dctx.toString1Line(this);
   }

   public void toString1Line(Dctx dc) {
      dc.root1Line(this, StringableWrapper.class);
   }

   public UCtx toStringGetUCtx() {
      return uc;
   }

   //#enddebug

}
