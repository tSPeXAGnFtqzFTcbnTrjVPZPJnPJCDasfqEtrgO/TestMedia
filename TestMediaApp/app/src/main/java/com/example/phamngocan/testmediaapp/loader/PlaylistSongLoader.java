package com.example.phamngocan.testmediaapp.loader;

import android.content.Context;
import android.database.Cursor;
import android.provider.MediaStore;

import com.example.phamngocan.testmediaapp.model.Song;

import java.util.ArrayList;
import java.util.HashMap;

public class PlaylistSongLoader {
    public static ArrayList<Song> getSongFromPlaylist(Context context, long playlistId) {
        ArrayList<Song>songs = new ArrayList<>();
        Cursor cursor = makeCursor(context, playlistId);
        if (cursor != null && cursor.moveToFirst()) {
            do {
                int id;
                String songName,artist;

                id = cursor.getInt(cursor.getColumnIndexOrThrow(MediaStore.Audio.Playlists.Members.AUDIO_ID));

                songs.add(new Song(cursor,songs.size()));

            } while (cursor.moveToNext());
        }
        return songs;

    }

    private static Cursor makeCursor(Context context, long playlistId) {
        return context.getContentResolver().query(
                MediaStore.Audio.Playlists.Members.getContentUri("external", playlistId),
                null,
                null,
                null,
                null);
    }
}
