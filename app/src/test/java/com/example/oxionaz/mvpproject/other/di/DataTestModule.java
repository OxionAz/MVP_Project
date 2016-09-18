package com.example.oxionaz.mvpproject.other.di;

import com.example.oxionaz.mvpproject.other.util.TestUtils;
import javax.inject.Singleton;
import dagger.Module;
import dagger.Provides;

@Module
public class DataTestModule {

    private TestUtils testUtils;

    public DataTestModule() {
        testUtils = new TestUtils();
    }

    @Provides
    @Singleton
    TestUtils provideTestUtils() {
        return testUtils;
    }
}
