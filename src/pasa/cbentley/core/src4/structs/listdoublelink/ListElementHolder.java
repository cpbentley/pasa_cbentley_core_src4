package pasa.cbentley.core.src4.structs.listdoublelink;

import pasa.cbentley.core.src4.logging.Dctx;
import pasa.cbentley.core.src4.logging.IStringable;

/**
 * When you want to hold an element using composition instead of inheritance
 * 
 * @author Charles Bentley
 *
 */
public class ListElementHolder extends ListElement {

   protected Object o;

   /**
    * 
    * @param rn
    * @param o
    */
   public ListElementHolder(LinkedListDouble rn, Object o) {
      super(rn);
      this.o = o;
   }

   /**
    * The object holded
    * @return
    */
   public Object getObject() {
      return o;
   }

   /**
    * 
    * @param o
    */
   public void setObject(Object o) {
      this.o = o;
   }

   //#mdebug
   public void toString(Dctx dc) {
      dc.root(this, "ListElementHolder");
      if (o instanceof IStringable) {
         IStringable is = (IStringable) o;
         dc.nlLvl(is);
      }
   }

   public void toString1Line(Dctx dc) {
      dc.root1Line(this, "ListElementHolder");
      if (o instanceof IStringable) {
         IStringable is = (IStringable) o;
         dc.nlLvl1Line(is, "");
      }
   }
   //#enddebug
}
