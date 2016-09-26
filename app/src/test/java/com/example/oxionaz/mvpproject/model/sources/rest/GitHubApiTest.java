package com.example.oxionaz.mvpproject.model.sources.rest;

import com.example.oxionaz.mvpproject.model.sources.rest.api.GitHubAPI;
import com.example.oxionaz.mvpproject.model.sources.rest.dto.BranchDTO;
import com.example.oxionaz.mvpproject.model.sources.rest.dto.ContributorDTO;
import com.example.oxionaz.mvpproject.model.sources.rest.dto.RepositoryDTO;
import com.example.oxionaz.mvpproject.other.BaseTest;
import com.example.oxionaz.mvpproject.other.Const;
import com.example.oxionaz.mvpproject.other.TestConst;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import java.util.List;
import okhttp3.HttpUrl;
import okhttp3.mockwebserver.Dispatcher;
import okhttp3.mockwebserver.MockResponse;
import okhttp3.mockwebserver.MockWebServer;
import okhttp3.mockwebserver.RecordedRequest;
import rx.observers.TestSubscriber;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

public class GitHubApiTest extends BaseTest {

    private MockWebServer mockWebServer;
    private GitHubAPI gitHubAPI;

    @Before
    public void setUp() throws Exception{
        super.setUp();
        mockWebServer = new MockWebServer();
        mockWebServer.start();
        final Dispatcher dispatcher = new Dispatcher() {

            @Override
            public MockResponse dispatch(RecordedRequest request) throws InterruptedException {
                switch (request.getPath()) {
                    case "/users/" + TestConst.TEST_OWNER + "/repos":
                        return new MockResponse().setResponseCode(200)
                                .setBody(gsonUtils.readString("json/repos.json"));
                    case "/repos/" + TestConst.TEST_OWNER + "/" + TestConst.TEST_REPO + "/branches":
                        return new MockResponse().setResponseCode(200)
                                .setBody(gsonUtils.readString("json/branches.json"));
                    case "/repos/" + TestConst.TEST_OWNER + "/" + TestConst.TEST_REPO + "/contributors":
                        return new MockResponse().setResponseCode(200)
                                .setBody(gsonUtils.readString("json/contributors.json"));
                }
                return new MockResponse().setResponseCode(404);
            }
        };
        mockWebServer.setDispatcher(dispatcher);
        HttpUrl testUrl = mockWebServer.url(Const.TEST_URL);
        gitHubAPI = new RestClient(testUrl.toString()).getGitHubAPI();
    }

    @Test
    public void testGetRepositories() throws Exception {
        TestSubscriber<List<RepositoryDTO>> testSubscriber = new TestSubscriber<>();
        gitHubAPI.getRepositories(TestConst.TEST_OWNER).subscribe(testSubscriber);

        testSubscriber.assertNoErrors();
        testSubscriber.assertValueCount(1);

        List<RepositoryDTO> actual = testSubscriber.getOnNextEvents().get(0);

        assertEquals(7, actual.size());
        assertEquals("Android-Rate", actual.get(0).getName());
        assertEquals("andrey7mel/Android-Rate", actual.get(0).getFullName());
        assertEquals(26314692, (int) actual.get(0).getId());
    }

    @Test
    public void testGetRepositoriesIncorrect() throws Exception {
        try {
            gitHubAPI.getRepositories("IncorrectRequest").subscribe();
            fail();
        } catch (Exception expected) {
            assertEquals(TestConst.ERROR_RESPONSE_404, expected.getMessage());
        }
    }

    @Test
    public void testGetContributors() {
        TestSubscriber<List<ContributorDTO>> testSubscriber = new TestSubscriber<>();
        gitHubAPI.getContributors(TestConst.TEST_OWNER, TestConst.TEST_REPO).subscribe(testSubscriber);

        testSubscriber.assertNoErrors();
        testSubscriber.assertValueCount(1);

        List<ContributorDTO> actual = testSubscriber.getOnNextEvents().get(0);

        assertEquals(11, actual.size());
        assertEquals("hotchemi", actual.get(0).getLogin());
        assertEquals("User", actual.get(0).getType());
        assertEquals(471318, (int) actual.get(0).getId());
    }

    @Test
    public void testGetContributorsIncorrect() throws Exception {
        try {
            gitHubAPI.getContributors("BBB", "AAA").subscribe();
            fail();
        } catch (Exception expected) {
            assertEquals(TestConst.ERROR_RESPONSE_404, expected.getMessage());
        }
    }


    @Test
    public void testGetBranches() {
        TestSubscriber<List<BranchDTO>> testSubscriber = new TestSubscriber<>();
        gitHubAPI.getBranches(TestConst.TEST_OWNER, TestConst.TEST_REPO).subscribe(testSubscriber);

        testSubscriber.assertNoErrors();
        testSubscriber.assertValueCount(1);

        List<BranchDTO> actual = testSubscriber.getOnNextEvents().get(0);

        assertEquals(3, actual.size());
        assertEquals("QuickStart", actual.get(0).getName());
        assertEquals("94870e23f1cfafe7201bf82985b61188f650b245", actual.get(0).getCommitDTO().getSha());
    }

    @Test
    public void testGetBranchesIncorrect() throws Exception {
        try {
            gitHubAPI.getContributors("A", "B").subscribe();
            fail();
        } catch (Exception expected) {
            assertEquals(TestConst.ERROR_RESPONSE_404, expected.getMessage());
        }
    }

    @After
    public void tearDown() throws Exception {
        mockWebServer.shutdown();
    }
}
