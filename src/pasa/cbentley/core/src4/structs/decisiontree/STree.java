package pasa.cbentley.core.src4.structs.decisiontree;

import pasa.cbentley.core.src4.ctx.UCtx;
import pasa.cbentley.core.src4.io.BADataIS;
import pasa.cbentley.core.src4.io.BADataOS;
import pasa.cbentley.core.src4.logging.Dctx;
import pasa.cbentley.core.src4.logging.IDLog;
import pasa.cbentley.core.src4.logging.IStringable;

/**
 * Decision tree to resolve Dynamic Boolean
 * <br>
 * <br>
 * Used for database filters created on the fly by users to browse a database records
 * based on certain fields.
 * <br>
 * @author Charles Bentley
 *
 */
public class STree implements IStringable, ITechDecisionTree {

   private BooleanTreeResolver  boolresolver;

   private IntArrayTreeResolver intresolver;

   /**
    * This node is one of the Nodes in _operators
    */
   private int                  root = -1;

   private Node[]               treenodes;

   protected final UCtx         uc;

   public STree(UCtx uc) {
      this.uc = uc;
      treenodes = new Node[0];
   }

   public STree(UCtx uc, BADataIS bc) {
      this.uc = uc;
      rmsInput(bc);
   }

   /**
    * add to the right, operator becomes the new root
    * 
    * @param f
    * @param operator
    */
   public void add(int f, int operator) {
      if (root == -1) {
         treenodes = new Node[1];
         treenodes[0] = new Node(uc, this, LEAF, f, 0);
         root = 0;
      } else {
         treenodes = increaseCapacity(treenodes, 2);
         Node newright = new Node(uc, this, LEAF, f, treenodes.length - 2);
         treenodes[newright.addy] = newright;
         Node newRoot = new Node(uc, this, NODE, root, newright.addy, operator, treenodes.length - 1);
         treenodes[newRoot.addy] = newRoot;
         root = newRoot.addy;
      }
   }

   /**
    * Add AND ( LEFT OR RIGHT)
    * AND TYPE = 3 OR TYPE = 5
    * <br>
    * <br>
    * @param left
    * @param opbetween
    * @param right
    * @param roperator if tree is empty, this operator is ignored
    */
   public void add(int left, int opbetween, int right, int roperator) {
      if (root == -1) {
         add(left, 0);
         add(right, opbetween);
      } else {
         addFull(left, opbetween, right, roperator);
      }
   }

   private void addFull(int left, int opbetween, int right, int roperator) {
      treenodes = increaseCapacity(treenodes, 4);
      int fl = treenodes.length;
      Node newright = new Node(uc, this, LEAF, right, fl - 4);
      treenodes[newright.addy] = newright;
      Node newleft = new Node(uc, this, LEAF, left, fl - 3);
      treenodes[newleft.addy] = newleft;
      Node newOp = new Node(uc, this, NODE, newleft.addy, newright.addy, opbetween, fl - 2);
      treenodes[newOp.addy] = newOp;
      //new root with old root as the right node
      Node newRoot = new Node(uc, this, NODE, root, newOp.addy, roperator, fl - 1);
      treenodes[newRoot.addy] = newRoot;
      root = newRoot.addy;
   }

   private void algo() {
      int len = (treenodes.length - 1) / 2;
      int count = 0;
      Node node = null;
      while (count != len) {
         for (int i = 0; i < treenodes.length; i++) {
            node = treenodes[i];
            // op is NODE , then left and right CANNOT be null
            if (node.type == NODE && !node.isLeaf && node.left().isLeaf && node.right().isLeaf) {
               node.isLeaf = true;
               resolve(node);
               count++;
            }
         }
      }
   }

   public void clear() {
      root = -1;
      treenodes = new Node[0];
   }

   public Node getNode(int index) {
      return treenodes[index];
   }

   public Node[] increaseCapacity(Node[] ar, int add) {
      if (ar == null) {
         return new Node[add];
      }
      Node[] oldData = ar;
      ar = new Node[oldData.length + add];
      System.arraycopy(oldData, 0, ar, 0, oldData.length);
      return ar;
   }

   private void prepare() {
      boolresolver = null;
      intresolver = null;
      for (int i = 0; i < treenodes.length; i++) {
         treenodes[i].aresult = null;
         treenodes[i].result = false;
         //make sure a leaf is a leaf and parents are unresolved
         if (treenodes[i].right == -1) {
            treenodes[i].isLeaf = true;
         } else {
            treenodes[i].isLeaf = false;
         }
      }
   }

   /**
    * At current Node (Boolean expression), ask the {@link BooleanTreeResolver} to compute result.
    * @param mf
    * @param op
    */
   private void resolve(BooleanTreeResolver mf, Node op) {
      if (op.left().type == NODE) {
         if (op.right().type == NODE) {
            // we have an operator result here
            op.result = mf.resolve(op.left().result, op.index, op.right().result);
         } else {
            // right is not a node
            op.result = mf.resolve(op.left().result, op.index, op.right().index);
         }
      } else {
         if (op.right().type == NODE) {
            op.result = mf.resolve(op.left().index, op.index, op.right().result);
         } else {
            // left and right are a LEAVES
            op.result = mf.resolve(op.left().index, op.index, op.right().index);
         }
      }
   }

   private void resolve(IntArrayTreeResolver mf, Node op) {
      if (op.left().type == NODE) {
         // check right
         if (op.right().type == NODE) {
            // we have an operator result here
            op.aresult = mf.resolveInArray(op.left().aresult, op.index, op.right().aresult);
         } else {
            // right is not a node
            op.aresult = mf.resolveInArray(op.left().aresult, op.index, op.right().index);
         }
      } else {
         if (op.right().type == NODE) {
            op.aresult = mf.resolveInArray(op.left().index, op.index, op.right().aresult);
         } else {
            // left and right are a LEAVES
            op.aresult = mf.resolveInArray(op.left().index, op.index, op.right().index);
         }
      }
   }

   private void resolve(Node op) {
      if (intresolver != null)
         resolve(intresolver, op);
      if (boolresolver != null) {
         resolve(boolresolver, op);
      }
   }

   /**
    * Run the {@link BooleanTreeResolver} through this boolean expression and those given Filter indexes.
    * .
    * Filter produce boolean result of matching.
    * @param mf
    * @return
    */
   public boolean resolveBoolean(BooleanTreeResolver mf) {
      if (root == -1)
         return true;
      prepare();
      if (treenodes.length == 1) {
         return mf.resolve(treenodes[root].index);
      }
      boolresolver = mf;
      algo();
      //when the algo finishes, the result is at the root
      return treenodes[root].result;
   }

   public int[] resolveIntArray(IntArrayTreeResolver mf) {
      if (root == -1)
         return new int[0];
      prepare();
      if (treenodes.length == 1) {
         return mf.resolve(treenodes[root].index);
      }
      intresolver = mf;
      algo();
      return treenodes[root].aresult;
   }

   public void rmsInput(BADataIS bc) {
      clear();
      // the number of nodes
      root = bc.read();
      int len = bc.read();
      treenodes = new Node[len];
      for (int i = 0; i < len; i++) {
         Node node = new Node(uc, this, bc);
         treenodes[node.addy] = node;
      }
   }

   /**
        * Serialization must keep reference equality 3 cases Single Pointer
        * Node Empty Tree Several Pointer Nodes rooted with an operator
        * 
        * @param dos
        */
   public void rmsOutput(BADataOS dos) {
      if (treenodes == null) {
         // case empty
         dos.write(0);
         return;
      } else {
         dos.write(root);
         dos.write(treenodes.length);
         for (int i = 0; i < treenodes.length; i++) {
            treenodes[i].rmsOutput(dos);
         }
      }
   }

   //#mdebug
   public IDLog toDLog() {
      return toStringGetUCtx().toDLog();
   }

   public String toString() {
      return Dctx.toString(this);
   }

   public void toString(Dctx dc) {
      dc.root(this, "STree");
      toStringPrivate(dc);
      dc.nlLvlArray("Nodes", treenodes);
   }

   public String toString1Line() {
      return Dctx.toString1Line(this);
   }

   public void toString1Line(Dctx dc) {
      dc.root1Line(this, "STree");
      toStringPrivate(dc);
   }

   public UCtx toStringGetUCtx() {
      return uc;
   }

   private void toStringPrivate(Dctx dc) {

   }

   //#enddebug

}
