package com.example.oxionaz.mvpproject.model;

import com.example.oxionaz.mvpproject.model.sources.db.DatabaseHelper;
import com.example.oxionaz.mvpproject.model.sources.db.models.Branch;
import com.example.oxionaz.mvpproject.model.sources.db.models.Contributor;
import com.example.oxionaz.mvpproject.model.sources.db.models.Repository;
import com.example.oxionaz.mvpproject.model.sources.rest.RestService;
import com.example.oxionaz.mvpproject.other.BaseTest;
import com.example.oxionaz.mvpproject.other.EventBus;
import com.example.oxionaz.mvpproject.other.TestConst;
import org.junit.Before;
import org.junit.Test;
import java.util.List;
import javax.inject.Inject;
import rx.Observable;
import rx.observers.TestObserver;
import rx.observers.TestSubscriber;
import static org.junit.Assert.assertEquals;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class DataManagerTest extends BaseTest {

    private DataManager dataManager;

    @Inject
    protected EventBus eventBus;

    @Inject
    protected RestService restService;

    @Inject
    protected DatabaseHelper databaseHelper;

    @Before
    public void setUp() throws Exception{
        super.setUp();
        component.inject(this);

        dataManager = new DataManager();
        dataManager.onCreate(eventBus);

        when(restService.downloadRepositoryList(TestConst.TEST_OWNER))
                .thenReturn(Observable.just(repositoryDTOList));
        when(restService.downloadBranchList(TestConst.TEST_OWNER, TestConst.TEST_REPO))
                .thenReturn(Observable.just(branchDTOList));
        when(restService.downloadContributorList(TestConst.TEST_OWNER, TestConst.TEST_REPO))
                .thenReturn(Observable.just(contributorDTOList));

        when(databaseHelper.getRepositories())
                .thenReturn(Observable.just(repositoryList));
        when(databaseHelper.getBranches())
                .thenReturn(Observable.just(branchList));
        when(databaseHelper.getContributors())
                .thenReturn(Observable.just(contributorList));
    }

    @Test
    public void testDownloadRepositories(){
        TestSubscriber<List<Repository>> testSubscriber = new TestSubscriber<>();
        dataManager.downloadRepositories(TestConst.TEST_OWNER).subscribe(testSubscriber);
        testSubscriber.assertNoErrors();
        testSubscriber.assertValueCount(1);

        List<Repository> actual = testSubscriber.getOnNextEvents().get(0);
        assertEquals(7, actual.size());
        assertEquals(26314692, actual.get(0).getId());
        assertEquals("Android-Rate", actual.get(0).getName());
        assertEquals("andrey7mel", actual.get(0).getOwner());
        assertEquals(null, actual.get(0).getLanguage());
        assertEquals("https://github.com/andrey7mel/Android-Rate", actual.get(0).getHtml_url());

        verify(databaseHelper).saveRepositories(actual);
    }

    @Test
    public void testDownloadRepositoriesOnNull(){
        when(restService.downloadRepositoryList(TestConst.TEST_OWNER))
                .thenReturn(Observable.just(null));

        TestSubscriber<List<Repository>> testSubscriber = new TestSubscriber<>();
        dataManager.downloadRepositories(TestConst.TEST_OWNER).subscribe(testSubscriber);
        testSubscriber.assertNoValues();

        verify(databaseHelper, never()).saveRepositories(any());
    }

    @Test
    public void testDownloadRepositoriesOnError(){
        Throwable throwable = new Throwable(TestConst.TEST_ERROR);
        when(restService.downloadRepositoryList(TestConst.TEST_OWNER))
                .thenReturn(Observable.error(throwable));

        TestSubscriber<List<Repository>> testSubscriber = new TestSubscriber<>();
        dataManager.downloadRepositories(TestConst.TEST_OWNER).subscribe(testSubscriber);

        List<Repository> actual = testSubscriber.getOnNextEvents().get(0);
        assertEquals(true, actual.isEmpty());

        verify(eventBus).onDownloadError(throwable);
    }

    @Test
    public void testDownloadBranches(){
        TestSubscriber<List<Branch>> testSubscriber = new TestSubscriber<>();
        dataManager.downloadBranches(TestConst.TEST_OWNER, TestConst.TEST_REPO).subscribe(testSubscriber);
        testSubscriber.assertNoErrors();
        testSubscriber.assertValueCount(1);

        List<Branch> actual = testSubscriber.getOnNextEvents().get(0);
        assertEquals(3, actual.size());
        assertEquals("QuickStart", actual.get(0).getName());
        assertEquals("94870e23f1cfafe7201bf82985b61188f650b245", actual.get(0).getSha());
        assertEquals("https://api.github.com/repos/andrey7mel/Android-Rate/commits/94870e23f1cfafe7201bf82985b61188f650b245", actual.get(0).getUrl());

        verify(databaseHelper).saveBranches(actual);
    }

    @Test
    public void testDownloadBranchesOnNull(){
        when(restService.downloadBranchList(TestConst.TEST_OWNER, TestConst.TEST_REPO))
                .thenReturn(Observable.just(null));

        TestSubscriber<List<Branch>> testSubscriber = new TestSubscriber<>();
        dataManager.downloadBranches(TestConst.TEST_OWNER, TestConst.TEST_REPO).subscribe(testSubscriber);
        testSubscriber.assertNoValues();

        verify(databaseHelper, never()).saveBranches(any());
    }

    @Test
    public void testDownloadBranchesOnError(){
        Throwable throwable = new Throwable(TestConst.TEST_ERROR);
        when(restService.downloadBranchList(TestConst.TEST_OWNER, TestConst.TEST_REPO))
                .thenReturn(Observable.error(throwable));

        TestSubscriber<List<Branch>> testSubscriber = new TestSubscriber<>();
        dataManager.downloadBranches(TestConst.TEST_OWNER, TestConst.TEST_REPO).subscribe(testSubscriber);

        List<Branch> actual = testSubscriber.getOnNextEvents().get(0);
        assertEquals(true, actual.isEmpty());

        verify(eventBus).onDownloadError(throwable);
    }

    @Test
    public void testDownloadContributors(){
        TestSubscriber<List<Contributor>> testSubscriber = new TestSubscriber<>();
        dataManager.downloadContributors(TestConst.TEST_OWNER, TestConst.TEST_REPO).subscribe(testSubscriber);
        testSubscriber.assertNoErrors();
        testSubscriber.assertValueCount(1);

        List<Contributor> actual = testSubscriber.getOnNextEvents().get(0);
        assertEquals(11, actual.size());
        assertEquals(471318, actual.get(0).getId());
        assertEquals("hotchemi", actual.get(0).getLogin());
        assertEquals("https://avatars.githubusercontent.com/u/471318?v=3", actual.get(0).getAvatar_url());
        assertEquals("https://github.com/hotchemi", actual.get(0).getHtml_url());

        verify(databaseHelper).saveContributors(actual);
    }

    @Test
    public void testDownloadContributorsOnNull(){
        when(restService.downloadContributorList(TestConst.TEST_OWNER, TestConst.TEST_REPO))
                .thenReturn(Observable.just(null));

        TestSubscriber<List<Contributor>> testSubscriber = new TestSubscriber<>();
        dataManager.downloadContributors(TestConst.TEST_OWNER, TestConst.TEST_REPO).subscribe(testSubscriber);
        testSubscriber.assertNoValues();

        verify(databaseHelper, never()).saveContributors(any());
    }

    @Test
    public void testDownloadContributorsOnError(){
        Throwable throwable = new Throwable(TestConst.TEST_ERROR);
        when(restService.downloadContributorList(TestConst.TEST_OWNER, TestConst.TEST_REPO))
                .thenReturn(Observable.error(throwable));

        TestSubscriber<List<Contributor>> testSubscriber = new TestSubscriber<>();
        dataManager.downloadContributors(TestConst.TEST_OWNER, TestConst.TEST_REPO).subscribe(testSubscriber);

        List<Contributor> actual = testSubscriber.getOnNextEvents().get(0);
        assertEquals(true, actual.isEmpty());

        verify(eventBus).onDownloadError(throwable);
    }

    @Test
    public void testGetRepositoriesFromCash() {
        TestSubscriber<List<Repository>> testSubscriber = new TestSubscriber<>();
        dataManager.getRepositoriesFromCash().subscribe(testSubscriber);
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
    public void testGetRepositoriesFromCashOnError() {
        Throwable throwable = new Throwable(TestConst.TEST_ERROR);
        when(databaseHelper.getRepositories())
                .thenReturn(Observable.error(throwable));

        TestSubscriber<List<Repository>> testSubscriber = new TestSubscriber<>();
        dataManager.getRepositoriesFromCash().subscribe(testSubscriber);
        testSubscriber.assertError(throwable);
        testSubscriber.assertNoValues();

        verify(eventBus).onCashError(throwable);
    }

    @Test
    public void testGetBranchesFromCash() {
        TestSubscriber<List<Branch>> testSubscriber = new TestSubscriber<>();
        dataManager.getBranchesFromCash().subscribe(testSubscriber);
        testSubscriber.assertNoErrors();
        testSubscriber.assertValueCount(1);

        List<Branch> actual = testSubscriber.getOnNextEvents().get(0);
        assertEquals(3, actual.size());
        assertEquals("QuickStart", actual.get(0).getName());
        assertEquals("94870e23f1cfafe7201bf82985b61188f650b245", actual.get(0).getSha());
        assertEquals("https://api.github.com/repos/andrey7mel/Android-Rate/commits/94870e23f1cfafe7201bf82985b61188f650b245", actual.get(0).getUrl());
    }

    @Test
    public void testGetBranchesFromCashOnError() {
        Throwable throwable = new Throwable(TestConst.TEST_ERROR);
        when(databaseHelper.getBranches())
                .thenReturn(Observable.error(throwable));

        TestSubscriber<List<Branch>> testSubscriber = new TestSubscriber<>();
        dataManager.getBranchesFromCash().subscribe(testSubscriber);
        testSubscriber.assertError(throwable);
        testSubscriber.assertNoValues();

        verify(eventBus).onCashError(throwable);
    }

    @Test
    public void testGetContributorsFromCash() {
        TestSubscriber<List<Contributor>> testSubscriber = new TestSubscriber<>();
        dataManager.getContributorsFromCash().subscribe(testSubscriber);
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
    public void testGetContributorsFromCashOnError() {
        Throwable throwable = new Throwable(TestConst.TEST_ERROR);
        when(databaseHelper.getContributors())
                .thenReturn(Observable.error(throwable));

        TestSubscriber<List<Contributor>> testSubscriber = new TestSubscriber<>();
        dataManager.getContributorsFromCash().subscribe(testSubscriber);
        testSubscriber.assertError(throwable);
        testSubscriber.assertNoValues();

        verify(eventBus).onCashError(throwable);
    }

    @Test
    public void testClearRepositoryCash() {
        dataManager.clearRepositoryCash();
        verify(databaseHelper).clearRepositoryTable();
    }

    @Test
    public void testClearBranchesCash() {
        dataManager.clearBranchesCash();
        verify(databaseHelper).clearBranchTable();
    }

    @Test
    public void testClearContributorsCash() {
        dataManager.clearContributorsCash();
        verify(databaseHelper).clearContributorTable();
    }
}
