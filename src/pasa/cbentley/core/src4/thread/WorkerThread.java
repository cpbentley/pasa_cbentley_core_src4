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

   private boolean    isRunning = false;

   private FiFoQueue  queue;

   private final UCtx uc;

   private int        workNum   = 0;

   public WorkerThread(UCtx uc) {
      super("WorkerThread");
      this.uc = uc;
      queue = new FiFoQueue(uc);
      this.start();
   }

   /**
    * 
    * @param r
    */
   public void addToQueue(Runnable r) {
      synchronized (queue) {
         queue.put(r);
         queue.notifyAll();
      }
   }

   public void run() {
      while (true) {
         synchronized (queue) {
            if (queue.size() == 0) {
               try {
                  queue.wait();
               } catch (InterruptedException e) {
                  e.printStackTrace();
               }
            } else {
               workNum++;
               Runnable r = (Runnable) queue.getHead();
               if (r != null) {
                  isRunning = true;
                  try {
                     r.run();
                  } catch (Exception e) {
                     e.printStackTrace();
                  }
                  isRunning = false;
               } else {
                  //SystemLog
               }

            }
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
