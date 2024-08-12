package pasa.cbentley.core.src4.ctx;

import pasa.cbentley.core.src4.logging.Dctx;
import pasa.cbentley.core.src4.logging.IDLog;
import pasa.cbentley.core.src4.logging.IStringable;

/**
 * 
 * @author Charles Bentley
 *
 */
public class ObjectU implements IStringable {

   //#debug
   protected String       toStringName;

   protected final UCtx uc;

   public ObjectU(UCtx uc) {
      this.uc = uc;
   }

   public UCtx getUC() {
      return uc;
   }

   //#mdebug
   public IDLog toDLog() {
      return toStringGetUCtx().toDLog();
   }

   public String toString() {
      return Dctx.toString(this);
   }

   public void toString(Dctx dc) {
      dc.root(this, ObjectU.class, "@line30");
      toStringPrivate(dc);
   }

   public String toString1Line() {
      return Dctx.toString1Line(this);
   }

   public void toString1Line(Dctx dc) {
      dc.root1Line(this, ObjectU.class);
      toStringPrivate(dc);
   }

   public UCtx toStringGetUCtx() {
      return uc;
   }

   private void toStringPrivate(Dctx dc) {
      if (toStringName != null) {
         dc.appendWithSpace(toStringName);
      }
   }

   public void toStringSetName(String name) {
      if (toStringName == null) {
         toStringName = name;
      } else {
         toStringName = toStringName + " - " + name;
      }
   }

   //#enddebug

}
