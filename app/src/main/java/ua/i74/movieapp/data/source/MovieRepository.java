package ua.i74.movieapp.data.source;

import java.util.List;

import ua.i74.movieapp.model.MovieModel;

public interface MovieRepository {
    void populateDatabase(PopulateDatabaseCallback callback);

    void saveMovie(MovieModel movie, SaveMovieResultCallback callback);

    void getAllMovies(GetMoviesCallback callback);
    void getMovieDetails(int movieId, GetMovieCallback callback);

    void searchMovies(String title, GetMoviesCallback callback);
    void searchPersonMovie(String name, GetMoviesCallback callback);

    interface SaveMovieResultCallback {
        void onSaved();
        void onError(String error);
    }

    interface GetMoviesCallback {
        void onMoviesLoaded(List<MovieModel> movies);
        void onError(String message);
    }

    interface GetMovieCallback {
        void onMovieLoaded(MovieModel movie);
        void onError(String message);
    }

    interface PopulateDatabaseCallback {
        void onResults(String message);
    }
}
