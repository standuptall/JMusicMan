/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package it.albe.jmusicman;

import it.albe.utils.IO;
import java.util.List;
import java.io.File;
import org.jaudiotagger.audio.AudioFileIO;
import org.jaudiotagger.tag.FieldKey;
import org.jaudiotagger.tag.Tag;
import java.io.IOException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.*;
import javax.swing.event.*;
import org.jaudiotagger.audio.exceptions.CannotReadException;
import org.jaudiotagger.tag.TagException;
import org.jaudiotagger.audio.AudioFile;
import org.jaudiotagger.audio.exceptions.CannotWriteException;
import org.jaudiotagger.tag.FieldDataInvalidException;
import org.jaudiotagger.tag.KeyNotFoundException;
/**
 *
 * @author Alberto
 */
public class SkippedTracksFrame extends javax.swing.JDialog {
    List<File> fileTracks;
    List<Track> tracks;
    int contatore = 0;
    /**
     * Creates new form SkippedTracksFrame
     */
    public SkippedTracksFrame(java.awt.Frame parent,List<File> tracce) {
        super(parent,true);
        this.fileTracks = tracce;
        initComponents();
        tracks = new ArrayList<>();
        caricaListaTracce();
        contatore = tracks.size();
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jSplitPane1 = new javax.swing.JSplitPane();
        jScrollPane1 = new javax.swing.JScrollPane();
        jList1 = new javax.swing.JList<>();
        jPanel2 = new javax.swing.JPanel();
        artistField1 = new javax.swing.JTextField();
        jLabel6 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        titleField1 = new javax.swing.JTextField();
        jLabel8 = new javax.swing.JLabel();
        albumField1 = new javax.swing.JTextField();
        jLabel9 = new javax.swing.JLabel();
        trackNumberField1 = new javax.swing.JTextField();
        jScrollPane7 = new javax.swing.JScrollPane();
        jScrollPane8 = new javax.swing.JScrollPane();
        commentField1 = new javax.swing.JTextArea();
        jLabel10 = new javax.swing.JLabel();
        OKBUtton1 = new javax.swing.JButton();
        annullaButton1 = new javax.swing.JButton();
        jButton1 = new javax.swing.JButton();
        jButton2 = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.DO_NOTHING_ON_CLOSE);
        setTitle("Modifica informazioni tracce con tag non validi");
        setLocation(new java.awt.Point(500, 200));

        jSplitPane1.setDividerLocation(200);

        jScrollPane1.setViewportView(jList1);

        jSplitPane1.setLeftComponent(jScrollPane1);

        artistField1.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusLost(java.awt.event.FocusEvent evt) {
                artistField1FocusLost(evt);
            }
        });

        jLabel6.setText("Artista");

        jLabel7.setText("Titolo");

        titleField1.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                titleField1KeyReleased(evt);
            }
        });

        jLabel8.setText("Album");

        albumField1.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                albumField1FocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                albumField1FocusLost(evt);
            }
        });

        jLabel9.setText("Numero traccia");

        trackNumberField1.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                trackNumberField1KeyReleased(evt);
            }
            public void keyTyped(java.awt.event.KeyEvent evt) {
                trackNumberField1KeyTyped(evt);
            }
        });

        commentField1.setColumns(20);
        commentField1.setRows(5);
        jScrollPane8.setViewportView(commentField1);

        jLabel10.setText("Commento");

        OKBUtton1.setText("OK");
        OKBUtton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                OKBUtton1ActionPerformed(evt);
            }
        });

        annullaButton1.setText("Annulla");
        annullaButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                annullaButton1ActionPerformed(evt);
            }
        });

        jButton1.setText("->");
        jButton1.setName(""); // NOI18N
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        jButton2.setText("->");
        jButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton2ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addComponent(OKBUtton1, javax.swing.GroupLayout.PREFERRED_SIZE, 73, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(annullaButton1, javax.swing.GroupLayout.PREFERRED_SIZE, 75, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel2Layout.createSequentialGroup()
                                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel6)
                                    .addComponent(jLabel7)
                                    .addComponent(jLabel8)
                                    .addComponent(jLabel9)
                                    .addComponent(jLabel10)
                                    .addComponent(jScrollPane8))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jScrollPane7, javax.swing.GroupLayout.PREFERRED_SIZE, 209, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addComponent(titleField1)
                            .addComponent(trackNumberField1)
                            .addGroup(jPanel2Layout.createSequentialGroup()
                                .addComponent(albumField1, javax.swing.GroupLayout.PREFERRED_SIZE, 470, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(jButton2, javax.swing.GroupLayout.PREFERRED_SIZE, 29, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(jPanel2Layout.createSequentialGroup()
                                .addComponent(artistField1, javax.swing.GroupLayout.PREFERRED_SIZE, 470, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(jButton1, javax.swing.GroupLayout.PREFERRED_SIZE, 29, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addContainerGap())))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel6)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(artistField1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jButton1))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel7)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(titleField1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel8)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(albumField1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jButton2))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel9)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(trackNumberField1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addComponent(jLabel10)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jScrollPane8, javax.swing.GroupLayout.DEFAULT_SIZE, 154, Short.MAX_VALUE))
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGap(20, 20, 20)
                        .addComponent(jScrollPane7)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(annullaButton1)
                    .addComponent(OKBUtton1))
                .addContainerGap())
        );

        jSplitPane1.setRightComponent(jPanel2);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jSplitPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 729, Short.MAX_VALUE)
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jSplitPane1))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents
    private void caricaListaTracce(){
        DefaultListModel<String> model = new DefaultListModel();
        jList1.addListSelectionListener((ListSelectionEvent e) -> {
            if (jList1.getSelectedIndices().length>1){
                int[] indices;
                indices = jList1.getSelectedIndices();
                String artist = tracks.get(jList1.getSelectedIndex()).getArtist();
                String album = tracks.get(jList1.getSelectedIndex()).getAlbum();
                for(int i:indices){
                    if (artist.equals(tracks.get(i).getArtist()))
                        ;
                    else artist="";
                    if (album.equals(tracks.get(i).getAlbum()))
                        ;
                    else album="";
                }
                artistField1.setText(artist);
                albumField1.setText(album);
                titleField1.setText("");
                commentField1.setText("");
                trackNumberField1.setText("");
                titleField1.setEnabled(false);
                commentField1.setEnabled(false);
                trackNumberField1.setEnabled(false);
                return;
            }
            
            else{
                /* Salvo il contenuto della traccia modificata finora */
                
                Track track = tracks.get(jList1.getSelectedIndex());
                albumField1.setText(track.getAlbum());
                artistField1.setText(track.getArtist());
                titleField1.setText(track.getName());
                commentField1.setText(track.getComment());
                trackNumberField1.setText(track.getNumber());
                titleField1.setEnabled(true);
                commentField1.setEnabled(true);
                trackNumberField1.setEnabled(true);
            }
        });
        int i=0;
        AudioFile audioFile;
        for(File file :fileTracks){
            try{
                audioFile = AudioFileIO.read(file);
            }
            catch(Exception ex){
                fileTracks.remove(file);
                continue;
            }
           
            Tag tag = audioFile.getTag();
            if (tag==null)
                tag = audioFile.createDefaultTag();
           
            String artist = (tag.getFirst(FieldKey.ARTIST)!="") ? tag.getFirst(FieldKey.ARTIST) : "";
            String album = (tag.getFirst(FieldKey.ALBUM)!="") ? tag.getFirst(FieldKey.ALBUM) : "";
            String title = (tag.getFirst(FieldKey.TITLE)!="") ? tag.getFirst(FieldKey.TITLE) : file.getName();
            String track = (tag.getFirst(FieldKey.TRACK)!="") ? tag.getFirst(FieldKey.TRACK) : "";
            String comment = (tag.getFirst(FieldKey.COMMENT)!="") ? tag.getFirst(FieldKey.COMMENT) : "";
            String duration = Integer.toString(audioFile.getAudioHeader().getTrackLength());
            Track traccia = new Track(artist,title,album,file.getAbsolutePath(),track,comment);
            traccia.setDuration(duration);
            tracks.add(i, traccia);
            model.add(i,file.getAbsolutePath().substring(JMusicMan.directory.length(),
                                                         file.getAbsolutePath().length()-JMusicMan.directory.length()));
            i++;
        }
        this.jList1.setModel(model);
    }
    private void OKBUtton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_OKBUtton1ActionPerformed
        
        for (Track track:tracks){
            AudioFile af;
            try{
                af = AudioFileIO.read(new File(track.getPath()));
            }
            catch(Exception ex){
                continue;
            }
            Tag tag = af.createDefaultTag();
            try {
                tag.addField(FieldKey.ARTIST, track.getArtist());
            } catch (KeyNotFoundException | FieldDataInvalidException ex) {
                contatore++;
                Logger.getLogger(SkippedTracksFrame.class.getName()).log(Level.SEVERE, null, ex);
            }
            try {
                tag.addField(FieldKey.ALBUM, track.getAlbum());
            } catch (KeyNotFoundException | FieldDataInvalidException ex) {
                contatore++;
                Logger.getLogger(SkippedTracksFrame.class.getName()).log(Level.SEVERE, null, ex);
            }
            try {
                tag.addField(FieldKey.TITLE, track.getName());
            } catch (KeyNotFoundException | FieldDataInvalidException ex) {
                contatore++;
                Logger.getLogger(SkippedTracksFrame.class.getName()).log(Level.SEVERE, null, ex);
            }
            try {
                tag.addField(FieldKey.TRACK, track.getNumber());
            } catch (KeyNotFoundException | FieldDataInvalidException ex) {
                contatore++;
                Logger.getLogger(SkippedTracksFrame.class.getName()).log(Level.SEVERE, null, ex);
            }
            try {
                af.setTag(tag);
                af.commit();
            } catch (CannotWriteException ex) {
                contatore++;
                Logger.getLogger(SkippedTracksFrame.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        this.setVisible(false);
        it.albe.jmusicman.JMusicMan.update(true,false);
        //image already handled in ActionListener, row 66
    }//GEN-LAST:event_OKBUtton1ActionPerformed

    private void annullaButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_annullaButton1ActionPerformed
        
        this.setVisible(false);
    }//GEN-LAST:event_annullaButton1ActionPerformed

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        //tasto memorizza artista
        int[] indici = jList1.getSelectedIndices();
        for(int i:indici){
            Track track = tracks.get(i);
            track.setArtist(artistField1.getText());
            contatore--;
        }
    }//GEN-LAST:event_jButton1ActionPerformed

    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed
        //tasto memorizza album
        int[] indici = jList1.getSelectedIndices();
        for(int i:indici){
            Track track = tracks.get(i);
            track.setAlbum(albumField1.getText());
            contatore--;
        }
        
    }//GEN-LAST:event_jButton2ActionPerformed

    private void titleField1KeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_titleField1KeyReleased
        tracks.get(jList1.getSelectedIndex()).setName(titleField1.getText());
    }//GEN-LAST:event_titleField1KeyReleased

    private void trackNumberField1KeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_trackNumberField1KeyReleased

        tracks.get(jList1.getSelectedIndex()).setTrack(trackNumberField1.getText());
    }//GEN-LAST:event_trackNumberField1KeyReleased

    private void trackNumberField1KeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_trackNumberField1KeyTyped
        
    }//GEN-LAST:event_trackNumberField1KeyTyped

    private void artistField1FocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_artistField1FocusLost
        int[] indici = jList1.getSelectedIndices();
        for(int i:indici){
            Track track = tracks.get(i);
            track.setArtist(artistField1.getText());
            contatore--;
        }
    }//GEN-LAST:event_artistField1FocusLost

    private void albumField1FocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_albumField1FocusLost
        //tasto memorizza album
        int[] indici = jList1.getSelectedIndices();
        for(int i:indici){
            Track track = tracks.get(i);
            track.setAlbum(albumField1.getText());
            contatore--;
        }
    }//GEN-LAST:event_albumField1FocusLost

    private void albumField1FocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_albumField1FocusGained
        albumField1.selectAll();
    }//GEN-LAST:event_albumField1FocusGained
    public void setVisible(boolean vsbl){
        if ((contatore>0)&&(!vsbl))
            IO.print(null, "Attenzione: alcuni file audio non sono stati catalogati.");
        super.setVisible(vsbl);
    }
    /**
     * @param args the command line arguments
     */

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton OKBUtton1;
    private javax.swing.JTextField albumField1;
    private javax.swing.JButton annullaButton1;
    private javax.swing.JTextField artistField1;
    private javax.swing.JTextArea commentField1;
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton2;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JList<String> jList1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane7;
    private javax.swing.JScrollPane jScrollPane8;
    private javax.swing.JSplitPane jSplitPane1;
    private javax.swing.JTextField titleField1;
    private javax.swing.JTextField trackNumberField1;
    // End of variables declaration//GEN-END:variables
}
