/*
 * (c) 2018-2020 Charles-Philip Bentley
 * This code is licensed under MIT license (see LICENSE.txt for details)
 */
package pasa.cbentley.core.src4.stator;

import pasa.cbentley.core.src4.ctx.ICtx;
import pasa.cbentley.core.src4.logging.IStringable;

/**
 * An {@link IStatorable} is a class that wants to write its state and read previous saved state.
 * and whose class can be instantiated by {@link IStatorFactory}. Singletons are not {@link IStatorable}
 * but {@link IStatorOwner}s.
 * 
 * <li> {@link IStatorable#stateReadFrom(StatorReader)}
 * <li> {@link IStatorable#stateWriteTo(StatorWriter)}
 * 
 * <br>
 * <br>
 * 
 * <p>
 * Android Note:
 * We cannot use the android way with parcellable in the constructor. The parcellable model does not
 * allow a code context as constructor parameter when creating an object from the byte array.
 * </p>
 * 
 * <p>
 * 
 * When re creating a tree of objects, the {@link IStatorable}{@link #stateReadFrom(StatorReader)} is responsible
 * to create it based on the flags and data inside the stator
 * 
 * </p>
 * <p>
 * The order of reading values is critical inside a method. It must follow the same routine as the writing method.
 * 
 * <br>
 * 
 * Every {@link IStatorable} may gets assigned an ID along with its byte position.
 * This allow for reading and writing in any order.
 * </p>
 * 
 * 
 * @author Charles Bentley
 *
 */
public interface IStatorable extends IStringable {

   /**
    * This method should only be called by a {@link StatorWriter}.
    * 
    * Implementation called on {@link StatorWriter} all the elements that needs to be written to {@link StatorWriter}.
    * 
    * Inverse method of {@link IStatorable#stateReadFrom(StatorReader)}
    * 
    * @param state {@link StatorWriter} cannot be null
    */
   public void stateWriteTo(StatorWriter state);

   /**
    * 
    * This method should only be called by a {@link StatorReader}.
    * 
    * Use Stator
    * 
    * Read the data from {@link StatorReader} and extract the state.
    * 
    * <p>
    * Use {@link IStatorFactory} for creating objects instances
    * </p>
    * 
    * @param state {@link StatorReader}
    */
   public void stateReadFrom(StatorReader state);

   /**
    * Class ID uniquely identifying this class in its factory. 
    * @return
    */
   public int getStatorableClassID();

   /**
    * 
    * @return
    */
   public ICtx getCtxOwner();

}
