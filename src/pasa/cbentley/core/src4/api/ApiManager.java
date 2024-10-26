package pasa.cbentley.core.src4.api;

import pasa.cbentley.core.src4.ctx.ACtx;
import pasa.cbentley.core.src4.ctx.ObjectU;
import pasa.cbentley.core.src4.ctx.UCtx;
import pasa.cbentley.core.src4.event.ILifeContext;
import pasa.cbentley.core.src4.event.ILifeListener;
import pasa.cbentley.core.src4.interfaces.IAPIService;
import pasa.cbentley.core.src4.structs.IntToObjects;

public class ApiManager extends ObjectU implements ILifeListener {

   /**
    * ID to {@link ServiceCtx}.
    */
   private IntToObjects services;

   public ApiManager(UCtx uc) {
      super(uc);
      services = new IntToObjects(uc);
   }

   /**
    * Returns null if no APIService can be found or created for this ID.
    * 
    * <br>
    * TODO unregisters service
    */
   public IAPIService getAPIService(int id) {
      //#debug
      toDLog().pFlow("param id=" + id + " services ->", services, ApiManager.class, "getAPIService@31", LVL_05_FINE, false);

      //look up registered service providers
      int index = services.findInt(id);
      if (index != -1) {
         Object obj = services.getObjectAtIndex(index);
         if (obj == null) {
            throw new NullPointerException();
         }
         if (obj instanceof ServiceCtx) {
            ServiceCtx serviceCtx = (ServiceCtx) obj;
            if (serviceCtx.service instanceof String) {
               //try loading the class
               try {
                  String className = (String) serviceCtx.service;
                  ACtx ctx = serviceCtx.context;
                  Class c = Class.forName(className);
                  //#debug
                  toDLog().pBridge1("Init for " + className + " for ID=" + id, this, ApiManager.class, "getAPIService@50");
                  //api services such a GamePAD JInput
                  IAPIService apiService = (IAPIService) c.newInstance();
                  apiService.setCtx(ctx);

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
            }
         } else if (obj instanceof IAPIService) {
            return (IAPIService) obj;
         }
      }
      return null;
   }

   /**
    * When service is not defined/null, the host will register default service.
    * 
    * <p>
    * 
    * If a host has gamepad functionalities, it will register the
    * class name with {@link ITechHostCore#SUP_ID_38_GAMEPADS} id
    * </p>
    * 
    * @param service force the driver to use the given class
    * @param id 
    * @return true when host managed to find the given service
    */
   public boolean setAPIService(int id, Object service, ACtx serviceContext) {
      ServiceCtx serviceCtx = new ServiceCtx(service, serviceContext);
      services.add(id, serviceCtx);
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
      for (int i = 0; i < services.getSize(); i++) {
         Object o = services.getObjectAtIndex(i);
         if (o instanceof IAPIService) {
            ((IAPIService) o).lifeStarted(context);
         }
      }
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
