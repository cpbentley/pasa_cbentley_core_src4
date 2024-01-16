package pasa.cbentley.core.src4.utils;

/**
 * 
 * https://stackoverflow.com/questions/13535172/list-of-all-unicodes-open-close-brackets
 * 
 * decoder
 * https://r12a.github.io/app-conversion/
 * 
 * @author Charles Bentley
 *
 */
public interface UnicodeBrakets {

   /**
    * \u29C5 = ⟅ -> ⟅ ⟆
    */
   public static final char BRAKET_S_SHAPE_BAG_1          = '\u29C5';

   /**
    * \u29C6 = ⟆ -> ⟅ ⟆ 
    */
   public static final char BRAKET_S_SHAPE_BAG_2          = '\u29C6';

   /**
    * \u29D8 = ⧘-> ⧘⧙
    */
   public static final char BRAKET_WIGGLY_FENCE_1         = '\u29D8';

   /**
    * \u29D9 = ⧙ -> ⧘⧙ 
    */
   public static final char BRAKET_WIGGLY_FENCE_2         = '\u29D9';

   /**
    * \u29DA = ⧚ -> ⧚⧛
    */
   public static final char BRAKET_WIGGLY_FENCE_DOUBLE_1  = '\u29DA';

   /**
    * \u29DB = ⧛ -> ⧚⧛ 
    */
   public static final char BRAKET_WIGGLY_FENCE_DOUBLE_2  = '\u29DB';

   /**
    * \u0F3C = ༼   s -> ༼ ༽
    */
   public static final char BRAKET_TIBETAN_ANG_KHANG_1    = '\u0F3C';

   /**
    * \u0F3D =   ༽  sa    ->  ༼ ༽
    */
   public static final char BRAKET_TIBETAN_ANG_KHANG_2    = '\u0F3D';

   /**
    * \u0F3A = ༺  s ->  ༺ ༻ 
    */
   public static final char BRAKET_TIBETAN_GUG_RTAGS_1    = '\u0F3A';

   /**
    * \uFD3B =  ༻  sa    ->  ༺ ༻
    */
   public static final char BRAKET_TIBETAN_GUG_RTAGS_2    = '\uFD3B';

   /**
    * \u169B = ᚛ -> ᚛ ᚜   
    */
   public static final char BRAKET_OGHAM_FEATHER_1        = '\u169B';

   /**
    * \u169C = ᚜  -> ᚛ ᚜ 
    */
   public static final char BRAKET_OGHAM_FEATHER_2        = '\u169C';

   /**
    * \uFD3E = ﴾ -< ﴾﴿
    */
   public static final char BRAKET_ORNATE_1               = '\uFD3E';

   /**
    * \uFD3F = ﴿ -< ﴾﴿
    */
   public static final char BRAKET_ORNATE_2               = '\uFD3F';

   /**
    * \u3018 = 〘 -> 〘〙
    */
   public static final char BRAKET_TORTOISE_SHELL_WHITE_1 = '\u3018';

   /**
    * \u3019 = 〙 -> 〘〙 
    */
   public static final char BRAKET_TORTOISE_SHELL_WHITE_2 = '\u3019';

   /**
    * \u3008 = 〈 -> 〈〉
    */
   public static final char BRAKET_ANGLE_1                = '\u3008';

   /**
    * \u3009 = 〉 -> 〈〉 
    */
   public static final char BRAKET_ANGLE_2                = '\u3009';

   /**
    * \u300A = 《 ->  《》
    */
   public static final char BRAKET_ANGLE_DOUBLE_1         = '\u300A';

   /**
    * \u300B = 》 ->  《》 
    */
   public static final char BRAKET_ANGLE_DOUBLE_2         = '\u300B';

   /**
    * \u3014 = 〔 -> 〔〕
    */
   public static final char BRAKET_TORTOISE_SHELL_1       = '\u3014';

   /**
    * \u3015 = 〕  -> 〔〕 
    */
   public static final char BRAKET_TORTOISE_SHELL_2       = '\u3015';

   /**
    * \u300E = 『 -> 『』
    */
   public static final char BRAKET_CORNER_WHITE_1         = '\u300E';

   /**
    * \u300F = 』 -> 『』 
    */
   public static final char BRAKET_CORNER_WHITE_2         = '\u300F';

   /**
    * \u300C = 「 -> 「」 
    */
   public static final char BRAKET_CORNER_1               = '\u300C';

   /**
    * \u3011 = 」 -> 「」 
    */
   public static final char BRAKET_CORNER_2               = '\u300D';

   /**
    * \u3010 = 【 -> 【】
    */
   public static final char BRAKET_LENTICULAR_BLACK_1     = '\u3010';

   /**
    * \u3011 = 】 -> 【】
    */
   public static final char BRAKET_LENTICULAR_BLACK_2     = '\u3011';

   /**
    * \u3016 = 〖 -> 〖〗
    */
   public static final char BRAKET_LENTICULAR_WHITE_1     = '\u3016';

   /**
    * \u3017 = 〗 -> 〖〗
    */
   public static final char BRAKET_LENTICULAR_WHITE_2     = '\u3017';

   /**
          * ❛ ❜ , ❝ ❞ , ❨ ❩ , ❪ ❫ , ❴ ❵ , ❬ ❭ , ❮ ❯ , ❰ ❱ , ❲ ❳ ⸨ 
    */
   public static final char BRAKET_DECO_0                 = 'c';

   /**
    * \u2E28 = -> ⸨ ⸩
    */
   public static final char BRAKET_DOUBLE_PARANTHESIS_1   = '\u2E28';

   /**
    * \u2E29 = ⸩ -> ⸨ ⸩
    */
   public static final char BRAKET_DOUBLE_PARANTHESIS_2   = '\u2E29';

   /**
    * \u298B = ⦋ -> ⦋ ⦌
    */
   public static final char BRAKET_SQUARE_UNDERBAR_1      = '\u298B';

   /**
    * \u298C = ⦌ -> ⦋ ⦌
    */
   public static final char BRAKET_SQUARE_UNDERBAR_2      = '\u298C';

}