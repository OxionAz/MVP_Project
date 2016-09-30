package com.example.oxionaz.mvpproject.presenter;

import com.example.oxionaz.mvpproject.other.App;
import com.example.oxionaz.mvpproject.model.DataManager;
import com.raizlabs.android.dbflow.structure.Model;
import java.util.List;
import javax.inject.Inject;
import rx.Subscription;
import rx.subscriptions.CompositeSubscription;

public abstract class BasePresenter implements Presenter {

    @Inject
    DataManager dataManager;

    @Inject
    CompositeSubscription compositeSubscription;

    public BasePresenter(){
        App.getAppComponent().inject(this);
    }

    // Clear all subscriptions
    @Override
    public void onStop() {
        compositeSubscription.clear();
    }

    // Add subscription
    void addSubscription(Subscription subscription) {
        compositeSubscription.add(subscription);
    }
}
