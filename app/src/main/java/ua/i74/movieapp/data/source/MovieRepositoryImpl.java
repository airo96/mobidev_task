package ua.i74.movieapp.data.source;

import android.util.Log;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import ua.i74.movieapp.data.source.assets.AssetsDataSource;
import ua.i74.movieapp.data.source.database.DatabaseDataSource;
import ua.i74.movieapp.data.source.network.NetworkDataSource;
import ua.i74.movieapp.data.source.sharedpreferences.SharedPreferencesDataSource;
import ua.i74.movieapp.model.GenreModel;
import ua.i74.movieapp.model.MovieModel;
import ua.i74.movieapp.model.mapper.GenreModelMapper;
import ua.i74.movieapp.model.mapper.MovieModelMapper;
import ua.i74.movieapp.model.network.GenreNetworkModel;
import ua.i74.movieapp.model.network.MovieGenreNetworkModel;
import ua.i74.movieapp.model.network.MovieNetworkModel;
import ua.i74.movieapp.model.network.PersonNetworkModel;
import ua.i74.movieapp.utils.AppExecutor;

public class MovieRepositoryImpl implements MovieRepository {
    private static final String TAG = "MovieRepositoryImpl";
    private SharedPreferencesDataSource sharedPreferencesDataSource;
    private DatabaseDataSource databaseDataSource;
    private NetworkDataSource networkDataSource;
    private AssetsDataSource assetsDataSource;
    private AppExecutor appExecutor;

    private Map<Integer, MovieModel> cache;
    private boolean isCacheActual = false;

    public MovieRepositoryImpl(SharedPreferencesDataSource sharedPreferencesDataSource,
                               DatabaseDataSource databaseDataSource,
                               NetworkDataSource networkDataSource,
                               AssetsDataSource assetsDataSource,
                               AppExecutor appExecutor) {
        this.sharedPreferencesDataSource = sharedPreferencesDataSource;
        this.databaseDataSource = databaseDataSource;
        this.networkDataSource = networkDataSource;
        this.assetsDataSource = assetsDataSource;
        this.appExecutor = appExecutor;
    }

//    При первом запуске приложения этот метод срабатывает дважды...
//    Сначала он правильно определяет
    @Override
    public void populateDatabase(final PopulateDatabaseCallback callback) {
        if (sharedPreferencesDataSource.isDatabasePopulated()) {
            Log.i(TAG, sharedPreferencesDataSource.isDatabasePopulated() + "");
        } else {
            Log.i(TAG, "else called");
            appExecutor.getDiskExecutor().execute(new Runnable() {
                @Override
                public void run() {
                    assetsDataSource.getMovieGenreList(new AssetsDataSource.GetMovieGenreListCallback() {
                        @Override
                        public void onReceived(List<GenreNetworkModel> genres) {
                            int genresCount = genres.size();
                            int insertedCount = databaseDataSource.insertGenres(GenreModelMapper.toModelList(genres));
                            if (genresCount == insertedCount) {
                                sharedPreferencesDataSource.setIsDatabasePopulatedValue(true);
                                Log.i(TAG, "database was populated successfully");
                            appExecutor.getMainThreadExecutor().execute(new Runnable() {
                                @Override
                                public void run() {
                                    sharedPreferencesDataSource.setIsDatabasePopulatedValue(true);
//                                    callback.onResults("");
                                }
                            });
                            }
                        }

                        @Override
                        public void onError(String message) {
                            Log.i(TAG, message);
//                        appExecutor.getMainThreadExecutor().execute(new Runnable() {
//                            @Override
//                            public void run() {
//                                callback.onResults("");
//                            }
//                        });
                        }
                    });
                }
            });
        }

    }

    @Override
    public void saveMovie(final MovieModel movie, final SaveMovieResultCallback callback) {
        isCacheActual = false;
        appExecutor.getDiskExecutor().execute(new Runnable() {
            @Override
            public void run() {
                final long savingMovieResult = databaseDataSource.insertMovie(movie);
                int movieId = movie.getId();
                for (GenreModel genre : movie.getGenreList())
                    databaseDataSource.insertMovieGenre(movieId, genre.getId());
                appExecutor.getMainThreadExecutor().execute(new Runnable() {
                    @Override
                    public void run() {
                        if (savingMovieResult > 0) callback.onSaved();
                        else callback.onError("Movie not saved");
                    }
                });
            }
        });
    }

    @Override
    public void getAllMovies(final GetMoviesCallback callback) {
        if (isCacheActual) {
            callback.onMoviesLoaded(new ArrayList<>(cache.values()));
        } else {
            appExecutor.getDiskExecutor().execute(new Runnable() {
                @Override
                public void run() {
                    final List<MovieModel> movies = databaseDataSource.getAllMovies();
                    for (MovieModel movie : movies)
                        movie.setGenreList(databaseDataSource.getGenresForMovie(movie.getId()));
                    appExecutor.getMainThreadExecutor().execute(new Runnable() {
                        @Override
                        public void run() {
                            refreshCache(movies);
                            callback.onMoviesLoaded(new ArrayList<>(cache.values()));
                        }
                    });
                }
            });
        }
    }

    @Override
    public void getMovieDetails(final int movieId, final GetMovieCallback callback) {
        MovieModel movie = getMovieFromCache(movieId);
        if (movie != null) {
            callback.onMovieLoaded(movie);
        } else {
            appExecutor.getNetworkExecutor().execute(new Runnable() {
                @Override
                public void run() {
                    networkDataSource.searchMovieDetails(movieId, new NetworkDataSource.OnSearchDetailsCallback() {
                        @Override
                        public void onMovieDetailsFound(MovieGenreNetworkModel movie) {
                            final MovieModel movieModel = MovieModelMapper.toModel(movie);
                            List<GenreModel> genreModels = new ArrayList<>();
                            for (GenreNetworkModel networkModel : movie.getGenres()) {
                                GenreModel genre = databaseDataSource.getGenreById(networkModel.getId());
                                genreModels.add(genre);
                            }
                            movieModel.setGenreList(genreModels);
//                            final MovieModel movieModel = MovieModelMapper.toModel(movie);
//                            List<GenreModel> genres = new ArrayList<>();
//                            for (int i = 0; i < movie.getGenreIds().size(); i++)
//                                genres.add(databaseDataSource.getGenreById(movie.getGenreIds().get(i)));
//                            movieModel.setGenreList(genres);
                            appExecutor.getMainThreadExecutor().execute(new Runnable() {
                                @Override
                                public void run() {
                                    callback.onMovieLoaded(movieModel);
                                }
                            });
                        }

                        @Override
                        public void onError(final String message) {
                            appExecutor.getMainThreadExecutor().execute(new Runnable() {
                                @Override
                                public void run() {
                                    callback.onError(message);
                                }
                            });
                        }
                    });
                }
            });
//            callback.onError("Movie not found");
        }

    }

    @Override
    public void searchMovies(final String title, final GetMoviesCallback callback) {
        appExecutor.getNetworkExecutor().execute(new Runnable() {
            @Override
            public void run() {
                networkDataSource.searchMoviesWithTitle(title, new NetworkDataSource.OnSearchMoviesCallback() {
                    @Override
                    public void onMoviesFound(List<MovieNetworkModel> movies) {
                        final List<MovieModel> movieList = MovieModelMapper.toModelList(movies);
                        getGenresForMovies(movieList, movies);
                        appExecutor.getMainThreadExecutor().execute(new Runnable() {
                            @Override
                            public void run() {
                                callback.onMoviesLoaded(movieList);
                            }
                        });
                    }

                    @Override
                    public void onError(final String message) {
                        appExecutor.getMainThreadExecutor().execute(new Runnable() {
                            @Override
                            public void run() {
                                callback.onError(message);
                            }
                        });
                    }
                });
            }
        });
    }

    @Override
    public void searchPersonMovie(final String name, final GetMoviesCallback callback) {
        appExecutor.getNetworkExecutor().execute(new Runnable() {
            @Override
            public void run() {
                networkDataSource.searchPersonWithName(name, new NetworkDataSource.OnSearchPersonCallback() {
                    @Override
                    public void onPersonFound(PersonNetworkModel person) {
                        networkDataSource.searchMoviesForPerson(person.getId(), new NetworkDataSource.OnSearchMoviesCallback() {
                            @Override
                            public void onMoviesFound(List<MovieNetworkModel> movies) {
                                final List<MovieModel> movieList = MovieModelMapper.toModelList(movies);
                                getGenresForMovies(movieList, movies);
                                appExecutor.getMainThreadExecutor().execute(new Runnable() {
                                    @Override
                                    public void run() {
                                        callback.onMoviesLoaded(movieList);
                                    }
                                });
                            }

                            @Override
                            public void onError(final String message) {
                                appExecutor.getMainThreadExecutor().execute(new Runnable() {
                                    @Override
                                    public void run() {
                                        callback.onError(message);
                                    }
                                });
                            }
                        });
                    }

                    @Override
                    public void onError(final String message) {
                        appExecutor.getMainThreadExecutor().execute(new Runnable() {
                            @Override
                            public void run() {
                                callback.onError(message);
                            }
                        });
                    }
                });
            }
        });
    }

    private void getGenresForMovies(List<MovieModel> movieList, List<MovieNetworkModel> movies) {
        for (int i = 0; i < movies.size(); i++) {
            List<Integer> genreIds = movies.get(i).getGenreIds();
            List<GenreModel> genres = new ArrayList<>();
            for (int j = 0; j < genreIds.size(); j++) {
                GenreModel genre = databaseDataSource.getGenreById(genreIds.get(j));
                genres.add(genre);
            }
            movieList.get(i).setGenreList(genres);
        }
    }


    private MovieModel getMovieFromCache(int movieId) {
        if (cache == null || cache.isEmpty())
            return null;
        else
            return cache.get(movieId);
    }

    private void refreshCache(List<MovieModel> movies) {
        if (cache == null) {
            cache = new LinkedHashMap<>();
        }
        cache.clear();
        for (MovieModel movie : movies) {
            cache.put(movie.getId(), movie);
        }
        isCacheActual = true;
    }
}
