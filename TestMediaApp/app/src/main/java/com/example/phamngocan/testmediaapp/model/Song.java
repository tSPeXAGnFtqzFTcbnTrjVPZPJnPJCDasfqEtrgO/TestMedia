package com.example.phamngocan.testmediaapp.model;

import android.database.Cursor;
import android.provider.MediaStore;

import com.example.phamngocan.testmediaapp.function.ConvertLanguage;
import com.example.phamngocan.testmediaapp.function.ShowLog;

import java.io.Serializable;

public class Song implements Serializable {

    private String nameVi, path, artistName, albumName, nameEn;
    private String nameSearch;
    private long id, idInPlaylist, artistId, albumId;
    private int duration;
    private int position;

    public Song(Cursor cursor, int pos) {
        position = pos;
        nameVi = cursor.getString(cursor.getColumnIndex(MediaStore.Audio.Media.TITLE));
        nameVi = nameVi.trim();
        nameEn = ConvertLanguage.convert(nameVi);
        nameSearch = nameEn.replaceAll(" ", "");
        duration = cursor.getInt(cursor.getColumnIndex(MediaStore.Audio.Media.DURATION));

        id = cursor.getLong(cursor.getColumnIndex(MediaStore.Audio.Media._ID));
        try {
            idInPlaylist = cursor.getLong(cursor.getColumnIndexOrThrow(MediaStore.Audio.Playlists.Members.AUDIO_ID));
        }catch (Exception e){
            ShowLog.logInfo("error in song class",e.getMessage() );
            idInPlaylist = -1;
        }

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

    public Song(Cursor cursor, int pos, long id) {
        position = pos;
        nameVi = cursor.getString(cursor.getColumnIndex(MediaStore.Audio.Media.TITLE));
        nameVi = nameVi.trim();
        nameEn = ConvertLanguage.convert(nameVi);
        nameSearch = nameEn.replaceAll(" ", "");
        duration = cursor.getInt(cursor.getColumnIndex(MediaStore.Audio.Media.DURATION));

        this.id = id;

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

    public void setNameVi(String nameVi) {
        this.nameVi = nameVi;
        nameEn = ConvertLanguage.convert(nameVi);
        nameSearch = nameEn.replaceAll(" ", "");
    }

    public void setArtistName(String artistName) {
        this.artistName = artistName;
    }

    public String getNameVi() {
        return nameVi;
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

    public long getId() {
        return id;
    }

    public long getArtistId() {
        return artistId;
    }

    public long getAlbumId() {
        return albumId;
    }

    public String getNameEn() {
        return nameEn;
    }

    public String getNameSearch() {
        return nameSearch;
    }

    public int getPosition() {
        return position;
    }

    public void setPosition(int position) {
        this.position = position;
    }

    public int getDuration() {
        return duration;
    }

    public long getIdInPlaylist() {
        return idInPlaylist;
    }
}
