package com.example.phamngocan.testmediaapp;

import android.graphics.Bitmap;

import com.example.phamngocan.testmediaapp.model.Album;
import com.example.phamngocan.testmediaapp.model.Playlist;
import com.example.phamngocan.testmediaapp.model.Song;

import java.util.ArrayList;
import java.util.HashMap;

public class Instance {
    public static ArrayList<Song> baseSong = new ArrayList<>();
    public static ArrayList<Song> songList = new ArrayList<>();
    public static ArrayList<Song> songShuffleList = new ArrayList<>();
    public static ArrayList<Playlist> playlists = new ArrayList<>();
    public static ArrayList<Album> albums = new ArrayList<>();
    public static HashMap<Long,Bitmap> mapImageAlbum = new HashMap<>();
}
