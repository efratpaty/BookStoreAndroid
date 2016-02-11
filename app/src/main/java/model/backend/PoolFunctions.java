package model.backend;

import android.app.Activity;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import StoreJavaClass.Book;
import StoreJavaClass.BookCondition;
import StoreJavaClass.BookSearch;
import StoreJavaClass.Buyer;
import StoreJavaClass.Complains;
import StoreJavaClass.Manager;
import StoreJavaClass.Order;
import StoreJavaClass.ShoppingCartHelper;
import StoreJavaClass.Subject;
import StoreJavaClass.Supplier;
import StoreJavaClass.SupplierBook;

/**
 * Created by aviga_000 on 09/11/2015.
 */
public interface PoolFunctions
{
    ArrayList<Book> books = new ArrayList<Book>();
    ArrayList<Buyer> buyers = new ArrayList<Buyer>();
    ArrayList<Complains> complainses = new ArrayList<Complains>();
    ArrayList<Order> orders = new ArrayList<Order>();
    ArrayList<Supplier> suppliers = new ArrayList<Supplier>();
    ArrayList<SupplierBook> supplierBooks = new ArrayList<SupplierBook>();
    ArrayList<BookSearch> bookSearchings = new ArrayList<BookSearch>();
    ArrayList<ShoppingCartHelper> supplierBookShoppingCart = new ArrayList<ShoppingCartHelper>();
    Manager manager = new Manager();

    //buyer
    void addBuyer(Buyer buyer);
    void updateBuyer(Buyer buyer);
    void deleteBuyer(Buyer buyer);
    List<Buyer> buyerList() throws Exception;

    //book
    void addBook(Book book) throws IOException;
    void deleteBook(Book book);
    ArrayList<SupplierBook> searchBooks(String bookName, String author, Subject subject, float price, BookCondition condition);
    ArrayList<Book> bookList();

    //supplier
    void addSupplier(Supplier supplier);
    void updateSupplier(Supplier supplier);
    void deleteSupplier(Supplier supplier);
    Supplier searchSupplier (String password );
    ArrayList<SupplierBook> supplierBookList(long supplierID);
    ArrayList<Supplier> supplierList();

    //BookSupplier
    void addBookSupplier(SupplierBook supplierBook) ;//throws AddressException;
    void updateBookSupplier(SupplierBook supplierBook) ;//throws AddressException;
    void deleteBookSupplier(SupplierBook supplierBook); //search supplierbook list and delete the requested book and search supplier by supplier ID and delete the book from its list
    ArrayList<SupplierBook> supplierBookList();

    //orders
    void addOrder(Order order);
   void deleteOrder(Order order);
    ArrayList<Order> customerOrderListByDate(Date from, Date to, Long customerId);//all the items the customer bought between certain dates
    ArrayList<Order> SupplierOrderListByDate(Date from, Date to, Long supplierId);//all the items the supplier sold between certain dates
    ArrayList<Order> orderListByDate(Date from, Date to);
    ArrayList<Order> orderList();
    ArrayList<Order> SupplierorderList(long supplierId);
    ArrayList<Order> BuyerOrderList(long buyerId);
    double expenses(Date from, Date to);
    double profit(Date from, Date to);

    //bookSearching
    void addBookSearch(BookSearch bookSearching);
    void updateBookSearch(BookSearch bookSearching);
    void deleteBookSearch(BookSearch bookSearching);
    ArrayList<BookSearch> bookSearchList();
    ArrayList<BookSearch> authorBookRequestList(String name, String author);//search bookSearch(requests) by author and/or book name
    void reminder();// once a month its check the date and send reminder to orders over two months old

    //complains
    void addComplains(Complains complains);
    void updateComplains(Complains complains);
    void deleteComplains(Complains complains);
    ArrayList<Complains> complainsList();
    ArrayList<Complains> complainsUserList(long userID);
    ArrayList<Complains> complainsAboutUserList(long userID);
    ArrayList<Complains> complainantDefendant(long complainantID, long defendantID);

    //manager
    void updateManager(Manager manager);

    void SendEmail(List<String> email, String subject, String text); //throws AddressException;

    ArrayList<Long> customersId();
    void exitProgram(final Activity activity);

    //shopping cart option
    ArrayList<ShoppingCartHelper> getCart();
    ArrayList<SupplierBook> findSuplierBookById(long userId);
    void addCartItem(long userId,SupplierBook sb);
    boolean deleteCartItem (long userId,SupplierBook sb);


}
