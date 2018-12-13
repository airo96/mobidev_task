package ua.i74.movieapp.data.network;

import java.io.IOException;
import java.lang.annotation.Annotation;

import okhttp3.ResponseBody;
import retrofit2.Converter;
import retrofit2.Response;
import retrofit2.Retrofit;
import ua.i74.movieapp.model.network.response.ErrorResponse;

public class ErrorParser {
    private Retrofit retrofit;

    public ErrorParser(Retrofit retrofit) {
        this.retrofit = retrofit;
    }

    public ErrorResponse parse(Response<?> response) {
        Converter<ResponseBody, ErrorResponse> converter =
                retrofit.responseBodyConverter(ErrorResponse.class, new Annotation[0]);
        try {
            return converter.convert(response.errorBody());
        } catch (IOException e) {
            return new ErrorResponse();
        }
    }
}
