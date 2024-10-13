/*
 * (c) 2018-2020 Charles-Philip Bentley
 * This code is licensed under MIT license (see LICENSE.txt for details)
 */
package pasa.cbentley.core.src4.stator;

import pasa.cbentley.core.src4.interfaces.ITech;
import pasa.cbentley.core.src4.utils.ColorUtils;

public interface ITechStator extends ITech {

   public static final int MAGIC_WORD_STATOR         = ColorUtils.getRGBInt(150, 200, 200, 201);

   public static final int MAGIC_WORD_MONO           = ColorUtils.getRGBInt(150, 210, 202, 201);

   public static final int MAGIC_WORD_PREFS          = ColorUtils.getRGBInt(200, 202, 200, 201);

   public static final int MAGIC_WORD_WRITER         = ColorUtils.getRGBInt(240, 200, 200, 201);

   public static final int MAGIC_WORD_OBJECT         = ColorUtils.getRGBInt(245, 200, 200, 201);

   public static final int MAGIC_WORD_OBJECT_PARAM   = ColorUtils.getRGBInt(245, 200, 206, 201);

   public static final int MAGIC_WORD_OBJECT_NULL    = ColorUtils.getRGBInt(245, 205, 200, 201);

   public static final int MAGIC_WORD_OBJECT_POINTER = ColorUtils.getRGBInt(247, 200, 200, 201);

   public static final int MAGIC_WORD_SEPARATOR      = ColorUtils.getRGBInt(240, 241, 242, 243);

   public static final int MAGIC_1_WRITER            = 1;

   public static final int MAGIC_0_NO_WRITER         = 0;

   /**
    * That's the main Writer/Reader from which others types are
    */
   public static final int TYPE_0_MASTER             = 0;

   /**
    * Contains view state
    */
   public static final int TYPE_1_VIEW               = 1;

   /**
    * State describing the data
    */
   public static final int TYPE_2_MODEL              = 2;

   /**
    * 
    * When loading the application, we want to load the ctx data bytes first.
    * 
    * We do not want Model and View stuff.
    */
   public static final int TYPE_3_CTX                = 3;

   /**
    * Single use Type used for special read/write
    */
   public static final int TYPE_4_TEMP               = 4;

   public static final int TYPE_5_TEMP_CONTAINER     = 5;

   public static final int TYPE_NUM                  = 6;

   public static final int FLAG_1_FAILED             = 1 << 0;
}
