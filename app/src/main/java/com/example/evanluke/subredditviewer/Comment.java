package com.example.evanluke.subredditviewer;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Created by evanluke on 4/17/18.
 */

public class Comment implements Serializable {

    private String subredditId;
    private String linkId;
    private JSONObject replies;
    private String id;
    private String author;
    private String parentId;
    private int score;
    private String body;
    private String permalink;
    private int created;
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

    public int getCreated() {
        return created;
    }

    public int getDepth() {
        return depth;
    }

    //TODO add static method getInnerComments()
    //To get a comments nested comments
    //create a static method? populate a comments innerCommentsArrayList
    //on click run that static method
    //and update the recycler view



    public static Comment fromJson(JSONObject jsonObject) {
        JSONObject data = null;
        Comment a = new Comment();
        //ArrayList<Comments> commentsArrayList = new ArrayList<>();
        try {
            data = jsonObject.getJSONObject("data");

            try {
                //Deserialize json into object fields
                a.subredditId = data.getString("subreddit_id");
                a.linkId = data.getString("link_id");
                a.replies = data.getJSONObject("replies");
                a.id = data.getString("id");
                a.author = data.getString("author");
                a.parentId = data.getString("parent_id");
                a.score = data.getInt("score");
                a.body = data.getString("body");
                a.permalink = data.getString("permalink");
                a.created = data.getInt("created");
                a.depth = data.getInt("depth");


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
    public static ArrayList<Comment> fromJson(JSONArray jsonArray) throws JSONException {
        ArrayList<Comment> businesses = new ArrayList<Comment>(jsonArray.length());
        // Process each result in json array, decode and convert to business object
     //TODO decide if start at i=0 to get the main title comment ...
        JSONObject depth0 = null;
        JSONObject depth0Data;
        JSONArray depth0DataChildrenArray;


        try {
            depth0 = jsonArray.getJSONObject(1);

            try {
                depth0Data = depth0.getJSONObject("data");
                try {
                    depth0DataChildrenArray = depth0Data.getJSONArray("children");
                } catch (Exception e) {
                    e.printStackTrace();
                    return null;
                }
            } catch (Exception e) {
                e.printStackTrace();
                return null;
            }
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }




        for (int i = 1; i < depth0DataChildrenArray.length(); i++) {
            JSONObject businessJson = null;
            try {
                businessJson = depth0DataChildrenArray.getJSONObject(i);
            } catch (Exception e) {
                e.printStackTrace();
                continue;
            }

            Comment business = Comment.fromJson(businessJson);
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


