/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package it.albe.JMusicMan;
import java.lang.String;
import java.io.File;
import com.mpatric.mp3agic.*;
import static it.albe.JMusicMan.JMusicMan.frame;
import static it.albe.JMusicMan.JMusicMan.organize;
import it.albe.utils.IO;
import net.sf.jni4net.Bridge;

/**
 *
 * @author Alberto
 */
public class AudioFile {
    private String artist,album,title;
    private int track;
    private enum _filetype {mp3,flac};
    public  _filetype filetype;
    public AudioFile(String path){
        artist = "";
        album = "";
        title = "";
        track = 0;
        try {
            if (path.toUpperCase().endsWith("MP3")){
                this.filetype = _filetype.mp3;
                File file = new File(path);
                Mp3File mp3file = new Mp3File(path);
                if (mp3file.hasId3v2Tag()){
                    ID3v2 id3v2Tag = mp3file.getId3v2Tag();
                    artist = (id3v2Tag.getArtist()!=null) ? id3v2Tag.getArtist() : "Sconosciuto";
                    album = (id3v2Tag.getAlbum()!=null) ? id3v2Tag.getAlbum() : "";
                    title = (id3v2Tag.getTitle()!=null) ? id3v2Tag.getTitle() : file.getName();
                    track = (id3v2Tag.getTrack()!=null) ? Integer.parseInt(id3v2Tag.getTrack().replaceAll("[^0-9]", "")) : 0;
                    if (!"".equals(artist))
                            organize(file,artist,album,title,track);
                }
                else if (mp3file.hasId3v1Tag()){
                    ID3v1 id3v1Tag = mp3file.getId3v2Tag();
                    artist = (id3v1Tag.getArtist()!=null) ? id3v1Tag.getArtist() : "Sconosciuto";
                    album = (id3v1Tag.getAlbum()!=null) ? id3v1Tag.getAlbum() : "";
                    title = (id3v1Tag.getTitle()!=null) ? id3v1Tag.getTitle() : file.getName();
                    track = (id3v1Tag.getTrack()!=null) ? Integer.valueOf(id3v1Tag.getTrack()) : 0;
                    if (!"".equals(artist))
                            organize(file,artist,album,title,track);
                }
            }

            else if(path.toUpperCase().endsWith("FLAC")){
                this.filetype = _filetype.flac;
                Bridge.setVerbose(true);
		Bridge.init();
                FlacReader flacReader = new FlacReader(path);
                artist = flacReader.getComment("artist");
                album = flacReader.getComment("album");
                title = flacReader.getComment("title");
                String trackString = flacReader.getComment("track");
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
}
