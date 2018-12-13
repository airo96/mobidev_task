package ua.i74.movieapp.presentation.list;

import com.arellomobile.mvp.InjectViewState;
import com.arellomobile.mvp.MvpPresenter;

import java.util.List;

import ua.i74.movieapp.R;
import ua.i74.movieapp.data.source.MovieRepository;
import ua.i74.movieapp.model.MovieModel;

@InjectViewState
public class SavedMoviesPresenter extends MvpPresenter<SavedMoviesView> {
    private MovieRepository repository;

    public SavedMoviesPresenter(MovieRepository repository) {
        this.repository = repository;
    }

    public void onLoadMovies() {
        repository.getAllMovies(new MovieRepository.GetMoviesCallback() {
            @Override
            public void onMoviesLoaded(List<MovieModel> movies) {
                getViewState().setList(movies);
            }

            @Override
            public void onError(String message) {
                getViewState().showToast(message);
            }
        });
    }
}
