package com.example.twitterclone.Model;

import android.app.Activity;
import android.os.Bundle;

import androidx.activity.OnBackPressedCallback;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.twitterclone.Controller.FollowingUsersRecyclerViewAdapter;
import com.example.twitterclone.R;
import com.example.twitterclone.databinding.FragmentFollowingBinding;
import com.parse.ParseUser;

import java.util.ArrayList;
import java.util.List;

public class FollowingFragment extends Fragment {

    private FragmentFollowingBinding binding;
    private FollowingUsersRecyclerViewAdapter adapter;
    private List<String> followerList;
    private FragmentActivity myContext;

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
    public void onAttach(Activity activity) {
        myContext=(FragmentActivity) activity;
        super.onAttach(activity);
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


        requireActivity().getOnBackPressedDispatcher().addCallback(getViewLifecycleOwner(), new OnBackPressedCallback(true) {
            @Override
            public void handleOnBackPressed() {
                FragmentManager fragManager = myContext.getSupportFragmentManager();
                fragManager.beginTransaction().replace(R.id.fragmentContainer,  new TweeterFeedFragment()).commit();
            }
        });

        return view;
    }
}