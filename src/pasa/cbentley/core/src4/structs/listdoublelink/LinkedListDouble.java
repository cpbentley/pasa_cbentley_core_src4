/*
 * (c) 2018-2020 Charles-Philip Bentley
 * This code is licensed under MIT license (see LICENSE.txt for details)
 */
package pasa.cbentley.core.src4.structs.listdoublelink;

import pasa.cbentley.core.src4.ctx.ObjectU;
import pasa.cbentley.core.src4.ctx.UCtx;
import pasa.cbentley.core.src4.logging.Dctx;
import pasa.cbentley.core.src4.logging.IDLog;
import pasa.cbentley.core.src4.logging.IStringable;

/**
 * 
 * The class creates elements without object. Why? To save a reference. You create a class that
 * extends {@link ListElement}. Not very orthodox! IsA inheritance relationship is shaky.
 * <br>
 * If you want to create a doubly linked list for objects without this inheritance, {@link LinkedListDouble#getFreeHolder()}
 * and use casting to {@link ListElementHolder} when you want to extract the element. 
 * <br>
 * <br>
 * 
 * <li>Head (oldest) -> next -> next -> Tail (newest) -> null
 * <li>Tail (newest -> previous -> previous -> Head -> null
 * 
 * <code><pre>   
 *  //Iterate from oldest to newest with
 *  ListElement e = list.getHead();
 *  while (e != null) {
 *      //do stuff here even remove e
 *      
 *      e = e.getNext();
 *  }
 *  
 *  //Iterate from newest to oldest with
 *  ListElement e = list.getTail();
 *  while (e != null) {
 *      //do stuff on e even remove e
 *      e = e.getPrev();
 *  }
 * </pre></code>
 * @author Charles Bentley
 *
 */
public class LinkedListDouble extends ObjectU implements IStringable {

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

   /**
    * 
    * @param uc
    */
   public LinkedListDouble(UCtx uc) {
      super(uc);
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

   /**
    * Adds an Object that is not a {@link ListElement}
    * @param o
    * @return
    */
   public ListElementHolder addFreeHolder(Object o) {
      ListElementHolder leh = getFreeHolder();
      leh.setObject(o);
      return leh;
   }

   private ListElementHolder getFreeHolder() {
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

   public Object[] getArray() {
      int size = this.getNumElements();
      Object[] ar = new Object[size];
      ListElement e = this.getHead();
      int count = 0;
      while (e != null) {
          //do stuff even remove e
          Object o = e;
          if(e instanceof ListElementHolder) {
             o = ((ListElementHolder)e).getObject();
          }
          ar[count]= o;
          count++;
          //iterate
          e = (ListElement)e.getNext();
      }

      return ar;
   }
   /**
    * Removes objects whose reference == object
    * @param le
    */
   public void removeObjectRef(Object le) {
      ListElement e = this.getTail();
      while (e != null) {
         ListElement prev = e.getPrev();

         //do stuff even remove e
         if (e instanceof ListElementHolder) {
            ListElementHolder holder = (ListElementHolder) e;
            if (holder.o == le) {
               removeFromList(e);
            }
         }

         e = prev;
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
   public void toString(Dctx dc) {
      dc.root(this, LinkedListDouble.class, 223);
      toStringPrivate(dc);
      super.toString(dc.sup());
      
      ListElement le = getHead();
      int count = 0;
      while (le != null) {
         dc.nl();
         dc.append("Element#");
         dc.append((count + 1));
         dc.append(" ");
         if (le == head) {
            dc.append("Head");
         }
         if (le == tail) {
            dc.append("Tail");
         }
         dc.append(" ");
         dc.append(le.toString1Line());
         count++;
         le = le.getNext();
      }
   }

   public void toString1Line(Dctx dc) {
      dc.root1Line(this, LinkedListDouble.class, 223);
      toStringPrivate(dc);
      super.toString1Line(dc.sup1Line());
   }

   private void toStringPrivate(Dctx dc) {
      dc.appendVarWithSpace("Size", size);
   }
   //#enddebug
}
