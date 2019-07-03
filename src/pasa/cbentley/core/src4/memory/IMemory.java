package pasa.cbentley.core.src4.memory;

import pasa.cbentley.core.src4.ctx.IEventsCore;
import pasa.cbentley.core.src4.logging.IStringable;

/**
 * Any object that creates an array of unknown size must go through this interface.
 * <br>
 * 
 * @author Charles Bentley
 *
 */
public interface IMemory extends IStringable {
   /**
    * 
    * @param len
    * @return
    */
   public char[] createCharArray(int len);

   public int[] resizeTo(int[] ar, int size);

   /**
    * Returns null if invalid len
    * @param len
    * @return
    */
   public char[][] createCharArrayDouble(int len);

   /**
    * if null return null
    * @param ar
    * @return
    */
   public int[] clone(int[] ar);

   /**
    * if null return null
    * @param ar
    * @return
    */
   public byte[] clone(byte[] ar);

   public byte[] createByteArray(int size);

   public int[] createIntArray(int size);

   public void softGC();

   /**
    * Add addition at the end
    * @param ar
    * @param addition
    * @return
    */
   public byte[] increaseCapacity(byte[] ar, int addition);

   public char[] increaseCapacity(char[] ar, int addition);

   public byte[][] increaseCapacity(byte[][] ar, int addition);

   /**
    * Increae the capacity of the array by addition, at the end of the array.
    * @param ar
    * @param addition
    * @return
    */
   public int[] increaseCapacity(int[] ar, int addition);

   public String[] increaseCapacity(String[] ar, int addition);

   public Object[] increaseCapacity(Object[] ar, int addition);

   /**
    * Create empty arrays instead of null refs
    * @param ar
    * @param addition
    * @return
    */
   public int[][] increaseCapacityNonEmpty(int[][] ar, int addition, int startsize);

   /**
    * Adds a {@link IMemFreeable}.
    * 
    * TODO how to unregister a module.. save state and reload?
    * @param free
    */
   public void addMemFreeable(IMemFreeable free);

   public int[] ensureCapacity(int[] ar, int size);

   /**
    * Removes #addition bytes starting from position
    * 
    * [ b b b b b position r r r c c c] of length 12
    * position = 5, addition = 4
    * [ b b b b b c c c ]
    * size is size - addition
    * @param ar
    * @param addition if <= 0 same array is returned
    * @param position
    * @return
    */
   public byte[] decreaseCapacity(byte[] ar, int addition, int position);

   /**
    * The size of each "rows" and create a byte array of newSize for the last
    * element in the master byte array.
    * <br>
    * It is similar to insert empty bytes at offset <code>position</code> . 
    * 
    * At position, ar[position] = 0
    * @param ar array to grow
    * @param addition the number of bytes added, negative value works removing position;position-1; -2
    * @param position the position at which virgin data will start for a length of the size addition
    * for the last position position=ar.length
    * [0 1 2 3 4]
    * @throws ArrayIndexOutOfBoundsException if position is < 0 or > ar.length 
    */
   public byte[] increaseCapacity(byte[] ar, int addition, int position);

   /**
    * Ensure size of the String array and add incr as padding if possible
    * <br>
    * POST: The array returns allows ar[size].
    * <br>
    * @param ar
    * @param size
    * @param incr
    * @return
    * @throws OutOfMemoryError
    */
   public String[] ensureCapacity(String[] ar, int size, int incr);

   public char[] ensureCapacity(char[] ar, int size, int incr);

   public int[] ensureCapacity(int[] ar, int size, int grow);

   public Object[] ensureCapacity(Object[] ar, int size, int grow);

   /**
    * Increase the capacity of the int array, creating the gap at position
    * @param ar
    * @param addition
    * @param position
    * @return
    */
   public int[] increaseCapacity(int[] ar, int addition, int position);
}
