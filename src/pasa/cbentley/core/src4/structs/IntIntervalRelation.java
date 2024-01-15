package pasa.cbentley.core.src4.structs;

import pasa.cbentley.core.src4.ctx.ObjectU;
import pasa.cbentley.core.src4.ctx.UCtx;
import pasa.cbentley.core.src4.logging.Dctx;

public class IntIntervalRelation extends ObjectU {

   /**
    * left complement of intersection. so null when no intersection
    */
   private IntInterval intersectComplementLeft;

   private IntInterval intersectComplementRight;

   private IntInterval intervalIntersect;

   private IntInterval intervalOne;

   private IntInterval intervalTwo;

   /**
    * 
    */
   private IntInterval intervalUnion;

   private boolean     isOneContainedInTwo;

   /**
    * is first interval seen first starting from the left (zero index)
    */
   private boolean     isOneFirst;

   private boolean     isSameLength;

   private boolean     isAdjacent;

   private boolean     isSameOffsetEnd;

   private boolean     isSameOffsetStart;

   private boolean     isTwoContainedInOne;

   private boolean     isTwoFirst;

   private boolean     isOneLast;

   private boolean     isTwoLast;

   /**
    * Length between extremities. negative when there is an intersect
    */
   private int         distance;

   public IntIntervalRelation(UCtx uc) {
      super(uc);
   }

   public void compute() {
      if (intervalOne == null) {
         throw new NullPointerException("");
      }
      if (intervalTwo == null) {
         throw new NullPointerException("");
      }
      intervalUnion = null;
      distance = 0;
      isOneContainedInTwo = false;
      isTwoContainedInOne = false;
      isOneFirst = false;
      isTwoFirst = false;
      isTwoLast = false;
      isOneLast = false;
      isSameOffsetStart = false;
      isSameOffsetEnd = false;
      isSameLength = false;
      isAdjacent = false;
      intersectComplementLeft = null;
      intersectComplementRight = null;
      intervalIntersect = null;
      int offset1 = intervalOne.getOffset();
      int offset2 = intervalTwo.getOffset();
      int offsetDifference = offset1 - offset2;
      int len1 = intervalOne.getLen();
      int len2 = intervalTwo.getLen();
      int lenDifference = len1 - len2;
      int offsetEnd1 = intervalOne.getOffsetEnd();
      int offsetEnd2 = intervalTwo.getOffsetEnd();
      int offsetEndDifference = offsetEnd1 - offsetEnd2;
      if (lenDifference == 0) {
         isSameLength = true;
      }
      if (offsetEndDifference == 0) {
         isSameOffsetEnd = true;
      } else if (offsetEndDifference > 0) {
         isOneLast = true;
      } else {
         isTwoLast = true;
      }

      if (offsetDifference == 0) {
         isSameOffsetStart = true;
      } else if (offsetDifference > 0) {
         isTwoFirst = true;
      } else {
         isOneFirst = true;
      }

      int intersectOffset = 0;
      int intersectLen = 0;

      if (offsetDifference > 0) {
         //two is strictly lower than one
         intersectOffset = offset1;
         intersectLen = offset2 + len2 - offset1;
         if (intersectLen > len1) {
            intersectLen = len1;
         }
         distance = offset1 - offsetEnd2;
         if (distance == 0) {
            isAdjacent = true;
         }

      } else {
         //two is higher or equal than one
         intersectOffset = offset2;
         //usual case when one is not full contained in the other
         intersectLen = offsetEnd1 - offset2;
         if (intersectLen > len2) {
            //assumed length of intersect is too big.. cap it at smaller sized interval
            intersectLen = len2;
         }
         distance = offset2 - offsetEnd1;
         if (distance == 0) {
            isAdjacent = true;
         }
      }
      isTwoContainedInOne = intervalTwo.isContainedBy(intervalOne);
      isOneContainedInTwo = intervalOne.isContainedBy(intervalTwo);
      if (intersectLen > 0) {
         intervalIntersect = new IntInterval(uc, intersectOffset, intersectLen);

         ///LEFT complement
         //when intersection compute complements
         if (!isSameOffsetStart) {
            if (isOneFirst) {
               int complementLeftLen = offset2 - offset1;
               intersectComplementLeft = new IntInterval(uc, offset1, complementLeftLen);
            } else {
               int complementLeftLen = offsetDifference;
               intersectComplementLeft = new IntInterval(uc, offset2, complementLeftLen);
            }
         }
         //RIGHT
         if (!isSameOffsetEnd) {
            //sometimes none is first
            if (isOneLast) {
               int complementRightLen = offsetEndDifference;
               intersectComplementRight = new IntInterval(uc, offsetEnd2, complementRightLen);
            } else {
               int complementRightLen = offsetEnd2 - offsetEnd1;
               intersectComplementRight = new IntInterval(uc, offsetEnd1, complementRightLen);
            }
         }
      }
   }

   /**
    * When there is an intersection, this value is <= 0 and measures the length of intersection
    * @return
    */
   public int getDistance() {
      return distance;
   }

   /**
    * A new interval
    * @return
    */
   public IntInterval getUnion() {
      if (intervalUnion == null) {
         intervalUnion = new IntInterval(uc);
         if (isOneContainedInTwo) {
            intervalUnion.copyFrom(intervalTwo);
         } else if (isTwoContainedInOne) {
            intervalUnion.copyFrom(intervalOne);
         } else if (isOneFirst) {
            intervalUnion.setOffsets(intervalOne.getOffset(), intervalTwo.getOffsetEnd());
         } else {
            //two first
            intervalUnion.setOffsets(intervalTwo.getOffset(), intervalOne.getOffsetEnd());
         }
      }
      return intervalUnion;
   }

   /**
    * The complement on the left of the intersect.
    * null if no interesect
    * @return
    */
   public IntInterval getIntersectComplementLeft() {
      return intersectComplementLeft;
   }

   /**
    * The area of One that is not inside Two
    * @return null when one interval is fully contained.
    */
   public IntInterval getIntervalComplementIntersectOne() {
      return intersectComplementLeft;
   }

   /**
    * The area of Two that is not inside One
    * @return
    */
   public IntInterval getIntervalComplementIntersectTwo() {
      return intersectComplementLeft;
   }

   public IntInterval getIntersectComplementRight() {
      return intersectComplementRight;
   }

   public IntInterval getIntervalIntersect() {
      return intervalIntersect;
   }

   public IntInterval getIntervalOne() {
      return intervalOne;
   }

   public IntInterval getIntervalTwo() {
      return intervalTwo;
   }

   /**
    * When both are contained in each other, they are equal
    * @return
    */
   public boolean isOneContainedInTwo() {
      return isOneContainedInTwo;
   }

   public boolean isOneFirst() {
      return isOneFirst;
   }

   public boolean isSameLength() {
      return isSameLength;
   }

   public boolean isSameOffsetEnd() {
      return isSameOffsetEnd;
   }

   public boolean isSameOffsetStart() {
      return isSameOffsetStart;
   }

   public boolean isTwoContainedInOne() {
      return isTwoContainedInOne;
   }

   public boolean isTwoFirst() {
      return isTwoFirst;
   }

   public void setIntervalOne(IntInterval intervalOne) {
      this.intervalOne = intervalOne;
   }

   public void setIntervalTwo(IntInterval intervalTwo) {
      this.intervalTwo = intervalTwo;
   }

   /**
    *
    * @return
    */
   public boolean isAdjacent() {
      return isAdjacent;
   }

   //#mdebug
   public void toString(Dctx dc) {
      dc.root(this, IntIntervalRelation.class, "@line5");
      toStringPrivate(dc);
      super.toString(dc.sup());
      dc.nlLvl(intervalOne, "One");
      dc.nlLvl(intervalTwo, "Two");
      dc.nlLvl(intervalIntersect, "Intersect");
      dc.nlLvl(intersectComplementLeft, "intersectComplementLeft");
      dc.nlLvl(intersectComplementRight, "intersectComplementRight");
   }

   private void toStringPrivate(Dctx dc) {
      dc.appendVarWithSpace("isAdjacent", isAdjacent);
      dc.appendVarWithSpace("isOneFirst", isOneFirst);
      dc.appendVarWithSpace("isTwoFirst", isTwoFirst);
      dc.appendVarWithSpace("isOneContainedInTwo", isOneContainedInTwo);
      dc.appendVarWithSpace("isTwoContainedInOne", isTwoContainedInOne);
      dc.appendVarWithSpace("isSameLength", isSameLength);
      dc.appendVarWithSpace("isSameOffsetEnd", isSameOffsetEnd);
      dc.appendVarWithSpace("isSameOffsetStart", isSameOffsetStart);
   }

   public void toString1Line(Dctx dc) {
      dc.root1Line(this, IntIntervalRelation.class);
      toStringPrivate(dc);
      super.toString1Line(dc.sup1Line());
   }

   public boolean isTwoLast() {
      return isTwoLast;
   }

   public boolean isOneLast() {
      return isOneLast;
   }

   //#enddebug

}
