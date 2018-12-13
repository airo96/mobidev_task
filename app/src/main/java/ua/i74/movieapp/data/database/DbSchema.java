package ua.i74.movieapp.data.database;

public class DbSchema {
    public static final class MovieTable {
        public static final String NAME = "movies";
        public static final class Cols {
            public static final String ID = "id";
            public static final String TITLE = "title";
            public static final String DESCRIPTION = "description";
            public static final String POSTER_PATH = "poster_path";
            public static final String BACKDROP_PATH = "backdrop_path";
            public static final String RELEASE_DATE = "release_date";
            public static final String RATING = "rating";
        }
    }

    public static final class GenreTable {
        public static final String NAME = "genres";
        public static final class Cols {
            public static final String ID = "id";
            public static final String NAME = "name";
        }

    }

    public static final class MovieGenreTable {
        public static final String NAME = "movie_genre";
        public static final class Cols {
            public static final String MOVIE_ID = "movie_id";
            public static final String GENRE_ID = "genre_id";
        }
    }
}
