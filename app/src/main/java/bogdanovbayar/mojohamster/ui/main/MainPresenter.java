package bogdanovbayar.mojohamster.ui.main;

import java.util.List;

import javax.inject.Inject;

import bogdanovbayar.mojohamster.base.BaseView;
import bogdanovbayar.mojohamster.data.HamsterLoadCallback;
import bogdanovbayar.mojohamster.data.MainRepository;
import bogdanovbayar.mojohamster.data.model.Hamster;

public class MainPresenter implements MainContract.Presenter {

    private MainRepository mRepository;
    private MainContract.MainView mView;

    @Inject
    public MainPresenter(MainRepository repository) {
        mRepository = repository;
    }

    @Override
    public void getData() {
        mRepository.getData(new HamsterLoadCallback() {
            @Override
            public void onHamsterLoaded(List<Hamster> hamsterList) {
                mView.showData(hamsterList);
            }

            @Override
            public void onError(Throwable throwable) {
                mView.showError(throwable.getMessage());
            }
        });
    }

    @Override
    public void attachView(BaseView view) {
        mView = (MainContract.MainView) view;
    }

    @Override
    public void detachView() {
        mView = null;
    }
}
