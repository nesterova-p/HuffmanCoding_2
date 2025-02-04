package pl.edu.pw.ee;

import pl.edu.pw.ee.resources.HuffmanNode;

import java.util.HashMap;
import java.util.Map;

public class HuffmanTree {
    private HuffmanNode root;
    private final Map<Character, String> huffmanCodes; // mapping characters to binary codes

    public HuffmanTree() {
        this.huffmanCodes = new HashMap<>();
        this.root = null;
    }

    public HuffmanNode getRoot() {
        return root;
    }

    public void setRoot(HuffmanNode root) {
        this.root = root;
    }

    public void buildHuffmanTree(Map<Character, Integer> frequencyMap) {
        PriorityQueueHeap priorityQueue = new PriorityQueueHeap();

        for (Map.Entry<Character, Integer> entry: frequencyMap.entrySet()) {
            priorityQueue.add(new HuffmanNode(entry.getKey(), entry.getValue()));
        }

        while (priorityQueue.isEmpty() == false) {
            HuffmanNode left = priorityQueue.removeMin();
            HuffmanNode right = priorityQueue.isEmpty() ? null : priorityQueue.removeMin();

            if (right == null) {
                this.root = left;
                break;
            }

            HuffmanNode parent = new HuffmanNode(left.getFrequency() + right.getFrequency(), left, right);
            priorityQueue.add(parent);
        }


        generateCodes(this.root, "");
    }


    private void generateCodes(HuffmanNode node, String code) {
        if (node == null) {
            return;
        }

        if (node.isLeaf()) {
            huffmanCodes.put(node.getCharacter(), code);
        } else {
            generateCodes(node.getLeft(), code + "0");
            generateCodes(node.getRight(), code + "1");
        }
    }

    public Map<Character, String> getHuffmanCodes() {
        return huffmanCodes;
    }

    public String getPreOrderTraversal() {
        StringBuilder preOrder = new StringBuilder();
        traversePreOrder(root, preOrder);
        return preOrder.toString();
    }

    private void traversePreOrder(HuffmanNode node, StringBuilder preOrder) {
        if (node == null) {
            return;
        }

        // Inner node: 0, leaf: 1 + sign
        if (node.isLeaf()) {
            preOrder.append("1").append(node.getCharacter());
        } else {
            preOrder.append("0");
        }

        traversePreOrder(node.getLeft(), preOrder);
        traversePreOrder(node.getRight(), preOrder);
    }

    public void rebuildTreeFromPreOrder(String preOrderTraversal) {
        int[] index = {0};  // Use an array to keep track of the current index during recursion
        this.root = rebuildTreeFromPreOrderHelper(preOrderTraversal, index);
    }

    private HuffmanNode rebuildTreeFromPreOrderHelper(String preOrderTraversal, int[] index) {
        if (index[0] >= preOrderTraversal.length()) {
            return null;
        }

        char current = preOrderTraversal.charAt(index[0]);
        index[0]++;

        // If it's a leaf node
        if (current == '1') {
            char character = preOrderTraversal.charAt(index[0]);
            index[0]++;
            return new HuffmanNode(character, 0);  // Frequency will be 0 since we're just rebuilding the tree
        }
        // If it's an internal node
        else if (current == '0') {
            HuffmanNode left = rebuildTreeFromPreOrderHelper(preOrderTraversal, index);
            HuffmanNode right = rebuildTreeFromPreOrderHelper(preOrderTraversal, index);
            return new HuffmanNode(0, left, right);  // Frequency is 0 for internal nodes
        }

        return null;  // This should not happen
    }

/*
    public static void main(String[] args) {
        Map<Character, Integer> frequencyMap = new HashMap<>();
        frequencyMap.put('a', 5);
        frequencyMap.put('b', 9);
        frequencyMap.put('c', 12);
        frequencyMap.put('d', 13);
        frequencyMap.put('e', 16);
        frequencyMap.put('f', 45);

        HuffmanTree huffmanTree = new HuffmanTree();
        huffmanTree.buildHuffmanTree(frequencyMap);

        System.out.println("Huffman Codes: " + huffmanTree.getHuffmanCodes());
        System.out.println("Pre-order Traversal: " + huffmanTree.getPreOrderTraversal());
    }

*/
}


