package com.unicef.thaimai.motherapp.view;

public interface CHILDDevlopmentViews {
    void showProgress();
    void hideProgress();
    void getCurrentmonthSuccess(String response);
    void getCurrentmonthFailiure(String response);

    void getQuestionReportSuccess(String response);
    void getQuestionReportFailiure(String response);

    void getQuestionsSuccess(String response);
    void getQuestionsFailiure(String response);

    void UpdateQuestinsSuccess(String response);
    void UpdateQuestinsFailiure(String response);
}
