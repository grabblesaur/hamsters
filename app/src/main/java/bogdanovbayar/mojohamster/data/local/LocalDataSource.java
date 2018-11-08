package bogdanovbayar.mojohamster.data.local;

import java.util.List;

import bogdanovbayar.mojohamster.data.HamsterLoadCallback;
import bogdanovbayar.mojohamster.data.model.Hamster;
import io.reactivex.SingleObserver;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

public class LocalDataSource {

    private HamsterDao mDao;

    public LocalDataSource(HamsterDao dao) {
        mDao = dao;
    }

    public void getData(HamsterLoadCallback callback) {
        mDao.getAllHamsters()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new SingleObserver<List<Hamster>>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onSuccess(List<Hamster> hamsters) {
                        callback.onHamsterLoaded(hamsters);
                    }

                    @Override
                    public void onError(Throwable e) {
                        callback.onError(e);
                    }
                });
    }

    public void writeData(List<Hamster> hamsterList) {
        for (Hamster hamster : hamsterList) {
            mDao.insertHamster(hamster);
        }
    }
}
