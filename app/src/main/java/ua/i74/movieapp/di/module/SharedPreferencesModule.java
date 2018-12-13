package ua.i74.movieapp.di.module;

import android.content.Context;
import android.content.SharedPreferences;

import dagger.Module;
import dagger.Provides;
import ua.i74.movieapp.di.scope.AppScope;

@Module
public class SharedPreferencesModule {
    private static final String APP_PREFERENCES_NAME = "movieapp_preferences";

    @AppScope
    @Provides
    public SharedPreferences provideSharedPreferences(Context context) {
        return context.getSharedPreferences(APP_PREFERENCES_NAME, Context.MODE_PRIVATE);
    }
}
