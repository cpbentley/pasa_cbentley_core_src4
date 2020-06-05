/*
 * (c) 2018-2020 Charles-Philip Bentley
 * This code is licensed under MIT license (see LICENSE.txt for details)
 */
package pasa.cbentley.core.src4.logging;

/**
 * Production Environement Error message that helps error management once
 * code has been proguarded.
 * <br>
 * Identifies
 * <li> Module Context
 * <li> Application Context
 * <li> Package ID
 * <li> Class ID
 * 
 * This class is not used for logging. Its only for reporting severe
 * exceptions in production.
 * 
 * @author Charles Bentley
 *
 */
public class BipException extends RuntimeException {

   public BipException(int bip) {
      super(String.valueOf(bip));
   }

   public BipException(int bip, String msg) {
      super(String.valueOf(bip) + " : " + msg);
   }

   public static long getBip(int moduleID, int appID, int packID, int classID) {
      return (moduleID << 24) + (appID << 16) + (packID << 8) + classID;
   }
}
