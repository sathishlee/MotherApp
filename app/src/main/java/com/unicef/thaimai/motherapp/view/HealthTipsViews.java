package com.unicef.thaimai.motherapp.view;

/**
 * Created by Suthishan on 20/1/2018.
 */

public interface HealthTipsViews {

    void showProgress();
    void hideProgress();

    void getHealthTipsVideoSuccess(String response);
    void getHealthTipsVideoFailure(String response);

    void healthTipsMessageSuccess(String response);
    void healthTipsMessageFailure(String response);

    void healthTipsImageSuccess(String response);
    void healthtipsImageFailure(String response);

}
