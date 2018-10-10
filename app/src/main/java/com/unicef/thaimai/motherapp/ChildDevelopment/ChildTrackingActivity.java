package com.unicef.thaimai.motherapp.ChildDevelopment;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.unicef.thaimai.motherapp.Interface.CaptureImageInterface;
import com.unicef.thaimai.motherapp.Preference.PreferenceData;
import com.unicef.thaimai.motherapp.Presenter.CHILDDevlopmentPresenter;
import com.unicef.thaimai.motherapp.R;
import com.unicef.thaimai.motherapp.adapter.ChildDevQuestionAdapter;
import com.unicef.thaimai.motherapp.adapter.ChildDevelopmentReportAdapter;
import com.unicef.thaimai.motherapp.constant.AppConstants;
import com.unicef.thaimai.motherapp.model.ChildDevelopmentModdel.ChildDevQuestionModel;
import com.unicef.thaimai.motherapp.model.ChildDevelopmentModdel.ChildQuestionReportViewModel;
import com.unicef.thaimai.motherapp.model.ChildDevelopmentModdel.OuterListModel;
import com.unicef.thaimai.motherapp.model.ChildDevelopmentModdel.OuterListforAllChildDevModel;
import com.unicef.thaimai.motherapp.model.ChildDevelopmentModdel.ShowAllChildDevModel;
import com.unicef.thaimai.motherapp.utility.CheckNetwork;
import com.unicef.thaimai.motherapp.view.CHILDDevlopmentViews;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Date;

/**
 * Created by Suthishan on 20/1/2018.
 */

public class ChildTrackingActivity extends AppCompatActivity implements CHILDDevlopmentViews,
        CaptureImageInterface {
    ProgressDialog progressDialog;
    PreferenceData preferenceData;
    CheckNetwork checkNetwork;
    CHILDDevlopmentPresenter childDevlopmentPresenter;
    TextView txtAgeOfMonth,txt_view_all_report;
    Button btn_submit;
    LinearLayout ll_button_block;

    RecyclerView rec_question_list;
    LinearLayoutManager mLayoutManager;
   ArrayList<ChildDevQuestionModel> arrchildDevList;
   ArrayList<ChildDevQuestionModel> arrchildDevListUpdate;

   ArrayList<Bitmap> arrchildDevListUpdate_bitmap;
    ChildDevQuestionModel childDevQuestionModel1,childDevQuestionModel2,childDevQuestionModel3,childDevQuestionModel4,
            childDevQuestionModel5,childDevQuestionModel6,childDevQuestionModel7,childDevQuestionModel8;
    ChildDevQuestionAdapter adapter;

    private static final int REQUEST_CAMERA = 3;
    int picture_position;
    ChildDevQuestionAdapter.ViewHolder viewHolder;

    int but_position;
    ChildDevQuestionAdapter.ViewHolder but_viewHolder;
    Bitmap thumbnail;

    String currentMounth="2-4";


    OuterListModel outerListModel;
    ArrayList<OuterListModel> nested_childQuestionReportViewModel;
    ArrayList<ChildQuestionReportViewModel> arr_childQuestionReportViewModel;


    OuterListforAllChildDevModel outerListModel1;
    ArrayList<OuterListforAllChildDevModel> nested_childQuestionReportViewModel1;
    ArrayList<ShowAllChildDevModel> arr_childQuestionReportViewModel1;
    ShowAllChildDevModel childQuestionReportViewModel1;



    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_child_tracking);
        showActionBar();


        nested_childQuestionReportViewModel = new ArrayList<>();
        arr_childQuestionReportViewModel = new ArrayList<>();

        nested_childQuestionReportViewModel1 = new ArrayList<>();
        arr_childQuestionReportViewModel1 = new ArrayList<>();


        arrchildDevList = new ArrayList<>();
        arrchildDevListUpdate = new ArrayList<>();
        arrchildDevListUpdate_bitmap =new ArrayList<>();

        childDevQuestionModel1 =new ChildDevQuestionModel();
        childDevQuestionModel2 =new ChildDevQuestionModel();

        progressDialog = new ProgressDialog(this);
        progressDialog.setCancelable(false);
        progressDialog.setMessage("Please Wait ...");
        preferenceData = new PreferenceData(this);
        checkNetwork =new CheckNetwork(this);

        childDevlopmentPresenter=new CHILDDevlopmentPresenter(ChildTrackingActivity.this,this);
        if (checkNetwork.isNetworkAvailable()){
            childDevlopmentPresenter.getCurrentChildDevMonth(preferenceData.getPicmeId(),preferenceData.getMId());
        }else{
            Toast.makeText(getApplicationContext(),"you are in offline",Toast.LENGTH_SHORT).show();
        }
        txtAgeOfMonth =(TextView) findViewById(R.id. age_of_month);
//        txtAgeOfMonth.setText(AppConstants.CURRENT_MONTH);

        txt_view_all_report = (TextView) findViewById(R.id.txt_view_all_report);
        btn_submit =(Button) findViewById(R.id.btn_submit_new);
        ll_button_block =(LinearLayout) findViewById(R.id.ll_button_block);

        rec_question_list = (RecyclerView)findViewById(R.id.rec_question_list);
        mLayoutManager = new LinearLayoutManager(this);
        rec_question_list.setLayoutManager(mLayoutManager);
//           adapter = new ChildDevQuestionAdapter(ChildTrackingActivity.this, arrchildDevList, this);
//            rec_question_list.setAdapter(adapter);
        txtAgeOfMonth.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selectMonth();
            }
        });
      /*  btn_submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getApplicationContext(),"you click button",Toast.LENGTH_SHORT).show();
                childDevlopmentPresenter.updateChildDevQuestions(preferenceData.getPicmeId(),preferenceData.getMId(),preferenceData.getVhnId(),txtAgeOfMonth.getText().toString(),arrchildDevListUpdate);
            }
        });*/
        txt_view_all_report.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(),ChildTrackingViewReportActivity.class));
            }
        });
       /* ll_button_block.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getApplicationContext(),"you click button block",Toast.LENGTH_SHORT).show();
                childDevlopmentPresenter.updateChildDevQuestions(preferenceData.getPicmeId(),preferenceData.getMId(),preferenceData.getVhnId(),txtAgeOfMonth.getText().toString(),arrchildDevListUpdate);

            }
        });*/
    }

    private void showActionBar() {
        ActionBar actionBar = getSupportActionBar();
        actionBar.setTitle("Child Tracking Questions");
        actionBar.setHomeButtonEnabled(true);
        actionBar.setDisplayHomeAsUpEnabled(true);
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        finish();
        return super.onOptionsItemSelected(item);
    }
    private void selectMonth() {

        final CharSequence[] items = { "2-4", "4-6","6-9","9-12","12-15","15-18","18-24","24-30","Cancel" };

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Add Photo!");
        builder.setItems(items, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int item) {

                if (items[item].equals("2-4")) {
                    txtAgeOfMonth.setText("2-4");
                    loadData("2-4");
                } else if (items[item].equals("4-6")) {
                    txtAgeOfMonth.setText("4-6");
                    loadData("4-6");

                } else if (items[item].equals("6-9")) {
                    txtAgeOfMonth.setText("6-9");
                    loadData("6-9");

                }
                else if (items[item].equals("9-12")) {
                    txtAgeOfMonth.setText("9-12");
                    loadData("9-12");

                }
                else if (items[item].equals("12-15")) {
                    txtAgeOfMonth.setText("12-15");
                    loadData("12-15");

                }
                else if (items[item].equals("15-18")) {
                    txtAgeOfMonth.setText("15-18");
                    loadData("15-18");

                }
                else if (items[item].equals("18-24")) {
                    txtAgeOfMonth.setText("18-24");
                    loadData("18-24");

                }
                else if (items[item].equals("24-30")) {
                    txtAgeOfMonth.setText("24-30");
                    loadData("24-30");

                }
                else if (items[item].equals("Cancel")) {
                    dialog.dismiss();
                }
            }
        });
        builder.show();

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
    public void getCurrentmonthSuccess(String response) {
//        loadData("2-4");
//        txtAgeOfMonth.setText(2-4);
        try {
            JSONObject mJsnobject = new JSONObject(response);
            String status = mJsnobject.getString("status");
            String message = mJsnobject.getString("message");
            String result = mJsnobject.getString("result");
            if (result.equalsIgnoreCase("1")){
                txtAgeOfMonth.setText("2-4");
            }else if(result.equalsIgnoreCase("2")){
                txtAgeOfMonth.setText("4-6");

            }else if(result.equalsIgnoreCase("3")){
                txtAgeOfMonth.setText("6-9");

            }else if(result.equalsIgnoreCase("4")){
                txtAgeOfMonth.setText("9-12");
            }else if(result.equalsIgnoreCase("5")){
                txtAgeOfMonth.setText("12-15");
            }else if(result.equalsIgnoreCase("6")){
                txtAgeOfMonth.setText("15-18");
            }else if(result.equalsIgnoreCase("7")){
                txtAgeOfMonth.setText("18-24");
            }else if(result.equalsIgnoreCase("8")){
                txtAgeOfMonth.setText("24-30");
            }
            AppConstants.CURRENT_MONTH = Integer.parseInt(result);


        }
        catch (Exception e) {
            e.printStackTrace();
        }

        //new
        childDevlopmentPresenter.getAllChildDevelopmentRecords(preferenceData.getPicmeId(),preferenceData.getMId());



//        currentMounth="2-4";
//        loadData(currentMounth);
//        adapter = new ChildDevQuestionAdapter(ChildTrackingActivity.this, arrchildDevList, this);
//        rec_question_list.setAdapter(adapter);
//        Toast.makeText(getApplicationContext(),response.toString(),Toast.LENGTH_SHORT).show();
        }

    @Override
    public void getCurrentmonthFailiure(String response) {
        Toast.makeText(getApplicationContext(),response.toString(),Toast.LENGTH_SHORT).show();
    }

    @Override
    public void getQuestionReportSuccess(String response) {
        try {
            JSONObject mJsnobject = new JSONObject(response);
            String status = mJsnobject.getString("status");
            String message = mJsnobject.getString("message");
            if (status.equalsIgnoreCase("1")) {
                JSONArray jsonArray = mJsnobject.getJSONArray("result");
                for (int i = 0; i < jsonArray.length(); i++) {
                    JSONArray njsonArray = jsonArray.getJSONArray(i);
                    outerListModel1 = new OuterListforAllChildDevModel();
                    for (int x = 0; x < njsonArray.length(); x++) {
                        JSONObject njsonObject = njsonArray.getJSONObject(x);
                        childQuestionReportViewModel1 = new ShowAllChildDevModel();
                        childQuestionReportViewModel1.setQuestionid(njsonObject.getString("questionid"));
                        childQuestionReportViewModel1.setAnswerid(njsonObject.getString("answerid"));
                        childQuestionReportViewModel1.setImageid(njsonObject.getString("imageid"));
                        childQuestionReportViewModel1.setMonth(njsonObject.getString("month"));
                        arr_childQuestionReportViewModel1.add(childQuestionReportViewModel1);

                    }
                    outerListModel1.setShowAllChildDevModel(arr_childQuestionReportViewModel1);
                    nested_childQuestionReportViewModel1.add(i, outerListModel1);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        childDevlopmentPresenter.getAllQuestions();


    }

    @Override
    public void getQuestionReportFailiure(String response) {

    }

    @Override
    public void getQuestionsSuccess(String response) {

        try {
            JSONObject mJsnobject = new JSONObject(response);
            String status = mJsnobject.getString("status");
            String message = mJsnobject.getString("message");
            if (status.equalsIgnoreCase("1")) {
                JSONArray jsonArray = mJsnobject.getJSONArray("result");
                for (int i = 0; i < jsonArray.length(); i++) {
                    JSONArray njsonArray = jsonArray.getJSONArray(i);
                    outerListModel = new OuterListModel();

                    for (int x = 0; x < njsonArray.length(); x++) {
                        JSONObject njsonObject = njsonArray.getJSONObject(x);

                        childDevQuestionModel1 = new ChildDevQuestionModel();
                        if (njsonObject.getString("qustid").equalsIgnoreCase(txtAgeOfMonth.getText().toString())) {
                            childDevQuestionModel1.setSetquestion(njsonObject.getString("qustno")+".  "+njsonObject.getString("qustname"));
                            childDevQuestionModel1.setDomain(njsonObject.getString("domain"));
                            childDevQuestionModel1.setSetquestionNo(njsonObject.getString("qustno"));
                            childDevQuestionModel1.setSetanswer(getAnser(njsonObject.getString("qustid"),njsonObject.getString("qustno")));
                            childDevQuestionModel1.setSetImageUri(getImage(njsonObject.getString("qustid"),njsonObject.getString("qustno")));
                            arrchildDevList.add(childDevQuestionModel1);
                        }

                    }
                }


                adapter = new ChildDevQuestionAdapter(ChildTrackingActivity.this, arrchildDevList, this);
                rec_question_list.setAdapter(adapter);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }



    }

    @Override
    public void getQuestionsFailiure(String response) {

    }

    @Override
    public void UpdateQuestinsSuccess(String response) {
        Log.e(ChildTrackingActivity.class.getSimpleName(),"response"+response);
        try {
            JSONObject mJsnobject = new JSONObject(response);
            String status = mJsnobject.getString("status");
            String message = mJsnobject.getString("message");
            if (status.equalsIgnoreCase("1")) {
                new android.support.v7.app.AlertDialog.Builder(this)
                        .setTitle("Response")
                        .setMessage(message)
                        .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                startActivity(new Intent(getApplicationContext(),ChildTrackingActivity.class));
                            }
                        })
                        .show();
            } else {
                new android.support.v7.app.AlertDialog.Builder(this)
                        .setTitle("Response")
                        .setMessage(message)
                        .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                startActivity(new Intent(getApplicationContext(),ChildTrackingActivity.class));

                            }
                        })
                        .show();
            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    @Override
    public void UpdateQuestinsFailiure(String response) {
        Log.e(ChildTrackingActivity.class.getSimpleName(),"response"+response);
        Toast.makeText(getApplicationContext(),response,Toast.LENGTH_SHORT).show();

    }

    @Override
    public void OpenCamera(ChildDevQuestionAdapter.ViewHolder holder,int position) {
        picture_position = position;
        viewHolder =holder;
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        startActivityForResult(intent, REQUEST_CAMERA);
    }

    @Override
    public void NextQuestion(int  position) {/*

        if (position==0){
            childDevQuestionModel1.setSetquestion(txtAgeOfMonth.getText().toString()+"a");
            childDevQuestionModel1.setDomain("GM");
            childDevQuestionModel1.setSetanswer("No");

            arrchildDevListUpdate.add(0,childDevQuestionModel1);

        }else if (position==1) {
            childDevQuestionModel2.setSetquestion(txtAgeOfMonth.getText().toString()+"b");
            childDevQuestionModel2.setDomain("GM");
            childDevQuestionModel2.setSetanswer("No");
            arrchildDevListUpdate.add(1,childDevQuestionModel2);


        }else if (position==2) {
           childDevQuestionModel3.setSetquestion(txtAgeOfMonth.getText().toString()+"c");
            childDevQuestionModel3.setDomain("FM");
            childDevQuestionModel3.setSetanswer("No");
            arrchildDevListUpdate.add(2,childDevQuestionModel3);

        }else if (position==3) {
            childDevQuestionModel4.setSetquestion(txtAgeOfMonth.getText().toString()+"d");
            childDevQuestionModel4.setDomain("H");
            childDevQuestionModel4.setSetanswer("No");
            arrchildDevListUpdate.add(3,childDevQuestionModel4);
        }else if (position==4) {
            childDevQuestionModel5.setSetquestion(txtAgeOfMonth.getText().toString()+"e");
            childDevQuestionModel5.setDomain("S");
            childDevQuestionModel5.setSetanswer("No");
            arrchildDevListUpdate.add(4,childDevQuestionModel5);

        }else if (position==5) {
            childDevQuestionModel6.setSetquestion(txtAgeOfMonth.getText().toString()+"f");
            childDevQuestionModel6.setDomain("V");
            childDevQuestionModel6.setSetanswer("No");
            arrchildDevListUpdate.add(5,childDevQuestionModel6);

        }else if (position==6) {
            childDevQuestionModel7.setSetquestion(txtAgeOfMonth.getText().toString()+"g");
            childDevQuestionModel7.setDomain("S");
            childDevQuestionModel7.setSetanswer("No");
            arrchildDevListUpdate.add(6,childDevQuestionModel7);

        }else if (position==7) {
            childDevQuestionModel8.setSetquestion(txtAgeOfMonth.getText().toString()+"h");
            childDevQuestionModel8.setDomain("Sp");
            childDevQuestionModel8.setSetanswer("No");
            arrchildDevListUpdate.add(7,childDevQuestionModel8);

        }
   */ }

    @Override
    public void SendQuestion(ChildDevQuestionAdapter.ViewHolder holder,int postion, Bitmap bitmap) {
        but_position=postion;
        but_viewHolder = holder;

        childDevQuestionModel1=new ChildDevQuestionModel();
        childDevQuestionModel1.setSetquestion(arrchildDevList.get(postion).getSetquestionNo());
        childDevQuestionModel1.setDomain(arrchildDevList.get(postion).getDomain());
        childDevQuestionModel1.setSetphoto(bitmap);
        childDevQuestionModel1.setSetanswer("Yes");
        arrchildDevListUpdate.add(childDevQuestionModel1);

        childDevlopmentPresenter.updateChildDevQuestions(preferenceData.getPicmeId(),preferenceData.getMId(),preferenceData.getVhnId(),txtAgeOfMonth.getText().toString(),arrchildDevListUpdate);
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == Activity.RESULT_OK) {
            if (requestCode == REQUEST_CAMERA)
                onCaptureImageResult(data);
        }
    }

    private void onCaptureImageResult(Intent data) {
          thumbnail = (Bitmap) data.getExtras().get("data");
        ByteArrayOutputStream bytes = new ByteArrayOutputStream();
        thumbnail.compress(Bitmap.CompressFormat.JPEG, 90, bytes);
    /*    File destination = new File(Environment.getExternalStorageDirectory(),
                System.currentTimeMillis() + ".jpg");*/
       String fname = arrchildDevList.get(picture_position).getSetquestionNo()+"_"+System.currentTimeMillis()  + ".jpg";
        File myDir;
myDir = new File(AppConstants.root + AppConstants.base_dir + "/Child development");
        myDir.mkdirs();
        File destination = new File(myDir,
                fname);

        FileOutputStream fo;
        try {
            destination.createNewFile();
            fo = new FileOutputStream(destination);
            fo.write(bytes.toByteArray());
            fo.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
if (picture_position==0){
    viewHolder.img_camera.setImageBitmap(thumbnail);    // set image to  adapter item image
    viewHolder.bitmap =thumbnail;

 }else if (picture_position==1) {
    viewHolder.img_camera.setImageBitmap(thumbnail);  // set image to  adapter item image
    viewHolder.bitmap =thumbnail;

}else if (picture_position==2) {
    viewHolder.img_camera.setImageBitmap(thumbnail);    // set image to  adapter item image

    viewHolder.bitmap =thumbnail;



}else if (picture_position==3) {
    viewHolder.img_camera.setImageBitmap(thumbnail);     // set image to  adapter item image
    viewHolder.bitmap =thumbnail;

}else if (picture_position==4) {
    viewHolder.img_camera.setImageBitmap(thumbnail);     // set image to  adapter item image
    viewHolder.bitmap =thumbnail;

}else if (picture_position==5) {
    viewHolder.img_camera.setImageBitmap(thumbnail);       // set image to  adapter item image
    viewHolder.bitmap =thumbnail;

}else if (picture_position==6) {
    viewHolder.img_camera.setImageBitmap(thumbnail);        // set image to  adapter item image
    viewHolder.bitmap =thumbnail;

}else if (picture_position==7) {
    viewHolder.img_camera.setImageBitmap(thumbnail);    // set image to  adapter item image
    viewHolder.bitmap =thumbnail;

}

         }

    private void loadData(String month) {
        if (month.equalsIgnoreCase("2-4")){
            childDevQuestionModel1 = new ChildDevQuestionModel();

            childDevQuestionModel1.setSetquestion("a. Does the child move both arms and both legs freely equally when awake or when excited.");
            childDevQuestionModel1.setDomain("GM");
            childDevQuestionModel1.setSetanswer("No");

            arrchildDevList.add(childDevQuestionModel1);

            childDevQuestionModel2 = new ChildDevQuestionModel();

            childDevQuestionModel2.setSetquestion("b. Does the child raise his or her Head momentarily when lying face down?");
            childDevQuestionModel2.setDomain("GM");
            childDevQuestionModel2.setSetanswer("No");
            arrchildDevList.add(childDevQuestionModel2);

            childDevQuestionModel3 = new ChildDevQuestionModel();

            childDevQuestionModel3.setSetquestion("c. Does the child keep his hands open and relaxed most of the  time>(By 3 months)");
            childDevQuestionModel3.setDomain("FM");
            childDevQuestionModel3.setSetanswer("Yes");
            arrchildDevList.add(childDevQuestionModel3);


            childDevQuestionModel4 = new ChildDevQuestionModel();
            childDevQuestionModel4.setSetquestion("d. Does the child respond to your voice or startles with ' loud sounds or becomes alert to new sound by quietening or smiling?");
            childDevQuestionModel4.setDomain("H");
            childDevQuestionModel4.setSetanswer("Yes");
            arrchildDevList.add(childDevQuestionModel4);

            childDevQuestionModel5 = new ChildDevQuestionModel();
            childDevQuestionModel5.setSetquestion("e. Does the Child coos or able  to vocalize other than crying? Like  'ooh','ng' ");
            childDevQuestionModel5.setDomain("S");
            childDevQuestionModel5.setSetanswer("Yes");
            arrchildDevList.add(childDevQuestionModel5);


            childDevQuestionModel6 = new ChildDevQuestionModel();
            childDevQuestionModel6.setSetquestion("f. Does the Child make eye contact?");
            childDevQuestionModel6.setDomain("V");
            childDevQuestionModel6.setSetanswer("Yes");
            arrchildDevList.add(childDevQuestionModel6);


            childDevQuestionModel7 = new ChildDevQuestionModel();
            childDevQuestionModel7.setSetquestion("g. Does the child give a social smile?(Reciprocal, responds to mothers expression or smile i.e. smile back at you)");
            childDevQuestionModel7.setDomain("S");
            childDevQuestionModel7.setSetanswer("Yes");
            arrchildDevList.add(childDevQuestionModel7);


            childDevQuestionModel8 = new ChildDevQuestionModel();
            childDevQuestionModel8.setSetquestion("h. Does the child suck and swallow well  during feeding... I.e. without any choking?");
            childDevQuestionModel8.setDomain("S");
            childDevQuestionModel8.setSetanswer("Yes");
            arrchildDevList.add(childDevQuestionModel8);
        }




        if (month.equalsIgnoreCase("4-6")){
            childDevQuestionModel1 = new ChildDevQuestionModel();

            childDevQuestionModel1.setSetquestion("a. Does the child hold head erect in sitting position without  bobbing  i.e. hold her head straight? while sitting with support, head is held steadily) refer if head flops ofalls back on anyone side when child is pulled to sitting position");
            childDevQuestionModel1.setDomain("GM");
            childDevQuestionModel1.setSetanswer("No");

            arrchildDevList.add(childDevQuestionModel1);

            childDevQuestionModel2 = new ChildDevQuestionModel();

            childDevQuestionModel2.setSetquestion("b. Does the child reach out for an object persistently? (Should use either hands) but refer it preference  for onw hand only, Observe that grasp of the object is in the ulnar side of palm and there is lack of thumb involvement.");
            childDevQuestionModel2.setDomain("GM");
            childDevQuestionModel2.setSetanswer("No");
            arrchildDevList.add(childDevQuestionModel2);

            childDevQuestionModel3 = new ChildDevQuestionModel();

            childDevQuestionModel3.setSetquestion("c. Does the child respond to mother's speech by looking directly at her face?");
            childDevQuestionModel3.setDomain("FM");
            childDevQuestionModel3.setSetanswer("Yes");
            arrchildDevList.add(childDevQuestionModel3);


            childDevQuestionModel4 = new ChildDevQuestionModel();
            childDevQuestionModel4.setSetquestion("d. Does the child laugh alound or make squealing sounds?");
            childDevQuestionModel4.setDomain("H");
            childDevQuestionModel4.setSetanswer("Yes");
            arrchildDevList.add(childDevQuestionModel4);

            childDevQuestionModel5 = new ChildDevQuestionModel();
            childDevQuestionModel5.setSetquestion("e. Does the child follow on object with his or her eyes?");
            childDevQuestionModel5.setDomain("S");
            childDevQuestionModel5.setSetanswer("Yes");
            arrchildDevList.add(childDevQuestionModel5);


            childDevQuestionModel6 = new ChildDevQuestionModel();
            childDevQuestionModel6.setSetquestion("f. Does the  child sucks on hands?");
            childDevQuestionModel6.setDomain("V");
            childDevQuestionModel6.setSetanswer("Yes");
            arrchildDevList.add(childDevQuestionModel6);

        }




        if (month.equalsIgnoreCase("6-9")){
            childDevQuestionModel1 = new ChildDevQuestionModel();

            childDevQuestionModel1.setSetquestion("a. Does the child roll over or turn over in either direction?");
            childDevQuestionModel1.setDomain("GM");
            childDevQuestionModel1.setSetanswer("No");

            arrchildDevList.add(childDevQuestionModel1);

            childDevQuestionModel2 = new ChildDevQuestionModel();

            childDevQuestionModel2.setSetquestion("b. Does the child grasp a small object by using his whole hand?(Secure it in the center of palm)");
            childDevQuestionModel2.setDomain("GM");
            childDevQuestionModel2.setSetanswer("No");
            arrchildDevList.add(childDevQuestionModel2);

            childDevQuestionModel3 = new ChildDevQuestionModel();

            childDevQuestionModel3.setSetquestion("c. Does the child locate source of sound? i.e turns his head or eyes if you whisper from behind");
            childDevQuestionModel3.setDomain("FM");
            childDevQuestionModel3.setSetanswer("Yes");
            arrchildDevList.add(childDevQuestionModel3);


            childDevQuestionModel4 = new ChildDevQuestionModel();
            childDevQuestionModel4.setSetquestion("d. Does the child utter consonant sounds like \"p\", \"b\", 's m\"?");
            childDevQuestionModel4.setDomain("H");
            childDevQuestionModel4.setSetanswer("Yes");
            arrchildDevList.add(childDevQuestionModel4);

            childDevQuestionModel5 = new ChildDevQuestionModel();
            childDevQuestionModel5.setSetquestion("e. Does your baby watch TV or any toy  without tilting his/her head? ");
            childDevQuestionModel5.setDomain("S");
            childDevQuestionModel5.setSetanswer("Yes");
            arrchildDevList.add(childDevQuestionModel5);


            childDevQuestionModel6 = new ChildDevQuestionModel();
            childDevQuestionModel6.setSetquestion("f. Does the child raises hands to be picked up by parents?");
            childDevQuestionModel6.setDomain("V");
            childDevQuestionModel6.setSetanswer("Yes");
            arrchildDevList.add(childDevQuestionModel6);


            childDevQuestionModel7 = new ChildDevQuestionModel();
            childDevQuestionModel7.setSetquestion("g. Does the child look for a spoon or toy that has dropped?");
            childDevQuestionModel7.setDomain("S");
            childDevQuestionModel7.setSetanswer("Yes");
            arrchildDevList.add(childDevQuestionModel7);

        }




        if (month.equalsIgnoreCase("9-12")){
            childDevQuestionModel1 = new ChildDevQuestionModel();

            childDevQuestionModel1.setSetquestion("a. Does the child sit without any support");
            childDevQuestionModel1.setDomain("GM");
            childDevQuestionModel1.setSetanswer("No");

            arrchildDevList.add(childDevQuestionModel1);

            childDevQuestionModel2 = new ChildDevQuestionModel();

            childDevQuestionModel2.setSetquestion("b. Does the child transfer object from hand to hand");
            childDevQuestionModel2.setDomain("GM");
            childDevQuestionModel2.setSetanswer("No");
            arrchildDevList.add(childDevQuestionModel2);

            childDevQuestionModel3 = new ChildDevQuestionModel();

            childDevQuestionModel3.setSetquestion("c. Does the child respond to his or her name");
            childDevQuestionModel3.setDomain("FM");
            childDevQuestionModel3.setSetanswer("Yes");
            arrchildDevList.add(childDevQuestionModel3);


            childDevQuestionModel4 = new ChildDevQuestionModel();
            childDevQuestionModel4.setSetquestion("d. Does the child babble example ba, ba, da, da, ma, ma");
            childDevQuestionModel4.setDomain("H");
            childDevQuestionModel4.setSetanswer("Yes");
            arrchildDevList.add(childDevQuestionModel4);

            childDevQuestionModel5 = new ChildDevQuestionModel();
            childDevQuestionModel5.setSetquestion("e. Does the child avoid bumping into objects while moving?");
            childDevQuestionModel5.setDomain("S");
            childDevQuestionModel5.setSetanswer("Yes");
            arrchildDevList.add(childDevQuestionModel5);


            childDevQuestionModel6 = new ChildDevQuestionModel();
            childDevQuestionModel6.setSetquestion("f. Does the child enjoy playing hid and seek (Peek-a-boo)?");
            childDevQuestionModel6.setDomain("V");
            childDevQuestionModel6.setSetanswer("Yes");
            arrchildDevList.add(childDevQuestionModel6);

        }


        if (month.equalsIgnoreCase("12-15")){
            childDevQuestionModel1 = new ChildDevQuestionModel();

            childDevQuestionModel1.setSetquestion("a. Does the child crawl on hand and knees");
            childDevQuestionModel1.setDomain("GM");
            childDevQuestionModel1.setSetanswer("No");

            arrchildDevList.add(childDevQuestionModel1);

            childDevQuestionModel2 = new ChildDevQuestionModel();

            childDevQuestionModel2.setSetquestion("b. Does the child pickup small objects using thumb and index fingers like peas, resins\n");
            childDevQuestionModel2.setDomain("GM");
            childDevQuestionModel2.setSetanswer("No");
            arrchildDevList.add(childDevQuestionModel2);

            childDevQuestionModel3 = new ChildDevQuestionModel();

            childDevQuestionModel3.setSetquestion("c. Does the child stops activity in response to NO?");
            childDevQuestionModel3.setDomain("FM");
            childDevQuestionModel3.setSetanswer("Yes");
            arrchildDevList.add(childDevQuestionModel3);


            childDevQuestionModel4 = new ChildDevQuestionModel();
            childDevQuestionModel4.setSetquestion("d. Does the child say one meaningful word clearly like mama, dada");
            childDevQuestionModel4.setDomain("H");
            childDevQuestionModel4.setSetanswer("Yes");
            arrchildDevList.add(childDevQuestionModel4);

            childDevQuestionModel5 = new ChildDevQuestionModel();
            childDevQuestionModel5.setSetquestion("e. Does the child imitate action like bye bye/clap. Kiss?(wave goodbye or greet you).");
            childDevQuestionModel5.setDomain("S");
            childDevQuestionModel5.setSetanswer("Yes");
            arrchildDevList.add(childDevQuestionModel5);


            childDevQuestionModel6 = new ChildDevQuestionModel();
            childDevQuestionModel6.setSetquestion("f. Does the child cry when a stranger picks him up? differentiate familiar faces from strangers");
            childDevQuestionModel6.setDomain("V");
            childDevQuestionModel6.setSetanswer("Yes");
            arrchildDevList.add(childDevQuestionModel6);


            childDevQuestionModel7 = new ChildDevQuestionModel();
            childDevQuestionModel7.setSetquestion("g. Does the child search for completely hidden objects");
            childDevQuestionModel7.setDomain("S");
            childDevQuestionModel7.setSetanswer("Yes");
            arrchildDevList.add(childDevQuestionModel7);


         }

        if (month.equalsIgnoreCase("15-18")){
            childDevQuestionModel1 = new ChildDevQuestionModel();

            childDevQuestionModel1.setSetquestion("a. Does the child walk alone?");
            childDevQuestionModel1.setDomain("GM");
            childDevQuestionModel1.setSetanswer("No");

            arrchildDevList.add(childDevQuestionModel1);

            childDevQuestionModel2 = new ChildDevQuestionModel();

            childDevQuestionModel2.setSetquestion("b. Does the child play by putting small things or objects in to a container(e.g. cup)");
            childDevQuestionModel2.setDomain("GM");
            childDevQuestionModel2.setSetanswer("No");
            arrchildDevList.add(childDevQuestionModel2);

            childDevQuestionModel3 = new ChildDevQuestionModel();

            childDevQuestionModel3.setSetquestion("c. Does the child make gesture on verbal request like pointing to objects");
            childDevQuestionModel3.setDomain("FM");
            childDevQuestionModel3.setSetanswer("Yes");
            arrchildDevList.add(childDevQuestionModel3);


            childDevQuestionModel4 = new ChildDevQuestionModel();
            childDevQuestionModel4.setSetquestion("d. Does the child follow  simple one step directions as far e.g. Sit down.");
            childDevQuestionModel4.setDomain("H");
            childDevQuestionModel4.setSetanswer("Yes");
            arrchildDevList.add(childDevQuestionModel4);

            childDevQuestionModel5 = new ChildDevQuestionModel();
            childDevQuestionModel5.setSetquestion("e. Does the child say at least two words other than mama, dada like dog cate and ball ever if it is not clearly ");
            childDevQuestionModel5.setDomain("S");
            childDevQuestionModel5.setSetanswer("Yes");
            arrchildDevList.add(childDevQuestionModel5);


            childDevQuestionModel6 = new ChildDevQuestionModel();
            childDevQuestionModel6.setSetquestion("f. Does the child manipulate or explore a toy with his/her finger like poking or pulling the toy");
            childDevQuestionModel6.setDomain("V");
            childDevQuestionModel6.setSetanswer("Yes");
            arrchildDevList.add(childDevQuestionModel6);

        }


        if (month.equalsIgnoreCase("18-24")){
            childDevQuestionModel1 = new ChildDevQuestionModel();

            childDevQuestionModel1.setSetquestion("a. Does the child walk alone even while pulling toy?");
            childDevQuestionModel1.setDomain("GM");
            childDevQuestionModel1.setSetanswer("No");

            arrchildDevList.add(childDevQuestionModel1);

            childDevQuestionModel2 = new ChildDevQuestionModel();

            childDevQuestionModel2.setSetquestion("b. Does the child scribble spontaneously");
            childDevQuestionModel2.setDomain("GM");
            childDevQuestionModel2.setSetanswer("No");
            arrchildDevList.add(childDevQuestionModel2);

            childDevQuestionModel3 = new ChildDevQuestionModel();

            childDevQuestionModel3.setSetquestion("c. Does the child say al lease five words consistently ever if not clear");
            childDevQuestionModel3.setDomain("FM");
            childDevQuestionModel3.setSetanswer("Yes");
            arrchildDevList.add(childDevQuestionModel3);


            childDevQuestionModel4 = new ChildDevQuestionModel();
            childDevQuestionModel4.setSetquestion("d. Does the child imitate house hold tasks (try to copy domestic chores like sweeping, washing cloths)");
            childDevQuestionModel4.setDomain("H");
            childDevQuestionModel4.setSetanswer("Yes");
            arrchildDevList.add(childDevQuestionModel4);

            childDevQuestionModel5 = new ChildDevQuestionModel();
            childDevQuestionModel5.setSetquestion("e. Does the Child point to 2 or more boy parts?");
            childDevQuestionModel5.setDomain("S");
            childDevQuestionModel5.setSetanswer("Yes");
            arrchildDevList.add(childDevQuestionModel5);

        }


        if (month.equalsIgnoreCase("24-30")){
            childDevQuestionModel1 = new ChildDevQuestionModel();

            childDevQuestionModel1.setSetquestion("a. Does the child climb upstairs & downstairs");
            childDevQuestionModel1.setDomain("GM");
            childDevQuestionModel1.setSetanswer("No");

            arrchildDevList.add(childDevQuestionModel1);

            childDevQuestionModel2 = new ChildDevQuestionModel();

            childDevQuestionModel2.setSetquestion("b. Does the child feed self either with hand or spoon");
            childDevQuestionModel2.setDomain("GM");
            childDevQuestionModel2.setSetanswer("No");
            arrchildDevList.add(childDevQuestionModel2);

            childDevQuestionModel3 = new ChildDevQuestionModel();

            childDevQuestionModel3.setSetquestion("c. Does the child join 2 word together like mama milk,car- go");
            childDevQuestionModel3.setDomain("FM");
            childDevQuestionModel3.setSetanswer("Yes");
            arrchildDevList.add(childDevQuestionModel3);


            childDevQuestionModel4 = new ChildDevQuestionModel();
            childDevQuestionModel4.setSetquestion("d. Does the child play along with other children");
            childDevQuestionModel4.setDomain("H");
            childDevQuestionModel4.setSetanswer("Yes");
            arrchildDevList.add(childDevQuestionModel4);

            childDevQuestionModel5 = new ChildDevQuestionModel();
            childDevQuestionModel5.setSetquestion("e. Does the Child enjoy simple pretend play like feeding doll");
            childDevQuestionModel5.setDomain("S");
            childDevQuestionModel5.setSetanswer("Yes");
            arrchildDevList.add(childDevQuestionModel5);

        }
    }
    private String getAnser(String month,String queno) {
        String strAns="No";

        for (int m=0;m<nested_childQuestionReportViewModel1.size();m++){
            outerListModel1 =nested_childQuestionReportViewModel1.get(m);
            arr_childQuestionReportViewModel1 =outerListModel1.getShowAllChildDevModel();
            for (int n=0;n<arr_childQuestionReportViewModel1.size();n++){
                childQuestionReportViewModel1 = arr_childQuestionReportViewModel1.get(n);
                if (month.equalsIgnoreCase(childQuestionReportViewModel1.getMonth())){
                    if (queno.equalsIgnoreCase(childQuestionReportViewModel1.getQuestionid())){
                        strAns=childQuestionReportViewModel1.getAnswerid();
                    }
                }

            }
        }
        return strAns;

    } private String getImage(String month,String queno) {
        String strImage="";
        for (int m=0;m<nested_childQuestionReportViewModel1.size();m++){
            outerListModel1 =nested_childQuestionReportViewModel1.get(m);
            arr_childQuestionReportViewModel1 =outerListModel1.getShowAllChildDevModel();
            for (int n=0;n<arr_childQuestionReportViewModel1.size();n++){
                childQuestionReportViewModel1 = arr_childQuestionReportViewModel1.get(n);
                if (month.equalsIgnoreCase(childQuestionReportViewModel1.getMonth())){
                    if (queno.equalsIgnoreCase(childQuestionReportViewModel1.getQuestionid())){
                        if (childQuestionReportViewModel1.getAnswerid().equalsIgnoreCase("Yes")){
                            strImage =childQuestionReportViewModel1.getImageid();
                        }
                        else{
                            strImage ="";
                        }
                    }
                }
            }
        }
        return strImage;

    }
}
