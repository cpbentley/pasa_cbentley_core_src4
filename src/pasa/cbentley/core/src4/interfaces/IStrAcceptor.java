/*
 * (c) 2018-2020 Charles-Philip Bentley
 * This code is licensed under MIT license (see LICENSE.txt for details)
 */
package pasa.cbentley.core.src4.interfaces;

import pasa.cbentley.core.src4.logging.IStringable;

/**
 * 
 * @author Charles Bentley
 *
 */
public interface IStrAcceptor extends IStringable {
   /**
    * true if String is accepted
    * @param str
    * @return
    */
   public boolean isStringAccepted(String str);
}
