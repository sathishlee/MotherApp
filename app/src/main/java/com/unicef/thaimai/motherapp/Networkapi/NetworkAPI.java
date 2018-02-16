package com.unicef.thaimai.motherapp.Networkapi;

import com.unicef.thaimai.motherapp.constant.Apiconstants;
import com.unicef.thaimai.motherapp.model.responsemodel.LoginResponseModel;

import retrofit2.Call;
import retrofit2.http.GET;

/**
 * Created by sathish on 2/15/2018.
 */

public interface NetworkAPI {

    @GET(Apiconstants.LOG_IN_CHECK_PICME)
    Call<LoginResponseModel> checkPicme();


}
