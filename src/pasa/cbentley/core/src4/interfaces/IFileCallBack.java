package pasa.cbentley.core.src4.interfaces;

/**
 * When a class wants to read a text file line by line it implements this interface.
 * <br>
 * <br>
 * It then calls FileUtils.readFile method
 * <br>
 * <br>
 * @author Charles-Philip Bentley
 *
 */
public interface IFileCallBack {
   /**
    * may only READ
    * @param b
    * @param start
    * @param length
    */
   public void lineCallBack(char[] b, int offset, int length);

   public boolean isFileContinue();
}
