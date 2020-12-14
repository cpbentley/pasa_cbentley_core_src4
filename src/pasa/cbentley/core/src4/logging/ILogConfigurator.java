/*
 * (c) 2018-2020 Charles-Philip Bentley
 * This code is licensed under MIT license (see LICENSE.txt for details)
 */
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
 * This class does not implement {@link IStringable}. Debugging it is simply 
 * 
 * It does not have a {@link UCtx} reference.
 * 
 * @author Charles Bentley
 *
 */
public interface ILogConfigurator extends ITechTags,ITechLvl, ITechConfig {

   //#mdebug
   public void apply(IDLogConfig log);
   
   //#enddebug
}
