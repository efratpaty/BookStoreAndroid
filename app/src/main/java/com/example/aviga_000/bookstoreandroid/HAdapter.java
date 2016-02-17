package com.example.aviga_000.bookstoreandroid;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import StoreJavaClass.Book;
import StoreJavaClass.Order;
import model.backend.PoolFunctions;
import model.datasource.BackendFactory;
import model.datasource.StoreMySql;

/**
 * Created by efrat on 1/27/16.
 */
public class HAdapter extends BaseAdapter {

    private Activity activity;
    private ArrayList<Integer> data;
    private static LayoutInflater inflater=null;
    StoreMySql _storeMySql;

    PoolFunctions backend = BackendFactory.getInstance(activity);
    ArrayList<Book> _books = new ArrayList<Book>();

    public HAdapter(Activity activity, ArrayList<Integer> data) {
        this.activity = activity;
        _storeMySql = new StoreMySql(activity);
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

        View vi = null;
        if (convertView == null) {
            inflater = (activity).getLayoutInflater();
            vi = inflater.inflate(R.layout.image_item, parent, false);

        } else {
            vi = convertView;
        }
        ImageView imageView = (ImageView) vi.findViewById(R.id.bookImageView);

        synchronized (_books = backend.bookList()) {
            if(_storeMySql.done == false)
            {
                try {
                    _books.wait(500);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            for (Book b : _books) {
                if (data.get(position) == b.getBookId()) {

                    Picasso.with(activity).load(b.getUrl()).into(imageView);
                }
            }
            return vi;
        }
    }
}
