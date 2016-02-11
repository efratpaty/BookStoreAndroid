/*
package com.example.aviga_000.bookstoreandroid;

import android.app.Fragment;
import android.app.ListFragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.Map;

import StoreJavaClass.Supplier;
import model.backend.PoolFunctions;
import model.datasource.BackendFactory;


public class SuppliersFragment extends ListFragment {

    private ListView mList;
    private ArrayAdapter<Supplier> mAdapter;
    final PoolFunctions backend = BackendFactory.getInstance(getActivity());
    Fragment fr= new UpdateSupplierFragment();
    Bundle bundle = new Bundle();
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mList = (ListView)getActivity().findViewById(R.id.suppliersListView);//find supplier listView on activity xml
        Supplier[] suppliers = (Supplier[]) backend.supplierList().toArray();//get all supliers and store them in an array
        addListItems(suppliers);//send to func to set suppliers list items
        }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_suppliers, container, false);
    }
    private void addListItems(Supplier[] suppliersArray) {//add items to suppliers list
        mAdapter = new ArrayAdapter<Supplier>(getActivity(), android.R.layout.simple_list_item_1, suppliersArray);
        mAdapter.notifyDataSetChanged();
        mList.setAdapter(mAdapter);

}
    @Override
    public void onListItemClick(ListView l, View v, int position, long id) {
        super.onListItemClick(l, v, position, id);
    mList.setOnItemClickListener(new AdapterView.OnItemClickListener() {//when clicking on an option on the navigation bar
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {//when user click on item in list view
            Map<String, String> map = (Map<String, String>) mAdapter.getItem(position);
            String supplierId = map.get("id");
            bundle.putString("id",supplierId);



        }
    });
}
}
*/
