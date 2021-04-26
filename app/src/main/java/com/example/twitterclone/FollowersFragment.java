package com.example.twitterclone;

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

import com.example.twitterclone.databinding.FragmentFollowersBinding;
import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.ParseUser;

import java.util.ArrayList;
import java.util.List;


public class FollowersFragment extends Fragment {

    private FragmentFollowersBinding binding;
    private FollowersUsersRecyclerViewAdapter adapter;
    private List<String> followerList;
    private FragmentActivity myContext;

    public FollowersFragment() {
        // Required empty public constructor
    }

    public static FollowersFragment newInstance() {
        FollowersFragment fragment = new FollowersFragment();
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
        binding = FragmentFollowersBinding.inflate(inflater, container, false);
        View view = binding.getRoot();
        followerList = new ArrayList<>();
        //followerList = ParseUser.getCurrentUser().getList("following");

        ParseQuery<ParseObject> query = ParseQuery.getQuery("Follow");
        query.whereEqualTo("username", ParseUser.getCurrentUser().getUsername());
        query.findInBackground(new FindCallback<ParseObject>() {
            @Override
            public void done(List<ParseObject> objects, ParseException e) {
                if (objects.get(0).getList("followers") != null) {
                    followerList = objects.get(0).getList("followers");
                } else {
                    ParseUser.getCurrentUser().add("followers", "temp");
                    ParseUser.getCurrentUser().getList("followers").remove("temp");
                    List tempList = ParseUser.getCurrentUser().getList("followers");
                    ParseUser.getCurrentUser().remove("followers");
                    ParseUser.getCurrentUser().put("followers", tempList);
                    ParseUser.getCurrentUser().saveInBackground();
                }
                adapter = new FollowersUsersRecyclerViewAdapter(followerList);
                binding.followersRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
                binding.followersRecyclerView.setAdapter(adapter);
            }
        });

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