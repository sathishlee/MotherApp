package com.unicef.thaimai.motherapp.activity;

import android.Manifest;
import android.annotation.TargetApi;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.res.ColorStateList;
import android.graphics.Bitmap;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.TextInputLayout;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewCompat;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.graphics.Palette;
import android.support.v7.widget.Toolbar;
import android.transition.Slide;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.MemoryPolicy;
import com.squareup.picasso.NetworkPolicy;
import com.squareup.picasso.Picasso;
import com.unicef.thaimai.motherapp.Preference.PreferenceData;
import com.unicef.thaimai.motherapp.Presenter.ImageUploadPresenter;
import com.unicef.thaimai.motherapp.Presenter.ProfilePresenter;
import com.unicef.thaimai.motherapp.R;
import com.unicef.thaimai.motherapp.constant.Apiconstants;
import com.unicef.thaimai.motherapp.realmDbModelClass.ProfileRealmModel;
import com.unicef.thaimai.motherapp.utility.CheckNetwork;
import com.unicef.thaimai.motherapp.view.ImageUploadViews;
import com.unicef.thaimai.motherapp.view.ProfileView;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

import io.realm.Realm;
import io.realm.RealmResults;

import static android.Manifest.permission.CAMERA;
import static android.Manifest.permission.GET_ACCOUNTS;
import static android.Manifest.permission.INTERNET;
import static android.Manifest.permission.READ_EXTERNAL_STORAGE;
import static android.Manifest.permission.RECORD_AUDIO;
import static android.Manifest.permission.SEND_SMS;
import static android.Manifest.permission.WRITE_EXTERNAL_STORAGE;
import static android.Manifest.permission.WRITE_SETTINGS;


public class ProfileActivity extends AppCompatActivity implements ProfileView, View.OnClickListener, ImageUploadViews, View.OnFocusChangeListener {

    public static final String TITLE = "Profile";
    public static final String UPLOAD_IMAGE = "user_profile_photo";
    private static final int PICK_IMAGE_REQUEST = 1;
    private static final int SELECT_FILE = 2;
    private static final int REQUEST_CAMERA = 3;

    CollapsingToolbarLayout toolbar_layout;

    ImageView user_profile_photo;
    Intent intent;
    Context context;
    TextView user_name, edt_picme_id, address, village_name, tvNumber5, district_name, tvNumber1, number_1;
    ProgressDialog pDialog;
    ProfilePresenter profilePresenter;
    PreferenceData preferenceData;

    ImageUploadPresenter imageUploadPresenter;

    public static final int RequestPermissionCode = 7;

    public static final int MY_PERMISSIONS_REQUEST_READ_EXTERNAL_STORAGE = 123;
    String userChoosenTask, str_mPhoto;

    boolean isoffline = false;
    Realm realm;
    CheckNetwork checkNetwork;

    RelativeLayout re_enter_number, re_number, emer_enter_number, alter_number;
    TextInputLayout input_layout_phone;
    EditText edt_phone,edt_phone_hus;
    LinearLayout support_layout, support_layout1, btn_open;
    Button btn_submit, btn_cancel;
    FloatingActionButton fab;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initActivityTransitions();
        setContentView(R.layout.layout_profile);
        showActionBar();

        if(CheckingPermissionIsEnabledOrNot())
        {
            Toast.makeText(ProfileActivity.this, "All Permissions Granted Successfully", Toast.LENGTH_LONG).show();
        }

        // If, If permission is not enabled then else condition will execute.
        else {

            //Calling method to enable permission.
            RequestMultiplePermission();

        }
      /*  if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M && ContextCompat.checkSelfPermission(this,
                android.Manifest.permission.READ_EXTERNAL_STORAGE)
                != PackageManager.PERMISSION_GRANTED) {
            Intent intent = new Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS,
                    Uri.parse("package:" + getPackageName()));
            finish();
            startActivity(intent);
            return;
        }*/
        initUI();
        onClickListner();

    }

    private void setValuesEdtNumber() {
        if (checkNetwork.isNetworkAvailable()) {
            user_name.setVisibility(View.VISIBLE);
            edt_picme_id.setVisibility(View.VISIBLE);
            address.setVisibility(View.VISIBLE);
            village_name.setVisibility(View.VISIBLE);
            re_number.setVisibility(View.GONE);
            support_layout.setVisibility(View.GONE);
            re_enter_number.requestFocus();
            re_enter_number.setFocusableInTouchMode(true);
            InputMethodManager imm = (InputMethodManager) this.getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.showSoftInput(re_enter_number,InputMethodManager.SHOW_FORCED);
//            re_enter_number.setOnFocusChangeListener(this);
            re_enter_number.setVisibility(View.VISIBLE);
            alter_number.setVisibility(View.GONE);
            support_layout1.setVisibility(View.GONE);
            emer_enter_number.requestFocus();
            emer_enter_number.postDelayed(new Runnable() {
                @Override
                public void run() {
                    InputMethodManager inputMethodManager = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                    inputMethodManager.showSoftInput(emer_enter_number, InputMethodManager.SHOW_FORCED);
                }
            },1000);
            emer_enter_number.setFocusable(true);
           /* emer_enter_number.setOnFocusChangeListener(this);*/
            emer_enter_number.setVisibility(View.VISIBLE);
            btn_open.requestFocus();
            btn_open.setFocusable(true);
//            btn_open.setOnFocusChangeListener(this);

            btn_open.setVisibility(View.VISIBLE);
            /*btn_submit.requestFocus();
            btn_submit.setFocusable(true);
            btn_submit.setFocusableInTouchMode(true);
            btn_submit.postDelayed(new Runnable() {
                @Override
                public void run() {
                    InputMethodManager inputMethodManager = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                    inputMethodManager.showSoftInput(btn_open, InputMethodManager.SHOW_FORCED);
                }
            },1000);
            btn_cancel.requestFocus();
            btn_cancel.setFocusable(true);
            btn_cancel.setFocusableInTouchMode(true);*/
        }else {
            Toast.makeText(getApplicationContext(),"Please Turn On Internet to edit.. \n No Internert connection",Toast.LENGTH_LONG).show();
        }
    }

    private void showActionBar(){
        ViewCompat.setTransitionName(findViewById(R.id.app_bar), UPLOAD_IMAGE);
        supportPostponeEnterTransition();
        setSupportActionBar((Toolbar) findViewById(R.id.toolbar));
        ActionBar actionBar = getSupportActionBar();
        actionBar.setTitle("Profile");
        actionBar.setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        String itemTitle = getIntent().getStringExtra(TITLE);
        toolbar_layout = (CollapsingToolbarLayout) findViewById(R.id.toolbar_layout);
        toolbar_layout.setTitle(itemTitle);
        toolbar_layout.setExpandedTitleColor(getResources().getColor(android.R.color.transparent));
    }

    private void onClickListner() {
        user_profile_photo.setOnClickListener(this);
        btn_submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(edt_phone.getText().toString().equalsIgnoreCase("")){
                    edt_phone.setError("Enter Phone Number");
                } else if (edt_phone.getText().toString().length()!=10){
                    edt_phone.setError("Enter Valid Number");
                } else if(edt_phone_hus.getText().toString().equalsIgnoreCase("")){
                    edt_phone_hus.setError("Enter Husband Number");
                } else if(edt_phone_hus.getText().toString().length()!=10){
                    edt_phone_hus.setError("Enter Valid Number");
                } else{
                    profilePresenter.sendMotherProfile(preferenceData.getMId(),preferenceData.getPicmeId(),
                            edt_phone.getText().toString(),edt_phone_hus.getText().toString());
                }
            }
        });

        btn_cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                user_name.setVisibility(View.VISIBLE);
                edt_picme_id.setVisibility(View.VISIBLE);
                address.setVisibility(View.VISIBLE);
                village_name.setVisibility(View.VISIBLE);
                re_number.setVisibility(View.VISIBLE);
                support_layout.setVisibility(View.VISIBLE);
                re_enter_number.setVisibility(View.GONE);
                alter_number.setVisibility(View.VISIBLE);
                support_layout1.setVisibility(View.VISIBLE);
                emer_enter_number.setVisibility(View.GONE);
                btn_open.setVisibility(View.GONE);
                fab.setVisibility(View.VISIBLE);

            }
        });
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                pDialog.show();
                for (int i=0;i<25;i++){
                    pDialog.hide();
                }
                fab.setVisibility(View.GONE);
                btn_open.setFocusable(true);
                setValuesEdtNumber();
            }
        });

    }

    private void initUI() {
        checkNetwork =new CheckNetwork(this);
        pDialog = new ProgressDialog(this);
        pDialog.setCancelable(false);
        pDialog.setMessage("Please Wait ...");
        preferenceData = new PreferenceData(this);
        context = ProfileActivity.this;

        profilePresenter = new ProfilePresenter(ProfileActivity.this, this);
        imageUploadPresenter = new ImageUploadPresenter(ProfileActivity.this, this);
        if (checkNetwork.isNetworkAvailable()) {
            profilePresenter.getMotherProfile(preferenceData.getMId(), preferenceData.getPicmeId());
        }else {
            isoffline=true;
        }
         fab = (FloatingActionButton) findViewById(R.id.fab);

        re_number = (RelativeLayout)findViewById(R.id.re_number);
        re_enter_number = (RelativeLayout)findViewById(R.id.re_enter_number);
        emer_enter_number = (RelativeLayout)findViewById(R.id.emer_enter_number);
        alter_number = (RelativeLayout)findViewById(R.id.alter_number);
        input_layout_phone = (TextInputLayout) findViewById(R.id.input_layout_phone);
        edt_phone = (EditText) findViewById(R.id.edt_phone);
        edt_phone_hus = (EditText) findViewById(R.id.edt_phone_hus);
        support_layout = (LinearLayout) findViewById(R.id.support_layout);
        support_layout1 = (LinearLayout) findViewById(R.id.support_layout1);
        btn_open = (LinearLayout) findViewById(R.id.btn_open);

        btn_submit = (Button) findViewById(R.id.btn_submit);
        btn_cancel = (Button) findViewById(R.id.btn_cancel);

        user_profile_photo = (ImageView) findViewById(R.id.user_profile_photo);
        user_name = (TextView) findViewById(R.id.user_name);
        edt_picme_id = (TextView) findViewById(R.id.edt_picme_id);
        address = (TextView) findViewById(R.id.address);
        village_name = (TextView) findViewById(R.id.village_name);
        tvNumber5 = (TextView) findViewById(R.id.tvNumber5);
        district_name = (TextView) findViewById(R.id.district_name);
        tvNumber1 = (TextView) findViewById(R.id.tvNumber1);
        number_1 = (TextView) findViewById(R.id.number_1);

        if (isoffline) {
            profileOffline();
        }else{
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setMessage("Record Not Found");
            builder.create();
        }


    }

    private void profileOffline() {
        realm.beginTransaction();
        RealmResults<ProfileRealmModel> profileRealmModels;
        profileRealmModels = realm.where(ProfileRealmModel.class).findAll();
        Log.e(String.valueOf(ProfileActivity.class), profileRealmModels.size() + "");
        Log.e(ProfileActivity.class.getSimpleName(), "profileRealmModels  -->" + profileRealmModels);

        for (int i = 0; i < profileRealmModels.size(); i++) {
            ProfileRealmModel model = profileRealmModels.get(i);

            if(model.getMName().equalsIgnoreCase("null")){
                user_name.setText("-");
            }else {
                user_name.setText(model.getMName());
            }
            if(model.getMPicmeId().equalsIgnoreCase("null")){
                edt_picme_id.setVisibility(View.GONE);
            }else {
                edt_picme_id.setText(model.getMPicmeId());
            }
            if(model.getmAddress().equalsIgnoreCase("null")){
                address.setVisibility(View.GONE);
            }else {
                address.setText(model.getmAddress());
            }
            if(model.getMVillage().equalsIgnoreCase("null")){
                village_name.setVisibility(View.GONE);
            }else {
                village_name.setText(model.getMVillage());
            }
            if(model.getMDistrict().equalsIgnoreCase("null")){
                district_name.setVisibility(View.GONE);
            }else {
                district_name.setText(model.getMDistrict());
            }
            if(model.getMMotherMobile().equalsIgnoreCase("null")){
                tvNumber1.setVisibility(View.GONE);
            }else {
                tvNumber1.setText(model.getMMotherMobile());
            }
            if(model.getBlockname().equalsIgnoreCase("null")){
                tvNumber5.setText("-");
            }else{
                tvNumber5.setText(model.getBlockname());
            }
            if(model.getMHusbandMobile().equalsIgnoreCase("null")){
                number_1.setVisibility(View.GONE);
            }else {
                number_1.setText(model.getMHusbandMobile());
            }
            if(model.getmPhoto().equalsIgnoreCase("null")){
                user_profile_photo.setVisibility(View.GONE);
            }else{
                str_mPhoto = model.getmPhoto();
                Picasso.with(context)
                        .load(Apiconstants.PHOTO_URL+str_mPhoto)
                        .placeholder(R.drawable.ln_logo)
                        .fit()
                        .centerCrop()
                        .memoryPolicy(MemoryPolicy.NO_CACHE)
                        .networkPolicy(NetworkPolicy.NO_CACHE)
                        .error(R.drawable.photo_upload)
                        .into(user_profile_photo);
            }



        }
    }
    @Override
    public void onBackPressed() {
        intent = new Intent(ProfileActivity.this, MainActivity.class);
        finish();
        startActivity(intent);
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        Intent intent = new Intent(ProfileActivity.this, MainActivity.class);
        finish();
        startActivity(intent);
        return super.onOptionsItemSelected(item);

    }

    private void initActivityTransitions() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            Slide transition = new Slide();
            transition.excludeTarget(android.R.id.statusBarBackground, true);
            getWindow().setEnterTransition(transition);
            getWindow().setReturnTransition(transition);
        }
    }

    private void applyPalette(Palette palette) {
        int primaryDark = getResources().getColor(R.color.primary_dark);
        int primary = getResources().getColor(R.color.primary);
        toolbar_layout.setContentScrimColor(palette.getMutedColor(primary));
        toolbar_layout.setStatusBarScrimColor(palette.getDarkMutedColor(primaryDark));
        updateBackground((FloatingActionButton) findViewById(R.id.fab), palette);
        supportStartPostponedEnterTransition();
    }

    private void updateBackground(FloatingActionButton fab, Palette palette) {
        int lightVibrantColor = palette.getLightVibrantColor(getResources().getColor(android.R.color.white));
        int vibrantColor = palette.getVibrantColor(getResources().getColor(R.color.accent));

        fab.setRippleColor(lightVibrantColor);
        fab.setBackgroundTintList(ColorStateList.valueOf(vibrantColor));
    }

    @Override
    public void showProgress() {
        pDialog.show();
    }

    @Override
    public void hideProgress() {
        pDialog.dismiss();
    }

    @Override
    public void onDestroy(){
        super.onDestroy();
        if (pDialog!=null && pDialog.isShowing() ){
            pDialog.cancel();
        }
    }

    @Override
    public void successUploadPhoto(String response) {
        Log.d("successUploadPhoto--->",response);

        Toast.makeText(ProfileActivity.this,"Photo Uploaded Successfully...",Toast.LENGTH_LONG).show();

        /*try {
            JSONObject jsonObject = new JSONObject(response);
            String status = jsonObject.getString("status");
            String msg = jsonObject.getString("message");
            if (status.equalsIgnoreCase("1")) {
                Toast.makeText(ProfileActivity.this,msg,Toast.LENGTH_LONG).show();
            }else{
                Toast.makeText(ProfileActivity.this,msg,Toast.LENGTH_LONG).show();
            }

        } catch (JSONException e) {
            e.printStackTrace();
        }*/
    }

    @Override
    public void errorUploadPhoto(String response) {
        Log.d("Image upload error",response);

    }


    @Override
    public void successViewProfile(String response) {
        Log.d("ProfileActivity success",response);


        try {
            JSONObject jsonObject = new JSONObject(response);
            String status = jsonObject.getString("status");
            String msg = jsonObject.getString("message");
            if (status.equalsIgnoreCase("1")) {
                JSONObject editprofile = jsonObject.getJSONObject("EditProfile");

                user_name.setText(editprofile.getString("mName"));
                edt_picme_id.setText(editprofile.getString("mPicmeId"));
                address.setText(editprofile.getString("mAddress"));
                village_name.setText(editprofile.getString("mVillage"));
                district_name.setText(editprofile.getString("mDistrict"));
                tvNumber1.setText(editprofile.getString("mMotherMobile"));
                edt_phone.setText(editprofile.getString("mMotherMobile"));
                number_1.setText(editprofile.getString("mHusbandMobile"));
                edt_phone_hus.setText(editprofile.getString("mHusbandMobile"));
                tvNumber5.setText(editprofile.getString("blockname"));

                str_mPhoto = editprofile.getString("mPhoto");

                if(editprofile.getString("mPhoto").equalsIgnoreCase("null")){
                    preferenceData.setMotherPhoto("");
                }else{
                    preferenceData.setMotherPhoto(editprofile.getString("mPhoto"));
                }

                Log.d("mphoto-->", Apiconstants.PHOTO_URL+str_mPhoto);

                Picasso.with(context)
                        .load(Apiconstants.PHOTO_URL+str_mPhoto)
                        .placeholder(R.drawable.ln_logo)
                        .fit()
                        .centerCrop()
                        .memoryPolicy(MemoryPolicy.NO_CACHE)
                        .networkPolicy(NetworkPolicy.NO_CACHE)
                        .error(R.drawable.photo_upload)
                        .into(user_profile_photo);
            }

        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void errorViewProfile(String response) {
        Log.d("ProfileActivity error", response);
    }

    @Override
    public void successupdateProfile(String response) {

        try {
            JSONObject jsonObject = new JSONObject(response);
            String status = jsonObject.getString("status");
            String msg = jsonObject.getString("message");
            if (status.equalsIgnoreCase("1")) {
                Toast.makeText(this,msg,Toast.LENGTH_SHORT).show();
                Intent intent = getIntent();
                finish();
                startActivity(intent);
                user_name.setVisibility(View.VISIBLE);
                edt_picme_id.setVisibility(View.VISIBLE);
                address.setVisibility(View.VISIBLE);
                village_name.setVisibility(View.VISIBLE);
                re_number.setVisibility(View.VISIBLE);
                support_layout.setVisibility(View.VISIBLE);
                re_enter_number.setVisibility(View.GONE);
                alter_number.setVisibility(View.VISIBLE);
                support_layout1.setVisibility(View.VISIBLE);
                emer_enter_number.setVisibility(View.GONE);
                btn_open.setVisibility(View.GONE);
            }else{
                Toast.makeText(this,msg,Toast.LENGTH_SHORT).show();
            }

        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void errorUpdateProfile(String response) {

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {

            case R.id.user_profile_photo:
                selectImage();
                break;

        }
    }


    private void selectImage() {
        //if everything is ok we will open image chooser
//        Intent i = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
//        startActivityForResult(i, 100);

        final CharSequence[] items = {"Take Photo", "Choose from Library",
                "Cancel"};

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Add Photo!");
        builder.setItems(items, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int item) {
                boolean result = checkPermission();
                if (items[item].equals("Take Photo")) {
                    userChoosenTask = "Take Photo";
                    if (result)
                        cameraIntent();
                } else if (items[item].equals("Choose from Library")) {
                    userChoosenTask = "Choose from Library";
                    if (result)
                        galleryIntent();
                } else if (items[item].equals("Cancel")) {
                    dialog.dismiss();
                }
            }
        });
        builder.show();
    }

    private void galleryIntent() {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);//
        startActivityForResult(Intent.createChooser(intent, "Select File"),SELECT_FILE);
    }

    private void cameraIntent() {
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        startActivityForResult(intent, REQUEST_CAMERA);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode) {

            case RequestPermissionCode:

                if (grantResults.length > 0) {

                    boolean CameraPermission = grantResults[0] == PackageManager.PERMISSION_GRANTED;
                    boolean RecordAudioPermission = grantResults[1] == PackageManager.PERMISSION_GRANTED;
                    boolean SendSMSPermission = grantResults[2] == PackageManager.PERMISSION_GRANTED;
                    boolean GetAccountsPermission = grantResults[3] == PackageManager.PERMISSION_GRANTED;

                    if (CameraPermission && RecordAudioPermission && SendSMSPermission && GetAccountsPermission) {

//                        Toast.makeText(ImageSelectedActivity.this, "Permission Granted", Toast.LENGTH_LONG).show();
                    }
                    else {
//                        Toast.makeText(ImageSelectedActivity.this,"Permission Denied",Toast.LENGTH_LONG).show();

                    }
                }

                break;
        }
    }

    private void RequestMultiplePermission() {
        // Creating String Array with Permissions.
        ActivityCompat.requestPermissions(ProfileActivity.this, new String[]
                {CAMERA,
                        RECORD_AUDIO,
                        SEND_SMS,
                        GET_ACCOUNTS,
                        READ_EXTERNAL_STORAGE,
                        WRITE_EXTERNAL_STORAGE,
                        INTERNET,
                        WRITE_SETTINGS}, RequestPermissionCode);

    }

    private boolean CheckingPermissionIsEnabledOrNot() {
        int FirstPermissionResult = ContextCompat.checkSelfPermission(getApplicationContext(), CAMERA);
        int SecondPermissionResult = ContextCompat.checkSelfPermission(getApplicationContext(), RECORD_AUDIO);
        int ThirdPermissionResult = ContextCompat.checkSelfPermission(getApplicationContext(), SEND_SMS);
        int ForthPermissionResult = ContextCompat.checkSelfPermission(getApplicationContext(), GET_ACCOUNTS);
        int FivePermissionResult = ContextCompat.checkSelfPermission(getApplicationContext(), READ_EXTERNAL_STORAGE);
        int SixPermissionResult = ContextCompat.checkSelfPermission(getApplicationContext(), WRITE_EXTERNAL_STORAGE);
        int SevenPermissionResult = ContextCompat.checkSelfPermission(getApplicationContext(), INTERNET);
        int EightPermissionResult = ContextCompat.checkSelfPermission(getApplicationContext(), WRITE_SETTINGS);

        return FirstPermissionResult == PackageManager.PERMISSION_GRANTED &&
                SecondPermissionResult == PackageManager.PERMISSION_GRANTED &&
                ThirdPermissionResult == PackageManager.PERMISSION_GRANTED &&
                ForthPermissionResult == PackageManager.PERMISSION_GRANTED &&
                FivePermissionResult == PackageManager.PERMISSION_GRANTED &&
                SixPermissionResult == PackageManager.PERMISSION_GRANTED &&
                SevenPermissionResult == PackageManager.PERMISSION_GRANTED &&
                EightPermissionResult == PackageManager.PERMISSION_GRANTED;
    }

    private boolean checkPermission() {
        int currentAPIVersion = Build.VERSION.SDK_INT;
        if (currentAPIVersion >= android.os.Build.VERSION_CODES.M) {
            if (ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
                if (ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.READ_EXTERNAL_STORAGE)) {
                    AlertDialog.Builder alertBuilder = new AlertDialog.Builder(this);
                    alertBuilder.setCancelable(true);
                    alertBuilder.setTitle("Permission necessary");
                    alertBuilder.setMessage("External storage permission is necessary");
                    alertBuilder.setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                        @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
                        public void onClick(DialogInterface dialog, int which) {
                            ActivityCompat.requestPermissions(ProfileActivity.this, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, MY_PERMISSIONS_REQUEST_READ_EXTERNAL_STORAGE);
                        }
                    });
                    AlertDialog alert = alertBuilder.create();
                    alert.show();
                } else {
                    ActivityCompat.requestPermissions(ProfileActivity.this, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, MY_PERMISSIONS_REQUEST_READ_EXTERNAL_STORAGE);
                }
                return false;
            } else {
                return true;
            }
        } else {
            return true;
        }
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

     /*   if (requestCode == 100 && resultCode == RESULT_OK && data != null) {

            //getting the image Uri
            Uri imageUri = data.getData();
            try {
                //getting bitmap object from uri
                Bitmap bitmap = MediaStore.Images.Media.getBitmap(this.getContentResolver(), imageUri);

                //displaying selected image to imageview
                user_profile_photo.setImageBitmap(bitmap);

                //calling the method uploadUserProfilePhoto to upload image
                uploadUserProfilePhoto(bitmap);
                Log.d("bitmap",bitmap.getByteCount()+"");
            } catch (IOException e) {
                e.printStackTrace();
            }
        }*/

        if (resultCode == Activity.RESULT_OK) {
            if (requestCode == SELECT_FILE)
                onSelectFromGalleryResult(data);
            else if (requestCode == REQUEST_CAMERA)
                onCaptureImageResult(data);
        }
    }

    private void onCaptureImageResult(Intent data) {
        Bitmap thumbnail = (Bitmap) data.getExtras().get("data");
        ByteArrayOutputStream bytes = new ByteArrayOutputStream();
        thumbnail.compress(Bitmap.CompressFormat.JPEG, 90, bytes);
        File destination = new File(Environment.getExternalStorageDirectory(),
                System.currentTimeMillis() + ".jpg");
        FileOutputStream fo;
        try {
            destination.createNewFile();
            fo = new FileOutputStream(destination);
            fo.write(bytes.toByteArray());
            fo.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        user_profile_photo.setImageBitmap(thumbnail);
        imageUploadPresenter.uploadUserProfilePhoto(preferenceData.getPicmeId(),thumbnail);

    }

    private void onSelectFromGalleryResult(Intent data) {
        Bitmap bm=null;
        if (data != null) {
            try {
                bm = MediaStore.Images.Media.getBitmap(getApplicationContext().getContentResolver(), data.getData());
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        user_profile_photo.setImageBitmap(bm);
        imageUploadPresenter.uploadUserProfilePhoto(preferenceData.getPicmeId(),bm);
    }

    private void uploadBitmap(Bitmap bitmap) {

    }

    @Override
    public void onFocusChange(View v, boolean hasFocus) {
        edt_phone_hus.requestFocus();
        edt_phone.requestFocus();
        btn_open.requestFocus();

    }
}
