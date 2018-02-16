package com.unicef.thaimai.motherapp.interactor;



public interface LoginInteractor {

    void checkPickmeId(String pickmeid) ;
    void verifyOtp(String pickmeid, String otp) ;
}
