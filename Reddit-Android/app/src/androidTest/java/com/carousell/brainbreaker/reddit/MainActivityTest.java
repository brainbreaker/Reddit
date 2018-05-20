package com.carousell.brainbreaker.reddit;

import android.support.test.espresso.UiController;
import android.support.test.espresso.ViewAction;
import android.support.test.espresso.contrib.RecyclerViewActions;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.carousell.brainbreaker.reddit.Adapter.RecyclerViewAdapter;

import org.hamcrest.Matcher;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.RootMatchers.withDecorView;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static org.hamcrest.Matchers.greaterThan;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.not;
import static org.hamcrest.Matchers.notNullValue;
import static org.hamcrest.core.IsInstanceOf.instanceOf;
import static org.junit.Assert.assertThat;

/**
 * Instrumented test, which will execute on an Android device.
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
@RunWith(AndroidJUnit4.class)
public class MainActivityTest {
    @Rule
    public ActivityTestRule activityTestRule = new ActivityTestRule<>(MainActivity.class);

    @Test
    public void recyclerViewTest() throws Exception {
        MainActivity activity = (MainActivity) activityTestRule.getActivity();
        View viewById = activity.findViewById(R.id.postsRecyclerView);

        // View should not be null
        assertThat(viewById, notNullValue());

        // View should be an instance of RecyclerView class
        assertThat(viewById, instanceOf(RecyclerView.class));

        RecyclerView postRecyclerView = (RecyclerView) viewById;
        RecyclerView.Adapter adapter = postRecyclerView.getAdapter();

        // Adapter should be an instance of RecyclerViewAdapter
        assertThat(adapter, instanceOf(RecyclerViewAdapter.class));

        // Number of items populated by adapter should be greater than 15
        assertThat(adapter.getItemCount(), greaterThan(15));

        // Click on the upvote button at first position and check if toast is displayed
        onView(withId(R.id.postsRecyclerView)).perform(RecyclerViewActions.actionOnItemAtPosition(0, new ClickOnUpvote()))
                .inRoot(withDecorView(
                not(is(activityTestRule.getActivity().
                        getWindow().getDecorView())))).
                check(matches(isDisplayed()));

        // Click on downvote button at first position and check if toast is displayed
        onView(withId(R.id.postsRecyclerView)).perform(RecyclerViewActions.actionOnItemAtPosition(0, new ClickOnDownvote()))
                .inRoot(withDecorView(
                        not(is(activityTestRule.getActivity().
                                getWindow().getDecorView())))).
                check(matches(isDisplayed()));
    }

    // Custom class to click within a particular view
    public class ClickOnUpvote implements ViewAction {
        ViewAction click = click();

        @Override
        public Matcher<View> getConstraints() {
            return click.getConstraints();
        }

        @Override
        public String getDescription() {
            return activityTestRule.getActivity().getString(R.string.click_upvote_String);
        }

        @Override
        public void perform(UiController uiController, View view) {
            click.perform(uiController, view.findViewById(R.id.upvote_button));
        }
    }

    public class ClickOnDownvote implements ViewAction {
        ViewAction click = click();

        @Override
        public Matcher<View> getConstraints() {
            return click.getConstraints();
        }

        @Override
        public String getDescription() {
            return activityTestRule.getActivity().getString(R.string.click_downvote_string);
        }

        @Override
        public void perform(UiController uiController, View view) {
            click.perform(uiController, view.findViewById(R.id.downvote_button));
        }
    }
}


