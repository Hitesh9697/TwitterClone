package com.example.twitterclone;

import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import com.example.twitterclone.databinding.ActivityHomeBinding;
import com.parse.ParseUser;

public class HomeActivity extends AppCompatActivity {

    private ActivityHomeBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityHomeBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);
        setSupportActionBar(binding.toolBar);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(HomeActivity.this, binding.drawerLayout, binding.toolBar,
                R.string.nav_app_bar_open_drawer_description, R.string.nav_app_bar_open_drawer_description);
        binding.drawerLayout.addDrawerListener(toggle);
        toggle.syncState();
        View header = binding.navigationView.getHeaderView(0);
        TextView text = header.findViewById(R.id.textViewNavUserName);
        text.setText(ParseUser.getCurrentUser().getUsername());
    }

    @Override
    public void onBackPressed() {
        if (binding.drawerLayout.isDrawerOpen(GravityCompat.START)) {
           binding.drawerLayout.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }
}