package com.unicef.thaimai.motherapp.activity;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.unicef.thaimai.motherapp.Preference.PreferenceData;
import com.unicef.thaimai.motherapp.R;
import com.unicef.thaimai.motherapp.constant.AppConstants;
import com.unicef.thaimai.motherapp.helper.LocaleHelper;

import java.util.Locale;


/**
 * Created by Suthishan on 20/1/2018.
 */

public class Language extends AppCompatActivity {

    Locale mylocale;
    TextView tam,eng;
    PreferenceData preferenceData;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_language);
        showActionBar();
        initUi();
    }

   public void initUi(){

       preferenceData = new PreferenceData(this);
       tam = (TextView)findViewById(R.id.tam);
       eng = (TextView) findViewById(R.id.eng);


       tam.setOnClickListener(new View.OnClickListener(){
           @Override
           public void onClick(View view){

               AlertDialog.Builder builder = new AlertDialog.Builder(Language.this);
               builder.setMessage("Are you Sure do you want to Change Language?")
                       .setCancelable(false)
                       .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                           public void onClick(DialogInterface dialog, int id) {
                               setLanguage("ta");
                               preferenceData.setSharePrefrenceLocale("ta");
                               LocaleHelper.setLocale(Language.this,"ta");

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
       });


       eng.setOnClickListener(new View.OnClickListener(){
           @Override
           public void onClick(View view){

               AlertDialog.Builder builder = new AlertDialog.Builder(Language.this);
               builder.setMessage("Are you Sure do you want to Change Language?")
                       .setCancelable(false)
                       .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                           public void onClick(DialogInterface dialog, int id) {
                               setLanguage("en");
                               preferenceData.setSharePrefrenceLocale("en");
                               LocaleHelper.setLocale(Language.this,"en");

//                               AppConstants.LANGUAGE_CHANGE_ENGLISH = false;
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
       });



   }

   public void showActionBar(){
       ActionBar actionBar = getSupportActionBar();
       actionBar.setTitle("Choose Language");
       actionBar.setHomeButtonEnabled(true);
       actionBar.setDisplayHomeAsUpEnabled(true);
   }

    protected void setLanguage(String language){

        mylocale=new Locale(language);

        Resources resources=getResources();
        DisplayMetrics dm=resources.getDisplayMetrics();
        Configuration conf= resources.getConfiguration();
        conf.locale=mylocale;
        getBaseContext().getResources().updateConfiguration(conf, getBaseContext().getResources().getDisplayMetrics());
        getApplicationContext().getResources().updateConfiguration(conf, getBaseContext().getResources().getDisplayMetrics());
        Language.this.getResources().updateConfiguration(conf, getBaseContext().getResources().getDisplayMetrics());
        resources.updateConfiguration(conf,dm);
        Intent refreshIntent=new Intent(Language.this,MainActivity.class);
//        finish();
        startActivity(refreshIntent);

        Log.d("Language--->",language);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        finish();
        return super.onOptionsItemSelected(item);
    }
}
