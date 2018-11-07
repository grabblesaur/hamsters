package bogdanovbayar.mojohamster.data.remote;

import android.util.Log;

import java.util.ArrayList;

import javax.inject.Inject;

import bogdanovbayar.mojohamster.data.HamsterLoadCallback;
import bogdanovbayar.mojohamster.data.model.Hamster;
import io.reactivex.SingleObserver;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

public class RemoteDataSource {

    private static final String TAG = RemoteDataSource.class.getSimpleName();

    private ApiService mApiService;

    public RemoteDataSource(ApiService apiService) {
        mApiService = apiService;
    }

    public void loadHamsters(HamsterLoadCallback callback) {
        mApiService.getHamsters()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new SingleObserver<ArrayList<Hamster>>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onSuccess(ArrayList<Hamster> hamsters) {
                        callback.onHamsterLoaded(hamsters);
                    }

                    @Override
                    public void onError(Throwable e) {
                        callback.onError(e);
                    }
                });
    }
}
