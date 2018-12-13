package ua.i74.movieapp.data.source.sharedpreferences;

import android.content.SharedPreferences;

public class SharedPreferencesDataSourceImpl implements SharedPreferencesDataSource {
    private static final String APP_PREFERENCE_IS_DATABASE_POPULATED = "is_database_populate";

    private SharedPreferences appSharedPreferences;

    public SharedPreferencesDataSourceImpl(SharedPreferences appSharedPreferences) {
        this.appSharedPreferences = appSharedPreferences;
    }

    @Override
    public void setIsDatabasePopulatedValue(boolean isDatabasePopulated) {
        SharedPreferences.Editor editor = appSharedPreferences.edit();
        editor.putBoolean(APP_PREFERENCE_IS_DATABASE_POPULATED, isDatabasePopulated);
        editor.apply();
    }

    @Override
    public boolean isDatabasePopulated() {
        boolean isDatabasePopulate = false;
        if (appSharedPreferences.contains(APP_PREFERENCE_IS_DATABASE_POPULATED))
            isDatabasePopulate = appSharedPreferences.getBoolean(APP_PREFERENCE_IS_DATABASE_POPULATED, false);
        return isDatabasePopulate;
    }
}
