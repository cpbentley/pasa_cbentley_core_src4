/*
 * (c) 2018-2020 Charles-Philip Bentley
 * This code is licensed under MIT license (see LICENSE.txt for details)
 */
package pasa.cbentley.core.src4.utils;

import pasa.cbentley.core.src4.ctx.UCtx;
import pasa.cbentley.core.src4.helpers.StringBBuilder;
import pasa.cbentley.core.src4.interfaces.IStrComparator;
import pasa.cbentley.core.src4.structs.IntBuffer;
import pasa.cbentley.core.src4.structs.IntIntervals;

/**
 * More comment
 * @author Charles Bentley
 *
 */
public class StringUtils {

   public static final int    EN_PAD                   = 0;

   /**
    * 
    */
   public static final char   ENGLISH_SPACE            = ' ';

   public static final char   NULL_CHAR                = '\u0000';

   public static final char   START_HEADING            = '\u0001';

   public static final char   START_TEXT               = '\u0002';

   public static final char   END_TEXT                 = '\u0003';

   public static final char   END_TRANSMISSION         = '\u0004';

   public static final char   NO_BREAKING_SPACE        = '\u00A0';

   /**
    * Zero width in Swing
    */
   public static final char   NEW_LINE                 = '\n';

   /**
    * Windows puts a \r in front of new lines so we have to handle \r\n
    * <p>
    * Zero width in Swing
    * </p>
    */
   public static final char   NEW_LINE_CARRIAGE_RETURN = '\r';

   public static final char   FORM_FEED                = '\u000C';

   public static final char   FORM_FEED_F              = '\f';

   public static final char   BACKSPACE                = '\u0008';

   public static final char   EM_QUAD                  = '\u2001';

   public static final char   EN_QUAD                  = '\u2000';

   public static final char   EM_SPACE                 = '\u2003';

   public static final char   EN_SPACE                 = '\u2002';

   public static final char   THREE_PER_EM_SPACE       = '\u2004';

   public static final char   FOUR_PER_EM_SPACE        = '\u2005';

   public static final char   SIX_PER_EM_SPACE         = '\u2006';

   /**
    * 2640 = ♀
    */
   public static final char   GENDER_FEMALE            = '\u2640';

   /**
    * 2642 = ♂
    */
   public static final char   GENDER_MALE              = '\u2642';

   /**
    * 2641 = ♁ 
    */
   public static final char   GENDER_EARTH             = '\u2641';

   /**
    * 263C = ☼ 
    */
   public static final char   GENDER_SUN               = '\u263C';

   /**
    * 263A = ☺
    */
   public static final char   GENDER_WHITE_SMILEY      = '\u263A';

   /**
    * [█]
    */
   public static final char   BLOCK_FULL               = '\u2588';

   /**
    * [▉]
    */
   public static final char   BLOCK_7_OF_8             = '\u2589';

   /**
    * u2581 = [▁]
    */
   public static final char   BLOCK_BOT_1_OF_8         = '\u2581';

   /**
    * u2594 = [▔]
    */
   public static final char   BLOCK_TOP_1_OF_8         = '\u2594';

   /**
    * u258F = [▏] [▏▔▏]
    */
   public static final char   BLOCK_LEFT_1_OF_8        = '\u2594';

   /**
    * u2595 = [▕] [▕▔]
    */
   public static final char   BLOCK_RIGHT_1_OF_8       = '\u2595';

   /**
    * [■] alt+254 ▉
    */
   public static final char   BLACK_SQUARE             = '\u25A0';

   /**
    * Size of a digit
    */
   public static final char   FIGURE_SPACE             = '\u2007';

   public static final char   LINE_SEPARATOR           = '\u2028';

   /**
    * Byte for russian characters in UTF-8
    */
   public static final int    RU_PAD                   = 4;

   public static final char   RU_SPACE                 = ' ';

   public static final char   TAB                      = '\t';

   /**
    * vertical tabulation Alt+011  
    */
   public static final char   TAB_LINE                 = '\u000B';

   /**
    * '\u000A' is illegal because compiler interprets it as a \n
    */
   public static final char   LINE_FEED                = '\n';

   /**
    * «—» [—][-][+]
    * Le tiret long ou « tiret cadratin 
    * <p>
    * Il est utilisé en français et dans plusieurs autres langues européennes pour introduire les répliques des dialogues.
    * — Bonjour, monsieur.
    * </p>
    */
   public static final char   EM_DASH                  = '\u2014';

   /**
    * «–» [–][-][+]
    * Le tiret moyen ou « tiret demi‑cadratin » ou « tiret semi‑cadratin » ou « demi‑tiret »
    * 
    */
   public static final char   EN_DASH                  = '\u2013';

   /**
    * «‐» [‐]     asdsad 
    */
   public static final char   SHORT_DASH               = '\u2010';

   /**
    * [-] 
    */
   public static final char   SIGN_MINUS               = '\u2212';

   public static final char   TAB_CHAR                 = '\u0009';

   /**
    *  00B7 = ·
    */
   public static final char   INTER_PUNCT              = '\u00B7';

   public static final char   BOTTOM_SQUARE_BRACKET    = '\u23B5';

   /**
    *    23CE = ⏎ 
    *    
    */
   public static final char   SYMBOL_RETURN            = '\u23CE';

   /**
    *  21B5 = ↵
    */
   public static final char   LINE_BREAK_RETURN        = '\u21B5';

   public static final char   ESCAPE_CHAR              = '\\';

   /**
    * sad →
    */
   public static final char   ARROW_RIGHT              = '\u2192';

   /**
    * [°] 
    * <p>
    * DEGREE SIGN, representing a non-breaking space (NBSP) character
    * </p>
    */
   public static final char   DEGREE                   = '\u00B0';

   public static final char   SYMBOL_FOR_SPACE         = '\u2420';

   /**
    * Chars that usually seperates words in usual latin text
    */
   public static final char[] CHARS_SEPARATORS_PUNC    = { ' ', ',', ':', ';' };

   /**
    * 
    */
   public static final String UTF8_BOM                 = "\uFEFF";

   public static char         UTF8_BOM_CHAR            = '\uFEFF';

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

   /**
    * Gets the relative index of word starting from startOffset.
    * -1 if word is not found.
    * @param ar
    * @param offset
    * @param len
    * @param word
    * @param startOffset relative to offset, not absolute to the array
    * @return relative index of word from offset.
    */
   public static int indexOf(char[] ar, int offset, int len, String word, int startOffset) {
      if (word == null || word.length() == 0) {
         return -1;
      }
      int end = offset + len;
      int start = offset + startOffset;
      char first = word.charAt(0);
      for (int i = start; i < end; i++) {
         if (ar[i] == first) {
            //
            int endj = i + word.length();
            int startj = i + 1;
            int wordOffset = 1;
            for (int j = startj; j < endj; j++) {
               char c = word.charAt(wordOffset++);
               if (ar[j] != c) {
                  break;
               }
            }
            return i - offset;
         }
      }

      return -1;
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
            if (c == '\t') {
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
      if (c == '\t') {
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

   public String addLineNumbers(String str) {
      StringBBuilder sb = new StringBBuilder(uc);
      int index = 0;
      int beginIndex = 0;
      int lineCount = 1;
      while ((index = str.indexOf("\n", beginIndex)) != -1) {
         String line = str.substring(beginIndex, index);
         String strL = prettyStringPadBack(String.valueOf(lineCount), 3, ' ');
         sb.append(strL);
         sb.append(line);
         sb.append('\n');
         beginIndex = index + 1;
         lineCount++;
      }
      return sb.toString();
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
    * Add <code>breakChar</code> index relative to <code>offset</code> positions to the {@link IntBuffer}
    * 
    * For each breakChar, add next index position
    * @param chars
    * @param offset
    * @param len
    * @param b
    * @param breakChar
    */
   public void getBreaksChar(char[] chars, int offset, int len, IntBuffer b, char breakChar) {
      int breakLength = 0;
      int breakStartOffset = 0; //count from offset
      int start = offset + 0;
      int end = offset + len;
      for (int i = start; i < end; i++) {
         char c = chars[i];
         if (c == breakChar) {
            b.addInt(breakStartOffset);
            b.addInt(breakLength);
            breakStartOffset = i + 1;
            breakLength = 0;
         } else {
            breakLength++;
         }
      }
      //at least one line, might it be empty
      b.addInt(breakStartOffset);
      b.addInt(breakLength);
   }

   public void getBreaksChar(String str, IntBuffer b, char breakChar) {
      getBreaksChar(str.toCharArray(), 0, str.length(), b, breakChar);
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
    * @see StringUtils#getBreaksLineNatural(char[], int, int, IntBuffer)
    * 
    */
   public int[] getBreaksLineNatural(char[] chars, int offset, int len) {
      IntBuffer b = new IntBuffer(uc, 5); //count index position of \n special char
      getBreaksLineNatural(chars, offset, len, b);
      return b.getIntsClonedTrimmed();
   }

   /**
    * Appends the relative offsets and line length to the IntBuffer.
    * <br>
    * <br>
    * <li>0 : number of slots used including this one
    * <li>1 : starting offset of line in the string
    * <li>2 : number of chars excluding the line break special characters
    * <li>3 : extra value for char width but is not computed here
    * <li>4 : starting offset of line in the string of 2nd line
    * <li>5 : number of chars excluding the line break special characters of 2nd line
    * <li>6 : extra value for char width but is not computed here
    * <li> etc
    * 
    * <p>
    *  Does not write any state flags at the end of the buffer
    * </p>
    * @param chars
    * @param offset
    * @param len
    * @param b
    * 
    */
   public void getBreaksLineNatural(char[] chars, int offset, int len, IntBuffer b) {
      getBreaksLineNatural(chars, offset, len, b, 1, -1);
   }

   public void getBreaksLineNatural(char[] chars, int offset, int len, IntBuffer b, int num, int fill) {
      int lineLength = 0;
      int lineStartOffset = 0; //count from 0, because relative offset
      int start = offset + 0;
      int startRelative = 0;
      int end = offset + len;
      for (int i = start; i < end; i++) {
         char c = chars[i];
         if (c == NEW_LINE) {
            b.addInt(lineStartOffset);
            b.addInt(lineLength);
            b.fill(fill, num);
            //start of the next line
            lineStartOffset = startRelative + 1;
            lineLength = 0;
         } else if (c == NEW_LINE_CARRIAGE_RETURN) {
            b.addInt(lineStartOffset);
            b.addInt(lineLength);
            b.fill(fill, num);
            //assume we have a \n afterwards
            //if none, next letter is eaten
            lineStartOffset = startRelative + 2;
            lineLength = 0;
            i = i + 1;
         } else {
            lineLength++;
         }
         startRelative++;
      }
      //at least one line, might it be empty
      b.addInt(lineStartOffset);
      b.addInt(lineLength);
      b.fill(fill, num);
   }

   public void getBreaksLineNatural(char[] chars, int offset, int len, IntIntervals ii) {
      int lineLength = 0;
      int lineStartOffset = 0; //count from 0, because relative offset
      int start = offset + 0;
      int startRelative = 0;
      int end = offset + len;
      for (int i = start; i < end; i++) {
         char c = chars[i];
         if (c == NEW_LINE) {
            ii.addInterval(lineStartOffset, lineLength);
            //start of the next line
            lineStartOffset = startRelative + 1;
            lineLength = 0;
         } else if (c == NEW_LINE_CARRIAGE_RETURN) {
            ii.addInterval(lineStartOffset, lineLength);
            //assume we have a \n afterwards
            //if none, next letter is eaten
            lineStartOffset = startRelative + 2;
            lineLength = 0;
            i = i + 1;
         } else {
            lineLength++;
         }
         startRelative++;
      }
      //at least one line, might it be empty
      ii.addInterval(lineStartOffset, lineLength);
   }

   /**
    * Null if  no line breaks.
    * 
    * @param str
    * @return
    */
   public int[] getBreaksLineNatural(String str) {
      char[] charArray = str.toCharArray();
      int length = str.length();
      return getBreaksLineNatural(charArray, 0, length);
   }

   /**
    * Appends the breaking data in the {@link IntBuffer}
    * each line is offset and number of characters in the line
    * @param str
    * @param b
    * @throws NullPointerException str is null or b is null 
    */
   public void getBreaksLineNatural(String str, IntBuffer b) {
      char[] charArray = str.toCharArray();
      int length = str.length();
      getBreaksLineNatural(charArray, 0, length, b);
   }

   public void getBreaksTab(char[] chars, int offset, int len, IntBuffer b) {
      getBreaksChar(chars, offset, len, b, '\t');
   }

   /**
    * An integer array describing tabs positions
    * @param str
    * @return
    * @see StringUtils#getBreaksChar(char[], int, int, IntBuffer, char)
    */
   public int[] getBreaksTab(String str) {
      char[] chars = str.toCharArray();
      int length = str.length();
      IntBuffer b = new IntBuffer(uc, 5); //count index position of \n special char
      getBreaksChar(chars, 0, length, b, '\t');
      return b.getIntsClonedTrimmed();
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
      getBreaksWord(chars, offset, len, invisibleOnly, b);
      return b.getIntsClonedTrimmed();
   }

   /**
    * Appends the {@link IntBuffer} with words offsets and lengths
    * <p>
    * When invisibleOnly is false, removed characters like .,?! from words
    * 
    * <li> {@link StringUtils#isWordDelimiter(char)}
    * <li> {@link StringUtils#isWordDelimiterInvisible(char)}
    * </p>
    * @param chars
    * @param offset
    * @param len
    * @param invisibleOnly
    * @param b
    */
   public void getBreaksWord(char[] chars, int offset, int len, boolean invisibleOnly, IntBuffer b) {
      //kick start, we don't know if we are inside a word or not.
      boolean isInsideWord = false; //we don't know 
      int wordLetterCount = 0;
      int wordStartOffset = offset;
      int start = offset;
      int end = offset + len;
      for (int i = start; i < end; i++) {
         char c = chars[i];
         boolean isWordDelimiter = invisibleOnly ? isWordDelimiterInvisible(c) : isWordDelimiter(c);
         if (i == start) {
            isInsideWord = !isWordDelimiter;
         }
         if (isWordDelimiter) {
            if (isInsideWord) {
               //we end a word
               b.addInt(wordStartOffset);
               b.addInt(wordLetterCount);
               isInsideWord = false;
            }
         } else {
            if (!isInsideWord) {
               //first letter of a new word
               wordStartOffset = i;
               wordLetterCount = 1;
               isInsideWord = true;
            } else {
               wordLetterCount++;
            }
         }
      }
      //for the last letter
      if (isInsideWord) {
         b.addInt(wordStartOffset);
         b.addInt(wordLetterCount);
      }
   }

   public int[] getBreaksWord(String str) {
      char[] chars = str.toCharArray();
      int length = str.length();
      return getBreaksWord(chars, 0, length, false);
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

   /**
    * [1,2,3],":" -> "1:2:3"
    * @param ar
    * @param separator
    * @return
    */
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
    * pasa.cbentley,. -> cbentley
    * pasa.cbentley,! -> pasa.cbentley
    * @param str
    * @param del
    * @return
    */
   public String getStringAfterLastIndex(String str, char del) {
      int lastIndex = str.lastIndexOf(del);
      if (lastIndex == -1) {
         return str;
      } else {
         return str.substring(lastIndex + 1);
      }
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
    * 
    * Example: home4you,4 -> you
    *  
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

   /**
    * Example: home4you,4 -> home
    * @param str
    * @param delEnd
    * @return
    */
   public String getSubstringStartToChar(String str, char delEnd) {
      int indexEnd = str.indexOf(delEnd);
      if (indexEnd != -1) {
         return str.substring(0, indexEnd);
      }
      return null;
   }

   /**
    * 
    * @param str
    * @param delEnd
    * @return
    */
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
}
