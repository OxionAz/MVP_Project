package com.example.oxionaz.mvpproject.other;

import com.example.oxionaz.mvpproject.other.di.AppComponent;
import com.example.oxionaz.mvpproject.other.di.DaggerTestComponent;

public class TestApp extends App {

    @Override
    protected AppComponent buildComponent() {
        return DaggerTestComponent.builder()
                .build();
    }
}
