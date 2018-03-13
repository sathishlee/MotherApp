package com.unicef.thaimai.motherapp.interactor;

/**
 * Created by sathish on 3/9/2018.
 */

public interface GetVisitHealthRecordsInteractor {
    void getAllVistHeathRecord(String strUrl,String pickmeid,String mid) ;
    void getPN_HBNC_VisitRecord(String strUrl,String pickmeid,String mid) ;

}
