package com.unicef.thaimai.motherapp.view;

/**
 * Created by Suthishan on 20/1/2018.
 */

public interface RegisterViews {

    void showProgress();
    void hideProgress();

    void getRegisterSuccess(String response);
    void getRegisterFailure(String response);

    void getAllDistrictDataSuccess(String response);
    void getAllDistrictDataFailure(String response);

    void getAllBlockDataSuccess(String response);
    void getAllBlockDataFailure(String response);

    void getAllPHCDataSuccess(String response);
    void getAllPHCDataFailure(String response);



}
