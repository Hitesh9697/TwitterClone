package com.example.twitterclone.Model;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentManager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;

import com.example.twitterclone.R;
import com.example.twitterclone.databinding.FragmentCreateTweetBinding;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseUser;
import com.parse.SaveCallback;

import java.util.List;


public class CreateTweetFragment extends Fragment {

    private FragmentActivity myContext;
    private Boolean isReply = false;
    private String tweet;
    private String user;
    private String tweetId;

    private FragmentCreateTweetBinding binding;
    public CreateTweetFragment() {
        // Required empty public constructor
    }

    public CreateTweetFragment(Boolean isReply, String tweet, String user, String tweetId) {
        this.isReply = isReply;
        this.tweet = tweet;
        this.user = user;
        this.tweetId = tweetId;
    }

    public static CreateTweetFragment newInstance() {
        CreateTweetFragment fragment = new CreateTweetFragment();
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
        binding = FragmentCreateTweetBinding.inflate(inflater, container, false);
        View view = binding.getRoot();

        if(!isReply) {
            binding.buttonTweet.setText("Tweet");

            binding.buttonTweet.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    ParseObject parseObject = new ParseObject("Tweets");
                    parseObject.put("username", ParseUser.getCurrentUser().getUsername());
                    parseObject.put("tweet", binding.editTextTweet.getText().toString());
                    parseObject.add("LikedBy", "temp");
                    parseObject.getList("LikedBy").remove("temp");
                    List tempList = parseObject.getList("LikedBy");
                    parseObject.remove("LikedBy");
                    parseObject.put("LikedBy", tempList);
                    parseObject.saveInBackground(new SaveCallback() {
                        @Override
                        public void done(ParseException e) {
                            hideKeyboard(getContext());
                            FragmentManager fragManager = myContext.getSupportFragmentManager();
                            fragManager.beginTransaction().replace(R.id.fragmentContainer,  new TweeterFeedFragment()).addToBackStack(null).commit();
                        }
                    });

                }
            });

        } else {
            binding.buttonTweet.setText("Reply");
            binding.buttonTweet.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    ParseObject parseObject = new ParseObject("replies");
                    parseObject.put("tweet", tweet);
                    parseObject.put("reply", binding.editTextTweet.getText().toString());
                    parseObject.put("replyBy", ParseUser.getCurrentUser().getUsername());
                    parseObject.put("replyTo", user);
                    parseObject.put("tweetId", tweetId);
                    parseObject.add("LikedBy", "temp");
                    parseObject.getList("LikedBy").remove("temp");
                    List tempList = parseObject.getList("LikedBy");
                    parseObject.remove("LikedBy");
                    parseObject.put("LikedBy", tempList);
                    parseObject.saveInBackground(new SaveCallback() {
                        @Override
                        public void done(ParseException e) {
                            hideKeyboard(getContext());
                            FragmentManager fragManager = myContext.getSupportFragmentManager();
                            getFragmentManager().popBackStack();
                        }
                    });
                }
            });


        }

        return view;
    }

    public static void hideKeyboard(Context ctx) {
        InputMethodManager inputManager = (InputMethodManager) ctx
                .getSystemService(Context.INPUT_METHOD_SERVICE);

        // check if no view has focus:
        View v = ((Activity) ctx).getCurrentFocus();
        if (v == null)
            return;

        inputManager.hideSoftInputFromWindow(v.getWindowToken(), 0);
    }
}