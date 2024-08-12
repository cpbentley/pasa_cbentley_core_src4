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

   public void pAnim(String msg, IStringable str, Class c, String method) {
      pAnim(msg, str, c, method, getLevelDefault(), false);
   }

   public void pAnim(String msg, IStringable str, Class c, String method, int lvl, boolean oneLine) {
      super.ptPrint(msg, str, c, method, ITechTags.STRING_23_ANIM, ITechTags.FLAG_23_PRINT_ANIM, lvl, oneLine);
   }

   public void pBridge(String msg, IStringable str, Class c, String method) {
      pBridge(msg, str, c, method, getLevelDefault(), false);
   }

   public void pBridge(String msg, IStringable str, Class c, String method, int lvl, boolean oneLine) {
      super.ptPrint(msg, str, c, method, ITechTags.STRING_19_BRIDGE, ITechTags.FLAG_19_PRINT_BRIDGE, lvl, oneLine);
   }

   public void pBridge1(String msg, IStringable str, Class c, String method) {
      pBridge(msg, str, c, method, getLevelDefault(), true);
   }

   public void pBusiness(String msg, IStringable str, Class c, String method, int lvl, boolean oneLine) {
      super.ptPrint(msg, str, c, method, ITechTags.STRING_12_BUSINESS, ITechTags.FLAG_12_PRINT_BUSINESS, lvl, oneLine);
   }

   public void pBusiness(String msg, IStringable str, Class c, String method, int lvl, int flags) {
      super.ptPrint(msg, str, c, method, ITechTags.STRING_12_BUSINESS, ITechTags.FLAG_12_PRINT_BUSINESS, lvl, flags);
   }

   public void pBusiness1(String msg, IStringable str, Class c, String method) {
      pCmd(msg, str, c, method, getLevelDefault(), true);
   }

   public void pCmd(String msg, IStringable str, Class c, String method) {
      pCmd(msg, str, c, method, getLevelDefault(), false);
   }

   public void pCmd(String msg, IStringable str, Class c, String method, int lvl, boolean oneLine) {
      super.ptPrint(msg, str, c, method, ITechTags.STRING_11_CMD, ITechTags.FLAG_11_PRINT_COMMANDS, lvl, oneLine);
   }

   public void pCmd1(String msg, IStringable str, Class c, String method) {
      pCmd(msg, str, c, method, getLevelDefault(), true);
   }

   public void pData(String msg, IStringable str, Class c, String method) {
      pData(msg, str, c, method, getLevelDefault(), false);
   }

   public void pData(String msg, IStringable str, Class c, String method, int lvl, boolean oneLine) {
      super.ptPrint(msg, str, c, method, ITechTags.STRING_15_DATA, ITechTags.FLAG_15_PRINT_DATA, lvl, oneLine);
   }

   public void pData(String msg, IStringable str, Class c, String method, int lvl, boolean oneLine, Exception e) {
      super.ptPrint(msg, str, c, method, ITechTags.STRING_15_DATA, ITechTags.FLAG_15_PRINT_DATA, lvl, oneLine);
      e.printStackTrace();
   }

   public void pDraw(String msg, IStringable str, Class c, String method) {
      pDraw(msg, str, c, method, getLevelDefault(), false);
   }

   public void pDraw(String msg, IStringable str, Class c, String method, int lvl, boolean oneLine) {
      super.ptPrint(msg, str, c, method, ITechTags.STRING_24_DRAW, ITechTags.FLAG_24_PRINT_DRAW, lvl, oneLine);
   }

   public void pEvent(String msg, IStringable str, Class c, String method) {
      pEvent(msg, str, c, method, getLevelDefault(), false);
   }

   public void pEvent(String msg, IStringable str, Class c, String method, int lvl) {
      pEvent(msg, str, c, method, lvl, false);
   }

   public void pEvent(String msg, IStringable str, Class c, String method, int lvl, boolean oneLine) {
      super.ptPrint(msg, str, c, method, ITechTags.STRING_07_EVENT, ITechTags.FLAG_07_PRINT_EVENT, lvl, oneLine);
   }

   public void pEvent(String msg, IStringable str, Class c, String method, int lvl, int flags) {
      super.ptPrint(msg, str, c, method, ITechTags.STRING_07_EVENT, ITechTags.FLAG_07_PRINT_EVENT, lvl, flags);
   }

   /**
    * Express cue to only print 1 line by default level defined by the {@RootDLogger} .
    * @param msg
    * @param str
    * @param c
    * @param method
    * @param lvl
    */
   public void pEvent1(String msg, IStringable str, Class c, String method) {
      pEvent(msg, str, c, method, getLevelDefault(), true);
   }

   public void pEventFine(String msg, IStringable str, Class c, String method) {
      pEvent(msg, str, c, method, ITechLvl.LVL_05_FINE);
   }

   public void pEventFiner(String msg, IStringable str, Class c, String method) {
      pEvent(msg, str, c, method, ITechLvl.LVL_04_FINER);
   }

   public void pEventFinest(String msg, IStringable str, Class c, String method) {
      pEvent(msg, str, c, method, ITechLvl.LVL_03_FINEST);
   }

   public void pEventInfo(String msg, IStringable str, Class c, String method) {
      pEvent(msg, str, c, method, ITechLvl.LVL_08_INFO);
   }

   public void pEventSevere(String msg, IStringable str, Class c, String method) {
      pEvent(msg, str, c, method, ITechLvl.LVL_10_SEVERE);
   }

   public void pEventWarn(String msg, IStringable str, Class c, String method) {
      pEvent(msg, str, c, method, ITechLvl.LVL_07_CONFIG);
   }

   public void pEx(String msg, IStringable str, Class c, String method, Exception e) {
      pEx(msg, str, c, method, getLevelDefault(), false, e);
   }

   public void pEx(String msg, IStringable str, Class c, String method, int lvl, boolean oneLine, Exception e) {
      super.ptPrint(msg, str, c, method, ITechTags.STRING_08_EX, ITechTags.FLAG_08_PRINT_EXCEPTION, lvl, oneLine);
      e.printStackTrace();
   }

   public void pFlag(int tagID, String msg, IStringable str, Class c, String method, int lvl, boolean oneLine) {
      this.ptPrint(msg, str, c, method, "FLAG", tagID, lvl, oneLine);
   }

   public void pFlow(String msg, IStringable str, Class c, String method) {
      pFlow(msg, str, c, method, getLevelDefault(), false);
   }

   public void pFlow(String msg, IStringable str, Class c, String method, int lvl, boolean oneLine) {
      super.ptPrint(msg, str, c, method, ITechTags.STRING_09_FLOW, ITechTags.FLAG_09_PRINT_FLOW, lvl, oneLine);
   }

   public void pFlow(String msg, IStringable str, Class c, String method, int lvl, int flags) {
      super.ptPrint(msg, str, c, method, ITechTags.STRING_09_FLOW, ITechTags.FLAG_09_PRINT_FLOW, lvl, flags);
   }

   public void pFlowBig(String msg, IStringable str, Class c, String method) {
      super.ptPrintBig(msg, str, c, method, ITechTags.STRING_09_FLOW, ITechTags.FLAG_09_PRINT_FLOW, getLevelDefault());
   }

   public void pInit(String msg, IStringable str, Class c, String method) {
      pInit(msg, str, c, method, getLevelDefault(), false);
   }

   public void pInit(String msg, IStringable str, Class c, String method, int lvl, boolean oneLine) {
      super.ptPrint(msg, str, c, method, ITechTags.STRING_20_INIT, ITechTags.FLAG_20_PRINT_INIT, lvl, oneLine);
   }

   public void pInit(String msg, IStringable str, Class c, String method, int lvl, int flags) {
      super.ptPrint(msg, str, c, method, ITechTags.STRING_20_INIT, ITechTags.FLAG_20_PRINT_INIT, lvl, flags);
   }

   public void pInit1(String msg, IStringable str, Class c, String method) {
      pInit(msg, str, c, method, getLevelDefault(), true);
   }

   public void pInitBig(String msg, IStringable str, Class c, String method) {
      super.ptPrintBig(msg, str, c, method, ITechTags.STRING_20_INIT, ITechTags.FLAG_20_PRINT_INIT, getLevelDefault());
   }

   public void pMemory(String msg, IStringable str, Class c, String method) {
      pMemory(msg, str, c, method, getLevelDefault(), false);
   }

   public void pMemory(String msg, IStringable str, Class c, String method, int lvl, boolean oneLine) {
      super.ptPrint(msg, str, c, method, ITechTags.STRING_18_MEMORY, ITechTags.FLAG_18_PRINT_MEMORY, lvl, oneLine);

   }

   public void pMemoryWarn(String msg, IStringable str, Class c, String method) {
      super.ptPrint(msg, str, c, method, ITechTags.STRING_18_MEMORY, ITechTags.FLAG_18_PRINT_MEMORY, ITechLvl.LVL_09_WARNING, false);
   }

   public void pModel(String msg, IStringable str, Class c, String method) {
      pModel(msg, str, c, method, getLevelDefault(), false);
   }

   public void pModel(String msg, IStringable str, Class c, String method, int lvl, boolean oneLine) {
      super.ptPrint(msg, str, c, method, ITechTags.STRING_10_MODEL, ITechTags.FLAG_10_PRINT_MODEL, lvl, oneLine);
   }

   public void pModel1(String msg, IStringable str, Class c, String method) {
      pModel(msg, str, c, method, getLevelDefault(), true);
   }

   public void pNull(String msg, IStringable str, Class c, String method) {
      pNull(msg, str, c, method, getLevelDefault(), false);
   }

   public void pNull(String msg, IStringable str, Class c, String method, int lvl, boolean oneLine) {
      super.ptPrint(msg, str, c, method, ITechTags.STRING_02_NULL, ITechTags.FLAG_02_PRINT_NULL, lvl, oneLine);
   }

   public void pSound(String msg, IStringable str, Class c, String method) {
      pSound(msg, str, c, method, getLevelDefault(), false);
   }

   public void pSound(String msg, IStringable str, Class c, String method, int lvl, boolean oneLine) {
      super.ptPrint(msg, str, c, method, ITechTags.STRING_13_SOUND, ITechTags.FLAG_13_PRINT_SOUND, lvl, oneLine);
   }

   public void pSound1(String msg, IStringable str, Class c, String method) {
      pSound(msg, str, c, method, getLevelDefault(), true);
   }

   public void pSoundEvent(String msg, IStringable str, Class c, String method, int lvl, boolean oneLine) {
      int tagId = ITechTags.FLAG_13_PRINT_SOUND | ITechTags.FLAG_07_PRINT_EVENT;
      String tagStr = ITechTags.STRING_13_SOUND + ITechTags.STRING_07_EVENT;
      super.ptPrint(msg, str, c, method, tagStr, tagId, lvl, oneLine);

   }

   public void pState(String msg, IStringable str, Class c, String method) {
      pState(msg, str, c, method, getLevelDefault(), false);
   }

   public void pState(String msg, IStringable str, Class c, String method, int lvl, boolean oneLine) {
      super.ptPrint(msg, str, c, method, ITechTags.STRING_22_STATE, ITechTags.FLAG_22_PRINT_STATE, lvl, oneLine);
   }

   public void pStator(String msg, IStringable str, Class c, String method) {
      pStator(msg, str, c, method, getLevelDefault(), false);
   }

   public void pStator(String msg, IStringable str, Class c, String method, int lvl, boolean oneLine) {
      super.ptPrint(msg, str, c, method, ITechTags.STRING_04_STATOR, ITechTags.FLAG_04_PRINT_STATOR, lvl, oneLine);
   }

   public void pTest(String msg, IStringable str, Class c, String method) {
      this.pTest(msg, str, c, method, getLevelDefault(), false);
   }

   public void pTest(String msg, IStringable str, Class c, String method, int lvl, boolean oneLine) {
      super.ptPrint(msg, str, c, method, ITechTags.STRING_17_TEST, ITechTags.FLAG_17_PRINT_TEST, lvl, oneLine);
   }

   public void pTest1(String msg, IStringable str, Class c, String method) {
      pTest(msg, str, c, method, getLevelDefault(), true);
   }

   public void pUI(String msg, IStringable str, Class c, String method) {
      pUI(msg, str, c, method, getLevelDefault(), false);
   }

   public void pUI(String msg, IStringable str, Class c, String method, int lvl, boolean oneLine) {
      super.ptPrint(msg, str, c, method, ITechTags.STRING_05_UI, ITechTags.FLAG_05_PRINT_UI, lvl, oneLine);
   }

   public void pWork(String msg, IStringable str, Class c, String method) {
      pWork(msg, str, c, method, getLevelDefault(), false);
   }

   public void pWork(String msg, IStringable str, Class c, String method, int lvl, boolean oneLine) {
      super.ptPrint(msg, str, c, method, ITechTags.STRING_06_WORK, ITechTags.FLAG_06_PRINT_WORK, lvl, oneLine);
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
