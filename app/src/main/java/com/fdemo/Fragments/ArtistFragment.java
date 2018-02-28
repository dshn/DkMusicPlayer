package com.fdemo.Fragments;

import android.content.res.Resources;
import android.database.Cursor;
import android.graphics.Rect;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.DisplayMetrics;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.fdemo.Adapters.ArtistAdapter;
import com.fdemo.Models.Model;
import com.fdemo.R;


import java.util.ArrayList;

/**
 * Created by DP on 9/20/2017.
 */

public class ArtistFragment extends Fragment {

    private View view;
    private RecyclerView recyclerView;
    private ArrayList<Model> artistList;
    private ArtistAdapter mAdapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view= inflater.inflate(R.layout.fragment_artist, container, false);
        recyclerView = (RecyclerView)view. findViewById(R.id.recycler_view_artists);
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        artistList = new ArrayList<Model>();
        mAdapter = new ArtistAdapter(getActivity(),artistList) ;
        RecyclerView.LayoutManager mLayoutManager = new GridLayoutManager(getActivity(), calculateNoOfColumns());
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.addItemDecoration(new GridSpacingItemDecoration(calculateNoOfColumns(), dpToPx(10), true));
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(mAdapter);
        getArtistsData();
    }

    private void getArtistsData() {

        String[] mProjection =
                {
                        MediaStore.Audio.Artists._ID,
                        MediaStore.Audio.Artists.ARTIST,
                        MediaStore.Audio.Artists.NUMBER_OF_TRACKS,
                        MediaStore.Audio.Artists.NUMBER_OF_ALBUMS,

                };

        Cursor cursor = getActivity().getContentResolver().query(
                MediaStore.Audio.Artists.EXTERNAL_CONTENT_URI,
                mProjection,
                null,
                null,
                MediaStore.Audio.Artists.ARTIST + " ASC");

        if(cursor != null){
            if(cursor.moveToFirst()){
                do{
                    int nameIndex = cursor.getColumnIndexOrThrow(MediaStore.Audio.Artists.ARTIST);
                    //   int pathIndex = cursor.getColumnIndexOrThrow(MediaStore.Audio.Media.DATA);
                 //   int artistIndex = cursor.getColumnIndexOrThrow(MediaStore.Audio.Albums.ARTIST);
                //   int artIndex = cursor.getColumnIndexOrThrow(MediaStore.Audio.Artists.Albums.ALBUM_ART);
                    int noOfSongsIndex = cursor.getColumnIndexOrThrow(MediaStore.Audio.Artists.NUMBER_OF_TRACKS);

                    Model model=new Model();
                    model.setName(cursor.getString(nameIndex));
                    //  model.setPath(cursor.getString(pathIndex));
                  //  model.setArtist(cursor.getString(artistIndex));
              //      model.setThumbnail(cursor.getString(artIndex));
                    model.setNumOfSongs(cursor.getInt(noOfSongsIndex));

                    artistList.add(model);
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
