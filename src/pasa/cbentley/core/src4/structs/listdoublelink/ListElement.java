/*
 * (c) 2018-2020 Charles-Philip Bentley
 * This code is licensed under MIT license (see LICENSE.txt for details)
 */
package pasa.cbentley.core.src4.structs.listdoublelink;

import pasa.cbentley.core.src4.ctx.UCtx;
import pasa.cbentley.core.src4.logging.Dctx;
import pasa.cbentley.core.src4.logging.IStringable;

/**
 * A List Element that can  belong to only one {@link LinkedListDouble}.
 * <br>
 * Head (oldest) -> next -> next -> Tail (newest) -> null
 * Tail (newest -> previous -> previous -> Head -> null
 * @author Charles Bentley
 *
 */
public class ListElement implements IStringable {

   /**
    * null when tail
    */
   ListElement                next;

   /**
    * null when head
    */
   ListElement                prev;

   protected LinkedListDouble list;

   public ListElement(LinkedListDouble rn) {
      if (rn == null)
         throw new NullPointerException();
      list = rn;
   }

   public LinkedListDouble getList() {
      return list;
   }

   /**
    * Removes from current list and add to the other list
    * @param lld
    */
   public void setListAndAdd(LinkedListDouble lld) {
      removeFromList();
      this.list = lld;
      addToList();
   }

   public boolean isTail() {
      return list.tail == this;
   }

   public boolean isHead() {
      return list.head == this;
   }

   /**
    * Is the {@link ListElement} linked to its list
    * @return
    */
   public boolean isListed() {
      if (prev == null) {
         //all elements have a previous element except the tail
         if (list.head != this) {
            return false;
         }
      }
      return true;
   }

   /**
    * Appends Element to List's tail, if already in list. ignore.
    * <br>
    * Newly added elements are
    */
   public void addToList() {
      list.addToList(this);
   }

   /**
    * Remove from List.
    * <br>
    * Can be relisted with {@link ListElement#addToList()}
    */
   public void removeFromList() {
      list.removeFromList(this);
   }

   public ListElement getPrev() {
      return list.getPrev(this);
   }

   public ListElement getNext() {
      //let the list implementation know
      return list.getNext(this);
   }

   //#mdebug
   public String toString() {
      return Dctx.toString(this);
   }

   public String toString1Line() {
      return Dctx.toString1Line(this);
   }

   public void toString(Dctx dc) {
      dc.root(this, ListElement.class,108);
   }

   public void toString1Line(Dctx dc) {
      dc.root1Line(this, ListElement.class);
   }

   public UCtx toStringGetUCtx() {
      return list.toStringGetUCtx();
   }
   //#enddebug
}
