package com.example.oxionaz.mvpproject;

public interface EventBus {
    void onDownloadError(String error);
    void onDBError(String error);
}
