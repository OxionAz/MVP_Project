package com.example.oxionaz.mvpproject.other;

public interface EventBus {

    void onDownloadError(String error);
    void onCashError(String error);

}
