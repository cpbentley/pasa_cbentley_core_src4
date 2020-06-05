package pasa.cbentley.core.src4.interfaces;

import pasa.cbentley.core.src4.io.BADataOS;
import pasa.cbentley.core.src4.logging.IStringable;

/**
 * Key-Value Mapping preferences interface
 * @author Charles Bentley
 *
 */
public interface IPrefsWriter extends IStringable {

   /**
    * 
    * @param key
    * @param strs
    * @param seperator char used to separate strs
    * @return
    */
   public void put(String key, String[] strs, char separator);

   /**
    * Writes its content to the byte array
    * @param os
    */
   public void export(BADataOS os);

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
    * @param value
    */
   public void putDouble(String key, double value);

}
