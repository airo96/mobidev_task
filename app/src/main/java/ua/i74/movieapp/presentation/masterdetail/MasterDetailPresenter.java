package ua.i74.movieapp.presentation.masterdetail;

import android.support.v4.app.Fragment;

import com.arellomobile.mvp.InjectViewState;
import com.arellomobile.mvp.MvpPresenter;


@InjectViewState
public class MasterDetailPresenter extends MvpPresenter<MasterDetailView> {
    private Fragment lastKnownFragment;
    private String lastKnownTitle;

    public void onShowLastKnownFragment(Fragment fragmentByDefault, String titleByDefault) {
        if (lastKnownFragment == null) {
            lastKnownFragment = fragmentByDefault;
            lastKnownTitle = titleByDefault;
        }
        getViewState().showFragment(lastKnownFragment, lastKnownTitle);
    }

    public void onShowFragment(Fragment fragment, String title) {
        lastKnownFragment = fragment;
        lastKnownTitle = title;
        getViewState().showFragment(lastKnownFragment, lastKnownTitle);
    }
}
