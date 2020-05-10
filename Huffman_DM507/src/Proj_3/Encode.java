package Proj_3; //Delete this before Aflevering
                // And change arguments in main to args[0], args[1]

  
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * @author Karl Amadeus Finsson Hansen - karlh18@student.sdu.dk
 * @author Pradeep Thayaparan - prtha18@student.sdu.dk
 * @author Lavan Sathiyaseelan - lasat17@student.sdu.dk
 */
public class Encode {

    static int[] alphabet = new int[256]; // Stores each byte as an int in the indexes
    static String[] codeLookupTable; // // Used to store the prefix code for each byte
    static DictBinTree huffManTree; // // HuffMan Tree
    
    

    // Remember to change arguments in methods to args[0] for inputfile & args[1]for outputfile
    public static void main(String[] args) throws IOException {
        
        // Open input and output byte streams to/from files.
        FileInputStream inFile = new FileInputStream("hej.txt");
        FileOutputStream outFile = new FileOutputStream("hej_zippy.txt");

        // Wrap the new bit streams around the input/output streams.
        BitInputStream in = new BitInputStream(inFile);
        BitOutputStream out = new BitOutputStream(outFile);
        
        countFrequencyOfBytes(inFile); //Reads a file anc checks how frequent a given byte occurs
        
        //Generates a Huffman-Tree
        BinNode huffmanTree = huffmanAlgorithm(alphabet);
        
        
        
        
        

//        // #Test2 - huffmanAlgorithm()
//        BinNode binNode = encode.huffmanAlgorithm(encode.alphabet);  // works 
//        // Uses the created graph  & adds it to the tree
//        encode.huffManTree = new DictBinTree();
//        encode.huffManTree.root = binNode;
//
//        //Test 3a:
////        String[] dog = encode.huffManTree.in_order_walk_with_path();
//
//
//
//
//        // String[255] is empty !!! Missing one assignment 
//        String[] lookupTable = encode.createCodeLookupTable(encode.huffManTree);
//
//
//
//
//
////        System.out.println("104: " + dog[104]); 
////        System.out.println("101: " + dog[101]); 
////        System.out.println("106: " + dog[106]); 
////        System.out.println("13: " + dog[13]); 
////        System.out.println("10: " + dog[10]); 
////        System.out.println("200: " + dog[200]); 
//
//        // Test 3b:
////        encode.createCodeLookupTable(encode.huffManTree);
//        // Test 4:
//        // Tests that need to be conducted:
//        // Check to see how many bytes are written 
//      //  encode.writeIntsToOutput(encode.alphabet, inputFile);
//        
//        encode.writeToOutPut(encode.alphabet,inputFile,outputFile);
//
//        //Test 5: 
//        // encode.writeCodeWordsToOutput(inputFile, outputFile);
        // TEST OF SAVECODE  
        // encode.saveCode(dictBinTree);
//        System.out.println(alphabet[3]);
    }
    
    // Task 1)  Reads a file and makes a table of how often a given byte occurs in the file    
    public static void countFrequencyOfBytes(FileInputStream input) throws IOException {

        int temp;  // Used to temporary store the current read() int

        while ((temp = input.read()) != -1) {// Uses the read method from FileInputStream  to read a byte (8bits) from a file at a time
            System.out.println(temp); // used to test if it reads correct
            alphabet[temp]++;
        }
    }

    //Task 2) Use the Huffman Algorithm with the alphabet Table as input ( Use all 256 entries, also those, that do not occur  
    // Creates a HuffmanTree
    public static BinNode huffmanAlgorithm(int[] alphabet) {

        int n = alphabet.length; // length of the alphabet

        //minHeap
        PQ priorityQueue = new PQHeap();

        // Q = C : Initialize the Priority Queue with 256 items aka the Characters to the queue 
        for (int i = 0; i < n; i++) {

            // Each node represents a character(byte)
            BinNode node = new BinNode(i);

            // Add an Element to the queue:  Frequency as Key in Element & the Tree/Node as data 
            Element e = new Element(alphabet[i], node);
            priorityQueue.insert(e);
        }

        // For loop: Takes everything out of the Priority Queue except for the last one
        for (int i = 0; i < n - 1; i++) {

            // Extract the 2 elements with the lowest frequency(key) in the heap
            Element x = priorityQueue.extractMin();
            Element y = priorityQueue.extractMin();

            int sum = x.getKey() + y.getKey(); // sum used as key for a newly created Node, which will have the two extracted nodes as children  

            // Creates new node & adds the two extracted nodes as children to it
            BinNode z = new BinNode(sum); // The sum of this one is irrelevant. 
            z.binNodeLeft = (BinNode) x.getData();
            z.binNodeRight = (BinNode) y.getData();

            // Adds the new node to the priority queue
            priorityQueue.insert(new Element(sum, z));
        }
        return (BinNode) priorityQueue.extractMin().getData(); // Returns the root of the tree
    }

    
    
    //Task 3 )  Convert the Huffman Tree to a Table(An Array with 256 entries)
    // Over the codes for each of the possible bytes 
    // Remember - The bytes are of the type int between 0 and 255 
    // and can be used as indexes in the arrays
    public void createCodeLookupTable(DictBinTree tree) {
        String[] code = tree.in_order_walk_with_path();
        codeLookupTable = code;
  }

    

    
    
    
    
    
    // Create Test:  Check if there has been written 256 bytes * 4 
    // 256*4 =  1024 bytes      // FileInputStream to check each byte  --- since the BitInputStream takes in 32 bytes
    // 4 bits 
    //task 4)  Write alphabet into outputfile
    public void writeIntsToOutput(int[] alphabeth, String outFile) throws IOException {
        try ( FileOutputStream output = new FileOutputStream(outFile);  BitOutputStream out = new BitOutputStream(output);  FileInputStream in = new FileInputStream(outFile)) {
            int sumofbytes = 0;
//            int counter = 0;
            for (int i : alphabeth) {

                out.writeInt(i);     //  
                in.read(); 
               
                //    sumofbytes +=i;   
                //     sumofbytes+= (int) out.writeInt(i);
//                System.out.println("Counter: " + counter++);
            }
           
            
            System.out.println("Sum of bytes " + sumofbytes);
            System.out.println("Alphabeth " + alphabet.length);

        }

    }
   
    // new method --> scan input     
    public void writeToOutPut(int[] alphabeth, String infile, String outFile) throws FileNotFoundException, IOException {
        FileOutputStream output = new FileOutputStream(outFile);
        BitOutputStream out = new BitOutputStream(output);
        FileInputStream in = new FileInputStream(infile);  
        
        // takes the 256 integers representing occurrences and writes dem down.
        // then the input file is scanned where each char is read to find its codeword in order, which then will be printed to the output file.  
        try {
            for (int i : alphabeth) {
                out.writeInt(i);

            }
        } catch (Exception e) { 
           e.printStackTrace();
        
        }  
          System.out.println("104 "+ codeLookupTable[104]);
          System.out.println("101 "+ codeLookupTable[101]);
          
          System.out.println("106 "+ codeLookupTable[106]);
        while (in.available() != 0) {

            int i = in.read();
          
            System.out.println("LOOKUP "+codeLookupTable[i]);  
            
            
            char[] temp = codeLookupTable[i].toCharArray();

           
            for (char c : temp) {
                
//               if(temp[i]=='1'){
//               
//                   System.out.println("TJEK");
//               
//               } else if (temp[i]=='0')
//                    System.out.println("NO TJEK");
                
                out.writeBit(Character.getNumericValue(c));    
                
               
                System.out.println("char : "+c+" Value = "+c + " Value of the char "+Character.getNumericValue(c));
                //
            }
     
       
        } 
        
        in.close();
        out.close();

    }

    // Figure out how to write correct bits each time
    // Figure out if should use Stream as parameter so it doesn't overrride the other method
    // Test file if it works as intended 
    //task 5)   Read the inputfile again 
    // For each byte look at that int write those bits into the output file 
    // "101"  -> writeBit(1), writeBit(0), write(1) 
    public void writeCodeWordsToOutput(String inputFile, String outputFile) throws IOException {
        int tempByte;

        // Opens File to read from 
        try ( FileInputStream input = new FileInputStream(inputFile); 
                FileOutputStream output = new FileOutputStream(outputFile);  
                BitOutputStream bitput = new BitOutputStream(output);) {
            // This read method reads 1 byte instead of 4 bytes like the read method of the library class BitINputstream 
            while ((tempByte = input.read()) != -1) {
                // int temp = (int) flin.read(); 
                System.out.println(tempByte);
                String currentCode = codeLookupTable[tempByte]; // "101" 
                System.out.println(currentCode);
                int j;
                for (int i = 0; i < currentCode.length() - 1; i++) {
                    if (currentCode.charAt(i) == '1') {
                        System.out.println(currentCode.charAt(i));
                        bitput.writeBit(1);
                    } else {
                        System.out.println(currentCode.charAt(i));
                        bitput.writeBit(0);
                    }
//                    j = currentCode.charAt(i);
//                    System.out.println(j);
//                    bitput.writeBit(1);
                }

            }
        } catch (FileNotFoundException ex) {
            Logger.getLogger(Encode.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

}
