package bogdanovbayar.mojohamster.ui.main;

import java.util.Comparator;

import bogdanovbayar.mojohamster.data.model.Hamster;

public class PinnedHamsterComparator implements Comparator<Hamster> {
    @Override
    public int compare(Hamster hamster, Hamster t1) {
        return Boolean.compare(t1.isPinned(), hamster.isPinned());
    }
}
