package pasa.cbentley.core.src4.structs;

import java.util.Enumeration;

import pasa.cbentley.core.src4.ctx.ObjectU;
import pasa.cbentley.core.src4.ctx.UCtx;
import pasa.cbentley.core.src4.helpers.StringBBuilder;
import pasa.cbentley.core.src4.logging.Dctx;
import pasa.cbentley.core.src4.utils.ArrayUtils;

/**
 * ASC sorted {@link IntInterval} collection with potential merges when adjacent or breakage when inside
 * 
 * Breaking Depends on
 * <li> {@link IntIntervals#isPayLoadCheck()}
 * 
 * When true, object payload must be of reference equality for merging otherwise breaking down intervals is done
 * when an interval inside another is added.
 * 
 * @author Charles Bentley
 *
 */
public class IntIntervals extends ObjectU {

   private IntInterval[] intervals;

   /**
    * @see IntIntervals#isPayLoadCheck()
    */
   private boolean       isPayLoadCheck;

   /**
    * New Payloads are written over existing payloads.
    * Flag is valid when PayLoakCheck flag is true
    */
   private boolean       isPayLoadUnderWrite;

   /**
    * @see IntIntervals#getSize()
    */
   private int           numIntervals = 0;

   public IntIntervals(UCtx uc) {
      this(uc, 5);
   }

   public IntIntervals(UCtx uc, int buf) {
      super(uc);
      intervals = new IntInterval[buf];
   }

   private void addAt(int insertionIndex, IntInterval si) {
      if (si == null) {
         throw new NullPointerException("");
      }
      intervals = ensureCapacity(intervals, numIntervals + 1);
      //shift at index
      ArrayUtils.shiftUp(intervals, 1, insertionIndex, numIntervals - 1, false);
      intervals[insertionIndex] = si;
      numIntervals++;
   }

   public IntInterval addInterval(int offset, int len) {
      return addInterval(offset, len, null);
   }

   /**
    * Creates and return an {@link IntInterval} for offset,len.
    * <p>
    * This might generates more intervals 
    * </p>
    * @param offset
    * @param len
    * @param payload when not null, decides by reference equality if neighbour intervals are merged into one.
    * @return
    * @throws IllegalArgumentException if len <= 0
    */
   public IntInterval addInterval(int offset, int len, Object payload) {
      if (len <= 0) {
         throw new IllegalArgumentException();
      }
      IntInterval si = new IntInterval(uc);
      si.setOffset(offset);
      si.setLen(len);
      si.setPayload(payload);
      this.addInterval(si);
      return si;
   }

   /**
    * Adds an interval based on the offsets
    * @param offsetStart
    * @param offsetEnd
    * @return
    * @throws IllegalArgumentException if offsetEnd <= offsetStart
    */
   public IntInterval addIntervalOffset(int offsetStart, int offsetEnd) {
      return this.addInterval(offsetStart, offsetEnd - offsetStart);
   }

   public IntInterval addIntervalOffset(int offsetStart, int offsetEnd, Object payload) {
      return this.addInterval(offsetStart, offsetEnd - offsetStart, payload);
   }

   private int getInsertionIndex(int offset) {
      int insertionIndex = 0;
      for (int i = 0; i < numIntervals; i++) {
         IntInterval intervalFromArray = intervals[i]; //interval to check. 
         if (offset <= intervalFromArray.getOffset()) {
            break;
         }
         insertionIndex++;
      }
      //check if we are inside the previous
      if (insertionIndex > 0) {
         IntInterval prev = intervals[insertionIndex - 1];
         if (prev.isInside(offset) || prev.getOffsetEnd() == offset) {
            insertionIndex = insertionIndex - 1;
         }
      }
      return insertionIndex;
   }

   /**
    * Return insertion index
    * @param intervalToBeAdded
    * @return -1 if interval was not addded because existing range is alreayd there
    */
   private int addIntervalInternal(IntInterval intervalToBeAdded) {
      //
      int offsetAdded = intervalToBeAdded.getOffset();
      int index = getInsertionIndex(offsetAdded);
      int count = 0;
      int removedStartIndex = -1; //removed intervals due to merges are always contiguous
      int numRemoved = 0;
      int finalInsertionIndex = -1;
      if (numIntervals == 0) {
         addAt(index, intervalToBeAdded);
         finalInsertionIndex = index;
      } else if (index == numIntervals) {
         addAt(index, intervalToBeAdded);
         //check for collision with front
         finalInsertionIndex = index;
      } else {
         //now clean up
         int max = numIntervals;
         for (int i = index; i < numIntervals; i++) {
            IntInterval intervalFromArray = intervals[i]; //interval to check. 
            IntIntervalRelation irr = intervalToBeAdded.getRelation(intervalFromArray);
            if (irr.isOneContainedInTwo()) {
               //case where new interval start with same offset but is smaller
               //in this case.. we do not add
               if (isPayLoadCheck) {
                  //new interval is contained. when different payloads
                  //we create intervals from complements

                  if (intervalToBeAdded.getPayload() != intervalFromArray.getPayload()) {

                     //harder case since we have to create new intervals based on complements of intersect
                     IntInterval intersectComplementLeft = irr.getIntersectComplementLeft();
                     if (intersectComplementLeft != null) {
                        intersectComplementLeft.setPayload(intervalFromArray.getPayload());
                        intervals[i] = intersectComplementLeft;
                        addAt(i + 1, intervalToBeAdded);
                        finalInsertionIndex = i + 1;
                     } else {
                        intervals[i] = intervalToBeAdded;
                        finalInsertionIndex = i;
                     }
                     IntInterval intersectComplementRight = irr.getIntersectComplementRight();
                     if (intersectComplementRight != null) {
                        intersectComplementRight.setPayload(intervalFromArray.getPayload());
                        addAt(finalInsertionIndex + 1, intersectComplementRight);
                     }
                  }
               }
               //nothing else.. since new interval is smaller
               break; //stop looking. by construction there are no more possible relations
            } else if (irr.isTwoContainedInOne()) {
               //
               if (isPayLoadCheck && isPayLoadUnderWrite) {
                  //TODO 
                  throw new RuntimeException();
               } else {
                  //easy case where two is just kicked out and replaced without any structural changes
                  if (removedStartIndex == -1) {
                     removedStartIndex = i;
                  }
                  intervals[i] = null;
                  numRemoved++;
               }
            } else if (irr.getIntervalIntersect() != null) {
               IntInterval sect = irr.getIntervalIntersect();
               if (isPayLoadCheck && intervalToBeAdded.getPayload() != intervalFromArray.getPayload()) {
                  if (finalInsertionIndex == -1) {
                     if (irr.isTwoFirst()) {
                        finalInsertionIndex = i + 1;
                     } else {
                        finalInsertionIndex = i;
                     }
                     addAt(finalInsertionIndex, intervalToBeAdded);
                     i++;
                  }
                  if (isPayLoadUnderWrite) {
                     //reduce added
                     int offsetStart = intervalToBeAdded.getOffset();
                     int offsetEnd = intervalFromArray.getOffset() - 1;
                     intervalToBeAdded.setOffsets(offsetStart, offsetEnd);
                  } else {
                     if (irr.isTwoFirst()) {
                        int offsetStart = intervalFromArray.getOffset();
                        int offsetEnd = intervalToBeAdded.getOffset();
                        intervalFromArray.setOffsets(offsetStart, offsetEnd);
                     } else {
                        //reduce one
                        int offsetStart = intervalToBeAdded.getOffsetEnd();
                        int offsetEnd = intervalFromArray.getOffsetEnd();
                        intervalFromArray.setOffsets(offsetStart, offsetEnd);
                     }
                  }
               } else {
                  //simple merge
                  if (irr.isOneFirst()) {
                     intervalToBeAdded.setOffsets(intervalToBeAdded.getOffset(), intervalFromArray.getOffsetEnd());
                  } else {
                     intervalToBeAdded.setOffsets(intervalFromArray.getOffset(), intervalToBeAdded.getOffsetEnd());
                  }
                  if (finalInsertionIndex == -1) {
                     intervals[i] = intervalToBeAdded;
                     finalInsertionIndex = i;
                  } else {
                     intervals[i] = null;
                     if (removedStartIndex == -1) {
                        removedStartIndex = i;
                     }
                     numRemoved++;
                  }
               }
            } else if (irr.isAdjacent()) {
               if (isPayLoadCheck && intervalToBeAdded.getPayload() != intervalFromArray.getPayload()) {
                  if (finalInsertionIndex == -1) {
                     finalInsertionIndex = i;
                     if (irr.isTwoFirst()) {
                        //when it is adjacent on the right.. add on the next index
                        finalInsertionIndex++;
                     }
                     addAt(finalInsertionIndex, intervalToBeAdded);
                     //increase index by one to skip slot just added
                     i++;
                  }
               } else {
                  if (irr.isOneFirst()) {
                     //tobeadded is first
                     intervalToBeAdded.incrLen(intervalFromArray.getLen());
                     //there won't be a new interval
                  } else {
                     //only one can be adjacent
                     intervalToBeAdded.setOffset(intervalFromArray.getOffset());
                     intervalToBeAdded.incrLen(intervalFromArray.getLen());
                  }
                  if (finalInsertionIndex == -1) {
                     intervals[i] = intervalToBeAdded;
                     finalInsertionIndex = i;
                  } else {
                     intervals[i] = null;
                     if (removedStartIndex == -1) {
                        removedStartIndex = i;
                     }
                     numRemoved++;
                  }
               }
            } else {
               //no relation.. break. since other intervals won't have relations either
               if (finalInsertionIndex == -1) {
                  addAt(i, intervalToBeAdded);
                  finalInsertionIndex = i;
               }
               break;
            }
         }
      }

      if (removedStartIndex != -1 && numRemoved != 0) {
         //when all intervals upwards are gobbled up with new interval.. it was not added ab

         int start = removedStartIndex + numRemoved;
         int end = numIntervals - 1;
         ArrayUtils.shiftDown(intervals, numRemoved, start, end, false);
         numIntervals = numIntervals - numRemoved;

         if (finalInsertionIndex == -1) {
            addAt(removedStartIndex, intervalToBeAdded);
            finalInsertionIndex = removedStartIndex;
         }
      }

      return finalInsertionIndex;
   }

   /**
    * When payload check is turned on, overwrite previous intervals with different payloads
    * <p>
    * When {@link IntIntervals#isPayLoadCheck()} return false,
    * Payloads are not checked and any merge will use the payload of the added {@link IntInterval}.
    * </p>
    * <p>
    * When {@link IntIntervals#isPayLoadCheck()} return true,
    * Merges only happen if Payloads are reference equal. otherwise existing payloads are kept
    * </p>
    * @param intervalToBeAdded
    * @return
    */
   public IntInterval addInterval(IntInterval intervalToBeAdded) {
      addIntervalInternal(intervalToBeAdded);
      return intervalToBeAdded;
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

   /**
    * Array of ints with startoffset and length pairs.
    * @return
    */
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
    * An array of ints with start and end offsets pairs
    * @return
    */
   public int[] getArrayOffsets() {
      int[] ar = new int[numIntervals * 2];
      int count = 0;
      for (int i = 0; i < numIntervals; i++) {
         ar[count++] = intervals[i].getOffset();
         ar[count++] = intervals[i].getOffsetEnd();
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
         int offsetFirst = first.getOffset();
         int offsetEndLast = last.getOffsetEnd();
         ii.setOffset(offsetFirst);
         ii.setLen(offsetEndLast - offsetFirst);
      }
      return ii;
   }

   /**
    * @see IntIntervals#getIntersection(IntInterval)
    * @param offset
    * @param len
    * @return an empty array if no intersection
    */
   public IntInterval[] getIntersection(int offset, int len) {
      return getIntersection(new IntInterval(uc, offset, len));
   }

   /**
    * Returns the intervals that intersect with the given {@link IntInterval}
    * 
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
               if (ii.isIntersect(iia)) {
                  buf.addLeft(iia);
               } else {
                  break;
               }
            }
            buf.add(iimid);

            for (int i = mid + 1; i < numIntervals; i++) {
               IntInterval iia = intervals[i];
               if (ii.isIntersect(iia)) {
                  buf.add(iia);
               } else {
                  break;
               }
            }
            IntInterval[] array = new IntInterval[buf.getSize()];
            buf.appendBufferToArrayAt(array, 0);
            return array;
         } else if (v == -1) {
            high = mid - 1;
         } else {
            low = mid + 1;
         }
      }
      return new IntInterval[0];
   }

   /**
    * Retyrbs {@link IntInterval} directly from the array on the index
    * @param index
    * @return
    */
   public IntInterval getInterval(int index) {
      return intervals[index];
   }

   /**
    * 
    * @param index
    * @return
    */
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
      if (offset == -1) {
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

   /**
    * 
    * @return
    */
   public boolean isPayLoadCheck() {
      return isPayLoadCheck;
   }

   public boolean hasInterval(IntInterval interval) {
      for (int i = 0; i < numIntervals; i++) {
         IntInterval myinterval = intervals[i];
         if (myinterval == interval) {
            return true;
         } else {
            if (myinterval.getOffset() == interval.getOffset()) {
               if (myinterval.getLen() == interval.getLen()) {
                  return true;
               }
            }
         }
      }
      return false;
   }

   /**
    * Only removes if the exact offset-len pair is found, thus simply removes it without creating new intervals
    * @param interval
    * @return
    */
   public boolean removeIntervalExact(IntInterval interval) {
      for (int i = 0; i < numIntervals; i++) {
         IntInterval myinterval = intervals[i];
         if (myinterval.getOffset() == interval.getOffset()) {
            if (myinterval.getLen() == interval.getLen()) {
               for (int j = i; j < intervals.length - 1; j++) {
                  intervals[j] = intervals[j + 1];
               }
               return true;
            }
         }
      }
      return false;
   }

   /**
    * Removes the range defined by offset+len in all intervals, removes an interval if fulled contained by that range.
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

      dc.nlLvlArray("intervals", intervals);

      dc.nlLvl("Bounds", getBounds());
   }

   public void toString1Line(Dctx dc) {
      dc.root1Line(this, IntIntervals.class);
      toStringPrivate(dc);
      super.toString1Line(dc.sup1Line());
   }

   private void toStringPrivate(Dctx dc) {
      dc.appendVarWithSpace("numIntervals", numIntervals);
      dc.appendVarWithSpace("isPayLoadCheck", isPayLoadCheck);
   }

   public boolean isPayLoadOverWrite() {
      return isPayLoadUnderWrite;
   }

   public void setPayLoadUnderWrite(boolean isPayLoadOverWrite) {
      this.isPayLoadUnderWrite = isPayLoadOverWrite;
   }

   public String toStringOffsetBracket() {
      StringBBuilder sb = new StringBBuilder(uc);
      for (int i = 0; i < numIntervals; i++) {
         if (i != 0) {
            sb.append(' ');
         }
         sb.append('[');
         sb.append(intervals[i].getOffset());
         sb.append(',');
         sb.append(intervals[i].getOffsetEndInside());
         sb.append(']');
      }
      return sb.toString();
   }

   public String toStringOffsetBracketPayload() {
      StringBBuilder sb = new StringBBuilder(uc);
      for (int i = 0; i < numIntervals; i++) {
         if (i != 0) {
            sb.append(' ');
         }
         sb.append('[');
         sb.append(intervals[i].getOffset());
         sb.append(',');
         sb.append(intervals[i].getOffsetEndInside());
         sb.append('-');
         Object o = intervals[i].getPayload();
         if (o == null) {
            sb.append("nullpayload");
         } else {
            sb.append(o.toString());
         }
         sb.append(']');
      }
      return sb.toString();
   }

   public String toStringOffsets() {
      return uc.getIU().debugString(this.getArrayOffsets(), ",", 1, "-", 2);
   }

   public String toStringOffsetLengthPairs() {
      return uc.getIU().debugString(this.getArrayOffsetLen(), ",", 1, "-", 2);
   }
   //#enddebug

}
