package com.carousell.brainbreaker.reddit.Model;

import com.google.gson.annotations.SerializedName;

/**
 * Created by brainbreaker on 19/05/18.
 */

public class Post {

    @SerializedName("_id")
    private String postID;

    @SerializedName("title")
    private String title;
    @SerializedName("content")
    private String content;

    @SerializedName("subreddit")
    private String subreddit;

    @SerializedName("author")
    private String author;

    @SerializedName("image_url")
    private String image_url;

    @SerializedName("votes")
    private Integer votes;

    // Constructor for creating a new post
    public Post(String title
                , String content
                , String subreddit
                , String author
                , String image_url
                , Integer votes) {

        this.title = title;
        this.content = content;
        this.subreddit = subreddit;
        this.author = author;
        this.image_url = image_url;
        this.votes = votes;
    }

    // Constructor with Post ID, to be used by GSON so as to get us the ID of incoming post
    public Post(String postID
            , String title
            , String content
            , String subreddit
            , String author
            , String image_url
            , Integer votes) {

        this.postID = postID;
        this.title = title;
        this.content = content;
        this.subreddit = subreddit;
        this.author = author;
        this.image_url = image_url;
        this.votes = votes;
    }

    public String getPostID() {
        return postID;
    }

    public String getTitle() {
        return title;
    }

    public String getContent() {
        return content;
    }

    public String getSubreddit() {
        return subreddit;
    }

    public String getAuthor() {
        return author;
    }

    public String getImage_url() {
        return image_url;
    }

    public Integer getVotes() {
        return votes;
    }
}
