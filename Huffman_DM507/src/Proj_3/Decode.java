package Proj_3;

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
public class Decode {

    static int[] alphabet = new int[256];
    static int byteAmount = 0;

    public static void main(String[] args) throws IOException {

        // Open input and output byte streams to/from files.
        FileInputStream inFile = new FileInputStream("hej_zippy.txt");
        FileOutputStream outFile = new FileOutputStream("hejsa.txt");

        // Wrap the new bit streams around the input/output streams.
        BitInputStream in = new BitInputStream(inFile);

        // Reads the occurencetable from the inputfile & stores them in an integer array
        alphabet = readoccurenceTable(in);

        //Regenerates the Huffman-Tree from the occurencetable
        Node huffmanNodes = huffmanAlgorithm(alphabet);

        // Finally calling the traversing method. 
        treewalk(huffmanNodes, in, outFile);

    }

    // Task 1)  reads  the Occurence table(Hyppighedstabellen) from the inputfile for the 256 bytes. & Calculates the number of bytes read
    public static int[] readoccurenceTable(BitInputStream bitInput) throws IOException {

        int[] occurenceTable = new int[256];

        try {
            for (int i = 0; i < occurenceTable.length; i++) {
                occurenceTable[i] = bitInput.readInt(); // This read method reads 1 byte and stores it in the correct index in the occurrence table
                byteAmount += occurenceTable[i]; // Creates byteAmount 
            }
        } catch (FileNotFoundException ex) {
            Logger.getLogger(Encode.class.getName()).log(Level.SEVERE, null, ex);
        }
        return occurenceTable;
    }

    // Task 2) Regenerates the huffman Tree  
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
            z.binNodeLeft = (Node) x.getData();
            z.binNodeRight = (Node) y.getData();

            // Adds the new node to the priority queue
            priorityQueue.insert(new Element(sum, z));
        }
        return (Node) priorityQueue.extractMin().getData(); // Returns the root of the tree
    }

    // Task 3) We use the Regenerated huffman tree to decode the bits in the inputfile by traversing down the root until we meet a leaf
    // When a leaf has been found we write its key to the outputFile
    public static void treewalk(Node rootNode, BitInputStream inputStream, FileOutputStream fileoutput) throws IOException {

        int counter = 0; // Keeps track of how many bytes we have written so far so we know when to stop. 
        Node resetBinNode = rootNode; // sets the node to the root.  
        String bitprefix = ""; // used to append the different bits from readbit 
        int readBit;

        while ((counter < byteAmount) && (readBit = inputStream.readBit()) != -1) {
            if (rootNode.binNodeLeft == null && rootNode.binNodeRight == null) {
                System.out.println("BitPrefix: " + bitprefix);
                fileoutput.write(rootNode.key);
                System.out.println("rootNode.key: " + rootNode.key);
                counter++;
                // reset
                bitprefix = "";
                rootNode = resetBinNode;

            }
            // If currentbit = 0 then write 0 to outputfile
            if (readBit == 0) {
                rootNode = rootNode.binNodeLeft;
                bitprefix += readBit;
                // if readBit = 1 then write 1 to outputfile
            } else if (readBit == 1) {
                rootNode = rootNode.binNodeRight;
                bitprefix += readBit;
            }
        }
    }
}
