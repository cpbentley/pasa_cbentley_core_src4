/*
 * (c) 2018-2020 Charles-Philip Bentley
 * This code is licensed under MIT license (see LICENSE.txt for details)
 */
package pasa.cbentley.core.src4.structs.decisiontree;

import pasa.cbentley.core.src4.ctx.UCtx;
import pasa.cbentley.core.src4.io.BADataIS;
import pasa.cbentley.core.src4.io.BADataOS;
import pasa.cbentley.core.src4.logging.Dctx;
import pasa.cbentley.core.src4.logging.IDLog;
import pasa.cbentley.core.src4.logging.IStringable;

public class Node implements ITechDecisionTree, IStringable {

   int                  addy;

   /**
    * Result for int[] operations
    */
   public int[]         aresult;

   /**
    * index of the Filter(Acceptor) element in the FilterSet (Acceptor)
    */
   int                  index;

   boolean              isLeaf = true;

   int                  left;

   /**
    * result for boolean expression
    */
   public boolean       result;

   int                  right;

   int                  type   = NODE;

   protected final UCtx uc;

   private STree        tree;

   Node(UCtx uc, STree tree, BADataIS bc) {
      this.uc = uc;
      this.tree = tree;
      rmsInput(bc);
   }

   Node(UCtx uc, STree tree, int type, int index, int addy) {
      this(uc, tree, type, -1, -1, index, addy);
   }

   Node(UCtx uc, STree tree, int type, int left, int right, int index, int addy) {
      this.uc = uc;
      this.tree = tree;
      this.type = type;
      if (this.type == NODE) {
         isLeaf = false;
      }
      this.left = left;
      this.right = right;
      this.index = index;
      this.addy = addy;
   }

   Node(UCtx uc, STree tree, int type, int left, int right, int index, int[] addies) {
      this(uc, tree, type, left, right, index, addies.length - 1);
   }

   Node(UCtx uc, STree tree, int type, int index, int[] addies) {
      this(uc, tree, type, -1, -1, index, addies.length - 1);
   }

   public Node left() {
      return tree.getNode(left);
   }

   public Node right() {
      return tree.getNode(right);
   }

   /**
    * Serialization must ensure reference equality
    * @param bc
    */
   public void rmsInput(BADataIS bc) {
      type = bc.read();
      if (type == NODE) {
         isLeaf = false;
      }
      index = bc.readInt();
      left = bc.read();
      if (left == 255) {
         left = -1;
      }
      right = bc.read();
      if (right == 255) {
         right = -1;
      }
      addy = bc.read();
   }

   public void rmsOutput(BADataOS dos) {
      dos.write(type);
      dos.writeInt(index);
      dos.write(left);
      dos.write(right);
      dos.write(addy);
   }
   
   //#mdebug
   public IDLog toDLog() {
      return toStringGetUCtx().toDLog();
   }

   public String toString() {
      return Dctx.toString(this);
   }

   public void toString(Dctx dc) {
      dc.root(this, "Node");
      toStringPrivate(dc);
   }

   public String toString1Line() {
      return Dctx.toString1Line(this);
   }

   private void toStringPrivate(Dctx sb) {
      sb.append(addy + ",");
      sb.append(index + ",");
      sb.append(left + ",");
      sb.append(right + ",");
      sb.append(type);
   }

   public void toString1Line(Dctx dc) {
      dc.root1Line(this, "Node");
      toStringPrivate(dc);
   }

   public UCtx toStringGetUCtx() {
      return uc;
   }

   //#enddebug
   

}