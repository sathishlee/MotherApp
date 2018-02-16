package com.unicef.thaimai.motherapp.Presenter;


import android.app.Activity;

import com.unicef.thaimai.motherapp.networkService.NetworkService;
import com.unicef.thaimai.motherapp.interactor.LoginInteractor;
import com.unicef.thaimai.motherapp.view.LoginViews;


public class LoginPresenter implements LoginInteractor {

    private LoginViews view;
    private Activity activity;
    private NetworkService service;

    public LoginPresenter(LoginViews view, Activity activity, NetworkService service) {
        this.view = view;
        this.activity = activity;
        this.service = service;
    }

    @Override
    public void checkPickmeId(String pickmeid) {

    }

    @Override
    public void verifyOtp(String pickmeid, String otp) {

    }
}
