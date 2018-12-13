package ua.i74.movieapp.model.network.response;

import com.squareup.moshi.Json;

import java.util.List;

import ua.i74.movieapp.model.network.MovieNetworkModel;

public class GetMoviesForPersonResponse {
    @Json(name = "cast")
    private List<MovieNetworkModel> movieList;

    public List<MovieNetworkModel> getMovieList() {
        return movieList;
    }

    public void setMovieList(List<MovieNetworkModel> movieList) {
        this.movieList = movieList;
    }
}
