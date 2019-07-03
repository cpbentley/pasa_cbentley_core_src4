package pasa.cbentley.core.src4.utils;

import pasa.cbentley.core.src4.ctx.UCtx;
import pasa.cbentley.core.src4.logging.Dctx;
import pasa.cbentley.core.src4.logging.IStringable;

/**
 * Helper class for dealing with colors.
 * <br>
 * TODO
 * Pour plus de couleurs et des visualizations voir C:\Java\Couleurs Françaises
 * @author Charles Bentley
 *
 */
public class ColorUtils implements IStringable {
   /**
    * blanchedalmond
    */
   public static final int   FR_BEIGE_Amande               = 0xFFEBCD;

   /**
    * antiquewhite
    */
   public static final int   FR_BEIGE_Antique              = 0xFAEBD7;

   public static final int   FR_BEIGE_Beige                = 0xF5F5DC;

   public static final int   FR_BEIGE_Bisque               = 0xFFE4BA;

   public static final int   FR_BEIGE_Blanc_Casse          = 0xFEFEE2;

   public static final int   FR_BEIGE_Blanc_coquillage     = 0xFFF5EE;

   public static final int   FR_BEIGE_Blanc_creme          = 0xFDF1B8;

   /**
    * oldlace
    */
   public static final int   FR_BEIGE_Blanc_dentelle       = 0xFDF5E6;

   public static final int   FR_BEIGE_Blanc_dEspagne       = 0xFEFDF0;

   public static final int   FR_BEIGE_Blanc_floral         = 0xFFFAF0;

   public static final int   FR_BEIGE_Blanc_Lavande        = 0xFFF0F5;

   public static final int   FR_BEIGE_Blanc_lin            = 0xFAF0E6;

   /**
    * navajowhite
    */
   public static final int   FR_BEIGE_Blanc_navaro         = 0xFFDEAD;

   public static final int   FR_BEIGE_Blanc_neige          = 0xFEFEFE;

   public static final int   FR_BEIGE_Blanc_spectral       = 0xF8F8FF;

   public static final int   FR_BEIGE_Coquille_oeuf        = 0xFDE9E0;

   public static final int   FR_BEIGE_Ecru                 = 0xFEFEE0;

   public static final int   FR_BEIGE_Ivoire               = 0xFFFFF0;

   public static final int   FR_BEIGE_Jaune_ble            = 0xF5DEB3;

   public static final int   FR_BEIGE_Mocassin             = 0xFFE4B5;

   public static final int   FR_BEIGE_Papaye               = 0xFFEFD5;

   public static final int   FR_BEIGE_Peche                = 0xFFDAB9;

   public static final int   FR_BEIGE_Platine              = 0xFAF0C5;

   public static final int   FR_BEIGE_Ventre_de_biche      = 0xE9C9B1;

   public final static int   FR_BLEU_Acier                 = 0x4682B4;

   public final static int   FR_BLEU_Acier_clair           = 0xB0C4DE;

   public final static int   FR_BLEU_Ardoise               = 0x6A5ACD;

   public final static int   FR_BLEU_Ardoise_fonce         = 0x483D8B;

   public final static int   FR_BLEU_Ardoise_moyen         = 0x7B68EE;

   public final static int   FR_BLEU_Azur                  = 0x87CEEB;

   public final static int   FR_BLEU_Azur_clair            = 0x87CEFA;

   public final static int   FR_BLEU_Azur_profond          = 0x00BFFF;

   public final static int   FR_BLEU_Azure_brume           = 0xF0FFFF;

   public final static int   FR_BLEU_Bleu                  = 0x0000FF;

   public final static int   FR_BLEU_Bleuet                = 0x6495ED;

   public final static int   FR_BLEU_Bleuet_fonce          = 0x5472AE;

   public final static int   FR_BLEU_Celeste               = 0x007FFF;

   public final static int   FR_BLEU_Ciel                  = 0x77B5FE;

   public final static int   FR_BLEU_Clair                 = 0xADD8E6;

   public final static int   FR_BLEU_Cobalt                = 0x22427C;

   public final static int   FR_BLEU_Denim                 = 0x1560BD;

   public final static int   FR_BLEU_Dragee                = 0xDFF2FF;

   public final static int   FR_BLEU_Electrique            = 0x2C75FF;

   public final static int   FR_BLEU_Fonce                 = 0x00008B;

   public final static int   FR_BLEU_Fumee                 = 0xBBD2E1;

   public final static int   FR_BLEU_Givre                 = 0x80D0D0;

   public final static int   FR_BLEU_Lavande               = 0xE6E6FA;

   public final static int   FR_BLEU_Marin                 = 0x000080;

   public final static int   FR_BLEU_Marine                = 0x03224C;

   public final static int   FR_BLEU_Minuit                = 0x191970;

   public final static int   FR_BLEU_Minuit2               = 0x003366;

   public final static int   FR_BLEU_Moyen                 = 0x0000CD;

   public final static int   FR_BLEU_Nuit                  = 0x0F056B;

   public final static int   FR_BLEU_Outremer              = 0x1B019B;

   public final static int   FR_BLEU_Persan                = 0x6600FF;

   public final static int   FR_BLEU_Petrole               = 0x5F9EA0;

   public final static int   FR_BLEU_Petrole_fonce         = 0x1D4851;

   public final static int   FR_BLEU_Poudre                = 0xB0E0E6;

   public final static int   FR_BLEU_Prusse                = 0x24445C;

   public final static int   FR_BLEU_Roi                   = 0x318CE7;

   public final static int   FR_BLEU_Royal                 = 0x4169E1;

   public final static int   FR_BLEU_Saphir                = 0x0131B4;

   public final static int   FR_BLEU_Toile                 = 0x1E90FF;

   public final static int   FR_BORDEAUX_Amarante_foncee   = 0x91283B;

   public final static int   FR_BORDEAUX_Bordeaux          = 0x6D071A;

   public final static int   FR_BORDEAUX_Bourgogne         = 0x6B0D0D;

   public final static int   FR_BORDEAUX_Framboise         = 0xC72C48;

   public final static int   FR_BORDEAUX_Grenat            = 0x6E0B14;

   public final static int   FR_BORDEAUX_Lie_de_vin        = 0xAC1E44;

   public final static int   FR_BORDEAUX_Pourpre           = 0x9E0E40;

   public final static int   FR_BORDEAUX_Puce              = 0x4E1609;

   public final static int   FR_BORDEAUX_Sang_de_boeuf     = 0x730800;

   public final static int   FR_BORDEAUX_Sanguine          = 0x850606;

   public static final int   FR_BRUN_Acajou                = 0x88421D;

   public static final int   FR_BRUN_Alezan                = 0xA76726;

   public static final int   FR_BRUN_Auburn                = 0xAD4F09;

   public static final int   FR_BRUN_Beige                 = 0xC8AD7F;

   public static final int   FR_BRUN_Bistre                = 0x856D4D;

   public static final int   FR_BRUN_Bitume                = 0x4E3D28;

   public static final int   FR_BRUN_Bois_rustique         = 0xDEB887;

   public static final int   FR_BRUN_Brique                = 0x842E1B;

   public static final int   FR_BRUN_Bronze                = 0x614E1A;

   public static final int   FR_BRUN_Brou_de_noix          = 0x3F2204;

   public static final int   FR_BRUN_Brun                  = 0x5B3C11;

   public static final int   FR_BRUN_Cacao                 = 0x614B3A;

   public static final int   FR_BRUN_Cachou                = 0x2F1B0C;

   public static final int   FR_BRUN_Cafe                  = 0x462E01;

   public static final int   FR_BRUN_Cafe_au_lait          = 0x785E2F;

   public static final int   FR_BRUN_Cannelle              = 0x7E5835;

   public static final int   FR_BRUN_Caramel               = 0x7E3300;

   public static final int   FR_BRUN_Chamois               = 0xD0C07A;

   public static final int   FR_BRUN_Chataigne             = 0x806D5A;

   public static final int   FR_BRUN_Chatain               = 0x8B6C42;

   public static final int   FR_BRUN_Chaudron              = 0x85530F;

   public static final int   FR_BRUN_Chocolat              = 0xD2691E;

   public static final int   FR_BRUN_Chocolat_fonce        = 0x5A3A22;

   public static final int   FR_BRUN_Cuir                  = 0x8B4513;

   public static final int   FR_BRUN_Cuivre                = 0xB36700;

   public static final int   FR_BRUN_Grege                 = 0xBBAE98;

   public static final int   FR_BRUN_Gris_de_maure         = 0x685E43;

   public static final int   FR_BRUN_Gris_taupe            = 0x463F32;

   public static final int   FR_BRUN_Havane                = 0x947F60;

   public static final int   FR_BRUN_Lavalliere            = 0x8F5922;

   public static final int   FR_BRUN_Marron                = 0x582900;

   public static final int   FR_BRUN_Mastic                = 0xB3B191;

   public static final int   FR_BRUN_Mordore               = 0x87591A;

   public static final int   FR_BRUN_Noisette              = 0x955628;

   public static final int   FR_BRUN_Perou                 = 0xCD853F;

   public static final int   FR_BRUN_Rouille               = 0x985717;

   public static final int   FR_BRUN_Roux                  = 0xD2B48C;

   public static final int   FR_BRUN_Sable                 = 0xE0CDA9;

   public static final int   FR_BRUN_Sepia                 = 0xAE8964;

   public static final int   FR_BRUN_Tabac                 = 0x9F551E;

   public static final int   FR_BRUN_Terre_de_sienne       = 0xA0522D;

   public static final int   FR_BRUN_Vanille               = 0xE1CE9A;

   public final static int   FR_CYAN_Aigue_marine          = 0x7FFFD4;

   public final static int   FR_CYAN_Aigue_marine_moyen    = 0x66CDAA;

   public final static int   FR_CYAN_Azurin                = 0xAFEEEE;

   public final static int   FR_CYAN_Bleu_alice            = 0xF0F8FF;

   public final static int   FR_CYAN_Bleu_azur             = 0xF0FFFF;

   public final static int   FR_CYAN_Bleu_canard           = 0x048B9A;

   public final static int   FR_CYAN_Bleu_paon             = 0x067790;

   public final static int   FR_CYAN_Clair                 = 0xE0FFFF;

   public final static int   FR_CYAN_Cyan                  = 0x00FFFF;

   public final static int   FR_CYAN_Fonce                 = 0x008B8B;

   public final static int   FR_CYAN_Turquoise             = 0x40E0D0;

   public final static int   FR_CYAN_Turquoise_fonce       = 0x00CED1;

   public final static int   FR_CYAN_Turquoise_moyen       = 0x48D1CC;

   public final static int   FR_CYAN_Vert_main_clair       = 0x20B2AA;

   public final static int   FR_CYAN_Vert_sarcelle         = 0x008080;

   public static final int   FR_GRIS_Acier                 = 0xA9A9A9;

   public static final int   FR_GRIS_Anthracite            = 0x303030;

   public static final int   FR_GRIS_Ardoise               = 0x708090;

   public static final int   FR_GRIS_Ardoise_clair         = 0x778899;

   public static final int   FR_GRIS_Ardoise_fonce         = 0x5A5E6B;

   public static final int   FR_GRIS_Argent                = 0xC0C0C0;

   public static final int   FR_GRIS_Argile                = 0xEFEFEF;

   public static final int   FR_GRIS_BLANC                 = 0xFFFFFF;

   public static final int   FR_GRIS_Carbone               = 0x130E0A;

   public static final int   FR_GRIS_Charbonneux           = 0x000010;

   public static final int   FR_GRIS_Clair                 = 0xD3D3D3;

   public static final int   FR_GRIS_Etain                 = 0xDCDCDC;

   public static final int   FR_GRIS_Fer                   = 0x8f8f8f;

   public static final int   FR_GRIS_Fumee                 = 0xF5F5F5;

   public static final int   FR_GRIS_Gris                  = 0x808080;

   public static final int   FR_GRIS_Mat                   = 0x696969;

   public static final int   FR_GRIS_Noir                  = 0x000000;

   public static final int   FR_GRIS_Perle                 = 0xCECECE;

   public static final int   FR_GRIS_Plomb                 = 0x798081;

   public static final int   FR_GRIS_Reglisse              = 0x2D241E;

   public static final int   FR_GRIS_Souris                = 0x9E9E9E;

   public final static int   FR_GRIS_Tourterelle           = 0xBBACAC;

   public static final int   FR_GRIS_Vert                  = 0x2F4F4F;

   public final static int   FR_JAUNE_Ambre_jaune          = 0xF0C300;

   public final static int   FR_JAUNE_Banane               = 0xD1B606;

   public final static int   FR_JAUNE_Beurre               = 0xF0E36B;

   public final static int   FR_JAUNE_Ble                  = 0xE8D630;

   public final static int   FR_JAUNE_Bouton_dor           = 0xFCDC12;

   public final static int   FR_JAUNE_Brun_kaki            = 0xF0E68C;

   public final static int   FR_JAUNE_Caca_doie            = 0xCDCD0D;

   public final static int   FR_JAUNE_Canari               = 0xE7F00D;

   public final static int   FR_JAUNE_Champagne            = 0xFBF2B7;

   public final static int   FR_JAUNE_Chartreuse           = 0xDFFF00;

   public final static int   FR_JAUNE_Citron               = 0xF7FF3C;

   public final static int   FR_JAUNE_Citron_soie          = 0xFFFACD;

   public final static int   FR_JAUNE_Clair                = 0xFFFFE0;

   public final static int   FR_JAUNE_Dor                  = 0xF6DC12;

   /** goldenrod */
   public final static int   FR_JAUNE_Dore                 = 0xDAA520;

   public final static int   FR_JAUNE_Dore_clair           = 0xFAFAD2;

   public final static int   FR_JAUNE_Dore_fonce           = 0xB8860B;

   public final static int   FR_JAUNE_Dore_pale            = 0xEEE8AA;

   public final static int   FR_JAUNE_Imperial             = 0xFFE436;

   public final static int   FR_JAUNE_Jaune                = 0xFFFF00;

   public final static int   FR_JAUNE_Mais                 = 0xFFDE75;

   public final static int   FR_JAUNE_Mais_doux            = 0xFFF8DC;

   public final static int   FR_JAUNE_Miel                 = 0xDAB30A;

   public final static int   FR_JAUNE_Mimosa               = 0xFEF86C;

   public final static int   FR_JAUNE_Moutarde             = 0xC7CF00;

   public final static int   FR_JAUNE_Ocre                 = 0xDFAF2C;

   public final static int   FR_JAUNE_Or                   = 0xFFD700;

   public final static int   FR_JAUNE_Paille               = 0xFEE347;

   public final static int   FR_JAUNE_Poussin              = 0xF7E35F;

   public final static int   FR_JAUNE_Safran               = 0xEFD807;

   public final static int   FR_JAUNE_Secondaire           = 0xFDEE00;

   public final static int   FR_JAUNE_Soufre               = 0xFFFF6B;

   public final static int   FR_JAUNE_Topaze               = 0xFAEA73;

   public static final int   FR_ORANGE_Abricot             = 0xE67E30;

   public static final int   FR_ORANGE_Aquilain            = 0xAD4F09;

   public static final int   FR_ORANGE_Blond               = 0xE2BC74;

   public static final int   FR_ORANGE_Blond_venitien      = 0xE7A854;

   public static final int   FR_ORANGE_Brulee              = 0xCC5500;

   public static final int   FR_ORANGE_Brun_sable          = 0xF4A460;

   public static final int   FR_ORANGE_Carotte             = 0xF4661B;

   public static final int   FR_ORANGE_Citrouille          = 0xDF6D14;

   public static final int   FR_ORANGE_Fonce               = 0xFF8C00;

   public static final int   FR_ORANGE_Mandarin            = 0xFEA347;

   public static final int   FR_ORANGE_Melon               = 0xDE9816;

   public static final int   FR_ORANGE_Orange              = 0xFFA500;

   public static final int   FR_ORANGE_Orangee             = 0xFF4500;

   public final static int   FR_ROSE_Amarante              = 0x91283B;

   public final static int   FR_ROSE_Bonbon                = 0xF9429E;

   public final static int   FR_ROSE_Brumeux               = 0xFFE4E1;

   /**rosyborwn */
   public final static int   FR_ROSE_Brun_Rosee            = 0xBC8F8F;

   public final static int   FR_ROSE_Cerise                = 0xDE3163;

   public final static int   FR_ROSE_Clair                 = 0xFFB6C1;

   public final static int   FR_ROSE_Dragee                = 0xFEBFD2;

   public final static int   FR_ROSE_Fuchsia               = 0xFD3F92;

   public final static int   FR_ROSE_Magenta_Fushia        = 0xDB0073;

   public final static int   FR_ROSE_Passion               = 0xFF69B4;

   public final static int   FR_ROSE_Pelure_doignon        = 0xD58490;

   public final static int   FR_ROSE_Profond               = 0xFF1493;

   public final static int   FR_ROSE_Rose                  = 0xFFC0CB;

   public final static int   FR_ROSE_Rose2                 = 0xFD6C9E;

   public final static int   FR_ROSE_Rubis                 = 0xE0115F;

   public final static int   FR_ROSE_Vif                   = 0xFF007F;

   public static final int   FR_ROUGE_Ambre                = 0xAD390E;

   public static final int   FR_ROUGE_Anglais              = 0xF7230C;

   public static final int   FR_ROUGE_Bordeaux             = 0x800000;

   public static final int   FR_ROUGE_Brique               = 0xB22222;

   public static final int   FR_ROUGE_Brun                 = 0xA52A2A;

   public static final int   FR_ROUGE_Capucine             = 0xFF5E4D;

   public static final int   FR_ROUGE_Cardinal             = 0xB82010;

   public static final int   FR_ROUGE_Carmin               = 0x960018;

   public static final int   FR_ROUGE_Cerise               = 0xBB0B0B;

   public static final int   FR_ROUGE_Coquelicot           = 0xC60800;

   public static final int   FR_ROUGE_Corail               = 0xFF7F50;

   public static final int   FR_ROUGE_Corail_clair         = 0xF08080;

   public static final int   FR_ROUGE_Cramoisi             = 0xDC143C;

   public static final int   FR_ROUGE_Ecarlate             = 0xED0000;

   public static final int   FR_ROUGE_Ecrevisse            = 0xBC2001;

   public static final int   FR_ROUGE_Feu                  = 0xFE1B00;

   public static final int   FR_ROUGE_Fonce                = 0x8B0000;

   public static final int   FR_ROUGE_Fraise               = 0xBF3030;

   public static final int   FR_ROUGE_Grenadine            = 0xE9383F;

   public static final int   FR_ROUGE_Grenat               = 0x6E0B14;

   public static final int   FR_ROUGE_Groseille            = 0xCF0A1D;

   public static final int   FR_ROUGE_Indien               = 0xCD5C5C;

   public static final int   FR_ROUGE_Rouge                = 0xFF0000;

   public static final int   FR_ROUGE_Sang                 = 0x850606;

   public static final int   FR_ROUGE_Tomate               = 0xFF6347;

   public static final int   FR_ROUGE_Tomate2              = 0xDE2916;

   public static final int   FR_ROUGE_Tomette              = 0xAE4A34;

   public static final int   FR_ROUGE_Vermeil              = 0xFF0921;

   public static final int   FR_ROUGE_Vermillon            = 0xDB1702;

   public static final int   FR_SAUMON_Aurore              = 0xFFCB60;

   public static final int   FR_SAUMON_Chair               = 0xFEC3AC;

   public static final int   FR_SAUMON_Clair               = 0xFFA07A;

   public static final int   FR_SAUMON_Fonce               = 0xE9967A;

   public static final int   FR_SAUMON_Incarnat            = 0xFF6F7D;

   public static final int   FR_SAUMON_Nacarat             = 0xFC5D5D;

   public static final int   FR_SAUMON_Peche               = 0xFDBFB7;

   public static final int   FR_SAUMON_Rose_the            = 0xFF866A;

   public static final int   FR_SAUMON_Saumon              = 0xFA8072;

   public static final int   FR_VERT_Absinthe              = 0x7FDD4C;

   public static final int   FR_VERT_Amande                = 0x82C46C;

   public static final int   FR_VERT_Anis                  = 0x9FE855;

   public static final int   FR_VERT_Asperge               = 0x7BA05B;

   public static final int   FR_VERT_Avocat                = 0x568203;

   public static final int   FR_VERT_Blanc_menthe          = 0xF5FFFA;

   public static final int   FR_VERT_Bouteille             = 0x096A09;

   public static final int   FR_VERT_Brun_kaki_fonce       = 0xBDB76B;

   public static final int   FR_VERT_Celadon               = 0x83A697;

   public static final int   FR_VERT_Chartreuse            = 0xC2F732;

   public static final int   FR_VERT_Chartreuse2           = 0x7FFF00;

   public static final int   FR_VERT_Citron_vert           = 0x00FF00;

   public static final int   FR_VERT_Citron_vert_fonce     = 0x32CD32;

   public static final int   FR_VERT_Clair                 = 0x90EE90;

   public static final int   FR_VERT_Deau                  = 0xB0F2B6;

   public static final int   FR_VERT_Emeraude              = 0x01D758;

   public static final int   FR_VERT_Epinard               = 0x175732;

   public static final int   FR_VERT_Fonce                 = 0x006400;

   public static final int   FR_VERT_Foret                 = 0x228B22;

   public static final int   FR_VERT_Gazon                 = 0x3A9D23;

   public static final int   FR_VERT_Imperial              = 0x00561B;

   public static final int   FR_VERT_Jade                  = 0x87E990;

   public static final int   FR_VERT_Jaune                 = 0xADFF2F;

   public static final int   FR_VERT_Jaune_vert            = 0x9ACD32;

   public static final int   FR_VERT_Kaki                  = 0x6B8E23;

   public static final int   FR_VERT_Lichen                = 0x85C17E;

   public static final int   FR_VERT_Malachite             = 0x1FA055;

   public static final int   FR_VERT_Menthe                = 0x16B84E;

   public static final int   FR_VERT_Menthe_a_leau         = 0x54F98D;

   public static final int   FR_VERT_Miellat               = 0xF0FFF0;

   public static final int   FR_VERT_Militaire             = 0x596643;

   public static final int   FR_VERT_Mousse                = 0x679F5A;

   public static final int   FR_VERT_Ocean                 = 0x2E8B57;

   public static final int   FR_VERT_Ocean_fonce           = 0x8FBC8F;

   public static final int   FR_VERT_Ocean_moyen           = 0x3CB371;

   public static final int   FR_VERT_Olive                 = 0x808000;

   public static final int   FR_VERT_Olive_fonce           = 0x556B2F;

   public static final int   FR_VERT_Opaline               = 0x97DFC6;

   public static final int   FR_VERT_Pale                  = 0x98FB98;

   public static final int   FR_VERT_Perroquet             = 0x3AF24B;

   public static final int   FR_VERT_Pin                   = 0x01796F;

   public static final int   FR_VERT_Pistache              = 0xBEF574;

   public static final int   FR_VERT_Poireau               = 0x4CA66B;

   public static final int   FR_VERT_Pomme                 = 0x34C924;

   public static final int   FR_VERT_Prairie               = 0x7CFC00;

   public static final int   FR_VERT_Prairie2              = 0x57D53B;

   public static final int   FR_VERT_Printemps             = 0x00FF7F;

   public static final int   FR_VERT_Printemps_moyen       = 0x00FA9A;

   public static final int   FR_VERT_Sapin                 = 0x095228;

   public static final int   FR_VERT_Sauge                 = 0x689D71;

   public static final int   FR_VERT_Tilleul               = 0xA5D152;

   public static final int   FR_VERT_Veronese              = 0x5A6521;

   public static final int   FR_VERT_Vert                  = 0x008000;

   public static final int   FR_VERT_Vert_de_gris          = 0x95A595;

   public static final int   FR_VERT_Vert_kaki             = 0x798933;

   public final static int   FR_VIOLET_Amethyste           = 0x884DA7;

   public final static int   FR_VIOLET_Aubergine           = 0x370028;

   public final static int   FR_VIOLET_Bleu_ardoise        = 0x6A5ACD;

   public final static int   FR_VIOLET_Bleu_ardoise_fonce  = 0x483D8B;

   public final static int   FR_VIOLET_Bleu_ardoise_moyen  = 0x7B68EE;

   public final static int   FR_VIOLET_Bleu_lavande        = 0x9683EC;

   public final static int   FR_VIOLET_Bleu_Persan         = 0x6600FF;

   public final static int   FR_VIOLET_Bleu_violet         = 0x8A2BE2;

   public final static int   FR_VIOLET_Byzantin            = 0xBD33A4;

   public final static int   FR_VIOLET_Cassis              = 0x2C030B;

   public final static int   FR_VIOLET_Chardon             = 0xD8BFD8;

   public final static int   FR_VIOLET_De_Bayeux           = 0x682145;

   public final static int   FR_VIOLET_Deveque             = 0x723E64;

   public final static int   FR_VIOLET_Fonce               = 0x9400D3;

   public final static int   FR_VIOLET_Fushia              = 0xFF00FF;

   public final static int   FR_VIOLET_Fushia_fonce        = 0xF400A1;

   public final static int   FR_VIOLET_Glycine             = 0xC9A0DC;

   public final static int   FR_VIOLET_Gorge_de_pigeon     = 0x6A455D;

   public final static int   FR_VIOLET_Gris_de_lin         = 0xD2CAEC;

   public final static int   FR_VIOLET_Indigo              = 0x4B0082;

   public final static int   FR_VIOLET_Indigo_chaud        = 0x791CF8;

   public final static int   FR_VIOLET_Indigo_fonce        = 0x2E006C;

   public final static int   FR_VIOLET_Lilas               = 0xB666D2;

   public final static int   FR_VIOLET_Magenta_fonce       = 0x8B008B;

   public final static int   FR_VIOLET_Mauve               = 0xD473D4;

   public final static int   FR_VIOLET_Moyen               = 0xC71585;

   public final static int   FR_VIOLET_Orchidee            = 0xDA70D6;

   public final static int   FR_VIOLET_Orchidee_fonce      = 0x9932CC;

   public final static int   FR_VIOLET_Orchidee_moyen      = 0xBA55D3;

   public final static int   FR_VIOLET_Pale                = 0xDB7093;

   public final static int   FR_VIOLET_Parme               = 0xCFA0E9;

   public final static int   FR_VIOLET_Pervenche           = 0xCCCCFF;

   public final static int   FR_VIOLET_Pourpre             = 0x800080;

   public final static int   FR_VIOLET_Pourpre_moyen       = 0x9370DB;

   public final static int   FR_VIOLET_Prune               = 0x811453;

   public final static int   FR_VIOLET_Prune2              = 0xDDA0DD;

   public final static int   FR_VIOLET_Violet              = 0xEE82EE;

   public final static int   FR_VIOLET_Violet2             = 0x660099;

   public final static int   FR_VIOLET_Violine             = 0xA10684;

   public final static int   FR_VIOLET_Zinzolin            = 0x6C0277;

   public static final int   FULLY_OPAQUE_BEIGE            = getRGBInt(255, 255, 128);

   public static final int   FULLY_OPAQUE_BLACK            = 0xFF000000;

   public static final int   FULLY_OPAQUE_BLUE             = getRGBInt(0, 0, 255);

   public static final int   FULLY_OPAQUE_CYAN             = getRGBInt(0, 255, 255);

   public static final int   FULLY_OPAQUE_GREEN            = getRGBInt(0, 255, 0);

   public static final int   FULLY_OPAQUE_GREY             = getRGBInt(128, 128, 128);

   public static final int   FULLY_OPAQUE_ORANGE           = getRGBInt(255, 128, 0);

   public static final int   FULLY_OPAQUE_PINK             = getRGBInt(255, 128, 255);

   public static final int   FULLY_OPAQUE_PURPLE           = getRGBInt(255, 0, 255);

   public static final int   FULLY_OPAQUE_RED              = getRGBInt(255, 0, 0);

   public static final int   FULLY_OPAQUE_SKY_BLUE         = getRGBInt(0, 128, 255);

   public static final int   FULLY_OPAQUE_SKY_GREEN        = getRGBInt(128, 255, 128);

   public static final int   FULLY_OPAQUE_WHITE            = -1;

   public static final int   FULLY_OPAQUE_YELLOW           = getRGBInt(255, 255, 0);

   public static final int   FULLY_TRANSPARENT_BLACK       = 0;

   public static final int   FULLY_TRANSPARENT_CYAN        = getRGBInt(0, 0, 255, 255);

   public static final int   FULLY_TRANSPARENT_WHITE       = 0x00FFFFFF;

   public static final int   FZ128_BLUE                    = getRGBInt(0, 0, 128);

   public static final int   FZ128_GREEN                   = getRGBInt(0, 128, 0);

   public static final int   FZ128_GREEN_BLUE              = getRGBInt(0, 128, 128);

   public static final int   FZ128_RED                     = getRGBInt(128, 0, 0);

   public static final int   FZ128_RED_BLUE                = getRGBInt(128, 0, 128);

   public static final int   FZ128_RED_GREEN               = getRGBInt(128, 128, 0);

   public static final float TO_GRAY_BLUE_DESATURATOR_FIX  = 0.114f;

   public static final float TO_GRAY_GREEN_DESATURATOR_FIX = 0.587f;

   public static final float TO_GRAY_RED_DESATURATOR_FIX   = 0.299f;

   public static final int   WEB_aliceblue                 = 0xf0f8ff;

   public static final int   WEB_antiquewhite              = 0xfaebd7;

   public static final int   WEB_aqua                      = 0x00ffff;

   public static final int   WEB_aquamarine                = 0x7fffd4;

   public static final int   WEB_azure                     = 0xf0ffff;

   public static final int   WEB_beige                     = 0xf5f5dc;

   public static final int   WEB_bisque                    = 0xffe4c4;

   public static final int   WEB_black                     = 0x000000;

   public static final int   WEB_blanchedalmond            = 0xffebcd;

   public static final int   WEB_blue                      = 0x0000ff;

   public static final int   WEB_blueviolet                = 0x8a2be2;

   public static final int   WEB_brown                     = 0xa52a2a;

   public static final int   WEB_burlywood                 = 0xdeb887;

   public static final int   WEB_cadetblue                 = 0x5f9ea0;

   public static final int   WEB_chartreuse                = 0x7fff00;

   public static final int   WEB_chocolate                 = 0xd2691e;

   public static final int   WEB_coral                     = 0xff7f50;

   public static final int   WEB_cornflowerblue            = 0x6495ed;

   public static final int   WEB_cornsilk                  = 0xfff8dc;

   public static final int   WEB_crimson                   = 0xdc143c;

   public static final int   WEB_cyan                      = 0x00ffff;

   public static final int   WEB_darkblue                  = 0x00008b;

   public static final int   WEB_darkcyan                  = 0x008b8b;

   public static final int   WEB_darkgoldenrod             = 0xb8860b;

   public static final int   WEB_darkgray                  = 0xa9a9a9;

   public static final int   WEB_darkgreen                 = 0x006400;

   public static final int   WEB_darkgrey                  = 0xa9a9a9;

   public static final int   WEB_darkkhaki                 = 0xbdb76b;

   public static final int   WEB_darkmagenta               = 0x8b008b;

   public static final int   WEB_darkolivegreen            = 0x556b2f;

   public static final int   WEB_darkorange                = 0xff8c00;

   public static final int   WEB_darkorchid                = 0x9932cc;

   public static final int   WEB_darkred                   = 0x8b0000;

   public static final int   WEB_darksalmon                = 0xe9967a;

   public static final int   WEB_darkseagreen              = 0x8fbc8f;

   public static final int   WEB_darkslateblue             = 0x483d8b;

   public static final int   WEB_darkslategrey             = 0x2f4f4f;

   public static final int   WEB_darkturquoise             = 0x00ced1;

   public static final int   WEB_darkviolet                = 0x9400d3;

   public static final int   WEB_deeppink                  = 0xff1493;

   public static final int   WEB_deepskyblue               = 0x00bfff;

   public static final int   WEB_dimgrey                   = 0x696969;

   public static final int   WEB_dodgerblue                = 0x1e90ff;

   public static final int   WEB_firebrick                 = 0xb22222;

   public static final int   WEB_floralwhite               = 0xfffaf0;

   public static final int   WEB_forestgreen               = 0x228b22;

   public static final int   WEB_fuchsia                   = 0xff00ff;

   public static final int   WEB_gainsboro                 = 0xdcdcdc;

   public static final int   WEB_ghostwhite                = 0xf8f8ff;

   public static final int   WEB_gold                      = 0xffd700;

   public static final int   WEB_goldenrod                 = 0xdaa520;

   public static final int   WEB_green                     = 0x008000;

   public static final int   WEB_greenyellow               = 0xadff2f;

   public static final int   WEB_grey                      = 0x808080;

   public static final int   WEB_honeydew                  = 0xf0fff0;

   public static final int   WEB_hotpink                   = 0xff69b4;

   public static final int   WEB_indianred                 = 0xcd5c5c;

   public static final int   WEB_indigo                    = 0x4b0082;

   public static final int   WEB_ivory                     = 0xfffff0;

   public static final int   WEB_khaki                     = 0xf0e68c;

   public static final int   WEB_lavender                  = 0xe6e6fa;

   public static final int   WEB_lavenderblush             = 0xfff0f5;

   public static final int   WEB_lawngreen                 = 0x7cfc00;

   public static final int   WEB_lemonchiffon              = 0xfffacd;

   public static final int   WEB_lightblue                 = 0xadd8e6;

   public static final int   WEB_lightcoral                = 0xf08080;

   public static final int   WEB_lightcyan                 = 0xe0ffff;

   public static final int   WEB_lightgoldenrodyellow      = 0xfafad2;

   public static final int   WEB_lightgray                 = 0xd3d3d3;

   public static final int   WEB_lightgreen                = 0x90ee90;

   public static final int   WEB_lightgrey                 = 0xd3d3d3;

   public static final int   WEB_lightpink                 = 0xffb6c1;

   public static final int   WEB_lightsalmon               = 0xffa07a;

   public static final int   WEB_lightseagreen             = 0x20b2aa;

   public static final int   WEB_lightskyblue              = 0x87cefa;

   public static final int   WEB_lightslategray            = 0x778899;

   public static final int   WEB_lightslategrey            = 0x778899;

   public static final int   WEB_lightsteelblue            = 0xb0c4de;

   public static final int   WEB_lightyellow               = 0xffffe0;

   public static final int   WEB_lime                      = 0x00ff00;

   public static final int   WEB_limegreen                 = 0x32cd32;

   public static final int   WEB_linen                     = 0xfaf0e6;

   public static final int   WEB_magenta                   = 0xff00ff;

   public static final int   WEB_maroon                    = 0x800000;

   public static final int   WEB_mediumaquamarine          = 0x66cdaa;

   public static final int   WEB_mediumblue                = 0x0000cd;

   public static final int   WEB_mediumorchid              = 0xba55d3;

   public static final int   WEB_mediumpurple              = 0x9370db;

   public static final int   WEB_mediumseagreen            = 0x3cb371;

   public static final int   WEB_mediumslateblue           = 0x7b68ee;

   public static final int   WEB_mediumspringgreen         = 0x00fa9a;

   public static final int   WEB_mediumturquoise           = 0x48d1cc;

   public static final int   WEB_mediumvioletred           = 0xc71585;

   public static final int   WEB_midnightblue              = 0x191970;

   public static final int   WEB_mintcream                 = 0xf5fffa;

   public static final int   WEB_mistyrose                 = 0xffe4e1;

   public static final int   WEB_moccasin                  = 0xffe4b5;

   public static final int   WEB_navajowhite               = 0xffdead;

   public static final int   WEB_navy                      = 0x000080;

   public static final int   WEB_oldlace                   = 0xfdf5e6;

   public static final int   WEB_olive                     = 0x808000;

   public static final int   WEB_olivedrab                 = 0x6b8e23;

   public static final int   WEB_orange                    = 0xffa500;

   public static final int   WEB_orangered                 = 0xff4500;

   public static final int   WEB_orchid                    = 0xda70d6;

   public static final int   WEB_palegoldenrod             = 0xeee8aa;

   public static final int   WEB_palegreen                 = 0x98fb98;

   public static final int   WEB_paleturquoise             = 0xafeeee;

   public static final int   WEB_palevioletred             = 0xdb7093;

   public static final int   WEB_papayawhip                = 0xffefd5;

   public static final int   WEB_peachpuff                 = 0xffdab9;

   public static final int   WEB_peru                      = 0xcd853f;

   public static final int   WEB_pink                      = 0xffc0cb;

   public static final int   WEB_plum                      = 0xdda0dd;

   public static final int   WEB_powderblue                = 0xb0e0e6;

   public static final int   WEB_purple                    = 0x800080;

   public static final int   WEB_red                       = 0xff0000;

   public static final int   WEB_rosybrown                 = 0xbc8f8f;

   public static final int   WEB_royalblue                 = 0x4169e1;

   public static final int   WEB_saddlebrown               = 0x8b4513;

   public static final int   WEB_salmon                    = 0xfa8072;

   public static final int   WEB_sandybrown                = 0xf4a460;

   public static final int   WEB_seagreen                  = 0x2e8b57;

   public static final int   WEB_seashell                  = 0xfff5ee;

   public static final int   WEB_sienna                    = 0xa0522d;

   public static final int   WEB_silver                    = 0xc0c0c0;

   public static final int   WEB_skyblue                   = 0x87ceeb;

   public static final int   WEB_slateblue                 = 0x6a5acd;

   public static final int   WEB_slategray                 = 0x708090;

   public static final int   WEB_slategrey                 = 0x708090;

   public static final int   WEB_snow                      = 0xfffafa;

   public static final int   WEB_springgreen               = 0x00ff7f;

   public static final int   WEB_steelblue                 = 0x4682b4;

   public static final int   WEB_tan                       = 0xd2b48c;

   public static final int   WEB_teal                      = 0x008080;

   public static final int   WEB_thistle                   = 0xd8bfd8;

   public static final int   WEB_tomato                    = 0xff6347;

   public static final int   WEB_turquoise                 = 0x40e0d0;

   public static final int   WEB_violet                    = 0xee82ee;

   public static final int   WEB_wheat                     = 0xf5deb3;

   public static final int   WEB_white                     = 0xffffff;

   public static final int   WEB_whitesmoke                = 0xf5f5f5;

   public static final int   WEB_yellow                    = 0xffff00;

   public static final int   WEB_yellowgreen               = 0x9acd32;

   public final static int[] z_128                         = new int[] { FZ128_BLUE, FZ128_GREEN, FZ128_GREEN_BLUE, FZ128_RED, FZ128_RED_BLUE, FZ128_RED_GREEN, };

   public final static int[] z_EXTREME                     = new int[] { FULLY_OPAQUE_BLACK, FULLY_OPAQUE_WHITE, FULLY_OPAQUE_CYAN, FULLY_OPAQUE_PURPLE, FULLY_OPAQUE_YELLOW, FULLY_OPAQUE_GREEN, FULLY_OPAQUE_RED, FULLY_OPAQUE_BLUE };

   public final static int[] z_FR_BEIGE                    = new int[] { FR_BEIGE_Amande, FR_BEIGE_Antique, FR_BEIGE_Beige, FR_BEIGE_Bisque, FR_BEIGE_Blanc_Casse, FR_BEIGE_Blanc_coquillage, FR_BEIGE_Blanc_creme, FR_BEIGE_Blanc_dentelle, FR_BEIGE_Blanc_dEspagne, FR_BEIGE_Blanc_floral,
         FR_BEIGE_Blanc_Lavande, FR_BEIGE_Blanc_lin, FR_BEIGE_Blanc_navaro, FR_BEIGE_Blanc_neige, FR_BEIGE_Blanc_spectral, FR_BEIGE_Coquille_oeuf, FR_BEIGE_Ecru, FR_BEIGE_Ivoire, FR_BEIGE_Jaune_ble, FR_BEIGE_Mocassin, FR_BEIGE_Papaye, FR_BEIGE_Peche, FR_BEIGE_Platine, FR_BEIGE_Ventre_de_biche };

   public final static int[] z_FR_BLEU                     = { FR_BLEU_Azure_brume, FR_BLEU_Lavande, FR_BLEU_Dragee, FR_BLEU_Poudre, FR_BLEU_Fumee, FR_BLEU_Acier_clair, FR_BLEU_Clair, FR_BLEU_Azur_clair, FR_BLEU_Azur, FR_BLEU_Givre, FR_BLEU_Petrole, FR_BLEU_Azur_profond, FR_BLEU_Ciel,
         FR_BLEU_Bleuet, FR_BLEU_Bleuet_fonce, FR_BLEU_Acier, FR_BLEU_Toile, FR_BLEU_Roi, FR_BLEU_Celeste, FR_BLEU_Electrique, FR_BLEU_Royal, FR_BLEU_Denim, FR_BLEU_Bleu, FR_BLEU_Persan, FR_BLEU_Ardoise_moyen, FR_BLEU_Ardoise, FR_BLEU_Ardoise_fonce, FR_BLEU_Saphir, FR_BLEU_Moyen, FR_BLEU_Outremer,
         FR_BLEU_Fonce, FR_BLEU_Minuit, FR_BLEU_Marin, FR_BLEU_Nuit, FR_BLEU_Marine, FR_BLEU_Cobalt, FR_BLEU_Minuit2, FR_BLEU_Prusse, FR_BLEU_Petrole_fonce };

   public final static int[] z_FR_BORDEAUX                 = { FR_BORDEAUX_Framboise, FR_BORDEAUX_Lie_de_vin, FR_BORDEAUX_Pourpre, FR_BORDEAUX_Amarante_foncee, FR_BORDEAUX_Grenat, FR_BORDEAUX_Bordeaux, FR_BORDEAUX_Bourgogne, FR_BORDEAUX_Sang_de_boeuf, FR_BORDEAUX_Sanguine, FR_BORDEAUX_Puce };

   public final static int[] z_FR_BRUN                     = { FR_BRUN_Sable, FR_BRUN_Vanille, FR_BRUN_Chamois, FR_BRUN_Bois_rustique, FR_BRUN_Roux, FR_BRUN_Beige, FR_BRUN_Sepia, FR_BRUN_Havane, FR_BRUN_Bistre, FR_BRUN_Chataigne, FR_BRUN_Chatain, FR_BRUN_Cannelle, FR_BRUN_Perou, FR_BRUN_Chocolat,
         FR_BRUN_Alezan, FR_BRUN_Cuivre, FR_BRUN_Auburn, FR_BRUN_Tabac, FR_BRUN_Noisette, FR_BRUN_Rouille, FR_BRUN_Lavalliere, FR_BRUN_Mordore, FR_BRUN_Chaudron, FR_BRUN_Cafe_au_lait, FR_BRUN_Bronze, FR_BRUN_Terre_de_sienne, FR_BRUN_Acajou, FR_BRUN_Cuir, FR_BRUN_Brique, FR_BRUN_Caramel,
         FR_BRUN_Cacao, FR_BRUN_Brun, FR_BRUN_Chocolat_fonce, FR_BRUN_Marron, FR_BRUN_Bitume, FR_BRUN_Cafe, FR_BRUN_Brou_de_noix, FR_BRUN_Cachou, FR_BRUN_Grege, FR_BRUN_Mastic, FR_BRUN_Gris_de_maure, FR_BRUN_Gris_taupe };

   public final static int[] z_FR_CYAN                     = { FR_CYAN_Bleu_alice, FR_CYAN_Bleu_azur, FR_CYAN_Clair, FR_CYAN_Azurin, FR_CYAN_Aigue_marine, FR_CYAN_Cyan, FR_CYAN_Turquoise, FR_CYAN_Turquoise_moyen, FR_CYAN_Turquoise_fonce, FR_CYAN_Aigue_marine_moyen, FR_CYAN_Vert_main_clair,
         FR_CYAN_Fonce, FR_CYAN_Vert_sarcelle, FR_CYAN_Bleu_canard, FR_CYAN_Bleu_paon };

   public final static int[] z_FR_GRIS                     = new int[] { FR_GRIS_BLANC, FR_GRIS_Acier, FR_GRIS_Anthracite, FR_GRIS_Ardoise, FR_GRIS_Ardoise_clair, FR_GRIS_Ardoise_fonce, FR_GRIS_Argent, FR_GRIS_Argile, FR_GRIS_Carbone, FR_GRIS_Charbonneux, FR_GRIS_Clair, FR_GRIS_Etain, FR_GRIS_Fer,
         FR_GRIS_Fumee, FR_GRIS_Gris, FR_GRIS_Mat, FR_GRIS_Perle, FR_GRIS_Plomb, FR_GRIS_Reglisse, FR_GRIS_Souris, FR_GRIS_Tourterelle, FR_GRIS_Vert, FR_GRIS_Noir };

   public final static int[] z_FR_JAUNE                    = { FR_JAUNE_Clair, FR_JAUNE_Mais_doux, FR_JAUNE_Dore_clair, FR_JAUNE_Citron_soie, FR_JAUNE_Champagne, FR_JAUNE_Dore_pale, FR_JAUNE_Brun_kaki, FR_JAUNE_Beurre, FR_JAUNE_Mais, FR_JAUNE_Mimosa, FR_JAUNE_Soufre, FR_JAUNE_Citron, FR_JAUNE_Jaune,
         FR_JAUNE_Secondaire, FR_JAUNE_Canari, FR_JAUNE_Chartreuse, FR_JAUNE_Caca_doie, FR_JAUNE_Moutarde, FR_JAUNE_Topaze, FR_JAUNE_Poussin, FR_JAUNE_Paille, FR_JAUNE_Imperial, FR_JAUNE_Dor, FR_JAUNE_Ble, FR_JAUNE_Safran, FR_JAUNE_Bouton_dor, FR_JAUNE_Or, FR_JAUNE_Ambre_jaune, FR_JAUNE_Miel,
         FR_JAUNE_Banane, FR_JAUNE_Ocre, FR_JAUNE_Dore, FR_JAUNE_Dore_fonce };

   public final static int[] z_FR_ORANGE                   = { FR_ORANGE_Blond, FR_ORANGE_Blond_venitien, FR_ORANGE_Brun_sable, FR_ORANGE_Mandarin, FR_ORANGE_Melon, FR_ORANGE_Orange, FR_ORANGE_Fonce, FR_ORANGE_Abricot, FR_ORANGE_Carotte, FR_ORANGE_Orangee, FR_ORANGE_Citrouille, FR_ORANGE_Brulee,
         FR_ORANGE_Aquilain };

   public final static int[] z_FR_ROSE                     = { FR_ROSE_Brumeux, FR_ROSE_Rose, FR_ROSE_Dragee, FR_ROSE_Clair, FR_ROSE_Brun_Rosee, FR_ROSE_Pelure_doignon, FR_ROSE_Passion, FR_ROSE_Rose2, FR_ROSE_Bonbon, FR_ROSE_Fuchsia, FR_ROSE_Profond, FR_ROSE_Vif, FR_ROSE_Magenta_Fushia,
         FR_ROSE_Cerise, FR_ROSE_Rubis, FR_ROSE_Amarante };

   public final static int[] z_FR_ROUGE                    = { FR_ROUGE_Corail_clair, FR_ROUGE_Corail, FR_ROUGE_Tomate, FR_ROUGE_Capucine, FR_ROUGE_Rouge, FR_ROUGE_Vermeil, FR_ROUGE_Feu, FR_ROUGE_Anglais, FR_ROUGE_Grenadine, FR_ROUGE_Ecarlate, FR_ROUGE_Tomate2, FR_ROUGE_Vermillon, FR_ROUGE_Indien,
         FR_ROUGE_Tomette, FR_ROUGE_Cramoisi, FR_ROUGE_Groseille, FR_ROUGE_Fraise, FR_ROUGE_Cardinal, FR_ROUGE_Cerise, FR_ROUGE_Coquelicot, FR_ROUGE_Ecrevisse, FR_ROUGE_Ambre, FR_ROUGE_Brique, FR_ROUGE_Brun, FR_ROUGE_Carmin, FR_ROUGE_Fonce, FR_ROUGE_Sang, FR_ROUGE_Bordeaux, FR_ROUGE_Grenat };

   public final static int[] z_FR_SAUMON                   = new int[] { FR_SAUMON_Aurore, FR_SAUMON_Chair, FR_SAUMON_Clair, FR_SAUMON_Fonce, FR_SAUMON_Incarnat, FR_SAUMON_Nacarat, FR_SAUMON_Peche, FR_SAUMON_Rose_the, FR_SAUMON_Saumon };

   public final static int[] z_FR_VERT                     = { FR_VERT_Blanc_menthe, FR_VERT_Miellat, FR_VERT_Deau, FR_VERT_Pale, FR_VERT_Clair, FR_VERT_Pistache, FR_VERT_Anis, FR_VERT_Tilleul, FR_VERT_Absinthe, FR_VERT_Chartreuse, FR_VERT_Jaune, FR_VERT_Chartreuse2, FR_VERT_Prairie,
         FR_VERT_Citron_vert, FR_VERT_Citron_vert_fonce, FR_VERT_Lichen, FR_VERT_Amande, FR_VERT_Brun_kaki_fonce, FR_VERT_Jaune_vert, FR_VERT_Opaline, FR_VERT_Jade, FR_VERT_Menthe_a_leau, FR_VERT_Printemps, FR_VERT_Printemps_moyen, FR_VERT_Asperge, FR_VERT_Mousse, FR_VERT_Sauge, FR_VERT_Ocean_moyen,
         FR_VERT_Poireau, FR_VERT_Ocean, FR_VERT_Malachite, FR_VERT_Menthe, FR_VERT_Emeraude, FR_VERT_Perroquet, FR_VERT_Prairie2, FR_VERT_Pomme, FR_VERT_Gazon, FR_VERT_Foret, FR_VERT_Vert, FR_VERT_Bouteille, FR_VERT_Fonce, FR_VERT_Imperial, FR_VERT_Sapin, FR_VERT_Epinard, FR_VERT_Vert_kaki,
         FR_VERT_Kaki, FR_VERT_Olive, FR_VERT_Avocat, FR_VERT_Olive_fonce, FR_VERT_Militaire, FR_VERT_Veronese, FR_VERT_Ocean_fonce, FR_VERT_Vert_de_gris, FR_VERT_Celadon, FR_VERT_Pin };

   public final static int[] z_FR_VIOLET                   = { FR_VIOLET_Gris_de_lin, FR_VIOLET_Pervenche, FR_VIOLET_Bleu_lavande, FR_VIOLET_Pourpre_moyen, FR_VIOLET_Pale, FR_VIOLET_Fushia, FR_VIOLET_Fushia_fonce, FR_VIOLET_Byzantin, FR_VIOLET_Moyen, FR_VIOLET_Violine, FR_VIOLET_Pourpre,
         FR_VIOLET_Magenta_fonce, FR_VIOLET_Zinzolin, FR_VIOLET_Deveque, FR_VIOLET_Gorge_de_pigeon, FR_VIOLET_De_Bayeux, FR_VIOLET_Prune, FR_VIOLET_Chardon, FR_VIOLET_Prune2, FR_VIOLET_Parme, FR_VIOLET_Glycine, FR_VIOLET_Violet, FR_VIOLET_Orchidee, FR_VIOLET_Mauve, FR_VIOLET_Lilas,
         FR_VIOLET_Orchidee_moyen, FR_VIOLET_Orchidee_fonce, FR_VIOLET_Fonce, FR_VIOLET_Amethyste, FR_VIOLET_Bleu_violet, FR_VIOLET_Indigo_chaud, FR_VIOLET_Bleu_Persan, FR_VIOLET_Bleu_ardoise_moyen, FR_VIOLET_Bleu_ardoise, FR_VIOLET_Bleu_ardoise_fonce, FR_VIOLET_Violet2, FR_VIOLET_Indigo,
         FR_VIOLET_Indigo_fonce, FR_VIOLET_Aubergine, FR_VIOLET_Cassis };

   public static final int[] z_WEB                         = new int[] { WEB_aliceblue, WEB_antiquewhite, WEB_aqua, WEB_aquamarine, WEB_azure, WEB_beige, WEB_bisque, WEB_black, WEB_blanchedalmond, WEB_blue, WEB_blueviolet, WEB_brown, WEB_burlywood, WEB_cadetblue, WEB_chartreuse, WEB_chocolate,
         WEB_coral, WEB_cornflowerblue, WEB_cornsilk, WEB_crimson, WEB_cyan, WEB_darkblue, WEB_darkcyan, WEB_darkgoldenrod, WEB_darkgray, WEB_darkgreen, WEB_darkkhaki, WEB_darkmagenta, WEB_darkolivegreen, WEB_darkorange, WEB_darkorchid, WEB_darkred, WEB_darksalmon, WEB_darkseagreen,
         WEB_darkslateblue, WEB_darkslategrey, WEB_darkturquoise, WEB_darkviolet, WEB_deeppink, WEB_deepskyblue, WEB_dimgrey, WEB_dodgerblue, WEB_firebrick, WEB_floralwhite, WEB_forestgreen, WEB_fuchsia, WEB_gainsboro, WEB_ghostwhite, WEB_gold, WEB_goldenrod, WEB_green, WEB_greenyellow, WEB_grey,
         WEB_honeydew, WEB_hotpink, WEB_indianred, WEB_indigo, WEB_ivory, WEB_khaki, WEB_lavender, WEB_lavenderblush, WEB_lawngreen, WEB_lemonchiffon, WEB_lightblue, WEB_lightblue, WEB_lightcoral, WEB_lightcyan, WEB_lightgoldenrodyellow, WEB_lightgray, WEB_lightgreen, WEB_lightpink, WEB_lightsalmon,
         WEB_lightseagreen, WEB_lightskyblue, WEB_lightslategray, WEB_lightsteelblue, WEB_lightyellow, WEB_lime, WEB_limegreen, WEB_linen, WEB_magenta, WEB_maroon, WEB_mediumaquamarine, WEB_mediumblue, WEB_mediumorchid, WEB_mediumpurple, WEB_mediumseagreen, WEB_mediumslateblue,
         WEB_mediumspringgreen, WEB_mediumturquoise, WEB_mediumvioletred, WEB_midnightblue, WEB_mintcream, WEB_mistyrose, WEB_moccasin, WEB_navajowhite, WEB_navy, WEB_oldlace, WEB_olive, WEB_olivedrab, WEB_orange, WEB_orangered, WEB_orchid, WEB_palegoldenrod, WEB_palegreen, WEB_paleturquoise,
         WEB_palevioletred, WEB_papayawhip, WEB_peachpuff, WEB_peru, WEB_pink, WEB_plum, WEB_powderblue, WEB_purple, WEB_red, WEB_rosybrown, WEB_royalblue, WEB_saddlebrown, WEB_salmon, WEB_sandybrown, WEB_seagreen, WEB_seashell, WEB_sienna, WEB_silver, WEB_skyblue, WEB_slateblue, WEB_slategray,
         WEB_snow, WEB_springgreen, WEB_steelblue, WEB_tan, WEB_teal, WEB_thistle, WEB_tomato, WEB_tomato, WEB_turquoise, WEB_violet, WEB_wheat, WEB_white, WEB_whitesmoke, WEB_yellow, WEB_yellowgreen };

   public static int getBlue(int c) {
      return (c) & 0xFF;
   }

   public static int getAlpha(int c) {
      return (c >> 24) & 0xFF;
   }

   public static int getComplementaryColor(int color) {
      return (0xFF000000 & color) | ((255 - ((0x00FF0000 & color) >> 16)) << 16) | ((255 - ((0x0000FF00 & color) >> 8)) << 8) | (255 - (0x000000FF & color));
   }

   /**
    * Takes a color and darkens/brighten each channel by param.
    * <br>
    * <br>
    * Function for a gradient may include target.
    * Or as soon as a channel hits a target.
    * @param baseColor
    * @param param
    * @return
    */
   public static int getDarkishColor(int baseColor, int param) {
      int r = ((baseColor >> 16) & 0xFF) - param;
      int g = ((baseColor >> 8) & 0xFF) - param;
      int b = (baseColor & 0xFF) - param;
      return getRGBIntVal(r, g, b);
   }

   public static int getGreen(int c) {
      return (c >> 8) & 0xFF;
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

   private UCtx uc;

   public ColorUtils(UCtx uc) {
      this.uc = uc;
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

   /**
    * Returns a String such as (a,r,g,b)
    * @param c
    * @return
    */
   public String toStringColor(int c) {
      return "(" + ((c >> 24) & 0xFF) + "," + ((c >> 16) & 0xFF) + "," + ((c >> 8) & 0xFF) + "," + (c & 0xFF) + ")";
   }

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

   public void toStringColor(StringBuilder sb, int c) {
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
