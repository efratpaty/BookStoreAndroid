package com.example.aviga_000.bookstoreandroid;

import android.app.AlertDialog;
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
import model.backend.PoolFunctions;
import model.datasource.BackendFactory;
import model.datasource.StoreMySql;

import static java.lang.String.*;

public class UpdateRequest extends AppCompatActivity {

    final PoolFunctions backend = BackendFactory.getInstance(this);
    ArrayList<BookSearch> _bookSearches = new ArrayList<BookSearch>();
    StoreMySql _storeMySql = new StoreMySql(this);

    Intent rIntent = null;
    Long id = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_request);

        id = rIntent.getLongExtra("user_id", 0);
        TextView customerId = (TextView)findViewById(R.id.customerIdText);
        TextView bookId = (TextView)findViewById(R.id.bookIdText);
        TextView email = (TextView)findViewById(R.id.emailText);
        TextView condition = (TextView)findViewById(R.id.conditionAutoCompleteTextView);
        TextView price = (TextView)findViewById(R.id.maxPriceText);
        CheckBox send = (CheckBox)findViewById(R.id.sendMessageCheckBox);

        ArrayList <BookSearch> _bookSearches = backend.bookSearchings;
        for (BookSearch bs: _bookSearches) {
            if (bs.getBookSearchId() == rIntent.getIntExtra("book_search_id",0))
            {
                customerId.setText(String.valueOf(bs.getCustomerId()));
                customerId.setEnabled(false);
                bookId.setText(String.valueOf(bs.getBookId()));
                bookId.setEnabled(false);
                email.setText(bs.getCustomerEmail());
                condition.setText(String.valueOf(bs.getBookCondition()));
                price.setText(String.valueOf(bs.getMaxPrice()));
                send.setChecked(bs.isSendMessage());
            }
        }


    }

    public void updateOnClick (View view) throws InterruptedException {
        int bookId = Integer.parseInt(((EditText) findViewById(R.id.bookIdText)).getText().toString());//get user input book id
        Long buyerId = Long.parseLong(((EditText) findViewById(R.id.customerIdText)).getText().toString());//get from form buyer id
        String email = ((EditText) findViewById(R.id.emailText)).getText().toString();
        BookCondition condition = BookCondition.valueOf(((EditText) findViewById(R.id.conditionAutoCompleteTextView)).getText().toString());
        float maxPrice = Float.parseFloat(((EditText) findViewById(R.id.maxPriceText)).getText().toString());
        boolean send = ((CheckBox) findViewById(R.id.sendMessageCheckBox)).isSelected();//convert checkBox value to boolean value
        BookSearch request = new BookSearch(bookId, buyerId, email, condition, maxPrice, send);

        ArrayList<Book> _books = backend.books;
        synchronized (_bookSearches = backend.bookSearchList()) {

            if (_storeMySql.done == false)
            {
               _bookSearches.wait(500);
            }
            if (!_bookSearches.contains(request.getBookSearchId()))//if list of all books requests does not contain requested search id
                System.out.println("ERROR: book does not exist in the system");
            //check if there is a book that meets all client requests
            for (BookSearch bs : _bookSearches) {
                if (bs.getCustomerId() == request.getCustomerId() && request.getBookId() == bs.getBookId()) {
                    new AlertDialog.Builder(this)
                            .setIcon(android.R.drawable.ic_dialog_alert)
                            .setTitle("Book Found!")
                            .setMessage("We found a book that meets you requirements. You are now being transferred to book searching")
                            .setPositiveButton("OK", null)
                            .setIcon(android.R.drawable.ic_dialog_info)
                            .show();
                    for (Book b : _books) {
                        if (b.getBookId() == request.getBookId()) {
                            Intent intent = new Intent(this, SupplierBooksActivity.class);
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
            //can update only selected fields
            for (BookSearch bs : _bookSearches) {
                if (bs.getBookSearchId() == request.getBookSearchId()) {
                    int indexl = _bookSearches.indexOf(bs);
                    bs.setBookCondition(request.getBookCondition());
                    bs.setMaxPrice(request.getMaxPrice());
                    bs.setSendMessage(request.isSendMessage());
                    backend.bookSearchings.set(indexl, bs);
                }
            }

            backend.updateBookSearch(request);//send to func to add request
        }
    }
}
