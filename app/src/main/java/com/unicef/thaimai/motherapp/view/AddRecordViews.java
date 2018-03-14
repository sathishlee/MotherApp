package com.unicef.thaimai.motherapp.view;

/**
 * Created by sathish on 3/9/2018.
 */

public interface AddRecordViews {
    void showProgress();
    void hideProgress();
    void insertRecordSuccess(String response);
    void insertRecordFailiure(String response);

    void getVisitIDSuccess(String response);
    void getVisitIDFailiure(String response);
}
