package ua.i74.movieapp.presentation.search;

import com.arellomobile.mvp.InjectViewState;
import com.arellomobile.mvp.MvpPresenter;

import java.util.List;

import ua.i74.movieapp.data.source.MovieRepository;
import ua.i74.movieapp.model.MovieModel;

@InjectViewState
public class SearchMoviesPresenter extends MvpPresenter<SearchMoviesView> {
    private MovieRepository repository;

    public SearchMoviesPresenter(MovieRepository repository) {
        this.repository = repository;
    }

    public void onQueryTextSubmit(String query) {
        repository.searchMovies(query, new MovieRepository.GetMoviesCallback() {
            @Override
            public void onMoviesLoaded(List<MovieModel> movies) {
                getViewState().setList(movies);
                if (movies.isEmpty())
                    getViewState().showToast("No results found");
                else
                    getViewState().hideSearchView();
            }

            @Override
            public void onError(String message) {
                getViewState().showToast(message);
            }
        });
    }
}
