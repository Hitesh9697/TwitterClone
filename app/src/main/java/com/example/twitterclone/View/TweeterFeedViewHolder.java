package com.example.twitterclone.View;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.twitterclone.R;

public class TweeterFeedViewHolder extends RecyclerView.ViewHolder {
    private ImageView imageViewReply, imageViewLike, imageViewShare;
    private TextView username, tweet, textVieReplyCounter, textViewLikeCounter;
    public TweeterFeedViewHolder(@NonNull View itemView) {
        super(itemView);
        username = itemView.findViewById(R.id.textViewTweetUsername);
        tweet = itemView.findViewById(R.id.textViewTweetContent);

        imageViewReply = itemView.findViewById(R.id.imageViewReplies);
        imageViewLike = itemView.findViewById(R.id.imageViewLike);
        imageViewShare = itemView.findViewById(R.id.imageViewShare);

        textVieReplyCounter = itemView.findViewById(R.id.textViewReplyCounter);
        textViewLikeCounter = itemView.findViewById(R.id.textViewLikeCounter);
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
}
