package pasa.cbentley.core.src4.structs;

import java.util.Enumeration;

import pasa.cbentley.core.src4.ctx.ObjectU;
import pasa.cbentley.core.src4.ctx.UCtx;
import pasa.cbentley.core.src4.helpers.StringBBuilder;
import pasa.cbentley.core.src4.logging.Dctx;
import pasa.cbentley.core.src4.utils.ArrayUtils;

/**
 * ASC sorted {@link IntInterval} collection
 * @author Charles Bentley
 *
 */
public class IntIntervals extends ObjectU {

   private IntInterval[] intervals;

   private int           numIntervals = 0;

   public IntIntervals(UCtx uc) {
      super(uc);
      intervals = new IntInterval[2];
   }

   public int getSize() {
      return numIntervals;
   }

   public Enumeration enumerates() {
      EnumerationBase eb = new EnumerationBase(uc);
      eb.setArray(intervals, numIntervals);
      return eb;
   }

   public int[] getArrayOffsetLen() {
      int[] ar = new int[numIntervals * 2];
      int count = 0;
      for (int i = 0; i < numIntervals; i++) {
         ar[count++] = intervals[i].getOffset();
         ar[count++] = intervals[i].getLen();
      }
      return ar;
   }

   public IntInterval addInterval(int offset, int len) {

      IntInterval si = new IntInterval(uc);
      si.setOffset(offset);
      si.setLen(len);

      int insertionIndex = 0;
      IntInterval mergeFront = null;
      for (int i = 0; i < numIntervals; i++) {
         IntInterval interval = intervals[i];
         if (offset <= interval.getOffset()) {
            insertionIndex = i;
            //at this stage check if merge with previous
            break;
         }
         insertionIndex++;
      }
      
      //check result with merge of element immediately below
      if(insertionIndex > 0) {
         IntInterval intervalPrev = intervals[insertionIndex-1];
         if(intervalPrev.isIntersect(si)) {
            mergeFront = intervalPrev;
            insertionIndex--;
         }
      }
      
      if(mergeFront == null) {
         intervals = ensureCapacity(intervals, numIntervals + 1);
         //shift at index
         ArrayUtils.shiftUp(intervals, 1, insertionIndex, numIntervals - 1, false);
         intervals[insertionIndex] = si;
         numIntervals++;
      } else {
         mergeFront.mergeFromBelowOf(si);
         intervals[insertionIndex] = si;
      }
      
      //merge end
      int removeNum = 0;
      for (int i = insertionIndex + 1; i < numIntervals; i++) {
         IntInterval iiAbove = intervals[i];
         boolean intersect = si.isIntersect(iiAbove);
         if (intersect) {
            //remove it
            removeNum++;
            //modify si to include it
            iiAbove.mergeFromAboveOf(si);
         } else {
            //escape by construction
            break;
         }
      }
      if (removeNum != 0) {
         int start = insertionIndex + removeNum + 1;
         int end = numIntervals - 1;
         ArrayUtils.shiftDown(intervals, removeNum, start, end, false);
         numIntervals = numIntervals - removeNum;
      }
      
      return si;
   }

   /**
    * [12-16][40-45]
    * @return
    */
   public String getStringBrackets() {
      StringBBuilder sb = new StringBBuilder(uc, numIntervals * 2);
      for (int i = 0; i < numIntervals; i++) {
         sb.append('[');
         sb.append(intervals[i].getOffset());
         sb.append('-');
         sb.append(intervals[i].getOffset() + intervals[i].getLen());
         sb.append(']');
      }
      return sb.toString();
   }

   public IntInterval[] ensureCapacity(IntInterval[] ar, int val) {
      if (ar == null) {
         return new IntInterval[val + 1];
      }
      if (ar.length <= val) {
         IntInterval[] newa = new IntInterval[val + 1];
         for (int i = 0; i < ar.length; i++) {
            newa[i] = ar[i];
         }
         return newa;
      }
      return ar;
   }

   //#mdebug
   public void toString(Dctx dc) {
      dc.root(this, IntIntervals.class, "@line5");
      toStringPrivate(dc);
      super.toString(dc.sup());
   }

   private void toStringPrivate(Dctx dc) {

   }

   public void toString1Line(Dctx dc) {
      dc.root1Line(this, IntIntervals.class);
      toStringPrivate(dc);
      super.toString1Line(dc.sup1Line());
   }

   //#enddebug

}
