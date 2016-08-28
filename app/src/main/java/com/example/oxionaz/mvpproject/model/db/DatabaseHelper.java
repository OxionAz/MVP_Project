package com.example.oxionaz.mvpproject.model.db;

import android.util.Log;
import com.example.oxionaz.mvpproject.model.db.models.Info;
import com.raizlabs.android.dbflow.config.FlowManager;
import com.raizlabs.android.dbflow.sql.language.SQLite;
import com.raizlabs.android.dbflow.structure.BaseModel;
import com.raizlabs.android.dbflow.structure.database.transaction.ProcessModelTransaction;
import java.util.List;
import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

public class DatabaseHelper implements Database {

    private static final String LOG_ERROR = "FlowManager";

    @Override
    public void saveRepoToDB(List<Info> repos){
        if (repos != null && !repos.isEmpty()) {
            clearRepoTable();
            FlowManager
                    .getDatabase(AppDatabase.class)
                    .beginTransactionAsync(new ProcessModelTransaction.Builder<>(
                            (ProcessModelTransaction.ProcessModel<Info>) BaseModel::save)
                            .addAll(repos)
                            .build())
                    .error((transaction, error) -> Log.e(LOG_ERROR, "onError: ", error))
                    .build()
                    .execute();
        }
    }

    @Override
    public Observable<List<Info>> getRepoFromDB(){
        return Observable.just(SQLite.select().from(Info.class).queryList())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io());
    }

    @Override
    public void clearRepoTable(){
        SQLite.delete(Info.class).execute();
    }
}
