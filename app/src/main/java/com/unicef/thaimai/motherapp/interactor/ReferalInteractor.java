package com.unicef.thaimai.motherapp.interactor;

/**
 * Created by sathish on 3/17/2018.
 */

public interface ReferalInteractor {
    //    void addNewReferal(NearestReferalHospitalModel addReferalRequestModel) ;
    void addNewReferal(
            String picmeId,
            String mid,
            String vhnId,
            String phcId,
            String rReferalDate,
            String rReferalTime,
            String rFacilityReferring,
            String rFacilityReferredTo,
            String rDiagonosis,
            String rReferalReason,
            String rReferredBy, String rFacilityReferredCode, String rFacilityReferredToCode);

    void updateReferal(
            String rid,
            String rUpdateDate,
            String rUpdateTime,

            String rUpdateReceivedBy,
            String rUpdateReceivingFacility,
            String rUpdateInLabour,
            String rUpdateAdmitted);

    void getReffralNearestHospital(String latitude, String longitude);

    void getReffralNeList(String mid,
                          String phcId,
                          String vhnId,
                          String picmeId);

}


