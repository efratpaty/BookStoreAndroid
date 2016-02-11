package com.example.aviga_000.bookstoreandroid;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.InputType;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import model.backend.PoolFunctions;
import model.datasource.BackendFactory;

public class UpdateManagerActivity extends AppCompatActivity {

    private String o_Text = "";
    private String n_Text = "";
    private PoolFunctions backend = BackendFactory.getInstance(this);
    Activity activity = this;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_manager);
    }

    protected void onPasswordClick(View view) {// when user click on password1 alert dialog that ask him for his current password1 pops up
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
                    final TextView error = new TextView(activity);
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



}
