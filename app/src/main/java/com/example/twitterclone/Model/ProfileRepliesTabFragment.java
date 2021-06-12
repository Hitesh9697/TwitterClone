package com.example.twitterclone.Model;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.twitterclone.R;
import com.example.twitterclone.databinding.FragmentProfileRepliesTabBinding;

public class ProfileRepliesTabFragment extends Fragment {

    private FragmentProfileRepliesTabBinding binding;

    public ProfileRepliesTabFragment() {
        // Required empty public constructor
    }

    // TODO: Rename and change types and number of parameters
    public static ProfileRepliesTabFragment newInstance(String param1, String param2) {
        ProfileRepliesTabFragment fragment = new ProfileRepliesTabFragment();
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
        binding = FragmentProfileRepliesTabBinding.inflate(inflater, container, false);
        View view = binding.getRoot();


        return view;
    }
}