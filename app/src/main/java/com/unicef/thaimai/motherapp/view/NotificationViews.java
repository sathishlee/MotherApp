package com.unicef.thaimai.motherapp.view;

/**
 * Created by sathish on 3/29/2018.
 */

public interface NotificationViews {
    void showProgress();
    void hideProgress();
    void NotificationResponseSuccess(String response);
    void NotificationResponseError(String response);
}
