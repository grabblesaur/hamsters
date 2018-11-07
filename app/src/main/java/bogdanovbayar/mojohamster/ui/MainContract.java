package bogdanovbayar.mojohamster.ui;

import java.util.List;

import bogdanovbayar.mojohamster.base.BasePresenter;
import bogdanovbayar.mojohamster.base.BaseView;
import bogdanovbayar.mojohamster.data.model.Hamster;

public interface MainContract {

    interface Presenter extends BasePresenter {
        void getData();
    }

    interface MainView extends BaseView {
        void showData(List<Hamster> hamsterList);
    }

}
