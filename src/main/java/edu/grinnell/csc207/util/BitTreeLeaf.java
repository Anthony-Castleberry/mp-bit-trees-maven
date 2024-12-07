package edu.grinnell.csc207.util;

/**
 * an object to hold a node in a binary tree with no children
 * 
 * @author Anthony Castleberry
 */
public class BitTreeLeaf implements BitTreeNode {

  String value;

  public BitTreeLeaf(String str) {
    this.value = str;
  }

  void set(String str) {
    this.value = str;
  }
}
