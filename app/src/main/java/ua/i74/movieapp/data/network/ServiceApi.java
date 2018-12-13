package ua.i74.movieapp.data.network;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;
import ua.i74.movieapp.model.network.MovieGenreNetworkModel;
import ua.i74.movieapp.model.network.MovieNetworkModel;
import ua.i74.movieapp.model.network.response.GetGenreListResponse;
import ua.i74.movieapp.model.network.response.GetMoviesForPersonResponse;
import ua.i74.movieapp.model.network.response.GetMoviesWithTitleResponse;
import ua.i74.movieapp.model.network.response.GetPersonWithNameResponse;

public interface ServiceApi {
    @GET("search/movie")
    Call<GetMoviesWithTitleResponse> getMoviesWithTitle(
            @Query("api_key") String apiKey,
            @Query("query") String query
    );

    @GET("search/person")
    Call<GetPersonWithNameResponse> getPersonWithName(
            @Query("api_key") String apiKey,
            @Query("query") String query
    );

    @GET("person/{person_id}/movie_credits")
    Call<GetMoviesForPersonResponse> getMoviesForPerson(
            @Path("person_id") int personId,
            @Query("api_key") String apiKey
    );

    @GET("movie/{movie_id}")
    Call<MovieGenreNetworkModel> getMovieDetails(
            @Path("movie_id") int movieId,
            @Query("api_key") String apiKey
    );
}
