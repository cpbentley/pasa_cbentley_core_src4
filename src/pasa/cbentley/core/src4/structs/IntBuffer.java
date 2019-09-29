package pasa.cbentley.core.src4.structs;

import pasa.cbentley.core.src4.ctx.UCtx;

import pasa.cbentley.core.src4.logging.Dctx;
import pasa.cbentley.core.src4.logging.IStringable;
import pasa.cbentley.core.src4.utils.IntUtils;

/**
 * Encapsulates an array of integer values. 
 * <br>
 * The purpose of this class is to hold the buffer and its size.
 * <br>
 * Tends to insert bugs but can prevent some array copy
 * {@link IntBuffer#getIntsRef()}
 * <br>
 * <br>
 * <li>ArrayList purpose.
 * <br>
 * @author Charles Bentley
 *
 */
public class IntBuffer implements IStringable {

   /**
    * must be 1 or higher
    */
   private int   increment = 5;

   /**
    * index 0 starts at 0 and gives the last used value.
    * Therefore it also gives the number of elements
    * buffer size = ints.length - 1 - int[0]
    */
   private int[] ints;

   private UCtx  uc;

   /**
    * 1,1
    */
   public IntBuffer(UCtx uc) {
      this(uc, 1, 1);
   }

   public IntBuffer(UCtx uc, int num) {
      this(uc, num, 3);
   }

   /**
    * 
    * @param startSize
    * @param increment few adds take a small increment., lots take a big increment
    */
   public IntBuffer(UCtx uc, int startSize, int increment) {
      setInts(new int[startSize]);
      this.increment = increment;
      this.uc = uc;
   }

   /**
    * Uses the reference!
    * @param ar
    */
   public IntBuffer(UCtx uc, int[] ar) {
      setInts(ar);
      this.uc = uc;
   }

   /**
    * append integer to the intbuffer
    * @param i
    */
   public void addInt(int i) {
      int num = ints[0];
      setInts(uc.getMem().ensureCapacity(ints, num + 1, increment));
      ints[num + 1] = i;
      ints[0] = num + 1;
   }

   public void addInt(int x, int y, int w, int h) {
      int num = ints[0];
      setInts(uc.getMem().ensureCapacity(ints, num + 4, increment));
      ints[num + 1] = x;
      ints[num + 2] = y;
      ints[num + 3] = w;
      ints[num + 4] = h;
      ints[0] = num + 4;

   }

   /**
    * Append the int array values to this buffer
    * @param data
    */
   public void addInt(int[] data) {
      setInts(uc.getMem().ensureCapacity(ints, ints[0] + data.length, increment));
      int start = ints[0] + 1; //+1 cuz 1st index is reserved
      for (int j = 0; j < data.length; j++) {
         ints[start + j] = data[j];
      }
      ints[0] += data.length;
   }

   /**
    * O(1)
    */
   public void clear() {
      ints[0] = 0;
   }

   /**
    * Clone tit for tat
    * @return
    */
   public IntBuffer cloneMe() {
      IntBuffer ib = new IntBuffer(uc);
      ib.increment = increment;
      ib.setInts(new int[ints.length]);
      for (int i = 0; i < ints.length; i++) {
         ib.ints[i] = this.ints[i];
      }
      return ib;
   }

   /**
    * O(n)
    * 
    * @param i
    * @return
    */
   public boolean contains(int i) {
      return IntUtils.contains(ints, i, 1, ints[0]);
   }

   /**
    * Copy clone to object the content of parameter {@link IntBuffer}.
    * <br>
    * <br>
    * Copy clone means all data of current object is deleted and all data of parameter object is copied over.
    * @param b
    */
   public void copyClone(IntBuffer b) {
      int offsetlength = b.ints[0] + 1;
      this.ints = uc.getMem().ensureCapacity(ints, offsetlength + 1);
      for (int i = 0; i < offsetlength; i++) {
         ints[i] = b.ints[i];
      }
   }

   /**
    * 0 based index
    * <br>
    * <br>
    * 
    * @param index
    * @return
    */
   public int get(int index) {
      return ints[index + 1];
   }

   /**
    * Returns a truncated copy of the int array with only the integer data.
    * <br> 
    * The size header and trailing buffer are removed.
    * @return
    */
   public int[] getIntsClonedTrimmed() {
      int size = ints[0];
      int[] ar = new int[size];
      for (int i = 0; i < size; i++) {
         ar[i] = ints[i + 1];
      }
      return ar;
   }

   /**
    * Returns the reference to the integer array. This mean the int[0] still means the number of elements.
    * <br>
    * <br>
    * A direct reference should be used with caution and is inherently unsafe unless
    * you know the implementation details of {@link IntBuffer}
    * @return
    */
   public int[] getIntsRef() {
      return ints;
   }

   /**
    * Returns the last element in the {@link IntBuffer}.
    * <br>
    * <br>
    * Does not remove it.
    * @return 0 if no elements (size implicitely)
    */
   public int getLast() {
      return ints[ints[0]];
   }

   public int getFirstIndexOf(int value) {
      return IntUtils.getFirstIndex(value, ints, 0, getSize());
   }

   /**
    * Number of elements.
    * @return
    */
   public int getSize() {
      return ints[0];
   }

   /**
    * Search for i instances and remove it from the buffer
    * @param i
    */
   public void removeAll(int val) {
      int[] ar = new int[ints.length];
      int count = 1;
      int size = getSize();
      for (int j = 1; j <= size; j++) {
         int d = ints[j];
         if (d != val) {
            ar[count] = d;
            count++;
         }
      }
      ar[0] = count - 1;
      this.ints = ar;
   }

   /**
    * 
    * @return 0 if size is zero
    */
   public int removeFirst() {
      if (ints[0] != 0) {
         int v = ints[1];
         IntUtils.shiftIntDown(ints, 1, 2, ints[0], false);
         ints[0]--;
         return v;
      } else {
         return 0;
      }
   }

   public void appendBufferToArrayAt(int[] array, int offset) {
      int size = getSize();
      for (int j = 1; j <= size; j++) {
         array[offset] = ints[j];
         offset++;
      }
   }

   /**
    * Removes the last element and returns it.
    * <br>
    * Returns 0 when {@link IntBuffer} is emptys
    * @return
    */
   public int removeLast() {
      if (ints[0] != 0) {
         int v = ints[ints[0]];
         ints[ints[0]] = 0;
         ints[0]--;
         return v;
      } else {
         return 0;
      }
   }

   /**
    * Sets the reference of the buffer.
    * @param ints
    */
   public void setInts(int[] ints) {
      this.ints = ints;
   }

   public void sortWith(IntSorter sorter) {
      sorter.sortArray(ints, 1, ints[0]);
   }

   //#mdebug
   public String toString() {
      return Dctx.toString(this);
   }

   public void toString(Dctx dc) {
      dc.root(this, "IntBuffer");
      dc.append(" #" + ints[0]);
      dc.append(':');
      for (int i = 0; i < ints[0]; i++) {
         dc.append("" + ints[i + 1]);
         dc.append(' ');
      }
   }

   public UCtx toStringGetUCtx() {
      return uc;
   }

   public String toString1Line() {
      return Dctx.toString1Line(this);
   }

   public void toString1Line(Dctx dc) {
      dc.root1Line(this, "IntBuffer");
      dc.append(" #" + ints[0]);
      dc.append(':');
      for (int i = 0; i < ints[0]; i++) {
         dc.append("" + ints[i + 1]);
         dc.append(' ');
      }
   }
   //#enddebug

}
