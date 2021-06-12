package com.example.twitterclone.Model;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.twitterclone.Controller.TweetReplyRecyclerViewAdapter;
import com.example.twitterclone.R;
import com.example.twitterclone.databinding.FragmentTweetBinding;
import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.ParseUser;
import com.parse.SaveCallback;

import java.util.ArrayList;
import java.util.List;


public class TweetFragment extends Fragment {

    private FragmentTweetBinding binding;
    private Boolean isLiked = false;
    private String likeCounter, username, tweet, tweetId;
    private TweetReplyRecyclerViewAdapter adapter;
    private List<ParseObject> parseObjectList;
    private Boolean fromReply = false;

    public TweetFragment() {
        // Required empty public constructor
    }

    public TweetFragment(Boolean isLiked, String likeCounter, String username, String tweet, String tweetId, Boolean fromReply) {
        this.isLiked = isLiked;
        this.likeCounter = likeCounter;
        this.username = username;
        this.tweet = tweet;
        this.tweetId = tweetId;
        this.fromReply = fromReply;
    }

    public static TweetFragment newInstance(String param1, String param2) {
        TweetFragment fragment = new TweetFragment();
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
        binding = FragmentTweetBinding.inflate(inflater, container, false);
        View view = binding.getRoot();

        binding.textViewTweetUsername.setText(username);
        binding.textViewTweetContent.setText(tweet);
        binding.textViewLikeCounter.setText(likeCounter);

        if (!fromReply) {
            ParseQuery<ParseObject> query = ParseQuery.getQuery("Tweets");
            query.whereEqualTo("tweet", tweet);
            query.findInBackground(new FindCallback<ParseObject>() {
                @Override
                public void done(List<ParseObject> objects, ParseException e) {

                    binding.imageViewReplies.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            AppCompatActivity activity = (AppCompatActivity) v.getContext();
                            Fragment myFragment = new CreateTweetFragment(true,
                                    binding.textViewTweetContent.getText().toString(),
                                    binding.textViewTweetUsername.getText().toString(),
                                    objects.get(0).getObjectId());
                            activity.getSupportFragmentManager().beginTransaction().replace(R.id.fragmentContainer, myFragment).addToBackStack(null).commit();
                        }
                    });

                    binding.imageViewLike.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            if (!isLiked) {
                                binding.imageViewLike.setImageResource(R.drawable.ic_like_pressed);
                                objects.get(0).add("LikedBy", ParseUser.getCurrentUser().getUsername());
                                objects.get(0).saveInBackground(new SaveCallback() {
                                    @Override
                                    public void done(ParseException e) {
                                        binding.textViewLikeCounter.setText(objects.get(0).getList("LikedBy").size()+"");
                                    }
                                });
                                isLiked = true;
                            } else {
                                binding.imageViewLike.setImageResource(R.drawable.ic_like);
                                objects.get(0).getList("LikedBy").remove(ParseUser.getCurrentUser().getUsername());
                                List tempList1 = objects.get(0).getList("LikedBy");
                                objects.get(0).remove("LikedBy");
                                objects.get(0).put("LikedBy", tempList1);
                                objects.get(0).saveInBackground(new SaveCallback() {
                                    @Override
                                    public void done(ParseException e) {
                                        binding.textViewLikeCounter.setText(objects.get(0).getList("LikedBy").size()+"");
                                    }
                                });
                                isLiked = false;
                            }
                        }
                    });


                    if (objects.get(0).getList("LikedBy").size() > 0) {
                        for (Object obj : objects.get(0).getList("LikedBy")) {
                            if (obj.equals(ParseUser.getCurrentUser().getUsername())) {
                                binding.imageViewLike.setImageResource(R.drawable.ic_like_pressed);
                                isLiked = true;
                            }
                        }
                    }

                    binding.textViewLikeCounter.setText(objects.get(0).getList("LikedBy").size()+"");

                    parseObjectList = new ArrayList<>();
                    ParseQuery<ParseObject> parseQuery = ParseQuery.getQuery("replies");
                    parseQuery.whereEqualTo("tweetId", tweetId);
                    parseQuery.findInBackground(new FindCallback<ParseObject>() {
                        @Override
                        public void done(List<ParseObject> objects, ParseException e) {
                            if (objects.size() > 0 && e == null) {
                                for (ParseObject object : objects) {
                                    parseObjectList.add(object);
                                }
                                binding.textViewReplyCounter.setText(parseObjectList.size()+"");
                                adapter = new TweetReplyRecyclerViewAdapter(parseObjectList);
                                binding.replyTweetsRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
                                DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(getContext(), LinearLayoutManager.VERTICAL);
                                dividerItemDecoration.setDrawable(getContext().getResources().getDrawable(R.drawable.tweet_background));
                                binding.replyTweetsRecyclerView.addItemDecoration(dividerItemDecoration);
                                binding.replyTweetsRecyclerView.setAdapter(adapter);
                            } else {
                                Toast.makeText(getContext(),objects.size()+"",Toast.LENGTH_SHORT).show();
                            }
                        }
                    });



                }
            });



        } else {
            ParseQuery<ParseObject> query = ParseQuery.getQuery("replies");
            query.whereEqualTo("reply", tweet);
            query.findInBackground(new FindCallback<ParseObject>() {
                @Override
                public void done(List<ParseObject> objects, ParseException e) {

                    binding.imageViewReplies.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            AppCompatActivity activity = (AppCompatActivity) v.getContext();
                            Fragment myFragment = new CreateTweetFragment(true,
                                    binding.textViewTweetContent.getText().toString(),
                                    binding.textViewTweetUsername.getText().toString(),
                                    objects.get(0).getObjectId());
                            activity.getSupportFragmentManager().beginTransaction().replace(R.id.fragmentContainer, myFragment).addToBackStack(null).commit();
                        }
                    });

                    binding.imageViewLike.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            if (!isLiked) {
                                binding.imageViewLike.setImageResource(R.drawable.ic_like_pressed);
                                objects.get(0).add("LikedBy", ParseUser.getCurrentUser().getUsername());
                                objects.get(0).saveInBackground(new SaveCallback() {
                                    @Override
                                    public void done(ParseException e) {
                                        binding.textViewLikeCounter.setText(objects.get(0).getList("LikedBy").size()+"");
                                    }
                                });
                                isLiked = true;
                            } else {
                                binding.imageViewLike.setImageResource(R.drawable.ic_like);
                                objects.get(0).getList("LikedBy").remove(ParseUser.getCurrentUser().getUsername());
                                List tempList1 = objects.get(0).getList("LikedBy");
                                objects.get(0).remove("LikedBy");
                                objects.get(0).put("LikedBy", tempList1);
                                objects.get(0).saveInBackground(new SaveCallback() {
                                    @Override
                                    public void done(ParseException e) {
                                        binding.textViewLikeCounter.setText(objects.get(0).getList("LikedBy").size()+"");
                                    }
                                });
                                isLiked = false;
                            }
                        }
                    });


                    if (objects.get(0).getList("LikedBy").size() > 0) {
                        for (Object obj : objects.get(0).getList("LikedBy")) {
                            if (obj.equals(ParseUser.getCurrentUser().getUsername())) {
                                binding.imageViewLike.setImageResource(R.drawable.ic_like_pressed);
                                isLiked = true;
                            }
                        }
                    }

                    binding.textViewLikeCounter.setText(objects.get(0).getList("LikedBy").size()+"");

                    parseObjectList = new ArrayList<>();
                    ParseQuery<ParseObject> parseQuery = ParseQuery.getQuery("replies");
                    parseQuery.whereEqualTo("tweetId", tweetId);
                    parseQuery.findInBackground(new FindCallback<ParseObject>() {
                        @Override
                        public void done(List<ParseObject> objects, ParseException e) {
                            if (objects.size() > 0 && e == null) {
                                for (ParseObject object : objects) {
                                    parseObjectList.add(object);
                                }
                                binding.textViewReplyCounter.setText(parseObjectList.size()+"");
                                adapter = new TweetReplyRecyclerViewAdapter(parseObjectList);
                                binding.replyTweetsRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
                                DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(getContext(), LinearLayoutManager.VERTICAL);
                                dividerItemDecoration.setDrawable(getContext().getResources().getDrawable(R.drawable.tweet_background));
                                binding.replyTweetsRecyclerView.addItemDecoration(dividerItemDecoration);
                                binding.replyTweetsRecyclerView.setAdapter(adapter);
                            } else {
                                Toast.makeText(getContext(),objects.size()+"",Toast.LENGTH_SHORT).show();
                            }
                        }
                    });



                }
            });
        }





        return view;
    }
}