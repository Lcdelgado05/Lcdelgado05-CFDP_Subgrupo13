package Utils;

import Entities.Product;

import java.io.*;
import java.text.DecimalFormat;
import java.util.*;

/**
 * Generates a report of sold products sorted by quantity in descending order.
 * Additionally, logs errors related to negative quantities and incorrect formats in sales.
 */
public class GenerateProductsReport {

    private static final String SALES_FOLDER = "SoldProducts/Sales/";
    private static final String PRODUCTS_FILE = "SoldProducts/Products/products_info.txt";
    private static final String PRODUCTS_REPORT_FILE = "SalesReport/Products/products_report.csv";
    private static final String WRONG_FILES_FOLDER = "ReportWrongFiles/wrong_files_report.txt";

    // Formatter for prices in Colombian format (e.g., 1.000,00)
    private static final DecimalFormat priceFormat = new DecimalFormat("#,##0.00");

    private static boolean hasErrors = false; // Flag to check if there are errors

    /**
     * Executes the generation of the product sales report.
     * It processes sales data, generates a report in CSV format, 
     * and logs any errors related to negative quantities or invalid formats.
     */
    public static void displayAndSaveProductsReport() {
        try {
            Map<String, Product> products = readProducts(); // Reads product data from file
            Map<String, Integer> productSales = readSales(); // Reads sales data from files

            // Collect sales data for the report
            List<String[]> productData = new ArrayList<>();
            for (String productId : productSales.keySet()) {
                Product product = products.get(productId);
                if (product != null) {
                    int quantitySold = productSales.get(productId);

                    // Validate negative quantity or price
                    if (quantitySold < 0 || product.getPrice() <= 0) {
                        logError("Product: " + productId + " has negative quantity or invalid price.");
                        continue;
                    }

                    double totalRevenue = product.getPrice() * quantitySold;

                    // Format the price in Colombian format for display
                    String formattedPrice = priceFormat.format(product.getPrice());

                    // Add formatted data (price in Colombian format and quantity sold as integer)
                    productData.add(new String[]{
                        product.getProductName(),
                        priceFormat.format(product.getPrice() * 1000), // Format price
                        String.valueOf(quantitySold), // Quantity sold
                        priceFormat.format(totalRevenue * 1000) // Total revenue
                    });

                } else {
                    logError("Product with ID " + productId + " not found.");
                }
            }

            // Sort by quantity sold in descending order
            productData.sort((a, b) -> Integer.compare(Integer.parseInt(b[2]), Integer.parseInt(a[2])));

            // Display results in console (optional)
            for (String[] data : productData) {
                System.out.printf("%s; %s; %s; %s%n", data[0], data[1], data[2], data[3]);
            }

            // Save to CSV file
            saveProductReportToCSV(productData);

            // Log if no errors were found
            if (!hasErrors) {
                logNoErrorsFound();
            }

            System.out.println("Product report successfully generated at: " + PRODUCTS_REPORT_FILE);
        } catch (IOException e) {
            System.err.println("Error generating the product report.");
            e.printStackTrace();
        }
    }

    /**
     * Reads the product information from the product file.
     * 
     * @return A map containing product data with product ID as the key and Product objects as values.
     * @throws IOException If there is an error reading the file.
     */
    private static Map<String, Product> readProducts() throws IOException {
        Map<String, Product> products = new HashMap<>();
        try (BufferedReader br = new BufferedReader(new FileReader(PRODUCTS_FILE))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] parts = line.split(";");
                if (parts.length == 3) {
                    String productId = parts[0];
                    String productName = parts[1];
                    try {
                        double price = Double.parseDouble(parts[2].replace(",", ""));
                        products.put(productId, new Product(productId, productName, price));
                    } catch (NumberFormatException e) {
                        logError("Invalid price format for product: " + productId);
                    }
                } else {
                    logError("Error in product format: " + line);
                }
            }
        }
        return products;
    }

    /**
     * Reads the sales files from the sales directory and accumulates the quantity sold for each product.
     * Logs errors related to negative quantities or invalid sales formats.
     * 
     * @return A map where each key is a product ID and the value is the total quantity sold for that product.
     * @throws IOException If there is an error reading the sales files.
     */
    private static Map<String, Integer> readSales() throws IOException {
        Map<String, Integer> productSales = new HashMap<>();
        File folder = new File(SALES_FOLDER);
        File[] salesFiles = folder.listFiles();

        if (salesFiles != null) {
            for (File salesFile : salesFiles) {
                try (BufferedReader br = new BufferedReader(new FileReader(salesFile))) {
                    String line;
                    while ((line = br.readLine()) != null) {
                        // Skip header lines
                        if (line.startsWith("DNI") || line.startsWith("CC") || line.startsWith("CE") || line.startsWith("TI")) {
                            continue;
                        }
                        String[] parts = line.split(";");
                        if (parts.length >= 2) {
                            String productId = parts[0];
                            try {
                                int quantity = Integer.parseInt(parts[1]);

                                // Check for negative quantity
                                if (quantity < 0) {
                                    logError("Negative quantity in sales file " + salesFile.getName() + ": " + line);
                                    continue;
                                }

                                productSales.put(productId, productSales.getOrDefault(productId, 0) + quantity);
                            } catch (NumberFormatException e) {
                                logError("Invalid sales quantity format in file " + salesFile.getName() + ": " + line);
                            }
                        } else {
                            logError("Sales format error in file " + salesFile.getName() + ": " + line);
                        }
                    }
                }
            }
        } else {
            logError("No sales files found in the folder: " + SALES_FOLDER);
        }
        return productSales;
    }

    /**
     * Saves the product report to a CSV file.
     *
     * @param productData List of sold products with details like name, price, quantity sold, and total revenue.
     * @throws IOException If there is an error writing the file.
     */
    private static void saveProductReportToCSV(List<String[]> productData) throws IOException {
        // Create the directory if it doesn't exist
        File reportFile = new File(PRODUCTS_REPORT_FILE);
        reportFile.getParentFile().mkdirs();

        try (BufferedWriter bw = new BufferedWriter(new FileWriter(reportFile))) {
            // Write CSV header
            bw.write("Product Name;Price;Quantity Sold;Total Revenue");
            bw.newLine();

            // Write product sales data
            for (String[] product : productData) {
                bw.write(String.join(";", product));
                bw.newLine();
            }
        }
    }

    /**
     * Logs an error in the wrong files report. This method appends the error message to the wrong files log.
     *
     * @param errorMessage The error message to log.
     */
    private static void logError(String errorMessage) {
        hasErrors = true;
        File errorFile = new File(WRONG_FILES_FOLDER);
        errorFile.getParentFile().mkdirs(); // Create parent directories if they don't exist
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(errorFile, true))) { // Append mode
            bw.write(errorMessage);
            bw.newLine();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Logs a message indicating that no errors were found.
     */
    private static void logNoErrorsFound() {
        File errorFile = new File(WRONG_FILES_FOLDER);
        errorFile.getParentFile().mkdirs(); // Create parent directories if they don't exist
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(errorFile, true))) { // Append mode
            bw.write("No errors found.");
            bw.newLine();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}









