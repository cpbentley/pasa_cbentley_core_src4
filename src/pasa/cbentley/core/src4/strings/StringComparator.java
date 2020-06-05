/*
 * (c) 2018-2020 Charles-Philip Bentley
 * This code is licensed under MIT license (see LICENSE.txt for details)
 */
package pasa.cbentley.core.src4.strings;

import pasa.cbentley.core.src4.ctx.UCtx;
import pasa.cbentley.core.src4.interfaces.IStrComparator;

/**
 * Default string comparators for all classes.
 * <br>
 * <br>
 * Change it with {@link UCtx#setStrComparator}
 * @author Charles Bentley
 *
 */
public class StringComparator implements IStrComparator {
   
   private UCtx uc;

   public StringComparator(UCtx uc) {
      this.uc = uc;
   }

   public int compare(String source, String target) {
      return uc.getStrU().compare(source, target);
   }

   public int compareChar(char source, char target) {
      return uc.getCU().compareChar(source, target);
   }

   public boolean isSimilar(String source, String test) {
      return uc.getStrU().isSimilar(source, test);
   }

   public boolean isEqual(String source, String test) {
      return source.equals(test);
   }
}
