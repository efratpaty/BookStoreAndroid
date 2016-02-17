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
import com.squareup.picasso.*;

import java.util.ArrayList;

import StoreJavaClass.Book;
import StoreJavaClass.Buyer;
import StoreJavaClass.Complains;
import StoreJavaClass.Supplier;
import StoreJavaClass.SupplierBook;
import model.backend.PoolFunctions;
import model.datasource.BackendFactory;

/**
 * Created by User on 19/01/2016.
 */
public class booksAdapter extends BaseAdapter {
    private Activity activity;
    private ArrayList<Book> data;
    private static LayoutInflater inflater=null;
    PoolFunctions backend = BackendFactory.getInstance(activity);

    //CONSTRACTURE
    public booksAdapter(Activity activity, ArrayList<Book> data) {
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


        View vi=convertView;

        if(convertView==null) {
            inflater = (LayoutInflater) activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            vi = inflater.inflate(R.layout.row_item, null);
        }

        TextView name = (TextView)vi.findViewById(R.id.nameTextView);
        TextView author=(TextView)vi.findViewById(R.id.authorTextView);
        TextView price=(TextView)vi.findViewById(R.id.priceTextView);
        ImageView imageView = (ImageView)vi.findViewById(R.id.imageViewItem);



        name.setText(data.get(position).getBookName());
        price.setText(String.valueOf(data.get(position).getRecommendedPrice()));
        author.setText(data.get(position).getAuthor());
        Picasso.with(activity).load(data.get(position).getUrl()).into(imageView);
        return vi;
    }
}
