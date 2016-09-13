package com.example.oxionaz.mvpproject.util;

public interface EventBus {

    void onDownloadError(String error);
    void onCashError(String error);

}
