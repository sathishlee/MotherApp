package com.unicef.thaimai.motherapp.activity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.unicef.thaimai.motherapp.Preference.PreferenceData;
import com.unicef.thaimai.motherapp.Presenter.DeliveryEntryPresenter;
import com.unicef.thaimai.motherapp.R;
import com.unicef.thaimai.motherapp.view.DeliveryEntryViews;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by Suthishan on 20/1/2018.
 */

public class DeliveryDetailsView extends AppCompatActivity implements DeliveryEntryViews{

    TextView txt_delivery_date, txt_delivery_time, txt_delivery_place, txt_delivery_type, txt_delivery_mother_outcome,
            txt_delivery_new_born, txt_infant_height, txt_infant_weight, txt_infant_id, txt_infant_birth_type,
            txt_breast_feeding_given, txt_admitted_in_sncu, new_born_sncu_date, txt_new_born_outcome, txt_bcg_given_date,
            txt_opv_given_date, txt_hepb_given_date;




    ProgressDialog progressDialog;

    FloatingActionButton fab_edt_details;
    DeliveryEntryPresenter deliveryEntryPresenter;
    PreferenceData preferenceData;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_delivery_details_view);
        initUI();
        onClickListner();

    }

    private void onClickListner() {
        fab_edt_details.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(),DeliveryDetailsActivityEntry.class));
            }
        });
    }

   private void initUI(){

       progressDialog = new ProgressDialog(this);
       progressDialog.setCancelable(false);
       progressDialog.setMessage("Please Wait ...");
       preferenceData = new PreferenceData(this);
       deliveryEntryPresenter = new DeliveryEntryPresenter(DeliveryDetailsView.this, this);
       deliveryEntryPresenter.deliveryDetails(preferenceData.getPicmeId(), preferenceData.getMId(), preferenceData.getDid());
       fab_edt_details = (FloatingActionButton) findViewById(R.id.fab_edt_details);


       txt_delivery_date = (TextView) findViewById(R.id.txt_delivery_date);
       txt_delivery_time = (TextView) findViewById(R.id.txt_delivery_time);
       txt_delivery_place = (TextView) findViewById(R.id.txt_delivery_place);
       txt_delivery_type = (TextView) findViewById(R.id.txt_delivery_type);
       txt_delivery_mother_outcome = (TextView) findViewById(R.id.txt_delivery_mother_outcome);
       txt_delivery_new_born = (TextView) findViewById(R.id.txt_delivery_new_born);
       txt_infant_height = (TextView) findViewById(R.id.txt_infant_height);
       txt_infant_weight = (TextView) findViewById(R.id.txt_infant_weight);
       txt_infant_id = (TextView) findViewById(R.id.txt_infant_id);
       txt_infant_birth_type = (TextView) findViewById(R.id.txt_infant_birth_type);
       txt_breast_feeding_given = (TextView) findViewById(R.id.txt_breast_feeding_given);
       txt_admitted_in_sncu = (TextView) findViewById(R.id.txt_admitted_in_sncu);
       new_born_sncu_date = (TextView) findViewById(R.id.new_born_sncu_date);
       txt_new_born_outcome = (TextView) findViewById(R.id.txt_new_born_outcome);
       txt_bcg_given_date = (TextView) findViewById(R.id.txt_bcg_given_date);
       txt_opv_given_date = (TextView) findViewById(R.id.txt_opv_given_date);
       txt_hepb_given_date = (TextView) findViewById(R.id.txt_hepb_given_date);

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
    public void deliveryDetailsSuccess(String response) {
        Log.d(DeliveryDetailsView.class.getSimpleName(), "Success Response" + response);
        deliveryValues(response);
    }

    @Override
    public void deliveryDetailsFailure(String response) {
        Log.d(DeliveryDetailsView.class.getSimpleName(), "failure" + response);
    }


    public void deliveryValues (String response){
        JSONObject jsonObject_res =null;
        try{
            jsonObject_res = new JSONObject(response);
            String status = jsonObject_res.getString("status");

            String message = jsonObject_res.getString("message");
            if (status.equalsIgnoreCase("1")) {
                JSONObject  jsonObject = jsonObject_res.getJSONObject("Delevery_Info");

preferenceData.storeDid(jsonObject.getString("did" ));
                Log.d("message---->", message);
//                if(jsonObject.getString("ddatetime")!="")
                    txt_delivery_date.setText(jsonObject.getString("ddatetime"   ));
//                if (jsonObject.getString("dtime")!="")
                    txt_delivery_time.setText(jsonObject.getString("dtime"));
//                if (jsonObject.getString("dplace")!="")
                    txt_delivery_place.setText(jsonObject.getString("dplace"));
//                if (jsonObject.getString("ddeleveryDetails")!="")
                    txt_delivery_type.setText(jsonObject.getString("ddeleveryDetails"));
//                if (jsonObject.getString("ddeleveryOutcome")!="")
                    txt_delivery_mother_outcome.setText(jsonObject.getString("ddeleveryOutcomeMother"));
//                if (jsonObject.getString("dnewBorn")!="")
                    txt_delivery_new_born.setText(jsonObject.getString("dnewBorn"));
//                if (jsonObject.getString("dInfantId")!="")
                    txt_infant_id.setText(jsonObject.getString("dInfantId"));
//                if (jsonObject.getString("dBirthDetails")!="")
                    txt_infant_birth_type.setText(jsonObject.getString("dBirthDetails"));
//                if (jsonObject.getString("dBirthWeight")!="")
                    txt_infant_weight.setText(jsonObject.getString("dBirthWeight"));

//                if (jsonObject.getString("dBirthHeight")!="")
                    txt_infant_height.setText(jsonObject.getString("dBirthHeight"));
//                if (jsonObject.getString("dBreastFeedingGiven")!="")
                    txt_breast_feeding_given.setText(jsonObject.getString("dBreastFeedingGiven"));
//                if (jsonObject.getString("dAdmittedSNCU")!="")
                    txt_admitted_in_sncu.setText(jsonObject.getString("dAdmittedSNCU"));
//                if (jsonObject.getString("dSNCUDate")!="")
                    new_born_sncu_date.setText(jsonObject.getString("dSNCUDate"));
//                if (jsonObject.getString("dSNCUOutcome")!="")
                    txt_new_born_outcome.setText(jsonObject.getString("dSNCUOutcome"));
//                if (jsonObject.getString("dBCGDate")!="")
                    txt_bcg_given_date.setText(jsonObject.getString("dBCGDate"));
//                if (jsonObject.getString("dOPVDate")!="")
                    txt_opv_given_date.setText(jsonObject.getString("dOPVDate"));
//                if (jsonObject.getString("dHEPBDate")!="")
                    txt_hepb_given_date.setText(jsonObject.getString("dHEPBDate"));
            }
            else{
                Log.d("message---->",message);

            }

        }
        catch (JSONException e) {
            e.printStackTrace();
        }


    }


    @Override
    public void deliveryentrySuccess(String response) {

    }

    @Override
    public void deliveryentryFailiure(String response) {

    }

    @Override
    public void getdeliveryNumberSuccess(String response) {

    }

    @Override
    public void getdeliveryNumberFailiure(String response) {

    }
}
