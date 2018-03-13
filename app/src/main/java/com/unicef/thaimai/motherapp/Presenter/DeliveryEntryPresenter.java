package com.unicef.thaimai.motherapp.Presenter;

import com.unicef.thaimai.motherapp.activity.DeliveryDetailsActivityEntry;
import com.unicef.thaimai.motherapp.interactor.DeliveryEntryInteractor;
import com.unicef.thaimai.motherapp.model.requestmodel.DeliveryEntryRequestModel;
import com.unicef.thaimai.motherapp.view.DeliveryEntryViews;

/**
 * Created by Suthishan on 20/1/2018.
 */

public class DeliveryEntryPresenter implements DeliveryEntryInteractor {
    DeliveryDetailsActivityEntry context;
    DeliveryEntryViews views;

    public DeliveryEntryPresenter (DeliveryDetailsActivityEntry context, DeliveryEntryViews views){
        this.context = context;
        this.views = views;
    }

    @Override
    public void deliveryEntry (final DeliveryEntryRequestModel deliveryEntryRequestModel){

    }

}
