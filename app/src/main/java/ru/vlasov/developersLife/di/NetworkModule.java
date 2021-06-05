package ru.vlasov.developersLife.di;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import ru.vlasov.developersLife.api.NetworkHelper;

@Module
public class NetworkModule {

    private static String BASE_URL = "https://developerslife.ru/";

    @Provides
    @Singleton
    static Gson provideGson() {
        return new GsonBuilder()
                .setLenient()
                .create();
    }

    @Provides
    @Singleton
    static Retrofit provideRetrofit(Gson gson) {
        return new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build();
    }

    @Provides
    @Singleton
    static NetworkHelper provideNetworkHelper(Retrofit retrofit) {
        return new NetworkHelper(retrofit);
    }
}
