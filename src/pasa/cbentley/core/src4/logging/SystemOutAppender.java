package pasa.cbentley.core.src4.logging;

import pasa.cbentley.core.src4.ctx.UCtx;
import pasa.cbentley.core.src4.helpers.StringBBuilder;

public class SystemOutAppender extends BaseAppender {

   //#mdebug
   private int    outputCounter = 0;

   private String previousStr   = "";

   public SystemOutAppender(UCtx uc) {
      super(uc);
   }

   /**
    * sub class with support with stack tracing building
    * @return
    */
   public String[] getStackTrace() {

      return null;
   }

   /**
    * The log entry is a value type and is owned by the Appender. It is not shared.
    */
   public void processLogEntry(DLogEntry entry) {

      //get the config of this appender
      ILogEntryAppender logEntryAppender = this;
      IDLogConfig config = logEntryAppender.getConfig();

      //apply the config to the entry
      DLogEntryOfConfig ec = entry.computeDLogEntryOfConfig(config);

      if (!ec.isAccepted()) {
         return;
      }
      //get DLogEntry appenders for this call
      StringBBuilder sb = new StringBBuilder(uc,500);

      //first enter message. format is hard coded
      String tagString = entry.getTagString();
      sb.append(tagString);
      if (ec.hasConfigFlag(ITechConfig.CONFIG_FLAG_04_SHOW_THREAD)) {
         sb.append("[" + entry.getThreadName() + "]");
      }
      sb.tab();

      //format specific
      Class c = entry.getClassL();
      //check if stack trace is enable for class and flag
      String cname = "";
      if (c != null) {
         cname = c.getName();
         if (cname.lastIndexOf('.') != -1) {
            cname = cname.substring(cname.lastIndexOf('.') + 1, cname.length());
         }
      }
      String mstr = cname + "#" + entry.getMethod();
      sb.append(mstr);
      sb.append(" ");

      boolean hasMessage = false;
      String msg = entry.getMsg();
      if (msg != null && !msg.equals("")) {
         //message
         sb.append(msg);
         hasMessage = true;
      }
      IStringable stringable = entry.getStringable();
      if (stringable != null) {
         //check if we have to 1 line
         if (ec.isOneLineConfig()) {
            if (hasMessage) {
               sb.append("|"); //appends separator between message and stringable
            }
            String str = entry.getString1Line();
            if (str != null && !str.equals("")) {
               sb.append(str);
            }
         } else {
            String objnl = "\n\t"; //the newline tab for the DCtx of the Stringable
            Dctx dc = new Dctx(uc, objnl);
            stringable.toString(dc);
            String str = dc.toString();
            if (str != null && !str.equals("")) {
               sb.append(objnl);
               sb.append(str);
            }
         }
      }
      String nl = "\n";
      //fill the stack trace of the calls
      stackTrace(ec,entry, sb, nl, config);

      String strToPrint = sb.toString();

      //if str is exactly the same.. print all on the same line 
      //      if(previousStr.equals(strToPrint)) {
      //         System.out.print("|");
      //      } else {
      //      }
      outputCounter++;
      if (outputCounter == 10000) {
         outputCounter = 1;
      }
      System.out.print(outputCounter);
      System.out.print(" ");
      System.out.println(strToPrint);
      previousStr = strToPrint;

   }

   /**
    * 
    * @param flag
    * @param c
    * @param sb
    * @param nl
    */
   private void stackTrace(DLogEntryOfConfig ec, DLogEntry entry, StringBBuilder sb, String nl, IDLogConfig config) {
      if (ec.isStackConfig()) {
         String[] stack = getStackTrace();
         if (stack != null) {
            String breakStack = getConfig().getStackTraceBreak();
            boolean doBreak = (breakStack != null && !breakStack.equals(""));
            for (int i = 0; i < stack.length; i++) {
               String str = stack[i].toString();
               //ignore stacktrace with this class.. not usefull.
               if (!str.startsWith("pasa.cbentley.utils_src4.logging")) {
                  if (doBreak && str.startsWith(breakStack)) {
                     break;
                  }
                  sb.append(nl);
                  sb.append(str);
               }
            }
            sb.append(nl);
         }
      }
   }

   //#enddebug
}
