/*
 * (c) 2018-2020 Charles-Philip Bentley
 * This code is licensed under MIT license (see LICENSE.txt for details)
 */
package pasa.cbentley.core.src4.event;

import pasa.cbentley.core.src4.ctx.UCtx;

public class EventParam {

   protected final UCtx     uc;

   protected final BusEvent be;

   private boolean          valueWas;

   private boolean          valueIs;

   public EventParam(UCtx uc, BusEvent be) {
      this.uc = uc;
      this.be = be;
   }

   public void setWasIs(boolean was, boolean is) {
      this.valueIs = is;
      this.valueWas = was;
   }

   public boolean getValueIs() {
      return valueWas;
   }

   public boolean getValueWas() {
      return valueWas;
   }
}
