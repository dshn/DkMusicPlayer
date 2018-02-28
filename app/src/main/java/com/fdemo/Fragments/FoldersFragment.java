package com.fdemo.Fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.HorizontalScrollView;

import com.fdemo.Adapters.FileManagerAdapter;
import com.fdemo.Adapters.SongsAdapter;
import com.fdemo.Models.FileModel;
import com.fdemo.R;
import com.ogaclejapan.smarttablayout.SmartTabLayout;

import java.util.ArrayList;

/**
 * Created by Darshan on 9/25/2017.
 */

public class FoldersFragment extends Fragment {

    private View view;
    private HorizontalScrollView hScrollview;
    private RecyclerView recyclerView;
    private String path;
    private ArrayList<FileModel> fileList;
    private FileManagerAdapter mAdapter;
    private FileModel model;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view= inflater.inflate(R.layout.fragment_folder, container, false);
        hScrollview=(HorizontalScrollView)view.findViewById(R.id.h_scroll_view);
        recyclerView = (RecyclerView)view. findViewById(R.id.recycler_view);
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        fileList = new ArrayList<FileModel>();
        path="Home";

        

        model=new FileModel();
        model.setFileName("0");
        model.setFilePath("/Home/0/");

        fileList.add(model);

        model=new FileModel();
        model.setFileName("sdscard");
        model.setFilePath("/Home/sdcard/");

        fileList.add(model);
        mAdapter = new FileManagerAdapter(getActivity(),fileList);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getActivity().getApplicationContext());
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(mAdapter);


    }
}
