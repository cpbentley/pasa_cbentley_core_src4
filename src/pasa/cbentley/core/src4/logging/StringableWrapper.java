/*
 * (c) 2018-2020 Charles-Philip Bentley
 * This code is licensed under MIT license (see LICENSE.txt for details)
 */
package pasa.cbentley.core.src4.logging;

import pasa.cbentley.core.src4.ctx.ObjectU;
import pasa.cbentley.core.src4.ctx.UCtx;

public class StringableWrapper extends ObjectU implements IStringable {


   protected final Object o;

   public StringableWrapper(UCtx uc, Object o) {
      super(uc);
      if (o == null) {
         throw new NullPointerException();
      }
      this.o = o;
   }

   //#mdebug
   public void toString(Dctx dc) {
      if (o instanceof IStringable) {
         ((IStringable) o).toString(dc);
      } else {
         //ask bo module man
         boolean b = uc.getCtxManager().toString(dc, o);
         if (!b) {
            String str = o.toString();
            dc.append(str);
         }
      }
   }

   public void toString1Line(Dctx dc) {
      if (o instanceof IStringable) {
         ((IStringable) o).toString1Line(dc);
      } else {
         //ask bo module man
         boolean b = uc.getCtxManager().toString1Line(dc, o);
         if (!b) {
            String str = o.toString();
            dc.append(str);
         }
      }
   }
   //#enddebug

}
