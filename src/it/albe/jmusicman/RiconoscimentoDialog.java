/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package it.albe.jmusicman;

import java.awt.Dimension;
import java.awt.FlowLayout;
import java.util.List;
import javax.swing.DefaultListModel;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JProgressBar;
import it.albe.jmusicman.Track;
import javax.swing.ListModel;

/**
 *
 * @author Alberto
 */
public class RiconoscimentoDialog extends javax.swing.JDialog {

    /**
     * Creates new form RiconoscimentoDialog
     */
    public List<Track> tracce = null;
    boolean daAlbum = false;
    public RiconoscimentoDialog(java.awt.Frame parent, boolean modal, boolean daAlbum) {
        
        super(parent, modal);
        initComponents();
        this.daAlbum = daAlbum;
        /*
        JPanel panel = new JPanel(false);
        panel.setLayout(new FlowLayout());
        JProgressBar progressBar = new JProgressBar();
        progressBar.setIndeterminate(true);
        JLabel label = new JLabel("Reperimento informazioni in corso...");
        label.setBounds((this.getWidth()/2)-100, (this.getHeight()/2)-30, 200, 20);
        progressBar.setBounds((this.getWidth()/2)-100, this.getHeight()/2, 200, 20);
        panel.add(label);
        panel.add(progressBar);
        this.getContentPane().add(panel);
        this.pack();    
        */
    }
    public void setTrackList(List<Track> trackList){
        //this.removeAll();
        //initComponents();
        DefaultListModel<String> model = new DefaultListModel<String>();
        int i=0;
        for (Track traccia : trackList){
            model.add(i, traccia.getName());
            i++;
        }
        jList1.setModel(model);
        tracce = trackList;
    }
    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        jList1 = new javax.swing.JList<>();
        jLabel1 = new javax.swing.JLabel();
        artistField = new javax.swing.JTextField();
        jLabel2 = new javax.swing.JLabel();
        titleField = new javax.swing.JTextField();
        jLabel3 = new javax.swing.JLabel();
        albumField = new javax.swing.JTextField();
        jLabel4 = new javax.swing.JLabel();
        trackNumberField = new javax.swing.JTextField();
        OKBUtton = new javax.swing.JButton();
        annullaButton = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);

        jList1.addListSelectionListener(new javax.swing.event.ListSelectionListener() {
            public void valueChanged(javax.swing.event.ListSelectionEvent evt) {
                jList1ValueChanged(evt);
            }
        });
        jScrollPane1.setViewportView(jList1);

        jLabel1.setText("Artista");

        jLabel2.setText("Titolo");

        jLabel3.setText("Album");

        jLabel4.setText("Numero traccia");

        trackNumberField.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                trackNumberFieldKeyReleased(evt);
            }
        });

        OKBUtton.setText("OK");
        OKBUtton.setToolTipText("");
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

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(artistField)
                    .addComponent(titleField)
                    .addComponent(albumField)
                    .addComponent(trackNumberField)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 494, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel1)
                            .addComponent(jLabel2)
                            .addComponent(jLabel3)
                            .addComponent(jLabel4)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addComponent(OKBUtton, javax.swing.GroupLayout.PREFERRED_SIZE, 73, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(annullaButton, javax.swing.GroupLayout.PREFERRED_SIZE, 75, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addContainerGap())
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 118, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
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
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(OKBUtton)
                    .addComponent(annullaButton))
                .addGap(0, 0, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void trackNumberFieldKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_trackNumberFieldKeyReleased
        
    }//GEN-LAST:event_trackNumberFieldKeyReleased

    private void OKBUttonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_OKBUttonActionPerformed
        if (daAlbum){
            it.albe.utils.IO.print(null,"Le tracce verranno modificate provando a cercare il numero della traccia nel titolo vecchio.");
            if (tracce!=null){
                this.setVisible(false);
            }
        }
        else {
            
        }
    }//GEN-LAST:event_OKBUttonActionPerformed

    private void annullaButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_annullaButtonActionPerformed
        
    }//GEN-LAST:event_annullaButtonActionPerformed

    private void jList1ValueChanged(javax.swing.event.ListSelectionEvent evt) {//GEN-FIRST:event_jList1ValueChanged
        if (tracce!=null){
            Track traccia = tracce.get(jList1.getSelectedIndex());
            artistField.setText(traccia.getArtist());
            titleField.setText(traccia.getName());
            trackNumberField.setText(traccia.getNumber());
            albumField.setText((traccia.getAlbum()));
        }
    }//GEN-LAST:event_jList1ValueChanged

    

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton OKBUtton;
    private javax.swing.JTextField albumField;
    private javax.swing.JButton annullaButton;
    private javax.swing.JTextField artistField;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JList<String> jList1;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTextField titleField;
    private javax.swing.JTextField trackNumberField;
    // End of variables declaration//GEN-END:variables
}