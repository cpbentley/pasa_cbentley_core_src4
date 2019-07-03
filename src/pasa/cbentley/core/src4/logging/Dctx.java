package pasa.cbentley.core.src4.logging;

import java.awt.Component;
import java.util.Iterator;
import java.util.Vector;

import pasa.cbentley.core.src4.ctx.ICtx;
import pasa.cbentley.core.src4.ctx.IFlagsToString;
import pasa.cbentley.core.src4.ctx.UCtx;
import pasa.cbentley.core.src4.helpers.StringBBuilder;
import pasa.cbentley.core.src4.structs.IntToObjects;
import pasa.cbentley.core.src4.structs.IntToStrings;
import pasa.cbentley.core.src4.utils.BitUtils;
import pasa.cbentley.core.src4.utils.StringUtils;

/**
 * Bastard context (lowercase C letter)
 * 
 * Tracks which class has been toString.
 * <br>
 * Cuts
 * <br>
 * Tracks the whole toString from the start by using a {@link IntToObjects}
 * to track debugged classes.
 * <br>
 * One {@link StringBuilder} for the whole process.
 * <br>
 * Each level can have its own {@link StringBuilder} for hiding purposes
 * <br>
 * <br>
 * Hide some levels.
 * 
 * <br>
 * @author Charles Bentley
 *
 */
public class Dctx implements IFlagsToString {
   //#mdebug

   public static String toString(IStringable is) {
      Dctx c = new Dctx(is.toStringGetUCtx(), "\n\t");
      is.toString(c);
      return c.toString();
   }

   public static String toString1Line(IStringable is) {
      Dctx c = new Dctx(is.toStringGetUCtx(), "");
      is.toString1Line(c);
      return c.toString();
   }

   private int            flags;

   private boolean        isCompact;

   private String         nl;

   private int            nlNum;

   private IntToStrings   nulls;

   private Dctx           parent;

   private IntToObjects   processedObjects;

   private String         rootTitle;

   private StringBBuilder sb;

   private int            tick;

   private IntToObjects   track;

   private UCtx           uc;

   public Dctx(UCtx uc) {
      this(uc, "\n");
   }

   /**
    * Creates a {@link Dctx} with parent
    * @param parent
    */
   public Dctx(UCtx uc, Dctx parent) {
      this(uc, parent, 0);
   }

   public Dctx(UCtx uc, Dctx parent, int i) {
      if (uc == null) {
         throw new NullPointerException();
      }
      this.uc = uc;
      this.parent = parent;
      String tab = "";
      for (int j = 0; j < i; j++) {
         tab = tab + "\t";
      }
      nl = parent.nl + tab;
      sb = parent.sb;
      nulls = new IntToStrings(uc);
      processedObjects = parent.processedObjects;
   }

   public Dctx(UCtx uc, String nl) {
      if (uc == null) {
         throw new NullPointerException();
      }
      this.uc = uc;
      this.nl = nl;
      sb = new StringBBuilder(4000);
      nulls = new IntToStrings(uc);
      processedObjects = new IntToObjects(uc);
   }

   public void append(char str[]) {
      sb.append(str);
   }

   public void append(char v) {
      sb.append(v);
   }

   public void append(char str[], int offset, int len) {
      sb.append(str, offset, len);
   }

   public void append(String[] strs, int offset, int len, String sep) {
      for (int i = offset; i < offset + len; i++) {
         if (i != offset) {
            sb.append(sep);
         }
         sb.append(strs[i]);
      }
   }

   public void append(int v) {
      sb.append(v);
   }

   public void append(String string) {
      sb.append(string);
   }

   public void appendName(Class cs) {
      String name = cs.getName();
      int v = name.lastIndexOf('.');
      if (v == -1) {
         v = 0;
      } else {
         v++;
      }
      sb.append('#');
      sb.append(name.substring(v, name.length()));
   }

   public void appendPretty(String val, int value, int max) {
      int numChars = String.valueOf(max).length();
      String pretty = uc.getStrU().prettyInt0Padd(value, numChars);
      append(val);
      append(pretty);
   }

   public void appendVar(String s, boolean v) {
      sb.append(s);
      sb.append('=');
      sb.append(String.valueOf(v));
   }

   public void appendVar(String s, int v) {
      sb.append(s);
      sb.append('=');
      sb.append(v);
   }

   public void appendVar(String s, String v) {
      sb.append(s);
      sb.append('=');
      sb.append(v);
   }

   public void appendVarWithSpace(String s, boolean v) {
      sb.append(' ');
      sb.append(s);
      sb.append('=');
      sb.append(String.valueOf(v));
   }

   public void appendVarWithSpace(String s, float v) {
      sb.append(' ');
      sb.append(s);
      sb.append('=');
      sb.append(String.valueOf(v));
   }

   public void appendVarWithSpace(String s, Double v) {
      if (v == null) {
         this.appendVarWithSpace(s, "null");
      } else {
         this.appendVarWithSpace(s, v.doubleValue());
      }
   }

   public void appendVarWithSpace(String s, Integer v) {
      if (v == null) {
         this.appendVarWithSpace(s, "null");
      } else {
         this.appendVarWithSpace(s, v.intValue());
      }
   }

   public void appendVarWithSpace(String s, int v) {
      sb.append(' ');
      sb.append(s);
      sb.append('=');
      sb.append(v);
   }

   public void appendVarWithSpace(String s, double v) {
      sb.append(' ');
      sb.append(s);
      sb.append('=');
      sb.append(String.valueOf(v));
   }

   public void appendVar(String s, Object v) {
      sb.append(s);
      sb.append('=');
      if (v == null) {
         sb.append("null");
      } else {
         if (v instanceof IStringable) {
            ((IStringable) v).toString1Line(this);
         } else {
            sb.append(v.toString());
         }
      }
   }

   public void appendVarWithSpace(String s, Object v) {
      sb.append(' ');
      appendVar(s, v);
   }

   public void appendVarWithSpace(String s, IStringable v) {
      sb.append(' ');
      sb.append(s);
      sb.append('=');
      if (v == null) {
         sb.append("null");
      } else {
         v.toString1Line(this);
      }
   }

   public void appendVarWithSpace(String s, long v) {
      sb.append(' ');
      sb.append(s);
      sb.append('=');
      sb.append(v);
   }

   /**
    * Fronted with space
    * @param s
    * @param v
    */
   public void appendVarWithSpace(String s, String v) {
      sb.append(' ');
      sb.append(s);
      sb.append('=');
      sb.append(v);
   }

   public void appendVarWithNewLine(String s, String v) {
      nl();
      sb.append(s);
      sb.append('=');
      sb.append(v);
   }

   public void appendVarWithSpaceNotNull(String s, String v) {
      if (v != null) {
         sb.append(' ');
         sb.append(s);
         sb.append('=');
         sb.append(v);
      }
   }

   public void appendVarWithTab(String s, int v) {
      sb.append('\t');
      sb.append(s);
      sb.append('=');
      sb.append(v);
   }

   public void appendVarWithTab(String s, String v) {
      sb.append('\t');
      sb.append(s);
      sb.append('=');
      sb.append(v);
   }

   public void appendWithSpace(String string) {
      sb.append(' ');
      sb.append(string);
   }

   public void debug(int[] ar, String sep) {
      if (ar == null) {
         this.append("null");
      } else {
         for (int i = 0; i < ar.length; i++) {
            this.append(i + "=" + ar[i] + sep);
         }
      }
   }

   public void debugNlSep(int[] ar) {
      if (ar == null) {
         this.append("null");
      } else {
         for (int i = 0; i < ar.length; i++) {
            this.append(i + "=" + ar[i] + nl);
         }
      }
   }

   private void doRootTitle() {
      if (rootTitle != null && rootTitle.length() != 0) {
         sb.append(' ');
         sb.append('[');
         sb.append(rootTitle);
         sb.append(']');
      }
   }

   public Dctx exclusive(String str, IStringable dd) {
      Dctx dc = new Dctx(uc, nl + "\t");
      dc.append(str);
      dd.toString(dc);
      return dc;
   }

   public int getCount() {
      return sb.getCount();
   }

   public StringBBuilder getSB() {
      return sb;
   }

   /**
    * 
    * @param ctx
    * @param flag
    * @return
    */
   public boolean hasFlagData(ICtx ctx, int flag) {
      return false;
   }

   public boolean isCompact() {
      return isCompact;
   }

   /**
    * Convert to data flag
    * @return
    */
   public boolean isExpand() {
      return BitUtils.hasFlag(flags, FLAG_1_EXPAND);
   }

   public boolean isTick() {
      return tick == sb.getCount();
   }

   public Dctx Level() {
      Dctx dc = new Dctx(uc, this);
      return dc;
   }

   /**
    * List nulls on 1 line
    */
   public void listNulls() {
      if (nulls.nextempty > 0) {
         nl();
         for (int i = 0; i < nulls.nextempty; i++) {
            sb.append(nulls.strings[i]);
            sb.append(" is null");
            if (i + 1 < nulls.nextempty) {
               sb.append(" | ");
            }
         }
      }
   }

   public void nl() {
      sb.append(nl);
   }

   public void nlAppend(String string) {
      nl();
      append(string);
   }

   /**
    * Print a nline.
    * Create a new {@link Dctx} increasing tabulation by 1.
    * 
    * @return
    */
   public Dctx nLevel() {
      nl();
      Dctx dc = new Dctx(uc, this);
      return dc;
   }

   public Dctx nLevel(int flags) {
      nl();
      Dctx c = new Dctx(uc, this);
      c.flags = flags;
      return c;
   }

   public Dctx nLevelTab() {
      nl();
      Dctx dc = new Dctx(uc, this);
      dc.tab();
      return dc;
   }

   /**
    * A new level prints a {@link IStringable}.
    * 1st line is on current level,
    * next lines will be indented once
    * @param is
    */
   public void nlLvl(IStringable is) {
      nlLvl("", is);
   }

   /**
    * Sets a root title 
    * @param is
    * @param t
    */
   public void nlLvl(IStringable is, String t) {
      nlLvl(t, is, 0, true);
   }

   public void nlLvlArray(Object[] ar, String t) {
      IStringable[] ars = new IStringable[ar.length];
      for (int i = 0; i < ar.length; i++) {
         ars[i] = (IStringable) ar[i];
      }
      nlLvlArray(t, ars);
   }

   /**
    * Only prints title if {@link IStringable} is null
    * @param is
    * @param t
    */
   public void nlLvlTitleIfNull(IStringable is, String t) {
      nlLvl(t, is, 0, true, true, true);
   }

   public void nlLvl(String str, int[] ins, int i) {
      sb.append(nl);
      sb.append(str);
      sb.append(nl + "\t");
      int count = 0;
      for (int j = 0; j < ins.length; j++) {
         sb.append(ins[j]);
         sb.append(" ");
         count++;
         if (count == i) {
            sb.append(nl + "\t");
         }
      }
   }

   /**
    * Check if {@link IStringable} is not null.
    * <br>
    * @param t
    * @param is
    */
   public void nlLvl(String t, IStringable is) {
      nlLvl(t, is, 0);
   }

   /**
    * If the {@link IStringable} has already been toString,
    * it will only be toString1Line
    * @param title
    * @param is
    * @param flags
    */
   public void nlLvl(String title, IStringable is, int flags) {
      nlLvl(title, is, flags, false);
   }

   public void nlLvl(String title, IStringable is, int flags, boolean flip) {
      nlLvl(title, is, flags, flip, false);
   }

   public void nlLvl(String title, IStringable is, int flags, boolean flip, boolean list) {
      nlLvl(title, is, flags, flip, list, false);
   }

   /**
    * 
    * @param title
    * @param is
    * @param flags
    * @param flip when true, the title goes after the root name, when false title=
    * @param list when true, if the {@link IStringable} is null, it is added to a list of null.
    * @param titleOnlyIfNull
    * That list is printed by the call {@link Dctx#listNulls()}
    */
   public void nlLvl(String title, IStringable is, int flags, boolean flip, boolean list, boolean titleOnlyIfNull) {
      if (is == null) {
         if (list) {
            nulls.add(title);
         } else {
            Dctx dc = nLevel(flags);
            dc.append(title + " is Null");
         }
      } else {
         Dctx dc = nLevel(flags);
         if (!titleOnlyIfNull) {
            if (title.length() != 0) {
               if (flip) {
                  //set root title
                  dc.setRootTitle(title);
               } else {
                  dc.append(title);
                  dc.append("=");
               }
            }
         }
         int reference = processedObjects.getObjectIndex(is);
         if (reference != -1) {
            //TODO sets a string reference to the object id
            //by using in the int to object id as the reference
            //dc.setRootReference(reference);
            is.toString1Line(dc);
         } else {
            processedObjects.add(is);
            is.toString(dc);
         }
      }
   }

   /**
    * one line or not?
    * @param t
    * @param is
    */
   public void nlLvlArray(String t, IStringable[] is) {
      if (is == null) {
         nlLvlArray(t, is, 0, 0);
      } else {
         nlLvlArray(t, is, 0, is.length);
      }
   }

   public void nlLvlArray(String t, IStringable[] is, int offset, int len) {
      nl();
      if (is == null) {
         append(t + " is null");
      } else {
         sb.append("#" + t);
         sb.append(" from " + offset + " to " + (offset + len - 1));
         sb.append(" len");
         sb.append('=');
         sb.append(is.length);
         for (int i = offset; i < offset + len; i++) {
            if (is[i] == null) {
               nnl();
               sb.append(i + " = null");
            } else if (is[i] instanceof IStringable) {
               IStringable io = (IStringable) is[i];
               //TODO we 
               nlLvl(String.valueOf(i), io);
            }
         }
      }
   }

   /**
    * 
    * @param string
    * @param is
    */
   public void nlLvlArray1Line(String string, Object[] is) {
      nl();
      sb.append("#" + string);
      sb.append('=');
      sb.append(is.length);
      for (int i = 0; i < is.length; i++) {
         nnl();
         if (is[i] == null) {
            sb.append("null");
         } else if (is[i] instanceof IStringable) {
            IStringable io = (IStringable) is[i];
            io.toString1Line(this);
         } else {
            sb.append(is[i].toString());
         }
      }
   }

   public void nlLvlArray1Line(String string, Object[] is, int offset, int len) {
      nl();
      sb.append("#" + string);
      sb.append(" from " + offset + " to " + (offset + len));
      sb.append(" RealLen");
      sb.append('=');
      sb.append(is.length);
      for (int i = offset; i < offset + len; i++) {
         nnl();
         sb.append(i + " = ");
         if (is[i] == null) {
            sb.append("null");
         } else if (is[i] instanceof IStringable) {
            IStringable io = (IStringable) is[i];
            io.toString1Line(this);
         } else {
            //cut it after the first \n
            sb.append(uc.getStrU().trimAtNewLine(is[i].toString()));
         }
      }
   }

   /**
    * 
    * @param t
    * @param is
    * @param sp
    */
   public void nlLvlArrayNoNLIfNull(String t, IStringable[] is, String sp) {
      if (is == null) {
         append(sp);
         append(t + " is null");
      } else {
         nl();
         sb.append("#" + t);
         sb.append('=');
         sb.append(is.length);
         for (int i = 0; i < is.length; i++) {
            if (is[i] == null) {
               nnl(); //we have a new line below
               sb.append(i + " = null");
            } else if (is[i] instanceof IStringable) {
               IStringable io = (IStringable) is[i];
               String msg = String.valueOf(i + 1);
               nlLvl(msg, io);
            }
         }
      }
   }

   /**
    * Pretty print array with first line for root and a double tab for 
    * @param t
    * @param is
    */
   public void nlLvlArrayNotNull(String t, IStringable[] is) {
      if (is != null) {
         sb.append("#" + t);
         sb.append('=');
         sb.append(is.length);
         tab();
         for (int i = 0; i < is.length; i++) {
            nl();
            sb.append(i);
            sb.append(' ');
            if (is[i] == null) {
               sb.append(" null");
            } else if (is[i] instanceof IStringable) {
               IStringable io = (IStringable) is[i];
               Dctx nd = Level();
               nd.tab();
               io.toString(nd);
            }
         }
      }
   }

   public void nlLvlArrayNotNull(String t, IStringable[] is, int offset, int len) {
      if (is != null) {
         sb.append("#" + t);
         sb.append('=');
         sb.append(len);
         sb.append(" [" + offset + "," + len + " " + is.length + "]");
         tab();
         for (int i = offset; i < len; i++) {
            nl();
            sb.append(i);
            sb.append(' ');
            if (is[i] == null) {
               sb.append(" null");
            } else if (is[i] instanceof IStringable) {
               IStringable io = (IStringable) is[i];
               Dctx nd = Level();
               nd.tab();
               io.toString(nd);
            }
         }
      }
   }

   public void nlLvlIgnoreNull(String t, IStringable is) {
      Dctx dc = new Dctx(uc, this);
      dc.flags = flags;
      dc.nl();
      if (is != null) {
         if (processedObjects.hasObject(is)) {
            dc.append(t);
            dc.append(" ");
            is.toString1Line(dc);
         } else {
            processedObjects.add(is);
            dc.append(t);
            dc.append(" ");
            is.toString(dc);
         }
      }
   }

   /**
    * Indent if not not line
    * @param title
    * @param is
    */
   public void nlLvlIndentIfNotNull(String title, IStringable is) {
      Dctx dc = nLevel(flags);
      if (is == null) {
         dc.append(title + " is Null");
      } else {
         dc.append(title);
         if (processedObjects.hasObject(is)) {
            is.toString1Line(dc);
         } else {
            //we don't add it because it will be added in the call below
            //processedObjects.add(is); 
            dc.nlLvl(is);
         }
      }
   }

   public void nlLvlList(String t, IStringable is) {
      nlLvl(t, is, 0, false, true);
   }

   public void nlLvlList(String title, IStringable is, int flags, boolean flip) {
      nlLvl(title, is, flags, flip, true);
   }

   public void nlLvlNoTitle(String t, IStringable is) {
      nlLvl(t, is, 0);
   }

   /**
    * Does not show the title when not null. List 
    * @param t
    * @param is
    * @param flags
    */
   public void nlLvlNoTitle(String t, IStringable is, int flags) {
      Dctx dc = nLevel(flags);
      if (is == null) {
         dc.append(t + " is Null");
      } else {
         if (processedObjects.hasObject(is)) {
            is.toString1Line(dc);
         } else {
            processedObjects.add(is);
            is.toString(dc);
         }
      }
   }

   public void nlLvlNoTitleList(String t, IStringable is) {
      Dctx dc = nLevel(0);
      if (is == null) {
         nulls.add(t);
      } else {
         if (processedObjects.hasObject(is)) {
            is.toString1Line(dc);
         } else {
            processedObjects.add(is);
            is.toString(dc);
         }
      }
   }

   public void nlLvlObject(String string, Object o) {
      Dctx dc = new Dctx(uc, this);
      if (o == null) {
         dc.append("null");
      } else if (o instanceof IStringable) {
         IStringable consumer = (IStringable) o;
         if (isExpand()) {
            consumer.toString(dc);
         } else {
            consumer.toString1Line(dc);
         }
      } else {
         String str = o.toString();
         dc.append(str);
      }
   }

   public void nlLvlOneLine(IStringable is) {
      nl();
      is.toString1Line(this);
   }

   public void nlLvlOneLine(IStringable is, String t) {
      nlLvlOneLine(t, is);
   }

   public void nlLvlOneLine(String t, IStringable is) {
      Dctx dc = nLevel();
      if (is == null) {
         dc.append(t + " is Null");
      } else {
         if (t.length() != 0) {
            dc.append(t);
            dc.append("=");
         }
         is.toString1Line(dc);
      }
   }

   /**
    * Print without a new level, Title= {@link IStringable#toString1Line()}.
    * <br>
    * The title is there to differentiate identic types and when object is null.
    * <br>
    * Otherwise title is probably implicit, user only wants it to be shown when object is null.
    * <br>
    * @param t
    * @param is
    */
   public void sameLine1(String t, IStringable is) {
      Dctx dc = this;
      if (is == null) {
         dc.append(t + " is null");
      } else {
         if (t != null && t.length() != 0) {
            dc.append(t);
            dc.append("=");
         }
         is.toString1Line(dc);
      }
   }

   public void nlThread(String title, Thread t) {
      Dctx dc = nLevel(0);
      if (t == null) {
         dc.append(title + " is null");
      } else {
         dc.append(title);
         dc.appendVarWithSpace("Name", t.getName());
         dc.appendVarWithSpace("State", t.getState().toString());

      }
   }

   public void nlVar(String s, boolean v) {
      sb.append(nl);
      sb.append(s);
      sb.append('=');
      if (v) {
         sb.append("true");
      } else {
         sb.append("false");
      }
   }

   /**
    * Add
    * nl s=v
    * @param s
    * @param v
    */
   public void nlVar(String s, int v) {
      nl();
      appendVar(s, v);
   }

   public void nlVar(String s, String v) {
      sb.append(nl);
      appendVar(s, v);
   }

   public void nlVarOneLine(String string, IStringable is) {
      sb.append(nl);
      if (is != null) {
         sb.append(string);
         sb.append('=');
         is.toString1Line(this);
      } else {
         sb.append(string);
         sb.append('=');
         sb.append("null");
      }
   }

   public void nlVarOneLineListNull(String string, IStringable is) {
      if (is != null) {
         nl();
         sb.append(string);
         sb.append('=');
         is.toString1Line(this);
      } else {
         nulls.add(string);
      }
   }

   public void nnl() {
      sb.append(nl + "\t");
   }

   public Dctx nnLvl() {
      Dctx c = new Dctx(uc, this, 2);
      return c;
   }

   public void nnnl() {
      sb.append(nl + "\t\t");
   }

   public void oneLine(IStringable is) {
      is.toString1Line(this);
   }

   public void printExclusive(Dctx deviceD) {
      append(deviceD.toString());
   }

   /**
    * Remove NL temporaryly and replace it with a space
    */
   public String removeNL() {
      String n = nl;

      return n;
   }

   public String replaceNL(String str) {
      String n = nl;
      nl = str;
      return n;
   }

   /**
    * Go back v chars, "erasing" them
    * @param v
    */
   public void reverse(int v) {
      sb.decrementCount(v);
   }

   /**
    * Displays a root. 
    * <br>
    * Link Str to IStringable
    * @param o
    * @param str
    */
   public void root(Object o, String str) {
      sb.append('#');
      append(str);
      doRootTitle();
      processedObjects.add(o);
      processedObjects.add(str);
      tab();
   }

   public void root1Line(Object o, String str) {
      sb.append('#');
      sb.append(str);
      setCompact(true);
      doRootTitle();
   }

   public void rootSub(String str) {
      sb.append('#');
      append(str);
      tab();
   }

   public void setCompact(boolean b) {
      isCompact = b;
   }

   public void setExpand(boolean v) {
      setFlag(FLAG_1_EXPAND, v);
   }

   public void setFlag(int flag, boolean v) {
      flags = BitUtils.setFlag(flags, flag, v);
   }

   private void setRootTitle(String title) {
      rootTitle = title;
   }

   /**
    * Called when going upwards one level in a Class Definition.
    * <br>
    * Prints a newline
    * @return
    */
   public Dctx sup() {
      Dctx d = nLevel();
      return d;
   }

   public Dctx sup1Line() {
      append(" ");
      return this;
   }

   /**
    * Increase tabulation
    */
   public void tab() {
      nl = nl + "│   ";
      nlNum++;
   }

   /**
    * Decrease tabulation
    */
   public void tabRemove() {
      if (nlNum > 0) {
         nlNum--;
         nl = "\n";
         for (int i = 0; i < nlNum; i++) {
            nl = nl + "│   ";
         }
      }
   }

   /**
    * 
    */
   public void tick() {
      tick = sb.getCount();
   }

   public String toString() {
      return sb.toString();
   }

   public void nlLvlVector(Vector v) {
      if (v == null) {
         this.append("Vector is null");
      } else {
         Iterator it = v.iterator();
         int count = 0;
         while (it.hasNext()) {
            Object o = it.next();
            if (o instanceof IStringable) {
               count++;
               this.nlLvl((IStringable) o, "Vector#" + count);
            } else {
               this.append(o.getClass().getName());
            }
         }
      }
   }

   /**
    * If {@link IStringable}, all is good, otherwise ask {@link ICtx} to debug it
    * @param o
    * @param title
    */
   public void nlLvlO(Object o, String title) {
      this.nlLvlO(o, title, uc);
   }

   /**
    * If {@link IStringable}, all is good, otherwise ask {@link ICtx} to debug it
    * @param o
    * @param title only used if object is null
    * @param ctx {@link ICtx} provides the ctx for the toString
    */
   public void nlLvlO(Object o, String title, ICtx ctx) {
      if (o == null) {
         nlLvl(null, title);
      } else {
         if (o instanceof IStringable) {
            nlLvlTitleIfNull((IStringable) o, title);
         } else {
            uc.toString(this, o, title);
         }
      }
   }

   /**
    * Append on the same line, the Debug of Object
    * @param o
    * @param title
    * @param ctx
    */
   public void sameLineO1(Object o, String title, ICtx ctx) {
      if (o == null) {
         sameLine1(title, null);
      } else {
         if (o instanceof IStringable) {
            sameLine1(title, (IStringable) o);
         } else {
            ctx.toString1(this, o, title);
         }
      }
   }
   //#enddebug
}
