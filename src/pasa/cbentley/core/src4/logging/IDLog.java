/*
 * (c) 2018-2020 Charles-Philip Bentley
 * This code is licensed under MIT license (see LICENSE.txt for details)
 */
package pasa.cbentley.core.src4.logging;

import pasa.cbentley.core.src4.ctx.UCtx;

/**
 * The interface for logging logs to registered {@link ILogEntryAppender}.
 * 
 * <li> {@link ITechDev} 
 * <li> {@link ITechLvl}
 * <li> {@link ITechTags} pEvent is a log for Tag Event.
 * </li>
 * <p>
 * Unlike cabi, we don't use the "this", because we want the log to be linked
 * to the actual class in which it is called. In class inheritance hierarchy,
 * so, you have to specify a {@link Class} in all {@link IDLog} methods.
 * 
 * 
 * For a list of all tags, {@link ITechTags}.
 * 
 * <b>Dev flags</b>
 * Flags that the dev can manually set for a quick debug override of the config.
 * 
 * Dev flags are only overriden by the master config flags of {@link ITechDLogConfig}
 * 
 * @author Charles Bentley
 *
 * @see ITechTags
 */
public interface IDLog extends IStringable {

   /**
    * Number of printed statements
    * @return
    */
   public long getCount();

   //#mdebug
   /**
    * The default logger created by {@link UCtx}.
    * 
    * For the default appender config {@link ILogEntryAppender#getConfig()}
    * 
    * To add new Appenders, you have to get a reference to the IDLog implementation.
    * It may or may not support multiple appenders.
    * 
    * @return
    */
   public ILogEntryAppender getDefault();

   public boolean getTag(int tag);

   public int levelDecrement();

   public int levelIncrement();

   public void pAlways(String msg, Object str, Class c, String method);

   public void pAlways(String msg, Object str, Class c, String method, int lvl, boolean oneLine);

   public void pAlways(String msg, Object str, LogParameters params, int lvl, boolean oneLine);

   public void pAnim(String msg, Object str, Class c, String method);

   public void pAnim(String msg, Object str, Class c, String method, int lvl, boolean oneLine);

   public void pBridge(String msg, Object str, Class c, String method);

   public void pBridge(String msg, Object str, Class c, String method, int lvl, boolean oneLine);

   public void pBridge(String msg, Object str, LogParameters params, int lvl, boolean oneLine);

   public void pBridge1(String msg, Object str, Class c, String method);

   public void pBusiness(String msg, Object str, Class c, String method, int lvl, boolean oneLine);

   public void pBusiness(String msg, Object str, Class c, String method, int lvl, int flags);

   public void pBusiness1(String msg, Object str, Class c, String method);

   public void pCmd(String msg, Object str, Class c, String method);

   public void pCmd(String msg, Object str, Class c, String method, int lvl, boolean oneLine);

   public void pCmd(String msg, Object str, LogParameters params);

   public void pCmd(String msg, Object str, LogParameters params, int lvl, boolean oneLine);

   public void pCmd1(String msg, Object str, Class c, String method);

   public void pConfig(String msg, Object str, Class c, String method, int lvl, boolean oneLine);

   public void pCreate(String msg, Object str, Class c, String method, int lvl, boolean oneLine);

   public void pCreate(String msg, Object str, LogParameters params, int lvl, boolean oneLine);

   public void pData(String msg, Object str, Class c, String method);

   public void pData(String msg, Object str, Class c, String method, int lvl, boolean oneLine);

   public void pData(String msg, Object str, Class c, String method, int lvl, boolean oneLine, Exception e);

   public void pData(String msg, Object str, LogParameters params, int lvl, boolean oneLine);

   public void pDraw(String msg, Object str, Class c, String method);

   public void pDraw(String msg, Object str, Class c, String method, int lvl, boolean oneLine);

   public void pDraw(String msg, Object str, LogParameters params, int lvl, boolean oneLine);

   public void pEvent(String msg, Object str, Class c, String method);

   public void pEvent(String msg, Object str, Class c, String method, int lvl, boolean oneLine);

   /**
    * Flag override the 1st level config
    * @param msg
    * @param str
    * @param c
    * @param method
    * @param lvl
    * @param flags
    */
   public void pEvent(String msg, Object str, Class c, String method, int lvl, int flags);

   public void pEvent(String msg, Object str, LogParameters params, int lvl, boolean oneLine);

   public void pEvent1(String msg, Object str, Class c, String method);

   public void pEventFine(String msg, Object str, Class c, String method);

   public void pEventFiner(String msg, Object str, Class c, String method);

   public void pEventFinest(String msg, Object str, Class c, String method);

   public void pEventInfo(String msg, Object str, Class c, String method);

   public void pEventSevere(String msg, Object str, Class c, String method);

   public void pEventWarn(String msg, Object str, Class c, String method);

   /**
    * When there is an exception
    * @param msg
    * @param str
    * @param c
    * @param method
    */
   public void pEx(String msg, Object str, Class c, String method, Exception e);

   public void pFlag(int flag, String msg, Object str, Class c, String method, int lvl, boolean oneLine);

   public void pFlow(String msg, Object str, Class c, String method);

   public void pFlow(String msg, Object str, Class c, String method, int lvl, boolean oneLine);

   public void pSetADraw(String msg, Object str, LogParameters params, int lvl, boolean oneLine);

   public void pSetAFlow(String msg, Object str, LogParameters params, int lvl, boolean oneLine);

   public void pSetFlow(String msg, Object str, LogParameters params, int lvl, boolean oneLine);

   public void pSet(String msg, Object str, LogParameters params, int lvl, boolean oneLine);

   public void pInitADraw(String msg, Object str, LogParameters params, int lvl, boolean oneLine);

   public void o5FlowEvent(String msg, Object str, LogParameters params);

   public void o4FlowEvent(String msg, Object str, LogParameters params);

   public void o3FlowEvent(String msg, Object str, LogParameters params);

   public void pFlow(String msg, Object str, Class c, String method, int lvl, int flags);

   public void pFlow(String msg, Object str, LogParameters params, int lvl, boolean oneLine);

   /**
    * We know its big chunk of data. Only printed if {@link ITechDLogConfig}
    * @param msg
    * @param str
    * @param c
    * @param method
    */
   public void pFlowBig(String msg, Object str, Class c, String method);

   public void pInit(String msg, Object str, Class c, String method);

   public void pInit(String msg, Object str, Class c, String method, int lvl, boolean oneLine);

   public void pInit(String msg, Object str, Class c, String method, int lvl, int flags);

   public void pInit(String msg, Object str, LogParameters params, int lvl, boolean oneLine);

   public void pInit1(String msg, Object str, Class c, String method);

   public void pInitBig(String msg, Object str, Class c, String method);

   public void pMemory(String msg, Object str, Class c, String method);

   public void pMemory(String msg, Object str, Class c, String method, int lvl, boolean oneLine);

   public void pMemoryWarn(String msg, Object str, Class c, String method);

   public void pModel(String msg, Object str, Class c, String method);

   public void pModel(String msg, Object str, Class c, String method, int lvl, boolean oneLine);

   public void pModel(String msg, Object str, LogParameters params, int lvl, boolean oneLine);

   public void pModel1(String msg, Object str, Class c, String method);

   public void pNull(String msg, Object str, Class c, String method);

   public void pNull(String msg, Object str, Class c, String method, int lvl, boolean oneLine);

   public void pNull(String msg, Object str, LogParameters params, int lvl, boolean oneLine);

   public void pSound(String msg, Object str, Class c, String method);

   public void pSound(String msg, Object str, Class c, String method, int lvl, boolean oneLine);

   public void pSound1(String msg, Object str, Class c, String method);

   /**
    * 
    * @param msg
    * @param str
    * @param c
    * @param method
    * @param lvl
    * @param oneLine
    */
   public void pSoundEvent(String msg, Object str, Class c, String method, int lvl, boolean oneLine);

   public void pState(String msg, Object str, Class c, String method);

   public void pState(String msg, Object str, Class c, String method, int lvl, boolean oneLine);

   public void pStator(String msg, Object str, Class c, String method);

   public void pStator(String msg, Object str, Class c, String method, int lvl, boolean oneLine);

   public void pStator(String msg, Object str, LogParameters params, int lvl, boolean oneLine);

   public void pSimul(String msg, Object str, LogParameters params, int lvl, boolean oneLine);

   public void pTest(String msg, Object str, Class c, String method);

   public void pTest(String msg, Object str, Class c, String method, int lvl, boolean oneLine);

   public void pTest1(String msg, Object str, Class c, String method);

   public void pUI(String msg, Object str, Class c, String method);

   public void pUI(String msg, Object str, Class c, String method, int lvl, boolean oneLine);

   public void pWork(String msg, Object str, Class c, String method);

   public void pWork(String msg, Object str, Class c, String method, int lvl, boolean oneLine);

   public boolean toggleTag(int tag);

   //#enddebug
}
