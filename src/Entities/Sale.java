package Entities;

/**
 * Represents a sale of a product.
 * <p>
 * The <code>Sale</code> class stores information about a specific sale, including the product identifier
 * and the quantity sold. It provides methods to access and modify these data.
 * </p>
 */
public class Sale {
    private String productId;
    private int quantity;

    public Sale(String productId, int quantity) {
        this.productId = productId;
        this.quantity = quantity;
    }

    /**
     * Returns the product ID associated with the sale.
     * 
     * @return the product ID
     */
    public String getProductId() {
        return productId;
    }

    /**
     * Sets the product ID associated with the sale.
     * 
     * @param productId the product ID to set
     */
    public void setProductId(String productId) {
        this.productId = productId;
    }


    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        if (quantity < 0) {
            throw new IllegalArgumentException("Quantity cannot be negative");
        }
        this.quantity = quantity;
    }

    public String toString() {
        return String.format("Product ID: %s, Quantity: %d", productId, quantity);
    }

}
