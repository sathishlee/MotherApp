package com.unicef.thaimai.motherapp.interactor;

/**
 * Created by Suthishan on 20/1/2018.
 */

public interface RegisterInteractor {

    void getDistrictJson();
    void getBlockJson(String selDistrict);
    void getPHCJson(String selDistrict, String selBlock);

    void submitRegisterMother(String deviceToken,String mlat, String mLong,String name, String dob,
                              String district, String block, String phc, String primaryNumber,
                              String secNumber, String husName);


}
