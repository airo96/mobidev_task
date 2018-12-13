package ua.i74.movieapp.di.module;

import android.content.Context;
import android.content.res.AssetManager;

import dagger.Module;
import dagger.Provides;
import ua.i74.movieapp.di.scope.AppScope;

@Module
public class AssetsModule {
    @AppScope
    @Provides
    public AssetManager provideAssetManager(Context context) {
        return context.getAssets();
    }
}
