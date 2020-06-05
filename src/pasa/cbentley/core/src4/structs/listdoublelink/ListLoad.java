/*
 * (c) 2018-2020 Charles-Philip Bentley
 * This code is licensed under MIT license (see LICENSE.txt for details)
 */
package pasa.cbentley.core.src4.structs.listdoublelink;

import pasa.cbentley.core.src4.ctx.UCtx;

/**
 * A List circular with 
 * <li>
 * @author Charles Bentley
 *
 */
public class ListLoad extends LinkedListDouble {
   public ListLoad(UCtx uc) {
      super(uc);
   }

   ListElement headData;

   ListElement tailData;

   public ListElement getHeadData() {
      return headData;
   }

   public ListElement getTailData() {
      return tailData;
   }

   public boolean isEmpty() {
      return headData == null;
   }

   public boolean isFull() {
      return headData == tailData.getNext();
   }

   public ListElement getNextEmpty() {
      ListElement le = tailData.getNext();
      if (le == headData) {
         return null;
      }
      return le;
   }

   ListElement getNext(ListElement le) {
      return le.next;
   }
}
