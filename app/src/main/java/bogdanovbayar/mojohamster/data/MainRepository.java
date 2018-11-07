package bogdanovbayar.mojohamster.data;

import java.util.List;

import javax.inject.Inject;

import bogdanovbayar.mojohamster.data.local.LocalDataSource;
import bogdanovbayar.mojohamster.data.model.Hamster;
import bogdanovbayar.mojohamster.data.remote.RemoteDataSource;

public class MainRepository {

    private RemoteDataSource mRemoteDataSource;
    private LocalDataSource mLocalDataSource;

    public MainRepository(RemoteDataSource remoteDataSource, LocalDataSource localDataSource) {
        mRemoteDataSource = remoteDataSource;
        mLocalDataSource = localDataSource;
    }

    public void getData(HamsterLoadCallback callback) {
        mRemoteDataSource.loadHamsters(new HamsterLoadCallback() {
            @Override
            public void onHamsterLoaded(List<Hamster> hamsterList) {
                callback.onHamsterLoaded(hamsterList);
            }

            @Override
            public void onError(Throwable throwable) {
                callback.onError(throwable);
            }
        });
    }
}
