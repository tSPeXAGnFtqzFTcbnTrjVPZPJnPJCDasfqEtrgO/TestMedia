package com.example.phamngocan.testmediaapp.utils;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.provider.BaseColumns;
import android.provider.MediaStore;

import com.example.phamngocan.testmediaapp.Instance;
import com.example.phamngocan.testmediaapp.function.ShowLog;

public class AndtUtils {
    public static int countSongInPlaylist(Context context, long playlistId) {
        Uri uri = MediaStore.Audio.Playlists.Members.getContentUri("external", playlistId);

        Cursor cursor = context.getContentResolver().query(uri,
                new String[]{BaseColumns._ID},
                MediaStore.Audio.Media.IS_MUSIC + "=1",
                null,
                null);

        int count = 0;
        if (cursor != null && cursor.moveToFirst()) {
            count = cursor.getCount();
            cursor.close();
        }
        return count;
    }

    public static long getDurationPlaylist(Context context, long playlistId) {
        Uri uri = MediaStore.Audio.Playlists.Members.getContentUri("external", playlistId);

        String projection = "SUM(" + MediaStore.Audio.Media.DURATION + ")";
        Cursor cursor = context.getContentResolver().query(uri,
                new String[]{projection},
                MediaStore.Audio.Media.IS_MUSIC + "=1",
                null,
                null);

        if (cursor.moveToFirst()) {
            long count = cursor.getLong(0);
            cursor.close();

            return count;
        }
        //return cursor.getLong(0);
        return 0;
    }

    public static boolean renamePlaylist(Context context, long playlistId, String newName) {
        Uri uri = MediaStore.Audio.Playlists.EXTERNAL_CONTENT_URI;

        String[] projection = {BaseColumns._ID};
        String where = MediaStore.Audio.PlaylistsColumns.NAME + " =? ";
        String[] argsClause = {newName};

        Cursor cursor = context.getContentResolver().query(uri,
                projection,
                where,
                argsClause,
                null);

        if (cursor == null || cursor.getCount() != 0) {
            return false;
        }

        cursor.close();

        ContentValues contentValues = new ContentValues(1);
        contentValues.put(MediaStore.Audio.Playlists.NAME, newName);


        context.getContentResolver().update(uri, contentValues,
                MediaStore.Audio.Playlists._ID + " =? ",
                new String[]{Long.toString(playlistId)});

        return true;
    }


    public static boolean deletePlaylist(Context context, long playlistId) {
        Uri uri = MediaStore.Audio.Playlists.EXTERNAL_CONTENT_URI;

        String where = MediaStore.Audio.Playlists._ID + " =? ";
        String[] argsClause = {Long.toString(playlistId)};
        Cursor cursor = context.getContentResolver().query(uri,
                null,
                where,
                argsClause,
                null);

        if (cursor == null || cursor.getCount() == 0) {
            return false;
        }
        cursor.close();

        context.getContentResolver().delete(uri,
                where,
                argsClause);

        return true;
    }

    public static void deleteSongPlaylist(Context context, long idPlaylist, long[] idSong, int positionPlaylist) {
        if (Instance.playlists.get(positionPlaylist).getSongs().size() == idSong.length) {
            deletePlaylist(context, idPlaylist);
            return;
        }

        Uri uri = MediaStore.Audio.Playlists.Members.getContentUri("external", idPlaylist);

        String where = MediaStore.Audio.Playlists.Members.AUDIO_ID + " =? ";

        String[] argsClause = new String[idSong.length];
        for (int i = 0; i < idSong.length; i++) {
            argsClause[i] = String.valueOf(idSong[i]);
        }

        Cursor cursor = context.getContentResolver().query(uri,
                null,
                where,
                argsClause,
                null
        );

        if (cursor != null) {
            cursor.close();
        }

        int cnt = context.getContentResolver().delete(uri,
                where,
                argsClause);

        ShowLog.logInfo("cnt del", cnt);
    }

}
