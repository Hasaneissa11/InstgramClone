package com.example.instgramclone;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

public class TapAdapter extends FragmentPagerAdapter {
    public TapAdapter(@NonNull FragmentManager fm) {
        super(fm);
    }

    @NonNull
    @Override
    public Fragment getItem(int tapSelected) {

        switch (tapSelected)
        {

            case 0 :
                Profile profile = new Profile();
                return profile ;
            case 1 :
                Users users = new Users();
                return users;
            case 2 :
                Share share = new Share();
                return share;
            default:
                return null ;


        }

    }

    @Override
    public int getCount() {
        return 3;
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {

        switch (position)
        {
            case 0:
                return "Profile" ;
            case 1 :
                return "Users" ;
            case 2 :
                return "Share" ;
            default:
                return null ;

        }

    }
}
