package com.example.evanluke.subredditviewer;

import android.content.Intent;
import android.graphics.Movie;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import com.loopj.android.http.JsonHttpResponseHandler;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import cz.msebera.android.httpclient.Header;

/**
 * Created by evanluke on 4/16/18.
 */

public class SubredditDetailActivity extends AppCompatActivity {


    ArrayList<Comment> comments;
    CommentAdapter adapter;
    RedditClient client;
    //public static final String SUBREDDIT_DETAIL_KEY = "subreddit";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.subreddit_detail_activity);


        RecyclerView rvComments = (RecyclerView) findViewById(R.id.rvComments);
        rvComments.setHasFixedSize(true);
        comments = new ArrayList<Comment>();
        adapter = new CommentAdapter(this, comments);
        rvComments.setAdapter(adapter);

        // Set layout manager to position the items
        rvComments.setLayoutManager(new LinearLayoutManager(this));
/*        GridLayoutManager gridLayoutManager = new GridLayoutManager(this, 2, GridLayoutManager.VERTICAL, false);
        rvMovies.setLayoutManager(gridLayoutManager);*/

        String intentKey = getIntent().getStringExtra(MainActivity.SUBREDDIT_DETAIL_KEY);

//TODO figure out why it wasn't working this way, just pass it the key now instead of passing whole
        //object
//https://stackoverflow.com/questions/23142893/parcelable-encountered-ioexception-writing-serializable-object-getactivity
//        Intent intent = getIntent().getSerializableExtra(MainActivity.SUBREDDIT_DETAIL_KEY);
  //      Subreddit subreddit = (Subreddit) getIntent().getSerializableExtra(MainActivity.SUBREDDIT_DETAIL_KEY);


//TODO GET INTENT HERE THEN PASS IN ID TO FETCH COMMENTS
        fetchComments(intentKey);

        adapter.setOnItemClickListener(new SubredditAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                //String title = movies.get(position).getName();
               // Subreddit clickedSubreddit = comments.get(position);
                //Toast.makeText(MainActivity.this, title, Toast.LENGTH_LONG).show();

                //Launch detail view passing movie as an extra
                //Intent intent = new Intent(MainActivity.this, SubredditDetailActivity.class);
                //had to cast Movie object to Serializable idk if it will work
                //intent.putExtra(SUBREDDIT_DETAIL_KEY, clickedSubreddit);
                //startActivity(intent);

            }
        });
    }

//TODO FINISH THIS FETCH COMMENTS ()

    private void fetchComments(String query) {
        client = new RedditClient();
        client.getComments(query, new JsonHttpResponseHandler() {
            //TODO changed from JSONObject to JSONArray in args
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONArray response) {

                Toast toast = Toast.makeText(SubredditDetailActivity.this, "Success on api call" , Toast.LENGTH_LONG);
                toast.show();
                try {
                    JSONObject listing = null;
                    //JSONArray docs = null;
                    JSONArray data;
                    if(response != null) {

                        //listing = response.getJSONObject("data");
                        //data = listing.getJSONArray("children");
                        Toast toast1 = Toast.makeText(SubredditDetailActivity.this, "Success on updated subreddits" , Toast.LENGTH_LONG);
                        toast1.show();
                        //jsonObject = response.getJSONObject("results");
                        //docs = jsonObject.getJSONArray("results");

                        final ArrayList<Comment> updatedComments = Comment.fromJson(response);

                        adapter.swap(updatedComments);
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
package com.example.evanluke.movieapp;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import com.loopj.android.http.JsonHttpResponseHandler;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;
import java.util.ArrayList;

import cz.msebera.android.httpclient.Header;

/**
 * Created by evanluke on 4/10/18.
 */
/*
public class MovieDetailActivity extends AppCompatActivity {

    //TODO Add check to make sure it is trailer playing not a clip
    //TODO add check disable onClick if api call fails,
    //TODO add better URL encoding
    //TODO fix UI, make it better looking
    //TODO fix update the star rating properly to display it

    private ImageView movieCover;
    private TextView movieTitle;
    private TextView movieReleaseDate;
    private TextView movieSynopsis;
    private RatingBar movieRating;
    private TextView movieRatingText;
    private Button trailerButton;
    private String movieUrl;
    MovieClient client;
    private String trailerKey;
    private String endUrl;
    private static final String API_BASE_URL_TRAILER = "https://www.youtube.com/watch?v=";
    private static final String API_END_URL_TRAILER = "/videos?api_key=";
    // private String testUrl = "https://www.youtube.com/watch?v=2waTBFJ0oPM";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_detail);
        // Fetch views
        movieCover = (ImageView) findViewById(R.id.imageView);
        movieTitle = (TextView) findViewById(R.id.movieTitle);
        movieReleaseDate = (TextView) findViewById(R.id.releaseDate);
        movieSynopsis = (TextView) findViewById(R.id.synopsis);
        movieRating = (RatingBar) findViewById(R.id.ratingBar);
        movieRatingText = (TextView) findViewById(R.id.ratingBarText);
        trailerButton = (Button) findViewById(R.id.trailerButton);
        Movie movie = (Movie) getIntent().getSerializableExtra(MainActivity.MOVIE_DETAIL_KEY);

        loadMovie(movie);


        trailerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(endUrl));
                startActivity(intent);
            }
        });

    }


    private void loadMovie(Movie movie) {
        Picasso.with(this).load(Uri.parse(movie.getImage())).error(R.drawable.ic_nocover).into(movieCover);
        movieTitle.setText(movie.getName());
        movieReleaseDate.setText(movie.getReleaseDate());
        movieSynopsis.setText(movie.getSynopsis());
        //movieRating.setRating(movie.getRating());
        //
        movieRatingText.setText(String.valueOf(movie.getRating()));
        movieRating.setEnabled(false);
        movieRating.setStepSize(0.1f);
        movieRating.setMax(10);
        movieRating.setRating((float) movie.getRating());
        movieRating.invalidate();
//TODO add from movie object the id, add it dynamically to fetchMovieTrailer()
        fetchMovieTrailer(String.valueOf(movie.getId()));

        //Add button for watch trailer intent
        //need another api call?


    }


    private void fetchMovieTrailer(String query) {
        client = new MovieClient();
        client.getMovieTrailer(query, new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                try {
                    JSONArray docs = null;
                    JSONObject jsonObject;

                    if (response != null) {
                        //Get outer object 'response' then results array in that

                        docs = response.getJSONArray("results");
                        //Get first key,
                        JSONObject firstObject = docs.getJSONObject(0);
                        trailerKey = firstObject.getString("key");
                        Toast toast = Toast.makeText(MovieDetailActivity.this, "Success" + trailerKey, Toast.LENGTH_LONG);
                        toast.show();
                        endUrl = API_BASE_URL_TRAILER + trailerKey;

                        //jsonObject = response.getJSONObject("results");
                        //docs = jsonObject.getJSONArray("results");

                        //final ArrayList<Movie> updatedMovies = Movie.fromJson(docs);

                        //adapter.swap(updatedMovies);
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