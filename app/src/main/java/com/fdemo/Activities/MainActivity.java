package com.fdemo.Activities;

import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.fdemo.Fragments.FoldersFragment;
import com.fdemo.Fragments.MusicLibFragment;
import com.fdemo.Fragments.SettingsFragment;
import com.fdemo.R;
import com.roughike.bottombar.BottomBar;
import com.roughike.bottombar.OnTabSelectListener;

public class MainActivity extends AppCompatActivity {

    private MusicLibFragment musicLibFragment;
    private FoldersFragment foldersFragment;
    private SettingsFragment settingsFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        BottomBar bottomBar=(BottomBar)findViewById(R.id.bottomBar);

        bottomBar.setOnTabSelectListener(new OnTabSelectListener() {
            @Override
            public void onTabSelected(int tabId) {
                if (tabId == R.id.tab_music_lib) {
                    FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
                    musicLibFragment = new MusicLibFragment();
                    ft.replace(R.id.container, musicLibFragment, "MusicLibFragment");
                    ft.commitAllowingStateLoss();
                }else if(tabId==R.id.tab_folders){
                    FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
                    foldersFragment= new FoldersFragment();
                    ft.replace(R.id.container, foldersFragment, "FoldersFragment");
                    ft.commitAllowingStateLoss();
                }else if(tabId==R.id.tab_settings){
                    FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
                    settingsFragment= new SettingsFragment();
                    ft.replace(R.id.container, settingsFragment, "SettingsFragment");
                    ft.commitAllowingStateLoss();
                }
            }
        });


        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        musicLibFragment = new MusicLibFragment();
        ft.replace(R.id.container, musicLibFragment, "MusicLibFragment");
        ft.commitAllowingStateLoss();
    }





}
