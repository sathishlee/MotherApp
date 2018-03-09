package com.unicef.thaimai.motherapp.interactor;

import com.unicef.thaimai.motherapp.model.requestmodel.PrimaryDataRequestModel;

import java.util.Map;

/**
 * Created by sathish on 3/7/2018.
 */

public interface PrimaryRegisterInteractor {
    void getAllMotherPrimaryRegistration(String picmeId);
    void postprimaryData(String strPicmeId,PrimaryDataRequestModel primaryDataRequestModel);
}
