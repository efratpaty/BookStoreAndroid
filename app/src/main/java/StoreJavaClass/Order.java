package StoreJavaClass;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * Created by aviga_000 on 08/11/2015.
 */
public class Order implements Serializable
{
    private static final AtomicInteger count = new AtomicInteger(1000);//variable for auto generating id
    private int orderId;
    private ArrayList<Integer> bookId;
    private ArrayList<Long> supplierId;
    private long customerId;
    private long creditNumber;
    private int cvs;//3 numbers in the back of the card
    private String cardHolderName;
    private long cardHolderId;
    private double paymentSum;
    private Date orderDate;
    //constructor
    public Order(ArrayList<Integer> bookId, long cardHolderId, String cardHolderName, long creditNumber, long customerId, int cvs, Date orderDate, float paymentSum, ArrayList<Long> supplierId) {
        this.bookId = bookId;
        this.cardHolderId = cardHolderId;
        this.cardHolderName = cardHolderName;
        this.creditNumber = creditNumber;
        this.customerId = customerId;
        this.cvs = cvs;
        this.orderDate = orderDate;
        this.orderId = count.incrementAndGet();
        this.paymentSum = paymentSum;
        this.supplierId = supplierId;
    }

    //default constructor
    public Order() {
    }

    //getters & setters
    public ArrayList<Integer> getBookId() {
        return bookId;
    }

    public void setBookId(ArrayList<Integer> bookId) {
        this.bookId = bookId;
    }

    public long getCardHolderId() {
        return cardHolderId;
    }

    public void setCardHolderId(long cardHolderId) {
        this.cardHolderId = cardHolderId;
    }

    public String getCardHolderName() {
        return cardHolderName;
    }

    public void setCardHolderName(String cardHolderName) {
        this.cardHolderName = cardHolderName;
    }

    public long getCreditNumber() {
        return creditNumber;
    }

    public void setCreditNumber(long creditNumber) {
        this.creditNumber = creditNumber;
    }

    public long getCustomerId() {
        return customerId;
    }

    public void setCustomerId(long customerId) {
        this.customerId = customerId;
    }

    public int getCvs() {
        return cvs;
    }

    public void setCvs(int cvs) {
        this.cvs = cvs;
    }

    public Date getOrderDate() {
        return orderDate;
    }

    public void setOrderDate(Date orderDate) {
        this.orderDate = orderDate;
    }

    public int getOrderId() {
        return orderId;
    }

    public void setOrderId(int orderId) {
        this.orderId = orderId;
    }

    public double getPaymentSum() {
        return paymentSum;
    }

    public void setPaymentSum(double paymentSum) {
        this.paymentSum = paymentSum;
    }

    public ArrayList<Long> getSupplierId() {
        return supplierId;
    }

    public void setSupplierId(ArrayList<Long> supplierId) {
        this.supplierId = supplierId;
    }
}
