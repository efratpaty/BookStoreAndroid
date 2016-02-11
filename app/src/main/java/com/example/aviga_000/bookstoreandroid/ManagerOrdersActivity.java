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
import android.util.TimeUtils;
import android.view.MenuItem;
import android.view.View;
import android.widget.ListView;
import android.widget.TextView;

import java.sql.Time;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import model.backend.PoolFunctions;
import model.datasource.BackendFactory;

public class ManagerOrdersActivity extends NavActivity {

    final PoolFunctions _backend = BackendFactory.getInstance(this);
    TextView expanses = null;
    TextView profits = null;
    TextView toProfit = null;
    TextView fromProfit = null;
    TextView toExpanses = null;
    TextView fromExpanses = null;
    TextView to = null;
    TextView from = null;
    OrdersAdapter mAdapter = null;
    private ListView mList;
    DateFormat df = new SimpleDateFormat("mm/dd/yyyy");
    Intent intent;
    Intent intentRecieve = null;
    Long userId;
    int userType;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        getLayoutInflater().inflate(R.layout.activity_manager_orders, frameLayout);

        intentRecieve = getIntent();
        userType = intentRecieve.getIntExtra("user", 0);
        userId = intentRecieve.getLongExtra("user_id", 0);

        Calendar cal = Calendar.getInstance();
        cal.add(Calendar.DATE, -360);
        Date dateYearAgo = cal.getTime();
        toProfit = (TextView)findViewById(R.id.toProfitText);
        toProfit.setText(String.valueOf(new Date()));
        fromProfit = (TextView)findViewById(R.id.fromProfitText);
        fromProfit.setText(String.valueOf(dateYearAgo));
        toExpanses = (TextView)findViewById(R.id.toExpansesText);
        toExpanses.setText(String.valueOf(new Date()));
        fromExpanses = (TextView)findViewById(R.id.fromExpansesText);
        fromExpanses.setText(String.valueOf(dateYearAgo));
        expanses = (TextView)findViewById(R.id.expansesText);
        expanses.setText(String.valueOf(_backend.expenses(new Date(), dateYearAgo)));
        profits = (TextView)findViewById(R.id.profitText);
        profits.setText(String.valueOf(_backend.profit(new Date(), dateYearAgo)));
        mAdapter = new OrdersAdapter(ManagerOrdersActivity.this, _backend.orders);
        mList = (ListView)findViewById(R.id.ordersListView);
        mList.setAdapter(mAdapter);

    }

    public void onProfitClick (View view) throws ParseException {//get the profit in the specific time frame

        profits.setText((int) _backend.profit(df.parse(fromProfit.toString()),df.parse(toProfit.toString())));
    }

    public void onExpansesClick (View view) throws ParseException {//get the expanses in the specific time frame

        expanses.setText((int) _backend.profit(df.parse(fromExpanses.toString()),df.parse(toExpanses.toString())));
    }

    public void onOrdersSearch (View view) throws ParseException {//display all the orders in the specific time frame
        to =  (TextView)findViewById(R.id.toText);
        from =  (TextView)findViewById(R.id.fromText);
        mAdapter = new OrdersAdapter(ManagerOrdersActivity.this, _backend.orderListByDate(df.parse(from.toString()),df.parse(to.toString())));
        mList.setAdapter(mAdapter);
    }
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();
        Intent intent = null;
        switch (id) {

            case R.id.cart:
                intent = new Intent(ManagerOrdersActivity.this, ShoppingCartActivity.class);
                intent.putExtra("user_id", userId);
                intent.putExtra("user", userType);
                break;
            case R.id.complaint:
                    intent = new Intent(ManagerOrdersActivity.this, ManagerComplaintsActivity.class);
                    intent.putExtra("user_id", userId);
                    intent.putExtra("user", userType);

                break;
            case R.id.order:
                intent = new Intent(ManagerOrdersActivity.this, ManagerOrdersActivity.class);
                intent.putExtra("user_id", userId);
                intent.putExtra("user", userType);
                break;
            case R.id.request:
                intent = new Intent(ManagerOrdersActivity.this, BookRequestsActivity.class);
                intent.putExtra("user_id", userId);
                intent.putExtra("user", userType);
                break;
            case R.id.update:
                intent = new Intent(ManagerOrdersActivity.this, UpdateManagerActivity.class);
                intent.putExtra("user_id", userId);
                intent.putExtra("user", userType);
                break;
            case R.id.delete:
                new AlertDialog.Builder(this)
                        .setIcon(android.R.drawable.ic_dialog_alert)
                        .setTitle("ERROR")
                        .setMessage("manager can't be deleted")
                        .setPositiveButton("OK", null)
                        .setIcon(android.R.drawable.stat_notify_error)
                        .show();
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

