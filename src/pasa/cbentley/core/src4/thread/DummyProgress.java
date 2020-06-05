/*
 * (c) 2018-2020 Charles-Philip Bentley
 * This code is licensed under MIT license (see LICENSE.txt for details)
 */
package pasa.cbentley.core.src4.thread;

import pasa.cbentley.core.src4.ctx.UCtx;
import pasa.cbentley.core.src4.logging.Dctx;

/**
 * To avoid null checks
 * @author Charles Bentley
 *
 */
public class DummyProgress implements IBProgessable {

   private UCtx uc;

   public DummyProgress(UCtx uc) {
      this.uc = uc;

   }

   public String getTitle() {
      return "";
   }

   public void setTitle(String title) {

   }

   public void setMaxValue(int mv) {

   }

   public void set(String title, String info, String label, int maxValue, int level) {

   }

   public void error(String msg) {

   }

   public void setLabel(String s) {

   }

   public int getLvl() {
      return 0;
   }

   public void setLvl(int lvl) {
   }

   public void setValue(int value) {

   }

   public void setInfo(String info) {

   }

   public void increment(int value) {
   }

   public IBProgessable getChild() {
      return this;
   }

   public boolean isCanceled() {
      return false;
   }

   public void close(String msg) {

   }

   public IBProgessable getChild(IBRunnable runnable) {
      return this;
   }

   public void newRunnableState(int state) {
   }

   public void setRunnable(IBRunnable runner) {
   }

   public void close() {

   }

   public void setTimeLeft(long value) {

   }

   //#mdebug
   public String toString() {
      return Dctx.toString(this);
   }

   public void toString(Dctx dc) {
      dc.root(this, "DummyProgress");
   }

   public String toString1Line() {
      return Dctx.toString1Line(this);
   }

   public void toString1Line(Dctx dc) {
      dc.root1Line(this, "DummyProgress");
   }

   public UCtx toStringGetUCtx() {
      return uc;
   }
   //#enddebug

}
