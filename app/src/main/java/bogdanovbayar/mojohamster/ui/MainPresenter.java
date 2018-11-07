package bogdanovbayar.mojohamster.ui;

import bogdanovbayar.mojohamster.base.BaseView;
import bogdanovbayar.mojohamster.data.MainRepository;

public class MainPresenter extends MainContract.Presenter {

    private MainRepository mRepository;

    public MainPresenter(MainRepository repository) {
        mRepository = repository;
    }

    @Override
    void getData() {
        mRepository.getData();
    }

    @Override
    public void attachView(BaseView view) {

    }

    @Override
    public void detachView() {

    }
}
