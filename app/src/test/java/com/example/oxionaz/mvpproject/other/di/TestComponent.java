package com.example.oxionaz.mvpproject.other.di;

import com.example.oxionaz.mvpproject.model.DataManagerTest;
import com.example.oxionaz.mvpproject.model.rest.RestServiceTest;
import com.example.oxionaz.mvpproject.other.BaseTest;

import javax.inject.Singleton;
import dagger.Component;

@Singleton
@Component(modules = {DataTestModule.class, ServiceHelperTestModule.class, ModelTestModule.class, PresenterTestModule.class, ViewTestModule.class})
public interface TestComponent extends AppComponent {

    void inject(BaseTest baseTest);

    void inject(RestServiceTest restServiceTest);

    void inject(DataManagerTest dataManagerTest);
}
