package com.example.aviga_000.bookstoreandroid;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

import StoreJavaClass.Buyer;
import StoreJavaClass.Complains;
import StoreJavaClass.Supplier;
import model.backend.PoolFunctions;
import model.datasource.BackendFactory;
import model.datasource.StoreMySql;

/**
 * Created by User on 18/01/2016.
 */
public class ComplaintAdapter extends BaseAdapter {

    private Activity activity;
    private ArrayList<Complains> data;
    private static LayoutInflater inflater=null;
    PoolFunctions backend = BackendFactory.getInstance(activity);
    ArrayList <Buyer> _buyers = new ArrayList<Buyer>();
    ArrayList<Supplier> _suppliers = new ArrayList<Supplier>();
    StoreMySql _storeMySql;


    //CONSTRACTURE
    public ComplaintAdapter(Activity activity, ArrayList<Complains> data) {

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
        View vi=convertView;

        if(convertView==null)
            inflater = (LayoutInflater) activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            vi = inflater.inflate(R.layout.complaint_row, null);

        TextView complainantFirst=(TextView)vi.findViewById(R.id.firstNameText1);
        TextView dFirst=(TextView)vi.findViewById(R.id.firstNameText2);
        TextView complainantLast=(TextView)vi.findViewById(R.id.lastNameText1);
        TextView dLast=(TextView)vi.findViewById(R.id.lastNameText2);
        TextView complaint=(TextView)vi.findViewById(R.id.Text);
        TextView complainantId = (TextView) vi.findViewById(R.id.idText1);
        TextView dId = (TextView) vi.findViewById(R.id.idText2);

        synchronized (_suppliers = backend.supplierList()) {
            if (_storeMySql.done == false) {
                try {
                    _suppliers.wait(500);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            for (Supplier s : _suppliers) {
                if (s.getId() == data.get(position).getComplainantId()) {
                    complainantFirst.setText(s.getFirstName());
                    complainantLast.setText(s.getLastName());
                }
                if (s.getId() == data.get(position).getDefendantId()) {
                    dFirst.setText(s.getFirstName());
                    dLast.setText(s.getLastName());
                }
            }
        }

        try {
            synchronized (_buyers = backend.buyerList()) {
                if (_storeMySql.done == false)
                {
                    _buyers.wait(500);
                }
                for (Buyer s : _buyers) {
                    if (s.getId() == data.get(position).getComplainantId()) {
                        complainantFirst.setText(s.getFirstName());
                        complainantLast.setText(s.getLastName());
                    }
                    if (s.getId() == data.get(position).getDefendantId()) {
                        dFirst.setText(s.getFirstName());
                        dLast.setText(s.getLastName());
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        complaint.setText(data.get(position).getComplaint());
        complainantId.setText(String.valueOf(data.get(position).getComplainantId()));
        dId.setText(String.valueOf(data.get(position).getDefendantId()));
        return vi;
    }
}
