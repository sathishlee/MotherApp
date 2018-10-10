package com.unicef.thaimai.motherapp.activity;

import android.app.ProgressDialog;
import android.graphics.Bitmap;
import android.os.AsyncTask;
import android.os.Environment;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.MenuItem;
import android.widget.Toast;

import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.assist.ImageScaleType;
import com.unicef.thaimai.motherapp.Interface.DownloadVideoInterface;
import com.unicef.thaimai.motherapp.Preference.PreferenceData;
import com.unicef.thaimai.motherapp.Presenter.HealthTipsPresenter;
import com.unicef.thaimai.motherapp.R;
import com.unicef.thaimai.motherapp.adapter.HealthTipsAdapter;
import com.unicef.thaimai.motherapp.constant.Apiconstants;
import com.unicef.thaimai.motherapp.model.VideoModel;
import com.unicef.thaimai.motherapp.view.HealthTipsViews;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;



public class HealthTipsActivity extends AppCompatActivity implements HealthTipsViews, DownloadVideoInterface {

    ProgressDialog pDialog;
    HealthTipsPresenter healthTipsPresenter;
    PreferenceData preferenceData;
    private List<VideoModel.VideoList> lists;
    VideoModel.VideoList videoList;
    private RecyclerView recyclerView;
    private HealthTipsAdapter healthTipsAdapter;
    private DisplayImageOptions options;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_video);
        initUI();
        onClickListner();
        showActionBar();
    }

    public  void initUI(){
        pDialog = new ProgressDialog(this);
        pDialog.setCancelable(false);
        pDialog.setMessage("Please Wait ...");
        preferenceData =new PreferenceData(this);
        healthTipsPresenter = new HealthTipsPresenter(HealthTipsActivity.this,this);
        healthTipsPresenter.getHealthTipsVideo(preferenceData.getPicmeId());
        lists = new ArrayList<>();
        recyclerView = (RecyclerView) findViewById(R.id.video_recycler_view);
        healthTipsAdapter = new HealthTipsAdapter(lists, HealthTipsActivity.this,this);

        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(HealthTipsActivity.this);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(healthTipsAdapter);

        options = new DisplayImageOptions.Builder()
                .showImageOnLoading(R.drawable.real1)
                .showImageForEmptyUri(R.drawable.real1)
                .showImageOnFail(R.drawable.real1)
                .cacheOnDisk(true)
                .considerExifParams(true)
                .bitmapConfig(Bitmap.Config.RGB_565)
                .imageScaleType(ImageScaleType.EXACTLY)
                .build();

    }

    public void  onClickListner(){

    }
    public void showActionBar(){
        ActionBar actionBar = getSupportActionBar();
        actionBar.setTitle("Health tips");
        actionBar.setHomeButtonEnabled(true);
        actionBar.setDisplayHomeAsUpEnabled(true);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        finish();
        return super.onOptionsItemSelected(item);
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
    public void onDestroy(){
        super.onDestroy();
        if (pDialog!=null && pDialog.isShowing() ){
            pDialog.cancel();
        }
    }


    @Override
    public void getHealthTipsVideoSuccess(String response) {
        Log.e(HealthTipsActivity.class.getSimpleName(), "Success Response"+ response);

        try{
            JSONObject jsonObject = new JSONObject(response);
            JSONArray jsonArray = jsonObject.getJSONArray("videoList");
            for (int i = 0; i < jsonArray.length(); i++){
                videoList = new VideoModel.VideoList();
                JSONObject object = jsonArray.getJSONObject(i);
                videoList.setId(object.getString("id"));
                videoList.setTitle(object.getString("title"));
                videoList.setVideo(object.getString("video"));

                lists.add(videoList);
                healthTipsAdapter.notifyDataSetChanged();
            }
        }
        catch (JSONException e){
            e.printStackTrace();
        }


    }

    @Override
    public void getHealthTipsVideoFailure(String response) {
    Log.e(HealthTipsActivity.class.getSimpleName(), "Error Message"+ response);
    }

    @Override
    public void healthTipsMessageSuccess(String response) {

    }

    @Override
    public void healthTipsMessageFailure(String response) {

    }

    @Override
    public void healthTipsImageSuccess(String response) {

    }

    @Override
    public void healthtipsImageFailure(String response) {

    }

    @Override
    public void download(String videoUrl, String fileName) {
        ProgressBack PB = new ProgressBack();
        PB.execute(videoUrl);
    }

    private class ProgressBack  extends AsyncTask<String,String,Boolean> {
        ProgressDialog PD;
        String filename;
        @Override
        protected void onPreExecute() {
            PD= ProgressDialog.show(getApplicationContext(),null, "Please Wait ...", true);
            PD.setCancelable(true);
        }
        @Override
        protected Boolean doInBackground(String... strings) {
            filename = strings[0];
//            downloadFile("http://beta-vidizmo.com/hilton.mp4","Sample.mp4");


            return downloadFile(Apiconstants.HEALTH_TIPS_VIDEO_URL+filename,filename);
        }

        protected void onPostExecute(Boolean result) {
            PD.dismiss();
            if (result) {
                Toast.makeText(getApplicationContext(), "please visit video folder in you sd card", Toast.LENGTH_SHORT).show();
            }else{
                Toast.makeText(getApplicationContext(), "please Try Again", Toast.LENGTH_SHORT).show();

            }

        }
    }

    private boolean downloadFile(String fileURL, String fileName) {
boolean returnValue=false;
        try {
         /*   String rootDir = AppConstants.root + AppConstants.base_dir
                    + File.separator + "Health Tips Video";*/

            String rootDir = Environment.getExternalStorageDirectory()
                    + File.separator + "Video";
            File rootFile = new File(rootDir);
            rootFile.mkdir();
            URL url = new URL(fileURL);
            HttpURLConnection c = (HttpURLConnection) url.openConnection();
            c.setRequestMethod("GET");
            c.setDoOutput(true);
            c.connect();
            FileOutputStream f = new FileOutputStream(new File(rootFile,
                    fileName));
            InputStream in = c.getInputStream();
            byte[] buffer = new byte[1024];
            int len1 = 0;
            while ((len1 = in.read(buffer)) > 0) {
                f.write(buffer, 0, len1);
            }
            f.close();
            returnValue=true;
        } catch (IOException e) {
            returnValue=false;
            Log.d("Error....", e.toString());
        }
return  returnValue;

    }
}
