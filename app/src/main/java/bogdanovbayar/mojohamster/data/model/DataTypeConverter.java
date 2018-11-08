package bogdanovbayar.mojohamster.data.model;

import android.arch.persistence.room.TypeConverter;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

public class DataTypeConverter {
    private static Gson gson = new Gson();

    @TypeConverter
    public static ArrayList<Hamster> stringToList(String data) {
        if (data == null) {
            return new ArrayList<>();
        }
        Type listType = new TypeToken<List<Hamster>>() {}.getType();
        return gson.fromJson(data, listType);
    }

    @TypeConverter
    public static String ListToString(List<Hamster> hamsterList) {
        return gson.toJson(hamsterList);
    }
}
