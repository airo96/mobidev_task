package ua.i74.movieapp.ui.masterdetail.detail;

import android.content.res.Configuration;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.arellomobile.mvp.MvpAppCompatFragment;
import com.arellomobile.mvp.presenter.InjectPresenter;
import com.arellomobile.mvp.presenter.ProvidePresenter;

import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import ua.i74.movieapp.App;
import ua.i74.movieapp.R;
import ua.i74.movieapp.model.MovieModel;
import ua.i74.movieapp.presentation.list.SavedMoviesPresenter;
import ua.i74.movieapp.presentation.list.SavedMoviesView;
import ua.i74.movieapp.ui.adapter.MovieRecyclerAdapter;
import ua.i74.movieapp.ui.listener.OnRecyclerItemClickListener;
import ua.i74.movieapp.ui.masterdetail.MasterDetailFragment;

public class SavedMoviesFragment extends MvpAppCompatFragment
        implements SavedMoviesView, OnRecyclerItemClickListener {

    @Inject
    SavedMoviesPresenter daggerPresenter;
    @InjectPresenter
    SavedMoviesPresenter presenter;

    @BindView(R.id.movie_recycler_view)
    RecyclerView movieRecyclerView;

    private Unbinder unbinder;
    private MovieRecyclerAdapter adapter;

    public static SavedMoviesFragment getNewInstance() {
        return new SavedMoviesFragment();
    }

    @ProvidePresenter
    public SavedMoviesPresenter provideSavedMoviesPresenter() {
        return daggerPresenter;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        App.getInstance().plusSavedMoviesScreenComponent().inject(this);
        super.onCreate(savedInstanceState);
        setRetainInstance(true);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.movie_list, container, false);
        unbinder = ButterKnife.bind(this, rootView);
        presenter.onLoadMovies();
        adapter = new MovieRecyclerAdapter(
                getResources().getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT,
                this
        );
        movieRecyclerView.setAdapter(adapter);
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
        App.getInstance().clearSavedMoviesScreenComponent();
    }

    @Override
    public void showToast(int stringId) {
        Toast.makeText(getActivity(), stringId, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void showToast(String message) {
        Toast.makeText(getActivity(), message, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void setList(List<MovieModel> movies) {
        adapter.setData(movies);
    }

    @Override
    public void onRecyclerItemClick(MovieModel movie) {
        MasterDetailFragment parentFragment = (MasterDetailFragment) getParentFragment();
        if (parentFragment != null)
            parentFragment.onReplaceToDetails(movie.getId(), true);
    }
}
