package pasa.cbentley.core.src4.structs.listdoublelink;

import pasa.cbentley.core.src4.ctx.UCtx;
import pasa.cbentley.core.src4.logging.Dctx;
import pasa.cbentley.core.src4.logging.IStringable;

/**
 * <li>Head (oldest) -> next -> next -> Tail (newest) -> null
 * <li>Tail (newest -> previous -> previous -> Head -> null
 * 
 * Iterate from oldest to newest with
 *  ListElement e = list.getHead();
    while (e != null) {
       ListElement next = e.getNext();
       //do stuff even remove e
       
       e = next;
    }
    
 * Iterate from newest to oldest with
 *  ListElement e = list.getTail();
    while (e != null) {
       ListElement prev = e.getPrev();
       //do stuff even remove e
       
       e = prev;
    }
 * 
 * @author Charles Bentley
 *
 */
public class LinkedListDouble implements IStringable {

   ListElementHolder empties;

   /**
    * Next element is null
    * Previous element is newer
    */
   ListElement       head;

   int               size;

   /**
    * Next is older
    * Previous is null. Newest element
    */
   ListElement       tail;

   private UCtx      uc;

   public LinkedListDouble(UCtx uc) {
      this.uc = uc;
   }

   /**
    * Add element to the tail
    * @param le
    */
   public void addToList(ListElement le) {
      if (!le.isListed()) {
         if (this.head == null) {
            //not listed
            this.head = le;
            this.tail = le;
            size++;
         } else {
            //Head (oldest) -> next -> next -> Tail (newest) -> null
            //Tail (newest -> previous -> previous -> Head -> null
            if (le.prev == null) {
               //not listed
               this.tail.next = le;
               le.prev = this.tail;
               this.tail = le;
               size++;
            }
         }
      }
   }

   public ListElementHolder addFreeHolder(Object o) {
      ListElementHolder leh = getFreeHolder();
      leh.setObject(o);
      return leh;
   }

   public ListElementHolder getFreeHolder() {
      if (empties == null) {
         ListElementHolder leh = new ListElementHolder(this, null);
         leh.addToList();
         return leh;
      } else {
         ListElementHolder nextEmpty = (ListElementHolder) empties.next;
         if (nextEmpty != null) {
            nextEmpty.prev = null;
         }
         ListElementHolder retO = empties;
         empties = nextEmpty;
         return retO;
      }
   }

   /**
    * The first/oldest element.
    * @return
    */
   public ListElement getHead() {
      return head;
   }

   ListElement getNext(ListElement le) {
      return le.next;
   }

   public int getNumElements() {
      return size;
   }

   ListElement getPrev(ListElement le) {
      return le.prev;
   }

   public ListElement getTail() {
      return tail;
   }

   public boolean isEmpty() {
      return head == null;
   }

   /**
    * Move oldest to the newest.
    * <br>
    * It is not a swap. 
    */
   public void moveHeadToTail() {
      //condition 
      if (head != null && head != tail) {
         //null -> Head (oldest) -> nextHead -> next -> Tail (newest) -> null
         //null -> Tail (newest -> previous -> previous -> Head -> null
         // nextHead -> next -> Tail -> Head
         //head and tail are not null and are different
         ListElement oldHead = head;
         ListElement oldTail = tail;
         ListElement newHead = head.next;
         //newtail is oldHead
         ListElement newTail = oldHead;
         newTail.prev = oldTail; //link head to old tail
         newTail.next = null;

         newHead.prev = null;
         //the newTail is next oldTail next NewTail
         oldTail.next = newTail;

         tail = newTail;
         head = newHead;
      }
   }

   public void removeFromList(ListElement le) {
      if (le.isListed()) {
         //when pre is null
         if (le.prev == null) {
            //we are head
            this.head = null;
            this.head = le.next;
         } else {
            le.prev.next = le.next;
         }
         //update the tail if remove it the tail
         if (le.next == null) {
            //we remove the last
            this.tail = le.prev;
         } else {
            le.next.prev = le.prev;
         }
         size--;
         le.prev = null;
         le.next = null;
      }
   }

   //#mdebug
   public String toString() {
      return Dctx.toString(this);
   }

   public void toString(Dctx dc) {
      dc.root(this, "LinkedListDouble");
      dc.appendVarWithSpace("Size", size);
      //dc.nlLvl(head, "Head");
      //dc.nlLvl(tail, "Tail");
      ListElement le = getHead();
      int count = 0;
      while (le != null) {
         dc.nl();
         dc.append("Element#" + (count + 1) + " " + le.toString1Line());
         count++;
         le = le.getNext();
      }
   }

   public String toString1Line() {
      return Dctx.toString1Line(this);
   }

   public void toString1Line(Dctx dc) {
      dc.root1Line(this, "LinkedListDouble");
   }

   public UCtx toStringGetUCtx() {
      return uc;
   }
   //#enddebug
}
