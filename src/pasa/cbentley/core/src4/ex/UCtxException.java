/*
 * (c) 2018-2020 Charles-Philip Bentley
 * This code is licensed under MIT license (see LICENSE.txt for details)
 */
package pasa.cbentley.core.src4.ex;

public class UCtxException extends RuntimeException {

   public static final int EVENT_MATCH_EX = 1;

   private int             id;

   /**
    * ID is a unique static id valid module wide.
    * @param id
    * @param message
    */
   public UCtxException(int id, String message) {
      super(message);
      this.id = id;
   }

   public int getId() {
      return id;
   }

   public void setId(int id) {
      this.id = id;
   }

}
