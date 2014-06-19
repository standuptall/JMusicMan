
package it.albe.jmusicman;


public class Track {
    private String name, artist,album,path;
    private int Duration,number;
    public Track () {

    }
    public Track(String artista, String nome, String album,String path) {
        this.name = nome;
        this.artist = artista;
        this.album = album;
        this.path = path;
    
    }
    public Track(String artista, String nome, String album,int count) {
        this.name = nome;
        this.artist = artista;
        this.album = album;
        this.number = count;
    }
    public void setDuration(int Duration) {
        this.Duration = Duration;
    }

    public int getDuration() {
        return Duration;
    }

    public String getAlbum() {
        return album;
    }

    public void setAlbum(String Album) {
        this.album = Album;
    }
    public void Track (){
        this.name="";
        this.artist="";
        this.album="";
        this.Duration=0;
    }
    public void setName(String n) {
        this.name=n;
    }
    public void setArtist(String n) {
        this.artist=n;
    }
    public String getName(){
        return name;
    }
    public String getArtist(){
        return artist;
    }

    public int getNumber() {
        return number;
    }
    public String toString(){
        if (number>0)
            return String.format("%02d",number)+" - " + name;
        else
            return name;
    }
    public String getPath(){
        return path;
    }
}
