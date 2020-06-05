package pasa.cbentley.core.src4.stator;

public interface IStatorFactory {

   /**
    * Creates object from {@link StatorReader}
    * @param state
    * @return null if none
    */
   public Object createObject(StatorReader state, Class type);

   /**
    * True if Factory can create objects of this class
    * @param cl
    * @return
    */
   public boolean isTypeSupported(Class cl);

   public Object[] createArray(Class cl, int size);
}
