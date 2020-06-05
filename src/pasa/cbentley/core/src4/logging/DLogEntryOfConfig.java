/*
 * (c) 2018-2020 Charles-Philip Bentley
 * This code is licensed under MIT license (see LICENSE.txt for details)
 */
package pasa.cbentley.core.src4.logging;

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
public class DLogEntryOfConfig implements ITechLvl {
   //#mdebug

   private IDLogConfig   config;

   /**
    * Result of {@link DLogEntry#computeDLogEntryOfConfig(IDLogConfig)}
    */
   private int configFlags;

   private DLogEntry entry;

   private UCtx      uc;

   public DLogEntryOfConfig(UCtx uc, IDLogConfig config, DLogEntry entry) {
      this.uc = uc;
      this.config = config;
      this.entry = entry;
   }

   public IDLogConfig getConfig() {
      return config;
   }

   public DLogEntry getEntry() {
      return entry;
   }

   public boolean hasConfigFlag(int flag) {
      return BitUtils.hasFlag(configFlags, flag);
   }

   public boolean isAccepted() {
      return BitUtils.hasFlag(configFlags, ITechConfig.CONFIG_FLAG_1_ACCEPTED);
   }

   public boolean isOneLineConfig() {
      return BitUtils.hasFlag(configFlags, ITechConfig.CONFIG_FLAG_2_1LINE);
   }

   public boolean isStackConfig() {
      return BitUtils.hasFlag(configFlags, ITechConfig.CONFIG_FLAG_3_STACK);
   }

   public void setConfigResFlag(int flag, boolean v) {
      configFlags = BitUtils.setFlag(configFlags, flag, v);
   }

   //#enddebug
}
