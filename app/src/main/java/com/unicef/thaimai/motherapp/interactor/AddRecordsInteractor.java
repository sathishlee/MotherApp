package com.unicef.thaimai.motherapp.interactor;

import com.unicef.thaimai.motherapp.model.requestmodel.AddRecordRequestModel;

/**
 * Created by sathish on 3/9/2018.
 */

public interface AddRecordsInteractor {

    void getVisitCount(String  strPickmeId, String strMid);
    void insertVistRecords(AddRecordRequestModel addRecordRequestModel);
}
