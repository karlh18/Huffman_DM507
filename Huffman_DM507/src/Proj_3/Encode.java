import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

/**
 * @author Karl Amadeus Finsson Hansen - karlh18@student.sdu.dk
 * @author Pradeep Thayaparan - prtha18@student.sdu.dk
 * @author Lavan Sathiyaseelan - lasat17@student.sdu.dk
 */
public class Encode {

    static int[] alphabet = new int[256]; // Stores each byte as an int in the indexes - Also called occurrence table
    static String[] codeLookupTable; // // Used to store the prefix code for each byte
    static DictBinTree huffManTree; // // HuffMan Tree

    // Remember to change arguments in methods to args[0] for inputfile & args[1]for outputfile
    public static void main(String[] args) throws IOException {
        
        // Open input and output byte streams to/from files.  
       //FileInputStream inFile = new FileInputStream(args[0]);  
       //FileOutputStream outFile = new FileOutputStream(args[1]);   
       
        // Open input and output byte streams to/from files. // skal fjernes 
        FileInputStream inFile = new FileInputStream("hej.txt");
        FileOutputStream outFile = new FileOutputStream("hej_zippy.txt");

        
        
        // Wrap the new bit streams around the input/output streams.
        BitOutputStream out = new BitOutputStream(outFile);

        countFrequencyOfBytes(inFile); //Reads a file anc checks how frequent a given byte occurs
        inFile = new FileInputStream("hej.txt");    // resets the stream  - so it can read again from start

        //Generates a Huffman-Tree
        Node huffmanNodes = huffmanAlgorithm(alphabet);
        DictBinTree huffmanTree = new DictBinTree();
        huffmanTree.root = huffmanNodes;

        // Generates a CodeLookupTable from the huffmanTree
        codeLookupTable = createCodeLookupTable(huffmanTree);

        // Writes the OccurrenceTable & The Code Words to the output File
        writeToOutPut(alphabet, inFile, outFile, out);
        
        // Closes the streams. 
        out.close();
        inFile.close();
        outFile.close();

//        System.out.println("The file " + args[0] + " has been successfully encoded to the file " + args[1]);
        System.out.println("The file " + "hej.txt" + " has been successfully encoded to the file " + "hej_zippy.txt");
        
    }

    // Task 1)  Reads a file and makes a table of how often a given byte occurs in the file    
    public static void countFrequencyOfBytes(FileInputStream input) throws IOException {

        int temp;  // Used to temporary store the current read() int

        while ((temp = input.read()) != -1) {// Uses the read method from FileInputStream  to read a byte (8bits) from a file at a time
            alphabet[temp]++;
        }
    }

    //Task 2) Use the Huffman Algorithm with the alphabet Table as input ( Use all 256 entries, also those, that do not occur  
    // Creates a HuffmanTree
    public static Node huffmanAlgorithm(int[] alphabet) {

        int n = alphabet.length; // length of the alphabet

        //minHeap
        PQ priorityQueue = new PQHeap();

        // Q = C : Initialize the Priority Queue with 256 items aka the Characters to the queue 
        for (int i = 0; i < n; i++) {

            // Each node represents a character(byte)
            Node node = new Node(i);

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
            Node z = new Node(sum); // The sum of this one is irrelevant. 
            z.nodeLeft = (Node) x.getData();
            z.nodeRight = (Node) y.getData();

            // Adds the new node to the priority queue
            priorityQueue.insert(new Element(sum, z));
        }
        return (Node) priorityQueue.extractMin().getData(); // Returns the root of the tree
    }

    //Task 3 )  Converts the Huffman Tree to a Code Lookup Table (An Array with 256 entries)
    // Each byte will thus have a String similar to : "101"    
    public static String[] createCodeLookupTable(DictBinTree tree) {
        // Uses the code from the DictBinTree that traverses through the tree
        String[] code = tree.in_order_walk_with_path();
        return code;
    }

    //task 4 & 5)   Writes the occurenceTable(Hyppighedstabellen) at the start of the file & then subsequentially writes for each byte its corresponding codeWord to the file 
    public static void writeToOutPut(int[] occurrenceTable, FileInputStream in, FileOutputStream output, BitOutputStream out) throws FileNotFoundException, IOException {
        try {
            // Writes the occurenceTable (Hyppighedstabellen) to the start of the file
            for (int i : occurrenceTable) {
                out.writeInt(i);
            }
            // For each byte in inputFile it writes the corresponding codeword to outputfile
            while (in.available() != 0) {
                int i = in.read();
                char[] temp = codeLookupTable[i].toCharArray();
                for (char c : temp) {
                    out.writeBit(Character.getNumericValue(c));
                }
            }
        } catch (IOException e) {
        }
    }
    
    
}
