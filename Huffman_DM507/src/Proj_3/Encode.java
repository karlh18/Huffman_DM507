/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Proj_3;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Karla
 */
public class Encode {    
    
    
    

    int counter = 0;
    int[] alphabet = new int[256];
    int[] isIndex = new int[256];   // used to compare
    //  int[] tree = new int[256];  // huffmans converted tree --> table 

    PQHeap z;
    
//    public Encode(String fil) throws IOException {
//        
//        countBytes(fil); 
//        
//    }
//    
    public static void main(String[] args) throws IOException {  
     Encode en = new Encode();  
     
     en.countBytes(args);
     
        
        
        int d = (int) 'd';
        System.out.println("Number: " + d);
//
//        alphabet[d]++;
//        
//
//        System.out.println(alphabet[d]);
//  int nextBit = 0;
//  int output = 2;  
//        System.out.println( 1 | nextBit);
//     output = output << 1 | nextBit; 
//        System.out.println("Annnnnnnddd   "+output);
        //      System.out.println(d);
    }
    
    public int[] huffMan(int[] alphabet) {
        
        int n = alphabet.length;
        
        int[] Q = alphabet;
        
        for (int i = 1; i < n - 1; i++) {
            z = new PQHeap();

            // z.left(z.extractMin(Q));  
            // z.right(z.extractMin(Q)):
            //z.INSERT
            //return z.extractMin();
        }
        
        return Q;
        
    }
    
    public void insertIntoAlphath(String File) {
        
        for (int i = 0; i < alphabet.length; i++) {
            
            int k = alphabet[i];            
            
            byte b = (byte) i;            
            
            System.out.println("b = " + b);   //-1 because out of range   
            
            int bigbyte = b & 0xFF;
            
            System.out.println("bigbyte = " + b);   //-1 because out of range   
            
            if (alphabet[i] == isIndex[i]) {
                alphabet[i]++;                
                counter++;                
            }
            
        }
    }
    Scanner sc;
    
    FileInputStream flin;

    // Opgave 1:
    // Læs en fil ind
    // for hvert byte (aka for hvert bogstav) tæl, hvor often den forekommer 
    // d = (int) 'd' 
    // alphabet[d} ++; 
    public void countBytes(String[] a) throws IOException {  

        int  tempr = 0;
        try ( FileInputStream flin = new FileInputStream(a[0])) {

            // flin = new FileInputStream("D:\\isnfaos");
            // Wrap the new bit streams around the input/output streams.
            BitInputStream bitInputStream = new BitInputStream(flin); 
            while(tempr == flin.read() != 0) {  
               int temp = (int) flin.read(); 
               
               
            
            }
         
            // need to use another file aka outfile.
            // BitOutputStream bitOutputStream = new BitOutputStream();
            // First read a full int (i.e., four bytes) from input file
            // using the library method readInt().
            int i = bitInputStream.readInt();
             
            
            //int i = flin.read();   
            alphabet[i]++;

//            System.out.println((char) i);
        } catch (FileNotFoundException ex) {
            Logger.getLogger(Encode.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        flin.close();
    }
    
}
