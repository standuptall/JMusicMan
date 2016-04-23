/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package it.albe.FlacReader;

/**
 *
 * @author Alberto
 */
public class NotAFlacException extends Exception {
    private String message;
    public NotAFlacException(String msg){
        super(msg);
        message = msg;
    }
    @Override
    public String getMessage(){
        return super.getMessage();
    }
}