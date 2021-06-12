package com.example.twitterclone.Model;

import android.app.Activity;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.twitterclone.R;
import com.example.twitterclone.Controller.TweeterFeedRecyclerViewAdapter;
import com.example.twitterclone.databinding.FragmentTweeterFeedBinding;
import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.ParseUser;

import java.util.ArrayList;
import java.util.List;


public class TweeterFeedFragment extends Fragment {
    private FragmentTweeterFeedBinding binding;
    private TweeterFeedRecyclerViewAdapter adapter;
    private FragmentActivity myContext;
    private List<ParseObject> parseObjectList;
    public TweeterFeedFragment() {
        // Required empty public constructor
    }


    public static TweeterFeedFragment newInstance() {
        TweeterFeedFragment fragment = new TweeterFeedFragment();
        Bundle args = new Bundle();
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
        binding = FragmentTweeterFeedBinding.inflate(inflater, container, false);
        View view = binding.getRoot();
        parseObjectList = new ArrayList<>();
        ParseQuery<ParseObject> parseQuery = ParseQuery.getQuery("Tweets");
        parseQuery.whereContainedIn("username", ParseUser.getCurrentUser().getList("following"));
        parseQuery.findInBackground(new FindCallback<ParseObject>() {
            @Override
            public void done(List<ParseObject> objects, ParseException e) {
                if (objects.size() > 0 && e == null) {
                   for (ParseObject object : objects) {
                       parseObjectList.add(object);
                   }
                    adapter = new TweeterFeedRecyclerViewAdapter(parseObjectList);
                    binding.tweeterFeedRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
                    DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(getContext(), LinearLayoutManager.VERTICAL);
                    dividerItemDecoration.setDrawable(getContext().getResources().getDrawable(R.drawable.tweet_background));
                    binding.tweeterFeedRecyclerView.addItemDecoration(dividerItemDecoration);
                    //binding.tweeterFeedRecyclerView.addItemDecoration(new DividerItemDecoration(binding.tweeterFeedRecyclerView.getContext(), DividerItemDecoration.HORIZONTAL));
                    binding.tweeterFeedRecyclerView.setAdapter(adapter);
                }
            }
        });



        binding.floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentManager fragManager = myContext.getSupportFragmentManager();
                fragManager.beginTransaction().replace(R.id.fragmentContainer,  new CreateTweetFragment(false, null,null,null)).commit();
            }
        });

        return view;
    }
}