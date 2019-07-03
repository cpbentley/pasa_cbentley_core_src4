package pasa.cbentley.core.src4.strings;

import pasa.cbentley.core.src4.ctx.UCtx;
import pasa.cbentley.core.src4.interfaces.IStrAcceptor;
import pasa.cbentley.core.src4.logging.Dctx;
import pasa.cbentley.core.src4.logging.IDLog;
import pasa.cbentley.core.src4.logging.IStringable;

public abstract class StrAcceptorAbstract implements IStringable, IStrAcceptor {

   protected final UCtx   uc;

   protected final String stringEtalon;

   public String getStringEtalon() {
      return stringEtalon;
   }

   public StrAcceptorAbstract(UCtx uc, String stringEtalon) {
      this.uc = uc;
      this.stringEtalon = stringEtalon;
   }

   //#mdebug
   public IDLog toDLog() {
      return toStringGetUCtx().toDLog();
   }

   public String toString() {
      return Dctx.toString(this);
   }

   public void toString(Dctx dc) {
      dc.root(this, "StrAcceptorAbstract");
      toStringPrivate(dc);
   }

   public String toString1Line() {
      return Dctx.toString1Line(this);
   }

   private void toStringPrivate(Dctx dc) {
      dc.appendVarWithSpace("stringEtalon", stringEtalon);
   }

   public void toString1Line(Dctx dc) {
      dc.root1Line(this, "StrAcceptorAbstract");
      toStringPrivate(dc);
   }

   public UCtx toStringGetUCtx() {
      return uc;
   }

   //#enddebug

}
