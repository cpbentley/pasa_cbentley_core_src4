package pasa.cbentley.core.src4.io;

public interface ILineReader {

   /**
    * 
    * @param reader
    * @param line the {@link XString} object for getting the string or reaching char directly
    * @param numA 1 based line number value counts ignored lines
    * @param numF 1 based line number value does not count ignore lines
    */
   public void lineRead(FileLineReader reader, XString line, int numA, int numF);
}
