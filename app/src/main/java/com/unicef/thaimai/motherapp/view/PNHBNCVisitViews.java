package com.unicef.thaimai.motherapp.view;

/**
 * Created by Suthishan on 20/1/2018.
 */

public interface PNHBNCVisitViews {

        void showProgress();
        void hideProgress();
        void pnhbncVisitSuccess(String response);
        void pnhbncVisitFailiure(String response);

        void getpnhbncVisitNumberSuccess(String response);
        void getpnhbncVisitNumberFailiure(String response);
void checkpnhbncVisitIdSuccess(String response);
void checkpnhbncVisitIdFailiure(String response);

}
