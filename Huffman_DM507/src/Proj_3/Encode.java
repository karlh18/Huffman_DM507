package Proj_3;

// Method 1 countFrequency Tested and is succesful
// Method 2 huffManAlgorithm
// Questions: Should it be a tree or a node that it uses to create graph/tree or okay to just go through nodes then set it into a tree?
// Questions: Should I use all those 0 as entries in the left side of the graph ? the tree/graph goes millions time to the left ?
// Questions: Should the new Node "z" at line 94 have anything in it's constructor? -- Set it as 0 or what?
// TODO: Delete the unneeded comments & clear up on the others less illustrative ones
// TODO: Create a Class called Huffman  or simular, since the code is used in both Encode & Decode
// Tests Done - Pretty sure it works as intended  but need revisit 
// Works : Checked if I return the last Node in the Heap()
// Method 3a:  dictBinTree.in_order_walk_with_path()
// Questions: Does it make any difference what I set as the key ?  Isn't it irrelevant ?
// TODO: Clean this method up  - I have ArrayList implementation still int and other weird things. 
// Method 3b:  createCodeLookupTable
// TODO: Clean up the code ; there is many double work in it 
// TODO: Doesn't seem to be any reason to return anything in many of the methods since it sets thing on the encode class 
// Method 4: writeIntsToOutput()
// Not sure if works, but it adds integers into it atleast 
// via encoding bit -- if anything this is the method that needs the most
// TODO:  Check the frequency ones and then check what their code is - because they should be the smallest ones 
// Method 5: writeCodeWordsToOutput()
// TODO: Need to figure out how to use the same streams so don't have to open all again and again
// TODO: Figure out best way to write after written the ints into -- probably just use same stream and thuse write from thereof ... 
// TODO: Something wrong with how I writeBit  
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
    
    
    


    // Stores each byte as an int
    int[] alphabet = new int[256]; //0-255 

    // Used to store the prefix code for each byte
    String[] codeLookupTable;

    // HuffMan Tree
    DictBinTree huffManTree;

//    private String[] code = createCodeLookupTable(huffManTree);  
    
    
    
    // Task 1)  Reads a file and makes a table of how often a given byte occurs in the file    
    public void countFrequencyOfBytes(String inputFile) throws IOException {
        // Used to temporary store the current read()
        int temp;

        // Opens File to read from 
        try ( FileInputStream input = new FileInputStream(inputFile)) {
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

    //Task 2) Use the Huffman Algorithm with the alphabet Table as input ( Use all 256 entries, also those, that do not occur  
    // Creates a HuffmanTree 
    public BinNode huffmanAlgorithm(int[] alphabet) {

        int n = alphabet.length;

        //minHeap
        PQ priorityQueue = new PQHeap();

        // Q = C 
        // Initialize the Priority Queue with 256 items aka the Characters to the queue 
        for (int i = 0; i < n - 1; i++) {

            // Each node represents a character 
            BinNode node = new BinNode(i);

            // Add an Element to the queue:  Frequency as Key in Element & the Tree/Node as data 
            Element e = new Element(alphabet[i], node);
            priorityQueue.insert(e);

        }

        // ########CAN BE DELETE VERY SOOOON###############################
//        int counter = 0; // used for test
        // for i = 1 to n-1 
        // Not entirely sure how it translates from pseudo to our yet. So I will just minus it as usual 
        // I just guess we start @Index 0  and then move to second last index which is length -2 in java ArrayList
        // ################################################################
        // For loop: Takes everything out of the Priority Queue except for the last one
        for (int i = 0; i < n - 2; i++) {

            // Extract the 2 elements with the lowest frequency(key) in the heap
            Element x = priorityQueue.extractMin();
            Element y = priorityQueue.extractMin();

            int sum = x.getKey() + y.getKey(); // sum used as key for a newly created Node, which will be set as parent of the two extracted nodes. 

//             System.out.println("counter: " + counter++ + " sum : " + sum); // Used for test ##############Can be deleted soon
            // Creates new node/tree & adds the two extracted nodes
            BinNode z = new BinNode(sum);   // Not sure if this should have anything  or if it should have 0 ???  Or does it not matter?
            z.binNodeLeft = (BinNode) x.getData();
            z.binNodeRight = (BinNode) y.getData();

            // Adds the new node/tree to the priority queue
            priorityQueue.insert(new Element(sum, z));
        }

        return (BinNode) priorityQueue.extractMin().getData();
    }

    //Task 3 )  Convert the Huffman Tree to a Table(An Array with 256 entries)
    // Over the codes for each of the possible bytes 
    // Remember - The bytes are of the type int between 0 and 255 
    // and can be used as indexes in the arrays
    public String[] createCodeLookupTable(DictBinTree tree) {
        String[] code = tree.in_order_walk_with_path();
        codeLookupTable = code; // Dobbeltarbejde
        return code;
//        System.out.println("Length of ArrayList: " + code.length);

        // Used for test
//        for (int i = 0; i < 255; i++) {
//            
//            System.out.println(i + ": " + code[i]);
//            
//        }
        // make toArray later
//        for (int i = 0; i < code.length - 1; i++) {
//            codeArray[i] = code.get(i);  
//            System.out.println(codeArray[i]);
//        }
    }

    // Remember to change arguments in methods to args[0] for inputfile & args[1]for outputfile
    public static void main(String[] args) throws IOException {
        // #Test1 - CountFrequency()

//        System.out.println("");
        String inputFile = "hej.txt";
        String outputFile = "hej_zippy.txt";
        Encode encode = new Encode();
        encode.countFrequencyOfBytes(inputFile);
//        System.out.println(encode.alphabet[104]);
//        System.out.println(encode.alphabet[107]);

        // #Test2 - huffmanAlgorithm()
        BinNode binNode = encode.huffmanAlgorithm(encode.alphabet);  // works 
        // Uses the created graph  & adds it to the tree
        encode.huffManTree = new DictBinTree();
        encode.huffManTree.root = binNode;

        //Test 3a:
//        String[] dog = encode.huffManTree.in_order_walk_with_path();
        String[] dog = encode.createCodeLookupTable(encode.huffManTree);
//        System.out.println("104: " + dog[104]); 
//        System.out.println("101: " + dog[101]); 
//        System.out.println("106: " + dog[106]); 
//        System.out.println("13: " + dog[13]); 
//        System.out.println("10: " + dog[10]); 
//        System.out.println("200: " + dog[200]); 

        // Test 3b:
//        encode.createCodeLookupTable(encode.huffManTree);
        // Test 4:
        // Tests that need to be conducted:
        // Check to see how many bytes are written 
      //  encode.writeIntsToOutput(encode.alphabet, inputFile);
        
        encode.writeToOutPut(encode.alphabet,inputFile,outputFile);

        //Test 5: 
        // encode.writeCodeWordsToOutput(inputFile, outputFile);
        // TEST OF SAVECODE  
        // encode.saveCode(dictBinTree);
//        System.out.println(alphabet[3]);
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

//    
//    
//    //Task 2) Use the Huffman Algorithm with the Occurrence Table as input ( Use all 256 entries, also those, that do not occur  
//    public DictBinTree huffmanAlgorithm2(int[] alphabet) {
//
//        int n = alphabet.length;
//
//        //minHeap
//        PQ priorityQueue = new PQHeap();
//
//        // Q = C 
//        // Initialize the Priority Queue with the Characters 
//        for (int i = 0; i < n - 1; i++) {
//
//            DictBinTree tree = new DictBinTree();
//
//            // Add element into tree? 
//            tree.insert(i); // Not sure if correct          // Should this be changed ? 
//
//            // Add a tree
//            Element e = new Element(alphabet[i], tree);
//
//            priorityQueue.insert(e);
//
//        }
//
//        // for i = 1 to n-1 
//        // Not entirely sure how it translates from pseudo to our yet. So I will just minus it as usual 
//        // I just guess we start @Index 0  and then move to second last index which is length -2 in java ArrayList
//        for (int i = 0; i < n - 2; i++) {
//
//            // Doesn't this provide us with a tree instead of a node?
//            // Should be tree or node? hmmm
//            // Extract the elements with the lowest key in the heap
//            // and get their sum
//            Element x = priorityQueue.extractMin();
//            Element y = priorityQueue.extractMin();
//            int sum = x.getKey() + y.getKey();
//
//            // Used later to get the nodes left 
//            DictBinTree xx = (DictBinTree) x.getData();
//            DictBinTree yy = (DictBinTree) y.getData();
//
//            // Creates new tree & adds the two previous as children
//            DictBinTree z = new DictBinTree();
//            z.root.binNodeLeft = xx.root;
//            z.root.binNodeRight = yy.root;
//
//            // Adds the new tree to the priority queue
//            priorityQueue.insert(new Element(sum, z));
//        }
//
//        return (DictBinTree) priorityQueue.extractMin().getData();
//
//        // Might need change it to Node
//    }
//    
}
