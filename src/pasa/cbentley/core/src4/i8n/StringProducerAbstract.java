/*
 * (c) 2018-2020 Charles-Philip Bentley
 * This code is licensed under MIT license (see LICENSE.txt for details)
 */
package pasa.cbentley.core.src4.i8n;

import pasa.cbentley.core.src4.ctx.UCtx;
import pasa.cbentley.core.src4.logging.Dctx;
import pasa.cbentley.core.src4.logging.IDLog;
import pasa.cbentley.core.src4.logging.IStringable;

/**
 * Base implementation of the {@link IStringProducer} contract.
 * 
 * Implementating code contexts 
 * 
 * @author Charles Bentley
 *
 */
public abstract class StringProducerAbstract implements IStringProducer, IStringable {

   protected LocaleID[] lids;

   protected LocaleID   current;

   protected final UCtx uc;

   /**
    * 
    * @param uc
    * @param ar
    */
   public StringProducerAbstract(UCtx uc, LocaleID[] ar) {
      this.uc = uc;
      lids = ar;
      setLocalID(ar[0]);
   }

   /**
    * The current Locale
    */
   public LocaleID getLocaleID() {
      return current;
   }

   public LocaleID getLocaleIDRoot() {
      return lids[0];
   }

   public LocaleID[] getLocaleIDs() {
      return lids;
   }

   public LocaleID getLocale(String suffix) {
      for (int i = 0; i < lids.length; i++) {
         if (lids[i].getSuffix() == suffix) {
            return lids[i];
         }
      }
      return null;
   }

   public UCtx getUCtx() {
      return uc;
   }

   /**
    * Called upon start of Appli or when Language is changed.
    * <br>
    */
   public void setLocalID(LocaleID lid) {
      if (lid == null) {
         throw new NullPointerException();
      }
      current = lid;
   }

   //#mdebug
   public String toString() {
      return Dctx.toString(this);
   }

   public UCtx toStringGetUCtx() {
      return uc;
   }

   public IDLog toDLog() {
      return uc.toDLog();
   }

   public String toString1Line() {
      return Dctx.toString1Line(this);
   }

   public void toString(Dctx dc) {
      dc.root(this, "StringProdBase");
      dc.nlLvl("CurrentLocale", current);
      dc.nlLvlArray1Line(lids, "Locales");
   }

   public void toString1Line(Dctx dc) {
      dc.root1Line(this, "StringProdBase");
      dc.append(" ");
      current.toString1Line(dc);
   }
   //#enddebug
}
