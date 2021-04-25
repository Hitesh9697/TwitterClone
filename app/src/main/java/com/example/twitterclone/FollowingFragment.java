package com.example.twitterclone;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.twitterclone.databinding.FragmentFollowingBinding;
import com.parse.ParseUser;

import java.util.ArrayList;
import java.util.List;

public class FollowingFragment extends Fragment {

    private FragmentFollowingBinding binding;
    private FollowingUsersRecyclerViewAdapter adapter;
    private List<String> followerList;

    public FollowingFragment() {
        // Required empty public constructor
    }

    public static FollowingFragment newInstance() {
        FollowingFragment fragment = new FollowingFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentFollowingBinding.inflate(inflater, container, false);
        View view = binding.getRoot();
        followerList = new ArrayList<>();
        followerList = ParseUser.getCurrentUser().getList("following");

        adapter = new FollowingUsersRecyclerViewAdapter(followerList);
        binding.followingRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        binding.followingRecyclerView.setAdapter(adapter);

        return view;
    }
}