package com.example.aviga_000.bookstoreandroid;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;

import java.util.ArrayList;

import StoreJavaClass.Buyer;
import StoreJavaClass.Supplier;
import StoreJavaClass.SupplierBook;
import model.backend.PoolFunctions;
import model.datasource.BackendFactory;
import model.datasource.StoreMySql;

public class EntranceActivity extends AppCompatActivity {

    PoolFunctions backend = BackendFactory.getInstance(this);
    final Activity activity = this;
    Intent intent = null;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my);


    }
    public void loginButtonOnClick (View view) throws Exception {
        String enteredUserName = ((EditText)findViewById(R.id.emailUserName)).getText().toString();
        String enteredPassword = ((EditText)findViewById(R.id.userPassword)).getText().toString();
        // check to see if their login info matches and open the right activity
        userAuthentication(enteredUserName, enteredPassword);
    }

    public void newUserButtonOnClick (View view)//open activity to add new user
    {
        //final Activity activity = this;
        CharSequence[] arraySB = {"Supplier", "Buyer"};
        //final Intent[] intent = {null};
        new AlertDialog.Builder(this)//open dialog to choose user type
                .setTitle("Choose user type")
                .setItems(arraySB, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        switch (which) {
                            case 0://case user chose buyer
                                intent = new Intent(activity, AddSupplierActivity.class);
                                startActivity(intent);
                                finish();
                                break;
                                //Supplier
                            case 1://case user chose customer
                                intent = new Intent(activity, AddCoustomerActivity.class);
                                startActivity(intent);
                                finish();
                                break;
                        }
                    }

                })
                .setNegativeButton("Cancel", null)
                .show();
    }

    public void guestButtonOnClick (View view)
    {//open book activity

        intent = new Intent(activity, BooksPoolActivity.class);
        intent.putExtra("user_id",0);
        intent.putExtra("user",0);
        startActivity(intent);

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

    public void userAuthentication(String userName, String password) throws Exception {

        StoreMySql storeMySql = new StoreMySql(this);
        ArrayList<Supplier> suppliers= storeMySql.supplierListForSignIn(userName,password,activity);
        ArrayList<Buyer> buyers= storeMySql.buyerListForSignIn(userName, password, activity);

        buyers.isEmpty();
        suppliers.isEmpty();

/*        //search buyer list for username and password1 if found make intent buy*//*er activity
            for (Supplier s :suppliers) {
                if (s.geteMail().equals(userName) && s.getPassword().equals(password)) {
                    intent = new Intent(activity, SupplierBooksActivity.class);
                    intent.putExtra("user_id", s.getId());
                    intent.putExtra("user", 2);
                    startActivity(intent);

             }*/

    /*        }

       //if user was not found, search buyer list for username and password1 if found make intent customer activity
            for (Buyer b : buyers) {
                if (b.geteMail().equals(userName) && b.getPassword().equals(password)) {
                    intent = new Intent(activity, CustomerActivity.class);
                    intent.putExtra("user_id", b.getId());
                    intent.putExtra("user",1);
                    startActivity(intent);


                //if user was not found, check if username and password1 belong to manger, if so make intent customer activity
                if (backend.manager.geteMail().equals(userName) && backend.manager.getPassword().equals(password)) {
                    intent = new Intent(activity, ManagerComplaintsActivity.class);
                    intent.putExtra("user_id", backend.manager.getId());
                    intent.putExtra("user",3);
                    startActivity(intent);
                }

                else
                {//if user not found open dialog to let user know the login failed
                    new AlertDialog.Builder(this)
                            .setIcon(android.R.drawable.ic_dialog_alert)
                            .setTitle("ERROR")
                            .setMessage("incorrect username or password1")
                            .setPositiveButton("OK", null)
                            .setIcon(android.R.drawable.stat_notify_error)
                            .show();
                }      */

     }
            }


