/*
 * (c) 2018-2020 Charles-Philip Bentley
 * This code is licensed under MIT license (see LICENSE.txt for details)
 */
package pasa.cbentley.core.src4.logging;

import pasa.cbentley.core.src4.ctx.UCtx;

public class LogAdapter implements IDLog {

   //#mdebug
   protected final UCtx uc;

   public LogAdapter(UCtx uc) {
      this.uc = uc;

   }

   public DLogConfig getConfig() {
      throw new RuntimeException("not implemented yet");
   }

   public long getCount() {
      return 0;
   }

   public ILogEntryAppender getDefault() {
      throw new RuntimeException("not implemented yet");
   }

   public void methodEnd(Class c, String method, int lvl) {
      throw new RuntimeException("not implemented yet");
   }

   public void methodStart(Class c, String method, int lvl) {
      throw new RuntimeException("not implemented yet");
   }

   public void pAlways(String msg, IStringable str, Class c, String method) {
      throw new RuntimeException("not implemented yet");

   }

   public void pAlways(String msg, IStringable str, Class c, String method, int lvl, boolean oneLine) {
      throw new RuntimeException("not implemented yet");

   }

   public void pAnim(String msg, IStringable str, Class c, String method) {
      throw new RuntimeException("not implemented yet");

   }

   public void pAnim(String msg, IStringable str, Class c, String method, int lvl, boolean oneLine) {
      throw new RuntimeException("not implemented yet");

   }

   public void pBridge(String msg, IStringable str, Class c, String method) {
      throw new RuntimeException("not implemented yet");

   }

   public void pBridge(String msg, IStringable str, Class c, String method, int lvl, boolean oneLine) {
      throw new RuntimeException("not implemented yet");

   }

   public void pBridge1(String msg, IStringable str, Class c, String method) {
      throw new RuntimeException("not implemented yet");
   }

   public void pCmd(String msg, IStringable str, Class c, String method) {
      throw new RuntimeException("not implemented yet");

   }

   public void pCmd(String msg, IStringable str, Class c, String method, int lvl, boolean oneLine) {
      throw new RuntimeException("not implemented yet");

   }

   public void pCmd1(String msg, IStringable str, Class c, String method) {
      throw new RuntimeException("not implemented yet");
   }

   public void pData(String msg, IStringable str, Class c, String method) {
      throw new RuntimeException("not implemented yet");
   }

   public void pData(String msg, IStringable str, Class c, String method, int lvl, boolean oneLine) {
      throw new RuntimeException("not implemented yet");

   }

   public void pData(String msg, IStringable str, Class c, String method, int lvl, boolean oneLine, Exception e) {
      throw new RuntimeException("not implemented yet");

   }

   public void pDraw(String msg, IStringable str, Class c, String method) {
      throw new RuntimeException("not implemented yet");

   }

   public void pDraw(String msg, IStringable str, Class c, String method, int lvl, boolean oneLine) {
      throw new RuntimeException("not implemented yet");

   }

   public void pEvent(String msg, IStringable str, Class c, String method) {
      throw new RuntimeException("not implemented yet");

   }

   public void pEvent(String msg, IStringable str, Class c, String method, int lvl, boolean oneLine) {
      throw new RuntimeException("not implemented yet");

   }

   public void pEvent(String msg, IStringable str, Class c, String method, int lvl, int flags) {
      throw new RuntimeException("not implemented yet");

   }

   public void pEvent1(String msg, IStringable str, Class c, String method) {
      throw new RuntimeException("not implemented yet");

   }

   public void pEventFine(String msg, IStringable str, Class c, String method) {
      throw new RuntimeException("not implemented yet");

   }

   public void pEventFiner(String msg, IStringable str, Class c, String method) {
      throw new RuntimeException("not implemented yet");

   }

   public void pEventFinest(String msg, IStringable str, Class c, String method) {
      throw new RuntimeException("not implemented yet");

   }

   public void pEventInfo(String msg, IStringable str, Class c, String method) {
      throw new RuntimeException("not implemented yet");

   }

   public void pEventSevere(String msg, IStringable str, Class c, String method) {
      throw new RuntimeException("not implemented yet");

   }

   public void pEventWarn(String msg, IStringable str, Class c, String method) {
      throw new RuntimeException("not implemented yet");

   }

   public void pEx(String msg, IStringable str, Class c, String method, Exception e) {
      throw new RuntimeException("not implemented yet");

   }

   public void pFlag(int flag, String msg, IStringable str, Class c, String method, int lvl, boolean oneLine) {
      throw new RuntimeException("not implemented yet");
   }

   public void pFlow(String msg, IStringable str, Class c, String method) {
      throw new RuntimeException("not implemented yet");

   }

   public void pFlow(String msg, IStringable str, Class c, String method, int lvl, boolean oneLine) {
      throw new RuntimeException("not implemented yet");

   }

   public void pFlow(String msg, IStringable str, Class c, String method, int lvl, int flags) {
      throw new RuntimeException("not implemented yet");
   }

   public void pFlowBig(String msg, IStringable str, Class c, String method) {
      throw new RuntimeException("not implemented yet");
   }

   public void pInit(String msg, IStringable str, Class c, String method) {
      throw new RuntimeException("not implemented yet");

   }

   public void pInit(String msg, IStringable str, Class c, String method, int lvl, boolean oneLine) {
      throw new RuntimeException("not implemented yet");

   }

   public void pInit1(String msg, IStringable str, Class c, String method) {
      throw new RuntimeException("not implemented yet");
   }

   public void pMemory(String msg, IStringable str, Class c, String method) {
      throw new RuntimeException("not implemented yet");

   }

   public void pMemory(String msg, IStringable str, Class c, String method, int lvl, boolean oneLine) {
      throw new RuntimeException("not implemented yet");

   }

   public void pMemoryWarn(String msg, IStringable str, Class c, String method) {
      throw new RuntimeException("not implemented yet");

   }

   public void pModel(String msg, IStringable str, Class c, String method) {
      throw new RuntimeException("not implemented yet");

   }

   public void pModel(String msg, IStringable str, Class c, String method, int lvl, boolean oneLine) {
      throw new RuntimeException("not implemented yet");

   }

   public void pModel1(String msg, IStringable str, Class c, String method) {
      throw new RuntimeException("not implemented yet");
   }

   public void pNull(String msg, IStringable str, Class c, String method) {
      throw new RuntimeException("not implemented yet");
   }

   public void pNull(String msg, IStringable str, Class c, String method, int lvl, boolean oneLine) {
      throw new RuntimeException("not implemented yet");
   }

   public void pSound(String msg, IStringable str, Class c, String method) {
      throw new RuntimeException("not implemented yet");

   }

   public void pSound(String msg, IStringable str, Class c, String method, int lvl, boolean oneLine) {
      throw new RuntimeException("not implemented yet");

   }

   public void pSound1(String msg, IStringable str, Class c, String method) {
      throw new RuntimeException("not implemented yet");

   }

   public void pState(String msg, IStringable str, Class c, String method) {
      throw new RuntimeException("not implemented yet");

   }

   public void pState(String msg, IStringable str, Class c, String method, int lvl, boolean oneLine) {
      throw new RuntimeException("not implemented yet");

   }

   public void pStator(String msg, IStringable str, Class c, String method) {
      throw new RuntimeException("not implemented yet");
   }

   public void pStator(String msg, IStringable str, Class c, String method, int lvl, boolean oneLine) {
      throw new RuntimeException("not implemented yet");
   }

   public void pTest(String msg, IStringable str, Class c, String method) {
      throw new RuntimeException("not implemented yet");

   }

   public void pTest(String msg, IStringable str, Class c, String method, int lvl, boolean oneLine) {
      throw new RuntimeException("not implemented yet");

   }

   public void pTest1(String msg, IStringable str, Class c, String method) {
      throw new RuntimeException("not implemented yet");

   }

   public void pUI(String msg, IStringable str, Class c, String method) {
      throw new RuntimeException("not implemented yet");

   }

   public void pUI(String msg, IStringable str, Class c, String method, int lvl, boolean oneLine) {
      throw new RuntimeException("not implemented yet");

   }

   public void pWork(String msg, IStringable str, Class c, String method) {
      throw new RuntimeException("not implemented yet");

   }

   public void pWork(String msg, IStringable str, Class c, String method, int lvl, boolean oneLine) {
      throw new RuntimeException("not implemented yet");

   }

   public IDLog toDLog() {
      return toStringGetUCtx().toDLog();
   }

   public String toString() {
      return Dctx.toString(this);
   }

   public void toString(Dctx dc) {
      dc.root(this, LogAdapter.class, "@line5");
      toStringPrivate(dc);
   }

   public String toString1Line() {
      return Dctx.toString1Line(this);
   }

   public void toString1Line(Dctx dc) {
      dc.root1Line(this, LogAdapter.class);
      toStringPrivate(dc);
   }

   public UCtx toStringGetUCtx() {
      return uc;
   }

   private void toStringPrivate(Dctx dc) {

   }

   //#enddebug
}
