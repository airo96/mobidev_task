package ua.i74.movieapp.data.source.network;

import java.util.List;

import ua.i74.movieapp.model.network.GenreNetworkModel;
import ua.i74.movieapp.model.network.MovieGenreNetworkModel;
import ua.i74.movieapp.model.network.MovieNetworkModel;
import ua.i74.movieapp.model.network.PersonNetworkModel;

public interface NetworkDataSource {
    void searchMoviesWithTitle(String title, OnSearchMoviesCallback callback);
    void searchMoviesForPerson(int personId, OnSearchMoviesCallback callback);
    void searchPersonWithName(String name, OnSearchPersonCallback callback);
    void searchMovieDetails(int movieId, OnSearchDetailsCallback callback);

    interface OnSearchMoviesCallback {
        void onMoviesFound(List<MovieNetworkModel> movies);
        void onError(String message);
    }

    interface OnSearchPersonCallback {
        void onPersonFound(PersonNetworkModel person);
        void onError(String message);
    }

    interface OnSearchDetailsCallback {
        void onMovieDetailsFound(MovieGenreNetworkModel movie);
        void onError(String message);
    }
}
