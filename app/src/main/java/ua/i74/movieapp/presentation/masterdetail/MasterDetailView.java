package ua.i74.movieapp.presentation.masterdetail;

import android.support.v4.app.Fragment;

import com.arellomobile.mvp.MvpView;
import com.arellomobile.mvp.viewstate.strategy.AddToEndSingleStrategy;
import com.arellomobile.mvp.viewstate.strategy.StateStrategyType;

@StateStrategyType(AddToEndSingleStrategy.class)
public interface MasterDetailView extends MvpView {
    void showFragment(Fragment fragment, String title);
}
