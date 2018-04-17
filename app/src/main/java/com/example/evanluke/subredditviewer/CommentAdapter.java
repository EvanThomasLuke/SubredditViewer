package com.example.evanluke.subredditviewer;

import android.support.v7.widget.RecyclerView;



import android.graphics.Movie;
import android.content.Context;
import android.net.Uri;
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
 * Created by evanluke on 4/17/18.
 */


public class CommentAdapter extends RecyclerView.Adapter<CommentAdapter.ViewHolder> {


//TODO add functionality to cut off selfText if it gets too long
    /***** Creating OnItemClickListener *****/

    // Define listener member variable
    private SubredditAdapter.OnItemClickListener listener;
    // Define the listener interface
    public interface OnItemClickListener {
        void onItemClick(View itemView, int position);
    }
    // Define the method that allows the parent activity or fragment to define the listener
    public void setOnItemClickListener(SubredditAdapter.OnItemClickListener listener) {
        this.listener = listener;
    }

    // Provide a direct reference to each of the views within a data item
    // Used to cache the views within the item layout for fast access
    public class ViewHolder extends RecyclerView.ViewHolder {
        // Your holder should contain a member variable
        // for any view that will be set as you render a row
        //TODO ADD FIRST TITLE COMMENT
        public ImageView commentImageView;
        public TextView subredditTitle;
        public TextView commentBodyText;
        public TextView commentAuthorText;




        // We also create a constructor that accepts the entire item row
        // and does the view lookups to find each subview
        public ViewHolder(final View itemView) {
            // Stores the itemView in a public final member variable that can be used
            // to access the context from any ViewHolder instance.
            super(itemView);

            commentImageView = (ImageView) itemView.findViewById(R.id.subredditImageView);
            subredditTitle = (TextView) itemView.findViewById(R.id.titleTextView);
            commentBodyText = (TextView) itemView.findViewById(R.id.bodyTextView);
            commentAuthorText = (TextView) itemView.findViewById(R.id.authorTextView);
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

    private List<Comment> mComments;
    private Context mContext;

    public CommentAdapter(Context context, List<Comment> comments) {
        mContext = context;
        mComments = comments;
    }

    private Context getContext() {
        return mContext;
    }


    // Usually involves inflating a layout from XML and returning the holder
    @Override
    public CommentAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);

        // Inflate the custom layout
        View commentView = inflater.inflate(R.layout.item_comment, parent, false);

        // Return a new holder instance
        CommentAdapter.ViewHolder viewHolder = new CommentAdapter.ViewHolder(commentView);
        return viewHolder;
    }

    // Involves populating data into the item through holder
    @Override
    public void onBindViewHolder(CommentAdapter.ViewHolder viewHolder, int position) {
        // Get the data model based on position
        Comment comment = mComments.get(position);

        // Set item views based on your views and data model
        ImageView imageView = viewHolder.commentImageView;
        //TODO fix this thumbnails contain empty string on test thread
//        Picasso.with(getContext()).load(Uri.parse(comment.getThumbnail())).error(R.drawable.ic_nocover).into(imageView);
        //TextView titleTextView = viewHolder.subredditTitle;
        //titleTextView.setText(comment.getTitle());

        TextView author = viewHolder.commentAuthorText;
        author.setText(comment.getAuthor());
        TextView body = viewHolder.commentBodyText;
        body.setText(comment.getBody());
      //  TextView selfTextView = viewHolder.subredditSelfText;
       // selfTextView.setText(comment.getSelfText());

        //I was going to add comments here but they are not retrieved on first
        //api call so i will only show the comments on the commentDetailScreen

/*
        TextView commentOneTextView = viewHolder.subredditCommentOne;
        commentOneTextView.setText(comment.getCommentOne());
        TextView commentOneTextView = viewHolder.subredditCommentTwo;
        commentOneTextView.setText(comment.getCommentTwo());
        TextView commentTwoTextView = viewHolder.subredditCommentThree;
        commentOneTextView.setText(comment.getCommentThree());
        TextView commentThreeTextView = viewHolder.subredditCommentFour;
        commentOneTextView.setText(comment.getCommentFour());
        TextView commentFourTextView = viewHolder.subredditCommentFive;
        commentOneTextView.setText(comment.getCommentFive());
*/
        /*
        imageView.setImageIcon(comment.getImage());
        TextView textView = viewHolder.nameTextView;
        textView.setText(contact.getName());
        Button button = viewHolder.messageButton;
        button.setText(contact.isOnline() ? "Message" : "Offline");

       button.setEnabled(contact.isOnline());*/

    }

    // Returns the total count of items in the list
    @Override
    public int getItemCount() {
        return mComments.size();
    }

    //Remove all data from recycler view on refresh or search
    public void clear() {
        final int size = mComments.size();
        if (size > 0) {
            for (int i = 0; i < size; i++) {
                mComments.remove(0);
            }

            notifyItemRangeRemoved(0, size);
        }
    }


    public void swap(ArrayList<Comment> comments)
    {
        if(comments == null || comments.size()==0)
            return;
        //Why is it saying movies is never null?
        if (comments != null && comments.size()>0)
            mComments.clear();
        mComments.addAll(comments);
        notifyDataSetChanged();

    }




}
