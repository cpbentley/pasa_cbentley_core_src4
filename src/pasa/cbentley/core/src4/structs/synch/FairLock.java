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
import pasa.cbentley.core.src4.structs.IntToObjects;

public class FairLock extends ObjectU implements IStringable {
   private boolean      isLocked      = false;

   private Thread       lockingThread = null;

   private IntToObjects waitingThreads;

   public FairLock(UCtx uc) {
      super(uc);
      waitingThreads = new IntToObjects(uc);
   }

   /**
    * Adds the current thread in the waiting list.
    * @throws InterruptedException
    */
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

   /**
    * Call this when running thread has locked this lock with a call to {@link FairLock#lock()}
    */
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
   public void toString(Dctx dc) {
      dc.root(this, FairLock.class, 75);
      toStringPrivate(dc);
      super.toString(dc.sup());
      dc.nlLvl(waitingThreads, "waitingThreads");

      dc.nlLvlO(lockingThread, "lockingThread");
   }

   private void toStringPrivate(Dctx dc) {
      dc.appendVarWithSpace("isLocked", isLocked);
   }

   public void toString1Line(Dctx dc) {
      dc.root1Line(this, FairLock.class);
      toStringPrivate(dc);
      super.toString1Line(dc.sup1Line());
   }

   //#enddebug

}