package pasa.cbentley.core.src4.i8n;

import pasa.cbentley.core.src4.ctx.UCtx;
import pasa.cbentley.core.src4.event.IEventBus;
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
 * 
 * A {@link LString} by default does not registers on the {@link IEventBus}.
 * 
 * When a language changes, the application must go through the tree hierarchy of objects
 * and ask them to update their {@link LString}s.
 * 
 * @author Charles Bentley
 *
 */
public class LString implements IString {

   protected String                def;

   protected String                id;

   /**
    * When not 0, takes precedence
    */
   protected int                   keyInt;

   /**
    * LocalID of this.
    * When returning def, returns localID of the application.
    * <br>
    * i.e. a translation could not be found
    */
   protected LocaleID              lid;

   protected String                myString;

   protected String                prefix;

   private boolean                 reset = false;

   protected String                suffix;

   protected final IStringProducer stringProducer;

   /**
    * Creates an {@link LString} with an integer key. 
    * No default String is given
    * @param dd
    * @param key
    */
   public LString(IStringProducer dd, int key) {
      this(dd,key,null);
   }

   /**
    * Creates an {@link LString} with a integer key. 
    * 
    * @param dd
    * @param key
    * @param def
    */
   public LString(IStringProducer dd, int key, String def) {
      this.stringProducer = dd;
      this.keyInt = key;
      this.def = def;
   }

   /**
    * 
    * @param uc
    * @param id
    * @param def
    */
   public LString(IStringProducer uc, String id, String def) {
      this.stringProducer = uc;
      this.id = id;
      this.def = def;
      myString = def;
   }

   /**
    * Returns the {@link String} represented by this {@link LString}
    */
   public String getStr() {
      //get current string producer 
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
      return stringProducer.getUCtx();
   }
   //#enddebug

}
