package com.example.aviga_000.bookstoreandroid;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.widget.Toolbar;
import android.text.Layout;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;

import StoreJavaClass.Book;
import StoreJavaClass.BookCondition;
import StoreJavaClass.Subject;
import StoreJavaClass.SupplierBook;
import model.backend.PoolFunctions;
import model.datasource.BackendFactory;
import android.widget.BaseAdapter;

public class BooksPoolActivity extends NavActivity {

    PoolFunctions backend = BackendFactory.getInstance(this);
    private ListView mList;
    private booksAdapter mAdapter;
    private SupplierBookAdapter sAdapter;
    Intent intentRecieve = null;
    Long userId;
    int userType;
    Button button = null;
    Intent intent = new Intent();

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        intentRecieve = getIntent();
        userType = intentRecieve.getIntExtra("user", 0);

        super.onCreate(savedInstanceState);

        getLayoutInflater().inflate(R.layout.activity_books_pool, frameLayout);

        /**
         * Adding our layout to parent class frame layout.
         */
        if (userType != 0)
            getLayoutInflater().inflate(R.layout.activity_books_pool, frameLayout);
        else
            setContentView(R.layout.activity_books_pool);


        button = (Button)findViewById(R.id.newButton);
        if (userType != 3) {
            button.setVisibility(View.INVISIBLE);
        }

        mList = (ListView) findViewById(R.id.bookListView);//find book listView on activity xml

        mAdapter = new booksAdapter(BooksPoolActivity.this, backend.bookList());
        mList.setAdapter(mAdapter);
        mAdapter.notifyDataSetChanged();

        userId = intentRecieve.getLongExtra("user_id", 0);

        mList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapter, View view, int position, long arg) {
                Intent sbi = new Intent(BooksPoolActivity.this, SupplierBooksActivity.class);
                sbi.putExtra("book_id", backend.books.get(position).getBookId());
                sbi.putExtra("user_id", intentRecieve.getLongExtra("user_id", 0));
                sbi.putExtra("user", userType);
                startActivity(sbi);
            }
        });

    }


    public void onSearchBookClick(View view) {//when user click on search icon, send search details to function and display result
        Subject subject = null;
        float price = 0L;
        BookCondition bookCondition = null;
        String nameBook = ((EditText) findViewById(R.id.bookPoolNameText)).getText().toString();//get user input
        String author = ((EditText) findViewById(R.id.bookPoolAuthorText)).getText().toString();//get user input
        String sSubject = ((EditText) findViewById(R.id.bookPoolSubjectTextView)).getText().toString();
        if (!sSubject.equals("") && !sSubject.equals(null) )
            subject = Subject.valueOf(sSubject);//get user input
        String sPrice = ((EditText) findViewById(R.id.bookPoolPriceText)).getText().toString();
        if (!sPrice.equals("") && !sPrice.equals(null) )
            price = Float.parseFloat(sPrice);//get user input
        String sCondition = ((EditText) findViewById(R.id.bookPoolConditiononditionTextView)).getText().toString();
        if (!sCondition.equals("") && !sCondition.equals(null) )
        bookCondition = BookCondition.valueOf(sCondition);//get user input

        //send to func to find all the query resualt
        ArrayList<SupplierBook> searchBooks = (ArrayList<SupplierBook>) backend.searchBooks(nameBook, author, subject, price, bookCondition);//send to func to find all the query result and save them in an array
        sAdapter = new SupplierBookAdapter(BooksPoolActivity.this, searchBooks);//set list items as the query results
        mList.invalidateViews();
        mList.setAdapter(sAdapter);
        sAdapter.notifyDataSetChanged();
    }

    protected void onNewClick(View view) {//on clicking new button open add book activity
        intent = new Intent(BooksPoolActivity.this, AddBookActivity.class);
        startActivity(intent);
    }

    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();
        Intent intent = null;
        switch (id) {

            case R.id.home:
                intent = new Intent(BooksPoolActivity.this, BooksPoolActivity.class);
                intent.putExtra("user_id", userId);
                intent.putExtra("user", userType);
                break;

            case R.id.bookdirectory:
                intent = new Intent(BooksPoolActivity.this, BooksPoolActivity.class);
                intent.putExtra("user_id", userId);
                intent.putExtra("user", userType);
                break;

            case R.id.cart:
                    intent = new Intent(BooksPoolActivity.this, ShoppingCartActivity.class);
                    intent.putExtra("user_id", userId);
                    intent.putExtra("user", userType);
                break;
            case R.id.complaint:
                if (userType == 3)
                    intent = new Intent(BooksPoolActivity.this, ManagerComplaintsActivity.class);
                else
                    intent = new Intent(BooksPoolActivity.this, ComplaintsActivity.class);
                    intent.putExtra("user_id", userId);
                    intent.putExtra("user", userType);

                break;
            case R.id.order:
                if (userType == 3)
                    intent = new Intent(BooksPoolActivity.this, ManagerOrdersActivity.class);
                intent = new Intent(BooksPoolActivity.this, OrdersActivity.class);
                intent.putExtra("user_id", userId);
                intent.putExtra("user", userType);
                break;
            case R.id.request:
                intent = new Intent(BooksPoolActivity.this, BookRequestsActivity.class);
                intent.putExtra("user_id", userId);
                intent.putExtra("user", userType);
                break;
            case R.id.update:
                if (userType == 3)
                intent = new Intent(BooksPoolActivity.this, UpdateManagerActivity.class);
                if (userType == 2)
                    intent = new Intent(BooksPoolActivity.this, UpdateSupplierActivity.class);
                if (userType == 1)
                    intent = new Intent(BooksPoolActivity.this, UpdateBuyerActivity.class);
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
                    intent = new Intent(BooksPoolActivity.this, UpdateSupplierActivity.class);
                if (userType == 1)
                    intent = new Intent(BooksPoolActivity.this, UpdateBuyerActivity.class);
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
