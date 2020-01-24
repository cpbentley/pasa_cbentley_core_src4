package pasa.cbentley.core.src4.structs.decisiontree;

import pasa.cbentley.core.src4.ctx.UCtx;
import pasa.cbentley.core.src4.utils.IntUtils;

/**
 * Get the intersection/union of two int[] arrays.
 * <br>
 * <br>
 * Int array represents database ids of {@link ByteObject}.
 * <br>
 * You want all Buys from last Month with Context = 15. this is an intersection of 2 searches.
 * <br>
 * <br>
 * Use the TimeIndex to read only Buys from last month, then for each, check Context ID
 * <br>
 * <br>
 * Or if Buys are indexed on Context, get all BIDs with Context 15 and check time of those buys.
 * <br>
 * <br>
 * 
 * @author Charles-Philip Bentley
 *
 */
public class IntArrayTreeResolver {

   private int[][]      rids;

   protected final UCtx uc;

   /**
    * 
    * @param rids
    */
   public IntArrayTreeResolver(UCtx uc, int[][] rids) {
      this.uc = uc;
      this.rids = rids;
   }

   public int[] resolveInArray(int left, int op, int right) {
      return resolveInArray(rids[left], op, rids[right]);
   }

   public int[] resolveInArray(int[] left, int op, int right) {
      return resolveInArray(left, op, rids[right]);
   }

   public int[] resolveInArray(int left, int op, int[] right) {
      return resolveInArray(rids[left], op, right);
   }

   public int[] resolveInArray(int[] left, int op, int[] right) {
      IntUtils iu = uc.getIU();
      if (op == BooleanTreeResolver.OP_AND)
         return iu.getIntersection(left, right);
      else {
         return iu.getUnion(left, right);
      }
   }

   public int[] resolve(int index) {
      return rids[index];
   }
}
