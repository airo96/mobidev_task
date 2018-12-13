package ua.i74.movieapp.di.module;

import dagger.Module;
import dagger.Provides;
import ua.i74.movieapp.data.source.MovieRepository;
import ua.i74.movieapp.di.scope.ScreenScope;
import ua.i74.movieapp.presentation.SingleAppPresenter;
import ua.i74.movieapp.presentation.details.MovieDetailsPresenter;
import ua.i74.movieapp.presentation.list.SavedMoviesPresenter;
import ua.i74.movieapp.presentation.masterdetail.MasterDetailPresenter;
import ua.i74.movieapp.presentation.search.SearchMoviesPresenter;
import ua.i74.movieapp.presentation.search.SearchPersonPresenter;

@Module
public class PresenterModule {
    @ScreenScope
    @Provides
    public SearchMoviesPresenter provideSearchMoviePresenter(MovieRepository repository) {
        return new SearchMoviesPresenter(repository);
    }

    @ScreenScope
    @Provides
    public SearchPersonPresenter provideSearchPersonPresenter(MovieRepository repository) {
        return new SearchPersonPresenter(repository);
    }

    @ScreenScope
    @Provides
    public SavedMoviesPresenter provideSavedMoviesPresenter(MovieRepository repository) {
        return new SavedMoviesPresenter(repository);
    }

    @ScreenScope
    @Provides
    public MasterDetailPresenter provideMasterDetailPresenter() {
        return new MasterDetailPresenter();
    }

    @ScreenScope
    @Provides
    public MovieDetailsPresenter provideMovieDetailsPresenter(MovieRepository repository) {
        return new MovieDetailsPresenter(repository);
    }

    @ScreenScope
    @Provides
    public SingleAppPresenter provideSingleAppPresenter(MovieRepository repository) {
        return new SingleAppPresenter(repository);
    }
}
