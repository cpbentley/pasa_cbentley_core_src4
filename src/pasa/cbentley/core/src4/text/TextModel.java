package pasa.cbentley.core.src4.text;

import pasa.cbentley.core.src4.ctx.ObjectU;
import pasa.cbentley.core.src4.ctx.UCtx;
import pasa.cbentley.core.src4.logging.Dctx;
import pasa.cbentley.core.src4.structs.BufferObject;
import pasa.cbentley.core.src4.structs.IntInterval;
import pasa.cbentley.core.src4.structs.IntIntervals;
import pasa.cbentley.core.src4.utils.CharUtils;
import pasa.cbentley.core.src4.utils.StringUtils;

public class TextModel extends ObjectU implements ITechText {

   private char[]           charsPunctOthers = { ';', ',', ':', '=', '(', ')', '[', ']' };

   private char[]           charsSentenceEnd = { '.', '?', '!' };

   private char[]           charsSeparators  = { ' ', ',', ':', ';' };

   private char[]           chars;

   private int              offsetChars;

   private int              lengthChars;

   private StringInterval[] intervalAll;

   private StringInterval[] intervalWords;

   private StringInterval[] intervalSentences;

   private StringInterval[] intervalParagraph;

   private int              numWords;

   private int              numUnknown;

   private int              numSeparators;

   private int              numNewLines;

   private int              numPuncts;

   private int              numSentences;

   private StringInterval[] intervalSeparators;

   private int              numParagraph;

   public TextModel(UCtx uc) {
      super(uc);
   }

   public void setString(String str) {
      this.setChars(str.toCharArray(), 0, str.length());
   }

   /**
    * Words separators chars.. usually we have the space. One might decide to include comma and parenthesis.
    * @param separators
    */
   public void setCharsSeparators(char[] separators) {
      if (separators == null) {
         throw new NullPointerException();
      }
      this.charsSeparators = separators;
   }

   public void setChars(char[] chars, int offset, int len) {
      if (chars == null) {
         throw new NullPointerException();
      }
      if (len == 0 || offset < 0 || offset + len > chars.length) {
         throw new IllegalArgumentException();
      }
      this.chars = chars;
      this.offsetChars = offset;
      this.lengthChars = len;

      //resets all
      numWords = 0;
      numNewLines = 0;
      numPuncts = 0;
      numSeparators = 0;
      numUnknown = 0;
      numSentences = 0;
      numParagraph = 0;
      intervalAll = null;
      intervalWords = null;
      intervalSentences = null;
      intervalParagraph = null;
      intervalSeparators = null;
   }

   public StringInterval[] getIntervalsWords() {
      if (intervalWords == null) {
         buildModel();
      }
      return intervalWords;
   }

   public StringInterval[] getIntervalsSpaces() {
      if (intervalSeparators == null) {
         buildModel();
      }
      return intervalSeparators;
   }

   public String getTextAsString() {
      return new String(chars, offsetChars, lengthChars);
   }

   /**
    * Check if character c changes the model. if so, sets to null the inner model but does not rebuild it.
    * @param offset
    * @param c
    */
   public void replaceChar(int offset, char c) {

   }

   private int getCharType(char c) {
      if (c == StringUtils.NEW_LINE) {
         return TEXT_TYPE_3_NEWLINES;
      } else if (c == StringUtils.NEW_LINE_CARRIAGE_RETURN) {
         return TEXT_TYPE_3_NEWLINES;
      } else if (CharUtils.contains(charsSeparators, c)) {
         return TEXT_TYPE_2_SEPARATOR;
      } else if (CharUtils.contains(charsSentenceEnd, c)) {
         return TEXT_TYPE_5_SENTENCE;
      } else if (CharUtils.contains(charsPunctOthers, c)) {
         return TEXT_TYPE_4_PUNCTUATION;
      } else {
         return TEXT_TYPE_1_WORD;
      }
   }

   private StringInterval getNewInterval(int type, int offset) {
      StringInterval newLineInt = new StringInterval(uc, this, type);
      newLineInt.setOffset(offset);
      newLineInt.setLen(1);
      return newLineInt;
   }

   private void dealWith(StringInterval si, BufferObject bo) {
      bo.add(si);
      //increment numof type
      int lasType = si.getType();
      switch (lasType) {
         case TEXT_TYPE_1_WORD:
            numWords++;
            break;
         case TEXT_TYPE_2_SEPARATOR:
            numSeparators++;
            break;
         case TEXT_TYPE_3_NEWLINES:
            numNewLines++;
            break;
         case TEXT_TYPE_4_PUNCTUATION:
            numPuncts++;
            break;
         case TEXT_TYPE_5_SENTENCE:
            numSentences++;
            break;
         default:
            numUnknown++;
            break;
      }
   }

   public int getNumSeparators() {
      return numSeparators;
   }

   public int getNumPuncs() {
      return numPuncts;
   }

   public int getParagraph() {
      return numParagraph;
   }

   public int getNumWords() {
      return numWords;
   }

   /**
    * Build Model from available text
    * 
    * When adding characters, we may take existing {@link IntIntervals} and modify it
    * 
    * @param intIntervals
    */
   public void buildModel() {
      //init aglo with first char
      char c = chars[offsetChars];
      BufferObject boAll = new BufferObject(uc, 5);
      int type = getCharType(c);
      StringInterval current = getNewInterval(type, offsetChars);
      boolean lastCreated = false;
      StringInterval last = null;
      numParagraph = 1;
      for (int i = offsetChars + 1; i < offsetChars + lengthChars; i++) {
         c = chars[i];
         type = getCharType(c);
         if (type == current.getType()) {
            current.incrementLenBy1();
            lastCreated = false;
         } else {
            //add current in 
            dealWith(current, boAll);
            //increment numof type
            current = getNewInterval(type, i);
            last = current;
            lastCreated = true;
         }
      }
      // if (lastCreated) {
      //end algo by 
      dealWith(current, boAll);
      //  }
      
      //last portion of text is last sentence even without a period.
      if (current.getType() != TEXT_TYPE_5_SENTENCE) {
         numSentences++;
      }

      
      intervalWords = new StringInterval[numWords];
      intervalSeparators = new StringInterval[numSeparators];
      intervalSentences = new StringInterval[numSentences];
      int wordsIndex = 0;
      int whiteIndex = 0;
      int sentenceIndex = 0;
      StringInterval firstWord = null;
      // iterate over and build
      int len = boAll.getLength();
      for (int i = 0; i < len; i++) {
         StringInterval si = (StringInterval) boAll.get(i);
         int itype = si.getType();
         if (itype == TEXT_TYPE_1_WORD) {
            if (firstWord == null) {
               firstWord = si;
            }
            intervalWords[wordsIndex] = si;
            wordsIndex++;
         } else if (itype == TEXT_TYPE_2_SEPARATOR) {
            intervalSeparators[whiteIndex] = si;
            whiteIndex++;
         } else if (itype == TEXT_TYPE_5_SENTENCE) {
            int startSent = si.getOffset();
            int lenSent = si.getLen();
            //first sentence.. create interval from start of first word
            if (firstWord == null) {
               //crap between
               //TODO ?? create from start ?
               si.setOffset(offsetChars);
               int sentenceLength = startSent - offsetChars + lenSent;
               si.setLen(sentenceLength);

            } else {
               int offsetFirstWord = firstWord.getOffset();
               si.setOffset(offsetFirstWord);
               int sentenceLength = startSent - offsetFirstWord + lenSent;
               si.setLen(sentenceLength);
            }
            intervalSentences[sentenceIndex] = si;
            //first check if we have \r
            sentenceIndex++;

            firstWord = null; //reset search
         }
      }

      if (current.getType() != TEXT_TYPE_5_SENTENCE) {
         //force one more sentence over last first word to end
         int offsetFirstWord = firstWord.getOffset();
         StringInterval lastSentence = getNewInterval(TEXT_TYPE_5_SENTENCE, offsetFirstWord);
         int sentenceLength = current.getOffset() - offsetFirstWord + current.getLen();
         lastSentence.setLen(sentenceLength);
         intervalSentences[sentenceIndex] = lastSentence;
      }

   }

   public int getNumNewLines() {
      return numNewLines;
   }

   public StringInterval[] getIntervalsSentences() {
      if (intervalSentences == null) {
         buildModel();
      }
      return intervalSentences;
   }

   public StringInterval[] getWords() {
      if (intervalWords == null) {
         buildModel();
      }
      return intervalWords;
   }

   public String getString(StringInterval stringInterval) {
      return new String(chars, stringInterval.getOffset(), stringInterval.getLen());
   }

   public int getNumSentences() {
      return numSentences;
   }

   //#mdebug
   public void toString(Dctx dc) {
      dc.root(this, TextModel.class, "@line5");
      toStringPrivate(dc);
      super.toString(dc.sup());
      dc.nl();
      dc.append("Numbers of");
      dc.appendVarWithSpace("numWords", numWords);
      dc.appendVarWithSpace("numSentences", numSentences);
      dc.appendVarWithSpace("numSeparators", numSeparators);
      dc.appendVarWithSpace("numNewLines", numNewLines);
      dc.appendVarWithSpace("numPuncts", numPuncts);
   }

   private void toStringPrivate(Dctx dc) {
      dc.appendVarWithSpace("offsetChars", offsetChars);
      dc.appendVarWithSpace("lengthChars", lengthChars);

   }

   public void toString1Line(Dctx dc) {
      dc.root1Line(this, TextModel.class);
      toStringPrivate(dc);
      super.toString1Line(dc.sup1Line());
   }

   //#enddebug

}
