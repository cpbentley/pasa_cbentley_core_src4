package pasa.cbentley.core.src4.interfaces;

import java.io.OutputStream;

import pasa.cbentley.core.src4.logging.IStringable;
import pasa.cbentley.core.src4.structs.IntToStrings;

/**
 * Key-Value Mapping preferences interface
 * @author Charles Bentley
 *
 */
public interface IPrefs extends IStringable {

   /**
    * 
    * @param key
    * @param strs
    * @param seperator char used to separate strs
    * @return
    */
   public void put(String key, String[] strs, char separator);

   /**
    * Empty array if no data, if no separator, it will return a array with one String
    * @param key
    * @param separator
    * @return
    */
   public String[] getStrings(String key, char separator);

   public void putBoolean(String key, boolean value);

   public void putInt(String key, int value);

   public void put(String key, String value);

   public String get(String key, String def);

   public boolean getBoolean(String key, boolean def);

   public int getInt(String key, int def);

   public void clear();

   public void export(OutputStream os);

   public double getDouble(String key, double d);
   
   public void putDouble(String key, double value);
   
   /**
    * Gets all keys. Int is a value for Type
    * @return
    */
   public IntToStrings getKeys();

}
