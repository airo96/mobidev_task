package ua.i74.movieapp.data.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.support.annotation.Nullable;

import ua.i74.movieapp.data.database.DbSchema.GenreTable;
import ua.i74.movieapp.data.database.DbSchema.MovieGenreTable;
import ua.i74.movieapp.data.database.DbSchema.MovieTable;

public class DbHelper extends SQLiteOpenHelper {
    private static final String DATABASE_NAME = "movie_db.sqlite";
    private static final int DATABASE_VERSION = 1;

    public DbHelper(@Nullable Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("pragma foreign_keys = on");
        db.execSQL(
                "create table " + MovieTable.NAME + "("
                + MovieTable.Cols.ID + " integer primary key not null, "
                + MovieTable.Cols.TITLE + " text not null, "
                + MovieTable.Cols.DESCRIPTION + " text not null, "
                + MovieTable.Cols.POSTER_PATH + " text, "
                + MovieTable.Cols.BACKDROP_PATH + " text, "
                + MovieTable.Cols.RELEASE_DATE + " text not null, "
                + MovieTable.Cols.RATING + " real not null);"
        );
        db.execSQL(
                "create table " + GenreTable.NAME + "("
                + GenreTable.Cols.ID + " integer primary key not null, "
                + GenreTable.Cols.NAME + " text not null);"
        );
        db.execSQL(
                "create table " + MovieGenreTable.NAME + "("
                + MovieGenreTable.Cols.MOVIE_ID + " integer not null, "
                + MovieGenreTable.Cols.GENRE_ID + " integer not null, "
                + "foreign key (" + MovieGenreTable.Cols.MOVIE_ID + ") "
                + "references " + MovieTable.NAME + "(" + MovieTable.Cols.ID + "), "
                + "foreign key (" + MovieGenreTable.Cols.GENRE_ID + ") "
                + "references " + GenreTable.NAME + "(" + GenreTable.Cols.ID + ") ); "
        );
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
