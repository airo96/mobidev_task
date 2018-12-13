package ua.i74.movieapp.data.source.assets;

import android.content.res.AssetManager;

import com.squareup.moshi.JsonAdapter;
import com.squareup.moshi.Moshi;
import com.squareup.moshi.Types;

import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import ua.i74.movieapp.model.GenreModel;
import ua.i74.movieapp.model.network.GenreNetworkModel;

public class AssetsDataSourceImpl implements AssetsDataSource{
    private AssetManager assetManager;

    public AssetsDataSourceImpl(AssetManager assetManager) {
        this.assetManager = assetManager;
    }

    @Override
    public void getMovieGenreList(GetMovieGenreListCallback callback) {
        String jsonString = "";
        try {
            InputStream inputStream = assetManager.open("movie_genre_list.json");
            byte[] buffer = new byte[inputStream.available()];
            inputStream.read(buffer);
            inputStream.close();
            jsonString = new String(buffer, "utf-8");
        } catch (IOException e) {
            String errorMessage = "Error read of file";
            callback.onError(errorMessage);
        }

        Moshi moshi = new Moshi.Builder().build();
        Type type = Types.newParameterizedType(List.class, GenreNetworkModel.class);
        JsonAdapter<List<GenreNetworkModel>> adapter = moshi.adapter(type);
        List<GenreNetworkModel> genres = new ArrayList<>();
        try {
            genres = adapter.fromJson(jsonString);
            callback.onReceived(genres);
        } catch (IOException e) {
            String errorMessage = "Error reading JSON data";
            callback.onError(errorMessage);
        }
        callback.onReceived(genres);
    }
}
