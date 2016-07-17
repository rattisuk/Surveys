package tech.test.surveys.dao;

import com.google.gson.annotations.SerializedName;

/**
 * Created by GOLF on 7/18/2016.
 */
public class SurveyItemDao {
    @SerializedName("title")            private  String title;
    @SerializedName("description")      private  String description;
    @SerializedName("cover_image_url")  private  String coverImageUrl;

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
