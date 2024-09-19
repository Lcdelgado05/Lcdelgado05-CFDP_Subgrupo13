package Entities;

import java.util.List;

/**
 * Represents a seller in the sales system.
 * <p>
 * The <code>Seller</code> class stores information about a seller, including their document type and number,
 * first name, last name, and a list of associated sales. It provides methods to access and modify these data.
 * </p>
 */
public class Seller {
    private String documentType;
    private String documentNumber;
    private String firstName;
    private String lastName;
    private List<Sale> sales;

    /**
     * Creates a seller with the specified document type, document number, first name, and last name.
     *
     * @param documentType   the type of the document (e.g., passport, ID)
     * @param documentNumber the document number
     * @param firstName      the first name of the seller
     * @param lastName       the last name of the seller
     */
    public Seller(String documentType, String documentNumber, String firstName, String lastName) {
        this.documentType = documentType;
        this.documentNumber = documentNumber;
        this.firstName = firstName;
        this.lastName = lastName;
    }

    public String getDocumentType() {
        return documentType;
    }

    public String getDocumentNumber() {
        return documentNumber;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public List<Sale> getSales() {
        return sales;
    }

    /**
     * Sets the list of sales for this seller.
     *
     * @param sales the list of sales to set
     */
    public void setSales(List<Sale> sales) {
        this.sales = sales;
    }

    /**
     * Returns a string representation of the seller's information.
     *
     * @return a string with the seller's document type, document number, first name, and last name
     */
    public String toString() {
        return String.format("Seller{documentType='%s', documentNumber='%s', firstName='%s', lastName='%s'}",
                documentType, documentNumber, firstName, lastName);
    }
}
