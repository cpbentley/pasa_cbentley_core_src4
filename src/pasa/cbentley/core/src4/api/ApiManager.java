package pasa.cbentley.core.src4.api;

import pasa.cbentley.core.src4.ctx.ACtx;
import pasa.cbentley.core.src4.ctx.ObjectU;
import pasa.cbentley.core.src4.ctx.UCtx;
import pasa.cbentley.core.src4.event.ILifeContext;
import pasa.cbentley.core.src4.event.ILifeListener;
import pasa.cbentley.core.src4.interfaces.IAPIService;
import pasa.cbentley.core.src4.structs.IntToObjects;

public class ApiManager extends ObjectU implements ILifeListener {

   private IntToObjects services;

   public ApiManager(UCtx uc) {
      super(uc);
      services = new IntToObjects(uc);
   }

   /**
    * Returns a class implementing the given API id.
    * <li> {@link ITechHost#SUP_ID_38_GAMEPADS}
    * @param id
    * @return
    */
   /**
    * Retursn null if no APIService can be found or created for this ID.
    * <br>
    * <br>
    * 
    * <br>
    * TODO unregisters service
    */
   public IAPIService getAPIService(int id) {
      //#debug
      toDLog().pFlow("id=" + id + " services ->", services, ApiManager.class, "getAPIService", LVL_05_FINE, false);

      //look up registered service providers
      int index = services.findInt(id);
      if (index != -1) {
         ServiceCtx o = (ServiceCtx) services.getObjectAtIndex(index);
         if (o.service instanceof String) {
            //try loading the class
            try {
               String className = (String) o.service;
               Class c = Class.forName(className);
               //#debug
               toDLog().pBridge1("Init " + className + " for ID=" + id, this, ApiManager.class, "getAPIService");
               //api services such a GamePAD JInput
               IAPIService apiService = (IAPIService) c.newInstance();
               apiService.setCtx(o.context);
               services.setObject(apiService, index); //set the apiservice to later uses
               return apiService;
            } catch (ClassNotFoundException e) {
               e.printStackTrace();
               return null;
            } catch (InstantiationException e) {
               e.printStackTrace();
            } catch (IllegalAccessException e) {
               // TODO Auto-generated catch block
               e.printStackTrace();
            }
         } else if (o instanceof IAPIService) {
            return (IAPIService) o;
         }
      }
      return null;
   }

   /**
    * Overrides any object at given id
    */
   public boolean registerServiceProvider(Object service, int id) {
      services.add(id, service);
      return true;
   }

   /**
    * When service is not defined/null, the host will register default service 
    * <br>
    * If a host has gamepad functionalities, it will register the
    * class name with {@link ITechHostCore#SUP_ID_38_GAMEPADS} id
    * @param service force the driver to use the given class
    * @param id 
    * <br>
    * @return true when host managed to find the given service
    */
   public boolean setAPIService(int id, Object service, ACtx serviceContext) {
      services.add(id, new ServiceCtx(service, serviceContext));
      return true;
   }

   public void lifePaused(ILifeContext context) {
      for (int i = 0; i < services.getSize(); i++) {
         Object o = services.getObjectAtIndex(i);
         if (o instanceof IAPIService) {
            ((IAPIService) o).lifePaused(context);
         }
      }
   }

   public void lifeResumed(ILifeContext context) {
      for (int i = 0; i < services.getSize(); i++) {
         Object o = services.getObjectAtIndex(i);
         if (o instanceof IAPIService) {
            ((IAPIService) o).lifeResumed(context);
         }
      }
   }

   public void lifeStarted(ILifeContext context) {

   }

   public void lifeStopped(ILifeContext context) {
      for (int i = 0; i < services.getSize(); i++) {
         Object o = services.getObjectAtIndex(i);
         if (o instanceof IAPIService) {
            ((IAPIService) o).lifeStopped(context);
         }
      }
   }
}
