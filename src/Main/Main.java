package Main;

import Entities.Seller;
import Utils.GeneralSalesReport;
import Utils.GenerateProductsReport;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * Main class to read seller and sales data, calculate total sales,
 * and generate a sales report.
 */
public class Main {

    private static final String SELLERS_FILE = "SoldProducts/Sellers/sellers_info.txt";
    private static final String SALES_FOLDER = "SoldProducts/Sales/";
    private static final String PRODUCTS_FILE = "SoldProducts/Products/products_info.txt";

    /**
     * Main method that initiates the process of reading data and generating the sales report.
     *
     * @param args Command line arguments (not used).
     */
    public static void main(String[] args) {
        Map<String, Seller> sellers = readSellers();
        Map<String, Double> totalSales = readSales(sellers);

        // Display and save the sales report
        GeneralSalesReport.displayAndSaveSalesReport(sellers, totalSales);
        
        // Display and save the products report
        GenerateProductsReport.displayAndSaveProductsReport();
    }

    /**
     * Reads seller information from the specified file and creates Seller objects.
     *
     * @return A map where the key is the seller's document number and the value is the Seller object.
     */
    private static Map<String, Seller> readSellers() {
        Map<String, Seller> sellers = new HashMap<>();
        try (BufferedReader br = new BufferedReader(new FileReader(SELLERS_FILE))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] parts = line.split(";");
                if (parts.length == 4) {
                    String documentType = parts[0];
                    String documentNumber = parts[1];
                    String firstName = parts[2];
                    String lastName = parts[3];
                    Seller seller = new Seller(documentType, documentNumber, firstName, lastName);
                    sellers.put(documentNumber, seller);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return sellers;
    }

    /**
     * Reads sales data from files and calculates the total sales for each seller.
     *
     * @param sellers A map of sellers to associate with sales data.
     * @return A map where the key is the seller's document number and the value is the total sales amount.
     */
    private static Map<String, Double> readSales(Map<String, Seller> sellers) {
        Map<String, Double> totalSales = new HashMap<>();
        for (String documentNumber : sellers.keySet()) {
            double total = 0.0;
            String salesFile = SALES_FOLDER + "sales_" + documentNumber + ".txt";
            try (BufferedReader br = new BufferedReader(new FileReader(salesFile))) {
                String line;
                while ((line = br.readLine()) != null) {
                    if (line.startsWith("DNI")) continue; // Skip header line
                    String[] parts = line.split(";");
                    if (parts.length == 2) {
                        String productId = parts[0];
                        int quantity = Integer.parseInt(parts[1]);
                        double price = getProductPrice(productId);
                        total += quantity * price;
                    }
                }
                totalSales.put(documentNumber, total);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return totalSales;
    }

    /**
     * Gets the price of a product by its ID from the products file.
     *
     * @param productId The product ID to search for.
     * @return The price of the product, or 0 if not found.
     */
    private static double getProductPrice(String productId) {
        try (BufferedReader br = new BufferedReader(new FileReader(PRODUCTS_FILE))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] parts = line.split(";");
                if (parts.length == 3 && parts[0].equals(productId)) {
                    return Double.parseDouble(parts[2].replace(",", ""));
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return 0;
    }
}









