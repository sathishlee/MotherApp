package com.unicef.thaimai.motherapp.constant;




public class Apiconstants {

//    public static final String BASE_URL = "http://192.168.100.19/thaimaiapp/api/";
    public static final String BASE_URL = "http://192.168.100.27/thaimaiapp/api/";
//    public static final String BASE_URL = "http://demo.satvatinfosol.com/thaimai/api/";

//    public static final String LOG_IN_CHECK_PICME = "login/";                  //100000000013
    public static final String LOG_IN_CHECK_PIKME = "login/check/";
    public static final String POST_PRIMARY_INFO = "Mother/mPrimaryInfo";
    public static final String POST_PRIMARY_INFO_UPDATE = "mother/mPrimaryInfoUpdate";
    public static final String POST_DASH_BOARD = "mother/mDashboard/";/*    http://192.168.100.19/thaimaiapp/api/Mother/mPrimaryInfo
     /*    http://192.168.100.19/thaimaiapp/api/Mother/mPrimaryInfo
*/
    public static final String POST_VIST_HEALTH_RECORD_NUMBER = "mother/mHealthRecordVisitNumber";
    public static final String POST_VIST_HEALTH_RECORD = "mother/mHealthRecord/";
//    public static final String POST_VIST_HEALTH_RECORD_PICME = "mother/mHealthRecordPicme/";
    public static final String POST_VIST_HEALTH_RECORD_BASE = "mother/mHealthRecordBase/";

     /*    http://192.168.100.19/thaimaiapp/api/mother/mHealthRecord*/

    public static final String POST_PN_HBNC_VIST_RECORD = "mother/mPN_Record/";

    public static final String POST_VIST_HEALTH_RECORD_INSERT = "mother/mHealthRecordInsert";
     /*   http://192.168.100.19/thaimaiapp/api/mother/mHealthRecordInsert*/

//    public static final String USER_INFO = "home/userinfo/";
    public static final String LOCATION_UPDATE = "mother/locationUpdate";

    public static final String DELIVERY_DETAIL_ENTRY = "mother/mDeleveryDetailsInsert";

    public static final String DELIVERY_NUMBER = "mother/mDeleveryNumber";
    public static final String POST_SOS_ALERT = "Sos/mSOS_alert";


    public static final String NEAR_BY_HOSPITAL = "mother/findNearbyLocation";

    public static final String PN_HBNC_VISIT_INSERT = "mother/mPN_Record_Insert";     //pnVisitID

    public static final String PN_HBNC_VISITNUMBER = "mother/mPN_RecordVisitNumber";

//    http://192.168.100.235/thaimaiapp/api/mother/mPN_RecordVisitExist
    public static final String PN_HBNC_VISIT_EXIST = "mother/mPN_RecordVisitExist";

    //    http://192.168.100.235/thaimaiapp/api/Referal/mReferalAdd
    public static final String POST_ADD_REFERAL = "Referal/mReferalAdd";

    //   http://192.168.100.235/thaimaiapp/api/Referal/mReferalUpdate
    public static final String POST_UPDATE_REFERAL = "Referal/mReferalUpdate";
    //    http://192.168.100.235/thaimaiapp/api/Referal/mReferalNearestHospital
    public static final String POST_REFERAL_NEAREST_HOSPITAL = "Referal/mReferalNearestHospital";

    //    http://192.168.100.235/thaimaiapp/api/Referal/mReferalList
    public static final String POST_REFERAL_LIST = "Referal/mReferalList";

    public static final String DELIVERY_DETAILS = "mother/mDeleveryDetails";

    public static final String IMMUNIZATION_ENTRY = "mother/mImmunizationAdd";

    public static final String IMMUNIZATION_ID = "mother/mImmunizationCount";

    public static final String IMMUNIZATION_LIST = "mother/mImmunizationList";



//    http://192.168.100.235/thaimaiapp/api/Referal/mReferalClosed

    public static final String POST_REFERAL_CLOSED = "Referal/mReferalClosed";






    private static final String REGISTER_REQUEST_URL = "http://192.168.100.19/maps/sample.php";

    private static final String REGISTER_REQUEST_URL_UPLOAD = "http://192.168.100.19/maps/";

    private static final String POST_UPLOAD_LOCATION = "sample.php";

    private static final String POST_NEAR_HOSPITAL = "near_by.php?lt=13.0054576&lg=80.2552421";



}
