package pasa.cbentley.core.src4.i8n;

import pasa.cbentley.core.src4.ctx.UCtx;
import pasa.cbentley.core.src4.logging.Dctx;

/**
 * Return true when events generated a force purge of String cache.
 * <br>
 * <br>
 * 
 * <b>Dynamic String Translation</b>
 * <br>
 * A String cannot be mapped to French. Defaults to english. IString 
 * cache that String cannot be mapped.
 * <br>
 * User selects {@link IString} through interface. Resets cache.
 * User sets a translation.
 * <br>
 * @author Charles Bentley
 *
 */
public class LString implements IString {

   protected String     def;

   protected String     id;

   /**
    * When not 0, takes precedence
    */
   protected int        keyInt;

   /**
    * LocalID of this.
    * When returning def, returns localID of the application.
    * <br>
    * i.e. a translation could not be found
    */
   protected LocaleID   lid;

   protected String     myString;

   protected String     prefix;

   private boolean      reset = false;

   protected String     suffix;

   protected final UCtx uc;

   public LString(UCtx dd, int key, String def) {
      this.keyInt = key;
      this.def = def;
      this.uc = dd;
   }

   public LString(UCtx uc, String id, String def) {
      this.id = id;
      this.def = def;
      this.uc = uc;
      myString = def;
   }

   public String getStr() {
      //get current string producer 
      IStringProducer stringProducer = null;
      LocaleID cid = stringProducer.getLocaleID();
      //check if localID has changed
      if (cid != lid || reset) {
         reset = false;
         IStringMapper sm = stringProducer.getStringMapper();
         if (sm != null) {
            String str = null;
            if (keyInt != 0) {
               str = sm.map(keyInt);
            }
            if (str == null) {
               str = sm.map(id);
            }
            if (str != null) {
               myString = str;
               lid = cid;
            } else {
               myString = def;
            }
         } else {
            myString = def;
         }
      }

      return myString;
   }

   public void reset() {
      reset = true;
   }

   public void setPrefix(String str) {
      prefix = str;
   }

   public void setSuffix(String str) {
      suffix = str;
   }

   //#mdebug
   public String toString() {
      return Dctx.toString(this);
   }

   public void toString(Dctx dc) {
      dc.root(this, "LString");
   }

   public String toString1Line() {
      return Dctx.toString1Line(this);
   }

   public void toString1Line(Dctx dc) {
      dc.root1Line(this, "LString");
   }

   public UCtx toStringGetUCtx() {
      return uc;
   }
   //#enddebug

}
