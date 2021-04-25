package com.example.twitterclone;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class FollowersUsersRecyclerViewAdapter extends RecyclerView.Adapter<FollowingUsersRecyclerViewHolder> {

    private List<String> followerList = new ArrayList<>();

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
    }

    @Override
    public int getItemCount() {
        return followerList.size();
    }
}
