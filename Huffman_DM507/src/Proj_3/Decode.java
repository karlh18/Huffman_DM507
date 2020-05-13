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

    static int[] alphabet;
    static int byteAmount = 0;

    public static void main(String[] args) throws IOException {

        // Open input and output byte streams to/from files.
//        FileInputStream inFile = new FileInputStream(args[0]);
//        FileOutputStream outFile = new FileOutputStream(args[1]);
      
        FileInputStream inFile = new FileInputStream("same2.txt");
        FileOutputStream outFile = new FileOutputStream("same3.txt");
      
        // Wrap the new bit streams around the input stream.
        BitInputStream in = new BitInputStream(inFile);

        // Reads the occurencetable from the inputfile & stores them in an integer array
        alphabet = readOccurenceTable(in);

        //Regenerates the Huffman-Tree from the occurencetable
        Node huffmanNodes = huffmanAlgorithm(alphabet);

        // converts the codewords to regular bytes to be written to the outputfile
        treewalk(huffmanNodes, in, outFile);

        // Closes the streams. 
        in.close();
        inFile.close();
        outFile.close();

//        System.out.println("The file " + args[0] + " has been successfully Decoded into the file " + args[1]);
    }

    // Task 1)  reads  the Occurence table(Hyppighedstabellen) from the inputfile for the 256 bytes. & Calculates the number of bytes read
    public static int[] readOccurenceTable(BitInputStream bitInput) throws IOException {

        int[] occurenceTable = new int[256]; // length of the array is 256 since that's how many different bytes/characters we can write 

        try {
            for (int i = 0; i < occurenceTable.length; i++) {
                occurenceTable[i] = bitInput.readInt(); // This read method reads 1 byte and stores it in the correct index in the occurrence table
                byteAmount += occurenceTable[i]; // Keep track of total amount of bytes in the file 
            }
        } catch (FileNotFoundException ex) {
            System.out.println("FileNotFound");
        }
        return occurenceTable;
    }

    // Task 2) Regenerates the huffman Tree  
    public static Node huffmanAlgorithm(int[] alphabet) {

        int n = alphabet.length; // length of the alphabet

        //minHeap
        PQ priorityQueue = new PQHeap();

        // Q = C : Initialize the Priority Queue with alphabet aka the occurenceTable
        for (int i = 0; i < n; i++) {

            // Each leaf-node represents a character(byte)
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

    // Task 3) We use the Regenerated huffman tree to decode the bits in the inputfile by traversing down the root until we meet a leaf
    // When a leaf has been found we write its key to the outputFile
    public static void treewalk(Node rootNode, BitInputStream inputStream, FileOutputStream fileoutput) throws IOException {
        int counter = 0; // Keeps track of how many bytes we have written so far so we know when to stop. 
        Node resetBinNode = rootNode; // sets the node to the root. 
        int readBit;  // initialized to get over the first loop

        do {
            readBit = inputStream.readBit();
            // if the leaf is found then write its key(which is the byte) to the output file and increment the counter to keep track of bytes written   
            if (rootNode.nodeLeft == null && rootNode.nodeRight == null) {

                fileoutput.write(rootNode.key);

                counter++;
                // resets the Node to the rootNode
                rootNode = resetBinNode;

            }
            // If currentbit = 0 then write 0 to outputfile
            if (readBit == 0) {
                rootNode = rootNode.nodeLeft;
                // if readBit = 1 then write 1 to outputfile
            } else if (readBit == 1) {
                rootNode = rootNode.nodeRight;
            }
        } while ((counter < byteAmount) && (readBit != -1));
        // 
        System.out.println("Counter is : " + counter);

    }
}
