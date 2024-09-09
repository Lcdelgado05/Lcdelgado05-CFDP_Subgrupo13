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

    public void setSales(List<Sale> sales) {
        this.sales = sales;
    }

    @Override
    public String toString() {
        return String.format("Seller{documentType='%s', documentNumber='%s', firstName='%s', lastName='%s'}",
                documentType, documentNumber, firstName, lastName);
    }
}
