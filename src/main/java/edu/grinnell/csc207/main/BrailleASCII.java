package edu.grinnell.csc207.main;

import java.io.PrintWriter;
import edu.grinnell.csc207.util.BrailleAsciiTables;

/**
 *
 */
public class BrailleASCII {
  // +------+--------------------------------------------------------
  // | Main |
  // +------+

  /**
   *
   */
  public static void main(String[] args) {
    PrintWriter pen = new PrintWriter(System.out, true);
    String command = args[0];
    String input = args[1];
    switch (command) {
      case "braille":
      try {
        String braille = "";
        for (int i = 0; i < input.length(); i++) {
          braille = braille.concat(BrailleAsciiTables.toBraille(input.charAt(i)));
        }
        pen.println(braille);}
        catch(Exception e) {
          throw new RuntimeException("Trouble translating because No corresponding value");
        }
        break;
      case "unicode":
        String unicode = "";
        for (int i = 0; i < input.length(); i++) {
          int num = Integer.parseInt(BrailleAsciiTables.toUnicode(BrailleAsciiTables.toBraille(input.charAt(i))), 16);
          unicode = unicode.concat(String.valueOf((char) num));
        }
        pen.println(unicode);
        break;
      case "ascii":
        if (input.length() % 6 != 0) {
          throw new RuntimeException("Invalid length of bit string");
        }
        String ascii = "";
        for (int i = 0; i < input.length() / 6; i++) {
          ascii = ascii.concat(BrailleAsciiTables.toAscii(input.substring(i * 6, (i * 6) + 6)));
        }
        pen.println(ascii);
        break;
    }
    pen.close();
  } // main(String[]

} // class BrailleASCII
