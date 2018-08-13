package com.example.phamngocan.testmediaapp.model;

import android.database.Cursor;
import android.provider.MediaStore;

public class Song {

    private String name,path,artistName,albumName;
    private int id,artistId,albumId;

    public Song(Cursor cursor) {
        name = cursor.getString(cursor.getColumnIndex(MediaStore.Audio.Media.TITLE));

        id = cursor.getInt(cursor.getColumnIndex(MediaStore.Audio.Media._ID));

        path = cursor.getString(cursor.getColumnIndex(MediaStore.Audio.Media.DATA));
        artistName = cursor.getString(cursor
                .getColumnIndex(MediaStore.Audio.Media.ARTIST));
        artistId = cursor.getInt(cursor
                .getColumnIndex(MediaStore.Audio.Media.ARTIST_ID));
        albumName = cursor.getString(cursor
                .getColumnIndex(MediaStore.Audio.Media.ALBUM));
        albumId = cursor.getInt(cursor
                .getColumnIndex(MediaStore.Audio.Media.ALBUM_ID));

    }

    public String getName() {
        return name;
    }

    public String getPath() {
        return path;
    }

    public String getArtistName() {
        return artistName;
    }

    public String getAlbumName() {
        return albumName;
    }

    public int getId() {
        return id;
    }

    public int getArtistId() {
        return artistId;
    }

    public int getAlbumId() {
        return albumId;
    }
}
