package pasa.cbentley.core.src4.utils;

import pasa.cbentley.core.src4.ctx.UCtx;


public class CharUtils {

   public static final int PLANE_0_EN = 0;

   public static final int PLANE_4_RU = 4;

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
    * Big Endian : high byte first, low byte last.
    * @param ar
    * @param index
    * @param value
    */
   public static void writeShortBE(byte[] ar, int index, char c) {
      ar[index++] = (byte) (c >>> 8);
      ar[index++] = (byte) (c >>> 0);
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

   private UCtx   uc;

   private char[] cyrChar;

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

   private int resultComp(int val) {
      if (val < 0)
         return -1;
      if (val > 0)
         return 1;
      return 0;
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

   public static int getPlane(char c) {
      return (c >>> 8) & 0xFF;
   }

   public static int getV(char c) {
      return c & 0xFF;
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

   public CharUtils(UCtx uc) {
      this.uc = uc;
   }

}
