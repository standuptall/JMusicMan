/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package it.albe.jmusicman;
import java.util.ArrayList;

/**
 *
 * @author Alberto
 */
public class Album {
    public Album(String artist, String name){
        this.name = name;
        this.artist = artist;
        trackList = new ArrayList<Track>();
    }
    public void addTrack(Track track) {
        trackList.add(track);
    }
    public Track getTrackByName(String name){
        for (int i=0;i<trackList.size();i++){
            Track track = (Track)trackList.get(i);
            if (track.getName().equals(name))
                    return track;
        }
        return null;
    }
    public Track getTrackByNumber(int number){
        for (int i=0;i<trackList.size();i++){
            Track track = (Track)trackList.get(i);
            if (track.getNumber()==number)
                    return track;
        }
        return null;
    }
    public int getNumberOfTracks(){
        return trackList.size();
    }
    public Track getTrackAt(int i){
        return trackList.get(i);
    }
    @Override
    public String  toString(){
        return name;
    }
    private String name;
    private String artist;
    private ArrayList<Track> trackList;
}
