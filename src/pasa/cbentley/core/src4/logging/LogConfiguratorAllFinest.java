/*
 * (c) 2018-2020 Charles-Philip Bentley
 * This code is licensed under MIT license (see LICENSE.txt for details)
 */
package pasa.cbentley.core.src4.logging;

public class LogConfiguratorAllFinest implements ILogConfigurator {

   public void apply(IDLogConfig log) {
      log.setLevelGlobal(ITechLvl.LVL_03_FINEST);
      log.setFlagMaster(ITechDLogConfig.MASTER_FLAG_02_OPEN_ALL_PRINT, true);
   }

}
