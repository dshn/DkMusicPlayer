package com.fdemo.Adapters;

import android.content.Intent;
import android.support.v4.app.FragmentActivity;
import android.support.v7.widget.PopupMenu;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.fdemo.Activities.AndroidBuildingMusicPlayerActivity;
import com.fdemo.Models.Model;
import com.fdemo.Models.Song;
import com.fdemo.R;

import java.util.ArrayList;

import info.androidhive.sqlite.database.DatabaseHelper;

/**
 * Created by DP on 9/19/2017.
 */

public class SongsAdapter extends RecyclerView.Adapter<SongsAdapter.MyViewHolder>{


    private final FragmentActivity mContext;
    private final ArrayList<Song> mAudioList;
    private final DatabaseHelper db;
    private PopupMenu popup;
    private View itemView;
    private int mPosition;

    public SongsAdapter(FragmentActivity activity, ArrayList<Song> audioList) {
        mContext=activity;
        mAudioList=audioList;
        db = new DatabaseHelper(activity);
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView name, year, artist;
        public ImageView imageOverflow;

        public MyViewHolder(View view) {
            super(view);
            name = (TextView) view.findViewById(R.id.title);
            artist = (TextView) view.findViewById(R.id.genre);
            imageOverflow = (ImageView) view.findViewById(R.id.img_overflow);
        }
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.custom_view, parent, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, final int position) {
        holder.name.setText(mAudioList.get(position).getTitle());
        holder.artist.setText(mAudioList.get(position).getArtist());

        showPopup(holder.imageOverflow);

        holder.imageOverflow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                popup.show();
            }
        });

        itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mPosition=position;

                Intent i = new Intent(mContext, AndroidBuildingMusicPlayerActivity.class);
                i.putExtra("pos",""+position);
                mContext.startActivity(i);
            }
        });

    }

    @Override
    public int getItemCount() {
        return mAudioList.size();
    }

    public void showPopup(ImageView img){
        popup = new PopupMenu(mContext, img);
        MenuInflater inflater = popup.getMenuInflater();
        inflater.inflate(R.menu.menu, popup.getMenu());

        popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {

                switch (item.getItemId()) {
                    case R.id.action_add_to_playlist:


                        return true;

                    /*case R.id.album_overflow_rename:
                        renameAlbum(mAlbum);
                        return true;

                    case R.id.album_overflow_lock:
                        lockAlbum(mAlbum);
                        return true;

                    case R.id.album_overflow_unlock:
                        unlockAlbum(mAlbum);
                        return true;

                    case R.id.album_overflow_set_cover:
                        setAlbumCover(mAlbum);
                        return true;*/

                   /* default:
                        return super.onMenuItemSelected(menu, item);*/
                }

                return false;
            }
        });

    }

}
