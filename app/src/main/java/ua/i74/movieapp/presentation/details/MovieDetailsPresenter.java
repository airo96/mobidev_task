package ua.i74.movieapp.presentation.details;

import com.arellomobile.mvp.InjectViewState;
import com.arellomobile.mvp.MvpPresenter;

import ua.i74.movieapp.data.source.MovieRepository;
import ua.i74.movieapp.model.MovieModel;

@InjectViewState
public class MovieDetailsPresenter extends MvpPresenter<MovieDetailsView> {
    private MovieRepository repository;

    private MovieModel movie;

    public MovieDetailsPresenter(MovieRepository repository) {
        this.repository = repository;
    }

    public void onLoadMovie(int movieId, boolean isSaved) {
        getViewState().setVisibilitySaveButton(isSaved);
        repository.getMovieDetails(movieId, new MovieRepository.GetMovieCallback() {
            @Override
            public void onMovieLoaded(MovieModel movie) {
                MovieDetailsPresenter.this.movie = movie;
                getViewState().setData(movie);
            }

            @Override
            public void onError(String message) {
                getViewState().showToast(message);
                getViewState().navigateBack();
            }
        });
    }

    public void onSaveMovie() {
        repository.saveMovie(movie, new MovieRepository.SaveMovieResultCallback() {
            @Override
            public void onSaved() {
                getViewState().showToast("Saved");
                getViewState().navigateBack();
            }

            @Override
            public void onError(String error) {
                getViewState().showToast(error);
                getViewState().navigateBack();
            }
        });
    }
}
