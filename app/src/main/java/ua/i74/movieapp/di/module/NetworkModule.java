package ua.i74.movieapp.di.module;

import dagger.Module;
import dagger.Provides;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.moshi.MoshiConverterFactory;
import ua.i74.movieapp.data.network.ErrorParser;
import ua.i74.movieapp.data.network.ServiceApi;
import ua.i74.movieapp.data.network.ServiceData;
import ua.i74.movieapp.di.scope.AppScope;

@Module
public class NetworkModule {
    @AppScope
    @Provides
    public Retrofit provideRetrofit() {
        OkHttpClient client = new OkHttpClient.Builder()
                .addInterceptor(new HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY))
                .build();

        return new Retrofit.Builder()
                .addConverterFactory(MoshiConverterFactory.create())
                .client(client)
                .baseUrl(ServiceData.BASE_URL)
                .build();
    }

    @AppScope
    @Provides
    public ServiceApi provideServiceApi(Retrofit retrofit) {
        return retrofit.create(ServiceApi.class);
    }

    @AppScope
    @Provides
    public ErrorParser provideErrorParser(Retrofit retrofit) {
        return new ErrorParser(retrofit);
    }
}
