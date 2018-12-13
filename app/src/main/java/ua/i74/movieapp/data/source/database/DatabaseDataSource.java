package ua.i74.movieapp.data.source.database;

import java.util.List;

import ua.i74.movieapp.model.GenreModel;
import ua.i74.movieapp.model.MovieModel;

public interface DatabaseDataSource {
    int insertGenres(List<GenreModel> genres);
    long insertMovie(MovieModel movie);
    void insertMovieGenre(int movieId, int genreId);

    List<GenreModel> getGenresForMovie(int movieId);
    List<MovieModel> getAllMovies();

    GenreModel getGenreById(int genreId);

//    boolean isGenresInitialized();
}
