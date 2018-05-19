package com.carousell.brainbreaker.reddit.Adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.carousell.brainbreaker.reddit.Model.Post;
import com.carousell.brainbreaker.reddit.R;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

/**
 * Created by brainbreaker on 19/05/18.
 */

public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.CustomViewHolder> {

    private Context context;
    private ArrayList<Post> postArrayList;

    public RecyclerViewAdapter(Context context, ArrayList<Post> postArrayList) {
        this.context = context;
        this.postArrayList = postArrayList;
    }

    @NonNull
    @Override
    public RecyclerViewAdapter.CustomViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item, null);

        return new CustomViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerViewAdapter.CustomViewHolder holder, int position) {
        Post currentPost = postArrayList.get(position);

        // Fill our views with incoming data
        Picasso.get()
                .load(currentPost.getImage_url())
                .into(holder.thumbnail);
        holder.title.setText(currentPost.getTitle());
        holder.content.setText(currentPost.getContent());
        holder.votesCount.setText(currentPost.getContent());

        holder.upvoteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });

        holder.downvoteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });

        holder.shareButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });
    }

    @Override
    public int getItemCount() {
        return postArrayList != null ? postArrayList.size() : 0;
    }


    class CustomViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        // Holder class containing a member variable for any view that will be rendered on a row

        CardView cardView;

        ImageView thumbnail;
        TextView title;
        TextView content;
        TextView votesCount;
        ImageButton upvoteButton;
        ImageButton downvoteButton;
        ImageButton shareButton;

        // Write a constructor that accepts the entire item row and does the view lookups to find each subview
        CustomViewHolder(View itemView) {
            // Store the itemView in a public final member variable that can be used to access the context from any ViewHolder instance.
            super(itemView);

            thumbnail = (ImageView) itemView.findViewById(R.id.thumbnail);
            title = (TextView) itemView.findViewById(R.id.post_title);
            content = (TextView) itemView.findViewById(R.id.post_content);
            votesCount = (TextView) itemView.findViewById(R.id.vote_count);
            upvoteButton = (ImageButton) itemView.findViewById(R.id.upvote_button);
            downvoteButton = (ImageButton) itemView.findViewById(R.id.downvote_button);
            shareButton = (ImageButton) itemView.findViewById(R.id.share_button);
            cardView = (CardView) itemView.findViewById(R.id.card_view);

            cardView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            int clickedPosition = getAdapterPosition();
//            Toast.makeText(context, dataList.get(clickedPosition).getName(), Toast.LENGTH_LONG).show();
        }
    }
}
