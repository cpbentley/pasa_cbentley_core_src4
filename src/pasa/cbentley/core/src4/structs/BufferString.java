/*
 * (c) 2018-2020 Charles-Philip Bentley
 * This code is licensed under MIT license (see LICENSE.txt for details)
 */
package pasa.cbentley.core.src4.structs;

import pasa.cbentley.core.src4.ctx.UCtx;
import pasa.cbentley.core.src4.interfaces.IStrComparator;
import pasa.cbentley.core.src4.logging.Dctx;
import pasa.cbentley.core.src4.logging.IStringable;
import pasa.cbentley.core.src4.utils.IntUtils;
import pasa.cbentley.core.src4.utils.StringUtils;

/**
 * Encapsulates an array of String. 
 * <br>
 * The purpose of this class is to hold the buffer and its size.
 * <br>
 * Tends to insert bugs but can prevent some array copy
 * {@link BufferString#getIntsRef()}
 * <br>
 * <br>
 * <li>ArrayList purpose.
 * 
 * Double ended buffer. By default and until a preppend occurs implementation does not
 * care about front buffer size.
 * 
 * <br>
 * Src 4 compatible for Strings only.
 * 
 * core.src5 has a BufferObject for generics.
 * 
 * @author Charles Bentley
 *
 */
public class BufferString implements IStringable {

   private int            count     = 0;

   private int            offset    = 0;

   /**
    * must be 1 or higher
    */
   private int            increment = 5;

   /**
    * 
    */
   private IStrComparator strComparator;

   /**
    * index 0 starts at 0 and gives the last used value.
    * Therefore it also gives the number of elements
    * buffer size = ints.length - 1 - int[0]
    */
   private String[]       strings;

   private UCtx           uc;

   /**
    * 1,1
    */
   public BufferString(UCtx uc) {
      this(uc, 1, 1);
   }

   public BufferString(UCtx uc, int num) {
      this(uc, num, 3);
   }

   /**
    * 
    * @param startSize
    * @param increment few adds take a small increment., lots take a big increment
    */
   public BufferString(UCtx uc, int startSize, int increment) {
      setStrings(new String[startSize]);
      this.increment = increment;
      this.uc = uc;
      strComparator = uc.getStrComparator();
   }

   /**
    * Uses the reference!
    * @param ar
    */
   public BufferString(UCtx uc, String[] ar) {
      setStrings(ar);
      this.uc = uc;
      strComparator = uc.getStrComparator();
   }

   /**
    * append String to the {@link BufferString}
    * @param str
    */
   public void addStr(String str) {
      setStrings(uc.getMem().ensureCapacity(strings, count, increment));
      strings[offset + count] = str;
      count += 1;
   }

   public void addStrs(String str1, String str2) {
      setStrings(uc.getMem().ensureCapacity(strings, count + 2, increment));
      strings[offset + count] = str1;
      strings[offset + count + 1] = str2;
      count += 2;
   }

   /**
    * Add the String in front
    * @param str
    */
   public void addStrFront(String str) {
      if (offset == 0) {
         setStrings(uc.getMem().ensureCapacity(strings, count + 2, increment));
         //shift them all up by 2 and
         StringUtils.shiftIntUp(strings, 2, offset, offset + count - 1, false);
         offset += 2;
      }
      strings[offset - 1] = str;
      count++;
   }

   public void appendBufferToArrayAt(String[] array, int offset) {
      int start = this.offset;
      int end = start + count;
      for (int j = start; j < end; j++) {
         array[offset] = strings[j];
         offset++;
      }
   }

   public void appendBufferToArrayAt(String[] array, int offset, int startOffset, int skip) {
      int start = this.offset;
      int end = start + count;
      for (int j = start; j < end; j++) {
         array[offset] = strings[j];
         offset++;
      }
   }

   /**
    * O(1)
    */
   public void clear() {
      count = 0;
   }

   /**
    * Clone tit for tat
    * @return
    */
   public BufferString cloneMe() {
      BufferString ib = new BufferString(uc);
      ib.increment = increment;
      ib.setStrings(new String[strings.length]);
      for (int i = 0; i < strings.length; i++) {
         ib.strings[i] = this.strings[i];
      }
      ib.offset = this.offset;
      ib.count = this.count;
      ib.setStrComparator(this.getStrComparator());
      return ib;
   }

   /**
    * True if str is contained strictly according to the {@link IStrComparator}
    * O(n)
    * 
    * @param i
    * @return
    */
   public boolean containsEqual(String str) {
      return getFirstIndexEqual(str) != -1;
   }

   public boolean containsSimilar(String str) {
      return getFirstIndexSimilar(str) != -1;
   }

   /**
    * 0 based index
    * <br>
    * <br>
    * 
    * @param index
    * @return
    */
   public String getStr(int index) {
      return strings[offset + index];
   }

   public void setStrUnsafe(int index, String str) {
      strings[offset + index] = str;
   }

   /**
    * Start at offset, 
    * @param str
    * @param skip offset change when looking inside the buffer. when buffer is used as a fixed size structure
    * @return
    */
   public int getFirstIndexEqual(String str, int startOffset, int skipSize) {
      int offsetStart = offset + startOffset;
      int offsetEnd = offset + startOffset + count;
      return StringUtils.getFirstIndexEqual(str, strings, offsetStart, offsetEnd, strComparator, skipSize);
   }

   public int getFirstIndexEqual(String str) {
      return StringUtils.getFirstIndexEqual(str, strings, offset, offset + count, strComparator);
   }

   public int getFirstIndexSimilar(String str) {
      return StringUtils.getFirstIndexSimilar(str, strings, offset, offset + count, strComparator);
   }

   /**
    * Returns a truncated copy of the int array with only the integer data.
    * <br> 
    * The size header and trailing buffer are removed.
    * @return
    */
   public String[] getIntsClonedTrimmed() {
      int size = count;
      String[] ar = new String[size];
      for (int i = offset; i < offset + count; i++) {
         ar[i] = strings[i];
      }
      return ar;
   }

   /**
    * Returns the reference to the integer array. This mean the int[0] still means the number of elements.
    * <br>
    * <br>
    * A direct reference should be used with caution and is inherently unsafe unless
    * you know the implementation details of {@link BufferString}
    * @return
    */
   public String[] getIntsRef() {
      return strings;
   }

   /**
    * Returns the last element in the {@link BufferString}.
    * <br>
    * <br>
    * Does not remove it.
    * @return null if no elements
    */
   public String getLast() {
      if (count > 0) {
         return strings[offset + count - 1];
      }
      return null;
   }

   /**
    * Returns the last element in the {@link BufferString}.
    * <br>
    * <br>
    * Does not remove it.
    * @return null if no elements
    */
   public String getFirst() {
      if (count > 0) {
         return strings[offset];
      }
      return null;
   }

   /**
    * Number of elements.
    * @return
    */
   public int getSize() {
      return count;
   }

   public IStrComparator getStrComparator() {
      return strComparator;
   }

   /**
    * Search for i instances and remove it from the buffer
    * @param i
    * @return the number of removed elements
    */
   public int removeAll(String val) {
      return removeAllEqual(val, strComparator);
   }

   public int removeAllEqual(String val, IStrComparator strComparator) {
      return removeAll(val, strComparator, 0);
   }

   public int removeAllSimilar(String val, IStrComparator strComparator) {
      return removeAll(val, strComparator, 1);
   }

   private int removeAll(String val, IStrComparator strComparator, int type) {
      String[] ar = new String[strings.length];
      int countNotRemoved = 0;
      int countRemoved = 0;
      int newOffset = offset;
      int size = getSize();
      for (int j = 0; j < size; j++) {
         String d = strings[j];
         boolean isSelectedForRemoval = false;
         if (type == 0) {
            isSelectedForRemoval = strComparator.isSimilar(d, val);
         } else {
            isSelectedForRemoval = strComparator.isEqual(d, val);
         }
         if (!isSelectedForRemoval) {
            ar[newOffset] = d;
            newOffset++;
            countNotRemoved++;
         } else {
            //remove it by doing nothing
            countRemoved++;
         }
      }
      this.count = countNotRemoved;
      this.strings = ar;
      return countRemoved;
   }

   /**
    * 
    * @return null if size is zero
    */
   public String removeFirst() {
      if (count != 0) {
         String v = strings[offset];
         offset += 1;
         count--;
         return v;
      } else {
         return null;
      }
   }

   /**
    * Removes the last element and returns it.
    * <br>
    * Returns 0 when {@link BufferString} is emptys
    * @return
    */
   public String removeLast() {
      if (count != 0) {
         String v = strings[offset + count - 1];
         strings[offset + count] = null;
         count--;
         return v;
      } else {
         return null;
      }
   }

   /**
    * Remove size elements starting at index
    * @param index
    * @param size
    */
   public void removeAtIndexFor(int index, int size) {
      int start = offset + index + size;
      int end = offset + count;
      StringUtils.shiftIntDown(strings, size, start, end, false);
      count -= size;
   }

   /**
    * Sets the reference of the buffer.
    * @param ints
    */
   public void setStrings(String[] ints) {
      this.strings = ints;
   }

   /**
    * If null resets comparator to {@link UCtx#getStrComparator()}
    * @param strComparator
    */
   public void setStrComparator(IStrComparator strComparator) {
      if (strComparator == null) {
         strComparator = uc.getStrComparator();
      }
      this.strComparator = strComparator;
   }

   public void sortWith(SorterString sorter) {
      sorter.sortArray(strings, offset, offset + count);
   }

   //#mdebug
   public String toString() {
      return Dctx.toString(this);
   }

   public void toString(Dctx dc) {
      dc.root(this, "BufferString");
      dc.append(" #" + count);
      dc.append(':');
      for (int i = 0; i < count; i++) {
         dc.append("" + strings[i]);
         dc.append(' ');
      }
   }

   public String toString1Line() {
      return Dctx.toString1Line(this);
   }

   public void toString1Line(Dctx dc) {
      dc.root1Line(this, "BufferString");
      dc.append(" #" + count);
      dc.append(':');
      for (int i = 0; i < count; i++) {
         dc.append("" + strings[i]);
         dc.append(' ');
      }
   }

   public UCtx toStringGetUCtx() {
      return uc;
   }

   //#enddebug

}
