package com.unicef.thaimai.motherapp.view;

/**
 * Created by Suthishan on 20/1/2018.
 */

public interface GetAllReportsViews {

    void showProgress();
    void hideProgress();
    void getVisitReportsSuccess(String visitReports);
    void  getVisitReportsFailure(String errorMsg);

}
