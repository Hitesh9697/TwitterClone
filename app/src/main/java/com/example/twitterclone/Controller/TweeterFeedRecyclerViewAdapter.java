package com.example.twitterclone.Controller;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.twitterclone.R;
import com.example.twitterclone.View.TweeterFeedViewHolder;
import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.ParseUser;
import com.parse.SaveCallback;

import java.util.List;

public class TweeterFeedRecyclerViewAdapter extends RecyclerView.Adapter<TweeterFeedViewHolder> {
    private List<ParseObject> parseObjectList;
    private boolean isLiked = false;

    public TweeterFeedRecyclerViewAdapter(List<ParseObject> list) {
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

        holder.getImageViewLike().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!isLiked) {
                    holder.getImageViewLike().setImageResource(R.drawable.ic_like_pressed);
                    parseObjectList.get(position).add("LikedBy",ParseUser.getCurrentUser().getUsername());
                    parseObjectList.get(position).saveInBackground(new SaveCallback() {
                        @Override
                        public void done(ParseException e) {
                            holder.getTextViewLikeCounter().setText(parseObjectList.get(position).getList("LikedBy").size()+"");
                        }
                    });
                    isLiked = true;
                } else {
                    holder.getImageViewLike().setImageResource(R.drawable.ic_like);
                    parseObjectList.get(position).getList("LikedBy").remove(ParseUser.getCurrentUser().getUsername());
                    List tempList1 = parseObjectList.get(position).getList("LikedBy");
                    parseObjectList.get(position).remove("LikedBy");
                    parseObjectList.get(position).put("LikedBy", tempList1);
                    parseObjectList.get(position).saveInBackground(new SaveCallback() {
                        @Override
                        public void done(ParseException e) {
                            holder.getTextViewLikeCounter().setText(parseObjectList.get(position).getList("LikedBy").size()+"");
                        }
                    });
                    isLiked = false;
                }
            }
        });


        if (parseObjectList.get(position).getList("LikedBy").size() > 0) {
            for (Object obj : parseObjectList.get(position).getList("LikedBy")) {
                if (obj.equals(ParseUser.getCurrentUser().getUsername())) {
                    holder.getImageViewLike().setImageResource(R.drawable.ic_like_pressed);
                    isLiked = true;
                }
            }
        }

        holder.getTextViewLikeCounter().setText(parseObjectList.get(position).getList("LikedBy").size()+"");

    }

    @Override
    public int getItemCount() {
        return parseObjectList.size();
    }
    
}
