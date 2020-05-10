package Proj_3;

import java.util.ArrayList;

/**
 * @author Karl Amadeus Finsson Hansen - karlh18@student.sdu.dk
 * @author Pradeep Thayaparan - prtha18@student.sdu.dk
 * @author Lavan Sathiyaseelan - lasat17@student.sdu.dk
 *
 * The Structure of DictBinTree is that of a Binary Search Tree (Based on
 * Chapter 12 - @Cormen)
 *
 * And Implements the Dict interface
 */
public class DictBinTree implements Dict {

    // Reference to the root of the DictBinTree object
    public Node root;

    // Constructor that returns a new empty Dictionary¨     
    DictBinTree() {
        root = null;
    }

    public static void main(String[] args) {
        DictBinTree dog = new DictBinTree();
        for (int i = 10; i > 0; i--) {
            dog.insert(i);

        }

        dog.in_order_walk_with_path();
    }

    /**
     * Output is the path to all the nodes in the tree
     */
    // path = code
    String[] codeAlphabet = new String[256];

    // Recursively adds "0" to the codeword if it goes left & adds "1" if it goes right - When leaf is found it adds said path to the correct Array Index
    private String[] in_order_walk_helper(String path, Node node, String[] code) {
        if (node != null) {
            in_order_walk_helper(path + "0", node.nodeLeft, code); //left subtree

            // Makes sure it only reads leaves, and therefore doesn't add duplicates. 
            if (node.nodeLeft == null && node.nodeRight == null) {
                code[node.key] = path;
            }
            in_order_walk_helper(path + "1", node.nodeRight, code); // Right subtree
        }
        return code;
    }

    // Wrapper
    public String[] in_order_walk_with_path() {
        return in_order_walk_helper("", root, new String[256]); // call the helper with an an empty String Array which will be used to store the codeWords
    }

    /**
     * orderedTraversal() returns a copy of the keys in the Dictionary as an
     * ArrayList<Integer>, which is sorted
     *
     * It uses only the private method OrderedTraversal(Node x,
     * ArrayList<Integer> nodes)
     *
     * @return sorted ArrayList<Integer>
     */
    @Override
    public ArrayList<Integer> orderedTraversal() {
        return orderedTraversal(root, new ArrayList<>());
    }

    /**
     * orderedTraversal(Node x, ArrayList<Integer> nodes) Traverses through the
     * DictBinTree and finds first the smallest key to add to the ArrayList and
     * then the second smallest and so forth. And what you have left is an
     * ArrayList that is sorted
     *
     * PseudoCode: P. 288 @Cormen
     *
     * @param x The Node that is recursively added to param (In our case we call
     * first the root, which will call the other nodes subsequently in the
     * Dictionary)
     * @param nodes the ArrayList that that is filled and then returned sorted
     * @return returns a sorted ArrayList<Integer>
     */
    private ArrayList<Integer> orderedTraversal(Node x, ArrayList<Integer> nodes) {
        // if root = null return an empty ArrayList
        if (x != null) {
            // Travels recursively down the DictBinTree to the left until it finds null
            orderedTraversal(x.nodeLeft, nodes);
            // Then adds the key found
            nodes.add(x.key);
            // Travels recursively down the DictBinTree to the right until it finds null 
            orderedTraversal(x.nodeRight, nodes);

        }
        // Returns the Arraylist sorted
        return nodes;
    }

    /**
     * Search(int k) uses the private method search(Node, int)
     *
     * @param k - The key to be found
     * @return true/false if k is found
     */
    @Override
    public boolean search(int k) {
        return search(root, k);
    }

    /**
     * search(BinNode binNode, int k) Returns true/false if k is found in the
     * Dictionary It works by checking first the root and then continues down
     * the DictBinTree
     *
     * PseudoCode: P. 290 @Cormen
     *
     * @param k key k is the key to be checked in the Dictionary
     * @return returns true/false if the key k is in or not
     */
    private boolean search(Node binNode, int k) {

        // Checks if the node is null, if it is then returns false
        if (binNode == null) {
            return false;
        }

        // if k is found return true
        if (k == binNode.key) {
            return true;
        }

        // If k is less current Node go left
        if (k < binNode.key) {
            return search(binNode.nodeLeft, k);
            // otherwise go right
        } else {
            return search(binNode.nodeRight, k);
        }
    }

    /**
     * insert(int k) Inserts the key k into the DictBinTree
     *
     * It works by starting at the root & then continuing down the tree based on
     * if its higher or lower When it finally finds a empty spot to put a Node
     * It inserts a new node there with k as its key
     *
     * PseudoCode: P. 294 @Cormen
     *
     * @param k the node with the key k to put in DictBinTree
     */
    @Override
    public void insert(int k) {
        // y is used to keep track of parent of x (trailing point for x)
        Node y = null;
        //start @root
        Node x = root;

        // while x!=null go down left or right until find x becomes null
        while (x != null) {
            y = x;
            if (k < x.key) {
                x = x.nodeLeft;

            } else {
                x = x.nodeRight;
            }

        }

        //If root is already null - insert a Node as root with k as its key
        if (y == null) {
            root = new Node(k);
        } // Insert a Node with k as its key on the left side  
        else if (k < y.key) {
            y.nodeLeft = new Node(k);
            // Insert a Node with k as its key on the right side
        } else {
            y.nodeRight = new Node(k);
        }
    }
}
