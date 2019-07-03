package pasa.cbentley.core.src4.helpers;

import pasa.cbentley.core.src4.ctx.UCtx;
import pasa.cbentley.core.src4.logging.Dctx;
import pasa.cbentley.core.src4.logging.IDLog;
import pasa.cbentley.core.src4.logging.IStringable;

public class CounterInt implements IStringable {

   private int  value;

   private UCtx uc;

   public CounterInt(UCtx uc, int value) {
      this.uc = uc;
      this.value = value;
   }

   public void increment() {
      value++;
   }

   public int getCount() {
      return value;
   }

   //#mdebug
   public IDLog toDLog() {
      return toStringGetUCtx().toDLog();
   }

   public String toString() {
      return Dctx.toString(this);
   }

   public void toString(Dctx dc) {
      dc.root(this, "CounterInt");
      toStringPrivate(dc);
   }

   public String toString1Line() {
      return Dctx.toString1Line(this);
   }

   private void toStringPrivate(Dctx dc) {
      dc.appendVarWithSpace("counter", value);
   }

   public void toString1Line(Dctx dc) {
      dc.root1Line(this, "CounterInt");
      toStringPrivate(dc);
   }

   public UCtx toStringGetUCtx() {
      return uc;
   }

   //#enddebug

}
