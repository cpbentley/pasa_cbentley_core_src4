package pasa.cbentley.core.src4.strings;

import pasa.cbentley.core.src4.ctx.UCtx;
import pasa.cbentley.core.src4.interfaces.IStrAcceptor;
import pasa.cbentley.core.src4.logging.Dctx;

/**
 * true when stringEtalon contains stringVariable
 * 
 * @author Charles Bentley
 *
 */
public class StrAcceptorContains extends StrAcceptorAbstract implements IStrAcceptor {

   public StrAcceptorContains(UCtx uc, String stringEtalon) {
      super(uc,stringEtalon);
   }

   public boolean isStringAccepted(String str) {
      return stringEtalon.indexOf(str) != -1;
   }
   
   //#mdebug
   public void toString(Dctx dc) {
      dc.root(this, "StrAcceptorContains");
      toStringPrivate(dc);
      super.toString(dc.sup());
   }

   private void toStringPrivate(Dctx dc) {

   }

   public void toString1Line(Dctx dc) {
      dc.root1Line(this, "StrAcceptorContains");
      toStringPrivate(dc);
      super.toString1Line(dc.sup1Line());
   }
   //#enddebug
   

}
