package com.example.oxionaz.mvpproject.model.sources;

import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

public abstract class ServiceHelper {

    private final Observable.Transformer schedulersTransformer;

    public ServiceHelper(){
        schedulersTransformer = o -> ((Observable) o)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io());
    }

    @SuppressWarnings("unchecked")
    protected <T> Observable.Transformer<T, T> applySchedulers() {
        return (Observable.Transformer<T, T>) schedulersTransformer;
    }

}
