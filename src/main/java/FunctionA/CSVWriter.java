package FunctionA;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class CSVWriter {

    public static void writeMatrixToCsv(int[][] matrix, String fileName) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(new File(fileName)))) {

            for (int[] row : matrix) {
                for (int i = 0; i < row.length; i++) {
                    writer.write(String.valueOf(row[i]));
                    if (i < row.length - 1) {
                        writer.write(",");
                    }
                }
                writer.newLine();
            }

            System.out.println("\nCSV file written successfully.");

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}