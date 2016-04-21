/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package it.albe.JMusicMan;
import java.io.*;
import java.io.IOException;
import java.util.Dictionary;
import java.util.Map;
import java.util.HashMap;

/*
 * 
 * TODO: devo implementare la creazione di vorbis_comment per file che non ce l'hanno
 */
public class FlacReader
{
    public static String vendor_string;
    public static int user_comment_list_length;
    private String filepath;
    /*
    private static class _metadata {
        public static class _streaminfo
        {
            public boolean presente;
            public int code = 0x0;
            public boolean ultimo;
        }
        public static class _padding
        {
            public boolean presente;
            public int code = 0x1;
            public boolean ultimo;
        }
        public static class _application
        {
            public boolean presente;
            public int code = 0x2;
            public boolean ultimo;
        }
        public static class _seektable
        {
            public boolean presente;
            public  int code = 0x3;
            public boolean ultimo;
        }
        public static class _vorbis_comment
        {
            public boolean presente;
            public int code = 0x4;
            public boolean ultimo;
        }
        public static class _cuesheet
        {
            public boolean presente;
            public int code = 0x5;
            public boolean ultimo;
        }
        public static class _picture
        {
            public boolean presente;
            public int code = 0x6;
            public boolean ultimo;
        }

        public _streaminfo streaminfo;
        public _padding padding;
        public _application application;
        public _seektable seektable;
        public _vorbis_comment vorbis_comment;
        public _cuesheet cuesheet;
        public _picture picture;
        public int ending_metadata_code = 0xF0;
        public int NUM_METADATA = 7;
        
        public _metadata()
        {
            streaminfo.presente = false;
            streaminfo.ultimo = false;
            padding.presente = false;
            padding.ultimo = false;
            application.presente = false;
            application.ultimo = false;
            seektable.presente = false;
            seektable.ultimo = false;
            vorbis_comment.presente = false;
            vorbis_comment.ultimo = false;
            cuesheet.presente = false;
            cuesheet.ultimo = false;
            picture.presente = false;
            picture.ultimo = false;
        }
        public void setMetadata(int code)
        {
            switch ((code<<1)>>1)  //tolgo il bit più significativo
            {
                case _streaminfo.code: streaminfo.presente = true; break;
                case _padding.code: padding.presente = true; break;
                case _application.code: application.presente = true; break;
                case _seektable.code: seektable.presente = true; break;
                case _vorbis_comment.code: vorbis_comment.presente = true; break;
                case _cuesheet.code: cuesheet.presente = true; break;
                case _picture.code: picture.presente = true; break;
            }
        }
        public int getFlag(int flag)  //devo controllare se è l'ultimo, così setto il bit più significativo a 1
        {
            flag = ((flag << 1) >> 1); //tolgo il bit più significtivo
            if (picture.presente)
                picture.ultimo = true;
            else if (cuesheet.presente)
                cuesheet.ultimo = true;
            else if (vorbis_comment.presente)
                vorbis_comment.ultimo = true;
            else if (seektable.presente)
                seektable.ultimo = true;
            else if (application.presente)
                application.ultimo = true;
            else if (padding.presente)
                padding.ultimo = true;
            else if (streaminfo.presente)
                streaminfo.ultimo = true;
            switch (flag)
            {
                case _streaminfo.code: if (streaminfo.presente) flag += ending_metadata_code; break;
                case _padding.code: if (streaminfo.presente) flag += ending_metadata_code; break;
                case _application.code: if (streaminfo.presente) flag += ending_metadata_code; break;
                case _seektable.code: if (streaminfo.presente) flag += ending_metadata_code; break;
                case _vorbis_comment.code: if (streaminfo.presente) flag += ending_metadata_code; break;
                case _cuesheet.code: if (streaminfo.presente) flag += ending_metadata_code; break;
                case _picture.code: if (streaminfo.presente) flag += ending_metadata_code; break;
            }
            return flag;
        }
    } ;
    _metadata metadata;
    */
    private int metadata_comments_length;
    public Map<String,String> comments;
    public Map<String, String> metadataDict;
    //public System.Data.DataTable metadataInfo;
    public FlacReader(String filepath)
    {
        metadata_comments_length = 0;
        //metadata = new _metadata();  //oggetto che memorizza se sono presenti o no metadata specifici
        comments = new HashMap<String, String>();
        metadataDict = new HashMap<String, String>();
        //metadataInfo = new System.Data.DataTable();
        //metadataInfo.Columns.Add("Metadata");
        //metadataInfo.Columns.Add("Code");
        //metadataInfo.Columns.Add("Offset");
        //metadataInfo.Columns.Add("Length");
        this.filepath = filepath;
        user_comment_list_length = 0;
        BufferedInputStream stream;
        try {
            stream = new BufferedInputStream(new FileInputStream(filepath));
            if (stream == null)
                throw new FileNotFoundException("Il file non esiste");
             byte[] array = {0,0,0,0};
            stream.read(array,0,4);
            if (!((array[0]==0x66)&&(array[1]==0x4C)&&(array[2]==0x61)&&(array[3]==0x43)))   //fLaC
                throw new it.albe.FlacReader.NotAFlacException("Il file non è un file flac!");
            byte flag;
            do 
            {
                stream.read(array,0,1);  //mi sposto di 1 byte
                flag = array[0];
                //metadata.setMetadata(flag);
                stream.read(array, 0, 3);  //leggo tre byte per la lunghezza del metadata
                int metadata_length = array[0] * 65536 + array[1] * 256 + array[2];
                String metaName;
                switch ((flag<<1)>>1)
                {
                    case 0: metaName = "STREAMINFO"; break;
                    case 1: metaName = "PADDING"; break;
                    case 2: metaName = "APPLICATION"; break;
                    case 3: metaName = "SEEKTABLE"; break;
                    case 4: metaName = "VORBIS_COMMENT"; break;
                    case 5: metaName = "CUESHEET"; break;
                    case 6: metaName = "PICTURE"; break;
                    default: metaName = "UNKNOWN"; break;
                }
                //System.Data.DataRow row = metadataInfo.NewRow();
                //row["Metadata"] = metaName;
                //row["Code"] = String.Format("{0,8}", Convert.ToString(flag, 2)).Replace(" ", "0");
                //row["Offset"] = Convert.ToString(stream.Position-4,16).ToUpper();
                //row["Length"] = Convert.ToString(metadata_length, 16).ToUpper();
                //metadataInfo.Rows.Add(row);
                if (flag == 4 || flag == 132)   //se è un vorbis comment cioè 00000100 oppure 10000100
                {
                    metadata_comments_length = metadata_length;
                    caricaCommenti(stream);
                }
                else
                    stream.skip(metadata_length); //mi sposto della lunghezza del metadata
            } while (!((flag>>7)==0x1)); //se il bit più significativo è uguale a uno vuol dire ch enon ci sono più metadata
            stream.close();
        }
        catch (FileNotFoundException ex){
            
        }
        catch (IOException ex){
            
        }
        catch (it.albe.FlacReader.NotAFlacException ex){
            
        }
        
       
    }
    public void setVendor(String vendorName)
    {
        int vendor_string_length_old = vendor_string.length();  
        metadata_comments_length -= vendor_string_length_old;//tolgo la lunghezza iniziale
        vendor_string = vendorName;
        metadata_comments_length += vendor_string.length();  //e metto la nuova lunghezza del vendor string
    }
    public String getVendor()
    {
        return vendor_string;
    }
    public void addComment(String field, String value)
    {
        if (comments.get(field.toUpperCase()) != null)   //se il commento già esiste
        {
            String val = comments.get(field.toUpperCase());
            metadata_comments_length -= (field.length() + val.length() + 1);   //tolgo la lunghezza originaria
            metadata_comments_length -= 4;
        }
        else 
        {
            comments.put(field.toUpperCase(),value);
            user_comment_list_length++;
            metadata_comments_length += 4; //aggiungo 4 byte per memorizzare la lunghezza del comment sul file
            metadata_comments_length += field.length() + value.length() + 1;  //aggiungo la lunghezza del commento più il simbolo uguale
        }


    }
    public String getComment(String key)
    {
        if (comments.get(key.toUpperCase())!=null)
        {
            return comments.get(key.toUpperCase());
        }
        else
        {
            return "";
        }
    }
    public void writeAll()  //file esistente quindi riverso il contenuto su un file _temp
    {
        BufferedOutputStream streamWrite;
        BufferedInputStream stream;
        try {
            stream = new BufferedInputStream(new FileInputStream(filepath));
            streamWrite =  new BufferedOutputStream(new FileOutputStream(filepath));
            byte[] array = { 0, 0, 0, 0 };
            stream.read(array, 0, 4);
            streamWrite.write(array, 0, 4);
            byte flag;
            do
            {
                stream.read(array, 0, 1);  //mi sposto di 1 byte
                streamWrite.write(array, 0, 1);  //mi sposto di 1 byte
                flag = array[0];
                stream.read(array, 0, 3);  //leggo tre byte per la lunghezza del metadata
                int metadata_length = array[0] * 65536 + array[1] * 256 + array[2];
                if (flag == 4 || flag == 132)  //se è un vorbis comment cioè 00000100 oppure 10000100
                {
                    stream.skip(metadata_length); //mi sposto sul lettore della lunghezza del metadata
                    array[0] = (byte) (metadata_comments_length >> 16);
                    array[1] = (byte) (metadata_comments_length >> 8);
                    array[2] = (byte)(metadata_comments_length);
                    streamWrite.write(array, 0, 3);  //scrivo la nuova lunghezza del metadata
                    scriviCommenti(streamWrite);
                }
                else
                {
                    streamWrite.write(array, 0, 3);  //scrivo la lunghezza del metadata
                    int i = 0;
                    for (i = 0; (i+4)< metadata_length; i+=4)
                    {
                        stream.read(array, 0, 4);
                        streamWrite.write(array, 0, 4);
                    }
                    /* scrivo i byte rimanenti */
                    stream.read(array, 0, metadata_length-i);
                    streamWrite.write(array, 0, metadata_length -i);
                }
            } while (!((flag >> 7) == 0x1)); //se il bit più significativo è uguale a uno vuol dire ch enon ci sono più metadata
            /* scrivo tutto il rimanente */
            byte[] chunk = new byte[10240];


            int num = 0;
            while (true)
            {
                num = stream.read(chunk, 0, 10240);
                streamWrite.write(chunk, 0, num);
                if (num < 10240)
                {
                    stream.close();
                    streamWrite.close();
                    return;
                }
            }
        }
        catch(FileNotFoundException ex){
            
        }
        catch(IOException ex){
            
        }
            

    }
    public void writeAll(String filename)  //nuovo file
    {
    }
    private void caricaCommenti(BufferedInputStream stream)
    {
        try {
            byte[] array = { 0, 0, 0, 0 };
            stream.read(array, 0, 4);
            int vendor_length = (int)((array[0]));  //non capisco perché ma conta solo il primo byte
            byte[] stringa = new byte[1024];
            stream.read(stringa, 0, (int)vendor_length);
            vendor_string = new String(stringa,0,vendor_length,"UTF8");
            stream.read(array, 0, 4); //leggo il numero dei commenti
            int number_of_comments = (int)array[0];
            for (int i = 0; i < number_of_comments; i++)
            {
                stream.read(array, 0, 4); //leggo il numero di caratteri da leggere
                int comment_length = (int)array[0];
                stream.read(stringa, 0, (int)comment_length);
                String comment =new String(stringa, 0,(int) comment_length,"UTF8");
                comments.put(comment.split("=")[0].toUpperCase(),comment.split("=")[1]);
                java.util.Arrays.fill(stringa, (byte)0);
            }
        }
        catch (IOException ex){
            
        }
            
    }
    private void scriviCommenti(BufferedOutputStream stream)
    {
        try {
            int length = vendor_string.length();
            byte[] array = { 0, 0, 0, 0 };
            array[0] = (byte)(length);
            array[1] = 0;
            array[2] = 0;
            array[3] = 0;
            stream.write(array, 0, 4);
            byte[] arrayName = vendor_string.getBytes();
            stream.write(arrayName, 0, length);
            byte numcommenti = (byte)comments.values().size();
            array[0] = (byte)(numcommenti);
            stream.write(array, 0, 4);
            /* scrivo i commenti */
            for (int i = 0;i<numcommenti;i++)
            {
                String comment = comments.keySet().toArray()[i] + "=" + comments.values().toArray()[i];
                length = comment.length();
                arrayName = comment.getBytes();
                array[0] = (byte)length;
                array[1] = 0;
                array[2] = 0;
                array[3] = 0;
                stream.write(array, 0, 4);
                stream.write(arrayName, 0, length);
            }
        }
        catch (IOException ex){
            
        }
            
    }
}