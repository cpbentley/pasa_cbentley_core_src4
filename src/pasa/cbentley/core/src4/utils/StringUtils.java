package pasa.cbentley.core.src4.utils;

import pasa.cbentley.core.src4.ctx.UCtx;
import pasa.cbentley.core.src4.helpers.StringBBuilder;
import pasa.cbentley.core.src4.interfaces.IStrComparator;
import pasa.cbentley.core.src4.structs.IntBuffer;

/**
 * 
 * @author Charles Bentley
 *
 */
public class StringUtils {

   public static final int    EN_PAD   = 0;

   /**
    * Byte for russian characters in UTF-8
    */
   public static final int    RU_PAD   = 4;

   /**
    * 
    */
   public static final String UTF8_BOM = "\uFEFF";

   /**
    * Append the byte values to the string builder
    * @param ar
    * @param offset
    * @param len
    * @param sb
    * @param cols
    */
   public static void appendData(byte[] ar, int offset, int len, StringBBuilder sb, int cols) {
      if (offset >= ar.length)
         return;
      int count = 0;
      int end = offset + len;
      if (end > ar.length)
         end = ar.length;
      for (int i = offset; i < end; i++) {
         sb.append(" " + ar[i]);
         count++;
         if (count == cols) {
            sb.append('\n');
            count = 0;
         }
      }
      sb.append('\n');
   }

   /**
    * return true if all chars have the same plane
    * @param _data
    */
   public static boolean checkSamePlane(char[][] data) {
      return true;

   }

   /**
    * Count the occurence of c in s
    * @param string
    * @param c
    * @return
    */
   public static int countChars(String s, char c) {
      int count = 0;
      for (int i = 0; i < s.length(); i++) {
         if (s.charAt(i) == c)
            count++;
      }
      return count;
   }

   /**
    * Return the first number occurence in the char array
    * # 45 here is a nice example will return 45
    * @param c
    * @return Integer.MIN_VALUE if no number was found
    */
   public static int extractNumber(char[] c) {
      return extractNumber(c, 0, c.length);
   }

   /**
    * Return the first number occurence in the char array
    * <li> #45 returns 45
    * <li> # 45 returns 45
    * @param c
    * @param offset
    * @param len
    * @return
    */
   public static int extractNumber(char[] c, int offset, int len) {
      String s = "";
      int loopl = offset + len;
      for (int i = offset; i < loopl; i++) {
         if (Character.isDigit(c[i])) {
            s = s + c[i];
         } else {
            if (s.length() != 0)
               break;
         }
      }
      if (s.length() != 0) {
         return Integer.parseInt(s);
      }
      return Integer.MIN_VALUE;
   }

   /**
    * 
    * @param url
    * @param delimLeft
    * @param delimRight
    * @return
    */
   public static String extractString(String url, String delimLeft, String delimRight) {
      int index = -1;
      int lastIndex = url.length() - 1;
      if ((index = url.indexOf(delimLeft)) != -1) {
         int rIndex = url.indexOf(delimRight);
         if (rIndex != -1) {
            lastIndex = rIndex;
         }
         return url.substring(index + delimLeft.length(), lastIndex);
      }
      return null;
   }

   public static char getChar(int i) {
      switch (i) {
         case 0:
            return '0';
         case 1:
            return '1';
         case 2:
            return '2';
         case 3:
            return '3';
         case 4:
            return '4';
         case 5:
            return '5';
         case 6:
            return '6';
         case 7:
            return '7';
         case 8:
            return '8';
         case 9:
            return '9';
         default:
            return ' ';
      }
   }

   /**
    * Reads byte array at offset for length number of characters reading 2 bytes for each character.
    * <br>
    * <br>
    * 
    * @param ar
    * @param offset
    * @param length
    * @return
    */
   private static char[] getCharArray(byte[] ar, int offset, int length) {
      char[] ii = new char[length];
      int index = offset;
      for (int i = 0; i < length; i++) {
         ii[i] = (char) (((ar[index] & 0xff) << 8) + ((ar[1 + index] & 0xff) << 0));
         index = index + 2;
      }
      return ii;
   }

   /**
    * Reads the byte array for a char array.
    * ..
    * <br>
    * 
    * If the header defines a plane, use it, otherwise use the given plane.
    * <br>
    * Multi plane 
    * @param byteArray
    * @param length
    * @param offset
    * @param plane
    * @param read holder for the number of read bytes in the array.
    * @return
    */
   public static char[] getCharArrayPlane(byte[] byteArray, int offset, int plane, int[] read) {
      int headerData = byteArray[offset];
      int headerLen = 1;
      int charArrayLen = headerData;
      //first bit for extra header
      if ((headerData & 128) == 128) {
         charArrayLen = ShortUtils.readShortBEUnsigned(byteArray, offset + 1);
         headerLen += 2;
         if ((headerData & 16) == 16) {
            //multiple planes
            read[0] = (charArrayLen * 2) + 3;
            //read 2 bytes for each char
            return getCharArray(byteArray, offset + 3, charArrayLen);
         }
         //first bit set tells us about another 2 bytes header
         if ((headerData & 64) == 64) {
            plane = byteArray[offset + 3];
            headerLen += 1;
         }
      }
      char[] chars = new char[charArrayLen];
      int index = offset;
      //read one byte for each char.
      for (int i = 0; i < charArrayLen; i++) {
         chars[i] = (char) ((plane << 8) + ((byteArray[headerLen + index] & 0xff) << 0));
         index++;
      }
      read[0] = charArrayLen + headerLen;
      return chars;
   }

   /**
    * Proxy for {@link StringUtils#getCharArrayPlane(byte[], int, int, int[])}
    * <br>
    * <br>
    * 
    * @param ar
    * @param offset
    * @param plane
    * @param read
    * @return
    */
   public static String getCharArrayPlaneString(byte[] ar, int offset, int plane, int[] read) {
      return new String(getCharArrayPlane(ar, offset, plane, read));
   }

   private static void getCharByteArray(byte[] da, int boffset, char[] ar, int offset, int length) {
      int end = offset + length;
      for (int i = offset; i < end; i++) {
         char c = ar[i];
         da[boffset] = (byte) ((c >>> 8) & 0xFF);
         da[boffset + 1] = (byte) (c & 0xff);
         boffset += 2;
      }
   }

   /**
    * Stores only the last byte if the characters match the plane given in parameters
    * Write the length
    * <br>
    * <br>
    * 
    * @param ar
    * @param length
    * @param offset
    * @param plane
    * @return
    */
   public static byte[] getCharByteArrayPlane(char[] ar, int offset, int length, int plane) {
      int byteHeader = 0;
      byte[] data = null;
      int pt = getPlaneTopology(ar, offset, length);
      if (length > 126 || plane != pt) {
         byteHeader |= 128;
         if (pt == -1) {
            byteHeader |= 16;
            data = new byte[3 + length * 2];
            ShortUtils.writeShortBEUnsigned(data, 1, length);
            getCharByteArray(data, 3, ar, offset, length);
         } else {
            byteHeader |= 64;
            data = new byte[4 + length];
            //different plane than the default
            ShortUtils.writeShortBEUnsigned(data, 1, length);
            data[3] = (byte) pt;
            for (int i = 0; i < length; i++) {
               data[4 + i] = (byte) ((ar[offset + i] & 0xff) << 0);
            }
         }
         data[0] = (byte) byteHeader;
      } else {
         data = new byte[length + 1];
         byteHeader = length;
         for (int i = 0; i < length; i++) {
            data[1 + i] = (byte) ((ar[offset + i] & 0xff) << 0);
         }
      }
      data[0] = (byte) byteHeader;
      return data;

   }

   /**
    * Proxy
    * @param str
    * @param plane
    * @return
    */
   public static byte[] getCharByteArrayPlane(String str, int plane) {
      return getCharByteArrayPlane(str.toCharArray(), 0, str.length(), plane);
   }

   /**
    * Returns the plane value when all characters match the given plane
    * <br>
    * -1 when several different planes.
    * <br>
    * <br>
    * @param plane
    * @param c
    * @param offset
    * @param len
    * @return
    */
   public static int getPlaneTopology(char[] c, int offset, int len) {
      int basePlane = (byte) ((c[offset] >>> 8) & 0xFF);
      for (int i = offset + 1; i < len; i++) {
         int plane = (byte) ((c[i] >>> 8) & 0xFF);
         if (basePlane != plane) {
            return -1;
         }
      }
      return basePlane;
   }

   /**
    * 190 = no rounding
    * 1990 = 2000
    * 1999 = 2000
    * if two digits, the complement is ignored
    * @param primary
    * @param complement
    * @return
    */
   public static int getRound(int primary, int complement) {
      int add = 0;
      //we only add if two digits
      if (complement >= 90) {
         primary += 1;
      }
      String s = String.valueOf(primary);
      int l = s.length();
      if (l == 1) {
         return primary;
      }
      int count = 0;
      add = 0;
      for (int i = 0; i < l; i++) {
         if (i == l - 1) {
            if (count != 0) {
               if (l == 2 && s.charAt(i) != '9') {
                  //90 case l = 2 count = 1
                  // 990 len = 3 count = 2
                  break;
               }
               int val = Integer.parseInt(s.substring(l - 1, l));
               add = 10 - val;
            }
         } else {
            if (s.charAt(i) == '9') {
               count++;
            } else {
               count = 0;
            }
         }
      }
      primary += add;
      return primary;
   }

   public static char[] getT9String(String s) {
      int[] ar = getT9Translate(s);
      char[] str = new char[ar.length];
      for (int i = 0; i < ar.length; i++) {
         str[i] = getChar(ar[i]);
      }
      return str;
   }

   /**
    * Translate a String into T9 coded numbers.
    * <br>
    * <br>
    * <li>lowercase
    * <li>latin a=1
    * <li>cyrillic 
    * <br>
    * <br>
    * @param s
    * @return
    */
   public static int[] getT9Translate(String s) {
      char[] c = s.toCharArray();
      int[] in = new int[c.length];

      for (int i = 0; i < c.length; i++) {
         char h = c[i];
         int plan = (byte) ((c[i] >>> 8) & 0xFF);
         h = toLowerCase(h);
         if (plan == EN_PAD) {
            setT9Latin(in, i, h);
         }
         if (plan == RU_PAD) {
            setT9Cyrilic(in, i, h);
         }
      }
      return in;
   }

   public static char getUpperCase(char charAt) {
      return Character.toUpperCase(charAt);
   }

   public static String getUpperCaseFirstLetter(String s) {
      return StringUtils.getUpperCase(s.charAt(0)) + s.substring(1, s.length());
   }

   public static boolean isEqualIgnoreCap(char c, char c2) {
      boolean is = c == c2;
      if (is) {
         return true;
      }
      int plan = (byte) ((c >>> 8) & 0xFF);
      int letter = (byte) ((c >>> 0) & 0xFF);
      int plan2 = (byte) ((c2 >>> 8) & 0xFF);
      int letter2 = (byte) ((c2 >>> 0) & 0xFF);

      if (plan != plan2)
         return false;

      switch (plan) {
         case RU_PAD:
            if (letter < 48) {

            }
            break;
         case EN_PAD: {
            // A = 65  Z=90
            // a = 97  z=122
            if (letter >= 97 && letter <= 122) {
               if (letter2 + 32 == letter) {
                  return true;
               }
            } else if (letter >= 65 && letter <= 90) {
               if (letter + 32 == letter2) {
                  return true;
               }
            }
         }

      }
      return false;
   }

   /**
    * Returns true if characters have all the same plane given in parameters.
    * <br>
    * Returns false as soon as a plane value not matching parameter plane is found inside the given offset-len interval.
    * <br>
    * <br>
    * 
    * @param plane
    * @param c
    * @param offset
    * @param len
    * @return
    */
   public static boolean isFullPlane(int plane, char[] c, int offset, int len) {
      for (int i = offset; i < len; i++) {
         int plan = (byte) ((c[offset] >>> 8) & 0xFF);
         if (plan != plane) {
            return false;
         }
      }
      return true;
   }

   /**
    * Returns the index of first occurence of val.
    * <br>
    * Index is absolute. if value is at offset, it returns offset.
    * @param val
    * @param ar
    * @param offset
    * @param len
    * @param strComparator {@link IStrComparator#isEqual(String, String)}
    * @return -1 if not found
    */
   public static int getFirstIndexEqual(String val, String[] ar, int offset, int len, IStrComparator strComparator) {
      return getFirstIndexEqual(val, ar, offset, len, strComparator, 1);
   }

   /**
    * Code is supposed to be inlined. Its here for better structing
    * @param val
    * @param ar
    * @param offset
    * @param len
    * @param strComparator
    * @param skip
    * @return
    */
   public static int getFirstIndexEqual(String val, String[] ar, int offset, int len, IStrComparator strComparator, int skip) {
      for (int j = offset; j < offset + len; j += skip) {
         if (strComparator.isEqual(val, ar[j])) {
            return j;
         }
      }
      return -1;
   }

   public static int getFirstIndexSimilar(String val, String[] ar, int offset, int len, IStrComparator strComparator) {
      for (int j = offset; j < offset + len; j++) {
         if (strComparator.isSimilar(val, ar[j])) {
            return j;
         }
      }
      return -1;
   }

   /**
    * @param c char to check
    * @return false if BIG letter. false otherwise
    */
   public static boolean isLowerCase(char c) {
      char nc = toLowerCase(c);
      return nc == c;
   }

   /**
    * 
    * @param c
    * @param offset
    * @param len
    * @return
    */
   public static boolean isMixedPlane(char[] c, int offset, int len) {
      int plane = (c[offset] >>> 8) & 0xFF;
      for (int i = offset + 1; i < offset + len; i++) {
         int p = (c[i] >>> 8) & 0xFF;
         if (plane != p) {
            return true;
         }
      }
      return false;
   }

   public static boolean isRuLetters(char[] c) {
      for (int i = 0; i < c.length; i++) {
         if (CharUtils.byteArrayBEFromChar(c[i])[0] != RU_PAD) {
            return false;
         }
      }
      return true;
   }

   /**
    * bonjour,3 et jour will return true
    * @param source string
    * @param index where to start in the source array
    * @param match string that has to match
    * @return
    */
   public static boolean isSame(char[] source, int index, char[] match) {
      if (source.length - index != match.length)
         return false;
      for (int i = 0; i < match.length; i++) {
         if (source[i + index] != match[i])
            return false;
      }
      return true;
   }

   /**
    * Compare two words
    * bonjour,3,jour will return -1 because bon[jour] matches jour after position 3
    * r,0,rare will return 1 because there is no 'a' after r in the 1st word
    * raw,0,rare will return 2 
    * rare,0,ra will return -1
    * @param source string
    * @param index where to start in the source array
    * @param match string that has to match
    * @return source index where match does not match
    * -1 if there is a match
    */
   public static int isSamePrefix(char[] source, int index, char[] match) {
      for (int i = 0; i < match.length; i++) {
         if (i + index >= source.length)
            return i + index;
         if (source[i + index] != match[i])
            return i + index;
      }
      return -1;
   }

   public static int isSamePrefix(String source, int index, String match) {
      return isSamePrefix(source.toCharArray(), index, match.toCharArray());
   }

   public static boolean isWhitespace(char character) {
      boolean returnValue;
      if ((character == '\t') || (character == '\n') || (character == ' ') || (character == '\u000C') || (character == '\u001C') || (character == '\u001D') || (character == '\u001E') || (character == '\u001F')) {
         returnValue = true;
      } else {
         returnValue = false;
      }
      return returnValue;
   }

   /**
    * True for
    * <li> space , . : ;
    * @param c
    * @return
    */
   public static boolean isWordDelimiter(char c) {
      int plan = (byte) ((c >>> 8) & 0xFF);
      int letter = (byte) ((c >>> 0) & 0xFF);
      switch (plan) {
         case RU_PAD:
            if (c == ' ' || c == ',' || c == '.') {
               return true;
            }
         case EN_PAD:
            // a = 97  z=122
            if (c == ' ' || c == ',' || c == '.' || c == '!' || c == '?') {
               return true;
            }
            if (c == '\n') {
               return true;
            }
            if (c == '\r') {
               return true;
            }
            return false;
      }
      return false;
   }

   public static boolean isWordDelimiterInvisible(char c) {
      // a = 97  z=122
      if (c == ' ') {
         return true;
      }
      if (c == '\n') {
         return true;
      }
      if (c == '\r') {
         return true;
      }
      return false;
   }

   public static void normalize(char[] c) {
      if (c == null)
         return;
      for (int i = 0; i < c.length; i++) {
         c[i] = toLowerCase(c[i]);
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
   public static void shiftInt(String[] ar, int shiftsize, int start, int end, boolean erase) {
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
   public static void shiftIntDown(String[] ar, int shiftsize, int start, int end, boolean erase) {
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
   public static void shiftIntUp(String[] ar, int shiftsize, int start, int end, boolean erase) {
      for (int i = end; i >= start; i--) {
         if (i + shiftsize < ar.length) {
            ar[i + shiftsize] = ar[i];
         }
      }
      if (erase) {
         fill(ar, null, start, start + shiftsize - 1);
      }
   }

   /**
    * Fills array [start,end] with filler
    * @param ar
    * @param filler
    * @param start included 
    * @param end included
    */
   public static void fill(String[] ar, String filler, int start, int end) {
      for (int i = start; i <= end; i++) {
         ar[i] = filler;
      }
   }

   /**
    * normalize the string
    * @param initWordUpper
    */
   public static String normalize(String s, boolean initWordUpper) {
      if (s == null && s.length() == 0)
         return s;
      char[] car = s.toCharArray();
      int count = 1;
      int i = -1;
      do {
         i = i + 1;
         char c = s.charAt(i);
         if (Character.isUpperCase(c)) {
            if (!initWordUpper) {
               c = Character.toLowerCase(c);
            }
         } else {
            //lowercase
            if (initWordUpper) {
               c = Character.toUpperCase(c);
            }
         }

         car[i] = c;
         count++;
      } while ((i = s.indexOf(' ', i)) != -1);

      return new String(car);
   }

   /**
    * each words are Upped
    * @param c an array of lowercase characters with maximum one consecutive space character
    */
   public static void prepare(char[] c) {
      if (c == null || c.length == 0)
         return;
      c[0] = toUpperCase(c[0]);
      boolean next = false;
      for (int i = 0; i < c.length; i++) {
         if (c[i] == ' ')
            next = true;
         else {
            if (next) {
               next = false;
               c[i] = toUpperCase(c[0]);
            }
         }
      }
   }

   public static String prettyFloat(float amountn) {
      if (amountn > 5) {
         return Integer.toString((int) amountn);
      } else {
         return Float.toString(amountn).substring(0, 3);
      }
   }

   /**
    * Replaces all instances of look by repl in url
    * @param url
    * @param look
    * @param repl
    * @return
    */
   public static String replace(String url, String look, String repl) {
      if (look == null || look.equals("")) {
         return url;
      }
      if (repl != null && look.equals(repl)) {
         return url;
      }
      int index = -1;
      int start = 0;
      while ((index = url.indexOf(look, start)) != -1) {
         url = url.substring(start, index) + repl + url.substring(index + look.length(), url.length());
         index = index + repl.length();
      }
      return url;
   }

   public static char[] reverse(String str) {
      char[] ar = new char[str.length()];
      for (int i = 0; i < ar.length; i++) {
         ar[i] = str.charAt(ar.length - i - 1);
      }
      return ar;
   }

   public static String reverseStr(String str) {
      char[] ar = reverse(str);
      return new String(ar);
   }

   public static void set(String[] ar, String val) {
      for (int i = 0; i < ar.length; i++) {
         ar[i] = val;
      }
   }

   /**
    * @param in
    * @param i
    * @param h
    */
   private static void setT9Cyrilic(int[] in, int i, char h) {
      //lowbytes
      int e = (byte) ((h >>> 0) & 0xFF);
      if (e > 0) {
         //if    (h == 'а' || h == 'б' || h == 'в' || h == 'г')
         if (e == 48 || e == 49 || e == 50 || e == 51)
            in[i] = 2;
         //else if   (h == 'д' || h == 'е' || h == 'ё' || h == 'ж' || h == 'ж')
         else if (e == 52 || e == 53 || e == 81 || e == 54 || e == 55)
            in[i] = 3;
         //else if (h == 'и' || h == 'й' || h == 'к' || h == 'л')
         else if (e == 56 || e == 57 || e == 58 || e == 59)
            in[i] = 4;
         //else if (h == 'м' || h == 'н' || h == 'о' || h == 'п')
         else if (e == 60 || e == 61 || e == 62 || e == 63)
            in[i] = 5;
         else if (e == 64 || e == 65 || e == 66 || e == 67)
            in[i] = 6;
         //else if (h == 'ф' || h == 'х' || h == 'ц' || h == 'ч')
         else if (e == 68 || e == 69 || e == 70 || e == 71)
            in[i] = 7;
         //else if (h == 'ш' || h == 'щ' || h == 'ъ' || h == 'ы')
         else if (e == 72 || e == 73 || e == 74 || e == 75)
            in[i] = 8;
         else if (e == 76 || e == 77 || e == 78 || e == 79)
            in[i] = 9;
         else if (h == ' ')
            in[i] = 0;
         else {
            in[i] = 1;
         }
      } else {
         in[i] = 1;
      }
   }

   /**
    * @param in
    * @param i
    * @param h
    */
   private static void setT9Latin(int[] in, int i, char h) {
      int e = (byte) ((h >>> 0) & 0xFF);
      if (e > 0) {
         if (h == 'a' || h == 'b' || h == 'c')
            in[i] = 2;
         else if (h == 'd' || h == 'e' || h == 'f')
            in[i] = 3;
         else if (h == 'g' || h == 'h' || h == 'i')
            in[i] = 4;
         else if (h == 'j' || h == 'k' || h == 'l')
            in[i] = 5;
         else if (h == 'm' || h == 'n' || h == 'o')
            in[i] = 6;
         else if (h == 'p' || h == 'q' || h == 'r' || h == 's')
            in[i] = 7;
         else if (h == 't' || h == 'u' || h == 'v')
            in[i] = 8;
         else if (h == 'w' || h == 'x' || h == 'y' || h == 'z')
            in[i] = 9;
         else if (h == ' ')
            in[i] = 0;
         else {
            in[i] = 1;
         }
      } else {
         // < 0
         //if (h == 'Ã ' || h == 'Ã¡' || h == 'Ã¢' || h == 'Ã£' || h == 'Ã¤' || h == 'Ã§')
         if (e == -32 || e == -31 || e == -30 || e == -29 || e == -28 || e == -25)
            in[i] = 2;
         //else if (h == 'Ã¨' || h == 'Ã©' || h == 'Ãª' || h == 'Ã«')
         else if (e == -24 || e == -23 || e == -22 || e == -21)
            in[i] = 3;
         //else if (h == 'Ã¬' || h == 'Ã­' || h == 'Ã®' || h == 'Ã¯')
         else if (e == -20 || e == -19 || e == -18 || e == -17)
            in[i] = 4;
         else if (false)
            in[i] = 5;
         //else if (h == 'Ã±' || h == 'Ã²' || h == 'Ã³' || h == 'Ã´' || h == 'Ãµ' || h == 'Ã¶')
         else if (e == -15 || e == -14 || e == -13 || e == -12 || e == -11 || e == -10)
            in[i] = 6;
         else if (false)
            in[i] = 7;
         //else if (h == 'Ã¹' || h == 'Ãº' || h == 'Ã»' || h == 'Ã¼')
         else if (e == -32 || e == -31 || e == -30 || e == -25)
            in[i] = 8;
         else if (false)
            in[i] = 9;
         else if (h == ' ')
            in[i] = 0;
         else {
            in[i] = 1;
         }
      }
   }

   public static char[] sort(char[] c, boolean asc) {
      char tempstr = ' ';
      if (c.length == 1)
         return c;
      int val = 0;
      for (int i = 0; i < c.length; i++) {
         for (int j = i + 1; j < c.length; j++) {
            val = (c[i] < c[j]) ? -1 : 1;
            if ((asc && val > 0) || (!asc && val < 0)) {
               tempstr = c[i];
               c[i] = c[j];
               c[j] = tempstr;
            }
         }
      }
      return c;
   }

   public static char[] subChars(char[] c, int offset) {
      char[] nc = new char[c.length - offset];
      for (int i = 0; i < nc.length; i++) {
         nc[i] = c[offset + i];
      }
      return nc;
   }

   public static char toLowerCase(char c) {
      boolean mod = false;
      int plan = (byte) ((c >>> 8) & 0xFF);
      int letter = (byte) ((c >>> 0) & 0xFF);

      switch (plan) {
         case RU_PAD:
            if (letter < 48) {
               letter = letter + 32;
               mod = true;
            }
            break;
         case EN_PAD:
            // A = 65  Z=90
            // a = 97  z=122
            if (letter >= 97) {
            } else if (letter >= 65) {
               letter = letter + 32;
               mod = true;
            } else {
               // -64 Ã€ -32 Ã 
               if (letter < -35 && letter >= -64) {
                  letter = letter + 32;
                  mod = true;
               }
            }
      }
      if (mod) {
         int ch1 = plan << 8;
         int ch2 = (letter << 0) & 0xFF;
         return (char) (ch1 + ch2);
      }
      return c;
   }

   /**
    * Put to lowercase all chars in array
    * @param s
    */
   public static void toLowerCaseMordan(char[] s) {
      toLowerCaseMordan(s, 0, s.length);
   }

   public static void toLowerCaseMordan(char[] s, int offset, int len) {
      if (len != 0) {
         for (int i = offset; i < offset + len; i++) {
            s[i] = toLowerCase(s[i]);
         }
      }
   }

   public static char[] toLowerCaseMordan(String s) {
      int len = s.length();
      if (len != 0) {
         char[] cha = new char[len];
         for (int i = 0; i < cha.length; i++) {
            cha[i] = toLowerCase(s.charAt(i));
         }
         return cha;
      } else {
         return new char[0];
      }
   }

   public static char toUpperCase(char c) {
      boolean mod = false;
      int plan = (byte) ((c >>> 8) & 0xFF);
      int letter = (byte) ((c >>> 0) & 0xFF);

      switch (plan) {
         case RU_PAD:
            //lowercase start at 48
            if (letter > 48) {
               letter = letter - 32;
               mod = true;
            }
            break;
         case EN_PAD:
            // A = 65  Z=90
            // a = 97  z=122
            if (letter >= 97 && letter <= 122) {
               letter = letter - 32;
               mod = true;
            }
      }
      if (mod) {
         int ch1 = plan << 8;
         int ch2 = (letter << 0) & 0xFF;
         return (char) (ch1 + ch2);
      }
      return c;
   }

   public final char[] as = new char[] { 'a', 'â' };

   private char[]      cyrChar;

   public final char[] es = new char[] { 'e', 'é', 'è', 'ê' };

   public final char[] is = new char[] { 'i', 'î' };

   private char[]      latinChar;

   private char[]      modChar;

   private UCtx        uc;

   public StringUtils(UCtx uc) {
      this.uc = uc;
   }

   /**
    * -1 when source is smaller
    * 1 when source is bigger
    * @param source
    * @param target
    * @return
    */
   public int compare(String source, String target) {
      return uc.getCU().compare(source.toCharArray(), target.toCharArray());
   }

   /**
    * Natural breaking does not take into account {@link Font} data.
    * <br>
    * <br>
    * Breaking indexes are relative to given offset!
    * <br>
    * <br>
    * Same behavior as {@link StringUtils#getBreaksWord(char[], int, int, boolean)}.
    * <br>
    * <br>
    * 
    * @param chars
    * @param offset
    * @param len
    * @return breaking indexes relative to offset. null when no break lines were found.
    * 
    */
   public int[] getBreaksLineNatural(char[] chars, int offset, int len) {
      char nl = '\n';
      char wnl = '\r'; //for windows \r\n
      //first look for new lines characters indexes relative to offset
      IntBuffer b = new IntBuffer(uc, 5); //count index position of \n special char
      int letterCount = 0; //count from offset
      int lineLength = 0;
      boolean notALine = false;
      if (chars[offset] == wnl || chars[offset] == nl) {
         notALine = true;
      } else {
         b.addInt(letterCount);
         lineLength = 1;
      }
      letterCount++;
      for (int i = 1; i < len; i++) {
         char c = chars[offset + i];
         if (c == nl) {
            if (!notALine) {
               notALine = true;
               b.addInt(lineLength);
            }
         } else if (c == wnl) {
            if (!notALine) {
               notALine = true;
               b.addInt(lineLength);
            }
            i++;
            letterCount++;
         } else {
            if (notALine) {
               //we start a new line
               b.addInt(letterCount);
               lineLength = 0;
               notALine = false;
            }
         }
         lineLength++;
         letterCount++;
      }
      if (!notALine) {
         //finalize by setting length of last line
         b.addInt(lineLength);
      }
      if (b.getSize() == 0) {
         return null;
      } else {
         return b.getIntsRef();
      }
   }

   /**
    * Null if  no line breaks.
    * 
    * @param str
    * @return
    */
   public int[] getBreaksLineNatural(String str) {
      return getBreaksLineNatural(str.toCharArray(), 0, str.length());
   }

   /**
    * 
    * 0: starting offset of line
    * 1: number of chars excluding the line break
    * <br>
    * <br>
    * Therefore the line breaks 'disappear'
    * <br>
    * <br>
    * 
    * @param str
    * @return not null 
    * if no breaks just one line
    */
   public int[][] getBreaksNaturalMatrix(String str) {
      char nl = '\n';
      char wnl = '\r'; //for windows \r\n
      IntBuffer b = new IntBuffer(uc, 3);
      for (int i = 0; i < str.length(); i++) {
         char c = str.charAt(i);
         if (c == nl) {
            b.addInt(i);
         }
         if (c == wnl) {
            b.addInt(i);
            i++;
         }
      }
      if (b.getSize() == 0) {
         int[][] breaks = new int[1][2];
         breaks[0][0] = 0;
         breaks[0][1] = str.length();
         return breaks;
      } else {
         int[][] breaks = new int[b.getSize() + 1][2];
         int[] vals = b.getIntsClonedTrimmed();
         breaks[0][0] = 0;
         breaks[0][1] = vals[0];
         for (int i = 1; i <= vals.length; i++) {
            breaks[i][0] = breaks[i - 1][0] + breaks[i - 1][1] + 1;
            if (i != vals.length) {
               breaks[i][1] = vals[i] - breaks[i][0];
            } else {
               breaks[i][1] = str.length() - breaks[i][0];
            }
         }
         return breaks;
      }
   }

   /**
    * Int array starts .
    * <br>
    * <br>
    * Breaking indexes are relative to given offset!
    * <br>
    * <br>
    * 1 index value gives the number of words * 2.
    * <br>
    * <br>
    * Array may be bigger than necessary.
    * <br>
    * <br>
    * 
    * @param chars
    * @param offset
    * @param len
    * @return
    */
   public int[] getBreaksWord(char[] chars, int offset, int len) {
      return getBreaksWord(chars, offset, len, false);
   }

   /**
    * Reject only invisible characters. keeps the rest as "word".
    * <br>
    * <br>
    * Values are relative to offset.
    * <br>
    * <br>
    * @param chars
    * @param offset
    * @param len
    * @param invisibleOnly
    * @return
    */
   public int[] getBreaksWord(char[] chars, int offset, int len, boolean invisibleOnly) {
      IntBuffer b = new IntBuffer(uc, 3); //count index position of \n special char
      //kick start
      boolean inWord = false;
      int wordLetterCount = 0;
      if (invisibleOnly ? !isWordDelimiterInvisible(chars[offset]) : !isWordDelimiter(chars[offset])) {
         //start with a word
         b.addInt(0);
         inWord = true;
         wordLetterCount = 1;
      }
      for (int i = 1; i < len; i++) {
         char c = chars[offset + i];
         if (invisibleOnly ? isWordDelimiterInvisible(c) : isWordDelimiter(c)) {
            if (inWord) {
               b.addInt(wordLetterCount);
               inWord = false;
            }
         } else {
            if (inWord == false) {
               inWord = true;
               wordLetterCount = 0;
               b.addInt(i);
            }
         }
         wordLetterCount++;
      }
      //finalize.
      if (inWord) {
         b.addInt(wordLetterCount);
      }
      return b.getIntsRef();
   }

   /**
    * Return an array of related characters
    * <li> i return i and î, ì, í, 
    * 
    * This depends on the context. A user in French does not need ñ
    * 
    * TODO
    * @param c
    * @return
    */
   public char[] getCharAccentMerge(char c) {
      switch (c) {
         case 'e':
            return es;
         case 'a':
            return as;
         case 'i':
            return is;
         default:
            return null;
      }
   }

   public char[] getChars(int plane) {
      char[] fgs = new char[256];
      for (int i = 0; i < 256; i++) {
         fgs[i] = (char) ((plane << 8) + i << 0);
      }
      return fgs;
   }

   /**
    * Gets the indexes of c inside str
    * 
    * Empty buffer if no occurences;
    * @param str
    * @param c
    * @return
    * @throws NullPointerException if str or c is null
    */
   public IntBuffer getIndexesAsBuffer(String str, String c) {
      if (str == null) {
         throw new NullPointerException();
      }
      if (c == null) {
         throw new NullPointerException();
      }
      IntBuffer ib = new IntBuffer(uc);
      int offset = 0;
      int iLen = c.length();
      //check if search string is empty ""
      if (iLen != 0) {

         int next = -1;
         while ((next = str.indexOf(c, offset)) != -1) {
            ib.addInt(next);
            offset = next + iLen;
         }
      }
      return ib;
   }

   /**
    * Min letters
    * @return
    */
   public char[] getLatinChar() {
      if (latinChar == null) {
         latinChar = new char[26];
         int ch1 = EN_PAD << 8;
         int count = 0;
         for (int i = 97; i < 123; i++) {
            int ch2 = (i << 0) & 0xFF;
            latinChar[count] = (char) (ch1 + ch2);
            count++;
         }
      }
      return latinChar;
   }

   public String getLowerCase(String s, int langid) {
      char[] chars = s.toCharArray();
      if (langid == RU_PAD) {
         int[] newv = new int[chars.length];
         for (int i = 0; i < chars.length; i++) {
            newv[i] = CharUtils.byteArrayBEFromChar(chars[i])[1];
            if (newv[i] < 48) {
               newv[i] = newv[i] + 32;
            }
         }
         return new String(CharUtils.buildCharsFromLowBytes(newv, StringUtils.RU_PAD, 0, newv.length));
      } else {
         return s.toLowerCase();
      }
   }

   public char[] getModifiers() {
      if (modChar == null) {
         modChar = new char[6];
         //modChar[0] = '`';
         modChar[0] = CharUtils.buildCharFromLowByte(96, EN_PAD);
         //modChar[0] = 'Â´';
         modChar[1] = CharUtils.buildCharFromLowByte(-76, EN_PAD);
         //modChar[0] = '^';
         modChar[2] = CharUtils.buildCharFromLowByte(94, EN_PAD);
         //modChar[0] = 'Â¨';
         modChar[3] = CharUtils.buildCharFromLowByte(-88, EN_PAD);
         //modChar[0] = '~';
         modChar[4] = CharUtils.buildCharFromLowByte(126, EN_PAD);
         //modChar[0] = 'Â¸' //cedill
         modChar[5] = CharUtils.buildCharFromLowByte(-72, EN_PAD);
      }
      return modChar;
   }

   /**
    * Returns an array of String from String str
    * 
    * <li> ("range;extends;try", ";") returns ["range","extends","try"]
    * 
    * <li> <b>Borderline cases</b>
    * <li> ("range;extends;try", "") returns ["range;extends;try"]
    * <li> ("range;extends;try", "range;extends;try") returns ["",""]
    * <li> (";;", ";") returns ["","",""]
    * 
    * @param str
    * @param splitter
    * @return
    * @throws NullPointerException if str or c is null
    */
   public String[] getSplitArray(String str, String splitter) {
      IntBuffer ib = getIndexesAsBuffer(str, splitter); //does the null check for us
      int num = ib.getSize();
      String[] array = new String[num + 1];
      int startOffset = 0;
      for (int i = 0; i < num; i++) {
         int endIndex = ib.get(i);
         array[i] = str.substring(startOffset, endIndex);
         startOffset = endIndex + splitter.length();
      }
      array[num] = str.substring(startOffset);
      return array;
   }

   public String getString(int[] ar, String separator) {
      if (ar == null) {
         return "null";
      }
      StringBBuilder sb = new StringBBuilder(uc);
      for (int i = 0; i < ar.length; i++) {
         if (i != 0) {
            sb.append(separator);
         }
         sb.append(ar[i]);
      }
      return sb.toString();
   }

   /**
    * Builds a String at offset with a header of 4 bytes
    * 
    * @return
    */
   public String getStringIntLong(byte[] data, int offset) {
      char[] cs = uc.getCU().getCharsIntLong(data, offset);
      return new String(cs);
   }

   public void writeStringIntLong(String str, byte[] data, int offset) {
      int length = str.length();
      IntUtils.writeIntBE(data, offset, length);
      int index = offset + 4;
      for (int i = 0; i < length; i++) {
         char c = str.charAt(i);
         CharUtils.writeShortBE(data, index, c);
         index += 2;
      }
   }

   public String getString(String[] ar, char separator) {
      return getString(ar, String.valueOf(separator));
   }

   public String getString(String[] ar, String separator) {
      if (ar == null) {
         return "null";
      }
      StringBBuilder sb = new StringBBuilder(uc);
      for (int i = 0; i < ar.length; i++) {
         if (i != 0) {
            sb.append(separator);
         }
         sb.append(ar[i]);
      }
      return sb.toString();
   }

   /**
    * Return the string inside delStart]...[delEnd
    * Null if no such substring.
    * @param delStart
    * @param delEnd
    * @return
    */
   public String getSubstring(String str, char delStart, char delEnd) {
      int indexStart = str.indexOf(delStart);
      if (indexStart != -1) {
         int indexEnd = str.indexOf(delEnd, indexStart);
         if (indexEnd != -1) {
            return str.substring(indexStart + 1, indexEnd);
         }
      }
      return null;
   }

   /**
    * returns a substring between the start and the first characters
    * <br>
    * isDelimiterIncluded.
    * <br>
    * <br>
    * If the char is the last, returns empty string
    * @param str
    * @param delEnd
    * @return
    */
   public String getSubstringCharToEnd(String str, char delStart) {
      int indexStart = str.indexOf(delStart);
      if (indexStart == str.length() - 1) {
         return "";
      }
      if (indexStart != -1) {
         return str.substring(indexStart + 1);
      }
      return null;
   }

   public String getSubstringStartToChar(String str, char delEnd) {
      int indexEnd = str.indexOf(delEnd);
      if (indexEnd != -1) {
         return str.substring(0, indexEnd);
      }
      return null;
   }

   public String getSubstringStartToString(String str, String delEnd) {
      int indexEnd = str.indexOf(delEnd);
      if (indexEnd != -1) {
         return str.substring(0, indexEnd);
      }
      return null;
   }

   /**
    * Split value in smaller strings
    * @param value
    * @param separator 
    * @return
    */
   public String[] getValues(String value, char separator) {
      if (value == null || value.equals(""))
         return new String[0];
      int index = -1;
      int count = 0;
      String[] s = new String[1];
      while ((index = value.indexOf(separator, count)) != -1) {
         s[s.length - 1] = value.substring(count, index);
         s = uc.getMem().increaseCapacity(s, 1);
         count = index + 1;
         index = -1;
      }
      //add the last, or the single one if only one
      s[s.length - 1] = value.substring(count, value.length());
      return s;
   }

   /**
    * Tells if character is the last letter of the alphabet.
    * <li> z in latin alphabet
    * <li> я in cyrillic
    * <br>
    * Works with uppercase and lowercase
    * <br>
    * @param c
    * @return
    */
   public boolean isLastLetter(char c) {
      int plan = (byte) ((c >>> 8) & 0xFF);
      int letter = (byte) ((c >>> 0) & 0xFF);
      switch (plan) {
         case RU_PAD:
            // я = 79   Я = 47 
            return letter == 79 || letter == 47;
         case EN_PAD:
            // a = 97  z=122    Z=90
            return letter == 122 || letter == 90;
      }
      return false;
   }

   /**
    * Test whether test can be humanly considered with the same
    * high level semantic meaning
    * ie Clothe, clothes
    * phone, Phones
    * @param source no null checks
    * @param test no null checks
    * @return
    * true if 
    * false if there is a length diff higher than 2
    */
   public boolean isSimilar(String source, String test) {
      int sl = source.length();
      int tl = test.length();
      int min = Math.min(sl, tl);
      //int max = Math.max(sl, tl);
      if (Math.abs(sl - tl) > 2)
         return false;
      for (int i = 0; i < min; i++) {
         if (toLowerCase(source.charAt(i)) != toLowerCase(test.charAt(i))) {
            return false;
         }

      }
      return true;
   }

   /**
    * Returns a String with at most the number of decimals. No rounding.
    * 
    * 1.025 , 2 -> "1.02" 
    * 1.025 , 0 -> "1" 
    * 
    * @param amountn
    * @param decimals [0-n]
    * @return
    */
   public String prettyFloat(float amountn, int decimals) {
      String s = Float.toString(amountn);
      return prettyDecimalString(s, decimals);
   }

   /**
    * Cuts the string at first '.' and keep at most
    * @param s if no . returns s
    * @param decimals [0-n] 
    * @throws {@link StringIndexOutOfBoundsException}
    * @throws {@link IllegalArgumentException} in debug mode when decimals < 0
    * @return
    */
   public String prettyDecimalString(String s, int decimals) {
      //#mdebug
      if (decimals < 0) {
         throw new IllegalArgumentException("Decimals must be >=0. It was " + decimals);
      }
      //#enddebug
      int indexOfDot = s.indexOf('.');
      if (indexOfDot == -1) {
         return s;
      }
      int index = indexOfDot + 1;
      int numofdeci = s.length() - index;
      if (index == -1) {
         index = 0;
         numofdeci = 0;
      }
      int max = Math.min(decimals, numofdeci);
      if (decimals == 0) {
         index--;
      }
      String m1 = s.substring(0, index);
      String m2 = s.substring(index, index + max);
      return m1 + m2;
   }

   public String prettyDouble(double amountn, int decimals) {
      String s = Double.toString(amountn);
      return prettyDecimalString(s, decimals);
   }

   /**
    * Pretty method:
    * <br>
    * Pads 0 in front value
    * @param value
    * @param numChars
    * @return
    */
   public String prettyInt0Padd(int value, int numChars) {
      return prettyIntPaddStr(value, numChars, "0");
   }

   public String prettyIntPaddStr(int value, int numChars, String str) {
      String numIt = String.valueOf(value);
      while (numIt.length() < numChars) {
         numIt = str + numIt;
      }
      return numIt;
   }

   /**
    * 
    * @param value
    * @param total
    * @param decimals
    * @return
    */
   public String prettyPercentage(int value, int total, int decimals) {
      double me = (double) ((double) value / (double) total) * ((double) 100);
      return prettyDouble(me, decimals);
   }

   /**
    * Computes a user readable String
    * Does m
    * @param m
    * @return
    */
   public String prettyStringMem(long m) {
      String s = null;
      double divisor = 1;
      double upper = (double) m;
      if (m < 2000) {
         s = " bytes";
      } else if (m < 2000000) {
         divisor = 1000;
         s = " kb";
      } else if (m < 2000000000L) {
         divisor = 1000000;
         s = " mb";
      } else {
         //giga
         divisor = 1000000000;
         s = " gb";
      }
      String v = String.valueOf((int) (upper / divisor));
      return v + s;

   }

   /**
    * pads String from front to reach size
    * @param str
    * @param size
    * @return
    */
   public String prettyStringPad(String str, int size, char padder) {
      int diff = size - str.length();
      if (diff > 0) {
         for (int i = 0; i < diff; i++) {
            str = padder + str;
         }
      }
      return str;
   }

   public String prettyStringPadBack(String str, int size, char padder) {
      int diff = size - str.length();
      if (diff > 0) {
         for (int i = 0; i < diff; i++) {
            str = str + padder;
         }
      }
      return str;
   }

   public String trimAtChar(String string, char c) {
      int index = string.indexOf(c);
      if (index != -1) {
         return string.substring(0, index);
      }
      return string;
   }

   /**
    * Returns a String trimmed at the first occurrence of the character 
    * @param string
    * @return
    */
   public String trimAtNewLine(String string) {
      return trimAtChar(string, '\n');
   }
}
