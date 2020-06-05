/*
 * (c) 2018-2020 Charles-Philip Bentley
 * This code is licensed under MIT license (see LICENSE.txt for details)
 */
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
public class BentleyRunException extends RuntimeException implements IStringable {
  
   /**
    * 
    */
   private Throwable e;

   private UCtx      uc;

   /**
    * Nest an exception inside
    * @param e
    */
   public BentleyRunException(UCtx uc, Exception e) {
      this(uc, "", e);
   }

   public BentleyRunException(UCtx uc, String message) {
      this(uc,message,null);
   }

   public BentleyRunException(UCtx uc, String message, Throwable cause) {
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
      dc.root(this, "BentleyRunException");
   }

   public UCtx toStringGetUCtx() {
      return uc;
   }

   public String toString1Line() {
      return Dctx.toString1Line(this);
   }

   public void toString1Line(Dctx dc) {
      dc.root1Line(this, "BentleyRunException");
   }
   //#enddebug
   
   


}
