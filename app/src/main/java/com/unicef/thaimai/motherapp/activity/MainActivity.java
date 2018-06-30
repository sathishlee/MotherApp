package com.unicef.thaimai.motherapp.activity;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.location.Address;
import android.location.Geocoder;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.design.widget.FloatingActionButton;

import android.support.v4.app.Fragment;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v4.view.MenuItemCompat;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AlertDialog;
import android.text.Html;
import android.text.TextUtils;
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
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.analytics.FirebaseAnalytics;
import com.squareup.picasso.MemoryPolicy;
import com.squareup.picasso.NetworkPolicy;
import com.squareup.picasso.Picasso;
import com.unicef.thaimai.motherapp.Preference.PreferenceData;
import com.unicef.thaimai.motherapp.Presenter.GetUserInfoPresenter;
import com.unicef.thaimai.motherapp.Presenter.LocationUpdatePresenter;
import com.unicef.thaimai.motherapp.Presenter.NotificationPresenter;
import com.unicef.thaimai.motherapp.Presenter.SosAlertPresenter;
import com.unicef.thaimai.motherapp.R;
//import com.unicef.thaimai.motherapp.bradcastReceiver.ConnectivityReceiver;
import com.unicef.thaimai.motherapp.adapter.ViewPagerAdapter;
import com.unicef.thaimai.motherapp.app.MyApplication;
import com.unicef.thaimai.motherapp.broadCastReceivers.ConnectivityReceiver;
import com.unicef.thaimai.motherapp.constant.Apiconstants;
import com.unicef.thaimai.motherapp.constant.AppConstants;
import com.unicef.thaimai.motherapp.fragment.NotificationFragment;
import com.unicef.thaimai.motherapp.fragment.PNhbncVisit;
import com.unicef.thaimai.motherapp.fragment.ReferralListFragment;
import com.unicef.thaimai.motherapp.fragment.health_records;
import com.unicef.thaimai.motherapp.fragment.home;
import com.unicef.thaimai.motherapp.realmDbModelClass.HomeRealmModel;
import com.unicef.thaimai.motherapp.utility.CheckNetwork;
import com.unicef.thaimai.motherapp.utility.LocationMonitoringService;
import com.unicef.thaimai.motherapp.utility.RoundedTransformation;
import com.unicef.thaimai.motherapp.view.LocationUpdateViews;
import com.unicef.thaimai.motherapp.view.NotificationViews;
import com.unicef.thaimai.motherapp.view.SosAlertViews;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener, ConnectivityReceiver.ConnectivityReceiverListener,
        SosAlertViews, NotificationViews{
    Intent intent;

    Locale mylocale;
    TextView tam, eng;
    SosAlertPresenter sosAlertPresenter;
    NotificationPresenter notificationPresenter;
    public static TextView notification_count;
    LinearLayout ll_notifi_count;
    String strTodayVisitCount="0", str_mPhoto;
    int mCartItemCount = 10;

    PreferenceData preferenceData;
    GetUserInfoPresenter getUserInfoPresenter;
    ProgressDialog pDialog;
    ArrayList<String> msgList;
    CheckNetwork checkNetwork;
    RelativeLayout noConnection;
    FirebaseAnalytics firebaseAnalytics;

    ImageView cardview_image;
    TextView txt_username,edt_picme_id;
    Context context;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        checkNetwork = new CheckNetwork(this);
        firebaseAnalytics = FirebaseAnalytics.getInstance(this);

        noConnection = (RelativeLayout) findViewById(R.id.view_btm_no_inernet);

        if (checkNetwork.isNetworkAvailable()) {
//            Toast.makeText(getApplicationContext(), "Internet connection is" + checkNetwork.isNetworkAvailable(), Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(getApplicationContext(), "No Internet connection..!" + checkNetwork.isNetworkAvailable(), Toast.LENGTH_SHORT).show();
//            startActivity(new Intent(getApplicationContext(),NoInternetConnection.class));
        }
        pDialog = new ProgressDialog(this);
        pDialog.setCancelable(true);
        pDialog.setMessage("Please Wait ...");
        preferenceData = new PreferenceData(this);

        notificationPresenter = new NotificationPresenter(getApplicationContext(), this);
        sosAlertPresenter = new SosAlertPresenter(MainActivity.this, this);

        notificationPresenter.getNotificationCount(preferenceData.getMId(),preferenceData.getPicmeId());

//        TextView txt_notify_count = (TextView)findViewById(R.id.txt_notify_count);
//        txt_notify_count.setText(preferenceData.getNotificationCount());

        if (AppConstants.isMainActivityOpen) {
            if (preferenceData.getMainScreenOpen().equalsIgnoreCase("0")) {
                preferenceData.setMainScreenOpen(1);
                showAlertDialog();
            }
        }
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.floatingActionButton2);

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (checkNetwork.isNetworkAvailable()) {
                    sosAlertPresenter.postSosAlert(preferenceData.getPicmeId(), preferenceData.getMId(), preferenceData.getVhnId(), preferenceData.getPhcId(), preferenceData.getAwwId(), preferenceData.getDeviceId());
                } else {
                    Toast.makeText(getApplicationContext(), "make call function" + preferenceData.getVHNMobileNumber(), Toast.LENGTH_SHORT).show();
                    startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("tel:+91" + preferenceData.getVHNMobileNumber())));
                }
            }
        });

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();
        setupNavigation();
        setupNavigationView();
    }

    private void setupNavigation() {
        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        View headerView = navigationView.getHeaderView(0);
        navigationView.setNavigationItemSelectedListener(this);
        cardview_image = (ImageView) headerView.findViewById(R.id.cardview_image);
        txt_username = (TextView) headerView.findViewById(R.id.txt_username);
        edt_picme_id = (TextView) headerView.findViewById(R.id.edt_picme_id);
        txt_username.setText(preferenceData.getMotherName());
        edt_picme_id.setText(preferenceData.getPicmeId());
        str_mPhoto = preferenceData.getMotherPhoto();

        if(TextUtils.isEmpty(str_mPhoto)){
            cardview_image.setImageResource(R.drawable.girl_1);
        }else{
            Log.d("mphoto-->", Apiconstants.PHOTO_URL+str_mPhoto);

            Picasso.with(getApplicationContext())
                    .load(Apiconstants.PHOTO_URL + str_mPhoto)
                    .placeholder(R.drawable.girl_1)
                    .fit()
                    .centerCrop()
                    .memoryPolicy(MemoryPolicy.NO_CACHE)
                    .networkPolicy(NetworkPolicy.NO_CACHE)
                    .transform(new RoundedTransformation(90,4))
                    .error(R.drawable.girl_1)
                    .into(cardview_image);
        }
    }


    private void showAlertDialog() {
        // custom dialog
        final Dialog dialog = new Dialog(this);
        dialog.setContentView(R.layout.view_dash_boards_alert);
        String circle = "\u27a4";
        dialog.setTitle("Hi " + preferenceData.getMotherName() + ",");

        // set the custom dialog components - text, image and button
        TextView text = (TextView) dialog.findViewById(R.id.txt_msg_welcome0);
        TextView text1 = (TextView) dialog.findViewById(R.id.txt_msg_welcome1);
        TextView text2 = (TextView) dialog.findViewById(R.id.txt_msg_welcome2);
//        TextView text3 = (TextView) dialog.findViewById(R.id.txt_msg_welcome3);
        TextView text4 = (TextView) dialog.findViewById(R.id.txt_msg_welcome4);
        text.setText("Good Morning Mrs. " + preferenceData.getMotherName() + ".");
        text1.setText(circle+getString(R.string.hope_you_are_doing_well));
        text2.setText(circle+ "  This is your " + setSufix(preferenceData.getGstWeek()) + " Week of pregnancy." + ".");
//        text3.setText(circle+getString(R.string.this_is_the_period_of_child_monthly_development));
        text4.setText(circle+getString(R.string.if_you_are_not_feeling_well_please));

        TextView dialogClickHere = (TextView) dialog.findViewById(R.id.btn_click_here);

        dialogClickHere.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sosAlertPresenter.postFlashAlert(preferenceData.getPicmeId(), preferenceData.getMId());
                AppConstants.isMainActivityOpen = false;
                dialog.dismiss();
            }
        });
        TextView dialogCancel = (TextView) dialog.findViewById(R.id.btn_cancel);

        dialogCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
        dialog.show();
    }

    private String setSufix(String gstWeek) {
        String returnstring = gstWeek;
        if (gstWeek.substring(1).equalsIgnoreCase("1")) {
            returnstring = gstWeek + " st";
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            returnstring= String.valueOf(Html.fromHtml(gstWeek+"<sup><small>nd</small></sup>", Html.FROM_HTML_MODE_LEGACY));
        }
        } else if (gstWeek.substring(1).equalsIgnoreCase("2")) {
            returnstring = gstWeek + " nd";
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                returnstring= String.valueOf(Html.fromHtml(gstWeek+"<sup><small>nd</small></sup>", Html.FROM_HTML_MODE_LEGACY));
            }

        } else if (gstWeek.substring(1).equalsIgnoreCase("3")) {
            returnstring = gstWeek + " rd";
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                returnstring= String.valueOf(Html.fromHtml(gstWeek+"<sup><small>rd</small></sup>", Html.FROM_HTML_MODE_LEGACY));
            }
        } else {
            returnstring = gstWeek + " th";
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                returnstring = String.valueOf(Html.fromHtml(gstWeek + "<sup><small>th</small></sup>", Html.FROM_HTML_MODE_LEGACY));

            }
        }
        return returnstring;
    }


    /*private void showAlertDialog(String msg, final String action, int i) {
        AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);


//        builder.setTitle("Hi Tamil Selvi,");
//        builder.setMessage("Have you take tablets regulerlly: ");
        builder.setTitle("Hi " + preferenceData.getMotherName() + ",");

        builder.setMessage(msg);
        if (action.equalsIgnoreCase("Click here")) {
            builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    dialog.dismiss();
                }
            });
        }

        //Yes Button
        builder.setPositiveButton(action, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
//                Toast.makeText(getApplicationContext(),"Take care",Toast.LENGTH_LONG).show();
                if (action.equalsIgnoreCase("Click here")) {
//                    Toast.makeText(getApplicationContext(),"Alert has set to VHN,  They will contact soon..",Toast.LENGTH_LONG).show();
//                    showAlertDialog("If you are not feeling well please Click here.", "Alert has set to VHN,  They will contact soon..", 0);
                    sosAlertPresenter.postSosAlert(preferenceData.getPicmeId(), preferenceData.getMId(), preferenceData.getVhnId(), preferenceData.getPhcId(), preferenceData.getAwwId(), preferenceData.getDeviceId());
                    AppConstants.isMainActivityOpen = false;
                    dialog.dismiss();
                } else if (action.equalsIgnoreCase("Next")) {

//                    showAlertDialog("If you are not feeling well please Click here.", "Thank You, Mrs."+preferenceData.getMotherName(), 0);
                    if (preferenceData.getMainScreenOpen().equalsIgnoreCase("1")) {
                        preferenceData.setMainScreenOpen(2);
                        showAlertDialog("Hope you are doing well..", "Next", 2);
                    } else if (preferenceData.getMainScreenOpen().equalsIgnoreCase("2")) {
                        preferenceData.setMainScreenOpen(3);
                        showAlertDialog("This is your 12th Week of pregnancy.", "Next", 3);
                    } else if (preferenceData.getMainScreenOpen().equalsIgnoreCase("3")) {
                        preferenceData.setMainScreenOpen(4);
                        showAlertDialog("this is the Period of child monthly development.", "Next", 4);
                    } else if (preferenceData.getMainScreenOpen().equalsIgnoreCase("4")) {
                        preferenceData.setMainScreenOpen(0);
                        showAlertDialog("If you are not feeling well please Click here.", "Click here", 0);
                    }
//                    Toast.makeText(getApplicationContext(),"Thank you Mrs."+preferenceData.getMotherName(),Toast.LENGTH_LONG).show();

                } else if (action.equalsIgnoreCase("close")) {
                    preferenceData.setMainScreenOpen(0);
                    dialog.dismiss();
                }


                AppConstants.isMainActivityOpen = false;
//                dialog.dismiss();
            }
        });
        *//*builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
//                Toast.makeText(getApplicationContext(),"Alert has set to VHN,  They will contact soon..",Toast.LENGTH_LONG).show();
//                AppConstants.isMainActivityOpen=false;
                dialog.dismiss();
            }
        });*//*

        AlertDialog alertDialog = builder.create();
        alertDialog.show();
    }
*/

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
            android.app.AlertDialog.Builder builder = new android.app.AlertDialog.Builder(MainActivity.this);
            builder.setTitle(R.string.app_name);
            builder.setIcon(R.mipmap.ic_launcher);
            builder.setMessage("Are you Sure do you want to exit?")
                    .setCancelable(false)
                    .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                            finish();
                        }
                    })
                    .setNegativeButton("No", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                            dialog.cancel();
                        }
                    });
            android.app.AlertDialog alert = builder.create();
            alert.show();
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        final MenuItem menuItem = menu.findItem(R.id.action_notification);
        MenuItemCompat.setActionView(menuItem, R.layout.notification_count);
        View view = MenuItemCompat.getActionView(menuItem);
        ll_notifi_count = view.findViewById(R.id.ll_notifi_count);
        notification_count = (TextView) view.findViewById(R.id.count);
        notification_count.setVisibility(View.GONE);
        notification_count.setText(String.valueOf(strTodayVisitCount));
        setupNotiCount();

        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onOptionsItemSelected(menuItem);
            }
        });
        return true;
    }

    private void setupNotiCount() {

        if(notification_count != null){
            if(mCartItemCount == 0){
                if (notification_count.getVisibility() != View.GONE){
                    notification_count.setVisibility(View.GONE);
                }
            }else{
//                notification_count.setText(String.valueOf(strTodayVisitCount));
                if (notification_count.getVisibility() != View.VISIBLE){
                    notification_count.setVisibility(View.VISIBLE);
                }
            }
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {
            case R.id.action_notification:
                android.support.v4.app.FragmentManager fragmentManager = getSupportFragmentManager();
                fragmentManager.beginTransaction().replace(R.id.content,
                        NotificationFragment.newInstance()).commit();
                return true;

            case R.id.language:
                Intent v = new Intent(MainActivity.this, Language.class);
                finish();
                startActivity(v);
                return true;

            case R.id.action_help:
                Intent i = new Intent(MainActivity.this, Help.class);
                finish();
                startActivity(i);
                return true;
            default:
                super.onOptionsItemSelected(item);
        }
        return true;


    }

    /*Slide navigation*/
    @SuppressWarnings("StatementWithEmptyBody")
    @Override

    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.primary_register) {
            Intent i = new Intent(getApplicationContext(), PrimaryRegisterView.class);
            startActivity(i);
        } else if (id == R.id.visit_record) {
            Intent i = new Intent(getApplicationContext(), AddRecords.class);
            startActivity(i);
        }
        else if (id == R.id.nearbyhospital) {
            Intent i = new Intent(getApplicationContext(), NearHospitalActivity.class);
            startActivity(i);
        } else if (id == R.id.immunization_menu) {
            Intent i = new Intent(getApplicationContext(), ImmunizationActivity.class);
            startActivity(i);
        } else if (id == R.id.health_tips) {
            Intent i = new Intent(getApplicationContext(), HealthTipsActivity.class);
            startActivity(i);
        } else if (id == R.id.pn_hbnc_visit_entry) {
            Intent v = new Intent(getApplicationContext(), PNHBNCVisitEntry.class);
            startActivity(v);
        } else if (id == R.id.immunization_entry) {
            AppConstants.IMMUNIZATION_EDIT = true;
            Intent v = new Intent(getApplicationContext(), ImmunizationEditActivity.class);
            startActivity(v);
        }

        else if (id == R.id.delivery_details_entry) {
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
            Menu menu = bottomNavigationView.getMenu();
            selectFragment(menu.getItem(0));
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
                selectedFragment = health_records.newInstance();
                break;

            case R.id.pn_hbnc_visit:
                selectedFragment = PNhbncVisit.newInstance();
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

    }

    @Override
    public void showFlashResult(String response) {

    }

    @Override
    public void showErrorMessage(String response) {

    }


    @Override
    public void onDestroy(){
        super.onDestroy();
        pDialog.hide();
       /* if (pDialog!=null && pDialog.isShowing() ){
            pDialog.cancel();
        }*/
    }

    @Override
    public void NotificationResponseSuccess(String response) {

    }

    @Override
    public void NotificationResponseError(String response) {
        Log.d(MainActivity.class.getSimpleName(), "Notification count response Error" + response);

    }

    @Override
    public void NotificationCountSuccess(String response) {

        Log.d(MainActivity.class.getSimpleName(), "Notification count response success" + response);
        try {
            JSONObject jsonObject = new JSONObject(response);
            String status = jsonObject.getString("status");
            String msg = jsonObject.getString("message");

            if (status.equalsIgnoreCase("1")) {
                notification_count.setVisibility(View.VISIBLE);
                String strNotifyCount = jsonObject.getString("count");
                preferenceData.setNotificationCount(strNotifyCount);
                Log.d(MainActivity.class.getSimpleName(), "Notification Count-->" + strNotifyCount);
            } else {
                if(msg.equalsIgnoreCase("No Notification")) {
//                    notification_count.setVisibility(View.GONE);
                    Log.d(MainActivity.class.getSimpleName(), "Notification message-->" + msg);
                }
            }

        } catch (JSONException e) {
            e.printStackTrace();
        }




    }

    @Override
    public void NotificationCountError(String response) {
        Log.d(MainActivity.class.getSimpleName(), "Notification count response Error" + response);
    }

    /*@Override
    public void showPickmeResult(String response) {
        Log.d(MainActivity.class.getSimpleName(), "Response Success--->" + response);
//        showAlertDialog("If you are not feeling well please Click here.", response, 0);

        try {
            JSONObject jsonObject = new JSONObject(response);
            String status = jsonObject.getString("status");
            String msg = jsonObject.getString("message");
//            showAlertDialog(msg);

            if (status.equalsIgnoreCase("1")) {
                showAlertDialog(msg, "close", 5);

                Toast.makeText(getApplicationContext(),msg,Toast.LENGTH_SHORT).show();
//                showAlertDialog(msg);
//                startActivity(new Intent(getApplicationContext(),MainActivity.class));
            } else {
                showAlertDialog(msg, "close", 5);
            }

        } catch (JSONException e) {
            e.printStackTrace();
        }
    }*/

    @Override
    protected void onResume() {
        super.onResume();
        pDialog.hide();
//        MyApplication.getInstance().setConnectivityListener(this);


    }

   /* @Override
    public void showFlashResult(String response) {
        showAlertDialog(response, "close", 5);
    }
*/
    /*@Override
    public void showErrorMessage(String response) {
        Log.d(AddRecords.class.getSimpleName(), "Response Error--->" + response);
        showAlertDialog(response, "close", 5);
    }*/


    @Override
    public void onNetworkConnectionChanged(boolean isConnected) {

    }
}
