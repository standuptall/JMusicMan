/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package it.albe.jmusicman;

import static it.albe.jmusicman.JMusicMan.document;
import it.albe.jmusicman.Contatori;
import it.albe.utils.IO;
import it.albe.jmusicman.Style;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import static java.awt.Image.SCALE_DEFAULT;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.TreeSelectionEvent;
import javax.swing.tree.*;
import java.io.*;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSpinner;
import javax.swing.SpinnerNumberModel;
import javax.swing.UIManager;
import javax.swing.event.ChangeEvent;
import org.jdom.*;
import org.jdom.Element;
import org.jdom.filter.ElementFilter;
import org.jdom.output.Format;
import org.jdom.output.XMLOutputter;
import org.jaudiotagger.audio.AudioFile;
import org.jaudiotagger.audio.AudioFileIO;
import org.jaudiotagger.tag.FieldKey;
import org.jaudiotagger.tag.Tag;
import org.jaudiotagger.tag.images.Artwork;
import org.jaudiotagger.tag.images.ArtworkFactory;
import org.jdom.*;
import org.jdom.input.SAXBuilder;
import org.jdom.output.*;

/**
 *
 * @author Alberto
 */
public class Frame extends javax.swing.JFrame{

    /**
     * Creates new form Frame
     */
    public Frame() {
        try{
           UIManager.setLookAndFeel("com.sun.java.swing.plaf.windows.WindowsLookAndFeel");
           javax.swing.SwingUtilities.updateComponentTreeUI(this);
        }
        catch (Exception e){
            IO.err(this, "Errore nel settare il look and feel:"+e.toString());
        
        }
        initComponents();
        jTree1.setCellRenderer(new it.albe.jmusicman.ArtistiCellRenderer());
        numerazione = 0;
        this.setLocation((Toolkit.getDefaultToolkit().getScreenSize().width)/2 - this.getWidth()/2, (Toolkit.getDefaultToolkit().getScreenSize().height)/2 - this.getHeight()/2);
        jTree1.getSelectionModel().setSelectionMode(TreeSelectionModel.SINGLE_TREE_SELECTION);
        jTree1.addTreeSelectionListener(new javax.swing.event.TreeSelectionListener() {

            @Override
            public void valueChanged(TreeSelectionEvent tse) {
                
                DefaultMutableTreeNode node = (DefaultMutableTreeNode)jTree1.getLastSelectedPathComponent();
                if (node==null)
                    return;
                
                if (node.isLeaf()){
                    Album album = (Album)node.getUserObject();
                    javax.swing.DefaultListModel model = new javax.swing.DefaultListModel();
                    for (int i=0;i<album.getNumberOfTracks();i++){
                        model.addElement(album.getTrackAt(i));
                    }
                    jList1.setModel(model);
                }
                
                    
                    
            }
        });
        jList1.setSelectionMode(javax.swing.ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
        jList1.getSelectionModel().addListSelectionListener(new javax.swing.event.ListSelectionListener() {

            @Override
            public void valueChanged(ListSelectionEvent lse) {
                if (!lse.getValueIsAdjusting()){
                    Track track = (Track)jList1.getSelectedValue();
                    mostraInfoTraccia(track);
                    try{
                        try{
                            if (JMusicMan.playerDir!="")
                                Runtime.getRuntime().exec(JMusicMan.playerDir + " \"" + track.getPath() + "\"");
                        }
                        catch(Exception e){
                            IO.err(null, "Errore nell'eseguire il player:"+e.toString());
                        }
                            
                        if ((numerazione>0)&&(numerazione<=numerazioneStart+numerazione)){
                            track.setTrack(String.format("%02d", numerazione));
                            AudioFile af = AudioFileIO.read(new File(track.getPath()));
                            af.getTag().setField(FieldKey.TRACK, track.getNumber());
                            af.commit();
                            numerazione++;
                            jList1.updateUI();
                        }
                        if ((numerazione-numerazioneStart+1)>jList1.getModel().getSize()){
                            numerazione = 0;
                            jTree1.setEnabled(true);
                            textFieldCerca.setEnabled(true);
                            iniziaNumerazioneMenuItem.setEnabled(true);
                        }
                    }
                    catch(Exception e){
                        IO.err(null, "Si è verificato un errore:"+e.toString());
                    }
                }
            }
        });
        ImageIcon imageIcon = new ImageIcon(it.albe.jmusicman.JMusicMan.class.getResource("icons/refresh.png"));
        Image image = imageIcon.getImage().getScaledInstance(30, 30, java.awt.Image.SCALE_DEFAULT);
        
        refreshButton.setIcon(new ImageIcon(image));
        //refreshButton.setText("\nRicarica albero");
        imageIcon = new ImageIcon(it.albe.jmusicman.JMusicMan.class.getResource("icons/tree.png"));
        image = imageIcon.getImage().getScaledInstance(30, 30, java.awt.Image.SCALE_DEFAULT);
        
        aggiornaButton.setIcon(new ImageIcon(image));        
        //aggiornaButton.setText("\nAggiorna libreria");
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPopupMenu1 = new javax.swing.JPopupMenu();
        modificaPopumMenu = new javax.swing.JMenuItem();
        jSplitPane1 = new javax.swing.JSplitPane();
        jScrollPane1 = new javax.swing.JScrollPane();
        jTree1 = new javax.swing.JTree();
        jScrollPane2 = new javax.swing.JScrollPane();
        jList1 = new javax.swing.JList();
        jProgressBar1 = new javax.swing.JProgressBar();
        label = new javax.swing.JLabel();
        refreshButton = new javax.swing.JButton();
        jScrollPane3 = new javax.swing.JScrollPane();
        jPanel1 = new javax.swing.JPanel();
        labelArtista = new javax.swing.JLabel();
        labelAlbum = new javax.swing.JLabel();
        labelTitolo = new javax.swing.JLabel();
        labelDuration = new javax.swing.JLabel();
        labelPath = new javax.swing.JLabel();
        jScrollPane4 = new javax.swing.JScrollPane();
        textFieldCerca = new javax.swing.JTextField();
        contatoriLabel = new javax.swing.JLabel();
        aggiornaButton = new javax.swing.JButton();
        jMenuBar1 = new javax.swing.JMenuBar();
        libreriaMenu = new javax.swing.JMenu();
        aggiornaMenuItem = new javax.swing.JMenuItem();
        aggiungiMenuItem = new javax.swing.JMenuItem();
        impostaPlayerMenuItem = new javax.swing.JMenuItem();
        impostaCartellaMenuItem = new javax.swing.JMenuItem();
        sincronizzazioneMenu = new javax.swing.JMenu();
        rilevaDispositivoMenuItem = new javax.swing.JMenuItem();
        sincronizzaMenuItem = new javax.swing.JMenuItem();
        jMenu1 = new javax.swing.JMenu();
        iniziaNumerazioneMenuItem = new javax.swing.JMenuItem();
        modificaMassiva = new javax.swing.JMenuItem();
        riconosciTracciaMenuItem = new javax.swing.JMenuItem();
        compilaTitoliMenuItem = new javax.swing.JMenuItem();
        infoMenu = new javax.swing.JMenu();
        aboutItem = new javax.swing.JMenuItem();

        modificaPopumMenu.setLabel("Modifica...");
        modificaPopumMenu.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                modificaPopumMenuActionPerformed(evt);
            }
        });
        jPopupMenu1.add(modificaPopumMenu);

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("JMusicMan");
        setResizable(false);

        jSplitPane1.setBackground(Style.backGroundColor);
        jSplitPane1.setDividerLocation(200);
        jSplitPane1.setToolTipText("");
        jSplitPane1.setMinimumSize(new java.awt.Dimension(200, 250));
        jSplitPane1.setOneTouchExpandable(true);
        jSplitPane1.setPreferredSize(new java.awt.Dimension(2, 324));

        jScrollPane1.setHorizontalScrollBarPolicy(javax.swing.ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
        jScrollPane1.setVerticalScrollBarPolicy(javax.swing.ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
        jScrollPane1.setAutoscrolls(true);
        jScrollPane1.setDoubleBuffered(true);

        jTree1.setBackground(Style.backGroundColor);
        jTree1.setForeground(Style.foreGroundColor);
        javax.swing.tree.DefaultMutableTreeNode treeNode1 = new javax.swing.tree.DefaultMutableTreeNode("JMusicManLibrary");
        jTree1.setModel(new javax.swing.tree.DefaultTreeModel(treeNode1));
        jTree1.setToolTipText("");
        jTree1.setAutoscrolls(true);
        jTree1.setMaximumSize(new java.awt.Dimension(107, 1000));
        jTree1.setPreferredSize(new java.awt.Dimension(107, 600));
        jTree1.setRowHeight(0);
        jScrollPane1.setViewportView(jTree1);

        jSplitPane1.setLeftComponent(jScrollPane1);

        jList1.setBackground(Style.backGroundColor);
        jList1.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jList1.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                Frame.this.mouseClicked(evt);
            }
        });
        jScrollPane2.setViewportView(jList1);

        jSplitPane1.setRightComponent(jScrollPane2);

        refreshButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                refreshButtonActionPerformed(evt);
            }
        });

        jPanel1.setBackground(Style.backGroundColor);

        labelArtista.setFont(new java.awt.Font("Tahoma", 0, 24)); // NOI18N
        labelArtista.setText("Artista");

        labelAlbum.setFont(new java.awt.Font("Tahoma", 2, 14)); // NOI18N
        labelAlbum.setText("Album");

        labelTitolo.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        labelTitolo.setText("Nome Traccia");

        labelDuration.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        labelDuration.setText("0:00");

        labelPath.setFont(new java.awt.Font("Tahoma", 0, 10)); // NOI18N
        labelPath.setText("<<Percorso_file>>");

        jScrollPane4.setBackground(Style.backGroundColor);
        jScrollPane4.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jScrollPane4MouseClicked(evt);
            }
        });

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane4, javax.swing.GroupLayout.PREFERRED_SIZE, 330, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                        .addComponent(labelAlbum, javax.swing.GroupLayout.DEFAULT_SIZE, 173, Short.MAX_VALUE)
                        .addComponent(labelArtista, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(labelTitolo, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(labelDuration, javax.swing.GroupLayout.PREFERRED_SIZE, 83, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(labelPath))
                .addContainerGap(137, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(labelArtista)
                .addGap(18, 18, 18)
                .addComponent(labelAlbum)
                .addGap(18, 18, 18)
                .addComponent(labelTitolo)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(labelDuration)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(labelPath)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane4, javax.swing.GroupLayout.PREFERRED_SIZE, 330, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(50, Short.MAX_VALUE))
        );

        jScrollPane3.setViewportView(jPanel1);

        textFieldCerca.setBackground(Style.backGroundColor);
        textFieldCerca.setText("Cerca...");
        textFieldCerca.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                textFieldCercaFocusGained(evt);
            }
        });
        textFieldCerca.addPropertyChangeListener(new java.beans.PropertyChangeListener() {
            public void propertyChange(java.beans.PropertyChangeEvent evt) {
                textFieldCercaPropertyChange(evt);
            }
        });
        textFieldCerca.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                textFieldCercaKeyTyped(evt);
            }
        });

        contatoriLabel.setText("jLabel1");

        aggiornaButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                aggiornaButtonActionPerformed(evt);
            }
        });

        jMenuBar1.setBackground(Style.backGroundColor);
        jMenuBar1.setForeground(Style.foreGroundColor);
        jMenuBar1.setToolTipText("");

        libreriaMenu.setText("Libreria");
        libreriaMenu.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                libreriaMenuActionPerformed(evt);
            }
        });

        aggiornaMenuItem.setText("Aggiorna");
        aggiornaMenuItem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                aggiornaMenuItemActionPerformed(evt);
            }
        });
        libreriaMenu.add(aggiornaMenuItem);

        aggiungiMenuItem.setText("Aggiungi...");
        libreriaMenu.add(aggiungiMenuItem);

        impostaPlayerMenuItem.setText("Imposta player predefinito");
        impostaPlayerMenuItem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                impostaPlayerMenuItemActionPerformed(evt);
            }
        });
        libreriaMenu.add(impostaPlayerMenuItem);

        impostaCartellaMenuItem.setText("Imposta cartella");
        libreriaMenu.add(impostaCartellaMenuItem);

        jMenuBar1.add(libreriaMenu);

        sincronizzazioneMenu.setText("Sincronizzazione");

        rilevaDispositivoMenuItem.setText("Rileva dispositivo");
        rilevaDispositivoMenuItem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                rilevaDispositivoMenuItemActionPerformed(evt);
            }
        });
        sincronizzazioneMenu.add(rilevaDispositivoMenuItem);

        sincronizzaMenuItem.setText("Sincronizza");
        sincronizzaMenuItem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                sincronizzaMenuItemActionPerformed(evt);
            }
        });
        sincronizzazioneMenu.add(sincronizzaMenuItem);

        jMenuBar1.add(sincronizzazioneMenu);

        jMenu1.setLabel("Automatismi");

        iniziaNumerazioneMenuItem.setText("Inizia numerazione");
        iniziaNumerazioneMenuItem.setToolTipText("");
        iniziaNumerazioneMenuItem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                iniziaNumerazioneMenuItemActionPerformed(evt);
            }
        });
        jMenu1.add(iniziaNumerazioneMenuItem);

        modificaMassiva.setLabel("Modifica titoli massivamente");
        modificaMassiva.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                modificaMassivaActionPerformed(evt);
            }
        });
        jMenu1.add(modificaMassiva);

        riconosciTracciaMenuItem.setText("Riconosci traccia selezionata");
        riconosciTracciaMenuItem.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                riconosciTracciaMenuItemMouseClicked(evt);
            }
        });
        riconosciTracciaMenuItem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                riconosciTracciaMenuItemActionPerformed(evt);
            }
        });
        jMenu1.add(riconosciTracciaMenuItem);

        compilaTitoliMenuItem.setText("Compila titoli album automaticamente");
        compilaTitoliMenuItem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                compilaTitoliMenuItemActionPerformed(evt);
            }
        });
        jMenu1.add(compilaTitoliMenuItem);

        jMenuBar1.add(jMenu1);

        infoMenu.setText("Info");

        aboutItem.setText("About...");
        aboutItem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                aboutItemActionPerformed(evt);
            }
        });
        infoMenu.add(aboutItem);

        jMenuBar1.add(infoMenu);

        setJMenuBar(jMenuBar1);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(label, javax.swing.GroupLayout.DEFAULT_SIZE, 240, Short.MAX_VALUE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jProgressBar1, javax.swing.GroupLayout.PREFERRED_SIZE, 327, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(aggiornaButton, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(refreshButton, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jSplitPane1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(textFieldCerca, javax.swing.GroupLayout.DEFAULT_SIZE, 385, Short.MAX_VALUE)
                    .addComponent(contatoriLabel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jScrollPane3, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jSplitPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 541, Short.MAX_VALUE)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(textFieldCerca, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(9, 9, 9)
                        .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE))
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(refreshButton, javax.swing.GroupLayout.PREFERRED_SIZE, 38, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(aggiornaButton, javax.swing.GroupLayout.PREFERRED_SIZE, 38, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                        .addComponent(label, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jProgressBar1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addComponent(contatoriLabel))
                .addGap(6, 6, 6))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void aggiornaMenuItemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_aggiornaMenuItemActionPerformed
        JMusicMan.update(false,true);
    }//GEN-LAST:event_aggiornaMenuItemActionPerformed

    private void libreriaMenuActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_libreriaMenuActionPerformed
        
    }//GEN-LAST:event_libreriaMenuActionPerformed

    private void rilevaDispositivoMenuItemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_rilevaDispositivoMenuItemActionPerformed
        final FrameDetect frame = new FrameDetect();
        java.awt.EventQueue.invokeLater(new Runnable(){
            public void run(){
                frame.setVisible(true);
            }
        });
    }//GEN-LAST:event_rilevaDispositivoMenuItemActionPerformed

    @SuppressWarnings("empty-statement")
    private void sincronizzaMenuItemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_sincronizzaMenuItemActionPerformed
        if (JMusicMan.root!=null){
            File database = new File(JMusicMan.root.getAbsolutePath()+"\\Music\\JMusicManLibrary.xml");
            
            SAXBuilder builder = new SAXBuilder();
            Document document;
            try{
                if (!database.exists())
                        JMusicMan.createEmptyDocument(database);
                
                document = builder.build(database);
                Element rootElement = document.getRootElement();
                Element lasteditElement = rootElement.getChild("lastedit");
                long lastedit = Long.valueOf(lasteditElement.getText());
                if (lastedit>=JMusicMan.lastedit)
                    IO.print(this, "La libreria del dispositivo è aggiornata");
                else {
                    /* Carico database dispositivo */
                    ElementFilter filter = new ElementFilter();
                    java.util.Iterator<Element> ciao = document.getDescendants(filter);
                    ArrayList<Element> arrayDispositivo = new ArrayList<Element>();
                    while (ciao.hasNext()){
                        Element element  = ciao.next();
                        String text = element.getName();
                        if (text.equals("track"))
                             arrayDispositivo.add(element);
                    }
                    /*Carico database locale */
                    document = builder.build(JMusicMan.directory+"\\JMusicManLibrary.xml");
                    rootElement = document.getRootElement();
                    ciao = document.getDescendants(filter);
                    ArrayList<Element> arraylocale = new ArrayList<Element>();
                    while (ciao.hasNext()){
                         Element element  = ciao.next();
                         String text = element.getName();
                         if (text.equals("track"))
                              arraylocale.add(element);
                     }
                    /*determino tracce da copiare */
                    ArrayList<Track> tracks = JMusicMan.findTracksToCopy(arrayDispositivo, arraylocale);
                    int fileCopiati = tracks.size();
                    /*Trovo e cancello le tracce che non sono più presenti sul PC (eliminate dalla libreria)*/
                    int fileEliminati = JMusicMan.findTrackToDelete(arrayDispositivo, arraylocale,JMusicMan.root.getAbsolutePath()+"\\Music\\" );
                    
                    
                    /*copio le tracce */
                    if (tracks.size()>0)
                        JMusicMan.copyTracks(tracks, JMusicMan.root.getAbsolutePath()+"\\Music\\");
                    /*copio i file di database */ 
                    FileInputStream reader = new FileInputStream(new File(JMusicMan.directory+"\\JMusicManLibrary.xml"));
                    FileOutputStream writer = new FileOutputStream(database);
                    byte[] bytes =  new byte[1024];
                    int numbytes;
                    while ((numbytes = reader.read(bytes))>0){
                        writer.write(bytes,0,numbytes);
                    }
                    reader.close();
                    writer.close();
                    IO.print(this, "Dispositivo: " +Integer.toString(fileCopiati)+" file copiati e "+Integer.toString(fileEliminati)+" file eliminati");
                }
            }
            catch (Exception e){
                IO.err(this, "Errore durante la sincronizzazione:"+e.toString());
        
            }
        }
        else IO.err(this, "Imposta prima il dispositivo!");
    }//GEN-LAST:event_sincronizzaMenuItemActionPerformed

    private void aboutItemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_aboutItemActionPerformed
        IO.print(this, "JMusicMan versione "+Version.getVersion()+"\n(c) 2016 Alberto Zichittella\nIl programma non fornisce alcuna garanzia.\nRilasciato sotto licenza GPL3" );
    }//GEN-LAST:event_aboutItemActionPerformed

    private void impostaPlayerMenuItemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_impostaPlayerMenuItemActionPerformed
        javax.swing.JFileChooser fchooser = new javax.swing.JFileChooser();
        int sc = fchooser.showOpenDialog(this);
        if (sc==javax.swing.JFileChooser.APPROVE_OPTION){
            if (!fchooser.getSelectedFile().getAbsolutePath().endsWith(".exe"))
                IO.print(this, "Il file selezionato non è un eseguibile!");
            else {
                JMusicMan.playerDir = fchooser.getSelectedFile().getAbsolutePath();
                Element element = new Element("player");
                element.setText(JMusicMan.playerDir);
                JMusicMan.document.getRootElement().addContent(element);
                XMLOutputter xmlOutput = new XMLOutputter();
                xmlOutput.setFormat(Format.getPrettyFormat());
                try{
                    xmlOutput.output(JMusicMan.document,new FileWriter(JMusicMan.directory+"JMusicManLibrary.xml"));
                }
                catch (Exception e){
                    IO.err(this, "Errore nello impostare il player:"+e.toString());
                }
             }
                
        }
            
    }//GEN-LAST:event_impostaPlayerMenuItemActionPerformed
    /*
     * Evento appartenente al menù contestuale della lista
     * 
     */
    private void mouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_mouseClicked
        Track track = null;
        try{
            if (evt.getButton()==3){
                List<Track> tracks = jList1.getSelectedValuesList();
                if (tracks.isEmpty())
                    throw new java.lang.NullPointerException("Nessuna traccia selezionata");
                jPopupMenu1.show(this.jList1,evt.getX(),evt.getY());
            }
        }
        catch (Exception e){
            
        }
    }//GEN-LAST:event_mouseClicked
    /*
     * Evento appartenente al menù contestuale della lista
     */
    private void modificaPopumMenuActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_modificaPopumMenuActionPerformed
        EditInfo dialog = new EditInfo(this,true,jList1.getSelectedValuesList());
        dialog.setVisible(true);
        if (dialog.getResponse()==1){   //se hai premuto OK...
            for (int i=0;i<dialog.getTracks().size();i++)
            try{
                Track track = dialog.getTracks().get(i);
                AudioFile audioFile = AudioFileIO.read(new File(track.getPath()));
                Tag tag = audioFile.getTag();
                if (dialog.albumModified)
                    tag.setField(FieldKey.ALBUM,track.getAlbum());
                if (dialog.titleModified)
                    tag.setField(FieldKey.TITLE,track.getName());
                if (dialog.artistModified)
                    tag.setField(FieldKey.ARTIST,track.getArtist());
                if (dialog.trackModified)
                    tag.setField(FieldKey.TRACK,track.getNumber());
                if (dialog.commentModified)
                    tag.setField(FieldKey.COMMENT,track.getComment());
                if (dialog.imageModified){
                    Artwork art = tag.getFirstArtwork();
                    if (art==null)
                        art = ArtworkFactory.getNew();
                    art.setBinaryData(track.getImg());
                    tag.addField(art);
                    tag.addField(art);
                }
                
                audioFile.commit();
                Track newTrack = new Track(track.getArtist(),track.getName(),track.getAlbum(),track.getPath(),track.getNumber(),track.getComment());
                JMusicMan.organize(new File(newTrack.getPath()));
                JMusicMan.updateTrack(newTrack, dialog.editResult());
                File file = new File(track.getPath());
                //file.delete();
            }
            catch (Exception e){
                System.out.print(e.getMessage());
            }
            dialog.dispose();
        } 
        else if (dialog.getResponse()==3){   //se hai premuto Reset tag...
            for (int i=0;i<dialog.getTracks().size();i++)
            try{
                Track track = dialog.getTracks().get(i);
                AudioFile audioFile = AudioFileIO.read(new File(track.getPath()));
                Tag tag = audioFile.createDefaultTag(); 
                if (dialog.albumModified)
                    tag.setField(FieldKey.ALBUM,track.getAlbum());
                if (dialog.titleModified)
                    tag.setField(FieldKey.TITLE,track.getName());
                if (dialog.artistModified)
                    tag.setField(FieldKey.ARTIST,track.getArtist());
                if (dialog.imageModified)
                    ;//audioFile.setAlbumImage(track.getImg(), "image/png");
                if (dialog.trackModified)
                    tag.setField(FieldKey.TRACK,track.getNumber());
                if (dialog.commentModified)
                    tag.setField(FieldKey.COMMENT,track.getComment());
                audioFile.setTag(tag);
                audioFile.commit();
                Track newTrack = new Track(track.getArtist(),track.getName(),track.getAlbum(),track.getPath(),track.getNumber(),track.getComment());
                JMusicMan.organize(new File(newTrack.getPath()));
                JMusicMan.updateTrack(newTrack, dialog.editResult());
                File file = new File(track.getPath());
                //file.delete();
            }
            catch (Exception e){
                System.out.print(e.getMessage());
            }
            dialog.dispose();
        }
    }//GEN-LAST:event_modificaPopumMenuActionPerformed

    private void refreshButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_refreshButtonActionPerformed
        JMusicMan.loadLibrary();
    }//GEN-LAST:event_refreshButtonActionPerformed

    private void jScrollPane4MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jScrollPane4MouseClicked
        try {
            //click sull'immagine della cover art
            Track track = (Track)jList1.getSelectedValue();
            if (track == null)
                return;
            if (track.getImg()==null)
                return;
            JFrame imageFrame;
            imageFrame = new JFrame("cover art");
            imageFrame.add(new ImagePanel(track));
            imageFrame.setLocationRelativeTo(null);
            imageFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
            imageFrame.pack();
            imageFrame.setResizable(false);
            imageFrame.setVisible(true);
        } catch (Exception ex) {
            Logger.getLogger(Frame.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_jScrollPane4MouseClicked

    private void textFieldCercaFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_textFieldCercaFocusGained
        if (textFieldCerca.getText().equals("Cerca..."))
            textFieldCerca.setText("");
    }//GEN-LAST:event_textFieldCercaFocusGained

    private void textFieldCercaKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_textFieldCercaKeyTyped
        
        ricerca(textFieldCerca.getText());
    }//GEN-LAST:event_textFieldCercaKeyTyped

    private void iniziaNumerazioneMenuItemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_iniziaNumerazioneMenuItemActionPerformed
        if (jList1.getModel().getSize()==0){
            IO.print(this, "Per usare questa funzione è necessario selezionare un album!");
            return;
        }
        // creo finestra di dialogo --->
        JDialog dialog = new JDialog(this);
        JPanel listPane = new JPanel();
        dialog.add(listPane);
        listPane.setLayout(new BoxLayout(listPane, BoxLayout.PAGE_AXIS));
        listPane.add(new JLabel("Inizia dal numero: "));
        JSpinner spinner = new JSpinner();
        spinner.getModel().setValue(1);
        spinner.getModel().addChangeListener(new javax.swing.event.ChangeListener() {
            public void stateChanged(ChangeEvent e) {
              if ((((int)((SpinnerNumberModel)e.getSource()).getValue())>jList1.getModel().getSize())||
                    (((int)((SpinnerNumberModel)e.getSource()).getValue())<1)  )
                  ((SpinnerNumberModel)e.getSource()).setValue(((SpinnerNumberModel)e.getSource()).getPreviousValue());
            }
          });
        spinner.setMinimumSize(new Dimension(200,30));
        listPane.add(spinner);
        JButton button = new JButton("OK");
        button.setMinimumSize(new Dimension(200,20));
        button.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent e){
                dialog.setVisible(false);
                numerazione = (int)spinner.getValue();
                numerazioneStart = numerazione;
            }
        });
        listPane.add(button);        
        dialog.pack();
        dialog.setLocation(java.awt.MouseInfo.getPointerInfo().getLocation());
        dialog.setSize(new Dimension(200,100));
        dialog.setVisible(true);
        
        //<--- creo finestra di dialogo
        jTree1.setEnabled(false);
        textFieldCerca.setEnabled(false);
        iniziaNumerazioneMenuItem.setEnabled(false);
    }//GEN-LAST:event_iniziaNumerazioneMenuItemActionPerformed

    private void textFieldCercaPropertyChange(java.beans.PropertyChangeEvent evt) {//GEN-FIRST:event_textFieldCercaPropertyChange
        
    }//GEN-LAST:event_textFieldCercaPropertyChange

    private void modificaMassivaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_modificaMassivaActionPerformed
        if (jList1.getModel().getSize()==0){
            IO.print(this, "Per usare questa funzione è necessario selezionare un album!");
            return;
        }
        it.albe.jmusicman.ModificaMassiva dialog = new it.albe.jmusicman.ModificaMassiva(this,true);
        dialog.setVisible(true);
    }//GEN-LAST:event_modificaMassivaActionPerformed

    private void riconosciTracciaMenuItemMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_riconosciTracciaMenuItemMouseClicked
        
        
        
    }//GEN-LAST:event_riconosciTracciaMenuItemMouseClicked

    private void riconosciTracciaMenuItemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_riconosciTracciaMenuItemActionPerformed
        if (jList1.getModel().getSize()==0){
            IO.print(this, "Per usare questa funzione è necessario selezionare un album!");
            return;
        }
        if (jList1.getSelectedIndices().length==0){
            IO.print(this, "Selezionare una traccia!");
            return;
        }
        if (jList1.getSelectedIndices().length>1){
            IO.print(this, "Selezionare solo una traccia!");
            return;
        }
        it.albe.jmusicman.RiconoscimentoDialog dialog = new it.albe.jmusicman.RiconoscimentoDialog(this,false,false);
        dialog.setVisible(true);
        String artista = null;
        int durata = 0;
        String album = null;
        try {
            artista = URLEncoder.encode(((Track) jList1.getModel().getElementAt(jList1.getSelectedIndex())).getArtist(), "UTF-8");
            durata = Integer.parseInt(((Track) jList1.getModel().getElementAt(jList1.getSelectedIndex())).getDuration()) * 1000;
            album = URLEncoder.encode(((Track) jList1.getModel().getElementAt(jList1.getSelectedIndex())).getArtist(), "UTF-8");
        } catch (UnsupportedEncodingException unsupportedEncodingException) {
        } 
        String query = "artist:%22"+artista+"%22%20AND%20dur:"+durata;
        URL url = null;
        try {
            url = new URL("http://musicbrainz.org/ws/2/recording/?query="+query);
        } catch (MalformedURLException ex) {
            Logger.getLogger(Frame.class.getName()).log(Level.SEVERE, null, ex);
            return;
        }
        try {
            URLConnection con = url.openConnection();
            InputStream in = con.getInputStream();
            String encoding = con.getContentEncoding();
            encoding = encoding == null ? "UTF-8" : encoding;
            SAXBuilder builder = new SAXBuilder(); 
            Document document;
            document = builder.build(in);
            Element root = document.getRootElement();
            Element recordingList = (Element)root.getChildren().get(0);
            List<Track> tracce = new ArrayList<Track>();
            int count = recordingList.getAttribute("count").getIntValue();
            List<Element> lista = recordingList.getChildren();
            Namespace ns = Namespace.getNamespace("http://musicbrainz.org/ns/mmd-2.0#");
            /* TODO: prevedere di aggiungere anche diversi tipi di album per una stessa traccia */
            for (Element elem : lista){
               String artist = elem.getChild("artist-credit",ns).getChild("name-credit",ns).getChild("artist",ns).getChildText("name",ns);
               String albume  = elem.getChild("release-list",ns).getChild("release",ns).getChildText("title",ns);
               String title = elem.getChildText("title",ns);
               tracce.add(new Track(artist,title,album,""));  
            }
            dialog.setTrackList(tracce);
        } catch (IOException iOException) {
        } catch (JDOMException ex) {
            Logger.getLogger(Frame.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_riconosciTracciaMenuItemActionPerformed

    private void compilaTitoliMenuItemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_compilaTitoliMenuItemActionPerformed
        if (jList1.getModel().getSize()==0){
            IO.print(this, "Per usare questa funzione è necessario selezionare un album!");
            return;
        }
        it.albe.jmusicman.RiconoscimentoDialog dialog = new it.albe.jmusicman.RiconoscimentoDialog(this,true,true);
        String artista = null;
        String albume = null;
        String album = ((Track) jList1.getModel().getElementAt(0)).getAlbum();
        try {
            artista = URLEncoder.encode(((Track) jList1.getModel().getElementAt(0)).getArtist(), "UTF-8");
            albume = URLEncoder.encode(album, "UTF-8");
        } catch (UnsupportedEncodingException unsupportedEncodingException) {
        } 
        String query = "artist:%22"+artista+"%22%20AND%20release:%22"+albume+"%22";
        URL url = null;
        try {
            url = new URL("http://musicbrainz.org/ws/2/recording/?query="+query);
        } catch (MalformedURLException ex) {
            Logger.getLogger(Frame.class.getName()).log(Level.SEVERE, null, ex);
            return;
        }
        try {
            
            URLConnection con = url.openConnection();
            InputStream in = con.getInputStream();
            
            String encoding = con.getContentEncoding();
            
            encoding = encoding == null ? "UTF-8" : encoding;
           
            SAXBuilder builder = new SAXBuilder(); 
            Document document;
            document = builder.build(in);
            //document = builder.build(new java.io.FileInputStream("C:\\Users\\Alberto\\Desktop\\download.xml"));
            Element root = document.getRootElement();
            Element recordingList = (Element)root.getChildren().get(0);
            List<Track> tracce = new ArrayList<Track>();
            int count = recordingList.getAttribute("count").getIntValue();
            List<Element> lista = recordingList.getChildren();
            Namespace ns = Namespace.getNamespace("http://musicbrainz.org/ns/mmd-2.0#");
            /* TODO: prevedere di aggiungere anche diversi tipi di album per una stessa traccia */
            for (Element elem : lista){
               String title = "";
               String track = "";
               List<Element> listaAlbum = elem.getChild("release-list",ns).getChildren();
               for (Element eleme : listaAlbum){
                   if (eleme.getChildText("title",ns).equals(album)){
                       track = eleme.getChild("medium-list",ns).getChild("medium",ns).getChild("track-list",ns).getChild("track",ns).getChildText("number",ns);
                       title = eleme.getChild("medium-list",ns).getChild("medium",ns).getChild("track-list",ns).getChild("track",ns).getChildText("title",ns);
                   }
               }
               tracce.add(new Track(artista,title,album,track));  
            }
            dialog.setTrackList(tracce);
            dialog.setVisible(true);
            if (dialog.tracce!=null)
                if (!dialog.tracce.isEmpty()){
                    for (int i=0;i<dialog.tracce.size();i++){
                        String trackNo = dialog.tracce.get(i).getNumber();
                        for (int j=0;j<jList1.getModel().getSize();j++){
                            try{
                                if (jList1.getModel().getElementAt(j).toString().contains(String.format("%02d",Integer.valueOf(trackNo)))){
                                    /*Traccia trovata e modifico le informazioni*/
                                    Track trackk = (Track)jList1.getModel().getElementAt(j);
                                    trackk.setName(dialog.tracce.get(i).getName());
                                    trackk.setTrack(String.format("%02d",Integer.valueOf(trackNo)));
                                    try{
                                        AudioFile audioFile = AudioFileIO.read(new File(trackk.getPath()));
                                        Tag tag = audioFile.getTag();
                                        tag.setField(FieldKey.TITLE,trackk.getName());
                                        tag.setField(FieldKey.TRACK,trackk.getNumber());
                                        audioFile.setTag(tag);
                                        audioFile.commit();
                                    }
                                    catch(Exception e){
                                        it.albe.utils.IO.err(this, "Si sono verificati alcuni errori");
                                        System.out.print(e.getMessage());
                                    }
                                    break;
                                }
                            }
                            catch (NumberFormatException ex){
                                
                            }
                                
                        }
                    }     
                    it.albe.jmusicman.JMusicMan.update(true,false);
                }
        } catch (IOException iOException) {
        } catch (JDOMException ex) {
            Logger.getLogger(Frame.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_compilaTitoliMenuItemActionPerformed

    private void aggiornaButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_aggiornaButtonActionPerformed
        it.albe.jmusicman.JMusicMan.update(false, true);
    }//GEN-LAST:event_aggiornaButtonActionPerformed

    private void mostraInfoTraccia(Track track) {
        int minuti = Integer.parseInt(track.getDuration()) / 60; 
        int secondi = Integer.parseInt(track.getDuration()) - (minuti*60);
        this.labelArtista.setText(track.getArtist());
        this.labelAlbum.setText(track.getAlbum());
        this.labelTitolo.setText(track.getName());
        this.labelDuration.setText(String.valueOf(minuti)+":"+String.valueOf(secondi));
        this.labelPath.setText(track.getPath());
        
        if (track.getImg()==null) {
                recuperaCoverArt(track);
            if (track.getImg()==null){
                jScrollPane4.setViewportView(null);
                
                return;
            }
                
        }
        javax.swing.ImageIcon image = new javax.swing.ImageIcon(track.getImg());
        image.setImage(image.getImage().getScaledInstance(jScrollPane4.getWidth()-10, jScrollPane4.getHeight()-10, SCALE_DEFAULT));
        JLabel label = new JLabel(image);
        label.setBackground(Style.backGroundColor);
        jScrollPane4.setViewportView(label);
            
    }
    
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JMenuItem aboutItem;
    private javax.swing.JButton aggiornaButton;
    private javax.swing.JMenuItem aggiornaMenuItem;
    private javax.swing.JMenuItem aggiungiMenuItem;
    private javax.swing.JMenuItem compilaTitoliMenuItem;
    private javax.swing.JLabel contatoriLabel;
    private javax.swing.JMenuItem impostaCartellaMenuItem;
    private javax.swing.JMenuItem impostaPlayerMenuItem;
    private javax.swing.JMenu infoMenu;
    private javax.swing.JMenuItem iniziaNumerazioneMenuItem;
    private javax.swing.JList jList1;
    private javax.swing.JMenu jMenu1;
    private javax.swing.JMenuBar jMenuBar1;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPopupMenu jPopupMenu1;
    public javax.swing.JProgressBar jProgressBar1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JScrollPane jScrollPane4;
    private javax.swing.JSplitPane jSplitPane1;
    public javax.swing.JTree jTree1;
    public javax.swing.JLabel label;
    private javax.swing.JLabel labelAlbum;
    private javax.swing.JLabel labelArtista;
    private javax.swing.JLabel labelDuration;
    private javax.swing.JLabel labelPath;
    private javax.swing.JLabel labelTitolo;
    private javax.swing.JMenu libreriaMenu;
    private javax.swing.JMenuItem modificaMassiva;
    private javax.swing.JMenuItem modificaPopumMenu;
    private javax.swing.JButton refreshButton;
    private javax.swing.JMenuItem riconosciTracciaMenuItem;
    private javax.swing.JMenuItem rilevaDispositivoMenuItem;
    private javax.swing.JMenuItem sincronizzaMenuItem;
    private javax.swing.JMenu sincronizzazioneMenu;
    private javax.swing.JTextField textFieldCerca;
    // End of variables declaration//GEN-END:variables
    private int numerazione; //se è maggiore di zero vuol dire che sta assegnando il numero della traccia 
    private int numerazioneStart; //mantiene il valore originario er il controllo del contatore
    /*
     * Metodo che serve a mostra informazioni traccia per visualizzae la cover nel caso non si possa caricare da memoria
     */
    private void recuperaCoverArt(Track track) {
        AudioFile af = null;
        try {
            af = AudioFileIO.read(new File(track.getPath()));
            Tag tag = af.getTag();
            if (tag.getFirstArtwork()!=null)
                track.setImg(tag.getFirstArtwork().getBinaryData());
        } catch (Exception ex) {
        }        
    }

    private void ricerca(String text) {
        List<Track> tracceTrovate = new ArrayList<Track>();
        TreeModel model = jTree1.getModel();
        List<Album> albums = new ArrayList<Album>();
        if (!"".equals(text)){
            DefaultMutableTreeNode root = (DefaultMutableTreeNode)model.getRoot();
            DefaultMutableTreeNode artista;
            DefaultMutableTreeNode album;
            for (Enumeration<DefaultMutableTreeNode> e = root.children(); e.hasMoreElements();){
                artista = e.nextElement();
                for (Enumeration<DefaultMutableTreeNode> e1 = artista.children(); e1.hasMoreElements();){
                    album = e1.nextElement();
                    albums.add((Album)album.getUserObject());
                }
            }
            for (int i=0;i<albums.size();i++)
                for (int j=0;j<albums.get(i).getNumberOfTracks();j++)
                    if (albums.get(i).getTrackAt(j).getName().toUpperCase().contains(text.toUpperCase())==true)
                        tracceTrovate.add(albums.get(i).getTrackAt(j));
        }
        javax.swing.DefaultListModel modello = new javax.swing.DefaultListModel();
        for (int i=0;i<tracceTrovate.size();i++){
            modello.addElement(tracceTrovate.get(i));
        }
        if (tracceTrovate.size()==0)
            modello.addElement(new Track("", "Nessun risultato", "",""));
        jList1.setModel(modello);                
    }

    void aggiornaContatori() {
        this.contatoriLabel.setText(String.format("%d artisti, %d album e %d brani per ", Contatori.artisti,Contatori.album,Contatori.brani));
        String durata = "";
        long resto = Contatori.durataTotale;
        int anni,mesi, settimane,giorni,ore,minuti,secondi;
        if (resto>0){
            if ((resto/31536000)>0){
                anni = (int) resto/31536000;
                resto = Contatori.durataTotale - (anni*31536000);
                durata += String.format("%d anni,", anni);   
            }                
            if ((resto/2592000)>0){
                mesi = (int) resto/2592000;
                resto = resto - (mesi*2592000);
                durata += String.format("%d mesi,", mesi);   
            }
            if ((resto/604800)>0){
                settimane = (int) resto/604800;
                resto = resto - (settimane*604800);
                durata += String.format("%d settimane,", settimane);   
            }
            if ((resto/86400)>0){
                giorni = (int) resto/86400;
                resto = resto - (giorni*86400);
                durata += String.format("%d giorni,", giorni);   
            }
            if ((resto/3600)>0){
                ore = (int) resto/3600;
                resto = resto - (ore*3600);
                durata += String.format("%d ore,", ore);   
            }
            if ((resto/60)>0){
                minuti = (int) resto/60;
                resto = resto - (minuti*60);
                durata += String.format("%d minuti,", minuti);   
            }
            durata += String.format("%d secondi.",resto); 
        }
        this.contatoriLabel.setText(this.contatoriLabel.getText()+durata);
    }
}

class ImagePanel extends JPanel {
    private ImageIcon image;
    
    public ImagePanel(Track track) throws Exception {
        image = new ImageIcon(track.getImg());
        this.setPreferredSize(new Dimension(image.getIconWidth(),image.getIconHeight()));
        setMinimumSize(new Dimension(image.getIconWidth(),image.getIconHeight()));
    }
    @Override
    public void paintComponent(Graphics g) {
        //super.paint(g);
        Graphics2D g2d = (Graphics2D) g.create();
        g2d.drawImage(image.getImage(), 0, 0, image.getIconWidth(), image.getIconHeight(), null);
        g2d.dispose();
    }
}