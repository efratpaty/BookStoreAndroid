package StoreJavaClass;

import java.io.Serializable;
import java.util.Calendar;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * Created by aviga_000 on 08/11/2015.
 */
public class BookSearch implements Serializable
{
    private static final AtomicInteger count = new AtomicInteger(1000);//variable for auto generating id
    private  int bookSearchId;
    private Calendar dateOfRequest ;
    private int bookId;
    private long customerId;
    private String customerEmail;
    private BookCondition bookCondition;//new/used/used-like new/all conditions
    private float maxPrice;//maximum price the client is willing to pay for the book
    private boolean sendMessage;//if the client want to receive a message in case the book was found

    //constructor
    public BookSearch( int bookId, long customerId, String customerEmail, BookCondition bookCondition, float maxPrice, boolean sendMessage) {
        this.bookSearchId = count.incrementAndGet();
        this.dateOfRequest = Calendar.getInstance();
        this.bookId = bookId;
        this.customerId = customerId;
        this.customerEmail = customerEmail;
        this.bookCondition = bookCondition;
        this.maxPrice = maxPrice;
        this.sendMessage = sendMessage;
    }

    // default constructor
    public BookSearch() {

    }

    //getters & setters
    public BookCondition getBookCondition() {
        return bookCondition;
    }

    public void setBookCondition(BookCondition bookCondition) {
        this.bookCondition = bookCondition;
    }

    public int getBookId() {
        return bookId;
    }

    public void setBookId(int bookId) {
        this.bookId = bookId;
    }

    public long getCustomerId() {
        return customerId;
    }

    public void setCustomerId(long customerId) {
        this.customerId = customerId;
    }

    public float getMaxPrice() {
        return maxPrice;
    }

    public void setMaxPrice(float maxPrice) {
        this.maxPrice = maxPrice;
    }

    public boolean isSendMessage() {
        return sendMessage;
    }

    public void setSendMessage(boolean sendMessage) {
        this.sendMessage = sendMessage;
    }

    public int getBookSearchId() {
        return bookSearchId;
    }

    public Calendar getDateOfRequest() {
        return dateOfRequest;
    }

    public String getCustomerEmail() {
        return customerEmail;
    }

    public void setCustomerEmail(String customerEmail) {
        this.customerEmail = customerEmail;
    }
}
