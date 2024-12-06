package edu.grinnell.csc207.util;

/**
 * Represents a node in the BitTree.
 * Nodes can either hold a value (leaf nodes) or have children (interior nodes).
 * @author Samuel A. Rebelsky
 * @author Sal Karki
 */
class BitTreeNode {
   /**
   * The value stored in node if it's a leaf.
   */
  String value;

   /**
   * The left child.
   */
  BitTreeNode left;

   /**
   * The right child.
   */
  BitTreeNode right;

  // Constructor for a node with no children and no value.
  public BitTreeNode() {
    this.value = null;
    this.left = null;
    this.right = null;
  } // Constructor
} // BitTreeNode
