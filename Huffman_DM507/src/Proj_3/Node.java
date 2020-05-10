package Proj_3;


/**
 * @author Karl Amadeus Finsson Hansen - karlh18@student.sdu.dk
 * @author Pradeep Thayaparan - prtha18@student.sdu.dk
 * @author Lavan Sathiyaseelan - lasat17@student.sdu.dk
 
 Node class represents the nodes in the tree
 Each node has two children & one key  
 * 
 * This class was called BinNode in the previous assignment 
 */

public class Node {
    //Left Child
    Node binNodeLeft;
    //Right Child
    Node binNodeRight;    
   
    //key of Node
    int key;

    //Sets the value of the Node to zero children & the key parameter as its key
    public Node(int key) {
        binNodeLeft = null;
        binNodeRight = null;
        this.key = key;
    }
    
    
    
}
