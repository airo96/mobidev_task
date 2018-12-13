package ua.i74.movieapp.ui.masterdetail;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import com.arellomobile.mvp.MvpAppCompatFragment;
import com.arellomobile.mvp.presenter.InjectPresenter;
import com.arellomobile.mvp.presenter.ProvidePresenter;

import javax.inject.Inject;

import butterknife.BindString;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import ua.i74.movieapp.App;
import ua.i74.movieapp.R;
import ua.i74.movieapp.presentation.masterdetail.MasterDetailPresenter;
import ua.i74.movieapp.presentation.masterdetail.MasterDetailView;
import ua.i74.movieapp.ui.listener.OnDetailsClickListener;
import ua.i74.movieapp.ui.masterdetail.detail.SavedMoviesFragment;
import ua.i74.movieapp.ui.masterdetail.detail.SearchMoviesFragment;
import ua.i74.movieapp.ui.masterdetail.detail.SearchPersonFragment;

public class MasterDetailFragment extends MvpAppCompatFragment implements MasterDetailView, NavigationView.OnNavigationItemSelectedListener {

    @Inject
    MasterDetailPresenter daggerPresenter;
    @InjectPresenter
    MasterDetailPresenter presenter;

    @BindView(R.id.app_navigation_view)
    NavigationView appNavigationView;
    @BindView(R.id.drawer_layout)
    DrawerLayout drawerLayout;
    @BindView(R.id.detail_toolbar)
    Toolbar toolbar;
    @BindString(R.string.screen_title_saved_movies)
    String titleSavedMovies;
    @BindString(R.string.screen_title_find_movie)
    String titleFindMovie;

    private Unbinder unbinder;
    private OnDetailsClickListener detailsPressedListener;
    private AppCompatActivity compatActivity;

    public static MasterDetailFragment getNewInstance() {
        return new MasterDetailFragment();
    }

    @ProvidePresenter
    MasterDetailPresenter provideMasterDetailPresenter() {
        return daggerPresenter;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        compatActivity = (AppCompatActivity) context;
        detailsPressedListener = (OnDetailsClickListener) context;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        App.getInstance().plusMasterDetailScreenComponent().inject(this);
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
        setRetainInstance(true);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_master_detail, container, false);
        unbinder = ButterKnife.bind(this, rootView);
        compatActivity.setSupportActionBar(toolbar);
        appNavigationView.setNavigationItemSelectedListener(this);
        presenter.onShowLastKnownFragment(SavedMoviesFragment.getNewInstance(), titleSavedMovies);
        return rootView;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        App.getInstance().clearMasterDetailScreenComponent();
    }

    @Override
    public void onDetach() {
        super.onDetach();
        detailsPressedListener = null;
        compatActivity = null;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                if (!drawerLayout.isDrawerOpen(GravityCompat.START))
                    drawerLayout.openDrawer(GravityCompat.START);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        Fragment fragment = getChildFragmentManager().findFragmentById(R.id.detail_container);

        switch (item.getItemId()) {
            case R.id.nav_item_saved_movies:
                if (fragment instanceof SavedMoviesFragment)
                    return false;
                presenter.onShowFragment(SavedMoviesFragment.getNewInstance(), titleSavedMovies);
                break;
            case R.id.nav_item_search_movies:
                if (fragment instanceof SearchMoviesFragment)
                    return false;
                presenter.onShowFragment(SearchMoviesFragment.getNewInstance(), titleFindMovie);
                break;
            case R.id.nav_item_search_person_movies:
                if (fragment instanceof SearchPersonFragment)
                    return false;
                presenter.onShowFragment(SearchPersonFragment.getNewInstance(), titleFindMovie);
                break;
        }

        if (drawerLayout.isDrawerOpen(GravityCompat.START))
            drawerLayout.closeDrawer(GravityCompat.START);
        return true;
    }

    public void onReplaceToDetails(int movieId, boolean isSaved) {
        detailsPressedListener.onDetailsClick(movieId, isSaved);
    }

    @Override
    public void showFragment(Fragment fragment, String title) {
        getChildFragmentManager().beginTransaction()
                .replace(R.id.detail_container, fragment)
                .commit();
    }
}
