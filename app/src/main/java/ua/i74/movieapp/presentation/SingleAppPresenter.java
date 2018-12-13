package ua.i74.movieapp.presentation;

import com.arellomobile.mvp.InjectViewState;
import com.arellomobile.mvp.MvpPresenter;

import ua.i74.movieapp.data.source.MovieRepository;

@InjectViewState
public class SingleAppPresenter extends MvpPresenter<SingleAppView> {
    private MovieRepository repository;

    public SingleAppPresenter(MovieRepository repository) {
        this.repository = repository;
    }

    public void onCreate() {
        repository.populateDatabase(new MovieRepository.PopulateDatabaseCallback() {
            @Override
            public void onResults(String message) {
                getViewState().showToast(message);
            }
        });
    }
}
