package tech.test.surveys.dao;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

/**
 * Created by GOLF on 7/18/2016.
 */
public class SurveyItemDao implements Parcelable{
    @SerializedName("title")            private  String title;
    @SerializedName("description")      private  String description;
    @SerializedName("cover_image_url")  private  String coverImageUrl;

    public SurveyItemDao(){

    }

    protected SurveyItemDao(Parcel in) {
        title = in.readString();
        description = in.readString();
        coverImageUrl = in.readString();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(title);
        dest.writeString(description);
        dest.writeString(coverImageUrl);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<SurveyItemDao> CREATOR = new Creator<SurveyItemDao>() {
        @Override
        public SurveyItemDao createFromParcel(Parcel in) {
            return new SurveyItemDao(in);
        }

        @Override
        public SurveyItemDao[] newArray(int size) {
            return new SurveyItemDao[size];
        }
    };

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getCoverImageUrl() {
        return coverImageUrl;
    }

    public void setCoverImageUrl(String coverImageUrl) {
        this.coverImageUrl = coverImageUrl;
    }
}
