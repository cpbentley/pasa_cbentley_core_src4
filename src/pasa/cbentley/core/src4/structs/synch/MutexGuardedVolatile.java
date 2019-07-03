package pasa.cbentley.core.src4.structs.synch;

import pasa.cbentley.core.src4.ctx.UCtx;
import pasa.cbentley.core.src4.logging.Dctx;

/**
 * 
 * @author Charles Bentley
 *
 */
public class MutexGuardedVolatile extends MutexSignal {

   public MutexGuardedVolatile(UCtx uc) {
      super(uc);
   }

   private volatile boolean hasThreadWainting = false;

   public void releaseAll() {
      //check if at least one waiter
      if (hasThreadWainting) {
         //notify waiters
         //synchronize on the structure holding the waiters
         synchronized (this) {
            //signals waiting threads that the gate has opened
            this.releaseAll();
            //set guard flag to false
            hasThreadWainting = false;
         }
      }
   }

   /**
    * 
    * @throws InterruptedException
    */
   public void acquireGuarded() throws InterruptedException {
      if (hasThreadWainting) {
         acquire();
      }
   }
   
   //#mdebug
   public void toString(Dctx dc) {
      dc.root(this, "MutexGuardedVolatile");
      toStringPrivate(dc);
      super.toString(dc.sup());
   }

   private void toStringPrivate(Dctx dc) {
      dc.appendVarWithSpace("hasThreadWainting", hasThreadWainting);
   }

   public void toString1Line(Dctx dc) {
      dc.root1Line(this, "MutexGuardedVolatile");
      toStringPrivate(dc);
      super.toString1Line(dc.sup1Line());
   }

   //#enddebug
   

}
