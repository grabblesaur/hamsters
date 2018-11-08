package bogdanovbayar.mojohamster.data.local;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.RoomDatabase;

import bogdanovbayar.mojohamster.data.model.Hamster;

@Database(entities = { Hamster.class }, version = 1)
public abstract class AppDataBase extends RoomDatabase {
    public abstract HamsterDao hamsterDao();
}
