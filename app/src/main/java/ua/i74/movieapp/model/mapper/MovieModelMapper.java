package ua.i74.movieapp.model.mapper;

import java.util.ArrayList;
import java.util.List;

import ua.i74.movieapp.model.MovieModel;
import ua.i74.movieapp.model.network.MovieGenreNetworkModel;
import ua.i74.movieapp.model.network.MovieNetworkModel;

public class MovieModelMapper {
    public static MovieModel toModel(MovieGenreNetworkModel networkModel) {
        MovieModel movie = new MovieModel();
        movie.setId(networkModel.getId());
        movie.setTitle(networkModel.getTitle());
        movie.setDescription(networkModel.getOverview());
        movie.setPosterPath(networkModel.getPosterPath());
        movie.setBackdropPath(networkModel.getBackdropPath());
        movie.setReleaseDate(networkModel.getReleaseDate());
        movie.setRating(networkModel.getRating());

        return movie;
    }

    public static MovieModel toModel(MovieNetworkModel networkModel) {
        MovieModel movie = new MovieModel();
        movie.setId(networkModel.getId());
        movie.setTitle(networkModel.getTitle());
        movie.setDescription(networkModel.getOverview());
        movie.setPosterPath(networkModel.getPosterPath());
        movie.setBackdropPath(networkModel.getBackdropPath());
        movie.setReleaseDate(networkModel.getReleaseDate());
        movie.setRating(networkModel.getRating());

        return movie;
    }

    public static List<MovieModel> toModelList(List<MovieNetworkModel> movies) {
        List<MovieModel> models = new ArrayList<>();
        for (MovieNetworkModel networkModel : movies) {
            models.add(toModel(networkModel));
        }
        return models;
    }
}
