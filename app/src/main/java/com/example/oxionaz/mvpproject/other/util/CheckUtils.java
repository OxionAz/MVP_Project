package com.example.oxionaz.mvpproject.other.util;

import com.raizlabs.android.dbflow.structure.Model;
import java.util.List;

public class CheckUtils {

    // Check data list for exists
    public static  <T extends Model> boolean checkList(List<T> data){
        return data != null && !data.isEmpty();
    }

    // Check item for exists
    public static  <T> boolean checkItem(T model){
        return model != null;
    }
}
