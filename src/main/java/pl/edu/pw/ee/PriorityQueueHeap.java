package pl.edu.pw.ee;

import pl.edu.pw.ee.resources.HuffmanNode;

import java.util.ArrayList;
import java.util.List;

public class PriorityQueueHeap {
    private final List<HuffmanNode> heap;

    public PriorityQueueHeap() {
        this.heap = new ArrayList<>();
    }

    public void add(HuffmanNode node) {
        heap.add(node);
        heapifyUp(heap.size() - 1);
    }

    private void heapifyUp(int i) {
        while ( i > 0){
            int parent = (i - 1) / 2; // parent index
            if(heap.get(i).compareTo(heap.get(parent)) >= 0){
                break;
            }
            swap(i, parent);
            i = parent;
        }
    }

    private void swap(int i, int j) {
        HuffmanNode temp = heap.get(i);
        heap.set(i, heap.get(j));
        heap.set(j, temp);
    }

    public HuffmanNode removeMin(){

        if(heap.isEmpty()){
            throw new IllegalStateException("Heap is empty");
        }

        HuffmanNode min = heap.get(0);
        HuffmanNode last = heap.remove(heap.size() - 1);

        if(!heap.isEmpty()){
            heap.set(0, last);
            heapifyDown(0);
        }
        return min;
    }

    private void heapifyDown(int i) {
        int size = heap.size();
        while (true){
            int smallest = i;
            int leftChild = 2 * i + 1;
            int rightChild = 2 * i + 2;

            if (leftChild < size && heap.get(leftChild).compareTo(heap.get(smallest)) < 0) {
                smallest = leftChild;
            }
            if (rightChild < size && heap.get(rightChild).compareTo(heap.get(smallest)) < 0) {
                smallest = rightChild;
            }
            if (smallest == i) {
                break;
            }
            swap(i, smallest);
            i = smallest;
        }
    }

    public boolean isEmpty() {
        return heap.isEmpty();
    }

}
