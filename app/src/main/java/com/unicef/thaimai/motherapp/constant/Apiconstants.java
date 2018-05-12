package com.unicef.thaimai.motherapp.constant;




public class Apiconstants {

//    public static final String BASE_URL = "http://218.248.44.77/thaimaiapp/api/";
    public static final String BASE_URL = "http://192.168.100.222/thaimaiapp/api/";
//    public static final String BASE_URL = "http://demo.satvatinfosol.com/thaimai/api/";

    public static final String PHOTO_URL = "http://192.168.100.222/thaimaiapp/assets/mother_photos/";
//    public static final String PHOTO_URL = "http://demo.satvatinfosol.com/thaimai/assets/mother_photos/";
//    public static final String PHOTO_URL = "http://218.248.44.77/thaimaiapp/assets/mother_photos/";


  public static final String HEALTH_TIPS_VIDEO_URL = "http://192.168.100.222/thaimaiapp/assets/mother_videos/";
//    public static final String HEALTH_TIPS_VIDEO_URL = "http://demo.satvatinfosol.com/thaimai/assets/mother_videos/";
//    public static final String HEALTH_TIPS_VIDEO_URL = "http://218.248.44.77/thaimaiapp/assets/mother_videos/";


    public static final String VISIT_REPORTS_URL = "http://192.168.100.222/thaimaiapp/assets/mother_reports/";
//    public static final String VISIT_REPORTS_URL = "http://demo.satvatinfosol.com/thaimai/assets/mother_reports/";
//    public static final String VISIT_REPORTS_URL = "http://218.248.44.77/thaimaiapp/assets/mother_reports/";

    public static final String LOG_IN_CHECK_PIKME = "login/check/";
    public static final String CHECK_OTP = "login/checkOTP/";

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

    public static final String IMMUNIZATION_ID_EXIST = "mother/mImmunizationIDExist";

//    http://192.168.100.27/thaimaiapp/api/mother/mImmunizationEditSingle
    public static final String GET_IMMUNIZATION_BY_VISIT= "mother/mImmunizationEditSingle";
//    http://192.168.100.27/thaimaiapp/api/mother/mImmunizationEdit
    public static final String IMMUNIZATION_BY_VISIT_EDIT= "mother/mImmunizationEdit";

//    http://192.168.100.27/thaimaiapp/api/mother/mMotherFlashNotification
public static final String FLASH_NOTIFICATION= "mother/mMotherFlashNotification";

//    http://192.168.100.235/thaimaiapp/api/Referal/mReferalClosed

    public static final String POST_REFERAL_CLOSED = "Referal/mReferalClosed";

//NOTIFICATION API's
//    http://192.168.100.27/thaimaiapp/api/mother/mNotificationCount

    public static final String POST_NOTIFICATION_COUNT= "mother/mNotificationCount";

    //    http://192.168.100.222/thaimaiapp/api/mother/mMotherNotificationList

    public static final String POST_NOTIFICATION_LIST= "mother/mMotherNotificationList";

    public static final String HEALTH_TIPS_VIDEO= "mother/HealthTips";

//http://192.168.100.27/thaimaiapp/api/mother/editProfile
    public   static  final String POST_EDIT_PROFILE ="mother/editProfile";
//    http://192.168.100.27/thaimaiapp/api/mother/uploadPhoto

    public  static  final String POST_UPLOAD_PROFILE_PHOTO ="mother/uploadPhoto";

    public static final String UPLOAD_MULTIPLE_IMAGES = "mother/uploadReports";

    public static final String GET_ALL_VISIT_REPORTS = "mother/getAllUploadReports";

}
