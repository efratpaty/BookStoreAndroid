package StoreJavaClass;

import android.widget.ImageView;

import java.io.Serializable;
import java.util.Date;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * Created by aviga_000 on 08/11/2015.
 */
public class SupplierBook implements Serializable
{
    private static final AtomicInteger count = new AtomicInteger(1000);//variable for auto generating id
    private int id;
    private int bookId;
    private long supplierId;
    private int copies;
    private double price;
    private BookCondition bookCondition;
    //default constructor


    public SupplierBook( int bookId, long supplierId, int copies, double price, BookCondition bookCondition) {
        this.id = count.incrementAndGet();;
        this.bookId = bookId;
        this.supplierId = supplierId;
        this.copies = copies;
        this.price = price;
        this.bookCondition = bookCondition;
    }

    //setters & getters
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

    public int getCopies() {
        return copies;
    }

    public void setCopies(int copies) {
        this.copies = copies;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public long getSupplierId() {
        return supplierId;
    }

    public void setSupplierId(long supplierId) {
        this.supplierId = supplierId;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}
