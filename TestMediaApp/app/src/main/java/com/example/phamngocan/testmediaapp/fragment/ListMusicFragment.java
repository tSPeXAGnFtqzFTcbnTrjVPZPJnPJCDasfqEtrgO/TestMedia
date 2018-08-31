package com.example.phamngocan.testmediaapp.fragment;

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

import com.example.phamngocan.testmediaapp.Instance;
import com.example.phamngocan.testmediaapp.R;
import com.example.phamngocan.testmediaapp.adapter.ListMusicAdapter;
import com.example.phamngocan.testmediaapp.dialog.ShowPlaylistDialog;
import com.example.phamngocan.testmediaapp.function.ShowLog;
import com.example.phamngocan.testmediaapp.model.Song;

import org.apache.commons.lang3.ArrayUtils;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ListMusicFragment extends Fragment {

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
    ListMusicAdapter.OnClickListener  onClickListener;

    ArrayList<Song> songs = new ArrayList<>();

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

    private void init() {
        onLongClickListener = (view, posion) -> {
            ShowLog.logVar("position long in frag", posion);
            showBottomMenu(true);
        };
        onClickListener = (view,position,numSelect, checkList )->{
            if(numSelect == 1){
                btnEdit.setVisibility(View.VISIBLE);
            }else {
                btnEdit.setVisibility(View.GONE);
            }
            songs.clear();
            for(int i=0;i<Instance.songList.size();i++){
                if(checkList.get(i)){
                    //songs.add(Instance.songList.get(i).getId());
                    songs.add(Instance.songList.get(i));
                }
            }
            ShowLog.logInfo("click fragment", position);
        };
        musicAdapter = new ListMusicAdapter(Instance.songList, getContext(), onLongClickListener,onClickListener);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerSong.setLayoutManager(layoutManager);
        recyclerSong.setAdapter(musicAdapter);


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
        btnAdd.setOnClickListener(v -> {

//            idsArr = songs.toArray(new Long[0]);
//            ShowPlaylistDialog.newInstance(ArrayUtils.toPrimitive(idsArr))
//                    .show(getActivity().getSupportFragmentManager(),"ls" );
            ShowPlaylistDialog.newInstance(songs)
                    .show(getActivity().getSupportFragmentManager(),"xx" );

            musicAdapter.callAddToPlaylist();
        });
        btnApply.setOnClickListener(v -> {
            musicAdapter.callApply();
            showBottomMenu(false);
        });
        btnEdit.setOnClickListener(v -> {
            musicAdapter.callEdit();
        });
        btnRemove.setOnClickListener(v -> {
            musicAdapter.callRemove();
        });
    }


}
