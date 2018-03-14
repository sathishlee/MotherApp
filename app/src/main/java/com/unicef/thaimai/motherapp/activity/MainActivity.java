package com.unicef.thaimai.motherapp.activity;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;

import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.util.DisplayMetrics;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.Toast;

import com.unicef.thaimai.motherapp.R;
import com.unicef.thaimai.motherapp.constant.AppConstants;
import com.unicef.thaimai.motherapp.fragment.NotificationFragment;
import com.unicef.thaimai.motherapp.fragment.PNhbncVisit;
import com.unicef.thaimai.motherapp.fragment.health_records;
import com.unicef.thaimai.motherapp.fragment.home;
import com.unicef.thaimai.motherapp.fragment.referral;

import java.util.Locale;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener   {
    Intent intent;

    Locale mylocale;
    TextView tam,eng;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);



if (AppConstants.isMainActivityOpen) {
    showAlertDialog();

}
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.floatingActionButton2);

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        setupNavigationView();


//        tam = (TextView)findViewById(R.id.tam);
//        eng = (TextView) findViewById(R.id.eng);
        /*tam.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){

                android.app.AlertDialog.Builder builder = new android.app.AlertDialog.Builder(MainActivity.this);
                builder.setMessage("Are you Sure do you want to Change Language?")
                        .setCancelable(false)
                        .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                setLanguage("ta");
                            }
                        })
                        .setNegativeButton("No", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                dialog.cancel();
                            }
                        });
                android.app.AlertDialog alert = builder.create();
                alert.show();
            }
        });*/

        /*eng.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){

                android.app.AlertDialog.Builder builder = new android.app.AlertDialog.Builder(MainActivity.this);
                builder.setMessage("Are you Sure do you want to Change Language?")
                        .setCancelable(false)
                        .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                setLanguage("en");
                            }
                        })
                        .setNegativeButton("No", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                dialog.cancel();
                            }
                        });
                android.app.AlertDialog alert = builder.create();
                alert.show();
            }
        });*/

    }

    private void showAlertDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);


        builder.setTitle("Hi Tamil Selvi,");
        builder.setMessage("Have you take tablets regulerlly: ");


        //Yes Button
        builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
//                Toast.makeText(getApplicationContext(),"Take care",Toast.LENGTH_LONG).show();
                AppConstants.isMainActivityOpen=false;
                dialog.dismiss();
            }
        });
        builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Toast.makeText(getApplicationContext(),"Alert has set to VHN,  They will contact soon..",Toast.LENGTH_LONG).show();
                AppConstants.isMainActivityOpen=false;
                dialog.dismiss();
            }
        });

        AlertDialog alertDialog = builder.create();
        alertDialog.show();
    }

    protected void setLanguage(String language){
        mylocale=new Locale(language);
        Resources resources=getResources();
        DisplayMetrics dm=resources.getDisplayMetrics();
        Configuration conf= resources.getConfiguration();
        conf.locale=mylocale;
        resources.updateConfiguration(conf,dm);
        Intent refreshIntent=new Intent(MainActivity.this,MainActivity.class);
        finish();
        startActivity(refreshIntent);
    }
    @Override
    protected void attachBaseContext(Context context) {
        super.attachBaseContext(context);

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
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {
            case R.id.action_notification:
//                Toast.makeText(getApplicationContext(),"Notification Clicked",Toast.LENGTH_LONG).show();
                android.support.v4.app.FragmentManager fragmentManager = getSupportFragmentManager();
                fragmentManager.beginTransaction().replace(R.id.content,
                        NotificationFragment.newInstance()).commit();
                return true;

            case R.id.language:
                Intent v  = new Intent(MainActivity.this, Language.class);
                finish();
                startActivity(v);
                return true;

            case R.id.action_help:
                Intent i  = new Intent(MainActivity.this, Help.class);
                finish();
                startActivity(i);
                Toast.makeText(getApplicationContext(),"Help Menu",Toast.LENGTH_LONG).show();
                return true;
            default:
                super.onOptionsItemSelected(item);
        }return true;


    }
    /*Slide navigation*/
    @SuppressWarnings("StatementWithEmptyBody")
    @Override

    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

//        if (id == R.id.navigation_home) {
//            android.support.v4.app.FragmentManager fragmentManager = getSupportFragmentManager();
//            fragmentManager.beginTransaction().replace(R.id.content,
//                    home.newInstance()).commit();
//
//
//        }
        if (id == R.id.primary_register){
            Intent i = new Intent(getApplicationContext(), PrimaryRegisterView.class);
            startActivity(i);
        }

        else if (id == R.id.visit_record){
            Intent i = new Intent(getApplicationContext(), AddRecords.class);
            startActivity(i);
        }

//        else if (id == R.id.navigation_notifications) {
//
//            android.support.v4.app.FragmentManager fragmentManager = getSupportFragmentManager();
//            fragmentManager.beginTransaction().replace(R.id.content,
//                    health_records.newInstance()).commit();
//        }
//        else if (id == R.id.baby) {
//            android.support.v4.app.FragmentManager fragmentManager = getSupportFragmentManager();
//            fragmentManager.beginTransaction().replace(R.id.content,
//                    baby.newInstance()).commit();
//        }

        else if (id == R.id.nearbyhospital) {
//            Intent i = new Intent(getApplicationContext(), NearbyHospital.class);
//            startActivity(i);
        }

        else if (id == R.id.immunization_menu){
            Intent i = new Intent(getApplicationContext(),ImmunizationActivity.class);
            startActivity(i);
        }

       else if (id == R.id.health_tips) {
            Intent i = new Intent(getApplicationContext(), HeathTipsActivity.class);
            startActivity(i);
        }

        else if (id == R.id.pn_hbnc_visit_entry) {
            Intent v = new Intent(getApplicationContext(), PNHBNCVisitEntry.class);
            startActivity(v);
        }

//        else if (id == R.id.hbnc_visit_entry) {
//            Intent v = new Intent(getApplicationContext(), InfantTrackingEditActivity.class);
//            startActivity(v);
//        }

        else if (id == R.id.delivery_details_entry){
            Intent i = new Intent(getApplicationContext(), DeliveryDetailsActivityEntry.class);
            startActivity(i);
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    private void setupNavigationView() {
        BottomNavigationView bottomNavigationView = (BottomNavigationView) findViewById(R.id.navigation);

        if (bottomNavigationView != null) {

            // Select first menu item by default and show Fragment accordingly.
            Menu menu = bottomNavigationView.getMenu();
            selectFragment(menu.getItem(0));

            // Set action to perform when any menu-item is selected.
            bottomNavigationView.setOnNavigationItemSelectedListener(
                    new BottomNavigationView.OnNavigationItemSelectedListener() {
                        @Override
                        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                            selectFragment(item);

                            return false;
                        }
                    });
        }
    }


    public void selectFragment(MenuItem item) {

        item.setChecked(true);
        Fragment selectedFragment = null;
        switch (item.getItemId()) {

            case R.id.navigation_home:
                selectedFragment = home.newInstance();
                break;

            case R.id.navigation_notifications:
                selectedFragment =  health_records.newInstance();
                break;

            case R.id.pn_hbnc_visit:
                selectedFragment =  PNhbncVisit.newInstance();
                break;

            case R.id.referral:
                selectedFragment = referral.newInstance();
                break;

        }

        android.support.v4.app.FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.content, selectedFragment);
        transaction.commit();

    }

}
