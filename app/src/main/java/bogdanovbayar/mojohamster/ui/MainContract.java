package bogdanovbayar.mojohamster.ui;

import bogdanovbayar.mojohamster.base.BasePresenter;
import bogdanovbayar.mojohamster.base.BaseView;

public interface MainContract {

    abstract class Presenter extends BasePresenter {
        abstract void getData();
    }

    interface MainView extends BaseView {

    }

}
