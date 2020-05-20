package com.example.parcial2.Repository;

import com.example.parcial2.Track;

import java.util.ArrayList;

public class TrackRepository {

    private static ArrayList<Track> tracks=new ArrayList<Track>();;

    public TrackRepository(){
        //tracks=new ArrayList<Track>();
    }
    public static ArrayList<Track> getOwnTrack(){
        return tracks;
    }
    public static void add(Track track){
        tracks.add(track);
    }


}
