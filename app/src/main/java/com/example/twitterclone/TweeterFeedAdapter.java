package com.example.twitterclone;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.parse.ParseObject;

import java.util.List;

public class TweeterFeedAdapter extends RecyclerView.Adapter<TweeterFeedViewHolder> {
    private List<ParseObject> parseObjectList;

    public TweeterFeedAdapter(List<ParseObject> list) {
        parseObjectList = list;
    }

    @NonNull
    @Override
    public TweeterFeedViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.tweet_display_layout, parent, false);
        return new TweeterFeedViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull TweeterFeedViewHolder holder, int position) {
        holder.getUsername().setText(parseObjectList.get(position).getString("username"));
        holder.getTweet().setText(parseObjectList.get(position).getString("tweet"));
    }

    @Override
    public int getItemCount() {
        return parseObjectList.size();
    }
}
