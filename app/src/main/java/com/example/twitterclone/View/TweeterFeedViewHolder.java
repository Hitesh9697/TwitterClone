package com.example.twitterclone.View;

import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.twitterclone.R;

public class TweeterFeedViewHolder extends RecyclerView.ViewHolder {
    private TextView username, tweet;
    public TweeterFeedViewHolder(@NonNull View itemView) {
        super(itemView);
        username = itemView.findViewById(R.id.textViewTweetUsername);
        tweet = itemView.findViewById(R.id.textViewTweetContent);
    }

    public TextView getUsername() {
        return username;
    }

    public TextView getTweet() {
        return tweet;
    }
}
