package com.unicef.thaimai.motherapp.interactor;

import com.unicef.thaimai.motherapp.model.requestmodel.DeliveryEntryRequestModel;

/**
 * Created by Suthishan on 20/1/2018.
 */

public interface DeliveryEntryInteractor {

    void deliveryEntry(DeliveryEntryRequestModel deliveryEntryRequestModel);

    void deliveryNumber(String strPicmeid, String strMid);

    void deliveryDetails (String strPicmeid, String strMid, String strDid);

}
