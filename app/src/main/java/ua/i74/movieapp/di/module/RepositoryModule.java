package ua.i74.movieapp.di.module;

import android.content.SharedPreferences;
import android.content.res.AssetManager;
import android.database.sqlite.SQLiteDatabase;

import dagger.Module;
import dagger.Provides;
import ua.i74.movieapp.data.network.ErrorParser;
import ua.i74.movieapp.data.network.ServiceApi;
import ua.i74.movieapp.data.source.MovieRepository;
import ua.i74.movieapp.data.source.MovieRepositoryImpl;
import ua.i74.movieapp.data.source.assets.AssetsDataSource;
import ua.i74.movieapp.data.source.assets.AssetsDataSourceImpl;
import ua.i74.movieapp.data.source.database.DatabaseDataSource;
import ua.i74.movieapp.data.source.database.DatabaseDataSourceImpl;
import ua.i74.movieapp.data.source.network.NetworkDataSource;
import ua.i74.movieapp.data.source.network.NetworkDataSourceImpl;
import ua.i74.movieapp.data.source.sharedpreferences.SharedPreferencesDataSource;
import ua.i74.movieapp.data.source.sharedpreferences.SharedPreferencesDataSourceImpl;
import ua.i74.movieapp.di.scope.AppScope;
import ua.i74.movieapp.utils.AppExecutor;

@Module
public class RepositoryModule {
    @AppScope
    @Provides
    public SharedPreferencesDataSource provideSharedPreferencesDataSource(SharedPreferences appPreferences) {
        return new SharedPreferencesDataSourceImpl(appPreferences);
    }

    @AppScope
    @Provides
    public AssetsDataSource provideAssetsDataSource(AssetManager assetManager) {
        return new AssetsDataSourceImpl(assetManager);
    }

    @AppScope
    @Provides
    public DatabaseDataSource provideDatabaseDataSource(SQLiteDatabase database) {
        return new DatabaseDataSourceImpl(database);
    }

    @AppScope
    @Provides
    public NetworkDataSource provideNetworkDataSource(ServiceApi client, ErrorParser errorParser) {
        return new NetworkDataSourceImpl(client, errorParser);
    }

    @AppScope
    @Provides
    public MovieRepository provideMovieRepository(SharedPreferencesDataSource sharedPreferencesDataSource,
                                                  AssetsDataSource assetsDataSource,
                                                  DatabaseDataSource databaseDataSource,
                                                  NetworkDataSource networkDataSource,
                                                  AppExecutor appExecutor) {
        return new MovieRepositoryImpl(
                sharedPreferencesDataSource,
                databaseDataSource,
                networkDataSource,
                assetsDataSource,
                appExecutor);
    }
}
