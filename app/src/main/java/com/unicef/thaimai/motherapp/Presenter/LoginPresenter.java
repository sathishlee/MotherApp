package com.unicef.thaimai.motherapp.Presenter;


import android.app.Activity;

import com.unicef.thaimai.motherapp.interactor.LoginInteractor;
import com.unicef.thaimai.motherapp.view.LoginViews;




public class LoginPresenter implements LoginInteractor {

    private LoginViews view;
    private Activity activity;





    public LoginPresenter(Activity activity, LoginViews view) {
        this.view = view;
        this.activity = activity;
    }

    @Override
    public void checkPickmeId(String pickmeid) {
//

    }

    @Override
    public void verifyOtp(String pickmeid, String otp) {

    }
}
