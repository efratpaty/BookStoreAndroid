package StoreJavaClass;

import android.widget.ImageView;

import java.io.Serializable;
import java.util.Date;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * Created by aviga_000 on 08/11/2015.
 */
public class Book implements Serializable
{
    public static AtomicInteger count = new AtomicInteger(1000);//variable for auto generating id
    private int bookId;
    private String bookName;
    private String author;
    private int publicationDate;
    private String publishers;
    private float recommendedPrice;
    private String summary;
    private Subject Subject;
    private String url;
    private List<Supplier> supplierList;
    //constructor


    public Book( String bookName, String author, int publicationDate, String publishers, float recommendedPrice, String summary, StoreJavaClass.Subject subject, String url) {
        this.bookId = count.incrementAndGet();
        this.bookName = bookName;
        this.author = author;
        this.publicationDate = publicationDate;
        this.publishers = publishers;
        this.recommendedPrice = recommendedPrice;
        this.summary = summary;
        Subject = subject;
        this.url = url;
    }

    //getters & setters
    public StoreJavaClass.Subject getSubject() {
        return Subject;
    }

    public void setSubject(StoreJavaClass.Subject subject) {
        Subject = subject;
    }

    public int getBookId() {
        return bookId;
    }

    public void setBookId(int bookId) {
        this.bookId = bookId;
    }

    public String getBookName() {
        return bookName;
    }

    public void setBookName(String bookName) {
        this.bookName = bookName;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public int getPublicationDate() {
        return publicationDate;
    }

    public void setPublicationDate(int publicationDate) {
        this.publicationDate = publicationDate;
    }

    public String getPublishers() {
        return publishers;
    }

    public void setPublishers(String publishers) {
        this.publishers = publishers;
    }

    public float getRecommendedPrice() {
        return recommendedPrice;
    }

    public void setRecommendedPrice(float recommendedPrice) {
        this.recommendedPrice = recommendedPrice;
    }

    public String getSummary() {
        return summary;
    }

    public void setSummary(String summary) {
        this.summary = summary;
    }

    public String getUrl() {return url;}

    public void setUrl(String url) {this.url = url;}

    public List<Supplier> getSupplierList() {
        return supplierList;
    }

    public void setSupplierList(List<Supplier> supplierList) {
        this.supplierList = supplierList;
    }
}
