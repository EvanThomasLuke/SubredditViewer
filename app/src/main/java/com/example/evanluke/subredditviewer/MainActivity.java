package com.example.evanluke.subredditviewer;

import android.graphics.Movie;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Toast;

import com.loopj.android.http.JsonHttpResponseHandler;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import cz.msebera.android.httpclient.Header;

import java.io.Serializable;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {



    ArrayList<Subreddit> subreddits;
    SubredditAdapter adapter;
    RedditClient client;
    public static final String SUBREDDIT_DETAIL_KEY = "subreddit";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        RecyclerView rvSubreddits = (RecyclerView) findViewById(R.id.rvSubreddits);
        rvSubreddits.setHasFixedSize(true);
        subreddits = new ArrayList<Subreddit>();
        adapter = new SubredditAdapter(this, subreddits);
        rvSubreddits.setAdapter(adapter);

        // Set layout manager to position the items
        rvSubreddits.setLayoutManager(new LinearLayoutManager(this));
/*        GridLayoutManager gridLayoutManager = new GridLayoutManager(this, 2, GridLayoutManager.VERTICAL, false);
        rvMovies.setLayoutManager(gridLayoutManager);*/

        fetchSubreddits("test");

        adapter.setOnItemClickListener(new SubredditAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                //String title = movies.get(position).getName();
                Subreddit clickedSubreddit = subreddits.get(position);
                //TODO add functionality decide get key or url or what to perform api call
                //on the thread
                String clickedSubredditKey = clickedSubreddit.getUrl();
                //Toast.makeText(MainActivity.this, title, Toast.LENGTH_LONG).show();

                //Launch detail view passing movie as an extra
                Intent intent = new Intent(MainActivity.this, SubredditDetailActivity.class);
                //had to cast Movie object to Serializable idk if it will work
                intent.putExtra(SUBREDDIT_DETAIL_KEY, clickedSubredditKey);
                startActivity(intent);

            }
        });
    }


    private void fetchSubreddits(String query) {
        client = new RedditClient();
        client.getSubreddits(query, new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {

                Toast toast = Toast.makeText(MainActivity.this, "Success on api call" , Toast.LENGTH_LONG);
                toast.show();
                try {
                    JSONObject listing = null;
                    //JSONArray docs = null;
                    JSONArray data;
                    if(response != null) {

                        listing = response.getJSONObject("data");
                        data = listing.getJSONArray("children");
                        Toast toast1 = Toast.makeText(MainActivity.this, "Success on updated subreddits" , Toast.LENGTH_LONG);
                        toast1.show();
                        //jsonObject = response.getJSONObject("results");
                        //docs = jsonObject.getJSONArray("results");

                        final ArrayList<Subreddit> updatedSubreddits = Subreddit.fromJson(data);

                        adapter.swap(updatedSubreddits);
                        //using this method we notifyDatasetChanged in the adapter
                        //I dont think we have to do it here


                        //adapter.clear();

                        //movies.addAll()


/*                        for (News newsObject : news) {
                            newsAdapter.add(newsObject);
                        }
                        newsAdapter.notifyDataSetChanged();*/

                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

        });
    }
}
/*



public class MainActivity extends AppCompatActivity {


    //Moved this into onCreate because adapter is init there... wouldn't work here
/*    adapter.setOnItemClickListener(new MoviesAdapter.OnItemClickListener() {
        @Override
        public void onItemClick(View view, int position) {
            String title = movies.get(position).getName();

        }
    });*/
/*
    private void fetchMovies(String query) {
        client = new MovieClient();
        client.getMovies(query, new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                try {
                    JSONArray docs = null;
                    JSONObject jsonObject;
                    if(response != null) {

                        docs = response.getJSONArray("results");

                        //jsonObject = response.getJSONObject("results");
                        //docs = jsonObject.getJSONArray("results");

                        final ArrayList<Movie> updatedMovies = Movie.fromJson(docs);

                        adapter.swap(updatedMovies);
                        //using this method we notifyDatasetChanged in the adapter
                        //I dont think we have to do it here


                        //adapter.clear();

                        //movies.addAll()
/*

                        for (News newsObject : news) {
                            newsAdapter.add(newsObject);
                        }
                        newsAdapter.notifyDataSetChanged();
*/
/*
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

        });
    }
}

 */