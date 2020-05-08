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
import java.util.ArrayList;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * @author Karl Amadeus Finsson Hansen - karlh18@student.sdu.dk
 * @author Pradeep Thayaparan - prtha18@student.sdu.dk
 * @author Lavan Sathiyaseelan - lasat17@student.sdu.dk
 */
public class Encode {
    
    
    public static void main(String[] args) throws IOException {
        Encode encode = new Encode();
        encode.countFrequencyOfBytes(args[0]);

//        System.out.println(alphabet[3]);
        
    }

    PQ z = new PQHeap();
    int counter = 0;
    int[] alphabet = new int[256]; // Could also just return it via countFrequencyOfBytes 
    int[] isIndex = new int[256];   // used to compare

    

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

    //Task 2) Use the Huffman Algorithm with the Occurrence Table as input ( Use all 256 entries, also those, that do not occur  
    public DictBinTree huffmanAlgorithm(int[] alphabet) {

        int n = alphabet.length;

        //minHeap
        PQ priorityQueue = new PQHeap();

        // Q = C 
        // Initialize the Priority Queue with the Characters 
        for (int i = 0; i < n - 1; i++) {

            DictBinTree tree = new DictBinTree();

            // Add element into tree? 
            tree.insert(i); // Not sure if correct          // Should this be changed ? 

            // Add a tree
            Element e = new Element(alphabet[i], tree);

            priorityQueue.insert(e);

        }

        // for i = 1 to n-1 
        // Not entirely sure how it translates from pseudo to our yet. So I will just minus it as usual 
        // I just guess we start @Index 0  and then move to second last index which is length -2 in java ArrayList
        for (int i = 0; i < n - 2; i++) {

            // Doesn't this provide us with a tree instead of a node?
            // Should be tree or node? hmmm
            // Extract the elements with the lowest key in the heap
            // and get their sum
            Element x = priorityQueue.extractMin();
            Element y = priorityQueue.extractMin();
            int sum = x.getKey() + y.getKey();

            // Used later to get the nodes left 
            DictBinTree xx = (DictBinTree) x.getData();
            DictBinTree yy = (DictBinTree) y.getData();

            // Creates new tree & adds the two previous as children
            DictBinTree z = new DictBinTree();
            z.root.binNodeLeft = xx.root;
            z.root.binNodeRight = yy.root;

            // Adds the new tree to the priority queue
            priorityQueue.insert(new Element(sum, z));
        }

        return (DictBinTree) priorityQueue.extractMin().getData();

        // Might need change it to Node
    }

    //Task 3 )  Convert the Huffman Tree to a Table(An Array with 256 entries)
    // Over the codes for each of the possible bytes 
    // Remember - The bytes are of the type int between 0 and 255 
    // and can be used as indexes in the arrays
    public void saveCode(DictBinTree tree) {
        ArrayList<String> code = tree.in_order_walk_with_path();
        String[] codeArray = new String[256];
        // make toArray later

        for (int i = 0; i < code.size() - 1; i++) {
            codeArray[i] = code.get(i);
        }

    }

    //task 4)  Write alphabeth into outputfile
    public void writeIntsTOoOutput(int[] alphabeth, String outFile) throws IOException {
        try (FileOutputStream output = new FileOutputStream(outFile);
                BitOutputStream out = new BitOutputStream(output);) {
            for (int i : alphabeth) {
                out.writeInt(i);
            }
        } 

    }
    
    //task 5) 
    

}
