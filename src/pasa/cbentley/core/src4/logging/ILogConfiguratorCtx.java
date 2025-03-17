package pasa.cbentley.core.src4.logging;

import pasa.cbentley.core.src4.ctx.ACtx;
import pasa.cbentley.core.src4.ctx.IToStringFlagsUC;
import pasa.cbentley.core.src4.ctx.UCtx;

/**
 * Extension of the {@link ILogConfigurator} which allows to configure {@link ACtx} flags.
 * 
 * @author Charles Bentley
 *
 */
public interface ILogConfiguratorCtx extends ILogConfigurator {

   /**
    * Configures the flags for the {@link ACtx} using
    * 
    * <li> {@link ACtx#toStringSetToStringFlag(int, boolean)}
    * @param ac
    */
   public void configureCtx(ACtx ac);

   /**
    * {@link UCtx#toStringSetToStringFlag(int, boolean)} from {@link IToStringFlagsUC}
    * @param uc
    */
   public void configureUCtx(UCtx uc);
}
