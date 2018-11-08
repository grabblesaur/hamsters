package bogdanovbayar.mojohamster.data.local;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;

import java.util.List;

import bogdanovbayar.mojohamster.data.model.Hamster;
import io.reactivex.Single;

@Dao
public interface HamsterDao {

    @Query("SELECT * FROM hamster")
    Single<List<Hamster>> getAllHamsters();

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertHamster(Hamster hamster);

}
