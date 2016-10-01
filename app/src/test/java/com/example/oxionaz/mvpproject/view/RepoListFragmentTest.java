package com.example.oxionaz.mvpproject.view;

import android.support.v7.view.menu.MenuBuilder;
import android.view.Menu;
import android.view.MenuInflater;

import com.example.oxionaz.mvpproject.R;
import com.example.oxionaz.mvpproject.other.BaseTest;
import com.example.oxionaz.mvpproject.other.EventBus;
import com.example.oxionaz.mvpproject.presenter.RepoListPresenter;
import com.example.oxionaz.mvpproject.view.ui.activities.ActivityCallback;
import com.example.oxionaz.mvpproject.view.ui.activities.MainActivity;
import com.example.oxionaz.mvpproject.view.ui.activities.MainActivity_;
import com.example.oxionaz.mvpproject.view.ui.fragments.RepoListFragment;
import com.example.oxionaz.mvpproject.view.ui.fragments.RepoListFragment_;
import com.example.oxionaz.mvpproject.view.ui.fragments.vh.RepoListVH;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;
import org.robolectric.Robolectric;
import org.robolectric.Shadows;
import org.robolectric.fakes.RoboMenu;
import org.robolectric.internal.Shadow;

import javax.inject.Inject;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.spy;
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
