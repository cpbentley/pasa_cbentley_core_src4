/*
 * (c) 2018-2020 Charles-Philip Bentley
 * This code is licensed under MIT license (see LICENSE.txt for details)
 */
package pasa.cbentley.core.src4.utils;

import pasa.cbentley.core.src4.ctx.UCtx;

/**
 * Static methods are Universe wide.
 * <br>
 * 
 * @author Charles Bentley
 *
 */
public class LongUtils {

   /**
    * Read long value in byte array at position index, high byte last, low byte first Little Endian.
    * @param ar
    * @param index
    * @return
    */
   public static long readLongLE(byte[] ar, int index) {
      long value = (long) (ar[index++] & 0xFF) << 0;
      value |= (long) (ar[index++] & 0xFF) << 8;
      value |= (long) (ar[index++] & 0xFF) << 16;
      value |= (long) (ar[index++] & 0xFF) << 24;
      value |= (long) (ar[index++] & 0xFF) << 32;
      value |= (long) (ar[index++] & 0xFF) << 40;
      value |= (long) (ar[index++] & 0xFF) << 48;
      value |= (long) (ar[index++] & 0xFF) << 56;
      return value;
   }

   /**
    * Read long value in byte array at position index, high byte first, Big Endian.
    * @param ar
    * @param index
    * @return
    */
   public static long readLongBE(byte[] ar, int index) {
      long value = (long) (ar[index++] & 0xff) << 56;
      value |= (long) (ar[index++] & 0xFF) << 48;
      value |= (long) (ar[index++] & 0xFF) << 40;
      value |= (long) (ar[index++] & 0xFF) << 32;
      value |= (long) (ar[index++] & 0xFF) << 24;
      value |= (long) (ar[index++] & 0xFF) << 16;
      value |= (long) (ar[index++] & 0xFF) << 8;
      value |= (long) (ar[index++] & 0xFF) << 0;
      return value;
   }

   public static void writeLongBE(byte[] ar, int index, long lv) {
      ar[index++] = (byte) (lv >>> 56);
      ar[index++] = (byte) (lv >>> 48);
      ar[index++] = (byte) (lv >>> 40);
      ar[index++] = (byte) (lv >>> 32);
      ar[index++] = (byte) (lv >>> 24);
      ar[index++] = (byte) (lv >>> 16);
      ar[index++] = (byte) (lv >>> 8);
      ar[index++] = (byte) (lv >>> 0);
   }

   /**
    * Little Endian : low byte first, high byte last.
    * @param ar
    * @param index
    * @param lv
    */
   public static void writeLongLE(byte[] ar, int index, long lv) {
      ar[index++] = (byte) (lv >>> 0);
      ar[index++] = (byte) (lv >>> 8);
      ar[index++] = (byte) (lv >>> 16);
      ar[index++] = (byte) (lv >>> 24);
      ar[index++] = (byte) (lv >>> 32);
      ar[index++] = (byte) (lv >>> 40);
      ar[index++] = (byte) (lv >>> 48);
      ar[index++] = (byte) (lv >>> 56);
   }

   public static byte[] byteArrayBEFromLong(long v) {
      byte[] writeBuffer = new byte[8];
      writeLongBE(writeBuffer, 0, v);
      return writeBuffer;
   }

   public static byte[] byteArrayLEFromLong(long v) {
      byte[] writeBuffer = new byte[8];
      writeLongLE(writeBuffer, 0, v);
      return writeBuffer;
   }
   
   private UCtx uc;

   public LongUtils(UCtx uc) {
      this.uc = uc;
   }
}
