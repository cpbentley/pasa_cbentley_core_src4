/*
 * (c) 2018-2020 Charles-Philip Bentley
 * This code is licensed under MIT license (see LICENSE.txt for details)
 */
package pasa.cbentley.core.src4.utils;

import pasa.cbentley.core.src4.ctx.UCtx;

public class ShortUtils {
   /**
    * Read short value in byte array at position index.
    * <br>
    * Little Endian : low byte first, high byte last. 
    * @param ar
    * @param index
    * @return
    */
   public static short readShortLESigned(byte[] ar, int index) {
      int value = (ar[index++] & 0xFF) << 0;
      value |= (ar[index] & 0xFF) << 8;
      return (short) value;
   }

   public static int readShortLEUnsigned(byte[] ar, int index) {
      int value = (ar[index++] & 0xFF) << 0;
      value |= (ar[index] & 0xFF) << 8;
      return value;
   }

   /**
    * Read short value in byte array at position index.
    * <br>
    * Big Endian : high byte first, low byte last.
    * @param ar
    * @param index
    * @return int since unsigned must take the level above
    */
   public static int readShortBEUnsigned(byte[] ar, int index) {
      int value = (ar[index++] & 0xff) << 8;
      value |= (ar[index] & 0xFF) << 0;
      return value;
   }

   /**
    * Big Endian : high byte first, low byte last.
    * @param ar
    * @param index
    * @param value
    */
   public static void writeShortBEUnsigned(byte[] ar, int index, int value) {
      ar[index++] = (byte) ((value >> 8) & 0xFF);
      ar[index] = (byte) ((value >> 0) & 0xFF);
   }

   public static short readShortBESigned(byte[] ar, int index) {
      int value = (ar[index++] & 0xff) << 8;
      value |= (ar[index] & 0xFF) << 0;
      return (short) value;
   }

   /**
    * Big Endian : high byte first, low byte last.
    * @param ar
    * @param index
    * @param value
    */
   public static void writeShortBESigned(byte[] ar, int index, int value) {
      ar[index++] = (byte) (value >>> 8);
      ar[index++] = (byte) (value >>> 0);
   }

   public static void writeShortLEUnsigned(byte[] ar, int index, int value) {
      ar[index++] = (byte) ((value >> 0) & 0xFF);
      ar[index] = (byte) ((value >> 8) & 0xFF);
   }

   /**
    * Little Endian : low byte first, high byte last.
    * @param ar
    * @param index
    * @param value
    */
   public static void writeShortLESigned(byte[] ar, int index, int value) {
      ar[index++] = (byte) (value >>> 0);
      ar[index++] = (byte) (value >>> 8);
   }

   public static byte[] byteArrayBEUnsignedFromShort(short v) {
      byte[] writeBuffer = new byte[2];
      writeShortBEUnsigned(writeBuffer, 0, v);
      return writeBuffer;
   }

   public static byte[] byteArrayBESignedFromShort(short v) {
      byte[] writeBuffer = new byte[2];
      writeShortBESigned(writeBuffer, 0, v);
      return writeBuffer;
   }

   public static byte[] byteArrayLEUnsignedFromShort(short v) {
      byte[] writeBuffer = new byte[2];
      writeShortLESigned(writeBuffer, 0, v);
      return writeBuffer;
   }

   public static byte[] byteArrayLESignedFromShort(short v) {
      byte[] writeBuffer = new byte[2];
      writeShortLESigned(writeBuffer, 0, v);
      return writeBuffer;
   }

   private UCtx uc;

   public ShortUtils(UCtx uc) {
      this.uc = uc;
   }
}
