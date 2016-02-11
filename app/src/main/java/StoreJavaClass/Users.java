package StoreJavaClass;

import model.backend.PoolFunctions;

/**
 * Created by aviga_000 on 08/11/2015.
 */
public abstract class Users
{
    private int classification;
    private String password;
    private String firstName;
    private String lastName;
    private long id;
    private Long phoneNumber;
    private String eMail;
    private String city;
    private String street;
    private int buildingNumber;
    private String houseNumber;
    private int zipCode;


    //copy constructor
    public Users(Users user) {
        this.classification = user.getClassification();
        this.password = user.getPassword();
        this.firstName = user.getFirstName();
        this.lastName = user.getLastName();
        this.id = user.getId();
        this.phoneNumber = user.getPhoneNumber();
        this.eMail = user.geteMail();
        this.city = user.getCity();
        this.street = user.getStreet();
        this.buildingNumber = user.getBuildingNumber();
        this.houseNumber = user.getHouseNumber();
        this.zipCode = user.getZipCode();
        ShoppingCartHelper sch = new ShoppingCartHelper(id);
        PoolFunctions.supplierBookShoppingCart.add(sch);
    }

    // default constructor
    public Users( int classification, String password, String firstName, String lastName, long id, Long phoneNumber, String eMail, String city, String street, int buildingNumber, String houseNumber, int zipCode) {
        this.classification = classification;
        this.password = password;
        this.firstName = firstName;
        this.lastName = lastName;
        this.id = id;
        this.phoneNumber = phoneNumber;
        this.eMail = eMail;
        this.city = city;
        this.street = street;
        this.buildingNumber = buildingNumber;
        this.houseNumber = houseNumber;
        this.zipCode = zipCode;
        ShoppingCartHelper sch = new ShoppingCartHelper(id);
        PoolFunctions.supplierBookShoppingCart.add(sch);
    }
    //empty constructor
    public Users() {

        ShoppingCartHelper sch = new ShoppingCartHelper(id);
        PoolFunctions.supplierBookShoppingCart.add(sch);
    }

    //getters& setters

    public int getClassification() {
        return classification;
    }

    public void setClassification(int classification) {
        this.classification = classification;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getStreet() {
        return street;
    }

    public void setStreet(String street) {
        this.street = street;
    }

    public int getBuildingNumber() {
        return buildingNumber;
    }

    public void setBuildingNumber(int buildingNumber) {
        this.buildingNumber = buildingNumber;
    }

    public String getHouseNumber() {
        return houseNumber;
    }

    public void setHouseNumber(String houseNumber) {
        this.houseNumber = houseNumber;
    }

    public int getZipCode() {
        return zipCode;
    }

    public void setZipCode(int zipCode) {
        this.zipCode = zipCode;
    }

    public String geteMail() {
        return eMail;
    }

    public void seteMail(String eMail) {
        this.eMail = eMail;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public Long getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(Long phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
