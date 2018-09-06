package com.example.phamngocan.testmediaapp.fragment;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.phamngocan.testmediaapp.DetailPlaylistActivity;
import com.example.phamngocan.testmediaapp.Instance;
import com.example.phamngocan.testmediaapp.R;
import com.example.phamngocan.testmediaapp.adapter.PlaylistFragmentAdapter;
import com.example.phamngocan.testmediaapp.constant.ActionBroadCast;
import com.example.phamngocan.testmediaapp.dialog.ShowRenamePlaylistDialog;
import com.example.phamngocan.testmediaapp.function.ShowLog;
import com.example.phamngocan.testmediaapp.utils.AndtUtils;

import butterknife.BindView;
import butterknife.ButterKnife;

public class PlayListFragment extends Fragment {

    @BindView(R.id.recycle_playlist)
    RecyclerView recyclerPlaylist;


    PlaylistFragmentAdapter playlistFragmentAdapter;
    PlaylistFragmentAdapter.OnClickItem onClickItem;
    PlaylistFragmentAdapter.OnClickMenu onClickMenu;

    boolean isRegister = false;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.layout_playlist_fragment,container,false );
        ButterKnife.bind(this,view );

        setClickInterface();
        init();

        return view;
    }

    @Override
    public void onStart() {
        if(playlistFragmentAdapter!=null){
            playlistFragmentAdapter.notifyDataSetChanged();
        }
        register();
        super.onStart();

    }

    @Override
    public void onStop() {
        unregister();
        super.onStop();
    }

    private void init(){
        playlistFragmentAdapter = new PlaylistFragmentAdapter(Instance.playlists,getContext(),onClickItem,onClickMenu);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
        recyclerPlaylist.setLayoutManager(layoutManager);
        recyclerPlaylist.setAdapter(playlistFragmentAdapter);

    }
    private void setClickInterface() {
        onClickItem = (view, position) -> {
            Intent intent = new Intent(getContext(), DetailPlaylistActivity.class);
            intent.putExtra(DetailPlaylistActivity.keyPositionPlaylist, position);
            startActivity(intent);
        };

        onClickMenu = (menuId, playlistId,position) -> {
            switch (menuId){
                case R.id.menu_play:{
                    ShowLog.logInfo("menu click","play" );
                    break;
                }
                case R.id.menu_remove:{
                    ShowLog.logInfo("menu click","remove" );

                    showConfirmDialog(Instance.playlists.get(position).getmName(),
                            Instance.playlists.get(position).getmId(),
                            position);

                    break;
                }
                case R.id.menu_rename:{

                    ShowRenamePlaylistDialog.newInstance(playlistId)
                            .setOnComplete(newName -> {
                                Instance.playlists.get(position).setmName(newName);
                                playlistFragmentAdapter.notifyDataSetChanged();
                            })
                            .show(getActivity().getSupportFragmentManager(),"s" );
                    ShowLog.logInfo("menu click","rename" );
                    break;
                }
            }
        };
    }

    private void register(){

        if(!isRegister){
            IntentFilter intentFilter = new IntentFilter();
            intentFilter.addAction(ActionBroadCast.UPDATE_PLAYLIST.getName());
            getContext().registerReceiver(broadcastReceiver,intentFilter);
            isRegister = true;
        }

    }
    private void unregister(){
        if(isRegister){
            getContext().unregisterReceiver(broadcastReceiver);
            isRegister = false;
        }
    }
    private void showConfirmDialog(String name,long playlistId,int position){

        AlertDialog.Builder alBuilder = new AlertDialog.Builder(getContext());

        alBuilder.setMessage("Remove " + name);
        alBuilder.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                boolean isSuccess = AndtUtils.deletePlaylist(getContext(), playlistId);
                if(isSuccess){
                    Instance.playlists.remove(position);
                    playlistFragmentAdapter.notifyDataSetChanged();
                }
            }
        });
        alBuilder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
            }
        });

        alBuilder.show();

    }

    BroadcastReceiver broadcastReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {

            ShowLog.logInfo("receiver","pp fm" );
            if(intent!=null && intent.getAction()!=null){


                String action = intent.getAction();
                if(action.equals(ActionBroadCast.UPDATE_PLAYLIST.getName())){
                    ShowLog.logInfo("receiver","update size" + playlistFragmentAdapter.getItemCount() );
                    playlistFragmentAdapter.notifyDataSetChanged();
                }

            }
        }
    };


}
