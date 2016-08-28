package com.example.oxionaz.mvpproject.model.rest;

import com.example.oxionaz.mvpproject.model.rest.models.Repo;
import java.util.List;
import rx.Observable;

public interface Rest {
    Observable<List<Repo>> downloadListRepo(String user);
}
