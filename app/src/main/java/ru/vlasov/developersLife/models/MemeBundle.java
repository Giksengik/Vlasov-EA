package ru.vlasov.developersLife.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class MemeBundle {

    @SerializedName("result")
    @Expose
    List<MemePost> posts;

    public List<MemePost> getPosts() {
        return posts;
    }

    public void setPosts(List<MemePost> posts) {
        this.posts = posts;
    }
}
