package pasa.cbentley.core.src4.interfaces;

public interface ITechStator extends ITech {

   
   /**
    * When last write/read on a Stator failed
    */
   public static final int FLAG_1_FAILURE = 1 << 0;
}
