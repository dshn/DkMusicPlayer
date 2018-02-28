package com.fdemo.Fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.fdemo.Adapters.SongsAdapter;
import com.fdemo.R;
import com.ogaclejapan.smarttablayout.SmartTabLayout;
import com.ogaclejapan.smarttablayout.utils.v4.FragmentPagerItemAdapter;
import com.ogaclejapan.smarttablayout.utils.v4.FragmentPagerItems;

/**
 * Created by DP on 9/19/2017.
 */

public class MusicLibFragment extends Fragment{


    private View view;
    private RecyclerView recyclerView;
    private SongsAdapter mAdapter;
    private ViewPager viewPager;
    private SmartTabLayout viewPagerTab;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view= inflater.inflate(R.layout.fragment_music_lib, container, false);
        viewPager = (ViewPager)view. findViewById(R.id.viewpager);
        viewPagerTab = (SmartTabLayout)view. findViewById(R.id.viewpagertab);
      //  recyclerView = (RecyclerView)view. findViewById(R.id.recycler_view);
        return view;
    }


    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        FragmentPagerItemAdapter adapter = new FragmentPagerItemAdapter(
                getChildFragmentManager(), FragmentPagerItems.with(getActivity())
                .add("Songs", SongsFragment.class)
                .add("Playlists", PlaylistsFragment.class)
                .add("Album",AlbumFragment.class)
                .add("Artist", ArtistFragment.class)
                .create());

        viewPager.setAdapter(adapter);
        viewPagerTab.setViewPager(viewPager);
    }
}
