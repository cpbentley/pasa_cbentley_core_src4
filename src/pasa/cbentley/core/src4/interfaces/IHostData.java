package pasa.cbentley.core.src4.interfaces;

import pasa.cbentley.core.src4.logging.IStringable;

/**
 * 
 * @author Charles Bentley
 *
 */
public interface IHostData extends IStringable {


   /**
    * 
    * @param dataID
    * @return
    */
   public float getHostDataFloat(int dataID);

   /**
    * Returns a configuration value from host framework context.
    * 
    * 
    * @param dataID
    * @return
    */
   public int getHostDataInt(int dataID);

   /**
    * @param dataID
    * @return
    */
   public Object getHostDataObject(int dataID);

   /**
    * Returns the String data keyed to <code>dataID</code>.
    * 
    * @param dataID
    * @return
    */
   public String getHostDataString(int dataID);


}
