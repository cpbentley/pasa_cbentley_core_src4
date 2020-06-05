/*
 * (c) 2018-2020 Charles-Philip Bentley
 * This code is licensed under MIT license (see LICENSE.txt for details)
 */
package pasa.cbentley.core.src4.ex;

/**
 * Implements nested exceptions
 * @author Charles Bentley
 *
 */
public class GenericException extends Exception {

   private Exception e;

   public GenericException() {
   }

   public Exception getNest() {
      return e;
   }
   /**
    * Nest an exception inside
    * @param e
    */
   public GenericException(Exception e) {
      this("", e);
      this.e = e;
   }

   public GenericException(String string) {
      super(string);
   }

   public GenericException(String message, Throwable cause) {
      //super(message, cause);
   }

}
