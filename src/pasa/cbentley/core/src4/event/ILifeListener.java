/*
 * (c) 2018-2020 Charles-Philip Bentley
 * This code is licensed under MIT license (see LICENSE.txt for details)
 */
package pasa.cbentley.core.src4.event;

public interface ILifeListener {

   public void lifeStarted(ILifeContext context);
   public void lifePaused(ILifeContext context);
   public void lifeResumed(ILifeContext context);
   public void lifeStopped(ILifeContext context);
}
