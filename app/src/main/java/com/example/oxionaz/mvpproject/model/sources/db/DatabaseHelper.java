package com.example.oxionaz.mvpproject.model.sources.db;

import android.util.Log;
import com.example.oxionaz.mvpproject.model.sources.ServiceHelper;
import com.example.oxionaz.mvpproject.model.sources.db.models.Branch;
import com.example.oxionaz.mvpproject.model.sources.db.models.Branch_Table;
import com.example.oxionaz.mvpproject.model.sources.db.models.Contributor;
import com.example.oxionaz.mvpproject.model.sources.db.models.Contributor_Table;
import com.example.oxionaz.mvpproject.model.sources.db.models.Repository;
import com.example.oxionaz.mvpproject.model.sources.db.models.Repository_Table;
import com.example.oxionaz.mvpproject.other.util.CheckUtils;
import com.raizlabs.android.dbflow.config.FlowManager;
import com.raizlabs.android.dbflow.sql.language.SQLite;
import com.raizlabs.android.dbflow.structure.Model;
import com.raizlabs.android.dbflow.structure.database.transaction.ProcessModelTransaction;
import java.util.List;
import rx.Observable;

public class DatabaseHelper extends ServiceHelper implements Database {

    private static final String LOG_ERROR = "FlowManager";

    // Save data to cash

    @Override
    public void saveRepositories(List<Repository> repositories){
        if (CheckUtils.checkList(repositories)) {
            clearRepositoryTable();
            saveTransaction(repositories);
        }
    }

    @Override
    public void saveBranches(List<Branch> branches) {
        if (CheckUtils.checkList(branches)) {
            clearBranchTable();
            saveTransaction(branches);
        }
    }

    @Override
    public void saveContributors(List<Contributor> contributors) {
        if (CheckUtils.checkList(contributors)) {
            clearContributorTable();
            saveTransaction(contributors);
        }
    }

    // Get data from cash

    @Override
    public Observable<List<Repository>> getRepositories(){
        return Observable.just(SQLite.select().from(Repository.class)
                .orderBy(Repository_Table.name, true)
                .queryList())
                .compose(applySchedulers());
    }

    @Override
    public Observable<List<Branch>> getBranches() {
        return Observable.just(SQLite.select().from(Branch.class)
                .orderBy(Branch_Table.name, true)
                .queryList())
                .compose(applySchedulers());
    }

    @Override
    public Observable<List<Contributor>> getContributors() {
        return Observable.just(SQLite.select().from(Contributor.class)
                .orderBy(Contributor_Table.login, true)
                .queryList())
                .compose(applySchedulers());
    }

    // Clear data from cash

    @Override
    public void clearRepositoryTable(){
        SQLite.delete(Repository.class).execute();
    }

    @Override
    public void clearBranchTable() {
        SQLite.delete(Branch.class).execute();
    }

    @Override
    public void clearContributorTable() {
        SQLite.delete(Contributor.class).execute();
    }

    // Transaction for save
    <T extends Model> void saveTransaction(List<T> data){
        FlowManager
                .getDatabase(AppDatabase.class)
                .beginTransactionAsync(new ProcessModelTransaction.Builder<>(
                        (ProcessModelTransaction.ProcessModel<T>) Model::save)
                        .addAll(data)
                        .build())
                .error((transaction, error) -> Log.e(LOG_ERROR, "onError: ", error))
                .build()
                .execute();
    }
}
