package com.unicef.thaimai.motherapp.app;
import android.app.Activity;
import android.app.Application;
import android.support.v4.app.Fragment;

import com.unicef.thaimai.motherapp.RealmDBModel.UserInfoRealmModel;

import io.realm.Realm;
import io.realm.RealmResults;


public class RealmController extends Application {
    private static RealmController instance;
    private final Realm realm;



    public RealmController(Application application) {
        realm = Realm.getDefaultInstance();
    }

    public static RealmController with(Fragment fragment) {

        if (instance == null) {
            instance = new RealmController(fragment.getActivity().getApplication());
        }
        return instance;
    }

    public static RealmController with(Activity activity) {

        if (instance == null) {
            instance = new RealmController(activity.getApplication());
        }
        return instance;
    }

    public static RealmController with(Application application) {

        if (instance == null) {
            instance = new RealmController(application);
        }
        return instance;
    }

    public static RealmController getInstance() {

        return instance;
    }

    public Realm getRealm() {

        return realm;
    }



    //clear all objects from Book.class
  /*  public void clearAll(String userInfoRealmModelClass) {
        realm.beginTransaction();
        realm.clearA(UserInfoRealmModelClass.class);
        realm.commitTransaction();
    }*/

    //find all objects in the Book.class
    public RealmResults<UserInfoRealmModel> getBooks() {

        return realm.where(UserInfoRealmModel.class).findAll();
    }

    //query a single item with the given id
    public UserInfoRealmModel getBook(String id) {

        return realm.where(UserInfoRealmModel.class).equalTo("id", id).findFirst();
    }

    //check if Book.class is empty
//    public boolean hasBooks() {
//
//        return !realm.allObjects(UserInfoRealmModel.class).isEmpty();
//    }

    //query example
    public RealmResults<UserInfoRealmModel> queryedBooks() {

        return realm.where(UserInfoRealmModel.class)
                .contains("author", "Author 0")
                .or()
                .contains("title", "Realm")
                .findAll();

    }

}
