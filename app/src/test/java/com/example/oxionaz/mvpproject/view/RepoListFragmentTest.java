package com.example.oxionaz.mvpproject.view;

import android.view.LayoutInflater;
import android.view.ViewGroup;
import com.example.oxionaz.mvpproject.R;
import com.example.oxionaz.mvpproject.other.BaseTest;
import com.example.oxionaz.mvpproject.presenter.RepoListPresenter;
import com.example.oxionaz.mvpproject.view.ui.activities.ActivityCallback;
import com.example.oxionaz.mvpproject.view.ui.activities.MainActivity;
import com.example.oxionaz.mvpproject.view.ui.fragments.RepoListFragment;
import com.example.oxionaz.mvpproject.view.ui.fragments.vh.RepoListVH;
import org.junit.Before;
import org.junit.Test;
import org.robolectric.Robolectric;
import javax.inject.Inject;

import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.stub;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

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

        repoListFragment = new RepoListFragment();
        mainActivity = Robolectric.setupActivity(MainActivity.class);
        repoListFragment.onCreate(null);
    }

    @Test
    public void testOnCreateView(){
        repoListFragment.onCreateView(LayoutInflater.from(mainActivity),
                (ViewGroup) mainActivity.findViewById(R.id.main_frame), null);
        verify(repoListPresenter).onCreate(repoListFragment, repoListFragment);
        verify(repoListPresenter).getRepoFromCash();
    }
}
