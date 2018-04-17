package com.example.evanluke.subredditviewer;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by evanluke on 4/17/18.
 */

public class Comment {

    private String subredditId;
    private String linkId;
    private JSONObject replies;
    private String id;
    private String author;
    private String parentId;
    private int score;
    private String body;
    private String permalink;
    private String created;
    private int depth;

    public String getSubredditId() {
        return subredditId;
    }

    public String getLinkId() {
        return linkId;
    }

    public JSONObject getReplies() {
        return replies;
    }

    public String getId() {
        return id;
    }

    public String getAuthor() {
        return author;
    }

    public String getParentId() {
        return parentId;
    }

    public int getScore() {
        return score;
    }

    public String getBody() {
        return body;
    }

    public String getPermalink() {
        return permalink;
    }

    public String getCreated() {
        return created;
    }

    public int getDepth() {
        return depth;
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



    //Every listing object to get to comments is 3rd layer

/*
    1st object is Listing main title comment
    2nd object listing, data object contains children array of top comments
    children array inside object kind 't1' is data object
    this level has main comment,
    subreddit_id, link_id, replies{}, id, author, parent_id, score, body, name, permalink,
        inside that is replies object
        listing, data {},
            Children[]
                object kind 't1' data {}
                depth 1
                    replies {}
                        data {}
                            children[]


Store a comment store the full replies object
if a comment is clicked on get the replies object and create a comment object from
each of the replies array objects
Add a button to view more comments from that comment thread if there are replies

How can I add up to 5 children rows in recycler view?

https://github.com/TellH/RecyclerTreeView
https://stackoverflow.com/questions/33258229/how-to-show-nested-or-tree-data-in-recycler-view?rq=1

Use that library
if more then depth 10
open new activity or fragment and api call for that comment
and top back button to go back


     */

}
