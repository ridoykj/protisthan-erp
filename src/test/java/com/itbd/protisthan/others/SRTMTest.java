package com.itbd.protisthan.others;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;

import java.io.*;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;

@Slf4j
public class SRTMTest {
    // Part 1: Write 2D integer array to .hgt file
    public static void writeHGTFile(String filePath, int[][] data) throws IOException {
        try (FileOutputStream fos = new FileOutputStream(filePath);
             DataOutputStream dos = new DataOutputStream(fos)) {

            for (int i = 0; i < data.length; i++) {
                for (int j = 0; j < data[i].length; j++) {
                    // Write as big-endian signed 2-byte integer
                    dos.writeShort(data[i][j]);
                }
            }
        }
    }

    // Part 2: Read .hgt file and load into a 2D integer array
    public static int[][] readHGTFile(String filePath, int rows, int cols) throws IOException {
        int[][] data = new int[rows][cols];

        try (FileInputStream fis = new FileInputStream(filePath);
             DataInputStream dis = new DataInputStream(fis)) {

            for (int i = 0; i < rows; i++) {
                for (int j = 0; j < cols; j++) {
                    // Read as big-endian signed 2-byte integer
                    data[i][j] = dis.readShort();
                }
            }
        }

        return data;
    }

    public static int[][] readFromBinaryFile(String inputFilePath, int rows, int cols) throws IOException {
        try (FileInputStream fis = new FileInputStream(inputFilePath)) {
            byte[] bytes = new byte[rows * cols * 2];
            fis.read(bytes);

            ByteBuffer buffer = ByteBuffer.wrap(bytes);
            buffer.order(ByteOrder.BIG_ENDIAN);

            int[][] data = new int[rows][cols];
            for (int i = 0; i < rows; i++) {
                for (int j = 0; j < cols; j++) {
                    data[i][j] = buffer.getShort();
                }
            }
            return data;
        }
    }

    public static void writeToBinaryFile(int[][] data, String outputFilePath) throws IOException {
        try (FileOutputStream fos = new FileOutputStream(outputFilePath)) {
            ByteBuffer buffer = ByteBuffer.allocate(data.length * data[0].length * 2);
            buffer.order(ByteOrder.BIG_ENDIAN);

            for (int i = 0; i < data.length; i++) {
                for (int j = 0; j < data[0].length; j++) {
                    buffer.putShort((short) data[i][j]);
                }
            }

            fos.write(buffer.array());
        }
    }

    public static int[][] generateArrayWithSquare(int rows, int cols, int squareSize) {
        int[][] array = new int[rows][cols];

        // Calculate the top-left corner of the square to center it in the array
        int startRow = (rows - squareSize) / 2;
        int startCol = (cols - squareSize) / 2;
        int endRow = startRow + squareSize;
        int endCol = startCol + squareSize;

        // Draw the top and bottom boundary of the square
        for (int i = startCol; i < endCol; i++) {
            array[startRow][i] = 1;  // Top boundary
            array[endRow - 1][i] = 1; // Bottom boundary
        }

        // Draw the left and right boundary of the square
        for (int i = startRow; i < endRow; i++) {
            array[i][startCol] = 1;  // Left boundary
            array[i][endCol - 1] = 1;  // Right boundary
        }

        return array;
    }


    @Test
    public void runWriteTest() throws IOException {
//        int[][] data = new int[3601][3601]; // Modify based on actual data
//
//        // Fill the array with sample data (for testing)
//        for (int i = 0; i < 3601; i++) {
//            for (int j = 0; j < 3601; j++) {
//                data[i][j] = i - j; // Example data
//            }
//        }
        // Write the data to a .hgt file
//            writeHGTFile("sample.hgt", data);


        // Part 1: Write to binary file
        int[][] data = {{1, 2, 3}, {4, 5, 6}, {7, 8, 9}};
//        int[][] data = generateArrayWithSquare(1200, 1200, 1201);
        String outputFilePath = "output.hgt";
        writeToBinaryFile(data, outputFilePath);
    }

    @Test
    public void runReadTest() {
        try {
            // Read the data back from the .hgt file
//            int[][] readData = readHGTFile("sample.hgt", 3601, 3601);
//            int[][] readData = readHGTFile("J:\\Git\\erp\\protisthan-erp\\S43E146.hgt", 1, 0);
//            int[][] readData = readHGTFile("J:\\Git\\erp\\protisthan-erp\\S43E146.hgt", 1201, 1201);
//            int[][] readData = readHGTFile("J:\\Git\\erp\\protisthan-erp\\S43E146.hgt", 3601, 3601);
            int[][] readData = readFromBinaryFile("J:\\Git\\erp\\protisthan-erp\\S43E146.hgt", 3601, 3601);
//            int[][] readData = readFromBinaryFile("J:\\Git\\erp\\protisthan-erp\\S43E146.hgt", 1409000, 1409000);

            // Print some values to verify
            System.out.println("Sample data read from the file:");
            System.out.println(readData[0][0]);
            System.out.println(readData[0][3]);
            System.out.println(readData[1200][1200]);
            System.out.println(readData[3600][3600]);

        } catch (IOException e) {
            log.error("Got on", e);
        }
    }
}
