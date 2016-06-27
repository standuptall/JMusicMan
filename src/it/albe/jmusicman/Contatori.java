/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package it.albe.jmusicman;

/**
 *
 * @author Alberto
 */
public class Contatori {
    public static int artisti = 0;
    public static int album = 0;
    public static int brani = 0 ;
    public static long durataTotale = 0;
    public static void azzera(){
        artisti = 0;
        album = 0;
        brani = 0;
        durataTotale = 0;
    }
}
