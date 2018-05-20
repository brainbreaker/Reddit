package com.carousell.brainbreaker.reddit;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.carousell.brainbreaker.reddit.Model.Post;
import com.carousell.brainbreaker.reddit.Model.Response;
import com.carousell.brainbreaker.reddit.Retrofit.RedditAPIInterface;
import com.carousell.brainbreaker.reddit.Retrofit.RetrofitAPIClient;

import retrofit2.Call;
import retrofit2.Callback;

public class NewPostActivity extends AppCompatActivity {
    private static final Integer voteCount = 250; // A default highest vote count so that new post appears on the top of our list
    private static final String author = "u/brainbreaker"; // default Reddit user

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_post);

        final EditText title_input = (EditText) findViewById(R.id.input_title);
        final EditText content_input = (EditText) findViewById(R.id.input_content);
        final EditText image_input = (EditText) findViewById(R.id.image_input);
        final EditText subreddit_input = (EditText) findViewById(R.id.subreddit_input);
        Button submitPostButton = (Button) findViewById(R.id.submit_post_button);

        submitPostButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(title_input.getText().toString().equals("")
                        || content_input.getText().toString().equals("")
                        || image_input.getText().toString().equals("")
                        || subreddit_input.getText().toString().equals("")) {

                    Toast.makeText(NewPostActivity.this, getString(R.string.mandatory_text), Toast.LENGTH_SHORT).show();
                } else {
                    // We don't want user to input a string more than 255 chars
                    if(content_input.getText().toString().length() > 255) {
                        Toast.makeText(NewPostActivity.this, getString(R.string.exceeds_char_messagge), Toast.LENGTH_SHORT).show();
                    } else {
                        Post newPost = new Post(title_input.getText().toString()
                                , content_input.getText().toString()
                                , image_input.getText().toString()
                                , subreddit_input.getText().toString()
                                , author
                                , voteCount);

                        sendNewPost(newPost);
                    }
                }
            }
        });
    }

    /**
     * Method to send a new post to our API
     * @param newPost - A new post object
     *
     **/
    private void sendNewPost(Post newPost) {
        Call<Post> call = MainActivity.redditAPIInterface.createNewPost(newPost.getTitle()
                                                                        , newPost.getContent()
                                                                        , newPost.getImage_url()
                                                                        , newPost.getSubreddit()
                                                                        , newPost.getAuthor()
                                                                        , newPost.getVotes());
        call.enqueue(new Callback<Post>() {
            @Override
            public void onResponse(Call<Post> call, retrofit2.Response<Post> response) {
                Toast.makeText(NewPostActivity.this, R.string.post_submitted_successfully, Toast.LENGTH_SHORT).show();
                onBackPressed();
            }

            @Override
            public void onFailure(Call<Post> call, Throwable t) {
                Toast.makeText(NewPostActivity.this, R.string.check_internet, Toast.LENGTH_SHORT).show();
            }
        });
    }

    /**
     * Duplicate method just to test the sending of post without any UI formalities
     * @param newPost - A new post object
     *
     **/
    public static Post sendNewPostTest(Post newPost) {
        final Post[] post = new Post[1];

        Call<Post> call = RetrofitAPIClient.getClient().create(RedditAPIInterface.class).createNewPost(newPost.getTitle()
                , newPost.getContent()
                , newPost.getImage_url()
                , newPost.getSubreddit()
                , newPost.getAuthor()
                , newPost.getVotes());
        call.enqueue(new Callback<Post>() {
            @Override
            public void onResponse(Call<Post> call, retrofit2.Response<Post> response) {
                post[0] =  response.body();
                System.out.println(post[0].getAuthor());
            }

            @Override
            public void onFailure(Call<Post> call, Throwable t) {
                System.out.println(R.string.sending_failed);
            }
        });

        return post[0];
    }
}
