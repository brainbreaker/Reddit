package com.carousell.brainbreaker.reddit;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.carousell.brainbreaker.reddit.Adapter.RecyclerViewAdapter;
import com.carousell.brainbreaker.reddit.Model.Post;
import com.carousell.brainbreaker.reddit.Retrofit.RedditAPIInterface;
import com.carousell.brainbreaker.reddit.Retrofit.RetrofitAPIClient;
import com.ontbee.legacyforks.cn.pedant.SweetAlert.SweetAlertDialog;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {

    public static RedditAPIInterface redditAPIInterface;
    private RecyclerViewAdapter recyclerViewAdapter;
    RecyclerView postsRecyclerView;
    SweetAlertDialog pDialog;

    /**
     * ArrayList of Posts to be fetched from server. This acts as an in-memory data structure to hold the posts till the
     * application is running.
     * */
    ArrayList<Post> postArrayList;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        // Instantiate the Recycler View and set LayoutManager
        postsRecyclerView = (RecyclerView) findViewById(R.id.postsRecyclerView);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        postsRecyclerView.setLayoutManager(linearLayoutManager);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.create_post_fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, NewPostActivity.class);
                startActivity(intent);
            }
        });

        postArrayList = new ArrayList<>();

        // Instantiate the reddit API interface
        redditAPIInterface = RetrofitAPIClient.getClient().create(RedditAPIInterface.class);

        // Perform the network operation only if network is available.
        if (isNetworkAvailable(this)) {
            fetchAllPosts();
        } else {
            Toast.makeText(this, getString(R.string.check_internet), Toast.LENGTH_SHORT).show();
            finish();
        }
    }

    /**
     * Fetch the top 20 posts sorted by votes from our API
     * and populate the recycler view
     */
    private void fetchAllPosts() {
        showProgressDialog();

        Call<ArrayList<Post>> call = redditAPIInterface.getAllPosts();
        call.enqueue(new Callback<ArrayList<Post>>() {
            @Override
            public void onResponse(Call<ArrayList<Post>> call, Response<ArrayList<Post>> response) {
                postArrayList = response.body();
                dismissProgressDialog();
                recyclerViewAdapter = new RecyclerViewAdapter(MainActivity.this, postArrayList);
                postsRecyclerView.setAdapter(recyclerViewAdapter);
            }

            @Override
            public void onFailure(Call<ArrayList<Post>> call, Throwable t) {
                showErrorDialog();
            }
        });
    }


    /**
     * A function to check whether we're connected to internet or not
     */
    public boolean isNetworkAvailable(Context context) {
        ConnectivityManager connectivityManager
                = (ConnectivityManager)context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isConnected();
    }

    /**
     * Shows a sweetalert progress dialog
     */
    public void showProgressDialog() {
        pDialog = new SweetAlertDialog(this, SweetAlertDialog.PROGRESS_TYPE);
        pDialog.getProgressHelper().setBarColor(Color.parseColor("#A5DC86"));
        pDialog.setTitleText(getString(R.string.loading_Text));
        pDialog.setCancelable(false);
        pDialog.show();
    }

    /**
     * Dismiss the sweetalert progress dialog
     */
    public void dismissProgressDialog() {
        if(pDialog.isShowing()) {
            pDialog.dismissWithAnimation();
        }
    }

    /**
     * Shows a sweetalert error dialog
     */
    public void showErrorDialog() {
        new SweetAlertDialog(this, SweetAlertDialog.ERROR_TYPE)
                .setTitleText(getString(R.string.oops_string))
                .setContentText(this.getString(R.string.check_internet))
                .show();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_refresh) {
            fetchAllPosts();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
