package com.example.twitterclone.Model;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.example.twitterclone.LoginActivity;
import com.example.twitterclone.R;
import com.example.twitterclone.databinding.ActivityHomeBinding;
import com.google.android.material.navigation.NavigationView;
import com.parse.ParseUser;

public class HomeActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    private ActivityHomeBinding binding;
    private ProgressDialog progressDialog;

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
        progressDialog = new ProgressDialog(HomeActivity.this);
        View header = binding.navigationView.getHeaderView(0);
        TextView text = header.findViewById(R.id.textViewNavUserName);
        text.setText(ParseUser.getCurrentUser().getUsername());
        getSupportFragmentManager().beginTransaction().replace(R.id.fragmentContainer,  new TweeterFeedFragment()).addToBackStack(null).commit();
        binding.navigationView.setNavigationItemSelectedListener(this);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.logout_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        switch (item.getItemId()) {

            case R.id.itemLogout :
                progressDialog.setMessage("Logging out");
                ParseUser.logOutInBackground(e -> {
                    progressDialog.dismiss();
                    finish();
                    Intent intent = new Intent(HomeActivity.this, LoginActivity.class);
                    startActivity(intent);
                });
        }
        return super.onOptionsItemSelected(item);
    }


    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {

        switch (item.getItemId()) {

            case R.id.itemProfile :
                getSupportFragmentManager().beginTransaction().replace(R.id.fragmentContainer,  new UserProfileFragment(ParseUser.getCurrentUser().getUsername(), false, 0f)).addToBackStack(null).commit();
                break;
            case R.id.itemUsers :
                getSupportFragmentManager().beginTransaction().replace(R.id.fragmentContainer,  new UserFragment()).addToBackStack(null).commit();
                break;
            case R.id.itemFollowing :
                getSupportFragmentManager().beginTransaction().replace(R.id.fragmentContainer,  new FollowingFragment()).addToBackStack(null).commit();
                break;
            case R.id.itemFollowers :
                getSupportFragmentManager().beginTransaction().replace(R.id.fragmentContainer,  new FollowersFragment()).addToBackStack(null).commit();
                break;
            case R.id.itemTweetFeed :
                getSupportFragmentManager().beginTransaction().replace(R.id.fragmentContainer,  new TweeterFeedFragment()).commit();
                break;
        }
        binding.drawerLayout.closeDrawer(GravityCompat.START);
        return true;
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