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
    
  int[] occurenceTable = new int[256];
    public static void main(String[] args) throws IOException {
        Decode decode = new Decode();

        String inputFile = "hej_zippy.txt";
        String outputFile = "hejsa.txt";
        
        
        FileInputStream finput = new FileInputStream(inputFile);  
        FileOutputStream foutput = new FileOutputStream(outputFile);
        BitInputStream binput = new BitInputStream(finput); 
        BitOutputStream boutput = new BitOutputStream(foutput);

        decode.occurenceTable = decode.readoccurenceTable(binput);

        System.out.println("104 "+decode.occurenceTable[104]);
        System.out.println("101 "+decode.occurenceTable[101]);
        System.out.println("106 "+decode.occurenceTable[106]);

        BinNode huffmantree = decode.huffmanAlgorithm(decode.occurenceTable); 
        
        DictBinTree dictBinTree = new DictBinTree();   
         
        dictBinTree.root = huffmantree;
        
         
         decode.treewalk(huffmantree, binput, foutput);       
    }
    

 
    
  

    // task 1  read  the Occurence table from the inputfile for the 256 bytes. It works
    public int[] readoccurenceTable(BitInputStream  bitInput) throws IOException {
        
        int temp;
        // Opens File to read from 
        try  {
            // "For at læse bytes fra en fil, skal man bruge read-metoden fra FileInputStream" 
            // This read method reads 1 byte instead of 4 bytes like the read method of the library class BitINputstream 

            for (int i = 0; i < occurenceTable.length - 1; i++) {

                occurenceTable[i] = bitInput.readInt(); 
          //       System.out.println(occurenceTable[i]);

            }
            // int temp = (int) flin.read();   

        } catch (FileNotFoundException ex) {
            Logger.getLogger(Encode.class.getName()).log(Level.SEVERE, null, ex);
        }

        return occurenceTable;
    }  
    
    // Task 2: regenerate a huffman tree.  
  public BinNode huffmanAlgorithm(int[] alphabet) {

        int n = alphabet.length;

        //minHeap
        PQ priorityQueue = new PQHeap();

        // Q = C 
        // Initialize the Priority Queue with 256 items aka the Characters to the queue 
        for (int i = 0; i < n - 1; i++) {

            // Each node represents a character 
            BinNode node = new BinNode(i);

            // Add an Element to the queue:  Frequency as Key in Element & the Tree/Node as data 
            Element e = new Element(alphabet[i], node);
            priorityQueue.insert(e);

        }

        // ########CAN BE DELETE VERY SOOOON###############################
//        int counter = 0; // used for test
        // for i = 1 to n-1 
        // Not entirely sure how it translates from pseudo to our yet. So I will just minus it as usual 
        // I just guess we start @Index 0  and then move to second last index which is length -2 in java ArrayList
        // ################################################################
        // For loop: Takes everything out of the Priority Queue except for the last one
        for (int i = 0; i < n - 2; i++) {

            // Extract the 2 elements with the lowest frequency(key) in the heap
            Element x = priorityQueue.extractMin();
            Element y = priorityQueue.extractMin();

            int sum = x.getKey() + y.getKey(); // sum used as key for a newly created Node, which will be set as parent of the two extracted nodes. 

//             System.out.println("counter: " + counter++ + " sum : " + sum); // Used for test ##############Can be deleted soon
            // Creates new node/tree & adds the two extracted nodes
            BinNode z = new BinNode(sum);   // Not sure if this should have anything  or if it should have 0 ???  Or does it not matter?
            z.binNodeLeft = (BinNode) x.getData();
            z.binNodeRight = (BinNode) y.getData();

            // Adds the new node/tree to the priority queue
            priorityQueue.insert(new Element(sum, z));
        }

        return (BinNode) priorityQueue.extractMin().getData();
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

     public void treewalk(BinNode rootNode, BitInputStream inputStream, FileOutputStream fileoutput) throws IOException {

        int counter = 0;
        int numBytes =calcBytes(occurenceTable);
      
        BinNode resetBinNode = rootNode;  
        String bitprefix = "";
        int readBit;
        
        
        
        

        while (counter < numBytes ) {
            if (rootNode.binNodeLeft == null && rootNode.binNodeRight == null) {
                System.out.println("BitPrefix"+bitprefix);
                fileoutput.write(rootNode.key); 
                System.out.println(rootNode.key);
                
                // reset
                bitprefix ="";
                rootNode = resetBinNode;
                
            }

            readBit = inputStream.readBit();

            if (readBit == 0) {

                rootNode = rootNode.binNodeLeft;
                bitprefix += readBit;
            } else if (readBit == 1) {
                rootNode = rootNode.binNodeRight;
                bitprefix += readBit;

            }  
            
            

        }
     }
}
 