package com.example.twitterclone.View;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.example.twitterclone.R;

import org.jetbrains.annotations.NotNull;

public class TweetReplyViewHolder extends RecyclerView.ViewHolder {

    private ImageView imageViewReply, imageViewLike, imageViewShare;
    private TextView username, tweet, textVieReplyCounter, textViewLikeCounter, replyingUser;
    private ConstraintLayout tweetContainer;

    public TweetReplyViewHolder(@NonNull @NotNull View itemView) {
        super(itemView);

        username = itemView.findViewById(R.id.textViewReplierUsername);
        tweet = itemView.findViewById(R.id.textViewReplyTweetContent);
        replyingUser = itemView.findViewById(R.id.textViewReplyingUser);

        imageViewReply = itemView.findViewById(R.id.imageViewRepliesToReply);
        imageViewLike = itemView.findViewById(R.id.imageViewReplyLike);
        imageViewShare = itemView.findViewById(R.id.imageViewReplyShare);

        textVieReplyCounter = itemView.findViewById(R.id.textViewReplyToReplyCounter);
        textViewLikeCounter = itemView.findViewById(R.id.textViewReplyLikeCounter);
        tweetContainer = itemView.findViewById(R.id.replyTweetContainer);

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

    public TextView getUsername() {
        return username;
    }

    public TextView getTweet() {
        return tweet;
    }

    public TextView getTextVieReplyCounter() {
        return textVieReplyCounter;
    }

    public TextView getTextViewLikeCounter() {
        return textViewLikeCounter;
    }

    public TextView getReplyingUser() {
        return replyingUser;
    }

    public ConstraintLayout getTweetContainer() {
        return tweetContainer;
    }
}
