package com.carousell.brainbreaker.reddit.Adapter;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.carousell.brainbreaker.reddit.Model.Post;
import com.carousell.brainbreaker.reddit.Model.Response;
import com.carousell.brainbreaker.reddit.R;
import com.carousell.brainbreaker.reddit.Retrofit.RedditAPIInterface;
import com.carousell.brainbreaker.reddit.Retrofit.RetrofitAPIClient;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;


/**
 * Created by brainbreaker on 19/05/18.
 */

public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.CustomViewHolder> {

    private Context context;
    private ArrayList<Post> postArrayList;
    private RedditAPIInterface redditAPIInterface;

    public RecyclerViewAdapter(Context context, ArrayList<Post> postArrayList) {
        this.context = context;
        this.postArrayList = postArrayList;
        redditAPIInterface = RetrofitAPIClient.getClient().create(RedditAPIInterface.class);
    }

    @NonNull
    @Override
    public RecyclerViewAdapter.CustomViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item, null);

        return new CustomViewHolder(view);
    }

    @Override
    public int getItemCount() {
        return postArrayList != null ? postArrayList.size() : 0;
    }

    @Override
    public void onBindViewHolder(@NonNull final RecyclerViewAdapter.CustomViewHolder holder, int position) {
        final Post currentPost = postArrayList.get(position);

        // Fill our views with incoming data
        Picasso.get()
                .load(currentPost.getImage_url())
                .into(holder.thumbnail);
        holder.title.setText(currentPost.getTitle());
        holder.content.setText(currentPost.getContent());
        holder.votesCount.setText(String.valueOf(currentPost.getVotes()));

        // Open the content in Android Browser when tapped on Thumbnail
        holder.thumbnail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(currentPost.getContent()));
                context.startActivity(browserIntent);
            }
        });

        // Upvote when clicked on upvote button
        holder.upvoteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                upvotePost(currentPost.getPostID(), holder.votesCount);
            }
        });

        // Downvote when clicked on downvote button
        holder.downvoteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                downvotePost(currentPost.getPostID(), holder.votesCount);
            }
        });

        // // Share when clicked on share button
        holder.shareButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                sharePost(currentPost.getTitle(), currentPost.getContent());
            }
        });
    }

    /**
     * Function that sends a request to upvote the post
     * @param postID - ID of the post to be upvoted
     * @param votesCount - TextView showing the vote count
     *
     * */
    private void upvotePost(String postID, final TextView votesCount) {
        Call<Response> call = redditAPIInterface.upvotePost(postID);
        call.enqueue(new Callback<Response>() {
            @Override
            public void onResponse(Call<Response> call, retrofit2.Response<Response> response) {
                Response messageResponse = response.body();
                votesCount.setText(String.valueOf(Integer.parseInt(votesCount.getText() + "") + 1));
                Toast.makeText(context, messageResponse.getMessage(), Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onFailure(Call<Response> call, Throwable t) {
                Toast.makeText(context, R.string.check_internet, Toast.LENGTH_SHORT).show();
            }
        });

    }

    /**
     * Function that sends a request to downvote the post
     * @param postID - ID of the post to be downvoted
     * @param votesCount - TextView showing the vote count
     *
     * */
    private void downvotePost(String postID, final TextView votesCount) {
        Call<Response> call = redditAPIInterface.downvotePost(postID);
        call.enqueue(new Callback<Response>() {
            @Override
            public void onResponse(Call<Response> call, retrofit2.Response<Response> response) {
                Response messageResponse = response.body();
                votesCount.setText(String.valueOf(Integer.parseInt(votesCount.getText() + "") - 1));
                Toast.makeText(context, messageResponse.getMessage(), Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onFailure(Call<Response> call, Throwable t) {
                Toast.makeText(context, R.string.check_internet, Toast.LENGTH_SHORT).show();
            }
        });
    }

    /**
     * Function to share the post via messaging apps
     * @param title - Title/Subject of the Message
     * @param content - Content Link
     *
     * */
    private void sharePost(String title, String content) {
        Intent sharingIntent = new Intent(android.content.Intent.ACTION_SEND);
        sharingIntent.setType("text/plain");
        sharingIntent.putExtra(android.content.Intent.EXTRA_SUBJECT, title);
        sharingIntent.putExtra(android.content.Intent.EXTRA_TEXT, content);
        context.startActivity(Intent.createChooser(sharingIntent, context.getResources().getString(R.string.share_using)));
    }


    class CustomViewHolder extends RecyclerView.ViewHolder {
        // Holder class containing a member variable for any view that will be rendered on a row

        ImageView thumbnail;
        TextView title;
        TextView content;
        TextView votesCount;
        ImageView upvoteButton;
        ImageView downvoteButton;
        ImageView shareButton;

        // Write a constructor that accepts the entire item row and does the view lookups to find each subview
        CustomViewHolder(View itemView) {
            // Store the itemView in a public final member variable that can be used to access the context from any ViewHolder instance.
            super(itemView);

            thumbnail = (ImageView) itemView.findViewById(R.id.thumbnail);
            title = (TextView) itemView.findViewById(R.id.post_title);
            content = (TextView) itemView.findViewById(R.id.post_content);
            votesCount = (TextView) itemView.findViewById(R.id.vote_count);
            upvoteButton = (ImageView) itemView.findViewById(R.id.upvote_button);
            downvoteButton = (ImageView) itemView.findViewById(R.id.downvote_button);
            shareButton = (ImageView) itemView.findViewById(R.id.share_button);
        }

    }
}
