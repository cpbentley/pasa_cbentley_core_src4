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

   /**
    * 
    * @param key
    * @param value
    */
   public void putBoolean(String key, boolean value);

   /**
    * 
    * @param key
    * @param value
    */
   public void putInt(String key, int value);

   /**
    * 
    * @param key
    * @param value
    */
   public void put(String key, String value);

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
   public int getInt(String key, int def);

   /**
    * 
    */
   public void clear();

   /**
    * 
    * @param os
    */
   public void export(OutputStream os);

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
    * @param value
    */
   public void putDouble(String key, double value);

   /**
    * Gets all keys. 
    * 
    * TODO Int is a value for Type ?
    * 
    * 
    * @return
    */
   public IntToStrings getKeys();

}
