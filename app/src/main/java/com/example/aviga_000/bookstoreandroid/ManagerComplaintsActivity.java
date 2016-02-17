package com.example.aviga_000.bookstoreandroid;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Fragment;
import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;

import java.util.ArrayList;

import StoreJavaClass.Complains;
import model.backend.PoolFunctions;
import model.datasource.BackendFactory;
import model.datasource.StoreMySql;

public class ManagerComplaintsActivity extends NavActivity {

    private ListView mList;
    private  ComplaintAdapter mAdapter;
    ArrayList <Complains> _complainses = new ArrayList<Complains>();
    StoreMySql _storeMySql = new StoreMySql(this);


    final PoolFunctions backend = BackendFactory.getInstance(this);
    Intent intentRecieve = null;
    Long userId;
    int userType;
    Intent intent = null;

    private String mActivityTitle;
    final Activity activity = this;


    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        intentRecieve = getIntent();
        userType = intentRecieve.getIntExtra("user", 0);

        getLayoutInflater().inflate(R.layout.activity_manager_complaint, frameLayout);


        mList = (ListView)findViewById(R.id.complaintslistView);//find complains listView on activity xml
        synchronized (_complainses = backend.complainsList()) {

            if (_storeMySql.done == false)
            {
                try {
                    _complainses.wait(500);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            mAdapter = new ComplaintAdapter(ManagerComplaintsActivity.this, _complainses);
            mList.setAdapter(mAdapter);
            userId = intentRecieve.getLongExtra("user_id", 0);

            mList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> adapter, View view, int position, long arg) {
                    Intent sbi = new Intent(ManagerComplaintsActivity.this, ComplaintsActivity.class);
                    sbi.putExtra("complaint_id", backend.complainses.get(position).getComplaintId());
                    sbi.putExtra("user_id", userId);
                    sbi.putExtra("user", userType);
                    startActivity(sbi);
                }
            });

            mActivityTitle = getTitle().toString();

        }
    }

    public void onSearchClick(View view)
    {
        Long defendantID = null;
        Long complainantId = null;
        String id = ((EditText) findViewById(R.id.defendentText)).getText().toString();
        if(!id.equals("") && !id.equals(null))
            defendantID = Long.parseLong(id);//get user input
        id = ((EditText) findViewById(R.id.complainantText)).getText().toString();
        if(!id.equals("") && !id.equals(null))
            complainantId = Long.parseLong(id);//get user input
        //send to func to find all the query resualt
        backend.complainantDefendant(complainantId,defendantID);


    }


    @Override
    public void onBackPressed() {
        backend.exitProgram(this);
    }




    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        //mDrawerToggle.syncState();
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        //mDrawerToggle.onConfigurationChanged(newConfig);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_my, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

//        // Activate the navigation drawer toggle
//        if (mDrawerToggle.onOptionsItemSelected(item)) {
//            return true;
//        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();
        Intent intent = null;
        switch (id) {

            case R.id.cart:
                intent = new Intent(ManagerComplaintsActivity.this, ShoppingCartActivity.class);
                intent.putExtra("user_id", userId);
                intent.putExtra("user", userType);
                break;
            case R.id.complaint:
                    intent = new Intent(ManagerComplaintsActivity.this, ManagerComplaintsActivity.class);
                    intent.putExtra("user_id", userId);
                    intent.putExtra("user", userType);
                break;
            case R.id.order:
                intent = new Intent(ManagerComplaintsActivity.this, ManagerOrdersActivity.class);
                intent.putExtra("user_id", userId);
                intent.putExtra("user", userType);
                break;
            case R.id.request:
                intent = new Intent(ManagerComplaintsActivity.this, BookRequestsActivity.class);
                intent.putExtra("user_id", userId);
                intent.putExtra("user", userType);
                break;
            case R.id.update:
                    intent = new Intent(ManagerComplaintsActivity.this, UpdateManagerActivity.class);
                intent.putExtra("user_id", userId);
                intent.putExtra("user", userType);
                break;
            case R.id.bookdirectory:
                intent = new Intent(ManagerComplaintsActivity.this, BooksPoolActivity.class);
                intent.putExtra("user_id", userId);
                intent.putExtra("user", userType);
                break;
            case R.id.delete:
                new AlertDialog.Builder(this)
                        .setIcon(android.R.drawable.ic_dialog_alert)
                        .setTitle("ERROR")
                        .setMessage("manager can't be deleted")
                        .setPositiveButton("OK", null)
                        .setIcon(android.R.drawable.stat_notify_error)
                        .show();
                break;
            default:
                break;
        }
        if (intent != null)
            startActivity(intent);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;


    }}

