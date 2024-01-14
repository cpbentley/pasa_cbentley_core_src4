/*
 * (c) 2018-2020 Charles-Philip Bentley
 * This code is licensed under MIT license (see LICENSE.txt for details)
 */
package pasa.cbentley.core.src4.logging;

import pasa.cbentley.core.src4.ctx.ICtx;

/**
 * Associates a debug String to a DID and a value.
 * 
 * <br>
 * @author Charles Bentley
 *
 */
public interface IDebugStringable {

   //#mdebug
   /**
    * Produce a String for a debugID (did) and a value.
    * <br>
    * It allows a generic processor to debug values with a title
    * <p>
    * Lets say you know values from 0 to 6 are mapped to
    * <li>"ShiftLines"
    * <li>"Pixeler"
    * <li>"MoveFunction"
    * <li>"Appearance"
    * <li>"PixelFalling"
    * <li>"AlphaRgb"
    * </p>
    * 
    * You want to register this array in the debug engine
    * So that when debugging the value, you call this method with did and the value.
    * 
    * the DID (DebugID) identifies the debugging array of strings to be used to fetch the String.
    * did=DebugStringKey
    * 
    * <p>
    * Why not using a String key? And use a hash table? Because nearly all values are coming
    * from {@link ByteObject} int fields. Much more efficient to use integers.
    * </p>
    * 
    * <p>
    * Also a DID can be attrituted to an offset of a ByteObject
    * Cell Policies for example.
    * </p>
    * <p>
    * Its quite useful to debug any int based key system like the ByteObject
    * </p>
    * <p>
    * <b>How are DID generated ?</b>
    * The first X values are reserved for the dynamics which are set by the program.
    * Other values are static defined at the level of {@link ICtx} similarly to other static integers
    * 
    * </p>
    * @param did the class
    * @param value the case value 
    * @return
    */
   public String toStringGetDIDString(int did, int value);


   //#enddebug

}
