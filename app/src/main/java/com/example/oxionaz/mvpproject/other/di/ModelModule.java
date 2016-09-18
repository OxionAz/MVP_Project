package com.example.oxionaz.mvpproject.other.di;

import com.example.oxionaz.mvpproject.model.sources.db.DatabaseHelper;
import com.example.oxionaz.mvpproject.model.sources.rest.RestService;
import javax.inject.Singleton;
import dagger.Module;
import dagger.Provides;

@Module
public class ModelModule {

    @Provides
    @Singleton
    RestService provideRestService(){
        return new RestService();
    }

    @Provides
    @Singleton
    DatabaseHelper provideDatabaseHelper(){
        return new DatabaseHelper();
    }

}
