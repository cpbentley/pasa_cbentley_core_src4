package pasa.cbentley.core.src4.structs.listdoublelink;

import pasa.cbentley.core.src4.logging.Dctx;
import pasa.cbentley.core.src4.logging.IStringable;

public class ListElementHolder extends ListElement {

   protected Object o;

   public ListElementHolder(LinkedListDouble rn, Object o) {
      super(rn);
      this.o = o;
   }

   public Object getObject() {
      return o;
   }

   public void setObject(Object o) {
      this.o = o;
   }

   //#mdebug
   public void toString(Dctx dc) {
      dc.root(this, "ListElementHolder");
      if(o instanceof IStringable) {
         IStringable is = (IStringable)o;
         dc.nlLvl(is);
      }
   }

   public void toString1Line(Dctx dc) {
      dc.root1Line(this, "ListElementHolder");
      if(o instanceof IStringable) {
         IStringable is = (IStringable)o;
         dc.nlLvlOneLine(is, "");
      }
   }
   //#enddebug
}
