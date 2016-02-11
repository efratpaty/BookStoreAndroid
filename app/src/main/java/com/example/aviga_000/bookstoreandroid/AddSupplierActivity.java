package com.example.aviga_000.bookstoreandroid;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;

import StoreJavaClass.Supplier;
import model.backend.PoolFunctions;
import model.datasource.BackendFactory;

public class AddSupplierActivity extends AppCompatActivity {

    final PoolFunctions backend = BackendFactory.getInstance(this);
    EditText phoneText;
    EditText emailText;
    EditText idText;
    EditText passwordText;
    String phoneAsString = null;
    Long phone = 0L;
    String email = null;
    String password = null;
    String idAsString = null;
    Long id = 0L;
    int buildingNumber;
    int zipCode;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_supplier);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        //validate phone number
        ((EditText)findViewById(R.id.phoneText)).setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {

                if (!hasFocus) {
                    phoneText =(EditText)findViewById(R.id.phoneText);
                    phoneAsString = (phoneText).getText().toString();
                    if(!phoneAsString.equals( "" )&& phoneAsString.equals(null)) {
                        phone = Long.parseLong(phoneAsString);
                        if (phoneAsString.length() != 9 && phoneAsString.length() != 10 || !phoneAsString.startsWith("0"))//validate phone1 number
                        {
                           phoneText.setError("Incorrect phone1 number, please enter a valid number");
                            ((EditText) findViewById(R.id.phoneText)).setText("");
                            return;
                        }
                    }
                }
            }
        });

        //validate email
        ((EditText)findViewById(R.id.emailText)).setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {

                if (!hasFocus) {
                    emailText = (EditText)findViewById(R.id.emailText);
                    email = (emailText).getText().toString();
                    if(!email.contains("@") || !email.endsWith(".com") )//validate email1 address
                    {
                        emailText.setError("Incorrect email1 address, please enter a valid email1");
                        ((EditText)findViewById(R.id.emailText)).setText("");
                        return;
                    }
                }
            }
        });

        //validate password
        ((EditText)findViewById(R.id.passwordText)).setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {

                if (!hasFocus) {
                    passwordText = (EditText)findViewById(R.id.passwordText);
                    password = (passwordText).getText().toString();
                    if(password.matches("^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(!=.*[@#$%^&+=])(?=\\S+$).{8,}$") || (password.length() < 6))//validate email1 address
                    {
                        passwordText.setError("Password must contain a combination of at least 6 numbers, uppercase & lowercase letters and must not contain punctuation marks");
                        ((EditText) findViewById(R.id.passwordText)).setText("");
                        return;
                    }
                }
            }
        });


        //validate id
        ((EditText)findViewById(R.id.IDText)).setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {

                if (!hasFocus) {
                    idText = (EditText) findViewById(R.id.IDText);
                    idAsString = (idText).getText().toString();
                    if (!idAsString.equals(null) && !idAsString.equals(""))
                        id = Long.parseLong(idAsString);
                    if(idAsString.length()!=9 )//validate id
                    {
                        idText.setError("Incorrect id, please enter a valid id number");
                        ((EditText)findViewById(R.id.IDText)).setText("");
                        return;
                    }


                }
            }
        });
    }
    public void addNewSupplierOnClick(View view)
    {

        String firstName = ((EditText)findViewById(R.id.firstNameText)).getText().toString();
        String lastName = ((EditText)findViewById(R.id.lastNameText)).getText().toString();


        String city = ((EditText)findViewById(R.id.cityText)).getText().toString();
        String street = ((EditText)findViewById(R.id.streetText)).getText().toString();
        String bn = ((EditText) findViewById(R.id.buildingText)).getText().toString();
        if(!bn.equals("") && !bn.equals(null))
            buildingNumber = Integer.parseInt(bn);
        String houseNumber =(((EditText) findViewById(R.id.houseText)).getText().toString());

        String zc = ((EditText) findViewById(R.id.zipCodeText)).getText().toString();
        if(!zc.equals("") && !zc.equals(null))
            zipCode = Integer.parseInt(zc);
        //if one of the must fill fields remain empty show message
        if(TextUtils.isEmpty(firstName) || TextUtils.isEmpty(lastName) || TextUtils.isEmpty(idAsString)  || TextUtils.isEmpty(phoneAsString) ||
                TextUtils.isEmpty(email) || TextUtils.isEmpty(city) || TextUtils.isEmpty(street) || TextUtils.isEmpty(Integer.toString(buildingNumber)) ||
                TextUtils.isEmpty(password) || TextUtils.isEmpty(Integer.toString(zipCode)) )
//        if(firstName1 == null || firstName1 == "" ||  lastName1 == "" || lastName1 == null || id ==null || phone1 == null ||email1 == null
//                || email1 == "" || city1 == null || city1 == "" || street1 == null || street1 == "" || houseNumber1 == null || houseNumber1 =="" || password1 == null || password1 == "")
        {
            new AlertDialog.Builder(this)
                    .setIcon(android.R.drawable.ic_dialog_alert)
                    .setTitle("Attention")
                    .setMessage("One or more of the required fields was not filled")
                    .setPositiveButton("OK", null)
                    .show();
        }
        else {
            final Supplier supplier = new Supplier(password, firstName, lastName, id, phone, email, city, street, buildingNumber, houseNumber, zipCode);
//            _storeList.addSupplier(buyer);//send to function to add buyer
            final Intent intent = new Intent(this, SupplierBooksActivity.class);
            new AlertDialog.Builder(this)
                    .setIcon(android.R.drawable.ic_dialog_alert)
                    .setTitle("WELCOME :)")
                    .setMessage("You are now loged in")
                    .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            intent.putExtra("user_id", supplier.getId());
                            intent.putExtra("user", 2);
                            startActivity(intent);//open buyer activity
                        }
                    })
                    .show();
        }
    }

}
