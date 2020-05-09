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
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author pradeepthayaparan
 */
public class Decode {

    public static void main(String[] args) {

        
    }

    int[] occurenceTable = new int[256];

    // task 1  read  the Occurence table from the inputfile for the 256 bytes.
    public int[] readoccurenceTable(String inputFile) throws IOException {

        int temp;
        // Opens File to read from 
        try ( FileInputStream input = new FileInputStream(inputFile);  BitInputStream bitInput = new BitInputStream(input);) {
            // "For at læse bytes fra en fil, skal man bruge read-metoden fra FileInputStream" 
            // This read method reads 1 byte instead of 4 bytes like the read method of the library class BitINputstream 

            for (int i = 0; i < occurenceTable.length - 1; i++) {

                occurenceTable[i] = bitInput.readInt();

            }
            // int temp = (int) flin.read();   

        } catch (FileNotFoundException ex) {
            Logger.getLogger(Encode.class.getName()).log(Level.SEVERE, null, ex);
        }

        return occurenceTable;
    }

    // Task 2: regenerate a huffman tree. 
    public void regeneratehuffmantree(int[] alphabet) {

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

        // return (DictBinTree) priorityQueue.extractMin().getData();
    }

    public int calcBytes(int[] bytes) {

        int sum = 0;

        for (int i = 0; i < bytes.length; i++) {

            sum += bytes[i];
        }

        return sum;
    }

    public int[] decodingBits(int[] freqTable, String inputFile, String outputFile ) {

         int bitsofar =0;
         int totalbits = calcBytes(freqTable);   
         List<Integer> list = new ArrayList<>();
              
         String s =""; 
        try(FileInputStream input = new FileInputStream(inputFile); 
            BitInputStream bitInput = new BitInputStream(input);   
            FileOutputStream output = new FileOutputStream(outputFile);
            BitOutputStream bitput = new BitOutputStream(output);) {
            
            
            while(bitsofar < totalbits){
            
             s += bitInput.readBit();    
                
          
             for(int i = 0; i< freqTable.length-1; i++){
             
                if(s.equals(freqTable[i])){
                   list.add(i);
               //    bitput.writeBit(i); 
                   s = "";
                }
             }
             
              
            
            }
//          for( int i = 0; i < bytes.length-1 ; i++){
//          bytes[i] = bitInput.readBit();  
//          }  
//         
            
            
        } catch (Exception e) {
        }
        

           return freqTable;  
    }

    
     
    public void convertto32Bits(){
    
    // 
    
        
        
    }
    
    
}
