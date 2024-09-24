package Utils;

import Entities.Product;

import java.io.*;
import java.text.DecimalFormat;
import java.util.*;

/**
 * Generates a report of sold products sorted by quantity in descending order.
 */
public class GenerateProductsReport {

    private static final String SALES_FOLDER = "SoldProducts/Sales/";
    private static final String PRODUCTS_FILE = "SoldProducts/Products/products_info.txt";
    private static final String PRODUCTS_REPORT_FILE = "SalesReport/Products/products_report.csv";

    // Formatter for prices in Colombian format (e.g., 1.000,00)
    private static final DecimalFormat priceFormat = new DecimalFormat("#,##0.00");

    /**
     * Executes the generation of the product sales report.
     */
    public static void displayAndSaveProductsReport() {
        try {
            Map<String, Product> products = readProducts();
            Map<String, Integer> productSales = readSales();

            // Collect sales data for report
            List<String[]> productData = new ArrayList<>();
            for (String productId : productSales.keySet()) {
                Product product = products.get(productId);
                if (product != null) {
                    int quantitySold = productSales.get(productId);
                    double totalRevenue = product.getPrice() * quantitySold;

                    // Format the price in Colombian format for display
                    String formattedPrice = priceFormat.format(product.getPrice());
                    
                    // Add formatted data (price in Colombian format and quantity sold as integer)
                    productData.add(new String[]{
                        product.getProductName(),
                        priceFormat.format(product.getPrice() * 1000), 
                        String.valueOf(quantitySold), 
                        priceFormat.format(totalRevenue * 1000) 
                    });

                } else {
                    System.err.println("Product with ID " + productId + " not found.");
                }
            }

            // Sort by quantity sold in descending order
            productData.sort((a, b) -> Integer.compare(Integer.parseInt(b[2]), Integer.parseInt(a[2])));

            // Display results in console (optional)
            for (String[] data : productData) {
                System.out.printf("%s; %s; %s; %s%n", data[0], data[1], data[2], data[3]);
            }

            // Save to CSV
            saveProductReportToCSV(productData);

            System.out.println("Product report successfully generated at: " + PRODUCTS_REPORT_FILE);
        } catch (IOException e) {
            System.err.println("Error generating the product report.");
            e.printStackTrace();
        }
    }

    /**
     * Reads the product file and returns a map of products.
     *
     * @return A map containing product information.
     * @throws IOException If an error occurs while reading the file.
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
                    double price = Double.parseDouble(parts[2].replace(",", ""));
                    products.put(productId, new Product(productId, productName, price));
                }
            }
        }
        return products;
    }

    /**
     * Reads the sales files and accumulates the quantity sold for each product.
     *
     * @return A map containing the total quantity sold per product.
     * @throws IOException If an error occurs while reading the files.
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
                            int quantity = Integer.parseInt(parts[1]);
                            productSales.put(productId, productSales.getOrDefault(productId, 0) + quantity);
                        }
                    }
                }
            }
        } else {
            System.err.println("No sales files found in folder: " + SALES_FOLDER);
        }
        return productSales;
    }

    /**
     * Saves the product report to a CSV file.
     *
     * @param productData List of sold products with their details.
     * @throws IOException If an error occurs while writing the file.
     */
    private static void saveProductReportToCSV(List<String[]> productData) throws IOException {
        // Create the directory if it doesn't exist
        File reportFile = new File(PRODUCTS_REPORT_FILE);
        reportFile.getParentFile().mkdirs();

        try (BufferedWriter bw = new BufferedWriter(new FileWriter(reportFile))) {
            bw.write("Product Name;Price;Quantity Sold;Total Revenue");
            bw.newLine();
            for (String[] product : productData) {
                bw.write(String.join(";", product));
                bw.newLine();
            }
        }
    }
}

