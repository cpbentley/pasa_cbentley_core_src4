package pasa.cbentley.core.src4.structs.decisiontree;

/**
 * 
 * @author Charles-Philip Bentley
 *
 */
public class BooleanTreeResolver {

   public static final int OP_AND = 0;

   public static final int OP_OR  = 1;

   public static boolean booleanAction(boolean b1, int op, boolean b2) {
      switch (op) {
         case OP_AND:
            return b1 && b2;
         case OP_OR:
            return b1 || b2;
         default:
            return false;
      }

   }

   private IResolvable res;

   public BooleanTreeResolver(IResolvable ires) {
      res = ires;
   }

   public boolean resolve(boolean left, int op, boolean right) {
      return booleanAction(left, op, right);
   }

   public boolean resolve(boolean left, int op, int right) {
      if (left && op == BooleanTreeResolver.OP_OR)
         return true;
      if (!left && op == BooleanTreeResolver.OP_AND)
         return false;
      boolean bright = res.resolve(right);
      return booleanAction(left, op, bright);

   }

   public boolean resolve(int index) {
      return res.resolve(index);
   }

   /**
    * 
    * @param left to be decided based on resolver.
    * @param op
    * @param right known boolean expression
    * @return
    */
   public boolean resolve(int left, int op, boolean right) {
      if (right && op == BooleanTreeResolver.OP_OR)
         return true;
      if (!right && op == BooleanTreeResolver.OP_AND)
         return false;
      //ask to resolve the filter at index left
      boolean bleft = res.resolve(left);
      return booleanAction(bleft, op, right);
   }

   public boolean resolve(int left, int op, int right) {
      boolean b1 = res.resolve(left);
      if (b1 && op == BooleanTreeResolver.OP_OR)
         return true;
      if (!b1 && op == BooleanTreeResolver.OP_AND)
         return false;
      boolean b2 = res.resolve(right);
      return booleanAction(b1, op, b2);
   }
}
