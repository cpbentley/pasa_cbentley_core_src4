/*
 * (c) 2018-2020 Charles-Philip Bentley
 * This code is licensed under MIT license (see LICENSE.txt for details)
 */
package pasa.cbentley.core.src4.structs.synch;

import pasa.cbentley.core.src4.ctx.ObjectU;
import pasa.cbentley.core.src4.ctx.UCtx;
import pasa.cbentley.core.src4.logging.Dctx;

/**
 * 
 * @author Charles Bentley
 *
 */
public class MutexGuardedVolatile extends ObjectU {

   /**
    * from a memory visibility perspective, 
    * writing to a volatile variable is like exiting a synchronized block.
    * reading a volatile variable is like entering a synchronized block
    * 
    */
   private volatile boolean hasThreadWaiting = false;

   /**
    * Guard against spurious wakeups (in linux)
    */
   private boolean          isClosed         = true;

   public MutexGuardedVolatile(UCtx uc) {
      super(uc);
   }

   /**
    * 
    * @throws InterruptedException
    */
   public void acquire() throws InterruptedException {
      hasThreadWaiting = true;
      synchronized (this) {
         while (this.isClosed) {
            wait();
         }
         this.isClosed = true;
      }
   }

   public void releaseAll() {
      //check if at least one waiter
      if (hasThreadWaiting) {
         //notify waiters
         //synchronize on the structure holding the waiters
         synchronized (this) {
            this.isClosed = false;
            //signals waiting threads that the gate has opened
            this.notifyAll();
            //set guard flag to false
            hasThreadWaiting = false;
         }
      }
   }

   //#mdebug
   public void toString(Dctx dc) {
      dc.root(this, MutexGuardedVolatile.class, 55);
      toStringPrivate(dc);
      super.toString(dc.sup());
   }

   public void toString1Line(Dctx dc) {
      dc.root1Line(this, MutexGuardedVolatile.class);
      toStringPrivate(dc);
      super.toString1Line(dc.sup1Line());
   }

   private void toStringPrivate(Dctx dc) {
      dc.appendVarWithSpace("hasThreadWainting", hasThreadWaiting);
   }

   //#enddebug

}
