# Huffman Encoder and Decoder with GUI

## **Table of Contents**
1. [Features](#features)  
2. [Usage](#usage)  
   - [Build and Run](#build-and-run)  
3. [Implementation Details](#implementation-details)  
   - [Huffman Tree Construction](#huffman-tree-construction)  
   - [Compression Workflow](#compression-workflow)  
   - [Decompression Workflow](#decompression-workflow)  
   - [Graphical User Interface (GUI)](#graphical-user-interface-gui)  
   - [Custom Priority Queue](#custom-priority-queue)  
   - [Tests](#tests)  
4. [GUI Instructions](#gui-instructions)  
5. [Command-Line Arguments (Optional)](#command-line-arguments-optional)  
6. [Example](#example)  
   - [Using the GUI](#using-the-gui)  
   - [Using the Command Line](#using-the-command-line)  
7. [Project Structure](#project-structure)  
8. [Requirements](#requirements)  
9. [To Do](#to-do)  
10. [License](#license)  

---

## **Features**

1. **Huffman Tree Construction:**
   - Builds a tree using a custom priority queue implemented with a non-recursive heap.

2. **Compression:**
   - Reads a file and generates a compressed version using Huffman coding.
   - Stores the Huffman tree structure (pre-order traversal) alongside the compressed data.

3. **Decompression:**
   - Reconstructs the Huffman tree from the pre-order traversal.
   - Decodes the compressed file back to the original text.

4. **Graphical User Interface (GUI):**
   - Allows users to select input and output files through a simple file picker.
   - Automatically saves results to `decompressed.txt` if no output file is specified.

5. **Command-Line Support:**
   - Retains CLI functionality for advanced users.

6. **Unit Testing:**
   - Includes comprehensive unit tests to validate tree construction, encoding, decoding, and priority queue operations.

---

## **Usage**

### **Build and Run**
1. Clone the repository:
   ```bash
   git clone https://github.com/yourusername/huffman-coding.git
   cd huffman-coding
   ```

2. Compile the project:
   ```bash
   javac -d out -sourcepath src src/pl/edu/pw/ee/Main.java
   ```

3. Package into a JAR file:
   ```bash
   jar cvfe huffman-coding.jar pl.edu.pw.ee.Main -C out .
   ```

4. Run the program with GUI:
   ```bash
   java -jar huffman-coding.jar
   ```

---

## **Implementation Details**

### **Huffman Tree Construction**
- Uses a custom priority queue implemented with a non-recursive heap.
- No external abstract data types (ADTs) are used, except `List`.

### **Compression Workflow**
- Reads the input file and calculates character frequencies.
- Constructs the Huffman tree.
- Generates a pre-order traversal string and compresses the input text using Huffman codes.

### **Decompression Workflow**
- Rebuilds the Huffman tree from the pre-order traversal string.
- Decodes the binary data back to the original text.

### **Graphical User Interface (GUI)**
- Built with Java Swing for simplicity.
- Provides file pickers for selecting input and output files.
- Automatically saves to `decompressed.txt` if no output file is chosen.

### **Custom Priority Queue**
- Built from scratch, backed by a heap with non-recursive operations (`insert`, `deleteMin`, etc.).

### **Tests**
- Ensures correctness of:
  - Huffman tree construction.
  - Encoding and decoding processes.
  - Priority queue operations.

---

## **GUI Instructions**

1. Launch the program:
   ```bash
   java -jar huffman-coding.jar
   ```

2. Select the mode:
   - **Compress**: Choose a text file to compress.
   - **Decompress**: Choose a Huffman-encoded file to decompress.

3. Use the file pickers to specify:
   - **Input File**: The file to be processed.
   - **Output File**: (Optional) The file where results will be saved.
     - If left blank, decompressed results will be saved to `decompressed.txt`.

4. Click "Run" to execute the selected operation.

---

## **Command-Line Arguments (Optional)**

For advanced users, the CLI functionality is still supported:

- `<input_file_path>`: Path to the input file.
- `<output_file_path>`: Path to the output file.
- `--mode`: Specify the mode of operation:
  - `compress`: Compress the input file.
  - `decompress`: Decompress the input file.

Example:
```bash
java -jar huffman-coding.jar input.txt compressed.huff --mode compress
java -jar huffman-coding.jar compressed.huff output.txt --mode decompress
```

---

## **Example**

### **Using the GUI**
1. Launch the program:
   ```bash
   java -jar huffman-coding.jar
   ```

2. Select your input file and specify the output file (or leave it blank for the default).

3. Click "Run" to compress or decompress the file.

### **Using the Command Line**
```bash
java -jar huffman-coding.jar input.txt compressed.huff --mode compress
java -jar huffman-coding.jar compressed.huff output.txt --mode decompress
```

---

## **Project Structure**

```
src/
├── pl/edu/pw/ee/
│   ├── HuffmanEncoder.java      # Handles compression
│   ├── HuffmanDecoder.java      # Handles decompression
│   ├── PriorityQueue.java       # Custom priority queue implementation
│   ├── Main.java                # Entry point for GUI and CLI
│   ├── GuiApp.java              # GUI implementation
│   └── resources/
│       └── HuffmanNode.java     # Represents nodes in the Huffman tree
tests/
├── pl/edu/pw/ee/
│   ├── HuffmanEncoderTest.java  # Unit tests for encoder
│   ├── HuffmanDecoderTest.java  # Unit tests for decoder
│   └── PriorityQueueTest.java   # Unit tests for priority queue
```

---

## **Requirements**

- Java 8 or later.

---

## **To Do**

- [ ] Enhance GUI styling.
- [ ] Add real-time progress updates for large files.
- [ ] Improve test coverage for edge cases.

---

## **License**

This project is licensed under the MIT License. See the [LICENSE](LICENSE) file for details. 

Feel free to contribute! 😊
