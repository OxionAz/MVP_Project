package com.example.oxionaz.mvpproject.model;

import com.example.oxionaz.mvpproject.EventBus;
import com.example.oxionaz.mvpproject.model.db.DatabaseHelper;
import com.example.oxionaz.mvpproject.model.db.models.Info;
import com.example.oxionaz.mvpproject.model.rest.RestService;
import com.example.oxionaz.mvpproject.model.rest.models.Repo;
import java.util.List;
import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.functions.Func1;
import rx.schedulers.Schedulers;

public class DataManager implements Model {

    private RestService restService = new RestService();
    private DatabaseHelper databaseHelper = new DatabaseHelper();
    private EventBus eventBus;

    public DataManager(EventBus eventBus){
        this.eventBus = eventBus;
    }

    @Override
    public Observable<List<Info>> downloadListRepo(String user) {
        return restService.downloadListRepo(user)
                .doOnError(throwable -> eventBus.onDownloadError(throwable.getMessage()))
                .onErrorResumeNext(Observable.empty())
                .flatMap(Observable::from)
                .map(repo -> {
                    Info item = new Info();
                    item.setId(repo.getId());
                    item.setName(repo.getName());
                    item.setLanguage(repo.getLanguage());
                    item.setHtml_url(repo.getHtml_url());
                    item.setLogin(repo.getOwner().getLogin());
                    return item;
                })
                .toList()
                .doOnNext(databaseHelper::saveRepoToDB);
    }

    @Override
    public Observable<List<Info>> getListRepoFromDB() {
        return databaseHelper.getRepoFromDB()
                .doOnError(throwable -> eventBus.onDBError(throwable.getMessage()));
    }

    @Override
    public void clearData() {
        databaseHelper.clearRepoTable();
    }
}
