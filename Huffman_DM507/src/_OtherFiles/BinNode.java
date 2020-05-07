/**
 * @author Karl Amadeus Finsson Hansen - karlh18@student.sdu.dk
 * @author Pradeep Thayaparan - prtha18@student.sdu.dk
 * @author Lavan Sathiyaseelan - lasat17@student.sdu.dk
 * 
 * BinNode class represents the nodes in the tree
 * Each node has two children &  one key
 */

public class BinNode {
    //Left Child
    BinNode binNodeLeft;
    //Right Child
    BinNode binNodeRight;    
   
    //key of Node
    int key;

    //Sets the value of the BinNode to zero children & the key parameter as its key
    public BinNode(int key) {
        binNodeLeft = null;
        binNodeRight = null;
        this.key = key;
    }
}
