package pasa.cbentley.core.src4.io;

import pasa.cbentley.core.src4.structs.IntToStrings;

public class LineReaderIntToStrings implements ILineReader {

   private IntToStrings its;

   public LineReaderIntToStrings(IntToStrings its) {
      this.its = its;

   }

   public void lineRead(FileLineReader reader, XString line, int numA, int numF) {
      //comment first
      its.add(line.getString());
   }

}
