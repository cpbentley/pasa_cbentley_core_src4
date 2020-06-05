/*
 * (c) 2018-2020 Charles-Philip Bentley
 * This code is licensed under MIT license (see LICENSE.txt for details)
 */
package pasa.cbentley.core.src4.interfaces;

/**
 * When running a Unit Test, set your own timer and random
 * @author Charles Bentley
 *
 */
public interface ITimeCtrl {

   /**
    * Tick with nano seconds precision
    * If the host is not able to measure time with nano precision, it will return
    * millis * 1000000
    * @return
    */
   public long getTickNano();

   /**
    * Same as {@link ITimeCtrl#getNowTickNano()} but in nano
    * @return
    */
   public long getTickMillis();

   /**
    * Returns the System clock in milliseconds.
    * <br>
    * Use this call when measuring external time.
    * <br>
    * When computing code performace, use Tick
    * @return
    */
   public long getNowClock();

}
