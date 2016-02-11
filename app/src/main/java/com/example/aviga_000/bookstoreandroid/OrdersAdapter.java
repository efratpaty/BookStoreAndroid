package com.example.aviga_000.bookstoreandroid;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.devsmart.android.ui.HorizontalListView;
import com.squareup.picasso.Picasso;


import java.util.ArrayList;

import StoreJavaClass.Book;
import StoreJavaClass.Order;
import StoreJavaClass.SupplierBook;
import model.backend.PoolFunctions;
import model.datasource.BackendFactory;

/**
 * Created by efrat on 1/23/16.
 */
public class OrdersAdapter  extends BaseAdapter  {
    private Activity activity;
    private ArrayList<Order> data;
    private static LayoutInflater inflater=null;
    PoolFunctions backend = BackendFactory.getInstance(activity);

    //CONSTRACTURE

    public OrdersAdapter(Activity activity, ArrayList<Order> data) {
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

        float sum = 0L;
        View vi = null;

        if (convertView == null) {
            inflater = (activity).getLayoutInflater();
            vi = inflater.inflate(R.layout.horizontal_item, parent, false);

        } else {
            vi = convertView;
        }

        TextView price = (TextView) vi.findViewById(R.id.sumText);
        HorizontalListView hListView = (HorizontalListView)vi.findViewById(R.id.hlistview);
        hListView.setAdapter(new HAdapter(activity, data.get(position).getBookId()));

        for (Long id : data.get(position).getSupplierId()) {
            for (SupplierBook b : backend.supplierBookList(id)) {
                if (data.get(position).getBookId().contains(b.getBookId())) {
                    sum += b.getPrice();

                }
            }
        }
        price.setText(String.valueOf(sum));

            return vi;
        }

}
