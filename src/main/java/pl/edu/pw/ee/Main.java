package pl.edu.pw.ee;

import java.io.IOException;

public class Main {
    public static void main(String[] args) {
        // Sprawdzenie liczby argumentów
        if (args.length < 2) {
            System.out.println("Użycie: java Main <ścieżka do pliku wejściowego> <ścieżka do pliku wynikowego>");
            return;
        }

        // Ścieżki do plików wejściowego i wynikowego
        String inputFilePath = args[0];
        String outputFilePath = args[1];

        // Tworzenie obiektów kodera i dekodera Huffmana
        HuffmanEncoder encoder = new HuffmanEncoder();
        HuffmanDecoder decoder = new HuffmanDecoder();

        // Kompresja (kodowanie pliku wejściowego)
        try {
            // Kompresja
            encoder.compress(inputFilePath, "encodedFile.dat");
            System.out.println("Plik skompresowany i zapisany jako: encodedFile.dat");

            // Dekompresja (dekodowanie do pliku wynikowego)
            decoder.decompress("encodedFile.dat", outputFilePath);
            System.out.println("Plik dekompresowany i zapisany jako: " + outputFilePath);
        } catch (IOException e) {
            System.err.println("Błąd podczas kompresji/dekompresji: " + e.getMessage());
        }
    }
}