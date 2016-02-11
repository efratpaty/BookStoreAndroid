package com.example.aviga_000.bookstoreandroid;

import android.app.AlertDialog;
import android.app.Fragment;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.design.widget.Snackbar;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.FrameLayout;

public class NavActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    Intent intentRecieve = null;
    protected FrameLayout frameLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nav);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        frameLayout = (FrameLayout)findViewById(R.id.frame_container);
        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
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

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();
        Intent intent = null;
        switch (id) {
            case R.id.home:
                intent = new Intent(NavActivity.this, ManagerComplaintsActivity.class);
                break;
            case R.id.cart:
                    intent = new Intent(NavActivity.this, ShoppingCartActivity.class);
                break;
            case R.id.complaint:
                intent = new Intent(NavActivity.this, ComplaintsActivity.class);
                break;
            case R.id.order:
                intent = new Intent(NavActivity.this, OrdersActivity.class);
                break;
            case R.id.bookdirectory:
                intent = new Intent(NavActivity.this, BooksPoolActivity.class);
                break;
            case R.id.request:
                intent = new Intent(NavActivity.this, BookRequestsActivity.class);
                break;
            case R.id.update:
                intent = new Intent(NavActivity.this, UpdateManagerActivity.class);
                break;
            case R.id.delete:
                CharSequence[] arraySB = {"Supplier", "Buyer"};
                new AlertDialog.Builder(NavActivity.this)//open dialog to choose user type
                        .setTitle("Choose user type")
                        .setItems(arraySB, new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                switch (which) {
                                    case 0://case user chose buyer
                                        //fragment = new SuppliersFragment();
                                        break;
                                        //Supplier
                                    case 1://case user chose customer
                                        //fragment = new BuyersFragment();
                                        break;
                                }
                            }

                        })
                        .setNegativeButton("Cancel", null)
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
    }
}