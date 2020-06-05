/*
 * (c) 2018-2020 Charles-Philip Bentley
 * This code is licensed under MIT license (see LICENSE.txt for details)
 */
package pasa.cbentley.core.src4.helpers;

import java.io.PrintStream;


/**
 * source https://en.wikipedia.org/wiki/ANSI_escape_code.
 *
 * Some console don't support ANSI escape characters at all.
 * Eclipse -> https://mihai-nita.net/2013/06/03/eclipse-plugin-ansi-in-console/
 * Root Eclipse Plug-in -> http://www.mihai-nita.net/eclipse
 * 
 * @author Charles Bentley
 */
public class ANSIColors {

    public static final String ANSI_BLACK_BACKGROUND = "\u001B[40m";
    public static final String ANSI_RED_BACKGROUND = "\u001B[41m";
    public static final String ANSI_GREEN_BACKGROUND = "\u001B[42m";
    public static final String ANSI_YELLOW_BACKGROUND = "\u001B[43m";
    public static final String ANSI_BLUE_BACKGROUND = "\u001B[44m";
    public static final String ANSI_PURPLE_BACKGROUND = "\u001B[45m";
    public static final String ANSI_CYAN_BACKGROUND = "\u001B[46m";
    public static final String ANSI_WHITE_BACKGROUND = "\u001B[47m";

    public static final String ANSI_RESET = "\u001B[0m";
    public static final String ANSI_BOLD = "\u001B[1m";
    public static final String ANSI_ITALIC = "\u001B[3m";

    public static final String ANSI_UNDERLINE = "\u001B[4m";
    public static final String ANSI_UNDERLINE_OFF = "\u001B[24m";
    public static final String ANSI_BLINK_SLOW = "\u001B[5m";

    public static final String ANSI_FRAMED = "\u001B[51m";

    public static final String ANSI_BLACK = "\u001B[30m";
    public static final String ANSI_RED = "\u001B[31m";
    public static final String ANSI_GREEN = "\u001B[32m";
    public static final String ANSI_YELLOW = "\u001B[33m";
    public static final String ANSI_BLUE = "\u001B[34m";
    public static final String ANSI_PURPLE = "\u001B[35m";
    public static final String ANSI_CYAN = "\u001B[36m";
    public static final String ANSI_WHITE = "\u001B[37m";

    public static final String ANSI_BRIGHT_BLACK_BACKGROUND = "\u001B[100m";
    public static final String ANSI_BRIGHT_RED_BACKGROUND = "\u001B[101m";
    public static final String ANSI_BRIGHT_GREEN_BACKGROUND = "\u001B[102m";
    public static final String ANSI_BRIGHT_YELLOW_BACKGROUND = "\u001B[103m";
    public static final String ANSI_BRIGHT_BLUE_BACKGROUND = "\u001B[104m";
    public static final String ANSI_BRIGHT_PURPLE_BACKGROUND = "\u001B[105m";
    public static final String ANSI_BRIGHT_CYAN_BACKGROUND = "\u001B[106m";
    public static final String ANSI_BRIGHT_WHITE_BACKGROUND = "\u001B[107m";


    public static final String ANSI_BRIGHT_BLACK = "\u001B[90m";
    public static final String ANSI_BRIGHT_RED = "\u001B[91m";
    public static final String ANSI_BRIGHT_GREEN = "\u001B[92m";
    public static final String ANSI_BRIGHT_YELLOW = "\u001B[93m";
    public static final String ANSI_BRIGHT_BLUE = "\u001B[94m";
    public static final String ANSI_BRIGHT_PURPLE = "\u001B[95m";
    public static final String ANSI_BRIGHT_CYAN = "\u001B[96m";
    public static final String ANSI_BRIGHT_WHITE = "\u001B[97m";


    /**
     * Quick easy test.
     * @param args
     */
    public static void main(String[] args) {
        PrintStream o = System.out;

        print(o,ANSI_BLACK_BACKGROUND,"Black Dark");
        print(o,ANSI_BRIGHT_BLACK_BACKGROUND,"Black Bright");
        print(o,ANSI_BLUE_BACKGROUND,"Blue Dark");
        print(o,ANSI_BRIGHT_BLUE_BACKGROUND,"Blue Bright");
        print(o,ANSI_PURPLE_BACKGROUND,"Purple Dark");
        print(o,ANSI_BRIGHT_PURPLE_BACKGROUND,"Purple Bright");
       
        print(o,ANSI_GREEN_BACKGROUND,"Green Dark");
        print(o,ANSI_BRIGHT_GREEN_BACKGROUND,"Green Bright");
        print(o,ANSI_RED_BACKGROUND,"Red  Dark");
        print(o,ANSI_BRIGHT_RED_BACKGROUND,"Red  Bright");
        print(o,ANSI_YELLOW_BACKGROUND,"Yellow Dark");
        print(o,ANSI_BRIGHT_YELLOW_BACKGROUND,"Yellow Bright");
        print(o,ANSI_CYAN_BACKGROUND,"Cyan Dark");
        print(o,ANSI_BRIGHT_CYAN_BACKGROUND,"Cyan Bright");
        print(o,ANSI_WHITE_BACKGROUND,"White Dark");
        print(o,ANSI_BRIGHT_WHITE_BACKGROUND,"White Bright");

        o.println(ANSI_RESET);
        o.print(ANSI_BOLD);
        o.print("Bold");

        o.println(ANSI_RESET);
        o.print(ANSI_ITALIC); //not widely supported
        o.print("Italic");


        o.println(ANSI_RESET);
        o.print(ANSI_UNDERLINE);
        o.print("Underline");

        o.println(ANSI_RESET);
        o.print(ANSI_BLINK_SLOW); //not widely supported
        o.print("Blink slowly");

        o.println(ANSI_RESET);
        o.print(ANSI_FRAMED); //not widely supported
        o.print("Framed");



    }

    private static void print(PrintStream o, String bg, String name) {
        o.println(ANSI_RESET);
        o.print(bg+ANSI_WHITE);
        o.print("White foreground on "+name+" background \t");
        o.print(bg+ANSI_RED);
        o.print("Red foreground on "+name+" background \t");
        o.print(bg+ANSI_YELLOW);
        o.print("Yellow foreground on "+name+" background \t");
        o.print(bg+ANSI_BLUE);
        o.print("Blue foreground on "+name+" background\t");
        o.print(bg+ANSI_CYAN);
        o.print("Cyan foreground on "+name+" background\t");
        o.print(bg+ANSI_GREEN);
        o.print("Green foreground on "+name+" background\t");
        o.print(bg+ANSI_WHITE);
        o.print("White foreground on "+name+" background\t");
        o.print(bg+ANSI_PURPLE);
        o.print("Purple foreground on "+name+" background\t");
    }
}
