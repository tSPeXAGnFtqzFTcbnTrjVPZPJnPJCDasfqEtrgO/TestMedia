package com.example.phamngocan.testmediaapp.fragment;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.TextView;

import com.example.phamngocan.testmediaapp.Instance;
import com.example.phamngocan.testmediaapp.PlayerActivity;
import com.example.phamngocan.testmediaapp.R;
import com.example.phamngocan.testmediaapp.adapter.ListMusicAdapter;
import com.example.phamngocan.testmediaapp.constant.Action;
import com.example.phamngocan.testmediaapp.dialog.ShowDialogEditSong;
import com.example.phamngocan.testmediaapp.dialog.ShowPlaylistDialog;
import com.example.phamngocan.testmediaapp.function.ShowLog;
import com.example.phamngocan.testmediaapp.model.Song;
import com.example.phamngocan.testmediaapp.services.ForegroundService;
import com.example.phamngocan.testmediaapp.utils.SetListPlay;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ListMusicFragment extends Fragment {

    public static final String actionNotify = "actionNotify";

    @BindView(R.id.btn_playall)
    TextView btnPlayAll;
    @BindView(R.id.recycle_song)
    RecyclerView recyclerSong;
    @BindView(R.id.bottom_menu)
    FrameLayout frameLayout;
    @BindView(R.id.btn_apply)
    ImageButton btnApply;
    @BindView(R.id.btn_edit)
    ImageButton btnEdit;
    @BindView(R.id.btn_remove)
    ImageButton btnRemove;
    @BindView(R.id.btn_add)
    ImageButton btnAdd;

    ListMusicAdapter musicAdapter;

    ListMusicAdapter.OnLongClickListener onLongClickListener;
    ListMusicAdapter.OnClickListener onClickListener;

    ArrayList<Song> songs = new ArrayList<>();

    private int positionEdit;
    boolean isRegister = false;

    IntentFilter intentFilter = new IntentFilter();
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.layout_list_music_frag, container, false);
        ButterKnife.bind(this, view);

        init();
        action();
        setClick();

        return view;
    }

    @Override
    public void onStart() {
        if(!isRegister){
            isRegister = true;
            getContext().registerReceiver(broadcastReceiver,intentFilter );
        }
        super.onStart();
    }

    @Override
    public void onStop() {
        if(isRegister){
            isRegister=false;
            getContext().unregisterReceiver(broadcastReceiver);
        }
        super.onStop();
    }

    private void init() {
        intentFilter.addAction(actionNotify);

        onLongClickListener = (view, posion) -> {
            ShowLog.logVar("position long in frag", posion);
            showBottomMenu(true);
        };
        onClickListener = (view, position, numSelect, checkList) -> {
            if (numSelect == 1) {
                btnEdit.setVisibility(View.VISIBLE);
                positionEdit = position;
            } else {
                btnEdit.setVisibility(View.GONE);
            }
            songs.clear();
            for (int i = 0; i < Instance.songList.size(); i++) {
                if (checkList.get(i)) {
                    //songs.add(Instance.songList.get(i).getId());
                    songs.add(Instance.songList.get(i));
                }
            }
        };
        musicAdapter = new ListMusicAdapter(Instance.baseSong, getContext(), onLongClickListener, onClickListener);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerSong.setLayoutManager(layoutManager);
        recyclerSong.setAdapter(musicAdapter);

        if (musicAdapter.getItemCount() < 1) {
            btnPlayAll.setVisibility(View.GONE);
        }


    }

    private void action() {

    }

    public void showBottomMenu(boolean show) {
        if (show) {
            frameLayout.setVisibility(View.VISIBLE);
            btnEdit.setVisibility(View.GONE);
        } else {
            frameLayout.setVisibility(View.GONE);
        }
    }

    private void setClick() {
        btnPlayAll.setOnClickListener(v -> {
            SetListPlay.playAll();

            Intent intent = new Intent(getContext(), ForegroundService.class);
            intent.putExtra(ForegroundService.POS_KEY, 0);
            intent.setAction(Action.START_FORE.getName());
            getContext().startService(intent);

            startActivity(new Intent(getContext(), PlayerActivity.class));
        });
        btnAdd.setOnClickListener(v -> {

//            idsArr = songs.toArray(new Long[0]);
//            ShowPlaylistDialog.newInstance(ArrayUtils.toPrimitive(idsArr))
//                    .show(getActivity().getSupportFragmentManager(),"ls" );

            ShowLog.logInfo("add to pp", null);

            ShowPlaylistDialog.newInstance(songs)
                    .show(getActivity().getSupportFragmentManager(), "xx");

            musicAdapter.callAddToPlaylist();
        });
        btnApply.setOnClickListener(v -> {
            musicAdapter.callApply();
            showBottomMenu(false);
        });
        btnEdit.setOnClickListener(v -> {
            //musicAdapter.callEdit();
            Song song = Instance.baseSong.get(positionEdit);
            ShowDialogEditSong.newInstance(song,positionEdit)
                    .show(getFragmentManager(), "a");

            musicAdapter.callApply();
            showBottomMenu(false);
        });
        btnRemove.setOnClickListener(v -> {
            musicAdapter.callRemove();
        });
    }

    BroadcastReceiver broadcastReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            if(intent.getAction().equals(actionNotify)){
                musicAdapter.notifyDataSetChanged();
            }
        }
    };

}
