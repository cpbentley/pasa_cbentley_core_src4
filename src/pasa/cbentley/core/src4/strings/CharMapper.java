package pasa.cbentley.core.src4.strings;

import pasa.cbentley.core.src4.ctx.ObjectU;
import pasa.cbentley.core.src4.ctx.UCtx;
import pasa.cbentley.core.src4.helpers.StringBBuilder;
import pasa.cbentley.core.src4.structs.IntBuffer;
import pasa.cbentley.core.src4.structs.IntInterval;

/**
 * 
 * One or more chars from Src are visually replaced by 0,one or more chars
 * <p>
 * Maps Model chars to Visible chars.
 * 
 * </p>
 * 
 * @author Charles Bentley
 *
 */
public class CharMapper extends ObjectU {

   /**
    * size 2
    */
   private static final int OP_0_KEEP            = 0;

   /**
    * size 3
    */
   private static final int OP_1_REPLACE_ONE     = 1;

   /**
    * size 2
    */
   private static final int OP_2_REMOVE          = 2;

   /**
    * size 3 + len
    */
   private static final int OP_3_REPLACE_SEVERAL = 3;

   private IntBuffer        buff;

   char[]                   chars;

   char[]                   charsSrc;

   /**
    * The mapping does not change offsets from src char array.
    * Simply replace some characters with others
    */
   boolean                  isOffsetEquivalent;

   int                      lengthSrc;

   /**
    * Maps the visual index (map offset) to the sourceOffset in srcCharArray.
    * 
    * When several characters replaced a single one, each point to that replaced character.
    */
   private int[]            map;

   /**
    * offset in Stringer of the first model character of this map
    */
   private int              offset;

   int                      offsetSrc;

   public CharMapper(UCtx uc) {
      super(uc);
      buff = new IntBuffer(uc);
   }

   public void appendStringSrc(StringBBuilder sb, int offsetStart, int offsetEnd) {
      int offsetSrc = 0;
      int len = offsetEnd - offsetStart + 1;
      if (offsetStart == 0) {
         offsetSrc = 0;
      } else {
         offsetSrc = map[offsetStart];
      }
      int lenSrc = 0;
      if (offsetStart + len == chars.length) {
         //take everything until end of src
         lenSrc = this.lengthSrc - offsetSrc;
      } else {
         int offsetInSource = map[offset + len - 1]; //
         if (offsetSrc == offsetInSource) {
            lenSrc = 1;
         } else {
            lenSrc = len;
         }
      }
      int aOffset = this.offsetSrc + offsetSrc;
      sb.append(charsSrc, aOffset, lenSrc);
   }

   /**
    * Build the char array to be used for the line
    */
   public void build() {

      if (charsSrc == null) {
         throw new IllegalStateException("must set source char");
      }

      int totalSizeCharArray = lengthSrc;
      int size = buff.getSize();
      for (int i = 0; i < size;) {
         int op = buff.get(i); //offset from original src on which this map initially works
         if (op == OP_3_REPLACE_SEVERAL) {
            int lenStr = buff.get(i + 2);
            totalSizeCharArray += (lenStr - 1); //-1 cuz replace one char
            i += 3 + lenStr;
         } else if (op == OP_2_REMOVE) {
            totalSizeCharArray--; //remove it
            i += 2;
         } else if (op == OP_1_REPLACE_ONE) {
            i += 3;
         } else {
            //no changes
            i += 2;
         }
      }
      int[] mapOffset = new int[totalSizeCharArray];
      chars = new char[totalSizeCharArray];
      int mapSize = Math.max(totalSizeCharArray, lengthSrc);
      int[] map = new int[mapSize];

      if (totalSizeCharArray == 0) {
         //reduce to null
      } else {
         size = buff.getSize();
         for (int i = 0; i < size;) {
            int op = buff.get(i);
            int offsetInSource = buff.get(i + 1); //src
            int offsetInMap = offsetInSource - offsetSrc;
            //use i+1 to make sure it is never equals to zero which means empty
            if (op == OP_3_REPLACE_SEVERAL) {
               map[offsetInMap] = i + 1;
               int lenStr = buff.get(i + 2);
               i = i + 3 + lenStr;
            } else if (op == OP_2_REMOVE) {
               map[offsetInMap] = i + 1;
               i += 2;
            } else if (op == OP_1_REPLACE_ONE) {
               map[offsetInMap] = i + 1;
               i += 3;
            }
         }
         //
         int offsetMap = 0;
         int offsetSrc = this.offsetSrc;
         for (int i = 0; i < map.length; i++) {
            int currentMapValue = map[i];
            //check if we have
            if (currentMapValue == 0) {
               if (offsetMap >= chars.length) {
                  break;
               }
               chars[offsetMap] = charsSrc[offsetSrc];
               mapOffset[offsetMap] = offsetSrc - this.offsetSrc;
               offsetSrc++;
               offsetMap++;
            } else {
               int indexOp = currentMapValue - 1;
               int op = buff.get(indexOp);
               if (op == OP_1_REPLACE_ONE) {
                  int indexChar = currentMapValue + 1;
                  chars[offsetMap] = (char) buff.get(indexChar);
                  mapOffset[offsetMap] = offsetSrc - this.offsetSrc;
                  offsetSrc++;
                  offsetMap++;
               } else if (op == OP_3_REPLACE_SEVERAL) {
                  int indexLenStr = currentMapValue + 1;
                  int lenStr = buff.get(indexLenStr);
                  for (int j = 0; j < lenStr; j++) {
                     chars[offsetMap] = (char) buff.get(indexLenStr + 1 + j);
                     mapOffset[offsetMap] = offsetSrc - this.offsetSrc;
                     offsetMap++;
                  }
                  offsetSrc++;
               } else if (op == OP_2_REMOVE) {
                  offsetSrc++;
               }
            }
         }
      }

      this.map = mapOffset;
   }

   /**
    * Reference to the array of mapped characters 
    * @return
    */
   public char[] getCharsMapped() {
      return chars;
   }

   public char getModelChar(int offset) {
      int mapOffset = map[offset];
      return charsSrc[this.offsetSrc + mapOffset];
   }

   /**
    * Removes 
    * @return
    */
   public String getModelStr() {
      StringBBuilder sb = new StringBBuilder(uc);
      int count = 0;
      for (int i = 0; i < map.length; i++) {
         if (map[i] == 0 || map[i] == 2) {
            int stringerOffset = offset + count;
            char c = charsSrc[stringerOffset];
            sb.append(c);
            count++;
         }
      }
      return sb.toString();
   }

   /**
    * The String of the map
    * @return
    */
   public String getStringMapped() {
      return new String(chars);
   }

   /**
    * The String of the source
    * @return
    */
   public String getStringSrc() {
      return new String(charsSrc, offsetSrc, lengthSrc);
   }

   /**
    * As soon as at least a mapped char is included in the interval, the src char is included.
    * For a removed character, to be included it has to be fully inside the interval. unless it is itself
    * a frontier characters in the mapped interval.
    * 
    * <p>
    * This method is used when {@link CharMapper} displays selectable text and user want to copy the model chars
    * of the visible selection
    * </p>
    * @param offset value in mapped plane
    * @param len length value in mapped plane
    * @return
    */
   public String getStringSrc(int offset, int len) {
      int offsetSrc = 0;
      if (offset == 0) {
         offsetSrc = 0;
      } else {
         offsetSrc = map[offset];
      }
      int lenSrc = 0;
      if (offset + len == chars.length) {
         //take everything until end of src
         lenSrc = this.lengthSrc - offsetSrc;
      } else {
         int offsetInSource = map[offset + len - 1]; //
         if (offsetSrc == offsetInSource) {
            lenSrc = 1;
         } else {
            lenSrc = len;
         }
      }
      String str = new String(charsSrc, this.offsetSrc + offsetSrc, lenSrc);
      return str;
   }

   /**
    * When copying a selected text, we want the real
    * @param offset zero based relative to this map
    * @param len
    * @return
    */
   public IntInterval mapModelInterval(int offset, int len) {
      IntInterval i = new IntInterval(uc);
      for (int j = offset; j < offset + len; j++) {
         int v = map[j];
         if (v == 0) {

         }
      }
      return i;
   }

   public void opKeep(int offset) {
      buff.addInt(OP_0_KEEP);
      buff.addInt(this.offsetSrc + offset);
   }

   public void opRemove(int offset) {
      buff.addInt(OP_2_REMOVE);
      buff.addInt(this.offsetSrc + offset);
   }

   /**
    * 
    * @param offset
    * @param num
    * @param c
    */
   public void opReplaceChar(int offset, char c) {
      buff.addInt(OP_1_REPLACE_ONE);
      buff.addInt(this.offsetSrc + offset);
      buff.addInt(c);
   }

   /**
    * Tells the map, that at this stringer offset, we have something special
    * @param offset 
    * @param c
    */
   public void opReplaceOne(int offset, char c) {
      buff.addInt(OP_1_REPLACE_ONE);
      buff.addInt(this.offsetSrc + offset);
      buff.addInt(c);
   }

   public void opReplaceWith(int offset, String str) {
      if (str.length() == 1) {
         this.opReplaceChar(offset, str.charAt(0));
      } else {
         buff.addInt(OP_3_REPLACE_SEVERAL);
         buff.addInt(this.offsetSrc + offset);
         buff.addInt(str.length());
         for (int i = 0; i < str.length(); i++) {
            buff.addInt(str.charAt(i));
         }
      }
   }

   public void reset() {
      buff.clear();
   }

   public void setSource(char[] src, int offset, int len) {
      this.charsSrc = src;
      this.offsetSrc = offset;
      this.lengthSrc = len;
   }

   public void setSource(String str) {
      this.setSource(str.toCharArray(), 0, str.length());
   }

}
