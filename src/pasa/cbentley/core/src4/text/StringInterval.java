package pasa.cbentley.core.src4.text;

import pasa.cbentley.core.src4.ctx.ObjectU;
import pasa.cbentley.core.src4.ctx.UCtx;
import pasa.cbentley.core.src4.logging.Dctx;
import pasa.cbentley.core.src4.structs.IntInterval;

/**
 * A {@link StringInterval} is an {@link IntInterval} over a source string defined by a {@link TextModel}
 * 
 * <p>
 * Potentially a type {@link StringInterval#getType()} for words, blob etc.
 * </p>
 * 
 * @author Charles Bentley
 *
 */
public class StringInterval extends ObjectU {

   private IntInterval     interval;

   /**
    * 
    * {@link StringInterval#getParent()}
    */
   private StringInterval  parent;

   private final TextModel textModel;

   private int             type;

   public StringInterval(UCtx uc, TextModel tm) {
      super(uc);
      this.textModel = tm;
      interval = new IntInterval(uc);
   }

   public StringInterval(UCtx uc, TextModel tm, int type) {
      super(uc);
      this.textModel = tm;
      interval = new IntInterval(uc);
      this.type = type;
   }

   /**
    * The length in characters of the {@link StringInterval} in the main char array of {@link Stringer}
    * @return
    */
   public int getLen() {
      return interval.getLen();
   }

   public IntInterval getInterval() {
      return interval;
   }

   /**
    * The offset in {@link Stringer} at which this {@link StringInterval} starts.
    * @return
    */
   public int getOffset() {
      return interval.getOffset();
   }

   public int getOffsetEnd() {
      return interval.getOffsetEnd();
   }

   /**
    * The {@link StringInterval} in which
    * <li> this.offset < parent.offset
    * <li> this.len < parent.len
    * 
    * @return null if none
    */
   public StringInterval getParent() {
      return parent;
   }

   /**
    * The {@link StringFx} for this interval of text
    * @return
    */
   public TextModel getTextModel() {
      return textModel;
   }

   /**
    * The type of string interval.. word, sentence, paragraph, punctuation, blobmix, separator
    * @return
    */
   public int getType() {
      return type;
   }

   public void incrementLenBy1() {
      interval.incrLen(1);
   }

   /**
    * True when index is inside the {@link StringInterval}
    * @param index
    * @return
    */
   public boolean isInside(int index) {
      return interval.isInside(index);
   }

   public void setInterval(IntInterval interval) {
      if (interval == null) {
         throw new NullPointerException();
      }
      this.interval = interval;
   }

   public void setLen(int len) {
      this.interval.setLen(len);
   }

   public void setOffset(int offset) {
      this.interval.setOffset(offset);
   }

   public void setParent(StringInterval parent) {
      this.parent = parent;
   }

   //#mdebug
   public void toString(Dctx dc) {
      dc.root(this, StringInterval.class, "@line5");
      toStringPrivate(dc);
      super.toString(dc.sup());
      dc.nlLvl(interval);
      dc.nlLvl(parent, "ParentInterval");
      dc.nl();
      dc.appendVar("textModel.getString", textModel.getString(this));

   }

   public void toString1Line(Dctx dc) {
      dc.root1Line(this, StringInterval.class);
      toStringPrivate(dc);
      super.toString1Line(dc.sup1Line());
   }

   private void toStringPrivate(Dctx dc) {
      dc.appendVarWithSpace("type", type);
      ;
   }

   //#enddebug

}
