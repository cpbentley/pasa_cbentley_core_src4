/*
 * (c) 2018-2020 Charles-Philip Bentley
 * This code is licensed under MIT license (see LICENSE.txt for details)
 */
package pasa.cbentley.core.src4.structs;

import pasa.cbentley.core.src4.ctx.UCtx;
import pasa.cbentley.core.src4.logging.Dctx;
import pasa.cbentley.core.src4.logging.IStringable;

/**
 * Infinite addTo.. clears of old value automatically.
 * <br>
 * Comes in handy.
 * <br>
 * @author Charles Bentley
 *
 */
public class CircularObjects implements IStringable {

   private Object[] ar;

   private int      nextFreePosition = 0;

   private int      nonNulls         = 0;

   private UCtx     uc;

   public CircularObjects(UCtx uc, int size) {
      this.uc = uc;
      ar = new Object[size];
   }

   public void addObject(Object i) {
      addTo(i, nextFreePosition);
      if (nextFreePosition + 1 == ar.length) {
         nextFreePosition = 0;
      } else {
         nextFreePosition++;
      }
   }

   private void addTo(Object i, int nextFreePosition) {
      if (ar[nextFreePosition] == null) {
         if (i != null) {
            nonNulls++;
         }
      } else {
         if (i == null) {
            nonNulls--;
         }
      }
      ar[nextFreePosition] = i;
   }

   public void clear() {
      nonNulls = 0;
      for (int i = 0; i < ar.length; i++) {
         ar[i] = null;
      }
   }

   public int getSizeNonNulls() {
      return nonNulls;
   }

   /**
    * Returns not null objects. 
    * <br>
    * <br>
    * 1st is the last added.
    * <br>
    * Last is the oldest.
    * @return
    */
   public Object[] getObjectsNotNullNewestFirst() {
      Object[] os = new Object[nonNulls];
      int count = 0;
      for (int i = 0; i < ar.length; i++) {
         int index = nextFreePosition - 1 - i;
         if (index < 0) {
            index = ar.length + index;
         }
         Object o = ar[index];
         if (o != null) {
            os[count] = o;
            count++;
         }
      }
      return os;
   }

   /**
    * 
    * @param last 0 r
    * @return
    */
   public Object getLast(int last) {
      if (last <= 0 || last >= ar.length)
         return ar[nextFreePosition];
      if (nextFreePosition >= last) {
         return ar[nextFreePosition - last];
      } else {
         return ar[ar.length - last + nextFreePosition];
      }
   }

   /**
    * Replace the last object set
    * @param ob
    */
   public void replaceFirst(Object ob) {
      int v;
      if (nextFreePosition == 0) {
         v = ar.length - 1;
      } else {
         v = nextFreePosition - 1;
      }
      addTo(ob, v);
   }

   //#mdebug
   public String toString() {
      return Dctx.toString(this);
   }

   public void toString(Dctx dc) {
      dc.root(this, "CircularObjects");
      dc.appendVarWithSpace("nextFreePosition", nextFreePosition);
      dc.appendVarWithSpace("nonNulls", nonNulls);
      dc.nlLvlArray1Line(ar, 0, ar.length, "");
   }

   public UCtx toStringGetUCtx() {
      return uc;
   }

   public String toString1Line() {
      return Dctx.toString1Line(this);
   }

   public void toString1Line(Dctx dc) {
      dc.root1Line(this, "CircularObjects");
      dc.appendVarWithSpace("nextFreePosition", nextFreePosition);
      dc.appendVarWithSpace("nonNulls", nonNulls);
   }
   //#enddebug

}
