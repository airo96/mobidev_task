package ua.i74.movieapp.presentation.details;

import com.arellomobile.mvp.MvpView;
import com.arellomobile.mvp.viewstate.strategy.SkipStrategy;
import com.arellomobile.mvp.viewstate.strategy.StateStrategyType;

import ua.i74.movieapp.model.MovieModel;

@StateStrategyType(SkipStrategy.class)
public interface MovieDetailsView extends MvpView {
    void setVisibilitySaveButton(boolean isSaved);
    void setData(MovieModel movie);


    void showToast(String message);
    void navigateBack();
}
