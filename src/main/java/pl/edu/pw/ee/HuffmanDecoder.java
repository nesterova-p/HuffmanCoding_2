package pl.edu.pw.ee;

import pl.edu.pw.ee.resources.HuffmanNode;

import java.io.*;

public class HuffmanDecoder {
    private HuffmanTree huffmanTree;

    public HuffmanDecoder() {
        this.huffmanTree = new HuffmanTree();
    }

    // Main decompression method
    public void decompress(String inputFilePath, String outputFilePath) throws IOException {
        try (DataInputStream inputStream = new DataInputStream(new FileInputStream(inputFilePath))) {
            // Read tree structure
            String treeStructure = inputStream.readUTF();

            // Read the length of the encoded text
            int encodedLength = inputStream.readInt();

            // Read the encoded text as bytes
            StringBuilder encodedText = new StringBuilder();
            while (inputStream.available() > 0) {
                String byteString = String.format("%8s", Integer.toBinaryString(inputStream.readByte() & 0xFF))
                        .replace(' ', '0');
                encodedText.append(byteString);
            }

            // Trim the encoded text to the correct length
            encodedText.setLength(encodedLength);

            // Rebuild the Huffman tree using HuffmanTree class
            huffmanTree.rebuildTreeFromPreOrder(treeStructure);

            // Decode the text
            String decodedText = decodeText(encodedText.toString());

            // Write to the output file
            writeFile(outputFilePath, decodedText);
        }
    }

    // Decode the encoded text
    private String decodeText(String encodedText) {
        StringBuilder decodedText = new StringBuilder();
        HuffmanNode root = huffmanTree.getRoot();
        HuffmanNode current = root;

        for (char bit : encodedText.toCharArray()) {
            if (bit == '0') {
                current = current.getLeft();
            } else if (bit == '1') {
                current = current.getRight();
            } else {
                throw new IllegalStateException("Unexpected bit in encoded text: " + bit);
            }

            if (current.isLeaf()) {
                decodedText.append(current.getCharacter());  // ASCII character
                current = root;
            }
        }

        return decodedText.toString();
    }

    // Write the decoded text to a file
    private void writeFile(String filePath, String content) throws IOException {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(filePath))) {
            writer.write(content);
        }
    }
}
