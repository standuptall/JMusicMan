/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package it.albe.JMusicMan;

import org.jaudiotagger.audio.AudioFile;

/**
 *
 * @author Alberto
 */
public class ExistingSongException extends Exception  {
    private String message;
    public AudioFile audioFile;
    public ExistingSongException(String msg,AudioFile af){
        super(msg);
        message = msg;
        audioFile=af;
    }
    @Override
    public String getMessage(){
        return super.getMessage();
    }
}
