import java.util.ArrayList;
import java.util.Collections;

/**
 * @author Karl Amadeus Finsson Hansen - karlh18@student.sdu.dk
 * @author Pradeep Thayaparan - prtha18@student.sdu.dk
 * @author Lavan Sathiyaseelan - lasat17@student.sdu.dk
 */
public class PQHeap implements PQ {

    // Stores the heap 
    ArrayList<Element> heap;

    // Initializes an empty heap
    public PQHeap() {
        heap = new ArrayList<>();
    }

    // Checks the parent 
    public int parent(int i) {

        return (i - 1) / 2;

    }

    // Checks the left child
    public int left(int i) {

        return (i * 2) + 1;

    }

    // Checks the right child
    public int right(int i) {

        return (i * 2) + 2;

    }

    // Makes it into a Min Heap
    public void minHeapify(ArrayList<Element> a, int i) {

        // Used to store smallest variable found in one of the three nodes (Parent, left child, right child) 	
        int smallest;

        // Representation of left & right child 
        int l = left(i);
        int r = right(i);

        // Checks if whether the current node (i)  is bigger or smaller than it's left child 
        // and assigns smallest variable accordingly 
        if (l <= a.size() - 1 && a.get(l).getKey() < a.get(i).getKey()) {
            smallest = l;
        } else {
            smallest = i;

        }

        // Checks right side is greater than the variable checked before 
        // it basically looks if right side is bigger than parent / left side 
        if (r <= a.size() - 1 && a.get(r).getKey() < a.get(smallest).getKey()) {

            smallest = r;

        }

        // If i (current node) is not the smallest we make a 
        // recursive call to minHeapify() 
        // the recursive call ends when it finally is a min heap again
        if (smallest != i) {
            // Swaps the current node with the smallest 
            Collections.swap(a, i, smallest);
            minHeapify(a, smallest);

        }
    }

    // Returns the element with the min
    @Override
    public Element extractMin() {

        // Gets the min
        // and at the end of the method returns it 
        Element min = heap.get(0);

        // Sets the index from the last index on the array to the first
        heap.set(0, heap.get(heap.size() - 1));

        // Remove the last index 
        heap.remove(heap.size() - 1);
        // makes it a minheap again 
        minHeapify(heap, 0);

        // returns the min 
        return min;

    }

    // Adds an element into the heap
    // Basically adds an element to the end of the heap
    // and then starts comparing it to the parent and swaps it until it on its rightful place
    @Override
    public void insert(Element e) {

        // Adds an element to the end of the heap 
        heap.add(e);

        // sets i to be the last index     
        int i = heap.size() - 1;

        // Keeps comparing the parent and i's current position until i is root 
        while (i > 0 && heap.get(parent(i)).getKey() > heap.get(i).getKey()) {

            // Swaps parent and child IF parent is bigger than child
            Collections.swap(heap, i, parent(i));

            // Sets i to the parent  
            // So it can keep comparing all the way up to the root if necessary
            i = parent(i);

        }

    }
}
