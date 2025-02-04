package pl.edu.pw.ee;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import pl.edu.pw.ee.resources.HuffmanNode;
import java.io.*;
import java.nio.file.Files;
import java.util.Map;
import java.util.HashMap;

public class HuffmanTest {

    // Testy dla HuffmanNode
    @Test
    public void testLeafNodeCreation() {
        HuffmanNode node = new HuffmanNode('a', 5);
        assertEquals('a', node.getCharacter());
        assertEquals(5, node.getFrequency());
        assertNull(node.getLeft());
        assertNull(node.getRight());
        assertTrue(node.isLeaf());
    }

    @Test
    public void testInternalNodeCreation() {
        HuffmanNode leftChild = new HuffmanNode('b', 3);
        HuffmanNode rightChild = new HuffmanNode('c', 2);
        HuffmanNode parentNode = new HuffmanNode(5, leftChild, rightChild);

        assertEquals('\0', parentNode.getCharacter());
        assertEquals(5, parentNode.getFrequency());
        assertEquals(leftChild, parentNode.getLeft());
        assertEquals(rightChild, parentNode.getRight());
        assertFalse(parentNode.isLeaf());
    }

    @Test
    public void testCompareTo() {
        HuffmanNode node1 = new HuffmanNode('a', 5);
        HuffmanNode node2 = new HuffmanNode('b', 3);
        HuffmanNode node3 = new HuffmanNode('c', 5);

        assertTrue(node1.compareTo(node2) > 0);
        assertTrue(node2.compareTo(node1) < 0);
        assertEquals(0, node1.compareTo(node3));
    }

    @Test
    public void testIsLeaf() {
        HuffmanNode leafNode = new HuffmanNode('a', 1);
        assertTrue(leafNode.isLeaf());

        HuffmanNode internalNode = new HuffmanNode(2, new HuffmanNode('b', 1), new HuffmanNode('c', 1));
        assertFalse(internalNode.isLeaf());
    }

    // Testy dla PriorityQueueHeap
    @Test
    public void testAddAndRemoveMin() {
        PriorityQueueHeap heap = new PriorityQueueHeap();
        heap.add(new HuffmanNode('a', 5));
        heap.add(new HuffmanNode('b', 3));
        heap.add(new HuffmanNode('c', 7));

        HuffmanNode minNode = heap.removeMin();
        assertEquals('b', minNode.getCharacter());
        assertEquals(3, minNode.getFrequency());

        minNode = heap.removeMin();
        assertEquals('a', minNode.getCharacter());
        assertEquals(5, minNode.getFrequency());

        minNode = heap.removeMin();
        assertEquals('c', minNode.getCharacter());
        assertEquals(7, minNode.getFrequency());

        assertTrue(heap.isEmpty());
    }

    @Test
    public void testIsEmpty() {
        PriorityQueueHeap heap = new PriorityQueueHeap();
        assertTrue(heap.isEmpty());

        heap.add(new HuffmanNode('a', 1));
        assertFalse(heap.isEmpty());
    }

    @Test
    public void testRemoveMinFromEmptyHeap() {
        PriorityQueueHeap heap = new PriorityQueueHeap();
        assertThrows(IllegalStateException.class, heap::removeMin);
    }

    @Test
    public void testAddNodesWithSameFrequency() {
        PriorityQueueHeap heap = new PriorityQueueHeap();
        heap.add(new HuffmanNode('a', 2));
        heap.add(new HuffmanNode('b', 2));
        heap.add(new HuffmanNode('c', 2));

        assertEquals(3, heap.size());

        heap.removeMin();
        heap.removeMin();
        heap.removeMin();

        assertTrue(heap.isEmpty());
    }

    // Testy dla HuffmanTree
    @Test
    public void testBuildHuffmanTree() {
        Map<Character, Integer> frequencyMap = new HashMap<>();
        frequencyMap.put('a', 5);
        frequencyMap.put('b', 9);
        frequencyMap.put('c', 12);
        frequencyMap.put('d', 13);
        frequencyMap.put('e', 16);
        frequencyMap.put('f', 45);

        HuffmanTree huffmanTree = new HuffmanTree();
        huffmanTree.buildHuffmanTree(frequencyMap);

        HuffmanNode root = huffmanTree.getRoot();
        assertNotNull(root);
        assertEquals(100, root.getFrequency());

        Map<Character, String> codes = huffmanTree.getHuffmanCodes();
        assertEquals(6, codes.size());
    }

    @Test
    public void testGetPreOrderTraversal() {
        Map<Character, Integer> frequencyMap = new HashMap<>();
        frequencyMap.put('a', 1);
        frequencyMap.put('b', 1);

        HuffmanTree huffmanTree = new HuffmanTree();
        huffmanTree.buildHuffmanTree(frequencyMap);

        String preOrder = huffmanTree.getPreOrderTraversal();
        assertEquals("01a1b", preOrder);
    }

    @Test
    public void testRebuildTreeFromPreOrder() {
        String preOrder = "01a1b";
        HuffmanTree huffmanTree = new HuffmanTree();
        huffmanTree.rebuildTreeFromPreOrder(preOrder);

        HuffmanNode root = huffmanTree.getRoot();
        assertNotNull(root);
        assertFalse(root.isLeaf());

        HuffmanNode left = root.getLeft();
        HuffmanNode right = root.getRight();

        assertEquals('a', left.getCharacter());
        assertEquals('b', right.getCharacter());
    }

    // Testy dla HuffmanEncoderDecoder
    @Test
    public void testCompressAndDecompress() throws IOException {
        String originalText = "this is an example for huffman encoding";
        File inputFile = File.createTempFile("input", ".txt");
        File compressedFile = File.createTempFile("compressed", ".huff");
        File outputFile = File.createTempFile("output", ".txt");

        try {
            Files.write(inputFile.toPath(), originalText.getBytes());

            HuffmanEncoder encoder = new HuffmanEncoder();
            encoder.compress(inputFile.getAbsolutePath(), compressedFile.getAbsolutePath());

            HuffmanDecoder decoder = new HuffmanDecoder();
            decoder.decompress(compressedFile.getAbsolutePath(), outputFile.getAbsolutePath());

            String decodedText = new String(Files.readAllBytes(outputFile.toPath()));

            assertEquals(originalText, decodedText);
        } finally {
            inputFile.delete();
            compressedFile.delete();
            outputFile.delete();
        }
    }

    @Test
    public void testCompressEmptyFile() throws IOException {
        File inputFile = File.createTempFile("empty", ".txt");
        File compressedFile = File.createTempFile("compressed", ".huff");
        File outputFile = File.createTempFile("output", ".txt");

        try {
            Files.write(inputFile.toPath(), new byte[0]);

            HuffmanEncoder encoder = new HuffmanEncoder();
            encoder.compress(inputFile.getAbsolutePath(), compressedFile.getAbsolutePath());

            HuffmanDecoder decoder = new HuffmanDecoder();
            decoder.decompress(compressedFile.getAbsolutePath(), outputFile.getAbsolutePath());

            String decodedText = new String(Files.readAllBytes(outputFile.toPath()));

            assertEquals("", decodedText);
        } finally {
            inputFile.delete();
            compressedFile.delete();
            outputFile.delete();
        }
    }

    @Test
    public void testCompressAndDecompressWithSpecialCharacters() throws IOException {
        String originalText = "Łódź to piękne miasto w Polsce! 😊";
        File inputFile = File.createTempFile("input", ".txt");
        File compressedFile = File.createTempFile("compressed", ".huff");
        File outputFile = File.createTempFile("output", ".txt");

        try {
            Files.write(inputFile.toPath(), originalText.getBytes());

            HuffmanEncoder encoder = new HuffmanEncoder();
            encoder.compress(inputFile.getAbsolutePath(), compressedFile.getAbsolutePath());

            HuffmanDecoder decoder = new HuffmanDecoder();
            decoder.decompress(compressedFile.getAbsolutePath(), outputFile.getAbsolutePath());

            String decodedText = new String(Files.readAllBytes(outputFile.toPath()));

            assertEquals(originalText, decodedText);
        } finally {
            inputFile.delete();
            compressedFile.delete();
            outputFile.delete();
        }
    }
}
