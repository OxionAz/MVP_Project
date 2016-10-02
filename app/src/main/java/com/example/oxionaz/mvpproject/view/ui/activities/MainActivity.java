package com.example.oxionaz.mvpproject.view.ui.activities;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import com.example.oxionaz.mvpproject.model.sources.db.models.Repository;
import com.example.oxionaz.mvpproject.R;
import com.example.oxionaz.mvpproject.view.ui.fragments.RepoInfoFragment;
import com.example.oxionaz.mvpproject.view.ui.fragments.RepoListFragment;

public class MainActivity extends AppCompatActivity implements ActivityCallback {

    private static final String TAG = "INFO";
    private FragmentManager fragmentManager;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        fragmentManager = getSupportFragmentManager();
        if (savedInstanceState == null) replaceFragment(new RepoListFragment(), false);
    }

    @Override
    public void startRepoInfoFragment(Repository repository) {
        replaceFragment(RepoInfoFragment.newInstance(repository), true);
    }

    private void replaceFragment(Fragment fragment, boolean addBackStack) {
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        transaction.replace(R.id.main_frame, fragment, TAG);
        if (addBackStack) transaction.addToBackStack(null);
        transaction.commit();
    }

}