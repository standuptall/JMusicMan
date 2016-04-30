/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package it.albe.JMusicMan;
import it.albe.utils.IO;
import java.io.*;
import java.util.Iterator;
import java.util.List;
import org.jdom.*;
import org.jdom.input.SAXBuilder;
import org.jdom.output.*;
import javax.swing.tree.*;
import com.mpatric.mp3agic.*;
import java.util.ArrayList;
import org.jdom.filter.ElementFilter;
import org.jaudiotagger.audio.AudioFile;
import org.jaudiotagger.audio.AudioFileIO;
import org.jaudiotagger.tag.FieldKey;
import org.jaudiotagger.tag.Tag;

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
    public static void main(String[] args) {
        playerDir = "";
        root = null;
        frame = new Frame();
        frame.setVisible(true);
        loadLibrary();
    }
    /******************************************************************
     * Carica la libreria XML e crea l'albero directory               *
     ******************************************************************/
    public static void loadLibrary(){
        SAXBuilder builder = new SAXBuilder();    
        javax.swing.tree.DefaultMutableTreeNode treeNode1 = new javax.swing.tree.DefaultMutableTreeNode("JMusicManLibrary");
        treeNode1.removeAllChildren();
        frame.jTree1.setModel(new javax.swing.tree.DefaultTreeModel(treeNode1));
        
        DefaultMutableTreeNode root = (DefaultMutableTreeNode)frame.jTree1.getModel().getRoot();
        try{
            File libraryFile = new File (directory+"JMusicManLibrary.xml");
            if (libraryFile.exists())
                    document = builder.build(libraryFile);
                else if (IO.confirm(frame, "I brani verranno ordinati nella cartella Musica come:\n"
                        + "[artista]/[album]/[numero traccia] - [titolo].mp3. Procedere?")>0)
                        System.exit(0);
            else 
                update();
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
                    Element album = (Element)iterator2.next();
                    DefaultMutableTreeNode albumNode = new DefaultMutableTreeNode(album.getAttributeValue("name"));
                    String albumName = (albumNode.toString()!="") ? albumNode.toString() : "Album senza nome";
                    albumNode.setUserObject(new Album(artistNode.toString(),albumName));
                    artistNode.add(albumNode);
                    List tracks = album.getChildren();
                    Iterator iterator3 = tracks.iterator();
                    while (iterator3.hasNext()){
                        Element track = (Element)iterator3.next();
                        Album alb = (Album)albumNode.getUserObject();
                        String number = (track.getChild("number")!=null) ? track.getChild("number").getText() : "";
                        alb.addTrack(new Track(artistNode.toString(),track.getChildText("name"),albumNode.toString(),track.getChildText("path"),number));
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
        catch (Exception e){
            IO.err(frame, "Errore nel caricare la libreria: "+e.toString());
        }

        frame.jTree1.expandRow(0);
        frame.jTree1.setSize(frame.getSize().width, frame.jTree1.getRowCount()*frame.jTree1.getRowHeight());
    }
    /************************************************************************************************
     * Trova le tracce da copiare sul dispositivo confrontando le librerie sul PC e sul dispositivo.*
     * In particolare confronta il "path"                                                           *
     ************************************************************************************************/
    public static ArrayList<Track> findTracksToCopy(ArrayList<Element> disp, ArrayList<Element> locale){
        ArrayList<Track> lista = new ArrayList<Track>();
        String name, artist, album, path;
        String number;
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
                lista.add(new Track(artist,name,album,path,number));
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
     *  file: file originale con posizione originale                                       *
     **************************************************************************************/
    
    public static void organize(File file) throws it.albe.JMusicMan.EmptyTagException{
     
        String title = "";
        String artist = "";
        String album = "";
        String track = "";
        AudioFile af = null;
        try {
            af = AudioFileIO.read(file);
        }
        catch (Exception e) {
            System.out.print("file skippato: "+file.getAbsolutePath());
            return;
        }
        Tag tag = af.getTag();
        title = (tag.getFirst(FieldKey.TITLE)!=null) ? tag.getFirst(FieldKey.TITLE) : "";
        artist = (tag.getFirst(FieldKey.ARTIST)!=null) ? tag.getFirst(FieldKey.ARTIST) : "";
        album = (tag.getFirst(FieldKey.ALBUM)!=null) ? tag.getFirst(FieldKey.ALBUM) : "";
        track = (tag.getFirst(FieldKey.TRACK)!=null) ? tag.getFirst(FieldKey.TRACK) : "";
        String fileTitle;
        String trackNo = "";
        fileTitle = "";
        if (track!="")
            trackNo = track +" - ";
        if (file.getAbsolutePath().endsWith(".mp3"))
            fileTitle = trackNo+checkFileName(title)+".mp3";
        else if (file.getAbsolutePath().endsWith(".flac"))
            fileTitle = trackNo+checkFileName(title)+".flac";
        if (fileTitle.equals(""))
            try {
                throw new it.albe.JMusicMan.EmptyTagException("Tag vuoti");
            }
            catch(it.albe.JMusicMan.EmptyTagException e){
                IO.err(frame, "Errore nello scrivere i dati sul disco: "+e.toString());
                return;
            }
        File audioFile = new File(directory+checkFileName(artist)+"\\"+checkFileName(album));
        if (audioFile.equals(file))
            return;
        audioFile.mkdirs();
        audioFile = new File(directory+checkFileName(artist)+"\\"+checkFileName(album)+"\\"+fileTitle);
        try{
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
            else {
                
            }
        }
        catch(IOException e){
            IO.err(frame, "Errore nello scrivere i dati sul disco: "+e.toString());
        }
        
        try{
            List artists =rootElement.getChild("library").getChildren();
            Iterator iterator = artists.iterator();
            Element artistElement = null;
            Element albumElement = null;
            Element trackElement;
            /*controllo se c'è l'artista */
            while (iterator.hasNext()){
                Element element = (Element)iterator.next();
                if (element.getAttributeValue("name").equals(artist)){
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
                   if (element.getAttributeValue("name").equals(album))
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
            element = new Element("path");
            trackElement.addContent(element);
            element.setText(audioFile.getAbsolutePath());
            albumElement.addContent(trackElement);
            java.util.Date data = new java.util.Date();
            rootElement.getChild("lastedit").setText(Long.toString(data.getTime()));
            XMLOutputter xmlOutput = new XMLOutputter();
            Format format = Format.getPrettyFormat();
            format.setEncoding("UTF-8");
            xmlOutput.setFormat(format);
            document.setRootElement(rootElement);
            xmlOutput.output(document,new FileWriter(directory+"JMusicManLibrary.xml"));
        }
        catch(IOException e){
            IO.err(frame, "Errore nello scrivere i dati sul disco: "+e.toString());
        }
        //loadLibrary();
    }
    /********************************************************************************************************
     * Trova tutti i files mp3 presenti nella cartella /Music del PC e li organizza nelle apposite cartelle,*
     * ricreando pure il file XML. Risponde alla funzione "Aggiorna"                                                                           *
     ********************************************************************************************************/
    public static void update(){
        javax.swing.SwingWorker<Void,Void> sw = new javax.swing.SwingWorker<Void,Void>() {

            @Override
            protected Void doInBackground() throws Exception {
                rootElement = new Element("JMusicManLibrary");
                rootElement.setAttribute("version","0.1");
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
             //   try {
                ArrayList<File> files = findFiles(directory);
                double progress = 0;
                int progressint = 0;
                double step = (double)100/(double)(files.size()+1);
                frame.jProgressBar1.setStringPainted(true);
                int i=0;
                for (File file :files){
                    if (i==24)
                        i=24;
                    i++;
                    
                    frame.jProgressBar1.setString(file.getAbsolutePath());
                    progress += step; 
                    progressint = (int)progress;
                    frame.jProgressBar1.setValue(progressint);
                    try {
                        AudioFile audioFile = AudioFileIO.read(file);
                        Tag tag = audioFile.getTag();
                        artist = (tag.getFirst(FieldKey.ARTIST)!=null) ? tag.getFirst(FieldKey.ARTIST) : "Sconosciuto";
                        album = (tag.getFirst(FieldKey.ALBUM)!=null) ? tag.getFirst(FieldKey.ALBUM) : "";
                        title = (tag.getFirst(FieldKey.TITLE)!=null) ? tag.getFirst(FieldKey.TITLE) : file.getName();
                        track = (tag.getFirst(FieldKey.TRACK)!=null) ? tag.getFirst(FieldKey.TRACK) : "";
                        if (!("".equals(artist)))
                            organize(file);
                    }    
                    catch(Exception e){
                        skippedTracks.add(file);
                    }
                        
                }
                if (skippedTracks.size()>0){
                    it.albe.jmusicman.SkippedTracksFrame skippedFrame = new it.albe.jmusicman.SkippedTracksFrame(frame,skippedTracks);
                    skippedFrame.setVisible(true);
                }
                frame.jProgressBar1.setString("Pronto");
                loadLibrary();
                return null;
                }
        };
        sw.execute();
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
    /************************************************************************
     * Trova ricorsivamente tutti i file mp3 contenuti nella directory dir  *
     ***********************************************************************/
    public static ArrayList<File> findFiles(String dir){
        File dirFile = new File (dir);
        File[] filesAndDirectories = null;
        ArrayList<File> files = new ArrayList<File>();
        int count = 0;
        if (dirFile.isDirectory()){
            filesAndDirectories = dirFile.listFiles();
            for (int i=0;i<filesAndDirectories.length;i++){
                if (filesAndDirectories[i].isDirectory()){
                        files.addAll(findFiles(filesAndDirectories[i].getAbsolutePath()));
                }
                else {
                    if (filesAndDirectories[i].getAbsolutePath().toUpperCase().endsWith("MP3")||
                        filesAndDirectories[i].getAbsolutePath().toUpperCase().endsWith("FLAC"))
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
