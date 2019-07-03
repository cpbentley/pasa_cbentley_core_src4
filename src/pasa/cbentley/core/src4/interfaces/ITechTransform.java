package pasa.cbentley.core.src4.interfaces;


/**
 * 
 * @author Charles Bentley
 *
 */
public interface ITechTransform {
   /**
    * No transformation. <br>
    * 8 different -> 4 bits for coding.
    */
   public static final int TRANSFORM_0_NONE                 = 0;

   /**
    * Flip around the horizontal axle.== Equivalent to {@link Sprite#TRANS_1_MIRROR_ROT180} 
    * <br>
    * Neutral <br>
    * <li>Rectangle
    * <li>Border Gradient
    * <br>
    * <br>
    * Dir Switch <br>
    * <li>Triangle Top <-> Bottom
    * <li>Triangle BR <-> TR
    * <li>Triangle BL <-> TL
    */
   public static final int TRANSFORM_1_FLIP_H_MIRROR_ROT180 = 1;

   /**
    * Flip around the vertical axle. Equivalent to {@link Sprite#TRANS_2_MIRROR} 
    * Neutral: <br> 
    * <li>For Rectangle
    * <li>Border Gradient
    * <li>Top and Bot Triangles
    * <br>
    * Dir Switch <br>
    * <li>Triangle Left <-> Right
    * <li>Triangle BR <-> BL
    * <li>Triangle TR <-> TL
    */
   public static final int TRANSFORM_2_FLIP_V_MIRROR        = 2;

   /**
    * Rotation of 180.
    * <br>
    * <br>
    * <b>Figure Neutral</b> for: <br> 
    * <li>Rectangle opaque or {@link IGradient#GRADIENT_TYPE_RECT_00_SQUARE}.
    * <li>Border Gradient
    * <br>
    * <b>Dir Switch</b> <br>
    * <li>Triangle {@link C#DIR_2LEFT} <->  {@link C#DIR_3RIGHT}
    * <li>Triangle {@link C#DIR_0TOP} <->  {@link C#DIR_1BOTTOM}
    * <li>Triangle {@link C#DIR_7BotRight} <->  {@link C#DIR_4TopLeft}
    * <li>Triangle {@link C#DIR_6BotLeft} <->  {@link C#DIR_5TopRight}
    * <br>
    * <br>
    * <br> Value/Meaning Equivalent to {@link Sprite#TRANS_3_ROT180} 
    */
   public static final int TRANSFORM_3_ROT_180              = 3;

   /**
    * <br> Value/Meaning Equivalent to {@link Sprite#TRANS_4_MIRROR_ROT270} 
    */
   public static final int TRANSFORM_4_MIRROR_ROT270        = 4;

   /**
    * Rotation of 90 degrees.
    * <br>
    * <br>
    * TODO animation of never ending rotation
    * <br>
    * <b>Dir Switch</b>
    * <li>{@link C#DIR_7BotRight} -> {@link C#DIR_6BotLeft}
    * <li>{@link C#DIR_6BotLeft} -> {@link C#DIR_4TopLeft}
    * <li>{@link C#DIR_4TopLeft} -> {@link C#DIR_5TopRight}
    * <li>{@link C#DIR_5TopRight} -> {@link C#DIR_7BotRight}
    * 
    * <br>
    * <br> Value/Meaning Equivalent to {@link Sprite#TRANS_5_ROT90} 
    */
   public static final int TRANSFORM_5_ROT_90               = 5;

   /**
    * <br> Value/Meaning Equivalent to {@link Sprite#TRANS_6_ROT270} 
    */
   public static final int TRANSFORM_6_ROT_270              = 6;

   /**
    * Flip around the vertical axle. 
    * <br>
    * <br> Value/Meaning Equivalent to {@link Sprite#TRANS_7_MIRROR_ROT90} 
    */
   public static final int TRANSFORM_7_MIRROR_ROT90         = 7;

   /**
    * Biggest valid value
    */
   public static final int TRANSFORM_MAX                    = 7;
   /**
    * Smallest valid value.
    */
   public static final int TRANSFORM_MIN                    = 0;
   /**
    * Number of transform values
    */
   public static final int TRANSFORM_COUNT                    = 8;
}
