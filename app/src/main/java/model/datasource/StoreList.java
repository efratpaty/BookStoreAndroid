package model.datasource;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;

import com.example.aviga_000.bookstoreandroid.AddBookActivity;
import com.example.aviga_000.bookstoreandroid.CustomerActivity;
import com.example.aviga_000.bookstoreandroid.ShoppingCartActivity;
import com.example.aviga_000.bookstoreandroid.SupplierBookAdapter;
import com.example.aviga_000.bookstoreandroid.SupplierBooksActivity;
import com.example.aviga_000.bookstoreandroid.UpdateBookSupplier;
import com.example.aviga_000.bookstoreandroid.UpdateBuyerActivity;
import com.example.aviga_000.bookstoreandroid.UpdateRequest;
import com.example.aviga_000.bookstoreandroid.UpdateSupplierActivity;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Scanner;

import javax.annotation.security.RolesAllowed;

import StoreJavaClass.ShoppingCartHelper;
import model.backend.PoolFunctions;
import StoreJavaClass.Book;
import StoreJavaClass.BookCondition;
import StoreJavaClass.BookSearch;
import StoreJavaClass.Buyer;
import StoreJavaClass.Complains;
import StoreJavaClass.Manager;
import StoreJavaClass.Order;
import StoreJavaClass.Subject;
import StoreJavaClass.Supplier;
import StoreJavaClass.SupplierBook;
/*
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Transport;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
*/

/**
 * Created by aviga_000 on 09/11/2015.
 */
    public class StoreList implements PoolFunctions {
    Scanner in = new Scanner(System.in);//used for inputs

    private Context context;

    //constructor
    public StoreList(Context current) {
        this.context = current;
    }


    @Override
    public void addBuyer(final Buyer buyer) {
        if (!buyers.contains(buyer.getId())) {
            buyers.add(buyer);
        }
        else {

            new AlertDialog.Builder(context)
                    .setIcon(android.R.drawable.ic_dialog_alert)
                    .setTitle("ERROR")
                    .setMessage("Id already exist in the system. Would you like to update buyer?")
                    .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            updateBuyer(buyer);
                            Intent intent = new Intent(context, UpdateBuyerActivity.class);
                            intent.putExtra("user_id", buyer.getId());
                            intent.putExtra("user", 1);
                            context.startActivity(intent);

                        }
                    })
                    .setNegativeButton("No", null)
                    .setIcon(android.R.drawable.ic_dialog_info)
                    .show();
            return;
            }

        }

    @Override
    @RolesAllowed({"Manager", "Buyer "})
    public void updateBuyer(Buyer buyer) {
        //check if there is no such buyer in the system
        if (!buyers.contains(buyer.getId()))//if not
        {
            System.out.println("ERROR: No buyer with this id exist in the system. Would you like to add new buyer?");
            char c;
            boolean flag = false;
            do {
                System.out.println("press y for yes and n for no");
                c = in.next().charAt(0);//get user input
                if (c != 'y' || c != 'n')//if input is not valid choice
                {
                    System.out.println("ERROR: wrong input. Please use valid input:");
                } else
                    flag = true;
            }
            while (flag == false);//if input is wrong repeat request for valid value and check again
            switch (c) {
                case 'y'://in case customer chose y meaning he wants to add new buyer
                    addBuyer(buyer);//send to function to add buyer
                    break;
                case ('n'):
                    break;
            }
        } else {//if there is a such a buyer in the system
            //update buyer
            for (Buyer b : buyers) {
                if (b.getId() == buyer.getId()) { //if buyer found update certain details
                    int index = buyers.indexOf(b);
                    b.setBuildingNumber(buyer.getBuildingNumber());
                    b.setCity(buyer.getCity());
                    b.seteMail(buyer.geteMail());
                    b.setFirstName(buyer.getFirstName());
                    b.setHouseNumber(buyer.getHouseNumber());
                    b.setLastName(buyer.getLastName());
                    b.setPassword(buyer.getPassword());
                    b.setPhoneNumber(buyer.getPhoneNumber());
                    b.setStreet(buyer.getStreet());
                    b.setZipCode(buyer.getZipCode());
                    buyers.set(index, b);//replace the previous buyer with the updated one

                }
            }

        }

    }

    @Override
    @RolesAllowed({"Manager", "Buyer"})
    public void deleteBuyer(Buyer buyer) {
        if (!buyers.contains(buyer.getId()))
            System.out.println("ERROR: no buyer with this id exist in the system");
        else
            buyers.remove(buyer);
    }

    @Override
    public ArrayList<Buyer> buyerList() {
        ArrayList<Buyer> buyerList = new ArrayList<Buyer>(buyers);
        return buyerList;
    }

    @Override
    @RolesAllowed({"Manager", "Supplier"})
    public void addBook(Book book) {
        if (!books.contains(book.getBookId()))
            books.add(book);
        else {
            System.out.println("ERROR: this book already exist in the system");
        }
    }

    @Override
    @RolesAllowed({"Manager", "Supplier"})
    public void deleteBook(Book book) {
        if (!books.contains(book.getBookId()))
            System.out.println("ERROR: this book does not exist in the system");
        else
            books.remove(book);
    }

    @Override
    public ArrayList<SupplierBook> searchBooks(String name, String author, Subject subject, float price, BookCondition condition) {
        //search book according to the inserted value
        ArrayList<SupplierBook> SPbooks = new ArrayList<SupplierBook>();//initialize list to contain all the books that stand to the conditions
        ArrayList<Integer> idbooks = new ArrayList<Integer>();//initialize list to contain all the book ids
        for (Book sp : bookList()) {//for each book check all the conditions
            if ((name .equals( null) || name.equals( "")  || sp.getBookName().toLowerCase().contains(name.toLowerCase()))
                    && (author.equals(null) || author.equals("") || sp.getAuthor().toLowerCase().contains( author.toLowerCase()))
                    && (subject == (null) || subject == Subject.ALL_SUBJECTS || sp.getSubject().contains(subject)))
                idbooks.add(sp.getBookId());//add book to the list if he stand to all the conditions
        }
        for (int id:idbooks) {

            for (SupplierBook sp : supplierBookList()) {//for each book check all the conditions
                if (sp.getBookId() == id && (price == 0L || sp.getPrice() <= price)
                        && (condition == null || condition == BookCondition.ALL_CONDITIONS || sp.getBookCondition() == condition))
                    SPbooks.add(sp);//add book to the list if he stand to all the conditions
            }
        }return SPbooks;//return list of all the book that stands to the buyers conditions
    }


    @Override
    public ArrayList<Book> bookList() {
        ArrayList<Subject> subject = new ArrayList<Subject>();
        subject.add(Subject.KIDS);
        Book a = new Book("Anne of Green Gables", "Lucy Maud Montgomery", 1908 , "shiran", 100, "very good", subject, "http://www.tourismpei.com/media/wp-content/uploads/2014/07/2012-05-anne-of-green-gables-book.jpg");
        Book b = new Book("harry potter", "j.k rolling",1997 , "efrat", 80, "very good", subject, "http://vaguelycircular.com/wp-content/uploads/sites/7/2015/08/harry-potter-11.jpg");
        books.add(a);
        books.add(b);
        ArrayList<Book> bookList = new ArrayList<Book>(books);
        return bookList;
    }

    @Override
    public void addSupplier(final Supplier supplier) {
        if (!suppliers.contains(supplier.getId())) {
            suppliers.add(supplier);
        }
        else {

            new AlertDialog.Builder(context)
                    .setIcon(android.R.drawable.ic_dialog_alert)
                    .setTitle("ERROR")
                    .setMessage("upplier already exist on the system. Would you like to update supplier?")
                    .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            updateSupplier(supplier);
                            Intent intent = new Intent(context, UpdateSupplierActivity.class);
                            intent.putExtra("user_id", supplier.getId());
                            intent.putExtra("user", 2);
                            context.startActivity(intent);

                        }
                    })
                    .setNegativeButton("No", null)
                    .setIcon(android.R.drawable.ic_dialog_info)
                    .show();
            return;
            }
        }

    @Override
    @RolesAllowed({"Manager", "Supplier"})
    public void updateSupplier(Supplier supplier) {

        int index = -1;

        if (!supplierList().contains(supplier.getId())) {
            System.out.println("ERROR:supplier does not exist in the system. Would you like to add new supplier? ");
            char c;
            boolean flag = false;
            do {
                System.out.println("press y for yes and n for no");
                c = in.next().charAt(0);//get user input
                if (c != 'y' || c != 'n')//if input is not valid choice
                {
                    System.out.println("ERROR: wrong input. Please use valid input:");
                } else
                    flag = true;
            }
            while (flag == false);//if input is wrong repeat request for valid value and check again
            switch (c) {
                case 'y'://in case customer chose y meaning he wants to add new supplier
                    addSupplier(supplier);//send to function to add supplier
                    break;
                case ('n'):
                    break;
            }
        } else {

            for (Supplier s:supplierList()) {
                if (s.getId() == supplier.getId())
                    index = supplierList().indexOf(s);
            }
            suppliers.set(index, supplier);//replacing supplier with the updated version

        }
    }

    @Override
    @RolesAllowed({"Manager", "Supplier"})
    public void deleteSupplier(Supplier supplier) {
        if (suppliers.contains(supplier.getId()))
            suppliers.remove(supplier);
        else
            System.out.println("ERROR: supplier does not exist on the system");
    }

    @Override
    public static Supplier searchSupplier(String password) {
        for (Supplier s : supplierList()) {
            if (s.getPassword() == password) {
                return s;
            }
        }

        return null;
    }

    @Override
    public static ArrayList<SupplierBook> supplierBookList(long supplierID) {
        //return a list of all the books this supplier supplies
        ArrayList<SupplierBook> supplierbooks = new ArrayList<>();
        // supplierBooks.stream().filter(supplierBook -> supplierBook.getSupplierId() == supplierID).collect(Collectors.toList());
        return supplierbooks;
    }

    @Override
    public ArrayList<Supplier> supplierList() {
        ArrayList<Supplier> supplierList = new ArrayList<Supplier>(suppliers);
        return supplierList;
    }

    @Override
    @RolesAllowed({"Manager", "Supplier"})
    public void addBookSupplier(final SupplierBook supplierBook) {//throws AddressException {

        if (!supplierBooks.contains(supplierBook.getBookId())) {
            supplierBooks.add(supplierBook);
            String bookName = "";
            ArrayList<String> bookFound = null;
            //search for customer who wait for this book
            for (BookSearch sb:bookSearchList()) {
                if (sb.getBookId() == supplierBook.getBookId() && (sb.getBookCondition() == BookCondition.ALL_CONDITIONS || sb.getBookCondition() == supplierBook.getBookCondition()) && sb.getMaxPrice() <= supplierBook.getPrice())
                    bookFound.add(sb.getCustomerEmail());
            }

            for (Book book:bookList()) {
               if (supplierBook.getBookId() == book.getBookId())
                   bookName = book.getBookName();
            }
           //set email's subject
            String subject = "Update on your book request";
            //set email's text
            String text = "We found a book that matches your book request:\n" + "Book name: " + bookName + ".\n" + "Book condition: " +supplierBook.getBookCondition() +".\n"
                    +"Book price: " + supplierBook.getPrice();
            //send emails to all customers who wait for this book
            SendEmail(bookFound, subject,text);
        }
        else {

            new AlertDialog.Builder(context)
                    .setIcon(android.R.drawable.ic_dialog_alert)
                    .setTitle("ERROR")
                    .setMessage("supplier book already exist on the system. Would you like to update book?")
                    .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            updateBookSupplier(supplierBook);
                            Intent intent = new Intent(context, UpdateBookSupplier.class);
                            intent.putExtra("book_id", supplierBook.getId());
                            intent.putExtra("user_id", supplierBook.getSupplierId());
                            intent.putExtra("user", 2);
                            context.startActivity(intent);

                        }
                    })
                    .setNegativeButton("No", null)
                    .setIcon(android.R.drawable.ic_dialog_info)
                    .show();
            return;
        }
    }

    @Override
    @RolesAllowed({"Manager", "Supplier"})
    public void updateBookSupplier(SupplierBook supplierBook) {//throws AddressException {

        for (SupplierBook sp:supplierBookList()) {
            if (supplierBook.getBookId() == sp.getBookId())
            {
                int indexl = supplierBooks.indexOf(sp);
                sp.setBookCondition(supplierBook.getBookCondition());
                sp.setCopies(supplierBook.getCopies());
                sp.setPrice(supplierBook.getPrice());
                supplierBooks.set(indexl, sp);
            }
        }

        List<String> bookFound= new ArrayList<>();
        //search for customer who wait for this book
        for (BookSearch bs : bookSearchList()) {
            if (bs.getBookId() == supplierBook.getBookId() && bs.getBookCondition() == supplierBook.getBookCondition()
                    && bs.getMaxPrice() >= supplierBook.getPrice())
            {
                bookFound.add(bs.getCustomerEmail());
            }
        }

        String bookName = null;
        for (Book sp:bookList()) {
            if (supplierBook.getBookId() == sp.getBookId())
            {
                bookName = sp.getBookName();
            }
        }
            //set email's subject
            String subject = "Update on your book request";
            //set email's text
            String text = "We found a book that matches your book request:\n" + "Book name: " + bookName + ".\n" + "Book condition: " +supplierBook.getBookCondition() +".\n"
                    +"Book price: " + supplierBook.getPrice();
            //send emails to all customers who wait for this book

            SendEmail(bookFound, subject,text);

    }

    @Override
    @RolesAllowed({"Manager", "Supplier"})
    public void deleteBookSupplier(SupplierBook supplierBook) {
        if (!supplierBooks.contains(supplierBook.getId()))
            System.out.println("ERROR: book does not exist in the system");
        else
            supplierBooks.remove(supplierBook);
    }

    @Override
    public ArrayList<SupplierBook> supplierBookList() {    //return clone of the supplierBookList
        SupplierBook sb = new SupplierBook(1001,536453678,3,60.7,BookCondition.USED);
        SupplierBook sb1 = new SupplierBook(1002,536453678,3,67.7,BookCondition.USED);
        supplierBooks.add(sb);
        supplierBooks.add(sb1);
        ArrayList<SupplierBook> supplierBookList = new ArrayList<SupplierBook>(supplierBooks);
        return supplierBookList;
    }

    @Override
    public void addOrder(Order order) {
        //not making validation because input is auto
        orders.add(order);
    }

    @Override
    public void deleteOrder(Order order) {
        if (!orders.contains(order.getOrderId()))
            System.out.println("ERROR: order does not exist on the system");
        else
            orders.remove(order);
    }

    @Override
    @RolesAllowed({"Manager"})
    public static ArrayList<Order> orderListByDate(Date from, Date to) {
        ArrayList<Order> ordersByDate = new ArrayList<>();
        for (Order o:orders ){
           if (o.getOrderDate().before(to) && o.getOrderDate().after(from))
               ordersByDate.add(o);
        }
        return ordersByDate;
    }

    @Override
    @RolesAllowed({"Manager", "Buyer"})
    public static ArrayList<Order> customerOrderListByDate(Date from, Date to, Long customerId) {

        ArrayList<Order> customerOrdersByDate = new ArrayList<>();
        for (Order o:orders ){
            if (o.getOrderDate().before(to) && o.getOrderDate().after(from) && o.getCustomerId() == customerId)
                customerOrdersByDate.add(o);
        }
        return customerOrdersByDate;
    }

    @Override
    @RolesAllowed({"Manager", "Supplier"})
    public static ArrayList<Order> SupplierOrderListByDate(Date from, Date to, Long supplierId) {
        ArrayList<Order> customerOrdersByDate = new ArrayList<>();
        for (Order o:orders ){
            if (o.getOrderDate().before(to) && o.getOrderDate().after(from) && o.getSupplierId().contains(supplierId))
                customerOrdersByDate.add(o);
        }
        return customerOrdersByDate;
    }

    @Override
    @RolesAllowed("Manager")
    public ArrayList<Order> orderList() {
        ArrayList<Order> orderList = new ArrayList<Order>(orders);
        return orderList;
    }

    @Override
    @RolesAllowed({"Manager", "Supplier"})
    public static ArrayList<Order> SupplierorderList(long supplierId)//return list of all orders from supplier in descending order(by date)
    {
        ArrayList<Order> supplierOrders = new ArrayList<>();
        for (Order order : orderList()) {
            if (order.getSupplierId().contains(supplierId))
                supplierOrders.add(order);
        }
        Collections.sort(supplierOrders, Collections.reverseOrder());
        return supplierOrders;
    }

    @Override
    @RolesAllowed({"Manager", "Buyer"})
    public static ArrayList<Order> BuyerOrderList(long buyerId)//return list of all buyer orders in descending order (by date)
    {
        ArrayList<Order> buyerOrders = new ArrayList<>();
        for (Order order : orderList()) {
            if (order.getCustomerId() == buyerId)
                buyerOrders.add(order);
        }
        Collections.sort(buyerOrders, Collections.reverseOrder());
        return buyerOrders;
    }

    @Override
    @RolesAllowed("Manager")
    public static double expenses(Date from, Date to)//return all the expenses un the requested time frame(the supplier gets 85% of the book price)
    {
        //calculate the total payments sum in the requested time frame
        double expensesSum = 2; //orders.stream().filter(o -> from != null && to != null && o.getOrderDate().after(from) && o.getOrderDate().before(to)).map(o -> o.getPaymentSum()).collect(Collectors.counting());
        //calculate the total sum the store payed the suppliers in the requested time frame
        return expensesSum * 0.85;
    }

    @Override
    @RolesAllowed("Manager")
    public static double profit(Date from, Date to) {
        //calculate the total payments sum in the requested time frame
        double profitSum = 2;//orders.stream().filter(o -> from != null && to != null && o.getOrderDate().after(from) && o.getOrderDate().before(to)).map(o -> o.getPaymentSum()).collect(Collectors.counting());
        //calculate the store total profits sum during the requested time frame
        return profitSum;
    }

    @Override
    public void addBookSearch(final BookSearch bookSearching) {

        //check if there is a book that meets all client requests
        for (SupplierBook sb:supplierBookList()) {
            if (sb.getBookId() == bookSearching.getBookId() && (bookSearching.getBookCondition() == BookCondition.ALL_CONDITIONS || sb.getBookCondition() == bookSearching.getBookCondition()) && sb.getPrice() <= bookSearching.getMaxPrice())
            {
                new AlertDialog.Builder(context)
                        .setIcon(android.R.drawable.ic_dialog_alert)
                        .setTitle("Book Found!")
                        .setMessage("We found a book that meets you requirements. You are now being transferred to book searching")
                        .setPositiveButton("OK", null)
                        .setIcon(android.R.drawable.ic_dialog_info)
                        .show();
                for (Book b : books) {
                    if (b.getBookId() == bookSearching.getBookId()) {
                        Intent intent = new Intent(this.context, SupplierBooksActivity.class);
                        intent.putExtra("book_id", b.getBookId());
                        intent.putExtra("condition", String.valueOf(bookSearching.getBookCondition()));
                        intent.putExtra("price", bookSearching.getMaxPrice());
                        intent.putExtra("request", 1);
                        context.startActivity(intent);
                    }
                }
                return;
            }
        }

        //check if user already put a request for the same book if so asks user if he ant to update his request
        for (BookSearch bs:bookSearchList()) {
            if (bs.getCustomerId() == bookSearching.getCustomerId() && bookSearching.getBookId() == bs.getBookId()) {

                new AlertDialog.Builder(context)
                        .setIcon(android.R.drawable.ic_dialog_alert)
                        .setTitle("ERROR")
                        .setMessage("book is already in the search book list, would you like to update the search details?")
                        .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                Intent intent = new Intent(context, UpdateRequest.class);
                                intent.putExtra("request_id", bookSearching.getBookSearchId());
                                context.startActivity(intent);
                                intent.putExtra("request", 1);
                            }
                        })
                        .setNegativeButton("No", null)
                        .setIcon(android.R.drawable.ic_dialog_info)
                        .show();
                return;

            }

            for (SupplierBook sb:supplierBookList()) {//check if there is a book that meets the customer requirements but his price is a bit higher than his max price (max 2 dollars over the requested price)
                if (sb.getBookId() == bookSearching.getBookId() && (bookSearching.getBookCondition() == BookCondition.ALL_CONDITIONS || sb.getBookCondition() == bookSearching.getBookCondition()) && sb.getPrice() <= (bookSearching.getMaxPrice() + 2.0)) {

                    new AlertDialog.Builder(context)
                            .setIcon(android.R.drawable.ic_dialog_alert)
                            .setTitle("ERROR")
                            .setMessage("We found a book that meets you requirements but his price is a bit higher than your max price (max 2 dollars over your requested price)" +
                                    " would you like to repeat your search with a higher max price?")
                            .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    for (Book b : books) {
                                        if (b.getBookId() == bookSearching.getBookId()) {
                                            Intent intent = new Intent(context, SupplierBooksActivity.class);
                                            intent.putExtra("book_id", b.getBookId());
                                            intent.putExtra("condition", String.valueOf(bookSearching.getBookCondition()));
                                            intent.putExtra("price", bookSearching.getMaxPrice());
                                            intent.putExtra("request", 1);
                                            context.startActivity(intent);
                                            return;
                                        }
                                    }
                                }
                            })
                            .setNegativeButton("No", null)
                            .setIcon(android.R.drawable.ic_dialog_info)
                            .show();
                }
                }
            //check if there is a book that meets the customer requirements but his price is a bit higher than his max price (max 2 dollars over the requested price)
                //add book search
                bookSearchings.add(bookSearching);
        }
    }

    @Override
    public void updateBookSearch(BookSearch bookSearching) {

        if (!bookSearchList().contains(bookSearching.getBookSearchId()))//if list of all books requests does not contain requested search id
            System.out.println("ERROR: book does not exist in the system");
        //check if there is a book that meets all client requests
        for (BookSearch bs:bookSearchList()) {
            if (bs.getCustomerId() == bookSearching.getCustomerId() && bookSearching.getBookId() == bs.getBookId()) {
                new AlertDialog.Builder(context)
                        .setIcon(android.R.drawable.ic_dialog_alert)
                        .setTitle("Book Found!")
                        .setMessage("We found a book that meets you requirements. You are now being transferred to book searching")
                        .setPositiveButton("OK", null)
                        .setIcon(android.R.drawable.ic_dialog_info)
                        .show();
                for (Book b : books) {
                    if (b.getBookId() == bookSearching.getBookId()) {
                        Intent intent = new Intent(this.context, SupplierBooksActivity.class);
                        intent.putExtra("book_id", b.getBookId());
                        intent.putExtra("condition", String.valueOf(bookSearching.getBookCondition()));
                        intent.putExtra("price", bookSearching.getMaxPrice());
                        intent.putExtra("request", 1);
                        context.startActivity(intent);
                    }
                }
                return;
            }
        }
         //can update only selected fields
        for (BookSearch bs:bookSearchings) {
            if(bs.getBookSearchId() == bookSearching.getBookSearchId()) {
                int indexl = bookSearchings.indexOf(bs);
                bs.setBookCondition(bookSearching.getBookCondition());
                bs.setMaxPrice(bookSearching.getMaxPrice());
                bs.setSendMessage(bookSearching.isSendMessage());
                bookSearchList().set(indexl, bs);
            }
        }
        }

    @Override
    public void deleteBookSearch(BookSearch bookSearching) {
        if (!bookSearchings.contains(bookSearching.getBookSearchId()))
            System.out.println("ERROR: book does not exist in the system");
        else
            bookSearchings.remove(bookSearching);
    }

    @Override
    public ArrayList<BookSearch> bookSearchList() {
        ArrayList<BookSearch> bookSearchList = new ArrayList<>(bookSearchings);
        return bookSearchList;
    }

    @Override
    public static ArrayList<BookSearch> authorBookRequestList(String name, String author)//search bookSearch(requests) by author and/or book name
    {
        ArrayList<Integer> booksId = new ArrayList<Integer>();//new list that will contain all the book id's that stand to the query terms
        ArrayList<BookSearch> requests = new ArrayList<BookSearch>();
        for (Book b : bookList()) {
            if (name == null || name == "" || name == b.getBookName() && (author == null || author == "" || author == b.getAuthor()))
                booksId.add(b.getBookId());
        }
        for (BookSearch r : bookSearchList()) {//for each request check if the requested book id match one of the id's that match the query
            for (Integer i : booksId) {
                if (r.getBookId() == i)
                    requests.add(r);
            }
        }
        return requests;
    }

    @Override
    public static void reminder() {

        Calendar calNow = Calendar.getInstance();
        calNow.add(Calendar.MONTH, -1);
        List<String> needReminder = new ArrayList<>();
        //find all customer that placed their search over a month ago and collect their emails
        for (BookSearch bs:bookSearchings) {
            if (bs.getDateOfRequest().equals(calNow.getTime())|| bs.getDateOfRequest().before(calNow.getTime()))
                needReminder.add(bs.getCustomerEmail());
        }
        String subject = "Reminder for your book request";
        String text = " Reminder: Over a month passed since you placed your book request. You may want to update your request details in order to find more matches";
        SendEmail(needReminder,subject,text);

    }

    @Override
    public void addComplains(Complains complains) {
        //check if complainant already complained about defendant
        /*
        Complains checkComplaint = (Complains) complainses.stream().filter(c -> c.getComplainantId() == complains.getComplainantId() && c.getDefendantId() == complains.getDefendantId());
        if (checkComplaint != null)//if complainant already complained about defendant
        {
            System.out.print("ERROR: you already filed a complaint about this person, would you like to update the complaint details, add a new complaint or do nothing?");
            char c;
            boolean flag = false;
            do {
                System.out.println("press u for update ,n for new and x for nothing");
                c = in.next().charAt(0);//get user input
                if (c != 'u' || c != 'n' || c != 'x')//if input is not valid choice
                {
                    System.out.println("ERROR: wrong input. Please use valid input:");
                } else
                    flag = true;
            } while (flag == false);//if input is wrong repeat request for valid value and check again
            switch (c) {
                case 'u'://in case customer chose u meaning he wants to update complaint
                    updateComplains(complains);//send to function to update
                    return;//exit function
                case ('n')://in case customer chose n meaning he wants to file a new complaint
                    break;
                case ('x')://in case customer chose x meaning he wants to do nothing
                    return;//exit function
            }
            complainses.add(complains);
        }*/
    }

    @Override
    public void updateComplains(Complains complains) {
        if (!complainses.contains(complains.getComplaintId()))//if complain list does not contain this complaint
        {
            System.out.println("ERROR: complain does not exist in the system. Would you like to add new complaint?");
            char c;
            boolean flag = false;
            do {
                System.out.println("press y for yes and n for no");
                c = in.next().charAt(0);//get user input
                if (c != 'y' || c != 'n')//if input is not valid choice
                {
                    System.out.println("ERROR: wrong input. Please use valid input:");
                } else
                    flag = true;
            }
            while (flag == false);//if input is wrong repeat request for valid value and check again
            switch (c) {
                case 'y'://in case customer chose y meaning he wants to add new complaint
                    addComplains(complains);//send to function to add complaint
                    break;
                case ('n'):
                    break;
            }
        } else //can update only selected fields
        {/*
            Complains complain = (Complains) complainses.stream().filter(c -> c.getComplaintId() == complains.getComplaintId());
            int indexl = complainses.indexOf((Complains) complainsList().stream().filter(c -> c.getComplaintId() == complains.getComplaintId()));
            complain.setComplaint(complains.getComplaint());
            complain.setImage(complains.getImage());
            complainses.set(indexl, complain);//replace the current complaint with the updated one*/
        }
    }

    @Override
    public void deleteComplains(Complains complains) {
        if (!complainses.contains(complains.getComplaintId()))//if complain list does not contain this complaint
            System.out.println("ERROR: complain does not exist in the system");
        else
            complainses.remove(complains);//delete complaint
    }

    @Override
    public ArrayList<Complains> complainsList() {
        Complains c1 = new Complains(123435366,342536475,"very upset");
        Complains c2 = new Complains(564756748,887354635,"bad book!");
        complainses.add(c1);
        complainses.add(c2);
        ArrayList<Complains> complainsList = new ArrayList<>(complainses);
        return complainsList;
    }

    //not sure this func is needed
    @Override
    public static ArrayList<Complains> complainsUserList(long userID) // user complaints
    {
        ArrayList<Complains> buyerComplaints = new ArrayList<>();
        for (Complains c : complainsList()) {
            if (c.getComplainantId() == userID)
                buyerComplaints.add(c);
        }
        return buyerComplaints;
    }

    //not sure this func is needed
    @Override
    public static ArrayList<Complains> complainsAboutUserList(long userID)//complaints about user
    {
        ArrayList<Complains> ComplaintsAboutBuyer = new ArrayList<>();
        for (Complains c : complainsList()) {
            if (c.getDefendantId() == userID)
                ComplaintsAboutBuyer.add(c);
        }
        return ComplaintsAboutBuyer;
    }

    @Override
    public static ArrayList<Complains> complainantDefendant(long complainantID, long defendantID)//all the complaints about specific user or from specific user or from specific user about another specific user
    {
        ArrayList<Complains> C = new ArrayList<>();
        for (Complains c : complainsList()) {
            if (complainantID == 0 || complainantID == 0L || c.getComplainantId() == complainantID && defendantID == 0 || defendantID == 0L || c.getDefendantId() == defendantID)
                C.add(c);
        }
        return C;
    }

    @Override
    @RolesAllowed("Manager")
    public void updateManager(Manager manager) {
        this.manager.setFirstName(manager.getFirstName());
        this.manager.setHouseNumber(manager.getHouseNumber());
        this.manager.setStreet(manager.getStreet());
        this.manager.seteMail(manager.geteMail());
        this.manager.setCity(manager.getCity());
        this.manager.setZipCode(manager.getZipCode());
        this.manager.setBuildingNumber(manager.getBuildingNumber());
        this.manager.setLastName(manager.getLastName());
        this.manager.setPassword(manager.getPassword());
        this.manager.setPhoneNumber(manager.getPhoneNumber());
    }

    @Override
    public static void SendEmail(List<String> emails, String subject, String text) {//throws AddressException {

/*
        // Sender's email ID
        String from = "web@gmail.com";

        // sending email from localhost
        String host = "localhost";

        // Get system properties
        Properties properties = System.getProperties();

        // Setup mail server
        properties.setProperty("mail.smtp.host", host);

        // Get the default Session object.
        javax.mail.Session session = javax.mail.Session.getDefaultInstance(properties);

        try {
            // Create a default MimeMessage object.
            javax.mail.internet.MimeMessage message = new javax.mail.internet.MimeMessage(session);

            // Set From: header field of the header.
            try {
                message.setFrom(new javax.mail.internet.InternetAddress(from));
            } catch (MessagingException e) {
                e.printStackTrace();
            }

            // Set To: header field of the header.
            for (int i = 0; i < emails.size(); i++)
            message.addRecipients(Message.RecipientType.CC,
                    InternetAddress.parse(emails.get(i)));

            // Set Subject: header field
            message.setSubject(subject);

            // Now set the actual message
            message.setText(text);


            // Send message
            Transport.send(message);
            System.out.println("Sent message successfully....");
        } catch (MessagingException mex) {
            mex.printStackTrace();
        }
*/

    }

    public static ArrayList<Long> customersId() {
        ArrayList<Long> customersId = new ArrayList<Long>();
        for (Buyer buyer : buyerList()) {
            customersId.add(buyer.getId());
        }
        for (Supplier supplier : supplierList()) {
            customersId.add(supplier.getId());
        }
        return customersId;
    }

    public static void exitProgram(final Activity activity) {
        new AlertDialog.Builder(activity)
                .setIcon(android.R.drawable.ic_dialog_alert)
                .setTitle("Closing program")
                .setMessage("Are you sure you want to close this app?")
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        activity.finish();
                    }

                })
                .setNegativeButton("No", null)
                .show();
    }

    @Override
    public boolean deleteCartItem(long userId, SupplierBook sb) {
        int index = -1;
        for (ShoppingCartHelper item : supplierBookShoppingCart) {
            if (item.getId() == userId) {
                index = supplierBookShoppingCart.indexOf(item);
                for (SupplierBook spb :item.getSupplierBookListCart()) {
                    if (spb.getId() == sb.getId())
                    {
                        item.getSupplierBookListCart().remove(spb);
                    }
                }
                supplierBookShoppingCart.set(index,item);
                return true;
            }
        }
        return  false;
    }

    @Override
    public ArrayList<ShoppingCartHelper> getCart() {
        // supplierBookShoppingCart.add(sb);
        // supplierBookShoppingCart.add(sb1);
//        if(supplierBookShoppingCart == null) {
//            supplierBookShoppingCart = new ArrayList<SupplierBook>();
//        }
        return supplierBookShoppingCart;
    }

    @Override
    public static ArrayList<SupplierBook> findSuplierBookById(long userId) {
        for (ShoppingCartHelper item : supplierBookShoppingCart) {
            if (item.getId() == userId)
                return item.getSupplierBookListCart();
        }
        return null;
    }

    @Override
    public void addCartItem(long userId, SupplierBook sb) {//find user cart and add supplier book
        ArrayList<SupplierBook> supplierBooks = findSuplierBookById(userId);
        int index = -1;
        for (ShoppingCartHelper item : supplierBookShoppingCart) {
            if (item.getId() == userId) {
                index = supplierBookShoppingCart.indexOf(item);
                item.getSupplierBookListCart().add(sb);
                supplierBookShoppingCart.set(index, item);
            }
        }
    }
}

