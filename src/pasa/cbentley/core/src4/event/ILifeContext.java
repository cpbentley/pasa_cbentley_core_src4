/*
 * (c) 2018-2020 Charles-Philip Bentley
 * This code is licensed under MIT license (see LICENSE.txt for details)
 */
package pasa.cbentley.core.src4.event;

public interface ILifeContext {

   
   /**
    * Called when {@link ILifeListener} is enable to successfully complete a life method.
    * @param lis
    */
   public void addFailure(ILifeListener lis);
}
