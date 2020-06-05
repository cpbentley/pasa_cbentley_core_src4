/*
 * (c) 2018-2020 Charles-Philip Bentley
 * This code is licensed under MIT license (see LICENSE.txt for details)
 */
package pasa.cbentley.core.src4.structs.synch;

public class QueueObject {

   private boolean isNotified = false;

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