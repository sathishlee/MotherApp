package com.unicef.thaimai.motherapp.interactor;

import com.unicef.thaimai.motherapp.model.requestmodel.ImmunizationEntryRequestModel;

/**
 * Created by Suthishan on 20/1/2018.
 */

public interface ImmunizationEntryInteractor {

    void immunizationEntry(String url, ImmunizationEntryRequestModel immunizationEntryRequestModel);

    void immunizationID(String strpicmeId, String strmid);

    void checkImmunizationId(String strpicmeId, String strmid, String strImmuID);


    public void getImmunizationByVisit(String immId, String mid);

}
