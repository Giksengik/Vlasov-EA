package ru.vlasov.developersLife.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class MemePost {

    @SerializedName("id")
    @Expose
    int id;

    @SerializedName("description")
    @Expose
    String description;

    @SerializedName("votes")
    @Expose
    int votes;

    @SerializedName("author")
    @Expose
    String author;

    @SerializedName("date")
    @Expose
    String date;

    @SerializedName("gifURL")
    @Expose
    String gifURL;

    @SerializedName("gifSize")
    @Expose
    int gifSize;

    @SerializedName("previewURL")
    @Expose
    String previewURL;

    @SerializedName("videoURL")
    @Expose
    String videoURL;

    @SerializedName("videoPath")
    @Expose
    String videoPath;

    @SerializedName("videoSize")
    @Expose
    int videoSize;

    @SerializedName("type")
    @Expose
    String type;

    @SerializedName("width")
    @Expose
    double width;

    @SerializedName("height")
    @Expose
    double height;

    @SerializedName("commentsCount")
    @Expose
    int commentsCount;

    @SerializedName("fileSize")
    @Expose
    int fileSize;

    @SerializedName("cavVote")
    @Expose
    boolean canVote;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getVotes() {
        return votes;
    }

    public void setVotes(int votes) {
        this.votes = votes;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getGifURL() {
        return gifURL;
    }

    public void setGifURL(String gifURL) {
        this.gifURL = gifURL;
    }

    public int getGifSize() {
        return gifSize;
    }

    public void setGifSize(int gifSize) {
        this.gifSize = gifSize;
    }

    public String getPreviewURL() {
        return previewURL;
    }

    public void setPreviewURL(String previewURL) {
        this.previewURL = previewURL;
    }
}
