package pasa.cbentley.core.src4.logging;

import pasa.cbentley.core.src4.ctx.UCtx;

/**
 * Configures the {@link IDLogConfig} that was gotten from {@link ILogEntryAppender#getConfig()}
 * 
 * {@link UCtx#toDLog()} gets you to the logger(s) of the root context.
 * 
 * If you want to have different loggers, you have to use different {@link UCtx} for each code region that
 * wants different loggers. Each of those code ctx regions will call {@link UCtx#toDLog()} without knowing better.
 * Loggers are effectively encapsulated.
 * 
 * @author Charles Bentley
 *
 */
public interface ILogConfigurator extends ITechTags,ITechLvl, ITechConfig {

   public void apply(IDLogConfig log);
}
