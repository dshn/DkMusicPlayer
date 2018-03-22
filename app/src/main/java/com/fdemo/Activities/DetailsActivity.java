package com.fdemo.Activities;

import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.fdemo.Adapters.SongsAdapter;
import com.fdemo.Adapters.SongsDetailsAdapter;
import com.fdemo.Models.Model;
import com.fdemo.Models.Song;
import com.fdemo.R;
import com.fdemo.Utils.RecyclerItemClickListener;

import java.io.Serializable;
import java.util.ArrayList;

public class DetailsActivity extends AppCompatActivity {

    private Model songs;
    private ImageView imgThumb;
    private RecyclerView recyclerViewDetails;
    private SongsDetailsAdapter mAdapter;
    private ArrayList<Song> songsList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);

        imgThumb=(ImageView)findViewById(R.id.img_thumb);
        recyclerViewDetails=(RecyclerView)findViewById(R.id.recycler_view_details);

        songs= getIntent().getParcelableExtra("Model");
        getAlbumSongs(Integer.parseInt(songs.getAlbumId()));
        Glide.with(this).load(songs.getThumbnail()).into(imgThumb);

        mAdapter = new SongsDetailsAdapter(DetailsActivity.this,songsList);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerViewDetails.setLayoutManager(mLayoutManager);
        recyclerViewDetails.setItemAnimator(new DefaultItemAnimator());
        recyclerViewDetails.setAdapter(mAdapter);

        recyclerViewDetails.addOnItemTouchListener(
                new RecyclerItemClickListener(DetailsActivity.this, recyclerViewDetails, new RecyclerItemClickListener.OnItemClickListener() {
                    @Override
                    public void onItemClick(View view, int position) {
                        //do whatever

                        Intent i = new Intent(DetailsActivity.this, AndroidBuildingMusicPlayerActivity.class);
                        i.putExtra("pos",""+position);
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
     *
     * @param albumId
     */
    private void getAlbumSongs(int albumId){
        songsList = new ArrayList<>();
        String selection = "is_music != 0";

        if (albumId > 0) {
            selection = selection + " and album_id = " + albumId;
        }

        String[] projection = {
                MediaStore.Audio.Media.TITLE,
                MediaStore.Audio.Media.ARTIST,
                MediaStore.Audio.Media.DATA,
                MediaStore.Audio.Media.DISPLAY_NAME,
                MediaStore.Audio.Media.DURATION,
                MediaStore.Audio.Media.ALBUM,
                MediaStore.Audio.Media.ALBUM_ID
        };
        final String sortOrder = MediaStore.Audio.AudioColumns.TITLE + " COLLATE LOCALIZED ASC";

        Cursor cursor = null;
        try {
            Uri uri = android.provider.MediaStore.Audio.Media.EXTERNAL_CONTENT_URI;
            cursor = getContentResolver().query(uri, projection, selection, null, sortOrder);
            if (cursor != null) {
                cursor.moveToFirst();
                int position = 1;
                while (!cursor.isAfterLast()) {
                    Song song = new Song();
                    song.setTitle(cursor.getString(0));
                    song.setDuration(cursor.getLong(4));
                    song.setArtist(cursor.getString(1));
                    song.setPath(cursor.getString(2));
                    song.setPosition(position);
                    song.setAlbumId(cursor.getLong(6));
                    songsList.add(song);
                    cursor.moveToNext();
                }
            }

        } catch (Exception e) {
            Log.e("Media", e.toString());
        } finally {
            if (cursor != null) {
                cursor.close();
            }
        }
    }

}
