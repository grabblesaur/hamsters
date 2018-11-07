package bogdanovbayar.mojohamster.di;

public class Application extends android.app.Application {

    private static AppComponent mApplicationComponent;

    @Override
    public void onCreate() {
        super.onCreate();
        mApplicationComponent = createComponent();
    }

    private AppComponent createComponent() {
        return DaggerAppComponent.builder()
                .appModule(new AppModule(this))
                .build();
    }

    public static AppComponent getAppComponent() {
        return mApplicationComponent;
    }
}
