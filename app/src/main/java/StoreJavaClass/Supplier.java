package StoreJavaClass;

import java.io.Serializable;
import java.util.List;

/**
 * Created by aviga_000 on 08/11/2015.
 */
public class Supplier extends Users implements Serializable
{
    private List<SupplierBook> supplierBookList;//list of all the books this supplier supplies
    // default constructor
    public Supplier(String password, String firstName, String lastName, long id, Long phoneNumber, String eMail, String city, String street, int buildingNumber, String houseNumber, int zipCode) {
        super( 2 ,password, firstName, lastName, id, phoneNumber, eMail, city, street, buildingNumber, houseNumber, zipCode);
        this.supplierBookList = supplierBookList;
    }
    //copy constructor
    public Supplier(Supplier supplier) {
        super(supplier);
        this.supplierBookList = supplier.getSupplierBookList();
    }

    public Supplier() {
    }

    // getters&setters
    public List<SupplierBook> getSupplierBookList() {
        return supplierBookList;
    }

    public void setSupplierBookList(List<SupplierBook> supplierBookList) {
        this.supplierBookList = supplierBookList;
    }
}
