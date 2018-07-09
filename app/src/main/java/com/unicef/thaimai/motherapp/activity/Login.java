package com.unicef.thaimai.motherapp.activity;

import android.Manifest;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.DownloadManager;
import android.app.ProgressDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.net.ConnectivityManager;
import android.net.Uri;
import android.net.wifi.WifiManager;
import android.os.Build;
import android.os.Bundle;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.support.design.widget.TextInputLayout;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
import android.telephony.TelephonyManager;
import android.text.format.Formatter;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.unicef.thaimai.motherapp.Preference.PreferenceData;
import com.unicef.thaimai.motherapp.Presenter.LocationUpdatePresenter;
import com.unicef.thaimai.motherapp.Presenter.LoginPresenter;
import com.unicef.thaimai.motherapp.R;
import com.unicef.thaimai.motherapp.constant.Apiconstants;
import com.unicef.thaimai.motherapp.constant.AppConstants;
import com.unicef.thaimai.motherapp.utility.CheckNetwork;
import com.unicef.thaimai.motherapp.utility.DownloadTask;
import com.unicef.thaimai.motherapp.utility.LocationMonitoringService;
import com.unicef.thaimai.motherapp.view.LocationUpdateViews;
import com.unicef.thaimai.motherapp.view.LoginViews;

import net.alexandroid.gps.GpsStatusDetector;

import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.UUID;


public class Login extends AppCompatActivity implements View.OnClickListener, LoginViews, GpsStatusDetector.GpsStatusDetectorCallBack, LocationUpdateViews {
    Button btn_login, btn_otp_submit;
    EditText edtPicme, edtDob, edt_otp;
    TextInputLayout iplPicmeId, iplDob, input_layout_otp;
    TextView txtForgetPicme, txt_vhn_nameotp;
    String strPicme, strDob, strOtp;
    ProgressDialog pDialog;
    LoginPresenter loginPresenter;
    PreferenceData preferenceData;
    ConnectivityManager conMgr;
    Calendar mCurrentDate;
    CheckNetwork checkNetwork;
    int day, month, year, hour, minute, sec;
    private String message;
    LinearLayout ll_signin, ll_otp, ll_vhn_not_found;
    CardView card_vhn_number;
    ImageView img_call_vhnnumber;
    String str_call_vhn, str_vhn_status, mobileCheck, ipAddress, apiVersion;
    private static final int MAKE_CALL_PERMISSION_REQUEST_CODE = 1;
    Boolean IPValue;
    String currentVersion;
    private GpsStatusDetector mGpsStatusDetector;
    boolean mISGpsStatusDetector;
    private int mYear,mMonth,mDay;
    private SimpleDateFormat dateFormatter;
    Activity activity;
    LocationUpdatePresenter locationUpdatePresenter;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login);
        mGpsStatusDetector = new GpsStatusDetector(this);
        mGpsStatusDetector.checkGpsStatus();
        checkNetwork = new CheckNetwork(this);

        conMgr = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        {
            if (conMgr.getActiveNetworkInfo() != null
                    && conMgr.getActiveNetworkInfo().isAvailable()
                    && conMgr.getActiveNetworkInfo().isConnected()) {
            } else {
                Toast.makeText(getApplicationContext(), "No Internet Connection",
                        Toast.LENGTH_LONG).show();
            }
        }

        initUI();
        onClickListner();
    }

    private void initUI() {
        pDialog = new ProgressDialog(this);
        pDialog.setCancelable(false);
        pDialog.setMessage("Please Wait ...");
        mCurrentDate = Calendar.getInstance();
        day = mCurrentDate.get(Calendar.DAY_OF_MONTH);
        month = mCurrentDate.get(Calendar.MONTH);
        year = mCurrentDate.get(Calendar.YEAR);
        hour = mCurrentDate.get(Calendar.HOUR);
        minute = mCurrentDate.get(Calendar.MINUTE);
        sec = mCurrentDate.get(Calendar.SECOND);
        loginPresenter = new LoginPresenter(Login.this, this);
        preferenceData = new PreferenceData(this);
        locationUpdatePresenter = new LocationUpdatePresenter(Login.this, this);

        btn_login = (Button) findViewById(R.id.btn_submit);
        edtPicme = (EditText) findViewById(R.id.edt_picme_id);
        edtDob = (EditText) findViewById(R.id.edt_dob);
        txtForgetPicme = (TextView) findViewById(R.id.txt_forgot_picme);
        iplPicmeId = (TextInputLayout) findViewById(R.id.input_layout_picme_id);
        iplDob = (TextInputLayout) findViewById(R.id.input_layout_dob);
        ll_signin = (LinearLayout) findViewById(R.id.ll_signin);
        ll_otp = (LinearLayout) findViewById(R.id.ll_otp);
        ll_vhn_not_found = (LinearLayout) findViewById(R.id.ll_vhn_not_found);
        card_vhn_number = (CardView) findViewById(R.id.card_vhn_number);
        img_call_vhnnumber = (ImageView)findViewById(R.id.img_call_vhnnumber);
        txt_vhn_nameotp = (TextView) findViewById(R.id.txt_vhn_nameotp);

        input_layout_otp = (TextInputLayout) findViewById(R.id.input_layout_otp);
        edt_otp = (EditText) findViewById(R.id.edt_otp);
        btn_otp_submit = (Button) findViewById(R.id.btn_otp_submit);
    }

    private void onClickListner() {
        btn_login.setOnClickListener(this);
        txtForgetPicme.setOnClickListener(this);
        edtDob.setOnClickListener(this);
        btn_otp_submit.setOnClickListener(this);
        img_call_vhnnumber.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_submit:
                getValue();
                break;
            case R.id.btn_otp_submit:
                enterOtp();
                break;

            case R.id.txt_forgot_picme:
                goforgetPicmepage();
                break;

            case R.id.edt_dob:
                getDob(edtDob);
                break;
            case R.id.img_call_vhnnumber:
                makeCall(str_call_vhn);
                break;
        }

    }

    private void enterOtp() {
        strOtp = edt_otp.getText().toString();

        if(strOtp.equalsIgnoreCase("")){
            input_layout_otp.setError("Please Enter OTP");
        }else{
            if (mISGpsStatusDetector) {
                if (checkNetwork.isNetworkAvailable()) {
                    loginPresenter.checkOtp(preferenceData.getCheckPicmeID(), preferenceData.getCheckDob(), preferenceData.getDeviceId(), strOtp, mobileCheck);
                }else {
                    startActivity(new Intent(getApplicationContext(), NoInternetConnection.class));
                }
            }else{
                Toast.makeText(getApplicationContext(), "Location Unavailable.. Please Turn on Location.." + checkNetwork.isNetworkAvailable(), Toast.LENGTH_LONG).show();
            }
        }
    }

    private void getDob(final EditText edtDob) {

        Calendar newCalendar = Calendar.getInstance();

        DatePickerDialog datePickerDialog = new DatePickerDialog(Login.this, R.style.DatePickerDialogTheme, new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                Calendar newDate = Calendar.getInstance();
                newDate.set(year, monthOfYear, dayOfMonth);
//                edtDob.setText(dayOfMonth + "-" + monthOfYear + "-" + year);
                dateFormatter = new SimpleDateFormat("dd-MM-yyyy", Locale.US);
                edtDob.setText(dateFormatter.format(newDate.getTime()));
            }
        }, newCalendar.get(Calendar.YEAR), newCalendar.get(Calendar.MONTH), newCalendar.get(Calendar.DAY_OF_MONTH));
        InputMethodManager imm =
                (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(edtDob.getWindowToken(), 0);

        datePickerDialog.show();

    }

    private void goforgetPicmepage() {
        startActivity(new Intent(getApplicationContext(), ForgotPasswordActivity.class));
    }

    private void getValue() {
        strPicme = edtPicme.getText().toString();
        strDob = edtDob.getText().toString();
        preferenceData.getDeviceId();

        if (strPicme.equalsIgnoreCase("")) {
            iplPicmeId.setError("Pickme ID is Empty");
        }
        if (strDob.equalsIgnoreCase("")) {
            iplDob.setError("Dob is Empty");
        }
        if (strPicme.length() < 10) {
            iplPicmeId.setError("Enter Correct Picme ID");
        }
        if (strPicme.length() > 14) {
            iplPicmeId.setError("Enter Correct Picme ID");
        } else {


//            loginPresenter.checkPickmeId(strPicme, strDob, "dT7h3twBpWU:APA91bHQqQOCBueyUGhvY2uIsMNfIfM7ynMlVzm89tTTWDeKhXzMWCS9WZL1gu8nFz_nkwU5Po9i8ytXHmjoxAeu36BTbIFHwWhWfjbWtO-EjG6n7zW4M_PFCCOID8eE0fQX4RPPHfBQ");
            WifiManager wifiManager = (WifiManager) getApplicationContext().getSystemService(WIFI_SERVICE);
            ipAddress = Formatter.formatIpAddress(wifiManager.getConnectionInfo().getIpAddress());

            mobileCheck = "Mobile:"+Build.MANUFACTURER +","+ "Model:" +Build.MODEL + "," + "Api Version:"
                    + Build.VERSION.RELEASE + "," + "SDK Version:" + Build.VERSION.SDK_INT + "," + "IP Address:"+ ipAddress;

            Log.d("Mobile Check Version-->", mobileCheck);

            PackageInfo packageInfo = null;
            String version_name = "Latest";
            int version_code = 4;
            String appversion = String.valueOf(version_code);

            TelephonyManager mTelephonyManager = (TelephonyManager) getSystemService(Context.TELEPHONY_SERVICE);
            String unique_deviceid = mTelephonyManager.getDeviceId();
            Log.d("Device Unique ID-- >", unique_deviceid);
            String androidId = Settings.Secure.getString(getContentResolver(),
                    Settings.Secure.ANDROID_ID);
            Log.d("android_id",androidId);

            String uuid = UUID.randomUUID().toString();
            Log.d("uuid",uuid);

            try {
                packageInfo = getPackageManager().getPackageInfo(getPackageName(), 0);
                version_name = packageInfo.versionName;
                version_code = packageInfo.versionCode;
            } catch (PackageManager.NameNotFoundException e) {
                e.printStackTrace();
            }
            if (checkNetwork.isNetworkAvailable()) {
                loginPresenter.checkPickmeId(strPicme, strDob, preferenceData.getDeviceId(), mobileCheck, AppConstants.EXTRA_LATITUDE, AppConstants.EXTRA_LONGITUDE, appversion, androidId);
//                loginPresenter.checkPickmeId(strPicme, strDob, preferenceData.getDeviceId(), mobileCheck, preferenceData.gettCurentlatitude(), preferenceData.gettCurentlongitude(), appversion, androidId);
            }else {
                startActivity(new Intent(getApplicationContext(), NoInternetConnection.class));
            }
        }
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
    public void locationUpdateSuccess(String loginResponseModel) {
        Log.e(Login.class.getSimpleName(),"Location update S"+loginResponseModel);
    }

    @Override
    public void locationUpdateFailiure(String string) {
        Log.e(Login.class.getSimpleName(),"Location update F "+string);

    }

    @Override
    public void getNearbyHospitalSuccess(String loginResponseModel) {

    }

    @Override
    public void getNearbyHospitalFailiure(String string) {

    }

    @Override
    public void loginSuccess(String response) {
        Log.d("Response success",response);
        JSONObject jsonObject = null;
        try{
            jsonObject = new JSONObject(response);
            String status = jsonObject.getString("status");
            String message = jsonObject.getString("message");

            if(message.equalsIgnoreCase("Please update the latest version app.")){

                AlertDialog.Builder builder = new AlertDialog.Builder(Login.this);
                builder.setTitle(R.string.app_name);
                builder.setIcon(R.mipmap.ic_launcher);
                builder.setMessage("Your are using Older Version of Apk Please Click Ok to Download New Apk")
                        .setCancelable(false)
                        .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                openUrl();
                            }
                        });
                AlertDialog alert = builder.create();
                alert.show();
            }
            else if (status.equalsIgnoreCase("1")){
                Toast.makeText(getApplicationContext(),message,Toast.LENGTH_SHORT).show();
                ll_otp.setVisibility(View.VISIBLE);
                card_vhn_number.setVisibility(View.VISIBLE);
                ll_vhn_not_found.setVisibility(View.GONE);
                ll_signin.setVisibility(View.GONE);
                str_vhn_status = jsonObject.getString("vhnStatus");
                if(str_vhn_status.equalsIgnoreCase("1")){
                    ll_vhn_not_found.setVisibility(View.GONE);
                    txt_vhn_nameotp.setText(jsonObject.getString("vhnName"));
                    str_call_vhn = jsonObject.getString("vhnMobile");
                    AppConstants.PICME_ID_NUM = jsonObject.getString("picmeId");
                    preferenceData.storePicmeInfo(jsonObject.getString("picmeId"),jsonObject.getString("DOB"));
                }else{
                    ll_vhn_not_found.setVisibility(View.VISIBLE);
                    card_vhn_number.setVisibility(View.GONE);
                    Toast.makeText(getApplicationContext(),"VHN Not Found...!",Toast.LENGTH_SHORT).show();
                }
            }else {
                Toast.makeText(getApplicationContext(),message,Toast.LENGTH_SHORT).show();
                ll_otp.setVisibility(View.GONE);
                ll_vhn_not_found.setVisibility(View.GONE);
                ll_signin.setVisibility(View.VISIBLE);
            }


        }catch (JSONException e) {
            e.printStackTrace();
        }
    }

    private void openUrl() {
        DownloadManager downloadManager;
        downloadManager = (DownloadManager)getSystemService(Context.DOWNLOAD_SERVICE);
        Uri uri = Uri.parse(Apiconstants.APK_URL+Apiconstants.DOWNLOAD_APK);
        DownloadManager.Request request = new DownloadManager.Request(uri);
        request.setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED);
        Long reference = downloadManager.enqueue(request);
    }

    @Override
    public void loginError(String string) {
        Log.d("Login Error-->", string);
    }

    @Override
    public void onDestroy(){
        super.onDestroy();
        if (pDialog!=null && pDialog.isShowing() ){
            pDialog.cancel();
        }
    }

    @Override
    public void showPickmeResult(String response) {
        Log.d("Response success", response);
        JSONObject jObj = null;
        try {
            jObj = new JSONObject(response);
            int status = jObj.getInt("status");
            String message = jObj.getString("message");
            Toast.makeText(getApplicationContext(),message,Toast.LENGTH_SHORT).show();
            if (status == 1) {
                Log.d("message---->", message);
                preferenceData.storeUserInfo(jObj.getString("picmeId"),jObj.getString("mid"),
                        jObj.getString("mName"), jObj.getString("motherAge"),
                        jObj.getString("motherStatus"), jObj.getString("phcId"), jObj.getString("vhnId"),
                        jObj.getString("awwId"), jObj.getString("mGesWeek"), jObj.getString("vhnMobile"));
                preferenceData.setLogin(true);
                preferenceData.setMainScreenOpen(0);
                AppConstants.POP_UP_COUNT=0;
                if (message.equalsIgnoreCase("Successfully Logined")) {
                    startActivity(new Intent(getApplicationContext(), MainActivity.class));
                    finish();
                } else {
                    AppConstants.BACK_BUTTON_GONE = false;
                    finish();
                }
            } else {
                Log.d("message---->", message);
                Toast.makeText(getApplicationContext(),message,Toast.LENGTH_SHORT).show();
            }

        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void showErrorMessage(String errormsg) {
        Log.d("Response field", errormsg);
    }

    @Override
    public void showForgetResult(String response) {

    }

    @Override
    public void showForgetErrorMessage(String string) {

    }

    @Override
    public void showUninstallSuccess(String response) {

    }

    @Override
    public void showUninstallError(String string) {

    }

    public void makeCall(String str_call_vhn) {
        Toast.makeText(this.getApplicationContext(),str_call_vhn,Toast.LENGTH_SHORT).show();

        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.CALL_PHONE)
                != PackageManager.PERMISSION_GRANTED) {
            // Camera permission has not been granted.

            requestCallPermission();

        } else {

            // Camera permissions is already available, show the camera preview.
            Log.i(Login.class.getSimpleName(),"CALL permission has already been granted. Displaying camera preview.");
//            showCameraPreview();
            startActivity(new Intent(Intent.ACTION_CALL, Uri.parse("tel:+"+str_call_vhn)));


        }
    }

    private void requestCallPermission() {


        Log.i(Login.class.getSimpleName(), "CALL permission has NOT been granted. Requesting permission.");

        // BEGIN_INCLUDE(camera_permission_request)
        if (ActivityCompat.shouldShowRequestPermissionRationale(this,
                Manifest.permission.CALL_PHONE)) {
            // Provide an additional rationale to the user if the permission was not granted
            // and the user would benefit from additional context for the use of the permission.
            // For example if the user has previously denied the permission.
            Log.i(Login.class.getSimpleName(),            "Displaying camera permission rationale to provide additional context.");
            Toast.makeText(this,"Displaying camera permission rationale to provide additional context.",Toast.LENGTH_SHORT).show();

        } else {

            // Camera permission has not been granted yet. Request it directly.
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.CALL_PHONE},
                    MAKE_CALL_PERMISSION_REQUEST_CODE);
        }
// END_INCLUDE(camera_permission_request)
    }
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        switch (requestCode) {
            case MAKE_CALL_PERMISSION_REQUEST_CODE:
                if (grantResults.length > 0 && (grantResults[0] == PackageManager.PERMISSION_GRANTED)) {
//                    dial.setEnabled(true);
                    Toast.makeText(this, "You can call the number by clicking on the button", Toast.LENGTH_SHORT).show();
                }
                return;
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        finish();
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {

        AlertDialog.Builder builder = new AlertDialog.Builder(Login.this);
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
        AlertDialog alert = builder.create();
        alert.show();

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        mGpsStatusDetector.checkOnActivityResult(requestCode, resultCode);
    }

    @Override
    public void onGpsSettingStatus(boolean enabled) {
        Log.d("TAG", "onGpsSettingStatus: " + enabled);
        mISGpsStatusDetector = enabled;
        if(!enabled){
            mGpsStatusDetector.checkGpsStatus();
        }
    }

    @Override
    public void onGpsAlertCanceledByUser() {
        Log.d("TAG", "onGpsAlertCanceledByUser");
        startActivity(new Intent(getApplicationContext(),TurnOnGpsLocation.class));
    }

}
