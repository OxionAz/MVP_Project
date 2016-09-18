package com.example.oxionaz.mvpproject.other.di;

import com.example.oxionaz.mvpproject.model.sources.db.DatabaseHelper;
import com.example.oxionaz.mvpproject.model.sources.rest.RestService;
import javax.inject.Singleton;
import dagger.Module;
import dagger.Provides;
import static org.mockito.Mockito.mock;

@Module
public class ModelTestModule {

    @Provides
    @Singleton
    RestService provideRestService(){
        return mock(RestService.class);
    }

    @Provides
    @Singleton
    DatabaseHelper provideDatabaseHelper(){
        return mock(DatabaseHelper.class);
    }
}
