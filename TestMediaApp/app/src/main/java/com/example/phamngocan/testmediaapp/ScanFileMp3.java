package com.example.phamngocan.testmediaapp;

import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.provider.MediaStore;
import android.util.Log;

import com.example.phamngocan.testmediaapp.function.ConvertLanguage;
import com.example.phamngocan.testmediaapp.function.ShowLog;
import com.example.phamngocan.testmediaapp.model.Song;

import java.util.ArrayList;

public class ScanFileMp3 {
    public static String file_path = "file_path", file_name = "file_name";
    private static final String STR_TYPE = MediaStore.Audio.Media.ARTIST;

    public static ArrayList<Song> queryFileInternal(Context context) {
        ArrayList<Song> songList = new ArrayList<>();
        Uri songUri = MediaStore.Audio.Media.INTERNAL_CONTENT_URI;
        String selection = MediaStore.Audio.Media.DATA + " = ? ";
        String[] selectionArgs = {""};

        Cursor cursor = context.getContentResolver().query(songUri, null,
                selection, selectionArgs, null);
        if (cursor != null) {
            if (cursor.moveToFirst()) {
                do {
                    songList.add(new Song(cursor, songList.size()));
                    String type = cursor.getString(cursor.getColumnIndex(STR_TYPE));
                    String name = cursor.getString(cursor.getColumnIndex(MediaStore.Audio.Media.TITLE));
                    ShowLog.logInfo("inter title", ConvertLanguage.convert(name) + "_" + type);
                    //  ShowLog.logInfo("",
                    //        cursor.getString(cursor.getColumnIndex(MediaStore.Audio.Media.DATA)) );

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
        String selection = MediaStore.Audio.Media.TITLE + " LIKE ? ";
        String[] selectionArgs = {"%"};

        Cursor cursor = context.getContentResolver().query(songUri, null,
                selection, selectionArgs, null);
        if (cursor != null) {
            if (cursor.moveToFirst()) {
                do {
                    songList.add(new Song(cursor, songList.size()));

                    String id = cursor.getString(cursor.getColumnIndex(MediaStore.Audio.Media._ID));
                    String name = cursor.getString(cursor.getColumnIndex(MediaStore.Audio.Media.TITLE));
                    //ShowLog.logInfo("inter title", songList.get(songList.size() - 1).getNameSearch());


                   // ShowLog.logInfo("type", id);

                } while (cursor.moveToNext());
                cursor.close();
            }
        } else {
            Log.d("AAA", "cursor nulll");
        }

        return songList;

    }
}
