package pasa.cbentley.core.src4.i8n;

import pasa.cbentley.core.src4.ctx.UCtx;
import pasa.cbentley.core.src4.logging.Dctx;
import pasa.cbentley.core.src4.logging.IStringable;

/**
 * Encapsulates a Local, simple name "English" and its suffix id "en"
 * 
 * @author Charles Bentley
 *
 */
public class LocaleID implements IStringable {

   private String name;

   private String suffix;

   private UCtx   uc;

   public LocaleID(UCtx uc, String name, String suffix) {
      this.uc = uc;
      this.name = name;
      this.suffix = suffix;
   }

   public String getName() {
      return name;
   }

   public String getSuffix() {
      return suffix;
   }

   //#mdebug
   public String toString() {
      return Dctx.toString(this);
   }

   public void toString(Dctx dc) {
      dc.root(this, "LocaleID");
   }

   public String toString1Line() {
      return Dctx.toString1Line(this);
   }

   public void toString1Line(Dctx dc) {
      dc.root1Line(this, "LocaleID");
      dc.append(name + " " + suffix);
   }

   public UCtx toStringGetUCtx() {
      return uc;
   }
   //#enddebug

}
