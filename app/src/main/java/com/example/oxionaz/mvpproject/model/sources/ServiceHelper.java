package com.example.oxionaz.mvpproject.model.sources;

import com.example.oxionaz.mvpproject.other.App;
import com.example.oxionaz.mvpproject.other.Const;
import javax.inject.Inject;
import javax.inject.Named;
import rx.Observable;
import rx.Scheduler;

public abstract class ServiceHelper {

    private final Observable.Transformer schedulersTransformer;

    @Inject
    @Named(Const.UI_THREAD)
    Scheduler uiThread;

    @Inject
    @Named(Const.IO_THREAD)
    Scheduler ioThread;

    public ServiceHelper(){
        App.getAppComponent().inject(this);
        schedulersTransformer = o -> ((Observable) o)
                .observeOn(uiThread)
                .subscribeOn(ioThread)
                .unsubscribeOn(ioThread);
    }

    @SuppressWarnings("unchecked")
    protected <T> Observable.Transformer<T, T> applySchedulers() {
        return (Observable.Transformer<T, T>) schedulersTransformer;
    }

}
