package pasa.cbentley.core.src4.structs;

import pasa.cbentley.core.src4.ctx.UCtx;
import pasa.cbentley.core.src4.logging.Dctx;
import pasa.cbentley.core.src4.logging.IStringable;

/**
 * First in, First Out Queue.
 * <br>
 * @author Charles Bentley
 *
 */
public class FiFoQueue implements IStringable {

   //extraction point for the next get.
   int              getIndex  = 0;

   private Object[] objects   = new Object[3];

   //insertion point for the next element
   int              putIndex  = 0;

   int              tailIndex = 0;

   private Object   to;

   private UCtx     uc;

   public FiFoQueue(UCtx uc) {
      this.uc = uc;

   }

   /**
    * Pop the head, removes object from array.
    * 
    * First In Element
    * @return null if none
    */
   public Object getHead() {
      to = objects[getIndex];
      objects[getIndex] = null;
      getIndex++;
      if (getIndex == objects.length) {
         getIndex = 0;
      }
      return to;
   }

   /**
    * Returns a reference to the tail. (last in element)
    * <br>
    * null if queue is empty.
    * 
    * @return
    */
   public Object getTail() {
      return objects[tailIndex];
   }

   /**
    * Not thread safe. Synch externally
    * @param ob
    */
   public void put(Object ob) {
      if (putIndex >= size()) {
         objects = uc.getMem().increaseCapacity(objects, 1);
      }
      objects[putIndex] = ob;
      tailIndex = putIndex;
      if (putIndex++ == objects.length) {
         putIndex = 0;
      }
   }

   public int size() {
      return putIndex - getIndex;
   }

   //#mdebug
   public String toString() {
      return Dctx.toString(this);
   }

   public void toString(Dctx dc) {
      dc.root(this, "FiFoQueue");
   }

   public String toString1Line() {
      return Dctx.toString1Line(this);
   }

   public void toString1Line(Dctx dc) {
      dc.root1Line(this, "FiFoQueue");
   }

   public UCtx toStringGetUCtx() {
      return uc;
   }
   //#enddebug

}
