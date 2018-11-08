package bogdanovbayar.mojohamster.data;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.util.Log;

import java.util.List;

import bogdanovbayar.mojohamster.data.local.LocalDataSource;
import bogdanovbayar.mojohamster.data.model.Hamster;
import bogdanovbayar.mojohamster.data.remote.RemoteDataSource;

public class MainRepository {

    private static final String TAG = MainRepository.class.getSimpleName();

    private RemoteDataSource mRemoteDataSource;
    private LocalDataSource mLocalDataSource;
    private Context mContext;

    public MainRepository(Context context, RemoteDataSource remoteDataSource, LocalDataSource localDataSource) {
        mContext = context;
        mRemoteDataSource = remoteDataSource;
        mLocalDataSource = localDataSource;
    }

    public void getData(HamsterLoadCallback callback) {
        // if !isOnline then get local data, otherwise get remote data
        if (isOnline()) {
            getRemoteData(callback);
        } else {
            getLocalData(callback);
        }

    }

    public void getRemoteData(HamsterLoadCallback callback) {
        Log.i(TAG, "getRemoteData: ");
        mRemoteDataSource.loadHamsters(new HamsterLoadCallback() {
            @Override
            public void onHamsterLoaded(List<Hamster> hamsterList) {
                saveLocalData(hamsterList);
                callback.onHamsterLoaded(hamsterList);
            }

            @Override
            public void onError(Throwable throwable) {
                callback.onError(throwable);
            }
        });
    }

    public void getLocalData(HamsterLoadCallback callback) {
        Log.i(TAG, "getLocalData: ");
        mLocalDataSource.getData(new HamsterLoadCallback() {
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

    public void saveLocalData(List<Hamster> hamsterList) {
        Log.i(TAG, "saveLocalData: ");
        mLocalDataSource.writeData(hamsterList);
    }

    private boolean isOnline() {
        ConnectivityManager cm =
                (ConnectivityManager) mContext.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo netInfo = cm.getActiveNetworkInfo();
        return netInfo != null && netInfo.isConnectedOrConnecting();
    }
}
