package pasa.cbentley.core.src4.event;

public interface ILifeContext {

   
   /**
    * Called when {@link ILifeListener} is enable to successfully complete
    * a life method.
    * @param lis
    */
   public void addFailure(ILifeListener lis);
}
