package com.example.oxionaz.mvpproject.other.di;

import com.example.oxionaz.mvpproject.model.DataManager;
import javax.inject.Singleton;
import dagger.Module;
import dagger.Provides;
import rx.subscriptions.CompositeSubscription;
import static org.mockito.Mockito.mock;

@Module
public class PresenterTestModule {

    @Provides
    @Singleton
    DataManager provideDataManager(){
        return mock(DataManager.class);
    }

    @Provides
    CompositeSubscription provideCompositeSubscription() {
        return new CompositeSubscription();
    }
}
