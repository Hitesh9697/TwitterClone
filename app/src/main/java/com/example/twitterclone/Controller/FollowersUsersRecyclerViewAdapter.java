package com.example.twitterclone.Controller;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.twitterclone.R;
import com.example.twitterclone.View.FollowingUsersRecyclerViewHolder;
import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.ParseUser;

import java.util.ArrayList;
import java.util.List;

public class FollowersUsersRecyclerViewAdapter extends RecyclerView.Adapter<FollowingUsersRecyclerViewHolder> {

    private List<String> followerList = new ArrayList<>();
    private ParseObject parseObject;

    public FollowersUsersRecyclerViewAdapter(List<String> list) {
        followerList = list;
    }

    @NonNull
    @Override
    public FollowingUsersRecyclerViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.following_user_item_holder, parent, false);
        return new FollowingUsersRecyclerViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull FollowingUsersRecyclerViewHolder holder, int position) {
        holder.getTextView().setText(followerList.get(position));
        if (ParseUser.getCurrentUser().getList("following").contains(followerList.get(position))) {
            holder.getButton().setText("Following");
        } else {
            holder.getButton().setText("Follow");
        }

        holder.getButton().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (holder.getButton().getText().equals("Follow")) {
                    holder.getButton().setText("Following");
                    ParseUser.getCurrentUser().add("following", followerList.get(position));
                    ParseQuery<ParseObject> query = ParseQuery.getQuery("Follow");
                    query.whereEqualTo("username", followerList.get(position));
                    query.findInBackground(new FindCallback<ParseObject>() {
                        @Override
                        public void done(List<ParseObject> objects, ParseException e) {
                            parseObject = objects.get(0);
                            parseObject.add("followers", ParseUser.getCurrentUser().getUsername());
                            parseObject.saveInBackground();

                        }
                    });
                } else {
                    holder.getButton().setText("Follow");
                    ParseUser.getCurrentUser().getList("following").remove(followerList.get(position));
                    List tempList = ParseUser.getCurrentUser().getList("following");
                    ParseUser.getCurrentUser().remove("following");
                    ParseUser.getCurrentUser().put("following", tempList);

                    ParseQuery<ParseObject> query = ParseQuery.getQuery("Follow");
                    query.whereEqualTo("username", followerList.get(position));
                    query.findInBackground(new FindCallback<ParseObject>() {
                        @Override
                        public void done(List<ParseObject> objects, ParseException e) {
                            parseObject = objects.get(0);
                            parseObject.getList("followers").remove(ParseUser.getCurrentUser().getUsername());
                            List tempList1 = parseObject.getList("followers");
                            parseObject.remove("followers");
                            parseObject.put("followers", tempList1);
                            parseObject.saveInBackground();
                        }
                    });

                }
                ParseUser.getCurrentUser().saveInBackground();
            }
        });

    }

    @Override
    public int getItemCount() {
        return followerList.size();
    }
}
