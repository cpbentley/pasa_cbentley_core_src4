/*
 * (c) 2018-2020 Charles-Philip Bentley
 * This code is licensed under MIT license (see LICENSE.txt for details)
 */
package pasa.cbentley.core.src4.i8n;

import pasa.cbentley.core.src4.ctx.ICtx;
import pasa.cbentley.core.src4.ctx.IEventsCore;
import pasa.cbentley.core.src4.ctx.UCtx;
import pasa.cbentley.core.src4.event.IEventProducer;
import pasa.cbentley.core.src4.logging.IStringable;

/**
 * Localize Strings.
 * <br>
 * In order to modify the Language, it will depends on the implementation.
 * <br>
 * The application sees this interface.
 * 
 * {@link IEventsCore#PID_01_FRAMEWORK_2_LANGUAGE_CHANGED}
 * 
 * @author Charles Bentley
 *
 */
public interface IStringProducer extends IStringable {

   /**
    * Creates a new locale, a new ID is assigned to the locale.
    * <br>
    * <br>
    * This can be done dynamically by the user.
    * <br>
    * <br>
    * In the Update Thread.
    * <br>
    * @param name
    * @param suffix
    * @return
    */
   public LocaleID addLocaleID(String name, String suffix);

   /**
    * Creates a mutable {@link I8nString} used to represent
    * a user visible string.
    * <br>
    * @param key
    * @return
    */
   public I8nString getIString(int key);

   /**
    * If no null and default language is set, returns def.
    * @param key
    * @param def
    * @return
    */
   public I8nString getIString(String key, String def);

   /**
    * 
    * @param key
    * @param def
    * @param suffix
    * @return
    */
   public I8nString getIString(String key, String def, String suffix);

   /**
    * Returns the {@link I8nString} for the integer key and current {@link LocaleID}. 
    * 
    * <p>
    * If the key is not found, the {@link I8nString} is loaded with def String
    * </p>
    * @param key
    * @param def
    * @return
    */
   public I8nString getIStringKey(int key, String def);

   /**
    * The {@link LocaleID} associated with the given suffix. null if suffix is not found
    * @param suf
    * @return
    */
   public LocaleID getLocale(String suf);

   /**
    * Returns the current {@link LocaleID} as decided by saved settings or currently by the user
    * @return
    */
   public LocaleID getLocaleID();

   /**
    * The default {@link LocaleID}
    * @return
    */
   public LocaleID getLocaleIDRoot();

   /**
    * Returns available {@link LocaleID}.
    * @return
    */
   public LocaleID[] getLocaleIDs();

   /**
    * As defined in {@link IEventProducer#getProducerID()}
    * The {@link IStringProducer} can generate events and needs its ID to register String events like Changes
    * @return
    */
   public int getProducerID();

   /**
    * 
    * @param key
    * @return
    */
   public String getString(int key);

   /**
    * One can also get just a String for those cases where {@link I8nString} is not supported.
    * <br>
    * @param key
    * @return
    */
   public String getString(String key);

   /**
    * Loads the Strings associated with the {@link ICtx}.
    * <br>
    * Tracks all path IDs. Those roots will be given to new {@link IStringProducer}
    * if one is changed
    * <br>
    * This method leaks some implementation detail, we need a pathID.. But the structure of the files and their 
    * content will depend on the implementation.
    * @param cl
    * @param pathid
    */
   public void loads(ICtx cl, String pathid);

   /**
    * Dynamically sets a translation.
    * <br>
    * <br>
    * Can be used to link a few strings. This avoids the creation of
    * text files.
    * <br>
    * <br>
    * @param lid
    * @param key 1based index
    * @param string
    */
   public void setValue(ICtx cl, LocaleID lid, int key, String string);

   /**
    * Internally sets the {@link LocaleID} used to fetch strings.
    * Calling this method will generate and event
    * {@link IStringProducer#EVENT_0_LANGUAGE} with producer id
    * {@link IStringProducer#getProducerID()}
    * <br>
    * <br>
    * 
    * @param lid
    */
   public void setLocalID(LocaleID lid);

   /**
    * Optional mapper
    * @return
    */
   public IStringMapper getStringMapper();
   
   public UCtx getUC();

}
