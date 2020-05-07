/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Proj_3;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * @author Karl Amadeus Finsson Hansen - karlh18@student.sdu.dk
 * @author Pradeep Thayaparan - prtha18@student.sdu.dk
 * @author Lavan Sathiyaseelan - lasat17@student.sdu.dk
 */
public class Encode {

    PQ z = new PQHeap();
    int counter = 0;
    int[] alphabet = new int[256]; // Could also just return it via countFrequencyOfBytes 
    int[] isIndex = new int[256];   // used to compare

    public static void main(String[] args) throws IOException {
        Encode encode = new Encode();
        // Read a file And put it into another file
        encode.countFrequencyOfBytes(args[0]);

    }

    // Task 1)  Read a file and make a table of how often a given byte occurs in the file
    public void countFrequencyOfBytes(String inputFile) throws IOException {
        // Used to temporary store the current read()
        int temp;
        // Opens File to read from 
        try (FileInputStream input = new FileInputStream(inputFile)) {
            // "For at læse bytes fra en fil, skal man bruge read-metoden fra FileInputStream" 
            // This read method reads 1 byte instead of 4 bytes like the read method of the library class BitINputstream 
            while ((temp = input.read()) != 0) {
                // int temp = (int) flin.read(); 
                System.out.println(temp);
                alphabet[temp]++;
            }
        } catch (FileNotFoundException ex) {
            Logger.getLogger(Encode.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    
    //Task 2) Use the Huffman Algorithm with the Occurrence Table as input ( Use all 256 entries, also those, that do not ocur) 
    public BinNode huffmanAlgorithm(int[] alphabet) {

        int n = alphabet.length;
        //minHeap
        PQ priorityQueue = new PQHeap();
        
        
        // Q = C 
            // Initialize the Priority Queue with the Characters 
        for(int i=0; i<n-1; i++){
           
            DictBinTree tree = new DictBinTree();
            // Add element into tree? 
            tree.insert(i); // Not sure if correct          // Should this be changed
            // Add a tree
            Element e = new Element(alphabet[i], tree );
            
            priorityQueue.insert(e);
        
        }
           
        // for i = 1 to n-1 
            // Not entirely sure how it translates from pseudo to our yet. So I will just minus it as usual 
                // I just guess we start @Index 0  and then move to second last index which is length -2 in java ArrayList
        for (int i = 0; i < n-2; i++) {
            
            BinNode x = (BinNode)priorityQueue.extractMin().getData();
            BinNode y = (BinNode)priorityQueue.extractMin().getData();
            int sum= x.key + y.key;
            BinNode z = new BinNode(sum);
            z.binNodeLeft = x;
            z.binNodeRight = y;
        }

        return (BinNode) priorityQueue.extractMin().getData();
        

    }

}
