package pl.edu.pw.ee;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.File;
import java.io.IOException;

public class GuiApp extends JFrame {
    private JRadioButton compressButton;
    private JRadioButton decompressButton;
    private JTextField inputFileField;
    private JTextField outputFileField;
    private JButton inputBrowseButton;
    private JButton outputBrowseButton;
    private JButton runButton;
    private JFileChooser fileChooser;
    private JLabel statusLabel;
    private JTextArea helpTextArea;

    public GuiApp() {
        setTitle("Huffman Coding Application");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(600, 450);
        setLocationRelativeTo(null);

        initializeComponents();
        layoutComponents();
        addEventHandlers();
    }

    private void initializeComponents() {
        // Mode selection radio buttons
        compressButton = new JRadioButton("Compress");
        decompressButton = new JRadioButton("Decompress");
        compressButton.setSelected(true);

        ButtonGroup modeGroup = new ButtonGroup();
        modeGroup.add(compressButton);
        modeGroup.add(decompressButton);

        // File selection fields and buttons
        inputFileField = new JTextField(25);
        outputFileField = new JTextField(25);

        inputBrowseButton = new JButton("Browse...");
        outputBrowseButton = new JButton("Browse...");

        // Run button
        runButton = new JButton("Run");

        // File chooser
        fileChooser = new JFileChooser();

        // Set the default directory to the "texts" folder in the project
        File defaultDirectory = new File("texts");
        if (!defaultDirectory.exists()) {
            defaultDirectory.mkdirs(); // Create the folder if it doesn't exist
        }
        fileChooser.setCurrentDirectory(defaultDirectory);

        // Status label
        statusLabel = new JLabel(" ");

        // Help text area
        helpTextArea = new JTextArea(5, 40);
        helpTextArea.setEditable(false);
        helpTextArea.setLineWrap(true);
        helpTextArea.setWrapStyleWord(true);
        helpTextArea.setText("Instructions:\n\n" +
                "1. Select the mode:\n" +
                "   - Compress: Choose a text file to compress.\n" +
                "   - Decompress: Choose a Huffman-encoded file to decompress.\n" +
                "2. Use the file pickers to specify:\n" +
                "   - Input File: The file to be processed.\n" +
                "   - Output File: (Optional) The file where results will be saved.\n" +
                "     If left blank during decompression, results will be saved to 'decompressed.txt'.\n" +
                "3. Click 'Run' to execute the selected operation.");

        helpTextArea.setBorder(BorderFactory.createTitledBorder("How to Use the Program"));
    }

    private void layoutComponents() {
        // Panel trybu (Kompresja/Dekompresja)
        JPanel modePanel = new JPanel();
        modePanel.setBorder(BorderFactory.createTitledBorder("Select Mode"));
        modePanel.add(compressButton);
        modePanel.add(decompressButton);

        // Panele wyboru plików
        JPanel inputPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        inputPanel.add(new JLabel("Input File:"));
        inputPanel.add(inputFileField);
        inputPanel.add(inputBrowseButton);

        JPanel outputPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        outputPanel.add(new JLabel("Output File:"));
        outputPanel.add(outputFileField);
        outputPanel.add(outputBrowseButton);

        JPanel filePanel = new JPanel(new GridLayout(2, 1));
        filePanel.setBorder(BorderFactory.createTitledBorder("File Selection"));
        filePanel.add(inputPanel);
        filePanel.add(outputPanel);

        // Panel centralny zawierający filePanel i helpScrollPane
        JPanel centerPanel = new JPanel(new BorderLayout());
        centerPanel.add(filePanel, BorderLayout.NORTH);

        JScrollPane helpScrollPane = new JScrollPane(helpTextArea);
        helpScrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);

        centerPanel.add(helpScrollPane, BorderLayout.CENTER);

        // Panel dolny zawierający runPanel i statusPanel
        JPanel runPanel = new JPanel();
        runPanel.add(runButton);

        JPanel statusPanel = new JPanel(new BorderLayout());
        statusPanel.add(statusLabel, BorderLayout.CENTER);

        JPanel bottomPanel = new JPanel(new BorderLayout());
        bottomPanel.add(runPanel, BorderLayout.NORTH);
        bottomPanel.add(statusPanel, BorderLayout.SOUTH);

        // Dodanie wszystkiego do głównego kontenera
        Container container = getContentPane();
        container.setLayout(new BorderLayout());
        container.add(modePanel, BorderLayout.NORTH);
        container.add(centerPanel, BorderLayout.CENTER);
        container.add(bottomPanel, BorderLayout.SOUTH);
    }


    private void addEventHandlers() {
        // Input file browse button action
        inputBrowseButton.addActionListener(e -> {
            int result;
            if (compressButton.isSelected()) {
                fileChooser.setDialogTitle("Select a text file to compress");
                fileChooser.setAcceptAllFileFilterUsed(true);
                result = fileChooser.showOpenDialog(this);
            } else {
                fileChooser.setDialogTitle("Select a Huffman-encoded file to decompress");
                fileChooser.setAcceptAllFileFilterUsed(true);
                result = fileChooser.showOpenDialog(this);
            }
            if (result == JFileChooser.APPROVE_OPTION) {
                File selectedFile = fileChooser.getSelectedFile();
                inputFileField.setText(selectedFile.getAbsolutePath());
            }
        });

        // Output file browse button action
        outputBrowseButton.addActionListener(e -> {
            fileChooser.setDialogTitle("Select output file");
            fileChooser.setAcceptAllFileFilterUsed(true);
            int result = fileChooser.showSaveDialog(this);
            if (result == JFileChooser.APPROVE_OPTION) {
                File selectedFile = fileChooser.getSelectedFile();
                outputFileField.setText(selectedFile.getAbsolutePath());
            }
        });

        runButton.addActionListener(e -> {
            String inputFilePath = inputFileField.getText().trim();
            String outputFilePath = outputFileField.getText().trim();

            if (inputFilePath.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Please specify an input file.", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            // Ensure the output path points to the "texts" directory
            File outputDirectory = new File("texts");
            if (!outputDirectory.exists()) {
                outputDirectory.mkdirs(); // Create the directory if it doesn't exist
            }

            if (outputFilePath.isEmpty()) {
                // Use default filenames based on the operation
                if (decompressButton.isSelected()) {
                    outputFilePath = new File(outputDirectory, "decompressed.txt").getAbsolutePath();
                } else {
                    outputFilePath = new File(outputDirectory, "compressed.huff").getAbsolutePath();
                }
            } else {
                // Save the user-specified file in the "texts" directory
                outputFilePath = new File(outputDirectory, new File(outputFilePath).getName()).getAbsolutePath();
            }

            outputFileField.setText(outputFilePath);

            if (compressButton.isSelected()) {
                // Perform compression
                HuffmanEncoder encoder = new HuffmanEncoder();
                try {
                    encoder.compress(inputFilePath, outputFilePath);
                    statusLabel.setText("File compressed successfully to " + outputFilePath);
                    JOptionPane.showMessageDialog(this, "File compressed successfully to:\n" + outputFilePath, "Success", JOptionPane.INFORMATION_MESSAGE);
                } catch (IOException ex) {
                    statusLabel.setText("Error during compression: " + ex.getMessage());
                    JOptionPane.showMessageDialog(this, "Error during compression:\n" + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
                }
            } else {
                // Perform decompression
                HuffmanDecoder decoder = new HuffmanDecoder();
                try {
                    decoder.decompress(inputFilePath, outputFilePath);
                    statusLabel.setText("File decompressed successfully to " + outputFilePath);
                    JOptionPane.showMessageDialog(this, "File decompressed successfully to:\n" + outputFilePath, "Success", JOptionPane.INFORMATION_MESSAGE);
                } catch (IOException ex) {
                    statusLabel.setText("Error during decompression: " + ex.getMessage());
                    JOptionPane.showMessageDialog(this, "Error during decompression:\n" + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        });

    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            // Set the look and feel to system default
            try {
                UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
            } catch (Exception ex) {
                ex.printStackTrace();
            }

            GuiApp app = new GuiApp();
            app.setVisible(true);
        });
    }
}
