package com.carousell.brainbreaker.reddit;

import com.carousell.brainbreaker.reddit.Adapter.RecyclerViewAdapter;
import com.carousell.brainbreaker.reddit.Model.Post;

import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
public class UpvoteDownvoteTest {
    private static final String id = "5b018271a6b9e6002069685f";
    @Test
    public void sendNewPost() throws Exception {
        // Upvote the post
        String upvoteResult = RecyclerViewAdapter.votePostTest(id, true);

        // Assert that received String is not equal to Error
        assertNotEquals(upvoteResult, "Error");

        // Now downvote the same post
        String downvoteResult = RecyclerViewAdapter.votePostTest(id, false);

        // Assert that received String is not equal to Error
        assertNotEquals(downvoteResult, "Error");
    }
}