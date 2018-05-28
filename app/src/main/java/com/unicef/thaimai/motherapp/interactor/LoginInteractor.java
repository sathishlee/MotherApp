package com.unicef.thaimai.motherapp.interactor;



public interface LoginInteractor {

    void checkPickmeId(String pickmeid, String dob, String strdeviceId, String mobileCheck, String mLatitude, String mLongitude, String versionCode) ;
    void checkOtp(String pickmeid, String dob, String strdeviceId,String otp, String mobileCheck);
    void forgetPassword(String pickmeid, String mob);

}
