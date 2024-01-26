/*
 * (c) 2018-2020 Charles-Philip Bentley
 * This code is licensed under MIT license (see LICENSE.txt for details)
 */
package pasa.cbentley.core.src4.structs.synch;

import pasa.cbentley.core.src4.ctx.ObjectU;
import pasa.cbentley.core.src4.ctx.UCtx;
import pasa.cbentley.core.src4.logging.Dctx;
import pasa.cbentley.core.src4.logging.IDLog;
import pasa.cbentley.core.src4.logging.IStringable;

/**
 * Gate.
 * 
 * A thread 
 * <br>
 * @author Charles Bentley
 *
 */
public class MutexSignal extends ObjectU implements IStringable {

   /**
    * Guard against spurious wakeups (in linux)
    */
   private boolean isClosed = true;

   public MutexSignal(UCtx uc) {
      super(uc);
   }

   /**
    * Releases one thread. 1 thread will go through. other waiting thread will need to wait
    * for another signal.
    * <br>
    */
   public synchronized void release() {
      //break the while loop 
      this.isClosed = false;
      this.notify();
   }

   /**
    * Notifies all threads that previously called {@link MutexSignal#acquire()}
    * <br>
    * After this call, new calls to {@link MutexSignal#acquire()} will wait
    */
   public synchronized void releaseAll() {
      this.isClosed = false;
      this.notifyAll();
   }

   /**
    * Acquires 
    * @throws InterruptedException
    */
   public synchronized void acquire() throws InterruptedException {
      while (this.isClosed) {
         wait();
      }
      this.isClosed = true;
   }

   public synchronized void acquireTest() {
      while (this.isClosed) {
         try {
            wait();
         } catch (InterruptedException e) {
            e.printStackTrace();
         }
      }
      this.isClosed = true;
   }

   //#mdebug
   public void toString(Dctx dc) {
      dc.root(this, MutexSignal.class, 70);
      toStringPrivate(dc);
      super.toString(dc.sup());
   }

   private void toStringPrivate(Dctx dc) {
      dc.appendVarWithSpace("isClosed", isClosed);
   }

   public void toString1Line(Dctx dc) {
      dc.root1Line(this, MutexSignal.class);
      toStringPrivate(dc);
      super.toString1Line(dc.sup1Line());
   }

   //#enddebug

}