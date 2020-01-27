package pasa.cbentley.core.src4.structs;

import pasa.cbentley.core.src4.ctx.UCtx;
import pasa.cbentley.core.src4.logging.Dctx;
import pasa.cbentley.core.src4.logging.IDLog;
import pasa.cbentley.core.src4.logging.IStringable;

/**
 * Thread safe for one writer, several readers.
 * <br>
 * @author Charles Bentley
 *
 */
public class CircularInts implements IStringable {

   /**
    * no need for volatile because min won't change after constructor call.
    */
   private int          min;

   /**
    * no need for volatile because max won't change after constructor call.
    */
   private int          max;

   /**
    * So that the Gui thread reads the current value always
    */
   private volatile int current;

   protected final UCtx uc;

   public CircularInts(UCtx uc, int min, int max) {
      this.uc = uc;
      this.min = min;
      this.max = max;
      this.current = min;
   }

   public int getValue() {
      return current;
   }

   public int getMin() {
      return min;
   }

   public int getMax() {
      return max;
   }

   /**
    * set to min when smaller than min
    * set to max when bigger than max
    * @param cur
    */
   public void setCurrent(int cur) {
      if (cur < min) {
         cur = min;
      }
      if (cur > max) {
         cur = max;
      }
      current = cur;
   }

   public int next() {
      if (current == max) {
         current = min;
      } else {
         current++;
      }
      return current;
   }

   public int previous() {
      if (current == min) {
         current = max;
      } else {
         current--;
      }
      return current;
   }
   
   //#mdebug
   public IDLog toDLog() {
      return toStringGetUCtx().toDLog();
   }

   public String toString() {
      return Dctx.toString(this);
   }

   public void toString(Dctx dc) {
      dc.root(this, "CircularInts");
      toStringPrivate(dc);
   }

   public String toString1Line() {
      return Dctx.toString1Line(this);
   }

   private void toStringPrivate(Dctx dc) {

   }

   public void toString1Line(Dctx dc) {
      dc.root1Line(this, "CircularInts");
      toStringPrivate(dc);
   }

   public UCtx toStringGetUCtx() {
      return uc;
   }

   //#enddebug
   


}
