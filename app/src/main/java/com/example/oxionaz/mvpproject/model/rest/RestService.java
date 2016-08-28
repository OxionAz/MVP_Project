package com.example.oxionaz.mvpproject.model.rest;

import com.example.oxionaz.mvpproject.model.rest.models.Repo;
import java.util.List;
import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

public class RestService implements Rest {

    private RestClient restClient = new RestClient();

    @Override
    public Observable<List<Repo>> downloadListRepo(String user){
        return restClient.getGitHubAPI().getListRepo(user)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io());
    }
}
