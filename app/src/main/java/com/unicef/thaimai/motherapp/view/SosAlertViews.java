package com.unicef.thaimai.motherapp.view;

/**
 * Created by sathish on 3/16/2018.
 */

public interface SosAlertViews {

    void showProgress();
    void hideProgress();
    void showPickmeResult(String response);
    void showFlashResult(String response);
    void showErrorMessage(String response);
}
