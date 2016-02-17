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
import android.widget.ImageView;

import java.util.ArrayList;

import StoreJavaClass.Complains;
import model.backend.PoolFunctions;
import model.datasource.BackendFactory;

public class AddComplaintActivity extends AppCompatActivity {

    final PoolFunctions  backend =BackendFactory.getInstance(this);
    Intent getIntent = getIntent();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_complaint);

        long[] ids =  getIntent.getLongArrayExtra("ids");
        ((EditText)findViewById(R.id.complainantIdText)).setText(String.valueOf(ids[0]));//set complainant id to sender id
        ((EditText)findViewById(R.id.defandantIdText)).setText(String.valueOf(ids[1]));//set defendant id
    }

    protected void addComplaintOnClick (View view)//get all the info the user entered on the form and sends to func to add complaint
    {
        Long complainantId = Long.parseLong(((EditText) findViewById(R.id.complainantIdText)).getText().toString());
        String idAsString = complainantId.toString();
        if(idAsString.length()!=9 )//validate id
        {
            new AlertDialog.Builder(this)
                    .setIcon(android.R.drawable.stat_notify_error)
                    .setTitle("ERROR")
                    .setMessage("Incorrect id, please enter a valid id number")
                    .setPositiveButton("OK", null)
                    .show();
            ((EditText)findViewById(R.id.complainantIdText)).setText("");
            return;
        }
        //check if such person exist in the system
        boolean flag = false;

        ArrayList<Long> _customersIds = backend.customersId();
        while (flag != true)
            for (Long id: _customersIds) {
                if (id == complainantId)
                    flag = true;
            }
        //
        //if person does not exist show error dialog
        if (flag = false)
        {
            new AlertDialog.Builder(this)
                    .setIcon(android.R.drawable.stat_notify_error)
                    .setTitle("ERROR")
                    .setMessage("Id does not exist in the system, please enter an id of an existing member")
                    .setPositiveButton("OK", null)
                    .show();
            ((EditText)findViewById(R.id.complainantIdText)).setText("");
            return;
        }
        Long defendantId = Long.parseLong(((EditText) findViewById(R.id.defandantIdText)).getText().toString());
        idAsString = defendantId.toString();
        if(idAsString.length()!=9 )//validate id
        {
            new AlertDialog.Builder(this)
                    .setIcon(android.R.drawable.stat_notify_error)
                    .setTitle("ERROR")
                    .setMessage("Incorrect id, please enter a valid id number")
                    .setPositiveButton("OK", null)
                    .show();
            ((EditText)findViewById(R.id.defandantIdText)).setText("");
            return;
        }
        //check if such person exist in the system
        flag = false;
        while (flag != true)
            for (Long id: _customersIds) {
                if (id == defendantId)
                    flag = true;
            }
        //
        //if person does not exist show error dialog
        if (flag = false)
        {
            new AlertDialog.Builder(this)
                    .setIcon(android.R.drawable.stat_notify_error)
                    .setTitle("ERROR")
                    .setMessage("Id does not exist in the system, please enter an id of an existing member")
                    .setPositiveButton("OK", null)
                    .show();
            ((EditText)findViewById(R.id.defandantIdText)).setText("");
            return;
        }
        String complaint = ((EditText) findViewById(R.id.complaintText)).getText().toString();

        if (complainantId == 0 || complainantId == 0L || defendantId == 0 || defendantId == 0L || complaint == null || complaint == "")
        {
            new AlertDialog.Builder(this)
                    .setIcon(android.R.drawable.ic_dialog_alert)
                    .setTitle("Attention")
                    .setMessage("One or more of the required fields was not filled")
                    .setPositiveButton("OK", null)
                    .show();
        }
        else
        {
            Complains complain = new Complains(complainantId, defendantId, complaint);
            backend.addComplains(complain);//send to func to add complain
            this.finish();//finish activity add complaint
        }


    }

}
