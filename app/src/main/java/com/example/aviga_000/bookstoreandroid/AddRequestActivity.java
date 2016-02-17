package com.example.aviga_000.bookstoreandroid;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;

import java.util.ArrayList;

import StoreJavaClass.Book;
import StoreJavaClass.BookCondition;
import StoreJavaClass.BookSearch;
import StoreJavaClass.SupplierBook;
import model.backend.PoolFunctions;
import model.datasource.BackendFactory;
import model.datasource.StoreMySql;

public class AddRequestActivity extends AppCompatActivity {

    final PoolFunctions backend = BackendFactory.getInstance(this);
    final StoreMySql _storeMySql = new StoreMySql(this);
    Intent rIntent = null;
    Long id = 0L;
    int bookId = 0;
    ArrayList <SupplierBook> _supplierBooks = new ArrayList<SupplierBook>();
    ArrayList <BookSearch> _bookSearches = new ArrayList<BookSearch>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_request);

        rIntent = getIntent();
        id = rIntent.getLongExtra("user_id", 0);
        bookId = rIntent.getIntExtra("book_id", 0);
        TextView userid = (TextView)findViewById(R.id.customerIdText);
        userid.setText(String.valueOf(id));
        userid.setEnabled(false);
        TextView bookid = (TextView)findViewById(R.id.bookIdText);
        bookid.setText(String.valueOf(bookId));




    }

    public void addNewOnClick (View view) throws InterruptedException {
        int bookId = Integer.parseInt(((EditText) findViewById(R.id.bookIdText)).getText().toString());//get user input book id
        Long buyerId = Long.parseLong(((EditText) findViewById(R.id.customerIdText)).getText().toString());//get from form buyer id
        String email = ((EditText) findViewById(R.id.emailText)).getText().toString();
        BookCondition condition = BookCondition.valueOf(((EditText) findViewById(R.id.conditionAutoCompleteTextView)).getText().toString());
        float maxPrice = Float.parseFloat(((EditText) findViewById(R.id.maxPriceText)).getText().toString());
        boolean send = ((CheckBox)findViewById(R.id.sendMessageCheckBox)).isSelected();//convert checkBox value to boolean value
        final BookSearch request = new BookSearch(bookId, buyerId, email, condition, maxPrice, send);

        final ArrayList <Book> _books = backend.books;
        synchronized (_supplierBooks = backend.supplierBookList()) {
            if (_storeMySql.done == false)
            {
                _supplierBooks.wait(500);
            }

            //check if there is a book that meets all client requests
            for (SupplierBook sb:_supplierBooks) {
                if (sb.getBookId() == request.getBookId() && (request.getBookCondition().equals (BookCondition.ALL_CONDITIONS) || sb.getBookCondition().equals(request.getBookCondition())) && sb.getPrice() <= request.getMaxPrice())
                {
                    new AlertDialog.Builder(this)
                            .setIcon(android.R.drawable.ic_dialog_alert)
                            .setTitle("Book Found!")
                            .setMessage("We found a book that meets you requirements. You are now being transferred to book searching")
                            .setPositiveButton("OK", null)
                            .setIcon(android.R.drawable.ic_dialog_info)
                            .show();
                    for (Book b : _books) {
                        if (b.getBookId() == request.getBookId()) {
                            Intent intent = new Intent(AddRequestActivity.this, SupplierBooksActivity.class);
                            intent.putExtra("book_id", b.getBookId());
                            intent.putExtra("condition", String.valueOf(request.getBookCondition()));
                            intent.putExtra("price", request.getMaxPrice());
                            intent.putExtra("request", 1);
                            startActivity(intent);
                        }
                    }
                    return;
                }
            }
        }
        synchronized (_bookSearches = backend.bookSearchList()) {
            if (_storeMySql.done == false)
            {
                _bookSearches.wait(500);
            }

            //check if user already put a request for the same book if so asks user if he ant to update his request
            for (BookSearch bs: _bookSearches) {
                if (bs.getCustomerId() == request.getCustomerId() && request.getBookId() == bs.getBookId()) {

                    new AlertDialog.Builder(this)
                            .setIcon(android.R.drawable.ic_dialog_alert)
                            .setTitle("ERROR")
                            .setMessage("book is already in the search book list, would you like to update the search details?")
                            .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    Intent intent = new Intent(AddRequestActivity.this, UpdateRequest.class);
                                    intent.putExtra("request_id", request.getBookSearchId());
                                    startActivity(intent);
                                    intent.putExtra("request", 1);
                                }
                            })
                            .setNegativeButton("No", null)
                            .setIcon(android.R.drawable.ic_dialog_info)
                            .show();
                    return;

                }

                for (SupplierBook sb:_supplierBooks) {//check if there is a book that meets the customer requirements but his price is a bit higher than his max price (max 2 dollars over the requested price)
                    if (sb.getBookId() == request.getBookId() && (request.getBookCondition().equals(BookCondition.ALL_CONDITIONS) || sb.getBookCondition().equals(request.getBookCondition())) && sb.getPrice() <= (request.getMaxPrice() + 2.0)) {

                        new AlertDialog.Builder(this)
                                .setIcon(android.R.drawable.ic_dialog_alert)
                                .setTitle("ERROR")
                                .setMessage("We found a book that meets you requirements but his price is a bit higher than your max price (max 2 dollars over your requested price)" +
                                        " would you like to repeat your search with a higher max price?")
                                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        for (Book b : _books) {
                                            if (b.getBookId() == request.getBookId()) {
                                                Intent intent = new Intent(AddRequestActivity.this, SupplierBooksActivity.class);
                                                intent.putExtra("book_id", b.getBookId());
                                                intent.putExtra("condition", String.valueOf(request.getBookCondition()));
                                                intent.putExtra("price", request.getMaxPrice());
                                                intent.putExtra("request", 1);
                                                startActivity(intent);
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
                backend.bookSearchings.add(request);
            }
        }

        backend.addBookSearch(request);//send to func to add request
    }
}
