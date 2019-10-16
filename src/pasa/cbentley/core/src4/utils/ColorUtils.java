package pasa.cbentley.core.src4.utils;

import pasa.cbentley.core.src4.ctx.UCtx;
import pasa.cbentley.core.src4.helpers.StringBBuilder;
import pasa.cbentley.core.src4.logging.Dctx;
import pasa.cbentley.core.src4.logging.IStringable;
import pasa.cbentley.core.src4.utils.interfaces.IColors;

/**
 * Helper class for dealing with colors.
 * <br>
 * TODO
 * Pour plus de couleurs et des visualizations voir C:\Java\Couleurs Françaises
 * @author Charles Bentley
 *
 */
public class ColorUtils implements IColors, IStringable {

   private static float convertHueToRGB(float p, float q, float h) {
      if (h < 0) {
         h += 1;
      }

      if (h > 1) {
         h -= 1;
      }

      if (6 * h < 1) {
         return p + ((q - p) * 6 * h);
      }
      if (2 * h < 1) {
         return q;
      }

      if (3 * h < 2) {
         return p + ((q - p) * 6 * ((2.0f / 3.0f) - h));
      }

      return p;
   }

   public static int getAlpha(int c) {
      return (c >> 24) & 0xFF;
   }

   public static int getBlue(int c) {
      return (c) & 0xFF;
   }

   public static int getBrighter(int rgb, int percent) {
      int r = ((rgb >> 16) & 0xFF);
      int g = ((rgb >> 8) & 0xFF);
      int b = (rgb & 0xFF);

      float rd = 255.0f - r;
      float gd = 255.0f - g;
      float bd = 255.0f - b;

      r += (rd * (float) percent) / 100.0f;
      g += (gd * (float) percent) / 100.0f;
      b += (bd * (float) percent) / 100.0f;

      return getRGBIntVal(r, g, b);
   }

   public static int getBrightish(int rgb, int add) {
      int r = ((rgb >> 16) & 0xFF) + add;
      int g = ((rgb >> 8) & 0xFF) + add;
      int b = (rgb & 0xFF) + add;
      return getRGBIntVal(r, g, b);
   }

   public static int getComplementaryColor(int color) {
      return (0xFF000000 & color) | ((255 - ((0x00FF0000 & color) >> 16)) << 16) | ((255 - ((0x0000FF00 & color) >> 8)) << 8) | (255 - (0x000000FF & color));
   }

   public static int getComplementaryColor(int[] rgb) {
      return (0xFF000000) | ((255 - rgb[0]) << 16) | ((255 - (rgb[1]) << 8) | (255 - rgb[2]));
   }
   
   public static int getDarker(int rgb, int percent) {
      int r = ((rgb >> 16) & 0xFF);
      int g = ((rgb >> 8) & 0xFF);
      int b = (rgb & 0xFF);
      r -= (r * percent) / 100.0;
      g -= (g * percent) / 100.0;
      b -= (b * percent) / 100.0;
      return getRGBIntVal(r, g, b);
   }

   /**
    * Takes a color and darkens/brighten each channel by param.
    * <br>
    * <br>
    * Function for a gradient may include target.
    * Or as soon as a channel hits a target.
    * @param rgb
    * @param sub
    * @return
    */
   public static int getDarkish(int rgb, int sub) {
      int r = ((rgb >> 16) & 0xFF) - sub;
      int g = ((rgb >> 8) & 0xFF) - sub;
      int b = (rgb & 0xFF) - sub;
      return getRGBIntVal(r, g, b);
   }

   public static int getGreen(int c) {
      return (c >> 8) & 0xFF;
   }

   /**
    * 
    * @param r
    * @param g
    * @param b
    * @param hsl
    */
   public static void getHSLFromRGB(float r, float g, float b, float[] hsl) {
      //  Get RGB values in the range 0 - 1

      float min = Math.min(r, Math.min(g, b));
      float max = Math.max(r, Math.max(g, b));

      //  Calculate the Hue
      float h = 0;

      if (max == min) {
         h = 0;
      } else if (max == r) {
         h = ((60 * (g - b) / (max - min)) + 360) % 360;
      } else if (max == g) {
         h = (60 * (b - r) / (max - min)) + 120;
      } else if (max == b) {
         h = (60 * (r - g) / (max - min)) + 240;
      }
      //  Calculate the Luminance
      float l = (max + min) / 2;

      //  Calculate the Saturation
      float s = 0;
      if (max == min) {
         s = 0;
      } else if (l <= .5f) {
         s = (max - min) / (max + min);
      } else {
         s = (max - min) / (2 - max - min);
      }

      hsl[0] = h;
      hsl[1] = s * 100;
      hsl[2] = l * 100;
   }

   public static void getHSLFromRGB(int r, int g, int b, float[] hsl) {
      getHSLFromRGB(r / 255f, g / 255f, b / 255f, hsl);
   }

   /**
    * Digital ITU BT.601 (gives more weight to the R and B components):
    * @param r
    * @param g
    * @param b
    * @return
    */
   public static float getLuma601Digital(float r, float g, float b) {
      return (float) (0.299 * r + 0.587 * g + 0.114f * b);
   }

   /**
    * Photometric/digital ITU BT.709: http://www.itu.int/rec/R-REC-BT.709
    * @param r
    * @param g
    * @param b
    * @return
    */
   public static float getLuma709Photo(float r, float g, float b) {
      return (float) (0.2126f * r + 0.7152f * g + 0.0722f * b);
   }

   /**
    * Y
    * Optimized for speed.
    * @param r
    * @param g
    * @param b
    * @return
    */
   public static int getLumaFast(int r, int g, int b) {
      //blue being the least important
      return (r + r + r + b + g + g + g + g) >> 3;
      //return (r << 1 + r + g << 2 + b) >> 3;
   }

   public static int getMedian(int rgb1, int rgb2) {
      int r1 = ((rgb1 >> 16) & 0xFF);
      int g1 = ((rgb1 >> 8) & 0xFF);
      int b1 = (rgb1 & 0xFF);
      int r2 = ((rgb2 >> 16) & 0xFF);
      int g2 = ((rgb2 >> 8) & 0xFF);
      int b2 = (rgb2 & 0xFF);
      int r = (r1 + r2) / 2;
      int g = (g1 + g2) / 2;
      int b = (b1 + b2) / 2;
      return getRGBIntVal(r, g, b);
   }

   public static int getRed(int c) {
      return (c >> 16) & 0xFF;
   }

   /**
    * Returns a byte array with only the Alpha values.
    * <br>
    * @param rgb array of argb color data.
    * @return byte array
    * @throws NullPointerException if rgb is null
    */
   public static byte[] getRGBAlpha(int[] rgb) {
      byte[] bs = new byte[rgb.length];
      int index = 0;
      for (int i = 0; i < rgb.length; i++) {
         int c = rgb[i];
         bs[index] = (byte) ((c >> 24) & 0xFF);
         index++;
      }
      return bs;
   }

   /**
    * Returns a byte array with only the r,g,b values.
    * <br>
    * @param rgb array of argb color data.
    * @return byte array whose length will be input array times 3.
    * @throws NullPointerException if rgb is null
    */
   public static byte[] getRGBBytes(int[] rgb) {
      byte[] bs = new byte[rgb.length * 3];
      int index = 0;
      for (int i = 0; i < rgb.length; i++) {
         int c = rgb[i];
         bs[index] = (byte) ((c >> 16) & 0xFF);
         index++;
         bs[index] = (byte) ((c >> 8) & 0xFF);
         index++;
         bs[index] = (byte) ((c >> 0) & 0xFF);
         index++;
      }
      return bs;
   }

   /**
    * No check on parameters
    * @param h
    * @param s
    * @param l
    * @param rgb
    */
   public static void getRGBFromH1S1L1Unsafe(float h, float s, float l, int[] rgb) {
      float q = 0;

      if (l < 0.5) {
         q = l * (1 + s);
      } else {
         q = (l + s) - (s * l);
      }

      float p = 2 * l - q;

      float r = Math.max(0, convertHueToRGB(p, q, h + (1.0f / 3.0f)));
      float g = Math.max(0, convertHueToRGB(p, q, h));
      float b = Math.max(0, convertHueToRGB(p, q, h - (1.0f / 3.0f)));

      r = Math.min(r, 1.0f);
      g = Math.min(g, 1.0f);
      b = Math.min(b, 1.0f);

      rgb[0] = (int) (r * 255 + 0.5);
      rgb[1] = (int) (g * 255 + 0.5);
      rgb[2] = (int) (b * 255 + 0.5);
   }

   /**
    * Values out of domain generate an exception
    * @param h 0-360
    * @param s 0-100
    * @param l 0-100
    * @param rgb
    */
   public static void getRGBFromH360S100L100Check(float h, float s, float l, int[] rgb) {
      if (s < 0.0f || s > 100.0f) {
         String message = "Saturation must be between 0 and 100";
         throw new IllegalArgumentException(message);
      }
      if (l < 0.0f || l > 100.0f) {
         String message = "Luminance must be between 0 and 100";
         throw new IllegalArgumentException(message);
      }
      if (h < 0.0f || h > 360.0f) {
         String message = "Hue must be between 0 and 360";
         throw new IllegalArgumentException(message);
      }

      h /= 360f;
      s /= 100f;
      l /= 100f;

      getRGBFromH1S1L1Unsafe(h, s, l, rgb);
   }

   /**
    * Clip values to fit the domain
    * @param h modulo 360 so any value is accepted
    * @param s clipped to 0-100
    * @param l clipped to 0-100
    * @param rgb
    */
   public static void getRGBFromH360S100L100Clip(float h, float s, float l, int[] rgb) {
      if (s < 0f) {
         s = 0f;
      }
      if (s > 100f) {
         s = 100f;
      }
      if (l < 0f) {
         l = 0f;
      }
      if (l > 100f) {
         l = 100f;
      }
      if (h < 360f || h > 360f) {
         h = h % 360f;
      }

      getRGBFromH1S1L1Unsafe(h / 360f, s / 100f, l / 100f, rgb);
   }

   /**
    * 
    * @param h must be between 0-360, otherwise undefined behavior
    * @param s must be between 0-100, otherwise undefined behavior
    * @param l must be between 0-100, otherwise undefined behavior
    * @param rgb
    */
   public static void getRGBFromH360S100L100Unsafe(int h, int s, int l, int[] rgb) {
      getRGBFromH1S1L1Unsafe(h / 360f, s / 100f, l / 100f, rgb);
   }

   /**
    * RGB from 360 angle, 255 saturation, 255 light. no checks on parameters validity.
    * @param h
    * @param s
    * @param l
    * @param rgb
    */
   public static void getRGBFromH360S255L255Unsafe(float h, float s, float l, int[] rgb) {
      getRGBFromH1S1L1Unsafe(h / 360f, s / 255f, l / 255f, rgb);
   }

   /**
    * Computes the RGB.
    * @param red
    * @param green
    * @param blue
    * @return
    */
   public static int getRGBInt(int red, int green, int blue) {
      return (255 << 24) + (red << 16) + (green << 8) + blue;
   }

   /**
    * Computes the aRGB.
    * @param alpha
    * @param red
    * @param green
    * @param bleu
    * @return
    */
   public static int getRGBInt(int alpha, int red, int green, int bleu) {
      return (alpha << 24) + (red << 16) + (green << 8) + bleu;
   }

   /**
    * Returns RGB values from red,green,blue in the array
    * @see ColorUtils#getRGBInt(int, int, int)
    * @param rgb
    * @return
    */
   public static int getRGBInt(int[] rgb) {
      return (255 << 24) + (rgb[0] << 16) + (rgb[1] << 8) + rgb[2];
   }

   /**
    * Creates a RGB with clipping on both sides
    * @param r
    * @param g
    * @param b
    * @return
    */
   public static int getRGBIntVal(int r, int g, int b) {
      if (r < 0)
         r = 0;
      if (g < 0)
         g = 0;
      if (b < 0)
         b = 0;
      if (b >> 8 != 0) {
         b = 0xFF;
      }
      if (r >> 8 != 0) {
         r = 0xFF;
      }
      if (g >> 8 != 0) {
         g = 0xFF;
      }
      return (255 << 24) + (r << 16) + (g << 8) + b;
   }

   public static int pixelToGrayScale(int pixel) {

      int fixR = (int) (((pixel >> 16) & 0xFF) * TO_GRAY_RED_DESATURATOR_FIX);
      int fixG = (int) (((pixel >> 8) & 0xFF) * TO_GRAY_GREEN_DESATURATOR_FIX);
      int fixB = (int) ((pixel & 0xFF) * TO_GRAY_BLUE_DESATURATOR_FIX);

      int sum = fixR + fixG + fixB;

      return getRGBInt(sum, sum, sum);
   }

   public static int pixelToSepia(int pixel, int sepiaDepth, int sepiaIntensity) {

      int r = (pixel >> 16) & 0xFF;
      int g = (pixel >> 8) & 0xFF;
      int b = (pixel >> 0) & 0xFF;

      int gry = (r + g + b) / 3;
      r = g = b = gry;
      r = r + (sepiaDepth * 2);
      g = g + sepiaDepth;

      if (r > 255)
         r = 255;
      if (g > 255)
         g = 255;
      if (b > 255)
         b = 255;

      // Darken blue color to increase sepia effect
      b -= sepiaIntensity;

      // normalize if out of bounds
      if (b < 0)
         b = 0;
      if (b > 255)
         b = 255;

      return (r << 16) + (g << 8) + b;
   }

   public static void replaceColor(int[] imgData, int oColor, int newColor) {
      for (int i = 0; i < imgData.length; i++) {
         if (imgData[i] == oColor)
            imgData[i] = newColor;
      }
   }

   public static int setAlpha(int color, int alpha) {
      if (alpha < 0)
         alpha = 0;
      if (alpha > 255)
         alpha = 255;
      return (color & 0x00FFFFFF) + (alpha << 24);
   }

   public static int setOpaque(int pcolor) {
      return (255 << 24) + (pcolor & 0xFFFFFF);
   }

   public static int[] setOpaque(int[] pcolor) {
      int[] n = new int[pcolor.length];
      for (int i = 0; i < pcolor.length; i++) {
         n[i] = (255 << 24) + (pcolor[i] & 0xFFFFFF);
      }
      return n;
   }

   /**
    * Smoothing function. Each channel is combined 
    * @param v1
    * @param per
    * @param v2
    * @return
    */
   public static int smoothStep(int v1, double per, int v2) {
      int alpha = (int) (((v1 >> 24) & 0xFF) * per + ((v2 >> 24) & 0xFF) * (1 - per));
      int red = (int) (((v1 >> 16) & 0xFF) * per + ((v2 >> 16) & 0xFF) * (1 - per));
      int green = (int) (((v1 >> 8) & 0xFF) * per + ((v2 >> 8) & 0xFF) * (1 - per));
      int blue = (int) (((v1 >> 0) & 0xFF) * per + ((v2 >> 0) & 0xFF) * (1 - per));
      return ColorUtils.getRGBInt(alpha, red, green, blue);
   }

   /**
    * A smoothed step function. A cubic function is used to smooth the step between two thresholds.
    * <br>
    * @param a the lower threshold position
    * @param b the upper threshold position
    * @param x the input parameter
    * @return the output value
    */
   public static float smoothStepCubic(float a, float b, float x) {
      if (x < a)
         return 0;
      if (x >= b)
         return 1;
      x = (x - a) / (b - a);
      return x * x * (3 - 2 * x);
   }

   private UCtx uc;

   public ColorUtils(UCtx uc) {
      this.uc = uc;
   }

   public void rotateHSL180(float[] hsl) {
      hsl[0] = (hsl[0] + 180f) % 360;
      hsl[1] = (hsl[1] + 50f) % 100;
      hsl[2] = (hsl[2] + 50f) % 100;
   }

   //#mdebug
   public String toString() {
      return Dctx.toString(this);
   }

   public void toString(Dctx dc) {
      dc.root(this, "ColorUtils");
   }

   public String toString1Line() {
      return Dctx.toString1Line(this);
   }

   public void toString1Line(Dctx dc) {
      dc.root1Line(this, "ColorUtils");
   }
   //#enddebug

   //#mdebug
   public void toStringColor(Dctx sb, int c) {
      sb.append("(");
      sb.append(((c >> 24) & 0xFF));
      sb.append(",");
      sb.append(((c >> 16) & 0xFF));
      sb.append(",");
      sb.append(((c >> 8) & 0xFF));
      sb.append(",");
      sb.append(((c >> 0) & 0xFF));
      sb.append(")");
   }
   //#enddebug

   /**
    * Returns a String such as (a,r,g,b)
    * @param c
    * @return
    */
   public String toStringColor(int c) {
      return "(" + ((c >> 24) & 0xFF) + "," + ((c >> 16) & 0xFF) + "," + ((c >> 8) & 0xFF) + "," + (c & 0xFF) + ")";
   }

   public void toStringColor(StringBBuilder sb, int c) {
      sb.append("(");
      sb.append(((c >> 24) & 0xFF));
      sb.append(",");
      sb.append(((c >> 16) & 0xFF));
      sb.append(",");
      sb.append(((c >> 8) & 0xFF));
      sb.append(",");
      sb.append(((c >> 0) & 0xFF));
      sb.append(")");
   }

   /**
    * Returns a String such as [r-g-b]
    * @param c
    * @return
    */
   public String toStringColorRGB(int c) {
      return "[" + ((c >> 16) & 0xFF) + "-" + ((c >> 8) & 0xFF) + "-" + (c & 0xFF) + "]";
   }

   public UCtx toStringGetUCtx() {
      return uc;
   }

}
