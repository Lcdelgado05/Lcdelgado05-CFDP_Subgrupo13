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

    public String getProductId() {
        return productId;
    }

    public void setProductId(String productId) {
        this.productId = productId;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    @Override
    public String toString() {
        return "Product ID: " + productId + ", Quantity: " + quantity;
    }
}
