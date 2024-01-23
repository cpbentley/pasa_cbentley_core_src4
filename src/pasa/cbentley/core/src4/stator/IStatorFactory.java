/*
 * (c) 2018-2020 Charles-Philip Bentley
 * This code is licensed under MIT license (see LICENSE.txt for details)
 */
package pasa.cbentley.core.src4.stator;

import pasa.cbentley.core.src4.ctx.ICtx;

public interface IStatorFactory {

   public Object[] createArray(int classID, int size);

   public boolean isSupported(int classID);

   /**
    * The Context to which belongs the Factory;
    * @return never null
    */
   public ICtx getCtx();

   /**
    * Returns null if class ID is not know
    * @param classID
    * @return
    */
   public Object createObject(int classID);
}
