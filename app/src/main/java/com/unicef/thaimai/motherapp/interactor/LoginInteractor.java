package com.unicef.thaimai.motherapp.interactor;



public interface LoginInteractor {

    void checkPickmeId(String pickmeid, String dob, String strdeviceId) ;
    void checkOtp(String pickmeid, String dob, String strdeviceId,String otp);
}
