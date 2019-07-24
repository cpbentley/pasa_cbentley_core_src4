package pasa.cbentley.core.src4.logging;

import pasa.cbentley.core.src4.ctx.UCtx;

/**
 * Debug interface that works with the {@link Dctx} class.
 * <br>
 * Each {@link IStringable} class implements.
 * <br>
 * This class use pre processor <code>//#mdebug, //#enddebug and //#debug</code> directives.<br>
 * This allows to remove Debugging code in production code.
 * <br>
 * @author Charles Bentley
 *
 */
public interface IStringable extends ITechLvl, ITechConfig, ITechDev, ITechTags {

   //#mdebug

   /**
    * Override Object.toString() in the first class in the type hierarchy with
    * <br>
    * <code>
    *    public String toString() { <br>
    *       return Dctx.toString(this); <br>
    *    } <br>
    * </code>
    * <br>
    * @return
    */
   public String toString();

   /**
    * Returns a 1 line String description. The first class in the type hierarchy should implement
    * <br>
    * <code>
    *    public String toString1Line() { <br>
    *       return Dctx.toString1Line(this); <br>
    *    } <br>
    * </code>
    * <br>
    * @return String
    */
   public String toString1Line();

   /**
    * Appends a multi line String description to the {@link Dctx}.
    * @param dc
    */
   public void toString(Dctx dc);

   /**
    * Appends a single line String description to the {@link Dctx}
    * @param dc
    */
   public void toString1Line(Dctx dc);

   /**
    * Returns the {@link UCtx} of the {@link IStringable}. The Dctx needs it for memory creation.
    */
   public UCtx toStringGetUCtx();
   //#enddebug
}
