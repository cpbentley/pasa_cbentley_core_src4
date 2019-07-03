package pasa.cbentley.core.src4.helpers;

import pasa.cbentley.core.src4.ctx.UCtx;
import pasa.cbentley.core.src4.logging.Dctx;
import pasa.cbentley.core.src4.logging.IStringable;

/**
 * Encapsulate the byte by byte iteration on a byte array.
 * <br>
 * @author Charles Bentley
 *
 */
public class BytesIterator implements IStringable {

   private byte[] array;

   private int    position;

   private int    rootOffset;

   private UCtx   uc;

   public BytesIterator(UCtx uc, byte[] ar) {
      this(uc, ar, 0);
   }

   public BytesIterator(UCtx uc, byte[] ar, int index) {
      this.uc = uc;
      this.array = ar;
      this.position = index;
      rootOffset = index;
   }

   public byte[] getArray() {
      return array;
   }

   public int getPosition() {
      return position;
   }

   public int getRootOffset() {
      return rootOffset;
   }

   /**
    * If 
    * @return
    */
   public boolean hasMore() {
      return position < array.length;
   }

   /**
    * Move pointer position
    * @param i
    */
   public void incrementBy(int i) {
      this.position = position + i;
   }

   //#mdebug
   public String toString() {
      return Dctx.toString(this);
   }

   public void toString(Dctx dc) {
      dc.root(this, "BytesIterator");
      toStringPrivate(dc);
   }

   public String toString1Line() {
      return Dctx.toString1Line(this);
   }

   public void toString1Line(Dctx dc) {
      dc.root1Line(this, "BytesIterator");
      toStringPrivate(dc);
   }

   public UCtx toStringGetUCtx() {
      return uc;
   }

   private void toStringPrivate(Dctx dc) {
      dc.appendVarWithSpace("position", getPosition());
      dc.appendVarWithSpace("array.length", getArray().length);
      dc.appendVarWithSpace("rootOffset", rootOffset);
   }
   //#enddebug
   

}
