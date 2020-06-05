/*
 * (c) 2018-2020 Charles-Philip Bentley
 * This code is licensed under MIT license (see LICENSE.txt for details)
 */
package pasa.cbentley.core.src4.interfaces;

public interface ITechStator extends ITech {

   
   /**
    * When last write/read on a Stator failed
    */
   public static final int FLAG_1_FAILURE = 1 << 0;
}
