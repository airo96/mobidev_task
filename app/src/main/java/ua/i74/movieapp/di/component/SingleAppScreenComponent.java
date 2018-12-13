package ua.i74.movieapp.di.component;

import dagger.Subcomponent;
import ua.i74.movieapp.di.module.PresenterModule;
import ua.i74.movieapp.di.scope.ScreenScope;
import ua.i74.movieapp.ui.SingleAppActivity;

@ScreenScope
@Subcomponent(
        modules = PresenterModule.class
)
public interface SingleAppScreenComponent {
    void inject(SingleAppActivity activity);
}
