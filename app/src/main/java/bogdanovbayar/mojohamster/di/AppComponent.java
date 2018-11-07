package bogdanovbayar.mojohamster.di;

import javax.inject.Singleton;

import bogdanovbayar.mojohamster.ui.MainActivity;
import dagger.Component;

@Singleton
@Component(modules = {
        AppModule.class
})
public interface AppComponent {
    void inject(MainActivity activity);
}
