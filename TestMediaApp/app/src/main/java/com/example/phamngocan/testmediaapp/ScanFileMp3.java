package com.example.phamngocan.testmediaapp;

import android.app.Activity;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.provider.MediaStore;
import android.util.Log;

import com.example.phamngocan.testmediaapp.COMMON_FUNCTION.ShowLog;
import com.example.phamngocan.testmediaapp.Model.Song;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;

public class ScanFileMp3 {
    public static String file_path = "file_path", file_name = "file_name";

    public static ArrayList<HashMap<String, String>> getPlayList(String directory) {
        ArrayList<HashMap<String, String>> listFile = new ArrayList<>();


        try {
            File rootFile = new File(directory);
            File[] files = rootFile.listFiles();

            for (File f : files) {
                if (f.isDirectory()) {
                    ArrayList<HashMap<String, String>> t = getPlayList(f.getAbsolutePath());
                    if (t != null) {
                        listFile.addAll(t);
                    } else {
//                        break;
                    }
                } else {
                    if (f.getName().endsWith(".mp3")) {
                        HashMap<String, String> t = new HashMap<>();
                        t.put(file_path, f.getAbsolutePath());
                        t.put(file_name, f.getName());
                        listFile.add(t);
                    }
                }
            }
            return listFile;
        } catch (Exception e) {
            Log.e("AAA", e.getMessage());
            return null;
        }
    }

    public static ArrayList<Song> queryFile(Context context) {
        ArrayList<Song> songList = new ArrayList<>();
        Uri songUri = MediaStore.Audio.Media.EXTERNAL_CONTENT_URI;
        //String selection = MediaStore.Audio.Media.IS_MUSIC ;

        Cursor cursor = context.getContentResolver().query(songUri, null,
                null, null, null);
        if (cursor != null) {
            if (cursor.moveToFirst()) {
                do {
                    songList.add(new Song(cursor));

                } while (cursor.moveToNext());
                cursor.close();
            }
        } else {
            Log.d("AAA", "cursor nulll");
        }
        return songList;

    }
}
