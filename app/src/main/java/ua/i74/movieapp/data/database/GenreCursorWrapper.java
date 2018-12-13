package ua.i74.movieapp.data.database;

import android.database.Cursor;
import android.database.CursorWrapper;
import android.util.Log;

import ua.i74.movieapp.data.database.DbSchema.GenreTable;
import ua.i74.movieapp.model.GenreModel;

public class GenreCursorWrapper extends CursorWrapper {

    public GenreCursorWrapper(Cursor cursor) {
        super(cursor);
    }

    public GenreModel getGenre() {
        int id = getInt(getColumnIndex(GenreTable.Cols.ID));
        String name = getString(getColumnIndex(GenreTable.Cols.NAME));

        GenreModel genre = new GenreModel();
        genre.setId(id);
        genre.setName(name);
        return genre;
    }
}
