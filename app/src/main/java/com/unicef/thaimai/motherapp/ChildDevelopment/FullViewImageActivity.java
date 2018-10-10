package com.unicef.thaimai.motherapp.ChildDevelopment;

import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.MenuItem;
import android.widget.ImageView;

import com.squareup.picasso.MemoryPolicy;
import com.squareup.picasso.NetworkPolicy;
import com.squareup.picasso.Picasso;
import com.unicef.thaimai.motherapp.R;
import com.unicef.thaimai.motherapp.constant.Apiconstants;
import com.unicef.thaimai.motherapp.constant.AppConstants;

public class FullViewImageActivity extends AppCompatActivity {
    ImageView img_full_view;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_full_view_image);
        showActionBar();
//Bundle bundle =getIntent().getExtras();
//String strImageUri = bundle.getString("strImageUri");
        img_full_view = (ImageView)findViewById(R.id.img_full_view);

            Picasso.with(this)
                    .load(AppConstants.FULL_VIEW_IMAGE_CHILD_REPORT)
                    .placeholder(R.drawable.no_image)
                    .fit()
                    .centerInside()
                    .memoryPolicy(MemoryPolicy.NO_CACHE)
                    .networkPolicy(NetworkPolicy.NO_CACHE)
                    .error(R.drawable.no_image)
                    .into(img_full_view);
    }

    private void showActionBar() {
        ActionBar actionBar = getSupportActionBar();
        actionBar.setTitle("Image");
        actionBar.setHomeButtonEnabled(true);
        actionBar.setDisplayHomeAsUpEnabled(true);
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        finish();
        return super.onOptionsItemSelected(item);
    }
}
