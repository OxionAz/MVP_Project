package com.example.oxionaz.mvpproject.other;

import com.example.oxionaz.mvpproject.BuildConfig;
import com.example.oxionaz.mvpproject.other.di.TestComponent;
import com.example.oxionaz.mvpproject.other.util.GsonUtils;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.runner.RunWith;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.annotation.Config;
import javax.inject.Inject;

@RunWith(RobolectricTestRunner.class)
@Config(application = TestApp.class,
        constants = BuildConfig.class,
        sdk = 23)
@Ignore
public class BaseTest {

    public TestComponent component;

    @Before
    public void setUp() throws Exception {
        component = (TestComponent) App.getAppComponent();
    }
}
