package bogdanovbayar.mojohamster.data;

import java.util.List;

import bogdanovbayar.mojohamster.data.model.Hamster;

public interface HamsterLoadCallback {
    void onHamsterLoaded(List<Hamster> hamsterList);
    void onError(Throwable throwable);
}
