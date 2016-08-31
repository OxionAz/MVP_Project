package com.example.oxionaz.mvpproject.view.ui.fragments;

import android.support.v4.app.Fragment;
import com.example.oxionaz.mvpproject.presenter.BasePresenter;

public abstract class BaseFragment extends Fragment {

    protected abstract BasePresenter getPresenter();

    @Override
    public void onStop() {
        super.onStop();
        if (getPresenter() != null) {
            getPresenter().onStop();
        }
    }

}
