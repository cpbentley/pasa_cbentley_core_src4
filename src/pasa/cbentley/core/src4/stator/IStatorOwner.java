package pasa.cbentley.core.src4.stator;

import pasa.cbentley.core.src4.ctx.ICtx;

/**
 * Owns some state and {@link IStatorable} objects but itself cannot be {@link IStatorable}
 * @author Charles Bentley
 *
 */
public interface IStatorOwner {

   public void stateOwnerWrite(Stator stator);
   
   public void stateOwnerRead(Stator stator);
   
   public ICtx getCtxOwner();
}
