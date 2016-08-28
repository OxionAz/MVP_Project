package com.example.oxionaz.mvpproject.model.db;

import com.example.oxionaz.mvpproject.model.db.models.Info;
import java.util.List;
import rx.Observable;

public interface Database {
    void saveRepoToDB(List<Info> repos);
    Observable<List<Info>> getRepoFromDB();
    void clearRepoTable();
}
