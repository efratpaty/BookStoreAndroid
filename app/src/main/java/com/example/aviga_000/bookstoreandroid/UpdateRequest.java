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

import static java.lang.String.*;

public class UpdateRequest extends AppCompatActivity {

    final PoolFunctions backend = BackendFactory.getInstance(this);
    Intent rIntent = null;
    Long id = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_request);

        id = rIntent.getLongExtra("user_id", 0);
        TextView customerId = (TextView)findViewById(R.id.customerIdText);
        TextView bookId = (TextView)findViewById(R.id.bookIdText);
        TextView email = (TextView)findViewById(R.id.emailText);
        TextView condition = (TextView)findViewById(R.id.conditionAutoCompleteTextView);
        TextView price = (TextView)findViewById(R.id.maxPriceText);
        CheckBox send = (CheckBox)findViewById(R.id.sendMessageCheckBox);
        for (BookSearch bs: backend.bookSearchings) {
            if (bs.getBookSearchId() == rIntent.getIntExtra("book_search_id",0))
            {
                customerId.setText(String.valueOf(bs.getCustomerId()));
                customerId.setEnabled(false);
                bookId.setText(String.valueOf(bs.getBookId()));
                bookId.setEnabled(false);
                email.setText(bs.getCustomerEmail());
                condition.setText(String.valueOf(bs.getBookCondition()));
                price.setText(String.valueOf(bs.getMaxPrice()));
                send.setChecked(bs.isSendMessage());
            }
        }


    }

    public void updateOnClick (View view)
    {
        int bookId = Integer.parseInt(((EditText) findViewById(R.id.bookIdText)).getText().toString());//get user input book id
        Long buyerId = Long.parseLong(((EditText) findViewById(R.id.customerIdText)).getText().toString());//get from form buyer id
        String email = ((EditText) findViewById(R.id.emailText)).getText().toString();
        BookCondition condition = BookCondition.valueOf(((EditText) findViewById(R.id.conditionAutoCompleteTextView)).getText().toString());
        float maxPrice = Float.parseFloat(((EditText) findViewById(R.id.maxPriceText)).getText().toString());
        boolean send = ((CheckBox)findViewById(R.id.sendMessageCheckBox)).isSelected();//convert checkBox value to boolean value
        BookSearch request = new BookSearch(bookId, buyerId, email, condition, maxPrice, send);
        backend.updateBookSearch(request);//send to func to add request
    }
}
