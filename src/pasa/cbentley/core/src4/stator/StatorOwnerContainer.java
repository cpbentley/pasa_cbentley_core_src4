package pasa.cbentley.core.src4.stator;

import pasa.cbentley.core.src4.ctx.ICtx;
import pasa.cbentley.core.src4.ctx.ObjectU;
import pasa.cbentley.core.src4.ctx.UCtx;

/**
 * State in a ... for later use
 *  
 * @author Charles Bentley
 *
 */
public class StatorOwnerContainer extends ObjectU implements IStatorOwner {

   int                  offset;

   int                  len;

   byte[]               array;

   private StatorReader reader;

   public StatorOwnerContainer(UCtx uc) {
      super(uc);
   }

   public void stateOwnerWrite(Stator stator) {
   }

   public void stateOwnerRead(Stator stator) {
      //save the reader for later use
      reader = stator.getActiveReader();
   }

   public StatorReader getReader() {
      return reader;
   }

   public ICtx getCtxOwner() {
      return uc;
   }

   public Stator activateReader() {
      Stator stator = reader.getStator();
      stator.setActiveReader(reader);
      return stator;
   }

}
