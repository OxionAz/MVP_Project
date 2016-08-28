package com.example.oxionaz.mvpproject.presenter;

public interface Presenter {
    void tryGetDataFromDB();
    void clearData();
    void onSearchClick();
    void onStop();
}
