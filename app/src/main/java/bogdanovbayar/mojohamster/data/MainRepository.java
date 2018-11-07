package bogdanovbayar.mojohamster.data;

import bogdanovbayar.mojohamster.data.local.LocalDataSource;
import bogdanovbayar.mojohamster.data.remote.RemoteDataSource;

public class MainRepository {

    private RemoteDataSource mRemoteDataSource;
    private LocalDataSource mLocalDataSource;

    public MainRepository(RemoteDataSource remoteDataSource, LocalDataSource localDataSource) {
        mRemoteDataSource = remoteDataSource;
        mLocalDataSource = localDataSource;
    }

    public void getData() {


    }
}
