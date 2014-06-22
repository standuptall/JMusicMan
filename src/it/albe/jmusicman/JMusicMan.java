/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package it.albe.jmusicman;
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
import javax.swing.UIManager;
/**
 *
 * @author Alberto
 * 
 * Classe principale che contiene i metodi statici principali
 * 
 */
public class JMusicMan {
    public static Element rootElement;  //rootElement del documento XML
    public static String directory = "C:\\Users\\Alberto\\Music\\";  
    public static Document document;
    public static File root = null;      //root directory del dispositivo
    public static long lastedit;         //timestamp dell'ultima modifica del file xml
    public static String playerDir = ""; //directory del player

    public static Frame frame = new Frame();
    public static void main(String[] args) {
        frame.setVisible(true);
        loadLibrary();
    }
    /******************************************************************
     * Carica la libreria XML e crea l'albero directory               *
     ******************************************************************/
    public static void loadLibrary(){
        SAXBuilder builder = new SAXBuilder();    
        javax.swing.tree.DefaultMutableTreeNode treeNode1 = new javax.swing.tree.DefaultMutableTreeNode("JMusicManLibrary");
        frame.jTree1.setModel(new javax.swing.tree.DefaultTreeModel(treeNode1));
        DefaultMutableTreeNode root = (DefaultMutableTreeNode)frame.jTree1.getModel().getRoot();
        try{
            document = builder.build(new File (directory+"JMusicManLibrary.xml"));
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
                root.add(artistNode);
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
                       
                        alb.addTrack(new Track(artistNode.toString(),track.getChildText("name"),albumNode.toString(),track.getChildText("path")));
                    }
                    
                 }
             }
        }
        catch (Exception e){
            
        }
        
        frame.jTree1.expandRow(0);
    }
    /************************************************************************************************
     * Trova le tracce da copiare sul dispositivo confrontando le librerie sul PC e sul dispositivo.*
     * In particolare confronta il "path"                                                           *
     ************************************************************************************************/
    public static ArrayList<Track> findTracksToCopy(ArrayList<Element> disp, ArrayList<Element> locale){
        ArrayList<Track> lista = new ArrayList<Track>();
        String name, artist, album, path;
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
                lista.add(new Track(artist,name,album,path));
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
     **************************************************************************************/
    
    public static void organize(File file,String artist,String album, String title, int track){
        File dir = new File(directory);
        String fileTitle;
        if (track!=0) 
            fileTitle = String.format("%02d",track) +" - "+title+".mp3";
        
        else 
            fileTitle = title+".mp3";
        File mp3file = new File(directory+artist+"\\"+album);
        mp3file.mkdirs();
        mp3file = new File(directory+artist+"\\"+album+"\\"+fileTitle);
        try{
            if (mp3file.createNewFile()){
                mp3file.delete();
                java.io.FileInputStream reader = new FileInputStream(file);
                FileOutputStream writer = new FileOutputStream(mp3file);
                byte[] bytes =  new byte[1024];
                int numbytes;
                while ((numbytes = reader.read(bytes))>0){
                    writer.write(bytes,0,numbytes);
                }
             reader.close();
             file.delete();
             writer.close();
            }
        }
        catch(IOException e){
            
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
             if (track>0){
                 element = new Element("number");
                 element.setText(Integer.toString(track));
                 trackElement.addContent(element);
             }
             element = new Element("path");
             trackElement.addContent(element);
             element.setText(mp3file.getAbsolutePath());
            albumElement.addContent(trackElement);
            java.util.Date data = new java.util.Date();
            rootElement.getChild("lastedit").setText(Long.toString(data.getTime()));
            XMLOutputter xmlOutput = new XMLOutputter();
            xmlOutput.setFormat(Format.getPrettyFormat());
            xmlOutput.output(document,new FileWriter(directory+"JMusicManLibrary.xml"));
        }
        catch(IOException e){
            
        }
        
    }
    /********************************************************************************************************
     * Trova tutti i files mp3 presenti nella cartella /Music del PC e li organizza nelle apposite cartelle,*
     * ricreando pure il file XML                                                                           *
     ********************************************************************************************************/
    public static void update(){
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
        String artist = "";
        String album = "";
        String title = "";
        int track = 0;
        try {
            ArrayList<File> files = findFiles(directory);
            int progress = 0;
            int step = 100/(files.size()+1);
            
            frame.label.setText("Sto elaborando...");
            for (File file :files){
                frame.jProgressBar1.setStringPainted(true);
                frame.jProgressBar1.setString(file.getAbsolutePath());
                frame.update(frame.getGraphics());
                progress += step; 
                frame.jProgressBar1.setValue(progress);
                if (!file.isDirectory()&&file.getCanonicalPath().endsWith("mp3")){
                    Mp3File mp3file = new Mp3File(file.getAbsolutePath());
                    if (mp3file.hasId3v2Tag()){
                        ID3v2 id3v2Tag = mp3file.getId3v2Tag();
                        artist = (id3v2Tag.getArtist()!=null) ? id3v2Tag.getArtist() : "Sconosciuto";
                        album = (id3v2Tag.getAlbum()!=null) ? id3v2Tag.getAlbum() : "";
                        title = (id3v2Tag.getTitle()!=null) ? id3v2Tag.getTitle() : file.getName();
                        track = (id3v2Tag.getTrack()!=null) ? Integer.valueOf(id3v2Tag.getTrack()) : 0;
                        if (!"".equals(artist))
                                organize(file,artist,album,title,track);
                    }
                        
                    
                }
            }
        frame.label.setText("Pronto");
        }
        catch(IOException e){
            e.printStackTrace();
        }
        catch(Exception e){
            IO.err(frame, "errore: "+e.getMessage()+artist);
        }
        loadLibrary();
    }
    /******************************************************************************
     *    Aggiorna solo la traccia in questione, sapendo che sono stati modificati*
     *    solo le informazioni in base a editInfo                                 *
     ******************************************************************************/
    public static void updateTrack(Track track,EditResult editInfo){
        Element artist,album;
        if (editInfo.artistModified()){
            artist = findOrCreateArtist(track.getArtist());
            album = findOrCreateAlbum(artist,track.getAlbum());
            Element trackElement = new Element("track");
            Element element = new Element("name");
            element.setText(track.getName());
            element = new Element("number");
            element.setText(Integer.toString(track.getNumber()));
            element = new Element("path");
            element.setText(track.getPath());
            album.addContent(element);                   
        }
        loadLibrary();
    }
    /***************************************************************
     * Copia le tracce sul dispositivo                             *
     ***************************************************************/
    public static void copyTracks(ArrayList<Track> tracks, String directory){
        int size = tracks.size();
        int progress = 0;
        int step = 100/size;
        frame.label.setText("Copio tracce...");
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
                
            }
            progress += step;
            frame.jProgressBar1.setValue(progress);
            frame.update(frame.getGraphics());
        }
        frame.label.setText("Pronto");
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
            xmlOutput.setFormat(Format.getPrettyFormat());
            xmlOutput.output(document,new FileWriter(file.getAbsolutePath()));
        }
        
        catch(Exception e){
            
        }
        
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
                    if (filesAndDirectories[i].getAbsolutePath().endsWith("mp3"))
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
