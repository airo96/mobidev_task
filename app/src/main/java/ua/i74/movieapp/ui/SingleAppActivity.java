package ua.i74.movieapp.ui;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.widget.Toast;

import com.arellomobile.mvp.MvpAppCompatActivity;
import com.arellomobile.mvp.presenter.InjectPresenter;
import com.arellomobile.mvp.presenter.ProvidePresenter;

import javax.inject.Inject;

import ua.i74.movieapp.App;
import ua.i74.movieapp.presentation.SingleAppPresenter;
import ua.i74.movieapp.presentation.SingleAppView;
import ua.i74.movieapp.ui.details.MovieDetailsFragment;
import ua.i74.movieapp.ui.listener.OnBackClickListener;
import ua.i74.movieapp.ui.listener.OnDetailsClickListener;
import ua.i74.movieapp.ui.masterdetail.MasterDetailFragment;
import ua.i74.movieapp.R;

public class SingleAppActivity extends MvpAppCompatActivity
        implements SingleAppView, OnDetailsClickListener, OnBackClickListener {

    @Inject
    SingleAppPresenter daggerPresenter;
    @InjectPresenter
    SingleAppPresenter presenter;

    @ProvidePresenter
    SingleAppPresenter provideSingleAppPresenter() {
        return daggerPresenter;
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        App.getInstance().plusSingleAppScreenComponent().inject(this);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_container);
        presenter.onCreate();

        Fragment fragment = getSupportFragmentManager().findFragmentById(R.id.app_container);
        if (fragment == null) {
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.app_container, MasterDetailFragment.getNewInstance())
                    .commit();
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        App.getInstance().clearSingleAppScreenComponent();
    }

    @Override
    public void onDetailsClick(int movieId, boolean isSaved) {
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.app_container, MovieDetailsFragment.getNewInstance(movieId,isSaved))
                .addToBackStack(null)
                .commit();
    }

    @Override
    public void onBackClicked() {
        getSupportFragmentManager().popBackStack();
    }

    @Override
    public void showToast(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }
}
