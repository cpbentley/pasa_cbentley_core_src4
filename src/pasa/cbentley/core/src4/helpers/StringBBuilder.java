package pasa.cbentley.core.src4.helpers;

import pasa.cbentley.core.src4.ctx.UCtx;
import pasa.cbentley.core.src4.utils.CharUtils;

/**
 * 
 * @author Charles Bentley
 *
 */
public class StringBBuilder {

   private char         charValues[];

   /** 
    * The count is the number of characters used.
    */
   private int          count;

   private String       sep;

   protected final UCtx uc;

   public StringBBuilder(UCtx uc) {
      this(uc, 100);
   }

   /** 
    * Creates an AbstractStringBuilder of the specified capacity.
    */
   public StringBBuilder(UCtx uc, int capacity) {
      this.uc = uc;
      charValues = new char[capacity];
   }

   public StringBBuilder append(char str[]) {
      int newCount = count + str.length;
      if (newCount > charValues.length)
         expandCapacity(newCount);
      System.arraycopy(str, 0, charValues, count, str.length);
      count = newCount;
      return this;
   }

   public void append(char c) {
      int newCount = count + 1;
      if (newCount > charValues.length)
         expandCapacity(newCount);
      charValues[count++] = c;

   }

   public StringBBuilder append(char str[], int offset, int len) {
      if (len + offset >= str.length) {
         //UiLink.bip(6060, offset+":"+len+ " bad append for string builder for appending " + new String(str),true);
         return this;
      }
      int newCount = count + len;
      if (newCount > charValues.length)
         expandCapacity(newCount);
      System.arraycopy(str, offset, charValues, count, len);
      count = newCount;
      return this;
   }

   public void append(double amount) {
      this.append(String.valueOf(amount));
   }

   public void append(int amount) {
      this.append(String.valueOf(amount));
   }

   public void append(Integer amount) {
      this.append("" + amount);
   }

   public void append(long amount) {
      this.append(String.valueOf(amount));
   }

   /**
    * 
    * @param str
    * @return
    */
   public StringBBuilder append(String str) {
      if (str == null)
         str = "null";
      int len = str.length();
      if (len == 0)
         return this;
      int newCount = count + len;

      ensureCapacity(newCount);

      str.getChars(0, len, charValues, count);
      count = newCount;
      return this;
   }

   public void appendPretty(int value, int numChars, char c, boolean isBack) {
      String valueStr = String.valueOf(value);
      int diff = numChars - valueStr.length();
      if (isBack) {
         this.append(valueStr);
      }
      while (diff > 0) {
         this.append(c);
         diff -= 1;
      }
      if (!isBack) {
         this.append(valueStr);
      }
   }

   public void appendPretty(String str, int numChars, char c, boolean isBack) {
      int diff = numChars - str.length();
      if (isBack) {
         this.append(str);
      }
      while (diff > 0) {
         this.append(c);
         diff -= 1;
      }
      if (!isBack) {
         this.append(str);
      }
   }

   /**
    * Appends the value and append c for that numChars are append in the builder.
    * <li>45, 5, '0' appends "45000"
    * <li>845, 8, '-' appends "845-----"
    * 
    * @param value
    * @param numChars
    * @param c
    */
   public void appendPrettyBack(int value, int numChars, char c) {
      appendPretty(value, numChars, c, true);
   }

   /**
    * <li>"data", 5, '-' appends "data-"
    * <li>"data", 8, ' ' appends "data    "
    * 
    * @param str
    * @param numChars
    * @param c
    */
   public void appendPrettyBack(String str, int numChars, char c) {
      appendPretty(str, numChars, c, true);
   }

   /**
    * Appends the value and append c for that numChars are append in the builder.
    * <li>45, 5, '0' appends "00045"
    * <li>845, 8, '-' appends "-----845"
    * @param value
    * @param numChars
    * @param c
    */
   public void appendPrettyFront(int value, int numChars, char c) {
      appendPretty(value, numChars, c, false);
   }

   /**
    * <li>"data", 5, '-' appends "-data"
    * <li>"data", 8, ' ' appends "    data"
    * 
    * @param str
    * @param numChars
    * @param c
    */
   public void appendPrettyFront(String str, int numChars, char c) {
      appendPretty(str, numChars, c, false);
   }

   /**
    * Append the string with the class separator
    * @param str
    * @return
    */
   public StringBBuilder appendStrSep(String str) {
      append(str);
      append(sep);
      return this;
   }

   public int capacity() {
      return charValues.length;
   }

   public char charAt(int index) {
      if ((index < 0) || (index >= count))
         throw new StringIndexOutOfBoundsException(index);
      return charValues[index];
   }

   public void decrementCount(int v) {
      count -= v;
   }

   public void ensureCapacity(int minimumCapacity) {
      if (minimumCapacity > charValues.length) {
         expandCapacity(minimumCapacity);
      }
   }

   /**
    * This implements the expansion semantics of ensureCapacity with no
    * size check or synchronization.
    */
   void expandCapacity(int minimumCapacity) {
      int newCapacity = (charValues.length + 1) * 2;
      if (newCapacity < 0) {
         newCapacity = Integer.MAX_VALUE;
      } else if (minimumCapacity > newCapacity) {
         newCapacity = minimumCapacity;
      }

      //might be very expensive.
      char newValue[] = new char[newCapacity];
      System.arraycopy(charValues, 0, newValue, 0, count);
      charValues = newValue;
   }

   public int getCount() {
      return count;
   }

   /**
    * Replaces all instances of strOld by strNew
    * @param strOld
    * @param strNew
    */
   public void replaceAll(String strOld, String strNew) {
      int index = 0;
      int indexIncrement = Math.max(0, strNew.length());
      while(index != -1 && index < count) {
         index = replaceFirst(strOld, strNew, index); 
         if(index != -1) {
            //we don't want the strNew to interfere whatsover
            index += indexIncrement; 
         }
      }
   }

   /**
    * Returns the index of the replace
    * @param strOld
    * @param strNew
    * @param offset
    * @return
    */
   public int replaceFirst(String strOld, String strNew, int offset) {
      int index = CharUtils.getFirstIndex(strOld, charValues, offset, count);
      int numCharsOld = strOld.length();
      int numCharsNew = strNew.length();
      int shiftsize = numCharsNew - numCharsOld;
      ensureCapacity(count + numCharsNew);

      if (index != -1) {
         int start = index + numCharsOld;
         int end = count-1;
         CharUtils.shiftChar(charValues, shiftsize, start, end);
         for (int i = 0; i < numCharsNew; i++) {
            charValues[index + i] = strNew.charAt(i);
         }
         count += shiftsize;
      }
      return index;
   }
   
   public void replaceFirst(String strOld, String strNew) {
      replaceFirst(strOld, strNew, 0);
   }

   /**
    * 
    * @param str
    * @param offset
    * @return
    */
   public StringBBuilder insert(String str, int offset) {
      if (str == null)
         return this;
      int len = str.length();
      if (len == 0)
         return this;
      if (offset < 0)
         offset = 0;
      if (offset > count)
         offset = count;
      int newCount = count + len;
      if (newCount >= charValues.length) {
         expandCapacity(newCount);
      }
      //shift data up
      for (int i = count - 1; i >= offset; i--) {
         charValues[i + len] = charValues[i];
      }
      str.getChars(0, len, charValues, offset);
      count = newCount;
      return this;
   }

   public int length() {
      return count;
   }

   public void nl() {
      append('\n');
   }

   public void reset() {
      count = 0;
   }

   public void setCharAt(int index, char ch) {
      if ((index < 0) || (index >= count))
         throw new StringIndexOutOfBoundsException(index);
      charValues[index] = ch;
   }

   /**
    * Arbitrarily move the pointer, effectively erase characters after count
    * @param count
    */
   public void setCount(int count) {
      this.count = count;
   }

   public void setSeparator(String sep) {
      this.sep = sep;
   }

   public void tab() {
      this.append("\t");
   }

   /**
    * @see java.lang.Object#toString()
    */
   public String toString() {
      String s = new String(charValues, 0, count);
      return s;
   }

   public void trimToSize() {
      if (count < charValues.length) {
         char[] newValue = new char[count];
         System.arraycopy(charValues, 0, newValue, 0, count);
         this.charValues = newValue;
      }
   }

}
