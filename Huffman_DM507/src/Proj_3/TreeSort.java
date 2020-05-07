package Proj_3;

import java.util.ArrayList;
import java.util.Scanner;

/**
 * @author Karl Amadeus Finsson Hansen - karlh18@student.sdu.dk
 * @author Pradeep Thayaparan - prtha18@student.sdu.dk
 * @author Lavan Sathiyaseelan - lasat17@student.sdu.dk
 * 
 * Adds data into the tree from input & sorts &  prints it 
 * You can optionally Uncomment the last 4 lines to check the search method 
 */
public class TreeSort {
    

    public static void main(String[] args) {
        
        
        Dict dictBinTree = new DictBinTree();
	Scanner sc = new Scanner(System.in);
        
        // adds data into the dictBinTree
        while (sc.hasNextInt()) {
	    dictBinTree.insert(sc.nextInt());
       }
        
        // Create ArrayList to be used to print out the data in an ordered fashion
	ArrayList<Integer> sortedTree = dictBinTree.orderedTraversal();

        // Prints the sorted data
        for (int i = 0; i < sortedTree.size(); i++) {
            System.out.println(sortedTree.get(i));
        }
//        Uncomment to check if search method works  - It checks only three numbers:: 10,20,36
//        System.out.println("Is 10 in the file: "+dictBinTree.search(10));
//        System.out.println("Is 20 in the file: "+dictBinTree.search(20));
//        System.out.println("Is 36 in the file:  "+dictBinTree.search(36));
        
        
    }
}
