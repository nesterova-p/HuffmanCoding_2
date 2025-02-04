package pl.edu.pw.ee.zadania;

import pl.edu.pw.ee.HuffmanTree;
import pl.edu.pw.ee.resources.HuffmanNode;

import java.util.HashMap;
import java.util.Map;

public class Zad2 {
    public static void main(String[] args) {
        String inputText = "więc jeszcze seta, znakomicie padniemy, ale zgódźcie się że z tylu różnych dróg przez życie każdy ma prawo wybrać źle.";

        // Calculate character frequencie
        Map<Character, Integer> frequencies = calculateFrequencies(inputText);

        HuffmanTree huffmanTree = new HuffmanTree();
        huffmanTree.buildHuffmanTree(frequencies);

        /*
        // Print the pre-order traversal of the tree structure
        String treeStructure = huffmanTree.getPreOrderTraversal();
        System.out.println("Tree structure (pre-order): " + treeStructure);
         */

        // Calculate the total cost of the tree
        int treeCost = calculateTreeCost(huffmanTree.getRoot(), 0);
        System.out.println("Tree cost = " + treeCost);
    }

    private static Map<Character, Integer> calculateFrequencies(String text) {
        Map<Character, Integer> frequencies = new HashMap<>();
        for (char c : text.toCharArray()) {
            frequencies.put(c, frequencies.getOrDefault(c, 0) + 1);
        }
        return frequencies;
    }

    private static int calculateTreeCost(HuffmanNode node, int depth) {
        if (node == null) {
            return 0;
        }
        if (node.isLeaf()) {
            return depth * node.getFrequency();
        }
        int leftCost = calculateTreeCost(node.getLeft(), depth + 1);
        int rightCost = calculateTreeCost(node.getRight(), depth + 1);
        return leftCost + rightCost;
    }
}
