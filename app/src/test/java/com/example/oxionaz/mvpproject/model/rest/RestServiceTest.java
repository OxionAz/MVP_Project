package com.example.oxionaz.mvpproject.model.rest;

import com.example.oxionaz.mvpproject.model.sources.rest.Rest;
import com.example.oxionaz.mvpproject.model.sources.rest.RestClient;
import com.example.oxionaz.mvpproject.model.sources.rest.RestService;
import com.example.oxionaz.mvpproject.model.sources.rest.api.GitHubAPI;
import com.example.oxionaz.mvpproject.model.sources.rest.dto.RepositoryDTO;
import com.example.oxionaz.mvpproject.other.BaseTest;
import com.example.oxionaz.mvpproject.other.TestConst;

import org.junit.Before;
import org.junit.Test;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.stubbing.Answer;

import java.util.Arrays;
import java.util.List;

import javax.inject.Inject;

import rx.Observable;
import rx.observers.TestSubscriber;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.doAnswer;
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
    }

    @Test
    public void testDownloadRepositoryList(){
        RepositoryDTO[] repositoryDTOs = testUtils.getGson()
                .fromJson(testUtils.readString("json/repos.json"), RepositoryDTO[].class);

        when(gitHubAPI.getRepositories(TestConst.TEST_OWNER))
                .thenReturn(Observable.just(Arrays.asList(repositoryDTOs)));

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

}
