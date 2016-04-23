/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package it.albe.JMusicMan;

import com.mpatric.mp3agic.*;
import it.albe.utils.IO;
import java.awt.event.MouseEvent;
import javax.swing.*;
import java.awt.datatransfer.*;
import java.awt.Toolkit;
import java.io.IOException;
import java.awt.image.BufferedImage;
import java.awt.Graphics;
import java.awt.event.WindowEvent;
import java.util.List;
/**
 
 * @author Alberto
 */
public class EditInfo extends javax.swing.JDialog {
    private Track track;
    private List<Track> tracks;
    private int response;
    public boolean multipleEdit;
    public boolean artistModified, titleModified, albumModified, imageModified, trackModified;

    /**
     * Creates new form EditInfo
     */
    public EditInfo(java.awt.Frame parent, boolean modal, List<Track> tracks) {
        super(parent, modal);
        this.addWindowStateListener(new java.awt.event.WindowStateListener() {
            @Override
            public void windowStateChanged(WindowEvent we) {
                throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
            }
        });
        artistModified = false;
        titleModified = false;
        albumModified = false;
        imageModified = false;
        multipleEdit = false;
        initComponents();
        this.tracks = tracks;
        if (tracks.size()==1){
            editTrack(tracks.get(0));
        }
        else
            editTracks(tracks);
        
        
        
    }
    
    public void editTrack(final Track track){
        this.track = track;
        artistField.setText(track.getArtist());
        albumField.setText(track.getAlbum());
        titleField.setText(track.getName());
        trackNumberField.setText(Integer.toString(track.getNumber()));
        try{
            AudioFile audioFile = new AudioFile(track.getPath());
            byte[] img = audioFile.getAlbumImage();
            javax.swing.ImageIcon image = new javax.swing.ImageIcon(img);
            final JLabel label = new JLabel(image);

            java.awt.event.ActionListener menuListener = new java.awt.event.ActionListener() {

                public void actionPerformed(java.awt.event.ActionEvent event) {
                    if (event.getActionCommand().equals("Incolla")){
                        Clipboard clipboard = Toolkit.getDefaultToolkit().getSystemClipboard();
                        Transferable contents = clipboard.getContents(null);
                        java.awt.Image image;
                        try {
                        if (contents!=null)
                            if (contents.isDataFlavorSupported(DataFlavor.imageFlavor)){
                                image = (java.awt.Image)contents.getTransferData(DataFlavor.imageFlavor);
                                label.setIcon(new javax.swing.ImageIcon(image));
                                jScrollPane2.setViewportView(label);
                                BufferedImage bimage = (BufferedImage)image;
                                java.io.ByteArrayOutputStream baos = new java.io.ByteArrayOutputStream(); 
                                javax.imageio.ImageIO.write(bimage, "png", baos); 
                                byte[] res=baos.toByteArray();
                                track.setImg(res);
                                imageModified = true;
                            }
                        }
                        catch (Exception e){

                        }
                    }
                    if (event.getActionCommand().equals("Copia")){
                        Clipboard clipboard = Toolkit.getDefaultToolkit().getSystemClipboard();
                        ImageIcon imageIcon = (ImageIcon)label.getIcon();
                        TransferableImage transferable= new TransferableImage(imageIcon.getImage());
                        clipboard.setContents(transferable, null);
                    }

                }
            };

            final JPopupMenu popup = new JPopupMenu();
            JMenuItem item = new JMenuItem("Copia");
            item.addActionListener(menuListener);
            popup.add(item);
            item = new JMenuItem("Incolla");
            item.addActionListener(menuListener);
            popup.add(item);
            label.addMouseListener(new javax.swing.event.MouseInputListener() {

                @Override
                public void mouseClicked(MouseEvent me) {
                    if (me.getButton()==3)
                        popup.show(label,me.getX(),me.getY());
                }

                @Override
                public void mousePressed(MouseEvent me) {



                }

                @Override
                public void mouseReleased(MouseEvent me) {

                }

                @Override
                public void mouseEntered(MouseEvent me) {

                }

                @Override
                public void mouseExited(MouseEvent me) {

                }

                @Override
                public void mouseDragged(MouseEvent me) {

                }

                @Override
                public void mouseMoved(MouseEvent me) {

                }
            });
            jScrollPane2.setViewportView(label);
        }
        catch (Exception e){

        }
    }
    
    public void editTracks(List<Track> tracks){
        /* per molte tracce */
        this.track = tracks.get(0);
        multipleEdit = true;
        titleField.setEnabled(false);
        trackNumberField.setEnabled(false);
        commentField.setEnabled(false);
        artistField.setText(tracks.get(0).getArtist());
        albumField.setText(tracks.get(0).getAlbum());
    }
    public EditResult editResult(){
        return new EditResult(artistModified, titleModified,albumModified,imageModified);
    }
    public Track getTrack(){
        return track;
    }
    public List<Track> getTracks(){
        return tracks;
    }
    public int getResponse(){
        return response;
    }
    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jLabel1 = new javax.swing.JLabel();
        artistField = new javax.swing.JTextField();
        jLabel2 = new javax.swing.JLabel();
        titleField = new javax.swing.JTextField();
        jLabel3 = new javax.swing.JLabel();
        albumField = new javax.swing.JTextField();
        jLabel4 = new javax.swing.JLabel();
        trackNumberField = new javax.swing.JTextField();
        jScrollPane1 = new javax.swing.JScrollPane();
        commentField = new javax.swing.JTextArea();
        jScrollPane2 = new javax.swing.JScrollPane();
        jLabel5 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        OKBUtton = new javax.swing.JButton();
        annullaButton = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("Modifica informazioni");

        jLabel1.setText("Artista");

        jLabel2.setText("Titolo");

        jLabel3.setText("Album");

        jLabel4.setText("Numero traccia");

        commentField.setColumns(20);
        commentField.setRows(5);
        jScrollPane1.setViewportView(commentField);

        jLabel5.setText("Commento");

        jLabel6.setText("Immagine");

        OKBUtton.setText("OK");
        OKBUtton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                OKBUttonActionPerformed(evt);
            }
        });

        annullaButton.setText("Annulla");
        annullaButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                annullaButtonActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(artistField)
                    .addComponent(titleField)
                    .addComponent(albumField)
                    .addComponent(trackNumberField)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel1)
                            .addComponent(jLabel2)
                            .addComponent(jLabel3)
                            .addComponent(jLabel4)
                            .addComponent(jLabel5)
                            .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 203, Short.MAX_VALUE))
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 70, Short.MAX_VALUE)
                                .addComponent(jLabel6)
                                .addGap(0, 65, Short.MAX_VALUE))
                            .addGroup(layout.createSequentialGroup()
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jScrollPane2))))
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(OKBUtton, javax.swing.GroupLayout.PREFERRED_SIZE, 73, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(annullaButton, javax.swing.GroupLayout.PREFERRED_SIZE, 75, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jLabel1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(artistField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel2)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(titleField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel3)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(albumField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel4)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(trackNumberField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jLabel5)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 130, Short.MAX_VALUE))
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jLabel6)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jScrollPane2)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(annullaButton)
                    .addComponent(OKBUtton)))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void OKBUttonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_OKBUttonActionPerformed
        this.setVisible(false);
        response = 1;
        if (!track.getAlbum().equals(albumField.getText())){
            albumModified = true;
            track.setAlbum(albumField.getText());
            for(int i=0;i<tracks.size();i++)
                tracks.get(i).setAlbum(albumField.getText());
        }
        if (!track.getArtist().equals(artistField.getText())){
            artistModified = true;
            track.setArtist(artistField.getText());
            for(int i=0;i<tracks.size();i++)
                tracks.get(i).setArtist(artistField.getText());
        }
        if (!multipleEdit){
            if (!track.getName().equals(titleField.getText())){
                titleModified = true;
                track.setName(titleField.getText());
            }
            if (!Integer.toString(track.getNumber()).equals(trackNumberField.getText())){
                trackModified = true;
                track.setNumber(Integer.parseInt(trackNumberField.getText()));
            }
        }

        //image already handled in ActionListener, row 66
    }//GEN-LAST:event_OKBUttonActionPerformed

    private void annullaButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_annullaButtonActionPerformed
        this.setVisible(false);
        response = 2;
    }//GEN-LAST:event_annullaButtonActionPerformed

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(EditInfo.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(EditInfo.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(EditInfo.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(EditInfo.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the dialog */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                EditInfo dialog = new EditInfo(new javax.swing.JFrame(), true,null);
                dialog.addWindowListener(new java.awt.event.WindowAdapter() {
                    @Override
                    public void windowClosing(java.awt.event.WindowEvent e) {
                        System.exit(0);
                    }
                });
                dialog.setVisible(true);
            }
        });
    }
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton OKBUtton;
    private javax.swing.JTextField albumField;
    private javax.swing.JButton annullaButton;
    private javax.swing.JTextField artistField;
    private javax.swing.JTextArea commentField;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JTextField titleField;
    private javax.swing.JTextField trackNumberField;
    // End of variables declaration//GEN-END:variables
}
