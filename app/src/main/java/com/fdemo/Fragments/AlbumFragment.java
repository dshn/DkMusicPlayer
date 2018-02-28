package com.fdemo.Fragments;

import android.content.Intent;
import android.content.res.Resources;
import android.database.Cursor;
import android.graphics.Rect;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.DisplayMetrics;
import android.util.Log;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.fdemo.Activities.DetailsActivity;
import com.fdemo.Adapters.AlbumsAdapter;
import com.fdemo.Adapters.SongsAdapter;
import com.fdemo.Models.Model;
import com.fdemo.Models.Song;
import com.fdemo.R;
import com.fdemo.Utils.RecyclerItemClickListener;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Created by DP on 9/19/2017.
 */

public class AlbumFragment extends Fragment {

    private View view;
    private RecyclerView recyclerView;
    private ArrayList<Model> albumList;
    private AlbumsAdapter mAdapter;
    ArrayList<Song> songs;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view= inflater.inflate(R.layout.fragment_album, container, false);
        recyclerView = (RecyclerView)view. findViewById(R.id.recycler_view_albums);
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        albumList = new ArrayList<>();
        mAdapter = new AlbumsAdapter(getActivity(),albumList) ;
        RecyclerView.LayoutManager mLayoutManager = new GridLayoutManager(getActivity(), calculateNoOfColumns());
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.addItemDecoration(new GridSpacingItemDecoration(calculateNoOfColumns(), dpToPx(10), true));
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(mAdapter);
        getAlbumData();

        recyclerView.addOnItemTouchListener(
                new RecyclerItemClickListener(getActivity(), recyclerView, new RecyclerItemClickListener.OnItemClickListener() {
                    @Override
                    public void onItemClick(View view, int position) {
                        //do whatever

                        Intent i = new Intent(getActivity(), DetailsActivity.class);
                        i.putExtra("Model",albumList.get(position));
                        startActivity(i);
                    }

                    @Override
                    public void onLongItemClick(View view, int position) {
                        // do whatever
                    }
                })
        );

    }

    /**
     * gets data from album
     */
    private void getAlbumData() {

        //https://stackoverflow.com/questions/9123700/listing-album-in-android

        String[] projection = new String[] { MediaStore.Audio.Albums._ID, MediaStore.Audio.Albums.ALBUM, MediaStore.Audio.Albums.ARTIST, MediaStore.Audio.Albums.ALBUM_ART, MediaStore.Audio.Albums.NUMBER_OF_SONGS };
        String selection = null;
        String[] selectionArgs = null;
        String sortOrder = MediaStore.Audio.Media.ALBUM + " ASC";
        Cursor cursor = getActivity().getContentResolver().query(MediaStore.Audio.Albums.EXTERNAL_CONTENT_URI, projection, selection, selectionArgs, sortOrder);
        if(cursor != null){
            if(cursor.moveToFirst()){
                do{
                    int nameIndex = cursor.getColumnIndexOrThrow(MediaStore.Audio.Albums.ALBUM);
                    int albumId = cursor.getColumnIndexOrThrow(MediaStore.Audio.Albums._ID);
                    int artistIndex = cursor.getColumnIndexOrThrow(MediaStore.Audio.Albums.ARTIST);
                    int artIndex = cursor.getColumnIndexOrThrow(MediaStore.Audio.Albums.ALBUM_ART);
                    int noOfSongsIndex = cursor.getColumnIndexOrThrow(MediaStore.Audio.Albums.NUMBER_OF_SONGS);

                    Model model=new Model();
                    model.setName(cursor.getString(nameIndex));
                    model.setAlbumId(cursor.getString(albumId));
                    model.setArtist(cursor.getString(artistIndex));
                    model.setThumbnail(cursor.getString(artIndex));
                    model.setNumOfSongs(cursor.getInt(noOfSongsIndex));
                 //   getAlbumSongs(Integer.parseInt(model.getAlbumId()));
                 //   model.setSongs(songs);
                    albumList.add(model);
                }while(cursor.moveToNext());
            }
        }
        cursor.close();


        mAdapter.notifyDataSetChanged();
    }


    /**
     * RecyclerView item decoration - give equal margin around grid item
     */
    public class GridSpacingItemDecoration extends RecyclerView.ItemDecoration {

        private int spanCount;
        private int spacing;
        private boolean includeEdge;

        public GridSpacingItemDecoration(int spanCount, int spacing, boolean includeEdge) {
            this.spanCount = spanCount;
            this.spacing = spacing;
            this.includeEdge = includeEdge;
        }

        @Override
        public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
            int position = parent.getChildAdapterPosition(view); // item position
            int column = position % spanCount; // item column

            if (includeEdge) {
                outRect.left = spacing - column * spacing / spanCount; // spacing - column * ((1f / spanCount) * spacing)
                outRect.right = (column + 1) * spacing / spanCount; // (column + 1) * ((1f / spanCount) * spacing)

                if (position < spanCount) { // top edge
                    outRect.top = spacing;
                }
                outRect.bottom = spacing; // item bottom
            } else {
                outRect.left = column * spacing / spanCount; // column * ((1f / spanCount) * spacing)
                outRect.right = spacing - (column + 1) * spacing / spanCount; // spacing - (column + 1) * ((1f /    spanCount) * spacing)
                if (position >= spanCount) {
                    outRect.top = spacing; // item top
                }
            }
        }
    }

    /**
     * Converting dp to pixel
     */
    private int dpToPx(int dp) {
        Resources r = getResources();
        return Math.round(TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp, r.getDisplayMetrics()));
    }

    public int calculateNoOfColumns() {
        DisplayMetrics displayMetrics = getActivity().getResources().getDisplayMetrics();
        float dpWidth = displayMetrics.widthPixels / displayMetrics.density;
        int noOfColumns = (int) (dpWidth / 180);
        return noOfColumns;
    }

}
