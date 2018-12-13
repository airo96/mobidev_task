package ua.i74.movieapp.presentation.list;

import android.support.annotation.StringRes;

import com.arellomobile.mvp.MvpView;
import com.arellomobile.mvp.viewstate.strategy.AddToEndSingleStrategy;
import com.arellomobile.mvp.viewstate.strategy.SkipStrategy;
import com.arellomobile.mvp.viewstate.strategy.StateStrategyType;

import java.util.List;

import ua.i74.movieapp.model.MovieModel;

@StateStrategyType(SkipStrategy.class)
public interface SavedMoviesView extends MvpView {
    void showToast(@StringRes int stringId);
    void showToast(String message);

    @StateStrategyType(AddToEndSingleStrategy.class)
    void setList(List<MovieModel> movies);
}
