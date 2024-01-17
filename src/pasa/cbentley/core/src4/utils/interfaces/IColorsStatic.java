/*
 * (c) 2018-2020 Charles-Philip Bentley
 * This code is licensed under MIT license (see LICENSE.txt for details)
 */
package pasa.cbentley.core.src4.utils.interfaces;

/**
 * Contains static state that is supposed to never be modified
 * 
 * @author Charles Bentley
 *
 */
public interface IColorsStatic extends IColors, IColorsWeb {

   public final static int[] z_128         = new int[] { ITechColor.FZ128_BLUE, ITechColor.FZ128_GREEN, ITechColor.FZ128_GREEN_BLUE, ITechColor.FZ128_RED, ITechColor.FZ128_RED_BLUE, ITechColor.FZ128_RED_GREEN, };

   public final static int[] z_EXTREME     = new int[] { FULLY_OPAQUE_BLACK, FULLY_OPAQUE_WHITE, FULLY_OPAQUE_CYAN, FULLY_OPAQUE_PURPLE, FULLY_OPAQUE_YELLOW, FULLY_OPAQUE_GREEN, FULLY_OPAQUE_RED, FULLY_OPAQUE_BLUE };

   public final static int[] z_FR_BEIGE    = new int[] { FR_BEIGE_Amande, FR_BEIGE_Antique, FR_BEIGE_Beige, FR_BEIGE_Bisque, FR_BEIGE_Blanc_Casse, FR_BEIGE_Blanc_coquillage, FR_BEIGE_Blanc_creme, FR_BEIGE_Blanc_dentelle, FR_BEIGE_Blanc_dEspagne, FR_BEIGE_Blanc_floral, FR_BEIGE_Blanc_Lavande,
         FR_BEIGE_Blanc_lin, FR_BEIGE_Blanc_navaro, FR_BEIGE_Blanc_neige, FR_BEIGE_Blanc_spectral, FR_BEIGE_Coquille_oeuf, FR_BEIGE_Ecru, FR_BEIGE_Ivoire, FR_BEIGE_Jaune_ble, FR_BEIGE_Mocassin, FR_BEIGE_Papaye, FR_BEIGE_Peche, FR_BEIGE_Platine, FR_BEIGE_Ventre_de_biche };

   public final static int[] z_FR_BLEU     = { FR_BLEU_Azure_brume, FR_BLEU_Lavande, FR_BLEU_Dragee, FR_BLEU_Poudre, FR_BLEU_Fumee, FR_BLEU_Acier_clair, FR_BLEU_Clair, FR_BLEU_Azur_clair, FR_BLEU_Azur, FR_BLEU_Givre, FR_BLEU_Petrole, FR_BLEU_Azur_profond, FR_BLEU_Ciel, FR_BLEU_Bleuet,
         FR_BLEU_Bleuet_fonce, FR_BLEU_Acier, FR_BLEU_Toile, FR_BLEU_Roi, FR_BLEU_Celeste, FR_BLEU_Electrique, FR_BLEU_Royal, FR_BLEU_Denim, FR_BLEU_Bleu, FR_BLEU_Persan, FR_BLEU_Ardoise_moyen, FR_BLEU_Ardoise, FR_BLEU_Ardoise_fonce, FR_BLEU_Saphir, FR_BLEU_Moyen, FR_BLEU_Outremer, FR_BLEU_Fonce,
         FR_BLEU_Minuit, FR_BLEU_Marin, FR_BLEU_Nuit, FR_BLEU_Marine, FR_BLEU_Cobalt, FR_BLEU_Minuit2, FR_BLEU_Prusse, FR_BLEU_Petrole_fonce };

   public final static int[] z_FR_BORDEAUX = { FR_BORDEAUX_Framboise, FR_BORDEAUX_Lie_de_vin, FR_BORDEAUX_Pourpre, FR_BORDEAUX_Amarante_foncee, FR_BORDEAUX_Grenat, FR_BORDEAUX_Bordeaux, FR_BORDEAUX_Bourgogne, FR_BORDEAUX_Sang_de_boeuf, FR_BORDEAUX_Sanguine, FR_BORDEAUX_Puce };

   public final static int[] z_FR_BRUN     = { FR_BRUN_Sable, FR_BRUN_Vanille, FR_BRUN_Chamois, FR_BRUN_Bois_rustique, FR_BRUN_Roux, FR_BRUN_Beige, FR_BRUN_Sepia, FR_BRUN_Havane, FR_BRUN_Bistre, FR_BRUN_Chataigne, FR_BRUN_Chatain, FR_BRUN_Cannelle, FR_BRUN_Perou, FR_BRUN_Chocolat, FR_BRUN_Alezan,
         FR_BRUN_Cuivre, FR_BRUN_Auburn, FR_BRUN_Tabac, FR_BRUN_Noisette, FR_BRUN_Rouille, FR_BRUN_Lavalliere, FR_BRUN_Mordore, FR_BRUN_Chaudron, FR_BRUN_Cafe_au_lait, FR_BRUN_Bronze, FR_BRUN_Terre_de_sienne, FR_BRUN_Acajou, FR_BRUN_Cuir, FR_BRUN_Brique, FR_BRUN_Caramel, FR_BRUN_Cacao, FR_BRUN_Brun,
         FR_BRUN_Chocolat_fonce, FR_BRUN_Marron, FR_BRUN_Bitume, FR_BRUN_Cafe, FR_BRUN_Brou_de_noix, FR_BRUN_Cachou, FR_BRUN_Grege, FR_BRUN_Mastic, FR_BRUN_Gris_de_maure, FR_BRUN_Gris_taupe };

   public final static int[] z_FR_CYAN     = { FR_CYAN_Bleu_alice, FR_CYAN_Bleu_azur, FR_CYAN_Clair, FR_CYAN_Azurin, FR_CYAN_Aigue_marine, FR_CYAN_Cyan, FR_CYAN_Turquoise, FR_CYAN_Turquoise_moyen, FR_CYAN_Turquoise_fonce, FR_CYAN_Aigue_marine_moyen, FR_CYAN_Vert_main_clair, FR_CYAN_Fonce,
         FR_CYAN_Vert_sarcelle, FR_CYAN_Bleu_canard, FR_CYAN_Bleu_paon };

   public final static int[] z_FR_GRIS     = new int[] { FR_GRIS_BLANC, FR_GRIS_Acier, FR_GRIS_Anthracite, FR_GRIS_Ardoise, FR_GRIS_Ardoise_clair, FR_GRIS_Ardoise_fonce, FR_GRIS_Argent, FR_GRIS_Argile, FR_GRIS_Carbone, FR_GRIS_Charbonneux, FR_GRIS_Clair, FR_GRIS_Etain, FR_GRIS_Fer, FR_GRIS_Fumee,
         FR_GRIS_Gris, FR_GRIS_Mat, FR_GRIS_Perle, FR_GRIS_Plomb, FR_GRIS_Reglisse, FR_GRIS_Souris, FR_GRIS_Tourterelle, FR_GRIS_Vert, FR_GRIS_Noir };

   public final static int[] z_FR_JAUNE    = { FR_JAUNE_Clair, FR_JAUNE_Mais_doux, FR_JAUNE_Dore_clair, FR_JAUNE_Citron_soie, FR_JAUNE_Champagne, FR_JAUNE_Dore_pale, FR_JAUNE_Brun_kaki, FR_JAUNE_Beurre, FR_JAUNE_Mais, FR_JAUNE_Mimosa, FR_JAUNE_Soufre, FR_JAUNE_Citron, FR_JAUNE_Jaune,
         FR_JAUNE_Secondaire, FR_JAUNE_Canari, FR_JAUNE_Chartreuse, FR_JAUNE_Caca_doie, FR_JAUNE_Moutarde, FR_JAUNE_Topaze, FR_JAUNE_Poussin, FR_JAUNE_Paille, FR_JAUNE_Imperial, FR_JAUNE_Dor, FR_JAUNE_Ble, FR_JAUNE_Safran, FR_JAUNE_Bouton_dor, FR_JAUNE_Or, FR_JAUNE_Ambre_jaune, FR_JAUNE_Miel,
         FR_JAUNE_Banane, FR_JAUNE_Ocre, FR_JAUNE_Dore, FR_JAUNE_Dore_fonce };

   public final static int[] z_FR_ORANGE   = { FR_ORANGE_Blond, FR_ORANGE_Blond_venitien, FR_ORANGE_Brun_sable, FR_ORANGE_Mandarin, FR_ORANGE_Melon, FR_ORANGE_Orange, FR_ORANGE_Fonce, FR_ORANGE_Abricot, FR_ORANGE_Carotte, FR_ORANGE_Orangee, FR_ORANGE_Citrouille, FR_ORANGE_Brulee,
         FR_ORANGE_Aquilain };

   public final static int[] z_FR_ROSE     = { FR_ROSE_Brumeux, FR_ROSE_Rose, FR_ROSE_Dragee, FR_ROSE_Clair, FR_ROSE_Brun_Rosee, FR_ROSE_Pelure_doignon, FR_ROSE_Passion, FR_ROSE_Rose2, FR_ROSE_Bonbon, FR_ROSE_Fuchsia, FR_ROSE_Profond, FR_ROSE_Vif, FR_ROSE_Magenta_Fushia, FR_ROSE_Cerise,
         FR_ROSE_Rubis, FR_ROSE_Amarante };

   public final static int[] z_FR_ROUGE    = { FR_ROUGE_Corail_clair, FR_ROUGE_Corail, FR_ROUGE_Tomate, FR_ROUGE_Capucine, FR_ROUGE_Rouge, FR_ROUGE_Vermeil, FR_ROUGE_Feu, FR_ROUGE_Anglais, FR_ROUGE_Grenadine, FR_ROUGE_Ecarlate, FR_ROUGE_Tomate2, FR_ROUGE_Vermillon, FR_ROUGE_Indien, FR_ROUGE_Tomette,
         FR_ROUGE_Cramoisi, FR_ROUGE_Groseille, FR_ROUGE_Fraise, FR_ROUGE_Cardinal, FR_ROUGE_Cerise, FR_ROUGE_Coquelicot, FR_ROUGE_Ecrevisse, FR_ROUGE_Ambre, FR_ROUGE_Brique, FR_ROUGE_Brun, FR_ROUGE_Carmin, FR_ROUGE_Fonce, FR_ROUGE_Sang, FR_ROUGE_Bordeaux, FR_ROUGE_Grenat };

   public final static int[] z_FR_SAUMON   = new int[] { FR_SAUMON_Aurore, FR_SAUMON_Chair, FR_SAUMON_Clair, FR_SAUMON_Fonce, FR_SAUMON_Incarnat, FR_SAUMON_Nacarat, FR_SAUMON_Peche, FR_SAUMON_Rose_the, FR_SAUMON_Saumon };

   public final static int[] z_FR_VERT     = { FR_VERT_Blanc_menthe, FR_VERT_Miellat, FR_VERT_Deau, FR_VERT_Pale, FR_VERT_Clair, FR_VERT_Pistache, FR_VERT_Anis, FR_VERT_Tilleul, FR_VERT_Absinthe, FR_VERT_Chartreuse, FR_VERT_Jaune, FR_VERT_Chartreuse2, FR_VERT_Prairie, FR_VERT_Citron_vert,
         FR_VERT_Citron_vert_fonce, FR_VERT_Lichen, FR_VERT_Amande, FR_VERT_Brun_kaki_fonce, FR_VERT_Jaune_vert, FR_VERT_Opaline, FR_VERT_Jade, FR_VERT_Menthe_a_leau, FR_VERT_Printemps, FR_VERT_Printemps_moyen, FR_VERT_Asperge, FR_VERT_Mousse, FR_VERT_Sauge, FR_VERT_Ocean_moyen, FR_VERT_Poireau,
         FR_VERT_Ocean, FR_VERT_Malachite, FR_VERT_Menthe, FR_VERT_Emeraude, FR_VERT_Perroquet, FR_VERT_Prairie2, FR_VERT_Pomme, FR_VERT_Gazon, FR_VERT_Foret, FR_VERT_Vert, FR_VERT_Bouteille, FR_VERT_Fonce, FR_VERT_Imperial, FR_VERT_Sapin, FR_VERT_Epinard, FR_VERT_Vert_kaki, FR_VERT_Kaki,
         FR_VERT_Olive, FR_VERT_Avocat, FR_VERT_Olive_fonce, FR_VERT_Militaire, FR_VERT_Veronese, FR_VERT_Ocean_fonce, FR_VERT_Vert_de_gris, FR_VERT_Celadon, FR_VERT_Pin };

   public final static int[] z_FR_VIOLET   = { FR_VIOLET_Gris_de_lin, FR_VIOLET_Pervenche, FR_VIOLET_Bleu_lavande, FR_VIOLET_Pourpre_moyen, FR_VIOLET_Pale, FR_VIOLET_Fushia, FR_VIOLET_Fushia_fonce, FR_VIOLET_Byzantin, FR_VIOLET_Moyen, FR_VIOLET_Violine, FR_VIOLET_Pourpre, FR_VIOLET_Magenta_fonce,
         FR_VIOLET_Zinzolin, FR_VIOLET_Deveque, FR_VIOLET_Gorge_de_pigeon, FR_VIOLET_De_Bayeux, FR_VIOLET_Prune, FR_VIOLET_Chardon, FR_VIOLET_Prune2, FR_VIOLET_Parme, FR_VIOLET_Glycine, FR_VIOLET_Violet, FR_VIOLET_Orchidee, FR_VIOLET_Mauve, FR_VIOLET_Lilas, FR_VIOLET_Orchidee_moyen,
         FR_VIOLET_Orchidee_fonce, FR_VIOLET_Fonce, FR_VIOLET_Amethyste, FR_VIOLET_Bleu_violet, FR_VIOLET_Indigo_chaud, FR_VIOLET_Bleu_Persan, FR_VIOLET_Bleu_ardoise_moyen, FR_VIOLET_Bleu_ardoise, FR_VIOLET_Bleu_ardoise_fonce, FR_VIOLET_Violet2, FR_VIOLET_Indigo, FR_VIOLET_Indigo_fonce,
         FR_VIOLET_Aubergine, FR_VIOLET_Cassis };

   public static final int[] z_WEB         = new int[] { WEB_aliceblue, WEB_antiquewhite, WEB_aqua, WEB_aquamarine, WEB_azure, WEB_beige, WEB_bisque, WEB_black, WEB_blanchedalmond, WEB_blue, WEB_blueviolet, WEB_brown, WEB_burlywood, WEB_cadetblue, WEB_chartreuse, WEB_chocolate, WEB_coral,
         WEB_cornflowerblue, WEB_cornsilk, WEB_crimson, WEB_cyan, WEB_darkblue, WEB_darkcyan, WEB_darkgoldenrod, WEB_darkgray, WEB_darkgreen, WEB_darkkhaki, WEB_darkmagenta, WEB_darkolivegreen, WEB_darkorange, WEB_darkorchid, WEB_darkred, WEB_darksalmon, WEB_darkseagreen, WEB_darkslateblue,
         WEB_darkslategrey, WEB_darkturquoise, WEB_darkviolet, WEB_deeppink, WEB_deepskyblue, WEB_dimgrey, WEB_dodgerblue, WEB_firebrick, WEB_floralwhite, WEB_forestgreen, WEB_gainsboro, WEB_ghostwhite, WEB_gold, WEB_goldenrod, WEB_green, WEB_greenyellow, WEB_grey, WEB_honeydew, WEB_hotpink,
         WEB_indianred, WEB_indigo, WEB_ivory, WEB_khaki, WEB_lavender, WEB_lavenderblush, WEB_lawngreen, WEB_lemonchiffon, WEB_lightblue, WEB_lightblue, WEB_lightcoral, WEB_lightcyan, WEB_lightgoldenrodyellow, WEB_lightgray, WEB_lightgreen, WEB_lightpink, WEB_lightsalmon, WEB_lightseagreen,
         WEB_lightskyblue, WEB_lightsteelblue, WEB_lightyellow, WEB_lime, WEB_limegreen, WEB_linen, WEB_magenta, WEB_maroon, WEB_mediumaquamarine, WEB_mediumblue, WEB_mediumorchid, WEB_mediumpurple, WEB_mediumseagreen, WEB_mediumslateblue, WEB_mediumspringgreen, WEB_mediumturquoise,
         WEB_mediumvioletred, WEB_midnightblue, WEB_mintcream, WEB_mistyrose, WEB_moccasin, WEB_navajowhite, WEB_navy, WEB_oldlace, WEB_olive, WEB_olivedrab, WEB_orange, WEB_orangered, WEB_orchid, WEB_palegoldenrod, WEB_palegreen, WEB_paleturquoise, WEB_palevioletred, WEB_papayawhip, WEB_peachpuff,
         WEB_peru, WEB_pink, WEB_plum, WEB_powderblue, WEB_purple, WEB_red, WEB_rosybrown, WEB_royalblue, WEB_saddlebrown, WEB_salmon, WEB_sandybrown, WEB_seagreen, WEB_seashell, WEB_sienna, WEB_silver, WEB_skyblue, WEB_slateblue, WEB_snow, WEB_springgreen, WEB_steelblue, WEB_tan, WEB_teal,
         WEB_thistle, WEB_tomato, WEB_tomato, WEB_turquoise, WEB_violet, WEB_wheat, WEB_white, WEB_whitesmoke, WEB_yellow, WEB_yellowgreen };

}
