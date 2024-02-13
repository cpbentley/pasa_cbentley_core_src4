/*
 * (c) 2018-2020 Charles-Philip Bentley
 * This code is licensed under MIT license (see LICENSE.txt for details)
 */
package pasa.cbentley.core.src4.i8n;

import pasa.cbentley.core.src4.ctx.ObjectU;
import pasa.cbentley.core.src4.ctx.UCtx;
import pasa.cbentley.core.src4.event.IEventBus;
import pasa.cbentley.core.src4.logging.Dctx;

/**
 * Default implementation of {@link I8nString}
 * 
 * It has
 * <li> A {@link LocaleID} telling which language the string belongs to
 * <li> {@link IStringProducer} to actually fetch the string when asked with {@link LString#getStr()}
 * 
 * Return true when events generated a force purge of String cache.
 * <br>
 * <br>
 * 
 * <b>Dynamic String Translation</b>
 * <br>
 * If a String Key cannot be mapped to the active {@link LocaleID}, it will default to english instead. 
 * 
 * IString cache that String cannot be mapped.
 * <br>
 * <p>
 * <b>Potential Uses</b>
 * </p>
 * <li>User selects {@link I8nString} through interface. Resets cache.
 * <li>User sets a translation.
 * <br>
 * 
 * <p>
 * <b>About Events</b>:<br>
 * 
 * A {@link LString} does not registers on the {@link IEventBus}.
 * 
 * When a language changes, the application must go through the tree hierarchy of objects
 * and ask them to update their {@link LString}s.
 * 
 * it would be too costly on the eventBus for a rare action anyways
 * </p>
 * 
 * What if a {@link LString} is purposely in another language. We do not want updates on language changes
 * 
 * @author Charles Bentley
 *
 */
public class LString extends ObjectU implements I8nString {

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

   /**
    * When not null, the acutal String that was last built by a call to {@link LString#getStr()}
    */
   protected String                myString;

   protected String                prefix;

   /**
    * By Default false;
    */
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
      this(dd, key, null);
   }

   /**
    * Creates an {@link LString} with a integer key. 
    * 
    * @param dd
    * @param key
    * @param def
    */
   public LString(IStringProducer dd, int key, String def) {
      super(dd.getUC());
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
   public LString(IStringProducer dd, String id, String def) {
      super(dd.getUC());
      this.stringProducer = dd;
      this.id = id;
      this.def = def;
      myString = def;
   }

   public String getPrefix() {
      return prefix;
   }

   /**
    * Returns the {@link String} represented by this {@link LString}
    */
   public String getStr() {
      //get current string producer 
      LocaleID currentLocalID = stringProducer.getLocaleID();
      //check if localID has changed
      if (currentLocalID != lid || reset) {
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
               lid = currentLocalID;
            } else {
               myString = def;
            }
         } else {
            myString = def;
         }
      }

      return myString;
   }

   /**
    * 
    */
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
   public void toString(Dctx dc) {
      dc.root(this, LString.class, 180);
      toStringPrivate(dc);
      super.toString(dc.sup());
   }

   private void toStringPrivate(Dctx dc) {
      
   }

   public void toString1Line(Dctx dc) {
      dc.root1Line(this, LString.class);
      toStringPrivate(dc);
      super.toString1Line(dc.sup1Line());
   }

   //#enddebug
   


}
