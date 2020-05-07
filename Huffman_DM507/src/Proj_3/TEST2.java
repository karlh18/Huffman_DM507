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
public class TEST2 {
    int[] alphabet = new int[256];
    int[] isIndex = new int[256];

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) throws IOException {
     TEST2 t2 = new TEST2();  
     String s = "Hej.txt";
     t2.countBytes(s);
      
    }

    public void countBytes(String path) throws IOException {
        try ( FileInputStream flin = new FileInputStream(path)) {

            // flin = new FileInputStream("D:\\isnfaos");
            // Wrap the new bit streams around the input/output streams.
            BitInputStream bitInputStream = new BitInputStream(flin);

            // need to use another file aka outfile.
            // BitOutputStream bitOutputStream = new BitOutputStream();
            // First read a full int (i.e., four bytes) from input file
            // using the library method readInt().
            int i = bitInputStream.readInt();
            System.out.println("The value: "+i);
            //int i = flin.read();   
            alphabet[i]++;
//            System.out.println((char) i);
        } catch (FileNotFoundException ex) {
            Logger.getLogger(Encode.class.getName()).log(Level.SEVERE, null, ex);
        }

      
    }

}
