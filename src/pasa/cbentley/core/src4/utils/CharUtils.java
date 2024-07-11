/*
 * (c) 2018-2020 Charles-Philip Bentley
 * This code is licensed under MIT license (see LICENSE.txt for details)
 */
package pasa.cbentley.core.src4.utils;

import pasa.cbentley.core.src4.ctx.UCtx;

/**
 * Escape Sequence  Description
 * <li> \t  Insert a tab in the text at this point.
 * <li> \b  Insert a backspace in the text at this point.
 * <li> \n  Insert a newline in the text at this point.
 * <li> \r  Insert a carriage return in the text at this point.
 * <li> \f  Insert a form feed in the text at this point.
 * <li> \'  Insert a single quote character in the text at this point.
 * <li> \"  Insert a double quote character in the text at this point.
 * <li> \\  Insert a backslash character in the text at this point.
 * @author Charles Bentley
 *
 */
public class CharUtils {

   public static final int PLANE_0_EN = 0;

   public static final int PLANE_4_RU = 4;

   public static char buildCharFromLowByte(int bys, int padding) {
      int ch1 = padding << 8;
      int ch2 = (bys << 0) & 0xFF;
      return (char) (ch1 + ch2);
   }

   /**
    * 
    * @param bys
    * @param padding 0x04 for russian chars
    * @return
    */
   public static char[] buildCharsFromLowByes(byte[] bys, int padding) {
      char[] chars = new char[bys.length];
      for (int i = 0; i < bys.length; i++) {
         int ch1 = padding << 8;
         int ch2 = (bys[i] << 0) & 0xFF;
         chars[i] = (char) (ch1 + ch2);
      }
      return chars;
   }

   /**
    * 
    * @param bys
    * @param padding 0x04 for russian chars
    * @return
    */
   public static char[] buildCharsFromLowByes(byte[] bys, int padding, int from, int to) {
      char[] chars = new char[to - from];
      for (int i = 0; i < chars.length; i++) {
         int ch1 = padding << 8;
         int ch2 = (bys[i + from] << 0) & 0xFF;
         chars[i] = (char) (ch1 + ch2);
      }
      return chars;
   }

   public static char[] buildCharsFromLowBytes(int[] bys, int padding, int from, int to) {
      char[] chars = new char[to - from];
      for (int i = 0; i < chars.length; i++) {
         int ch1 = padding << 8;
         int ch2 = (bys[i + from] << 0) & 0xFF;
         chars[i] = (char) (ch1 + ch2);
      }
      return chars;
   }

   public static String buildStringFromLowByes(byte[] bys, int padding) {
      return new String(buildCharsFromLowByes(bys, padding));
   }

   public static String buildStringFromLowByes(byte[] bys, int padding, int from, int to) {
      return new String(buildCharsFromLowByes(bys, padding, from, to));
   }

   public static String buildStringFromLowByes(int i, int pad) {
      return String.valueOf(buildCharFromLowByte(i, pad));
   }

   public static byte[] byteArrayBEFromChar(char c) {
      byte[] writeBuffer = new byte[2];
      writeShortBE(writeBuffer, 0, c);
      return writeBuffer;
   }

   public static byte[] byteArrayLEFromChar(char c) {
      byte[] writeBuffer = new byte[2];
      writeShortLE(writeBuffer, 0, c);
      return writeBuffer;
   }

   /**
    * Construct a character from id and plane.
    * 
    * @param id
    * @param plane
    * @return
    */
   public static char charFromLowByteInt(int id, int plane) {
      return (char) (((plane & 0xff) << 8) + ((id & 0xff) << 0));
   }

   /**
    * 
    * @param ar
    * @param c
    * @return true if c is inside the array
    */
   public static boolean contains(char[] ar, char c) {
      for (int i = ar.length - 1; i >= 0; i--) {
         if (c == ar[i]) {
            return true;
         }
      }
      return false;
   }

   /**
    * Fills array [start,end] with filler
    * @param ar
    * @param filler
    * @param start
    * @param end
    */
   public static void fill(char[] ar, char filler, int start, int end) {
      for (int i = start; i <= end; i++) {
         ar[i] = filler;
      }
   }

   public static int getFirstIndex(char c, char[] chars) {
      return getFirstIndex(c, chars, 0, chars.length);
   }

   /**
    * Returns the index of first occurence of char.
    * <br>
    * Index is absolute. if value is at offset, it returns offset.
    * @param val
    * @param ar cannot be null
    * @param offset
    * @param len
    * @return -1 if not found
    */
   public static int getFirstIndex(char c, char[] chars, int offset, int len) {
      for (int j = offset; j < offset + len; j++) {
         if (chars[j] == c)
            return j;
      }
      return -1;
   }

   public static int getFirstIndex(String str, char[] chars) {
      return getFirstIndex(str, chars, 0, chars.length);
   }

   public static int getFirstIndex(String str, char[] chars, int offset) {
      return getFirstIndex(str, chars, offset, chars.length);
   }

   /**
    * 
    * @param str -1 if null or ""
    * @param chars cannot be null
    * @param offset
    * @param len
    * @return
    */
   public static int getFirstIndex(String str, char[] chars, int offset, int len) {
      if (str == null || str.equals("")) {
         return -1;
      }
      char c0 = str.charAt(0);
      int indexC0 = -1;
      int offsetSearch = offset;
      boolean isGoodCandidate = false;
      int strLen = str.length();
      do {
         indexC0 = CharUtils.getFirstIndex(c0, chars, offsetSearch, len);
         if (indexC0 == -1) {
            isGoodCandidate = false;
         } else {
            isGoodCandidate = true;
            int i = 1;
            while (isGoodCandidate && i < strLen) {
               char ci = str.charAt(i);
               int offseti = indexC0 + i;
               if (ci != chars[offseti]) {
                  //failed candidate
                  offsetSearch = offseti;
                  isGoodCandidate = false;
               }
               i += 1;
            }
         }
      } while (!isGoodCandidate && (indexC0 != -1 && offsetSearch < offset + len));

      return indexC0;
   }

   public static int getPlane(char c) {
      return (c >>> 8) & 0xFF;
   }

   public static int getV(char c) {
      return c & 0xFF;
   }

   public static boolean isEqual(char[] c, char[] b) {
      return isEqual(c, 0, c.length, b);
   }

   public static boolean isEqual(char[] src, int srcoffset, int srclen, char[] b) {
      if (srclen != b.length)
         return false;
      for (int i = 0; i < b.length; i++) {
         if (src[srcoffset + i] != b[i])
            return false;
      }
      return true;
   }

   /**
    * Read short value in byte array at position index.
    * <br>
    * Big Endian : high byte first, low byte last.
    * @param ar
    * @param index
    * @return
    */
   public static char readCharBE(byte[] ar, int index) {
      int value = (ar[index++] & 0xff) << 8;
      value |= (ar[index++] & 0xFF) << 0;
      return (char) value;
   }

   /**
    * Read short value in byte array at position index.
    * <br>
    * Little Endian : low byte first, high byte last. 
    * @param ar
    * @param index
    * @return
    */
   public static char readCharLE(byte[] ar, int index) {
      int value = (ar[index++] & 0xFF) << 0;
      value |= (ar[index++] & 0xFF) << 8;
      return (char) value;
   }

   /**
    * shift only if start < end
    * @param ar
    * @param shiftsize <0 for shifting down
    * @param start index value starts at 0. inclusive
    * @param end index value inclusive
    * @param erase pad the hole with 0s
    */
   public static void shiftChar(char[] ar, int shiftsize, int start, int end) {
      if (start > end) {
         return;
      }
      if (shiftsize < 0) {
         shiftCharDown(ar, 0 - shiftsize, start, end);
      } else {
         shiftCharUp(ar, shiftsize, start, end);
      }
   }

   /**
    * 
    * Dumb shifts down, erases data in the shift destination.
    * 
    * Used by buffers where its known that data above the end point is not relevant
    * 
    * @param ar
    * @param shiftsize number of jumps up in the array
    * @param start included in the shift
    * @param end included in the shift
    * @throws ArrayIndexOutOfBoundsException if ar is not big enough for end+ shiftsize
    */
   public static void shiftCharDown(char[] ar, int shiftsize, int start, int end) {
      for (int i = start; i <= end; i++) {
         int j = i - shiftsize;
         if (j >= 0) {
            ar[j] = ar[i];
         }
      }
   }

   /**
    * 
    * Dumb shifts, erases data in the shift destination.
    * 
    * Used by buffers where its known that data above the end point is not relevant
    * 
    * @param ar
    * @param shiftsize number of jumps up in the array. MUST be positive
    * @param start included in the shift
    * @param end included in the shift
    * @throws ArrayIndexOutOfBoundsException if ar is not big enough for end+ shiftsize
    */
   public static void shiftCharUp(char[] ar, int shiftsize, int start, int end) {
      for (int i = end; i >= start; i--) {
         if (i + shiftsize < ar.length) {
            ar[i + shiftsize] = ar[i];
         }
      }
   }

   /**
    * Big Endian : high byte first, low byte last.
    * @param ar
    * @param index
    * @param value
    */
   public static void writeShortBE(byte[] ar, int index, char c) {
      ar[index++] = (byte) (c >>> 8);
      ar[index] = (byte) (c >>> 0);
   }

   public static void writeShortBE(byte[] ar, int index, char[] cs) {
      for (int i = 0; i < cs.length; i++) {
         char c = cs[i];
         ar[index++] = (byte) (c >>> 8);
         ar[index++] = (byte) (c >>> 0);
      }
   }

   /**
    * Little Endian : low byte first, high byte last.
    * @param ar
    * @param index
    * @param value
    */
   public static void writeShortLE(byte[] ar, int index, char c) {
      ar[index++] = (byte) (c >>> 0);
      ar[index++] = (byte) (c >>> 8);
   }

   public static void writeShortLE(byte[] ar, int index, char[] cs) {
      for (int i = 0; i < cs.length; i++) {
         char c = cs[i];
         ar[index++] = (byte) (c >>> 0);
         ar[index++] = (byte) (c >>> 8);
      }
   }

   private char[] cyrChar;

   private UCtx   uc;

   public CharUtils(UCtx uc) {
      this.uc = uc;
   }

   /**
    * 
    * @param source
    * @param target
    * @return
    */
   public int compare(char[] source, char[] target) {
      for (int i = 0; i < source.length; i++) {
         if (target.length <= i)
            return 1;
         int r = compareChar(source[i], target[i]);
         if (r != 0) {
            return r;
         }
      }
      if (source.length == target.length) {
         return 0;
      } else {
         return -1;
      }
   }

   /**
    * 
    * @param source
    * @param target
    * @return
    */
   public int compareChar(char source, char target) {
      int cp1 = ((source >>> 8) & 0xFF);
      int cp2 = ((target >>> 8) & 0xFF);
      if (cp1 != cp2)
         return resultComp(cp1 - cp2);
      int c1 = (byte) ((source >>> 0) & 0xFF);
      int c2 = (byte) ((target >>> 0) & 0xFF);
      if (c1 != c2) {
         if (cp1 == 4 && (c1 == 81 || c2 == 81)) {
            if (c1 == 81) {
               int equivc1 = (getNonAccentuated(source) >>> 0) & 0xFF;
               if (c2 == equivc1)
                  return 1;
               // inverse because the yo is 81 in value, bigger than all
               return resultComp(equivc1 - c2);
            } else {
               int equivc2 = (getNonAccentuated(target) >>> 0) & 0xFF;
               if (c1 == equivc2)
                  return -1;
               return resultComp(c1 - equivc2);
            }
         }
         if (c1 > 0 && c2 > 0) {
            if (c1 != c2)
               return resultComp(c1 - c2);
         } else {
            char equivc1 = getNonAccentuated(source);
            char equivc2 = getNonAccentuated(target);
            if (c1 < 0 && c2 < 0) {
               if (equivc1 == equivc2) {
                  return resultComp(c1 - c2);
               } else {
                  return resultComp(equivc1 - equivc2);
               }
            } else {
               if (c2 > 0) {
                  if (equivc1 == c2) {
                     return 1;
                  } else {
                     return resultComp(equivc1 - c2);
                  }
               }
               if (c1 > 0) {
                  if (equivc2 == c1) {
                     return -1;
                  } else {
                     return resultComp(c1 - equivc2);
                  }
               }
            }
         }
      }
      return 0;
   }

   public byte[] getByteCharsIntLong(char[] cs, int coffset, int clen) {
      byte[] data = new byte[4 + clen * 2];
      writeCharsIntLong(data, 0, cs, coffset, clen);
      return data;
   }

   /**
    * 
    * @param bys
    * @param padding 0x04 for russian chars
    * @return
    */
   public char[] getCharsIntLong(byte[] data, int offset) {
      int numChars = IntUtils.readIntBE(data, offset);
      char[] chars = uc.getMem().createCharArray(numChars);
      int index = offset + 4;
      for (int i = 0; i < numChars; i++) {
         int ch1 = data[index++] << 8;
         int ch2 = data[index++] & 0xFF;
         chars[i] = (char) (ch1 + ch2);
      }
      return chars;
   }

   public char[] getCyrillicChar() {
      if (cyrChar == null) {
         cyrChar = new char[33];
         int ch1 = PLANE_4_RU << 8;
         int count = 0;
         for (int i = 48; i < 80; i++) {
            int ch2 = (i << 0) & 0xFF;
            cyrChar[count] = (char) (ch1 + ch2);
            count++;
         }
         int ch2 = (81 << 0) & 0xFF;
         cyrChar[32] = (char) (ch1 + ch2);
      }
      return cyrChar;
   }

   /**
    * Returns a non accentuated char.
    * <li> Latin é,è ê becomes e
    * For russian language remove yo to ye
    * @param a
    * @return
    */
   public char getNonAccentuated(char a) {
      int p = (a >>> 8) & 0xFF;
      int c = (byte) ((a >>> 0) & 0xFF);
      if (p == 4) {
         if (c == 81)
            return CharUtils.buildCharFromLowByte(53, PLANE_4_RU);
         return a;
      }
      return getNonAccentuatedPlane1(a);
   }

   public char getNonAccentuatedPlane1(char a) {
      int c = (byte) ((a >>> 0) & 0xFF);
      if (c > 0)
         return a;
      if (c == -25)
         return 'c';
      if (c == -21 || c == -22 || c == -23 || c == -24) {
         return 'e';
      }
      if (-32 <= c && c <= -27) {
         return 'a';
      }
      if (c == -20 || c == -19 || c == -18 || c == -17) {
         return 'i';
      }
      if (c == -15)
         return 'n';
      if (-14 <= c && c <= -10) {
         return 'o';
      }
      if (-7 <= c && c <= -4) {
         return 'u';
      }
      return a;
   }

   /**
    * Returns 0-9 values exclusively from plane 0
    * @param c
    * @return -1 if it does not have a numerical value
    */
   public int getNumericalValue(char c) {
      switch (c) {
         case '0':
            return 0;
         case '1':
            return 1;
         case '2':
            return 2;
         case '3':
            return 3;
         case '4':
            return 4;
         case '5':
            return 5;
         case '6':
            return 6;
         case '7':
            return 7;
         case '8':
            return 8;
         case '9':
            return 9;
         default:
            return -1;
      }
   }

   /**
    * This implementation considers a number
    * <br>
    * "0123456789"
    * Subclasses may change this by setting an implementation in {@link UCtx}
    * @param character
    * @return
    */
   public boolean isNumerical(char character) {
      switch (character) {
         case '0':
         case '1':
         case '2':
         case '3':
         case '4':
         case '5':
         case '6':
         case '7':
         case '8':
         case '9':
            return true;
         default:
            return false;
      }
   }

   /**
    * This implementation considers a whitespace
    * <br>
    * <li> '\t'
    * <li> '\n'
    * <li> ' '
    * <li> '\u000C'
    * <li> '\u001C'
    * <li> '\u001D'
    * <li> '\u001E'
    * <li> '\u001F'
    * Subclasses may change this by setting an implementation in {@link UCtx}
    * @param character
    * @return
    */
   public boolean isWhitespace(char character) {
      switch (character) {
         case '\t':
         case '\n':
         case ' ':
         case '\u000C':
         case '\u001C':
         case '\u001D':
         case '\u001E':
         case '\u001F':
            return true;
         default:
            return false;
      }
   }

   /**
    * When char is below a, returns a. if char is above z, returns z.
    * <br>
    * When char is accentuated, returns its non accentuated form
    * @param c
    * @return
    */
   public int mapZero(char c) {
      int plane = getPlane(c);
      int v = getV(c);
      switch (plane) {
         case PLANE_4_RU:
            // a=48 - я=79
            if (v <= 48) {
               return 0;
            } else if (v <= 79) {
               return v - 48;
            } else {
               return 79 - 48;
            }
         default:
            //case EN_PAD:
            // a = 97  z=122
            if (v <= 97) {
               return 0;
            } else if (v <= 122) {
               return v - 97;
            } else {
               return 122 - 97; //25 there is 26 letters
            }
      }
   }

   private int resultComp(int val) {
      if (val < 0)
         return -1;
      if (val > 0)
         return 1;
      return 0;
   }

   /**
    * Assume v is 0 based for lowercase letter a
    * @param v
    * @param plane
    * @return
    */
   public char unMapZero(int v, int plane) {
      switch (plane) {
         case PLANE_4_RU:
            // a = 48 - ya
            return CharUtils.charFromLowByteInt(v + 48, plane);
         default:
            //case EN_PAD:
            // a = 97  z=122
            return CharUtils.charFromLowByteInt(v + 97, plane);
      }
   }

   /**
    * Read by {@link CharUtils#getCharsIntLong(byte[], int)}
    * @param cs
    * @param coffset
    * @param clen
    * @param data
    * @param offset
    */
   public void writeCharsIntLong(byte[] data, int offset, char[] cs, int coffset, int clen) {
      IntUtils.writeIntBE(data, offset, clen);
      int index = offset + 4;
      for (int i = 0; i < clen; i++) {
         char c = cs[coffset + i];
         CharUtils.writeShortBE(data, index, c);
         index += 2;
      }
   }

}
