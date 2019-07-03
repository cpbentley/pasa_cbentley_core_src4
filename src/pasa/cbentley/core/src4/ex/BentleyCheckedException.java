package pasa.cbentley.core.src4.ex;

import pasa.cbentley.core.src4.ctx.UCtx;
import pasa.cbentley.core.src4.logging.Dctx;
import pasa.cbentley.core.src4.logging.IStringable;

/**
 * Src_4 midp compatible.
 * <br>
 * @author Charles Bentley
 *
 */
public class BentleyCheckedException extends Exception implements IStringable {
  
   /**
    * 
    */
   private Throwable e;

   private UCtx      uc;

   /**
    * Nest an exception inside
    * @param e
    */
   public BentleyCheckedException(UCtx uc, Exception e) {
      this(uc, "", e);
   }

   public BentleyCheckedException(UCtx uc, String message) {
      this(uc,message,null);
   }

   public BentleyCheckedException(UCtx uc, String message, Throwable cause) {
      super(message);
      this.uc = uc;
      this.e = cause;
   }

   public Throwable getCause() {
      return e;
   }
   
   //#mdebug
   public String toString() {
      return Dctx.toString(this);
   }

   public void toString(Dctx dc) {
      dc.root(this, "BentleyCheckedException");
   }

   public UCtx toStringGetUCtx() {
      return uc;
   }

   public String toString1Line() {
      return Dctx.toString1Line(this);
   }

   public void toString1Line(Dctx dc) {
      dc.root1Line(this, "BentleyCheckedException");
   }
   //#enddebug
   
   


}
