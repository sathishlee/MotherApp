package com.unicef.thaimai.motherapp.fragment;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.AsyncTask;
import android.os.Environment;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.assist.ImageScaleType;
import com.unicef.thaimai.motherapp.ChildDevelopment.ChildTrackingActivity;
import com.unicef.thaimai.motherapp.Interface.DownloadVideoInterface;
import com.unicef.thaimai.motherapp.Preference.PreferenceData;
import com.unicef.thaimai.motherapp.Presenter.HealthTipsPresenter;
import com.unicef.thaimai.motherapp.R;
import com.unicef.thaimai.motherapp.activity.HealthTipsActivity;
import com.unicef.thaimai.motherapp.adapter.HealthTipsAdapter;
import com.unicef.thaimai.motherapp.constant.Apiconstants;
import com.unicef.thaimai.motherapp.constant.AppConstants;
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

/**
 * Created by Suthishan on 20/1/2018.
 */

public class VideoFragment extends Fragment implements HealthTipsViews, DownloadVideoInterface {

    ProgressDialog pDialog;
    HealthTipsPresenter healthTipsPresenter;
    PreferenceData preferenceData;
    private List<VideoModel.VideoList> lists;
    VideoModel.VideoList videoList;
    private RecyclerView recyclerView;
    private HealthTipsAdapter healthTipsAdapter;
    private DisplayImageOptions options;
    private boolean isFragmentLoaded=false;
    TextView txt_no_records_found;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View view = null;

        view =  inflater.inflate(R.layout.activity_list_video, container, false);
        initUi(view);
        onClickListner(view);
        return view;
    }


    private void onClickListner(View view) {
    }

    private void initUi(View view) {

        pDialog = new ProgressDialog(getActivity());
        pDialog.setCancelable(false);
        pDialog.setMessage("Please Wait ...");
        preferenceData =new PreferenceData(getActivity());
        healthTipsPresenter = new HealthTipsPresenter(getActivity(),this);
        lists = new ArrayList<>();
        recyclerView = (RecyclerView) view.findViewById(R.id.video_recycler_view);
        txt_no_records_found = (TextView) view.findViewById(R.id.txt_no_records_found);

        healthTipsAdapter = new HealthTipsAdapter(lists, getActivity(),this);

        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getActivity());
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

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if (isVisibleToUser && !isFragmentLoaded ) {
            // Load your data here or do network operations here
            loadData();
            isFragmentLoaded = true;
        }
    }

    private void loadData() {
        healthTipsPresenter.getHealthTipsVideo(preferenceData.getPicmeId());
    }


    @Override
    public void showProgress() {
        pDialog.show();
    }

    @Override
    public void onDestroy(){
        super.onDestroy();

        if (pDialog!=null && pDialog.isShowing() ){
            pDialog.cancel();
        }
    }

    @Override
    public void hideProgress() {
        pDialog.hide();
    }

    @Override
    public void getHealthTipsVideoSuccess(String response) {
        Log.e(HealthTipsActivity.class.getSimpleName(), "Success Response"+ response);

        try{
            JSONObject jsonObject = new JSONObject(response);
            String status = jsonObject.getString("status");
            String message = jsonObject.getString("message");
            Toast.makeText(getActivity(),message, Toast.LENGTH_SHORT).show();
            if(status.equalsIgnoreCase("1")){
                lists.clear();
                txt_no_records_found.setVisibility(View.GONE);
                recyclerView.setVisibility(View.VISIBLE);
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
            }else{
                txt_no_records_found.setVisibility(View.VISIBLE);
                recyclerView.setVisibility(View.GONE);
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
    public void onResume() {
        super.onResume();
        loadData();
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
            PD= ProgressDialog.show(getActivity(),null, "Please Wait ...", true);
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
                new android.support.v7.app.AlertDialog.Builder(getActivity())
                        .setTitle("Video Download Successfully")
                        .setMessage("please visit Health Tips Videos folder in you sd card")
                        .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
//                                startActivity(new Intent(getActivity(),ChildTrackingActivity.class));
                            }
                        })
                        .show();
//                Toast.makeText(getActivity(), "", Toast.LENGTH_SHORT).show();
            }else{
                new android.support.v7.app.AlertDialog.Builder(getActivity())
                        .setTitle("Video Not Download ")
                        .setMessage("please Try Again")
                        .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
//                                startActivity(new Intent(getActivity(),ChildTrackingActivity.class));
                            }
                        })
                        .show();


            }

        }
    }

    private boolean downloadFile(String fileURL, String fileName) {
        boolean returnValue=false;
        try {
         /*   String rootDir = AppConstants.root + AppConstants.base_dir
                    + "/Health Tips Video";*/

            String rootDir = Environment.getExternalStorageDirectory()
                    + File.separator + "/Health Tips Videos";
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
