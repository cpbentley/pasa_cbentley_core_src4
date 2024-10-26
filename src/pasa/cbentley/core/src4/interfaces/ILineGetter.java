package pasa.cbentley.core.src4.interfaces;

import pasa.cbentley.core.src4.logging.LogParameters;

public interface ILineGetter {

   public String getLine(int value);

   /**
    * Default parameters
    * @param cl
    * @param method
    * @param line
    * @return
    */
   public LogParameters getLine(Class cl, String method, int line);

}
