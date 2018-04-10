package com.unicef.thaimai.motherapp.activity;

import android.Manifest;
import android.annotation.TargetApi;
import android.app.Activity;
import android.app.ProgressDialog;
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
import android.support.design.widget.Snackbar;
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
import android.widget.ImageView;
import android.widget.TextView;


import com.unicef.thaimai.motherapp.Preference.PreferenceData;
import com.unicef.thaimai.motherapp.Presenter.ImageUploadPresenter;
import com.unicef.thaimai.motherapp.Presenter.ProfilePresenter;
import com.unicef.thaimai.motherapp.R;
import com.unicef.thaimai.motherapp.view.ImageUploadViews;
import com.unicef.thaimai.motherapp.view.ProfileView;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;


public class ProfileActivity extends AppCompatActivity implements ProfileView, View.OnClickListener, ImageUploadViews {

    public static final String TITLE = "Profile";
    public static final String UPLOAD_IMAGE = "user_profile_photo";
    private static final int PICK_IMAGE_REQUEST = 1;
    private static final int SELECT_FILE = 2;
    private static final int REQUEST_CAMERA = 3;

    CollapsingToolbarLayout toolbar_layout;

    ImageView user_profile_photo;
    Intent intent;

    TextView user_name, edt_picme_id, address, village_name, tvNumber5, district_name, tvNumber1, number_1;
    ProgressDialog pDialog;
    ProfilePresenter profilePresenter;
    PreferenceData preferenceData;

    ImageUploadPresenter imageUploadPresenter;

    public static final int MY_PERMISSIONS_REQUEST_READ_EXTERNAL_STORAGE = 123;
    String userChoosenTask;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initActivityTransitions();
        setContentView(R.layout.layout_profile);
        showActionBar();
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
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
    }

    private void initUI() {

        pDialog = new ProgressDialog(this);
        pDialog.setCancelable(false);
        pDialog.setMessage("Please Wait ...");
        preferenceData = new PreferenceData(this);

        profilePresenter = new ProfilePresenter(ProfileActivity.this, this);
        imageUploadPresenter = new ImageUploadPresenter(ProfileActivity.this, this);

        profilePresenter.getMotherProfile(preferenceData.getMId(), preferenceData.getPicmeId());
        user_profile_photo = (ImageView) findViewById(R.id.user_profile_photo);
        user_name = (TextView) findViewById(R.id.user_name);
        edt_picme_id = (TextView) findViewById(R.id.edt_picme_id);
        address = (TextView) findViewById(R.id.address);
        village_name = (TextView) findViewById(R.id.village_name);
        tvNumber5 = (TextView) findViewById(R.id.tvNumber5);
        district_name = (TextView) findViewById(R.id.district_name);
        tvNumber1 = (TextView) findViewById(R.id.tvNumber1);
        number_1 = (TextView) findViewById(R.id.number_1);


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
    public void successUploadPhoto(String response) {
        Log.d("Image upload success",response);
    }

    @Override
    public void errorUploadPhoto(String response) {
        Log.d("Image upload error",response);

    }


    @Override
    public void successViewProfile(String response) {
        Log.d("ProfileActivity success", response);


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
                number_1.setText(editprofile.getString("mHusbandMobile"));
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
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode) {
            case MY_PERMISSIONS_REQUEST_READ_EXTERNAL_STORAGE:
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    if(userChoosenTask.equals("Take Photo"))
                        cameraIntent();
                    else if(userChoosenTask.equals("Choose from Library"))
                        galleryIntent();
                } else {
                    //code for deny
                }
                break;
        }
    }
}