package com.example.oxionaz.mvpproject.view;

import com.example.oxionaz.mvpproject.other.BaseTest;
import com.example.oxionaz.mvpproject.presenter.RepoListPresenter;
import com.example.oxionaz.mvpproject.view.ui.activities.ActivityCallback;
import com.example.oxionaz.mvpproject.view.ui.activities.MainActivity;
import com.example.oxionaz.mvpproject.view.ui.activities.MainActivity_;
import com.example.oxionaz.mvpproject.view.ui.fragments.RepoListFragment;
import com.example.oxionaz.mvpproject.view.ui.fragments.RepoListFragment_;
import com.example.oxionaz.mvpproject.view.ui.fragments.vh.RepoListVH;
import org.junit.Before;
import org.junit.Test;
import org.robolectric.Robolectric;
import org.robolectric.Shadows;
import javax.inject.Inject;
import static org.mockito.Mockito.verify;

public class RepoListFragmentTest extends BaseTest {

    private RepoListFragment repoListFragment;
    private MainActivity mainActivity;

    @Inject
    RepoListVH repoListVH;

    @Inject
    ActivityCallback activityCallback;

    @Inject
    RepoListPresenter repoListPresenter;

    @Override
    @Before
    public void setUp() throws Exception {
        super.setUp();
        component.inject(this);

        repoListFragment = new RepoListFragment_();
        mainActivity = Robolectric.setupActivity(MainActivity_.class);
        repoListFragment.onCreate(null);
    }

    @Test
    public void testOnCreateOptionsMenu(){
        repoListFragment.onCreateOptionsMenu(Shadows.shadowOf(mainActivity).getOptionsMenu(), mainActivity.getMenuInflater());
        verify(repoListPresenter).onCreate(repoListFragment, repoListFragment);
        verify(repoListPresenter).getRepoFromCash();
    }
}
