/*
 * (c) 2018-2020 Charles-Philip Bentley
 * This code is licensed under MIT license (see LICENSE.txt for details)
 */
package pasa.cbentley.core.src4.structs.synch;

public class QueueObject {

   private boolean isNotified = false;

   /**
    * Wait until notified. Implementation is signal loss proof by using a defense flag as we cannot
    * trust this.wait(). On some OSes, it will spuriously wake up without a code notify.
    * 
    * <p>
    * Spurious wakeup.
    * While this will rarely occur in practice, applications must guard against it by testing for the condition 
    * that should have caused the thread to be awakened, and continuing to wait if the condition is not satisfied. 
    * In other words, waits should always occur in loops
    * </p>
    * @throws InterruptedException
    */
   public synchronized void doWait() throws InterruptedException {
      while (!isNotified) {
         this.wait();
      }
      this.isNotified = false;
   }

   public synchronized void doNotify() {
      this.isNotified = true;
      this.notify();
   }

   public boolean equals(Object o) {
      return this == o;
   }
}