package pasa.cbentley.core.src4.stator;

import pasa.cbentley.core.src4.logging.IStringable;

/**
 * we cannot use the android way with parcellable in the constructor, because we have to give
 * the a code context when creating any object
 * <br>
 * <br>
 * It could be delegated to a Factory
 * 
 * When re creating a tree of objects, the {@link IStatorable}{@link #stateReadFrom(Stator)} is responsible
 * to create it based on the flags and data inside the stator
 * 
 * The order of reading values is critical inside a method.
 * 
 * Every {@link IStatorable} may gets assigned an ID along with its byte position.
 * This allow for reading and writing in any order.
 * 
 *  When calling another 
 * 
 * @author Charles Bentley
 *
 */
public interface IStatorable extends IStringable {

   /**
    * Write currently state.
    * 
    * Method takes the current state of the object and writes it to the {@link Stator}
    * 
    * Children of this object that are not {@link IStatorable} and are not one of the supported objects are not save.
    * Children that are {@link IStatorable} may get written by value or by reference
    * If the {@link IStatorable} is known to be shared by several objects, the Stator keeps track of them
    * and is able to write a reference.
    * 
    * @param boState
    */
   public void stateWriteTo(StatorWriter state);

   /**
    * @param boState
    */
   public void stateReadFrom(StatorReader state);
}
