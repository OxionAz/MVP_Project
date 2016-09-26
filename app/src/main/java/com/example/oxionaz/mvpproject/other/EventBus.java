package com.example.oxionaz.mvpproject.other;

public interface EventBus {

    void onDownloadError(Throwable error);
    void onCashError(Throwable error);
}
