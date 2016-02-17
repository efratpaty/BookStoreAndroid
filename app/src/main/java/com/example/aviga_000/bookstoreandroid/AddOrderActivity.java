package com.example.aviga_000.bookstoreandroid;

import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.devsmart.android.ui.HorizontalListView;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import StoreJavaClass.Order;
import StoreJavaClass.SupplierBook;
import model.backend.PoolFunctions;
import model.datasource.BackendFactory;

public class AddOrderActivity extends AppCompatActivity {

    final PoolFunctions backend = BackendFactory.getInstance(this);
    TextView buyerId;
    TextView creditNumber;
    TextView cvs;
    TextView holdrName;
    TextView holderId;
    TextView orderDate;
    TextView sum;
    Intent intentRecieve = null;
    DateFormat df = new SimpleDateFormat("mm/dd/yyyy");
    Long userId;
    int userType;
    ArrayList<Integer> supplierBookId;
    ArrayList<Long> supplierId;


    boolean flag = false;
    float temp = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_order);


        intentRecieve = getIntent();
        userType = intentRecieve.getIntExtra("user", 0);
        userId = intentRecieve.getLongExtra("user_id", 0);
        supplierBookId = intentRecieve.getIntegerArrayListExtra("book_ids");
        sum = (TextView) findViewById(R.id.paymentText);

        HorizontalListView hListView = (HorizontalListView)findViewById(R.id.hlistview);
        hListView.setAdapter(new HAdapter(this, supplierBookId));

        ArrayList <SupplierBook> _supplierBooks = backend.supplierBooks;
        for (int id : supplierBookId) {

            for (SupplierBook sb : _supplierBooks) {
                if (sb.getId() == id) {
                    if (sb.getCopies() > 0) {

                        flag = true;
                        buyerId = (TextView) findViewById(R.id.customerIdText);
                        buyerId.setText(String.valueOf(userId));
                        temp += sb.getPrice();
                        supplierId.add(sb.getSupplierId());

                    }
                }
            }

            creditNumber = (TextView) findViewById(R.id.creditText);
            cvs = (TextView) findViewById(R.id.cvsText);
            holderId = (TextView) findViewById(R.id.cardHolderIdText);
            holdrName = (TextView) findViewById(R.id.cardHolderNameText);
            orderDate = (TextView) findViewById(R.id.orderDateText);
            orderDate.setText((CharSequence) new Date());
        }
        sum.setText(String.valueOf(temp));

        if (flag == false) {
            new AlertDialog.Builder(this)
                    .setIcon(android.R.drawable.ic_dialog_alert)
                    .setTitle("ERROR")
                    .setMessage("This book is out of stock")
                    .setPositiveButton("OK", null)
                    .setIcon(android.R.drawable.stat_notify_error)
                    .show();
            this.finish();
        }
    }

    public void addOrderClick (View view)
    {

        Long sbuyerId = Long.getLong(buyerId.getText().toString());
        Long screditNumber = Long.getLong(creditNumber.getText().toString());
        int scvs = Integer.getInteger(cvs.getText().toString());
        String sholdrName = holdrName.getText().toString();
        Long sholderId = Long.getLong(holderId.getText().toString());
        Date sorderDate =  new Date();
        float ssum = Float.parseFloat(sum.getText().toString());

        Order order = new Order(supplierBookId, sholderId,sholdrName,screditNumber,sbuyerId,scvs,sorderDate,ssum,supplierId);

        backend.addOrder(order);

    }

}
