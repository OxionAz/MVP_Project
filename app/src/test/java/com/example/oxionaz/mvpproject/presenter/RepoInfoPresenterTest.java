package com.example.oxionaz.mvpproject.presenter;

import com.example.oxionaz.mvpproject.model.DataManager;
import com.example.oxionaz.mvpproject.other.BaseTest;
import com.example.oxionaz.mvpproject.other.EventBus;
import com.example.oxionaz.mvpproject.other.TestConst;
import com.example.oxionaz.mvpproject.view.ui.fragments.RepoInfoFragmentView;
import org.junit.Before;
import org.junit.Test;
import org.mockito.ArgumentCaptor;
import java.util.Collections;
import java.util.List;
import javax.inject.Inject;
import rx.Observable;
import rx.Subscription;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.spy;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class RepoInfoPresenterTest extends BaseTest {

    private RepoInfoPresenter repoInfoPresenter;

    @Inject
    DataManager dataManager;

    @Inject
    EventBus eventBus;

    @Inject
    RepoInfoFragmentView repoInfoFragmentView;

    @Before
    public void setUp() throws Exception {
        super.setUp();
        component.inject(this);

        repoInfoPresenter = new RepoInfoPresenter();
        repoInfoPresenter.onCreate(repoInfoFragmentView, eventBus);
        dataManager.onCreate(eventBus);

        when(dataManager.downloadBranches(TestConst.TEST_OWNER, TestConst.TEST_REPO))
                .thenReturn(Observable.just(branchList));
        when(dataManager.getBranchesFromCash())
                .thenReturn(Observable.just(branchList));
        when(dataManager.downloadContributors(TestConst.TEST_OWNER, TestConst.TEST_REPO))
                .thenReturn(Observable.just(contributorList));
        when(dataManager.getContributorsFromCash())
                .thenReturn(Observable.just(contributorList));
    }

    @Test
    public void testDownloadInfo(){
        repoInfoPresenter.downloadInfo(TestConst.TEST_OWNER, TestConst.TEST_REPO);

        verify(repoInfoFragmentView).showBranches(branchList);
        verify(repoInfoFragmentView).showContributors(contributorList);
    }

    @Test
    public void testDownloadInfoOnNull(){
        when(dataManager.downloadBranches(TestConst.TEST_OWNER, TestConst.TEST_REPO))
                .thenReturn(Observable.just(null));
        when(dataManager.downloadContributors(TestConst.TEST_OWNER, TestConst.TEST_REPO))
                .thenReturn(Observable.just(null));
        repoInfoPresenter.downloadInfo(TestConst.TEST_OWNER, TestConst.TEST_REPO);

        verify(repoInfoFragmentView, never()).showBranches(branchList);
        verify(repoInfoFragmentView, never()).showContributors(contributorList);
    }

    @Test
    public void testDownloadInfoOnEmpty(){
        when(dataManager.downloadBranches(TestConst.TEST_OWNER, TestConst.TEST_REPO))
                .thenReturn(Observable.just(Collections.emptyList()));
        when(dataManager.downloadContributors(TestConst.TEST_OWNER, TestConst.TEST_REPO))
                .thenReturn(Observable.just(Collections.emptyList()));
        repoInfoPresenter.downloadInfo(TestConst.TEST_OWNER, TestConst.TEST_REPO);

        verify(repoInfoFragmentView, never()).showBranches(branchList);
        verify(repoInfoFragmentView, never()).showContributors(contributorList);
    }

    @Test
    public void testGetInfoFromCash(){
        repoInfoPresenter.getInfoFromCash();

        verify(repoInfoFragmentView).showBranches(branchList);
        verify(repoInfoFragmentView).showContributors(contributorList);
    }

    @Test
    public void testGetInfoFromCashOnNull(){
        when(dataManager.getBranchesFromCash())
                .thenReturn(Observable.just(null));
        when(dataManager.getContributorsFromCash())
                .thenReturn(Observable.just(null));
        repoInfoPresenter.getInfoFromCash();

        verify(repoInfoFragmentView, never()).showBranches(branchList);
        verify(repoInfoFragmentView, never()).showContributors(contributorList);
    }

    @Test
    public void testGetInfoFromCashOnEmpty(){
        when(dataManager.getBranchesFromCash())
                .thenReturn(Observable.just(Collections.emptyList()));
        when(dataManager.getContributorsFromCash())
                .thenReturn(Observable.just(Collections.emptyList()));
        repoInfoPresenter.getInfoFromCash();

        verify(repoInfoFragmentView, never()).showBranches(branchList);
        verify(repoInfoFragmentView, never()).showContributors(contributorList);
    }

    @Test
    public void testSubscribe() {
        repoInfoPresenter = spy(new RepoInfoPresenter()); //for ArgumentCaptor
        repoInfoPresenter.onCreate(repoInfoFragmentView, eventBus);
        repoInfoPresenter.downloadInfo(TestConst.TEST_OWNER, TestConst.TEST_REPO);
        repoInfoPresenter.onStop();

        ArgumentCaptor<Subscription> captor = ArgumentCaptor.forClass(Subscription.class);
        verify(repoInfoPresenter, times(2)).addSubscription(captor.capture());
        List<Subscription> subscriptions = captor.getAllValues();
        assertEquals(2, subscriptions.size());
        assertTrue(subscriptions.get(0).isUnsubscribed());
        assertTrue(subscriptions.get(1).isUnsubscribed());
    }
}
