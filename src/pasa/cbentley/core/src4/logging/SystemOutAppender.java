/*
 * (c) 2018-2020 Charles-Philip Bentley
 * This code is licensed under MIT license (see LICENSE.txt for details)
 */
package pasa.cbentley.core.src4.logging;

import pasa.cbentley.core.src4.ctx.UCtx;
import pasa.cbentley.core.src4.helpers.StringBBuilder;
import pasa.cbentley.core.src4.utils.StringUtils;

public class SystemOutAppender extends BaseAppender {

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

   private volatile int outputCounterNext = 0;

   /**
    * The log entry is a value type and is owned by the Appender. It is not shared.
    */
   public void processLogEntry(DLogEntry entry) {

      //get the config of this appender
      ILogEntryAppender logEntryAppender = this;
      IDLogConfig config = logEntryAppender.getConfig();

      //apply the config to the entry
      DLogEntryOfConfig logEntryc = entry.computeDLogEntryOfConfig(config);

      if (logEntryc.hasFormatFlag(FORMAT_FLAG_06_BIG)) {
         if (config.hasFlagMaster(MASTER_FLAG_11_IGNORES_BIGS)) {
            return;
         }
      }

      if (!logEntryc.isAccepted()) {
         return;
      }

      //get DLogEntry appenders for this call
      StringBBuilder sb = new StringBBuilder(uc, 500);

      int count = sb.getCount();
      if (logEntryc.hasFormatFlag(ITechDLogConfig.FORMAT_FLAG_08_OWNER_NAME)) {
         sb.append(entry.getNameOwner());
         sb.append('\t');
      }
      int workingCounter = outputCounterNext; //avoid thread issues

      appendPrefxLineNumber(sb, workingCounter);
      sb.append(' ');

      //first enter message. format is hard coded
      String tagString = entry.getTagString();
      sb.append(tagString);
      if (logEntryc.hasFormatFlag(ITechDLogConfig.FORMAT_FLAG_07_LEVEL)) {
         sb.append(" " + entry.getLevel() + " ");
      }
      if (logEntryc.hasFormatFlag(ITechDLogConfig.FORMAT_FLAG_04_THREAD)) {
         sb.append(" [" + entry.getThreadName() + "]");
      }
      int extraSpaces = sb.getCount() - count;
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
      String method = entry.getMethod();
      int indexLine = -1;
      if ((indexLine = method.indexOf("@")) != -1) {
         String methodStr = method.substring(0, indexLine);
         String lineData = method.substring(indexLine, method.length());
         int startLineValue = indexLine + 1;
         if (lineData.startsWith("@line")) {
            startLineValue = indexLine + 5;
         }
         String lineNumber = method.substring(startLineValue, method.length());

         sb.append(">");
         sb.append(' '); //a link needs to prefixed with " ("  otherwise eclipse system out does not create a link
         sb.append('('); //a link needs to prefixed with " ("  otherwise eclipse system out does not create a link
         sb.append(cname);
         sb.append(".java:");
         sb.append(lineNumber);
         sb.append(')');
         sb.append("#");
         sb.append(methodStr);
      } else {
         String mstr = cname + "#" + entry.getMethod();
         sb.append(mstr);
      }
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
         if (logEntryc.isOneLineConfig()) {
            if (hasMessage) {
               sb.append("|"); //appends separator between message and stringable
            }
            String str = entry.getString1Line();
            if (str != null && !str.equals("")) {
               sb.append(str);
            }
         } else {
            //create a new line prefix with as many spaces are necessary to match the start
            StringBBuilder sbnl = new StringBBuilder(uc, extraSpaces + 2); //+2 for the \n and \t
            sbnl.append('\n');
            appendPrefxLineNumber(sbnl, workingCounter);
            sbnl.append(' ');
            for (int i = 0; i < extraSpaces - 3; i++) {
               sbnl.append('-');
            }
            //sbnl.append('\t');
            String objnl = sbnl.toString(); //the newline tab for the DCtx of the Stringable
            
            //#mdebug
            Dctx dc = new Dctx(uc, objnl);
            stringable.toString(dc);
            dc.toStringCtx();
            String str = dc.toString();
            if (str != null && !str.equals("")) {
               sb.append(objnl);
               sb.append(str);
            }
            //#enddebug
         }
      }
      String nl = "\n";
      //fill the stack trace of the calls
      stackTrace(logEntryc, entry, sb, nl, config);

      String strToPrint = sb.toString();

      //if str is exactly the same.. print all on the same line 
      //      if(previousStr.equals(strToPrint)) {
      //         System.out.print("|");
      //      } else {
      //      }
      synchronized (System.out) {
         outputCounter++;
         if (outputCounter == 10000) {
            outputCounter = 1;
         }
         outputCounterNext = outputCounter;
         System.out.println(strToPrint);
         previousStr = strToPrint;
      }

   }

   private void appendPrefxLineNumber(StringBBuilder sb, int workingCounter) {
      if (workingCounter == 0) {
         sb.append(StringUtils.BLOCK_FULL);
         sb.append(StringUtils.ARROW_RIGHT);
         sb.append(workingCounter);
      } else if (workingCounter < 10) {
         sb.append('0');
         sb.append('0');
         sb.append(workingCounter);
      } else if (workingCounter < 100) {
         sb.append('0');
         sb.append(workingCounter);
      } else {
         sb.append(workingCounter);
      }
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

   //#mdebug
   public void toString(Dctx dc) {
      dc.root(this, SystemOutAppender.class, "@line166");
      toStringPrivate(dc);
      super.toString(dc.sup());
   }

   public void toString1Line(Dctx dc) {
      dc.root1Line(this, SystemOutAppender.class);
      toStringPrivate(dc);
      super.toString1Line(dc.sup1Line());
   }

   private void toStringPrivate(Dctx dc) {
      dc.appendVarWithSpace("outputCounter", outputCounter);
   }
   //#enddebug
}
