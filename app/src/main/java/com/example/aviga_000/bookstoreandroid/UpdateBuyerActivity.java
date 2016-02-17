package com.example.aviga_000.bookstoreandroid;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.InputType;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import java.util.ArrayList;

import StoreJavaClass.Buyer;
import model.backend.PoolFunctions;
import model.datasource.BackendFactory;

public class UpdateBuyerActivity extends AppCompatActivity {

    private String o_Text = "";
    private String n_Text = "";
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
    String previousEmail = null;
    String previousPhone = null;
    String previousPassword = null;
    ArrayList <Buyer> _buyers = new ArrayList<Buyer>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_buyer);

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

        _buyers = backend.buyers;
        for (Buyer b: _buyers) {
            if ( b.getId() == userId)
            {
                buyer = b;
                ID.setText(String.valueOf(userId));
                firstName1.setText(b.getFirstName());
                lastName1.setText(b.getLastName());
                password1.setText(b.getPassword());
                previousPassword = b.getPassword();
                phone1.setText(String.valueOf(b.getPhoneNumber()));
                previousPhone = String.valueOf(b.getPhoneNumber());
                houseNumber1.setText(String.valueOf(b.getHouseNumber()));
                city1.setText(b.getCity());
                street1.setText(b.getStreet());
                buildingNumer.setText(String.valueOf(b.getBuildingNumber()));
                email1.setText(b.geteMail());
                previousEmail = b.geteMail();
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
                        ((EditText) findViewById(R.id.phoneText)).setText(previousPhone);
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
                        ((EditText) findViewById(R.id.emailText)).setText(previousEmail);
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
                        ((EditText) findViewById(R.id.passwordText)).setText(previousPassword);
                        return;
                    }
                }
            }
        });

    }

    public void onPasswordClick(View view) {// when user click on password1 alert dialog that ask him for his current password1 pops up
        final AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Please enter your current password1");
// Set up the input
        final EditText oldPassword = new EditText(this);
        final EditText newPassword = new EditText(this);
// Specify the type of input expected; this, for example, sets the input as a password1, and will mask the text
        oldPassword.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
        oldPassword.setHint("Password:");
        newPassword.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
        newPassword.setHint("New password1:");
        builder.setView(oldPassword);
        builder.setView(newPassword);

// Set up the buttons
        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {//when user click ok
            @Override
            public void onClick(DialogInterface dialog, int which) {
                o_Text = oldPassword.getText().toString();//get user input (old password1)
                n_Text = newPassword.getText().toString();//get user input(new password1)
                if (o_Text != backend.manager.getPassword()) {//if couldnt match user input password1 with saved current password1
                    final TextView error = new TextView(UpdateBuyerActivity.this);
                    error.setText("Error wrong password1 please try again");
                    builder.setView(error);
                }
                else {
                    backend.manager.setPassword(n_Text);
                    ((EditText)findViewById(R.id.passwordText)).setText(n_Text);
                }
                (findViewById(R.id.passwordText)).setEnabled(false);//if user didnt supply valid password1 make passwordEditText  not editable
            }
        });
        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
                (findViewById(R.id.passwordText)).setEnabled(false);//if user didnt supply valid password1 make passwordEditText  not editable
            }
        });

        builder.show();
    }


    public void onUpdateBuyerClick (View view) {
        String firstName = ((EditText) findViewById(R.id.firstNameText)).getText().toString();
        String lastName = ((EditText) findViewById(R.id.lastNameText)).getText().toString();
        String eMail = ((EditText) findViewById(R.id.emailText)).getText().toString();
        String city = ((EditText) findViewById(R.id.cityText)).getText().toString();
        String street = ((EditText) findViewById(R.id.streetText)).getText().toString();
        Long _phone = Long.valueOf(((EditText) findViewById(R.id.phoneText)).getText().toString());
        String paswd = ((EditText) findViewById(R.id.passwordText)).getText().toString();
        int buildingNumber = Integer.parseInt((((EditText) findViewById(R.id.buildingText)).getText().toString()));
        String houseNumber = (((EditText) findViewById(R.id.houseText)).getText().toString());


        int zipCode = Integer.parseInt((((EditText) findViewById(R.id.zipCodeText)).getText().toString()));

        for (Buyer b: _buyers) {//update details for the buyer
            if (b.getId() == userId)
            {
                if (!TextUtils.isEmpty(firstName))
                    b.setFirstName(firstName);
                if (!TextUtils.isEmpty(lastName))
                    b.setLastName(lastName);
                if (!TextUtils.isEmpty(paswd))
                    b.setPassword(paswd);
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
                if (!TextUtils.isEmpty(eMail))
                    b.seteMail(eMail);
                if (!TextUtils.isEmpty(String.valueOf(_phone)))
                    b.setPhoneNumber(_phone);

                final Intent intent = new Intent(this, BooksPoolActivity.class);
                backend.updateBuyer(b);
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

