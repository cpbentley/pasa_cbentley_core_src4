/*
 * (c) 2018-2020 Charles-Philip Bentley
 * This code is licensed under MIT license (see LICENSE.txt for details)
 */
package pasa.cbentley.core.src4.utils;

import pasa.cbentley.core.src4.ctx.UCtx;
import pasa.cbentley.core.src4.interfaces.IFactoryBase;
import pasa.cbentley.core.src4.structs.IntToObjects;
import pasa.cbentley.core.src4.structs.IntToStrings;

/**
 * 
 * @author Charles Bentley
 *
 */
public class ArrayUtils {

   /**
    * Reference equality
    * @param ar
    * @param i
    * @return
    */
   public static boolean contains(Object[] ar, Object i) {
      for (int j = 0; j < ar.length; j++) {
         if (ar[j] == i)
            return true;
      }
      return false;
   }

   public static int containsInt(Object[] ar, Object i) {
      for (int j = 0; j < ar.length; j++) {
         if (ar[j] == i)
            return j;
      }
      return -1;
   }

   public static int countNulls(Object[] ar) {
      int count = 0;
      for (int i = 0; i < ar.length; i++) {
         if (ar[i] == null)
            count++;
      }
      return count;
   }

   /**
    * Fills array [start,end] with filler
    * @param ar
    * @param filler
    * @param start included 
    * @param end included
    */
   public static void fill(Object[] ar, Object filler, int start, int end) {
      for (int i = start; i <= end; i++) {
         ar[i] = filler;
      }
   }

   /**
    *  { 1 , 2, 3 , 4 } becomes { 2 , 3 ,4 ,1 }
    * @param ar
    * @param offset
    * @param len
    */
   public static void rotateLeft(Object[] ar, int offset, int len) {
      int start = offset;
      int end = offset + len - 1;
      Object o = ar[start];
      for (int i = start; i < end; i++) {
         ar[i] = ar[i + 1];
      }
      ar[end] = o;
   }

   /**
    *  { 1 , 2, 3 , 4 } becomes { 4 , 1 ,2 ,3 }
    * @param ar
    * @param offset
    * @param len
    */
   public static void rotateRight(Object[] ar, int offset, int len) {
      int start = offset + len - 1;
      int end = offset;
      Object o = ar[start];
      for (int i = start; i > end; i--) {
         ar[i] = ar[i - 1];
      }
      ar[end] = o;
   }

   /**
    * shift only if start < end
    * @param ar
    * @param shiftsize <0 for shifting down
    * @param start index value starts at 0. inclusive
    * @param end index value inclusive
    * @param erase pad the hole with 0s
    */
   public static void shiftInt(Object[] ar, int shiftsize, int start, int end, boolean erase) {
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
   public static void shiftIntDown(Object[] ar, int shiftsize, int start, int end, boolean erase) {
      for (int i = start; i <= end; i++) {
         if (i - shiftsize >= 0) {
            ar[i - shiftsize] = ar[i];
         }
      }
      if (erase) {
         fill(ar, null, end - shiftsize + 1, end);
      }
   }

   /**
    * Dumb shifts, erases data in the shift destination.
    * 
    * Used by buffers where its known that data above the end point is not relevant
    * 
    * @param ar
    * @param shiftsize number of jummps up in the array
    * @param start included in the shift
    * @param end included in the shift
    * @param erase erase data liberated
    * @throws ArrayIndexOutOfBoundsException if ar is not big enough for end+ shiftsize
    */
   public static void shiftIntUp(Object[] ar, int shiftsize, int start, int end, boolean erase) {
      for (int i = end; i >= start; i--) {
         if (i + shiftsize < ar.length) {
            ar[i + shiftsize] = ar[i];
         }
      }
      if (erase) {
         fill(ar, null, start, start + shiftsize - 1);
      }
   }

   private final UCtx uc;

   public ArrayUtils(UCtx uc) {
      this.uc = uc;
   }

   /**
    * Copy src into destination starting at offset 0.
    * @param src
    * @param dest
    * @throws ArrayIndexOutOfBoundsException
    * @throws {@link NullPointerException} if any array is null
    */
   public void copy1stTo2nd(Object[] src, Object[] dest) {
      copyTo(src, 0, dest, 0, src.length);
   }

   /**
    * Copies an array from the specified source array, beginning at the specified position, to the specified position of the destination array.
    * @param src
    * @param srcPos
    * @param dst
    * @param dst_position
    * @param length
    */
   public void copyTo(Object src, int srcPos, Object dest, int destPos, int length) {
      System.arraycopy(src, srcPos, dest, destPos, length);
   }

   /**
    * 
    * @param strings
    * @param val
    * @return
    */
   public IntToStrings[] ensureCapacity(IntToStrings[] strings, int val) {
      if (val >= strings.length) {
         IntToStrings[] news = new IntToStrings[val + 1];
         for (int i = 0; i < strings.length; i++) {
            news[i] = strings[i];
         }
         return news;
      }
      return strings;
   }

   /**
    * 
    * @param array
    * @return -1 if all null
    */
   public static int getFirstNotNullIndex(Object[] array) {
      for (int i = 0; i < array.length; i++) {
         if (array[i] != null) {
            return i;
         }
      }
      return -1;
   }

   public static int getFirstIndex(Object[] array, Object o) {
      for (int i = 0; i < array.length; i++) {
         if (array[i] != o) {
            return i;
         }
      }
      return -1;
   }

   /**
    * Index of first null value. 
    * @return -1 if none
    */
   public static int getFirstNullIndex(Object[] array) {
      for (int i = 0; i < array.length; i++) {
         if (array[i] == null) {
            return i;
         }
      }
      return -1;
   }

   /**
    * 
    * @param array
    * @return -1 if no nulls
    */
   public static int getLastNotNullIndex(Object[] array) {
      for (int i = array.length - 1; i >= 0; i--) {
         if (array[i] != null) {
            return i;
         }
      }
      return -1;
   }

   /**
    * Trim from front as soon as one reference is null
    * @param ar
    * @return
    */
   public Object[] getTrim(Object[] ar) {
      return getTrim(ar, null);
   }

   public Object[] getTrim(Object[] ar, IFactoryBase fac) {
      int count = ArrayUtils.getLastNotNullIndex(ar);
      if (count == -1) { //only nulls
         count = 0;
      }
      int size = count + 1;
      Object[] pa = null;
      if (fac == null) {
         pa = new Object[size];
      } else {
         pa = fac.createArray(size);
      }
      for (int i = 0; i < pa.length; i++) {
         pa[i] = ar[i];
      }
      return pa;
   }

   /**
    * Returns a copy of the array
    * Trim from the rear of the array. As soon as one reference is not null
    * @param ar
    * @return
    */
   public Object[] getTrimRear(Object[] ar) {
      int count = ArrayUtils.getLastNullIndex(ar);
      if (count == -1) {
         count = ar.length;
      }
      Object[] pa = new Object[count];
      for (int i = 0; i < pa.length; i++) {
         pa[i] = ar[i];
      }
      return pa;
   }

   public static int getLastNullIndex(Object[] array) {
      for (int i = array.length - 1; i >= 0; i--) {
         if (array[i] == null) {
            return i;
         }
      }
      return -1;
   }

   public IntToObjects[] increaseCapacity(IntToObjects[] ar, int increment) {
      //else increase array
      IntToObjects[] old = ar;
      IntToObjects[] news = new IntToObjects[old.length + increment];
      for (int i = 0; i < old.length; i++) {
         news[i] = old[i];
      }
      return news;
   }

   /**
    * Shift up and stops at.
    * No shift if start is higher and not compatible
    * @param ar
    * @param start must be
    * @param index
    */
   public void shiftUp(Object[] ar, int start, int index) {
      if (start < ar.length - 2) {
         for (int i = start; i >= index; i--) {
            ar[i + 1] = ar[i];
         }
      }
   }
}
