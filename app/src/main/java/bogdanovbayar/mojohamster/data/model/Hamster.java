package bogdanovbayar.mojohamster.data.model;

import com.google.gson.annotations.SerializedName;

public class Hamster {

    @SerializedName("title")
    private String mTitle;

    @SerializedName("description")
    private String mDescription;

    @SerializedName("image")
    private String mImageUrl;

    public String getTitle() {
        return mTitle;
    }

    public String getDescription() {
        return mDescription;
    }

    public String getImageUrl() {
        return mImageUrl;
    }
}
