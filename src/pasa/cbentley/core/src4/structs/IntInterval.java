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
    * <li>0 if inside
    * <li>1 if bigger
    * <li>-1 if smaller
    * @param index
    * @return
    */
   public int getIndexPosition(int index) {
      if (index < offset) {
         return -1;
      }
      if (index < offset + len) {
         return 0;
      }
      return 1;
   }

   /**
    * <li>0 if intersect
    * <li>1 if above
    * <li>-1 if below
    * @param index
    * @return
    */
   public int getPositionInterval(IntInterval ii) {
      if (ii.getOffsetEnd() <= offset) {
         return -1;
      }
      if (ii.getOffset() >= this.getOffsetEnd()) {
         return 1;
      }
      return 0;
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

   public int getLen() {
      return len;
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

   /**
    * The {@link StringFx} for this interval of text
    * @return
    */
   public Object getPayload() {
      return payload;
   }

   public void incrLen(int incr) {
      this.len += incr;
   }

   /**
    * 
    * @param offset
    * @param len
    * @return
    */
   public boolean isContainedBy(int offset, int len) {
      if (offset <= this.offset && (offset + len) >= this.offset + this.len) {
         return true;
      }
      return false;
   }

   /**
    * Could be equal to, returns true
    * @param ii
    * @return
    */
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

   public boolean isContaining(IntInterval ii) {
      return isContaining(ii.offset, ii.len);
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
    * When offset and len are equal
    * @param offset
    * @param len
    * @return
    */
   public boolean isIntervalEqual(int offset, int len) {
      if (this.offset == offset && this.len == len) {
         return true;
      }
      return false;
   }

   public boolean isIntervalEqual(IntInterval ii) {
      return isIntervalEqual(ii.offset, ii.len);
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
    * Modifies parameters of si.
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

   public void setLen(int len) {
      this.len = len;
   }

   public void setOffset(int offset) {
      this.offset = offset;
   }

   public void setPayload(Object payload) {
      this.payload = payload;
   }

   /**
    * Reduce its value.
    * what if in middle, cuts the interval at offset
    * @param si
    * @return the value that parameter si substracted to this instance
    * 0 meaning no substraction
    * 
    * When len is brought back to zero? offset stays the same.
    */
   public int substract(IntInterval si) {
      if (si.offset <= this.offset) {
         int substraction = si.getOffsetEnd() - this.offset;
         if (substraction > 0) {
            if (substraction >= len) {
               int rv = len;
               len = 0;
               return rv;
            } else {
               len -= substraction;
               this.offset += substraction;
               return substraction;

            }
         }
      } else {
         int sub = this.getOffsetEnd() - si.offset;
         if (sub > 0) {
            len -= sub;
            return sub;
         }
      }
      return 0;
   }

   //#mdebug
   public void toString(Dctx dc) {
      dc.root(this, IntInterval.class, "@line5");
      toStringPrivate(dc);
      super.toString(dc.sup());
      dc.nlLvlO(payload, "payload");
   }

   public void toString1Line(Dctx dc) {
      dc.root1Line(this, IntInterval.class);
      toStringPrivate(dc);
      super.toString1Line(dc.sup1Line());
   }

   private void toStringPrivate(Dctx dc) {
      dc.appendVarWithSpace("offset", offset);
      dc.appendVarWithSpace("len", len);
   }

   /**
    * Create an {@link IntInterval} from the intersection of this instance and offset/len
    * 
    * @param offset
    * @param len
    * @return null if no intersection
    */
   public IntInterval createFromIntersectionWith(int offset, int len) {
      int myend = offset + len;
      int thisend = this.getOffsetEnd();
      if (offset < this.offset) {
         int newlen = myend - this.offset;
         if (newlen > 0) {
            return new IntInterval(uc, this.offset, newlen);
         }
         return null;
      }
      //we have an intersection
      int flen = thisend - offset;
      if (flen > 0) {
         if (myend < thisend) {
            //we are inside
            flen = len;
         }
         return new IntInterval(uc, offset, flen);
      }
      return null;
   }

   /**
    * Create an {@link IntInterval} from the intersection
    * @param intervalBelowStringLeaves
    * @return
    */
   public IntInterval createFromIntersectionWith(IntInterval ii) {
      return createFromIntersectionWith(ii.offset, ii.len);
   }

   //#enddebug

}
