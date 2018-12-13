package ua.i74.movieapp.data.source.sharedpreferences;

public interface SharedPreferencesDataSource {
    void setIsDatabasePopulatedValue(boolean isDatabasePopulate);
    boolean isDatabasePopulated();
}
