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
import android.widget.ImageView;

import com.squareup.picasso.Picasso;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import StoreJavaClass.Book;
import StoreJavaClass.Subject;
import model.backend.PoolFunctions;
import model.datasource.BackendFactory;

public class AddBookActivity extends AppCompatActivity {

    final PoolFunctions backend= BackendFactory.getInstance(this);

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_book);

        /*Intent getIntent = getIntent();
        Manager manager = (Manager) getIntent.getSerializableExtra("Manager");
        if (_backend.getManager().getId() != manager.getId()) {//if user is not manager finish activity
            new AlertDialog.Builder(this)
                    .setIcon(android.R.drawable.stat_notify_error)
                    .setTitle("ERROR")
                    .setMessage("Only manager is allowed to add books")
                    .setPositiveButton("OK", null)
                    .show();
            finish();
        }*/
    }

    protected void subjectOnClick(View view) {//when clicking on subject box alert dialog with all the subjects will show the user choose as many subject as he wants,ubjects display on subject box
        // where we will store or remove selected items
        final ArrayList<Integer> mSelectedItems = new ArrayList<Integer>();
        final List<Subject> s = new ArrayList<Subject>();

        AlertDialog.Builder builder = new AlertDialog.Builder(this);

        // set the dialog title
        builder.setTitle("Choose One or More")

                // specify the list array, the items to be selected by default (null for none),
                // and the listener through which to receive call backs when items are selected
                // R.array.subjects were set in the resources res/values/strings.xml
                .setMultiChoiceItems(R.array.subjects, null, new DialogInterface.OnMultiChoiceClickListener() {

                    @Override
                    public void onClick(DialogInterface dialog, int which, boolean isChecked) {

                        if (isChecked) {
                            // if the user checked the item, add it to the selected items
                            mSelectedItems.add(which);
                        } else if (mSelectedItems.contains(which)) {
                            // else if the item is already in the array, remove it
                            mSelectedItems.remove(Integer.valueOf(which));
                        }

                    }

                })

                        // Set the action buttons
                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int id) {


                        // retrieve  selected items indices
                        String selectedIndex = "";
                        for (Integer i : mSelectedItems) {
                            selectedIndex += i + ", ";
                        }
                        //set text box ro show all selections
                        ((EditText)findViewById(R.id.subjectText)).setText(selectedIndex);

                    }
                })

                .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int id) {
                        // removes the AlertDialog in the screen
                    }
                })

                .show();
    }

    protected void addBookOnClick (View view) throws IOException//get all the info the user entered on the form and sends to func to add book
    {

        String bookName = ((EditText)findViewById(R.id.nameText)).getText().toString();
        String author = ((EditText)findViewById(R.id.authorText)).getText().toString();
        SimpleDateFormat dateFormat = new SimpleDateFormat("DD-MM-YYYY");//help variable defined in order to parse string to date format
        int publicationDate = Integer.valueOf(((EditText) findViewById(R.id.publicationText)).getText().toString());
        String publishers = ((EditText)findViewById(R.id.publisherText)).getText().toString();
        float recPrice = Float.parseFloat(((EditText) findViewById(R.id.priceText)).getText().toString());//parse price from string to float
        if ( recPrice <= 0)//if price is zero or less
        {//display alert dialog and delete inserted price
            new AlertDialog.Builder(this)
                    .setIcon(android.R.drawable.stat_notify_error)
                    .setTitle("ERROR")
                    .setMessage("Price cannot be 0 or less")
                    .setPositiveButton("OK", null)
                    .show();
            ((EditText)findViewById(R.id.priceText)).setText("");
            return;
        }
        String summary = ((EditText)findViewById(R.id.summaryText)).getText().toString();
        List<Subject> subjects = new ArrayList<>();
        String[] subjectsA = (((EditText)findViewById(R.id.subjectText)).getText().toString()).split(", ");//split the chosen subjects and put each one in an array cell
        for (String s:subjectsA) {//add each subject as enum type to subjects list
            subjects.add(Subject.valueOf(s));
        }
        String url = ((EditText)findViewById(R.id.urlText)).getText().toString();//find book image the manager entered
        ImageView bookImage = (ImageView)findViewById(R.id.bookImageView);
        Picasso.with(AddBookActivity.this).load(url).into(bookImage);
        //bookImage.setImageResource(R.drawable.books_images);//add image to src
        //if all fields are not empty
        if (url != null && bookName != null && recPrice != 0L && subjects != null && summary != null && publishers != null && author != null && publicationDate != 0L) {
            Book book = new Book(bookName,author,publicationDate,publishers, recPrice, summary, subjects, url);
            backend.addBook(book);//send to func to add book
            Intent intent = new Intent(this, BooksPoolActivity.class);
        }
        else // if some of the fields are empty
        {
            new AlertDialog.Builder(this)
                    .setIcon(android.R.drawable.stat_notify_error)
                    .setTitle("ERROR")
                    .setMessage("All the fields must be filled")
                    .setPositiveButton("OK", null)
                    .show();
            return;

        }

    }
}
