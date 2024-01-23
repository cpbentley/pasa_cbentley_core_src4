package pasa.cbentley.core.src4.api;

import pasa.cbentley.core.src4.ctx.ACtx;

class ServiceCtx {
   ACtx   context;

   Object service;

   public ServiceCtx(Object service, ACtx context) {
      this.service = service;
      this.context = context;

   }
}