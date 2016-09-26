package com.example.oxionaz.mvpproject.model.sources.rest;

import com.example.oxionaz.mvpproject.model.sources.rest.api.GitHubAPI;
import com.example.oxionaz.mvpproject.model.sources.rest.dto.BranchDTO;
import com.example.oxionaz.mvpproject.model.sources.rest.dto.ContributorDTO;
import com.example.oxionaz.mvpproject.model.sources.rest.dto.RepositoryDTO;
import com.example.oxionaz.mvpproject.other.BaseTest;
import com.example.oxionaz.mvpproject.other.TestConst;

import org.junit.Before;
import org.junit.Test;

import java.util.List;

import javax.inject.Inject;

import rx.Observable;
import rx.observers.TestSubscriber;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.when;

public class RestServiceTest extends BaseTest {

    private Rest rest;

    @Inject
    protected GitHubAPI gitHubAPI;

    @Before
    public void setUp() throws Exception {
        super.setUp();
        component.inject(this);
        rest = new RestService();

        when(gitHubAPI.getRepositories(TestConst.TEST_OWNER))
                .thenReturn(Observable.just(repositoryDTOList));
        when(gitHubAPI.getBranches(TestConst.TEST_OWNER, TestConst.TEST_REPO))
                .thenReturn(Observable.just(branchDTOList));
        when(gitHubAPI.getContributors(TestConst.TEST_OWNER, TestConst.TEST_REPO))
                .thenReturn(Observable.just(contributorDTOList));
    }

    @Test
    public void testDownloadRepositoryList(){
        TestSubscriber<List<RepositoryDTO>> testSubscriber = new TestSubscriber<>();
        rest.downloadRepositoryList(TestConst.TEST_OWNER).subscribe(testSubscriber);

        testSubscriber.assertNoErrors();
        testSubscriber.assertValueCount(1);

        List<RepositoryDTO> actual = testSubscriber.getOnNextEvents().get(0);

        assertEquals(7, actual.size());
        assertEquals("Android-Rate", actual.get(0).getName());
        assertEquals("andrey7mel/Android-Rate", actual.get(0).getFullName());
        assertEquals(26314692, (int) actual.get(0).getId());
    }

    @Test
    public void testDownloadBranchList() {
        TestSubscriber<List<BranchDTO>> testSubscriber = new TestSubscriber<>();
        rest.downloadBranchList(TestConst.TEST_OWNER, TestConst.TEST_REPO).subscribe(testSubscriber);

        testSubscriber.assertNoErrors();
        testSubscriber.assertValueCount(1);

        List<BranchDTO> actual = testSubscriber.getOnNextEvents().get(0);

        assertEquals(3, actual.size());
        assertEquals("QuickStart", actual.get(0).getName());
        assertEquals("94870e23f1cfafe7201bf82985b61188f650b245", actual.get(0).getCommitDTO().getSha());
    }

    @Test
    public void testDownloadContributorList() {
        TestSubscriber<List<ContributorDTO>> testSubscriber = new TestSubscriber<>();
        rest.downloadContributorList(TestConst.TEST_OWNER, TestConst.TEST_REPO).subscribe(testSubscriber);

        testSubscriber.assertNoErrors();
        testSubscriber.assertValueCount(1);

        List<ContributorDTO> actual = testSubscriber.getOnNextEvents().get(0);

        assertEquals(11, actual.size());
        assertEquals("hotchemi", actual.get(0).getLogin());
        assertEquals("User", actual.get(0).getType());
        assertEquals(471318, (int) actual.get(0).getId());
    }
}
