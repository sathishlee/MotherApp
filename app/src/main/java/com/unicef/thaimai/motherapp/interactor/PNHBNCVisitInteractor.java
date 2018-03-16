package com.unicef.thaimai.motherapp.interactor;


import com.unicef.thaimai.motherapp.model.requestmodel.PNHBNCVisitEntryRequestModel;
import com.unicef.thaimai.motherapp.model.responsemodel.PnHbncVisitRecordsModel;

/**
 * Created by Suthishan on 20/1/2018.
 */

public interface PNHBNCVisitInteractor {

    void getPNHBNCVisitCount(String strPicmeId, String strMid);
    void insertPNHBNCVistRecords(PNHBNCVisitEntryRequestModel pnhbncVisitEntryRequestModel);
}
