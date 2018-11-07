package bogdanovbayar.mojohamster.di;

import android.content.Context;

import javax.inject.Singleton;

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
}
