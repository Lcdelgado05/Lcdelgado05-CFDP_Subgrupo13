package Entities;

/**
 * Represents a product in the sales system.
 * <p>
 * The <code>Product</code> class stores information about a specific product, including its identifier,
 * name, and price per unit. It provides methods to access and modify these data.
 * </p>
 */
public class Product {
    private String productId;
    private String productName;
    private double pricePerUnit;

    public Product(String productId, String productName, double pricePerUnit) {
        this.productId = productId;
        this.productName = productName;
        this.pricePerUnit = pricePerUnit;
    }

    public String getProductId() {
        return productId;
    }

    public void setProductId(String productId) {
        this.productId = productId;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public double getPricePerUnit() {
        return pricePerUnit;
    }

    public void setPricePerUnit(double pricePerUnit) {
        this.pricePerUnit = pricePerUnit;
    }

    @Override
    public String toString() {
        return productId + ";" + productName + ";" + pricePerUnit;
    }
}
