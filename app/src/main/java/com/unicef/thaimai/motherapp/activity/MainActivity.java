package com.unicef.thaimai.motherapp.activity;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.design.widget.FloatingActionButton;

import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.util.DisplayMetrics;
import android.util.Log;
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

import com.unicef.thaimai.motherapp.Preference.PreferenceData;
import com.unicef.thaimai.motherapp.Presenter.SosAlertPresenter;
import com.unicef.thaimai.motherapp.R;
import com.unicef.thaimai.motherapp.constant.AppConstants;
import com.unicef.thaimai.motherapp.fragment.NotificationFragment;
import com.unicef.thaimai.motherapp.fragment.PNhbncVisit;
import com.unicef.thaimai.motherapp.fragment.ReferralListFragment;
import com.unicef.thaimai.motherapp.fragment.health_records;
import com.unicef.thaimai.motherapp.fragment.home;
import com.unicef.thaimai.motherapp.view.SosAlertViews;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Locale;

import static com.unicef.thaimai.motherapp.constant.AppConstants.isMainActivityOpen_Count;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener, SosAlertViews {
    Intent intent;

    Locale mylocale;
    TextView tam,eng;
    SosAlertPresenter sosAlertPresenter;
    PreferenceData preferenceData;

    ProgressDialog pDialog;
ArrayList<String> msgList;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
//        addMsgtoList();
        pDialog = new ProgressDialog(this);
        pDialog.setCancelable(false);
        pDialog.setMessage("Please Wait ...");
        preferenceData = new PreferenceData(this);

        sosAlertPresenter =new SosAlertPresenter(MainActivity.this,this);


if (AppConstants.isMainActivityOpen) {


    if(preferenceData.getMainScreenOpen().equalsIgnoreCase("0")){
        preferenceData.setMainScreenOpen(1);
        showAlertDialog("Good Morning Mrs. "+ preferenceData.getMotherName()+".","Close");
    } else  if(preferenceData.getMainScreenOpen().equalsIgnoreCase("1")){
        preferenceData.setMainScreenOpen(2);
        showAlertDialog("Hope you are doing well..","Close");
    }else  if(preferenceData.getMainScreenOpen().equalsIgnoreCase("2")){
        preferenceData.setMainScreenOpen(3);
        showAlertDialog("This is your 12th Week of pregnancy.","Close");
    }else  if(preferenceData.getMainScreenOpen().equalsIgnoreCase("3")){
        preferenceData.setMainScreenOpen(4);
        showAlertDialog("this is the Period of child monthly development.","Close");
    }else  if(preferenceData.getMainScreenOpen().equalsIgnoreCase("4")){
        preferenceData.setMainScreenOpen(0);
        showAlertDialog("If you are not feeling well please Click here.","Click here");
    }
}
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.floatingActionButton2);

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                sosAlertPresenter.postSosAlert(preferenceData.getPicmeId(),preferenceData.getMId(),preferenceData.getVhnId(),preferenceData.getPhcId(),preferenceData.getAwwId(), preferenceData.getDeviceId());
//                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
//                        .setAction("Action", null).show();
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

    /*private void addMsgtoList() {
        msgList =new ArrayList<>();

        msgList.add(0,"Good Morning"+preferenceData.getMotherName()+".");
        msgList.add(1,"Hope you are doing well.");
        msgList.add(2,"This is your GST Week of pregnancy.");
        msgList.add(3,"this is the Period of child monthly development.");
        msgList.add(4,"If you are not feeling well please press here.");

    }*/

    private void showAlertDialog(String msg) {
        AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);


//        builder.setTitle("Hi Tamil Selvi,");
//        builder.setMessage("Have you take tablets regulerlly: ");
        builder.setTitle("Hi"+preferenceData.getMotherName()+",");
        builder.setMessage(msg);


        //Yes Button
        builder.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
//                Toast.makeText(getApplicationContext(),"Take care",Toast.LENGTH_LONG).show();
//                Toast.makeText(getApplicationContext(),"Alert has set to VHN,  They will contact soon..",Toast.LENGTH_LONG).show();
//                AppConstants.isMainActivityOpen=false;
                dialog.dismiss();
            }
        });
        /*builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
//                Toast.makeText(getApplicationContext(),"Alert has set to VHN,  They will contact soon..",Toast.LENGTH_LONG).show();
//                AppConstants.isMainActivityOpen=false;
                dialog.dismiss();
            }
        });*/

        AlertDialog alertDialog = builder.create();
        alertDialog.show();
    }

    private void showAlertDialog(String msg, final String action) {
        AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);


//        builder.setTitle("Hi Tamil Selvi,");
//        builder.setMessage("Have you take tablets regulerlly: ");
        builder.setTitle("Hi"+preferenceData.getMotherName()+",");
        builder.setMessage(msg);


        //Yes Button
        builder.setPositiveButton(action, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
//                Toast.makeText(getApplicationContext(),"Take care",Toast.LENGTH_LONG).show();
                if (action.equalsIgnoreCase("Click here")){
//                    Toast.makeText(getApplicationContext(),"Alert has set to VHN,  They will contact soon..",Toast.LENGTH_LONG).show();
                    showAlertDialog("Alert has set to VHN,  They will contact soon..");
                    sosAlertPresenter.postSosAlert(preferenceData.getPicmeId(),preferenceData.getMId(),preferenceData.getVhnId(),preferenceData.getPhcId(),preferenceData.getAwwId(), preferenceData.getDeviceId());
                }else{
                    showAlertDialog("Thank You, Mrs."+preferenceData.getMotherName());

//                    Toast.makeText(getApplicationContext(),"Thank you Mrs."+preferenceData.getMotherName(),Toast.LENGTH_LONG).show();

                }


                AppConstants.isMainActivityOpen=false;
                dialog.dismiss();
            }
        });
        /*builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
//                Toast.makeText(getApplicationContext(),"Alert has set to VHN,  They will contact soon..",Toast.LENGTH_LONG).show();
//                AppConstants.isMainActivityOpen=false;
                dialog.dismiss();
            }
        });*/

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
                preferenceData.setLogin(false);

//                Intent i  = new Intent(MainActivity.this, LocationUpdateActivity.class);
//
//                startActivity(i);
                finish();
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
            Intent i = new Intent(getApplicationContext(), NearHospitalActivity.class);
            startActivity(i);
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

        else if (id == R.id.immunization_entry) {
            Intent v = new Intent(getApplicationContext(), ImmunizationEditActivity.class);
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
                selectedFragment = ReferralListFragment.newInstance();
                break;

        }

        android.support.v4.app.FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.content, selectedFragment);
        transaction.commit();

    }

    @Override
    public void showProgress() {
        pDialog.show();

    }

    @Override
    public void hideProgress() {
        pDialog.hide();
    }

    @Override
    public void showPickmeResult(String response) {
        Log.d(MainActivity.class.getSimpleName(), "Response Success--->" + response);
        showAlertDialog(response);

        try {
            JSONObject jsonObject =new JSONObject(response);
            String status =jsonObject.getString("status");
            String msg = jsonObject.getString("message");
//            showAlertDialog(msg);

//            if (status.equalsIgnoreCase("1")){
//                Toast.makeText(getApplicationContext(),msg,Toast.LENGTH_SHORT).show();
//                showAlertDialog(msg);
////                startActivity(new Intent(getApplicationContext(),MainActivity.class));
//            }

        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void showErrorMessage(String response) {
        Log.d(AddRecords.class.getSimpleName(), "Response Error--->" + response);

    }
}
