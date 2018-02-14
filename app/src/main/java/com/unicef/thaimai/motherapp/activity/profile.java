package com.unicef.thaimai.motherapp.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageButton;
import android.widget.Toast;

import com.unicef.thaimai.motherapp.R;


public class profile extends AppCompatActivity {

    public static final String UPLOAD_URL = "http://192.168.100.19/image/upload.php";
    public static final String UPLOAD_KEY = "image";
    private static final int PICK_IMAGE_REQUEST = 1;

    ImageButton profileImage;
    Intent intent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_profile);

        profileImage = (ImageButton) findViewById(R.id.user_profile_photo);

        profileImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                intent.setType("image/*");
                startActivityForResult(intent, PICK_IMAGE_REQUEST);

            }
        });

    }

    @Override
    public void onBackPressed() {
        intent = new Intent(profile.this, MainActivity.class);
        finish();
        startActivity(intent);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.main, menu);

        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {
            case R.id.action_notification:
                Toast.makeText(getApplicationContext(),"Notification Clicked",Toast.LENGTH_LONG).show();
                return true;
            case R.id.action_help:
                Toast.makeText(getApplicationContext(),"Help Menu",Toast.LENGTH_LONG).show();
                return true;
            default:
                super.onOptionsItemSelected(item);
        }return true;

    }

}
