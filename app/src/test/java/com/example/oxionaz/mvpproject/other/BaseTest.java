package com.example.oxionaz.mvpproject.other;

import com.example.oxionaz.mvpproject.BuildConfig;
import com.example.oxionaz.mvpproject.model.sources.db.models.Branch;
import com.example.oxionaz.mvpproject.model.sources.db.models.Contributor;
import com.example.oxionaz.mvpproject.model.sources.db.models.Repository;
import com.example.oxionaz.mvpproject.model.sources.rest.dto.BranchDTO;
import com.example.oxionaz.mvpproject.model.sources.rest.dto.ContributorDTO;
import com.example.oxionaz.mvpproject.model.sources.rest.dto.RepositoryDTO;
import com.example.oxionaz.mvpproject.other.di.TestComponent;
import com.example.oxionaz.mvpproject.other.util.GsonUtils;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.runner.RunWith;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.annotation.Config;
import java.util.List;
import javax.inject.Inject;

@RunWith(RobolectricTestRunner.class)
@Config(application = TestApp.class,
        constants = BuildConfig.class,
        sdk = 23)
@Ignore
public class BaseTest {

    public TestComponent component;

    @Inject
    protected GsonUtils gsonUtils;

    @Inject
    protected List<RepositoryDTO> repositoryDTOList;

    @Inject
    protected List<BranchDTO> branchDTOList;

    @Inject
    protected List<ContributorDTO> contributorDTOList;

    @Inject
    protected List<Repository> repositoryList;

    @Inject
    protected List<Branch> branchList;

    @Inject
    protected List<Contributor> contributorList;

    @Before
    public void setUp() throws Exception {
        component = (TestComponent) App.getAppComponent();
        component.inject(this);
    }
}
