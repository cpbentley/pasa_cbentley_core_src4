package pasa.cbentley.core.src4.structs.synch;

import pasa.cbentley.core.src4.ctx.UCtx;
import pasa.cbentley.core.src4.logging.Dctx;
import pasa.cbentley.core.src4.logging.IStringable;
import pasa.cbentley.core.src4.structs.FiFoQueue;

/**
 * 
 * Queue using {@link FiFoQueue}.
 * 
 * @author Charles Bentley
 *
 */
public class BlockingQueue implements IStringable {

   private final FiFoQueue queue;

   private int             limit = 10;

   private final UCtx      uc;

   public BlockingQueue(UCtx uc, int limit) {
      this.uc = uc;
      queue = new FiFoQueue(uc);
      this.limit = limit;
   }

   public synchronized void enqueue(Object item) throws InterruptedException {
      while (this.queue.size() == this.limit) {
         wait();
      }
      if (this.queue.size() == 0) {
         notifyAll();
      }
      this.queue.put(item);
   }

   public synchronized Object dequeue() throws InterruptedException {
      while (this.queue.size() == 0) {
         wait();
      }
      if (this.queue.size() == this.limit) {
         notifyAll();
      }

      return this.queue.getHead();
   }

   
   //#mdebug
   public String toString() {
      return Dctx.toString(this);
   }

   public void toString(Dctx dc) {
      dc.root(this, "BlockingQueue");
   }

   public UCtx toStringGetUCtx() {
      return uc;
   }

   public String toString1Line() {
      return Dctx.toString1Line(this);
   }

   public void toString1Line(Dctx dc) {
      dc.root1Line(this, "BlockingQueue");
   }

   //#enddebug
   
   

}
