package com.unicef.thaimai.motherapp.interactor;


import com.unicef.thaimai.motherapp.model.ChildDevelopmentModdel.ChildDevQuestionModel;

import java.util.ArrayList;

public interface CHILDDevlopmentInteractor {
    void getCurrentChildDevMonth(String strPickmeId, String strMid);
    void getAllChildDevelopmentRecords(String strPickmeId, String strMid);
    void getAllQuestions( );

    void updateChildDevQuestions(String strPickmeId, String strMid, String strVhnId, String MtrmonthCheck, ArrayList<ChildDevQuestionModel> childDevQuestionModel);

}
