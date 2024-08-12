/*
 * (c) 2018-2020 Charles-Philip Bentley
 * This code is licensed under MIT license (see LICENSE.txt for details)
 */
package pasa.cbentley.core.src4.logging;

import java.util.Enumeration;
import java.util.Vector;

import pasa.cbentley.core.src4.ctx.ACtx;
import pasa.cbentley.core.src4.ctx.ICtx;
import pasa.cbentley.core.src4.ctx.IToStringFlags;
import pasa.cbentley.core.src4.ctx.UCtx;
import pasa.cbentley.core.src4.helpers.CounterInt;
import pasa.cbentley.core.src4.helpers.StringBBuilder;
import pasa.cbentley.core.src4.structs.IntToObjects;
import pasa.cbentley.core.src4.structs.IntToStrings;
import pasa.cbentley.core.src4.utils.ArrayUtils;
import pasa.cbentley.core.src4.utils.BitUtils;
import pasa.cbentley.core.src4.utils.CharUtils;
import pasa.cbentley.core.src4.utils.ColorUtils;
import pasa.cbentley.core.src4.utils.StringUtils;

/**
 * Bastard context (lowercase C letter), so not a code context. 
 * It provides a debug context to the {@link IStringable#toString(Dctx)} and {@link IStringable#toString1Line(Dctx)} methods
 * <br>
 * <br>
 * 
 * Provides extensive support methods for writing {@link IStringable#toString()} methods
 * which basically all objects in our code implement.
 * <br>
 * 
 * <li>Tracks which class has been toString.
 * Tracks the whole toString from the start by using a {@link IntToObjects}
 * to track debugged classes.
 * 
 * @author Charles Bentley
 *
 */
public class Dctx implements IToStringFlags {
   //#mdebug

   /**
    * Static method for debugging. Supposedly to be inlined but since its debug code, it will be removed in production.
    * Makes a 3 line method into a 1 line.
    * @param is
    * @return
    */
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

   private String         defaultLine     = "30";

   private int            flags;

   private IntToObjects   flagsData;

   private boolean        isClassLinks;

   private boolean        isCompact;

   private boolean        isExpand;

   private boolean        isLineNumbers   = true;

   private char[]         linesArray      = new char[] { '│', '↑', '║', '↓', '├' };

   /**
    *  ⋮ ⼁ ︴｜ ⍭  ⎜ ⎨ ⧸ ┠ ∫  '┇' ║ │ ├ ▌ █
    *    https://www.compart.com/en/unicode/block/U+2500 box drawing
    */
   private char[]         linesArrayStart = new char[] { '┌', '→', '╓', '←', '╒' };

   private String         nlRoot;

   private String         nlTabs;

   private IntToStrings   nulls;

   private CounterInt     numLines;

   private int            numTabs;

   private Dctx           parent;

   /**
    * Track multi lines objects and the line at which it is printed
    */
   private IntToObjects   processedObjectsMulti;

   /**
    * Track single line objects and the line at which it is printed
    */
   private IntToObjects   processingObjectsSingle;

   private StringBBuilder sb;

   private String         strInterTab     = "  ";

   private int            tick;

   private String         titlePrefix;

   private String         titleSuffix;

   private IntToObjects   track;

   private IntToObjects   trackedCtx;

   private UCtx           uc;

   public Dctx(UCtx uc) {
      this(uc, "\n");
   }

   /**
    * Creates a {@link Dctx} with parent
    * 
    * @param uc
    * @param parent
    * @param tabNumExtra
    */
   private Dctx(UCtx uc, Dctx parent) {
      if (uc == null) {
         throw new NullPointerException();
      }
      this.uc = uc;
      this.parent = parent;
      this.sb = parent.sb;
      //tab method is called during the root method once the title has been written
      this.nlRoot = parent.nlRoot;
      this.nlTabs = parent.nlTabs;
      this.numTabs = parent.numTabs;
      //must be a reference t
      this.numLines = parent.numLines;

      this.nulls = new IntToStrings(uc);
      this.processedObjectsMulti = parent.processedObjectsMulti;
      this.flagsData = parent.flagsData;
      this.trackedCtx = parent.trackedCtx;
      this.processingObjectsSingle = parent.processingObjectsSingle;

      this.isClassLinks = parent.isClassLinks;
      this.isCompact = parent.isCompact;
      this.isExpand = parent.isExpand;
      this.isLineNumbers = parent.isLineNumbers;
   }

   /**
    * 
    * @param uc
    * @param nl provides the base nl string
    */
   public Dctx(UCtx uc, String nl) {
      if (uc == null) {
         throw new NullPointerException();
      }
      this.uc = uc;
      if (nl == null || nl.length() == 0) {
         nl = "\n";
      }
      this.nlRoot = nl;
      this.nlTabs = "";
      sb = new StringBBuilder(uc, 4000);
      nulls = new IntToStrings(uc);
      flagsData = new IntToObjects(uc);
      processedObjectsMulti = new IntToObjects(uc);
      trackedCtx = new IntToObjects(uc);
      processingObjectsSingle = new IntToObjects(uc);
      numLines = new CounterInt(uc);
      isClassLinks = uc.getConfigU().toStringIsUsingClassLinks();
   }

   public void append(boolean v) {
      sb.append(String.valueOf(v));
   }

   public void append(Boolean v) {
      if (v == null) {
         sb.append("null");
      } else {
         this.append(v.booleanValue());
      }
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

   public void append(double v) {
      sb.append(v);
   }

   public void append(Double v) {
      if (v == null) {
         sb.append("null");
      } else {
         sb.append(v.doubleValue());
      }
   }

   public void append(int v) {
      sb.append(v);
   }

   public void append(Integer v) {
      if (v == null) {
         sb.append("null");
      } else {
         sb.append(v.intValue());
      }
   }

   public void append(Long v) {
      if (v == null) {
         sb.append("null");
      } else {
         sb.append(v.longValue());
      }
   }

   public void append(String string) {
      sb.append(string);
   }

   public void appendClassLink(Class cl) {
      String str = this.getClassSimpleName(cl);
      appendClassLink(str, "40");
   }

   public void append(String[] strs, int offset, int len, String sep) {
      for (int i = offset; i < offset + len; i++) {
         if (i != offset) {
            sb.append(sep);
         }
         sb.append(strs[i]);
      }
   }

   public void append(String[] strs, String sep) {
      this.append(strs, 0, strs.length, sep);
   }

   public void appendBracketedWithSpace(String str) {
      this.append(' ');
      this.append('[');
      this.append(str);
      this.append(']');
   }

   public void appendBracketedWithSpace(boolean b) {
      this.append(' ');
      this.append('[');
      this.append(String.valueOf(b));
      this.append(']');
   }

   public void appendColorRGB(int colorRGB) {
      sb.append(uc.getColorU().toStringColorRGB(colorRGB));
   }

   public void appendColorWithName(int rgb) {
      this.appendColorWithName("color", rgb);
   }

   public void appendColorWithName(String color, int rgb) {
      sb.append(color);
      sb.append('=');
      ColorUtils colorU = uc.getColorU();
      colorU.toStringColorWithName(rgb, this);
   }

   public void appendColorWithSpace(String color, int rgb) {
      sb.append(' ');
      sb.append(color);
      sb.append('=');
      sb.append(uc.getColorU().toStringColor(rgb));
   }

   public void appendFlags(int flags, String title, IntToStrings data, boolean isPositive) {
      sb.append(title);
      if (isPositive) {
         sb.append(" as TRUE = ");
      } else {
         sb.append(" as FALSE = ");
      }
      int count = 0;
      for (int i = 0; i < data.getSize(); i++) {
         int flag = data.getInt(i);
         boolean hasFlag = BitUtils.hasFlag(flags, flag);
         if ((isPositive && hasFlag) || (!isPositive && !hasFlag)) {
            String str = data.getString(i);
            if (count != 0) {
               sb.append(",");
            }
            sb.append(str);
            count++;
         }
      }
   }

   public void appendFlagsNewLine(int flags, String title, IntToStrings data) {
      nl();
      appendFlagsPositive(flags, title, data);
      nl();
      appendFlagsNegative(flags, title, data);
   }

   public void appendFlagsNegative(int flags, String title, IntToStrings data) {
      this.appendFlags(flags, title, data, false);
   }

   public void appendFlagsPositive(int flags, String title, IntToStrings data) {
      this.appendFlags(flags, title, data, true);
   }

   /**
    * Appends the characters of string ignoring the given c
    * @param string
    * @param c
    */
   public void appendIgnoreChar(String string, char c) {
      int len = string.length();
      for (int i = 0; i < len; i++) {
         char ch = string.charAt(i);
         if (ch != c) {
            sb.append(ch);
         }
      }
   }

   public void appendVarPretty(String var, float value, int numDecimals) {
      sb.append(var);
      sb.append('=');
      String str = uc.getStrU().prettyFloat(value, numDecimals);
      sb.append(str);
   }

   public void appendPretty(float value, int numDecimals) {
      String str = uc.getStrU().prettyFloat(value, numDecimals);
      append(str);
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

   /**
    * 
    * @param title
    * @param array
    * @param columnSize
    */
   public void appendVar(String title, byte[] array, int columnSize, String sep) {
      sb.append(title);
      sb.append('=');
      if (array == null) {
         sb.append("byte[] is null");
      } else {
         if (columnSize < 0) {
            columnSize = 1;
         }
         int count = 0;
         for (int i = 0; i < array.length; i++) {
            if (count == columnSize) {
               this.nl();
               count = 0;
            }
            sb.append(String.valueOf(array[i]));
            sb.append(sep);
            count++;
         }
      }

   }

   public void appendVar(String s, int v) {
      sb.append(s);
      sb.append('=');
      sb.append(v);
   }

   public void appendVar(String s, long v) {
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

   public void appendVar(String s, String v) {
      sb.append(s);
      sb.append('=');
      sb.append(v);
   }

   public void appendVarWithNewLine(String s, boolean v) {
      nl();
      sb.append(s);
      sb.append('=');
      sb.append(String.valueOf(v));
   }

   public void appendVarWithNewLine(String s, int v) {
      nl();
      sb.append(s);
      sb.append('=');
      sb.append(v);
   }

   public void appendVarWithNewLine(String s, int[] ar, String sep, boolean size) {
      this.nl();
      debugAlone(s, ar, sep, size);
   }

   public void appendVarWithNewLine(String s, String[] ar, String sep, boolean size) {
      this.nl();
      debugAlone(s, ar, sep, size);
   }

   public void appendVarWithNewLine(String s, String v) {
      nl();
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

   public void appendVarWithSpace(String s, double v) {
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

   public void appendVarWithSpace(String s, float v) {
      sb.append(' ');
      sb.append(s);
      sb.append('=');
      sb.append(String.valueOf(v));
   }

   public void appendVarWithSpace(String s, float v, int numDecimals) {
      sb.append(' ');
      sb.append(s);
      sb.append('=');
      sb.append(uc.getStrU().prettyFloat(v, numDecimals));
   }

   public void appendVarWithSpace(String s, int v) {
      sb.append(' ');
      sb.append(s);
      sb.append('=');
      sb.append(v);
   }

   public void appendVarWithSpace(String s, int[] ar) {
      debugAlone(s, ar, " ");
   }

   public void appendVarWithSpace(String s, int[] ar, String sep) {
      debugAlone(s, ar, sep);
   }

   public void appendVarWithSpace(String s, int[] ar, String sep, boolean size) {
      sb.append(' ');
      debugAlone(s, ar, sep, size);
   }

   public void appendVarWithSpace(String s, Integer v) {
      if (v == null) {
         this.appendVarWithSpace(s, "null");
      } else {
         this.appendVarWithSpace(s, v.intValue());
      }
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

   public void appendVarWithSpace(String s, Object v) {
      sb.append(' ');
      appendVar(s, v);
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

   public void appendWithNewLine(String string) {
      this.nl();
      sb.append(string);
   }

   public void appendWithSpaceArrayNullOnly(Object[] ar, String t) {
      if (ar == null) {
         if (t.endsWith("s")) {
            append(t + " are nulls");
         } else {
            append(t + " is null");
         }
         return;
      }
   }

   public void appendWithSpaceIfNotNull(char c, String name, char d) {
      if (name != null) {
         this.append(' ');
         this.append(c);
         this.append(name);
         this.append(d);
      }
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

   public void debugAlone(int[] ar, String sep) {
      for (int i = 0; i < ar.length; i++) {
         if (i != 0) {
            append(sep);
         }
         append(ar[i]);
      }
   }

   public void debugAlone(String title, int[] ar, String sep) {
      sb.append(title);
      for (int i = 0; i < ar.length; i++) {
         if (i != 0) {
            append(sep);
         }
         append(ar[i]);
      }
   }

   public void debugAlone(String title, int[] ar, String sep, boolean size) {
      sb.append(title);
      sb.append('=');
      if (ar == null) {
         sb.append("null");
         return;
      }
      if (size) {
         sb.append('#');
         sb.append(ar.length);
      }
      sb.append('[');
      for (int i = 0; i < ar.length; i++) {
         if (i != 0) {
            append(sep);
         }
         append(ar[i]);
      }
      sb.append(']');
   }

   public void debugAlone(String title, String[] ar, String sep, boolean size) {
      sb.append(title);
      sb.append('=');
      if (ar == null) {
         sb.append("null");
         return;
      }
      if (size) {
         sb.append('#');
         sb.append(ar.length);
      }
      sb.append('[');
      for (int i = 0; i < ar.length; i++) {
         if (i != 0) {
            append(sep);
         }
         append(ar[i]);
      }
      sb.append(']');
   }

   private void doTitlePrefix() {
      if (titlePrefix != null) {
         sb.append(titlePrefix);
         sb.append('=');
      }
   }

   private void doTitleSuffix() {
      if (titleSuffix != null && titleSuffix.length() != 0) {
         sb.append(' ');
         sb.append('[');
         sb.append(titleSuffix);
         sb.append(']');
         titleSuffix = null;
      }
   }

   private String getClassSimpleName(Class cl) {
      StringUtils strU = uc.getStrU();
      return strU.getNameClass(cl);
   }

   public int getCount() {
      return sb.getCount();
   }

   private char getLevelStartChar() {
      return linesArrayStart[numTabs % linesArrayStart.length];
   }

   private char getLevelStartChar1Line() {
      return '→';
   }

   public String getNlTab(int numTabs) {
      StringBBuilder sb = new StringBBuilder(uc);
      for (int i = 0; i < numTabs; i++) {
         char c = linesArray[i % linesArray.length];
         sb.append(c);
         sb.append(strInterTab);
      }
      return sb.toString();
   }

   public StringBBuilder getSB() {
      return sb;
   }

   /**
    * Flag from a marked prefixed interface {@link IToStringFlags}
    * 
    * If {@link ICtx} has not registered flags values with
    * 
    * {@link Dctx#setFlagData(ICtx, int, boolean)}, method calls the global method
    * 
    * {@link ICtx#toStringHasToStringFlag(int)} which can be set externall with
    * 
    * {@link ACtx#toStringSetToStringFlag(int, boolean)}
    * @param ctx
    * @param flag
    * @return
    */
   public boolean hasFlagData(ICtx ctx, int flag) {
      int index = flagsData.findObjectRef(ctx);
      if (index == -1) {
         return ctx.toStringHasToStringFlag(flag);
      } else {
         return flagsData.hasIntFlag(index, flag);
      }
   }

   public boolean isCompact() {
      return isCompact;
   }

   /**
    * true when showing multi line objects in arrays and data structures such as {@link IntToObjects}
    * @return
    */
   public boolean isExpand() {
      return isExpand;
   }

   public boolean isLineNumbers() {
      return isLineNumbers;
   }

   public boolean isTick() {
      return tick == sb.getCount();
   }

   public Dctx Level() {
      Dctx dc = new Dctx(uc, this);
      return dc;
   }

   public void line() {
      this.nl();
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

   /**
    * Creates a new {@link Dctx} child of the current one.
    * <br>
    * A new line is appended.
    * Tabulation is not increased.
    * 
    * @return {@link Dctx}
    */
   public Dctx newLevel() {
      int flags = 0;
      return newLevel(flags);
   }

   /**
    * New level with overriding flags
    * @param flags
    * @return
    */
   public Dctx newLevel(int flags) {
      nl();
      Dctx c = new Dctx(uc, this);
      c.flags = flags;
      return c;
   }

   public Dctx newLevel1Line() {
      append(' ');
      Dctx dc = new Dctx(uc, this);
      return dc;
   }

   public Dctx newLevelTab() {
      nl();
      Dctx dc = new Dctx(uc, this);
      dc.tab();
      return dc;
   }

   public void nl() {
      numLines.increment();
      if (isLineNumbers) {
         String strLineNumber = uc.getStrU().prettyIntPaddStr(numLines.getCount(), 4, " ");
         sb.append(nlRoot);
         sb.append(strLineNumber);
         sb.append(nlTabs);
      } else {
         sb.append(nlRoot);
         sb.append(nlTabs);
      }
   }

   public void nlAppend(String string) {
      nl();
      append(string);
   }

   public void nlArrayRaw(Object[] param, String string) {
      this.nl();
      if (param == null) {
         sb.append(string + " is null");
      } else {
         sb.append(string + " size=" + param.length);
         int countNulls = ArrayUtils.countNulls(param);
         sb.append("nulls=");
         sb.append(countNulls);
      }
   }

   public void nlFrontTitle(int[] data, String title) {
      this.nl();
      if (title != null) {
         append(title);
         append("=");
      }
      if (data != null) {
         String str = uc.getIU().debugString(data);
         this.append(str);
      } else {
         this.append(" int[] is null");
      }
   }

   public void nlLvl(IntToObjects ito, String t, String titleRow, IStringableInt stringerInt) {
      nl();
      if (ito == null) {
         sb.append(t + " is null");
      } else {
         int num = ito.getLength();
         sb.append(t);
         sb.append(" with ");
         sb.append(num);
         sb.append(" elements");
         for (int i = 0; i < num; i++) {
            nl();
            append("         ////////////////////////    ");
            append(i);
            if (stringerInt != null) {
               append(":");
               append(stringerInt.toString(ito.ints[i]));
            }
            append("    \\\\\\\\\\\\\\\\\\\\\\\\\\\\");
            nl();
            append("#");
            append(i);
            append(" -> ");
            append(titleRow);
            append(" [");
            append(ito.ints[i]);
            append("]");
            if (stringerInt != null) {
               append(" ");
               append(stringerInt.toString(ito.ints[i]));
            }
            Object o = ito.objects[i];
            if (o instanceof IStringable) {
               nlLvl("", (IStringable) o);
            } else {
               nl();
               append(o.toString());
            }
         }
      }
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
    * Suffixed title to {@link IStringable} root name.
    * <br>
    * Title is the simple class name.
    * @param str
    * @param class1
    */
   public void nlLvl(IStringable str, Class class1) {
      String title = getClassSimpleName(class1);
      nlLvl(str, title);
   }

   /**
    * Suffixed title to {@link IStringable} root name.
    * <br>
    * Cue is because the title parameter is after {@link IStringable}.
    * @param is
    * @param title
    */
   public void nlLvl(IStringable is, String title) {
      nlLvl(title, is, 0, true);
   }

   public void nlLvl(IStringable is, String title, Class cl) {
      nlLvl(title, is, 0, true, cl);
   }

   /**
    * 
    * @param title
    * @param ints
    * @param numIntPerLine
    */
   public void nlLvl(String title, int[] ints, int numIntPerLine) {
      nl();
      if (ints == null) {
         sb.append(title + " is null");
      } else {
         sb.append(title);
         this.tab();
         this.nl();
         int count = 0;
         for (int j = 0; j < ints.length; j++) {
            sb.append(ints[j]);
            sb.append(" ");
            count++;
            if (count == numIntPerLine) {
               count = 0;
               this.nl();
            }
         }
         this.tabRemove();
      }
   }

   public void nlLvl(String title, char[] cs, int numIntPerLine) {
      nl();
      if (cs == null) {
         sb.append(title + " is null");
      } else {
         sb.append(title);
         this.nl();
         this.tab();
         int count = 0;
         for (int j = 0; j < cs.length; j++) {
            sb.append(cs[j]);
            sb.append(" ");
            count++;
            if (count == numIntPerLine) {
               count = 0;
               this.nl();
            }
         }
         this.tabRemove();
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

   /**
    * Suffixed title to the root name
    * @param title
    * @param is
    * @param flags
    * @param isTitleSuffix
    */
   public void nlLvl(String title, IStringable is, int flags, boolean isTitleSuffix) {
      nlLvl(title, is, flags, isTitleSuffix, false);
   }

   public void nlLvl(String title, IStringable is, int flags, boolean isTitleSuffix, Class cl) {
      nlLvl(title, is, flags, isTitleSuffix, false, cl);
   }

   public void nlLvl(String title, IStringable is, int flags, boolean flip, boolean list) {
      nlLvl(title, is, flags, flip, list, true);
   }

   public void nlLvl(String title, IStringable is, int flags, boolean flip, boolean list, Class cl) {
      nlLvl(title, is, flags, flip, list, true, cl);
   }

   public void nlLvl(String title, IStringable is, int flags, boolean isTitleSuffix, boolean listNulls, boolean showTitleWhenNotNull) {
      nlLvl(title, is, flags, isTitleSuffix, listNulls, showTitleWhenNotNull, null);
   }

   /**
    * 
    * @param title
    * @param is
    * @param flags
    * @param isTitleSuffix when true, the title is printed after the root name, when false title=
    * @param listNulls when true, if the {@link IStringable} is null, it is added to a list of nulls for current level.
    * @param showTitleWhenNotNull when true, show title when {@link IStringable} is not null.
    * That list is printed by the call {@link Dctx#listNulls()}
    */
   public void nlLvl(String title, IStringable is, int flags, boolean isTitleSuffix, boolean listNulls, boolean showTitleWhenNotNull, Class cl) {
      if (is == null) {
         if (listNulls) {
            nulls.add(title);
         } else {
            Dctx dc = newLevel(flags);
            if (cl == null) {
               dc.append(getLevelStartChar());
               dc.append(' ');
               dc.append(title + " is Null");
            } else {
               dc.append(getLevelStartChar());
               dc.append(' ');
               sb.append('(');
               String strCl = getClassSimpleName(cl);
               append(strCl);
               append(".java:");
               append(15);
               append(")");
               dc.append(' ');
               dc.append(title + " is Null");
            }
         }
      } else {
         Dctx dc = newLevel(flags);
         if (showTitleWhenNotNull) {
            if (title.length() != 0) {
               if (isTitleSuffix) {
                  //set root title
                  dc.setTitleSuffix(title);
               } else {
                  //title
                  dc.setTitlePrefix(title);
               }
            }
         }
         int referenceIndex = processedObjectsMulti.getObjectIndex(is);
         if (referenceIndex != -1) {
            //TODO sets a string reference to the object id
            //by using in the int to object id as the reference
            //dc.setRootReference(reference);
            if (isLineNumbers) {
               int lineNum = processedObjectsMulti.getInt(referenceIndex);
               String suffix = "@Line " + lineNum + "";
               dc.setTitleSuffix(suffix);
               this.lineForCollapsed = lineNum;
            }
            dc.setFlag(FLAG_DATA_07_COLLAPSED, true);
            is.toString1Line(dc);
         } else {
            is.toString(dc);
         }
      }
   }

   public void nlLvl1Line(IStringable is) {
      nl();
      int index = processingObjectsSingle.getObjectIndex(is);
      if (index != -1) {
         String str = getClassSimpleName(is.getClass());
         if (isLineNumbers) {
            append(getLevelStartChar());
            append("@Line");
            append(processingObjectsSingle.getInt(index));
            append("]");
            append(str);
         } else {
            append(str + " is already oneline printed above");
         }
      } else {
         processingObjectsSingle.add(is, numLines.getCount());
         is.toString1Line(this);
      }
   }

   /**
    * 
    * @param is
    * @param title when null, ignores the title
    */
   public void nlLvl1Line(IStringable is, String title) {
      //break cycle don't print if we are currently printing
      //this one liner
      if (processingObjectsSingle.hasObject(is)) {
         String str = getClassSimpleName(is.getClass());
         append(title + " is already printed above");
      } else {
         Dctx dc = newLevel();
         if (is == null) {
            dc.append(title + " is Null");
         } else {
            if (title.length() != 0) {
               dc.append(title);
               dc.append("=");
            }
            processingObjectsSingle.add(is);
            is.toString1Line(dc);
         }
      }
   }

   /**
    * Considered {@link IStringable} inside, otherwise {@link ClassCastException}
    * @param ar
    * @param t
    */
   public void nlLvlArray(Object[] ar, String t) {
      if (ar == null) {
         nl();
         append(t + " is null");
         return;
      }
      IStringable[] ars = new IStringable[ar.length];
      for (int i = 0; i < ar.length; i++) {
         ars[i] = (IStringable) ar[i];
      }
      nlLvlArray(t, ars);
   }

   /**
    * one line or not?
    * @param title
    * @param is
    */
   public void nlLvlArray(String title, IStringable[] is) {
      if (is == null) {
         nlLvlArray(title, is, 0, 0);
      } else {
         nlLvlArray(title, is, 0, is.length);
      }
   }

   /**
    * Shows title when null.
    * @param title
    * @param is
    * @param offset
    * @param len
    */
   public void nlLvlArray(String title, IStringable[] is, int offset, int len) {
      nl();
      if (is == null) {
         append(title + " is [null]"); //tells us its an array
      } else {
         sb.append(getLevelStartChar());
         sb.append("[");
         sb.append(title);
         sb.append("]");
         if (offset == 0 && is.length == len) {
            //usual case of full array
            appendVarWithSpace("size", len);
         } else {
            sb.append(" [");
            sb.append(" from offset " + offset + " to " + (offset + len - 1));
            sb.append(" len");
            sb.append('=');
            sb.append(is.length);
            sb.append("]");
         }
         for (int i = offset; i < offset + len; i++) {
            tab();
            String stri = String.valueOf(i);
            if (is[i] == null) {
               nl();
               sb.append(i + " = null");
            } else if (is[i] instanceof IStringable) {
               IStringable io = (IStringable) is[i];
               nlLvl(stri, io);
            }
            tabRemove();
         }
      }
   }

   public void nlLvlArray1Line(Object[] is, int offset, int len, String string) {
      nl();
      sb.append(getLevelStartChar());
      sb.append(string);
      sb.append(" from " + offset + " to " + (offset + len));
      sb.append(" RealLen");
      sb.append('=');
      sb.append(is.length);
      for (int i = offset; i < offset + len; i++) {
         nl();
         tab();
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
         tabRemove();
      }
   }

   /**
    * 
    * @param is
    * @param string
    */
   public void nlLvlArray1Line(Object[] is, String string) {
      nl();
      sb.append(getLevelStartChar());
      sb.append(string);
      sb.append('=');
      sb.append(is.length);
      for (int i = 0; i < is.length; i++) {
         nl();
         tab();
         if (is[i] == null) {
            sb.append("null");
         } else if (is[i] instanceof IStringable) {
            IStringable io = (IStringable) is[i];
            io.toString1Line(this);
         } else {
            sb.append(is[i].toString());
         }
         tabRemove();
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
               nl(); //we have a new line below
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

   /**
    * Prints the array only if not null.
    * @param ar
    * @param t
    */
   public void nlLvlArrayNullIgnore(Object[] ar, String t) {
      if (ar == null) {
         return;
      }
      IStringable[] ars = new IStringable[ar.length];
      for (int i = 0; i < ar.length; i++) {
         ars[i] = (IStringable) ar[i];
      }
      nlLvlArray(t, ars);
   }

   public void nlLvlCtx(ICtx ctx, Class cl) {
      this.nlLvl(ctx, cl);
   }

   public void nlLvlIgnoreNull(String t, IStringable is) {
      Dctx dc = new Dctx(uc, this);
      dc.flags = flags;
      dc.nl();
      if (is != null) {
         if (processedObjectsMulti.hasObject(is)) {
            dc.append(t);
            dc.append(" ");
            is.toString1Line(dc);
         } else {
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
      Dctx dc = newLevel(flags);
      if (is == null) {
         dc.append(title + " is Null");
      } else {
         dc.append(title);
         if (processedObjectsMulti.hasObject(is)) {
            is.toString1Line(dc);
         } else {
            //we don't add it because it will be added in the call below
            //processedObjects.add(is); 
            dc.nlLvl(is);
         }
      }
   }

   /**
    * Shows title when not null. but if null, add to null list
    * @param t
    * @param is
    */
   public void nlLvlList(String t, IStringable is) {
      nlLvl(t, is, 0, false, true);
   }

   public void nlLvlList(String title, IStringable is, int flags, boolean flip) {
      nlLvl(title, is, flags, flip, true);
   }

   public void nlLvlNullTitle(String t, IStringable is) {
      nlLvlNullTitle(t, is, 0);
   }

   /**
    * Does not show the title when not null. List 
    * @param t
    * @param is
    * @param flags
    */
   public void nlLvlNullTitle(String t, IStringable is, int flags) {
      Dctx dc = newLevel(flags);
      if (is == null) {
         dc.append(t + " is Null");
      } else {
         if (processedObjectsMulti.hasObject(is)) {
            is.toString1Line(dc);
         } else {
            is.toString(dc);
         }
      }
   }

   public void nlLvlNullTitleList(String t, IStringable is) {
      Dctx dc = newLevel(0);
      if (is == null) {
         nulls.add(t);
      } else {
         if (processedObjectsMulti.hasObject(is)) {
            is.toString1Line(dc);
         } else {
            is.toString(dc);
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
         nlLvl((IStringable) null, title);
      } else {
         if (o instanceof IStringable) {
            nlLvlTitleIfNull((IStringable) o, title);
         } else {
            boolean isPrinted = uc.toString(this, o, title);
            if (!isPrinted) {
               nlLvl(title, new StringableWrapper(uc, o));
            }
         }
      }
   }

   /**
    * Compared to {@link Dctx#nlLvlO(Object, String)}, this method
    * @param title
    * @param o
    */
   public void nlLvlObject(String title, Object o) {
      nlLvlO(o, title);
   }

   public void nlLvlOWithTitle(Object o, String title, ICtx ctx) {
      if (o == null) {
         nlLvl((IStringable) null, title);
      } else {
         if (o instanceof IStringable) {
            boolean showTitleWhenNotNull = true;
            boolean listNulls = true;
            boolean isTitleSuffix = false;
            nlLvl(title, (IStringable) o, 0, isTitleSuffix, listNulls, showTitleWhenNotNull);
         } else {
            this.nl();
            uc.toString(this, o, title);
         }
      }
   }

   /**
    * Only prints title if {@link IStringable} is null
    * @param is
    * @param t
    */
   public void nlLvlTitleIfNull(IStringable is, String t) {
      boolean showTitleWhenNotNull = false;
      boolean listNulls = true;
      boolean isTitleSuffix = true; //irrelevant when title not showned
      nlLvl(t, is, 0, isTitleSuffix, listNulls, showTitleWhenNotNull);
   }

   public void nlLvlVector(Vector v) {
      if (v == null) {
         this.append("Vector is null");
      } else {
         Enumeration it = v.elements();
         int count = 0;
         while (it.hasMoreElements()) {
            Object o = it.nextElement();
            if (o instanceof IStringable) {
               count++;
               this.nlLvl((IStringable) o, "Vector#" + count);
            } else {
               this.append(o.getClass().getName());
            }
         }
      }
   }

   public void nlSpace() {
      nl();
      space();
   }

   public void nlTab() {
      nl();
      tab();
   }

   public void nlThread(String title, Thread t) {
      Dctx dc = newLevel(0);
      if (t == null) {
         dc.append(title + " is null");
      } else {
         dc.append(title);
         dc.appendVarWithSpace("Name", t.getName());
         dc.appendVarWithSpace("Priority", t.getPriority());

      }
   }

   public void nlVar(String s, boolean v) {
      nl();
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
      nl();
      appendVar(s, v);
   }

   public void nlVarOneLine(String string, IStringable is) {
      nl();
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

   public void oneLine(IStringable is) {
      //this.append(" ");
      is.toString1Line(this);
   }

   public void printExclusive(Dctx deviceD) {
      append(deviceD.toString());
   }

   /**
    * Go back v chars, "erasing" them
    * @param v
    */
   public void reverse(int v) {
      sb.decrementCount(v);
   }

   public void root(Object o, Class cl) {
      String str = getClassSimpleName(cl);
      this.root(o, str, defaultLine);
   }

   public void root(Object o, Class cl, ICtx ctx) {
      String str = getClassSimpleName(cl);
      this.root(o, str, defaultLine);

   }

   public void root(Object o, Class cl, int line) {
      String str = getClassSimpleName(cl);
      this.root(o, str, String.valueOf(line));
   }

   public void root(Object o, Class title, Class cl, int line) {
      String str = getClassSimpleName(title);
      String classLink = getClassSimpleName(cl);
      this.root(o, str, classLink, String.valueOf(line));
   }

   /**
    * 
    * @param o
    * @param c
    * @param line
    */
   public void root(Object o, Class cl, String line) {
      String str = getClassSimpleName(cl);
      if (line.startsWith("@line")) {
         line = line.substring(5);
      }
      root(o, str, line);
   }

   public void root(Object o, String str) {
      this.root(o, str, defaultLine);
   }

   public void root(Object o, String str, String classLink, String line) {
      if (isLineNumbers && numLines.getCount() == 0) {
         numLines.increment();
         String strLine = uc.getStrU().prettyIntPaddStr(numLines.getCount(), 4, " ");
         sb.append(strLine);
      }
      if (isClassLinks) {
         sb.append(getLevelStartChar());
         doTitlePrefix();
         appendClassLink(classLink, line);
      } else {
         sb.append(getLevelStartChar());
         doTitlePrefix();
         append(str);
      }

      doTitleSuffix();
      processedObjectsMulti.addUnique(o, numLines.getCount());

      tab();
   }

   /**
    * Displays a root. 
    * <br>
    * Link Str to IStringable
    * @param o when null, print that
    * @param str
    */
   public void root(Object o, String str, String line) {
      this.root(o, str, str, line);
   }

   private void appendClassLink(String str, String line) {
      sb.append(' ');
      sb.append('(');
      append(str);
      append(".java:");
      append(line);
      append(")");
   }

   public void root1Line(Object o, Class cl) {
      String str = getClassSimpleName(cl);
      this.root1Line(o, str, cl);
   }

   public void root1LineCollapsed(Object o, String str, int line) {
      if (isClassLinks) {
         sb.append(getLevelStartChar());
         doTitlePrefix();
         sb.append(' ');
         sb.append('(');
         append(str);
         append(".java:");
         append(line);
         append(")");
      } else {
         sb.append(getLevelStartChar());
         doTitlePrefix();
         append(str);
      }
      setCompact(true);
      doTitleSuffix();
   }

   private int lineForCollapsed;

   public void root1Line(Object o, String str) {
      root1Line(o, str, null);
   }

   public void root1Line(Object o, String str, Class cl) {
      this.root1Line(o, str, cl, "40");
   }

   public void root1Line(Object o, Class cl, int line) {
      root1Line(o, null, cl, String.valueOf(line));
   }

   public void root1Line(Object o, String str, Class cl, String line) {
      if (hasFlag(FLAG_DATA_07_COLLAPSED)) {
         this.setFlag(FLAG_DATA_07_COLLAPSED, false);
         root1LineCollapsed(o, str, lineForCollapsed);
      } else {
         if (isClassLinks && cl != null) {
            sb.append(getLevelStartChar1Line());
            doTitlePrefix();
            String classSimpleName = getClassSimpleName(cl);
            this.appendClassLink(classSimpleName, line);
            setCompact(true);
            doTitleSuffix();
         } else {
            sb.append(getLevelStartChar1Line());
            doTitlePrefix();
            sb.append(str);
            setCompact(true);
            doTitleSuffix();
         }
      }
   }

   public void rootCtx(ICtx ctx, Class cl) {
      this.trackedCtx.addUnique(ctx);
      String str = getClassSimpleName(cl);
      append("RootCtx=");
      if (isClassLinks) {
         sb.append(' ');
         sb.append('(');
         append(str);
         append(".java:40)");
      } else {
         append(str);
      }
   }

   public void rootN(Object o, String str) {
      sb.append(getLevelStartChar());
      doTitlePrefix();
      append(str);
      doTitleSuffix();
      processedObjectsMulti.add(o, numLines.getCount());
      processedObjectsMulti.add(str);
      tab();
   }

   public void rootN(Object o, String str, Class cl, int line) {
      //add a suffix
      if (isClassLinks) {
         String strCl = getClassSimpleName(cl);
         StringBBuilder sb = new StringBBuilder(uc);
         sb.append(' ');
         sb.append('(');
         sb.append(strCl);
         sb.append(".java:");
         sb.append(line);
         sb.append(")");
         setTitlePrefix(sb.toString());
      }
      this.rootN(o, str);
   }

   public void rootN1Line(Object o, String str) {
      sb.append(getLevelStartChar1Line());
      doTitlePrefix();
      sb.append(str);
      setCompact(true);
      doTitleSuffix();
   }

   public void rootN1Line(Object o, String str, Class cl, int line) {
      //add a suffix
      if (isClassLinks) {
         String strCl = getClassSimpleName(cl);
         StringBBuilder sb = new StringBBuilder(uc);
         sb.append(' ');
         sb.append('(');
         sb.append(strCl);
         sb.append(".java:");
         sb.append(line);
         sb.append(")");
         setTitlePrefix(sb.toString());
      }
      this.rootN1Line(o, str);
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

   public void setCompact(boolean b) {
      isCompact = b;
   }

   public void setExpand(boolean v) {
      isExpand = v;
   }

   public void setFlag(int flag, boolean v) {
      flags = BitUtils.setFlag(flags, flag, v);
   }

   public boolean hasFlag(int flag) {
      return BitUtils.hasFlag(flags, flag);
   }

   /**
    * Sets
    * @param ctx
    * @param flag
    * @param b
    */
   public void setFlagData(ICtx ctx, int flag, boolean b) {
      int index = flagsData.findObjectRef(ctx);
      if (index == -1) {
         //not even there
         index = flagsData.addReturnIndex(ctx, ctx.toStringGetToStringFlags());
      }
      flagsData.setIntFlag(index, flag, b);
      //enable ctx to set other flags linked to this flag.. but invisible
      //to the caller of this method
      ctx.toStringFlagSetOn(flag, b, this);
   }

   public void setLineNumbers(boolean isLineNumbers) {
      this.isLineNumbers = isLineNumbers;
   }

   private void setTitlePrefix(String title) {
      titlePrefix = title;
   }

   private void setTitleSuffix(String title) {
      titleSuffix = title;
   }

   public void space() {
      this.append(" ");
   }

   /**
    * Called when going upwards one level in a Class Definition.
    * <br>
    * Prints a newline
    * @return
    */
   public Dctx sup() {
      Dctx d = newLevel();
      return d;
   }

   public Dctx sup1Line() {
      append(" ");
      return this;
   }

   /**
    * Increase tabulation.
    * 
    * Undo with {@link Dctx#tabRemove()}
    */
   public void tab() {
      char c = linesArray[numTabs % linesArray.length];
      nlTabs = nlTabs + c + strInterTab;
      numTabs++;
   }

   /**
    * Decrease tabulation
    */
   public void tabRemove() {
      if (numTabs > 0) {
         numTabs--;
         nlTabs = getNlTab(numTabs);
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

   /**
    * Append data about the ctxs. This is not always desirable
    */
   public void toStringCtx() {
      for (int i = 0; i < trackedCtx.getLength(); i++) {
         ICtx ctx = (ICtx) trackedCtx.getObjectAtIndex(i);
         ICtx[] subs = ctx.getCtxSub();
         for (int j = 0; j < subs.length; j++) {
            int index = trackedCtx.getObjectIndex(subs[j]);
            if (index != -1) {
               trackedCtx.incrementInt(index, 1);
            }
         }
      }
      trackedCtx.sortInt(true);
      for (int i = 0; i < trackedCtx.getLength(); i++) {
         ICtx ctx = (ICtx) trackedCtx.getObjectAtIndex(i);
         int refs = trackedCtx.getInt(i);
         this.nlLvl(ctx, "refs=" + refs);
      }
   }

   public void nlLvlArrayBytes(String title, byte[] data) {
      if (data == null) {
         this.nl();
         this.append(title);
         this.append(" is null");
      } else {
         this.nl();
         nlLvlArrayBytes(title, data, 0, data.length, 15);
      }
   }

   public void nlLvlArrayBytes(String title, byte[] data, int offset, int len, int colSize) {
      if (data == null) {
         this.nl();
         this.append(title);
         this.append(" is null");
      } else {
         this.nl();
         this.append(title);
         uc.getBU().toStringBytes(this, data, offset, len, colSize);
      }
   }

   //#enddebug
}
