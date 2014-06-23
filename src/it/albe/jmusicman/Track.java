
package it.albe.jmusicman;


public class Track {
    private String name, artist,album,path;
    private int Duration,number;
    private byte[] img;
    public Track () {

    }
    public Track(String artista, String nome, String album,String path,int n) {
        this.name = nome;
        this.artist = artista;
        this.album = album;
        this.path = path;
        this.number = n;
    }
    public Track(String artista, String nome, String album,String path,byte[] img) {
        this.name = nome;
        this.artist = artista;
        this.album = album;
        this.path = path;
        this.img = img;
    }
    public Track(String artista, String nome, String album,int count) {
        this.name = nome;
        this.artist = artista;
        this.album = album;
        this.number = count;
    }
    public void setNumber(int n) {
        this.number = n;
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
