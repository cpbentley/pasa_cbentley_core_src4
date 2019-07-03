package pasa.cbentley.core.src4.logging;


/**
 * An Appender is interested by some log entries based on a Config.
 * 
 * <br>
 * Appender can be configured to accept {@link DLogEntry} based on
 * <li> {@link Class} 
 *  <ol> 
 *      <li> {@link IConfig#setClassPositives(Class, boolean)}
 *      <li> {@link IConfig#setClassNegative(Class, boolean)}
 *      <li> {@link IConfig#setClassFullPositive(Class, boolean)}
 *  </ol>
 * <li> Method 
 * <li> Tag {@link IConfig#setFlagTag(int, boolean)}
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
   public IConfig getConfig();

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
    * {@link IConfig#setStackTraced(int, Class, boolean)} tells appender to append stack call for logs of that class
    * <br>
    * Note: The appender may decide to keep every single logs.
    * The {@link IConfig} here is the developpeur telling the logger what he really wants to see.
    * <br>
    * JTronconneuse for instance keeps everything and each tab has its own {@link IConfig}.
    * @param c
    */
   public void setConfig(IConfig c);
   
   //#enddebug
   
}
