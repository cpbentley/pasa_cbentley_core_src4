package pasa.cbentley.core.src4.structs.synch;

import pasa.cbentley.core.src4.ctx.UCtx;
import pasa.cbentley.core.src4.logging.Dctx;
import pasa.cbentley.core.src4.logging.IDLog;
import pasa.cbentley.core.src4.logging.IStringable;
import pasa.cbentley.core.src4.structs.FiFoQueue;

public class BlockingQueueUnlimited implements IStringable {

   private FiFoQueue queue;

   /**
    * 
    */
   private Object    last;

   protected final UCtx uc;

   public BlockingQueueUnlimited(UCtx uc) {
      this.uc = uc;
      queue = new FiFoQueue(uc);
   }

   public Object getLast() {
      return last;
   }

   public synchronized void enqueue(Object item) {
      if (this.queue.size() == 0) {
         notifyAll();
      }
      last = item;
      this.queue.put(item);
   }

   public synchronized Object dequeue() throws InterruptedException {
      while (this.queue.size() == 0) {
         wait();
      }
      return this.queue.getHead();
   }

   public synchronized Object getTail() {
      return this.queue.getTail();
   }
   
   //#mdebug
   public IDLog toDLog() {
      return toStringGetUCtx().toDLog();
   }

   public String toString() {
      return Dctx.toString(this);
   }

   public void toString(Dctx dc) {
      dc.root(this, "BlockingQueueUnlimited");
      toStringPrivate(dc);
   }

   public String toString1Line() {
      return Dctx.toString1Line(this);
   }

   private void toStringPrivate(Dctx dc) {

   }

   public void toString1Line(Dctx dc) {
      dc.root1Line(this, "BlockingQueueUnlimited");
      toStringPrivate(dc);
   }

   public UCtx toStringGetUCtx() {
      return uc;
   }

   //#enddebug
   

}
