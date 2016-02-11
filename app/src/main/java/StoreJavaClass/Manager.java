package StoreJavaClass;

import java.io.Serializable;

import model.backend.PoolFunctions;

/**
 * Created by aviga_000 on 08/11/2015.
 */
public class Manager extends Users implements Serializable
{
    private int classification = 1;
    private String password = "ES123456789";
    private String firstName = "EfratShiran";
    private String lastName = "LeviHalevi";
    private long id = 123456789;
    private Long phoneNumber = Long.parseLong("0541234567");
    private String eMail = "efratpaty@gmail.com";
    private String city = "jerusalem";
    private String street = "yafo";
    private int buildingNumber = 23;
    private char houseNumber = 5;
    private int zipCode = 9199907;

    public Manager() {
        super(1, "ES123456789","EfratShiran","LeviHalevi",123456789, Long.parseLong("0541234567"),"efratpaty@gmail.com","jerusalem","yafo",23,"5",9199907);
    }


}
