package io.github.xerada.SectionPageAdapters;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import io.github.xerada.Fragments.Home_Fragment;
import io.github.xerada.Fragments.Notification_fragment;
import io.github.xerada.Fragments.Online_Friends_fragment;

/**
 * Created by Lakshay on 6/24/2017.
 */
public class SectionsPageAdapter extends FragmentPagerAdapter {

    public SectionsPageAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {
        switch (position){
            case 0:
                Home_Fragment home_fragment = new Home_Fragment();
                return home_fragment;
            case 1:
                Notification_fragment notification_fragment = new Notification_fragment();
                return notification_fragment;
           case 2:
               Online_Friends_fragment online_friends_fragment = new Online_Friends_fragment();
               return online_friends_fragment;
            default:
                return null;
        }



    }

    @Override
    public int getCount() {
        return 3;
    }
}
