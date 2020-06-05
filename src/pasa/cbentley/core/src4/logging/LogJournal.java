package pasa.cbentley.core.src4.logging;

import pasa.cbentley.core.src4.ctx.UCtx;
import pasa.cbentley.core.src4.structs.IntToObjects;

/**
 * Adds all requests in a Journal until a real log takes this log
 * and prints the Log entries.
 * <br>
 * Ignores Flags. Log all entries.
 * <br>
    * Do we keep a copy of {@link IStringable} in both style?
    * Yes because this class will be used for more complex logging
 * @author Charles Bentley
 *
 */
public class LogJournal extends LogAdapter implements IDLog, ITechTags {
   //#mdebug
   private IntToObjects ito;

   public LogJournal(UCtx uc) {
      super(uc);
      ito = new IntToObjects(uc);
   }

   public boolean hasDataFlag(int flag) {
      return true;
   }

   public boolean hasDFlag(int flag) {
      return true;
   }

   public boolean hasFlag(int flagType, int flag) {
      return true;
   }

   public void print(int code, String msg, Class c) {
      ito.add("sd");
   }

   public void pInit(String msg, IStringable d, Class c, String method) {
      addLogEntry(msg, d, c, method, FLAG_20_PRINT_INIT, false);
   }

   public void pInit1(String msg, IStringable d, Class c, String method) {
      addLogEntry(msg, d, c, method, FLAG_20_PRINT_INIT, true);
   }

   public void pData(String msg, IStringable d, Class c, String method) {
      addLogEntry(msg, d, c, method, FLAG_15_PRINT_DATA, false);
   }

   private void addLogEntry(String msg, IStringable d, Class c, String method, int flag, boolean oneLine) {
      LogEntry le = new LogEntry(uc, msg, d, c, method, flag, oneLine);
      ito.add(le);
   }

   public void pBridge(String msg, IStringable d, Class c, String method) {
      addLogEntry(msg, d, c, method, FLAG_19_PRINT_BRIDGE, false);
   }

   public void pBridge1(String msg, IStringable d, Class c, String method) {
      addLogEntry(msg, d, c, method, FLAG_19_PRINT_BRIDGE, true);
   }

   public void pEvent1(String msg, IStringable d, Class c, String method) {
      addLogEntry(msg, d, c, method, FLAG_07_PRINT_EVENT, true);
   }

   public void logTo(IDLog log) {
      for (int i = 0; i < ito.nextempty; i++) {
         Object logEntry = ito.objects[i];
         if (logEntry instanceof LogEntry) {
            LogEntry le = (LogEntry) logEntry;
            log.pFlag(le.flag, le.msg, le, le.c, le.method, le.lvl, le.is1Line);
         }
      }
   }

   //#enddebug
}
