/*
 * (c) 2018-2020 Charles-Philip Bentley
 * This code is licensed under MIT license (see LICENSE.txt for details)
 */
package pasa.cbentley.core.src4.structs;

import pasa.cbentley.core.src4.ctx.UCtx;
import pasa.cbentley.core.src4.interfaces.IStrComparator;

/**
 * 
 * @author Charles Bentley
 *
 */
public class SorterString {

   private boolean        asc;

   private IStrComparator comparator;

   protected final UCtx   uc;

   public SorterString(UCtx uc) {
      this.uc = uc;
      comparator = uc.getStrComparator();
   }

   public IStrComparator getComparator() {
      return comparator;
   }

   public boolean isAscending() {
      return asc;
   }

   public void setAscending() {
      asc = true;
   }

   public void setComparator(IStrComparator comparator) {
      this.comparator = comparator;
   }

   public void setDescending() {
      asc = false;
   }

   public void sortArray(String[] strings, int offset, int len) {
      String tempstr = "";
      if (strings.length == 1)
         return;
      int val = 0;
      IStrComparator strc = uc.getStrComparator();
      int end = offset + len;
      for (int i = offset; i < end; i++) {
         for (int j = i + 1; j < end; j++) {
            //-1 if i < j
            val = strc.compare(strings[i], strings[j]);
            if ((asc && val > 0) || (!asc && val < 0)) {
               tempstr = strings[i];
               strings[i] = strings[j];
               strings[j] = tempstr;
            }
         }
      }
   }

}
