/*
 * (c) 2018-2020 Charles-Philip Bentley
 * This code is licensed under MIT license (see LICENSE.txt for details)
 */
package pasa.cbentley.core.src4.utils;

import pasa.cbentley.core.src4.ctx.UCtx;
import pasa.cbentley.core.src4.structs.IntBuffer;

/**
 * Collection of methods to help complete 2d geometry tasks.
 * <br>
 * This class is heavily dependant on the {@link MathUtils} context.
 * <br>
 * @author Charles Bentley
 *
 */
public class Geo2dUtils {

   private UCtx uc;

   public Geo2dUtils(UCtx uc) {
      this.uc = uc;
   }

   /**
    * Returns a series of rectangles that cover the x,y,w,h area less the defined holes
    * <br>
    * <br>
    * @return
    */
   public int[] getHolesInverse(int x, int y, int w, int h, int[] holes) {
      int numHoles = holes.length / 4;
      if (numHoles == 1) {
         return getHoleReverse1(x, y, w, h, holes);
      } else {
         //no need to start with the smallest y
         //just take one hole. apply function.
         //take next hole, take intersection with each parts. apply function.

      }
      return null;
   }

   protected int[] getHoleReverse1(int x, int y, int w, int h, int[] holes) {
      return getHoleReverse1(x, y, w, h, holes, 0);
   }

   public static float getDistance(int x1, int y1, int x2, int y2) {
      int x = x1 - x2;
      int y = y1 - y2;
      double a = x * x + y * y;
      float dist = (float) Math.sqrt(a);
      return dist;
   }

   public float getAngle(int px, int py, int tx, int ty) {
      float atan = MathUtils.aTanFloat(ty - py, tx - px);
      float angle = (float) Math.toDegrees(atan);
      if (angle < 0) {
         angle += 360;
      }
      return angle;
   }

   public float angleBetweenLines(int fX, int fY, int sX, int sY, int nfX, int nfY, int nsX, int nsY) {

      float angle1 = (float) MathUtils.aTanFloat((fY - sY), (fX - sX));
      float angle2 = (float) MathUtils.aTanFloat((nfY - nsY), (nfX - nsX));

      float angle = ((float) Math.toDegrees(angle1 - angle2)) % 360;
      if (angle < -180.f)
         angle += 360.0f;
      if (angle > 180.f)
         angle -= 360.0f;
      return angle;
   }

   /**
    * 
    * @param x
    * @param y
    * @param w
    * @param h
    * @param holes
    * @param offset
    * @return
    */
   protected int[] getHoleReverse1(int x, int y, int w, int h, int[] holes, int offset) {
      IntBuffer buf = new IntBuffer(uc, 5);
      int hx = holes[offset];
      int hy = holes[offset + 1];
      int hw = holes[offset + 2];
      int hh = holes[offset + 3];
      int ah1 = hy - y;
      if (ah1 != 0) {
         buf.addInt(x, y, w, ah1);
      }
      int aw2 = hx - x;
      int ah2 = h - ah1;
      int ay2 = x + ah1;
      int ax2 = x;
      if (aw2 != 0) {
         buf.addInt(ax2, ay2, aw2, ah2);
      }
      int aw3 = w - aw2 - hw;
      int ax3 = x + w - aw3;
      int ay3 = ay2;
      int ah3 = ah2;
      if (aw3 != 0) {
         buf.addInt(ax3, ay3, aw3, ah3);
      }
      int ax4 = x + aw2;
      int ay4 = y + ah1 + hh;
      int aw4 = hw;
      int ah4 = h - hh - ah1;
      if (ah4 != 0) {
         buf.addInt(ax4, ay4, aw4, ah4);
      }
      return buf.getIntsClonedTrimmed();
   }

   /**
    * 
    * @param rx
    * @param ry
    * @param rw
    * @param rh
    * @param x
    * @param y
    * @param w
    * @param h
    * @return
    */
   public int[] getIntersection(int rx, int ry, int rw, int rh, int x, int y, int w, int h) {
      int[] sec = new int[4];
      return getIntersection(rx, ry, rw, rh, x, y, w, h, sec);
   }

   /**
    * 
    * @param rx
    * @param ry
    * @param rw
    * @param rh
    * @param x
    * @param y
    * @param w
    * @param h
    * @param union
    * @return
    */
   public int[] getUnion(int rx, int ry, int rw, int rh, int x, int y, int w, int h, int[] union) {
      int ux = 0;
      int uw = 0;
      int uh = 0;
      int uy = 0;
      int rxw = rx + rw;
      int xw = x + w;
      if (x < rx) {
         ux = x;
         if (xw < rxw) {
            uw = rx + rw - x;
         } else {
            uw = w;
         }
      } else {
         ux = rx;
         if (rxw < xw) {
            uw = x + w - rx;
         } else {
            uw = rw;
         }
      }
      int ryh = ry + rh;
      int yh = y + h;
      if (y < ry) {
         uy = y;
         if (yh < ryh) {
            uh = ry + rh - y;
         } else {
            uh = h;
         }
      } else {
         uy = ry;
         if (ryh < yh) {
            uh = y + h - ry;
         } else {
            uh = rh;
         }
      }
      union[0] = ux;
      union[1] = uy;
      union[2] = uw;
      union[3] = uh;
      return union;
   }

   /**
    * Intersection of two rectangles. All coordinates are expressed in the coordinate system of the first rectangle. <br>
    * <br>
    * <li>int[0] = x coordinate in canvas system
    * <li>int[1] = y coorindate in canvas system
    * <li>int[2] = width of rectangle
    * <li>int[3] = height of rectangle
    * <br>
    * If there is no intersection, return null. <br>
    * 
    * @param rx x coordinate of the first rectangle
    * @param ry root y coordinate
    * @param rw root x coordinate
    * @param rh root x coordinate
    * @param x coordinate of the intersanct rectangle
    * @param y coordinate of the intersanct rectangle in the canvas coordinate system 
    * @param w width of the intersanct rectangle
    * @param h
    * @param intersect container for output values
    * @return null if no intersection intersect array if there is such
   
    */
   public int[] getIntersection(int rx, int ry, int rw, int rh, int x, int y, int w, int h, int[] intersect) {
      //SystemLog.printDraw("#DrawUtilz#getIntersection x1=" + rx + " y1=" + ry + "w1=" + rw + " h1=" + rh + " x2=" + x + " y2=" + y + " w2=" + w + " h2=" + h);
      if (hasCollision(rx, ry, rw, rh, x, y, w, h)) {
         if (rx < x) {
            intersect[0] = x;
            if (x + w < rx + rw) {
               intersect[2] = w;
            } else {
               intersect[2] = rx + rw - x;
            }
         } else {
            intersect[0] = rx;
            if (rx + rw < x + w) {
               //both are inside
               intersect[2] = rw;
            } else {
               intersect[2] = w - rx + x;
            }
         }
         if (ry < y) {
            intersect[1] = y;
            if (y + h < ry + rh) {
               intersect[3] = h;
            } else {
               intersect[3] = ry + rh - y;
            }
         } else {
            intersect[1] = ry;
            if (ry + rh < y + h) {
               //both are inside
               intersect[3] = rh;
            } else {
               intersect[3] = h - ry + y;
            }
         }
         return intersect;
      }
      return null;
   }

   /**
    * 
    * @param rx
    * @param ry
    * @param rw
    * @param rh
    * @param x
    * @param y
    * @param w
    * @param h
    * @return null if no intersection
    */
   public int[] getIntersectionDest(int rx, int ry, int rw, int rh, int x, int y, int w, int h) {
      int[] sec = new int[4];
      return getIntersectionDest(rx, ry, rw, rh, x, y, w, h, sec);
   }

   /** 
    * Same as other function {@link Geo2dUtils#getIntersection(int, int, int, int, int, int, int, int, int[])}
    * but returned values are in the coordinate system of the second rectangle
    * 
    */
   public int[] getIntersectionDest(int rx, int ry, int rw, int rh, int x, int y, int w, int h, int[] intersect) {
      int[] ar = getIntersection(rx, ry, rw, rh, x, y, w, h, intersect);
      if (ar != null) {
         ar[0] = ar[0] - x;
         ar[1] = ar[1] - y;
      }
      return ar;
   }

   /**
    * 
    * @param rx
    * @param ry
    * @param rw
    * @param rh
    * @param x
    * @param y
    * @param w
    * @param h
    * @return
    */
   public boolean hasCollision(int rx, int ry, int rw, int rh, int x, int y, int w, int h) {
      //by default collision. invalidates with root coordinate
      if (rx + rw <= x)
         return false;
      if (rx >= x + w)
         return false;
      if (ry + rh <= y)
         return false;
      if (ry >= y + h)
         return false;
      return true;
   }

   public boolean hasCollision(int[] dim1, int x, int y, int w, int h) {
      return hasCollision(dim1[0], dim1[1], dim1[2], dim1[3], x, y, w, h);
   }

   public boolean hasCollision(int[] dim1, int[] dim2) {
      //by default collision. invalidates with root coordinate
      if (dim1[0] + dim1[2] <= dim2[0])
         return false;
      if (dim1[0] >= dim2[0] + dim2[2])
         return false;
      if (dim1[1] + dim1[3] <= dim2[1])
         return false;
      if (dim1[1] >= dim2[1] + dim2[3])
         return false;
      return true;
   }
}
