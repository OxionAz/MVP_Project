package com.example.oxionaz.mvpproject.other.util;

import android.content.res.Resources;
import com.example.oxionaz.mvpproject.R;

public class ConstManager {

    private static Resources resources = Resources.getSystem();

    public static final String BASE_URL = resources.getString(R.string.base_url);
    public static final String YANDEX_API_KEY = resources.getString(R.string.yandex_api_key);
}
