package ua.i74.movieapp.data.database;

import android.database.Cursor;
import android.database.CursorWrapper;

import ua.i74.movieapp.data.database.DbSchema.MovieTable;
import ua.i74.movieapp.model.MovieModel;

public class MovieCursorWrapper extends CursorWrapper {

    public MovieCursorWrapper(Cursor cursor) {
        super(cursor);
    }

    public MovieModel getMovie() {
        int id = getInt(getColumnIndex(MovieTable.Cols.ID));
        String title = getString(getColumnIndex(MovieTable.Cols.TITLE));
        String description = getString(getColumnIndex(MovieTable.Cols.DESCRIPTION));
        String posterPath = getString(getColumnIndex(MovieTable.Cols.POSTER_PATH));
        String backdropPath = getString(getColumnIndex(MovieTable.Cols.BACKDROP_PATH));
        String releaseDate = getString(getColumnIndex(MovieTable.Cols.RELEASE_DATE));
        double rating = getDouble(getColumnIndex(MovieTable.Cols.RATING));

        MovieModel movie = new MovieModel();
        movie.setId(id);
        movie.setTitle(title);
        movie.setDescription(description);
        movie.setPosterPath(posterPath);
        movie.setBackdropPath(backdropPath);
        movie.setReleaseDate(releaseDate);
        movie.setRating(rating);
        return movie;
    }
}
