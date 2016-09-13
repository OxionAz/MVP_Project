package com.example.oxionaz.mvpproject.presenter;

import com.example.oxionaz.mvpproject.App;
import com.example.oxionaz.mvpproject.model.DataManager;
import com.raizlabs.android.dbflow.structure.Model;
import java.util.List;
import javax.inject.Inject;
import rx.Subscription;
import rx.subscriptions.CompositeSubscription;

public abstract class BasePresenter implements Presenter {

    @Inject
    protected DataManager dataManager;

    @Inject
    protected CompositeSubscription compositeSubscription;

    public BasePresenter(){
        App.getAppComponent().inject(this);
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
