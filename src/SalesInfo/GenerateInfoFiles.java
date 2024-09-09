package SalesInfo;

import Utils.FileUtils;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * This class generates information files for sellers, products, and sales. It creates
 * necessary directories, generates random data, and writes the data to corresponding files.
 */
public class GenerateInfoFiles {

    private static final String MAIN_FOLDER = "SoldProducts";
    private static final String SALES_FOLDER = "Sales";
    private static final String SELLERS_FOLDER = "Sellers";
    private static final String PRODUCTS_FOLDER = "Products";
    private static final int NUM_SELLERS = 5;
    private static final int NUM_PRODUCTS = 5;

    /**
     * Main method that orchestrates the creation of folders and the generation of sellers, sales, and products files.
     *
     * @param args the command line arguments (not used).
     */
    public static void main(String[] args) {
        File mainFolder = new File(MAIN_FOLDER);
        File salesFolder = new File(mainFolder, SALES_FOLDER);
        File sellersFolder = new File(mainFolder, SELLERS_FOLDER);
        File productsFolder = new File(mainFolder, PRODUCTS_FOLDER);

        // Create folders if they do not exist
        FileUtils.createFolder(salesFolder);
        FileUtils.createFolder(sellersFolder);
        FileUtils.createFolder(productsFolder);

        // Create a map to store document numbers
        Map<String, String> documentNumbers = new HashMap<>();

        // Generate files
        generateSellersFile(new File(sellersFolder, "sellers_info.txt"), documentNumbers);
        generateSalesFiles(salesFolder, documentNumbers);
        generateProductsFile(new File(productsFolder, "products_info.txt"));
    }

    /**
     * Generates a file with random seller information. Each line contains the document type, document number,
     * first name, and last name of a seller.
     *
     * @param file            The destination file where the seller information will be written.
     * @param documentNumbers A map associating document numbers with seller identifiers.
     */
    private static void generateSellersFile(File file, Map<String, String> documentNumbers) {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(file))) {
            for (int i = 0; i < NUM_SELLERS; i++) {
                String documentType = FileUtils.getRandomDocumentType();
                String documentNumber = FileUtils.generateRandomDocumentNumber();
                String firstName = FileUtils.getRandomFirstName();
                String lastName = FileUtils.getRandomLastName();
                bw.write(String.format("%s;%s;%s;%s%n", documentType, documentNumber, firstName, lastName));

                // Save the document number to use in sales files
                documentNumbers.put(documentNumber, "Seller_" + i);
            }
            System.out.println("Sellers file generated successfully at: " + file.getAbsolutePath());
        } catch (IOException e) {
            System.err.println("Error generating the sellers file: " + file.getAbsolutePath());
            e.printStackTrace();
        }
    }

    /**
     * Generates sales files for each seller. Each file contains the seller's document number and a list of sales
     * with product IDs and quantities.
     *
     * @param folder          The folder where the sales files will be created.
     * @param documentNumbers A map associating document numbers with seller identifiers.
     */
    private static void generateSalesFiles(File folder, Map<String, String> documentNumbers) {
        for (String documentNumber : documentNumbers.keySet()) {
            File salesFile = new File(folder, "sales_" + documentNumber + ".txt");
            try (BufferedWriter bw = new BufferedWriter(new FileWriter(salesFile))) {
                bw.write(String.format("DNI;%s%n", documentNumber));
                for (int j = 0; j < 3; j++) {
                    String productId = "P" + String.format("%03d", j + 1);
                    int quantity = FileUtils.RANDOM.nextInt(20) + 1;
                    bw.write(String.format("%s;%d;%n", productId, quantity));
                }
                System.out.println("Sales file generated successfully for seller: " + documentNumber);
            } catch (IOException e) {
                System.err.println("Error generating the sales file for seller: " + documentNumber);
                e.printStackTrace();
            }
        }
    }

    /**
     * Generates a file with random product information. Each line contains the product ID, product name, and price.
     *
     * @param file The destination file where the product information will be written.
     */
    private static void generateProductsFile(File file) {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(file))) {
            for (int i = 0; i < NUM_PRODUCTS; i++) {
                String productId = "P" + String.format("%03d", i + 1);
                String productName = "Product_" + i;
                double price = FileUtils.RANDOM.nextDouble() * 100;
                bw.write(String.format("%s;%s;%.2f%n", productId, productName, price));
            }
            System.out.println("Products file generated successfully at: " + file.getAbsolutePath());
        } catch (IOException e) {
            System.err.println("Error generating the products file: " + file.getAbsolutePath());
            e.printStackTrace();
        }
    }
}
