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
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

import StoreJavaClass.Order;
import model.backend.PoolFunctions;
import model.datasource.BackendFactory;

public class OrdersActivity extends NavActivity {

    final PoolFunctions backend = BackendFactory.getInstance(this);
    Intent getIntent = null;
    Intent intent  = new Intent();
    private ListView mListSales;
    private ListView mListPurchase;
    private OrdersAdapter mAdapter;
    OrdersAdapter myAdapater;
    int type;
    Long Id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getLayoutInflater().inflate(R.layout.activity_orders, frameLayout);

        getIntent = getIntent();
        Id = getIntent.getLongExtra("user_id", 0);
        type = getIntent.getIntExtra("user",0);

        if (type == 2) {
            //set list to show all Supplier sales
            myAdapater = new OrdersAdapter(OrdersActivity.this, backend.SupplierorderList(Id));
            ListView salesList = (ListView) findViewById(R.id.saleListView);
            salesList.setAdapter(myAdapater);
            //
            mListSales = (ListView)findViewById(R.id.saleListView);//find user sales listView on activity xml
            addSalesListItems(backend.SupplierorderList(Id));//send to func to set purchaseList list items

        }


        //set list to show all the user purchase
        myAdapater = new OrdersAdapter(this,backend.BuyerOrderList(Id));
        ListView purchaseList = (ListView) findViewById(R.id.purchaseListView);
        purchaseList.setAdapter(myAdapater);
        //

        mListPurchase = (ListView)findViewById(R.id.purchaseListView);//find user purchases listView on activity xml

        addPurchaseListItems(backend.BuyerOrderList(Id));//send to func to set purchaseList list items
    }

    private void addSalesListItems(ArrayList<Order> sales) {//add items to complaints about user and user complaints lists
        mAdapter = new OrdersAdapter(OrdersActivity.this, sales);
        mListSales.setAdapter(mAdapter);}

    private void addPurchaseListItems(ArrayList<Order>  purchase) {//add items to complaints about user and user complaints lists
        mAdapter = new OrdersAdapter(OrdersActivity.this, purchase);
        mListPurchase.setAdapter(mAdapter);
    }

    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();
        Intent intent = null;
        switch (id) {

            case R.id.cart:
                intent = new Intent(OrdersActivity.this, ShoppingCartActivity.class);
                intent.putExtra("user_id", Id);
                intent.putExtra("user", type);
                break;
            case R.id.complaint:
                    intent = new Intent(OrdersActivity.this, ComplaintsActivity.class);
                    intent.putExtra("user_id", Id);
                    intent.putExtra("user", type);

                break;
            case R.id.order:
                intent = new Intent(OrdersActivity.this, OrdersActivity.class);
                intent.putExtra("user_id", Id);
                intent.putExtra("user", type);
                break;
            case R.id.request:
                intent = new Intent(OrdersActivity.this, BookRequestsActivity.class);
                intent.putExtra("user_id", Id);
                intent.putExtra("user", type);
                break;
            case R.id.update:
                if (type == 2)
                    intent = new Intent(OrdersActivity.this, UpdateSupplierActivity.class);
                if (type == 1)
                    intent = new Intent(OrdersActivity.this, UpdateBuyerActivity.class);
                intent.putExtra("user_id", Id);
                intent.putExtra("user", type);
                break;
            case R.id.delete:
                if (type == 2)
                    intent = new Intent(OrdersActivity.this, UpdateSupplierActivity.class);
                if (type == 1)
                    intent = new Intent(OrdersActivity.this, UpdateBuyerActivity.class);
                intent.putExtra("user_id", Id);
                intent.putExtra("user", type);

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

