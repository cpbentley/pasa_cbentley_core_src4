package pasa.cbentley.core.src4.structs;

import java.util.Enumeration;

import pasa.cbentley.core.src4.ctx.ObjectU;
import pasa.cbentley.core.src4.ctx.UCtx;
import pasa.cbentley.core.src4.helpers.StringBBuilder;
import pasa.cbentley.core.src4.logging.Dctx;
import pasa.cbentley.core.src4.utils.ArrayUtils;

/**
 * ASC sorted {@link IntInterval} collection with merges when inside or adjacent
 * @author Charles Bentley
 *
 */
public class IntIntervals extends ObjectU {

   private IntInterval[] intervals;

   private boolean       isPayLoadCheck;

   private int           numIntervals = 0;

   public IntIntervals(UCtx uc) {
      super(uc);
      intervals = new IntInterval[2];
   }

   private void addAt(int insertionIndex, IntInterval si) {
      intervals = ensureCapacity(intervals, numIntervals + 1);
      //shift at index
      ArrayUtils.shiftUp(intervals, 1, insertionIndex, numIntervals - 1, false);
      intervals[insertionIndex] = si;
      numIntervals++;
   }

   public IntInterval addInterval(int offset, int len) {
      IntInterval si = new IntInterval(uc);
      si.setOffset(offset);
      si.setLen(len);
      this.addInterval(si);
      return si;
   }

   public IntInterval addInterval(IntInterval si) {
      int insertionIndex = 0;
      IntInterval mergeFront = null;
      for (int i = 0; i < numIntervals; i++) {
         IntInterval interval = intervals[i];
         if (si.getOffset() <= interval.getOffset()) {
            insertionIndex = i;
            //at this stage check if merge with previous
            break;
         }
         insertionIndex++;
      }

      //check result with merge of element immediately below
      if (insertionIndex > 0) {
         IntInterval intervalPrev = intervals[insertionIndex - 1];
         if (intervalPrev.isIntersect(si)) {
            mergeFront = intervalPrev;
            insertionIndex--;
         }
      }

      if (mergeFront == null) {
         addAt(insertionIndex, si);
      } else {
         if (isPayLoadCheck && mergeFront.getPayload() != si.getPayload()) {
            mergeFront.substract(si);
            addAt(insertionIndex, si);
         } else {
            mergeFront.mergeFromBelowOf(si);
            intervals[insertionIndex] = si; //replace all interval, setting the new payload
         }
      }

      //merge end
      int removeNum = 0;
      boolean isStopRemove = false; //used to stop the removal when payload checking
      for (int i = insertionIndex + 1; i < numIntervals; i++) {
         IntInterval iiAbove = intervals[i];
         boolean intersect = si.isIntersect(iiAbove);
         if (intersect) {
            boolean isContaining = si.isContaining(iiAbove);
            if (isContaining && !isStopRemove) {
               //case where fully contained and we aren't forbidden to remove intervals anymore
               removeNum++;
            } else {
               //by construction, if we reach this stage. its the last possible intersection
               if (isPayLoadCheck && iiAbove.getPayload() != si.getPayload()) {
                  isStopRemove = true;
                  iiAbove.substract(si);
               } else {
                  if (!isStopRemove) {
                     //remove it
                     removeNum++;
                     //modify si to include it
                     iiAbove.mergeFromAboveOf(si);
                  }
               }
            }
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

   public void clear() {
      for (int i = 0; i < intervals.length; i++) {
         intervals[i] = null;
      }
      numIntervals = 0;
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

   public int[] getArrayOffsetLen() {
      int[] ar = new int[numIntervals * 2];
      int count = 0;
      for (int i = 0; i < numIntervals; i++) {
         ar[count++] = intervals[i].getOffset();
         ar[count++] = intervals[i].getLen();
      }
      return ar;
   }

   /**
    * {@link IntInterval} that describes the bounding interval that contains every {@link IntIntervals} in this structure.
    * @return
    */
   public IntInterval getBounds() {
      IntInterval ii = new IntInterval(uc);
      if (numIntervals != 0) {
         IntInterval first = intervals[0];
         IntInterval last = intervals[numIntervals - 1];
         ii.setOffset(first.getOffset());
         ii.setLen(last.getOffsetEnd() - first.getOffset());
      }
      return ii;
   }

   public IntInterval[] getIntersection(int offset, int len) {
      return getIntersection(new IntInterval(uc, offset, len));
   }
   /**
    * Returns the intervals that intersect with given {@link IntInterval}
    * @param intervalWithStyle
    * @return
    */
   public IntInterval[] getIntersection(IntInterval ii) {
      int mid = 0;
      int low = 0;
      int high = numIntervals - 1;
      while (low <= high) {
         mid = (low + high) / 2;
         IntInterval iimid = intervals[mid];
         int v = iimid.getPositionInterval(ii);
         if (v == 0) {
            //we found one
            //first add
            BufferObject buf = new BufferObject(uc);
            for (int i = mid - 1; i >= 0; i--) {
               IntInterval iia = intervals[i];
               if(ii.isIntersect(iia)) {
                  buf.addLeft(iia);
               } else {
                  break;
               }
            }
            buf.add(iimid);
            
            for (int i = mid + 1; i < numIntervals; i++) {
               IntInterval iia = intervals[i];
               if(ii.isIntersect(iia)) {
                  buf.add(iia);
               } else {
                  break;
               }
            }
            IntInterval[] array = new IntInterval[buf.getSize()];
            buf.appendBufferToArrayAt(array,0);
            return array;
         } else if (v == -1) {
            high = mid - 1;
         } else {
            low = mid + 1;
         }
      }
      return new IntInterval[0];
   }

   public int getIntervalIntersectIndex(int index) {
      //since it is sorted.. we can do logn
      int mid = 0;
      int low = 0;
      int high = numIntervals - 1;
      while (low <= high) {
         mid = (low + high) / 2;
         IntInterval ii = intervals[mid];
         int v = ii.getIndexPosition(index);
         if (v == 0) {
            return mid;
         } else if (v == -1) {
            high = mid - 1;
         } else {
            low = mid + 1;
         }
      }
      return -1;
   }
   
   /**
    * Return the interval for under the given index. null otherwise
    * @param index
    * @return {@link IntInterval}
    */
   public IntInterval getIntervalIntersect(int index) {
      //since it is sorted.. we can do logn
      int offset = getIntervalIntersectIndex(index);
      if(offset == -1) {
         return null;
      } else {
         return intervals[offset];
      }
   }

   public Enumeration getIntervalEnumeration() {
      EnumerationBase eb = new EnumerationBase(uc);
      eb.setArray(intervals, numIntervals);
      return eb;
   }

   public int getSize() {
      return numIntervals;
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

   public boolean isPayLoadCheck() {
      return isPayLoadCheck;
   }

   /**
    * Removes the range defined by offset+len in all intervals, removes an interval if fulled contained
    * by that range.
    * @param offset
    * @param len
    */
   public void removeInterval(int offset, int len) {
      IntInterval si = new IntInterval(uc);
      si.setOffset(offset);
      si.setLen(len);
      int insertionVirtualIndex = 0;
      for (int i = 0; i < numIntervals; i++) {
         IntInterval interval = intervals[i];
         if (offset <= interval.getOffset()) {
            insertionVirtualIndex = i;
            //at this stage check if merge with previous
            break;
         }
         insertionVirtualIndex++;
      }

      //check result with merge of element immediately below
      if (insertionVirtualIndex > 0) {
         IntInterval intervalPrev = intervals[insertionVirtualIndex - 1];
         if (intervalPrev.isIntersect(si)) {
            if (si.isContaining(intervalPrev)) {
               //remove mergeFront// quit by construction
               ArrayUtils.shiftDown(intervals, 1, insertionVirtualIndex, numIntervals - 1, false);
               numIntervals--;
            } else {
               intervalPrev.substract(si);
            }
         }
      }

      //merge end
      for (int i = insertionVirtualIndex; i < numIntervals; i++) {
         IntInterval iiAbove = intervals[i];
         if (si.isContaining(iiAbove)) {
            //remove mergeFront// quit by construction
            ArrayUtils.shiftDown(intervals, 1, insertionVirtualIndex, numIntervals - 1, false);
            numIntervals--;
         } else {
            boolean intersect = si.isIntersect(iiAbove);
            if (intersect) {
               //modify si to include it
               iiAbove.substract(si);
            } else {
               //escape by construction
               break;
            }
         }
      }
   }

   public void setPayLoadCheck(boolean isPayLoadCheck) {
      this.isPayLoadCheck = isPayLoadCheck;
   }

   //#mdebug
   public void toString(Dctx dc) {
      dc.root(this, IntIntervals.class, "@line5");
      toStringPrivate(dc);
      super.toString(dc.sup());
   }

   public void toString1Line(Dctx dc) {
      dc.root1Line(this, IntIntervals.class);
      toStringPrivate(dc);
      super.toString1Line(dc.sup1Line());
   }

   private void toStringPrivate(Dctx dc) {

   }

   //#enddebug

}
