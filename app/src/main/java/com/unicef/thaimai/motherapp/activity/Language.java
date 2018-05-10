package com.unicef.thaimai.motherapp.activity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.util.DisplayMetrics;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.unicef.thaimai.motherapp.R;

import java.util.Locale;



/**
 * Created by Suthishan on 20/1/2018.
 */

public class Language extends AppCompatActivity {

    Locale mylocale;
    TextView tam,eng;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_language);
        showActionBar();
        initUi();
    }

   public void initUi(){
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
        resources.updateConfiguration(conf,dm);
        Intent refreshIntent=new Intent(Language.this,MainActivity.class);
//        finish();
        startActivity(refreshIntent);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        Intent intent = new Intent(Language.this, MainActivity.class);
        startActivity(intent);
        finish();
        return super.onOptionsItemSelected(item);
    }
}
