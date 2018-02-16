package com.unicef.thaimai.motherapp.Presenter;


import android.app.Activity;
import android.content.Context;
import android.util.Log;


import com.unicef.thaimai.motherapp.R;
import com.unicef.thaimai.motherapp.activity.Login;
import com.unicef.thaimai.motherapp.interactor.LoginInteractor;
import com.unicef.thaimai.motherapp.model.responsemodel.LoginResponseModel;
import com.unicef.thaimai.motherapp.view.LoginViews;
import com.unicef.thaimai.motherapp.NetworkService.NetworkService;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class LoginPresenter implements LoginInteractor {

    private LoginViews view;
    private Activity activity;
    private NetworkService service;




    public LoginPresenter(Activity activity, LoginViews view,  NetworkService service) {
        this.view = view;
        this.activity = activity;
        this.service = service;
    }

    @Override
    public void checkPickmeId(String pickmeid) {
//        view.showProgress();

        Call<LoginResponseModel> loginResponseModelCall = service.getApi().checkPicme(pickmeid);
        loginResponseModelCall.enqueue(new Callback<LoginResponseModel>() {
            @Override
            public void onResponse(Call<LoginResponseModel> call, Response<LoginResponseModel> response) {
                Log.e("loginResponseModelCall",response.body().toString());
               int code = response.code();
                if (code>=200 && code<300){
                    if(response.isSuccessful()&& response.body().status){
                        view.showPickmeResult(response.body());
                    }
                }

            }

            @Override
            public void onFailure(Call<LoginResponseModel> call, Throwable throwable) {
//                view.hideProgress();
                Log.e("loginResponseModelCall",call.toString());
                view.showErrorMessage(activity.getString(R.string.no_internet_connection));
            }
        });

    }

    @Override
    public void verifyOtp(String pickmeid, String otp) {

    }
}
