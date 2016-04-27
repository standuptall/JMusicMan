
package it.albe.JMusicMan;


public class Track {
    private String name, artist,album,path,track;
    private int Duration;
    private byte[] img;
    public Track () {

    }
    public Track(String artista, String nome, String album,String path,String n) {
        this.name = nome;
        this.artist = artista;
        this.album = album;
        this.path = path;
        this.track = n;
    }
    public Track(String artista, String nome, String album,String path,byte[] img) {
        this.name = nome;
        this.artist = artista;
        this.album = album;
        this.path = path;
        this.img = img;
    }
    public Track(String artista, String nome, String album,String track) {
        this.name = nome;
        this.artist = artista;
        this.album = album;
        this.track = track;
    }
    public void setTrack(String n) {
        this.track = n;
    }

    public int getDuration() {
        return Duration;
    }

    public String getAlbum() {
        return album;
    }
    public byte[] getImg(){
        return img;
    }
    public void setImg(byte[] b){
        img = b;
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

    public String getNumber() {
        return track;
    }
    public String toString(){
        return track+" - " + name;
    }
    public String getPath(){
        return path;
    }
}
