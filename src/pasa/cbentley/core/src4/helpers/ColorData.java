package pasa.cbentley.core.src4.helpers;

import pasa.cbentley.core.src4.ctx.UCtx;
import pasa.cbentley.core.src4.logging.Dctx;
import pasa.cbentley.core.src4.logging.IDLog;
import pasa.cbentley.core.src4.logging.IStringable;
import pasa.cbentley.core.src4.utils.ColorUtils;
import pasa.cbentley.core.src4.utils.StringUtils;

public class ColorData implements IStringable {

   protected final UCtx uc;

   private int          r;

   private int          b;

   private int          g;

   private int[]        rgb = new int[3];

   private float[]      hsl = new float[3];

   private float        hue360;

   private float        saturation100;

   private float        light100;

   private float        v;

   private int          luma255;

   private int          sumRGB255;

   private int          max255;

   private int          min255;

   private float        rFloat;

   private float        gFloat;

   private float        bFloat;

   private float        gIsh;

   private float        rIsh;

   private float        bIsh;

   private int          chroma255;

   private int          value;

   private int          light255;

   private int          intensity255;

   private int          minMaxAdd255;

   private int          divisor255;

   private int          saturation255;

   public ColorData(UCtx uc) {
      this.uc = uc;

   }

   /**
    * 
    * @param h
    * @param s
    * @param l
    */
   public void setH360S100L100(float h, float s, float l) {
      this.hue360 = h;
      this.saturation100 = s;
      this.light100 = l;
      //compute
      ColorUtils.getRGBFromH360S100L100Clip(h, s, l, rgb);
      r = rgb[0];
      g = rgb[1];
      b = rgb[2];
      computeValues();
   }

   public int getLuma255() {
      return luma255;
   }

   public void setRGB(int r, int g, int b) {
      this.r = r;
      this.g = g;
      this.b = b;

      min255 = Math.min(r, Math.min(g, b));
      max255 = Math.max(r, Math.max(g, b));

      chroma255 = max255 - min255;
      minMaxAdd255 = max255 + min255;
      divisor255 = 510 - max255 - min255;
      //  Calculate the Luminance
      light255 = (max255 + min255) / 2;

      //  Calculate the Saturation
      saturation255 = 0;
      if (min255 == max255) {
         saturation255 = 0;
      } else if (light255 <= 127) {
         saturation255 = chroma255 / minMaxAdd255;
      } else {
         saturation255 = chroma255 / divisor255;
      }
      computeValues();
   }

   public void computeValues() {
      rFloat = (float) r / 255f;
      gFloat = (float) g / 255f;
      bFloat = (float) b / 255f;

      max255 = Math.max(Math.max(r, g), b);
      min255 = Math.min(Math.min(r, g), b);

      sumRGB255 = (r + g + b);

      gIsh = (float) g / (float) sumRGB255;
      rIsh = (float) r / (float) sumRGB255;
      bIsh = (float) b / (float) sumRGB255;

      chroma255 = max255 - min255;
      value = max255;
      light255 = (max255 + min255) / 2;
      intensity255 = sumRGB255 / 3;

      luma255 = (int) ColorUtils.getLumaFast(r, g, b);
   }

   public float getSaturation100() {
      return light100;
   }

   public float getGreenishFloat() {
      return gIsh;
   }

   public float getBlueishFloat() {
      return bIsh;
   }

   public float getRedishFloat() {
      return rIsh;
   }

   public float getLight100() {
      return light100;
   }

   public int getGreen255() {
      return g;
   }

   public int getBlue255() {
      return b;
   }

   public int getRed255() {
      return r;
   }

   public void createString(StringBBuilder sb) {
      StringUtils su = uc.getStrU();
      sb.append(' ');
      sb.append("r=");
      sb.appendPrettyFront(r, 3, ' ');
      sb.append(' ');
      sb.append("g=");
      sb.appendPrettyFront(g, 3, ' ');
      sb.append(' ');
      sb.append("b=");
      sb.appendPrettyFront(b, 3, ' ');

      sb.append(" rgb=[");
      sb.append(su.prettyFloat(rFloat, 2));
      sb.append(',');
      sb.append(su.prettyFloat(gFloat, 2));
      sb.append(',');
      sb.append(su.prettyFloat(bFloat, 2));
      sb.append("]");

      sb.append(' ');
      sb.append("hue=");
      sb.appendPrettyFront(su.prettyFloat(hue360, 1), 5, ' ');
      sb.append('°');
      sb.append(' ');
      sb.append(" saturation=");
      sb.appendPrettyFront(su.prettyFloat(saturation100, 1), 5, ' ');
      sb.append('%');

      sb.append(' ');
      sb.append(" light=");
      sb.appendPrettyFront(su.prettyFloat(light100, 1), 5, ' ');
      sb.append('%');
      sb.append(' ');
      sb.append("luma=");
      sb.appendPrettyFront(luma255, 3, ' ');
      sb.append(' ');
      sb.append("intensity=");
      sb.appendPrettyFront(intensity255, 3, ' ');
      sb.append(' ');
      sb.append("V=");
      sb.appendPrettyFront(value, 3, ' ');
      sb.append(' ');
      sb.append("lightness=");
      sb.appendPrettyFront(light255, 3, ' ');
      sb.append(' ');
      sb.append("chroma=");
      sb.appendPrettyFront(chroma255, 3, ' ');

      sb.append(' ');
      sb.append("ish ");
      sb.append("r=");
      sb.append(su.prettyFloat(rIsh, 2));
      sb.append(' ');
      sb.append("g=");
      sb.append(su.prettyFloat(gIsh, 2));
      sb.append(' ');
      sb.append("b=");
      sb.append(su.prettyFloat(bIsh, 2));

   }

   //#mdebug
   public IDLog toDLog() {
      return toStringGetUCtx().toDLog();
   }

   public String toString() {
      return Dctx.toString(this);
   }

   public void toString(Dctx dc) {
      dc.root(this, "ColorData");
      toStringPrivate(dc);
   }

   public String toString1Line() {
      return Dctx.toString1Line(this);
   }

   private void toStringPrivate(Dctx dc) {

   }

   public void toString1Line(Dctx dc) {
      dc.root1Line(this, "ColorData");
      toStringPrivate(dc);
   }

   public UCtx toStringGetUCtx() {
      return uc;
   }

   //#enddebug

}
