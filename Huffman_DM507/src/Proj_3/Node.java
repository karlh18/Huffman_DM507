/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Proj_3;

/**
 *
 * @author Karla
 */
public class Node {
        //Left Child
    Element binNodeLeft;
    //Right Child
    Element binNodeRight;    
   
    //key of Node
    int key;

    //Sets the value of the BinNode to zero children & the key parameter as its key
    public Node(int key) {
        binNodeLeft = null;
        binNodeRight = null;
        this.key = key;
    }
    
    
}
