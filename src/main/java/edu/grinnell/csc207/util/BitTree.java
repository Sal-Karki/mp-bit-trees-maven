package edu.grinnell.csc207.util;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintWriter;

/**
 * BitTree class that stores mappings between bits and values.
 * @author Samuel A. Rebelsky
 * @author Sal Karki
 */
public class BitTree {
  /**
   * root - highest level node.
   */
  private final BitTreeNode root;

  /**
   * length of bit array.
   */
  private final int bitLength;

  /**
   * Constructs a new BitTree for storing mappings of the given bit length.
   * @param n the fixed length of bit strings.
   */
  public BitTree(int n) {
    if (n <= 0) {
      throw new IllegalArgumentException("Bit length must be positive.");
    } // if
    this.root = new BitTreeNode();
    this.bitLength = n;
  } // BitTree

  /**
   * Adds or replaces a mapping in the tree.
   * @param bits the bit string to map.
   * @param value the value to associate with the bit string.
   */
  public void set(String bits, String value) {
    if (bits.length() != bitLength) {
      throw new IndexOutOfBoundsException("Bit string length must be " + bitLength);
    } // if
    BitTreeNode current = root;
    for (char bit : bits.toCharArray()) {
      if (bit == '0') {
        if (current.left == null) {
          current.left = new BitTreeNode();
        } // if
        current = current.left;
      } else if (bit == '1') {
        if (current.right == null) {
          current.right = new BitTreeNode();
        } // if
        current = current.right;
      } else {
        throw new IndexOutOfBoundsException("Bit string must only contain '0' or '1'.");
      } // if
    } // for
    current.value = value;
  } // set

  /**
   * Retrieves the value associated with a bit string.
   * @param bits the bit string to look up.
   * @return the associated value.
   */
  public String get(String bits) {
    if (bits.length() != bitLength) {
      throw new IndexOutOfBoundsException("Bit string length must be " + bitLength);
    } // if
    BitTreeNode current = root;
    for (char bit : bits.toCharArray()) {
      if (bit == '0') {
        if (current.left == null) {
          throw new IndexOutOfBoundsException("No mapping for bit string: " + bits);
        } // if
        current = current.left;
      } else if (bit == '1') {
        if (current.right == null) {
          throw new IndexOutOfBoundsException("No mapping for bit string: " + bits);
        } // if
        current = current.right;
      } else {
        throw new IndexOutOfBoundsException("Bit string must only contain '0' or '1'.");
      } // if
    } // for
    if (current.value == null) {
      throw new IndexOutOfBoundsException("No mapping for bit string: " + bits);
    } // if
    return current.value;
  } // get
  /**
   * Dumps the contents of the tree.
   * @param pen for the PrintWriter
   */
  public void dump(PrintWriter pen) {
    dumpHelper(root, "", pen);
  } // dump

  /**
   * Helper method for dump.
   * @param node the current node.
   * @param prefix the current bit string prefix.
   * @param pen for the PrintWriter
   */
  private void dumpHelper(BitTreeNode node, String prefix, PrintWriter pen) {
    if (node == null) {
      return;
    } // if
    if (node.value != null) {
      pen.println(prefix + "," + node.value);
    } // if
    dumpHelper(node.left, prefix + "0", pen);
    dumpHelper(node.right, prefix + "1", pen);
  } // dumpHelper

  /**
   * Loads mappings from an input stream into the tree.
   * @param source the input stream to read from.
   */
  public void load(InputStream source) {
    try (BufferedReader reader = new BufferedReader(new InputStreamReader(source))) {
      String line;
      while ((line = reader.readLine()) != null) {
        String[] parts = line.split(",", 2);
        if (parts.length != 2) {
          throw new IllegalArgumentException("Invalid input format: " + line);
        } // if
        set(parts[0], parts[1]);
      } // while
    } catch (Exception e) {
      throw new RuntimeException("Error loading data: " + e.getMessage(), e);
    } // try/catch
  } // load
} // BitTree

