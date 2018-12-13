package ua.i74.movieapp.data.source.assets;

import java.util.List;

import ua.i74.movieapp.model.network.GenreNetworkModel;

public interface AssetsDataSource {
    void getMovieGenreList(GetMovieGenreListCallback callback);

    interface GetMovieGenreListCallback {
        void onReceived(List<GenreNetworkModel> genres);
        void onError(String message);
    }
}
