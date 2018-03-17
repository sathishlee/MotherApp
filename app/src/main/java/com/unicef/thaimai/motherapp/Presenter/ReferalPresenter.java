package com.unicef.thaimai.motherapp.Presenter;

import android.app.Activity;
import android.util.Log;

import com.unicef.thaimai.motherapp.constant.Apiconstants;
import com.unicef.thaimai.motherapp.interactor.ReferalInteractor;
import com.unicef.thaimai.motherapp.model.requestmodel.AddReferalRequestModel;
import com.unicef.thaimai.motherapp.view.ReferalViews;
import com.unicef.thaimai.motherapp.view.SosAlertViews;

/**
 * Created by sathish on 3/17/2018.
 */

public class ReferalPresenter implements ReferalInteractor {



    private ReferalViews view;
    private Activity activity;

    public ReferalPresenter(ReferalViews view, Activity activity) {
        this.view = view;
        this.activity = activity;
    }
    @Override
    public void addNewReferal(AddReferalRequestModel addReferalRequestModel) {
        String url = Apiconstants.BASE_URL + Apiconstants.POST_ADD_REFERAL;
        Log.d("Log in check Url--->", url);
        Log.d("picmeId", addReferalRequestModel.getPicmeId());
        Log.d("phcId", addReferalRequestModel.getPhcId());
        Log.d("mid", addReferalRequestModel.getMid());
        Log.d("vhnId", addReferalRequestModel.getVhnId());
        Log.d("rReferalDate", addReferalRequestModel.getrReferalDate());
        Log.d("rReferalTime", addReferalRequestModel.getrReferalTime());
        Log.d("rFacilityReferring", addReferalRequestModel.getrFacilityReferring());
        Log.d("rFacilityReferredTo", addReferalRequestModel.getrFacilityReferredTo());
        Log.d("rDiagonosis", addReferalRequestModel.getrDiagonosis());
        Log.d("rReferalReason", addReferalRequestModel.getrReferalReason());
        Log.d("rReferredBy", addReferalRequestModel.getrReferredBy());

    }
}
