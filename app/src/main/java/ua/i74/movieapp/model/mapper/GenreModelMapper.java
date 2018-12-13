package ua.i74.movieapp.model.mapper;

import java.util.ArrayList;
import java.util.List;

import ua.i74.movieapp.model.GenreModel;
import ua.i74.movieapp.model.network.GenreNetworkModel;

public class GenreModelMapper {
    public static GenreModel toModel(GenreNetworkModel networkModel) {
        GenreModel genre = new GenreModel();
        genre.setId(networkModel.getId());
        genre.setName(networkModel.getName());
        return genre;
    }

    public static List<GenreModel> toModelList(List<GenreNetworkModel> genres) {
        List<GenreModel> genreList = new ArrayList<>();
        for (GenreNetworkModel networkModel : genres) {
            genreList.add(toModel(networkModel));
        }
        return genreList;
    }
}
