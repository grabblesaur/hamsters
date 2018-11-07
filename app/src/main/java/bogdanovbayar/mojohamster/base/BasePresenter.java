package bogdanovbayar.mojohamster.base;

import java.net.ConnectException;
import java.net.SocketTimeoutException;
import java.net.UnknownHostException;

import io.reactivex.Single;

public abstract class BasePresenter {

    public abstract void attachView(BaseView view);
    public abstract void detachView();

    public Single onError(Throwable throwable) {
        return Single.create(subscriber -> {
            if (throwable != null && throwable.getMessage() != null) {
                if (throwable instanceof UnknownHostException
                        || throwable instanceof SocketTimeoutException
                        || throwable instanceof ConnectException) {
                    subscriber.onError(new Throwable("Проблемы с интернет соединением"));
                } else {
                    subscriber.onError(throwable);
                }
            }
        });
    }
}
