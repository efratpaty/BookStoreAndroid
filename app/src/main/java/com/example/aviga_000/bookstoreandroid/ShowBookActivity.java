package com.example.aviga_000.bookstoreandroid;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import StoreJavaClass.Book;
import StoreJavaClass.Subject;
import StoreJavaClass.SupplierBook;
import model.backend.PoolFunctions;
import model.datasource.BackendFactory;

public class ShowBookActivity extends AppCompatActivity {

    Intent rIntent = null;
    Intent intent = null;
    PoolFunctions backend = BackendFactory.getInstance(this);

    int supplierBookId;
    Long userId;
    int userType;
    int bookId;
    SupplierBook spb;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_book);

        rIntent = getIntent();
        supplierBookId = rIntent.getIntExtra("book_id", 0);
        userId = rIntent.getLongExtra("user_id", 0);
        userType = rIntent.getIntExtra("user", 0);

        TextView bookName = (TextView)findViewById(R.id.bookNameText);
        TextView author = (TextView)findViewById(R.id.authorText);
        TextView publisher = (TextView)findViewById(R.id.publisherText);
        TextView publicationDate = (TextView)findViewById(R.id.publicationDateText);
        TextView subject = (TextView)findViewById(R.id.subjectAutoCompleteTextView);
        TextView copies = (TextView)findViewById(R.id.copiesText);
        TextView recPrice = (TextView)findViewById(R.id.editText3);
        TextView price = (TextView)findViewById(R.id.priceText);
        TextView summary = (TextView)findViewById(R.id.summaryText);
        TextView condition = (TextView)findViewById(R.id.conditionAutoCompleteTextView);
        ImageView imageView = (ImageView)findViewById(R.id.bookImageView);

        ArrayList <SupplierBook> _supplierBooks  = backend.supplierBooks;
        for (SupplierBook sb : _supplierBooks) {

            if (sb.getId() == supplierBookId) {
                copies.setText(String.valueOf(sb.getCopies()));
                price.setText(String.valueOf(sb.getPrice()));
                condition.setText(String.valueOf(sb.getBookCondition()));
                bookId = sb.getBookId();
                spb = sb;

                if (userId == sb.getSupplierId()) {//if the user is the buyer make the update button visible
                    Button b = (Button) findViewById(R.id.updateBookButton);
                    b.setVisibility(View.VISIBLE);

                }
                break;

            }
        }

        ArrayList <Book> _books  = backend.books;
        for (Book b : _books) {

            if (bookId == b.getBookId()) {
                bookName.setText(b.getBookName());
                author.setText(b.getAuthor());
                publisher.setText(b.getPublishers());
                publicationDate.setText(String.valueOf(b.getPublicationDate()));
                subject.setText(String.valueOf(b.getSubject()));
                recPrice.setText(String.valueOf(b.getRecommendedPrice()));
                summary.setText(b.getSummary());
                Picasso.with(ShowBookActivity.this).load(b.getUrl()).into(imageView);
            }
        }


    }


        public void onCartClickActivity (View view)
    {
        if (userType == 0)
        {
            new AlertDialog.Builder(ShowBookActivity.this)
                    .setIcon(android.R.drawable.ic_dialog_alert)
                    .setTitle("Request Denied!")
                    .setMessage("You are not logged in")
                    .setPositiveButton("Login\\Sign in", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            intent = new Intent(ShowBookActivity.this, EntranceActivity.class);
                            startActivity(intent);
                        }

                    })
                    .setNegativeButton("Cancel", null)
                    .show();

        }

        else
        {
            backend.addCartItem(userId,spb);//add book to cart
            new AlertDialog.Builder(this)
                    .setIcon(android.R.drawable.ic_dialog_alert)
                    .setTitle("Success")
                    .setMessage("Item added to cart")
                    .setPositiveButton("OK", null)
                    .setIcon(android.R.drawable.star_on)
                    .show();
        }
    }


    public void onUpdateClick (View view)
    {
        intent = new Intent(ShowBookActivity.this,UpdateBookSupplier.class);
        intent.putExtra("user",userType);
        intent.putExtra("user_id",userId);
        intent.putExtra("buyer Book id",supplierBookId);
        startActivity(intent);
    }





}
