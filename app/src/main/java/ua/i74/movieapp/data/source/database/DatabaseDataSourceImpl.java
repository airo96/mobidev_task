package ua.i74.movieapp.data.source.database;

import android.content.ContentValues;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.List;

import ua.i74.movieapp.data.database.DbSchema.GenreTable;
import ua.i74.movieapp.data.database.DbSchema.MovieGenreTable;
import ua.i74.movieapp.data.database.DbSchema.MovieTable;
import ua.i74.movieapp.data.database.GenreCursorWrapper;
import ua.i74.movieapp.data.database.MovieCursorWrapper;
import ua.i74.movieapp.model.GenreModel;
import ua.i74.movieapp.model.MovieModel;

public class DatabaseDataSourceImpl implements DatabaseDataSource {
    private static final String QUERY_GENRES_BY_MOVIE_ID = "select * from " + GenreTable.NAME + " inner join " + MovieGenreTable.NAME
            + " on " + MovieGenreTable.NAME + "." + MovieGenreTable.Cols.GENRE_ID
            + " = " + GenreTable.NAME + "." + GenreTable.Cols.ID + " where "
            + MovieGenreTable.Cols.MOVIE_ID + " = ?";

    private SQLiteDatabase database;

    public DatabaseDataSourceImpl(SQLiteDatabase database) {
        this.database = database;
    }

    @Override
    public int insertGenres(List<GenreModel> genres) {
        int counter = 0;
        for (GenreModel genre : genres) {
            long result = database.insert(
                    GenreTable.NAME,
                    null,
                    getGenreValues(genre)
            );
            if (result < 0) counter++;
        }
        return counter;
    }

    @Override
    public long insertMovie(MovieModel movie) {
        return database.insert(
                MovieTable.NAME,
                null,
                getMovieValues(movie)
        );
    }

    @Override
    public void insertMovieGenre(int movieId, int genreId) {
        database.insert(
                MovieGenreTable.NAME,
                null,
                getMovieGenreValues(movieId, genreId)
        );
    }

    @Override
    public List<GenreModel> getGenresForMovie(int movieId) {
        List<GenreModel> genres = new ArrayList<>();
        try (GenreCursorWrapper cursor = new GenreCursorWrapper(database.rawQuery(
                QUERY_GENRES_BY_MOVIE_ID,
                new String[]{String.valueOf(movieId)}
        ))) {
            cursor.moveToFirst();
            while (!cursor.isAfterLast()) {
                genres.add(cursor.getGenre());
                cursor.moveToNext();
            }
        }

        return genres;
    }

    @Override
    public List<MovieModel> getAllMovies() {
        List<MovieModel> movies = new ArrayList<>();
        try (MovieCursorWrapper cursor = new MovieCursorWrapper(database.query(MovieTable.NAME, null, null, null, null, null, null))) {
            cursor.moveToFirst();
            while (!cursor.isAfterLast()) {
                movies.add(cursor.getMovie());
                cursor.moveToNext();
            }
        }
        return movies;
    }


    @Override
    public GenreModel getGenreById(int genreId) {
        try (GenreCursorWrapper cursor = new GenreCursorWrapper(database.query(
                GenreTable.NAME,
                null,
                GenreTable.Cols.ID + " =?",
                new String[]{String.valueOf(genreId)},
                null,
                null,
                null
        ))) {
            if (cursor.getCount() == 0)
                return null;
            cursor.moveToFirst();
            return cursor.getGenre();
        }
    }

//    @Override
//    public boolean isGenresInitialized() {
//        boolean isInitialized;
//        try (GenreCursorWrapper cursor = new GenreCursorWrapper(database.query(GenreTable.NAME, null, null, null, null, null, null))) {
//            cursor.moveToFirst();
//            isInitialized = cursor.getCount() != 0;
//        }
//        return isInitialized;
//    }

    private ContentValues getMovieGenreValues(int movieId, int genreId) {
        ContentValues values = new ContentValues();
        values.put(MovieGenreTable.Cols.MOVIE_ID, movieId);
        values.put(MovieGenreTable.Cols.GENRE_ID, genreId);
        return values;
    }

    private ContentValues getMovieValues(MovieModel movie) {
        ContentValues values = new ContentValues();
        values.put(MovieTable.Cols.ID, movie.getId());
        values.put(MovieTable.Cols.TITLE, movie.getTitle());
        values.put(MovieTable.Cols.DESCRIPTION, movie.getDescription());
        values.put(MovieTable.Cols.POSTER_PATH, movie.getPosterPath());
        values.put(MovieTable.Cols.BACKDROP_PATH, movie.getBackdropPath());
        values.put(MovieTable.Cols.RELEASE_DATE, movie.getReleaseDate());
        values.put(MovieTable.Cols.RATING, movie.getRating());
        return values;
    }

    private ContentValues getGenreValues(GenreModel genre) {
        ContentValues values = new ContentValues();
        values.put(GenreTable.Cols.ID, genre.getId());
        values.put(GenreTable.Cols.NAME, genre.getName());
        return values;
    }
}
