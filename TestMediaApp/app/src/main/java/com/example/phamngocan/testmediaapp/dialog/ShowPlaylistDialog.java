package com.example.phamngocan.testmediaapp.dialog;

import android.app.Dialog;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Layout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.webkit.WebSettings;
import android.widget.LinearLayout;

import com.example.phamngocan.testmediaapp.Instance;
import com.example.phamngocan.testmediaapp.PlayerActivity;
import com.example.phamngocan.testmediaapp.R;
import com.example.phamngocan.testmediaapp.adapter.PlaylistAdapter;
import com.example.phamngocan.testmediaapp.function.ShowLog;
import com.example.phamngocan.testmediaapp.model.Playlist;
import com.example.phamngocan.testmediaapp.model.Song;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ShowPlaylistDialog extends DialogFragment {

    @BindView(R.id.recycle_playlist)
    RecyclerView recyclePlaylist;

    PlaylistAdapter playlistAdapter;
    ArrayList<Playlist> playlists = new ArrayList<>();

    PlaylistAdapter.ItemClick itemClick;

    private static final String keySong = "key_song";


    public static void newInstance(Song song) {

        long[] id = new long[1];
        id[0] = song.getId();
        newInstance(id);
    }

    public static ShowPlaylistDialog newInstance(long[] id) {
        ShowPlaylistDialog dialog = new ShowPlaylistDialog();

        Bundle bundle = new Bundle();
        bundle.putLongArray(keySong, id);
        dialog.setArguments(bundle);

        return dialog;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        ShowLog.logInfo("onCreateView dl", null);
        View view = inflater.inflate(R.layout.layout_dialog_playlist, container,false);
        ButterKnife.bind(this, view);

        init();

        return view;
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        Dialog dialog = super.onCreateDialog(savedInstanceState);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        return dialog;
    }

    @Override
    public void onStart() {
        super.onStart();

        Dialog dialog = getDialog();
        if(dialog!=null){
            dialog.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, 400);
        }
    }

    private void init(){
        int id = getArguments().getInt(keySong);
        setItemClick();


        ShowLog.logInfo("show pp dialog",Instance.playlists.size() );
        playlists.addAll(Instance.playlists);
        playlists.add(0,new Playlist(-1, 0, "New Playlist"));
        playlistAdapter = new PlaylistAdapter(playlists, getContext(), itemClick);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);

        recyclePlaylist.setLayoutManager(layoutManager);
        recyclePlaylist.setAdapter(playlistAdapter);

    }

    private void setItemClick() {
        itemClick = (view, position) -> {
            ShowLog.logInfo("adapter pp click",position );
            if (position == 0) {

            } else {

            }
        };
    }
}
