/*
 * (c) 2018-2020 Charles-Philip Bentley
 * This code is licensed under MIT license (see LICENSE.txt for details)
 */
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
