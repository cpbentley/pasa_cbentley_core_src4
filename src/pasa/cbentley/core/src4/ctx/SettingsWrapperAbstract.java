/*
 * (c) 2018-2020 Charles-Philip Bentley
 * This code is licensed under MIT license (see LICENSE.txt for details)
 */
package pasa.cbentley.core.src4.ctx;

import pasa.cbentley.core.src4.logging.Dctx;
import pasa.cbentley.core.src4.logging.IDLog;
import pasa.cbentley.core.src4.logging.IStringable;

public abstract class SettingsWrapperAbstract implements IStringable {

   protected final ICtx ctx;

   public SettingsWrapperAbstract(ICtx ctx) {
      this.ctx = ctx;
   }

   //#mdebug
   public IDLog toDLog() {
      return toStringGetUCtx().toDLog();
   }

   public String toString() {
      return Dctx.toString(this);
   }

   public void toString(Dctx dc) {
      dc.root(this, SettingsWrapperAbstract.class);
      toStringPrivate(dc);
   }

   public String toString1Line() {
      return Dctx.toString1Line(this);
   }

   private void toStringPrivate(Dctx dc) {

   }

   public void toString1Line(Dctx dc) {
      dc.root1Line(this, SettingsWrapperAbstract.class);
      toStringPrivate(dc);
   }

   public UCtx toStringGetUCtx() {
      return ctx.toStringGetUCtx();
   }

   //#enddebug

}
