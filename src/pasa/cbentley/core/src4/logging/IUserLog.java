/*
 * (c) 2018-2020 Charles-Philip Bentley
 * This code is licensed under MIT license (see LICENSE.txt for details)
 */
package pasa.cbentley.core.src4.logging;

/**
 * Interface to a User logger
 * <br>
 * This logger is not intented for debug purposes. It is there to provide messages back to 
 * the dumb final user.
 * <br>
 * <br>
 * Typically a GUI application will implement this interface with a thread safe GUI scrolling text pane.
 * <br>
 * The calls might come from any thread.
 * <br>
 * <br>
 * 
 * @author Charles Bentley
 *
 */
public interface IUserLog extends IStringable {

   public static final int consoleLog          = 0;

   public static final int consoleLogError     = 1;

   public static final int consoleLogGreen     = 2;

   public static final int consoleLogDate      = 3;

   public static final int consoleLogDateGreen = 4;

   public static final int consoleLogDateRed   = 5;

   public void consoleLog(String str);

   public void consoleLogError(String str);

   public void consoleLogGreen(String str);

   public void consoleLogDate(String str);

   public void consoleLogDateGreen(String str);

   public void consoleLogDateRed(String str);
   
   /**
    * Called when replacing another log. Implementation is free to do what it wants
    * @param log
    */
   public void processOld(IUserLog log);
}
