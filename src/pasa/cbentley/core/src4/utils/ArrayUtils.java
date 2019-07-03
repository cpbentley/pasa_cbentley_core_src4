package pasa.cbentley.core.src4.utils;

import pasa.cbentley.core.src4.ctx.UCtx;
import pasa.cbentley.core.src4.structs.IntToObjects;
import pasa.cbentley.core.src4.structs.IntToStrings;

/**
 * 
 * @author Charles Bentley
 *
 */
public class ArrayUtils {

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

   public IntToObjects[] increaseCapacity(IntToObjects[] ar, int increment) {
      //else increase array
      IntToObjects[] old = ar;
      IntToObjects[] news = new IntToObjects[old.length + increment];
      for (int i = 0; i < old.length; i++) {
         news[i] = old[i];
      }
      return news;
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
    * Index of first null value. 
    * @return -1 if none
    */
   public int getFirstNullIndex(Object[] array) {
      for (int i = 0; i < array.length; i++) {
         if (array[i] == null) {
            return i;
         }
      }
      return -1;
   }

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

   /**
    * 
    * @param array
    * @return -1 if all null
    */
   public int getFirstNotNullIndex(Object[] array) {
      for (int i = 0; i < array.length; i++) {
         if (array[i] != null) {
            return i;
         }
      }
      return -1;
   }

   public int getLastNullIndex(Object[] array) {
      for (int i = array.length - 1; i >= 0; i--) {
         if (array[i] == null) {
            return i;
         }
      }
      return -1;
   }

   public int getLastNotNullIndex(Object[] array) {
      for (int i = array.length - 1; i >= 0; i--) {
         if (array[i] != null) {
            return i;
         }
      }
      return -1;
   }
}
