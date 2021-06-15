package com.example.twitterclone.Model;

import android.app.Activity;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentManager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.twitterclone.Controller.TabAdapter;
import com.example.twitterclone.Controller.TweeterFeedRecyclerViewAdapter;
import com.example.twitterclone.R;
import com.example.twitterclone.databinding.FragmentUserProfileBinding;
import java.util.List;

public class UserProfileFragment extends Fragment {

    private FragmentUserProfileBinding binding;
    private TweeterFeedRecyclerViewAdapter adapter;
    private FragmentActivity myContext;
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


        AppCompatActivity activity = (AppCompatActivity) view.getContext();
        TabAdapter tabAdapter = new TabAdapter(activity.getSupportFragmentManager(), username);
        //binding.viewPager.removeAllViews();
        tabAdapter.notifyDataSetChanged();
        binding.viewPager.setAdapter(tabAdapter);
        binding.tabs.setupWithViewPager(binding.viewPager, true);


        return view;
    }
}