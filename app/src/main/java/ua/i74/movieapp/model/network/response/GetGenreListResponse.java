package ua.i74.movieapp.model.network.response;

import com.squareup.moshi.Json;

import java.util.List;

import ua.i74.movieapp.model.network.GenreNetworkModel;

public class GetGenreListResponse {
    @Json(name = "genres")
    private List<GenreNetworkModel> genreList;

    public List<GenreNetworkModel> getGenreList() {
        return genreList;
    }

    public void setGenreList(List<GenreNetworkModel> genreList) {
        this.genreList = genreList;
    }
}
