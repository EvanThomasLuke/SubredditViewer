package com.example.evanluke.subredditviewer;

import android.graphics.Movie;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Created by evanluke on 4/16/18.
 */

public class Subreddit implements Serializable {
    //TODO decide /research whether use parcelable or serializable what is less memory usage and faster
    private String subredditId;
    private String subreddit;
    private String selfText; // This is below title
    private String id;
    private String author;
    private String thumbnail;
    private JSONObject secureMediaEmbed;
    private JSONObject mediaEmbed;
    private String name;
    private String url;
    private int created;
    private int numComments;
    private String title;
    private boolean isVideo;

    public String getSubredditId() {
        return subredditId;
    }

    public String getSubreddit() {
        return subreddit;
    }

    public String getSelfText() {
        return selfText;
    }

    public String getId() {
        return id;
    }

    public String getAuthor() {
        return author;
    }

    public JSONObject getSecureMediaEmbed() {
        return secureMediaEmbed;
    }

    public JSONObject getMediaEmbed() {
        return mediaEmbed;
    }

    public String getName() {
        return name;
    }

    public String getUrl() {
        return url;
    }

    public int getCreated() {
        return created;
    }

    public int getNumComments() {
        return numComments;
    }

    public String getTitle() {
        return title;
    }

    public boolean getIsVideo() {
        return isVideo;
    }
    public String getThumbnail() {
        return thumbnail;
    }


    public static Subreddit fromJson(JSONObject jsonObject) {
        JSONObject data = null;
        Subreddit a = new Subreddit();
        //ArrayList<Comments> commentsArrayList = new ArrayList<>();
        try {
            data = jsonObject.getJSONObject("data");

            try {
                //Deserialize json into object fields
                a.subredditId = data.getString("subreddit_id");
                a.subreddit = data.getString("subreddit");
                a.selfText = data.getString("selftext");
                a.id = data.getString("id");
                a.author = data.getString("author");
                a.secureMediaEmbed = data.getJSONObject("secure_media_embed");
                a.mediaEmbed = data.getJSONObject("media_embed");
                a.name = data.getString("name");
                a.url = data.getString("url");
                a.created = data.getInt("created");
                a.numComments = data.getInt("num_comments");
                a.title = data.getString("title");
                a.isVideo = data.getBoolean("is_video");

            } catch (JSONException e) {
                e.printStackTrace();
                return null;
            }
        } catch (JSONException e) {
            e.printStackTrace();
            return null;
        }
        //Subreddit data = new Subreddit();
/*
        try {
            //Deserialize json into object fields
            a.subredditId = jsonObject.getString("subreddit_id");
            a.subreddit = jsonObject.getString("subreddit");
            a.selfText = jsonObject.getString("selftext");
            a.id = jsonObject.getString("id");
            a.author = jsonObject.getString("author");
            a.secureMediaEmbed = jsonObject.getJSONObject("secure_media_embed");
            a.mediaEmbed = jsonObject.getJSONObject("media_embed");
            a.name = jsonObject.getString("name");
            a.url = jsonObject.getString("url");
            a.created = jsonObject.getInt("created");
            a.numComments = jsonObject.getInt("num_comments");
            a.title = jsonObject.getString("title");
            a.isVideo = jsonObject.getBoolean("is_video");

        } catch (JSONException e) {
            e.printStackTrace();
            return null;
        }*/
        return a;
    }


    // Decodes array of movie json results into business model objects
    public static ArrayList<Subreddit> fromJson(JSONArray jsonArray) {
        ArrayList<Subreddit> businesses = new ArrayList<Subreddit>(jsonArray.length());
        // Process each result in json array, decode and convert to business object
        for (int i = 0; i < jsonArray.length(); i++) {
            JSONObject businessJson = null;
            try {
                businessJson = jsonArray.getJSONObject(i);
            } catch (Exception e) {
                e.printStackTrace();
                continue;
            }

            Subreddit business = Subreddit.fromJson(businessJson);
            if (business != null) {
                businesses.add(business);
            }
        }

        return businesses;
    }
}

/*



 * Created by evanluke on 4/10/18.
 */
/*
public class Movie implements Serializable {
    private String title;
    private String releaseDate;
    private String image;
    private int id;
    private String synopsis;
    private double rating;
    private static String IMAGE_BASE_URL = "http://image.tmdb.org/t/p/w185//";


/*
    public Movie(String mName, String mReleaseDate, String mImage, String mId, String mSynopsis, double mRating) {
        this.mName = mName;
        this.mReleaseDate = mReleaseDate;
        this.mImage = mImage;
        this.mId = mId;
        this.mSynopsis = mSynopsis;
        this.mRating = mRating;
    }
*/
/*
    public String getName() {
        return title;
    }

    public String getReleaseDate() {
        return releaseDate;
    }

    public String getImage() {

        return IMAGE_BASE_URL + image;
    }

    public int getId() {
        return id;
    }

    public String getSynopsis() {
        return synopsis;
    }

    public double getRating() {
        return rating;
    }

    public static Movie fromJson(JSONObject jsonObject) {
        Movie a = new Movie();
        try {
            //Deserialize json into object fields
            a.title = jsonObject.getString("title");
            a.releaseDate = jsonObject.getString("release_date");
            a.image = jsonObject.getString("poster_path");
            //this is string or int idk
            a.id = jsonObject.getInt("id");
            a.synopsis = jsonObject.getString("overview");
            a.rating = jsonObject.getDouble("vote_average");


        } catch (JSONException e) {
            e.printStackTrace();
            return null;
        }
        return a;
    }


    // Decodes array of movie json results into business model objects
    public static ArrayList<Movie> fromJson(JSONArray jsonArray) {
        ArrayList<Movie> businesses = new ArrayList<Movie>(jsonArray.length());
        // Process each result in json array, decode and convert to business object
        for (int i = 0; i < jsonArray.length(); i++) {
            JSONObject businessJson = null;
            try {
                businessJson = jsonArray.getJSONObject(i);
            } catch (Exception e) {
                e.printStackTrace();
                continue;
            }

            Movie business = Movie.fromJson(businessJson);
            if (business != null) {
                businesses.add(business);
            }
        }

        return businesses;
    }

}

 */