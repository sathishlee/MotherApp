package com.unicef.thaimai.motherapp.constant;

import com.unicef.thaimai.motherapp.model.responsemodel.VisitRecordsSingleResponseModel;

import java.util.ArrayList;

/**
 * Created by Suthishan on 20/1/2018.
 */

public class AppConstants {

    public static boolean REFERAL_STATUS = false;
    public static String REFERAL_REASON = "";
    public static String REFERAL_DIAGONOSIS = "";
    public static String REFERAL_TO = "";
    public static String REFERAL_FACILITY = "";
    public static String REFERAL_BY = "";
    public static String REFERAL_TIME = "";
    public static String REFERAL_DATE = "";
    public static String REFERAL_ID = "";
    public static boolean CREATE_NEW_REFRAL = false;
    public  static  boolean isMainActivityOpen=true;
    public static int POP_UP_COUNT;
    public   static final String isMainActivityOpen_Count="minActivity_open_count";

    public static final String PREF_NAME = "motherapp";
    public static final String IS_LOGIN = "is_Login";
    public static final String USER_INFO = "user_info";
    public static final String USER_MEDICAL = "user_medical";
    public static final String USER_EMERGENCY_CONTACTS = "emergency_contacts";

    public static final int LOCATION_INTERVAL = 1000;            //1800000
    public static final int FASTEST_LOCATION_INTERVAL = 1000;
    public static String EXTRA_LATITUDE = "";
    public static String EXTRA_LONGITUDE = "";



    public static String NEAR_LATITUDE = "";
    public static String NEAR_LONGITUDE = "";

    public static final int REQUEST_LOCATION=001;


    public  static boolean BACK_BUTTON_GONE=false;

    public static final String PICME_ID = "picme_id";
    public static final String M_ID = "m_id";
    public static final String PHC_ID = "phcId";
    public static final String VHN_ID = "vhnId";
    public static final String AWW_ID = "awwId";
    public static final String MOTHER_NAME = "name";
    public static final String MOTHER_AGE = "age";
    public static final String MOTHER_STATUS = "status";
    public static final String DELIVERY_ID = "did";


    public static final String DEVICE_ID = "deviceId";
    public static final String GST_WEEK = "week";
    public static final String VHN_MOBILE_NUMBER = "9788782667";

    public static final String IMMUNIZATION_ID = "immId";
    public static Boolean IMMUNIZATION_EDIT = true;
//    public  static String ImmuID="";


//    public static final String M_ID = "m_id";


    public static final String NOTIFICATION_COUNT= "0";

    public static final String CURENT_ADDRESS= "curaddress";
    public static final String CURENT_LAT= "curlatitude";
    public static final String CURENT_LON= "curlontitude";

    //Store First Time While Login
    public static final String PICME_ID_CHECK = "picmeId";
    public static final String MOTHER_DOB = "DOB";
    public static final String MOTHER_PHOTO = "mPhoto";


    public static ArrayList<VisitRecordsSingleResponseModel> mylist=null;
}
