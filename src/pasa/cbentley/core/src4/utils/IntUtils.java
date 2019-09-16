package pasa.cbentley.core.src4.utils;

import java.io.PrintStream;

import pasa.cbentley.core.src4.ctx.UCtx;
import pasa.cbentley.core.src4.helpers.StringBBuilder;

import pasa.cbentley.core.src4.logging.Dctx;
import pasa.cbentley.core.src4.logging.IStringable;
import pasa.cbentley.core.src4.structs.IntBuffer;

public class IntUtils implements IStringable {

   /**
    * basic insert of values in front of array
    */
   public static final int ITIS_ARRAY_ORDER_0_APPEND_SUFFIX = 0;

   /**
    * No sorting. but integers are added on the top of the array
    */
   public static final int ITIS_ARRAY_ORDER_1_APPEND_PREFIX = 1;

   /**
    * Sort from small to tall
    */
   public static final int ITIS_ARRAY_ORDER_2_ASC           = 2;

   /**
    * Sorted from tall to small
    */
   public static final int ITIS_ARRAY_ORDER_3_DESC          = 3;

   public static byte[] byteArrayBEFromInt(int v) {
      byte[] writeBuffer = new byte[4];
      writeIntBE(writeBuffer, 0, v);
      return writeBuffer;
   }

   public static byte[] byteArrayBEFromInt24(int v) {
      byte[] writeBuffer = new byte[3];
      writeInt24BE(writeBuffer, 0, v);
      return writeBuffer;
   }

   public static byte[] byteArrayLEFromInt(int v) {
      byte[] writeBuffer = new byte[4];
      writeIntLE(writeBuffer, 0, v);
      return writeBuffer;
   }

   public static byte[] byteArrayLEFromInt24(int v) {
      byte[] writeBuffer = new byte[3];
      writeInt24LE(writeBuffer, 0, v);
      return writeBuffer;
   }

   /**
    * true if i is inside the array.
    * <br>
    * Static method to be inlined.
    * @param ar non null
    * @param i
    * @return
    */
   public static boolean contains(int[] ar, int i) {
      for (int j = 0; j < ar.length; j++) {
         if (ar[j] == i)
            return true;
      }
      return false;
   }

   public static boolean contains(int[] ar, int i, int offset, int len) {
      return getFirstIndex(i, ar, offset, len) != -1;
   }

   public static boolean containsOnly(int[] ar, int i) {
      return containsOnly(ar, 0, ar.length, i);
   }

   /**
    * Returns the index of first occurence of val.
    * <br>
    * Index is absolute. if value is at offset, it returns offset.
    * @param val
    * @param ar
    * @param offset
    * @param len
    * @return -1 if not found
    */
   public static int getFirstIndex(int val, int[] ar, int offset, int len) {
      for (int j = offset; j < offset + len; j++) {
         if (ar[j] == val)
            return j;
      }
      return -1;
   }

   public static int getFirstIndex(int val, int[] ar) {
      return getFirstIndex(val, ar, 0, ar.length);
   }

   /**
    * true if array only contains i values
    * @param ar
    * @param i
    * @return
    */
   public static boolean containsOnly(int[] ar, int offset, int len, int i) {
      for (int j = offset; j < offset + len; j++) {
         if (ar[j] != i)
            return false;
      }
      return true;
   }

   public static void debug(int[] ar, PrintStream ps) {
      for (int i = 0; i < ar.length; i++) {
         ps.println(i + ":" + ar[i]);
      }
   }

   public static void debug(int[] ar, PrintStream ps, String sep) {
      if (ar == null) {
         ps.print("null");
         return;
      }
      for (int i = 0; i < ar.length; i++) {
         ps.print(i + ":" + ar[i] + sep);
      }
   }

   /**
    * 
    * how many values needed
    * <li>1/6 returns 1
    * <li>6/6 returns 1
    * <li>6/60 returns 1
    * <li>6/2 returns 3
    * <li>6/3 returns 2
    * <li>6/4 returns 2
    * <li> 0 / 4 returns 0.
    * <br>
    * <br>
    * @param value
    * @param divider
    * @return when divider is 0 returns 0
    */
   public static int divideCeil(int value, int divider) {
      if (divider == 0 || value == 0) {
         return 0;
      }
      int count = 1;
      int var = divider;
      while (var < value) {
         count++;
         var += divider;
      }
      return count;
   }

   /**
    * On which column and row cell is, 
    * <li>row 0-based index
    * <li>col 0-based index
    * <br>
    * <br>
    * @param cell zero based index
    * @param numColumns
    * @param res
    */
   public static void divideColumn(int cell, int numColumns, int[] res) {
      int rowCount = 0;
      int var = numColumns;
      while (var <= cell) {
         rowCount++;
         var += numColumns;
      }
      res[0] = rowCount;
      int colCount = cell % numColumns;
      res[1] = colCount;
   }

   /**
    * 
    * @param value
    * @param divider
    * @return
    */
   public static int divideFloor(int value, int divider) {
      if (divider == 0)
         throw new IllegalArgumentException();
      int count = 0;
      int var = divider;
      while (var <= value) {
         count++;
         var += divider;
      }
      return count;
   }

   public static void fill(int[] ar, int filler) {
      fill(ar, filler, 0, ar.length - 1);
   }

   /**
    * Fills array [start,end] with filler
    * @param ar
    * @param filler
    * @param start
    * @param end
    */
   public static void fill(int[] ar, int filler, int start, int end) {
      for (int i = start; i <= end; i++) {
         ar[i] = filler;
      }
   }

   /**
    * true if array only contains i values
    * @param ar
    * @param i
    * @return
    */
   public static boolean isOnly(int[] ar, int offset, int len, int i) {
      for (int j = offset; j < offset + len; j++) {
         if (ar[j] != i)
            return false;
      }
      return true;
   }

   public static boolean isOnly(int[] ar, int i) {
      return isOnly(ar, 0, ar.length, i);
   }

   /**
    * 
    * @param ar
    * @param v
    * @return -1 if v is not in array
    */
   public static int getIndexFirstValue(int[] ar, int v) {
      for (int i = 0; i < ar.length; i++) {
         if (ar[i] == v)
            return i;
      }
      return -1;
   }

   public static int getIntByteSize(int value) {
      if (value < 0)
         return 4;
      if (value <= 255) {
         return 1;
      }
      if (value < 65536)
         return 2;
      if (value < 16777216)
         return 3;
      return 4;
   }

   public static int getMax(int[] ar) {
      return getMax(ar, 0, ar.length);
   }

   /**
    * 
    * @param ar
    * @param offset
    * @param len excluded
    * @return
    */
   public static int getMax(int[] ar, int offset, int len) {
      int max = 0;
      for (int i = offset; i < offset + len; i++) {
         if (ar[i] > max)
            max = ar[i];
      }
      return max;
   }

   public static int getMaxBitSize(int[] ar, int offset, int len) {
      int max = getMax(ar, offset, len);
      return BitUtils.widthInBits(max);
   }

   /**
    * 
    * @param ar
    * @param offset
    * @param len
    * @return
    */
   public static int getMin(int[] ar, int offset, int len) {
      if (ar.length == 0)
         return 0;
      int min = Integer.MAX_VALUE;
      for (int i = offset; i < len; i++) {
         if (ar[i] < min)
            min = ar[i];
      }
      return min;
   }

   /**
    * the index of unused val or smaller in vals
    * -1 if there are still unused smaller values in vals
    * -2 if there are no more values unused value smaller or equal to val
    * @param vals sorted integers in ASC order
    * @param used
    * @param val
    * @return
    */
   private static int getNonUsedVal(int[] vals, boolean[] used, int val, int startindex) {
      boolean unused = false;
      for (int i = startindex; i >= 0; i--) {
         if (!used[i]) {
            int check = vals[i];
            if (check <= val)
               return i;
         }
      }
      if (unused)
         return -1;
      return -2;
   }

   /**
    * The index to which insert value
    * @param ints an array with ordered value in ASC order.
    * @param value
    * @param unique
    * @param offset
    * @param len
    * @return -1 if unique and already found
    */
   public static int getOrderIndexAsc(int[] ints, int value, boolean unique, int offset, int len) {
      if (len == 0) {
         return offset;
      }
      int low = 0;
      int high = len - 1;
      int mid = -1;
      while (low <= high) {
         mid = offset + (low + high) / 2;
         if (ints[mid] < value) {
            low = mid + 1 - offset;
            mid++;
         } else if (ints[mid] > value) {
            high = mid - 1 - offset;
         } else {
            //value is equal
            if (unique)
               return -1;
            break;
         }
      }
      return mid;
   }

   /**
    * 
    * @param value
    * @param ints a ASC sorted array with possibly multiple identical values
    * @param offset
    * @param len
    * @return
    */
   public static int getFirstIndexASC(int value, int[] ints, int offset, int len) {
      return getSearchInASC(value, ints, offset, len)[TUPLE_SEARCH_0_FIRST_INDEX];
   }

   public static int getFirstIndexDESC(int value, int[] ints, int offset, int len) {
      return getSearchInDESC(value, ints, offset, len)[TUPLE_SEARCH_0_FIRST_INDEX];
   }

   public static final int TUPLE_SEARCH_0_FIRST_INDEX  = 0;

   public static final int TUPLE_SEARCH_1_INSERT_INDEX = 1;

   public static int[] getSearchInASC(int value, int[] ints, int offset, int len) {
      int[] tuple = new int[] { -1, -1 }; //first index value, insert value
      if (len == 0) {
         tuple[TUPLE_SEARCH_1_INSERT_INDEX] = 0;
         return tuple;
      }
      int low = 0;
      int high = len - 1;
      int mid = -1;
      while (low <= high) {
         mid = offset + (low + high) / 2;
         if (ints[mid] < value) {
            low = mid + 1 - offset;
            mid++;
         } else if (ints[mid] > value) {
            high = mid - 1 - offset;
         } else {
            //value is equal. if several we have to down
            int val = getIndexSimilarValueDown(mid, ints, offset, len);
            tuple[TUPLE_SEARCH_0_FIRST_INDEX] = val;
            tuple[TUPLE_SEARCH_1_INSERT_INDEX] = val;
            return tuple;
         }
      }
      tuple[TUPLE_SEARCH_1_INSERT_INDEX] = mid;
      return tuple;
   }

   /**
    * Look up the index of the last value that is the same to the value at startIndex
    * @param startIndex
    * @param ints
    * @param offset
    * @param len
    * @return
    */
   public static int getIndexSimilarValueDown(int startIndex, int[] ints, int offset, int len) {
      int startValue = ints[startIndex];
      int val = startIndex;
      boolean isContinue = true;
      while (val - 1 >= offset && isContinue) {
         int valBelow = ints[val - 1];
         if (valBelow == startValue) {
            val = val - 1;
         } else {
            isContinue = false;
         }
      }
      return val;
   }

   public static int getIndexSimilarValueUp(int startIndex, int[] ints, int offset, int len) {
      int startValue = ints[startIndex];
      int val = startIndex;
      boolean isContinue = true;
      while (val + 1 < offset + len && isContinue) {
         int valUp = ints[val + 1];
         if (valUp == startValue) {
            val = val + 1;
         } else {
            isContinue = false;
         }
      }
      return val;
   }

   public static int[] getSearchInDESC(int value, int[] ints, int offset, int len) {
      int[] tuple = new int[] { -1, -1 }; //first index value, insert value
      if (len == 0) {
         tuple[TUPLE_SEARCH_1_INSERT_INDEX] = 0;
         return tuple;
      }
      int low = 0;
      int high = len - 1;
      int mid = -1;
      while (low <= high) {
         mid = offset + (low + high) / 2;
         if (ints[mid] > value) {
            low = mid + 1 - offset;
            mid++;
         } else if (ints[mid] < value) {
            //go to the left
            high = mid - 1 - offset;
         } else {
            //value is equal. if several we have to down
            int val = getIndexSimilarValueDown(mid, ints, offset, len);
            tuple[TUPLE_SEARCH_0_FIRST_INDEX] = val;
            tuple[TUPLE_SEARCH_1_INSERT_INDEX] = val;
            return tuple;
         }
      }
      tuple[TUPLE_SEARCH_1_INSERT_INDEX] = mid;
      return tuple;
   }

   /**
    * 
    * @param ints
    * @param value
    * @param unique
    * @param offset
    * @param len
    * @return
    */
   public static int getOrderIndexDesc(int[] ints, int value, boolean unique, int offset, int len) {
      if (len == 0) {
         return offset;
      }
      int low = 0;
      int high = len - 1;
      int mid = -1;
      while (low <= high) {
         mid = offset + (low + high) / 2;
         if (ints[mid] > value) {
            low = mid + 1 - offset;
            mid++;
         } else if (ints[mid] < value) {
            //go to the left
            high = mid - 1 - offset;
         } else {
            if (unique) {
               return -1;
            }
            break;
         }
      }
      return mid;
   }

   /**
    * Compute an interval of integers. 
    * An array of <code>size</code> starting with <code>start</code> and ending with <code>end</code>
    * <br>
    * <br>
    * 
    * Its different than {@link IntUtils#getGeneratedInterval(int, int)}
    * @param size number of number
    * @param start inclusive
    * @param end inclusive
    * @return
    */
   public int[] getValues(int size, int start, int end) {
      if (size <= 0)
         return new int[0];
      if (size == 1)
         return new int[] { start };
      int[] values = new int[size];
      values[0] = start;
      values[values.length - 1] = end;
      if (size != 2) {
         int steps = size - 2;
         int diff = end - start;
         float step = (float) diff / (float) (size - 1);
         int index = 1;
         float val = start;
         for (int i = 0; i < steps; i++) {
            val += step;
            values[index] = (int) val;
            index++;
         }
      }
      return values;
   }

   public static boolean isNullOrLengthSmaller(int[] ar, int minLength) {
      if (ar == null || ar.length < minLength)
         return true;
      return false;
   }

   public static int readInt24BE(byte[] ar, int index) {
      int value = (ar[index++] & 0xFF) << 16;
      value |= (ar[index++] & 0xFF) << 8;
      value |= (ar[index] & 0xFF) << 0;
      return value;
   }

   public static int readInt24LE(byte[] ar, int index) {
      int value = (ar[index++] & 0xff) << 0;
      value |= (ar[index++] & 0xFF) << 8;
      value |= (ar[index] & 0xFF) << 16;
      return value;
   }

   /**
    * 
    * @param ar
    * @param index
    * @return
    */
   public static long readIntBEUnsigned(byte[] ar, int index) {
      long value = (ar[index++] & 0xFF) << 24;
      value |= (ar[index++] & 0xFF) << 16;
      value |= (ar[index++] & 0xFF) << 8;
      value |= (ar[index] & 0xFF) << 0;
      return value;
   }

   public static int readIntBE(byte[] ar, int index) {
      int value = (ar[index++] & 0xFF) << 24;
      value |= (ar[index++] & 0xFF) << 16;
      value |= (ar[index++] & 0xFF) << 8;
      value |= (ar[index] & 0xFF) << 0;
      return value;
   }

   public static int readIntLE(byte[] ar, int index) {
      int value = (ar[index++] & 0xff) << 0;
      value |= (ar[index++] & 0xFF) << 8;
      value |= (ar[index++] & 0xFF) << 16;
      value |= (ar[index] & 0xFF) << 24;
      return value;
   }

   public static int readIntXXLE(byte[] ar, int i, int v) {
      int base = 0;
      int val = 0;
      for (int j = 0; j < v; j++) {
         val += ((ar[j + i] & 0xff) << base);
         base += 8;
      }
      return val;
   }

   /**
    * 1 2 3 4 5 becomes 5 4 3 2 1
    * @param b
    */
   public static void reverse(int[] b) {
      int left = 0; // index of leftmost element
      int right = b.length - 1; // index of rightmost element
      while (left < right) {
         // exchange the left and right elements
         int temp = b[left];
         b[left] = b[right];
         b[right] = temp;
         // move the bounds toward the center
         left++;
         right--;
      }
   }

   public static void reverse(int[] b, int offset, int len) {
      int left = offset; // index of leftmost element
      int right = offset + len - 1; // index of rightmost element
      while (left < right) {
         // exchange the left and right elements
         int temp = b[left];
         b[left] = b[right];
         b[right] = temp;
         // move the bounds toward the center
         left++;
         right--;
      }
   }

   private static void search(int max, int diff, int i, int[] workvalues, boolean[] finished, IntBuffer vals, IntBuffer index, int sum) {
      if (diff < 0)
         throw new RuntimeException();
      if (diff == 0) {
         vals.addInt(workvalues[i]);
         index.addInt(i);
         finished[i] = true;
         return;
      } else {
         //diff is smaller. add it and try to find a new one
         vals.addInt(workvalues[i]);
         index.addInt(i);
         finished[i] = true;
         //find
         int res = getNonUsedVal(workvalues, finished, diff, i);
         if (res >= 0) {
            sum = sum + workvalues[res];
            int newdiff = max - sum;
            search(max, newdiff, res, workvalues, finished, vals, index, sum);
         }
      }
   }

   /**
    * shift only if start < end
    * @param ar
    * @param shiftsize <0 for shifting down
    * @param start index value starts at 0. inclusive
    * @param end index value inclusive
    * @param erase pad the hole with 0s
    */
   public static void shiftInt(int[] ar, int shiftsize, int start, int end, boolean erase) {
      if (start > end)
         return;
      if (shiftsize < 0)
         shiftIntDown(ar, 0 - shiftsize, start, end, erase);
      else
         shiftIntUp(ar, shiftsize, start, end, erase);
   }

   /**
    * 
    * 
    * @param ar array to shift down
    * @param shiftsize number of units to go down
    * @param start start index will go down shiftsize
    * @param end end index will be the last to be shifted
    * @param erase
    */
   public static void shiftIntDown(int[] ar, int shiftsize, int start, int end, boolean erase) {
      for (int i = start; i <= end; i++) {
         if (i - shiftsize >= 0) {
            ar[i - shiftsize] = ar[i];
         }
      }
      if (erase) {
         fill(ar, 0, end - shiftsize + 1, end);
      }
   }

   /**
    * 
    * @param ar
    * @param shiftsize number of jummps up in the array
    * @param start included in the shift
    * @param end included in the shift
    * @param erase erase data liberated
    * @throws ArrayIndexOutOfBoundsException if ar is not big enough for end+ shiftsize
    */
   public static void shiftIntUp(int[] ar, int shiftsize, int start, int end, boolean erase) {
      for (int i = end; i >= start; i--) {
         if (i + shiftsize < ar.length) {
            ar[i + shiftsize] = ar[i];
         }
      }
      if (erase) {
         fill(ar, 0, start, start + shiftsize - 1);
      }
   }

   public static void shiftUp(int[] ds) {
      for (int i = ds.length - 1; i > 0; i--) {
         ds[i] = ds[i - 1];
      }
      ds[0] = 0;
   }

   /**
    * pre: array is full, all elements are valid integers (not null)
    * post: array is sorted in ascending order (lowest to highest)
    * @author Pavel Yermalenkau
    * @param array
    * @return
    */
   public static int[] sortMerge(int array[]) {
      // if the array has more than 1 element, we need to split it and merge the sorted halves
      if (array.length > 1) {
         // number of elements in sub-array 1
         // if odd, sub-array 1 has the smaller half of the elements
         // e.g. if 7 elements total, sub-array 1 will have 3, and sub-array 2 will have 4
         int elementsInA1 = array.length / 2;
         // since we want an even split, we initialize the length of sub-array 2 to
         // equal the length of sub-array 1
         int elementsInA2 = elementsInA1;
         // if the array has an odd number of elements, let the second half take the extra one
         // see note (1)
         if ((array.length % 2) == 1)
            elementsInA2 += 1;
         // declare and initialize the two arrays once we've determined their sizes
         int arr1[] = new int[elementsInA1];
         int arr2[] = new int[elementsInA2];
         // copy the first part of 'array' into 'arr1', causing arr1 to become full
         for (int i = 0; i < elementsInA1; i++)
            arr1[i] = array[i];
         // copy the remaining elements of 'array' into 'arr2', causing arr2 to become full
         for (int i = elementsInA1; i < elementsInA1 + elementsInA2; i++)
            arr2[i - elementsInA1] = array[i];
         // recursively call mergeSort on each of the two sub-arrays that we've just created
         // note: when mergeSort returns, arr1 and arr2 will both be sorted!
         // it's not magic, the merging is done below, that's how mergesort works :)
         arr1 = sortMerge(arr1);
         arr2 = sortMerge(arr2);

         // the three variables below are indexes that we'll need for merging
         // [i] stores the index of the main array. it will be used to let us
         // know where to place the smallest element from the two sub-arrays.
         // [j] stores the index of which element from arr1 is currently being compared
         // [k] stores the index of which element from arr2 is currently being compared
         int i = 0, j = 0, k = 0;
         // the below loop will run until one of the sub-arrays becomes empty
         // in my implementation, it means until the index equals the length of the sub-array
         while (arr1.length != j && arr2.length != k) {
            // if the current element of arr1 is less than current element of arr2
            if (arr1[j] < arr2[k]) {
               // copy the current element of arr1 into the final array
               array[i] = arr1[j];
               // increase the index of the final array to avoid replacing the element
               // which we've just added
               i++;
               // increase the index of arr1 to avoid comparing the element
               // which we've just added
               j++;
            }
            // if the current element of arr2 is less than current element of arr1
            else {
               // copy the current element of arr1 into the final array
               array[i] = arr2[k];
               // increase the index of the final array to avoid replacing the element
               // which we've just added
               i++;
               // increase the index of arr2 to avoid comparing the element
               // which we've just added
               k++;
            }
         }
         // at this point, one of the sub-arrays has been exhausted and there are no more
         // elements in it to compare. this means that all the elements in the remaining
         // array are the highest (and sorted), so it's safe to copy them all into the
         // final array.
         while (arr1.length != j) {
            array[i] = arr1[j];
            i++;
            j++;
         }
         while (arr2.length != k) {
            array[i] = arr2[k];
            i++;
            k++;
         }
      }
      // return the sorted array to the caller of the function
      return array;
   }

   /**
    * Take a number apart into its digits.
    * <li>123 will return [3] [2] [1] in base 10
    * <li>75 will return  [5] [7] [0]  in base 10
    * @param number the number to take apart into digits.
    * @param maxDigits maximum number of digits you wish returned.
    * @param base usually 10, which number base to use.
    * @return byte[] containing digits 0..base-1, least significant first,
    * Note it does not return characters, but signed byte numbers.
    * So if one is interested in 0,1,2,3,4,5,6,7,8,9 values, this method is better
    * than using String.charAt
    */
   public static byte[] splitIntoDigits(long number, int maxDigits, int base) {
      // byte array, least significant digit first, initialised to zeros.
      byte[] digits = new byte[maxDigits];
      // work right to left, peeling off digits.
      for (int i = 0; i < maxDigits; i++) {
         //when zero.. n
         if (number == 0) {
            break;
         }
         digits[i] = (byte) (number % base);
         number /= base;
      }
      return digits;
   }

   public static int sum(int[] is) {
      int sum = 0;
      for (int i = 0; i < is.length; i++) {
         sum += is[i];
      }
      return sum;
   }

   /**
    * Write val to array at index, the high order byte first
    * @param arr
    * @param index
    * @param val
    */
   public static void writeInt24BE(byte[] arr, int index, int val) {
      arr[index++] = (byte) ((val >>> 16) & 0xFF);
      arr[index++] = (byte) ((val >>> 8) & 0xFF);
      arr[index] = (byte) ((val >>> 0) & 0xFF);
   }

   public static void writeInt24LE(byte[] arr, int index, int val) {
      arr[index++] = (byte) ((val >>> 0) & 0xFF);
      arr[index++] = (byte) ((val >>> 8) & 0xFF);
      arr[index] = (byte) ((val >>> 16) & 0xFF);
   }

   /**
    * Write val to array at index, the high order byte first
    * @param arr
    * @param index
    * @param val
    */
   public static void writeIntBE(byte[] arr, int index, int val) {
      arr[index++] = (byte) ((val >>> 24) & 0xFF);
      arr[index++] = (byte) ((val >>> 16) & 0xFF);
      arr[index++] = (byte) ((val >>> 8) & 0xFF);
      arr[index] = (byte) ((val >>> 0) & 0xFF);
   }

   public static void writeIntBEUnsigned(byte[] arr, int index, long val) {
      arr[index++] = (byte) ((val >>> 24) & 0xFF);
      arr[index++] = (byte) ((val >>> 16) & 0xFF);
      arr[index++] = (byte) ((val >>> 8) & 0xFF);
      arr[index] = (byte) ((val >>> 0) & 0xFF);
   }

   /**
    * Write val to array at index, the low order byte first.
    * <br>
    * <br>
    * Its the "natural" order.
    * @param arr
    * @param index
    * @param val
    */
   public static void writeIntLE(byte[] arr, int index, int val) {
      arr[index++] = (byte) ((val >>> 0) & 0xFF);
      arr[index++] = (byte) ((val >>> 8) & 0xFF);
      arr[index++] = (byte) ((val >>> 16) & 0xFF);
      arr[index] = (byte) ((val >>> 24) & 0xFF);
   }

   public static void writeIntXXLE(byte[] arr, int index, int v, int byteSize) {
      int base = 0;
      for (int i = 0; i < byteSize; i++) {
         arr[i + index] = (byte) ((v >>> base) & 0xFF);
         base += 8;
      }
   }

   private UCtx uc;

   public IntUtils(UCtx uc) {
      this.uc = uc;
   }

   /**
    * Add the value to array according to type
    * If using
    * When array is null, create a new array
    * @param value
    * @param key
    * @param unique when true. check for duplicates
    * @param type type
    * @return
    */
   public int[] addIntToArray(int value, int[] array, boolean unique, int type) {
      if (array == null) {
         return new int[] { value };
      }
      if (type == ITIS_ARRAY_ORDER_0_APPEND_SUFFIX) {
         return addIntToEndOf(value, array, unique);
      } else if (type == ITIS_ARRAY_ORDER_2_ASC) {
         return addOrderAsc(value, array, unique);
      } else if (type == ITIS_ARRAY_ORDER_3_DESC) {
         return addOrderDesc(value, array, unique);
      } else {
         return addIntToFrontOf(value, array, unique);
      }
   }

   public int[] addIntToEndOf(int value, int[] array, boolean isUnique) {
      if (isUnique) {
         if (contains(array, value)) {
            return array;
         }
      }
      return addIntToEndOf(array, value);
   }

   /**
    * Increase array by 1 and add the integer 
    * @param ar
    * @param id
    * @return
    */
   public int[] addIntToEndOf(int[] ar, int id) {
      ar = uc.getMem().increaseCapacity(ar, 1);
      ar[ar.length - 1] = id;
      return ar;
   }

   public int[] addIntToFrontOf(int value, int[] array, boolean isUnique) {
      if (isUnique) {
         if (contains(array, value)) {
            return array;
         }
      }
      return addIntToFrontOf(array, value);
   }

   /**
    * Append value in position 0
    * @param ar
    * @param value
    * @return
    */
   public int[] addIntToFrontOf(int[] ar, int value) {
      int[] d = uc.getMem().increaseCapacity(ar, 1);
      for (int i = 0; i < ar.length; i++) {
         d[i + 1] = ar[i];
      }
      d[0] = value;
      return d;
   }

   public int[] addOrderAsc(int value, int[] array, boolean isUnique) {
      return addOrderedAscIntLogn(array, value, isUnique);
   }

   public int[] addOrderDesc(int value, int[] array, boolean isUnique) {
      return addOrderedDescIntLogn(array, value, isUnique);
   }

   public int[] addOrderedAscIntLogn(int[] ints, int value, boolean unique) {
      int index = getOrderIndexAsc(ints, value, unique, 0, ints.length);
      if (index != -1) {
         return insertIntAtIndex(ints, value, index);
      }
      return ints;
   }

   /**
    * Starts looking at offset. 
    * <br>
    * <br>
    * Result is random if value are not correctly sorted in ascending order in the array in the interval defined
    * by offset and len.
    * <br>
    * <br>
    * Speed complexity is log(n) where is the number of integers in the virtual array (starts at index offset and ends at index offset+arraylen-1)
    * <br>
    * <br>
    * It is the responsability of the caller to check for existing buffer space.
    * <br>
    * <br>
    * @param ints
    * @param value value to be added
    * @param unique when true, do not add duplicates
    * @param offset where to start looking for inserting
    * @param len tells the end of the ar. look until offset +len -1
    * @return the same array reference with end shifted + 1. value at offset + len is erased.
    * 
    */
   public boolean addOrderedAscIntLognBuffer(int[] ints, int value, boolean unique, int offset, int len) {
      int mid = getOrderIndexAsc(ints, value, unique, offset, len);
      return addOrderedBufferIntoIt(ints, value, unique, offset, len, mid);
   }

   private boolean addOrderedBufferIntoIt(int[] ints, int value, boolean unique, int offset, int len, int mid) {
      if (mid == -1) {
         return false;
      }
      if (unique && ints[mid] == value && len != 0) {
         //when len is zero, we cannot vouch for what was in the int array
         return false;
      }
      shiftIntUp(ints, 1, mid, offset + len, false);
      ints[mid] = value;
      return true;
   }

   public int[] addOrderedDescIntLogn(int[] ints, int value, boolean unique) {
      int index = getOrderIndexDesc(ints, value, unique, 0, ints.length);
      if (index != -1) {
         return insertIntAtIndex(ints, value, index);
      }
      return ints;
   }

   public int[] clone(int[] ar) {
      if (ar == null) {
         return null;
      }
      int len = ar.length;
      int[] clone = new int[len];
      for (int i = 0; i < len; i++) {
         clone[i] = ar[i];
      }
      return clone;
   }

   public void debugAlone(int[] ar, PrintStream ps, String sep) {
      for (int i = 0; i < ar.length; i++) {
         if (i != 0)
            ps.print(sep);
         ps.print(ar[i]);
      }
   }

   public boolean equals(int[] is, int[] array) {
      if (is.length != array.length)
         return false;
      for (int i = 0; i < array.length; i++) {
         if (is[i] != array[i])
            return false;
      }
      return true;
   }

   /**
    * Create an array with startkey at 0 and endkey at length-1
    * <br>
    * Its size will be endKey - startkey + 1
    * @param startkey
    * @param endkey
    * @return eg [10 11 12 13] for 10,13
    */
   public int[] getGeneratedInterval(int startkey, int endkey) {
      int start = 0;
      int diff = 0;
      if (startkey > endkey) {
         diff = startkey - endkey + 1;
         start = endkey;
      } else {
         diff = endkey - startkey + 1;
         start = startkey;
      }
      int[] keys = new int[diff];
      for (int i = 0; i < diff; i++) {
         keys[i] = start + i;
      }
      return keys;
   }

   public int[] getConcat(int[] ar1, int[] ar2) {
      int[] ar = uc.getMem().createIntArray(ar1.length + ar2.length);
      int count = 0;
      for (int i = 0; i < ar1.length; i++) {
         ar[i] = ar1[i];
      }
      count = ar1.length;
      for (int i = 0; i < ar2.length; i++) {
         ar[count + i] = ar2[i];
      }
      return ar;
   }

   /**
    * Returns an array with integers of a1 and those from a2 that are not in a1.
    * <br>
    * <br>
    * @param a1 no duplicates, should be the biggest
    * @param a2 may have duplicates
    * @return unique int that are in a1 OR in a2
    */
   public int[] getUnion(int[] a1, int[] a2) {
      int[] ar = uc.getMem().createIntArray(a1.length + a2.length);
      System.arraycopy(a1, 0, ar, 0, a1.length);
      int count = a1.length;
      for (int i = 0; i < a2.length; i++) {
         if (!contains(ar, a2[i])) {
            ar[count] = a2[i];
            count++;
         }
      }
      return uc.getMem().resizeTo(ar, count);
   }

   /**
    * Increase
    * @param ints
    * @param value
    * @param index
    * @return
    */
   public int[] insertIntAtIndex(int[] ints, int value, int index) {
      int[] d = uc.getMem().increaseCapacity(ints, 1);
      for (int i = 0; i < index; i++) {
         d[i] = ints[i];
      }
      d[index] = value;
      for (int i = index + 1; i < d.length; i++) {
         d[i] = ints[i - 1];
      }
      return d;
   }

   public int[][][] minize(int[] values) {
      return minize(values, getMax(values, 0, values.length));
   }

   /**
    * Arrange values in the array so that together they total max or less
    * @param values
    * @param max value bigger than RunTimeexception returned
    * @return a minimum big sized array where values inside total <= max
    * with index[0][0] the biggest value with only one element
    * we also need a mapping between the index of a value in values
    * to the index in the results
    * int[1] returns the index in the initial int[] array
    */
   public int[][][] minize(int[] values, int max) {
      int len = values.length;
      int[] workvalues = clone(values);
      int[] map = sortMapped(workvalues, 0, len);
      IntBuffer[] vals = new IntBuffer[len];
      //index of a val in the sorted workvalue array
      IntBuffer[] indexes = new IntBuffer[len];
      int count = 0;
      boolean[] finished = new boolean[len];
      for (int i = len - 1; i >= 0; i--) {
         if (!finished[i]) {
            IntBuffer ivalues = new IntBuffer(uc);
            IntBuffer iindex = new IntBuffer(uc);
            int diff = max - workvalues[i];
            search(max, diff, i, workvalues, finished, ivalues, iindex, workvalues[i]);
            vals[count] = ivalues;
            indexes[count] = iindex;
            count++;
         }

      }
      int[][][] fina = new int[2][count][];
      int[] nmap = swapIndex(map);
      for (int i = 0; i < count; i++) {
         fina[0][i] = vals[i].getIntsClonedTrimmed();
         int[] index = indexes[i].getIntsClonedTrimmed();
         for (int j = 0; j < index.length; j++) {
            index[j] = nmap[index[j]];
         }
         fina[1][i] = index;
      }
      return fina;
   }

   /**
    * Patch the int values on 1 byte. First order then all 
    * <br>
    * <br>
    * Patch protocol: first value tells how much contiguous values there are. Next value gives the starting value
    * [0] [1] = no value or null
    * [1] [1] = 1
    * [2] [1] = 1,2
    * [3] [2] = 2,3,4
    * @param array duplicates allowed? yes. method will remove them
    * @param offset
    * @param len
    * @return
    */
   public int[] patch(int[] v, int offset, int len, int maxContiguous) {
      sortBasicAscending(v, offset, len);
      //create patches
      IntBuffer ib = new IntBuffer(uc, len * 2);
      //algo initialization
      int contiguous = 1;
      int previous = v[offset];
      //algo loop
      for (int i = offset + 1; i < len; i++) {
         int current = v[i];
         if (current == previous) {
            continue;
         }
         if (current == previous + 1) {
            if (contiguous == maxContiguous) {
               ib.addInt(contiguous);
               ib.addInt(previous - (contiguous - 1));
               contiguous = 1;
            } else {
               contiguous++;
            }
         } else {
            //add the previous
            ib.addInt(contiguous);
            ib.addInt(previous - (contiguous - 1));
            contiguous = 1;
         }
         previous = current;
      }
      //finish the algo by adding the last one
      ib.addInt(contiguous);
      ib.addInt(previous - (contiguous - 1));

      return ib.getIntsClonedTrimmed();
   }

   /**
    * Removes duplicates and zeros
    * @param ar
    * @return int[] array whose size is smaller by the amounts of duplicate
    */
   public int[] removeDuplicates(int[] ar) {
      int[] r = uc.getMem().createIntArray(ar.length);
      int count = 0;
      for (int i = 0; i < r.length; i++) {
         if (!contains(r, ar[i])) {
            r[count] = ar[i];
         }
      }
      return uc.getMem().resizeTo(ar, count);
   }

   /**
    * Make the array smaller
    * @param ar
    * @param index
    * @return
    */
   public int[] removeIndex(int[] ar, int index) {
      int[] newi = uc.getMem().createIntArray(ar.length - 1);
      int count = 0;
      for (int i = 0; i < ar.length; i++) {
         if (i != index) {
            newi[count] = ar[i];
            count++;
         }
      }
      return newi;
   }

   /**
    * from offset to offset+len-1, sort the integers in ASC order
    * @param array
    * @param offset
    * @param len
    */
   public void sortBasicAscending(int[] array, int offset, int len) {
      int f = offset + len;
      boolean asc = true;
      int val = 0;
      int tempint = 0;
      for (int i = offset; i < f; i++) {
         for (int j = i + 1; j < f; j++) {
            //-1 if i < j
            val = (array[i] > array[j]) ? 1 : -1;
            if ((asc && val > 0) || (!asc && val < 0)) {
               tempint = array[i];
               array[i] = array[j];
               array[j] = tempint;
            }
         }
      }
   }

   /**
    * sort and map the old offset to new offset
    * @param array
    * @param offset
    * @param len
    * @return
    */
   public int[] sortMapped(int[] array, int offset, int len) {
      int f = offset + len;
      boolean asc = true;
      int val = 0;
      int tempint = 0;
      int tempi = 0;
      int[] map = new int[len];
      //keeps track for int[current i] = old index
      int[] map2 = new int[len];
      for (int i = 0; i < map.length; i++) {
         map2[i] = i;
      }
      for (int i = offset; i < f; i++) {
         for (int j = i + 1; j < f; j++) {
            //-1 if i < j
            val = (array[i] > array[j]) ? 1 : -1;
            if ((asc && val > 0) || (!asc && val < 0)) {
               tempint = array[i];
               array[i] = array[j];
               array[j] = tempint;

               //deal with the index. no relation to the values
               // i was swapped to j. keep the original index swapping it to i to j and vice versa
               tempi = map2[i];
               map2[i] = map2[j];
               map2[j] = tempi;

            }
         }
      }
      for (int i = 0; i < map2.length; i++) {
         map[map2[i]] = i;
      }
      return map;
   }

   /**
    * only valid is integers in array are unique and smaller than length
    * @param ar
    * @return
    * @throws ArrayIndexOutOfBoundsException if data in ar
    */
   public int[] swapIndex(int[] ar) {
      int[] arn = uc.getMem().createIntArray(ar.length);
      for (int i = 0; i < ar.length; i++) {
         arn[ar[i]] = i;
      }
      return arn;
   }

   //#mdebug
   public String toString() {
      return Dctx.toString(this);
   }

   public void toString(Dctx dc) {
      dc.root(this, "IntUtils");
   }

   public String toString1Line() {
      return Dctx.toString1Line(this);
   }

   public void toString1Line(Dctx dc) {
      dc.root1Line(this, "IntUtils");
   }

   public UCtx toStringGetUCtx() {
      return uc;
   }

   public void toStringIntArray(Dctx dc, int[] ar) {
      toStringIntArray(dc, ar, ",");
   }

   public void toStringIntArray(Dctx dc, int[] ar, String sep) {
      if (ar == null) {
         dc.append("null");
      } else {
         for (int i = 0; i < ar.length; i++) {
            dc.append(i);
            dc.append("=");
            dc.append(ar[i]);
            dc.append(sep);
         }
      }
   }

   public void toStringIntArray(Dctx dc, String title, int[] ar) {
      toStringIntArray(dc, ar, ",");
   }

   public String toStringIntArray(int[] ar) {
      Dctx dc = new Dctx(uc);
      toStringIntArray(dc, ar, ",");
      return dc.toString();
   }

   public void toStringIntArray(int[] ar, StringBBuilder ps, String sep) {
      if (ar == null) {
         ps.append("null");
      } else {
         for (int i = 0; i < ar.length; i++) {
            ps.append(i + "=" + ar[i] + sep);
         }
      }
   }

   public void toStringIntArray1Line(Dctx dc, String title, int[] ar, String sep) {
      dc.append(title);
      dc.append("=");
      if (ar == null) {
         dc.append("null");
      } else {
         for (int i = 0; i < ar.length; i++) {
            if (i != 0) {
               dc.append(sep);
            }
            dc.append(ar[i]);
         }
      }
   }

   /**
    * 
    * @param ar
    * @param offset
    * @param len
    * @param sep
    * @param max maximum chars
    * @return
    */
   public String debugString(byte[] ar, String sep, int max) {
      if (ar == null) {
         return "null";
      }
      StringBBuilder sb = new StringBBuilder(uc);
      int len = ar.length;
      sb.append("[#" + len + "]");
      if (len < max) {
         max = len;
      }
      for (int i = 0; i < max; i++) {
         if (i != 0)
            sb.append(sep);
         sb.append(ar[i]);
      }
      return sb.toString();
   }

   public void debugAlone(int[] ar, Dctx ps, String sep) {
      for (int i = 0; i < ar.length; i++) {
         if (i != 0)
            ps.append(sep);
         ps.append(ar[i]);
      }
   }

   public String debugString(byte[] ar, int offset, int len, String sep) {
      if (ar == null) {
         return "null";
      }
      StringBBuilder sb = new StringBBuilder(uc);
      for (int i = offset; i < offset + len; i++) {
         if (i != 0)
            sb.append(sep);
         sb.append(ar[i]);
      }
      return sb.toString();
   }

   public String debugString(int[] ar) {
      if (ar == null) {
         return "null";
      }
      StringBBuilder sb = new StringBBuilder(uc);
      for (int i = 0; i < ar.length; i++) {
         if (i != 0)
            sb.append(",");
         sb.append(ar[i]);
      }
      return sb.toString();
   }
   //#enddebug
}
