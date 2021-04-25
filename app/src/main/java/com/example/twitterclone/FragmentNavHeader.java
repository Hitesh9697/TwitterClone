package com.example.twitterclone;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.activity.OnBackPressedCallback;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentManager;

import com.example.twitterclone.databinding.NavHeaderBinding;
import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.ParseUser;
import com.parse.SaveCallback;

import java.util.List;

public class FragmentNavHeader extends Fragment {

    private NavHeaderBinding binding;
    private String username;
    private FragmentActivity myContext;
    private Boolean btnClickable;
    private Float btnAlpha;
    private List<String> userList;
    private ParseObject parseObject;

    public FragmentNavHeader() {
        // Required empty public constructor
        btnClickable = false;
        btnAlpha = 0.0f;
    }

    public FragmentNavHeader(String s, Boolean b,Float f, List l) {
        username = s;
        btnClickable = b;
        btnAlpha = f;
        userList = l;
    }

    public static FragmentNavHeader newInstance() {
        FragmentNavHeader fragment = new FragmentNavHeader();
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
        binding = NavHeaderBinding.inflate(inflater, container, false);
        View view = binding.getRoot();
        binding.textViewNavUserName.setText(username);
        binding.button.setClickable(btnClickable);
        binding.button.setAlpha(btnAlpha);
        /*for (String user : userList) {
            if (ParseUser.getCurrentUser().getList("following").contains(user)) {
                binding.button.setText("Following");
            }
        }*/
        if (ParseUser.getCurrentUser().getList("following").contains(username)) {
            binding.button.setText("Following");
        } else {
            binding.button.setText("Follow");
        }

        binding.button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (binding.button.getText().equals("Follow")) {
                    binding.button.setText("Following");
                    ParseUser.getCurrentUser().add("following", username);
                    ParseQuery<ParseObject> query = ParseQuery.getQuery("Follow");
                    query.whereEqualTo("username", username);
                    query.findInBackground(new FindCallback<ParseObject>() {
                        @Override
                        public void done(List<ParseObject> objects, ParseException e) {
                            parseObject = objects.get(0);
                            parseObject.add("followers", ParseUser.getCurrentUser().getUsername());
                            parseObject.saveInBackground();

                        }
                    });
                } else {
                    binding.button.setText("Follow");
                    ParseUser.getCurrentUser().getList("following").remove(username);
                    List tempList = ParseUser.getCurrentUser().getList("following");
                    ParseUser.getCurrentUser().remove("following");
                    ParseUser.getCurrentUser().put("following", tempList);

                    ParseQuery<ParseObject> query = ParseQuery.getQuery("Follow");
                    query.whereEqualTo("username", username);
                    query.findInBackground(new FindCallback<ParseObject>() {
                        @Override
                        public void done(List<ParseObject> objects, ParseException e) {
                            parseObject = objects.get(0);
                            parseObject.getList("followers").remove(ParseUser.getCurrentUser().getUsername());
                            List tempList1 = parseObject.getList("followers");
                            parseObject.remove("followers");
                            parseObject.put("followers", tempList1);
                            parseObject.saveInBackground();
                        }
                    });

                }
                ParseUser.getCurrentUser().saveInBackground();
            }
        });



        requireActivity().getOnBackPressedDispatcher().addCallback(getViewLifecycleOwner(), new OnBackPressedCallback(true) {
            @Override
            public void handleOnBackPressed() {
                FragmentManager fragManager = myContext.getSupportFragmentManager();
                fragManager.beginTransaction().replace(R.id.fragmentContainer,  new UserFragment()).commit();
            }
        });

        return view;
    }

}
