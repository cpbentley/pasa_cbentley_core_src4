package pasa.cbentley.core.src4.event;

public interface ILifeListener {

   public void lifeStarted(ILifeContext context);
   public void lifePaused(ILifeContext context);
   public void lifeResumed(ILifeContext context);
   public void lifeStopped(ILifeContext context);
}
