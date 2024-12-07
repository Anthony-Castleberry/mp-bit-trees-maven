package edu.grinnell.csc207.util;

/**
 * an object to hold a node in a binary tree with children
 * 
 * @author Anthony Castleberry
 */
public class BitTreeInteriorNode implements BitTreeNode {
  
  BitTreeNode leftChild;

  BitTreeNode rightChild;

  public BitTreeInteriorNode(BitTreeNode left, BitTreeNode right) {
    this.leftChild = left;
    this.rightChild = right;
  }
}
