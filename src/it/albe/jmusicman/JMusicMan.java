/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package it.albe.jmusicman;
import it.albe.utils.DataTable;
import it.albe.utils.IO;
import it.albe.utils.JSuggest;
import java.io.*;
import java.util.Iterator;
import java.util.List;
import org.jdom.*;
import org.jdom.input.SAXBuilder;
import org.jdom.output.*;
import javax.swing.tree.*;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.jdom.filter.ElementFilter;
import org.jaudiotagger.audio.AudioFile;
import org.jaudiotagger.audio.AudioFileIO;
import org.jaudiotagger.audio.exceptions.CannotReadException;
import org.jaudiotagger.audio.exceptions.InvalidAudioFrameException;
import org.jaudiotagger.audio.exceptions.ReadOnlyFileException;
import org.jaudiotagger.tag.FieldKey;
import org.jaudiotagger.tag.Tag;
import org.jaudiotagger.tag.TagException;

/**
 *
 * @author Alberto
 * 
 * Classe principale che contiene i metodi statici principali
 * 
 */
public class JMusicMan {
    public static Element rootElement;  //rootElement del documento XML
    public static String directory = System.getProperty("user.home")+"\\Music\\";  
    public static Document document;
    public static File root;      //root directory del dispositivo
    public static long lastedit;         //timestamp dell'ultima modifica del file xml
    public static String playerDir; //directory del player
    public static Frame frame;
    public static boolean primaVolta = false;
    public static JSuggest jSuggest;
    public static boolean modalitaorganizza = false; //non crea xml, mette gli album nella stessa directory di "directory" 
    public static void main(String[] args) {
        PrintStream out = null;        
        playerDir = "";
        root = null;
        frame = new Frame();
        frame.setVisible(true);
        jSuggest = null;
        Logger.getLogger("org.jaudiotagger").setLevel(Level.OFF);
        try {
            out = new PrintStream(new FileOutputStream(directory+"error.log"));        
            System.setErr(out);
        } 
        catch (FileNotFoundException ex) {
            Logger.getLogger(JMusicMan.class.getName()).log(Level.SEVERE, null, ex);
        } 
        loadLibrary(); 
        //update();
    }
    /******************************************************************
     * Carica la libreria XML e crea l'albero directory               *
     ******************************************************************/
    public static void loadLibrary(){
        if (JMusicMan.modalitaorganizza)
            return;
        List<File> files = deleteEmptyDirectories(directory);
        if (!files.isEmpty()){
            //it.albe.utils.IO("");
            it.albe.jmusicman.NotEmptyDirectories dialog = new it.albe.jmusicman.NotEmptyDirectories(frame,files);
            dialog.setVisible(true);
        }
        DataTable dataTableSuggest = new DataTable(new String[]{"Artista","Album","Titolo"});
        it.albe.jmusicman.Contatori.azzera();
        SAXBuilder builder = new SAXBuilder();    
        javax.swing.tree.DefaultMutableTreeNode treeNode1 = new javax.swing.tree.DefaultMutableTreeNode("JMusicMan Library");
        treeNode1.removeAllChildren(); 
        TreeModel treeModel = new javax.swing.tree.DefaultTreeModel(treeNode1);
        DefaultMutableTreeNode root = (DefaultMutableTreeNode)treeModel.getRoot();
        try{
            File libraryFile = new File (directory+"JMusicManLibrary.xml");
            if (libraryFile.exists())
                    document = builder.build(libraryFile);
                else if (IO.confirm(frame, "I brani verranno ordinati nella cartella Musica come:\n"
                        + "[artista]/[album]/[numero traccia] - [titolo].mp3. Procedere?")>0)
                        System.exit(0);
            else {
                primaVolta = true;
                update(false,true);
                return;
            }
            rootElement = document.getRootElement();
            Element lasteditElement = rootElement.getChild("lastedit");
            lastedit = Long.valueOf(lasteditElement.getText());
            lasteditElement = rootElement.getChild("player");
            if (lasteditElement!=null)
                playerDir = lasteditElement.getText();
            Element library = rootElement.getChild("library"); 
            List artists = library.getChildren();
            Iterator iterator = artists.iterator(); 
            while(iterator.hasNext()){
                it.albe.jmusicman.Contatori.artisti++;
                Element artist = (Element)iterator.next();
                DefaultMutableTreeNode artistNode = new DefaultMutableTreeNode(artist.getAttributeValue("name"));
                //Ordino alfabeticamente
                if (root.getChildCount()>0)
                    for (int i=0;i<root.getChildCount();i++){
                        if (artist.getAttributeValue("name").compareTo(root.getChildAt(i).toString())>=0)
                            root.insert(artistNode, i);
                    }
                else root.add(artistNode);
                
                List albums = artist.getChildren();
                Iterator iterator2 = albums.iterator(); 
                while(iterator2.hasNext()){
                    it.albe.jmusicman.Contatori.album++;
                    Element album = (Element)iterator2.next();
                    DefaultMutableTreeNode albumNode = new DefaultMutableTreeNode(album.getAttributeValue("name"));
                    String albumName = (!"".equals(albumNode.toString())) ? albumNode.toString() : "Album senza nome";
                    albumNode.setUserObject(new Album(artistNode.toString(),albumName));
                    artistNode.add(albumNode);
                    List tracks = album.getChildren();
                    Iterator iterator3 = tracks.iterator();
                    while (iterator3.hasNext()){
                        it.albe.jmusicman.Contatori.brani++;
                        Element track = (Element)iterator3.next();
                        Album alb = (Album)albumNode.getUserObject();
                        String number = (track.getChild("number")!=null) ? track.getChild("number").getText() : "";
                        String comment = (track.getChild("comment")!=null) ? track.getChild("comment").getText() : "";
                        Track traccia = new Track(artistNode.toString(),track.getChildText("name"),albumNode.toString(),track.getChildText("path"),number,comment);        
                        traccia.setDuration((track.getChild("duration")!=null) ? track.getChild("duration").getText() : "");
                        if (track.getChild("duration")!=null)
                            it.albe.jmusicman.Contatori.durataTotale += Integer.valueOf(traccia.getDuration());
                        alb.addTrack(traccia);
                        dataTableSuggest.addRow(new String[]{artistNode.toString(),albumNode.toString(),track.getChildText("name")});
                    }
                    
                 }
             }
            
            frame.jProgressBar1.setStringPainted(true);
            frame.jProgressBar1.setString("Pronto");
            File[] roots = File.listRoots();
            for (int i=0;i<roots.length;i++){
                File file = new File(roots[i]+"\\.is_audio_player");
                if (file.exists()){
                    JMusicMan.root = roots[i];
                    break;
                }
            }
            if (JMusicMan.root==null)
                frame.label.setText("Dispositivo non rilevato");
            else 
                frame.label.setText(JMusicMan.root.getAbsolutePath());
        }
        catch (com.sun.org.apache.xerces.internal.impl.io.MalformedByteSequenceException e){
            IO.err(frame, "Il file XML non è valido: "+e.toString());
        }
        catch (Exception e){
            IO.err(frame, "Errore nel caricare la libreria: "+e.toString());
        }
        frame.jTree1.expandRow(0);
        frame.jTree1.setSize(frame.getSize().width, frame.jTree1.getRowCount()*frame.jTree1.getRowHeight());
        frame.aggiornaContatori();  
        frame.jTree1.setModel(treeModel);
        frame.jTree1.updateUI();
        jSuggest = new JSuggest(dataTableSuggest);
    }
    /************************************************************************************************
     * Trova le tracce da copiare sul dispositivo confrontando le librerie sul PC e sul dispositivo.*
     * In particolare confronta il "path"                                                           *
     ************************************************************************************************/
    public static ArrayList<Track> findTracksToCopy(ArrayList<Element> disp, ArrayList<Element> locale){
        ArrayList<Track> lista = new ArrayList<Track>();
        String name, artist, album, path;
        String number;
        String comment;
        String duration;
        int sizel = locale.size();
        int sized = disp.size();
        int c;
        for (int i=0;i<sizel;i++){
            String nomeFile = locale.get(i).getChild("path").getText();
            for (c=0;c<sized;c++)
                if (disp.get(c).getChild("path").getText().equals(nomeFile))
                    break;
            /*se non è stato trovato */ 
            if (c==sized) {
                name = locale.get(i).getChild("name").getText();
                artist = locale.get(i).getParentElement().getParentElement().getAttributeValue("name");
                album = locale.get(i).getParentElement().getAttributeValue("name");
                path = locale.get(i).getChild("path").getText();
                number = (locale.get(i).getChild("number")!=null) ? locale.get(i).getChild("number").getText() : "";
                comment = (locale.get(i).getChild("comment")!=null) ? locale.get(i).getChild("comment").getText() : "";
                duration = (locale.get(i).getChild("duration")!=null) ? locale.get(i).getChild("duration").getText() : "";
                Track traccia = new Track(artist,name,album,path,number,comment);
                traccia.setDuration(duration);
                lista.add(traccia);
            }
        }
        return lista;
    }
    /*************************************************************************************
     * Trova le tracce che sono state eliminate dal PC e le elimina anche dal dispositivo*
     *************************************************************************************/
    public static int findTrackToDelete(ArrayList<Element> disp, ArrayList<Element> locale,String pathToDisp){
        
        String name, artist, album, path;
        int fileEliminati = 0;
        int sizel = locale.size();
        int sized = disp.size();
        int c;
        for (int i=0;i<sized;i++){
            String nomeFile = disp.get(i).getChild("path").getText();
            for (c=0;c<sizel;c++)
                if (locale.get(c).getChild("path").getText().equals(nomeFile))
                    break;
            /*se non è stato trovato */ 
            if (c==sizel) {
                name = disp.get(i).getChild("name").getText();
                artist = disp.get(i).getParentElement().getParentElement().getAttributeValue("name");
                album = disp.get(i).getParentElement().getAttributeValue("name");
                path = disp.get(i).getChild("path").getText();
                
                
                File file = new File(path);
                String fileName = file.getName();
                path = pathToDisp + "\\" + artist + "\\" + album + "\\" + fileName;
                file = new File(path);
                
                if (file.exists()){
                    file.delete();
                    fileEliminati++;
                }
                else 
                    return fileEliminati;
                path = file.getParent();
                file = new File(path);
                File[] files = file.listFiles();
                if (files.length==0)
                    file.delete();
                path = file.getParent();
                file = new File(path);
                files = file.listFiles();
                if (files.length==0)
                    file.delete();
            }
        }
        return fileEliminati;
    }
    /***************************************************************************************
     *  crea una directory con il nome dell'artista, una con l'album e ci mette la canzone**
     *  file: file originale con posizione originale     
     *  ritorna il file stesso se si è verificato un errore, così viene gestito dalla funzione chiamante
     * 
     **************************************************************************************/
    
    public static File organize(File file) throws it.albe.jmusicman.ExistingSongException, CannotReadException, TagException, ReadOnlyFileException, InvalidAudioFrameException{
        String directory_bak = JMusicMan.directory;
        if (JMusicMan.modalitaorganizza)
            JMusicMan.directory = JMusicMan.directory.substring(0, JMusicMan.directory.lastIndexOf('\\')+1);
        int i =0;
        boolean skippaCopiaFile = false;
        String title = "";
        String artist = "";
        String album = "";
        String track = "";
        String comment = "";
        String duration = "";
        AudioFile af = null;
        try {
            af = AudioFileIO.read(file);
        }
        catch (Exception e) {
            return file;
        }
        finally{
            JMusicMan.directory = directory_bak;
        }
        if (JMusicMan.modalitaorganizza)
            JMusicMan.directory = JMusicMan.directory.substring(0, JMusicMan.directory.lastIndexOf('\\')+1);
        Tag tag = af.getTag();
        title = (tag.getFirst(FieldKey.TITLE)!=null) ? tag.getFirst(FieldKey.TITLE) : "";
        artist = (tag.getFirst(FieldKey.ARTIST)!=null) ? tag.getFirst(FieldKey.ARTIST) : "";
        album = (tag.getFirst(FieldKey.ALBUM)!=null) ? tag.getFirst(FieldKey.ALBUM) : "";
        track = (tag.getFirst(FieldKey.TRACK)!=null) ? tag.getFirst(FieldKey.TRACK) : "";
        comment = (tag.getFirst(FieldKey.COMMENT)!=null) ? tag.getFirst(FieldKey.COMMENT) : "";
        duration = Integer.toString(af.getAudioHeader().getTrackLength());
        try{
            int trackint = Integer.parseInt(track);
            track = String.format("%02d", trackint);
        }
        catch(Exception e){
            
        }
        finally{
            JMusicMan.directory = directory_bak;
        }
   
        if (modalitaorganizza)
            JMusicMan.directory = JMusicMan.directory.substring(0, JMusicMan.directory.lastIndexOf('\\')+1);
        if (artist.equals("")) artist=  "Sconosciuto";
        if (title.equals("")) title= file.getName().substring(0,file.getName().length()-4);
        String fileTitle;
        String trackNo = "";
        fileTitle = "";
        if (track!="")
            trackNo = track +" - ";
        if (file.getAbsolutePath().endsWith(".mp3"))
            fileTitle = trackNo+checkFileName(title)+".mp3";
        else if (file.getAbsolutePath().endsWith(".flac"))
            fileTitle = trackNo+checkFileName(title)+".flac";
        File audioFile = new File(directory+checkFileName(artist)+"\\"+checkFileName(album));
        audioFile.mkdirs();
        audioFile = new File(directory+checkFileName(artist)+"\\"+checkFileName(album)+"\\"+fileTitle);
        if (audioFile.equals(file))
            skippaCopiaFile = true;
        try{
            if (!skippaCopiaFile){
                if (audioFile.createNewFile()){
                    audioFile.delete();
                    java.io.FileInputStream reader = new FileInputStream(file);
                    FileOutputStream writer = new FileOutputStream(audioFile);
                    byte[] bytes =  new byte[102400];
                    int numbytes;
                    while ((numbytes = reader.read(bytes))>0){
                        writer.write(bytes,0,numbytes);
                    }
                 reader.close();
                 file.delete();
                 writer.close();
                }
                else { //vuol dire che in  quella cartella esiste già un file con lo stesso nome
                    AudioFile audioOld = AudioFileIO.read(audioFile);
                    throw new ExistingSongException("Tag non univoci",audioOld);
                }
            }
                
        }
        catch(IOException e){
            return file;
        }
        finally{
            JMusicMan.directory = directory_bak;
        }
        if (JMusicMan.modalitaorganizza)
            JMusicMan.directory = JMusicMan.directory.substring(0, JMusicMan.directory.lastIndexOf('\\')+1);
        try{
            List artists =rootElement.getChild("library").getChildren();
            Iterator iterator = artists.iterator();
            Element artistElement = null;
            Element albumElement = null;
            Element trackElement;
            /*controllo se c'è l'artista */
            while (iterator.hasNext()){
                Element element = (Element)iterator.next();
                if (element.getAttributeValue("name").toLowerCase().equals(artist.toLowerCase())){
                    artistElement = element;
                    break;
                }
            }
            /* se è stato trovato...*/
            if (artistElement != null) {
                /*controllo se c'è l'album*/
                List albums = artistElement.getChildren();
                iterator = albums.iterator();
                while (iterator.hasNext()){
                   Element element = (Element)iterator.next();
                   if (element.getAttributeValue("name").toLowerCase().equals(album.toLowerCase()))
                       albumElement = element;
                }
                if (albumElement==null){
                    albumElement = new Element("album");
                    albumElement.setAttribute("name",album);
                    artistElement.addContent(albumElement);
                }
            }
            else {
                artistElement = new Element("artist");
                artistElement.setAttribute("name",artist);
                rootElement.getChild("library").addContent(artistElement);
                albumElement = new Element("album");
                albumElement.setAttribute("name",album);
                artistElement.addContent(albumElement);
            }
             
            trackElement = new Element("track");
            Element element = new Element("name");
            trackElement.addContent(element);
            element.setText(title);
            if (track!=""){
                element = new Element("number");
                element.setText(track);
                trackElement.addContent(element);
            }
            if (comment!=""){
                element = new Element("comment");
                element.setText(comment);
                trackElement.addContent(element);
            }
            if (duration!=""){
                element = new Element("duration");
                element.setText(duration);
                trackElement.addContent(element);
            }
            element = new Element("path");
            trackElement.addContent(element);
            element.setText(audioFile.getAbsolutePath());
            albumElement.addContent(trackElement);
            java.util.Date data = new java.util.Date();
            rootElement.getChild("lastedit").setText(Long.toString(data.getTime()));
            XMLOutputter xmlOutput = new XMLOutputter();
            Format format = Format.getPrettyFormat();
            format.setEncoding("ISO-8859-1");
            xmlOutput.setFormat(format);
            document.setRootElement(rootElement);
            xmlOutput.output(document,new FileWriter(directory+"JMusicManLibrary.xml"));
        }
        catch(IOException e){
            return file;
        }
        finally{
            JMusicMan.directory = directory_bak;
        }
        return null;
        //loadLibrary();
    }
    /********************************************************************************************************
     * Trova tutti i files mp3 presenti nella cartella /Music del PC e li organizza nelle apposite cartelle,*
     * ricreando pure il file XML. Risponde alla funzione "Aggiorna"   
     * @param noshow true se non deve mostrare la progressbar e gli errori nella libreria
     * @param async true se deve essere asincrono
     ********************************************************************************************************/
    public static void update(boolean noshow,boolean async){
        javax.swing.SwingWorker<Void,Void> sw = new javax.swing.SwingWorker<Void,Void>() {

            @Override
            protected Void doInBackground() throws Exception {
                rootElement = new Element("JMusicManLibrary");
                rootElement.setAttribute("version",Version.getVersion());
                Element element = new Element("lastedit");
                rootElement.addContent(element);
                element = new Element("player");
                element.setText(playerDir);
                rootElement.addContent(element);
                element = new Element("library");
                rootElement.addContent(element);
                document = new Document(rootElement);
                List<File> skippedTracks = new ArrayList<File>();
                String artist = "";
                String album = "";
                String title = "";
                String track = "";
                String duration = "";
                List<File> files = findFiles(directory);
                double progress = 0;
                int progressint = 0;
                double step = (double)100/(double)(files.size()+1);
                frame.jProgressBar1.setStringPainted(true);
                int i=0;
                for (File file :files){
                    if (!noshow){
                        frame.jProgressBar1.setString(file.getAbsolutePath());
                        progress += step; 
                        progressint = (int)progress;
                        frame.jProgressBar1.setValue(progressint);
                    }
                    try {
                        AudioFile audioFile = AudioFileIO.read(file);
                        Tag tag = audioFile.getTag();
                        artist = (tag.getFirst(FieldKey.ARTIST)!=null) ? tag.getFirst(FieldKey.ARTIST) : "Sconosciuto";
                        album = (tag.getFirst(FieldKey.ALBUM)!=null) ? tag.getFirst(FieldKey.ALBUM) : "";
                        title = (tag.getFirst(FieldKey.TITLE)!=null) ? tag.getFirst(FieldKey.TITLE) : file.getName();
                        track = (tag.getFirst(FieldKey.TRACK)!=null) ? tag.getFirst(FieldKey.TRACK) : "";
                        duration = Integer.toString(audioFile.getAudioHeader().getTrackLength());
                        if (artist.equals("")) artist=  "Sconosciuto";
                        if (title.equals("")) title= file.getName().substring(0,file.getName().length()-4);
                        if (!("".equals(artist))){
                            File filerr = organize(file);
                            if (filerr!=null)
                                skippedTracks.add(filerr);
                        }
                            
                    }
                    catch(ExistingSongException e){
                        AudioFile audioFile = e.audioFile; //file che si tenta di scrivere
                        AudioFile audioFileOld = AudioFileIO.read(file);  //file da spostare/rinominare
                        if (primaVolta){
                            skippedTracks.add(file);
                        } 
                        else if (audioFileOld.getAudioHeader().getTrackLength()==audioFile.getAudioHeader().getTrackLength()){
                            int resp = it.albe.utils.IO.confirm(frame, "Il file: \""+file.getAbsolutePath()+"\" contiene tag uguali \ne ha la stessa durata di un file già presente in libreria. \n"+
                                                                "Artista: " + artist + "\n" +
                                                                "Album: " + album + "\n" +
                                                                "Vuoi eliminarlo (Si) o creare una copia rinominata (No)? ");
                            if (resp==0){
                                file.delete();
                            }                               
                            else {
                                String name = file.getName();
                                name = name.substring(0,name.length()-4)+"(1)"+name.substring(name.length()-4,name.length());
                                File newf = new File(audioFile.getFile().getParent()+"\\"+name); 
                                java.io.FileInputStream reader = new FileInputStream(file);
                                FileOutputStream writer = new FileOutputStream(newf);
                                byte[] bytes =  new byte[102400];
                                int numbytes;
                                while ((numbytes = reader.read(bytes))>0){
                                    writer.write(bytes,0,numbytes);
                                }
                                reader.close();
                                file.delete();
                                writer.close();
                            }                               
                        } else { //la durata dei file è diversa e il tag DEVE essere modificato
                            skippedTracks.add(file);
                        }
                    }    
                    catch(Exception e){
                        skippedTracks.add(file);
                    }                                            
                }
                if ((skippedTracks.size()>0)&&(!noshow)){
                    it.albe.jmusicman.SkippedTracksFrame skippedFrame = new it.albe.jmusicman.SkippedTracksFrame(frame,skippedTracks);
                    skippedFrame.setVisible(true);
                }
                if (!noshow) 
                    frame.jProgressBar1.setString("Pronto");
                
                    
                loadLibrary();
                return null;
                }
        };
        sw.execute();
        if (!async)
            while(true){
                if (sw.isDone())
                    break;
            }
    }
    /******************************************************************************
     *    Aggiorna solo la traccia in questione, sapendo che sono stati modificati*
     *    solo le informazioni in base a editInfo                                 *
     ******************************************************************************/
    public static void updateTrack(Track newTrack,EditResult editInfo){
        Element artist,album;
        if (editInfo.artistModified()||editInfo.albumModified()||editInfo.titleModified()){
            artist = findOrCreateArtist(newTrack.getArtist());
            album = findOrCreateAlbum(artist,newTrack.getAlbum());
            Element trackElement = new Element("track");
            album.addContent(trackElement);
            Element element = new Element("name");
            element.setText(newTrack.getName());
            trackElement.addContent(element);
            element = new Element("number");
            element.setText(newTrack.getNumber());
            trackElement.addContent(element);
            element = new Element("path");
            element.setText(newTrack.getPath());
            trackElement.addContent(element);
            while (deleteOrphansFiles())
                ;
            while (deleteOrphansElements())
                ;
            try {
                java.util.Date data = new java.util.Date();
                rootElement.getChild("lastedit").setText(Long.toString(data.getTime()));
                XMLOutputter xmlOutput = new XMLOutputter();
                Format format = Format.getPrettyFormat();
                format.setEncoding("UTF-8");
                xmlOutput.setFormat(format);
                document.setRootElement(rootElement);
                if (!JMusicMan.modalitaorganizza)
                    xmlOutput.output(document,new FileWriter(directory+"JMusicManLibrary.xml"));
                int  c=0;
                loadLibrary();
            }
            catch (Exception e){
                IO.err(frame, "Errore nell'aggiornare la traccia:"+e.toString());
            }
            
        }
        
    }
    /***************************************************************
     * Copia le tracce sul dispositivo                             *
     ***************************************************************/
    public static void copyTracks(ArrayList<Track> tracks, String directory){
        int size = tracks.size();
        int progress = 0;
        int step = 100/size;
        frame.jProgressBar1.setString("Copio tracce...");
        for (int i=0;i<size;i++){
            String path =tracks.get(i).getPath();
            frame.jProgressBar1.setStringPainted(true);
            frame.jProgressBar1.setString(path);
            File input = new File(path);
            int at = path.indexOf("Music");
            at += 5;
            path = path.substring(at);
            String newFilePath = directory;
            newFilePath += path;
            File output = new File (newFilePath);
            path = output.getParent();
            File filePath = new File(path);
            filePath.mkdirs();
            try{
                output.createNewFile();
                FileInputStream reader = new FileInputStream(input);
                FileOutputStream writer = new FileOutputStream(output);
                byte[] bytes =  new byte[1024];
                int numbytes;
                while ((numbytes = reader.read(bytes))>0){
                    writer.write(bytes,0,numbytes);
                }
                reader.close();
                writer.close();
            }
            catch(Exception e){
                IO.err(frame, "Errore nel copiare i dati sul dispositivo:"+e.toString());
        
            }
            progress += step;
            frame.jProgressBar1.setValue(progress);
            frame.update(frame.getGraphics());
        }
        //frame.label.setText("Pronto");
    }
    /**************************************************
     * Crea un documento XML vuoto                    *
     **************************************************/
    public static void createEmptyDocument(File file){
        try{
            primaVolta = true;
            Element root = new Element("JMusicManLibrary");
            root.setAttribute("version","0.1");
            Element element = new Element("lastedit");
            element.setText("0");
            root.addContent(element);
            element = new Element("player");
            element.setText(playerDir);
            root.addContent(element);
            element = new Element("library");
            root.addContent(element);
            Document document = new Document(root);
            XMLOutputter xmlOutput = new XMLOutputter();
            Format format = Format.getPrettyFormat();
            format.setEncoding("UTF-8");
            xmlOutput.setFormat(format);
            xmlOutput.output(document,new FileWriter(file.getAbsolutePath()));
        }
        
        catch(Exception e){
            IO.err(frame, "Errore nello scrivere i dati sul disco:"+e.toString());
        
        }
        
    }
    
    public static String checkFileName(String fileName){
        /*if (fileName.contains("\\")||fileName.contains("/")||fileName.contains(":")||fileName.contains("*")
                ||fileName.contains("?")||fileName.contains("\"")||fileName.contains("<")||fileName.contains(">")
                ||fileName.contains("|")){*/
            char[] array = fileName.toCharArray();
            char c;
            for(int i=0;i<array.length;i++){
                c = array[i];
                if (c=='\\'||c=='/'||c==':'||c=='*'||c=='?'||c=='\"'||c=='<'||c=='>'||c=='|'||c=='\t')
                    array[i] = '_';
            }
            String checked = String.copyValueOf(array);
            return checked;
        }
    
    /**********************************************************************************
     *   Cancella tutti le track che non esistono sul disco,                          *
     * Ritorna true se è stato eliminato un elemento                                  *
     * ********************************************************************************/
    public static boolean deleteOrphansFiles(){
        ElementFilter filter = new ElementFilter();
        Iterator<Element> iterator = rootElement.getDescendants(filter);
        Element element;
        int c;
        boolean deleted = false;
        try{
            /*trovo tracce che non esistono */
            while(iterator.hasNext()){
            element = iterator.next();
            if (element.getName().equals("track"))
                if (element.getChild("path")!=null)
                    if (!(new File(element.getChild("path").getText())).exists()){   //se il file non esiste...
                        element.detach();
                        return true;
                }
            }
            
        }
        catch(Exception e){
            IO.err(frame, "Errore nell'eliminare gli elementi orfani della libreria:"+ e.toString());
        }
        return false;
    }
    /**********************************************************************************
     *   Elimina gli eventuali elementi orfani.                                       *
     *   Ritorna true se è stato eliminato un elemento                                 *
     * ********************************************************************************/
    public static boolean deleteOrphansElements(){
        ElementFilter filter = new ElementFilter();
        Iterator<Element> iterator = rootElement.getChild("library").getDescendants(filter);
        Element element;
        int c;
        boolean deleted = false;
        try{
            /*trovo elementi che non hanno child */
            iterator = rootElement.getChild("library").getDescendants(filter);
            while(iterator.hasNext()){
                element = iterator.next();
                if (element.getAttributeValue("name")!=null)
                    if (element.getAttributeValue("name").equals("Vivo da Re"))
                        c=0;
                if (element.getChildren().isEmpty())
                    if (element.getText()!=null)
                        if (element.getText().replaceAll("\n ", "").trim().equals("")){
                            element.detach();
                            return true;
                        }
                    
            }
        }
        catch (Exception e){
            IO.err(frame, "Errore nell'eliminare gli elementi orfani della libreria:"+ e.toString());
        }
        return false;
    }
    /**********************************************************************************************************
     * ritorna le directory che non sono vuote perché hanno file diversi dai file supportati da JMusicMan
     *********************************************************************************************************/
    public static ArrayList<File> deleteEmptyDirectories(String dir){
        boolean onlyAudio = false;
        boolean Other = false;
        boolean isdir = false;
        ArrayList<String> list = new ArrayList<String>();
        File dirFile = new File (dir);
        File[] filesAndDirectories = null;
        ArrayList<File> files = new ArrayList<File>();
        File[] controllo;
        if (dirFile.isDirectory()){
            filesAndDirectories = dirFile.listFiles();
            for (int i=0;i<filesAndDirectories.length;i++){
                if (filesAndDirectories[i].isDirectory()){
                    if (filesAndDirectories[i].listFiles().length==0)
                        filesAndDirectories[i].delete();  //elimino la cartella vuota
                    else
                        files.addAll(deleteEmptyDirectories(filesAndDirectories[i].getAbsolutePath()));
                }
                else {
                    if ((filesAndDirectories[i].getAbsolutePath().toUpperCase().endsWith(".MP3"))||
                        (filesAndDirectories[i].getAbsolutePath().toUpperCase().endsWith(".FLAC"))||
                        (filesAndDirectories[i].getAbsolutePath().toUpperCase().endsWith(".XML"))||
                        (filesAndDirectories[i].getAbsolutePath().toUpperCase().endsWith(".INI")))
                        continue;
                    
                    Other = true;
                    
                }
                        
            }
            if ((Other)){
                //controllo che non ci siano file audio
                controllo = dirFile.listFiles();
                for (int k=0; k< controllo.length;k++){
                    if ((filesAndDirectories[k].getAbsolutePath().toUpperCase().endsWith(".MP3"))||
                        (filesAndDirectories[k].getAbsolutePath().toUpperCase().endsWith(".FLAC")))
                        onlyAudio = true;
                    if (filesAndDirectories[k].isDirectory())
                        isdir = true;
                }
                if ((!onlyAudio)&&(!isdir))
                    files.add(dirFile);
                onlyAudio=false;
                Other=false;
            }              
        }
        return files;
    }
    /************************************************************************
     * Trova ricorsivamente tutti i file mp3 contenuti nella directory dir  *
     ***********************************************************************/
    public static ArrayList<File> findFiles(String dir){
        File dirFile = new File (dir);
        File[] filesAndDirectories = null;
        ArrayList<File> files = new ArrayList<File>();
        if (dirFile.isDirectory()){
            filesAndDirectories = dirFile.listFiles();
            for (int i=0;i<filesAndDirectories.length;i++){
                if (filesAndDirectories[i].isDirectory()){
                        files.addAll(findFiles(filesAndDirectories[i].getAbsolutePath()));
                }
                else {
                    if (filesAndDirectories[i].getAbsolutePath().toUpperCase().endsWith(".MP3")||
                        filesAndDirectories[i].getAbsolutePath().toUpperCase().endsWith(".FLAC"))
                        files.add(filesAndDirectories[i]);
                }
            }
        }
        return files;
    }
    /****************************************************************
     * trova l'artista nel documento XML e, se non esiste, lo crea  *
     ****************************************************************/
    public static Element findOrCreateArtist(String artistName){
        Element artist;
        List artists = rootElement.getChild("library").getChildren();
        Iterator<Element> iterator = artists.iterator();
        while (iterator.hasNext()){
            artist = iterator.next();
            if (artist.getAttributeValue("name").equals(artistName))
                return artist;
        }
        artist = new Element("artist");
        artist.setAttribute("name",artistName);
        rootElement.getChild("library").addContent(artist);
        return artist;
    }
    /***************************************************************************
     * trova l'album dell'artista nel documento XML e, se non esiste, lo crea  *
     ***************************************************************************/
    public static Element findOrCreateAlbum(Element artist,String albumName){
        Element album;
        List albums = artist.getChildren();
        Iterator<Element> iterator = albums.iterator();
        while(iterator.hasNext()){
            album = iterator.next();
            if (album.getAttributeValue("name").equals(albumName))
                return album;
        }
        album = new Element("album");
        album.setAttribute("name", albumName);
        return album;
    }
}
