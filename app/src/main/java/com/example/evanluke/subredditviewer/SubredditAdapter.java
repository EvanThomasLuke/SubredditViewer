package com.example.evanluke.subredditviewer;

import android.graphics.Movie;


import android.content.Context;
import android.net.Uri;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by evanluke on 4/16/18.
 */

public class SubredditAdapter extends RecyclerView.Adapter<SubredditAdapter.ViewHolder> {

//TODO add functionality to cut off selfText if it gets too long
    /***** Creating OnItemClickListener *****/

    // Define listener member variable
    private OnItemClickListener listener;
    // Define the listener interface
    public interface OnItemClickListener {
        void onItemClick(View itemView, int position);
    }
    // Define the method that allows the parent activity or fragment to define the listener
    public void setOnItemClickListener(OnItemClickListener listener) {
        this.listener = listener;
    }

    // Provide a direct reference to each of the views within a data item
    // Used to cache the views within the item layout for fast access
    public class ViewHolder extends RecyclerView.ViewHolder {
        // Your holder should contain a member variable
        // for any view that will be set as you render a row
        public ImageView subredditImageView;
        public TextView subredditTitle;
        public TextView subredditSelfText;


        public TextView subredditCommentOne;
        public TextView subredditCommentTwo;
        public TextView subredditCommentThree;
        public TextView subredditCommentFour;
        public TextView subredditCommentFive;


        // We also create a constructor that accepts the entire item row
        // and does the view lookups to find each subview
        public ViewHolder(final View itemView) {
            // Stores the itemView in a public final member variable that can be used
            // to access the context from any ViewHolder instance.
            super(itemView);

            subredditImageView = (ImageView) itemView.findViewById(R.id.subredditImageView);
            subredditTitle = (TextView) itemView.findViewById(R.id.titleTextView);
            subredditSelfText = (TextView) itemView.findViewById(R.id.selfTextTextView);
            /*
            subredditCommentOne = (TextView) itemView.findViewById(R.id.commentOneTextView);
            subredditCommentTwo = (TextView) itemView.findViewById(R.id.commentTwoTextView);
            subredditCommentThree = (TextView) itemView.findViewById(R.id.commentThreeTextView);
            subredditCommentFour = (TextView) itemView.findViewById(R.id.commentFourTextView);
            subredditCommentFive = (TextView) itemView.findViewById(R.id.commentFiveTextView);
*/
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(listener != null) {
                        int position = getAdapterPosition();
                        if (position != RecyclerView.NO_POSITION) {
                            listener.onItemClick(itemView, position);
                        }
                    }
                }
            });

        }
    }

    private List<Subreddit> mSubreddits;
    private Context mContext;

    public SubredditAdapter(Context context, List<Subreddit> subreddits) {
        mContext = context;
        mSubreddits = subreddits;
    }

    private Context getContext() {
        return mContext;
    }


    // Usually involves inflating a layout from XML and returning the holder
    @Override
    public SubredditAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);

        // Inflate the custom layout
        View subredditView = inflater.inflate(R.layout.item_subreddit, parent, false);

        // Return a new holder instance
        ViewHolder viewHolder = new ViewHolder(subredditView);
        return viewHolder;
    }

    // Involves populating data into the item through holder
    @Override
    public void onBindViewHolder(SubredditAdapter.ViewHolder viewHolder, int position) {
        // Get the data model based on position
        Subreddit subreddit = mSubreddits.get(position);

        // Set item views based on your views and data model
        ImageView imageView = viewHolder.subredditImageView;
        //TODO fix this thumbnails contain empty string on test thread
        if (subreddit.getThumbnail() != null && subreddit.getThumbnail() != "") {
            Picasso.with(getContext()).load(Uri.parse(subreddit.getThumbnail())).error(R.drawable.ic_nocover).into(imageView);
        }
//        Picasso.with(getContext()).load(Uri.parse(subreddit.getThumbnail())).error(R.drawable.ic_nocover).into(imageView);
        TextView titleTextView = viewHolder.subredditTitle;
        titleTextView.setText(subreddit.getTitle());

        TextView selfTextView = viewHolder.subredditSelfText;
        selfTextView.setText(subreddit.getSelfText());

        //I was going to add comments here but they are not retrieved on first
        //api call so i will only show the comments on the commentDetailScreen

/*
        TextView commentOneTextView = viewHolder.subredditCommentOne;
        commentOneTextView.setText(subreddit.getCommentOne());
        TextView commentOneTextView = viewHolder.subredditCommentTwo;
        commentOneTextView.setText(subreddit.getCommentTwo());
        TextView commentTwoTextView = viewHolder.subredditCommentThree;
        commentOneTextView.setText(subreddit.getCommentThree());
        TextView commentThreeTextView = viewHolder.subredditCommentFour;
        commentOneTextView.setText(subreddit.getCommentFour());
        TextView commentFourTextView = viewHolder.subredditCommentFive;
        commentOneTextView.setText(subreddit.getCommentFive());
*/
        /*
        imageView.setImageIcon(subreddit.getImage());
        TextView textView = viewHolder.nameTextView;
        textView.setText(contact.getName());
        Button button = viewHolder.messageButton;
        button.setText(contact.isOnline() ? "Message" : "Offline");

       button.setEnabled(contact.isOnline());*/

    }

    // Returns the total count of items in the list
    @Override
    public int getItemCount() {
        return mSubreddits.size();
    }

    //Remove all data from recycler view on refresh or search
    public void clear() {
        final int size = mSubreddits.size();
        if (size > 0) {
            for (int i = 0; i < size; i++) {
                mSubreddits.remove(0);
            }

            notifyItemRangeRemoved(0, size);
        }
    }


    public void swap(ArrayList<Subreddit> subreddits)
    {
        if(subreddits == null || subreddits.size()==0)
            return;
        //Why is it saying movies is never null?
        if (subreddits != null && subreddits.size()>0)
            mSubreddits.clear();
        mSubreddits.addAll(subreddits);
        notifyDataSetChanged();

    }




}


