package com.example.oxionaz.mvpproject.model.db;

import com.raizlabs.android.dbflow.annotation.Database;

@Database(name = AppDatabase.NAME, version = AppDatabase.VERSION)
public class AppDatabase {

    public static final String NAME = "GitHub"; // we will add the .db extension
    public static final int VERSION = 1;
}