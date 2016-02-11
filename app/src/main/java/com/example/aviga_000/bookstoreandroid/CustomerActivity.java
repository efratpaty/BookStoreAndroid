package com.example.aviga_000.bookstoreandroid;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;

import model.backend.PoolFunctions;
import model.datasource.BackendFactory;


public class CustomerActivity extends AppCompatActivity {

    final PoolFunctions backend = BackendFactory.getInstance(this);
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_customer);

    }
    public void openBookPool(View view)
    {
        Intent openBookPoolIntent = new Intent(this, BooksPoolActivity.class);
    }
    public void openComplaints(View view)
    {
        Intent openComplaintsIntent = new Intent(this, ComplaintsActivity.class);
    }
    public void openRequests(View view)
    {
        Intent openRequestsIntent = new Intent(this, BookRequestsActivity.class);
    }
    public void openOrders(View view)
    {
        Intent openOrdersIntent = new Intent(this, OrdersActivity.class);
    }
    public void openCart(View view)
    {
        Intent openCartIntent = new Intent(this, ShoppingCartActivity.class);
    }
    public void exit() {
         backend.exitProgram(this);
    }

        @Override
        public void onBackPressed() {
        backend.exitProgram(this);
    }


}

