package com.unicef.thaimai.motherapp.view;

/**
 * Created by sathish on 3/14/2018.
 */

public interface LocationUpdateViews {
    void showProgress();
    void hideProgress();
    void locationUpdateSuccess(String loginResponseModel);
    void locationUpdateFailiure(String string);

    void getNearbyHospitalSuccess(String loginResponseModel);
    void getNearbyHospitalFailiure(String string);
}
