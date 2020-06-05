package pasa.cbentley.core.src4.logging;

public class LogConfiguratorAllFinest implements ILogConfigurator {


   public void apply(IDLogConfig log) {
      log.setLevelGlobal(ITechLvl.LVL_03_FINEST);
      log.setFlagPrint(ITechConfig.MASTER_FLAG_02_OPEN_ALL_PRINT, true);
   }

}
