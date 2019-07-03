package pasa.cbentley.core.src4.memory;

import pasa.cbentley.core.src4.logging.IStringable;

/**
 * 
 * @author Charles Bentley
 *
 */
public interface IMemFreeable extends IStringable {

   /**
    * Sets to null big data
    *
    */
   public void freeMemory();

}
