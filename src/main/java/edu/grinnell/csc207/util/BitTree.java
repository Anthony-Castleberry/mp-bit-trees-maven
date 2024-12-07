package edu.grinnell.csc207.util;

import java.io.InputStream;
import java.io.PrintWriter;
import java.util.Arrays;

/**
 * Trees intended to be used in storing mappings between fixed-length 
 * sequences of bits and corresponding values.
 *
 * @author Anthony Castleberry
 */
public class BitTree {
  // +--------+------------------------------------------------------
  // | Fields |
  // +--------+

  BitTreeInteriorNode root = new BitTreeInteriorNode(null, null);

  int height;

  // +--------------+------------------------------------------------
  // | Constructors |
  // +--------------+

  /**
   *
   */
  public BitTree(int n) {
    height = n;
  } // BitTree(int)

  // +---------------+-----------------------------------------------
  // | Local helpers |
  // +---------------+

  void createLeftChild(BitTreeInteriorNode node) {
    node.leftChild = new BitTreeInteriorNode(null, null);
  }

  void createRightChild(BitTreeInteriorNode node) {
    node.rightChild = new BitTreeInteriorNode(null, null);
  }

  void createLeftLeaf(BitTreeInteriorNode node, String str) {
    node.leftChild = new BitTreeLeaf(str);
  }

  void createRightLeaf(BitTreeInteriorNode node, String str) {
    node.rightChild = new BitTreeLeaf(str);
  }


  // +---------+-----------------------------------------------------
  // | Methods |
  // +---------+

  /**
   *
   */
  public void set(String bits, String value) throws IndexOutOfBoundsException{
    BitTreeInteriorNode node = root;

    if (bits.length() != height) {
        throw new IndexOutOfBoundsException("incorrect length for bits");
    }

    for (int i = 0; i < height - 1; i++) {
      if (bits.charAt(i) == '0') {
        if (node.leftChild == null) {
          createLeftChild(node);
          node = (BitTreeInteriorNode) node.leftChild;
        } else {
          node = (BitTreeInteriorNode) node.leftChild;
        }
      } else if (bits.charAt(i) == '1') {
        if (node.rightChild == null) {
          createRightChild(node);
          node = (BitTreeInteriorNode) node.rightChild;
        } else {
          node = (BitTreeInteriorNode) node.rightChild;
        }
      } else {
        throw new IndexOutOfBoundsException("numbers not 0 or 1");
      }
    }

    if (bits.charAt(height - 1) == '0') {
      if (node.leftChild == null) {
        createLeftLeaf(node, value);
      } else {
        ((BitTreeLeaf) node.leftChild).value = value;
      }
    } else if (bits.charAt(height - 1) == '1') {
      if (node.rightChild == null) {
        createRightLeaf(node, value);
      } else {
        ((BitTreeLeaf) node.rightChild).value = value;
      }
    } else {
      throw new IndexOutOfBoundsException("numbers not 0 or 1");
    }
  } // set(String, String)

  /**
   *
   */
  public String get(String bits) throws IndexOutOfBoundsException{
    BitTreeInteriorNode node = root;

    if (bits.length() != height) {
      throw new IndexOutOfBoundsException("incorrect length for bits");
    }

    for (int i = 0; i < height - 1; i++) {
      if (bits.charAt(i) == '0') {
        if (node.leftChild == null) {
          throw new IndexOutOfBoundsException("path does not exist");
        } else {
          node = (BitTreeInteriorNode) node.leftChild;
        }
      } else if (bits.charAt(i) == '1') {
        if (node.rightChild == null) {
          throw new IndexOutOfBoundsException("path does not exist");
        } else {
          node = (BitTreeInteriorNode) node.rightChild;
        }
      } else {
        throw new IndexOutOfBoundsException("numbers not 0 or 1");
      }
    }

    if (bits.charAt(height - 1) == '0') {
      if (node.leftChild == null) {
        throw new IndexOutOfBoundsException("path does not exist");
      } else {
        return ((BitTreeLeaf) node.leftChild).value;
      }
    } else if (bits.charAt(height - 1) == '1') {
      if (node.rightChild == null) {
        throw new IndexOutOfBoundsException("path does not exist");
      } else {
        return ((BitTreeLeaf) node.rightChild).value;
      }
    } else {
      throw new IndexOutOfBoundsException("numbers not 0 or 1");
    }
  } // get(String, String)

  /**
   *
   */
  public void dump(PrintWriter pen) {
    int[] left = new int [height];
    int[] right = new int [height];
    int count = 0;

    try {
      right[count] = 1;
      String rightBits = "";
      for (int i = 0; i < height; i++) {
        rightBits = rightBits.concat(String.valueOf(right[i]));
      }
      String val = get(rightBits);
      pen.println(rightBits + "," + val);
      count++;
      dumpHelper(pen, right, count);
    } catch (IndexOutOfBoundsException e) {}

    try {
      String leftBits = "";
      for (int i = 0; i < height; i++) {
        leftBits = leftBits.concat(String.valueOf(left[i]));
      }
      String val = get(leftBits);
      pen.println(leftBits + "," + val);
      dumpHelper(pen, left, count);
    } catch (IndexOutOfBoundsException e) {}

  } // dump(PrintWriter)


  /**
   * 
   */
  public void dumpHelper(PrintWriter pen, int[] path, int count) {
    int[] order = path.clone();
        int newCount = count + 1;
if (count < height) {
    try {
      dumpHelper(pen, order, newCount);
    } catch (IndexOutOfBoundsException e) {}

    try {
      order[count] = 1;
      String rightBits = "";
      for (int i = 0; i < height; i++) {
        rightBits = rightBits.concat(String.valueOf(order[i]));
      }
      String val = get(rightBits);
      pen.println(rightBits + "," + val);
      dumpHelper(pen, order, newCount);
    } catch (IndexOutOfBoundsException e) {
      dumpHelper(pen, order, newCount);
    }

  }
  }
  /**
   *
   */
  public void load(InputStream source) {
    byte[] binary = new byte[height];
    byte[] letter = new byte[1];
    String binaryStr;
    String charStr = "";
    try {
      int length = source.available();
      while (source.read(letter, 0, 1) != -1){
        source.reset();
        source.read(binary, 0, height);
        binaryStr = new String(binary);
        source.skipNBytes(1);
        source.read(letter, 0, 1);
        while (letter[0] != '\n') {
          String temp = new String(letter);
          charStr = charStr.concat(temp);
          source.read(letter, 0, 1);
        }
        this.set(binaryStr, charStr);
        source.mark(length);
        charStr = "";
      }
    } catch (Exception e) {
      // TODO: handle exception
    }
  } // load(InputStream)


} // class BitTree
