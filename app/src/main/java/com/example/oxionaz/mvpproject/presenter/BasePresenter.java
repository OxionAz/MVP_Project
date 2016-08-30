package com.example.oxionaz.mvpproject.presenter;

import com.example.oxionaz.mvpproject.EventBus;
import com.example.oxionaz.mvpproject.model.DataManager;
import rx.Subscription;
import rx.subscriptions.CompositeSubscription;

public abstract class BasePresenter implements Presenter {

    protected DataManager dataManager;
    private CompositeSubscription compositeSubscription = new CompositeSubscription();

    public BasePresenter(EventBus eventBus){
        this.dataManager = new DataManager(eventBus);
    }

    protected void addSubscription(Subscription subscription) {
        compositeSubscription.add(subscription);
    }

    @Override
    public void onStop() {
        compositeSubscription.clear();
    }

}
