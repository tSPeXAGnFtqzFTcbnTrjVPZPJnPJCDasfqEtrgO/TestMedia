package com.example.phamngocan.testmediaapp.Loader;

import android.content.Context;
import android.database.Cursor;
import android.provider.BaseColumns;
import android.provider.MediaStore;

import com.example.phamngocan.testmediaapp.model.Playlist;
import com.example.phamngocan.testmediaapp.utils.AndtUtils;

import java.util.ArrayList;

public class PlaylistLoader {


    public static ArrayList<Playlist> load(Context context){
        ArrayList<Playlist> playlists = new ArrayList<>();
        Cursor cursor = makeCursor(context);

        if(cursor!=null && cursor.moveToFirst()){
            do{
                int id = cursor.getInt(0);
                String name = cursor.getString(1);
                int count = AndtUtils.countSongInPlaylist(context,id );
                playlists.add(new Playlist(id,count,name));
            }while (cursor.moveToNext());
        }

        return playlists;
    }

    private static Cursor makeCursor(Context context){
        return context.getContentResolver().query(
                MediaStore.Audio.Playlists.EXTERNAL_CONTENT_URI,
                new String[]{BaseColumns._ID,
                        MediaStore.Audio.PlaylistsColumns.NAME},
                null,null,null );
    }
}
