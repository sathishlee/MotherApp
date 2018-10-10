package com.unicef.thaimai.motherapp;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.design.widget.TabLayout;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.widget.AdapterView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.commonsware.cwac.camera.CameraHost;
import com.commonsware.cwac.camera.CameraHostProvider;
import com.unicef.thaimai.motherapp.Preference.PreferenceData;
import com.unicef.thaimai.motherapp.Presenter.PNHBNCVisitPresenter;
import com.unicef.thaimai.motherapp.Presenter.UploadMulitipleImagesPresenter;
import com.unicef.thaimai.motherapp.activity.MainActivity;
import com.unicef.thaimai.motherapp.activity.PNHBNCVisitEntry;
import com.unicef.thaimai.motherapp.adapter.SpacesItemDecoration;
import com.unicef.thaimai.motherapp.utility.Util;
import com.unicef.thaimai.motherapp.view.ImageUploadViews;
import com.unicef.thaimai.motherapp.view.PNHBNCVisitViews;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import static android.Manifest.permission.CAMERA;
import static android.Manifest.permission.GET_ACCOUNTS;
import static android.Manifest.permission.INTERNET;
import static android.Manifest.permission.READ_EXTERNAL_STORAGE;
import static android.Manifest.permission.RECORD_AUDIO;
import static android.Manifest.permission.SEND_SMS;
import static android.Manifest.permission.WRITE_EXTERNAL_STORAGE;
import static android.Manifest.permission.WRITE_SETTINGS;

/**
 * Created by Suthishan on 20/1/2018.
 */

public class PNImageSelectedActivity extends AppCompatActivity implements CameraHostProvider,
        AdapterView.OnItemSelectedListener,ImageUploadViews, PNHBNCVisitViews {

        public static final String EXTRA_IMAGE_URIS = "image_uris";
        public static CameraFragmentPN.MyCameraHost mMyCameraHost;
        // initialize with default config.
        private static Config mConfig = new Config();

        public ArrayList<Uri> mSelectedImages;
        public ArrayList<Bitmap> mBitmapSelectedImages;

        protected Toolbar toolbar;
        View view_root;
        TextView mSelectedImageEmptyMessage;
        View view_selected_photos_container;
        RecyclerView rc_selected_photos;
        TextView tv_selected_title;
        ViewPager mViewPager;
        TabLayout tabLayout;
        PNPageAdpaterSelection adapter;
        PNAdapter_selectedPhoto pnAdapter_selectedPhoto;
        ProgressDialog progressDialog;
        PreferenceData preferenceData;
        public static String strTotalVisitCount="0";
        Spinner sp_visit_no;
        String strVisitNo;
        public static String strVisitId="-1", strPicmeId;

    public static final int MY_PERMISSIONS_REQUEST_READ_EXTERNAL_STORAGE = 123;

        public static Config getConfig() {
            return mConfig;
        }

        public static void setConfig(Config config) {

            if (config == null) {
                throw new NullPointerException("Config cannot be passed null. Not setting config will use default values.");
            }
            mConfig = config;
        }

        @Override
        public CameraHost getCameraHost() {
            return mMyCameraHost;
        }


        public static final int RequestPermissionCode = 7;

        UploadMulitipleImagesPresenter uploadMulitipleImagesPresenter;
        PNHBNCVisitPresenter pnhbncVisitPresenter;

        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setupFromSavedInstanceState(savedInstanceState);
            setContentView(R.layout.activity_image_selected_pn);

//        checkRunTimePermission();


            if(CheckingPermissionIsEnabledOrNot())
            {
                Toast.makeText(PNImageSelectedActivity.this, "All Permissions Granted Successfully", Toast.LENGTH_LONG).show();
            }

            // If, If permission is not enabled then else condition will execute.
            else {

                //Calling method to enable permission.
                RequestMultiplePermission();

            }
            initView();
            setTitle(mConfig.getToolbarTitleRes());
            setupTabs();
            setSelectedPhotoRecyclerView();
            onClickListner();
            OnItemSelectedListener();
        }

    private void OnItemSelectedListener() {

    }

    private void onClickListner() {
        sp_visit_no.setOnItemSelectedListener(this);
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
            ActivityCompat.requestPermissions(PNImageSelectedActivity.this, new String[]
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


        private void initView() {
            toolbar = (Toolbar) findViewById(R.id.toolbar);
            setSupportActionBar(toolbar);
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setHomeButtonEnabled(true);

            progressDialog = new ProgressDialog(this);
            progressDialog.setCancelable(false);
            progressDialog.setMessage("Please Wait...");

            preferenceData = new PreferenceData(this);
            pnhbncVisitPresenter = new PNHBNCVisitPresenter(PNImageSelectedActivity.this,this);
            pnhbncVisitPresenter.getPNHBNCVisitCount(preferenceData.getPicmeId(), preferenceData.getMId());

            mBitmapSelectedImages =new ArrayList<>();
            view_root = findViewById(R.id.view_root);
            mViewPager = (ViewPager) findViewById(R.id.pager);
            tabLayout = (TabLayout) findViewById(R.id.tab_layout);

            uploadMulitipleImagesPresenter = new UploadMulitipleImagesPresenter(PNImageSelectedActivity.this,this);

            tv_selected_title = (TextView) findViewById(R.id.tv_selected_title);
            sp_visit_no = (Spinner) findViewById(R.id.sp_visit_no);

            rc_selected_photos = (RecyclerView) findViewById(R.id.rc_selected_photos);
            mSelectedImageEmptyMessage = (TextView) findViewById(R.id.selected_photos_empty);

            view_selected_photos_container = findViewById(R.id.view_selected_photos_container);
            view_selected_photos_container.getViewTreeObserver().addOnPreDrawListener(new ViewTreeObserver.OnPreDrawListener() {
                @Override
                public boolean onPreDraw() {
                    view_selected_photos_container.getViewTreeObserver().removeOnPreDrawListener(this);

                    int selected_bottom_size = (int) getResources().getDimension(mConfig.getSelectedBottomHeight());

                    ViewGroup.LayoutParams params = view_selected_photos_container.getLayoutParams();
                    params.height = selected_bottom_size;
                    view_selected_photos_container.setLayoutParams(params);
                    return true;
                }
            });

            if (mConfig.getSelectedBottomColor() > 0) {
                tv_selected_title.setBackgroundColor(ContextCompat.getColor(this, mConfig.getSelectedBottomColor()));
                mSelectedImageEmptyMessage.setTextColor(ContextCompat.getColor(this, mConfig.getSelectedBottomColor()));
            }
        }

        private void setupFromSavedInstanceState(Bundle savedInstanceState) {
            if (savedInstanceState != null) {
                mSelectedImages = savedInstanceState.getParcelableArrayList(EXTRA_IMAGE_URIS);
            } else {
                mSelectedImages = getIntent().getParcelableArrayListExtra(EXTRA_IMAGE_URIS);
            }
            if (mSelectedImages == null) {
                mSelectedImages = new ArrayList<>();
            }

        }

        @Override
        protected void onSaveInstanceState(Bundle outState) {
            super.onSaveInstanceState(outState);
            if (mSelectedImages != null) {
                outState.putParcelableArrayList(EXTRA_IMAGE_URIS, mSelectedImages);
            }

        }

        private void setupTabs() {
            adapter = new PNPageAdpaterSelection(this, getSupportFragmentManager());
            mViewPager.setAdapter(adapter);
            tabLayout.setupWithViewPager(mViewPager);

            if (mConfig.getTabBackgroundColor() > 0)
                tabLayout.setBackgroundColor(ContextCompat.getColor(this, mConfig.getTabBackgroundColor()));

            if (mConfig.getTabSelectionIndicatorColor() > 0)
                tabLayout.setSelectedTabIndicatorColor(ContextCompat.getColor(this, mConfig.getTabSelectionIndicatorColor()));
        }

        private void setSelectedPhotoRecyclerView() {
            LinearLayoutManager mLayoutManager_Linear = new LinearLayoutManager(this);
            mLayoutManager_Linear.setOrientation(LinearLayoutManager.HORIZONTAL);

            rc_selected_photos.setLayoutManager(mLayoutManager_Linear);
            rc_selected_photos.addItemDecoration(new SpacesItemDecoration(Util.dpToPx(this, 5), SpacesItemDecoration.TYPE_VERTICAL));
            rc_selected_photos.setHasFixedSize(true);

            int closeImageRes = mConfig.getSelectedCloseImage();

            pnAdapter_selectedPhoto = new PNAdapter_selectedPhoto(this, closeImageRes);
            pnAdapter_selectedPhoto.updateItems(mSelectedImages);
            rc_selected_photos.setAdapter(pnAdapter_selectedPhoto);
            if (mSelectedImages.size() >= 1) {
                mSelectedImageEmptyMessage.setVisibility(View.GONE);
            }

        }


        public GalleryFragmentPN getGalleryFragment() {

            if (adapter == null || adapter.getCount() < 2)
                return null;

            return (GalleryFragmentPN) adapter.getItem(1);

        }

        public void addImage(final Uri uri) {

            if (mSelectedImages.size() == mConfig.getSelectionLimit()) {
                String text = String.format(getResources().getString(R.string.max_count_msg), mConfig.getSelectionLimit());
                Toast.makeText(this, text, Toast.LENGTH_SHORT).show();
                return;
            }
            mSelectedImages.add(uri);
            convertImgURI(Uri.fromFile(new File(String.valueOf(uri))),"add");
            Log.e("ImageSelected URI",mBitmapSelectedImages.size()+"");
            //        Uri.fromFile(new File(your image path));

            pnAdapter_selectedPhoto.updateItems(mSelectedImages);


            if (mSelectedImages.size() >= 1) {
                mSelectedImageEmptyMessage.setVisibility(View.GONE);
            }

            rc_selected_photos.smoothScrollToPosition(pnAdapter_selectedPhoto.getItemCount()-1);
        }

        public void removeImage(Uri uri,int position) {
            mSelectedImages.remove(uri);

            convertImgURI(Uri.fromFile(new File(String.valueOf(uri))),"remove");
            mBitmapSelectedImages.remove(position);

            pnAdapter_selectedPhoto.updateItems(mSelectedImages);

            if (mSelectedImages.size() == 0) {
                mSelectedImageEmptyMessage.setVisibility(View.VISIBLE);
            }
            GalleryFragmentPN.mGalleryAdapter.notifyDataSetChanged();
        }

        public boolean containsImage(Uri uri) {
            return mSelectedImages.contains(uri);
        }

        @Override
        public boolean onCreateOptionsMenu(Menu menu) {
            MenuInflater inflater = getMenuInflater();
            inflater.inflate(R.menu.menu_confirm, menu);
            return super.onCreateOptionsMenu(menu);
        }


        @Override
        public boolean onOptionsItemSelected(MenuItem item) {

            int id = item.getItemId();
            if (id == android.R.id.home) {
                finish();
                return true;
            } else if (id == R.id.action_done) {
                if(mSelectedImages.size() >= 1){
                    updatePicture();
                }else{
                    Toast.makeText(this, "No Image to Upload", Toast.LENGTH_SHORT).show();
                }
                return true;
            }

            return super.onOptionsItemSelected(item);
        }

        private void updatePicture() {

            if (mSelectedImages.size() < mConfig.getSelectionMin()) {
                String text = String.format(getResources().getString(R.string.min_count_msg), mConfig.getSelectionMin());
                Toast.makeText(this, text, Toast.LENGTH_SHORT).show();
                return;
            }
            Log.e("mBitmap","SelectedImages size"+mBitmapSelectedImages.size()+"");
            if(strVisitNo.equalsIgnoreCase("--Select--")){
                Toast.makeText(this, "Please Select Visit Id", Toast.LENGTH_SHORT).show();
            }else{
                uploadMulitipleImagesPresenter.pnVisitUploadPhoto(preferenceData.getPicmeId(),strTotalVisitCount,preferenceData.getMId(),mBitmapSelectedImages);
            }

//        Intent intent = new Intent();
//        intent.putParcelableArrayListExtra(EXTRA_IMAGE_URIS, mSelectedImages);
//        setResult(Activity.RESULT_OK, intent);
//        finish();

        }

        private void convertImgURI(Uri imageUri,String type) {
//        Uri imageUri = intent.getData();
            try {
                Log.e("ImageSelected URI",imageUri+"");

                Bitmap bitmap = MediaStore.Images.Media.getBitmap(this.getContentResolver(),imageUri);
                if (type.equalsIgnoreCase("add")) {
                    mBitmapSelectedImages.add(bitmap);
                }else{
                    mBitmapSelectedImages.remove(bitmap);

                }
            } catch (IOException e) {
                e.printStackTrace();
            }

        }

        @Override
        public void showProgress() {
            progressDialog.show();
        }

        @Override
        public void hideProgress() {
            progressDialog.dismiss();
        }

    @Override
    public void pnhbncVisitSuccess(String response) {

    }

    @Override
    public void pnhbncVisitFailiure(String response) {

    }

    @Override
    public void getpnhbncVisitNumberSuccess(String response) {

    }

    @Override
    public void getpnhbncVisitNumberFailiure(String response) {
        Log.d(PNHBNCVisitEntry.class.getSimpleName(),"Response Failure-->" + response);
    }

    @Override
    public void successUploadPhoto(String response) {
        Log.d("images ", "Reports Uploaded Successfully.."+ response);
        Toast.makeText(this, "Reports Uploaded Successfully..", Toast.LENGTH_SHORT).show();
        startActivity(new Intent(getApplicationContext(), MainActivity.class));
        finish();

    }

        @Override
        public void errorUploadPhoto(String response) {
            Log.d("images ", "uploaded not successfully"+ response);

        }

    @Override
    public void checkpnhbncVisitIdSuccess(String response) {

        Log.e(PNImageSelectedActivity.class.getSimpleName(),response);
        try{
            JSONObject jsonObject = new JSONObject(response);
            String status =jsonObject.getString("status");
            String msg = jsonObject.getString("message");

            if (status.equalsIgnoreCase("1")){
                strTotalVisitCount= jsonObject.getString("visitNo");
                Toast.makeText(getApplicationContext(),msg,Toast.LENGTH_SHORT).show();
            }else{
                Toast.makeText(getApplicationContext(),msg,Toast.LENGTH_SHORT).show();
                startActivity(new Intent(getApplicationContext(),MainActivity.class));
            }
        }
        catch(JSONException e){
            e.printStackTrace();
        }
    }

    @Override
    public void checkpnhbncVisitIdFailiure(String response) {
        Log.e(PNImageSelectedActivity.class.getSimpleName(),response);
        Toast.makeText(getApplicationContext(),response,Toast.LENGTH_SHORT).show();
    }


    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        switch(parent.getId()) {
            case R.id.sp_visit_no:
                strVisitNo = parent.getSelectedItem().toString();
                if (strVisitNo.equalsIgnoreCase("--Select--")) {
                    position = -1;
                    strVisitId = String.valueOf(position);
                    pnhbncVisitPresenter.checkImagePNHBNCVisitId(preferenceData.getPicmeId(), preferenceData.getMId(), strVisitId);
                } else {
                    strVisitId = String.valueOf(position);
                    pnhbncVisitPresenter.checkImagePNHBNCVisitId(preferenceData.getPicmeId(), preferenceData.getMId(), strVisitId);

                }
                /*strVisitId= String.valueOf(position);
                pnhbncVisitPresenter.checkPNHBNCVisitId(preferenceData.getPicmeId(),preferenceData.getMId(),strVisitId);
*/

                break;
        }
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }
}

