package bogdanovbayar.mojohamster.data.model;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.TypeConverters;
import android.support.annotation.NonNull;

import com.google.gson.annotations.SerializedName;

@Entity(primaryKeys = {"mTitle", "mDescription"})
@TypeConverters(DataTypeConverter.class)
public class Hamster {

    @NonNull
    @SerializedName("title")
    private String mTitle;

    @NonNull
    @SerializedName("description")
    private String mDescription;

    @SerializedName("image")
    private String mImageUrl;

    @NonNull
    public String getTitle() {
        return mTitle;
    }

    @NonNull
    public String getDescription() {
        return mDescription;
    }

    public String getImageUrl() {
        return mImageUrl;
    }

    public void setTitle(@NonNull String title) {
        mTitle = title;
    }

    public void setDescription(@NonNull String description) {
        mDescription = description;
    }

    public void setImageUrl(String imageUrl) {
        mImageUrl = imageUrl;
    }

    @Override
    public String toString() {
        return "Hamster{" +
                "mTitle='" + mTitle + '\'' +
                ", mDescription='" + mDescription + '\'' +
                ", mImageUrl='" + mImageUrl + '\'' +
                '}';
    }
}
