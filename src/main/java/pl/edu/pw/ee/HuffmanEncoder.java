package pl.edu.pw.ee;

import pl.edu.pw.ee.resources.HuffmanNode;

import java.io.*;
import java.util.*;

public class HuffmanEncoder {
    private HuffmanTree huffmanTree;
    private Map<Character, String> huffmanCodes;

    public HuffmanEncoder() {
        this.huffmanTree = new HuffmanTree();
    }

    // Main compression method
    public void compress(String inputFilePath, String outputFilePath) throws IOException {
        // Read text from the input file
        String text = readFile(inputFilePath);

        // Calculate character frequencies
        Map<Character, Integer> frequencies = calculateFrequencies(text);

        // Build Huffman tree using HuffmanTree class
        huffmanTree.buildHuffmanTree(frequencies);

        // Get Huffman codes from the tree
        huffmanCodes = huffmanTree.getHuffmanCodes();

        // Encode the text
        String encodedText = encodeText(text);

        // Write the encoded text to the output file
        writeToFile(outputFilePath, encodedText);
    }

    // Read the input file
    private String readFile(String filePath) throws IOException {
        StringBuilder content = new StringBuilder();

        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
            int c;
            while ((c = reader.read()) != -1) {
                content.append((char) c);
            }
        }

        return content.toString();
    }

    // Calculate character frequencies
    private Map<Character, Integer> calculateFrequencies(String text) {
        Map<Character, Integer> frequencies = new HashMap<>();
        for (char c : text.toCharArray()) {
            frequencies.put(c, frequencies.getOrDefault(c, 0) + 1);
        }
        return frequencies;
    }

    // Encode the input text using Huffman codes
    private String encodeText(String text) {
        StringBuilder encodedText = new StringBuilder();

        for (char c : text.toCharArray()) {
            String code = huffmanCodes.get(c);
            if (code == null) {
                throw new IllegalStateException("Character does not have a Huffman code: " + c);
            }
            encodedText.append(code);
        }

        return encodedText.toString();
    }

    // Write the encoded text to the output file
    private void writeToFile(String filePath, String encodedText) throws IOException {
        try (DataOutputStream outputStream = new DataOutputStream(new FileOutputStream(filePath))) {
            String treeStructure = huffmanTree.getPreOrderTraversal();
            outputStream.writeUTF(treeStructure);

            // Write the length of the encoded text
            outputStream.writeInt(encodedText.length());

            // Write the encoded text as bytes (ASCII)
            int byteCount = (encodedText.length() + 7) / 8;
            for (int i = 0; i < byteCount; i++) {
                int start = i * 8;
                int end = Math.min(start + 8, encodedText.length());
                String byteString = encodedText.substring(start, end);
                if (byteString.length() < 8) {
                    byteString = String.format("%-8s", byteString).replace(' ', '0');
                }
                byte b = (byte) Integer.parseInt(byteString, 2);
                outputStream.writeByte(b);
            }
        }
    }
}
