package pasa.cbentley.core.src4.utils;

import pasa.cbentley.core.src4.ctx.UCtx;

public class MathUtils {

   /**
    * In the case x >= 0d in the posted code, think of z = y/x.
    * <br>
    * <br>
    * Plot the graph of the function f(z) = tan^{-1}(z). 
    * <br>
    * It is an s-shaped curve, symmetric through the origin, and is asymptotic to pi/2 as z approaches infinity and asymptotic
    *  to -pi/2 as z approaches -infinity. Now plot the graph of the function g(z) = (pi/2)*z/(1+|z|). 
    *  <br>
    * It is also s-shaped, symmetric through the origin, and has the same asymptotes. 
    * <br>
    * The maximum error for z > 0 occurs when the derivative of the function h(z) = f(z) - g(z) is zero. 
    * <br>
    * Setting h'(z) = 0 leads to a quadratic equation with roots (approximately) z = 0.313436 and z = 3.19044.
    * At those points, |h(z)| is approximately 0.0711 radians, which is slightly larger than 4 degrees. 
    * <br>
    * The website you linked to mentions this maximum error.
    * <br>
    * @param y
    * @param x
    * @return
    */
   public static double aTan(double y, double x) {
      double coeff_1 = Math.PI / 4d;
      double coeff_2 = 3d * coeff_1;
      double abs_y = Math.abs(y);
      double angle;
      if (x >= 0d) {
         double r = (x - abs_y) / (x + abs_y);
         angle = coeff_1 - coeff_1 * r;
      } else {
         double r = (x + abs_y) / (abs_y - x);
         angle = coeff_2 - coeff_1 * r;
      }
      //for y=2 and x=1 angle += 59.99999999999999
      //if we add 0.000000000000001 => in degrees that's += 0.00000000000006 1/14th is 6 degrees
      return y < 0d ? -angle : angle;
   }

   public static float aTanFloat(int y, int x) {
      float coeff_1 = (float) (Math.PI / 4d);
      float coeff_2 = 3f * coeff_1;
      float abs_y = Math.abs(y);
      float angle;
      if (x >= 0d) {
         float r = (x - abs_y) / (x + abs_y);
         angle = coeff_1 - coeff_1 * r;
      } else {
         float r = (x + abs_y) / (abs_y - x);
         angle = coeff_2 - coeff_1 * r;
      }
      //for y=2 and x=1 angle += 59.99999999999999
      //if we add 0.000000000000001 => in degrees that's += 0.00000000000006 1/14th is 6 degrees
      return y < 0d ? -angle : angle;
   }

   public static double fastInverseSqrt(double x) {
      double xhalf = 0.5 * x;
      long i = Double.doubleToLongBits(x);
      i = 0x5FE6EB50C7B537AAL - (i >> 1);
      x = Double.longBitsToDouble(i);
      x = x * (1.5 - xhalf * x * x);
      return x;
   }

   public static double pow(double x, double y) {
      if (y < 0) {
         x = ((double) 1) / x;
         y *= -1;
      }
      //Convert the real power to a fractional form     
      int den = 1024;
      //declare the denominator to be 1024     
      /*Conveniently 2^10=1024, so taking the square root 10   
       *   times will yield our estimate for n.  In our example     n^3=8^2    n^1024 = 8^683.*/
      int num = (int) (y * den);
      // declare numerator      
      int iterations = 10;
      /*declare the number of square root         iterations associated with our denominator, 1024.*/
      double n = Double.MAX_VALUE;
      /* we initialize our                  estimate, setting it to max*/
      while (n >= Double.MAX_VALUE && iterations > 1) {
         /*  We try to set our estimate equal to the right     
          *     hand side of the equation (e.g., 8^2048). 
          *      If this         number is too large, we will have to rescale. */
         n = x;
         for (int i = 1; i < num; i++)
            n *= x;
         /*here, we handle the condition where our starting     
          *     point is too large*/
         if (n >= Double.MAX_VALUE) {
            iterations--; /*reduce the iterations by one*/
            den = (int) (den / 2); /*redefine the denominator*/
            num = (int) (y * den); //redefine the numerator       
         }
      }
      /*************************************************     
       * ** We now have an appropriately sized right-hand-side. 
       *     ** Starting with this estimate for n, we proceed.  
       *        **************************************************/
      for (int i = 0; i < iterations; i++) {
         n = Math.sqrt(n);
      } // Return our estimate    
      return n;
   }

   public static double powTaylor(double a, double b) {
      if (b < 0) {
         a = ((double) 1) / a;
         b *= -1;
      }
      // true if base is greater than 1     
      boolean gt1 = (Math.sqrt((a - 1) * (a - 1)) <= 1) ? false : true;
      int oc = -1; // used to alternate math symbol (+,-)
      int iter = 20; // number of iterations     
      double p, x, x2, sumX, sumY;
      // is exponent a whole number? 
      if ((b - Math.floor(b)) == 0) {
         // return base^exponent    
         p = a;
         for (int i = 1; i < b; i++)
            p *= a;
         return p;
      }
      x = (gt1) ? (a / (a - 1)) : (a - 1);
      sumX = (gt1) ? (1 / x) : x;
      for (int i = 2; i < iter; i++) {
         // find x^iteration
         p = x;
         for (int j = 1; j < i; j++)
            p *= x;
         double xTemp = (gt1) ? (1 / (i * p)) : (p / i);
         sumX = (gt1) ? (sumX + xTemp) : (sumX + (xTemp * oc));
         oc *= -1; // change math symbol (+,-)   
      }
      x2 = b * sumX;
      sumY = 1 + x2;
      // our estimate  
      for (int i = 2; i <= iter; i++) {
         // find x2^iteration      
         p = x2;
         for (int j = 1; j < i; j++)
            p *= x2;
         // multiply iterations (ex: 3 iterations = 3*2*1)       
         int yTemp = 2;
         for (int j = i; j > 2; j--)
            yTemp *= j;
         // add to estimate (ex: 3rd iteration => (x2^3)/(3*2*1) )  
         sumY += p / yTemp;
      }
      return sumY; // return our estimate }  
   }

   /**
    * if 25 pixels separate origin from dest
    * 8 + 5 + 3 + 2 + 1 + 1= 20
    * Take the difference of the sum 25 - 20 = 5 so we will do
    * 8 + 5 + 5 + 3 + 2 + 1 + 1
    * for 26 pixels 26 - 20 = 6 => 5 + 1
    * 8 + 5 + 5 + 3 + 2 + 1 + 1 + 1
    * 
    * 25 - 8 = 17;
    * 17 - 5 = 12
    * 12 - 5 = 7
    * 7 - 3 = 4;
    * 4 - 2 = 2;
    * 1 - 1 = 1;
    * 1 - 1 = 0;
    */
   public int[] fib         = new int[] { 1, 1, 2, 3, 5, 8, 13, 21, 34, 55, 89, 144 };

   public int[] fib2        = new int[] { 2, 2, 4, 6, 10, 16, 26, 42 };

   /**
    * Maxed at 50
    */
   public int[] fibFast     = new int[] { 1, 3, 5, 8, 13, 21, 34, 50 };

   /**
    * k = k + i;
    */
   public int[] fibKth      = new int[] { 1, 3, 6, 10, 15, 21, 28, 36 };

   /**
    * k = i + j - ( (k/2 -1)) 
    */
   public int[] fibSmallMod = new int[] { 2, 2, 4, 5, 8, 11, 17, 25 };

   private UCtx uc;

   public MathUtils(UCtx uc) {
      this.uc = uc;
   }

   /**
    * Returns an array of size so that the value in the array do not exceed distance.
    * <br>
    * <br>
    * @param distance positive distance.
    * @return
    */
   public int[] getFibIncrement(int distance) {
      return getFibIncrement(distance, fib);
   }

   /**
    * Returns an array of size so that the value in the array do not exceed distance.
    * <br>
    * <br>
    * Special cases:
    * <li>When distance is <= to zero, treat distance as zero and return array with one value 0
    * <li>When distance is smaller than the smallest fibs value, return array with one value. i.e. the distance
    * <br>
    * <br>
    * Any difference left is added as a fib value in itself in the ordered array.
    * <br>
    * @param distance positive distance.
    * @param fibs an array with values ordered in ascending order. when there are not enough fibs, the last value will be taken
    * @return asc ordered integers who sum is equal to parameter distance
    * @throws NullPointerException when fibs is null
    */
   public static int[] getFibIncrement(int distance, int[] fibs) {
      int xFibMax = 0;
      int count = 0;
      //if distance is 0, an empty array is returned
      while (xFibMax < distance) {
         xFibMax += fibs[count];
         //prevent an arrayoutofbound exception.
         if (count + 1 < fibs.length) {
            count++;
         }
      }
      if (count == 0) {
         return new int[] { distance };
      }
      //now xFibMax is equal or bigger
      int oldFibmax = xFibMax - fibs[count - 1];
      int diff = distance - oldFibmax;
      if (diff != 0) {
         count--;
      }
      //the count is correct
      int[] xFib = new int[count];
      for (int i = 0; i < count; i++) {
         xFib[i] = fibs[i];
      }
      if (diff != 0) {
         xFib = IntUtils.addOrderedIntDouble(xFib, diff);
      }
      return xFib;
   }
}
