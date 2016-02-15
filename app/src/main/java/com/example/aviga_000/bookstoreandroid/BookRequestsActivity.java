package com.example.aviga_000.bookstoreandroid;

import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
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

public class BookRequestsActivity extends NavActivity {

    final PoolFunctions _backend = BackendFactory.getInstance(this);
    Intent intent  = new Intent();
    private static final List<String> bookNames = new ArrayList<String>();
    private static final List<String> authorsNames = new ArrayList<String>();
    private ListView mList;
    private ArrayAdapter<BookSearch> mAdapter;
    Intent intentRecieve = null;
    int userType = 0;
    Long userId = 0L;
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        intentRecieve = getIntent();
        userType = intentRecieve.getIntExtra("user", 0);
        userId = intentRecieve.getLongExtra("user_id", 0);

        getLayoutInflater().inflate(R.layout.activity_book_requests, frameLayout);


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

    public void onSearchClick(View view)
    {
        String name = ((EditText) findViewById(R.id.nameAutoCompleteTextView)).getText().toString();//get user input
        String author = ((EditText) findViewById(R.id.authorAutoCompleteTextView)).getText().toString();//get user input
        //send to func to find all the query resualt
        ArrayList<BookSearch> requests = _backend.authorBookRequestList(name, author);//send to func to find all the query result and save them in an array
        addListItems(requests);//set list items as the query results

    }
    public  void newRequestClick(View view)
    {
        intent = new Intent(this, AddRequestActivity.class);
        intent.putExtra("user_id", userId);
        intent.putExtra("user", userType);
        startActivity(intent);

    }

    public  void allClick(View view)
    {
        ArrayList<BookSearch> requests = _backend.bookSearchList();//get all requests
        addListItems(requests);//send to func to set requests list items
    }

    public  void myRequestsClick(View view)
    {
    //change to show only customer requests
        ArrayList<BookSearch> requests = _backend.bookSearchList();//get all requests
        addListItems(requests);//send to func to set requests list items
    }

    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();
        Intent intent = null;
        switch (id) {

            case R.id.home:
                intent = new Intent(BookRequestsActivity.this, BooksPoolActivity.class);
                intent.putExtra("user_id", userId);
                intent.putExtra("user", userType);
                break;

            case R.id.bookdirectory:
                intent = new Intent(BookRequestsActivity.this, BooksPoolActivity.class);
                intent.putExtra("user_id", userId);
                intent.putExtra("user", userType);
                break;

            case R.id.cart:
                intent = new Intent(BookRequestsActivity.this, ShoppingCartActivity.class);
                intent.putExtra("user_id", userId);
                intent.putExtra("user", userType);
                break;
            case R.id.complaint:
                if (userType == 3)
                    intent = new Intent(BookRequestsActivity.this, ManagerComplaintsActivity.class);
                else
                    intent = new Intent(BookRequestsActivity.this, ComplaintsActivity.class);
                intent.putExtra("user_id", userId);
                intent.putExtra("user", userType);

                break;
            case R.id.order:
                if (userType == 3)
                    intent = new Intent(BookRequestsActivity.this, ManagerOrdersActivity.class);
                intent = new Intent(BookRequestsActivity.this, OrdersActivity.class);
                intent.putExtra("user_id", userId);
                intent.putExtra("user", userType);
                break;
            case R.id.request:
                intent = new Intent(BookRequestsActivity.this, BookRequestsActivity.class);
                intent.putExtra("user_id", userId);
                intent.putExtra("user", userType);
                break;
            case R.id.update:
                if (userType == 3)
                    intent = new Intent(BookRequestsActivity.this, UpdateManagerActivity.class);
                if (userType == 2)
                    intent = new Intent(BookRequestsActivity.this, UpdateSupplierActivity.class);
                if (userType == 1)
                    intent = new Intent(BookRequestsActivity.this, UpdateBuyerActivity.class);
                intent.putExtra("user_id", userId);
                intent.putExtra("user", userType);
                break;
            case R.id.delete:
                if (userType == 3) {
                    new AlertDialog.Builder(this)
                            .setIcon(android.R.drawable.ic_dialog_alert)
                            .setTitle("ERROR")
                            .setMessage("manager can't be deleted")
                            .setPositiveButton("OK", null)
                            .setIcon(android.R.drawable.stat_notify_error)
                            .show();
                    break;
                }
                if (userType == 2)
                    intent = new Intent(BookRequestsActivity.this, UpdateSupplierActivity.class);
                if (userType == 1)
                    intent = new Intent(BookRequestsActivity.this, UpdateBuyerActivity.class);
                intent.putExtra("user_id", userId);
                intent.putExtra("user", userType);

                break;
            default:
                break;
        }
        if (intent != null)
            startActivity(intent);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;

    }}
