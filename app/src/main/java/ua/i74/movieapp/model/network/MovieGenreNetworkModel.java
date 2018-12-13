package ua.i74.movieapp.model.network;

import com.squareup.moshi.Json;

import java.util.List;

public class MovieGenreNetworkModel {
    @Json(name = "id")
    private int id;
    @Json(name = "title")
    private String title;
    @Json(name = "overview")
    private String overview;
    @Json(name = "poster_path")
    private String posterPath;
    @Json(name = "backdrop_path")
    private String backdropPath;
    @Json(name = "release_date")
    private String releaseDate;
    @Json(name = "vote_average")
    private double rating;
    @Json(name = "genres")
    private List<GenreNetworkModel> genres;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getOverview() {
        return overview;
    }

    public void setOverview(String overview) {
        this.overview = overview;
    }

    public String getPosterPath() {
        return posterPath;
    }

    public void setPosterPath(String posterPath) {
        this.posterPath = posterPath;
    }

    public String getBackdropPath() {
        return backdropPath;
    }

    public void setBackdropPath(String backdropPath) {
        this.backdropPath = backdropPath;
    }

    public String getReleaseDate() {
        return releaseDate;
    }

    public void setReleaseDate(String releaseDate) {
        this.releaseDate = releaseDate;
    }

    public double getRating() {
        return rating;
    }

    public void setRating(double rating) {
        this.rating = rating;
    }

    public List<GenreNetworkModel> getGenres() {
        return genres;
    }

    public void setGenres(List<GenreNetworkModel> genres) {
        this.genres = genres;
    }
}
