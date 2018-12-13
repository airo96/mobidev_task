package ua.i74.movieapp.data.source.network;

import java.io.IOException;

import retrofit2.Response;
import ua.i74.movieapp.data.network.ErrorParser;
import ua.i74.movieapp.data.network.ServiceApi;
import ua.i74.movieapp.data.network.ServiceData;
import ua.i74.movieapp.model.network.MovieGenreNetworkModel;
import ua.i74.movieapp.model.network.MovieNetworkModel;
import ua.i74.movieapp.model.network.response.GetGenreListResponse;
import ua.i74.movieapp.model.network.response.GetMoviesForPersonResponse;
import ua.i74.movieapp.model.network.response.GetMoviesWithTitleResponse;
import ua.i74.movieapp.model.network.response.GetPersonWithNameResponse;

public class NetworkDataSourceImpl implements NetworkDataSource {
    private ServiceApi client;
    private ErrorParser errorParser;

    public NetworkDataSourceImpl(ServiceApi client,
                                 ErrorParser errorParser) {
        this.client = client;
        this.errorParser = errorParser;
    }

    @Override
    public void searchMoviesWithTitle(String title, OnSearchMoviesCallback callback) {
        try {
            Response<GetMoviesWithTitleResponse> response =
                    client.getMoviesWithTitle(ServiceData.API_KEY, title).execute();
            if (response.isSuccessful()) {
                callback.onMoviesFound(response.body().getMovieList());
            } else {
                callback.onError(errorParser.parse(response).getMessage());
            }
        } catch (IOException e) {
            callback.onError("Network not available");
        }
    }

    @Override
    public void searchMoviesForPerson(int personId, OnSearchMoviesCallback callback) {
        try {
            Response<GetMoviesForPersonResponse> response =
                    client.getMoviesForPerson(personId, ServiceData.API_KEY).execute();
            if (response.isSuccessful()) {
                callback.onMoviesFound(response.body().getMovieList());
            } else {
                callback.onError(errorParser.parse(response).getMessage());
            }
        } catch (IOException e) {
            callback.onError("Network not available");
        }
    }

    @Override
    public void searchPersonWithName(String name, OnSearchPersonCallback callback) {
        try {
            Response<GetPersonWithNameResponse> response =
                    client.getPersonWithName(ServiceData.API_KEY, name).execute();
            if (response.isSuccessful()) {
                if (!response.body().getPersonList().isEmpty())
                    callback.onPersonFound(response.body().getPersonList().get(0));
            } else {
                callback.onError(errorParser.parse(response).getMessage());
            }
        } catch (IOException e) {
            callback.onError("Network not available");
        }
    }

    @Override
    public void searchMovieDetails(int movieId, OnSearchDetailsCallback callback) {
        try {
            Response<MovieGenreNetworkModel> response =
                    client.getMovieDetails(movieId, ServiceData.API_KEY).execute();
            if (response.isSuccessful()) {
                callback.onMovieDetailsFound(response.body());
            } else {
                callback.onError(errorParser.parse(response).getMessage());
            }
        } catch (IOException e) {
            callback.onError("Network not available");
        }
    }
}
