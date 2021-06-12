package com.example.twitterclone.Model;

import android.app.Activity;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.twitterclone.Controller.TweeterFeedRecyclerViewAdapter;
import com.example.twitterclone.R;
import com.example.twitterclone.databinding.FragmentUserBinding;
import com.example.twitterclone.databinding.FragmentUserProfileBinding;
import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.ParseUser;

import java.util.ArrayList;
import java.util.List;

public class UserProfileFragment extends Fragment {

    private FragmentUserProfileBinding binding;
    private TweeterFeedRecyclerViewAdapter adapter;
    private FragmentActivity myContext;
    private List<ParseObject> parseObjectList;
    private List<String> userList;
    private String username;
    private Boolean btnClickable;
    private Float btnAlpha;

    public UserProfileFragment() {
        // Required empty public constructor
    }

    public UserProfileFragment(String user, Boolean b,Float f) {
        username = user;
        btnClickable = b;
        btnAlpha = f;
    }

    public static UserProfileFragment newInstance(String param1, String param2) {
        UserProfileFragment fragment = new UserProfileFragment();
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
        // Inflate the layout for this fragment
        binding = FragmentUserProfileBinding.inflate(inflater, container, false);
        View view = binding.getRoot();

        FragmentManager fragManager = myContext.getSupportFragmentManager();
        fragManager.beginTransaction().replace(R.id.userProfileNavContainer,  new FragmentNavHeader(username, btnClickable, btnAlpha, userList)).commit();



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