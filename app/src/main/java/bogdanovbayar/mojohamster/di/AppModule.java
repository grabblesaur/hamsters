package bogdanovbayar.mojohamster.di;

import android.content.Context;

import javax.inject.Singleton;

import bogdanovbayar.mojohamster.data.MainRepository;
import bogdanovbayar.mojohamster.data.local.LocalDataSource;
import bogdanovbayar.mojohamster.data.remote.ApiService;
import bogdanovbayar.mojohamster.data.remote.RemoteDataSource;
import dagger.Module;
import dagger.Provides;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

@Module
public class AppModule {

    private Application mApp;

    public AppModule(Application app) {
        mApp = app;
    }

    @Singleton
    @Provides
    public Context provideContext() {
        return mApp.getApplicationContext();
    }

    @Singleton
    @Provides
    public Retrofit provideRetrofit() {
        String BASE_URL = "https://www.unrealmojo.com/porn/";
        return new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .build();
    }

    @Singleton
    @Provides
    public ApiService provideApiService(Retrofit retrofit) {
        return retrofit.create(ApiService.class);
    }

    @Singleton
    @Provides
    public RemoteDataSource provideRemoteDataSource(ApiService apiService) {
        return new RemoteDataSource(apiService);
    }

    @Singleton
    @Provides
    public LocalDataSource provideLocalDataSource() {
        return new LocalDataSource();
    }

    @Singleton
    @Provides
    public MainRepository provideMainRepository(RemoteDataSource remoteDataSource,
                                                LocalDataSource localDataSource) {
        return new MainRepository(remoteDataSource, localDataSource);
    }
}
