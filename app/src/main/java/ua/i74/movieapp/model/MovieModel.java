package ua.i74.movieapp.model;

import java.util.List;

public class MovieModel {
    private int id;
    private String title;
    private String description;
    private String posterPath;
    private String backdropPath;
    private String releaseDate;
    private double rating;
    private List<GenreModel> genreList;

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

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
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

    public List<GenreModel> getGenreList() {
        return genreList;
    }

    public void setGenreList(List<GenreModel> genreList) {
        this.genreList = genreList;
    }
}
