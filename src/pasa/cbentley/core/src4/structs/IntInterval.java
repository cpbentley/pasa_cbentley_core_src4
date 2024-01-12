package pasa.cbentley.core.src4.structs;

import pasa.cbentley.core.src4.ctx.ObjectU;
import pasa.cbentley.core.src4.ctx.UCtx;
import pasa.cbentley.core.src4.logging.Dctx;
import pasa.cbentley.core.src4.text.StringInterval;

/**
 * An interval with a length of ZERO is a singularity
 * An interval with a negative length is illegal
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

   public IntInterval(UCtx uc, int offset, int len, Object payload) {
      super(uc);
      this.offset = offset;
      this.len = len;
      this.payload = payload;
   }

   public void copyFrom(IntInterval i) {
      this.len = i.len;
      this.offset = i.offset;
      this.payload = i.payload;
   }

   public void copyTo(IntInterval i) {
      i.len = this.len;
      i.offset = this.offset;
      i.payload = this.payload;
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
    * Create an {@link IntInterval} from the intersection of this instance and offset/len
    * <p>
    * 
    * </p>
    * @param offset
    * @param len
    * @return null if there is no intersection between this interval and [offset-len] interval
    */
   public IntInterval getIntersectionWith(int offset, int len) {
      //if there is an intersection, the offset of that intersect is necessary 
      //either one of the 2 offsets
      //then trick is to compute the length
      int offsetDifference = this.offset - offset;

      int intersectOffset = 0;
      int intersectLen = 0;
      if (offsetDifference > 0) {
         intersectOffset = this.offset;
         intersectLen = offset + len - this.offset;
         if (intersectLen > this.len) {
            intersectLen = this.len;
         }
      } else {
         intersectOffset = offset;
         intersectLen = this.getOffsetEnd() - offset;
         if (intersectLen > len) {
            intersectLen = len;
         }
      }

      if (intersectLen > 0) {
         return new IntInterval(uc, intersectOffset, intersectLen);
      }
      return null;

   }

   /**
    * Create an {@link IntInterval} from the intersection
    * @param intervalBelowStringLeaves
    * @return
    * @see IntInterval#getIntersectionWith(int, int)
    */
   public IntInterval getIntersectionWith(IntInterval ii) {
      return getIntersectionWith(ii.offset, ii.len);
   }

   /**
    * The distance between offset (included) and the end of the interval
    * <li> 10:8 -> [10,17] and offset=15 return 18-15 = 3
    * <li> 10:8 -> [10,17] and offset=17 return 18-17 = 1
    * <li> 10:8 -> [10,17] and offset=10 return 18-10 = 8 = len
    * @param offset
    * @return 
    */
   public int getDistanceToEnd(int offset) {
      return this.offset + this.len - offset;
   }

   /**
    * Distance from the startOffset.
    * <li> 10:8 -> [10,17] and offset=15 return 15-10 = 5
    * <li> 10:8 -> [10,17] and offset=10 return 10-10 = 0
    * @param offset
    * @return
    */
   public int getDistanceToStart(int offset) {
      return offset - this.offset;
   }

   public int getLen() {
      return len;
   }

   /**
    * The offset in {@link Stringer} at which this {@link StringInterval} starts.
    * It is included in the interval
    * @return
    */
   public int getOffset() {
      return offset;
   }

   /**
    * The first offset outside this interval
    * @return
    */
   public int getOffsetEnd() {
      return offset + len;
   }

   /**
    * The last offset inside this interval on the right
    * @return
    */
   public int getOffsetEndInside() {
      return offset + len - 1;
   }

   /**
    * The {@link StringFx} for this interval of text
    * @return
    */
   public Object getPayload() {
      return payload;
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
    * {@link IntIntervalRelation} with first interval being this
    * @param interval
    * @return
    */
   public IntIntervalRelation getRelation(IntInterval interval) {
      IntIntervalRelation iir = new IntIntervalRelation(uc);
      iir.setIntervalOne(this);
      iir.setIntervalTwo(interval);
      iir.compute();
      return iir;
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
    * 
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

   /**
    * Return true when this fully contains given {@link IntInterval}.
    * <p>
    * For collision detection, use {@link IntInterval#isIntersect(IntInterval)}
    * </p>
    * @param ii
    * @return
    */
   public boolean isContaining(IntInterval ii) {
      return isContaining(ii.offset, ii.len);
   }

   /**
    * True when index is inside the {@link StringInterval}
    * @param index
    * @return
    */
   public boolean isInside(int index) {
      return offset <= index && (offset + len) > index;
   }

   /**
    * True when intervals overlap one another
    * @param offset
    * @param len
    * @return
    */
   public boolean isIntersect(int offset, int len) {
      if (this.offset >= offset + len) {
         return false;
      } else if (this.offset + this.len <= offset) {
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
    * True when index is not inside the interval
    * @param index
    * @return
    */
   public boolean isOutside(int index) {
      return offset > index || (offset + len) <= index;
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

   public void setOffsets(int offsetStart, int offsetEnd) {
      this.offset = offsetStart;
      this.len = offsetEnd - offsetStart;
      if (len < 0) {
         throw new IllegalArgumentException();
      }
   }

   public void setPayload(Object payload) {
      this.payload = payload;
   }

   /**
    * Reduce this interval so that it will not intersect si anymore.
    * When si is contained by this, cuts the interval at offset
    * NOTE: When len is brought back to zero? offset stays the same.
    * @param si
    * @return the length that was removed. 0 meaning no substraction
    * 
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
      dc.root(this, IntInterval.class, "@line330");
      toStringPrivate(dc);
      super.toString(dc.sup());
      dc.nlLvlOWithTitle(payload, "payload", uc);
   }

   public void toString1Line(Dctx dc) {
      dc.root1Line(this, IntInterval.class);
      toStringPrivate(dc);
      super.toString1Line(dc.sup1Line());
   }

   public String toStringOffsetsBracket() {
      return "[" + offset + "," + (offset + len - 1) + "]";
   }

   public String toStringOffsets() {
      return offset + "," + (offset + len);
   }

   private void toStringPrivate(Dctx dc) {
      dc.appendVarWithSpace("offset", offset);
      dc.appendVarWithSpace("len", len);
      dc.append(" -> [");
      dc.append(offset);
      dc.append(",");
      dc.append((offset + len - 1));
      dc.append("]");
   }

   //#enddebug

}
