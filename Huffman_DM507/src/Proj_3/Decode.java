/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Proj_3;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author pradeepthayaparan
 */
public class Decode {  
    
  int[] occurenceTable = new int[256];     
  
   // task 1  read  the Occurence table from the inputfile for the 256 bytes.
  public int[] readoccurenceTable(String inputFile) throws IOException{  
  
   int temp;
        // Opens File to read from 
        try ( FileInputStream input = new FileInputStream(inputFile); 
              BitInputStream bitInput = new BitInputStream(input);) {
            // "For at læse bytes fra en fil, skal man bruge read-metoden fra FileInputStream" 
            // This read method reads 1 byte instead of 4 bytes like the read method of the library class BitINputstream 
               
            
            for (int i = 0; i < occurenceTable.length-1; i++){
            
              occurenceTable[i] = bitInput.readInt();

            }
                // int temp = (int) flin.read();   
                
                
             
            
        } catch (FileNotFoundException ex) {
            Logger.getLogger(Encode.class.getName()).log(Level.SEVERE, null, ex);
        }  
        
        return occurenceTable;
    }
      
  
  }
  
  
 

    
    


