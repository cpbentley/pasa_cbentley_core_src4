/*
 * (c) 2018-2020 Charles-Philip Bentley
 * This code is licensed under MIT license (see LICENSE.txt for details)
 */
package pasa.cbentley.core.src4.stator;

import pasa.cbentley.core.src4.ctx.ICtx;

public interface IStatorFactory {

   public Object[] createArray(int classID, int size);

   /**
    * selector for the class supported ?
    * @param statorable
    * @return
    */
   public boolean isSupported(IStatorable statorable);

   /**
    * The Context to which belongs the Factory;
    * @return never null
    */
   public ICtx getCtx();

   /**
    * Returns null if class ID is not know.
    * 
    * Object constructor parameters are {@link IStatorable} and read in front on the {@link StatorReader}
    * as
    * @param reader allows the factory to read parameters
    * @param classID selector for the class
    * @return
    */
   public Object createObject(StatorReader reader, int classID);
}
