package com.unicef.thaimai.motherapp.activity;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.unicef.thaimai.motherapp.Preference.PreferenceData;
import com.unicef.thaimai.motherapp.Presenter.GetVisitReportsPresenter;
import com.unicef.thaimai.motherapp.R;
import com.unicef.thaimai.motherapp.adapter.ANVisitRecordsAdapter;
import com.unicef.thaimai.motherapp.adapter.PNVisitRecordsAdapter;
import com.unicef.thaimai.motherapp.constant.Apiconstants;
import com.unicef.thaimai.motherapp.model.responsemodel.ANVisitRecordsFullResponseModel;
import com.unicef.thaimai.motherapp.model.responsemodel.ANVisitRecordsSingleResponseModel;
import com.unicef.thaimai.motherapp.model.responsemodel.PNVisitRecordsFullResponseModel;
import com.unicef.thaimai.motherapp.model.responsemodel.PNVisitRecordsSingleResponseModel;
import com.unicef.thaimai.motherapp.view.GetAllReportsViews;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by Suthishan on 20/1/2018.
 */

public class PNViewReportsActivity extends AppCompatActivity implements GetAllReportsViews {

    ProgressDialog progressDialog;
    PreferenceData preferenceData;

    ArrayList<PNVisitRecordsFullResponseModel> visitRecordsFullResponseModels = new ArrayList<>();
    RecyclerView rec_mother_reports;
    PNVisitRecordsAdapter visitRecordsAdapter;
    GetVisitReportsPresenter getVisitReportsPresenter;
    TextView txt_no_records_found;
    String visitImage;
    Context context;
    ImageView itemImage;
    boolean isImageFitToScreen;

    ArrayList<PNVisitRecordsSingleResponseModel> singleResponseModelsList;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_reports);
        showActionBar();
        initUI();
        onClickListner();
    }

    private void initUI() {

        progressDialog = new ProgressDialog(this);
        progressDialog.setCancelable(false);
        progressDialog.setMessage("Please Wait ...");
        preferenceData = new PreferenceData(this);
        context = PNViewReportsActivity.this;

        visitRecordsFullResponseModels = new ArrayList<>();

        getVisitReportsPresenter = new GetVisitReportsPresenter(PNViewReportsActivity.this, this);
        getVisitReportsPresenter.getallPNVisitReports(preferenceData.getPicmeId(),preferenceData.getMId());
        txt_no_records_found = (TextView) findViewById(R.id.txt_no_records);
        itemImage = (ImageView) findViewById(R.id.itemImage);

        txt_no_records_found.setVisibility(View.GONE);
//        displayRecords();
        rec_mother_reports  = (RecyclerView) findViewById(R.id.rec_mother_reports);
        rec_mother_reports.setHasFixedSize(true);
        visitRecordsAdapter = new PNVisitRecordsAdapter(visitRecordsFullResponseModels,this);
        rec_mother_reports.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        rec_mother_reports.setAdapter(visitRecordsAdapter);
    }

    private void showActionBar() {
        ActionBar actionBar = getSupportActionBar();
        actionBar.setTitle("PN Mother Reports");
        actionBar.setHomeButtonEnabled(true);
        actionBar.setDisplayHomeAsUpEnabled(true);
    }

    private void onClickListner() {

    }

    @Override
    public void showProgress() {
        progressDialog.show();
    }

    @Override
    public void hideProgress() {
        progressDialog.hide();
    }

    @Override
    public void getVisitReportsSuccess(String response) {
        Log.w(PNViewReportsActivity.class.getSimpleName(), "Response success" + response);

        try {
            JSONObject jsonObject = new JSONObject(response);
            String status = jsonObject.getString("status");
            String message = jsonObject.getString("message");
            Log.w(PNViewReportsActivity.class.getSimpleName(), "status"+status);
            Log.w(PNViewReportsActivity.class.getSimpleName(), "message -->"+ message);

//            Toast.makeText(getApplicationContext(), message, Toast.LENGTH_SHORT).show();
            if (status.equalsIgnoreCase("1")){
                txt_no_records_found.setVisibility(View.GONE);
                JSONArray jsonArray = jsonObject.getJSONArray("reportList");

                Log.w(PNViewReportsActivity.class.getSimpleName(), "jsonArray -->"+ jsonArray);

                for (int i = 0; i <jsonArray.length(); i++) {

                    PNVisitRecordsFullResponseModel model = new PNVisitRecordsFullResponseModel();

                    JSONObject mjsonObject = jsonArray.getJSONObject(i);
                    model.setTitle(mjsonObject.getString("title"));

                    Log.w(PNViewReportsActivity.class.getSimpleName(), "title -->"+ mjsonObject.getString("title"));

                    JSONArray mjsonArray = mjsonObject.getJSONArray("section");
                    singleResponseModelsList = new ArrayList<>();
                    for(int j = 0; j < mjsonArray.length(); j++){
                        JSONObject sjsonObject = mjsonArray.getJSONObject(j);

                        PNVisitRecordsSingleResponseModel singleResponseModels =new PNVisitRecordsSingleResponseModel();

                        Log.w(PNViewReportsActivity.class.getSimpleName(), "image -->"+ sjsonObject.getString("image"));
                        visitImage = sjsonObject.getString("image");
                        singleResponseModels.setImage(visitImage);
                        Log.w(" visitImage ", Apiconstants.PN_VISIT_REPORTS_URL+preferenceData.getPicmeId()+"/"+visitImage);

                        /*Log.d("Visit Reports", Apiconstants.VISIT_REPORTS_URL+visitImage);

                        Picasso.with(context)
                                .load(Apiconstants.VISIT_REPORTS_URL+visitImage)
                                .placeholder(R.drawable.no_image)
                                .fit()
                                .centerCrop()
                                .memoryPolicy(MemoryPolicy.NO_CACHE)
                                .networkPolicy(NetworkPolicy.NO_CACHE)
                                .error(R.drawable.no_image)
                                .into(itemImage);*/
                        singleResponseModelsList.add(singleResponseModels);
                    }
                    model.setVisitRecordsSingleResponseModels(singleResponseModelsList);
                    visitRecordsFullResponseModels.add(model);
                }
                visitRecordsAdapter.notifyDataSetChanged();
                Log.w("tRecordsFullResp",visitRecordsFullResponseModels.size()+"");

            }else{
                txt_no_records_found.setVisibility(View.VISIBLE);
            }
        }
        catch (JSONException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void getVisitReportsFailure(String errorMsg) {
        Log.e(PNViewReportsActivity.class.getSimpleName(),"Response error"+errorMsg);
    }

    @Override
    public void onDestroy(){
        super.onDestroy();
        if (progressDialog!=null && progressDialog.isShowing() ){
            progressDialog.cancel();
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        finish();
        return super.onOptionsItemSelected(item);
    }
}
