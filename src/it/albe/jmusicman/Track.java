
package it.albe.JMusicMan;


public class Track {
    private String name, artist,album,path,track,comment;
    private int Duration;
    private byte[] img;
    public Track () {
        
        this.comment="";    
    }
    public Track(String artista, String nome, String album,String path,String n,String cmt) {
        this.name = nome;
        this.artist = artista;
        this.album = album;
        this.path = path;
        this.track = n;
        this.comment=cmt;
    }
    public Track(String artista, String nome, String album,String path,byte[] img) {
        this.name = nome;
        this.artist = artista;
        this.album = album;
        this.path = path;
        this.img = img;
        this.comment="";
    }
    public Track(String artista, String nome, String album,String track) {
        this.name = nome;
        this.artist = artista;
        this.album = album;
        this.track = track;
        this.comment="";
    }
    public void setTrack(String n) {
        this.track = n;
    }
    public void setComment(String n) {
        this.comment = n;
    }
    public String getComment() {
        return this.comment;
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
        this.comment="";
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
        if (track!="")
            return String.format("%2d",Integer.valueOf(track))+" - " + name;
        else return name;
    }
    public String getPath(){
        return path;
    }
}
