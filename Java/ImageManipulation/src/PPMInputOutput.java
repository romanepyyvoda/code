import java.io.*;
import java.util.Arrays;
import java.util.Scanner;

/**
 * Handles reading and writing .PPM images from files.
 *
 * YOU SHOULD NOT MODIFY THIS CODE
 *
 * @author amurray
 */
public class PPMInputOutput {

    private static final int MIN_RGB = 0;
    private static final int MAX_RGB = 255;

    // Number of values in an RGB pixel
    private static final int PIXEL_SIZE = 3;

    /**
     * Reads a .PPM file from the hard drive and converts it to a 3D array of pixels.
     * NOTE: This method will NOT WORK WITH FILES THAT CONTAIN COMMENTS
     * @param inputFileName Name of file to read
     * @return Matrix with image data
     */
    public static int[][][] readPPMFile(String inputFileName) {

        Scanner scanner;
        try {
            File inputFile = new File(inputFileName);
            scanner = new Scanner(inputFile);
        } catch (FileNotFoundException e) {
            throw new IllegalArgumentException("No such file file: " + inputFileName);
        }

        int[] dimensions = scanFileHeader(scanner);
        int numRows = dimensions[0];
        int numCols = dimensions[1];

        int[][][] pixelMatrix = new int[numRows][numCols][PIXEL_SIZE];
        for (int i = 0; i < numRows; i++) {
            pixelMatrix[i] = scanRow(scanner, numCols);
        }
        scanner.close();

        return pixelMatrix;
    }

    /**
     * Scans the PPM file and returns the dimensions of the PPM
     * @param scanner Scanner that contains PPM file
     * @return int[2] with numRows, numCols
     */
    private static int[] scanFileHeader(Scanner scanner) {
        int[] dimens = new int[2];

        // Useless header line-- P3
        scanner.nextLine();

        String dimenLine = scanner.nextLine();
        Scanner dimenScanner = new Scanner(dimenLine);
        dimens[1] = dimenScanner.nextInt();
        dimens[0] = dimenScanner.nextInt();

        // Useless header line-- 255
        scanner.nextLine();

        return dimens;
    }

    /**
     * Scans a single row
     * @param rowScanner
     * @param numCols
     * @return
     */
    private static int[][] scanRow(Scanner rowScanner, int numCols) {
        int[][] row = new int[numCols][PIXEL_SIZE];

        int rgbCount = 0;
        int pixelCount = 0;
        while (pixelCount < numCols) {
            int curVal = rowScanner.nextInt();
            row[pixelCount][rgbCount] = curVal;

            rgbCount++;
            if (rgbCount % PIXEL_SIZE == 0) {
                rgbCount = 0;
                pixelCount++;
            }
        }


        return row;
    }

    /**
     * Write an image to a file in .PPM file format.
     * @param outputFileName Name of file to output, including extension.
     * @param outputImage Matrix representing image. Dimensions are height, width, and RGB values
     */
    public static void  writePPMFile(String outputFileName, int[][][] outputImage) {
        validateImage(outputImage);

        BufferedWriter writer;
        try {
            writer = new BufferedWriter(new FileWriter(outputFileName));
        } catch (IOException e) {
            throw new IllegalStateException(String.format("Error while opening file %s: %s", outputFileName, e.getMessage()));
        }


        int numRows = outputImage.length;
        int numCols = outputImage[0].length;

        try {
            writeHeader(writer, numCols, numRows);
        } catch (IOException e) {
            throw new IllegalStateException(String.format("Error while writing header to file %s: %s", outputFileName, e.getMessage()));
        }

        for (int i = 0; i < outputImage.length; i++) {
            int[][] row = outputImage[i];
            try {
                writeRow(writer, row);
            } catch (IOException e) {
                throw new IllegalStateException(String.format("Error while writing row index %d to file %s: %s", i, outputFileName, e.getMessage()));
            }
        }

        try {
            writer.close();
        } catch (IOException e) {
            throw new IllegalStateException(String.format("Error while closing file %s: %s", outputFileName, e.getMessage()));
        }

    }

    /**
     * Writes the standard .PPM file header
     * @param writer Open writer to write to
     * @param width Width of image
     * @param height Height of image
     * @throws IOException
     */
    private static void writeHeader(BufferedWriter writer, int width, int height) throws IOException {
        writer.write(String.format("P3\n%d %d\n255\n", width, height));
    }

    /**
     * Writes a row of a PPM file
     * @param writer writer to write to
     * @param row row to write
     * @throws IOException
     */
    private static void writeRow(BufferedWriter writer, int[][] row) throws IOException {

        StringBuilder builder = new StringBuilder();
        builder.append(imageRowToString(row));
        builder.append("\n");

        writer.write(builder.toString());
    }

    /**
     * Converts an image matrix into an easily readable string format.
     * @param image image matrix containing pixel values.
     * @return a string representation of the image matrix
     */
    public static String imageMatrixToString(int[][][] image) {
        StringBuilder builder = new StringBuilder();

        for (int[][] row : image) {
            builder.append(imageRowToString(row));
            builder.append("\n");
        }

        return builder.toString();
    }

    /**
     * Converts an image row into an easily readable string format.
     * @param row image matrix containing pixel values.
     * @return a string representation of the image matrix
     */
    public static String imageRowToString(int[][] row) {
        StringBuilder builder = new StringBuilder();

        for (int[] pixel : row) {

            for (int curVal : pixel) {
                builder.append(curVal);
                builder.append(" ");
            }
            builder.replace(builder.length() - 1, builder.length(), "");
            builder.append("\t\t");
        }

        return builder.toString();
    }

    /**
     * Sanity checks size and values of PPM image matrix
     * @param image
     */
    private static void validateImage(int[][][] image) {
        if (image == null) {
            throw new IllegalArgumentException("Parameter image must be non-null.");
        }

        int width = -1;

        // Checks for null rows, null pixels, ragged edged matrices, and pixels with three parts
        for (int i = 0; i < image.length; i++) {
            int[][] row = image[i];
            if (row == null) {
                throw new IllegalArgumentException("Parameter image contains null row at index " + i);
            }

            if (width == -1) {
                width = row.length;
            }

            if (width != row.length) {
                throw new IllegalArgumentException(
                        String.format("Row at index %d has length %d, which does not match length of first row %d",
                                i, row.length, width));
            }

            for (int j = 0; j < row.length; j++) {
                int[] pixel = row[j];
                if (pixel == null) {
                    throw new IllegalArgumentException(String.format("Parameter image contains null pixel at index %d,%d", i, j));
                }

                if (pixel.length != PIXEL_SIZE) {
                    throw new IllegalArgumentException(String.format("Pixel %s contains incorrect number of elements ", Arrays.toString(pixel)));
                }

                for (int val : pixel) {
                    if (val < MIN_RGB || val > MAX_RGB) {
                        throw new IllegalArgumentException(
                                String.format("Parameter image contains illegal pixel value %d at index %d,%d", val, i, j));
                    }
                }
            }
        }


    }

}
