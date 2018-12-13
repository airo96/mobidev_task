package ua.i74.movieapp.ui.details;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.arellomobile.mvp.MvpAppCompatFragment;
import com.arellomobile.mvp.presenter.InjectPresenter;
import com.arellomobile.mvp.presenter.ProvidePresenter;
import com.squareup.picasso.MemoryPolicy;
import com.squareup.picasso.NetworkPolicy;
import com.squareup.picasso.Picasso;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import ua.i74.movieapp.App;
import ua.i74.movieapp.R;
import ua.i74.movieapp.data.network.ServiceData;
import ua.i74.movieapp.model.MovieModel;
import ua.i74.movieapp.presentation.details.MovieDetailsPresenter;
import ua.i74.movieapp.presentation.details.MovieDetailsView;
import ua.i74.movieapp.ui.listener.OnBackClickListener;

public class MovieDetailsFragment extends MvpAppCompatFragment
        implements MovieDetailsView {

    private static final String ARG_MOVIE_ID =
            "ua.i74.movieapp.ui.details.movie_details_fragment.arg_movie_id";
    private static final String ARG_IS_SAVED =
            "ua.i74.movieapp.ui.details.movie_details_fragment.arg_is_saved";

    @Inject
    MovieDetailsPresenter daggerPresenter;
    @InjectPresenter
    MovieDetailsPresenter presenter;

    @BindView(R.id.movie_details_toolbar)
    Toolbar toolbar;
    @BindView(R.id.backdrop_image_view)
    ImageView backdropImageView;
    @BindView(R.id.release_date_text_view)
    TextView releaseDateTextView;
    @BindView(R.id.rating_text_view)
    TextView ratingTextView;
    @BindView(R.id.summary_text_view)
    TextView summaryTextView;

    private Unbinder unbinder;
    private OnBackClickListener backClickListener;
    private AppCompatActivity compatActivity;

    private boolean isSaved = false;

    public static MovieDetailsFragment getNewInstance(int movieId, boolean isSaved) {
        Bundle args = new Bundle();
        args.putInt(ARG_MOVIE_ID, movieId);
        args.putBoolean(ARG_IS_SAVED, isSaved);

        MovieDetailsFragment fragment = new MovieDetailsFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @ProvidePresenter
    MovieDetailsPresenter provideMovieDetailsPresenter() {
        return daggerPresenter;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        compatActivity = (AppCompatActivity) context;
        backClickListener = (OnBackClickListener) context;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        App.getInstance().plusMovieDetailsScreenComponent().inject(this);
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_movie_details, container, false);
        unbinder = ButterKnife.bind(this, rootView);
        compatActivity.setSupportActionBar(toolbar);

        return rootView;
    }

    @Override
    public void onStart() {
        super.onStart();
        int movieId = getArguments().getInt(ARG_MOVIE_ID, 0);
        boolean isSaved = getArguments().getBoolean(ARG_IS_SAVED, false);
        presenter.onLoadMovie(movieId, isSaved);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        App.getInstance().clearMovieDetailsScreenComponent();
    }

    @Override
    public void onDetach() {
        super.onDetach();
        compatActivity = null;
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.menu_movie_details, menu);
    }

    @Override
    public void onPrepareOptionsMenu(Menu menu) {
        super.onPrepareOptionsMenu(menu);
        menu.findItem(R.id.menu_item_save).setVisible(!isSaved);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                backClickListener.onBackClicked();
                return true;
            case R.id.menu_item_save:
                presenter.onSaveMovie();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public void setVisibilitySaveButton(boolean isSaved) {
        this.isSaved = isSaved;
        compatActivity.invalidateOptionsMenu();
    }

    @Override
    public void setData(MovieModel movie) {
        releaseDateTextView.setText(movie.getReleaseDate());
        ratingTextView.setText(String.valueOf(movie.getRating()));
        summaryTextView.setText(movie.getDescription());
        compatActivity.getSupportActionBar().setTitle(movie.getTitle());
        Picasso.get()
                .load(ServiceData.BACKDROP_IMAGE_URL + movie.getBackdropPath())
                .error(R.drawable.emoticon_devil_outline)
                .centerInside()
                .networkPolicy(NetworkPolicy.NO_CACHE)
                .resizeDimen(R.dimen.backdrop_width, R.dimen.backdrop_height)
                .into(backdropImageView);
    }

    @Override
    public void showToast(String message) {
        Toast.makeText(compatActivity, message, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void navigateBack() {
        backClickListener.onBackClicked();
    }
}
