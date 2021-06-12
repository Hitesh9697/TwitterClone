package com.example.twitterclone.Controller;

import android.os.Parcelable;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.fragment.app.FragmentStatePagerAdapter;

import com.example.twitterclone.Model.ProfileTweetTabFragment;
import com.example.twitterclone.Model.UserProfileFragment;

import org.jetbrains.annotations.NotNull;

public class TabAdapter extends FragmentStatePagerAdapter {
    String username;
    public TabAdapter(@NonNull @NotNull FragmentManager fm, String user) {
        super(fm);
        username = user;
    }

    @NonNull
    @NotNull
    @Override
    public Fragment getItem(int position) {
        switch (position) {

            case 0:

                return new ProfileTweetTabFragment(username);
            case 1:

                return new ProfileTweetTabFragment(username);
            default:
                return null;
        }
    }

    @Override
    public int getItemPosition(@NonNull @NotNull Object object) {
        return POSITION_NONE;
    }

    @Override
    public int getCount() {
        return 2;
    }

    @Nullable
    @org.jetbrains.annotations.Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        switch (position) {

            case 0:
                return "Tweet";
            case 1:
                return "Replies";
            default:
                return null;

        }
    }

    @Override
    public Parcelable saveState() {
        return null;
    }

    @Override
    public void restoreState(Parcelable state, ClassLoader loader) {

    }
}
