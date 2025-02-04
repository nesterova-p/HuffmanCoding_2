package pl.edu.pw.ee.resources;

public class HuffmanNode implements Comparable<HuffmanNode> {
    private char character;
    private int frequency;
    private HuffmanNode left;
    private HuffmanNode right;

    // Constructor for leaf nodes
    public HuffmanNode(char character, int frequency) {
        this.character = character;
        this.frequency = frequency;
        this.left = null;
        this.right = null;
    }

    // Constructor for internal nodes
    public HuffmanNode(int frequency, HuffmanNode left, HuffmanNode right) {
        this.character = '\0'; // Use null character or any placeholder
        this.frequency = frequency;
        this.left = left;
        this.right = right;
    }

    // Getters
    public char getCharacter() {
        return character;
    }

    public int getFrequency() {
        return frequency;
    }

    public HuffmanNode getLeft() {
        return left;
    }

    public HuffmanNode getRight() {
        return right;
    }

    // Check if the node is a leaf
    public boolean isLeaf() {
        return (left == null && right == null);
    }

    // Implement the compareTo method
    @Override
    public int compareTo(HuffmanNode other) {
        return Integer.compare(this.frequency, other.frequency);
    }
}
