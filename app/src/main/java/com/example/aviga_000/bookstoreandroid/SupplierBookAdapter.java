package com.example.aviga_000.bookstoreandroid;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;


import StoreJavaClass.Book;
import StoreJavaClass.SupplierBook;
import model.backend.PoolFunctions;
import model.datasource.BackendFactory;

/**
 * Created by User on 19/01/2016.
 */
public class SupplierBookAdapter extends BaseAdapter {
    private Activity activity;
    private ArrayList<SupplierBook> data;
    private static LayoutInflater inflater=null;
    PoolFunctions backend = BackendFactory.getInstance(activity);

    //CONSTRACTURE

    public SupplierBookAdapter(Activity activity, ArrayList<SupplierBook> data) {
        this.activity = activity;
        this.data = data;
    }


    @Override
    public int getCount() {
        return data.size();
    }

    @Override
    public Object getItem(int position) {
        return position;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {


        View vi=null;

        if(convertView == null) {
            inflater =  (activity).getLayoutInflater();
            vi = inflater.inflate(R.layout.row_item, parent,false);
        }
        else {vi = convertView;}

        TextView name = (TextView)vi.findViewById(R.id.nameTextView);
        TextView author=(TextView)vi.findViewById(R.id.authorTextView);
        TextView price=(TextView)vi.findViewById(R.id.priceTextView);
        ImageView imageView = (ImageView)vi.findViewById(R.id.imageViewItem);

        for (Book b: backend.bookList()) {
            if (data.get(position).getBookId() == b.getBookId()) {
                name.setText(b.getBookName());
                author.setText(b.getAuthor());
                Picasso.with(activity).load(b.getUrl()).into(imageView);
            }
        }


        price.setText(String.valueOf(data.get(position).getPrice()));

        return vi;
    }
}
