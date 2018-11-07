package bogdanovbayar.mojohamster.data.remote;

import java.util.ArrayList;

import bogdanovbayar.mojohamster.data.model.Hamster;
import io.reactivex.Single;
import retrofit2.http.GET;

public interface ApiService {

    @GET("test3")
    Single<ArrayList<Hamster>> getHamsters();

}
