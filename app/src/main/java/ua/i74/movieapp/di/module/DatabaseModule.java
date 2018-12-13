package ua.i74.movieapp.di.module;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import dagger.Module;
import dagger.Provides;
import ua.i74.movieapp.data.database.DbHelper;
import ua.i74.movieapp.di.scope.AppScope;

@Module
public class DatabaseModule {
    @AppScope
    @Provides
    public SQLiteDatabase provideDatabase(Context context) {
        return new DbHelper(context).getWritableDatabase();
    }
}
