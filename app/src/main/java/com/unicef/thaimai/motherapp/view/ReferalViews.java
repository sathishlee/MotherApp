package com.unicef.thaimai.motherapp.view;

/**
 * Created by sathish on 3/17/2018.
 */

public interface ReferalViews {
    void showProgress();
    void hideProgress();
    void successReferalAdd(String response);
    void errorReferalAdd(String response);
}
