package com.example.aviga_000.bookstoreandroid;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.List;

import StoreJavaClass.Complains;
import model.backend.PoolFunctions;
import model.datasource.BackendFactory;

public class ComplaintsActivity extends AppCompatActivity {

    final PoolFunctions backend = BackendFactory.getInstance(this);
    Intent getIntent = getIntent();
    private ListView mListAgainst;
    private ListView mListMy;
    private ArrayAdapter<Complains> mAdapter;
    Long id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_complaints);

        id = getIntent.getLongExtra("id", -1);
        List<Complains> complainses = backend.complainsAboutUserList(id);//fix bundle than change
        //set list to show all the complaints about user
        ArrayAdapter <Complains> myAdapater = new ArrayAdapter<Complains>(this,android.R.layout.simple_list_item_1,complainses);
        ListView complaintsAgainstList = (ListView) findViewById(R.id.complaintsAgainstMeListView);
        complaintsAgainstList.setAdapter(myAdapater);
        //

        //set list to show all the user complaints
        complainses = backend.complainsUserList(id);//fix bundle than change
        myAdapater = new ArrayAdapter<Complains>(this,android.R.layout.simple_list_item_1,complainses);
        ListView userComplaintsList = (ListView) findViewById(R.id.userComplaintsListView);
        userComplaintsList.setAdapter(myAdapater);
        //

        mListAgainst = (ListView)findViewById(R.id.complaintsAgainstMeListView);//find complaints against user listView on activity xml
        mListMy = (ListView)findViewById(R.id.userComplaintsListView);//find user complaints listView on activity xml

        //needs to change after changing bundle
        Complains[] complaintsAgainst = (Complains[]) backend.complainsAboutUserList(id).toArray();//get all complaints about user and store them in an array
        Complains[] complaintsUser = (Complains[]) backend.complainsUserList(id).toArray();//get all complaints about user and store them in an array
        addListsItems(complaintsAgainst, complaintsUser);//send to func to set complainses list items
    }

    private void addListsItems(Complains[] complaintsAgainstUserArray, Complains[] complaintsUserArray) {//add items to complaints about user and user complaints lists
        mAdapter = new ArrayAdapter<Complains>(this, android.R.layout.simple_list_item_1, complaintsAgainstUserArray);
        mListAgainst.setAdapter(mAdapter);

        mAdapter = new ArrayAdapter<Complains>(this, android.R.layout.simple_list_item_1, complaintsUserArray);
        mListMy.setAdapter(mAdapter);
    }

    protected void onNewClick(View view)//send to add complaint activity with user id
        {//open activity add new complaint
            Intent intent = new Intent(this, AddComplaintActivity.class);
            Long[] ids = {id,0L};
            intent.putExtra("ids",ids);
            startActivity(intent);
        }


    }
