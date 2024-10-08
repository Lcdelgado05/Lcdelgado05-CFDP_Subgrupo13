package SalesInfo;

import Utils.ProductsCount;
import Utils.SalesmanCount;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * Generates files with pseudo-random data for sellers, sales, and products.
 */
public class GenerateInfoFiles {

    private static final String MAIN_FOLDER = "SoldProducts";
    private static final String SALES_FOLDER = "Sales";
    private static final String SELLERS_FOLDER = "Sellers";
    private static final String PRODUCTS_FOLDER = "Products";
    private static final int NUM_SELLERS = 7;
    private static final int NUM_PRODUCTS = 10;
    private static final int NUM_PRODUCTS_PER_SELLER = 8; // Number of products per seller
    private static final int MAX_QUANTITY = 10; // Maximum quantity per sale

    public static void main(String[] args) {
        File mainFolder = new File(MAIN_FOLDER);
        File salesFolder = new File(mainFolder, SALES_FOLDER);
        File sellersFolder = new File(mainFolder, SELLERS_FOLDER);
        File productsFolder = new File(mainFolder, PRODUCTS_FOLDER);

        createFolder(mainFolder);
        createFolder(salesFolder);
        createFolder(sellersFolder);
        createFolder(productsFolder);

        Map<String, String> documentNumbers = new HashMap<>();

        createSalesManInfoFile(new File(sellersFolder, "sellers_info.txt"), documentNumbers);
        createSalesFiles(salesFolder, documentNumbers);
        createProductsFile(new File(productsFolder, "products_info.txt"));
    }

    /**
     * Creates a folder if it does not exist. Prints a message if the folder already exists.
     *
     * @param folder The folder to be created on the file system.
     */
    private static void createFolder(File folder) {
        if (folder.exists()) {
            System.out.println("Folder already exists: " + folder.getAbsolutePath());
        } else if (folder.mkdirs()) {
            System.out.println("Folder created successfully: " + folder.getAbsolutePath());
        } else {
            System.err.println("Error creating folder: " + folder.getName());
        }
    }

    /**
     * Creates a file with information about sellers.
     *
     * @param file            The file to be created.
     * @param documentNumbers A map to store document numbers and corresponding seller identifiers.
     */
    private static void createSalesManInfoFile(File file, Map<String, String> documentNumbers) {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(file))) {
            for (int i = 0; i < NUM_SELLERS; i++) {
                String documentType = SalesmanCount.getRandomDocumentType();
                String documentNumber = SalesmanCount.generateRandomDocumentNumber();
                String[] name = SalesmanCount.generateUniqueName();
                String firstName = name[0];
                String lastName = name[1];
                bw.write(String.format("%s;%s;%s;%s%n", documentType, documentNumber, firstName, lastName));
                documentNumbers.put(documentNumber, "Seller_" + i);
            }
            System.out.println("Sellers file generated successfully at: " + file.getAbsolutePath());
        } catch (IOException e) {
            System.err.println("Error generating the sellers file: " + file.getAbsolutePath());
            e.printStackTrace();
        }
    }


    /**
     * Creates sales files for each seller. Each file contains the seller's document number and a list of sales
     * with product IDs and quantities.
     *
     * @param folder          The folder where the sales files will be created.
     * @param documentNumbers A map associating document numbers with seller identifiers.
     */
    private static void createSalesFiles(File folder, Map<String, String> documentNumbers) {
        for (String documentNumber : documentNumbers.keySet()) {
            File salesFile = new File(folder, "sales_" + documentNumber + ".txt");
            try (BufferedWriter bw = new BufferedWriter(new FileWriter(salesFile))) {
                bw.write(String.format("DNI;%s%n", documentNumber));
                for (int j = 0; j < NUM_PRODUCTS_PER_SELLER; j++) {
                    String productId = "P" + String.format("%03d", j + 1);
                    int quantity = SalesmanCount.RANDOM.nextInt(MAX_QUANTITY) + 1; // Correct reference to RANDOM
                    bw.write(String.format("%s;%d;%n", productId, quantity));
                }
                System.out.println("Sales file generated successfully for seller: " + documentNumber);
            } catch (IOException e) {
                System.err.println("Error generating the sales file for seller with document number: " + documentNumber);
                e.printStackTrace();
            }
        }
    }

    /**
     * Creates a file with pseudo-random information about products.
     *
     * @param file The file to be created.
     */
    private static void createProductsFile(File file) {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(file))) {
            for (int i = 0; i < NUM_PRODUCTS; i++) {
                String productId = "P" + String.format("%03d", i + 1);
                String productName = ProductsCount.getRandomProductName();
                int price = ProductsCount.getRandomProductPrice();
                bw.write(String.format("%s;%s;%,d%n", productId, productName, price));
            }
            System.out.println("Products file generated successfully at: " + file.getAbsolutePath());
        } catch (IOException e) {
            System.err.println("Error generating the products file: " + file.getAbsolutePath());
            e.printStackTrace();
        }
    }
}



