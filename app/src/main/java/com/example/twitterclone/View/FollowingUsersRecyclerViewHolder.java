package com.example.twitterclone.View;

import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.twitterclone.R;

public class FollowingUsersRecyclerViewHolder extends RecyclerView.ViewHolder {

    private TextView textView;
    private Button button;

    public FollowingUsersRecyclerViewHolder(@NonNull View itemView) {
        super(itemView);
        textView = itemView.findViewById(R.id.textViewFollowingUsers);
        button = itemView.findViewById(R.id.buttonFollowingUsers);
    }

    public TextView getTextView() {
        return textView;
    }

    public Button getButton() {
        return button;
    }
}
