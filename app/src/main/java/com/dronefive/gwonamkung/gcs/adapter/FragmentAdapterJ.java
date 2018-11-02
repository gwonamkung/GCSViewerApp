package com.dronefive.gwonamkung.gcs.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import com.dronefive.gwonamkung.gcs.fragment.Fragment1;
import com.dronefive.gwonamkung.gcs.fragment.Fragment2;
import com.dronefive.gwonamkung.gcs.fragment.Fragment3;

public class FragmentAdapterJ extends FragmentStatePagerAdapter{
    Fragment1 fragment1 = new Fragment1();
    Fragment2 fragment2 = new Fragment2();
    Fragment3 fragment3 = new Fragment3();
    Fragment[] fragments = {fragment1, fragment2, fragment3};

    public FragmentAdapterJ(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int i) {
        return fragments[i];
    }

    @Override
    public int getCount() {
        return fragments.length;
    }
}
