package pasa.cbentley.core.src4.thread;

import pasa.cbentley.core.src4.ctx.UCtx;
import pasa.cbentley.core.src4.logging.Dctx;
import pasa.cbentley.core.src4.logging.IDLog;
import pasa.cbentley.core.src4.logging.IStringable;
import pasa.cbentley.core.src4.structs.FiFoQueue;

/**
 * Simple worker thread. Executes work tasks from a {@link FiFoQueue}.
 * <br>
 * It starts automatically in the constructor.
 * @author Charles Bentley
 *
 */
public class WorkerThread extends Thread implements IStringable {

   private volatile boolean isRunning = false;

   private FiFoQueue        queue;

   private final UCtx       uc;

   private int              workNum   = 0;

   public WorkerThread(UCtx uc) {
      this(uc,"WorkerThread");
   }
   
   /**
    * Start the thread and listens to incoming {@link Runnable}
    * @param uc
    * @param threadName
    */
   public WorkerThread(UCtx uc, String threadName) {
      super(threadName);
      this.uc = uc;
      queue = new FiFoQueue(uc);
      this.start();
   }
   /**
    * 
    * @param r
    * @throws NullPointerException if r is null
    */
   public void addToQueue(Runnable r) {
      if(r == null) {
         throw new NullPointerException();
      }
      synchronized (queue) {
         queue.put(r);
         queue.notifyAll();
      }
   }

   public void run() {
      while (true) {
         Runnable r = null;
         synchronized (queue) {
            if (queue.size() == 0) {
               try {
                  queue.wait();
               } catch (InterruptedException e) {
                  e.printStackTrace();
               }
            } else {
               workNum++;
               r = (Runnable) queue.getHead();
            }
         }
         if (r != null) {
            isRunning = true;
            try {
               r.run();
            } catch (Exception e) {
               e.printStackTrace();
            }
            isRunning = false;
         } else {
            //#debug
            toDLog().pNull("Runnable is null.. Maybe because of a spurious wake up", this, WorkerThread.class, "run", LVL_05_FINE, true);
         }
      }
   }

   public int size() {
      return queue.size();
   }

   //#mdebug
   public IDLog toDLog() {
      return toStringGetUCtx().toDLog();
   }

   public String toString() {
      return Dctx.toString(this);
   }

   public void toString(Dctx dc) {
      dc.root(this, "WorkerThread");
      toStringPrivate(dc);
   }

   public String toString1Line() {
      return Dctx.toString1Line(this);
   }

   private void toStringPrivate(Dctx dc) {

   }

   public void toString1Line(Dctx dc) {
      dc.root1Line(this, "WorkerThread");
      toStringPrivate(dc);
   }

   public UCtx toStringGetUCtx() {
      return uc;
   }

   //#enddebug

}
