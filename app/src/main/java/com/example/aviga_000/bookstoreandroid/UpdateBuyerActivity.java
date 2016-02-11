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
import android.widget.TextView;

import StoreJavaClass.Buyer;
import model.backend.PoolFunctions;
import model.datasource.BackendFactory;

public class UpdateBuyerActivity extends AppCompatActivity {


    PoolFunctions backend = BackendFactory.getInstance(this);
    EditText phoneText;
    EditText emailText;
    EditText passwordText;
    Buyer buyer = null;
    Intent rIntent =  null;
    Long userId;
    TextView ID;
    TextView firstName1;
    TextView lastName1;
    TextView phone1;
    TextView email1;
    TextView city1;
    TextView street1;
    TextView buildingNumer;
    TextView houseNumber1;
    TextView password1;
    TextView zipCode1;
    String phoneAsString = null;
    Long phone = 0L;
    String email = null;
    String password = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_supplier);

        rIntent= getIntent();
        userId= rIntent.getLongExtra("user_id", 0);
        ID = (TextView)findViewById(R.id.IDText);
        firstName1 =(TextView)findViewById(R.id.firstNameText);
        lastName1 =(TextView)findViewById(R.id.lastNameText);
        phone1 =(TextView)findViewById(R.id.phoneText);
        email1 =(TextView)findViewById(R.id.emailText);
        city1 =(TextView)findViewById(R.id.cityText);
        street1 =(TextView)findViewById(R.id.streetText);
        buildingNumer =(TextView)findViewById(R.id.buildingText);
        houseNumber1 =(TextView)findViewById(R.id.houseText);
        password1 =(TextView)findViewById(R.id.passwordText);
        zipCode1 =(TextView)findViewById(R.id.zipCodeText);
        for (Buyer b: backend.buyers) {
            if ( b.getId() == userId)
            {
                buyer = b;
                ID.setText(String.valueOf(userId));
                firstName1.setText(b.getFirstName());
                lastName1.setText(b.getLastName());
                password1.setText(b.getPassword());
                phone1.setText(String.valueOf(b.getPhoneNumber()));
                houseNumber1.setText(String.valueOf(b.getHouseNumber()));
                city1.setText(b.getCity());
                street1.setText(b.getStreet());
                buildingNumer.setText(String.valueOf(b.getBuildingNumber()));
                email1.setText(b.geteMail());
                zipCode1.setText(String.valueOf(b.getZipCode()));
            }
        }

        //validate phone number
        ((EditText)findViewById(R.id.phoneText)).setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {

                if (!hasFocus) {

                    phoneText =(EditText)findViewById(R.id.phoneText);
                    String phoneAsString = ((EditText) findViewById(R.id.phoneText)).getText().toString();
                    Long phone = Long.parseLong(phoneAsString);
                    if (phoneAsString != null && phoneAsString.length() != 9 && phoneAsString.length() != 10 || !phoneAsString.startsWith("0"))//validate phone1 number
                    {
                        phoneText.setError("Incorrect phone1 number, please enter a valid number");
                        ((EditText) findViewById(R.id.phoneText)).setText("");
                        return;
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
                        ((EditText) findViewById(R.id.emailText)).setText("");
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
                    if (password!= null && password != "" && password.matches("^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(!=.*[@#$%^&+=])(?=\\S+$).{8,}$") || (password.length() < 6))//validate email1 address
                    {
                        passwordText.setError("Password must contain a combination of at least 6 numbers, uppercase & lowercase letters and must not contain punctuation marks");
                        ((EditText) findViewById(R.id.passwordText)).setText("");
                        return;
                    }
                }
            }
        });

    }

    public void onUpdateBuyerClick (View view) {
        String firstName = ((EditText) findViewById(R.id.firstNameText)).getText().toString();
        String lastName = ((EditText) findViewById(R.id.lastNameText)).getText().toString();

        String city = ((EditText) findViewById(R.id.cityText)).getText().toString();
        String street = ((EditText) findViewById(R.id.streetText)).getText().toString();
        int buildingNumber = Integer.parseInt((((EditText) findViewById(R.id.buildingText)).getText().toString()));
        String houseNumber = (((EditText) findViewById(R.id.houseText)).getText().toString());


        int zipCode = Integer.parseInt((((EditText) findViewById(R.id.zipCodeText)).getText().toString()));

        for (Buyer b: backend.buyers) {//update details for the buyer
            if (b.getId() == userId)
            {
                if (!TextUtils.isEmpty(firstName))
                    b.setFirstName(firstName);
                if (!TextUtils.isEmpty(lastName))
                    b.setLastName(lastName);
                if (!TextUtils.isEmpty(password))
                    b.setPassword(password);
                if (!TextUtils.isEmpty(street))
                    b.setStreet(street);
                if (!TextUtils.isEmpty(city))
                    b.setCity(city);
                if (!TextUtils.isEmpty(houseNumber))
                    b.setHouseNumber(houseNumber);
                if (!TextUtils.isEmpty(String.valueOf(buildingNumber)))
                    b.setBuildingNumber(buildingNumber);
                if (!TextUtils.isEmpty(String.valueOf(zipCode)))
                    b.setZipCode(zipCode);
                if (!TextUtils.isEmpty(email))
                    b.seteMail(email);
                if (!TextUtils.isEmpty(String.valueOf(phone)))
                    b.setPhoneNumber(phone);

                final Intent intent = new Intent(this, BooksPoolActivity.class);
                new AlertDialog.Builder(this)
                        .setIcon(android.R.drawable.ic_dialog_alert)
                        .setTitle("Success!")
                        .setMessage("details successfully updated")
                        .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                intent.putExtra("user_id", userId);
                                intent.putExtra("user", 2);
                                startActivity(intent);//open buyer activity
                            }
                        })
                        .show();
            }
        }


    }

    public void onDeleteBuyerClick (View view)
    {
        final Intent intent = new Intent(this, EntranceActivity.class);
        new AlertDialog.Builder(this)//make sure user really want to be deleted
                .setIcon(android.R.drawable.ic_dialog_alert)
                .setTitle("Alert!")
                .setMessage("are you sure you want to delete yourself from the system?")
                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {//if user really want to be deleted,delete him and open entrance activity
                        backend.deleteBuyer(buyer);
                        intent.putExtra("user_id", 0);
                        intent.putExtra("user", 0);
                        startActivity(intent);//open entrance activity
                    }
                })
                .setNegativeButton("Cancel", null)
                .show();

    }
}

