package com.example.aviga_000.bookstoreandroid;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.media.Image;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;

import StoreJavaClass.Book;
import StoreJavaClass.BookCondition;
import StoreJavaClass.ShoppingCartHelper;
import StoreJavaClass.SupplierBook;
import model.backend.PoolFunctions;
import model.datasource.BackendFactory;

public class ShoppingCartActivity extends AppCompatActivity {

    private ListView mList;
    private SupplierBookAdapter mAdapter;
    Intent intentFrom = null;
    Intent intent = null;
    ArrayList<SupplierBook> sp = null;
    //ArrayList<ShoppingCartHelper> sch = null;
    PoolFunctions backend = null;
    long userId;
    int user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shopping_cart);

        backend = BackendFactory.getInstance(this);

        userId = intentFrom.getLongExtra("user_id", 0);
        user = intentFrom.getIntExtra("user", 0);
        if (user != 0) {
            for (ShoppingCartHelper s : backend.getCart()) {
                if (s.getId() == userId) {
                    sp = s.getSupplierBookListCart();
                    break;

                }
            }
        }

        mList = (ListView) findViewById(R.id.supplierBookslistView);//find complains listView on activity xml
        mAdapter = new SupplierBookAdapter(ShoppingCartActivity.this, sp);
        mList.setAdapter(mAdapter);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);

        mList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapter, View view, int position, long arg) {
                {
                    new AlertDialog.Builder(ShoppingCartActivity.this)
                            .setIcon(android.R.drawable.ic_dialog_alert)
                            .setTitle("Delete item:")
                            .setMessage("Are you sure you want to delete the book from the list?")
                            .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    boolean flag = backend.deleteCartItem(userId, sp.get(which));
                                    if (flag) {
                                        new AlertDialog.Builder(ShoppingCartActivity.this)
                                                .setIcon(android.R.drawable.ic_dialog_alert)
                                                .setTitle("Success")
                                                .setMessage("book successfully deleted")
                                                .setPositiveButton("OK", null)
                                                .setIcon(android.R.drawable.star_on)
                                                .show();
                                    } else {
                                        new AlertDialog.Builder(ShoppingCartActivity.this)
                                                .setIcon(android.R.drawable.ic_dialog_alert)
                                                .setTitle("Error")
                                                .setMessage("couldn't delete book")
                                                .setPositiveButton("OK", null)
                                                .setIcon(android.R.drawable.star_on)
                                                .show();
                                    }
                                }

                            })
                            .setNegativeButton("Cancel", null)
                            .show();

                }
            }
        });

    }



    public void deleteButton(View view)
    {}

    public void backToMenuButton(View view)
    {
        intent = new Intent(this,BooksPoolActivity.class);
        startActivity(intent);
    }

    public void purchaseButton (View view)
    {
        float totalAmount = 0;
        for (SupplierBook item : sp) {
            totalAmount += item.getPrice();
        }
        final String totalAmountAsString = Float.toString(totalAmount);
        Button purchase = (Button) findViewById(R.id.purchaseButton);
        purchase.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {

                final AlertDialog alertDialog = new AlertDialog.Builder(ShoppingCartActivity.this).create();
                //AlertDialog.Builder alertDialog = new AlertDialog.Builder(ShoppingCartActivity.this);
                alertDialog.setTitle("Purchase");
                alertDialog.setMessage("Your total purchase amount is: " + totalAmountAsString);
                alertDialog.setButton(Dialog.BUTTON_POSITIVE, "pay now", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        intent = new Intent(ShoppingCartActivity.this, PaymentActivity.class);
                        startActivity(intent);
                    }
                });
                alertDialog.setButton( Dialog.BUTTON_NEGATIVE, "Cancel", new DialogInterface.OnClickListener()    {
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.cancel();
                        }
});
                alertDialog.show();  //<-- See This!
                    }

    });
}

}
