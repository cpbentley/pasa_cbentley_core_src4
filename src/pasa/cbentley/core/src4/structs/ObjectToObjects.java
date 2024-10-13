/*
 * (c) 2018-2020 Charles-Philip Bentley
 * This code is licensed under MIT license (see LICENSE.txt for details)
 */
package pasa.cbentley.core.src4.structs;

import java.util.Enumeration;

import pasa.cbentley.core.src4.ctx.ObjectU;
import pasa.cbentley.core.src4.ctx.UCtx;
import pasa.cbentley.core.src4.logging.Dctx;
import pasa.cbentley.core.src4.logging.IStringable;
import pasa.cbentley.core.src4.structs.listdoublelink.LinkedListDouble;

/**
 * Maps one Object key to exactly one object value
 * @author Charles Bentley
 *
 */
public class ObjectToObjects extends ObjectU implements IStringable {

   private BufferObject bufferKeys;

   private BufferObject bufferValues;

   private final boolean      isMulti;

   /**
    * Returns {@link Integer#MAX_VALUE} when key not found.
    */
   public ObjectToObjects(UCtx uc) {
      this(uc,false);
   }

   public ObjectToObjects(UCtx uc, boolean isMultiValue) {
      super(uc);
      this.isMulti = isMultiValue;
      this.bufferKeys = new BufferObject(uc, 4);
      this.bufferValues = new BufferObject(uc, 4);
   }

   public int getSize() {
      return bufferKeys.getSize();
   }

   public boolean isMultiValues() {
      return isMulti;
   }

   public Enumeration getEnumerationKeys() {
      return bufferKeys.getEnumeration();
   }

   /**
    * Add key as being unique.
    * @param key
    * @param value
    */
   public void add(Object key, Object value) {
      int index = bufferKeys.getObjectIndex(key);
      if (index == -1) {
         bufferKeys.add(key);
         bufferValues.add(value);
      } else {
         if (isMulti) {
            Object object = bufferValues.get(index);
            if (object instanceof LinkedListDouble) {
               ((LinkedListDouble) object).addFreeHolder(value);
            } else {
               LinkedListDouble lld = new LinkedListDouble(uc);
               lld.addFreeHolder(object);
               lld.addFreeHolder(value);
               bufferValues.setUnsafe(index, lld);
            }
         } else {
            //replace the other
            bufferValues.setUnsafe(index, value);
         }
      }
   }

   public Object getValue(Object key) {
      int index = bufferKeys.getObjectIndex(key);
      if (index == -1) {
         return null;
      } else {
         return bufferValues.get(index);
      }
   }

   public Object[] getValues(Object key) {
      int index = bufferKeys.getObjectIndex(key);
      if (index == -1) {
         return null;
      } else {
         Object object = bufferValues.get(index);
         if (object instanceof LinkedListDouble) {
            LinkedListDouble lld = (LinkedListDouble) object;
            return lld.getArray();
         } else {
            return new Object[] { object };
         }
      }
   }

   //#mdebug
   public void toString(Dctx dc) {
      dc.root(this, ObjectToObjects.class, 356);
      toStringPrivate(dc);
      super.toString(dc.sup());
   }

   public void toString1Line(Dctx dc) {
      dc.root1Line(this, ObjectToObjects.class, 356);
      toStringPrivate(dc);
      super.toString1Line(dc.sup1Line());
   }

   private void toStringPrivate(Dctx dc) {
   }
   //#enddebug

}
