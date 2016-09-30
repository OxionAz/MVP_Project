package com.example.oxionaz.mvpproject.model.sources;

import com.example.oxionaz.mvpproject.model.sources.db.models.Branch;
import com.example.oxionaz.mvpproject.model.sources.db.models.Contributor;
import com.example.oxionaz.mvpproject.model.sources.db.models.Repository;
import com.example.oxionaz.mvpproject.other.BaseTest;
import org.junit.Before;
import org.junit.Test;
import java.util.List;
import rx.Observable;
import rx.observers.TestSubscriber;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

public class MapperDTOTest extends BaseTest {

    @Before
    public void setUp() throws Exception {
        super.setUp();
        component.inject(this);
    }

    @Test
    public void testMapRepo(){
        TestSubscriber<List<Repository>> testSubscriber = new TestSubscriber<>();
        Observable.from(repositoryDTOList).map(MapperDTO::mapRepo).toList().subscribe(testSubscriber);
        testSubscriber.assertNoErrors();
        testSubscriber.assertValueCount(1);

        List<Repository> actual = testSubscriber.getOnNextEvents().get(0);
        assertEquals(7, actual.size());
        assertEquals("Android-Rate", actual.get(0).getName());
        assertEquals("andrey7mel", actual.get(0).getOwner());
        assertEquals("utils", repositoryList.get(6).getName());
        assertEquals("andrey7mel", repositoryList.get(6).getOwner());
    }

    @Test
    public void testMapRepoOnNull(){
        Repository repository = MapperDTO.mapRepo(null);
        assertNull(repository);
    }

    @Test
    public void testMapBranch(){
        TestSubscriber<List<Branch>> testSubscriber = new TestSubscriber<>();
        Observable.from(branchDTOList).map(MapperDTO::mapBranch).toList().subscribe(testSubscriber);
        testSubscriber.assertNoErrors();
        testSubscriber.assertValueCount(1);

        List<Branch> actual = testSubscriber.getOnNextEvents().get(0);
        assertEquals(3, actual.size());
        assertEquals("QuickStart", branchList.get(0).getName());
        assertEquals("gh-pages", branchList.get(1).getName());
        assertEquals("master", branchList.get(2).getName());
    }

    @Test
    public void testMapBranchOnNull(){
        Branch branch = MapperDTO.mapBranch(null);
        assertNull(branch);
    }

    @Test
    public void testMapContrib(){
        TestSubscriber<List<Contributor>> testSubscriber = new TestSubscriber<>();
        Observable.from(contributorDTOList).map(MapperDTO::mapContrib).toList().subscribe(testSubscriber);
        testSubscriber.assertNoErrors();
        testSubscriber.assertValueCount(1);

        List<Contributor> actual = testSubscriber.getOnNextEvents().get(0);
        assertEquals(11, actual.size());
        assertEquals("hotchemi", contributorList.get(0).getLogin());
        assertEquals("mrmike", contributorList.get(1).getLogin());
        assertEquals("amitkot", contributorList.get(2).getLogin());
        assertEquals("maarekj", contributorList.get(10).getLogin());
    }

    @Test
    public void testMapContribOnNull(){
        Contributor contributor = MapperDTO.mapContrib(null);
        assertNull(contributor);
    }
}
