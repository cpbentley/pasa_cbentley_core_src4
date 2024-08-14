/*
 * (c) 2018-2020 Charles-Philip Bentley
 * This code is licensed under MIT license (see LICENSE.txt for details)
 */
package pasa.cbentley.core.src4.logging;

import pasa.cbentley.core.src4.ctx.UCtx;

/**
 * Src4 Logger cannot produce a stack trace.
 * 
 * Extends this logger to provide additional types
 * @author Charles Bentley
 *
 */
public class BaseDLogger extends RootDLogger implements IDLog, ITechTags {

   public BaseDLogger(UCtx uc) {
      super(uc);
   }

   /**
    * This is used for quick debugging without having to change configuration of the logger.
    * <br>
    * The call should always be changed back to something else
    * @param msg
    * @param str
    * @param c
    * @param method
    */
   public void pAlways(String msg, Object str, Class c, String method) {
      ptPrint(msg, getStringable(str), c, method, ITechTags.STRING_01_ALWAYS, ITechTags.FLAG_01_PRINT_ALWAYS, getLevelDefault(), false);
   }

   public void pAlways(String msg, Object str, Class c, String method, int lvl, boolean oneLine) {
      ptPrint(msg, getStringable(str), c, method, ITechTags.STRING_01_ALWAYS, ITechTags.FLAG_01_PRINT_ALWAYS, lvl, oneLine);
   }

   public void pAnim(String msg, Object str, Class c, String method) {
      pAnim(msg, getStringable(str), c, method, getLevelDefault(), false);
   }

   public void pAnim(String msg, Object str, Class c, String method, int lvl, boolean oneLine) {
      super.ptPrint(msg, getStringable(str), c, method, ITechTags.STRING_23_ANIM, ITechTags.FLAG_23_PRINT_ANIM, lvl, oneLine);
   }

   public void pBridge(String msg, Object str, Class c, String method) {
      pBridge(msg, getStringable(str), c, method, getLevelDefault(), false);
   }

   public void pBridge(String msg, Object str, Class c, String method, int lvl, boolean oneLine) {
      super.ptPrint(msg, getStringable(str), c, method, ITechTags.STRING_19_BRIDGE, ITechTags.FLAG_19_PRINT_BRIDGE, lvl, oneLine);
   }

   public void pCreate(String msg, Object str, Class c, String method, int lvl, boolean oneLine) {
      super.ptPrint(msg, getStringable(str), c, method, ITechTags.STRING_25_CREATE, ITechTags.FLAG_25_PRINT_CREATE, lvl, oneLine);
   }

   public void pBridge1(String msg, Object str, Class c, String method) {
      pBridge(msg, getStringable(str), c, method, getLevelDefault(), true);
   }

   public void pBusiness(String msg, Object str, Class c, String method, int lvl, boolean oneLine) {
      super.ptPrint(msg, getStringable(str), c, method, ITechTags.STRING_12_BUSINESS, ITechTags.FLAG_12_PRINT_BUSINESS, lvl, oneLine);
   }

   public void pBusiness(String msg, Object str, Class c, String method, int lvl, int flags) {
      super.ptPrint(msg, getStringable(str), c, method, ITechTags.STRING_12_BUSINESS, ITechTags.FLAG_12_PRINT_BUSINESS, lvl, flags);
   }

   public void pBusiness1(String msg, Object str, Class c, String method) {
      pCmd(msg, getStringable(str), c, method, getLevelDefault(), true);
   }

   public void pCmd(String msg, Object str, Class c, String method) {
      pCmd(msg, getStringable(str), c, method, getLevelDefault(), false);
   }

   public void pCmd(String msg, Object str, Class c, String method, int lvl, boolean oneLine) {
      super.ptPrint(msg, getStringable(str), c, method, ITechTags.STRING_11_CMD, ITechTags.FLAG_11_PRINT_COMMANDS, lvl, oneLine);
   }

   public void pCmd1(String msg, Object str, Class c, String method) {
      pCmd(msg, getStringable(str), c, method, getLevelDefault(), true);
   }

   public void pData(String msg, Object str, Class c, String method) {
      pData(msg, getStringable(str), c, method, getLevelDefault(), false);
   }

   public void pData(String msg, Object str, Class c, String method, int lvl, boolean oneLine) {
      super.ptPrint(msg, getStringable(str), c, method, ITechTags.STRING_15_DATA, ITechTags.FLAG_15_PRINT_DATA, lvl, oneLine);
   }

   public void pData(String msg, Object str, Class c, String method, int lvl, boolean oneLine, Exception e) {
      super.ptPrint(msg, getStringable(str), c, method, ITechTags.STRING_15_DATA, ITechTags.FLAG_15_PRINT_DATA, lvl, oneLine);
      e.printStackTrace();
   }

   public void pDraw(String msg, Object str, Class c, String method) {
      pDraw(msg, getStringable(str), c, method, getLevelDefault(), false);
   }

   public void pDraw(String msg, Object str, Class c, String method, int lvl, boolean oneLine) {
      super.ptPrint(msg, getStringable(str), c, method, ITechTags.STRING_24_DRAW, ITechTags.FLAG_24_PRINT_DRAW, lvl, oneLine);
   }

   public void pEvent(String msg, Object str, Class c, String method) {
      pEvent(msg, getStringable(str), c, method, getLevelDefault(), false);
   }

   public void pEvent(String msg, Object str, Class c, String method, int lvl) {
      pEvent(msg, getStringable(str), c, method, lvl, false);
   }

   public void pEvent(String msg, Object str, Class c, String method, int lvl, boolean oneLine) {
      super.ptPrint(msg, getStringable(str), c, method, ITechTags.STRING_07_EVENT, ITechTags.FLAG_07_PRINT_EVENT, lvl, oneLine);
   }

   public void pEvent(String msg, Object str, Class c, String method, int lvl, int flags) {
      super.ptPrint(msg, getStringable(str), c, method, ITechTags.STRING_07_EVENT, ITechTags.FLAG_07_PRINT_EVENT, lvl, flags);
   }

   /**
    * Express cue to only print 1 line by default level defined by the {@RootDLogger} .
    * @param msg
    * @param str
    * @param c
    * @param method
    * @param lvl
    */
   public void pEvent1(String msg, Object str, Class c, String method) {
      pEvent(msg, getStringable(str), c, method, getLevelDefault(), true);
   }

   public void pEventFine(String msg, Object str, Class c, String method) {
      pEvent(msg, getStringable(str), c, method, ITechLvl.LVL_05_FINE);
   }

   public void pEventFiner(String msg, Object str, Class c, String method) {
      pEvent(msg, getStringable(str), c, method, ITechLvl.LVL_04_FINER);
   }

   public void pEventFinest(String msg, Object str, Class c, String method) {
      pEvent(msg, getStringable(str), c, method, ITechLvl.LVL_03_FINEST);
   }

   public void pEventInfo(String msg, Object str, Class c, String method) {
      pEvent(msg, getStringable(str), c, method, ITechLvl.LVL_08_INFO);
   }

   public void pEventSevere(String msg, Object str, Class c, String method) {
      pEvent(msg, getStringable(str), c, method, ITechLvl.LVL_10_SEVERE);
   }

   public void pEventWarn(String msg, Object str, Class c, String method) {
      pEvent(msg, getStringable(str), c, method, ITechLvl.LVL_07_CONFIG);
   }

   public void pEx(String msg, Object str, Class c, String method, Exception e) {
      pEx(msg, getStringable(str), c, method, getLevelDefault(), false, e);
   }

   public void pEx(String msg, Object str, Class c, String method, int lvl, boolean oneLine, Exception e) {
      super.ptPrint(msg, getStringable(str), c, method, ITechTags.STRING_08_EX, ITechTags.FLAG_08_PRINT_EXCEPTION, lvl, oneLine);
      e.printStackTrace();
   }

   public void pFlag(int tagID, String msg, Object str, Class c, String method, int lvl, boolean oneLine) {
      this.ptPrint(msg, getStringable(str), c, method, "FLAG", tagID, lvl, oneLine);
   }

   public void pFlow(String msg, Object str, Class c, String method) {
      pFlow(msg, getStringable(str), c, method, getLevelDefault(), false);
   }

   public void pFlow(String msg, Object str, Class c, String method, int lvl, boolean oneLine) {
      super.ptPrint(msg, getStringable(str), c, method, ITechTags.STRING_09_FLOW, ITechTags.FLAG_09_PRINT_FLOW, lvl, oneLine);
   }

   private IStringable getStringable(Object str) {
      if (str == null) {
         return null;
      }
      if (str instanceof IStringable) {
         return (IStringable) str;
      } else {
         return new StringableWrapper(uc, str);
      }
   }

   public void pFlow(String msg, Object str, Class c, String method, int lvl, int flags) {
      super.ptPrint(msg, getStringable(str), c, method, ITechTags.STRING_09_FLOW, ITechTags.FLAG_09_PRINT_FLOW, lvl, flags);
   }

   public void pFlowBig(String msg, Object str, Class c, String method) {
      super.ptPrintBig(msg, getStringable(str), c, method, ITechTags.STRING_09_FLOW, ITechTags.FLAG_09_PRINT_FLOW, getLevelDefault());
   }

   public void pInit(String msg, Object str, Class c, String method) {
      pInit(msg, getStringable(str), c, method, getLevelDefault(), false);
   }

   public void pInit(String msg, Object str, Class c, String method, int lvl, boolean oneLine) {
      super.ptPrint(msg, getStringable(str), c, method, ITechTags.STRING_20_INIT, ITechTags.FLAG_20_PRINT_INIT, lvl, oneLine);
   }

   public void pInit(String msg, Object str, Class c, String method, int lvl, int flags) {
      super.ptPrint(msg, getStringable(str), c, method, ITechTags.STRING_20_INIT, ITechTags.FLAG_20_PRINT_INIT, lvl, flags);
   }

   public void pInit1(String msg, Object str, Class c, String method) {
      pInit(msg, getStringable(str), c, method, getLevelDefault(), true);
   }

   public void pInitBig(String msg, Object str, Class c, String method) {
      super.ptPrintBig(msg, getStringable(str), c, method, ITechTags.STRING_20_INIT, ITechTags.FLAG_20_PRINT_INIT, getLevelDefault());
   }

   public void pMemory(String msg, Object str, Class c, String method) {
      pMemory(msg, getStringable(str), c, method, getLevelDefault(), false);
   }

   public void pMemory(String msg, Object str, Class c, String method, int lvl, boolean oneLine) {
      super.ptPrint(msg, getStringable(str), c, method, ITechTags.STRING_18_MEMORY, ITechTags.FLAG_18_PRINT_MEMORY, lvl, oneLine);

   }

   public void pMemoryWarn(String msg, Object str, Class c, String method) {
      super.ptPrint(msg, getStringable(str), c, method, ITechTags.STRING_18_MEMORY, ITechTags.FLAG_18_PRINT_MEMORY, ITechLvl.LVL_09_WARNING, false);
   }

   public void pModel(String msg, Object str, Class c, String method) {
      pModel(msg, getStringable(str), c, method, getLevelDefault(), false);
   }

   public void pModel(String msg, Object str, Class c, String method, int lvl, boolean oneLine) {
      super.ptPrint(msg, getStringable(str), c, method, ITechTags.STRING_10_MODEL, ITechTags.FLAG_10_PRINT_MODEL, lvl, oneLine);
   }

   public void pModel1(String msg, Object str, Class c, String method) {
      pModel(msg, getStringable(str), c, method, getLevelDefault(), true);
   }

   public void pNull(String msg, Object str, Class c, String method) {
      pNull(msg, getStringable(str), c, method, getLevelDefault(), false);
   }

   public void pNull(String msg, Object str, Class c, String method, int lvl, boolean oneLine) {
      super.ptPrint(msg, getStringable(str), c, method, ITechTags.STRING_02_NULL, ITechTags.FLAG_02_PRINT_NULL, lvl, oneLine);
   }

   public void pSound(String msg, Object str, Class c, String method) {
      pSound(msg, getStringable(str), c, method, getLevelDefault(), false);
   }

   public void pSound(String msg, Object str, Class c, String method, int lvl, boolean oneLine) {
      super.ptPrint(msg, getStringable(str), c, method, ITechTags.STRING_13_SOUND, ITechTags.FLAG_13_PRINT_SOUND, lvl, oneLine);
   }

   public void pSound1(String msg, Object str, Class c, String method) {
      pSound(msg, getStringable(str), c, method, getLevelDefault(), true);
   }

   public void pSoundEvent(String msg, Object str, Class c, String method, int lvl, boolean oneLine) {
      int tagId = ITechTags.FLAG_13_PRINT_SOUND | ITechTags.FLAG_07_PRINT_EVENT;
      String tagStr = ITechTags.STRING_13_SOUND + ITechTags.STRING_07_EVENT;
      super.ptPrint(msg, getStringable(str), c, method, tagStr, tagId, lvl, oneLine);

   }

   public void pState(String msg, Object str, Class c, String method) {
      pState(msg, getStringable(str), c, method, getLevelDefault(), false);
   }

   public void pState(String msg, Object str, Class c, String method, int lvl, boolean oneLine) {
      super.ptPrint(msg, getStringable(str), c, method, ITechTags.STRING_22_STATE, ITechTags.FLAG_22_PRINT_STATE, lvl, oneLine);
   }

   public void pStator(String msg, Object str, Class c, String method) {
      pStator(msg, getStringable(str), c, method, getLevelDefault(), false);
   }

   public void pStator(String msg, Object str, Class c, String method, int lvl, boolean oneLine) {
      super.ptPrint(msg, getStringable(str), c, method, ITechTags.STRING_04_STATOR, ITechTags.FLAG_04_PRINT_STATOR, lvl, oneLine);
   }

   public void pTest(String msg, Object str, Class c, String method) {
      this.pTest(msg, getStringable(str), c, method, getLevelDefault(), false);
   }

   public void pTest(String msg, Object str, Class c, String method, int lvl, boolean oneLine) {
      super.ptPrint(msg, getStringable(str), c, method, ITechTags.STRING_17_TEST, ITechTags.FLAG_17_PRINT_TEST, lvl, oneLine);
   }

   public void pTest1(String msg, Object str, Class c, String method) {
      pTest(msg, getStringable(str), c, method, getLevelDefault(), true);
   }

   public void pUI(String msg, Object str, Class c, String method) {
      pUI(msg, getStringable(str), c, method, getLevelDefault(), false);
   }

   public void pUI(String msg, Object str, Class c, String method, int lvl, boolean oneLine) {
      super.ptPrint(msg, getStringable(str), c, method, ITechTags.STRING_05_UI, ITechTags.FLAG_05_PRINT_UI, lvl, oneLine);
   }

   public void pWork(String msg, Object str, Class c, String method) {
      pWork(msg, getStringable(str), c, method, getLevelDefault(), false);
   }

   public void pWork(String msg, Object str, Class c, String method, int lvl, boolean oneLine) {
      super.ptPrint(msg, getStringable(str), c, method, ITechTags.STRING_06_WORK, ITechTags.FLAG_06_PRINT_WORK, lvl, oneLine);
   }

   //#mdebug
   public void toString(Dctx dc) {
      dc.root(this, BaseDLogger.class, 250);
      toStringPrivate(dc);
      super.toString(dc.sup());
   }

   public void toString1Line(Dctx dc) {
      dc.root1Line(this, BaseDLogger.class);
      toStringPrivate(dc);
      super.toString1Line(dc.sup1Line());
   }

   private void toStringPrivate(Dctx dc) {

   }
   //#enddebug
}
