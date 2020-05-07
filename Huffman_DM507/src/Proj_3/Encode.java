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
    public void huffmanAlgorithm(int[] alphabet) {

        int n = alphabet.length;
        // Q = new PQHeap();
        PQ Q = new PQHeap();

        for (int i = 0; i < n - 1; i++) {

//            Q.insert(new Element(, i));
        }

        for (int i = 1; i < n - 1; i++) {
//           z = new PQHeap();
//
//           z.left(z.extractMin(Q));  
//           z.right(z.extractMin(Q)):
//           z.insert();
//           
//               return z.extractMin();
        }

    }

}
