package com.example.aviga_000.bookstoreandroid;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.EditText;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

import StoreJavaClass.Book;
import StoreJavaClass.BookSearch;
import model.backend.PoolFunctions;
import model.datasource.BackendFactory;

public class BookRequestsActivity extends AppCompatActivity {

    final PoolFunctions _backend = BackendFactory.getInstance(this);
    Intent intent  = new Intent();
    private static final List<String> bookNames = new ArrayList<String>();
    private static final List<String> authorsNames = new ArrayList<String>();
    private ListView mList;
    private ArrayAdapter<BookSearch> mAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_book_requests);


        mList = (ListView)findViewById(R.id.RequestsListView);//find requests listView on activity xml
        //needs to change after changing bundle
        ArrayList<BookSearch> requests = _backend.bookSearchList();//get all requests
        addListItems(requests);//send to func to set requests list items

        for (Book book: _backend.bookList()) {
            bookNames.add(book.getBookName());
            authorsNames.add(book.getAuthor());
        }
        //set the book-names autoComplete
        ArrayAdapter<String> bookNameAdapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_dropdown_item_1line, bookNames);
        AutoCompleteTextView nameTextView = (AutoCompleteTextView)
                findViewById(R.id.nameAutoCompleteTextView);
        nameTextView.setAdapter(bookNameAdapter);

        //set the authors-names autoComplete
        ArrayAdapter<String> authorNameAdapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_dropdown_item_1line, authorsNames);
        AutoCompleteTextView authorTextView = (AutoCompleteTextView)
                findViewById(R.id.authorAutoCompleteTextView);
        authorTextView.setAdapter(authorNameAdapter);
    }

    private void addListItems(ArrayList<BookSearch> requestsArray) {//add items to complains list
        mAdapter = new ArrayAdapter<BookSearch>(this, android.R.layout.simple_list_item_1, requestsArray);
        mList.setAdapter(mAdapter);
    }

    protected  void onSearchClick(View view)
    {
        String name = ((EditText) findViewById(R.id.nameAutoCompleteTextView)).getText().toString();//get user input
        String author = ((EditText) findViewById(R.id.authorAutoCompleteTextView)).getText().toString();//get user input
        //send to func to find all the query resualt
        ArrayList<BookSearch> requests = _backend.authorBookRequestList(name, author);//send to func to find all the query result and save them in an array
        addListItems(requests);//set list items as the query results

    }
    protected  void newRequestClick(View view)
    {
        intent = new Intent(this, AddRequestActivity.class);
        startActivity(intent);

    }

    protected  void allClick(View view)
    {
        ArrayList<BookSearch> requests = _backend.bookSearchList();//get all requests
        addListItems(requests);//send to func to set requests list items
    }

    protected  void myRequestsClick(View view)
    {
    //change to show only customer requests
        ArrayList<BookSearch> requests = _backend.bookSearchList();//get all requests
        addListItems(requests);//send to func to set requests list items
    }

}
