package pasa.cbentley.core.src4.structs.synch;

import pasa.cbentley.core.src4.ctx.UCtx;
import pasa.cbentley.core.src4.logging.Dctx;
import pasa.cbentley.core.src4.logging.IDLog;
import pasa.cbentley.core.src4.logging.IStringable;
import pasa.cbentley.core.src4.structs.IntToObjects;

public class FairLock implements IStringable {
   private boolean      isLocked      = false;

   private Thread       lockingThread = null;

   private IntToObjects waitingThreads;

   protected final UCtx uc;

   public FairLock(UCtx uc) {
      this.uc = uc;
      waitingThreads = new IntToObjects(uc);
   }

   public void lock() throws InterruptedException {
      QueueObject queueObject = new QueueObject();
      boolean isLockedForThisThread = true;
      synchronized (this) {
         waitingThreads.add(queueObject);
      }

      while (isLockedForThisThread) {
         synchronized (this) {
            isLockedForThisThread = isLocked || waitingThreads.getObjectAtIndex(0) != queueObject;
            if (!isLockedForThisThread) {
               isLocked = true;
               waitingThreads.removeRef(queueObject);
               lockingThread = Thread.currentThread();
               return;
            }
         }
         try {
            queueObject.doWait();
         } catch (InterruptedException e) {
            synchronized (this) {
               waitingThreads.removeRef(queueObject);
            }
            throw e;
         }
      }
   }

   public synchronized void unlock() {
      if (this.lockingThread != Thread.currentThread()) {
         throw new IllegalMonitorStateException("Calling thread has not locked this lock");
      }
      isLocked = false;
      lockingThread = null;
      if (waitingThreads.getLength() > 0) {
         ((QueueObject) waitingThreads.getObjectAtIndex(0)).doNotify();
      }
   }

   //#mdebug
   public IDLog toDLog() {
      return toStringGetUCtx().toDLog();
   }

   public String toString() {
      return Dctx.toString(this);
   }

   public void toString(Dctx dc) {
      dc.root(this, "FairLock");
      toStringPrivate(dc);
   }

   public String toString1Line() {
      return Dctx.toString1Line(this);
   }

   private void toStringPrivate(Dctx dc) {

   }

   public void toString1Line(Dctx dc) {
      dc.root1Line(this, "FairLock");
      toStringPrivate(dc);
   }

   public UCtx toStringGetUCtx() {
      return uc;
   }

   //#enddebug

}