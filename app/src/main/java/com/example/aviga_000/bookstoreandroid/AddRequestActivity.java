package com.example.aviga_000.bookstoreandroid;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;

import StoreJavaClass.BookCondition;
import StoreJavaClass.BookSearch;
import model.backend.PoolFunctions;
import model.datasource.BackendFactory;

public class AddRequestActivity extends AppCompatActivity {

    final PoolFunctions backend = BackendFactory.getInstance(this);
    Intent rIntent = null;
    Long id = 0L;
    int bookId = 0;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_request);

        rIntent = getIntent();
        id = rIntent.getLongExtra("user_id", 0);
        bookId = rIntent.getIntExtra("book_id", 0);
        TextView userid = (TextView)findViewById(R.id.customerIdText);
        userid.setText(String.valueOf(id));
        userid.setEnabled(false);
        TextView bookid = (TextView)findViewById(R.id.bookIdText);
        bookid.setText(String.valueOf(bookId));




    }

    public void addNewOnClick (View view)
    {
        int bookId = Integer.parseInt(((EditText) findViewById(R.id.bookIdText)).getText().toString());//get user input book id
        Long buyerId = Long.parseLong(((EditText) findViewById(R.id.customerIdText)).getText().toString());//get from form buyer id
        String email = ((EditText) findViewById(R.id.emailText)).getText().toString();
        BookCondition condition = BookCondition.valueOf(((EditText) findViewById(R.id.conditionAutoCompleteTextView)).getText().toString());
        float maxPrice = Float.parseFloat(((EditText) findViewById(R.id.maxPriceText)).getText().toString());
        boolean send = ((CheckBox)findViewById(R.id.sendMessageCheckBox)).isSelected();//convert checkBox value to boolean value
        BookSearch request = new BookSearch(bookId, buyerId, email, condition, maxPrice, send);
        backend.addBookSearch(request);//send to func to add request
    }
}
