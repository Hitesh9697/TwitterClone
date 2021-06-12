package com.example.twitterclone.Controller;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import com.example.twitterclone.Model.CreateTweetFragment;
import com.example.twitterclone.Model.TweetFragment;
import com.example.twitterclone.R;
import com.example.twitterclone.View.TweetReplyViewHolder;
import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.ParseUser;
import com.parse.SaveCallback;

import org.jetbrains.annotations.NotNull;

import java.util.List;

public class TweetReplyRecyclerViewAdapter extends RecyclerView.Adapter<TweetReplyViewHolder> {

    private List<ParseObject> parseObjectsList;
    private Boolean isLiked = false;

    public TweetReplyRecyclerViewAdapter(List<ParseObject> list) {
        parseObjectsList = list;
    }

    @NonNull
    @NotNull
    @Override
    public TweetReplyViewHolder onCreateViewHolder(@NonNull @NotNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.tweer_reply_layout, parent, false);
        return new TweetReplyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull @NotNull TweetReplyViewHolder holder, int position) {

        holder.getUsername().setText(parseObjectsList.get(position).getString("replyBy"));
        holder.getReplyingUser().setText(parseObjectsList.get(position).getString("replyTo"));
        holder.getTweet().setText(parseObjectsList.get(position).getString("reply"));

        holder.getImageViewLike().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!isLiked) {
                    holder.getImageViewLike().setImageResource(R.drawable.ic_like_pressed);
                    parseObjectsList.get(position).add("LikedBy", ParseUser.getCurrentUser().getUsername());
                    parseObjectsList.get(position).saveInBackground(new SaveCallback() {
                        @Override
                        public void done(ParseException e) {
                            holder.getTextViewLikeCounter().setText(parseObjectsList.get(position).getList("LikedBy").size()+"");
                        }
                    });
                    isLiked = true;
                } else {
                    holder.getImageViewLike().setImageResource(R.drawable.ic_like);
                    parseObjectsList.get(position).getList("LikedBy").remove(ParseUser.getCurrentUser().getUsername());
                    List tempList1 = parseObjectsList.get(position).getList("LikedBy");
                    parseObjectsList.get(position).remove("LikedBy");
                    parseObjectsList.get(position).put("LikedBy", tempList1);
                    parseObjectsList.get(position).saveInBackground(new SaveCallback() {
                        @Override
                        public void done(ParseException e) {
                            holder.getTextViewLikeCounter().setText(parseObjectsList.get(position).getList("LikedBy").size()+"");
                        }
                    });
                    isLiked = false;
                }
            }
        });


        if (parseObjectsList.get(position).getList("LikedBy").size() > 0) {
            for (Object obj : parseObjectsList.get(position).getList("LikedBy")) {
                if (obj.equals(ParseUser.getCurrentUser().getUsername())) {
                    holder.getImageViewLike().setImageResource(R.drawable.ic_like_pressed);
                    isLiked = true;
                }
            }
        }

        holder.getTextViewLikeCounter().setText(parseObjectsList.get(position).getList("LikedBy").size()+"");


        holder.getTweetContainer().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AppCompatActivity activity = (AppCompatActivity) v.getContext();

                Fragment myFragment = new TweetFragment(isLiked,
                        holder.getTextViewLikeCounter().getText().toString(),
                        holder.getUsername().getText().toString(),
                        holder.getTweet().getText().toString(),
                        parseObjectsList.get(position).getObjectId(),
                        true);
                activity.getSupportFragmentManager().beginTransaction().replace(R.id.fragmentContainer, myFragment).addToBackStack(null).commit();
            }
        });


        holder.getImageViewReply().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AppCompatActivity activity = (AppCompatActivity) v.getContext();
                Fragment myFragment = new CreateTweetFragment(true,
                        holder.getTweet().getText().toString(),
                        holder.getUsername().getText().toString(),
                        parseObjectsList.get(position).getObjectId());
                activity.getSupportFragmentManager().beginTransaction().replace(R.id.fragmentContainer, myFragment).addToBackStack(null).commit();
            }
        });

        ParseQuery<ParseObject> query = ParseQuery.getQuery("replies");
        query.whereEqualTo("tweetId",parseObjectsList.get(position).getObjectId());
        query.findInBackground(new FindCallback<ParseObject>() {
            @Override
            public void done(List<ParseObject> objects, ParseException e) {
                holder.getTextVieReplyCounter().setText(objects.size()+"");
            }
        });

    }

    @Override
    public int getItemCount() {
        return parseObjectsList.size();
    }
}
