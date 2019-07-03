package pasa.cbentley.core.src4.structs.synch;

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
public class MutexSignal implements IStringable {

   /**
    * Guard against spurious wakeups (in linux)
    */
   private boolean      isClosed = true;

   protected final UCtx uc;

   public MutexSignal(UCtx uc) {
      this.uc = uc;
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

   //#mdebug
   public IDLog toDLog() {
      return toStringGetUCtx().toDLog();
   }

   public String toString() {
      return Dctx.toString(this);
   }

   public void toString(Dctx dc) {
      dc.root(this, "MutexSignal");
      toStringPrivate(dc);
   }

   public String toString1Line() {
      return Dctx.toString1Line(this);
   }

   private void toStringPrivate(Dctx dc) {
      dc.appendVarWithSpace("isClosed", isClosed);
   }

   public void toString1Line(Dctx dc) {
      dc.root1Line(this, "MutexSignal");
      toStringPrivate(dc);
   }

   public UCtx toStringGetUCtx() {
      return uc;
   }

   //#enddebug

}