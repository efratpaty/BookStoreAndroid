package StoreJavaClass;

import java.io.Serializable;

/**
 * Created by aviga_000 on 08/11/2015.
 */
public class Buyer extends Users implements Serializable
{
    //constructor
    public Buyer( String password, String firstName, String lastName, long id, Long phoneNumber, String eMail, String city, String street, int buildingNumber, String houseNumber, int zipCode) {
        super(3, password, firstName, lastName, id, phoneNumber, eMail, city, street, buildingNumber, houseNumber, zipCode);
    }

    public Buyer(Buyer buyer) {
        super(buyer);
    }

    public Buyer() {

    }
}

