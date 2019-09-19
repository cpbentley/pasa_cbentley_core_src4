package pasa.cbentley.core.src4.helpers;

import pasa.cbentley.core.src4.ctx.UCtx;
import pasa.cbentley.core.src4.logging.Dctx;
import pasa.cbentley.core.src4.logging.IDLog;
import pasa.cbentley.core.src4.logging.IStringable;

/**
 * Helper class to handle i8n strings
 * 
 * @author Charles Bentley
 *
 */
public class StringParametrized implements IStringable {

   protected final UCtx   uc;

   private String         root;

   private StringBBuilder sb;

   public StringParametrized(UCtx uc) {
      this.uc = uc;
      sb = new StringBBuilder(uc);
   }

   /**
    * The current representation with current parameters
    * @return
    */
   public String getString() {
      return sb.toString();
   }

   /**
    * Replace the all instances of the parameter with its value
    * @param param
    * @param value
    */
   public void setParam(String param, String value) {
      sb.replaceAll(param, value);
   }

   /**
    * 
    * @param param
    * @param value
    */
   public void setParam(String param, int value) {
      this.setParam(param, String.valueOf(value));
   }

   /**
    * The original string that was set
    * @return
    */
   public String getRoot() {
      return root;
   }

   /**
    * Resets the string to this new str
    * @param str
    */
   public void setString(String str) {
      this.root = str;
      sb.reset();
      sb.append(str);
   }

   //#mdebug
   public IDLog toDLog() {
      return toStringGetUCtx().toDLog();
   }

   public String toString() {
      return Dctx.toString(this);
   }

   public void toString(Dctx dc) {
      dc.root(this, "StringParametrized");
      toStringPrivate(dc);
   }

   public String toString1Line() {
      return Dctx.toString1Line(this);
   }

   private void toStringPrivate(Dctx dc) {

   }

   public void toString1Line(Dctx dc) {
      dc.root1Line(this, "StringParametrized");
      toStringPrivate(dc);
   }

   public UCtx toStringGetUCtx() {
      return uc;
   }

   //#enddebug

}
