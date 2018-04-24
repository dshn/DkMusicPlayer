package com.fdemo.Fragments;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.fdemo.Activities.AndroidBuildingMusicPlayerActivity;
import com.fdemo.Activities.DetailsActivity;
import com.fdemo.Adapters.SongsAdapter;
import com.fdemo.Models.Model;
import com.fdemo.Models.Song;
import com.fdemo.R;
import com.fdemo.Utils.RecyclerItemClickListener;

import java.util.ArrayList;

import info.androidhive.sqlite.database.DatabaseHelper;

/**
 * Created by DP on 9/19/2017.
 */

public class SongsFragment extends Fragment {

    private View view;
    private RecyclerView recyclerView;
    private SongsAdapter mAdapter;
    private ArrayList<Song> audioList;
    private DatabaseHelper db;
    // public static ArrayList<Model> songsList=new ArrayList<>();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view= inflater.inflate(R.layout.fragment_songs, container, false);
        recyclerView = (RecyclerView)view. findViewById(R.id.recycler_view);
        return view;
    }


    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);



        audioList = new ArrayList<>();
        mAdapter = new SongsAdapter(getActivity(),audioList);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getActivity().getApplicationContext());
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(mAdapter);

        getAllSongs();

        AndroidBuildingMusicPlayerActivity.playlist=audioList;

       /* recyclerView.addOnItemTouchListener(
                new RecyclerItemClickListener(getActivity(), recyclerView, new RecyclerItemClickListener.OnItemClickListener() {
                    @Override
                    public void onItemClick(View view, int position) {
                        //do whatever

                        Intent i = new Intent(getActivity(), AndroidBuildingMusicPlayerActivity.class);
                        i.putExtra("pos",""+position);
                        startActivity(i);
                    }

                    @Override
                    public void onLongItemClick(View view, int position) {
                        // do whatever
                    }
                })
        );*/

    }

    private void getAllSongs() {

        //http://www.oodlestechnologies.com/blogs/Retrieving-all-the-Audio-files-from-MediaStore-in-Android

        String[] proj = { MediaStore.Audio.Media._ID,MediaStore.Audio.Media.DISPLAY_NAME,MediaStore.Audio.Media.DATA,MediaStore.Audio.Media.ARTIST};// Can include more data for more details and check it.

        Cursor audioCursor = getActivity().getContentResolver().query(MediaStore.Audio.Media.EXTERNAL_CONTENT_URI, proj, null, null, null);

        if(audioCursor != null){
            if(audioCursor.moveToFirst()){
                do{
                    int nameIndex = audioCursor.getColumnIndexOrThrow(MediaStore.Audio.Media.DISPLAY_NAME);
                    int pathIndex = audioCursor.getColumnIndexOrThrow(MediaStore.Audio.Media.DATA);
                    int artistIndex = audioCursor.getColumnIndexOrThrow(MediaStore.Audio.Media.ARTIST);

                    Song model=new Song();

                    model.setTitle(audioCursor.getString(nameIndex));
                    model.setPath(audioCursor.getString(pathIndex));
                    model.setArtist(audioCursor.getString(artistIndex));

                    audioList.add(model);
                }while(audioCursor.moveToNext());
            }
        }
        audioCursor.close();
        mAdapter.notifyDataSetChanged();
    }


}
