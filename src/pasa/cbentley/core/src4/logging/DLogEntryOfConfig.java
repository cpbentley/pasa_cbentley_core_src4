/*
 * (c) 2018-2020 Charles-Philip Bentley
 * This code is licensed under MIT license (see LICENSE.txt for details)
 */
package pasa.cbentley.core.src4.logging;

import pasa.cbentley.core.src4.ctx.ObjectU;
import pasa.cbentley.core.src4.ctx.UCtx;
import pasa.cbentley.core.src4.utils.BitUtils;

/**
 * Log Entry applied to a {@link IDLogConfig}.
 * 
 * Answer the questions
 * <li>should the log entry be displayed for this config
 * <li>should it include stack trace data, if so it computes it
 * <li>should it include thread data, if so it computes it
 * 
 * @author Charles Bentley
 *
 */
public class DLogEntryOfConfig extends ObjectU implements ITechLvl {

   private IDLogConfig   config;

   /**
    * Result of {@link DLogEntry#computeDLogEntryOfConfig(IDLogConfig)}
    */
   private int formatFlags;

   private DLogEntry entry;


   public DLogEntryOfConfig(UCtx uc, IDLogConfig config, DLogEntry entry) {
      super(uc);
      this.config = config;
      this.entry = entry;
   }

   public IDLogConfig getConfig() {
      return config;
   }

   public DLogEntry getEntry() {
      return entry;
   }

   public boolean hasFormatFlag(int flag) {
      return BitUtils.hasFlag(formatFlags, flag);
   }

   public boolean isAccepted() {
      return BitUtils.hasFlag(formatFlags, ITechDLogConfig.FORMAT_FLAG_01_ACCEPTED);
   }

   public boolean isOneLineConfig() {
      return BitUtils.hasFlag(formatFlags, ITechDLogConfig.FORMAT_FLAG_02_1LINE);
   }

   public boolean isStackConfig() {
      return BitUtils.hasFlag(formatFlags, ITechDLogConfig.FORMAT_FLAG_03_STACK);
   }

   public void setConfigResFlag(int flag, boolean v) {
      formatFlags = BitUtils.setFlag(formatFlags, flag, v);
   }

   //#mdebug
   public void toString(Dctx dc) {
      dc.root(this, DLogEntryOfConfig.class, toStringGetLine(70));
      toStringPrivate(dc);
      super.toString(dc.sup());
      
      dc.nlLvl(entry, "entry");
      dc.nlLvl(config, "config");
   }

   public void toString1Line(Dctx dc) {
      dc.root1Line(this, DLogEntryOfConfig.class, toStringGetLine(70));
      toStringPrivate(dc);
      super.toString1Line(dc.sup1Line());
   }

   private void toStringPrivate(Dctx dc) {
      
   }
   //#enddebug
   

   
}
