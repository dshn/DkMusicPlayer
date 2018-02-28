package com.fdemo.Adapters;

import android.support.v4.app.FragmentActivity;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.fdemo.Models.FileModel;
import com.fdemo.R;

import java.util.ArrayList;

/**
 * Created by Darshan on 9/25/2017.
 */

public class FileManagerAdapter extends RecyclerView.Adapter<FileManagerAdapter.MyViewHolder>{


    private final FragmentActivity mContext;
    private final ArrayList<FileModel> mFileList;

    public FileManagerAdapter(FragmentActivity activity, ArrayList<FileModel> fileList) {


        mContext=activity;
        mFileList =fileList;

    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView name, year, artist;

        public MyViewHolder(View view) {
            super(view);
            name = (TextView) view.findViewById(R.id.title);
            artist = (TextView) view.findViewById(R.id.genre);
            //  year = (TextView) view.findViewById(R.id.year);
        }
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.custom_view, parent, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        holder.name.setText(mFileList.get(position).getFileName());
       // holder.artist.setText(mFileList.get(position).getFilePath());
    }




    @Override
    public int getItemCount() {
        return mFileList.size();
    }

}
