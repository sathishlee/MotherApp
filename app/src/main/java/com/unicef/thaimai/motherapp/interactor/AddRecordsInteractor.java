package com.unicef.thaimai.motherapp.interactor;

import com.unicef.thaimai.motherapp.model.requestmodel.AddRecordRequestModel;

/**
 * Created by sathish on 3/9/2018.
 */

public interface AddRecordsInteractor {
    void insertVistRecords(AddRecordRequestModel addRecordRequestModel);
}
