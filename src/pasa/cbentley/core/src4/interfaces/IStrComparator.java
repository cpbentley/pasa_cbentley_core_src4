package pasa.cbentley.core.src4.interfaces;

import pasa.cbentley.core.src4.ctx.UCtx;

/**
 * 
 * @author Charles Bentley
 *
 */
public interface IStrComparator {

   /**
    * <li>-1 when source is smaller
    * <li>1 when source is bigger
    * <li>0 when equal
    * @param source
    * @param target
    * @return
    */
   public int compare(String source, String target);

   /**
    * <li>-1 when source is smaller
    * <li>1 when source is bigger
    * <li>0 when equal
    * @param source
    * @param target
    * @return
    */
   public int compareChar(char source, char target);

   /**
    * Test whether test can be humanly considered with the same
    * high level semantic meaning
    * ie Clothe, clothes
    * phone, Phones
    * <br>
    * <br>
    * Implementation will vary depending on the language. You would then create a new {@link UCtx} with a different {@link IStrComparator}.
    * @param source
    * @param test
    * @return true if similar according to implementation
    */
   public boolean isSimilar(String source, String test);
}
