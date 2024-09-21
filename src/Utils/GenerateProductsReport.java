package Utils;

import Entities.Product;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.*;

/**
 * Generates a report of sold products sorted by quantity in descending order.
 */
public class GenerateProductsReport {

    private static final String SALES_FOLDER = "SoldProducts/Sales/";
    private static final String PRODUCTS_FILE = "SoldProducts/Products/products_info.txt";
    private static final String PRODUCTS_REPORT_FILE = "SalesReport/Products/products_report.csv";

    public static void executeReport() {
        try {
            Map<String, Product> products = readProducts();
            Map<String, Integer> productSales = readSales();

            // Combine product and sales information
            List<Product> soldProducts = new ArrayList<>();
            for (String productId : productSales.keySet()) {
                Product product = products.get(productId);
                if (product != null) {
                    product.setQuantitySold(productSales.get(productId));
                    soldProducts.add(product);
                } else {
                    System.err.println("Product with ID " + productId + " not found in product file.");
                }
            }

            // Sort products by quantity sold in descending order
            soldProducts.sort((p1, p2) -> Integer.compare(p2.getQuantitySold(), p1.getQuantitySold()));

            // Generate the report
            generateProductsReport(soldProducts);

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
     * Generates the report file for sold products.
     *
     * @param soldProducts List of sold products with their quantities.
     * @throws IOException If an error occurs while writing the file.
     */
    private static void generateProductsReport(List<Product> soldProducts) throws IOException {
        // Create the directory if it doesn't exist
        File reportFile = new File(PRODUCTS_REPORT_FILE);
        reportFile.getParentFile().mkdirs();

        try (BufferedWriter bw = new BufferedWriter(new FileWriter(reportFile))) {
            bw.write("Product Name;Price;Quantity Sold");
            bw.newLine();
            for (Product product : soldProducts) {
                String line = String.format("%s;%.2f;%d",
                        product.getProductName(),
                        product.getPrice(),
                        product.getQuantitySold());
                bw.write(line);
                bw.newLine();
            }
        }
    }
}

