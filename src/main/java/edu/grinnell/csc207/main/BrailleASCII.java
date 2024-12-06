package edu.grinnell.csc207.main;

import java.io.PrintWriter;

import edu.grinnell.csc207.util.BrailleAsciiTables;

/**
 * Main method for the project.
 * Note: uses Stringbuilder for conversion methods as that was just what I found
 * easiest to use due to general ease of appending.
 * @author Samuel A. Rebelsky
 * @author Sal Karki
 */
public class BrailleASCII {
  /**
  * Main method.
  *
  * @param args
  * Command-line arguments.
  */
    public static void main(String[] args) {
    // Chekcs if args is the correct number of strings
    PrintWriter pen = new PrintWriter(System.out, true);
    if (args.length != 2) {
      System.err.println("Usage: BrailleASCII <target> <source>");
      System.exit(1);
    } // if

    String target = args[0].toLowerCase();
    String source = args[1];

    try {
      switch (target) {
        case "braille":
          pen.println(toBrailleString(source));
          break;
        case "ascii":
          pen.println(toAsciiString(source));
          break;
        case "unicode":
          pen.println(toUnicodeString(source));
          break;
        default:
          System.err.println("Invalid target. Choose 'braille', 'ascii', or 'unicode'.");
          System.exit(1);
      } // switch
    } catch (RuntimeException e) {
      System.err.println("Trouble translating because " + e.getMessage());
    } // try/catch
  } // main

  /**
   * Converts a string of ASCII characters to a string of Braille bit strings.
   * @param input the string of ASCII characters.
   * @return a string of braille
   */
  private static String toBrailleString(String input) {
    StringBuilder result = new StringBuilder();
    for (char c : input.toCharArray()) {
      String braille = BrailleAsciiTables.toBraille(c);
      if (braille == null) {
        throw new RuntimeException("No corresponding value for '" + c + "'");
      } // if
      result.append(braille);
    } // for
    return result.toString();
  } // toBrailleString

  /**
   * Converts a string of Braille bit strings to ASCII.
   * @param input the string of Braille.
   * @return a string of ascii
   */
  private static String toAsciiString(String input) {
    if (input.length() % 6 != 0) {
      throw new RuntimeException("Invalid length of bit string");
    } // if

    StringBuilder result = new StringBuilder();
    for (int i = 0; i < input.length(); i += 6) {
      String bits = input.substring(i, i + 6);
      String ascii = BrailleAsciiTables.toAscii(bits);
      if (ascii == null) {
        throw new RuntimeException("No corresponding value for bits '" + bits + "'");
      } // if
      result.append(ascii);
    } // for
    return result.toString();
  } // toAsciiString

  /**
   * Converts a string of ASCII characters to Unicode Braille.
   * @param input the string of ASCII characters.
   * @return a string of braille
   */
  private static String toUnicodeString(String input) {
    StringBuilder result = new StringBuilder();
    for (char c : input.toCharArray()) {
      if (c == ' ') {
        result.append("⠀"); // Unicode Braille blank space
      } else {
        String brailleBits = BrailleAsciiTables.toBraille(c);
        if (brailleBits == null) {
          throw new RuntimeException("No corresponding value for '" + c + "'");
        } // if
        result.append(BrailleAsciiTables.toUnicode(brailleBits));
      } // if
    } // for
    return result.toString();
  } // toUnicodeString
} // BrailleASCII


