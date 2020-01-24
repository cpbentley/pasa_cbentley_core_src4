package pasa.cbentley.core.src4.i8n;

import pasa.cbentley.core.src4.ctx.ICtx;
import pasa.cbentley.core.src4.logging.IStringable;

/**
 * Localize Strings.
 * <br>
 * In order to modify the Language, it will depends on the implementation.
 * <br>
 * The application sees this interface.
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
    * Creates a mutable {@link IString} used to represent
    * a user visible string.
    * <br>
    * @param key
    * @return
    */
   public IString getIString(int key);

   /**
    * If no null and default language is set, returns def.
    * @param key
    * @param def
    * @return
    */
   public IString getIString(String key, String def);

   /**
    * 
    * @param key
    * @param def
    * @param suffix
    * @return
    */
   public IString getIString(String key, String def, String suffix);

   /**
    * 
    * @param key
    * @param def
    * @return
    */
   public IString getIStringKey(int key, String def);

   /**
    * 
    * @param suf
    * @return
    */
   public LocaleID getLocale(String suf);

   /**
    * Returns the current LocalID
    * @return
    */
   public LocaleID getLocaleID();

   /**
    * 
    * @return
    */
   public LocaleID getLocaleIDRoot();

   /**
    * Returns available {@link LocaleID}.
    * @return
    */
   public LocaleID[] getLocaleIDs();

   /**
    * ID to register String events like Changes
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
    * One can also get just a String for those cases where {@link IString} is not supported.
    * <br>
    * @param key
    * @return
    */
   public String getString(String key);

   /**
    * Loads the Strings associated with the {@link IModule}.
    * <br>
    * Tracks all path IDs. Those roots will be given to new {@link IStringProducer}
    * if one is changed
    * <br>
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
    * @param key
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

}
