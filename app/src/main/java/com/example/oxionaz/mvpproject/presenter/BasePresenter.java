package com.example.oxionaz.mvpproject.presenter;

import com.example.oxionaz.mvpproject.EventBus;
import com.example.oxionaz.mvpproject.model.DataManager;
import com.raizlabs.android.dbflow.structure.Model;

import java.util.List;

import rx.Subscription;
import rx.subscriptions.CompositeSubscription;

public abstract class BasePresenter implements Presenter {

    protected DataManager dataManager;
    private CompositeSubscription compositeSubscription = new CompositeSubscription();

    public BasePresenter(EventBus eventBus){
        this.dataManager = new DataManager(eventBus);
    }

    // Clear all subscriptions
    @Override
    public void onStop() {
        compositeSubscription.clear();
    }

    // Add subscription
    protected void addSubscription(Subscription subscription) {
        compositeSubscription.add(subscription);
    }

    // Check data for exists
    protected static <T extends Model> boolean checkList(List<T> data){
        return data != null && !data.isEmpty();
    }

}
