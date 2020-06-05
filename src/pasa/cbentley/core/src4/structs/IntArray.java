/*
 * (c) 2018-2020 Charles-Philip Bentley
 * This code is licensed under MIT license (see LICENSE.txt for details)
 */
package pasa.cbentley.core.src4.structs;

/**
 * 
 * @author Charles Bentley
 *
 */
public class IntArray {

   private int[] array;

   private int   offset;

   private int   len;

   public IntArray(int[] array, int offset, int len) {
      super();
      this.array = array;
      this.offset = offset;
      this.len = len;
   }

   public int[] getArray() {
      return array;
   }

   public void setArray(int[] array) {
      this.array = array;
   }

   public int getOffset() {
      return offset;
   }
   
   public int[] getTrimmedCopy() {
      int[] ar = new int[len];
      for (int i = 0; i < len; i++) {
         ar[i] = array[offset + i];
      }
      return ar;
   }

   public void setOffset(int offset) {
      this.offset = offset;
   }

   public void incrementLen() {
      len++;
   }
   
   public int getLen() {
      return len;
   }

   public void setLen(int len) {
      this.len = len;
   }
   
   
}
