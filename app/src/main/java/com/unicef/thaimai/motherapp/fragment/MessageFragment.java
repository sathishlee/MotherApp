package com.unicef.thaimai.motherapp.fragment;

import android.app.AlertDialog;
import android.app.ProgressDialog;
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

import com.unicef.thaimai.motherapp.Preference.PreferenceData;
import com.unicef.thaimai.motherapp.Presenter.HealthTipsPresenter;
import com.unicef.thaimai.motherapp.R;
import com.unicef.thaimai.motherapp.adapter.MessageAdapter;
import com.unicef.thaimai.motherapp.app.RealmController;
import com.unicef.thaimai.motherapp.model.responsemodel.MessageFragmentModel;
import com.unicef.thaimai.motherapp.model.responsemodel.ReferalListResponseModel;
import com.unicef.thaimai.motherapp.utility.CheckNetwork;
import com.unicef.thaimai.motherapp.view.HealthTipsViews;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.util.ArrayList;

import io.realm.Realm;

/**
 * Created by Suthishan on 20/1/2018.
 */

public class MessageFragment extends Fragment implements HealthTipsViews{

    RecyclerView message_recycler_view;
    PreferenceData preferenceData;
    ProgressDialog pDialog;
    HealthTipsPresenter healthTipsPresenter;
    MessageFragmentModel.Healthtips healthtips;
    ArrayList<MessageFragmentModel.Healthtips> healthtipses;
    TextView txt_no_records_found;
    MessageAdapter messageAdapter;
    Realm realm;
    CheckNetwork checkNetwork;
    boolean isoffline = false;


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View view = null;
        view = inflater.inflate(R.layout.message_fragment, container, false);
        realm = RealmController.with(getActivity()).getRealm();
        initUI(view);
        onClickListner();
        return view;

    }

    private void onClickListner() {

    }

    private void initUI(View view) {
        checkNetwork = new CheckNetwork(getActivity());
        preferenceData = new PreferenceData(getActivity());
        healthTipsPresenter = new HealthTipsPresenter(getActivity(),this);
        pDialog = new ProgressDialog(getActivity());
        pDialog.setCancelable(false);
        pDialog.setMessage("Please Wait ...");

        if(checkNetwork.isNetworkAvailable()){
            healthTipsPresenter.healthTipsMessage(preferenceData.getMId());
        }else {
            isoffline = true;
        }
        healthtipses = new ArrayList<>();
        message_recycler_view = (RecyclerView) view.findViewById(R.id.message_recycler_view);
        txt_no_records_found = (TextView) view.findViewById(R.id.txt_no_records);
        txt_no_records_found.setVisibility(View.GONE);
        messageAdapter = new MessageAdapter(getActivity(),healthtipses);

        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getActivity());
        message_recycler_view.setLayoutManager(mLayoutManager);
        message_recycler_view.setItemAnimator(new DefaultItemAnimator());
        message_recycler_view.setAdapter(messageAdapter);


        if (isoffline) {
            messageOffline();
        } else {
            AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
            builder.setMessage("Record Not Found");
            builder.create();
        }


    }

    private void messageOffline() {

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

    }

    @Override
    public void getHealthTipsVideoFailure(String response) {

    }

    @Override
    public void healthTipsMessageSuccess(String response) {

        Log.d(MessageFragment.class.getSimpleName(), "Message --->" + response);

        try {
            JSONObject mJsnobject = new JSONObject(response);
            String status = mJsnobject.getString("status");
            String message = mJsnobject.getString("message");
            Toast.makeText(getActivity(),message, Toast.LENGTH_SHORT).show();
            if (status.equalsIgnoreCase("1")) {
                txt_no_records_found.setVisibility(View.GONE);
                message_recycler_view.setVisibility(View.VISIBLE);
                JSONArray jsonArray = mJsnobject.getJSONArray("healthtips");
                for (int i = 0; i < jsonArray.length(); i++) {
                    healthtips = new MessageFragmentModel.Healthtips();

                    JSONObject jsonObject = jsonArray.getJSONObject(i);
                    healthtips.setSubject(jsonObject.getString("subject"));
//                    String message = healthtips.setMessage(jsonObject.getString("message"));

                    /*final String HTML = "<table cellspacing=\"0\" style=\"height: 24px;\">\r\n<tr class=\"tr-hover\">\r\n<th rowspan=\"15\" scope=\"row\">Network</th>\r\n<td class=\"ttl\"><a href=\"network-bands.php3\">Technology</a></td>\r\n<td class=\"nfo\"><a href=\"#\" class=\"link-network-detail collapse\">GSM</a></td>\r\n</tr>\r\n<tr class=\"tr-toggle\">\r\n<td class=\"ttl\"><a href=\"network-bands.php3\">2G bands</a></td>\r\n<td class=\"nfo\">GSM 900 / 1800 - SIM 1 & SIM 2</td>\r\n</tr>   \r\n<tr class=\"tr-toggle\">\r\n<td class=\"ttl\"><a href=\"glossary.php3?term=gprs\">GPRS</a></td>\r\n<td class=\"nfo\">Class 12</td>\r\n</tr>   \r\n<tr class=\"tr-toggle\">\r\n<td class=\"ttl\"><a href=\"glossary.php3?term=edge\">EDGE</a></td>\r\n<td class=\"nfo\">Yes</td>\r\n</tr>\r\n</table>";
                    Document document = Jsoup.parse(message);
                    Element div = document.select("div").first();


                    Element table = document.select("table").first();
                    String arrayName = table.select("th").first().text();
                    JSONObject jsonObj = new JSONObject();
                    JSONArray jsonArr = new JSONArray();
                    Elements ttls = table.getElementsByClass("ttl");
                    Elements nfos = table.getElementsByClass("nfo");
                    JSONObject jo = new JSONObject();
                    for (int i = 0, l = ttls.size(); i < l; i++) {
                        String key = ttls.get(i).text();
                        String value = nfos.get(i).text();
                        jo.put(key, value);
                    }
                    jsonArr.put(jo);
                    jsonObj.put(arrayName, jsonArr);
                    System.out.println(jsonObj.toString());*/


                    healthtips.setMessage(jsonObject.getString("message"));
                    healthtipses.add(healthtips);
                    messageAdapter.notifyDataSetChanged();
                }
            }else{
//                Toast.makeText(getActivity(),message,Toast.LENGTH_SHORT).show();
                txt_no_records_found.setVisibility(View.VISIBLE);
                message_recycler_view.setVisibility(View.GONE);

            }

        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void healthTipsMessageFailure(String response) {
        Log.d(MessageFragment.class.getSimpleName(), "Error Message-->" + response);

    }
}
