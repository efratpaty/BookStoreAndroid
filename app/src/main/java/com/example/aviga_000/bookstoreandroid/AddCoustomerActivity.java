package com.example.aviga_000.bookstoreandroid;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.EditText;

import StoreJavaClass.Buyer;
import model.backend.PoolFunctions;
import model.datasource.BackendFactory;

public class AddCoustomerActivity extends AppCompatActivity {

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
        setContentView(R.layout.activity_add_coustomer);


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
                    //check if allows !@#$%^&*  (?=.*[A-Z]) (?!.*(AND|NOT)).*[a-z].*")
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

    public void addNewBuyerOnClick(View view)
    {
        final PoolFunctions backend = BackendFactory.getInstance(this);
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
        if(firstName == null || firstName == "" || lastName == "" || lastName == null || id ==null || phone == null ||email == null
                || email == "" || city == null || city == "" || street == null || street == "" || houseNumber == null || houseNumber =="" || password == null || password == "")
        {
            new AlertDialog.Builder(this)
                    .setIcon(android.R.drawable.ic_dialog_alert)
                    .setTitle("Attention")
                    .setMessage("One or more of the required fields was not filled")
                    .setPositiveButton("OK", null)
                    .show();
        }
        else {
            final Buyer buyer = new Buyer(password, firstName, lastName, id, phone, email, city, street, buildingNumber, houseNumber, zipCode);
            backend.addBuyer(buyer);//send to function to add buyer
            final Intent intent = new Intent(this, BooksPoolActivity.class);
            new AlertDialog.Builder(this)
                    .setIcon(android.R.drawable.ic_dialog_alert)
                    .setTitle("WELCOME :)")
                    .setMessage("You are now loged in")
                    .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            intent.putExtra("user_id", id);
                            intent.putExtra("user", 1);
                            startActivity(intent);//open buyer activity
                        }
                    })
                    .show();
            startActivity(intent);//open buyer activity
        }
    }

}
