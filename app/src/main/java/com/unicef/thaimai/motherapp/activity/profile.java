package com.unicef.thaimai.motherapp.activity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.res.ColorStateList;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.os.Build;
import android.os.Bundle;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.view.ViewCompat;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.graphics.Palette;
import android.support.v7.widget.Toolbar;
import android.transition.Slide;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;


import com.unicef.thaimai.motherapp.Preference.PreferenceData;
import com.unicef.thaimai.motherapp.Presenter.ProfilePresenter;
import com.unicef.thaimai.motherapp.R;
import com.unicef.thaimai.motherapp.view.ProfileView;

import org.json.JSONException;
import org.json.JSONObject;


public class profile extends AppCompatActivity implements ProfileView {

    public static final String TITLE = "Profile";
    public static final String UPLOAD_IMAGE = "user_profile_photo";
    private static final int PICK_IMAGE_REQUEST = 1;

    CollapsingToolbarLayout toolbar_layout;

    ImageView user_profile_photo;
    Intent intent;

    TextView user_name, edt_picme_id, address, village_name, tvNumber5, district_name, tvNumber1, number_1;
    ProgressDialog pDialog;
    ProfilePresenter profilePresenter;
    PreferenceData preferenceData;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initActivityTransitions();
        setContentView(R.layout.layout_profile);

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


//        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
//        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

        initUI();
        onClickListner();

    }

    private void onClickListner() {

    }

    private void initUI() {

        pDialog = new ProgressDialog(this);
        pDialog.setCancelable(false);
        pDialog.setMessage("Please Wait ...");
        preferenceData = new PreferenceData(this);

        profilePresenter = new ProfilePresenter(profile.this, this);

        profilePresenter.getMotherProfile(preferenceData.getMId(),preferenceData.getPicmeId());
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
        intent = new Intent(profile.this, MainActivity.class);
        finish();
        startActivity(intent);
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        Intent intent = new Intent(profile.this, MainActivity.class);
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
    public void successViewProfile(String response) {
        Log.d("profile success", response);


        try {
            JSONObject jsonObject =new JSONObject(response);
            String status =jsonObject.getString("status");
            String msg = jsonObject.getString("message");
            if (status.equalsIgnoreCase("1")){
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
        Log.d("profile error", response);

    }
}
