package com.unicef.thaimai.motherapp.interactor;

/**
 * Created by sathish on 3/14/2018.
 */

public interface LocationUpdateIntractor {
      void uploadLocationToServer(String picmeId,String vhnId,String mid,String latitude,String longitude);
}
