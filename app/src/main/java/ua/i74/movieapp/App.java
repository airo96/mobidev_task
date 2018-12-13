package ua.i74.movieapp;

import android.app.Application;

import com.squareup.picasso.Picasso;

import ua.i74.movieapp.di.component.AppComponent;
import ua.i74.movieapp.di.component.DaggerAppComponent;
import ua.i74.movieapp.di.component.MasterDetailScreenComponent;
import ua.i74.movieapp.di.component.MovieDetailsScreenComponent;
import ua.i74.movieapp.di.component.SavedMoviesScreenComponent;
import ua.i74.movieapp.di.component.SearchMoviesScreenComponent;
import ua.i74.movieapp.di.component.SearchPersonScreenComponent;
import ua.i74.movieapp.di.component.SingleAppScreenComponent;
import ua.i74.movieapp.di.module.AppModule;
import ua.i74.movieapp.di.module.AssetsModule;
import ua.i74.movieapp.di.module.DatabaseModule;
import ua.i74.movieapp.di.module.NetworkModule;
import ua.i74.movieapp.di.module.PresenterModule;
import ua.i74.movieapp.di.module.RepositoryModule;
import ua.i74.movieapp.di.module.SharedPreferencesModule;

public class App extends Application {
    private static App instance;

    private AppComponent appComponent;
    private SingleAppScreenComponent singleAppScreenComponent;
    private MasterDetailScreenComponent masterDetailScreenComponent;
    private MovieDetailsScreenComponent movieDetailsScreenComponent;
    private SavedMoviesScreenComponent savedMoviesScreenComponent;
    private SearchMoviesScreenComponent searchMoviesScreenComponent;
    private SearchPersonScreenComponent searchPersonScreenComponent;

    @Override
    public void onCreate() {
        super.onCreate();
        instance = this;
        Picasso.get().setIndicatorsEnabled(true);
        appComponent = DaggerAppComponent.builder()
                .appModule(new AppModule(this))
                .networkModule(new NetworkModule())
                .databaseModule(new DatabaseModule())
                .repositoryModule(new RepositoryModule())
                .sharedPreferencesModule(new SharedPreferencesModule())
                .assetsModule(new AssetsModule())
                .build();
    }

    public static App getInstance() {
        return instance;
    }

    public SingleAppScreenComponent plusSingleAppScreenComponent() {
        if (singleAppScreenComponent == null) {
            singleAppScreenComponent = appComponent.plusSingleAppScreenComponent(new PresenterModule());
        }
        return singleAppScreenComponent;
    }

    public void clearSingleAppScreenComponent() {
        singleAppScreenComponent = null;
    }

    public MasterDetailScreenComponent plusMasterDetailScreenComponent() {
        if (masterDetailScreenComponent == null) {
            masterDetailScreenComponent = appComponent.plusMasterDetailScreenComponent(new PresenterModule());
        }
        return masterDetailScreenComponent;
    }

    public void clearMasterDetailScreenComponent() {
        masterDetailScreenComponent = null;
    }

    public MovieDetailsScreenComponent plusMovieDetailsScreenComponent() {
        if (movieDetailsScreenComponent == null) {
            movieDetailsScreenComponent = appComponent.plusMovieDetailsScreenComponent(new PresenterModule());
        }
        return movieDetailsScreenComponent;
    }

    public void clearMovieDetailsScreenComponent() {
        movieDetailsScreenComponent = null;
    }

    public SavedMoviesScreenComponent plusSavedMoviesScreenComponent() {
        if (savedMoviesScreenComponent == null) {
            savedMoviesScreenComponent = appComponent.plusSavedMoviesScreenComponent(new PresenterModule());
        }
        return savedMoviesScreenComponent;
    }

    public void clearSavedMoviesScreenComponent() {
        savedMoviesScreenComponent = null;
    }

    public SearchMoviesScreenComponent plusSearchMoviesScreenComponent() {
        if (searchMoviesScreenComponent == null) {
            searchMoviesScreenComponent = appComponent.plusSearchMoviesScreenComponent(new PresenterModule());
        }
        return searchMoviesScreenComponent;
    }

    public void clearSearchMoviesScreenComponent() {
        searchMoviesScreenComponent = null;
    }

    public SearchPersonScreenComponent plusSearchPersonScreenComponent() {
        if (searchPersonScreenComponent == null) {
            searchPersonScreenComponent = appComponent.plusSearchPersonScreenComponent(new PresenterModule());
        }
        return searchPersonScreenComponent;
    }

    public void clearSearchPersonScreenComponent() {
        searchPersonScreenComponent = null;
    }
}
