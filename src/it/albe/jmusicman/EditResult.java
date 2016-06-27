/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package it.albe.jmusicman;

/**
 *
 * @author Alberto
 */
public class EditResult {
    
    
    public EditResult(boolean aM,boolean tm, boolean am, boolean im){
        artistModified = aM;
        titleModified = tm;
        albumModified = am;
        imageModified = im;
    }
    public boolean artistModified(){
        return artistModified;
    }
    public boolean titleModified(){
        return titleModified;
    }
    public boolean albumModified(){
        return albumModified;
    }
    public boolean imageModified(){
        return imageModified;
    }
    private boolean artistModified,titleModified,albumModified,imageModified;
}
