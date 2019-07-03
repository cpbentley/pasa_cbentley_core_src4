package pasa.cbentley.core.src4.strings;

import pasa.cbentley.core.src4.ctx.UCtx;
import pasa.cbentley.core.src4.interfaces.IStrAcceptor;
import pasa.cbentley.core.src4.logging.Dctx;

/**
 * true when stringEtalon is contained in stringVariable
 * 
 * @author Charles Bentley
 *
 */
public class StrAcceptorContained extends StrAcceptorAbstract implements IStrAcceptor {


   public StrAcceptorContained(UCtx uc, String stringEtalon) {
      super(uc,stringEtalon);
   }

   public boolean isStringAccepted(String str) {
      return str.contains(stringEtalon); //contained
   }
   
   //#mdebug
   public void toString(Dctx dc) {
      dc.root(this, "StrAcceptorContained");
      toStringPrivate(dc);
      super.toString(dc.sup());
   }

   private void toStringPrivate(Dctx dc) {

   }

   public void toString1Line(Dctx dc) {
      dc.root1Line(this, "StrAcceptorContained");
      toStringPrivate(dc);
      super.toString1Line(dc.sup1Line());
   }

   //#enddebug
   

}
