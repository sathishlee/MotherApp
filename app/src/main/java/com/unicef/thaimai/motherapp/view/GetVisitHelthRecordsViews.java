package com.unicef.thaimai.motherapp.view;

import com.unicef.thaimai.motherapp.model.responsemodel.HealthRecordResponseModel;

/**
 * Created by sathish on 3/9/2018.
 */

public interface GetVisitHelthRecordsViews {
    void showProgress();
    void hideProgress();
    void getVisitHealthRecordsSuccess(String healthRecordResponseModel);
    void  getVisitHealthRecordsFailiur(String errorMsg);

    void getPNHBNCVisitRecordsSuccess(String healthRecordResponseModel);
    void  getPNHBNCVisitRecordsFailiur(String errorMsg);
}
