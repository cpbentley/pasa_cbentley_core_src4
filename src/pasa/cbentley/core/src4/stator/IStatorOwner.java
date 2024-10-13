package pasa.cbentley.core.src4.stator;

import pasa.cbentley.core.src4.ctx.ICtx;

/**
 * Owns some state and {@link IStatorable} objects but itself cannot be {@link IStatorable}.
 * 
 * @author Charles Bentley
 *
 */
public interface IStatorOwner {

   /**
    * Write the state to the {@link Stator}. Which {@link StatorWriter} is used depends ? By default it uses active writer.
    * 
    * Caller of this 
    * 
    * <p>
    * Method may request a specific keyed {@link StatorWriter}.
    * </p>
    * 
    * <p>
    * The {@link Stator} is shared in all the object hierarchy to avoid creating duplicates of the same object references.
    * So that when reading is done, a single object is created
    * </p>
    * 
    * @param stator
    */
   public void stateOwnerWrite(Stator stator);

   /**
    * Read the state from the {@link Stator} using which {@link StatorReader} ?
    * 
    * <p>
    * 
    * </p>
    * @param stator
    */
   public void stateOwnerRead(Stator stator);

   /**
    * The {@link ICtx} being this {@link IStatorOwner}
    * @return
    */
   public ICtx getCtxOwner();
}
