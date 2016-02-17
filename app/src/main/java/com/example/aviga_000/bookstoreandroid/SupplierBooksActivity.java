package com.example.aviga_000.bookstoreandroid;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.concurrent.locks.Condition;

import StoreJavaClass.Book;
import StoreJavaClass.BookCondition;
import StoreJavaClass.SupplierBook;
import model.backend.PoolFunctions;
import model.datasource.BackendFactory;
import model.datasource.StoreMySql;

public class SupplierBooksActivity extends NavActivity {

    private ListView mList;
    private SupplierBookAdapter mAdapter;
    Intent intent = null;
    Intent intentRecieve = null;
    final PoolFunctions backend = BackendFactory.getInstance(this);
    StoreMySql _storeMySql = new StoreMySql(this);
    int userType = 0;
    Long userId = 0L;
    int bookId = 0;
    float price = 0;
    Button button = null;
    BookCondition condition = null;
    ArrayList<SupplierBook> _supplierBooks = new ArrayList<SupplierBook>();
    ArrayList <SupplierBook> supplierBookList = new ArrayList<SupplierBook>();
    final Activity activity = this;
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        getLayoutInflater().inflate(R.layout.activity_supplier_books, frameLayout);

        intentRecieve = getIntent();
        userType = intentRecieve.getIntExtra("user", 0);
        userId = intentRecieve.getLongExtra("user_id", 0);
        bookId = intentRecieve.getIntExtra("book_id", 0);

        button = (Button)findViewById(R.id.addSupplierBookBtn);
        if (userType != 2 && userType != 3) {
            button.setVisibility(View.INVISIBLE);
        }

        synchronized (supplierBookList =backend.supplierBookList()) {

            if (_storeMySql.done == false)
            {
                try {
                    supplierBookList.wait(500);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }

            int request = intentRecieve.getIntExtra("request", 0);
            if (request == 1) {//if activity was open from book requests recieve price and condition and find all books that stand for the right criterion
                price = intentRecieve.getFloatExtra("price", 0);
                condition = BookCondition.valueOf(intentRecieve.getStringExtra("condition"));
                ArrayList<Book> _books = backend.books;
                for (Book book : _books) {
                    if (book.getBookId() == bookId) {
                        _supplierBooks = backend.searchBooks(book.getBookName(), book.getAuthor(), null, price, condition);
                        break;
                    }

                }
            } else if (bookId != 0)
                for (SupplierBook sb : supplierBookList) {
                    if (sb.getBookId() == bookId)
                        _supplierBooks.add(sb);
                }
            else if (bookId == 0 && userType == 2)
                for (SupplierBook sb : supplierBookList) {
                    if (sb.getSupplierId() == userId)
                        _supplierBooks.add(sb);
                }

            else _supplierBooks = backend.supplierBookList();

            mList = (ListView) findViewById(R.id.supplierBookslistView);//find supplierbook listView on activity xml
            mAdapter = new SupplierBookAdapter(SupplierBooksActivity.this, _supplierBooks);
            mList.setAdapter(mAdapter);
            mAdapter.notifyDataSetChanged();

            mList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> adapter, View view, int position, long arg) {
                    Intent sbi = new Intent(SupplierBooksActivity.this, ShowBookActivity.class);
                    int supplierBookId = _supplierBooks.get(position).getId();
                    sbi.putExtra("book_id", supplierBookId);
                    sbi.putExtra("user", userType);
                    sbi.putExtra("user_id", userId);
                    startActivity(sbi);
                }
            });
        }

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_my, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public void addRequestClick (View view)
    {
        intent = new Intent(SupplierBooksActivity.this, AddRequestActivity.class);
        intent.putExtra("user_id",intentRecieve.getIntExtra("user_id",0));
        intent.putExtra("book_id", bookId);
        startActivity(intent);
    }

  /*  public void addSupplierBookClick (View view)
    {
        intent = new Intent(SupplierBooksActivity.this, AddRequestActivity.class);
        intent.putExtra("user_id",intentRecieve.getIntExtra("user_id",0));
        intent.putExtra("book_id", bookId);
        startActivity(intent);
    }
*/
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();
        Intent intent = null;
        switch (id) {

            case R.id.home:
                intent = new Intent(this, BooksPoolActivity.class);
                intent.putExtra("user_id", userId);
                intent.putExtra("user", userType);
                break;

            case R.id.bookdirectory:
                intent = new Intent(this, BooksPoolActivity.class);
                intent.putExtra("user_id", userId);
                intent.putExtra("user", userType);
                break;

            case R.id.cart:
                if(userType == 0){
                    new AlertDialog.Builder(this)
                            .setIcon(android.R.drawable.ic_dialog_alert)
                            .setTitle("ERROR")
                            .setMessage("You must be logged-in in order to view cart")
                            .setPositiveButton("OK", null)
                            .setIcon(android.R.drawable.stat_notify_error)
                            .show();
                    break;
                }
                intent = new Intent(this, ShoppingCartActivity.class);
                intent.putExtra("user_id", userId);
                intent.putExtra("user", userType);
                break;
            case R.id.complaint:
                if(userType == 0){
                    new AlertDialog.Builder(this)
                            .setIcon(android.R.drawable.ic_dialog_alert)
                            .setTitle("ERROR")
                            .setMessage("You must be logged-in in order to view complaints")
                            .setPositiveButton("OK", null)
                            .setIcon(android.R.drawable.stat_notify_error)
                            .show();
                    break;
                }
                if (userType == 3)
                    intent = new Intent(this, ManagerComplaintsActivity.class);
                else
                    intent = new Intent(this, ComplaintsActivity.class);
                intent.putExtra("user_id", userId);
                intent.putExtra("user", userType);

                break;
            case R.id.order:
                if(userType == 0){
                    new AlertDialog.Builder(this)
                            .setIcon(android.R.drawable.ic_dialog_alert)
                            .setTitle("ERROR")
                            .setMessage("You must be logged-in in order to view orders")
                            .setPositiveButton("OK", null)
                            .setIcon(android.R.drawable.stat_notify_error)
                            .show();
                    break;
                }
                if (userType == 3)
                    intent = new Intent(this, ManagerOrdersActivity.class);
                intent = new Intent(this, OrdersActivity.class);
                intent.putExtra("user_id", userId);
                intent.putExtra("user", userType);
                break;
            case R.id.request:
                if(userType == 0){
                    new AlertDialog.Builder(this)
                            .setIcon(android.R.drawable.ic_dialog_alert)
                            .setTitle("ERROR")
                            .setMessage("You must be logged-in in order to view requests")
                            .setPositiveButton("OK", null)
                            .setIcon(android.R.drawable.stat_notify_error)
                            .show();
                    break;
                }
                intent = new Intent(this, BookRequestsActivity.class);
                intent.putExtra("user_id", userId);
                intent.putExtra("user", userType);
                break;
            case R.id.update:
                if(userType == 0){
                    new AlertDialog.Builder(this)
                            .setIcon(android.R.drawable.ic_dialog_alert)
                            .setTitle("ERROR")
                            .setMessage("You must be logged-in in order to update information")
                            .setPositiveButton("OK", null)
                            .setIcon(android.R.drawable.stat_notify_error)
                            .show();
                    break;
                }
                if (userType == 3)
                    intent = new Intent(this, UpdateManagerActivity.class);
                if (userType == 2)
                    intent = new Intent(this, UpdateSupplierActivity.class);
                if (userType == 1)
                    intent = new Intent(this, UpdateBuyerActivity.class);
                intent.putExtra("user_id", userId);
                intent.putExtra("user", userType);
                break;
            case R.id.delete:
                if(userType == 0){
                    new AlertDialog.Builder(this)
                            .setIcon(android.R.drawable.ic_dialog_alert)
                            .setTitle("ERROR")
                            .setMessage("You must be logged-in in order to delete user")
                            .setPositiveButton("OK", null)
                            .setIcon(android.R.drawable.stat_notify_error)
                            .show();
                    break;
                }
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
                    intent = new Intent(SupplierBooksActivity.this, UpdateSupplierActivity.class);
                if (userType == 1)
                    intent = new Intent(this, UpdateBuyerActivity.class);
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


