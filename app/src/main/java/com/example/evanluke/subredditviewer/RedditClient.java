package com.example.evanluke.subredditviewer;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

import android.app.DownloadManager;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;
import org.json.JSONException;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

/**
 * Created by evanluke on 4/16/18.
 */

public class RedditClient {
//TODO Add functionality for multiple subreddits
        //TODO add functionality for comments view api call

    //TODO ADD dynamic url for comments and subreddits
        private static final String API_BASE_COMMENT_URL = "https://www.reddit.com/r/pics/comments/8cqtmj/my_brothers_tombstone_after_his_passing_from_the/.json";
        private static final String API_BASE_URL = "https://www.reddit.com/r/pics.json";

        private static final String API_BASE_URL_SEARCH_SUBREDDITS = "";

        private static final String API_BASE_URL_TRAILER = "https://api.themoviedb.org/3/movie/";
        private static final String API_END_URL_TRAILER = "/videos?api_key=";
        private static final String API_END_URL = "&api-key=";

        private static final String API_BASE_PERMALINK = "https://www.reddit.com";

        private AsyncHttpClient client;

        public RedditClient() {
            this.client = new AsyncHttpClient();
        }

        private String getApiUrl(String relativeUrl) {
            return API_BASE_URL + relativeUrl;
        }

        // Method for accessing the search API
        public void getSubreddits(final String query, JsonHttpResponseHandler handler) {
            //Just use base URL right now
            //Make sure if spacing in query that it formats it,... does urlencoder do that?
            //String url = API_BASE_URL + query + API_END_URL;

            String url = API_BASE_URL;
            client.get(url, handler);
            //client.get(API_BASE_URL, handler);
            try {
                client.get(URLEncoder.encode(API_BASE_URL, "utf-8"), handler);
            } catch (UnsupportedEncodingException e){
                e.printStackTrace();
            }
/*        try {
            String url = API_BASE_URL;//getApiUrl("search?q=");
            client.get(url*//* + URLEncoder.encode(query, "utf-8")*//*, handler);
        } catch (*//*UnsupportedEncodingException e*//*) {
            e.printStackTrace();
        }
        */
        }

        //Method for finding subreddits to subscribe to
        public void findSubreddits(final String query, JsonHttpResponseHandler handler) {

        }


        public void getComments(final String query, JsonHttpResponseHandler handler) {

            String url = API_BASE_PERMALINK + query + ".json";
            client.get(url, handler);
            try {
                client.get(URLEncoder.encode(API_BASE_URL, "utf-8"), handler);
            } catch (UnsupportedEncodingException e){
                e.printStackTrace();
            }

        }

    }


