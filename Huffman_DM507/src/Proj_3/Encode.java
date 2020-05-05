/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Proj_3;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Karla
 */
public class Encode {
        int[] alphabet = new int[256];

    public static void main(String[] args) {
        int d = (int) 'd';
        
        
        
        
        
        
        
//        int[] alphabet = new int[256];

//        alphabet[d]++;
//        
//
//        System.out.println(alphabet[d]);

        //   System.out.println(d);
    }

//    public void insertIntoAlphath("File"){
//        for (int i = 0; i < alphabet.length; i++) {
//            alphabet[i] = 
//            
//        }
//    }
    Scanner sc;

    FileInputStream flin;

    
    // Opgave 1:
        // Læs en fil ind
        // for hvert byte (aka for hvert bogstav) tæl, hvor often den forekommer 
            // d = (int) 'd' 
            // alphabet[d} ++; 
    
    
    
    public void countBytes(String path) throws IOException {
        try {
            flin = new FileInputStream(path);
//            flin = new FileInputStream("D:\\isnfaos");

//            InputStream input = new InputStream();

//            BitInputStream bitInputStream = new BitInputStream(new InputStream());

//            int i = BitInputStream.readInt();
//            int i = flin.read();
//            alphabet[i]++;
            
           
            
            
//            System.out.println((char) i);

        } catch (FileNotFoundException ex) {
            Logger.getLogger(Encode.class.getName()).log(Level.SEVERE, null, ex);
        }

        flin.close();
    }

}
