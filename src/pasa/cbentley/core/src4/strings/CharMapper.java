package pasa.cbentley.core.src4.strings;

import pasa.cbentley.core.src4.ctx.ObjectU;
import pasa.cbentley.core.src4.ctx.UCtx;
import pasa.cbentley.core.src4.helpers.StringBBuilder;
import pasa.cbentley.core.src4.logging.Dctx;
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
   public static final int OP_0_KEEP            = 0;

   /**
    * size 3
    */
   public static final int OP_1_REPLACE_ONE     = 1;

   /**
    * size 2
    */
   public static final int OP_2_REMOVE          = 2;

   /**
    * size 3 + len
    */
   public static final int OP_3_REPLACE_SEVERAL = 3;

   public static final int OP_4_ADD_ONE         = 4;

   public static final int OP_5_ADD_SEVERAL     = 5;

   private IntBuffer       buff;

   char[]                  visualCharacters;

   char[]                  charsSrc;

   /**
    * The mapping does not change offsets from src char array.
    * Simply replace some characters with others
    */
   boolean                 isOffsetEquivalent;

   int                     lengthSrc;

   /**
    * Maps the visual index (map offset) to the sourceOffset in srcCharArray.
    * 
    * When several characters replaced a single one, each point to that replaced character.
    */
   private int[]           visualOffsetToSrcOffset;

   /**
    * offset in Stringer of the first model character of this map
    */
   private int             offset;

   /**
    * Zero-Based offset at which the charMap starts in the source array relative to offsetSrc.
    */
   private int             offsetVisualRelative;

   /**
    * Offset at which the data starts in the char array.
    */
   private int             offsetSrc;

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
         offsetSrc = visualOffsetToSrcOffset[offsetStart];
      }
      int lenSrc = 0;
      if (offsetStart + len == visualCharacters.length) {
         //take everything until end of src
         lenSrc = this.lengthSrc - offsetSrc;
      } else {
         int offsetInSource = visualOffsetToSrcOffset[offset + len - 1]; //
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
         } else if (op == OP_5_ADD_SEVERAL) {
            int lenStr = buff.get(i + 2);
            totalSizeCharArray += (lenStr); //only addition
            i += 3 + lenStr;
         } else if (op == OP_2_REMOVE) {
            totalSizeCharArray--; //remove it
            i += 2;
         } else if (op == OP_1_REPLACE_ONE) {
            //no change in size since one char replaces the other
            i += 3;
         } else if (op == OP_4_ADD_ONE) {
            totalSizeCharArray++;
            i += 3;
         } else {
            //no changes
            i += 2;
         }
      }
      visualOffsetToSrcOffset = new int[totalSizeCharArray];
      visualCharacters = new char[totalSizeCharArray];

      int mapSize = Math.max(totalSizeCharArray, lengthSrc);
      int[] map = new int[mapSize];
      //map array. for each character in the source. maps an action 
      //when zero i.e not initialized, it means the

      if (totalSizeCharArray == 0) {
         //reduce to null
      } else {
         size = buff.getSize();
         for (int i = 0; i < size;) {

            int op = buff.get(i);
            int offsetInSource = buff.get(i + 1); //src relative
            //offsetInMap is lineoffset relative
            int offsetVisual = offsetInSource - offsetVisualRelative; //minus line offset 
            //use i+1 to make sure it is never equals to zero which means empty
            int mapBufferIndexPlusOne = i + 1;
            map[offsetVisual] = mapBufferIndexPlusOne;
            if (op == OP_3_REPLACE_SEVERAL) {
               int lenStr = buff.get(i + 2);
               i = i + 3 + lenStr;
            } else if (op == OP_2_REMOVE) {
               i += 2;
            } else if (op == OP_5_ADD_SEVERAL) {
               int lenStr = buff.get(i + 2);
               i = i + 3 + lenStr;
            } else if (op == OP_1_REPLACE_ONE) {
               i += 3;
            } else if (op == OP_4_ADD_ONE) {
               i += 3;
            }
         }
         //
         //tracks the visual offset
         int offsetVisual = 0;
         int offsetSrc = this.offsetSrc;
         for (int i = 0; i < map.length; i++) {
            int bufferIndexPlusOneValue = map[i];
            //check if we have
            if (bufferIndexPlusOneValue == 0) {
               if (offsetVisual >= visualCharacters.length) {
                  break;
               }
               char charSrc = charsSrc[offsetSrc];
               visualCharacters[offsetVisual] = charSrc;
               visualOffsetToSrcOffset[offsetVisual] = offsetSrc;
               offsetSrc++;
               offsetVisual++;
            } else {
               //get the index of the operator from the map value
               int indexOp = bufferIndexPlusOneValue - 1;
               int op = buff.get(indexOp);
               if (op == OP_1_REPLACE_ONE) {
                  int indexChar = bufferIndexPlusOneValue + 1;
                  visualCharacters[offsetVisual] = (char) buff.get(indexChar);
                  visualOffsetToSrcOffset[offsetVisual] = offsetSrc;
                  offsetSrc++;
                  offsetVisual++;
               } else if (op == OP_4_ADD_ONE) {
                  int indexChar = bufferIndexPlusOneValue + 1;
                  visualCharacters[offsetVisual] = (char) buff.get(indexChar);
                  visualOffsetToSrcOffset[offsetVisual] = offsetSrc;
                  offsetVisual++;
               } else if (op == OP_3_REPLACE_SEVERAL) {
                  int indexLenStr = bufferIndexPlusOneValue + 1;
                  int lenStr = buff.get(indexLenStr);
                  for (int j = 0; j < lenStr; j++) {
                     int indexBuff = indexLenStr + 1 + j;
                     char replacingChar = (char) buff.get(indexBuff);
                     visualCharacters[offsetVisual] = replacingChar;
                     visualOffsetToSrcOffset[offsetVisual] = offsetSrc;
                     offsetVisual++;
                  }
                  offsetSrc++;
               } else if (op == OP_2_REMOVE) {
                  offsetSrc++;
               } else if (op == OP_5_ADD_SEVERAL) {
                  int indexLenStr = bufferIndexPlusOneValue + 1;
                  int lenStr = buff.get(indexLenStr);
                  for (int j = 0; j < lenStr; j++) {
                     visualCharacters[offsetVisual] = (char) buff.get(indexLenStr + 1 + j);
                     visualOffsetToSrcOffset[offsetVisual] = offsetSrc;
                     offsetVisual++;
                  }
               }
            }
         }
      }
   }

   /**
    * Reference to the array of mapped characters 
    * @return
    */
   public char[] getCharsMapped() {
      return visualCharacters;
   }

   public char getModelChar(int offset) {
      int mapOffset = visualOffsetToSrcOffset[offset];
      return charsSrc[mapOffset];
   }

   /**
    * Removes 
    * @return
    */
   public String getModelStr() {
      StringBBuilder sb = new StringBBuilder(uc);
      int count = 0;
      for (int i = 0; i < visualOffsetToSrcOffset.length; i++) {
         if (visualOffsetToSrcOffset[i] == 0 || visualOffsetToSrcOffset[i] == 2) {
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
      return new String(visualCharacters);
   }

   public String getStringMapped(int offset, int len) {
      return new String(visualCharacters);
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
         offsetSrc = visualOffsetToSrcOffset[offset];
      }
      int lenSrc = 0;
      if (offset + len == visualCharacters.length) {
         //take everything until end of src
         lenSrc = this.lengthSrc - offsetSrc + this.offsetSrc;
      } else {
         int offsetInSource = visualOffsetToSrcOffset[offset + len - 1]; //
         if (offsetSrc == offsetInSource) {
            lenSrc = 1;
         } else {
            lenSrc = len;
         }
      }
      String str = new String(charsSrc, offsetSrc, lenSrc);
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
         int v = visualOffsetToSrcOffset[j];
         if (v == 0) {

         }
      }
      return i;
   }

   public void opKeep(int offset) {
      buff.addInt(OP_0_KEEP);
      buff.addInt(offset);
   }

   private int sizeDiff;

   public void opRemove(int offset) {
      buff.addInt(OP_2_REMOVE);
      buff.addInt(offset);
      sizeDiff--;
   }

   /**
    * 
    * @param offset
    * @param num
    * @param c
    */
   public void opReplaceChar(int offset, char c) {
      buff.addInt(OP_1_REPLACE_ONE);
      buff.addInt(offset);
      buff.addInt(c);
   }

   public void opAddChar(int offset, char c) {
      buff.addInt(OP_4_ADD_ONE);
      buff.addInt(offset);
      buff.addInt(c);
      sizeDiff++;
   }

   /**
    * Tells the map, that at this stringer offset, we have something special
    * @param offset 
    * @param c
    */
   public void opReplaceOne(int offset, char c) {
      buff.addInt(OP_1_REPLACE_ONE);
      buff.addInt(offset);
      buff.addInt(c);
   }

   public void opAddChars(int offset, String str) {
      buff.addInt(OP_5_ADD_SEVERAL);
      buff.addInt(offset);
      int length = str.length();
      buff.addInt(length);
      for (int i = 0; i < length; i++) {
         buff.addInt(str.charAt(i));
      }
      sizeDiff += length;
   }

   public int getSizeDiff() {
      return sizeDiff;
   }

   public void opReplaceWith(int offset, String str) {
      int length = str.length();
      if (length == 0) {
         throw new IllegalArgumentException("try remove ?");
      }
      if (length == 1) {
         this.opReplaceChar(offset, str.charAt(0));
      } else {
         buff.addInt(OP_3_REPLACE_SEVERAL);
         buff.addInt(offset);
         buff.addInt(length);
         for (int i = 0; i < length; i++) {
            buff.addInt(str.charAt(i));
         }
         sizeDiff += (length - 1);
      }
   }

   public void reset() {
      buff.clear();
   }

   public void setOffsetExtra(int offsetExtra) {
      this.offsetVisualRelative = offsetExtra;
   }

   /**
    * 
    * @param src
    * @param offset
    * @param len
    */
   public void setSource(char[] src, int offset, int len) {
      this.charsSrc = src;
      this.offsetSrc = offset;
      this.lengthSrc = len;
   }

   public void setSource(String str) {
      this.setSource(str.toCharArray(), 0, str.length());
   }

   //#mdebug
   public void toString(Dctx dc) {
      dc.root(this, CharMapper.class, 380);
      toStringPrivate(dc);
      super.toString(dc.sup());

      dc.appendVarWithSpace("offset", offset);
      dc.appendVarWithSpace("offsetExtra", offsetVisualRelative);
      dc.appendVarWithSpace("offsetSrc", offsetSrc);
      dc.appendVarWithSpace("lengthSrc", lengthSrc);
      dc.appendVarWithSpace("isOffsetEquivalent", isOffsetEquivalent);

      dc.nlLvl("map", visualOffsetToSrcOffset, 10);
      dc.nlLvl("chars", visualCharacters, 10);
      dc.nlLvl("charsSrc", charsSrc, 30);

      dc.nlLvl(buff, "buff");
      dc.appendWithNewLine("Buffer Interpreted");
      int size = buff.getSize();
      int count = 0;
      for (int i = 0; i < size;) {
         int op = buff.get(i);
         dc.nl();
         dc.append("#" + count);
         count++;
         dc.appendVarWithSpace("Operator", toStringOp(op));
         int offsetInSource = buff.get(i + 1); //src relative
         dc.appendVarWithSpace("offsetInSource", offsetInSource);
         //offsetInMap is lineoffset relative
         int offsetVisual = offsetInSource - offsetVisualRelative; //minus line offset 
         dc.appendVarWithSpace("offsetInLine", offsetVisual);
         if (op == OP_3_REPLACE_SEVERAL || op == OP_5_ADD_SEVERAL) {
            int lenStr = buff.get(i + 2);
            char[] cs = new char[lenStr];
            for (int j = 0; j < lenStr; j++) {
               int indexBuff = i + 3 + j;
               char replacingChar = (char) buff.get(indexBuff);
               cs[j] = replacingChar;
            }
            dc.appendVarWithSpace("str", new String(cs));
            i = i + 3 + lenStr;
         } else if (op == OP_2_REMOVE) {
            i += 2;
         } else if (op == OP_5_ADD_SEVERAL) {
            int lenStr = buff.get(i + 2);
            i = i + 3 + lenStr;
         } else if (op == OP_1_REPLACE_ONE || op == OP_4_ADD_ONE) {
            char c = (char) buff.get(i + 2);
            dc.appendVarWithSpace("char", String.valueOf(c));
            i += 3;
         }
      }
   }

   public String toStringOp(int op) {
      switch (op) {
         case OP_0_KEEP:
            return "Keep";
         case OP_1_REPLACE_ONE:
            return "ReplaceOne";
         case OP_2_REMOVE:
            return "Remove";
         case OP_3_REPLACE_SEVERAL:
            return "ReplaceSeveral";
         case OP_4_ADD_ONE:
            return "AddOne";
         case OP_5_ADD_SEVERAL:
            return "AddSeveral";
         default:
            return "Unknown " + op;
      }
   }

   public void toString1Line(Dctx dc) {
      dc.root1Line(this, CharMapper.class);
      toStringPrivate(dc);
      super.toString1Line(dc.sup1Line());
   }

   private void toStringPrivate(Dctx dc) {

   }
   //#enddebug

}
