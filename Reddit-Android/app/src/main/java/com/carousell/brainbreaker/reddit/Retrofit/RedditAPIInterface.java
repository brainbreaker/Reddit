package com.carousell.brainbreaker.reddit.Retrofit;

import com.carousell.brainbreaker.reddit.Model.Post;
import com.carousell.brainbreaker.reddit.Model.Response;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;

/**
 * Created by brainbreaker on 19/05/18.
 */

public interface RedditAPIInterface {

    // Get me top 20 posts sorted by votes count
    @GET("/posts")
    Call<ArrayList<Post>> getAllPosts();

    // Submit a new post
    @FormUrlEncoded
    @POST("/posts")
    Call<Response> createNewPost(@Field("title") String title
            , @Field("content") String content
            , @Field("image_url") String image_url
            , @Field("subreddit") String subreddit
            , @Field("author") String author
            , @Field("votes") Integer votes);

    // Upvote a post
    @PUT("/posts/{postID}/upvote")
    Call<Response> upvotePost(@Path("postID") String postID);

    // Downvote a post
    @PUT("/posts/{postID}/downvote")
    Call<Response> downvotePost(@Path("postID") String postID);

}
