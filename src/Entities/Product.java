package Entities;

/**
 * Represents a product with an ID, name, and price.
 */
public class Product {
    private String productId;
    private String productName;
    private double pricePerUnit;

    /**
     * Constructs a Product object with the specified details.
     *
     * @param productId The ID of the product.
     * @param productName The name of the product.
     * @param pricePerUnit The price per unit of the product.
     */
    public Product(String productId, String productName, double pricePerUnit) {
        this.productId = productId;
        this.productName = productName;
        this.pricePerUnit = pricePerUnit;
    }

    /**
     * Gets the product ID.
     *
     * @return The product ID.
     */
    public String getProductId() {
        return productId;
    }

    /**
     * Gets the product name.
     *
     * @return The product name.
     */
    public String getName() {
        return productName;
    }

    /**
     * Gets the price per unit of the product.
     *
     * @return The price per unit.
     */
    public double getPrice() {
        return pricePerUnit;
    }
}


