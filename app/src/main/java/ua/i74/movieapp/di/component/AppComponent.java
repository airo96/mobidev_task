package ua.i74.movieapp.di.component;

import dagger.Component;
import ua.i74.movieapp.di.module.AppModule;
import ua.i74.movieapp.di.module.AssetsModule;
import ua.i74.movieapp.di.module.DatabaseModule;
import ua.i74.movieapp.di.module.NetworkModule;
import ua.i74.movieapp.di.module.PresenterModule;
import ua.i74.movieapp.di.module.RepositoryModule;
import ua.i74.movieapp.di.module.SharedPreferencesModule;
import ua.i74.movieapp.di.scope.AppScope;

@AppScope
@Component(
        modules = {
                AppModule.class, NetworkModule.class, DatabaseModule.class,
                AssetsModule.class, SharedPreferencesModule.class, RepositoryModule.class
        }
)
public interface AppComponent {
    MasterDetailScreenComponent plusMasterDetailScreenComponent(PresenterModule presenterModule);
    MovieDetailsScreenComponent plusMovieDetailsScreenComponent(PresenterModule presenterModule);
    SavedMoviesScreenComponent plusSavedMoviesScreenComponent(PresenterModule presenterModule);
    SearchMoviesScreenComponent plusSearchMoviesScreenComponent(PresenterModule presenterModule);
    SearchPersonScreenComponent plusSearchPersonScreenComponent(PresenterModule presenterModule);
    SingleAppScreenComponent plusSingleAppScreenComponent(PresenterModule presenterModule);
}
