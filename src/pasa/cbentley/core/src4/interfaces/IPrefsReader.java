package pasa.cbentley.core.src4.interfaces;

import pasa.cbentley.core.src4.io.BADataIS;
import pasa.cbentley.core.src4.logging.IStringable;
import pasa.cbentley.core.src4.structs.IntToStrings;

/**
 * Key-Value Mapping preferences interface
 * @author Charles Bentley
 *
 */
public interface IPrefsReader extends IStringable {

   /**
    * 
    * @param key
    * @param def
    * @return
    * @throws NullPointerException - if key is null. (A null value for def is permitted.)
    */
   public String get(String key, String def);

   /**
    * the boolean value represented by the string associated with key, 
    * or def if the associated value does not exist or cannot be interpreted as a boolean.
    * @param key
    * @param def
    * @return
    */
   public boolean getBoolean(String key, boolean def);

   /**
    * 
    * @param key
    * @param def
    * @return
    */
   public double getDouble(String key, double def);

   /**
    * 
    * @param key
    * @param def
    * @return
    */
   public int getInt(String key, int def);

   /**
    * Gets all keys. 
    * 
    * TODO Int is a value for Type ?
    * 
    * 
    * @return
    */
   public IntToStrings getKeys();

   /**
    * Empty array if no data, if no separator, it will return a array with one String
    * @param key
    * @param separator
    * @return
    */
   public String[] getStrings(String key, char separator);

   /**
    * This method leaks implementation details ?
    * 
    * Reads what has been exported with {@link IPrefsWriter#export(pasa.cbentley.core.src4.io.BADataOS)}
    * 
    * @param dis
    * @throws IllegalArgumentException if cannot read
    */
   public void importPrefs(BADataIS dis);

}
