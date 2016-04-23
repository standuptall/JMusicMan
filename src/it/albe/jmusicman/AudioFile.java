/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package it.albe.JMusicMan;
import it.albe.FlacReader.FlacReader;
import java.lang.String;
import java.io.File;
import com.mpatric.mp3agic.*;
import static it.albe.JMusicMan.JMusicMan.frame;
import static it.albe.JMusicMan.JMusicMan.organize;
import it.albe.utils.IO;

/**
 *
 * @author Alberto
 */
public class AudioFile {
    private String artist,album,title, path;
    private FlacReader flacReader;
    private Mp3File mp3file;
    private byte[] albumImage;
    private int track;
    private enum _filetype {mp3,flac};
    public  _filetype filetype;
    public AudioFile(String path){
        artist = "";
        album = "";
        title = "";
        this.path = path;
        track = 0;
        try {
            if (path.toUpperCase().endsWith("MP3")){
                this.filetype = _filetype.mp3;
                File file = new File(path);
                mp3file = new Mp3File(path);
                if (mp3file.hasId3v2Tag()){
                    ID3v2 id3v2Tag = mp3file.getId3v2Tag();
                    artist = (id3v2Tag.getArtist()!=null) ? id3v2Tag.getArtist() : "Sconosciuto";
                    album = (id3v2Tag.getAlbum()!=null) ? id3v2Tag.getAlbum() : "";
                    title = (id3v2Tag.getTitle()!=null) ? id3v2Tag.getTitle() : file.getName();
                    track = (id3v2Tag.getTrack()!=null) ? Integer.parseInt(id3v2Tag.getTrack().replaceAll("[^0-9]", "")) : 0;
                    albumImage = id3v2Tag.getAlbumImage();
                    if (!"".equals(artist))
                            organize(file,artist,album,title,track);
                }
                else if (mp3file.hasId3v1Tag()){
                    ID3v1 id3v1Tag = mp3file.getId3v2Tag();
                    artist = (id3v1Tag.getArtist()!=null) ? id3v1Tag.getArtist() : "Sconosciuto";
                    album = (id3v1Tag.getAlbum()!=null) ? id3v1Tag.getAlbum() : "";
                    title = (id3v1Tag.getTitle()!=null) ? id3v1Tag.getTitle() : file.getName();
                    track = (id3v1Tag.getTrack()!=null) ? Integer.valueOf(id3v1Tag.getTrack()) : 0;
                    
                }
            }

            else if(path.toUpperCase().endsWith("FLAC")){
                this.filetype = _filetype.flac;
                File file = new File(path);
                flacReader = new FlacReader(path);
                artist = flacReader.getComment("artist");
                album = flacReader.getComment("album");
                title = flacReader.getComment("title");
                albumImage = flacReader.getAlbumImage();
                String trackString = flacReader.getComment("track");
                String n,nall;
                n = trackString.split("/")[0];
                if (!n.equals(""))
                        track = Integer.parseInt(n);
                
            }    
        }
        catch (java.io.IOException e){
            IO.err(frame, "Errore nella lettura dati su disco: "+e.getMessage()+artist);
        }
        catch (UnsupportedTagException e){
            IO.err(frame, "Tag non supportato: "+e.getMessage()+artist);
        }
        catch (InvalidDataException e){
            IO.err(frame, "Errore sui dati: "+e.getMessage()+artist);
        }
         
            
    }
    public void setArtist(String art){        
        if (!art.equals(""))
        {
            artist = art;
            if (this.filetype == _filetype.flac){
                flacReader.addComment("artist", art);
            }
            if (this.filetype == _filetype.mp3){
                ID3v2 id3;
                if (mp3file.hasId3v2Tag()) {
                    id3 = mp3file.getId3v2Tag();
                    } else {
                      id3 = new ID3v24Tag();
                      mp3file.setId3v2Tag(id3);
                      }
                id3.setArtist(art);
            }
        }
    }
    public void setAlbum(String alb){
        album = alb;
        if (this.filetype == _filetype.flac){
            flacReader.addComment("album", alb);
        }
        if (this.filetype == _filetype.mp3){
            ID3v2 id3;
            if (mp3file.hasId3v2Tag()) {
                id3 = mp3file.getId3v2Tag();
                } else {
                  id3 = new ID3v24Tag();
                  mp3file.setId3v2Tag(id3);
                  }
            id3.setAlbum(alb);
        }
    }
    public void setTitle(String tit){
        title = tit;
        if (this.filetype == _filetype.flac){
            flacReader.addComment("title", tit);
        }
        if (this.filetype == _filetype.mp3){
            ID3v2 id3;
            if (mp3file.hasId3v2Tag()) {
                id3 = mp3file.getId3v2Tag();
                } else {
                  id3 = new ID3v24Tag();
                  mp3file.setId3v2Tag(id3);
                  }
            id3.setTitle(tit);
        }
    }
    public void setTrack(int tra) {
        track = tra;
        if (this.filetype == _filetype.flac){
            flacReader.addComment("track", Integer.toString(tra));
        }
        if (this.filetype == _filetype.mp3){
            ID3v2 id3;
            if (mp3file.hasId3v2Tag()) {
                id3 = mp3file.getId3v2Tag();
                } else {
                  id3 = new ID3v24Tag();
                  mp3file.setId3v2Tag(id3);
                  }
            id3.setTrack(Integer.toString(tra));
        }
    }
    public String getArtist(){
         return artist;
    }
    public String getAlbum(){
        return album;
    }
    public String getTitle(){
        return title;
    }
    public int getTrack(){
        return track;
    }
    public byte[] getAlbumImage(){
        return albumImage;
    }
    public void close(){
        if (this.filetype == _filetype.flac){
            flacReader.writeAll();
        }
        if (this.filetype == _filetype.mp3)
            ;//mp3file.save(newTrack.getPath());
    }
}
