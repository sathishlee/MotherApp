/*
 * Copyright (c) 2016. Ted Park. All Rights Reserved
 */

package com.gun0912.tedpicker;

import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.ActivityCompat;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.widget.TextView;
import android.widget.Toast;

import com.commonsware.cwac.camera.CameraHost;
import com.commonsware.cwac.camera.CameraHostProvider;
import com.gun0912.tedpicker.custom.adapter.SpacesItemDecoration;
import com.gun0912.tedpicker.util.Util;

import java.util.ArrayList;

import static android.Manifest.permission.CAMERA;
import static android.Manifest.permission.GET_ACCOUNTS;
import static android.Manifest.permission.INTERNET;
import static android.Manifest.permission.READ_EXTERNAL_STORAGE;
import static android.Manifest.permission.RECORD_AUDIO;
import static android.Manifest.permission.SEND_SMS;
import static android.Manifest.permission.WRITE_EXTERNAL_STORAGE;
import static android.Manifest.permission.WRITE_SETTINGS;


public class ImagePickerActivity extends AppCompatActivity implements CameraHostProvider, ImageUploadViews {

    public static final String EXTRA_IMAGE_URIS = "image_uris";
    public static CwacCameraFragment.MyCameraHost mMyCameraHost;
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
    PagerAdapter_Picker adapter;
    Adapter_SelectedPhoto adapter_selectedPhoto;

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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setupFromSavedInstanceState(savedInstanceState);
        setContentView(R.layout.picker_activity_main_pp);

        if(CheckingPermissionIsEnabledOrNot())
        {
            Toast.makeText(ImagePickerActivity.this, "All Permissions Granted Successfully", Toast.LENGTH_LONG).show();
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

    }

    private void RequestMultiplePermission() {
        // Creating String Array with Permissions.
        ActivityCompat.requestPermissions(ImagePickerActivity.this, new String[]
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

        mBitmapSelectedImages =new ArrayList<>();
        view_root = findViewById(R.id.view_root);
        mViewPager = (ViewPager) findViewById(R.id.pager);
        tabLayout = (TabLayout) findViewById(R.id.tab_layout);

        uploadMulitipleImagesPresenter = new UploadMulitipleImagesPresenter(ImagePickerActivity.this,this);

        tv_selected_title = (TextView) findViewById(R.id.tv_selected_title);

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
        adapter = new PagerAdapter_Picker(this, getSupportFragmentManager());
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

        adapter_selectedPhoto = new Adapter_SelectedPhoto(this, closeImageRes);
        adapter_selectedPhoto.updateItems(mSelectedImages);
        rc_selected_photos.setAdapter(adapter_selectedPhoto);
        if (mSelectedImages.size() >= 1) {
            mSelectedImageEmptyMessage.setVisibility(View.GONE);
        }
    }


    public GalleryFragment getGalleryFragment() {
        if (adapter == null || adapter.getCount() < 2)
            return null;
        return (GalleryFragment) adapter.getItem(1);
    }

    public void addImage(final Uri uri) {
        if (mSelectedImages.size() == mConfig.getSelectionLimit()) {
            String text = String.format(getResources().getString(R.string.max_count_msg), mConfig.getSelectionLimit());
            Toast.makeText(this, text, Toast.LENGTH_SHORT).show();
            return;
        }
        mSelectedImages.add(uri);
        adapter_selectedPhoto.updateItems(mSelectedImages);
        if (mSelectedImages.size() >= 1) {
            mSelectedImageEmptyMessage.setVisibility(View.GONE);
        }
        rc_selected_photos.smoothScrollToPosition(adapter_selectedPhoto.getItemCount()-1);
    }

    public void removeImage(Uri uri) {
        mSelectedImages.remove(uri);

        adapter_selectedPhoto.updateItems(mSelectedImages);

        if (mSelectedImages.size() == 0) {
            mSelectedImageEmptyMessage.setVisibility(View.VISIBLE);
        }
        GalleryFragment.mGalleryAdapter.notifyDataSetChanged();

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
            updatePicture();
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

        uploadMulitipleImagesPresenter.uploadVisitReportsPhoto(PreferenceData.,mBitmapSelectedImages);

        Intent intent = new Intent();
        intent.putParcelableArrayListExtra(EXTRA_IMAGE_URIS, mSelectedImages);
        setResult(Activity.RESULT_OK, intent);
        finish();

    }


    @Override
    public void showProgress() {

    }

    @Override
    public void hideProgress() {

    }

    @Override
    public void successUploadPhoto(String response) {

    }

    @Override
    public void errorUploadPhoto(String response) {

    }
}
