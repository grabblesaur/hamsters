package bogdanovbayar.mojohamster.data.model;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.TypeConverters;
import android.support.annotation.NonNull;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

@Entity(primaryKeys = {"mTitle", "mDescription"})
@TypeConverters(DataTypeConverter.class)
public class Hamster implements Serializable {

    @NonNull
    @SerializedName("title")
    private String mTitle;

    @SerializedName("pinned")
    private boolean mPinned;

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

    public boolean isPinned() {
        return mPinned;
    }

    public void setPinned(boolean pinned) {
        mPinned = pinned;
    }

    @Override
    public String toString() {
        return "Hamster{" +
                "mTitle='" + mTitle + '\'' +
                ", mPinned=" + mPinned +
                ", mDescription='" + mDescription + '\'' +
                ", mImageUrl='" + mImageUrl + '\'' +
                '}';
    }
}
