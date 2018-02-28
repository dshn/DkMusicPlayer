package com.fdemo.Fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.fdemo.R;
import com.ogaclejapan.smarttablayout.SmartTabLayout;

/**
 * Created by DP on 9/19/2017.
 */

public class PlaylistsFragment extends Fragment{

    private View view;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view= inflater.inflate(R.layout.fragment_playlists, container, false);
        //  recyclerView = (RecyclerView)view. findViewById(R.id.recycler_view);
        return view;
    }





}
