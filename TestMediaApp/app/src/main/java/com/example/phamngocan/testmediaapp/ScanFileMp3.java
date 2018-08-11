package com.example.phamngocan.testmediaapp;

import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.provider.MediaStore;
import android.util.Log;

import com.example.phamngocan.testmediaapp.COMMON_FUNCTION.ShowLog;
import com.example.phamngocan.testmediaapp.Model.Song;

import java.util.ArrayList;

public class ScanFileMp3 {
    public static String file_path = "file_path", file_name = "file_name";

    public static ArrayList<Song> queryFileInternal(Context context) {
        ArrayList<Song> songList = new ArrayList<>();
        Uri songUri = MediaStore.Audio.Media.INTERNAL_CONTENT_URI;
        String selection = MediaStore.Audio.Media.IS_MUSIC;
 //       String[] selectionArgs = {".mp3"};

        Cursor cursor = context.getContentResolver().query(songUri, null,
                null, null, null);
        if (cursor != null) {
            if (cursor.moveToFirst()) {
                do {
                    songList.add(new Song(cursor));
                    String type = cursor.getString(cursor.getColumnIndex(MediaStore.Audio.Media.MIME_TYPE));
                    String name = cursor.getString(cursor.getColumnIndex(MediaStore.Audio.Media.TITLE));
                    ShowLog.logInfo("title", name + "_" + type);

                } while (cursor.moveToNext());
                cursor.close();
            }
        } else {
            Log.d("AAA", "cursor nulll");
        }
        return songList;

    }

    public static ArrayList<Song> queryFileExternal(Context context) {
        ArrayList<Song> songList = new ArrayList<>();
        Uri songUri = MediaStore.Audio.Media.EXTERNAL_CONTENT_URI;
        //String selection = MediaStore.Audio.Media.IS_MUSIC ;

        Cursor cursor = context.getContentResolver().query(songUri, null,
                null, null, null);
        if (cursor != null) {
            if (cursor.moveToFirst()) {
                do {
                    songList.add(new Song(cursor));

                    String type = cursor.getString(cursor.getColumnIndex(MediaStore.Audio.Media.MIME_TYPE));
                    String name = cursor.getString(cursor.getColumnIndex(MediaStore.Audio.Media.TITLE));
                    ShowLog.logInfo("title", name + "_" + type);

                } while (cursor.moveToNext());
                cursor.close();
            }
        } else {
            Log.d("AAA", "cursor nulll");
        }
        return songList;

    }
}
