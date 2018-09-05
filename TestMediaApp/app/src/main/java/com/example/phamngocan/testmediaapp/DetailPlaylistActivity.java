package com.example.phamngocan.testmediaapp;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.TextView;

import com.example.phamngocan.testmediaapp.adapter.ListMusicPlaylistAdapter;
import com.example.phamngocan.testmediaapp.constant.Action;
import com.example.phamngocan.testmediaapp.function.ShowLog;
import com.example.phamngocan.testmediaapp.model.Song;
import com.example.phamngocan.testmediaapp.services.ForegroundService;
import com.example.phamngocan.testmediaapp.utils.SetListPlay;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

public class DetailPlaylistActivity extends AppCompatActivity {

    public static final String keyPositionPlaylist = "keyPositionPlaylist";

    @BindView(R.id.btn_playall)
    TextView btnPlayAll;
    @BindView(R.id.recycle_song)
    RecyclerView recyclerSong;
    @BindView(R.id.bottom_menu)
    FrameLayout frameLayout;
    @BindView(R.id.btn_apply)
    ImageButton btnApply;
    @BindView(R.id.btn_remove)
    ImageButton btnRemove;
    @BindView(R.id.btn_edit)
    ImageButton btnEdit;
    @BindView(R.id.btn_add)
    ImageButton btnAdd;


    ListMusicPlaylistAdapter musicAdapter;

    ListMusicPlaylistAdapter.OnLongClickListener onLongClickListener;
    ListMusicPlaylistAdapter.OnClickListener onClickListener;

    ArrayList<Song> songs = new ArrayList<>();

    int position;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_playlist);

        ButterKnife.bind(this);

        Intent intent = getIntent();
        if (intent != null) {
            position = intent.getIntExtra(keyPositionPlaylist, 0);
        }

        init();
        action();
        setClick();
    }

    private void init() {

        btnAdd.setVisibility(View.GONE);
        btnEdit.setVisibility(View.GONE);

        onLongClickListener = (view, posion) -> {
            ShowLog.logVar("posAlbum long in frag", posion);
            showBottomMenu(true);
        };
        onClickListener = (view, pos, numSelect, checkList) -> {

            songs.clear();

            for (int i = 0; i < Instance.playlists.get(position).getSongs().size(); i++) {
                if (checkList.get(i)) {
                    //songs.add(Instance.songList.get(i).getId());
                    songs.add(Instance.playlists.get(position).getSongs().get(i));
                }
            }
        };

        ShowLog.logInfo("posAlbum pp", Instance.playlists.size() + "_" + position);
        musicAdapter = new ListMusicPlaylistAdapter(Instance.playlists.get(position).getSongs(),
                position, DetailPlaylistActivity.this, onLongClickListener, onClickListener);

        LinearLayoutManager layoutManager = new LinearLayoutManager(DetailPlaylistActivity.this);
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerSong.setLayoutManager(layoutManager);
        recyclerSong.setAdapter(musicAdapter);


        if (musicAdapter.getItemCount() < 0) {
            btnPlayAll.setVisibility(View.GONE);
        }


    }

    private void action() {

    }

    public void showBottomMenu(boolean show) {
        if (show) {
            frameLayout.setVisibility(View.VISIBLE);
        } else {
            frameLayout.setVisibility(View.GONE);
        }
    }

    private void setClick() {
        btnPlayAll.setOnClickListener(v -> {
            SetListPlay.playPlaylist(position);

            Intent intent = new Intent(DetailPlaylistActivity.this, ForegroundService.class);
            intent.putExtra(ForegroundService.POS_KEY, 0);
            intent.setAction(Action.START_FORE.getName());
            startService(intent);

            startActivity(new Intent(DetailPlaylistActivity.this, PlayerActivity.class));
        });

        btnApply.setOnClickListener(v -> {
            musicAdapter.callApply();
            showBottomMenu(false);
        });
        btnRemove.setOnClickListener(v -> {
            musicAdapter.callRemove();
        });
    }


}
