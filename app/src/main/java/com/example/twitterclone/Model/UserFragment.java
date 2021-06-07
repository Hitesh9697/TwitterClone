package com.example.twitterclone.Model;

import android.app.Activity;
import android.os.Bundle;

import androidx.activity.OnBackPressedCallback;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentManager;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Toast;

import com.example.twitterclone.R;
import com.example.twitterclone.databinding.FragmentUserBinding;
import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseQuery;
import com.parse.ParseUser;

import java.util.ArrayList;
import java.util.List;

public class UserFragment extends Fragment implements AdapterView.OnItemClickListener {

    private FragmentUserBinding binding;
    private ArrayList<String> userList;
    private ArrayAdapter adapter;
    private FragmentActivity myContext;


    public UserFragment() {
        // Required empty public constructor
    }

    public static UserFragment newInstance() {
        UserFragment fragment = new UserFragment();
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

        binding = FragmentUserBinding.inflate(inflater, container, false);
        View view = binding.getRoot();

        userList = new ArrayList<>();
        adapter = new ArrayAdapter(getContext(), android.R.layout.simple_list_item_1, userList);
        binding.userListView.setOnItemClickListener(this);
        ParseQuery<ParseUser> query = ParseUser.getQuery();
        query.whereNotEqualTo("username", ParseUser.getCurrentUser().getUsername());
        query.findInBackground(new FindCallback<ParseUser>() {
            @Override
            public void done(List<ParseUser> objects, ParseException e) {
                if (e == null && objects.size() > 0) {
                    for (ParseUser user : objects) {
                        userList.add(user.getUsername());
                        Log.i("users", user.getUsername());
                    }
                    binding.userListView.setAdapter(adapter);
                } else {
                    Toast.makeText(getContext(), objects.size() + "", Toast.LENGTH_SHORT).show();
                }
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


    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        FragmentManager fragManager = myContext.getSupportFragmentManager();
        fragManager.beginTransaction().replace(R.id.fragmentContainer,  new UserProfileFragment(userList.get(position), true, 1f)).commitNow();

    }


}