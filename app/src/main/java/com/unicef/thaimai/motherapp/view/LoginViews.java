package com.unicef.thaimai.motherapp.view;

import com.unicef.thaimai.motherapp.model.responsemodel.LoginResponseModel;

/**
 * Created by Suthishan on 20/1/2018.
 */

public interface LoginViews {
    void showProgress();
    void hideProgress();
    void loginSuccess(String response);
    void loginError(String string);
    void showPickmeResult(String loginResponseModel);
    void showErrorMessage(String string);

}
