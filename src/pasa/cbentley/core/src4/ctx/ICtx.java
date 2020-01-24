package pasa.cbentley.core.src4.ctx;


import pasa.cbentley.core.src4.i8n.IStringsKernel;
import pasa.cbentley.core.src4.logging.Dctx;
import pasa.cbentley.core.src4.logging.IStringable;

/**
 * Implement "static" code context.
 * <br>
 * The reference can be stored in classes. It will never changes.
 * However contents of the ICtx might change and should always be accessed through a getter.
 * <br>
 * 2 applications may have 2 different {@link ICtx}s classes in the same process (thus having 2 different
 * static states) but within the application, a hierarchy of {@link ICtx} will not change.
 * So all references of module {@link ICtx} should be final
 * 
 * @see UCtx UCtx for details
 * 
 * @author Charles Bentley
 *
 */
public interface ICtx extends IStringable {

   public static final int STATIC_ID_01_STRINGS = IStringsKernel.SID_STRINGS_1;

   /**
    * ID used to register events
    * <li>{@link CtxManager#registerStaticID(ICtx, int)}
    * <li>{@link CtxManager#registerStaticRange(ICtx, int, int, int)}
    */
   public static final int STATIC_ID_02_EVENTS  = IEventsCore.SID_EVENTS_2;

   /**
    * Unique id 1 to 255. Used to create unique integer IDs.
    * <br>
    * a Module ctx.
    * <br>
    * what about sub classing? isA relationship.. subclass must keep the same ID
    * @return 0 is not ID defined
    */
   public int getCtxID();

   /**
    * The ID given by the {@link CtxManager} to the {@link ICtx} when it was registered at creation time.
    * @return
    */
   public int getRegistrationID();

   /**
    * Returns a snapshot of the current state/settings/data used by the Module classes.
    * <br>
    * Method is used by the {@link IStaticObjCtrl} to write module state to disk.
    * <br>
    * The module must have a chain of hooks to all class that need to save state or settings.
    * <br>
    * @return a byte array of a {@link ByteOrder}
    */
   public byte[] getSettings();

   /**
    * 
    * @param type
    * @param key
    * @return
    */
   public int getStaticKeyRegistrationID(int type, int key);

   /**
    * Loads the module settings. The byte array is not secured.
    * <br>
    * There might be several objects.
    * 
    * When state is set, the module must check data and update itself.
    * 
    * Security issue if data is malicious.
    * 
    * @param data
    */
   public void setSettings(byte[] data);

   //#mdebug
   /**
    * 
    * @param dctx
    * @param o
    * @return
    */
   public boolean toString(Dctx dctx, Object o);

   /**
    * Finds a toString match for the instance of Object.
    * If none is found inside the ctx, the {@link Dctx}
    * @param dc
    * @param o
    * @param title
    * @return true if Object was found
    */
   public boolean toString(Dctx dc, Object o, String title);

   public boolean toString1(Dctx dc, Object o, String title);

   public String toStringEventID(int pid, int eid);

   public boolean toStringHasToStringFlag(int flag);

   public String toStringProducerID(int pid);

   public void toStringSetToStringFlag(int flag, boolean v);
   //#enddebug
}
