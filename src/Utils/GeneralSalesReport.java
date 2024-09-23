package Utils;

import java.io.*;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import Entities.Seller;

/**
 * Utility class for displaying and saving sales reports.
 */
public class GeneralSalesReport {

    /**
     * Displays and saves the sales report.
     *
     * @param sellers   A map where the key is the seller's document number and the value is the Seller object.
     * @param totalSales A map where the key is the seller's document number and the value is the total sales amount.
     */
    public static void displayAndSaveSalesReport(Map<String, Seller> sellers, Map<String, Double> totalSales) {
        List<String[]> salesData = new ArrayList<>();

        // Collect sales data
        for (Map.Entry<String, Double> entry : totalSales.entrySet()) {
            String documentNumber = entry.getKey();
            double total = entry.getValue() * 1000; // Multiplicar el total por 1000
            Seller seller = sellers.get(documentNumber);

            if (seller != null) {
                // Format the total as Colombian currency
                String formattedTotal = formatAsColombianCurrency(total);
                salesData.add(new String[]{
                    seller.getFirstName(),
                    seller.getLastName(),
                    formattedTotal
                });
            } else {
                System.out.printf("Seller with Document Number %s not found.%n", documentNumber);
            }
        }

        // Sort by total sales in descending order
        salesData.sort((a, b) -> Double.compare(
            Double.parseDouble(b[2].replace(".", "").replace(",", ".")),
            Double.parseDouble(a[2].replace(".", "").replace(",", "."))
        ));

        // Display results in console
        for (String[] data : salesData) {
            System.out.printf("%s;%s;%s%n", data[0], data[1], data[2]);
        }

        // Save to CSV
        saveSalesReportToCSV(salesData);
    }

    /**
    
     *
     * @param amount The amount to format.
     * @return The formatted currency string with points for thousands.
     */
    private static String formatAsColombianCurrency(double amount) {
        DecimalFormat df = new DecimalFormat("#,##0.00");
        return df.format(amount).replace(",", "."); 
    }

    /**
     * Saves the sales report data to a CSV file.
     *
     * @param salesData The list of sales data to write to the CSV file.
     */
    private static void saveSalesReportToCSV(List<String[]> salesData) {
        String csvFile = "SalesReport/TotalSales/sales_report.csv";

        // Create the directory if it doesn't exist
        File reportFile = new File(csvFile);
        reportFile.getParentFile().mkdirs();

        try (BufferedWriter bw = new BufferedWriter(new FileWriter(reportFile))) {
            // Write CSV header
            bw.write("First Name;Last Name;Total Sales");
            bw.newLine();

            // Write sales data
            for (String[] data : salesData) {
                bw.write(String.join(";", data));
                bw.newLine();
            }
            System.out.println("Sales report saved to " + csvFile);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}












