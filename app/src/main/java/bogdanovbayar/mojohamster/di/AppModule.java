package bogdanovbayar.mojohamster.di;

import android.arch.persistence.room.Room;
import android.content.Context;

import javax.inject.Singleton;

import bogdanovbayar.mojohamster.BuildConfig;
import bogdanovbayar.mojohamster.data.MainRepository;
import bogdanovbayar.mojohamster.data.local.AppDataBase;
import bogdanovbayar.mojohamster.data.local.HamsterDao;
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
    public AppDataBase provideRoomDatabase(Context context) {
        return Room.databaseBuilder(context, AppDataBase.class, BuildConfig.APPLICATION_ID)
                .fallbackToDestructiveMigration()
                .allowMainThreadQueries()
                .build();
    }

    @Singleton
    @Provides
    public HamsterDao provideCatalogDao(AppDataBase appDataBase) {
        return appDataBase.hamsterDao();
    }

    @Singleton
    @Provides
    public LocalDataSource provideLocalDataSource(HamsterDao hamsterDao) {
        return new LocalDataSource(hamsterDao);
    }

    @Singleton
    @Provides
    public MainRepository provideMainRepository(Context context,
                                                RemoteDataSource remoteDataSource,
                                                LocalDataSource localDataSource) {
        return new MainRepository(context, remoteDataSource, localDataSource);
    }
}
