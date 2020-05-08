package Proj_3;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * @author Karl Amadeus Finsson Hansen - karlh18@student.sdu.dk
 * @author Pradeep Thayaparan - prtha18@student.sdu.dk
 * @author Lavan Sathiyaseelan - lasat17@student.sdu.dk
 */
public class Encode {

    
    // Stores each byte as an int
    int[] alphabet = new int[256];

    // Used to store the prefix code for each byte
    String[] codeLookupTable;
    
    // HuffMan Tree
    DictBinTree huffManTree;
    
    // Task 1)  Reads a file and makes a table of how often a given byte occurs in the file    
    public void countFrequencyOfBytes(String inputFile) throws IOException {
        // Used to temporary store the current read()
        int temp;

        // Opens File to read from 
        try (FileInputStream input = new FileInputStream(inputFile)) {
            // Use the read method from FileInputStream  to read a byte (8bits) from a file
            // This read method reads 1 byte instead of 4 bytes like the read method of the library class BitInputstream 
            while ((temp = input.read()) != -1) {
                System.out.println(temp); // used to test if it reads correct
                alphabet[temp]++;
            }
        } catch (FileNotFoundException ex) {
            Logger.getLogger(Encode.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    //Task 2) Use the Huffman Algorithm with the Occurrence Table as input ( Use all 256 entries, also those, that do not occur  
    // Creates a HuffmanTree 
     public BinNode huffmanAlgorithm(int[] alphabet) {

        int n = alphabet.length;

        //minHeap
        PQ priorityQueue = new PQHeap();

        // Q = C 
        // Initialize the Priority Queue with the Characters 
        for (int i = 0; i < n - 1; i++) {

            BinNode node = new BinNode(i);

            // Add element into tree? 
            node.key = i ; // Not sure if correct          // Should this be changed ? 

            // Add a tree
            Element e = new Element(alphabet[i], node);

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
            System.out.println(sum);
            // Used later to get the nodes left 
            BinNode xx = (BinNode) x.getData();
            BinNode yy = (BinNode) y.getData();

            // Creates new tree & adds the two previous as children
            BinNode z = new BinNode(sum);
            z.binNodeLeft =  (BinNode) x.getData();
            z.binNodeRight =(BinNode) y.getData();

            // Adds the new tree to the priority queue
            priorityQueue.insert(new Element(sum, z));
        }

        return (BinNode) priorityQueue.extractMin().getData();
    }

    
    
    
    
    // Remember to change arguments in methods to args[0] for inputfile & args[1]for outputfile
    public static void main(String[] args) throws IOException { 
       // TEST OF countFrequencyOfBytes Method
        String s = "hej.txt";
        Encode encode = new Encode();
        encode.countFrequencyOfBytes(s);  
//        System.out.println(encode.alphabet[104]);
//        System.out.println(encode.alphabet[107]); 
//        





      // TEST OF huffmanAlgorithm  
 //     BinNode binNode = encode.huffmanAlgorithm2(encode.alphabet);  
       
//       DictBinTree dictBinTree = new DictBinTree();
       
      // dictBinTree.root = binNode;
       
//       dictBinTree.in_order_walk_with_path();
       
     // TEST OF SAVECODE  
     
      // encode.saveCode(dictBinTree);
        
        

//        System.out.println(alphabet[3]);
    }

    
    
    
    
    
    
    

    
    //Task 3 )  Convert the Huffman Tree to a Table(An Array with 256 entries)
    // Over the codes for each of the possible bytes 
    // Remember - The bytes are of the type int between 0 and 255 
    // and can be used as indexes in the arrays
    public String[] saveCode(DictBinTree tree) {
        ArrayList<String> code = tree.in_order_walk_with_path();
        String[] codeArray = new String[256];
        // make toArray later

        for (int i = 0; i < code.size() - 1; i++) {
            codeArray[i] = code.get(i);  
            System.out.println(codeArray[i]);
        }
        codeLookupTable = codeArray;
        return codeArray;
    }

    //task 4)  Write alphabeth into outputfile
    public void writeIntsTOoOutput(int[] alphabeth, String outFile) throws IOException {
        try ( FileOutputStream output = new FileOutputStream(outFile);  BitOutputStream out = new BitOutputStream(output);) {
            for (int i : alphabeth) {
                out.writeInt(i);
            }
        }

    }

    //task 5)   
    public void WriteInput(String inputFile, String outputFile) throws IOException {

        // Vi skal læse fra inputfil et byte ad gangen for hvert byte skal vi kigge i Code tabellen og skrive de bytes i outputfil   
        int tempByte;
        // Opens File to read from 
        try ( FileInputStream input = new FileInputStream(inputFile);  FileOutputStream output = new FileOutputStream(outputFile);  BitOutputStream bitput = new BitOutputStream(output);) {
            // "For at læse bytes fra en fil, skal man bruge read-metoden fra FileInputStream" 
            // This read method reads 1 byte instead of 4 bytes like the read method of the library class BitINputstream 
            while ((tempByte = input.read()) != 0) {
                // int temp = (int) flin.read(); 
                System.out.println(tempByte);

                String currentCode = codeLookupTable[tempByte];
                for (int i = 0; i < currentCode.length() - 1; i++) {
                    bitput.writeBit(currentCode.charAt(i));

                }

            }
        } catch (FileNotFoundException ex) {
            Logger.getLogger(Encode.class.getName()).log(Level.SEVERE, null, ex);
        }

    }
    
    
    
    //Task 2) Use the Huffman Algorithm with the Occurrence Table as input ( Use all 256 entries, also those, that do not occur  
    public DictBinTree huffmanAlgorithm2(int[] alphabet) {

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
    

}
