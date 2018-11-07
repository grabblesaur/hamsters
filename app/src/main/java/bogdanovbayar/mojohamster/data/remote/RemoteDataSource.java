package bogdanovbayar.mojohamster.data.remote;

import java.util.ArrayList;

import bogdanovbayar.mojohamster.data.model.Hamster;
import io.reactivex.SingleObserver;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

public class RemoteDataSource {

    private ApiService mApiService;

    public RemoteDataSource(ApiService apiService) {
        mApiService = apiService;
    }

    public void loadHamsters() {
        mApiService.getHamsters()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new SingleObserver<ArrayList<Hamster>>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onSuccess(ArrayList<Hamster> hamsters) {

                    }

                    @Override
                    public void onError(Throwable e) {

                    }
                });
    }
}
