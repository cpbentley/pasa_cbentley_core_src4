package pasa.cbentley.core.src4.helpers;

import pasa.cbentley.core.src4.ctx.UCtx;
import pasa.cbentley.core.src4.logging.Dctx;
import pasa.cbentley.core.src4.logging.IDLog;
import pasa.cbentley.core.src4.logging.IStringable;
import pasa.cbentley.core.src4.utils.CharUtils;

/**
 * Computes thing about a number
 * @author Charles Bentley
 *
 */
public class IntegerString implements IStringable {

   /**
    * The maximum number of characters of a positive Integer
    * 
    * @see {@link Integer#MAX_VALUE}
    */
   public static final int MAX_STR_SIZE             = 10;

   private char[]          charValuesFromLeft       = new char[MAX_STR_SIZE];

   private char[]          charValuesFromRight      = new char[MAX_STR_SIZE];

   private int             number;

   private String          numberStr;

   private int             numChars;

   private int             numDifferentDigits;

   private int[]           numericalValuesFromLeft  = new int[MAX_STR_SIZE];

   private int[]           numericalValuesFromRight = new int[MAX_STR_SIZE];

   private int             numIdenticalDigitsFromLeft;

   private int             numIdenticalDigitsFromRight;

   private int[]           occurrences              = new int[10];

   protected final UCtx    uc;

   public IntegerString(UCtx uc) {
      this.uc = uc;
   }

   public void computeOccurences() {
      int counterNumDifferent = 0;
      for (int i = 0; i < numChars; i++) {
         int value = numericalValuesFromLeft[i];
         if (occurrences[value] == 0) {
            counterNumDifferent++;
            int countOccurence = 0;
            for (int j = 0; j < numChars; j++) {
               int valuej = numericalValuesFromLeft[j];
               if (value == valuej) {
                  countOccurence++;
               }
            }
            occurrences[value] = countOccurence;
         }
      }
      numDifferentDigits = counterNumDifferent;
   }

   public char getCharFromLeft1() {
      return charValuesFromLeft[0];
   }

   public char getCharFromLeft2() {
      return charValuesFromLeft[1];
   }

   public char getCharFromLeft3() {
      return charValuesFromLeft[2];
   }

   public char getCharFromLeft4() {
      return charValuesFromLeft[3];
   }

   public char getCharFromLeftIndex(int index0) {
      return charValuesFromLeft[index0];
   }

   /**
    * First in human representation.
    * For 12345 the last is 1
    * @return
    */
   public int getDigitFromLeft1() {
      return numericalValuesFromLeft[0];
   }

   /**
    * First in human representation.
    * For 12345 the last is 1
    * @return
    */
   public int getDigitFromLeft2() {
      return numericalValuesFromLeft[1];
   }

   public int getDigitFromLeft3() {
      return numericalValuesFromLeft[2];
   }

   public int getDigitFromLeft4() {
      return numericalValuesFromLeft[3];
   }

   public int getDigitFromLeftIndex(int index) {
      return numericalValuesFromLeft[index];
   }

   /**
    * Tail/Last/Least significant in human representation.
    * 
    * For 12345 the last is 5
    * @return
    */
   public int getDigitFromRight1() {
      return numericalValuesFromRight[0];
   }

   public int getDigitFromRight2() {
      return numericalValuesFromRight[1];
   }

   public int getDigitFromRight3() {
      return numericalValuesFromRight[2];
   }

   public int getDigitFromRight4() {
      return numericalValuesFromRight[3];
   }

   /**
    * In 978055 return 7
    * 
    * return -1 if number is smaller
    * @return
    */
   public int getDigitFromRight5() {
      return numericalValuesFromRight[4];
   }

   public int getDigitFromRight6() {
      return numericalValuesFromRight[5];
   }

   public int getDigitFromRight7() {
      return numericalValuesFromRight[6];
   }

   public int getDigitFromRightIndex(int index) {
      return numericalValuesFromRight[index];
   }

   public int getNumber() {
      return number;
   }

   public int getNumDifferentDigits() {
      if (numDifferentDigits == 0) {
         computeOccurences();
      }
      return numDifferentDigits;
   }

   public int getNumIdenticalDigitsFromLeft() {
      if (numIdenticalDigitsFromLeft == -1) {
         int count = 1;
         int first = numericalValuesFromLeft[0];
         for (int i = 1; i < numChars; i++) {
            if (first == numericalValuesFromLeft[i]) {
               count++;
            } else {
               break;
            }
         }
         numIdenticalDigitsFromLeft = count;
      }
      return numIdenticalDigitsFromLeft;
   }

   public int getNumIdenticalDigitsFromRight() {
      if (numIdenticalDigitsFromRight == -1) {
         int count = 1;
         int first = numericalValuesFromRight[0];
         for (int i = 1; i < numChars; i++) {
            if (first == numericalValuesFromRight[i]) {
               count++;
            } else {
               break;
            }
         }
         numIdenticalDigitsFromRight = count;
      }
      return numIdenticalDigitsFromRight;
   }

   public int getNumOccurenceOfFromLeft(int index) {
      if (numDifferentDigits == 0) {
         computeOccurences();
      }
      int val = getDigitFromLeftIndex(index);
      return getOccurenceOfNumber(val);
   }

   public int getOccurenceOfNumber(int int0To9) {
      if (numDifferentDigits == 0) {
         computeOccurences();
      }
      return occurrences[int0To9];
   }

   public int getSize() {
      return numChars;
   }

   public String getString() {
      return numberStr;
   }

   /**
    * True when 1001, 6006, 44044, 440044
    * 
    * True if 181 
    * @return
    */
   public boolean isPalindrome() {
      int num = numChars / 2;
      for (int i = 0; i < num; i++) {
         int digitLeft = numericalValuesFromLeft[i];
         int digitRight = numericalValuesFromRight[i];
         if (digitLeft != digitRight) {
            return false;
         }
      }
      return true;
   }

   public void setNumber(int number) {
      this.number = number;
      numberStr = String.valueOf(number);
      numChars = numberStr.length();
      CharUtils cu = uc.getCU();
      for (int i = 0; i < numChars; i++) {
         char ci = numberStr.charAt(i);
         int intValue = cu.getNumericalValue(ci);
         charValuesFromLeft[i] = ci;
         numericalValuesFromLeft[i] = intValue;
         int rightIndex = numChars - 1 - i;
         charValuesFromRight[rightIndex] = ci;
         numericalValuesFromRight[rightIndex] = intValue;
      }

      for (int i = numChars; i < MAX_STR_SIZE; i++) {
         numericalValuesFromLeft[i] = -1;
         charValuesFromLeft[i] = '-';
         charValuesFromRight[i] = '-';
         numericalValuesFromRight[i] = -1;

      }

      numIdenticalDigitsFromRight = -1;
      numIdenticalDigitsFromLeft = -1;
      numDifferentDigits = 0;
      for (int i = 0; i < occurrences.length; i++) {
         occurrences[i] = 0;
      }
   }
   
   //#mdebug
   public IDLog toDLog() {
      return toStringGetUCtx().toDLog();
   }

   public String toString() {
      return Dctx.toString(this);
   }

   public void toString(Dctx dc) {
      dc.root(this, "IntegerString");
      toStringPrivate(dc);
   }

   public String toString1Line() {
      return Dctx.toString1Line(this);
   }

   private void toStringPrivate(Dctx dc) {
         dc.appendVarWithSpace("number", number);
         dc.appendVarWithSpace("numberStr", numberStr);
         dc.appendVarWithSpace("numChars", numChars);
         dc.appendVarWithSpace("numDifferentDigits", numDifferentDigits);
   }

   public void toString1Line(Dctx dc) {
      dc.root1Line(this, "IntegerString");
      toStringPrivate(dc);
   }

   public UCtx toStringGetUCtx() {
      return uc;
   }

   //#enddebug
   

}
