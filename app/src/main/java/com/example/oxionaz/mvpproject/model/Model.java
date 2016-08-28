package com.example.oxionaz.mvpproject.model;

import com.example.oxionaz.mvpproject.model.db.models.Info;
import java.util.List;
import rx.Observable;

public interface Model {
    Observable<List<Info>> downloadListRepo(String user);
    Observable<List<Info>> getListRepoFromDB();
    void clearData();
}
