package com.example.twitterclone.Model;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.twitterclone.Controller.TweeterFeedRecyclerViewAdapter;
import com.example.twitterclone.R;
import com.example.twitterclone.databinding.FragmentProfileTweetTabBinding;
import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;

import java.util.ArrayList;
import java.util.List;

public class ProfileTweetTabFragment extends Fragment {

    private FragmentProfileTweetTabBinding binding;
    private List<ParseObject> parseObjectList;
    private String username;
    private TweeterFeedRecyclerViewAdapter adapter;

    public ProfileTweetTabFragment() {
        // Required empty public constructor
    }

    public ProfileTweetTabFragment(String user) {
        // Required empty public constructor
        username = user;
    }


    public static ProfileTweetTabFragment newInstance(String param1, String param2) {
        ProfileTweetTabFragment fragment = new ProfileTweetTabFragment();
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
        // Inflate the layout for this fragment

        binding = FragmentProfileTweetTabBinding.inflate(inflater, container, false);
        View view = binding.getRoot();

        parseObjectList = new ArrayList<>();
        ParseQuery<ParseObject> parseQuery = ParseQuery.getQuery("Tweets");
        parseQuery.whereEqualTo("username", username);
        parseQuery.findInBackground(new FindCallback<ParseObject>() {
            @Override
            public void done(List<ParseObject> objects, ParseException e) {
                if (objects.size() > 0 && e == null) {
                    for (ParseObject object : objects) {
                        parseObjectList.add(object);
                    }
                    adapter = new TweeterFeedRecyclerViewAdapter(parseObjectList);
                    binding.userTweetFeedRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
                    DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(getContext(), LinearLayoutManager.VERTICAL);
                    dividerItemDecoration.setDrawable(getContext().getResources().getDrawable(R.drawable.tweet_background));
                    binding.userTweetFeedRecyclerView.addItemDecoration(dividerItemDecoration);
                    binding.userTweetFeedRecyclerView.setAdapter(adapter);
                }
            }
        });

        return view;
    }
}