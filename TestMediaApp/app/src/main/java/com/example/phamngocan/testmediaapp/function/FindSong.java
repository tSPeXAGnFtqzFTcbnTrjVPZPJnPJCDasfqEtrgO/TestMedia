package com.example.phamngocan.testmediaapp.function;

import com.example.phamngocan.testmediaapp.model.Song;

import java.util.ArrayList;

public class FindSong {
    public static int findPositionById(long id, ArrayList<Song> songs){
        int pos = -1;
        for(int i=0;i<songs.size();i++){
            if(songs.get(i).getId() == id){
                pos = i;
                break;
            }
        }
        return pos;
    }
}
