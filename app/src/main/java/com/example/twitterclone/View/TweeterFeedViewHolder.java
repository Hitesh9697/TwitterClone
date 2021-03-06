package com.example.twitterclone.View;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.example.twitterclone.R;

public class TweeterFeedViewHolder extends RecyclerView.ViewHolder {
    private ImageView imageViewReply, imageViewLike, imageViewShare;
    private TextView username, tweet, textVieReplyCounter, textViewLikeCounter;
    private ConstraintLayout tweetContainer;
    public TweeterFeedViewHolder(@NonNull View itemView) {
        super(itemView);
        username = itemView.findViewById(R.id.textViewReplierUsername);
        tweet = itemView.findViewById(R.id.textViewReplyTweetContent);

        imageViewReply = itemView.findViewById(R.id.imageViewRepliesToReply);
        imageViewLike = itemView.findViewById(R.id.imageViewReplyLike);
        imageViewShare = itemView.findViewById(R.id.imageViewReplyShare);

        textVieReplyCounter = itemView.findViewById(R.id.textViewReplyToReplyCounter);
        textViewLikeCounter = itemView.findViewById(R.id.textViewReplyLikeCounter);
        tweetContainer = itemView.findViewById(R.id.replyTweetContainer);
    }

    public TextView getUsername() {
        return username;
    }

    public TextView getTweet() {
        return tweet;
    }

    public ImageView getImageViewReply() {
        return imageViewReply;
    }

    public ImageView getImageViewLike() {
        return imageViewLike;
    }

    public ImageView getImageViewShare() {
        return imageViewShare;
    }

    public TextView getTextVieReplyCounter() {
        return textVieReplyCounter;
    }

    public TextView getTextViewLikeCounter() {
        return textViewLikeCounter;
    }

    public ConstraintLayout getTweetContainer() {
        return tweetContainer;
    }
}
