package com.example.oxionaz.mvpproject.model.sources.db;

import com.example.oxionaz.mvpproject.model.sources.db.models.Branch;
import com.example.oxionaz.mvpproject.model.sources.db.models.Contributor;
import com.example.oxionaz.mvpproject.model.sources.db.models.Repository;
import com.example.oxionaz.mvpproject.other.BaseTest;
import com.raizlabs.android.dbflow.config.FlowManager;
import com.raizlabs.android.dbflow.sql.language.SQLite;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import java.util.ArrayList;
import java.util.List;
import rx.Observable;
import rx.observers.TestSubscriber;
import static org.junit.Assert.assertEquals;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.spy;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class DatabaseHelperTest extends BaseTest {

    private DatabaseHelper databaseHelper;

    @Before
    public void setUp() throws Exception {
        super.setUp();
        component.inject(this);

        databaseHelper = spy(new DatabaseHelper());

        when(databaseHelper.getRepositories()).thenReturn(Observable.just(repositoryList));
        when(databaseHelper.getBranches()).thenReturn(Observable.just(branchList));
        when(databaseHelper.getContributors()).thenReturn(Observable.just(contributorList));
    }

    @Test
    public void testSaveRepositories(){
        databaseHelper.saveRepositories(repositoryList);

        verify(databaseHelper).clearRepositoryTable();
        verify(databaseHelper).saveTransaction(repositoryList);
    }

    @Test
    public void testSaveRepositoriesOnNull(){
        databaseHelper.saveRepositories(null);

        verify(databaseHelper, never()).clearRepositoryTable();
        verify(databaseHelper, never()).saveTransaction(any());
    }

    @Test
    public void testSaveRepositoriesOnEmpty(){
        databaseHelper.saveRepositories(new ArrayList<>());

        verify(databaseHelper, never()).clearRepositoryTable();
        verify(databaseHelper, never()).saveTransaction(any());
    }

    @Test
    public void testSaveBranches() {
        databaseHelper.saveBranches(branchList);

        verify(databaseHelper).clearBranchTable();
        verify(databaseHelper).saveTransaction(branchList);
    }

    @Test
    public void testSaveBranchesOnNull(){
        databaseHelper.saveBranches(null);

        verify(databaseHelper, never()).clearBranchTable();
        verify(databaseHelper, never()).saveTransaction(any());
    }

    @Test
    public void testSaveBranchesOnEmpty(){
        databaseHelper.saveBranches(new ArrayList<>());

        verify(databaseHelper, never()).clearBranchTable();
        verify(databaseHelper, never()).saveTransaction(any());
    }

    @Test
    public void testSaveContributors() {
        databaseHelper.saveContributors(contributorList);

        verify(databaseHelper).clearContributorTable();
        verify(databaseHelper).saveTransaction(contributorList);
    }

    @Test
    public void testSaveContributorsOnNull(){
        databaseHelper.saveContributors(null);

        verify(databaseHelper, never()).clearContributorTable();
        verify(databaseHelper, never()).saveTransaction(any());
    }

    @Test
    public void testSaveContributorsOnEmpty(){
        databaseHelper.saveContributors(new ArrayList<>());

        verify(databaseHelper, never()).clearContributorTable();
        verify(databaseHelper, never()).saveTransaction(any());
    }

    @Test
    public void testGetRepositories(){
        TestSubscriber<List<Repository>> testSubscriber = new TestSubscriber<>();
        databaseHelper.getRepositories().subscribe(testSubscriber);
        testSubscriber.assertNoErrors();
        testSubscriber.assertValueCount(1);

        List<Repository> actual = testSubscriber.getOnNextEvents().get(0);
        assertEquals(7, actual.size());
        assertEquals(26314692, actual.get(0).getId());
        assertEquals("Android-Rate", actual.get(0).getName());
        assertEquals("andrey7mel", actual.get(0).getOwner());
        assertEquals(null, actual.get(0).getLanguage());
        assertEquals("https://github.com/andrey7mel/Android-Rate", actual.get(0).getHtml_url());
    }

    @Test
    public void testGetBranches() {
        TestSubscriber<List<Branch>> testSubscriber = new TestSubscriber<>();
        databaseHelper.getBranches().subscribe(testSubscriber);
        testSubscriber.assertNoErrors();
        testSubscriber.assertValueCount(1);

        List<Branch> actual = testSubscriber.getOnNextEvents().get(0);
        assertEquals(3, actual.size());
        assertEquals("QuickStart", actual.get(0).getName());
        assertEquals("94870e23f1cfafe7201bf82985b61188f650b245", actual.get(0).getSha());
        assertEquals("https://api.github.com/repos/andrey7mel/Android-Rate/commits/94870e23f1cfafe7201bf82985b61188f650b245", actual.get(0).getUrl());
    }

    @Test
    public void testGetContributors() {
        TestSubscriber<List<Contributor>> testSubscriber = new TestSubscriber<>();
        databaseHelper.getContributors().subscribe(testSubscriber);
        testSubscriber.assertNoErrors();
        testSubscriber.assertValueCount(1);

        List<Contributor> actual = testSubscriber.getOnNextEvents().get(0);
        assertEquals(11, actual.size());
        assertEquals(471318, actual.get(0).getId());
        assertEquals("hotchemi", actual.get(0).getLogin());
        assertEquals("https://avatars.githubusercontent.com/u/471318?v=3", actual.get(0).getAvatar_url());
        assertEquals("https://github.com/hotchemi", actual.get(0).getHtml_url());
    }

    @Test
    public void testClearRepositoryTable(){
        databaseHelper.clearRepositoryTable();
        assertEquals(0, SQLite.select().from(Repository.class).count());
    }

    @Test
    public void testClearBranchTable() {
        databaseHelper.clearBranchTable();
        assertEquals(0, SQLite.select().from(Branch.class).count());
    }

    @Test
    public void testClearContributorTable() {
        databaseHelper.getContributors();
        assertEquals(0, SQLite.select().from(Contributor.class).count());
    }

    @After
    public void tearDown() throws Exception {
        FlowManager.destroy();
    }
}
