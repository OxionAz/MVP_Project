package com.example.oxionaz.mvpproject.presenter;

import com.example.oxionaz.mvpproject.model.DataManager;
import com.example.oxionaz.mvpproject.other.BaseTest;
import com.example.oxionaz.mvpproject.other.EventBus;
import com.example.oxionaz.mvpproject.other.TestConst;
import com.example.oxionaz.mvpproject.view.ui.fragments.RepoListFragmentView;
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
import static org.junit.Assert.fail;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.spy;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class RepoListPresenterTest extends BaseTest {

    private RepoListPresenter repoListPresenter;

    @Inject
    DataManager dataManager;

    @Inject
    EventBus eventBus;

    @Inject
    RepoListFragmentView repoListFragmentView;

    @Before
    public void setUp() throws Exception {
        super.setUp();
        component.inject(this);

        repoListPresenter = new RepoListPresenter();
        repoListPresenter.onCreate(repoListFragmentView, eventBus);
        dataManager.onCreate(eventBus);

        when(dataManager.downloadRepositories(TestConst.TEST_OWNER))
                .thenReturn(Observable.just(repositoryList));
        when(dataManager.getRepositoriesFromCash())
                .thenReturn(Observable.just(repositoryList));
        when(repoListFragmentView.getUserName())
                .thenReturn(TestConst.TEST_OWNER);
    }

    @Test
    public void testClearRepoCash() {
        repoListPresenter.clearRepoCash();
        verify(dataManager).clearRepositoryCash();
    }

    @Test
    public void testGetRepoFromCash() {
        repoListPresenter.getRepoFromCash();
        verify(repoListFragmentView).showRepoList(repositoryList);
    }

    @Test
    public void testGetRepoFromCashOnNull() {
        when(dataManager.getRepositoriesFromCash())
                .thenReturn(Observable.just(null));
        repoListPresenter.getRepoFromCash();

        verify(repoListFragmentView, never()).showRepoList(any());
    }

    @Test
    public void testGetRepoFromCashOnEmpty() {
        when(dataManager.getRepositoriesFromCash())
                .thenReturn(Observable.just(Collections.emptyList()));
        repoListPresenter.getRepoFromCash();

        verify(repoListFragmentView, never()).showRepoList(any());
    }

    @Test
    public void testOnSearchClick() {
        repoListPresenter.onSearchClick();

        verify(repoListFragmentView).showLoading(true);
        verify(repoListFragmentView).showRepoList(repositoryList);
        verify(repoListFragmentView).showLoading(false);
    }

    @Test
    public void testOnSearchClickOnNull() {
        when(dataManager.downloadRepositories(TestConst.TEST_OWNER))
                .thenReturn(Observable.just(null));
        repoListPresenter.onSearchClick();

        verify(repoListFragmentView).showLoading(true);
        verify(repoListFragmentView, never()).showRepoList(any());
    }

    @Test
    public void testOnSearchClickOnEmpty() {
        when(dataManager.downloadRepositories(TestConst.TEST_OWNER))
                .thenReturn(Observable.just(Collections.emptyList()));
        repoListPresenter.onSearchClick();

        verify(repoListFragmentView).showLoading(true);
        verify(repoListFragmentView, never()).showRepoList(any());
    }

    @Test
    public void testOnSearchClickEmptyName() throws Exception {
        when(repoListFragmentView.getUserName())
                .thenReturn("");

        try {
            repoListPresenter.onSearchClick();
            fail();
        } catch (Exception e) {
            assertEquals(NullPointerException.class, e.getClass());
        }
    }

    @Test
    public void testSubscribe() {
        repoListPresenter = spy(new RepoListPresenter());
        repoListPresenter.onCreate(repoListFragmentView, eventBus);
        repoListPresenter.onSearchClick();
        repoListPresenter.onStop();

        ArgumentCaptor<Subscription> captor = ArgumentCaptor.forClass(Subscription.class);
        verify(repoListPresenter).addSubscription(captor.capture());
        List<Subscription> subscriptions = captor.getAllValues();
        assertEquals(1, subscriptions.size());
        assertTrue(subscriptions.get(0).isUnsubscribed());
    }
}
