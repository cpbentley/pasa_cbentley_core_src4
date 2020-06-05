/*
 * (c) 2018-2020 Charles-Philip Bentley
 * This code is licensed under MIT license (see LICENSE.txt for details)
 */
package pasa.cbentley.core.src4.event;

import pasa.cbentley.core.src4.ctx.UCtx;
import pasa.cbentley.core.src4.logging.Dctx;

public abstract class EventConsumerAdapter implements IEventConsumer {

   private UCtx uc;

   public EventConsumerAdapter(UCtx uc) {
      this.uc = uc;
   }

   //#mdebug
   public String toString() {
      return Dctx.toString(this);
   }

   public void toString(Dctx dc) {
      dc.root(this, "EventConsumerAdapter");
      toStringPrivate(dc);
   }

   public String toString1Line() {
      return Dctx.toString1Line(this);
   }

   private void toStringPrivate(Dctx dc) {

   }

   public void toString1Line(Dctx dc) {
      dc.root1Line(this, "EventConsumerAdapter");
      toStringPrivate(dc);
   }

   public UCtx toStringGetUCtx() {
      return uc;
   }
   //#enddebug

}
