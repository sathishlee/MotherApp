package com.unicef.thaimai.motherapp.view;

/**
 * Created by Suthishan on 20/1/2018.
 */

public interface DeliveryEntryViews {
    void showProgress();
    void hideProgress();
    void deliveryentrySuccess(String response);
    void deliveryentryFailiure(String response);

    void getdeliveryNumberSuccess(String response);
    void getdeliveryNumberFailiure(String response);

    void deliveryDetailsSuccess(String response);
    void deliveryDetailsFailure(String response);

}
