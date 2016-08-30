package com.example.oxionaz.mvpproject;

public interface EventBus {

    void onDownloadError(String error);
    void onCashError(String error);

}
