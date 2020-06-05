/*
 * (c) 2018-2020 Charles-Philip Bentley
 * This code is licensed under MIT license (see LICENSE.txt for details)
 */
package pasa.cbentley.core.src4.logging;


/**
 * An Appender is interested by some log entries based on a Config.
 * 
 * <br>
 * Appender can be configured to accept {@link DLogEntry} based on
 * <li> {@link Class} 
 *  <ol> 
 *      <li> {@link IDLogConfig#setClassPositives(Class, boolean)}
 *      <li> {@link IDLogConfig#setClassNegative(Class, boolean)}
 *      <li> {@link IDLogConfig#setClassFullPositive(Class, boolean)}
 *  </ol>
 * <li> Method 
 * <li> Tag {@link IDLogConfig#setFlagTag(int, boolean)}
 * <br>
 * 
 * @author Charles Bentley
 *
 */
public interface ILogEntryAppender {

   //#mdebug
   
   /**
    * Current configuration of Appender
    * @return
    */
   public IDLogConfig getConfig();

   /**
    * Analyse the {@link DLogEntry} and if it is accepted, keep it, print it, network it, drop it, file it.
    * <br>
    * We don't care. Each appender will have its own rules
    * @param logEntry
    */
   public void processLogEntry(DLogEntry logEntry);

   /**
    * Set the config that will filter out and format log entries.
    * <br>
    * {@link IDLogConfig#setStackTraced(int, Class, boolean)} tells appender to append stack call for logs of that class
    * <br>
    * Note: The appender may decide to keep every single logs.
    * The {@link IDLogConfig} here is the developpeur telling the logger what he really wants to see.
    * <br>
    * JTronconneuse for instance keeps everything and each tab has its own {@link IDLogConfig}.
    * @param c
    */
   public void setConfig(IDLogConfig c);
   
   //#enddebug
   
}
