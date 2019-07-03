package pasa.cbentley.core.src4.io;

import pasa.cbentley.core.src4.ctx.UCtx;
import pasa.cbentley.core.src4.logging.Dctx;
import pasa.cbentley.core.src4.logging.IStringable;
import pasa.cbentley.core.src4.structs.IntBuffer;

/**
 * Memory efficient String for reading huge amount of strings sequentially.
 * <br>
 * Kind of a string buffer to implement a mutable {@link String} <br>
 * <br>
 * When reading string information.
 * @author Charles Bentley
 *
 */
public class XString implements IStringable {

   private char[]       chars;

   private int          offset;

   private int          len;

   protected final UCtx uc;

   /**
    */
   public XString(UCtx uc) {
      this.uc = uc;
      ib = new IntBuffer(uc, 10);
   }

   public XString(UCtx uc, char[] c, int offset, int len) {
      this.uc = uc;
      this.chars = c;
      this.offset = offset;
      this.len = len;
      ib = new IntBuffer(uc, 10);
   }

   public void set(char[] ar, int offset, int len) {
      this.chars = ar;
      this.offset = offset;
      this.len = len;
   }

   /**
    * The relative to offset
    * @param c
    * @return -1 if not found
    * @see XString#indexOf(char, int)
    */
   public int indexOf(char c) {
      return indexOf(c, 0);
   }

   private IntBuffer ib;

   /**
    * Computes the token with starting offset and length of token.
    * <br>
    * Offset in the returned array are relative to base offset. So the method
    * {@link XString#getStringRelative(int, int)} must be used for correct behavior.
    * <br>
    * Single thread only!!
    * <br>
    * Removes duplicates and surronding delimiters.
    * <br>
    * <br>
    * Hello Brother
    * Gives Hello for 0 and 1 and Brother for 2 and 3
    * @param del
    * @return
    */
   public int[] getTokens(char del) {
      ib.clear();
      int count = 0;
      int startToken = 0;
      int v = -1;
      while ((v = indexOf(del, startToken)) != -1) {
         count++;
         int len = v - startToken;
         if (len != 0) {
            ib.addInt(startToken);
            ib.addInt(len);
         }
         startToken = v + 1;
      }
      if (getLen() - startToken != 0) {
         ib.addInt(startToken);
         ib.addInt(getLen() - startToken);
      }
      return ib.getIntsClonedTrimmed();
   }

   public char getChar(int offset) {
      return chars[this.offset + offset];
   }

   public int[] getTokensDuplicates(char del) {
      ib.clear();
      int startToken = 0;
      int v = -1;
      while ((v = indexOf(del, startToken)) != -1) {
         int len = v - startToken;
         ib.addInt(startToken);
         ib.addInt(len);
         startToken = v + 1;
      }
      if (getLen() - startToken != 0) {
         ib.addInt(startToken);
         ib.addInt(getLen() - startToken);
      }
      return ib.getIntsClonedTrimmed();
   }

   /**
    * Returns the Strings in the {@link XString} that are separated by the given delimiter.
    * <br>
    * <br>
    * 
    * @param del
    * @return
    */
   public String[] getTokenStrings(char del) {
      int[] tok = this.getTokens(del);
      int count = tok.length / 2;
      String[] d = new String[count];
      int x = 0;
      for (int i = 0; i < tok.length; i += 2) {
         d[x] = this.getStringRelative(tok[i], tok[i + 1]);
         x++;
      }
      return d;
   }

   /**
    * The relative to offset index of char c.
    * <br>
    * <br>
    * It starts to look at offset + pos.
    * <br>
    * <br>
    * 
    * @param c
    * @param pos 0 relative offset.
    * @return -1 if not found
    */
   public int indexOf(char c, int pos) {
      int llen = offset + getLen();
      int indexof = -1;
      for (int i = offset + pos; i < llen; i++) {
         if (chars[i] == c) {
            indexof = i;
            return indexof - offset;
         }
      }
      return -1;
   }

   public String getStringAbsolute(int offset, int len) {
      return new String(chars, offset, len);
   }

   public String getString() {
      return new String(chars, offset, len);
   }

   /**
    * 
    * @param offset
    * @param len
    * @return
    */
   public String getStringRelative(int offset, int len) {
      return new String(chars, this.offset + offset, len);
   }

   public XString getXStringRelative(int offset, int len) {
      return new XString(uc, chars, this.offset + offset, len);
   }

   public void reset() {
      len = 0;
   }

   /**
    * Sets the String as the internal String.
    * Old array is gone.
    * @param str
    */
   public void set(String str) {
      chars = str.toCharArray();
      len = chars.length;
      offset = 0;
   }

   //#mdebug
   public String toString() {
      return Dctx.toString(this);
   }

   public void toString(Dctx dc) {
      dc.root(this, "XString");
      dc.appendWithSpace(new String(chars, offset, getLen()));
   }

   public String toString1Line() {
      return Dctx.toString1Line(this);
   }

   public void toString1Line(Dctx dc) {
      dc.root1Line(this, "XString");
   }

   public UCtx toStringGetUCtx() {
      return uc;
   }
   //#enddebug

   public int getLen() {
      return len;
   }

}