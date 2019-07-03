package pasa.cbentley.core.src4.helpers;

/**
 * 
 * @author Charles Bentley
 *
 */
public class StringBBuilder {

   private char   charValues[];

   /** 
    * The count is the number of characters used.
    */
   private int    count;

   private String sep;

   /**
    * There is a logic.. Big? But what is big? And what is small?
    * So there is a logic and its not just sugar.
    * <br>
    * In this case, the consequences are few. The array is designed to grow anyways.
    * <br>
    * @return
    */
   public static StringBBuilder getBig() {
      return new StringBBuilder(10000);
   }

   public void decrementCount(int v) {
      count -= v;
   }

   public static StringBBuilder getThreadSmall() {
      return new StringBBuilder(200);
   }

   public StringBBuilder() {
      this(100);
   }

   public void nl() {
      append('\n');
   }

   /**
    * Arbitrarily move the pointer, effectively erase characters after count
    * @param count
    */
   public void setCount(int count) {
      this.count = count;
   }
   /** 
    * Creates an AbstractStringBuilder of the specified capacity.
    */
   public StringBBuilder(int capacity) {
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

   //#ifdef polish.hasFloatingPoint
   public void append(double amount) {
      this.append(String.valueOf(amount));
   }

   //#endif

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
      if (newCount > charValues.length)
         expandCapacity(newCount);
      str.getChars(0, len, charValues, count);
      count = newCount;
      return this;
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

   public void tab() {
      this.append("\t");
   }

   public int capacity() {
      return charValues.length;
   }

   public char charAt(int index) {
      if ((index < 0) || (index >= count))
         throw new StringIndexOutOfBoundsException(index);
      return charValues[index];
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
      if (newCount >= charValues.length)
         expandCapacity(newCount);
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

   public void reset() {
      count = 0;
   }

   public void setCharAt(int index, char ch) {
      if ((index < 0) || (index >= count))
         throw new StringIndexOutOfBoundsException(index);
      charValues[index] = ch;
   }

   public void setSeparator(String sep) {
      this.sep = sep;
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

   public int getCount() {
      return count;
   }

}
