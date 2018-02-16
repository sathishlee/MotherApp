package com.unicef.thaimai.motherapp.view;

import android.content.Context;

import com.unicef.thaimai.motherapp.model.responsemodel.LoginResponseModel;

/**
 * Created by Suthishan on 20/1/2018.
 */

public interface LoginViews {
    void showProgress();
    void hideProgress();
    void showPickmeResult(LoginResponseModel loginResponseModel);
    void showVerifyOtpResult(LoginResponseModel loginResponseModel);

}
