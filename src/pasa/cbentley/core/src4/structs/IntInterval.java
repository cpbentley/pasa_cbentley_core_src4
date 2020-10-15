package pasa.cbentley.core.src4.structs;

import pasa.cbentley.core.src4.ctx.ObjectU;
import pasa.cbentley.core.src4.ctx.UCtx;
import pasa.cbentley.core.src4.logging.Dctx;

/**
 * 
 * @author Charles Bentley
 *
 */
public class IntInterval extends ObjectU {

   private int    len;

   private int    offset;

   private Object payload;

   public IntInterval(UCtx uc) {
      super(uc);
   }

   public IntInterval(UCtx uc, int offset, int len) {
      super(uc);
      this.offset = offset;
      this.len = len;
   }

   /**
    * The {@link StringFx} for this interval of text
    * @return
    */
   public Object getPayload() {
      return payload;
   }

   public int getLen() {
      return len;
   }

   public boolean isContaining(IntInterval ii) {
      return isContaining(ii.offset, ii.len);
   }

   public boolean isContainedBy(IntInterval ii) {
      return isContainedBy(ii.offset, ii.len);
   }

   /**
    * True when 
    * @param offset
    * @param len
    * @return
    */
   public boolean isContaining(int offset, int len) {
      if (this.offset <= offset && (this.offset + this.len) >= offset + len) {
         return true;
      }
      return false;
   }

   public boolean isContainedBy(int offset, int len) {
      if (offset <= this.offset && (offset + len) >= this.offset + this.len) {
         return true;
      }
      return false;
   }

   /**
    * True when intervals overlap one another
    * @param offset
    * @param len
    * @return
    */
   public boolean isIntersect(int offset, int len) {
      if (this.offset > offset + len) {
         return false;
      } else if (this.offset + this.len < offset) {
         return false;
      } else {
         return true;
      }

   }

   public boolean isIntersect(IntInterval ii) {
      return isIntersect(ii.offset, ii.len);
   }

   /**
    * The offset in {@link Stringer} at which this {@link StringInterval} starts.
    * @return
    */
   public int getOffset() {
      return offset;
   }

   public int getOffsetEnd() {
      return offset + len;
   }

   public void setLen(int len) {
      this.len = len;
   }

   public void incrLen(int incr) {
      this.len += incr;
   }

   public void setOffset(int offset) {
      this.offset = offset;
   }

   /**
    * Modifies parameters
    * @param si
    */
   public void merge(IntInterval si) {
      if (this.offset < si.offset) {
         mergeFromBelowOf(si);
      } else {
         mergeFromAboveOf(si);
      }
   }

   /**
    * Modifies parameters
    * @param si
    */
   public void mergeFromBelowOf(IntInterval si) {
      int lenNew = this.len;
      int add = si.getOffsetEnd() - this.getOffsetEnd();
      if (add > 0) {
         lenNew += add;
      }
      si.setOffset(this.offset);
      si.setLen(lenNew);
   }

   /**
    * @param si
    */
   public void mergeFromAboveOf(IntInterval si) {
      int lenNew = si.len;
      int add = this.getOffsetEnd() - si.getOffsetEnd();
      if (add > 0) {
         lenNew += add;
      }
      //offset does not change
      si.setLen(lenNew);
   }

   /**
    * True when index is inside the {@link StringInterval}
    * @param index
    * @return
    */
   public boolean isInside(int index) {
      return offset <= index && (offset + len) < index;
   }

   /**
    * 
    * 10:8 and offset=15 return 18-15 = 3
    * 10:8 and offset=10 return 18-10 = 8 = len
    * @param offset
    * @return
    */
   public int getLeftCenter(int offset) {
      return this.offset + this.len - offset;
   }

   //#mdebug
   public void toString(Dctx dc) {
      dc.root(this, IntInterval.class, "@line5");
      toStringPrivate(dc);
      super.toString(dc.sup());
      dc.nlLvlO(payload, "payload");
   }

   private void toStringPrivate(Dctx dc) {
      dc.appendVarWithSpace("offset", offset);
      dc.appendVarWithSpace("len", len);
   }

   public void toString1Line(Dctx dc) {
      dc.root1Line(this, IntInterval.class);
      toStringPrivate(dc);
      super.toString1Line(dc.sup1Line());
   }

   //#enddebug

}
