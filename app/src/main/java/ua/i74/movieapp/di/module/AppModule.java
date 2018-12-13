package ua.i74.movieapp.di.module;

import android.content.Context;

import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

import dagger.Module;
import dagger.Provides;
import ua.i74.movieapp.di.scope.AppScope;
import ua.i74.movieapp.utils.AppExecutor;
import ua.i74.movieapp.utils.MainThreadExecutor;

@Module
public class AppModule {
    private Context context;

    public AppModule(Context context) {
        this.context = context;
    }

    @AppScope
    @Provides
    public Context provideContext() {
        return context;
    }

    @AppScope
    @Provides
    public AppExecutor provideAppExecutor() {
        return new AppExecutor(
                Executors.newFixedThreadPool(3),
                Executors.newSingleThreadExecutor(),
                new MainThreadExecutor()
        );
    }
}
