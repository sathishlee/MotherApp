package com.unicef.thaimai.motherapp.view;

/**
 * Created by Suthishan on 20/1/2018.
 */

public interface UninstallerViews {

    void showProgress();
    void hideProgress();
    void showUninstallSuccess(String response);
    void showUninstallError(String error);
}
