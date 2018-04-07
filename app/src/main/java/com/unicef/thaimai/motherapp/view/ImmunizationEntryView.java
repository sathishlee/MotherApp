package com.unicef.thaimai.motherapp.view;

/**
 * Created by Suthishan on 20/1/2018.
 */

public interface ImmunizationEntryView {

    void showProgress();
    void hideProgress();

    void immunizationEntrySuccess(String response);
    void immunizationEntryFailure(String response);

    void immunizationIDSuccess(String response);
    void immunizationIDFailure(String response);

    void getImmunizationByVisitSuccess(String response);
    void getImmunizationByVisitFailure(String response);



}
